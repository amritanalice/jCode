package com.ibm.mr.inventory.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.mr.inventory.service.ImageStoreService;

@Component
public class ImageStoreServiceFileImpl implements ImageStoreService {

    private static final String IMAGE_FOLDER = "productmgmtimages";

    private boolean isFileServiceOn = false;
    private String imageFolderLocation = null;
    private String fileSeparatior = "/";

    /**
     * Initializes 
     * Checks 	if temp file is writable , if yes then turns the file service on 
     * else we dont save images
     * 
     * images are saved in windows tempdirectory/productmgmtimages/username_productid.jpg
     * 
     */
    @PostConstruct
    protected void initializeService() {


	String imageStoreLocation = getTempDirLocation();
	File fileBase = null;
	this.imageFolderLocation = imageStoreLocation + IMAGE_FOLDER;

	try {
	    fileBase = new File(imageStoreLocation);
	    if (!fileBase.canWrite()) {
		System.out.println("Setting file service off");
		isFileServiceOn = false;

		return;
	    }

	    File directory = new File(imageFolderLocation);
	    directory.delete();
	    directory.mkdir();
	    isFileServiceOn = true;

	} catch (Exception ex) {
	    ex.printStackTrace();
	    isFileServiceOn = false;
	}

    }


    /**
     * Stores image
     */
    public void storeImageFile(MultipartFile file, String imageName) {
	if (!isFileServiceOn || null == file || null == imageName) {
	    System.out.println("Empty inputs" + isFileServiceOn + file + imageName);
	    return;
	}
	try {
	    byte[] bytes = file.getBytes();
	    Path path = Paths.get(imageFolderLocation + this.fileSeparatior + imageName);
	    Files.write(path, bytes);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    
    public Resource loadImageAsResource(String imageName) {
	if (!isFileServiceOn) {
	    return null;
	}
	try {
	    System.out.println("trying to retrive image"+imageFolderLocation + this.fileSeparatior + imageName);
	    Path filePath = Paths.get(imageFolderLocation + this.fileSeparatior + imageName).normalize();
	    Resource resource = new UrlResource(filePath.toUri());
	    if (resource.exists()) {
		return resource;
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return null;
    }
    
    /**
     * deletes the image file 
     */
    public void deletResource(String imageName) {
	if (!isFileServiceOn || null == imageName) {
	    return ;
	}
	try {
	    System.out.println("trying to delete image"+imageFolderLocation + this.fileSeparatior + imageName);
	    Path filePath = Paths.get(imageFolderLocation + this.fileSeparatior + imageName).normalize();
	    Files.delete(filePath);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    /**
     * finds the temp directory location to store images
     * @return
     */
    protected String getTempDirLocation() {
	String tempFileLocation = System.getProperty("java.io.tmpdir").toLowerCase();
	System.out.println("temp file location" + tempFileLocation);
	return tempFileLocation;
    }

    protected void setFileSeparator() {
	String fileSeparator = System.getProperty("file.separator");
	this.fileSeparatior = fileSeparator;
    }

}
