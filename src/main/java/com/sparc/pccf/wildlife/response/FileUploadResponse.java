package com.sparc.pccf.wildlife.response;

public class FileUploadResponse {
	
	private String folderName;

	private String fileName;

	public FileUploadResponse() {
		super();
	}

	public FileUploadResponse(String folderName, String fileName) {
		super();
		this.folderName = folderName;
		this.fileName = fileName;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
