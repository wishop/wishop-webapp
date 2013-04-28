package com.wishop.mvc.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import com.wishop.model.Address;
import com.wishop.model.AuditInfo;
import com.wishop.model.User;
import com.wishop.mvc.WebConstants;
import com.wishop.service.UserService;
import com.wishop.utils.WishopMessages;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"classpath:/META-INF/springApp/daoContext-app.xml",
		"classpath:/META-INF/applicationContext.xml",
		"classpath:test-wishop-servlet.xml"})
@TransactionConfiguration(defaultRollback = true)
public class UserControllerTest extends BaseControllerTestImpl implements WebConstants {

	private static final String ATTR_PASSWORD_CONFIRMATION = "passwordConfirmation";
	private static final String FORWARDED_URL = "/WEB-INF/tiles/workbench/layout.jsp";
	private static final String USER_REQUEST_MAPPING = "/workbench/user";

	private static final String ATTR_USER = "user";
	private static final String ATTR_USER_DOB = "dateOfBirth";
	private static final String ATTR_USER_LIST = "userList";
	private static final String ATTR_USER_EMAIL = "email";
	private static final String ATTR_EMAIL_CONFIRMATION = "emailConfirmation";
	private static final String ATTR_CURRENT_PASSWORD = "currentPassword";
	
	private static final String BINDING_ERRORS_KEY = "org.springframework.validation.BindingResult.user";
	
	private static final String REQUEST_CHANGE_PASSWORD = "/changePassword/#";
	private static final String REQUEST_ENABLE_ACCOUNT = USER_REQUEST_MAPPING + "/enableAccount";
	private static final String REQUEST_DISABLE_ACCOUNT = USER_REQUEST_MAPPING + "/disableAccount";
	private static final String REQUEST_UPDATE_PASSWORD = USER_REQUEST_MAPPING + "/updatePassword";
	private static final String VIEW_NAME_CHANGE_PASSWORD = "/password_form";
	
	private static final String USER_TEST_PASSWORD = "Password123";
	private static final String TEST_USER_EMAIL_1 = "test.user@mailinator.com";
	private static final String TEST_USER_EMAIL_2 = "test.user2@mailinator.com";
	
	private static final long USER_ID_4 = 4;
	private static final long USER_ID_3 = 3;
	private static final long USERS_LIST_SIZE = 3;
	
	@Autowired
	private Md5PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;

	/**
	 * Calls the BaseControllerTest setUp method to set up all the configurations needed for the jUnit test
	 */
	@Before 
	public void setUp() {
		super.setUp();
		setRequestMapping(USER_REQUEST_MAPPING);
		setForwardedUrl(FORWARDED_URL);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testList() throws Exception {
		MvcResult result = list();
		Map<String, Object> model = result.getModelAndView().getModel();
		Assert.assertNotNull(model);
		List<User> users = (List<User>) model.get(ATTR_USER_LIST);
		Assert.assertNotNull(users);
		Assert.assertEquals(USERS_LIST_SIZE, users.size());
		for(User user: users) {
			testAuditInfo(user.getAuditInfo());
		}
	}

	@Test
	public void testShow() throws Exception {
		MvcResult result = show(USER_ID_3);
		Map<String, Object> model = result.getModelAndView().getModel();
		Assert.assertNotNull(model);
		User user = (User) model.get(ATTR_USER);
		testUser(user, TEST_USER_EMAIL_1);
	}

	@Test
	public void testCreate() throws Exception {
		MvcResult result = create();
		Map<String, Object> model = result.getModelAndView().getModel();
		Assert.assertNotNull(model);
		User user = (User) model.get(ATTR_USER);
		Assert.assertNotNull(user);
		Assert.assertNull(user.getId());
		Assert.assertNull(user.getEmail());
		Assert.assertNull(user.getAddress());
	}

	@Test
	public void testEdit() throws Exception {
		MvcResult result = edit(USER_ID_3);
		Map<String, Object> model = result.getModelAndView().getModel();
		Assert.assertNotNull(model);
		User user = (User) model.get(ATTR_USER);
		testUser(user, TEST_USER_EMAIL_1);
	}

	@Test
	public void testPurge() throws Exception {
		purge(ATTR_USER, userService.getById(3l));
		Assert.assertNotNull(userService.getAll());
		Assert.assertEquals(USERS_LIST_SIZE-1, userService.getAll().size());
	}

	@Test
	public void testSave() throws Exception {
		User user = createUser();
		user.setEmailConfirmation(user.getEmail());
		//user.setPasswordConfirmation(passwordEncoder.encodePassword(USER_TEST_PASSWORD, user.getEmail()));
		user.setPasswordConfirmation(USER_TEST_PASSWORD);
		MvcResult result = super.save(ATTR_USER, user, USER_ID_4);
		Map<String, Object> model = result.getModelAndView().getModel();
		Assert.assertNotNull(model);
		user = (User) model.get(ATTR_USER);
		testUser(user, TEST_USER_EMAIL_2);
	}

	@Test
	public void testSaveWithErrors() throws Exception {
		User user = createUser();
		MvcResult mvcResult = super.saveWithErrors(ATTR_USER, user);
		Map<String, Object> model = mvcResult.getModelAndView().getModel();
		Assert.assertNotNull(model);
		user = (User) model.get(ATTR_USER);
		Assert.assertNull(user.getId());
		
		//Assert the form errors
		BeanPropertyBindingResult bindingResult = (BeanPropertyBindingResult) model.get(BINDING_ERRORS_KEY);
		Assert.assertFalse(bindingResult.getAllErrors().isEmpty());
		assertFormFieldError(bindingResult.getFieldError(ATTR_USER_EMAIL), WishopMessages.getMessage(FORM_EMAIL_ALREADY_EXISTS));
		assertFormFieldError(bindingResult.getFieldError(ATTR_EMAIL_CONFIRMATION), WishopMessages.getMessage(FORM_FIELD_REQUIRED));
		assertFormFieldError(bindingResult.getFieldError(ATTR_PASSWORD_CONFIRMATION), WishopMessages.getMessage(FORM_FIELD_REQUIRED));
		
		
		//test incorrect email confirmation && incorrect password confirmation
		User user2 = createUser();
		user2.setEmailConfirmation(TEST_USER_EMAIL_1);
		user2.setPasswordConfirmation(USER_TEST_PASSWORD+"4");
		mvcResult = super.saveWithErrors(ATTR_USER, user2);
		bindingResult = (BeanPropertyBindingResult) mvcResult.getModelAndView().getModel().get(BINDING_ERRORS_KEY);
		Assert.assertFalse(bindingResult.getAllErrors().isEmpty());
		assertFormFieldError(bindingResult.getFieldError(ATTR_USER_EMAIL), WishopMessages.getMessage(FORM_EMAIL_ALREADY_EXISTS)); 
		assertFormFieldError(bindingResult.getFieldError(ATTR_EMAIL_CONFIRMATION), WishopMessages.getMessage(FORM_EMAIL_IS_INCORRECT));
		assertFormFieldError(bindingResult.getFieldError(ATTR_PASSWORD_CONFIRMATION), WishopMessages.getMessage(FORM_PASSWORD_IS_INCORRECT));
	}

	/**
	 * Asserts the the form field message error
	 * @param fieldError 
	 * @param message - Wishop Message expected 
	 */
	private void assertFormFieldError(FieldError fieldError, String message) {
		Assert.assertNotNull(fieldError);
		Assert.assertEquals(message, fieldError.getDefaultMessage());
	}

	@Test
	public void testUpdate() throws Exception {
		User user = userService.getById(4l);
		user.setAccountActive(false);
		super.update(ATTR_USER, user, USER_ID_4);
		user = userService.getById(4l);
		Assert.assertFalse(user.isAccountActive());
		testUser(user, TEST_USER_EMAIL_2);
		Assert.assertNotSame(user.getAuditInfo().getCreationTimestamp(), user.getAuditInfo().getModificationTimestamp());
	}

	@Test
	public void testUpdateWithErrors() throws Exception {
		User user = userService.getById(4l);
		user.setDateOfBirth(null);
		MvcResult mvcResult = super.saveWithErrors(ATTR_USER, user);
		Map<String, Object> model = mvcResult.getModelAndView().getModel();
		Assert.assertNotNull(model);
		user = (User) model.get(ATTR_USER);
		Assert.assertNotNull(user.getId());
		
		//testing an incorrect Date Of Birth
		BeanPropertyBindingResult bindingResult = (BeanPropertyBindingResult) model.get(BINDING_ERRORS_KEY);
		Assert.assertFalse(bindingResult.getAllErrors().isEmpty());
		assertFormFieldError(bindingResult.getFieldError(ATTR_USER_DOB), WishopMessages.getMessage(FORM_FIELD_REQUIRED));
	}
	
	@Test
	public void testChangePassword() throws Exception {
		MvcResult result = perform(get(getChangePasswordRequestMapping()), FORWARDED_URL, _200);
		assertView(result, USER_REQUEST_MAPPING + VIEW_NAME_CHANGE_PASSWORD);
		User user = (User) result.getModelAndView().getModel().get(ATTR_USER);
		Assert.assertNotNull(user);
		Assert.assertEquals(4l, user.getId().longValue());
	}
	
	@Test
	public void testDisableAccount() throws Exception {
		MvcResult result = perform(post(REQUEST_DISABLE_ACCOUNT).sessionAttr(ATTR_USER, userService.getById(1l)), null, _302);
		assertView(result, USER_REQUEST_MAPPING + getShowResponseMapping(true, 1l));
		User user = (User) result.getModelAndView().getModel().get(ATTR_USER);
		Assert.assertNotNull(user);
		Assert.assertFalse(user.isAccountActive());
	}
	
	@Test
	public void testEnableAccount() throws Exception {
		MvcResult result = perform(post(REQUEST_ENABLE_ACCOUNT).sessionAttr(ATTR_USER, userService.getById(1l)), null, _302);
		assertView(result, USER_REQUEST_MAPPING + getShowResponseMapping(true, 1l));
		User user = (User) result.getModelAndView().getModel().get(ATTR_USER);
		Assert.assertNotNull(user);
		Assert.assertTrue(user.isAccountActive());
	}
	
	@Test
	public void testUpdatePassword() throws Exception {
		
		//Test a successfully update
		User user = userService.getById(4l);
		user.setCurrentPassword(USER_TEST_PASSWORD);
		user.setPassword(USER_TEST_PASSWORD+"4");
		user.setPasswordConfirmation(USER_TEST_PASSWORD+"4");
		MvcResult result = perform(post(REQUEST_UPDATE_PASSWORD).sessionAttr(ATTR_USER, user), null, _302);
		assertView(result, USER_REQUEST_MAPPING + getShowResponseMapping(true, 4l));
		user = (User) result.getModelAndView().getModel().get(ATTR_USER);
		Assert.assertNotNull(user);
		Assert.assertEquals(4l, user.getId().longValue());
		
		//Test an unsuccessful update - currentPassword missing
		User user2 = userService.getById(4l);
		user2.setCurrentPassword(null);
		user2.setPassword(USER_TEST_PASSWORD);
		user2.setPasswordConfirmation(USER_TEST_PASSWORD);
		result = perform(post(REQUEST_UPDATE_PASSWORD).sessionAttr(ATTR_USER, user2), FORWARDED_URL, _200);
		assertView(result, USER_REQUEST_MAPPING + VIEW_NAME_CHANGE_PASSWORD);
		user2 = (User) result.getModelAndView().getModel().get(ATTR_USER);
		Assert.assertNotNull(user2);
		Assert.assertEquals(4l, user2.getId().longValue());
		BeanPropertyBindingResult bindingResult = (BeanPropertyBindingResult) result.getModelAndView().getModel().get(BINDING_ERRORS_KEY);
		Assert.assertFalse(bindingResult.getAllErrors().isEmpty());
		assertFormFieldError(bindingResult.getFieldError(ATTR_CURRENT_PASSWORD), WishopMessages.getMessage(FORM_FIELD_REQUIRED));
		
		//Test an unsuccessful update - currentPassword is incorrect
		User user3 = userService.getById(4l);
		user.setCurrentPassword(USER_TEST_PASSWORD);
		user3.setPassword(USER_TEST_PASSWORD);
		user3.setPasswordConfirmation(USER_TEST_PASSWORD);
		result = perform(post(REQUEST_UPDATE_PASSWORD).sessionAttr(ATTR_USER, user3), FORWARDED_URL, _200);
		assertView(result, USER_REQUEST_MAPPING + VIEW_NAME_CHANGE_PASSWORD);
		user3 = (User) result.getModelAndView().getModel().get(ATTR_USER);
		Assert.assertNotNull(user3);
		Assert.assertEquals(4l, user3.getId().longValue());
		bindingResult = (BeanPropertyBindingResult) result.getModelAndView().getModel().get(BINDING_ERRORS_KEY);
		Assert.assertFalse(bindingResult.getAllErrors().isEmpty());
		assertFormFieldError(bindingResult.getFieldError(ATTR_CURRENT_PASSWORD), WishopMessages.getMessage(FORM_CURRENT_PASSWORD_IS_INCORRECT));
	}

	/**
	 * Returns the changePassword Request Mapping
	 * @param requestMapping
	 * @return String requestMapping
	 */
	private String getChangePasswordRequestMapping() {
		return USER_REQUEST_MAPPING + REQUEST_CHANGE_PASSWORD.replaceAll(REPLACE_VAR, String.valueOf(4));
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
