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
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.wishop.model.AuditLogRecord;
import com.wishop.model.Auditable;
import com.wishop.model.BaseObject;
import com.wishop.model.User;
import com.wishop.service.AuditLogConstants;
import com.wishop.utils.WishopMessages;
import com.wishop.utils.WishopSecurityContext;

/**
 * Uses @AspectJ aspects (AOP) to create and save the AuditInfo of every User object
 * and to store all DAO actions to the AuditLogRecord table. 
 * 
 * @author Paulo Monteiro
 *
 */

@Aspect
@Component
@EnableAspectJAutoProxy
public class UserAuditLog extends AuditLog implements AuditLogConstants {
	
	private static final Logger logger = Logger.getLogger(UserAuditLog.class);
	
	/**
	 * Method that uses @AspectJ around advice annotation. <br>
	 * ere the @Around annotation indicates that the log() method is to be applied as around advice to the 
	 * com.wishop.service.UserService.setAccountActive() pointcut. <br>
	 * 
	 * @param jp object that carries the target proxied bean, the method signature, method arguments, and 
	 * needs to explicitly call proceed(), doing a call to the proxied bean method
	 */
	@SuppressWarnings("unchecked")
	@Around("execution(* com.wishop.service.UserService.setAccountActive(..))")
	public void logSetAccountActive(ProceedingJoinPoint jp) {
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
			logAction(baseObject, auditLogRecord, now, user, AUDIT_USER_SET_ACTIVE_ACCOUNT);
			
		} catch (Throwable e) { 
			logger.error(WishopMessages.getMessage(AUDIT_ERROR_WHILE_UPDATING, baseObject));
			throw new HibernateException(WishopMessages.getMessage(AUDIT_ERROR_WHILE_UPDATING, baseObject), e);
		}
	}
}
