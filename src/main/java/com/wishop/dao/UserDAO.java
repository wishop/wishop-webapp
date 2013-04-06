package com.wishop.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wishop.model.User;

@Repository
public interface UserDAO extends BaseDAO<User, Long>{
	
	/**
	 * Get user by email
	 * @param email user's email
	 * @return User
	 */
	public User getByEmail(String email);
	
	/**
	 * Get user by last name
	 * @param lastName user's last name
	 * @return User
	 */
	public List<User> getByLastName(String lastName);
	
	/**
	 * Retrieves User from DB by userId and password
	 * @param id - User id
	 * @param encryptedPassword - salted & encrypted password
	 * @return User
	 */
	public User getUserByIdAndPassword(Long id, String encryptedPassword);

	/**
	 * Disables a User account
	 * @param user - User
	 */
	public void setAccountActive(User user, boolean isAccountActive);
	
	/**
	 * Checks to see if the email is unique in the system.
	 * @param user User
	 * @return boolean true/false
	 */
	public boolean isUniqueEmail(User user);
}
