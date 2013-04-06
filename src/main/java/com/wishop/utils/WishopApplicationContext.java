package com.wishop.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class WishopApplicationContext implements ApplicationContextAware {
	
	private static WishopApplicationContext cmsApplicationContext;
	private static ApplicationContext applicationContext;
	
	public static WishopApplicationContext getInstance() {
		if(cmsApplicationContext == null) {
			cmsApplicationContext = new WishopApplicationContext();
		}
		return cmsApplicationContext;
	}
	
	/**
     * Sets the application context, so the bean becomes aware of the Spring context.
     * This is done automatically by Spring
     * @param applicationContext 
     */
	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		applicationContext = appContext;
	}
	
	/**
     * Returns the ApplicationContext object, previously injected by Spring. 
     * @return ApplicationContext
     */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
