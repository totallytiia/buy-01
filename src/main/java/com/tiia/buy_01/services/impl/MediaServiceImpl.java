package com.tiia.buy_01.services.impl;

import com.tiia.buy_01.exceptions.InstanceUndefinedException;
import com.tiia.buy_01.model.Media;
import com.tiia.buy_01.model.Product;
import com.tiia.buy_01.repository.MediaRepository;
import com.tiia.buy_01.repository.ProductRepository;
import com.tiia.buy_01.response.UserResponse;
import com.tiia.buy_01.services.MediaService;
import com.tiia.buy_01.services.ProductService;
import com.tiia.buy_01.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Override
    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    @Override
    public void removeMedia(String id) {
        Optional<Media> mediaOptional = mediaRepository.findById(id);
        UserResponse currentUser = userService.getCurrentUser();
        if (mediaOptional.isPresent()) {
            String productId = mediaOptional.get().getProductId();
            System.out.println(productId);
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                if (optionalProduct.get().getUserId().equalsIgnoreCase(currentUser.getId())) {
                    mediaRepository.deleteById(id);
                } else {
                    //fix error message
                    Error error = new Error("Not rights to modify this product!");
                    throw new InstanceUndefinedException(error);
                }
            } else {
                //fix error message
                Error error = new Error("The product has not been found!");
                throw new InstanceUndefinedException(error);
            }
        } else {
            Error error = new Error("The media has not been found!");
            throw new InstanceUndefinedException(error);
        }
    }

    @Override
    public List<Media> getAllMediaByProductId(String productId) {
        return mediaRepository.findByProductId(productId);
    }

    @Override
    public Media getMedia(String id) {
        Media returnValue = null;
        Optional<Media> mediaOptional = mediaRepository.findById(id);
        if (mediaOptional.isPresent()) {
            returnValue = mediaOptional.get();
        } else {
            Error error = new Error("Media not found.");
            throw new InstanceUndefinedException(error);
        }
        return returnValue;
    }

    @Override
    public Media storeMedia(MultipartFile file, String productId) {
        try {
            Product product = productService.getProductById(productId);
            UserResponse currentUser = userService.getCurrentUser();

            if (!product.getUserId().equals(currentUser.getId())) {
                Error error = new Error("Not authorized to upload media for this product!");
                throw new InstanceUndefinedException(error);
            }

            // Create a new Media object
            Media media = new Media();

            media.setProductId(productId);
            media.setName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
            media.setContentType(file.getContentType());
            media.setSize(file.getSize());
            media.setData(file.getBytes());
            Media storedMedia = mediaRepository.save(media);

            // Save the Media object to the database
            return mediaRepository.save(storedMedia);
        } catch (IOException e) {
            // Handle exceptions
            throw new RuntimeException("Error processing file", e);
        }
    }
}
