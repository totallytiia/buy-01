package com.tiia.buy_01.controller;

import com.tiia.buy_01.model.Media;
import com.tiia.buy_01.model.Product;
import com.tiia.buy_01.services.MediaService;
import com.tiia.buy_01.utils.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @Autowired
    private FileValidator fileValidator;

    @GetMapping(value = "/allMedia")
    public ResponseEntity<List<Media>> getAllMedia() {
        List<Media> allMedia = mediaService.getAllMedia();
        return ResponseEntity.ok().body(allMedia);
    }

    @GetMapping(value = "/allMedia/{productId}")
    public ResponseEntity<List<Media>> getAllProductsByUserId(@PathVariable("productId") String productId) {
        List<Media> allMedia = mediaService.getAllMediaByProductId(productId);
        return  ResponseEntity.ok().body(allMedia);
    }


    @GetMapping(value = "/getMediaUrl/{id}")
    public ResponseEntity<String> getMediaUrl(@PathVariable String id) {
        Media media = mediaService.getMedia(id);
        // check this
        return ResponseEntity.ok().body("Media found");
    }

    @DeleteMapping(value = "/removeMedia/{id}")
    public ResponseEntity<String> removeProduct(@PathVariable("id") String id) {
        mediaService.removeMedia(id);
        return  ResponseEntity.ok().body("Media has been deleted with the id: " + id);
    }

    @PreAuthorize(value = "hasAuthority('Seller')")
    @PostMapping(value = ("/storeMedia"))
    public ResponseEntity<Media> storeMedia(
            @RequestPart("file") MultipartFile file,
            @RequestPart("productId") String productId
    ) throws IOException {
        fileValidator.validateFile(file);
        Media storedMedia = mediaService.storeMedia(file, productId);
        return ResponseEntity.ok().body(storedMedia);
    }
}
