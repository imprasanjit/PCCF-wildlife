package com.sparc.pccf.wildlife.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.sparc.pccf.wildlife.response.FileUploadResponse;

public interface FileStorageService {

	public HttpServletResponse downloadFile(String location, HttpServletResponse response);

	public FileUploadResponse uploadImage(MultipartFile file, String userId,String type);

	//public Resource loadFileAsResource(String fileName);

	public FileUploadResponse storeFile(MultipartFile file, String iemiNo, String userId);

	public String uploadPhoto(MultipartFile file,String type,String userId);

	

}
