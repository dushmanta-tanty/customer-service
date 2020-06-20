package com.example.customerservice.exceptions;

public class RecordExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public RecordExistException(String message) {
		super(message);
	}
}
