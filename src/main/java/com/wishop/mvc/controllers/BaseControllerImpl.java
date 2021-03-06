package com.wishop.mvc.controllers;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.wishop.model.BaseObject;
import com.wishop.model.exceptions.WishopException;
import com.wishop.mvc.WebConstants;
import com.wishop.mvc.editors.WishopFormBindingInitializer;
import com.wishop.mvc.validators.BaseValidator;
import com.wishop.service.BaseService;
/**
 * JavaBean BaseObject controller that is used to respond to <code>BaseObject</code> requests.
 * @author Paulo Monteiro
 *
 */
public abstract class BaseControllerImpl<T, ID extends Serializable> 
	extends WishopFormBindingInitializer implements BaseController<T, ID>, WebConstants {

	protected static final String LIST = "/list";
	protected static final String SHOW = "/show/";
	protected static final String REDIRECT = "redirect:/";
	protected static final String FORM = "/form";
	protected static final String WORKBENCH = "workbench/";
	protected static final String ATTR_ERRORS = "errors";
	
	private final BaseService<?, BaseObject<?, Long>> baseService;
	private final BaseValidator<?, Long> baseObjectValidator;
	
	private String entityShortName;
	
	/**
	 * Injects the <b>BaseService</b> through Spring DI
	 * @param BaseService - baseService
	 */
	@Autowired
	public BaseControllerImpl(BaseService<?, BaseObject<?, Long>> baseService, BaseValidator<?, Long> baseObjectValidator) {
		this.baseService = baseService;
		this.baseObjectValidator = baseObjectValidator;
		this.entityShortName = ClassUtils.getShortName(getEntityClass()).toLowerCase();
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder, WebRequest request) {
		dataBinder.setDisallowedFields("id");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	 public ModelMap list(ModelMap model) {
		model.addAttribute(baseService.getAll());
		return model;
	}
	
	@RequestMapping("/show/{objectId}")
	public ModelAndView show(@PathVariable("objectId") int objectId) {
		ModelAndView mav = new ModelAndView(WORKBENCH + entityShortName + "/show");
		mav.addObject(entityShortName, baseService.getById(objectId));
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/purge", method = RequestMethod.POST)
    public String purge(@ModelAttribute T entity, BindingResult result, SessionStatus status, WebRequest request) throws WishopException {
    	baseService.purge((BaseObject<?, Long>) entity);
        return REDIRECT + WORKBENCH + entityShortName + LIST;
    }
	
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute(entityShortName, getEntityInstance());
		return WORKBENCH + entityShortName + FORM;
	}
	
	@RequestMapping(value="/edit/{objectId}", method = RequestMethod.GET)
	public String edit(@PathVariable("objectId") int objectId, Model model) {
		model.addAttribute(entityShortName, baseService.getById(objectId));
		return WORKBENCH + entityShortName + FORM;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String save(@ModelAttribute T entity, BindingResult result, SessionStatus status) throws WishopException {
		BaseObject<?, Long> baseObject = (BaseObject<?, Long>) entity;
		baseObjectValidator.validate(baseObject, result);
		if (result.hasErrors()) {
			return WORKBENCH + entityShortName + FORM;
		} else {
			if(baseObject.getId() == null) {
				baseService.save(baseObject);
			} else {
				baseService.update(baseObject);
			}
			status.setComplete();
			return REDIRECT + WORKBENCH + entityShortName + SHOW + baseObject.getId();
		}
	}
	
	@SuppressWarnings("unchecked")
    public Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
	
	@SuppressWarnings("unchecked")
    protected T getEntityInstance()
    {
        try {
            return (T) Class.forName(getEntityClass().getName()).newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

	public BaseValidator<?, Long> getBaseObjectValidator() {
		return baseObjectValidator;
	}

	@SuppressWarnings({ "rawtypes" })
	public BaseService getBaseService() {
		return baseService;
	}

	public String getEntityShortName() {
		return entityShortName;
	}
}