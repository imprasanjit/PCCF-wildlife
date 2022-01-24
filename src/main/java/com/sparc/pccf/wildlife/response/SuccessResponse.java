package com.sparc.pccf.wildlife.response;

public class SuccessResponse<T> {

	private Status status = new Status();

	private T response;

	public SuccessResponse(T restResponse) {
		setResponse(restResponse);
	}

	public SuccessResponse(T restResponse, String successMessage) {
		setResponse(restResponse);
		this.status.setMessage(successMessage);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

}