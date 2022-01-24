package com.sparc.pccf.wildlife.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sparc.pccf.wildlife.entity.PublicUserDO;
import com.sparc.pccf.wildlife.exception.FileStorageException;
import com.sparc.pccf.wildlife.repository.IGajasathiUserRepository;
import com.sparc.pccf.wildlife.response.FileUploadResponse;
import com.sparc.pccf.wildlife.service.FileStorageService;
import com.sparc.pccf.wildlife.service.IGajasathiUserService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

	@Value("${file.upload.directory}")
	private String fileUploadDirectory;
	
	@Value("${file.upload-dir}")
	private String path;
	
	@Autowired
	ServletContext context;
	
	@Autowired
	IGajasathiUserService iGajasathiUserService;
	
	@Autowired
	IGajasathiUserRepository iGajasathiUserRepository;

	@Override
	public FileUploadResponse storeFile(MultipartFile file, String iemiNo, String userId) {
		MultipartFile multipartFile = file;
		
		int maxValue = Integer.MAX_VALUE;
		//String folderName = Integer.toString(new Random().nextInt(1000000));
		
		//String filePath1 = fileUploadDirectory ;
		String filePath = context.getRealPath(fileUploadDirectory);
		try {
			File newDirectory = new File(filePath);
			boolean mkdirs = newDirectory.mkdirs();
			File uploadedFile = new File(newDirectory, iemiNo+"_"+userId+"_"+multipartFile.getOriginalFilename());
			uploadedFile.createNewFile();
			uploadedFile.setReadable(true);
			FileOutputStream fileOutputStream = new FileOutputStream(uploadedFile);
			fileOutputStream.write((multipartFile.getBytes()));
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		FileUploadResponse fileUploadResponse = new FileUploadResponse();
		fileUploadResponse.setFileName(iemiNo+"_"+userId+"_"+multipartFile.getOriginalFilename());
		//fileUploadResponse.setFolderName(folderName);
		return fileUploadResponse;
	}

	@Override
	public HttpServletResponse downloadFile(String location,HttpServletResponse response) 
	{
		String filePath1 = fileUploadDirectory +"/"+ location;
		File file = new File(context.getRealPath(filePath1));

		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(file);

			IOUtils.copy(fileInputStream, response.getOutputStream());
			response.setContentLength(fileInputStream.available());
			response.getOutputStream().flush();
		} catch (IOException e) {
			  e.printStackTrace();
			  throw new FileStorageException("File Not Found");
		}
		return response;

	}

	@Override
	public FileUploadResponse uploadImage(MultipartFile file, String userId,String type) {
		String originalFilename=null;
		try {
			Random random = new Random(); 
			//String fileNameWithOutExt = FilenameUtils.removeExtension(file.getOriginalFilename());
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			originalFilename=type+"_"+userId+"_"+(file.getOriginalFilename()).substring(0,3)+"_"+ LocalDateTime.now()+"."+extension;  
			InputStream inputStream = file.getInputStream();
			Files.copy(inputStream, Paths.get(path +type+"/"+ originalFilename),StandardCopyOption.REPLACE_EXISTING);
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	
		FileUploadResponse fileUploadResponse = new FileUploadResponse();
		fileUploadResponse.setFileName(originalFilename);
		//fileUploadResponse.setFolderName(folderName);
		return fileUploadResponse;
	}

	@Override
	public String uploadPhoto(MultipartFile file,String type,String userId) {
		String originalFilename =null;
		try {	
			Random random = new Random(); 
			//String fileNameWithOutExt = FilenameUtils.removeExtension(file.getOriginalFilename());
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			originalFilename=type.substring(0,2)+"_"+userId+"_"+(file.getOriginalFilename()).substring(0,3)+"_"+random.nextInt(1000)+"."+extension;  
			//originalFilename=file.getOriginalFilename();
			InputStream inputStream = file.getInputStream();
			Files.copy(inputStream, Paths.get(path +type+"/"+originalFilename),StandardCopyOption.REPLACE_EXISTING);
			PublicUserDO publicUserDO = iGajasathiUserRepository.findByMobile(Long.parseLong(userId));
			if(!originalFilename.isEmpty()) 
			{
				publicUserDO.setImgPath(originalFilename);
				publicUserDO=iGajasathiUserRepository.save(publicUserDO);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return originalFilename;
	}
}
