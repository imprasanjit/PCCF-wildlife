package com.sparc.pccf.wildlife.exception;

public class WrongPasswordException extends RuntimeException {
	public WrongPasswordException(String message) {
        super(message);
    }
}
