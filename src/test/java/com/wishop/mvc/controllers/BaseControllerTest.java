package com.wishop.mvc.controllers;

/**
 * Base jUnit Controller test contract.
 * @author pmonteiro
 *
 */
public interface BaseControllerTest {
	
	/**
	 * Method that sets up all the configurations needed for the jUnit test. <br>
	 */
	public void setUp();
	
	/**
	 * Testing the custom handler for displaying lists of Wishop BaseObjects.
	 * @throws Exception
	 */
	 public void testList() throws Exception;
	
	/**
	 * Testing the custom handler for displaying a Wishop BaseObject.
	 * @throws Exception
	 */
	public void testShow() throws Exception;	
	
	/**
	 * Testing the method that completely removes the object from the database.
	 * @throws Exception 
	 */
    public void testPurge() throws Exception;
	
	/**
	 * Testing the method responsible for setting up the <b>new</b> BaseObject form.
	 * @throws Exception 
	 */
	public void testCreate() throws Exception;
	
	/**
	 * Testing the method responsible for setting up the <b>edit</b> BaseObject form.
	 * @throws Exception 
	 */
	public void testEdit() throws Exception;
	
	/**
	 * Tests the request method from a given BaseController implementation
	 * @throws Exception
	 */
	public void testSave() throws Exception;
	
	/**
	 * Tests the request method from a given BaseController implementation <br>
	 * This method version tests the BaseObject Controller validators, by checking the result errors
	 * @throws Exception
	 */
	public void testSaveWithErrors() throws Exception;
	
	/**
	 * Tests the request method from a given BaseController implementation
	 * @throws Exception
	 */
	public void testUpdate() throws Exception;
	
	/**
	 * Tests the request method from a given BaseController implementation <br>
	 * This method version tests the BaseObject Controller validators, by checking the result errors
	 * @throws Exception
	 */
	public void testUpdateWithErrors() throws Exception;
	
}
