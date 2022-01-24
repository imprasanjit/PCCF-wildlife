package com.sparc.pccf.wildlife.response;

public class Status {
	private int code = 0;

	private String message;

	public Status(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public Status() {

	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
