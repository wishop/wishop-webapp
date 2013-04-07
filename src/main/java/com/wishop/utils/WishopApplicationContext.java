package com.wishop.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WishopApplicationContext implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
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
	 * Method that retuns a bean from the Spring Application context
	 * @param beanName
	 * @return Proxy of bean
	 */
	public static Object getBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}

	/**
	 * Method that retrieves the Spring Application context
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		
		if(applicationContext == null) {
			String [] applicationContexts = new String[] { 
					"classpath:/META-INF/applicationContext.xml",  
					"classpath:/META-INF/springApp/daoContext-app.xml" };
			applicationContext = new ClassPathXmlApplicationContext(applicationContexts);
		}
		return applicationContext;
	}

}
