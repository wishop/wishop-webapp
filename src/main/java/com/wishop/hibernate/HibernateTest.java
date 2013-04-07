package com.wishop.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.wishop.model.Address;
import com.wishop.model.Sku;
import com.wishop.model.User;
import com.wishop.model.Wishlist;
import com.wishop.utils.HibernateUtil;

@Deprecated
public class HibernateTest {

	public static void main(String[] args) {
		
		//create two users
		User user1 = new User();
		User user2 = new User();
		
		user1.setEmail("firstuser@hotmail.com");
		user1.setPassword("testing");
		
		user2.setEmail("adminuser@gmail.com");
		user2.setPassword("testing");
		
		//make an address for each user. The address is an embeddable object
		Address addr1 = new Address();
		addr1.setCountry("GB");
		addr1.setCity("London");
		addr1.setAddressLine1("10 Downing Street");
		addr1.setPostcode("w1");
		
		Address addr2 = new Address();
		addr2.setCountry("GB");
		addr2.setCity("London");
		addr2.setAddressLine1("11 Downing Street");
		addr2.setPostcode("w1");
		
		//create multiple wishlists for each user ( user can have many wishlists)
		Wishlist wishlist1 = new Wishlist();
		wishlist1.setDescription("FirstUser's birthday wishlist");
		
		Wishlist wishlist2 = new Wishlist();
		wishlist2.setDescription("SecondUser's birthday wishlist");
		
		Wishlist wishlist3 = new Wishlist();
		wishlist3.setDescription("FirstUser's anniversary wishlist");
		
		Wishlist wishlist4 = new Wishlist();
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
		User user = (User)session.get(User.class, 1);
		System.out.print(">>> Username retrieved: " + user.getEmail());
	}
}
