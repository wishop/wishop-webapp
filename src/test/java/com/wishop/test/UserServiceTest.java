package com.wishop.test;


/**
 * Contract for the jUnit UserServiceTest 
 * @author pmonteiro
 *
 */
public interface UserServiceTest {

	public void testGetById();
    public void testGetByEmail();
    public void testGetByLastName();
    public void testIsUniqueEmail();
    public void testSetAccountActive();
    public void testGetUserByIdAndPassword();

    /* TODO: These tests should be tested on the BaseServiceTest and reused in all tests 
    public void testGetAll();
    public void testSave();
    public void testUpdate();
    public void testDelete();
    public void testPurge();
    */
    
}
