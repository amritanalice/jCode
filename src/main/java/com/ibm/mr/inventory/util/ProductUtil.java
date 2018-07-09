package com.ibm.mr.inventory.util;

import java.math.BigDecimal;

import com.ibm.mr.inventory.model.Product;


public class ProductUtil {

    public final String imageRestUrl = "rest/product/productimage/";


    public Product addProduct(int productId, String name, String description, BigDecimal price, int quantity,
	    String user, String imageUrl) {

	Product product = new Product();
	product.setId(productId);
	product.setName(name);
	product.setDescription(description);
	product.setPrice(price);
	product.setQuantity(quantity);
	product.setUser(user);
	if (null != imageUrl) {
	    product.setImageUrl(imageUrl);
	}
	return product;
    }

    public static void getFileLocation() {
	System.out.println(System.getProperty("os.name"));
    }

    public String getFileImageUrl(String savedFileName) {
	return imageRestUrl + savedFileName;
    }


    public String getFileNameToSave(String fileName, Integer productId, String user) {
	return user + "_" + productId + "." + getFileExtension(fileName);
    }

    protected String getFileExtension(String fileName) {
	String[] split = fileName.split("\\.");
	String ext = split[split.length - 1];
	return ext;
    }

    public String getImageFileLocation(Product product) {
	if(null == product)
	{
	    return null;
	}
	String imageUrl = product.getImageUrl();
	String[] split = imageUrl.split("/");
	String fileName = split[split.length - 1];
	System.out.println("File to delete = " + fileName);
	return fileName;
    }


}
