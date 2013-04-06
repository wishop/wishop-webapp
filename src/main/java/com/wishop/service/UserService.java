package com.wishop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wishop.dao.UserDAO;
import com.wishop.model.User;

/**
 * <b>User</b> Service Interface. 
 * @author pmonteiro
 *
 */

@Service
public interface UserService extends BaseService<UserDAO, User>
{
	/**
	 * Service that retrieves a User by email.
	 * Email is unique. <br/>
	 * Uses @Cacheable to declares that the method’s return value should be cached
	 * @param email - user's email
	 * @return User
	 */
	public User getByEmail(String email);
	
	/**
	 * Service that retrieves a <code>List</code> of <code>User</code> by their last name.
	 * @param lastName - user's last name
	 * @return List - list of users
	 */
	public List<User> getByLastName(String lastName);
	
	/**
	 * Retrieves User from DB by userId and password
	 * @param user - User
	 * @param rawPass - raw password
	 * @return User
	 */
	public User getUserByIdAndPassword(User User, String rawPass);
	

	/**
	 * Enable an User account
	 * @param user
	 */
	public void setAccountActive(User user, boolean isAccountActive);
	
	/**
	 * Checks to see if the email is unique in the system.
	 * @param user User
	 * @return boolean true/false
	 */
	public boolean isUniqueEmail(User user);
}
