package com.wishop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wishop.dao.BaseDAO;
import com.wishop.model.exceptions.WishopException;

@Service
public interface BaseService<X extends BaseDAO<T, Long>, T> {

	/**
	 * BaseObjectService service that gets a BaseObject by Id.
	 * Uses @Cacheable to declares that the method’s return value should be cached
	 * @param id Serializable
	 * @return T BaseObject
	 */
	public T getById(Long id);
	
	/**
	 * BaseObjectService service that gets a BaseObject by Id.
	 * Uses @Cacheable to declares that the method’s return value should be cached
	 * @param id Integer
	 * @return T BaseObject
	 */
	public T getById(Integer id);
	
	/**
	 * BaseObjectService service that gets a BaseObject by ID
	 * Uses @Cacheable to declares that the method’s return value should be cached
	 * @param id String
	 * @return T BaseObject
	 */
	public T getById(String id);
	
	/**
	 * BaseObjectService service that gets all BaseObject.<br>
	 * Uses @Cacheable to declares that the method’s return value should be cached
	 * @param id Integer
	 */
	public List<T> getAll();
	
	/**
	 * BaseObjectService service that gets all BaseObject by the <b><code>deleted</code></b> flag.<br>
	 * Uses @Cacheable to declares that the method’s return value should be cached
	 * @param deleted boolean
	 */
	public List<T> getAll(boolean deleted);
	
	/**
	 * BaseObjectService service that gets all BaseObject, except the <b>objectId</b>.<br>
	 * Uses @Cacheable to declares that the method’s return value should be cached
	 * @param id Long
	 */
	public List<T> getAll(Long id);
	
	/**
	 * BaseObjectService service that saves or updates a BaseObject. <br>
	 * Makes use of @CacheFlush to specify a flush action when the method is called.
	 * @param entity BaseObject
	 */
	public void saveOrUpdate(T entity);
	
	/**
	 * BaseObjectService service that saves or updates a BaseObject.<br>
	 * Makes use of @CacheFlush to specify a flush action when the method is called.
	 * @param entity BaseObject
	 * @throws ScribeException 
	 */
	public void save(T entity) throws WishopException;
	
	/**
	 * BaseObjectService service that update a BaseObject.<br>
	 * Makes use of @CacheFlush to specify a flush action when the method is called. 
	 * @param entity BaseObject
	 * @throws ScribeException 
	 */
	public void update(T entity) throws WishopException;
	
	/**
	 * BaseObjectService service that update a BaseObject.<br>
	 * Makes use of @CacheFlush to specify a flush action when the method is called.
	 * @param entity BaseObject
	 * @throws ScribeException 
	 */
	public void purge(T entity) throws WishopException;
	
	/**
	 * BaseObjectService service that update a BaseObject.<br>
	 * Makes use of @CacheFlush to specify a flush action when the method is called.
	 * @param entity - BaseObject
	 * @param deleted - the desired deleted state of the <b>BaseObject</b> in the database
	 * @return result - the number of affected rows in the database
	 */
	public void delete(T entity, boolean deleted);
	
	/**
	 * Set the BaseObjectDAO concrete class. Injected by the Spring Framework
	 * @param dao subclass of BaseObjectDAO
	 */
	public void setDao(X dao);
	
	/**
	 * Returns the BaseObjectDAO concrete class, that was injected by the Spring Framework
	 */
	public X getDao();
}