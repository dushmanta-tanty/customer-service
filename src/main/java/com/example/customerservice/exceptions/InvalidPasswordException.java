package com.example.customerservice.exceptions;

public class InvalidPasswordException extends Exception {

	private static final long serialVersionUID = -1709713694745673160L;

	public InvalidPasswordException(String msg) {
		super("*Password : " + msg);
	}
}
