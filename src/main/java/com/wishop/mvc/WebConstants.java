package com.wishop.mvc;

/**
 * Class that holds all the Wishop Web constants, including the Spring beans id's.
 * Also handles the Wishop Form error constants.
 *  
 * @author Paulo Monteiro
 *
 */
public interface WebConstants {
	public static final String PARAM_CMS_USER = "user";
	public static final String PARAM_CMS_USERS = "users";
	
	//Wishop FORM CONSTANTS
	public static final String PARAM_ERRORS = "errors";
	public static final String PARAM_NAME = "name";
	public static final String FIELD_REQUIRED = "required";
	public static final String FIELD_INVALID = "invalid";
	public static final String FORM_INVALID_EMAIL = "FORM_INVALID_EMAIL";
	public static final String FORM_EMAIL_ALREADY_EXISTS = "FORM_EMAIL_ALREADY_EXISTS";
	public static final String FORM_INVALID_PASSWORD = "FORM_INVALID_PASSWORD";
	public static final String FORM_FIELD_REQUIRED = "FORM_FIELD_REQUIRED";
	public static final String FORM_FIELD_INTEGRITY = "FORM_FIELD_INTEGRITY";
	public static final String FORM_FIELD_MUST_BE_NUMERIC = "FORM_FIELD_MUST_BE_NUMERIC";
	public static final String FORM_FILE_EXTENSION_NOT_VALID = "FORM_FILE_EXTENSION_NOT_VALID";
	public static final String FORM_PASSWORD_IS_INCORRECT = "FORM_PASSWORD_IS_INCORRECT";
	public static final String FORM_OLD_PASSWORD_IS_INCORRECT = "FORM_OLD_PASSWORD_IS_INCORRECT";
	public static final String FORM_ORIGINAL_FILE_NAME_ALREADY_EXISTS = "FORM_ORIGINAL_FILE_NAME_ALREADY_EXISTS";
	public static final String FORM_EMAIL_IS_INCORRECT = "FORM_EMAIL_IS_INCORRECT";
	public static final String FORM_FOLDER_URI_ALREADY_EXISTS = "FORM_FOLDER_URI_ALREADY_EXISTS";
	public static final String WARNING_PURGING_VIEW_FOREIGN_KEY = "WARNING_PURGING_VIEW_FOREIGN_KEY";
	public static final String WARNING_PURGING_WEBPAGE_FOREIGN_KEY = "WARNING_PURGING_WEBPAGE_FOREIGN_KEY";
	
}
