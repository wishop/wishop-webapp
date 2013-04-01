package com.wishop.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.wishop.dto.Address;
import com.wishop.dto.Sku;
import com.wishop.dto.UserDetails;
import com.wishop.dto.Wishlist;
import com.wishop.utils.HibernateUtil;

public class HibernateTest {

	public static void main(String[] args) {
		
		//create two users
		UserDetails user1 = new UserDetails();
		UserDetails user2 = new UserDetails();
		
		user1.setEmail("firstuser@hotmail.com");
		user1.setJoinedDate(new Date());
		
		user2.setEmail("adminuser@gmail.com");
		user2.setJoinedDate(new Date());
		
		//make an address for each user. The address is an embeddable object
		Address addr1 = new Address();
		addr1.setCountry("GB");
		addr1.setCity("London");
		addr1.setStreet("10 Downing Street");
		addr1.setPostcode("w1");
		
		Address addr2 = new Address();
		addr2.setCountry("GB");
		addr2.setCity("London");
		addr2.setStreet("11 Downing Street");
		addr2.setPostcode("w1");
		
		//create multiple wishlists for each user ( user can have many wishlists)
		Wishlist wishlist1 = new Wishlist();
		wishlist1.setDateCreated(new Date());
		wishlist1.setDescription("FirstUser's birthday wishlist");
		
		Wishlist wishlist2 = new Wishlist();
		wishlist2.setDateCreated(new Date());
		wishlist2.setDescription("SecondUser's birthday wishlist");
		
		Wishlist wishlist3 = new Wishlist();
		wishlist3.setDateCreated(new Date());
		wishlist3.setDescription("FirstUser's anniversary wishlist");
		
		Wishlist wishlist4 = new Wishlist();
		wishlist4.setDateCreated(new Date());
		wishlist4.setDescription("SecondUser's anniversary wishlist");
		
		//each wishlist can have many Skus
		Sku sku1 = new Sku();
		sku1.setDescription("Sku001");
		
		Sku sku2 = new Sku();
		sku2.setDescription("Sku002");
		
		//associate Skus with Wishlists
		sku1.getWishlists().add(wishlist1);
		sku1.getWishlists().add(wishlist2);
		sku2.getWishlists().add(wishlist1);
		sku2.getWishlists().add(wishlist2);
		
		//populate the objects
		user1.setAddress(addr1);
		user2.setAddress(addr2);
		
		user1.getListOfWishlists().add(wishlist1);
		user2.getListOfWishlists().add(wishlist2);
		
		try { 
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(user1);
			session.save(user2);
			session.save(sku1);
			session.save(sku2);
			session.save(wishlist1);
			session.save(wishlist2);
			session.save(wishlist3);
			session.save(wishlist4);
			session.getTransaction().commit();

			testObjectRetrieval(session);
			
			session.close();
		} catch(Exception e) { 
			//TODO rollback transaction
		}
	}
	
	//One way to get a User object if we know the primary key
	public static void testObjectRetrieval(Session session) { 
		session.beginTransaction();
		//retrieve user with id of 1
		UserDetails user = (UserDetails)session.get(UserDetails.class, 1);
		System.out.print(">>> Username retrieved: " + user.getEmail());
	}
}
