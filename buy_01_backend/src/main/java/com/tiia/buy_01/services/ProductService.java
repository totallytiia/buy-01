package com.tiia.buy_01.services;

import com.tiia.buy_01.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {

    Product storeProduct(Product product);

    Product getProductById(String productId);

    List<Product> listAllByUserId(String userId);

    List<Product> listAll();

    void removeProduct(String productId);

    Product updateProduct(Product product, String productId);

}