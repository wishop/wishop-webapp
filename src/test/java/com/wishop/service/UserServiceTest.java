package com.wishop.service;

/**
 * Contract for the jUnit UserServiceTest
 * 
 * @author pmonteiro
 * 
 */
public interface UserServiceTest extends BaseServiceTest {

	public void testGetByEmail();

	public void testGetByLastName();

	public void testIsUniqueEmail();

	public void testSetAccountActive();

	public void testGetUserByIdAndPassword();

}
