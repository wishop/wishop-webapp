package com.wishop.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * This class is deprecated. In a real world application we should never get the Hibernate Session from a HibernateUtil, instead
 * we should be using Spring to inject the sessionfactory into the DAOs 
 * @author htran
 *
 */
public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	@SuppressWarnings("deprecation")
	private static SessionFactory buildSessionFactory() {
	    try {
	        // Create the SessionFactory from hibernate.cfg.xml
	        return new Configuration().configure().buildSessionFactory();
	    }
	    catch (Throwable ex) {
	        // Make sure you log the exception, as it might be swallowed
	        System.err.println("Initial SessionFactory creation failed." + ex);
	        throw new ExceptionInInitializerError(ex);
	    }
	}
	
	public static SessionFactory getSessionFactory() {
	    return sessionFactory;
	}
}
