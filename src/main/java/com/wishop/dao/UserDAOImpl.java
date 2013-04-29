package com.wishop.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wishop.model.User;

/**
 * User Data Access Object 
 * @author Paulo Monteiro
 *
 */
@Repository
@Transactional
@Component("userDAO")
public class UserDAOImpl extends BaseDAOImpl<User, Long> implements UserDAO {
	
	@Autowired
	public UserDAOImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
	 * Get user by id. <br>
	 * Will check if the <b>id</b> String is not empty and if is numeric.
	 * @param id - unique user id
	 * @return User
	 */
	public User getById(String id) {
		if(!StringUtils.isEmpty(id) && StringUtils.isNumeric(id)) {
			return getById(Long.parseLong(id));
		}
		return null;
	}

	/**
	 * Get user by id. <br>
	 * @param id - unique user id
	 * @return User
	 */
	public User getById(Integer id) {
		return getById(new Long(id.intValue()));
	}
	
	public User getByEmail(String email) {
		String hql = "from User o where o.email = :email";
		Query query = getSession().createQuery(hql);
		query.setString("email", email);
		return (User) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<User> getByLastName(String lastName) {
		String hql = "from User o where o.lastName = :lastname";
		Query query = getSession().createQuery(hql);
		query.setString("lastname", lastName);
		return query.list();
	}

	public User getUserByIdAndPassword(Long id, String encryptedPassword) {
		String hql = "FROM User o WHERE o.id = :id AND o.password = :password";
		Query query = getSession().createQuery(hql);
		query.setLong("id", id);
		query.setString("password", encryptedPassword);
		return (User) query.uniqueResult();
	}
	//TODO - Update the audit-info with this call.
	public void setAccountActive(User user, boolean isAccountActive) {
		user.setAccountActive(isAccountActive);
		super.update(user);
	}

	@SuppressWarnings("unchecked")
	public boolean isUniqueEmail(User user) {
		String hql = "from User o where o.email = :email";
		if(user.getId() != null) {
			hql += " and o.id != :id";
		}
		Query query = getSession().createQuery(hql);
		query.setParameter("email", user.getEmail());
		if(user.getId() != null) {
			query.setParameter("id", user.getId());
		}
		List<User> userList = query.list();
		if(userList != null && userList.size() > 0) {
			return false;
		}
		return true;
	}
}
