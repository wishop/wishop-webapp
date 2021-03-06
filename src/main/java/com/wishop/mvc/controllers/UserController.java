package com.wishop.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import com.wishop.model.BaseObject;
import com.wishop.model.Role;
import com.wishop.model.User;
import com.wishop.model.exceptions.WishopException;
import com.wishop.mvc.validators.BaseValidator;
import com.wishop.mvc.validators.UserValidator;
import com.wishop.service.BaseService;
import com.wishop.service.RoleService;
import com.wishop.service.UserService;
import com.wishop.utils.WishopSecurityContext;

/**
 * JavaBean User controller that is used to respond to <code>User</code> requests. <b>
 * @author Paulo Monteiro
 *
 */
@Controller 
@RequestMapping("/workbench/user")
@SessionAttributes(value = {"user", "roles"})
public class UserController extends BaseControllerImpl<User, Long>  {

	private static final String PASSWORD_FORM = "/password_form";
	private static final String ROLES_FORM = "/roles_form";

	public static final String FORM_STATUS_UPDATE = "UPDATE";
	public static final String FORM_STATUS_SUBMIT = "SUBMIT";
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private Md5PasswordEncoder passwordEncoder;
	
	/**
	 * UserController Constructor
	 * @param userService
	 * @param userValidator
	 */
	@Autowired
	public UserController(BaseService<?, BaseObject<?, Long>> userService, BaseValidator<User, Long> userValidator) {
		super(userService, userValidator);
	}

	/**
	 * Registers custom editors to bind String values to Classes
	 * @param dataBinder - used to register custom editors to bind String values to Classes 
	 * @param request
	 */
	@Override
	@InitBinder
	public void initBinder(WebDataBinder dataBinder, WebRequest request) {
		super.initBinder(dataBinder, request);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String save(@ModelAttribute User user, BindingResult result, SessionStatus status) throws WishopException {
		this.getBaseObjectValidator().validate(user, result);
		if (result.hasErrors()) {
			return WORKBENCH + getEntityShortName() + FORM;
		} else {
			if(user.isNew()) {
				user.setPassword(passwordEncoder.encodePassword(user.getPassword(), user.getEmail()));
				getBaseService().save(user);
			} else {
				getBaseService().update(user);
			}
			status.setComplete();
			return REDIRECT + WORKBENCH + getEntityShortName() + SHOW + user.getId();
		}
	}
	
	/**
	 * Method responsible for setting up the <b>new</b> User form. 
	 * @param model
	 * @return view name for the model
	 */
	@Override
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute(getEntityShortName(), getEntityInstance());
		return WORKBENCH + getEntityShortName() + FORM;
	}
	
	/**
	 * Method responsible for setting up the <b>edit</b> User form. 
	 * @param model
	 * @return view name for the model
	 */
	@Override
	@RequestMapping(value="/edit/{objectId}", method = RequestMethod.GET)
	public String edit(@PathVariable("objectId") int objectId, Model model) {
		model.addAttribute(getEntityShortName(), getBaseService().getById(objectId));
		return WORKBENCH + getEntityShortName() + FORM;
	}
	
	/**
	 * Enables an user account.<br>
	 * @param user - User
	 * @return View name, to redirect the user to
	 */
	@RequestMapping(value="/enableAccount", method=RequestMethod.POST)
    public String enableAccount(@ModelAttribute User user) {
		((UserService)getBaseService()).setAccountActive(user, true);
        return REDIRECT + WORKBENCH + getEntityShortName() + SHOW + user.getId();
    }
	
	/**
	 * Disable an user account.<br>
	 * @param user - User
	 * @return View name, to redirect the user to
	 */
	@RequestMapping(value="/disableAccount", method=RequestMethod.POST)
    public String disableAccount(@ModelAttribute User user) {
		((UserService)getBaseService()).setAccountActive(user, false);
		return REDIRECT + WORKBENCH + getEntityShortName() + SHOW + user.getId();
    }
	
	/**
	 * Method responsible for setting the form when changing password for a User. 
	 * @param model
	 * @param userId
	 * @return view name for the model
	 */
	@RequestMapping(value="/changePassword/{userId}", method = RequestMethod.GET)
	public String changePassword(@PathVariable("userId") int userId, Model model) {
		model.addAttribute(getEntityShortName(), getBaseService().getById(userId));
		return WORKBENCH + getEntityShortName() + PASSWORD_FORM;
	}
	
	/**
	 * Method that validates the change password form for an existing <b>User</b> object, previously submitted by the user
	 * @param user - the User object
	 * @param result - BindingResult
	 * @param status - SessionStatus
	 * @return view to display the object
	 * @throws WishopException 
	 */
	@RequestMapping(value="/updatePassword", method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute User user,	BindingResult result, SessionStatus status) throws WishopException {
		((UserValidator)getBaseObjectValidator()).setUserFormStatus(FORM_STATUS_SUBMIT);
		((UserValidator)getBaseObjectValidator()).validatePasswordChange(result, user, WishopSecurityContext.getSessionUser());
		if (result.hasErrors()) {
			return WORKBENCH + getEntityShortName() + PASSWORD_FORM;
		} else {
			((UserService)getBaseService()).update(user);
			status.setComplete();
			return REDIRECT + WORKBENCH + getEntityShortName() + SHOW + user.getId();
		}
	}
	
	/**
	 * Remove a user role. 
	 * @param user - User
	 * @return View name, to redirect the user to
	 * @throws WishopException 
	 */
	@RequestMapping(method=RequestMethod.POST)
    public String removeRole(User user, @RequestParam("roleId") int roleId) throws WishopException {
		Role role = roleService.getById(roleId);
		user.getRoles().remove(role);
		((UserService)getBaseService()).update(user);
        return  REDIRECT + WORKBENCH + getEntityShortName() + SHOW + user.getId();
    }
	
	/**
	 * Method responsible for adding a <b>role</b> to a User. 
	 * @param model
	 * @return view name for the model
	 */
	@RequestMapping(value="/{userId}/roles", method = RequestMethod.GET)
	public String addRoles(@PathVariable("userId") int userId, Model model) {
		User user = ((UserService)getBaseService()).getById(userId);
		model.addAttribute(getEntityShortName(), user);
		model.addAttribute(WEB_ROLES, roleService.getAll());
		return WORKBENCH + getEntityShortName() + ROLES_FORM;
	}
	
	/**
	 * Method that validates the form for a new object, previously submitted by the user
	 * @param user - the User object
	 * @param result - BindingResult
	 * @param status - SessionStatus
	 * @return view to display the object
	 * @throws WishopException 
	 */
	@RequestMapping(value="/{userId}/saveRoles", method = RequestMethod.POST)
	public String saveRoles(@ModelAttribute User user, BindingResult result, SessionStatus status) throws WishopException {
		((UserService)getBaseService()).update(user);
		status.setComplete();
		return REDIRECT + WORKBENCH + getEntityShortName() + SHOW + user.getId();
	}
}