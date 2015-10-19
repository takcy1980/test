/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.controllers.photographers;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author René
 */
public class FileUpload {
       private List<MultipartFile> fileList;
 
    public List<MultipartFile> getFiles() {
        return fileList;
    }
 
    public void setFiles(List<MultipartFile> files) {
        this.fileList = files;
    } 
}
