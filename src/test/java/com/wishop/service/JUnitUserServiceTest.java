package com.wishop.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.wishop.model.Address;
import com.wishop.model.AuditInfo;
import com.wishop.model.User;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/springApp/daoContext-app.xml",
		"classpath:/META-INF/applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class JUnitUserServiceTest implements UserServiceTest {

	private static final String TEST_USER_EMAIL_1 = "test.user@mailinator.com";
	private static final String TEST_USER_EMAIL_2 = "test.user2@mailinator.com";

	private static final int USERS_LIST_SIZE = 3;

	@Autowired
	private UserService userService;
	
	@Autowired
	private Md5PasswordEncoder passwordEncoder;

	@Test
	@Override
	public void testGetById() {
		User user = userService.getById(3);
		testUser(user, TEST_USER_EMAIL_1);
	}

	@Test
	@Override
	public void testGetByEmail() {
		User user = userService.getByEmail(TEST_USER_EMAIL_1);
		testUser(user, TEST_USER_EMAIL_1);
	}

	@Test
	@Override
	public void testGetByLastName() {
		List<User> users = userService.getByLastName("User");
		Assert.assertNotNull(users);
		Assert.assertEquals(1, users.size());
		testUser(users.get(0), TEST_USER_EMAIL_1);
	}

	@Test
	@Override
	public void testIsUniqueEmail() {
		User user = new User();
		user.setEmail("test.user@mailinator.com");
		Assert.assertFalse(userService.isUniqueEmail(user));
		user.setEmail(TEST_USER_EMAIL_2);
		Assert.assertTrue(userService.isUniqueEmail(user));
	}

	@Test
	@Override
	public void testSetAccountActive() {
		User user = userService.getById(USERS_LIST_SIZE);
		Assert.assertTrue(user.isAccountActive());
		userService.setAccountActive(user, false);
		Assert.assertFalse(user.isAccountActive());
	}

	@Test
	@Override
	public void testGetUserByIdAndPassword() {
		User user = userService.getById(USERS_LIST_SIZE);
		User user2 = userService.getUserByIdAndPassword(user, "password");
		Assert.assertTrue(user2.equals(user));
	}

	/**
	 * Method used to test the user details
	 * 
	 * @param user
	 * @param testUserEmail 
	 */
	private void testUser(User user, String testUserEmail) {
		Assert.assertEquals("Test", user.getFirstName());
		Assert.assertEquals("User", user.getLastName());
		Assert.assertEquals(testUserEmail, user.getEmail());
		testUserAddress(user);
		testAuditInfo(user.getAuditInfo());
	}

	@Test
	@Override
	public void testGetAll() {
		List<User> users = userService.getAll();
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE, users.size());
		for(User user: users) {
			testAuditInfo(user.getAuditInfo());
		}
	}

	@Test
	@Override
	public void testGetAllExceptOne() {
		List<User> users = userService.getAll(3l);
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE-1, users.size());
		for(User user: users) {
			testAuditInfo(user.getAuditInfo());
		}
	}

	@Test
	@Override
	public void testGetAllByDeleteBasis() {
		boolean deletedUsers = false;
		List<User> users = userService.getAll(deletedUsers);
		User user = users.get(0);
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE, users.size());
		user.setDeleted(true);
		userService.update(user);
		Assert.assertEquals(1,userService.getAll(true).size());
		//TODO: is the delete flag all that important? it is already causing issues with the cache. as in this example
		Assert.assertEquals(USERS_LIST_SIZE,userService.getAll(deletedUsers).size());
	}

	@Test
	@Override
	public void testGetAllByPagination() {
		List<User> users = userService.getAll(1,3);
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE, users.size());
		for(User user: users) {
			testAuditInfo(user.getAuditInfo());
		}
	}

	@Test
	@Override
	public void testGetAllByPaginationAndByDeleteBasis() {
		
	}

	@Test
	@Override
	public void testSave() {
		User newUser =  createUser();
		userService.save(newUser);
		List<User> users = userService.getAll();
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE+1, users.size());
		testUser(userService.getByEmail(TEST_USER_EMAIL_2), TEST_USER_EMAIL_2);
	}

	/**
	 * The <code>testSaveOrUpdate()</code> reuses the DB data from the <code>testSave()</code> method
	 */
	@Test
	@Override
	public void testSaveOrUpdate() {
		List<User> users = userService.getAll();
		User newUser =  createUser();
		userService.saveOrUpdate(newUser);
		users = userService.getAll();
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE+1, users.size());
		testUser(userService.getByEmail(TEST_USER_EMAIL_2), TEST_USER_EMAIL_2);
	}

	@Test
	@Override
	public void testUpdate() {
		User user = userService.getByEmail("test.user@mailinator.com");
		user.setEmail("test.user3@mailinator.com");
		user.getAddress().setAddressLine3("Joy Street");
		userService.update(user);
		user =  userService.getByEmail("test.user3@mailinator.com");
		Assert.assertNotNull(user);
		Assert.assertEquals("test.user3@mailinator.com", user.getEmail());
		Assert.assertEquals("Joy Street", user.getAddress().getAddressLine3());
		userService.purge(user);
	}

	@Test
	@Override
	public void testDelete() {
		List<User> users = userService.getAll();
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE, users.size());
		User user = users.get(0);
		userService.delete(user, true);
		users = userService.getAll();
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE-1, users.size());
		userService.delete(user, false);
		users = userService.getAll();
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE, users.size());
	}

	@Test
	@Override
	public void testPurge() {
		List<User> users = userService.getAll();
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE, users.size());
		User user = users.get(0);
		userService.purge(user);
		users = userService.getAll();
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE-1, users.size());
	}
	
	/**
	 * Tests the User Address
	 * 
	 * @param user
	 */
	private void testUserAddress(User user) {
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
	private void testAuditInfo(AuditInfo<Long> auditInfo) {
		Assert.assertNotNull(auditInfo);
		Assert.assertEquals(-1l, auditInfo.getCreatorUserId());
		Assert.assertEquals(-1l, auditInfo.getModifierUserId());
	}
	
	/**
	 * Creates a new User
	 * @return User
	 */
	private User createUser() {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		User user = new User();
		try {

			user.setTitle("Mr");
			user.setFirstName("Test");
			user.setLastName("User");
			Date date = formatter.parse("24/01/2013");
			user.setDateOfBirth(date);
			user.setEmail(TEST_USER_EMAIL_2);
			user.setPassword(passwordEncoder.encodePassword("password", user.getEmail()));
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
