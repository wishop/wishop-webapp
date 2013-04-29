package com.wishop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wishop.dao.RoleDAO;
import com.wishop.model.Role;

@Service
@Component("roleService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class RoleServiceImpl extends BaseServiceImpl<RoleDAO, Role> implements RoleService {

	@Autowired
	public RoleServiceImpl(RoleDAO dao) {
		super(dao);
	}

	public boolean isUniqueName(String name) {
		return this.getDao().isUniqueName(name);
	}

	public boolean isUniqueName(Role role) {
		return this.getDao().isUniqueName(role);
	}

	public Role getByName(String name) {
		return this.getDao().getByName(name);
	}

}
