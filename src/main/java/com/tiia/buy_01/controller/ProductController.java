package com.tiia.buy_01.controller;

import com.tiia.buy_01.exceptions.DataNotValidatedException;
import com.tiia.buy_01.model.Product;
import com.tiia.buy_01.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/allProducts")
    public ResponseEntity<List<Product>> ListAllProducts() {
        List<Product> allProducts = productService.listAll();
        return ResponseEntity.ok().body(allProducts);
    }

    @GetMapping(value = "/productDetails/{id}")
    public ResponseEntity<Product> getProductDetails(@PathVariable("id") String id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping(value = "/allProducts/{userId}")
    public ResponseEntity<List<Product>> getAllProductsByUserId(@PathVariable("userId") String userId) {
        List<Product> allProducts = productService.listAllByUserId(userId);
        return  ResponseEntity.ok().body(allProducts);
    }

    @PreAuthorize(value = "hasAuthority('Seller')")
    @PostMapping(value = "/storeProduct")
    public ResponseEntity<Product> storeProduct(@Validated @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            Error error = new Error("Data is not validated");
            throw new DataNotValidatedException(error);
        }
        Product storedProduct = productService.storeProduct(product);
        return ResponseEntity.ok().body(storedProduct);
    }

    @DeleteMapping(value = "/removeProduct/{id}")
    public ResponseEntity<String> removeProduct(@PathVariable("id") String productId) {
        productService.removeProduct(productId);
        return  ResponseEntity.ok().body("Product has been deleted with the id: " + productId);
    }

    @PutMapping(value = "updateProduct/{id}")
    public ResponseEntity<String> updateProduct(@Validated @RequestBody Product product, @PathVariable("id") String productId, Errors errors) {
        if (errors.hasErrors()) {
            Error error = new Error("Data is not validated");
            throw new DataNotValidatedException(error);
        }
        Product updatedProduct = productService.updateProduct(product, productId);
        return ResponseEntity.ok().body("Product has been successfully updated with the id: " + updatedProduct.getId());
    }
}
