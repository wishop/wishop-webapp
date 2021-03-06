package com.wishop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wishop.dao.UserDAO;
import com.wishop.model.Role;
import com.wishop.model.User;

@Service
@Component("userService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class UserServiceImpl extends BaseServiceImpl<UserDAO, User> implements UserService {
	
	@Autowired
	private Md5PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserDAO userDAO) {
		super(userDAO);
	}
	
	public User getByEmail(String email) {
		return this.getDao().getByEmail(email);
	}

	public List<User> getByLastName(String lastName) {
		return this.getDao().getByLastName(lastName);
	}

	public User getUserByIdAndPassword(User user, String rawPass) {
		return this.getDao().getUserByIdAndPassword(user.getId(), passwordEncoder.encodePassword(rawPass, user.getEmail()));
	}

	@CachePut("userCache")
	@Transactional(readOnly=false)
	public void setAccountActive(User user, boolean isAccountActive) {
		this.getDao().setAccountActive(user, isAccountActive);
	}

	public boolean isUniqueEmail(User user) {
		return this.getDao().isUniqueEmail(user);
	}
	
	@Cacheable("userCache")
	public List<User> getByRole(Role role) {
		return this.getDao().getByRole(role);
	}
}
