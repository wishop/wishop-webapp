package com.wishop.run;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.wishop.dao.exceptions.HibernateSessionException;
import com.wishop.model.Address;
import com.wishop.model.User;
import com.wishop.service.UserService;
import com.wishop.utils.WishopApplicationContext;

public class SimpleCommand {

	private UserService userService;
	private Logger logger = Logger.getLogger(SimpleCommand.class);
	
	public void run() {
		try{
			userService = (UserService) WishopApplicationContext.getBean("userService");
			populateDatabase();
			displayDatabase();
			logger.info("NOW WITH CACHE! (no call to the DB is made) ");
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
		   if(user.getAddress() != null) {
			   logger.info("Address: " + user.getAddress().getFullAddress());
		   }
		}
	}

	private void populateDatabase() throws NoSuchAlgorithmException, ParseException {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");

		User user1 = new User();
		user1.setTitle("Mr");
		user1.setFirstName("Test");
		user1.setLastName("User 2");
		Date date = formatter.parse("24/01/2013");
		user1.setDateOfBirth(date);
		user1.setEmail("test.user2@mailinator.com");
		user1.setPassword("password");
		user1.setMobile("1231231231");
		user1.setProfile("Hello World");
		Address address1 = new Address();
		address1.setAddressLine1("Test Address 2");
		address1.setCity("London");
		address1.setCounty("London");
		address1.setCountry("UK");
		address1.setPostcode("SE1");
		user1.setAddress(address1);
		userService.save(user1);
	}
}
