package com.wishop.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.wishop.dto.Users;
import com.wishop.utils.HibernateUtil;

public class HibernateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Users users = new Users();
		users.setId(1);
		users.setUsername("FirstUser");
		
		try { 
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(users);
			session.getTransaction().commit();
		} catch(Exception e) { 
			//TODO rollback transaction
		}

	}
}
