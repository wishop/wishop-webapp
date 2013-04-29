package com.wishop.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wishop.model.Role;

/**
 * Role Data Access Object 
 * @author pmonteiro
 *
 */
@Repository
@Transactional
@Component("roleDAO")
public class RoleDAOImpl extends BaseDAOImpl<Role, Long> implements RoleDAO {

	/**
	 * Get user by id. <br>
	 * Will check if the <b>id</b> String is not empty and if is numeric.
	 * @param id - unique user id
	 * @return Role
	 */
	public Role getById(String id) {
		if(!StringUtils.isEmpty(id) && StringUtils.isNumeric(id)) {
			return getById(Long.parseLong(id));
		}
		return null;
	}

	/**
	 * Get user by id. <br>
	 * @param id - unique user id
	 * @return Role
	 */
	public Role getById(Integer id) {
		return getById(new Long(id.intValue()));
	}
	
	public Role getByName(String name) {
		String hql = "from Role o where o.name = :name";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		return (Role) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public boolean isUniqueName(String name) {
		String hql = "from Role o where o.name = :name";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		List<Role> rolesList = query.list();
		if(rolesList != null && rolesList.size() > 0) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean isUniqueName(Role role) {
		String hql = "from Role o where o.name = :name and o.id != :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", role.getName());
		query.setParameter("id", role.getId());
		List<Role> rolesList = query.list();
		if(rolesList != null && rolesList.size() > 0) {
			return false;
		}
		return true;
	}

}
