package com.wishop.dao.exceptions;

public class HibernateSessionException extends RuntimeException
{

	private static final long serialVersionUID = -2630928336709482286L;

	public HibernateSessionException (String message) {
		super(message);
	}
	
	public HibernateSessionException (String message, Throwable throwable) {
		super(message, throwable);
	}
}