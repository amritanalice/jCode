package com.ibm.mr.inventory.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.ibm.mr.inventory.dao.ProductStoreDao;
import com.ibm.mr.inventory.model.Product;

@Component
public class ProductJavaInMemoryStoreDAOImpl implements ProductStoreDao {

    private Map<String, Map<Integer, Product>> productStore;

    @PostConstruct
    protected void initializeStore() {
	this.productStore = new HashMap<String, Map<Integer, Product>>();
    }


    public void addProductToStoreForUser(Map<Integer, Product> productStoreForUser, Product productToAdd) {
	Integer productIdToAdd = productToAdd.getId();
	Product existingProductWithId = productStoreForUser.get(productIdToAdd);
	if (null == existingProductWithId) {
	    productStoreForUser.put(productIdToAdd, productToAdd);
	    System.out.println("Addinf new product=" + productToAdd.getName());
	} else {
	    if (existingProductWithId.equals(productToAdd)) {
		int quantityBeforeAdd = existingProductWithId.getQuantity();
		existingProductWithId.setQuantity(quantityBeforeAdd + productToAdd.getQuantity());
		System.out.println("Incrementing quantity for product " + existingProductWithId.getName()
			+ ", product to add was:" + productToAdd.getName());
	    } else {
		// Override with new data , just increment quantity
		int quantityBeforeAdd = existingProductWithId.getQuantity();
		productToAdd.setQuantity(quantityBeforeAdd + productToAdd.getQuantity());
		productStoreForUser.put(productIdToAdd, productToAdd);
		System.out.println("Incrementing quantity for product " + existingProductWithId.getName()
			+ ", product to add was:" + productToAdd.getName());
	    }

	}

    }


    public Map<Integer, Product> getProductStoreByUserName(String userName) throws Exception {
	Map<Integer, Product> productForUser = this.productStore.get(userName);
	if (null == productForUser) {
	    productForUser = Collections.synchronizedMap(new HashMap<Integer, Product>());
	    this.productStore.put(userName, productForUser);
	}
	return productForUser;

    }

    public Product getProductById(String userName, Integer productId) throws Exception {
	Map<Integer, Product> productForUser = this.productStore.get(userName);
	if (null != productForUser) {
	    return productForUser.get(productId);
	} else {
	    return null;
	}

    }

    public List<Product> getProductListForUser(String userName) {
	System.out.println("Getting List for user=" + userName);
	List<Product> productList = new ArrayList<Product>();
	if (null != this.productStore.get(userName)) {
	    productList = new ArrayList<Product>(this.productStore.get(userName).values());
	}
	return productList;
    }

    public List<Product> getAllProducts() throws Exception {

	List<Product> productListAll = new ArrayList<Product>();
	for (String userName : this.productStore.keySet()) {
	    List<Product> productList = new ArrayList<Product>(this.productStore.get(userName).values());
	    if (null != productList) {
		productListAll.addAll(productList);
	    }
	}
	return productListAll;
    }

    public void updateProduct(String userName, Product product) throws Exception {
	Map<Integer, Product> productStoreForUser = getProductStoreByUserName(userName);
	productStoreForUser.put(product.getId(), product);
    }

    public void deleteProduct(String userName, Integer productId) throws Exception {
	Map<Integer, Product> productStoreForUser = getProductStoreByUserName(userName);
	productStoreForUser.remove(productId);
    }


    public void addProduct(String userName, Product product) throws Exception {
	Map<Integer, Product> productStoreForUser = getProductStoreByUserName(userName);
	addProductToStoreForUser(productStoreForUser, product);
    }


}
