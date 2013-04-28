package com.wishop.mvc.controllers;

import java.io.FileNotFoundException;
import java.io.Serializable;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.wishop.model.exceptions.WishopException;
import com.wishop.mvc.validators.BaseValidator;
import com.wishop.service.BaseService;

public interface BaseController<T, ID extends Serializable>  {

	/**
	 * Registers custom editors to bind String values to Classes
	 * @param dataBinder - used to register custom editors to bind String values to Classes 
	 * @param request
	 */
	public void initBinder(WebDataBinder dataBinder, WebRequest request);
	
	/**
	 * Custom handler for displaying Wishop lists of BaseObjects.
	 * @return a ModelMap with the model attributes for the view
	 */
	 public ModelMap list(ModelMap model);
	
	/**
	 * Custom handler for displaying a Wishop BaseObject.
	 *
	 * @param objectId the ID of the view to display
	 * @return a ModelAndView with the model attributes for the view
	 */
	public ModelAndView show(@PathVariable("objectId") int objectId);	
	
	/**
	 * Completely removes the object from the database. 
	 * @param entity BaseObject
	 * @param result
	 * @param status
	 * @param request
	 * @return Webpage to redirect the User to 
	 * @throws WishopException
	 */
    public String purge(@ModelAttribute T entity, BindingResult result, SessionStatus status, WebRequest request) throws WishopException;
	
	/**
	 * Method responsible for setting up the <b>new</b> BaseObject form. 
	 * @param model
	 * @return view name for the model
	 */
	public String create(Model model) ;
	
	/**
	 * Method responsible for setting up the <b>edit</b> BaseObject form. 
	 * @param model
	 * @return view name for the model
	 */
	public String edit(@PathVariable("objectId") int objectId, Model model);
	
	/**
	 * Method that validates the form for an existing <b>BaseObject</b> object, previously submitted by the user
	 * @param view - the BaseObject object
	 * @param result - BindingResult
	 * @param status - SessionStatus
	 * @return view to display the object
	 * @throws WishopException 
	 * @throws FileNotFoundException 
	 */
	public String save(@ModelAttribute T entity, BindingResult result, SessionStatus status) throws WishopException;
	
	
    public Class<T> getEntityClass();
	
	/**
	 * Returns the BaseObject Validator for the concrete Controller
	 * 
	 * @return the baseObjectValidator
	 */
	public BaseValidator<?, Long> getBaseObjectValidator();

	/**
	 * 
	 * @return the baseService
	 */
	@SuppressWarnings("rawtypes")
	public BaseService getBaseService();

	/**
	 * @return the entityShortName
	 */
	public String getEntityShortName();
}
