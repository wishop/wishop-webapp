package com.wishop.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.wishop.dao.exceptions.HibernateSessionException;

@Repository
public interface BaseDAO<T, ID extends Serializable> {

	final static String ID = "id";
	final static String DELETED = "deleted";
	
	/**
	 * Returns the current Hibernate Session
	 * @return 
	 */
	public Session getSession();
	    
	/**
	 * Returns a new Hibernate Session
	 * @return
	 */
	public Session getNewSession();
	
	/**
	 * An object with a Hibernate session
	 * @param entity
	 */
	public void reassociate(Object entity);
	
	/**
     * Loads the Serializable Object by id
     * 
     * @param id the id of the Serializable Object
     * @throws HibernateSessionException session couldn't load the object.
     * @return Serializable Object
     */
	public T getById(ID id);
	
	/**
	 * Get Object by id. <br>
	 * @param id - unique Object id
	 * @return Object
	 */
	public T getById(Integer id);
	
	/**
	 * Get Object by id. <br>
	 * Will check if the <b>id</b> String is not empty and if is numeric.
	 * @param id - unique Object id
	 * @return Object
	 */
	public T getById(String id);
	
	/**
     * Get all objects where property <b>deleted</b> is <code>false</code>
     * The method’s return value will be cached.
     * 
     * @return list of objects
     */
	public List<T> getAll();
	
	/**
     * Get all objects based on the property <b>deleted</b>.
     * @param deleted <code>true</code> or <code>false</code>
     * The method’s return value will be cached.
     * @return list of objects
     */
	public List<T> getAll(boolean deleted);
	
	/**
     * Get all objects except the object with the parameter <b>id</b>.
     * @param id - Long object Id 
     * The method’s return value will be cached.
     * @return list of objects
     */
	public List<T> getAll(Long id);
	
	/**
	 * Get all objects where <code>deleted</code> is <b>false</b>, between the firstResult and N maxResults.
	 * Useful for pagination.
	 * @param firstResult
	 * @param maxResults
	 * @param deleted <code>true</code> or <code>false</code>
	 * @return List of concrete BaseObjects
	 * @throws HibernateSessionException
	 */
	public List<T> getAll(int firstResult, int maxResults);
	
	/**
	 * Get all objects based on the property deleted between the firstResult and N maxResults.
	 * Useful for pagination.
	 * @param firstResult
	 * @param maxResults
	 * @param deleted <code>true</code> or <code>false</code>
	 * @return List of concrete BaseObjects
	 * @throws HibernateSessionException
	 */
	public List<T> getAll(int firstResult, int maxResults, boolean deleted);
	
	/**
	 * @param entity - Object 
	 * @return saved or updated object
     */
	public T saveOrUpdate(T entity);
	
	/**
	 * @param entity - Object 
     * @return saved object
     */
	public T save(T entity);
	
	/**
	 * @throws org.hibernate.NonUniqueObjectException: a different object with the same identifier value was 
	 * already associated with the session. In this case a merge() is performed.
	 * @param entity - Object 
     * @return updated object
     */
	public T update(T entity);
	
	/**
	 * @param entity - Object 
     * @return refreshed object
     */
	public T refresh(T entity);
	
	/**
     * Remove/Erase an object from the database.
     * @param entity - Object
     */
	public void purge(T entity);
	
	/**
     * Tags the object on the database as deleted
     * @param entity - Object
     * @param deleted -the desired deleted state of the <b>Object</b> in the database
     */
	public void delete(T entity, boolean deleted);
	
	/**
	 * Session Factory is injected by the Spring Framework
	 * @param factory SessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory);
	
	/**
     * Returns the SessionFactory that was injected by the Spring Framework  
     */
	public SessionFactory getSessionFactory();
	
	
	/**
	 * Logs all the actions related to an object, with the default Locale.
	 * @param code
	 * @param entity
	 */
	public void logAction(String code, T entity);	
	
	/**
	 * Logs all the actions related to an object, with the default Locale.
	 * @param code
	 * @param entity
	 */
	public void logAction(String code, ID id);
	
	/**
	 * Logs all the actions related to an object, depending on the Locale
	 * @param code
	 * @param entity
	 * @param locale
	 */
	public void logAction(String code, T entity, Locale locale);
	
	/**
	 * Logs all the actions related to an object, depending on the Locale
	 * @param code
	 * @param entity
	 * @param locale
	 */
	public void logAction(String code, ID id, Locale locale);
}
