package com.tiia.buy_01.services;

import com.tiia.buy_01.model.Media;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;


public interface MediaService {

    void removeMedia(String id);

    List<Media> getAllMediaByProductId(String userId);

    Media storeMedia(MultipartFile file, String productId);

    Media getMedia(String id);

   List<Media> getAllMedia();

}
