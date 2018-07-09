package com.ibm.mr.inventory.app.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;
import com.ibm.mr.inventory.dao.impl.ProductJavaInMemoryStoreDAOImpl;
import com.ibm.mr.inventory.model.Product;
import com.ibm.mr.inventory.service.impl.ImageStoreServiceFileImpl;
import com.ibm.mr.inventory.util.ProductUtil;
import com.ibm.mr.inventory.views.JsonViews;

@Controller
@RequestMapping("/rest/product")
public class ProductController {


    @Autowired
    ProductJavaInMemoryStoreDAOImpl productJavaStore;

    @Autowired
    ImageStoreServiceFileImpl imageStoreServiceFileImpl;

    @JsonView(JsonViews.Public.class)
    @ResponseBody
    @RequestMapping(value = "/getallproductsforuser", method = RequestMethod.GET)
    public List<Product> getAllProductsForUser(Principal principal) {
	String loggedInUser = principal.getName();
	List<Product> productList = productJavaStore.getProductListForUser(loggedInUser);
	System.out.println("Priting all user at" + new Date());
	for (Product product : productList) {
	    System.out.println(product.toString());
	}
	return productList;
    }

    @JsonView(JsonViews.Public.class)
    @ResponseBody
    @RequestMapping(value = "/getallproducts", method = RequestMethod.GET)
    public List<Product> getAllProducts(Principal principal) {
	List<Product> productList = null;
	try {
	    productList = productJavaStore.getAllProducts();
	    System.out.println("Priting all list at" + new Date());
	    for (Product product : productList) {
		System.out.println(product.toString());
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return productList;
    }

    @ResponseBody
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    public String addProduct(Principal principal, @RequestParam("productName") String productName,
	    @RequestParam("productId") int productId, @RequestParam("quantity") int quantity,
	    @RequestParam("productDescription") String productDescription, @RequestParam("price") BigDecimal price,
	    @RequestParam(value = "productImage", required = false) MultipartFile file) {

	try {
	    String fileNameToSave = null;
	    String loggedInUser = principal.getName();
	    ProductUtil productUtil = new ProductUtil();
	    String imageUrl = null;

	    if (null != file) {
		fileNameToSave = productUtil.getFileNameToSave(file.getOriginalFilename(), productId, loggedInUser);
		imageUrl = productUtil.getFileImageUrl(fileNameToSave);
	    }
	    Product product = productUtil.addProduct(productId, productName, productDescription, price, quantity,
		    loggedInUser, imageUrl);
	    productJavaStore.addProduct(loggedInUser, product);
	    imageStoreServiceFileImpl.storeImageFile(file, fileNameToSave);
	} catch (Exception ex) {
	    ex.printStackTrace();
	    return "Product Add Failed";
	}
	return "Product Added";

    }

    @ResponseBody
    @RequestMapping(value = "/updateproduct", method = RequestMethod.POST)
    public String updateProduct(Principal principal, @RequestParam("productName") String productName,
	    @RequestParam("productId") int productId, @RequestParam("quantity") int quantity,
	    @RequestParam("productDescription") String productDescription, @RequestParam("price") BigDecimal price,
	    @RequestParam(value = "productImage", required = false) MultipartFile file) {

	try {
	    String loggedInUser = principal.getName();
	    ProductUtil productUtil = new ProductUtil();
	    String fileNameToSave = null;
	    String imageUrl = null;

	    if (null != file) {
		fileNameToSave = productUtil.getFileNameToSave(file.getOriginalFilename(), productId, loggedInUser);
		imageUrl = productUtil.getFileImageUrl(fileNameToSave);
	    }
	    Product product = productUtil.addProduct(productId, productName, productDescription, price, quantity,
		    loggedInUser, imageUrl);
	    productJavaStore.addProduct(loggedInUser, product);
	    imageStoreServiceFileImpl.storeImageFile(file, fileNameToSave);
	} catch (Exception ex) {
	    ex.printStackTrace();
	    return "Product Update  Failed";
	}
	return "Product Updated";

    }

    @ResponseBody
    @RequestMapping(value = "/deleteproduct", method = RequestMethod.POST)
    public String deleteProduct(Principal principal, @RequestParam("productId") int productId) {

	try {
	    String loggedInUser = principal.getName();	
	    ProductUtil productUtil = new ProductUtil();
	    Product product = productJavaStore.getProductById(loggedInUser, productId);
	    String imageFileName = productUtil.getImageFileLocation(product);
	    productJavaStore.deleteProduct(loggedInUser, productId);
	    imageStoreServiceFileImpl.deletResource(imageFileName);
	} catch (Exception ex) {
	    ex.printStackTrace();
	    return "Product Delete Failed";
	}
	return "Product Delete";

    }


    @RequestMapping(value = "/productimage/{fileName:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getFile(@PathVariable("fileName") String fileName) {
	System.out.println("file name="+fileName);
	Resource file = imageStoreServiceFileImpl.loadImageAsResource(fileName);
	if (null != file) {
	    return ResponseEntity.ok()
		    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
		    .body(file);
	} else {
	    return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
	}

    }


}
