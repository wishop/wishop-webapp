package com.wishop.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.wishop.dao.exceptions.HibernateSessionException;

@Repository
public interface BaseDAO<T, ID extends Serializable> {

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
     * Get all objects where property <b>deleted</b> is <code>true</code>
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
}