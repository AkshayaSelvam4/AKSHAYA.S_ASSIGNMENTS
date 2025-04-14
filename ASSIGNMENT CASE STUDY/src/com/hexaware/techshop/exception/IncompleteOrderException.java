package com.hexaware.techshop.exception;

public class IncompleteOrderException extends Exception {
	private static final long serialVersionUID = 1L;
    public IncompleteOrderException(String message) {
        super(message);
    }

}
