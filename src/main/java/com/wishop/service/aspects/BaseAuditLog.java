package com.wishop.service.aspects;


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
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

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
 * and to store all DAO actions to the AuditLogRecord table. <br> 
 * 
 * By using <b><code>@EnableAspectJAutoProxy</code></b> if a bean is advised by one or more aspects, 
 * it will automatically generate a proxy for that bean to intercept method invocations and ensure 
 * that advice is executed as needed.
 * 
 * @author Paulo Monteiro
 *
 */

@Aspect
@Component
@EnableAspectJAutoProxy
public class BaseAuditLog extends AuditLog implements AuditLogConstants {
	
	/**
	 * SYSTEM_USER is used to indicate that the Wishop system, 
	 * rather than a real human/logical user, has performed/requested an action against the DB.
	 */
	protected static final int SYSTEM_USER = -1;
	protected static final String MODEL_PACKAGE = "com.wishop.model";
	protected static final Logger auditLogger = Logger.getLogger(BaseAuditLog.class);
	
	@Autowired
	protected AuditLogRecordService<Long> logRecordService;
	
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
	@SuppressWarnings("unchecked")
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
			baseObject = updateAuditInfo(jp, (BaseObject<Auditable<Long>, Long>) list.get(0), user, now);
			
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
	@SuppressWarnings("unchecked")
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
			baseObject = updateAuditInfo(jp, (BaseObject<Auditable<Long>, Long>) list.get(0), user, now);
			
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
	@SuppressWarnings("unchecked")
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
			baseObject = updateAuditInfo(jp, (BaseObject<Auditable<Long>, Long>) list.get(0), user, now);
			
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
}
