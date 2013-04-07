package com.wishop.run;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.wishop.dao.exceptions.HibernateSessionException;
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
			//with cache
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
		   logger.info("Address Details:");
		}
	}

	private void populateDatabase() throws NoSuchAlgorithmException, ParseException {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");

		User user1 = new User();
		user1.setFirstName("Paulo");
		user1.setLastName("Monteiro");
		Date date = formatter.parse("24/05/1982");
		user1.setDateOfBirth(date);
		user1.setEmail("paulo.from.portugal@gmail.com");
		user1.setPassword("password");
		user1.setMobile("07590541213");
		userService.saveOrUpdate(user1);
		
		User user2 = new User();
		user2.setFirstName("Hoang");
		user2.setLastName("Tran");
		date = formatter.parse("24/06/1984");
		user2.setDateOfBirth(date);
		user2.setEmail("hoang.from.australia@gmail.com");
		user2.setPassword("password");
		user2.setMobile("1231231321323");
		userService.saveOrUpdate(user2);
	}
}
