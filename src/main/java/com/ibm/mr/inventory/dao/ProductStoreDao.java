package com.ibm.mr.inventory.dao;

import java.util.List;
import java.util.Map;

import com.ibm.mr.inventory.model.Product;

public interface ProductStoreDao {

    public List<Product> getProductListForUser(String userName) throws Exception;

    public void addProduct(String userName, Product product) throws Exception;

    public Map<Integer, Product> getProductStoreByUserName(String userName) throws Exception;

    public List<Product> getAllProducts() throws Exception;


}
