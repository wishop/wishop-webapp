package com.wishop.service.aspects;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.wishop.model.AuditInfo;
import com.wishop.model.AuditLogRecord;
import com.wishop.model.Auditable;
import com.wishop.model.BaseObject;
import com.wishop.model.User;
import com.wishop.model.exceptions.WishopException;
import com.wishop.service.AuditLogConstants;
import com.wishop.service.AuditLogRecordService;
import com.wishop.utils.WishopMessages;
import com.wishop.utils.WishopSecurityContext;

/**
 * Uses @AspectJ aspects (AOP) to create and save the AuditInfo of every BaseObject 
 * and to store all DAO actions to the AuditLogRecord table. 
 * 
 * @author Paulo Monteiro
 *
 */

@Aspect
public class AuditLog implements AuditLogConstants {
	
	/**
	 * SYSTEM_USER is used to indicate that the Wishop system, 
	 * rather than a real human/logical user, has performed/requested an action against the DB.
	 */
	private static final int SYSTEM_USER = -1;
	private static final String MODEL_PACKAGE = "com.wishop.model";
	private static final Logger auditLogger = Logger.getLogger(AuditLog.class);
	
	private AuditLogRecordService<Long> logRecordService;
	
	@Autowired
	public AuditLog(AuditLogRecordService<Long> logRecordService) {
		this.logRecordService = logRecordService;
	}
	
	/**
	 * Method that uses @AspectJ around advice annotation.
	 * Here the @Around annotation indicates that the log() method is to be applied as around advice to the 
	 * com.wishop.service.BaseService.save() pointcut (where to apply aspects).
	 * Note that com.wishop.service.BaseService.save() is a method inherited by all the Wishop services. 
	 * This way we ensure that every object model  will get the correct AuditInfo. 
	 * <b>ProceedingJoinPoint</b> is an object that carries the target proxied bean, the method signature, method arguments, and 
	 * needs to explicitly call proceed(), doing a call to the proxied bean method.
	 * This is the most transparent way of using AOP. 
	 * 
	 * The action also gets logged in the AuditLogRecord table.
	 * 
	 * @param jp ProceedingJoinPoint
	 */
	@Around("execution(* com.wishop.service.BaseService.save(..))")
	public void logSave(ProceedingJoinPoint jp) {
		BaseObject<Auditable<Long>, Long> baseObject = null;
		AuditLogRecord<Long> auditLogRecord = new AuditLogRecord<Long>();
		Date now = Calendar.getInstance().getTime();
		
		User user = WishopSecurityContext.getSessionUser();
		
		try {
			List<Object> list = Arrays.asList(jp.getArgs());	
			if (list == null || list.size() <= 0) {
				throw new HibernateException(WishopMessages.getMessage(AUDIT_NO_OBJECT_TO_SAVE, (String) jp.getSignature().toShortString()));
			}
			
			//saves the BaseObject auditInfo
			baseObject = updateAuditInfo(jp, list, baseObject, user, now);
			
			//proceed with the proxied method
			jp.proceed();
			
			//add action to the AuditLogRecord table and to the log
			logAction(baseObject, auditLogRecord, now, user, AUDIT_ACTION_SAVE);
		} catch (Throwable e) { 
			auditLogger.error(WishopMessages.getMessage(AUDIT_ERROR_WHILE_SAVING, baseObject));
			throw new HibernateException(WishopMessages.getMessage(AUDIT_ERROR_WHILE_SAVING, baseObject.getClass().getName()), e);
		} 
	}

	/**
	 * Method that uses @AspectJ around advice annotation.
	 * Here the @Around annotation indicates that the log() method is to be applied as around advice to the 
	 * com.wishop.service.BaseService.update() pointcut (where to apply aspects).
	 * Note that com.wishop.service.BaseService.update() is a method inherited by all the Wishop services. 
	 * This way we ensure that every object model  will get the correct AuditInfo. 
	 * <b>ProceedingJoinPoint</b> is an object that carries the target proxied bean, the method signature, method arguments, and 
	 * needs to explicitly call proceed(), doing a call to the proxied bean method.
	 * 
	 * The action also gets logged in the AuditLogRecord table.
	 * 
	 * This is the most transparent way of using AOP. 
	 * 
	 * @param jp ProceedingJoinPoint
	 */
	@Around("execution(* com.wishop.service.BaseService.update(..))")
	public void logUpdate(ProceedingJoinPoint jp) {
		BaseObject<Auditable<Long>, Long> baseObject = null;
		AuditLogRecord<Long> auditLogRecord = new AuditLogRecord<Long>();
		Date now = Calendar.getInstance().getTime();
		User user = WishopSecurityContext.getSessionUser();
		
		try {
			List<Object> list = Arrays.asList(jp.getArgs());	
			if (list == null || list.size() <= 0) { 
				throw new HibernateException(WishopMessages.getMessage(AUDIT_NO_OBJECT_TO_UPDATE, (String) jp.getSignature().toShortString()));
			}
			
			//saves the BaseObject auditInfo
			baseObject = updateAuditInfo(jp, list, baseObject, user, now);
			
			//proceed with the proxied method
			jp.proceed();
			
			//add action to the AuditLogRecord table and o the log file
			logAction(baseObject, auditLogRecord, now, user, AUDIT_ACTION_UPDATE);
			
		} catch (Throwable e) { 
			auditLogger.error(WishopMessages.getMessage(AUDIT_ERROR_WHILE_UPDATING, baseObject));
			throw new HibernateException(WishopMessages.getMessage(AUDIT_ERROR_WHILE_UPDATING, baseObject), e);
		}
	}
	
	/**
	 * Method that uses @AspectJ around advice annotation.
	 * Here the @Around annotation indicates that the log() method is to be applied as around advice to the 
	 * com.wishop.service.BaseService.saveOrUpdate() pointcut (where to apply aspects).
	 * Note that com.wishop.service.BaseService.saveOrUpdate() is a method inherited by all the Wishop services. 
	 * This way we ensure that every object model  will get the correct AuditInfo. 
	 * <b>ProceedingJoinPoint</b> is an object that carries the target proxied bean, the method signature, method arguments, and 
	 * needs to explicitly call proceed(), doing a call to the proxied bean method.
	 * 
	 * The action also gets logged in the AuditLogRecord table.
	 * 
	 * This is the most transparent way of using AOP. 
	 * 
	 * @param jp ProceedingJoinPoint
	 */
	@Around("execution(* com.wishop.service.BaseService.saveOrUpdate(..))")
	public void logSaveOrUpdate(ProceedingJoinPoint jp) {
		BaseObject<Auditable<Long>, Long> baseObject = null;
		AuditLogRecord<Long> auditLogRecord = new AuditLogRecord<Long>();
		Date now = Calendar.getInstance().getTime();
		User user = WishopSecurityContext.getSessionUser();
		
		try {
			List<Object> list = Arrays.asList(jp.getArgs());	
			if (list == null || list.size() <= 0) { 
				throw new HibernateException(WishopMessages.getMessage(AUDIT_NO_OBJECT_TO_SAVE_OR_UPDATE, (String) jp.getSignature().toShortString()));
			}
			
			//saves the BaseObject auditInfo
			baseObject = updateAuditInfo(jp, list, baseObject, user, now);
			
			//proceed with the proxied method
			jp.proceed();
			
			//add action to the AuditLogRecord table and log
			logAction(baseObject, auditLogRecord, now, user, AUDIT_ACTION_SAVE_OR_UPDATE);
			
		} catch (Throwable e) {
			auditLogger.error(WishopMessages.getMessage(AUDIT_ERROR_WHILE_SAVING_OR_UPDATING, baseObject));
			throw new HibernateException(WishopMessages.getMessage(AUDIT_ERROR_WHILE_SAVING_OR_UPDATING, baseObject), e);
		}
	}
	
	
	
	/**
	 * Method that uses @AspectJ around advice annotation.
	 * Here the @Around annotation indicates that the log() method is to be applied as around advice to the 
	 * com.wishop.service.BaseService.purge(..) pointcut (where to apply aspects).
	 * Note that com.wishop.service.BaseService.purge() is a method inherited by all the Wishop services. 
	 * Here we are not interested in saving the BaseObject AuditInfo, only in storing the action to the AuditLogRecord table. <br>
	 * <b>ProceedingJoinPoint</b> is an object that carries the target proxied bean, the method signature, method arguments, and 
	 * needs to explicitly call proceed(), doing a call to the proxied bean method.
	 * This is the most transparent way of using AOP. 
	 * 
	 * The action also gets logged in the AuditLogRecord table.
	 * 
	 * @param jp ProceedingJoinPoint
	 * @throws WishopException 
	 */
	@SuppressWarnings("unchecked")
	@Around("execution(* com.wishop.service.BaseService.purge(..))")
	public void logPurge(ProceedingJoinPoint jp) throws WishopException {
		BaseObject<Auditable<Long>, Long> baseObject = null;
		AuditLogRecord<Long> auditLogRecord = new AuditLogRecord<Long>();
		Date now = Calendar.getInstance().getTime();
		User user = WishopSecurityContext.getSessionUser();
		
		try {
			List<Object> list = Arrays.asList(jp.getArgs());	
			if (list == null || list.size() <= 0) {
				throw new HibernateException(WishopMessages.getMessage(AUDIT_NO_OBJECT_TO_SAVE, (String) jp.getSignature().toShortString()));
			}
			// get BaseObject
			baseObject =  (BaseObject<Auditable<Long>, Long>) list.get(0);
			
			//proceed with the proxied method
			jp.proceed();
			
			//add action to the AuditLogRecord table
			logAction(baseObject, auditLogRecord, now, user, AUDIT_ACTION_PURGE);
		} catch (HibernateException he) {
			throw new WishopException(he.getMessage());
		} catch (Throwable e) { 
			auditLogger.error(WishopMessages.getMessage(AUDIT_ERROR_WHILE_SAVING, baseObject));
			throw new HibernateException(WishopMessages.getMessage(AUDIT_ERROR_WHILE_SAVING, baseObject.getClass().getName()), e);
		} 
	}


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
	@SuppressWarnings("unchecked")
	private BaseObject<Auditable<Long>, Long> updateAuditInfo(ProceedingJoinPoint jp, List<Object> list, 
			BaseObject<Auditable<Long>, Long> baseObject, User user, Date now) throws IllegalArgumentException, 
			IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		AuditInfo<Long> auditInfo = null;
		
		baseObject =  (BaseObject<Auditable<Long>, Long>) list.get(0);
		
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
	private void auditInnerObjects(BaseObject<Auditable<Long>, Long> baseObject, User user, Date now)
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
	 * Saves the BaseObject auditInfo when a delete action is requested. <br>
	 * Only the <b>ModificationTimestamp</b> and the <b>ModifierUser</b> are updated. <br>
	 * Adds the Creation Timestamp, Modification Timestamp, Creator ID and Modifier ID
	 * @param jp 
	 * @param list
	 * @param baseObject
	 * @param user 
	 * @param now 
	 * @return BaseObject
	 */
	@SuppressWarnings("unchecked")
	private BaseObject<Auditable<Long>, Long> updateAuditInfoOnDelete(ProceedingJoinPoint jp, List<Object> list, 
			BaseObject<Auditable<Long>, Long> baseObject, User user, Date now) {
		AuditInfo<Long> auditInfo = null;
		
		baseObject =  (BaseObject<Auditable<Long>, Long>) list.get(0);
		
		auditInfo = baseObject.getAuditInfo();
		auditInfo.setModificationTimestamp(now);
		
		//modifier user id
		if (user != null) {
		    auditInfo.setModifierUserId((Long) user.getId());
		} else {
		    auditInfo.setModifierUserId(new Long(SYSTEM_USER));
		}
		
		return baseObject;
	}
	
	/**
	 * Add action to the the AuditLogRecord table and to the log
	 * @param baseObject
	 * @param auditLogRecord
	 * @param now
	 * @param user
	 * @param action - constant to get the message from a <b>.properties</b> file
	 */
	private void logAction(BaseObject<Auditable<Long>, Long> baseObject,
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
	private void saveAuditLogRecord(BaseObject<Auditable<Long>, Long> baseObject, 
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
