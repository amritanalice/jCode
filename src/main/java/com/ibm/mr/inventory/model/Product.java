package com.ibm.mr.inventory.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;
import com.ibm.mr.inventory.views.JsonViews;


public class Product {

    @JsonView(JsonViews.Public.class)
    private Integer id;
    @JsonView(JsonViews.Public.class)
    private String description;
    @JsonView(JsonViews.Public.class)
    private String imageUrl;
    @JsonView(JsonViews.Public.class)
    private String name;

    @JsonView(JsonViews.Public.class)
    private BigDecimal price;

    @JsonView(JsonViews.Public.class)
    private int quantity;
    
    @JsonView(JsonViews.Public.class)
    private String user;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
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

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getImageUrl() {
	return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
	return price;
    }

    public void setPrice(BigDecimal price) {
	this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}

	if (!(obj instanceof Product)) {
	    return false;
	}

	Product product = (Product) obj;

	return product.id == this.id && this.getName().equals(product.getName()) && this.price == product.getPrice();


    }

    @Override
    public int hashCode() {

	return this.id;
    }
    
    public String toString()
    {
	return this.name+","+this.id+","+this.quantity+","+this.imageUrl;
    }

}
