package com.wishop.service;

/**
 * AuditLog aspect constants
 * @author pmonteiro
 *
 */
public interface AuditLogConstants {

	public static final String AUDIT_ACTION_DELETE = "AUDIT_ACTION_DELETE";
	public static final String AUDIT_ACTION_PURGE = "AUDIT_ACTION_PURGE";
	public static final String AUDIT_ACTION_SAVE = "AUDIT_ACTION_SAVE";
	public static final String AUDIT_ACTION_SAVE_OR_UPDATE = "AUDIT_ACTION_SAVE_OR_UPDATE";
	public static final String AUDIT_ACTION_UPDATE = "AUDIT_ACTION_UPDATE";
	public static final String AUDIT_ACTION_UNDELETE = "AUDIT_ACTION_UNDELETE";
	public static final String AUDIT_ERROR_WHILE_SAVING = "AUDIT_ERROR_WHILE_SAVING";
	public static final String AUDIT_ERROR_WHILE_SAVING_OR_UPDATING = "AUDIT_ERROR_WHILE_SAVING_OR_UPDATING";
	public static final String AUDIT_ERROR_WHILE_UPDATING = "AUDIT_ERROR_WHILE_UPDATING";
	public static final String AUDIT_NO_OBJECT_TO_SAVE = "AUDIT_NO_OBJECT_TO_SAVE";
	public static final String AUDIT_NO_OBJECT_TO_SAVE_OR_UPDATE = "AUDIT_NO_OBJECT_TO_SAVE_OR_UPDATE";
	public static final String AUDIT_NO_OBJECT_TO_UPDATE = "AUDIT_NO_OBJECT_TO_UPDATE";
}
