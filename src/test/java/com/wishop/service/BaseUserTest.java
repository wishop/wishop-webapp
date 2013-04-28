package com.wishop.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.wishop.model.Address;
import com.wishop.model.AuditInfo;
import com.wishop.model.User;

public class BaseUserTest {
	
	protected static final String USER_TEST_PASSWORD = "Password123";
	protected static final String USER_ENTITY_CLASS_NAME = "com.wishop.model.User";
	protected static final String TEST_USER_EMAIL_1 = "test.user@mailinator.com";
	protected static final String TEST_USER_EMAIL_2 = "test.user2@mailinator.com";
	
	protected static final String ATTR_USER = "user";
	
	protected static final int USER_ID_4 = 4;

	protected static final int USER_ID_3 = 3;
	
	protected static final int NO_USER_ID = -1;
	
	protected static final int USERS_LIST_SIZE = 3;
	
	@Autowired
	protected Md5PasswordEncoder passwordEncoder;

	protected void testGetAll(List<User> users, int listSize) {
		Assert.assertNotNull(users);
		Assert.assertEquals(listSize, users.size());
		for(User user: users) {
			testAuditInfo(user.getAuditInfo());
		}
	}
	
	protected void testPurge(List<User> users, int listSize) {
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE-1, users.size());
	}
	
	/**
	 * Method used to test the user details
	 * 
	 * @param user
	 * @param testUserEmail 
	 */
	protected void testUser(User user, String testUserEmail) {
		Assert.assertEquals("Test", user.getFirstName());
		Assert.assertEquals("User", user.getLastName());
		Assert.assertEquals(testUserEmail, user.getEmail());
		testUserAddress(user);
		testAuditInfo(user.getAuditInfo());
	}
	
	/**
	 * Tests the User Address
	 * 
	 * @param user
	 */
	protected void testUserAddress(User user) {
		Assert.assertNotNull(user.getAddress());
		Assert.assertEquals("Test Address", user.getAddress().getAddressLine1());
		Assert.assertNull(user.getAddress().getAddressLine2());
		Assert.assertNull(user.getAddress().getAddressLine3());
		Assert.assertEquals("SE1", user.getAddress().getPostcode());
		Assert.assertEquals("London", user.getAddress().getCity());
		Assert.assertEquals("London", user.getAddress().getCounty());
		Assert.assertEquals("UK", user.getAddress().getCountry());
	}

	
	/**
	 * Test the AuditInfo data from each BaseObject
	 * 
	 * @param auditInfo
	 */
	protected void testAuditInfo(AuditInfo<Long> auditInfo) {
		Assert.assertNotNull(auditInfo);
		Assert.assertEquals(-1l, auditInfo.getCreatorUserId());
		Assert.assertEquals(-1l, auditInfo.getModifierUserId());
	}
	
	/**
	 * Creates a new User
	 * @return User
	 */
	protected User createUser() {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		User user = new User();
		try {
			user.setTitle("Mr");
			user.setFirstName("Test");
			user.setLastName("User");
			Date date = formatter.parse("24/01/2013");
			user.setDateOfBirth(date);
			user.setEmail(TEST_USER_EMAIL_2);
			user.setPassword(USER_TEST_PASSWORD);
			user.setMobile("12123123123123");
			user.setProfile("Hello World");
			Address address1 = new Address();
			address1.setAddressLine1("Test Address");
			address1.setCity("London");
			address1.setCounty("London");
			address1.setCountry("UK");
			address1.setPostcode("SE1");
			user.setAddress(address1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		return user;
	}
}
