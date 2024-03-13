package com.tiia.buy_01.model;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "products")
public class Product implements Serializable {

    @Id
    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Size(min = 6, max = 200)
    private String description;

    @NotNull
    @DecimalMin(value = "0.01")
    @DecimalMax(value = "5000.00")
    private Double price;

    @Range(max = 1000L)
    private int quantity;

    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
