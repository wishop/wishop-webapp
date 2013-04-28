package com.wishop.service;


/**
 * Contract for the jUnit BaseServiceTest
 * 
 * @author pmonteiro
 * 
 */
public interface BaseServiceTest {

	/**
	 * Test the BaseService <code>getById(ID id)</code> method.<br>
	 */
	public void testGetById();

	/**
	 * Test the BaseService <code>getAll()</code> method.<br>
	 */
	public void testGetAll();

	/**
	 * Test the BaseService <code>getAll(Long id)</code> method.<br>
	 */
	public void testGetAllExceptOne();

	/**
	 * Test the BaseService <code>getAll(int firstResult, int maxResults)</code>
	 * method.<br>
	 */
	public void testGetAllByPagination();

	/**
	 * Test the BaseService <code>save(Entity entity)</code> method.<br>
	 */
	public void testSave();

	/**
	 * Test the BaseService <code>update(Entity entity)</code> method.<br>
	 */
	public void testUpdate();

	/**
	 * Test the BaseService <code>saveOrUpdate(Entity entity)</code> method.<br>
	 */
	public void testSaveOrUpdate();

	/**
	 * Test the BaseService <code>purge(Entity entity)</code> method.<br>
	 */
	public void testPurge();
}
