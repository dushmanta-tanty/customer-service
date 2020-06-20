package com.example.customerservice.exceptions;

public class CustomerNotFoundException extends Exception {

	private static final long serialVersionUID = -3468058475379849440L;

	public CustomerNotFoundException(String message) {
		super(message);
	}
}
