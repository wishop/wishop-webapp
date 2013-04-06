package com.wishop.model.exceptions;

/**
 * Default application exception class
 * @author pmonteiro
 *
 */
public class WishopException extends Exception {

	private static final long serialVersionUID = 6316168427858619960L;

	public WishopException(String message) {
		super(message);
	}
	
	public WishopException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
