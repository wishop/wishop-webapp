package com.wishop.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.wishop.model.AuditLogRecord;
import com.wishop.model.User;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/springApp/daoContext-app.xml",
		"classpath:/META-INF/applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class UserServiceTest extends BaseServiceTestImpl {

	/**
	 * To be incremented as the tests are performed
	 */
	private static int AUDIT_LOG_RECORD_TEST_INDEX = 0;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuditLogRecordService<Long> auditLogRecordService;
	
	@Test
	public void testGetById() {
		User user = userService.getById(3);
		testUser(user, TEST_USER_EMAIL_1);
	}

	@Test
	public void testGetByEmail() {
		User user = userService.getByEmail(TEST_USER_EMAIL_1);
		testUser(user, TEST_USER_EMAIL_1);
	}

	@Test
	public void testGetByLastName() {
		List<User> users = userService.getByLastName("User");
		Assert.assertNotNull(users);
		Assert.assertEquals(1, users.size());
		testUser(users.get(0), TEST_USER_EMAIL_1);
	}

	@Test
	public void testIsUniqueEmail() {
		User user = new User();
		user.setEmail("test.user@mailinator.com");
		Assert.assertFalse(userService.isUniqueEmail(user));
		user.setEmail(TEST_USER_EMAIL_2);
		Assert.assertTrue(userService.isUniqueEmail(user));
	}

	@Test
	public void testSetAccountActive() {
		User user = userService.getById(3l);
		Assert.assertTrue(user.isAccountActive());
		userService.setAccountActive(user, false);
		Assert.assertFalse(user.isAccountActive());
	}

	@Test
	public void testGetUserByIdAndPassword() {
		User user = userService.getById(3l);
		User user2 = userService.getUserByIdAndPassword(user, "password");
		Assert.assertTrue(user2.equals(user));
	}

	@Test
	public void testGetAll() {
		List<User> users = userService.getAll();
		super.testGetAll(users, USERS_LIST_SIZE);
	}

	@Test
	public void testGetAllExceptOne() {
		List<User> users = userService.getAll(3l);
		super.testGetAll(users, USERS_LIST_SIZE-1);
	}

	@Test
	public void testGetAllByPagination() {
		List<User> users = userService.getAll(1,3);
		super.testGetAll(users, USERS_LIST_SIZE);
	}

	@Test
	public void testSave() {
		AuditLogRecord<Long> auditLogRecordOld = auditLogRecordService.getLatest();
		User newUser =  createUser();
		userService.save(newUser);
		List<User> users = userService.getAll();
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE+1, users.size());
		testUser(userService.getByEmail(TEST_USER_EMAIL_2), TEST_USER_EMAIL_2);
		testAuditLogRecord(auditLogRecordOld, newUser);
	}

	/**
	 * The <code>testSaveOrUpdate()</code> reuses the DB data from the <code>testSave()</code> method
	 */
	@Test
	public void testSaveOrUpdate() {
		AuditLogRecord<Long> auditLogRecordOld = auditLogRecordService.getLatest();
		List<User> users = userService.getAll();
		User newUser =  createUser();
		userService.saveOrUpdate(newUser);
		users = userService.getAll();
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE+1, users.size());
		testUser(userService.getByEmail(TEST_USER_EMAIL_2), TEST_USER_EMAIL_2);
		testAuditLogRecord(auditLogRecordOld, newUser);
	}

	@Test
	public void testUpdate() {
		AuditLogRecord<Long> auditLogRecordOld = auditLogRecordService.getLatest();
		User user = userService.getByEmail("test.user@mailinator.com");
		user.setEmail("test.user3@mailinator.com");
		user.getAddress().setAddressLine3("Joy Street");
		userService.update(user);
		testAuditLogRecord(auditLogRecordOld, user);
		user =  userService.getByEmail("test.user3@mailinator.com");
		Assert.assertNotNull(user);
		Assert.assertEquals("test.user3@mailinator.com", user.getEmail());
		Assert.assertEquals("Joy Street", user.getAddress().getAddressLine3());
		userService.purge(user);
		testAuditLogRecord(auditLogRecordOld, user);
	}

	@Test
	public void testPurge() {
		List<User> users = userService.getAll();
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE, users.size());
		User user = users.get(0);
		userService.purge(user);
		super.testPurge(userService.getAll(), USERS_LIST_SIZE-1);
	}
	
	/**
	 *  Test the AuditLogRecord data, based on each User CRUD action
	 * 
	 * @param auditLogRecordOld
	 * @param newUser The latest changed user
	 */
	private void testAuditLogRecord(AuditLogRecord<Long> auditLogRecordOld,	User newUser) {
		AuditLogRecord<Long> auditLogRecord = auditLogRecordService.getLatest();
		Assert.assertNotNull(auditLogRecord);
		Assert.assertEquals(auditLogRecordOld.getId().longValue()+ ++AUDIT_LOG_RECORD_TEST_INDEX,auditLogRecord.getId().longValue());
		Assert.assertEquals(USER_ENTITY_CLASS_NAME,auditLogRecord.getEntityClass().getName());
		Assert.assertEquals(newUser.getId().longValue(), auditLogRecord.getEntityId().longValue());
		Assert.assertEquals(-1, auditLogRecord.getUserId().longValue());
	}

}
