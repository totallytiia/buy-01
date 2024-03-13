package com.tiia.buy_01.services.impl;

import com.tiia.buy_01.exceptions.InstanceUndefinedException;
import com.tiia.buy_01.model.Product;
import com.tiia.buy_01.repository.ProductRepository;
import com.tiia.buy_01.response.UserResponse;
import com.tiia.buy_01.services.ProductService;
import com.tiia.buy_01.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Override
    public Product storeProduct(Product product) {
        UserResponse currentUser = userService.getCurrentUser();
        product.setUserId(currentUser.getId());
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(String productId) {
        Product returnValue = null;
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            returnValue = optionalProduct.get();
        } else {
            Error error = new Error("Product not found with the ID.");
            throw new InstanceUndefinedException(error);
        }
        return returnValue;
    }

    @Override
    public List<Product> listAllByUserId(String userId) {
        return productRepository.findByUserId(userId);
    }

    @Override
    public List<Product> listAll() {
        return productRepository.findAll();
    }

    @Override
    public void removeProduct(String productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        UserResponse currentUser = userService.getCurrentUser();
        if (productOptional.isPresent()) {
            if (productOptional.get().getUserId().equalsIgnoreCase(currentUser.getId())) {
                productRepository.deleteById(productId);
            } else {
                //fix error message
                Error error = new Error("The product has not been found!");
                throw new InstanceUndefinedException(error);
            }
        } else {
            Error error = new Error("The product has not been found!");
            throw new InstanceUndefinedException(error);
        }
    }

    @Override
    public Product updateProduct(Product product, String productId) {
        Product returnValue = null;
        UserResponse currentUser = userService.getCurrentUser();
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            if (currentUser.getId().equalsIgnoreCase(productOptional.get().getUserId())) {
                product.setUserId(currentUser.getId());
                product.setId(productId);
                returnValue = productRepository.save(product);
            } else {
                // fix error message
                Error error = new Error("The product has not been found!");
                throw new InstanceUndefinedException(error);
            }
        } else {
            Error error = new Error("The product has not been found!");
            throw new InstanceUndefinedException(error);
        }
        return returnValue;
    }
}
