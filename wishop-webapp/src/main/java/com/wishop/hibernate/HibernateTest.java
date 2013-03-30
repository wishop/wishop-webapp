package com.wishop.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.wishop.dto.UserDetails;
import com.wishop.utils.HibernateUtil;

public class HibernateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UserDetails user = new UserDetails();
		user.setUserId(1);
		user.setUsername("First User");
		
		try { 
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		} catch(Exception e) { 
			//TODO rollback transaction
		}

	}
}
