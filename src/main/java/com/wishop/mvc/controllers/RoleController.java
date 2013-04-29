package com.wishop.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wishop.model.BaseObject;
import com.wishop.model.Role;
import com.wishop.mvc.validators.BaseValidator;
import com.wishop.service.BaseService;
/**
 * JavaBean Role controller that is used to respond to <code>Role</code> requests.
 * @author Paulo Monteiro
 *
 */
@Controller
@RequestMapping("/workbench/roles")
@SessionAttributes("role")
public class RoleController extends BaseControllerImpl<Role, Long> {

	
	/**
	 * Injects the <b>IRoleService RoleService</b> through Spring DI
	 * @param RoleService - IRoleService
	 */
	@Autowired
	public RoleController(BaseService<?, BaseObject<?, Long>> roleService, BaseValidator<Role,Long> roleValidator) {
		super(roleService, roleValidator);
	}
	
}