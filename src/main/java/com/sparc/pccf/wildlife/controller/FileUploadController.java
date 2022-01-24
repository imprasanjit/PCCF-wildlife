package com.sparc.pccf.wildlife.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sparc.pccf.wildlife.response.FileUploadResponse;
import com.sparc.pccf.wildlife.service.FileStorageService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/uploadController")
public class FileUploadController {

	@Autowired
	private FileStorageService fileStorageService;

	@PostMapping("/uploadFile")
	public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String iemiNo,
			@RequestParam String userId) {
		return fileStorageService.storeFile(file, iemiNo, userId);
	}

	@PostMapping("/uploadImage")
	public FileUploadResponse uploadImage(@RequestParam("file") MultipartFile file, @RequestParam String userId,@RequestParam String type) {
		return fileStorageService.uploadImage(file, userId,type);
	}

	@GetMapping("/downloadFile")
	public HttpServletResponse downloadFile(HttpServletResponse response, @RequestParam String location) {
		return fileStorageService.downloadFile(location, response);
	}

	@PostMapping("/uploadPhoto")
	public String uploadPhoto(@RequestParam("file") MultipartFile file,@RequestParam String type,@RequestParam String userId) {
		return fileStorageService.uploadPhoto(file,type,userId);
	}

	@PostMapping("/uploadMultipleFiles")
	public List<FileUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
			@RequestParam String iemiNo, @RequestParam String userId) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file, iemiNo, userId)).collect(Collectors.toList());
	}
}
