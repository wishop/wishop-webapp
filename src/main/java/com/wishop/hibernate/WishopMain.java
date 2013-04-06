package com.wishop.hibernate;


public class WishopMain {
	public static void main(String[] args) {
		SimpleCommand simpleCommand = new SimpleCommand();
		simpleCommand.run();
		//Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		//System.out.println("MD5 Hash (1): " + encoder.encodePassword("qwerty123", new String("pmonteiro@salmon.com")));
		//System.out.println("MD5 Hash (26): " + encoder.encodePassword("123123", new String("paulo.from.portugal@gmail.com")));
	}
}
