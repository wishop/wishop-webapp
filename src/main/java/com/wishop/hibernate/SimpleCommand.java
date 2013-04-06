package com.wishop.hibernate;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wishop.dao.exceptions.HibernateSessionException;
import com.wishop.model.User;
import com.wishop.service.UserService;

public class SimpleCommand {

	private UserService userService;
	//private RoleService cmsRoleService;
	private Logger logger = Logger.getLogger(SimpleCommand.class);
	private String [] applicationContexts;
	public void run() {
		try{
			applicationContexts = new String[] { 
					"classpath:/META-INF/applicationContext.xml",  
					"classpath:/META-INF/springApp/daoContext-app.xml" };
			ApplicationContext context = new ClassPathXmlApplicationContext(applicationContexts);
			userService = (UserService) context.getBean("userService");
			//cmsRoleService = (ICmsRoleService) context.getBean("cmsRoleService");
			populateDatabase();
			displayDatabase();
			//with cache
			logger.info("WITH CACHE NOW!");
			displayDatabase();
		} catch (HibernateSessionException e) {
			logger.error("Error", e);
			throw new HibernateSessionException("Error", e);			
		} catch (Exception e) {
			logger.error("Error", e);
			throw new HibernateSessionException(e.getMessage(), e);
		} 
	}
	
	private void displayDatabase() {
		Iterator<User> usersList = userService.getAll().iterator();
		while (usersList.hasNext()) {
		   User user = (User) usersList.next();
		   logger.info("====User====");
		   logger.info("Email: " + user.getEmail());
		   logger.info("Name: " + user.getFullName());
		   logger.info("DoB: " + user.getDateOfBirth());
		   logger.info("Address Details:");
		   //CmsAddress userCmsAddress = user.getCmsAddress();
//		   if(userCmsAddress != null) {
//			   logger.info("Address Line 1: " + userCmsAddress.getAddressLine1());
//			   logger.info("Address Line 2: " + userCmsAddress.getAddressLine2());
//			   logger.info("Address Line 3: " + userCmsAddress.getAddressLine3());
//			   logger.info("City: " + userCmsAddress.getCity());
//			   logger.info("County: " + userCmsAddress.getCounty());
//			   logger.info("Postcode: " + userCmsAddress.getPostcode());
//			   logger.info("--------");
//		   }
		}
	}

	private void populateDatabase() throws NoSuchAlgorithmException, ParseException {
		User user = new User();
		user.setFirstName("Paulo");
		user.setLastName("Monteiro");
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		Date date = formatter.parse("24/05/1982");
		user.setDateOfBirth(date);
		user.setEmail("paulo.from.portugal001@gmail.com");
		user.setPassword("password");
		user.setMobile("07590541213");
		userService.saveOrUpdate(user);
	}
}
