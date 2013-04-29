package com.wishop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wishop.dao.RoleDAO;
import com.wishop.model.Role;

/**
 * <b>Role</b> Service class
 * @author pmonteiro
 *
 */
@Service
@Transactional
public interface RoleService extends BaseService<RoleDAO, Role> {
	
	/**
	 * Checks to see it the role name is unique
	 * @param name Role name
	 * @return boolean - true/false
	 */
	public boolean isUniqueName(String name);
	
	/**
	 * Checks to see it the role name is unique
	 * @param role Role object
	 * @return boolean - true/false
	 */
	public boolean isUniqueName(Role role);

	/**
	 * Return a Role based on a name
	 * @param name Role name
	 * @return Role
	 */
	public Role getByName(String name);
}
