package com.wishop.mvc.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.wishop.model.BaseObject;
import com.wishop.model.Role;
import com.wishop.mvc.WebConstants;
import com.wishop.service.BaseService;
import com.wishop.service.RoleService;
import com.wishop.utils.WishopMessages;

/**
 * <code>Validator</code> for <code>Role</code> forms.
 *
 * @author Paulo Monteiro
 */
@Component
public class RoleValidator extends BaseValidator<Role, Long> implements WebConstants{

	@Autowired
	public RoleValidator(BaseService<?, BaseObject<?, Long>> roleService) {
		super(roleService);
	}
	
	/**
	 * Method to validate the Role object. <br>
	 * @param role - Role
	 * @param errors - List of errors
	 */
	public void validate(Object target, Errors errors) {
		Role role = (Role) target;
		ValidationUtils.rejectIfEmpty(errors, PARAM_NAME, FIELD_REQUIRED, WishopMessages.getMessage(FORM_FIELD_REQUIRED));
		if( (role.getId() == null && !((RoleService)getBaseService()).isUniqueName(role.getName())) ||
			(role.getId() != null && !((RoleService)getBaseService()).isUniqueName(role))) {
				errors.rejectValue(PARAM_NAME, FIELD_REQUIRED, WishopMessages.getMessage(FORM_FIELD_INTEGRITY));
		}
	}
}
