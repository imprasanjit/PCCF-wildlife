package com.sparc.pccf.wildlife.response;

public class ErrorResponse {

	private Status status;

	public ErrorResponse() {

		status = new Status();
	}

	public ErrorResponse(int code, String message) {
		this.status = new Status(code, message);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
