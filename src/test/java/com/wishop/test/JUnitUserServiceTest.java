package com.wishop.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.wishop.model.User;
import com.wishop.service.UserService;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/springApp/daoContext-app.xml", "classpath:/META-INF/applicationContext.xml"})
@TransactionConfiguration(defaultRollback=true)
public class JUnitUserServiceTest implements UserServiceTest {
	
    @Autowired
    private UserService userService;

    @Test
    @Override
    public void testGetById()
    {
        User user = userService.getById(3);
        testUser(user);
    }

    @Test
    @Override
    public void testGetByEmail()
    {
    	User user = userService.getByEmail("test.user@mailinator.com");
    	testUser(user);
    }

    @Test
	@Override
	public void testGetByLastName() {
    	List<User> users = userService.getByLastName("User");
    	Assert.assertNotNull(users);
    	Assert.assertEquals(1, users.size());
    	testUser(users.get(0));
	}

    @Test
	@Override
	public void testIsUniqueEmail() {
    	User user = new User();
    	user.setEmail("test.user@mailinator.com");
    	Assert.assertFalse(userService.isUniqueEmail(user));
    	user.setEmail("test.user2@mailinator.com");
    	Assert.assertTrue(userService.isUniqueEmail(user));
	}

    @Test
	@Override
	public void testSetAccountActive() {
    	User user = userService.getById(3);
    	Assert.assertTrue(user.isAccountActive());
    	userService.setAccountActive(user, false);
    	Assert.assertFalse(user.isAccountActive());
	}

    @Test
	@Override
	public void testGetUserByIdAndPassword() {
    	User user1 = userService.getById(3);
    	User user2 = userService.getUserByIdAndPassword(user1, "password");
    	Assert.assertTrue(user2.equals(user1));
	}
	
	/**
     * Method used to test the user details
     * @param user
     */
	private void testUser(User user) {
		Assert.assertEquals("Test", user.getFirstName());
        Assert.assertEquals("User", user.getLastName());
        Assert.assertEquals("test.user@mailinator.com", user.getEmail());
        testUserAddress(user);
        testAuditInfo(user);
	}

	/**
	 * Tests the User Address
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
	 * @param user
	 */
	private void testAuditInfo(User user) {
		Assert.assertNotNull(user.getAuditInfo());
        Assert.assertEquals(-1l, user.getAuditInfo().getCreatorUserId());
        Assert.assertEquals(-1l, user.getAuditInfo().getModifierUserId());
	}
}
