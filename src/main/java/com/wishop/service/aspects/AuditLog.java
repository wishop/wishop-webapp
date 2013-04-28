package com.wishop.service.aspects;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.wishop.model.AuditInfo;
import com.wishop.model.AuditLogRecord;
import com.wishop.model.Auditable;
import com.wishop.model.BaseObject;
import com.wishop.model.User;
import com.wishop.service.AuditLogConstants;
import com.wishop.service.AuditLogRecordService;
import com.wishop.utils.WishopMessages;

public class AuditLog implements AuditLogConstants {
	
	/**
	 * SYSTEM_USER is used to indicate that the Wishop system, 
	 * rather than a real human/logical user, has performed/requested an action against the DB.
	 */
	protected static final int SYSTEM_USER = -1;
	protected static final String MODEL_PACKAGE = "com.wishop.model";
	protected static final Logger auditLogger = Logger.getLogger(AuditLog.class);
	
	@Autowired
	protected AuditLogRecordService<Long> logRecordService;

	/**
	 * Saves the BaseObject auditInfo when a save or an update action is requested. <br>
	 * Adds the Creation Timestamp, Modification Timestamp, Creator ID and Modifier ID
	 * @param jp 
	 * @param list
	 * @param baseObject
	 * @param user 
	 * @param now 
	 * @return BaseObject
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	protected BaseObject<Auditable<Long>, Long> updateAuditInfo(ProceedingJoinPoint jp, 
			BaseObject<Auditable<Long>, Long> baseObject, User user, Date now) throws IllegalArgumentException, 
			IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		AuditInfo<Long> auditInfo = null;
		
		auditInfo = baseObject.getAuditInfo();
		
		//set the creation and modification Timestamp
		if (auditInfo.getCreationTimestamp() == null) {
			auditInfo.setCreationTimestamp(now);
		}
		auditInfo.setModificationTimestamp(now);
		
		//set the creator and modifier user id
		if (user != null)
		{
		    if (auditInfo.getCreatorUserId() == null) {
		    	auditInfo.setCreatorUserId((Long) user.getId());
		    }
		    auditInfo.setModifierUserId((Long) user.getId());
		} else {
			if (auditInfo.getCreatorUserId() == null) {
		    	auditInfo.setCreatorUserId(new Long(SYSTEM_USER));
			}
		    auditInfo.setModifierUserId(new Long(SYSTEM_USER));
		}
		auditInnerObjects(baseObject, user, now);
		return baseObject;
	}
	
	/**
	 * Users Java Reflection to inspect the returning types of the BaseObject methods and create the AuditInfo object. <br>
	 * <b>Example:</b> A BaseObject might be part of another BaseObjects which need to have the AuditInfo created as well. <br>
	 * @param baseObject
	 * @param user - authenticated User
	 * @param now - Date
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	protected void auditInnerObjects(BaseObject<Auditable<Long>, Long> baseObject, User user, Date now)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Method[] methods = baseObject.getClass().getMethods();
		for (Method method : methods) {
			if (method.getReturnType().getPackage() != null && MODEL_PACKAGE.equals(method.getReturnType().getPackage().getName())) {
				Method privateStringMethod = baseObject.getClass().getMethod(method.getName(), null);
				privateStringMethod.setAccessible(true);
				Object returnedObject = (Object)privateStringMethod.invoke(baseObject, null);
				if(returnedObject != null && returnedObject instanceof BaseObject) {
					BaseObject<Auditable<Long>, Long> returnedBaseObject = (BaseObject<Auditable<Long>, Long>) returnedObject;
					if(returnedBaseObject.isNew()) {
						returnedBaseObject.getAuditInfo().setCreationTimestamp(now);
						returnedBaseObject.getAuditInfo().setCreatorUserId(user.getId());
						returnedBaseObject.getAuditInfo().setModificationTimestamp(now);
						returnedBaseObject.getAuditInfo().setModifierUserId(user.getId());
						auditInnerObjects(returnedBaseObject, user, now);
					}
				}
			}
		}
	}
	
	/**
	 * Add action to the the AuditLogRecord table and to the log
	 * @param baseObject
	 * @param auditLogRecord
	 * @param now
	 * @param user
	 * @param action - constant to get the message from a <b>.properties</b> file
	 */
	protected void logAction(BaseObject<Auditable<Long>, Long> baseObject,
		AuditLogRecord<Long> auditLogRecord, Date now, User user, String action) {
		String message = WishopMessages.getMessage(action, baseObject, user);
		auditLogger.info(message);
		saveAuditLogRecord(baseObject, auditLogRecord, now, user, message);
	}

	/**
	 * Logs the action performed against the Wishop database, by calling the AuditLogRecordService.
	 * @param baseObject
	 * @param auditLogRecord
	 * @param now
	 * @param user
	 * @param message
	 */
	protected void saveAuditLogRecord(BaseObject<Auditable<Long>, Long> baseObject, 
			AuditLogRecord<Long> auditLogRecord, Date now, User user, String message) {
		auditLogRecord.setCreated(now);
		auditLogRecord.setEntityId(baseObject.getId());
		auditLogRecord.setEntityClass(baseObject.getClass());
		if (user != null) {
			auditLogRecord.setUserId(user.getId());
		} else {
			auditLogRecord.setUserId(new Long(SYSTEM_USER));
		}
		auditLogRecord.setMessage(message);
		logRecordService.save(auditLogRecord);
	}

}
