package com.tiia.buy_01.repository;

import com.tiia.buy_01.model.Media;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MediaRepository extends MongoRepository<Media, String> {
    List<Media> findByProductId(String productId);
    Media findMediaById(String id);

}
