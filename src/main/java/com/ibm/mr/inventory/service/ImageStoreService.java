package com.ibm.mr.inventory.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStoreService {

    public void storeImageFile(MultipartFile file, String imageName);

    public Resource loadImageAsResource(String imageName);
    
    public void deletResource(String imageName) ;

}
