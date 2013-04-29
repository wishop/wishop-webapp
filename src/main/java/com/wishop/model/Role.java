package com.wishop.model;

import javax.persistence.Entity;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.core.GrantedAuthority;

/**
 * Permissions interface 
 * @author pmonteiro
 *
 */
@Entity
public class Role extends BaseObject<Role, Long> implements GrantedAuthority {

	private static final long serialVersionUID = -5213141021966783621L;
	
	private String name;

	public static final String ROLE_REGISTERED_USER = "ROLE_REGISTERED_USER";
	public static final String ROLE_REGISTERED_RETAILER = "ROLE_REGISTERED_RETAILER";
	public static final String ROLE_ADMINISTRATOR = "ROLE_ADMINISTRATOR";

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	@Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        
        if (obj instanceof String) {
            return obj.equals(this.getName());
        }
//
//        if (obj instanceof GrantedAuthority) {
//            GrantedAuthority attr = (GrantedAuthority) obj;
//            return this.getName().equals(attr.getAuthority());
//        }
        //TODO: CORRECT THIS GrantedAuthority
        
        if( !(obj instanceof BaseObject) ) return false;
        if( !(obj instanceof Role) ) return false;
        Role roleObj = (Role) obj;
        if(this.getId() == null || roleObj.getId() == null) return false;
        return this.getId().equals(roleObj.getId()) || this.getName().equals(roleObj.getName());
    }
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	/**
	 * <b>Spring Security 3.0 UserDetails method.</b>
     * If the <code>GrantedAuthority</code> can be represented as a <code>String</code> and that
     * <code>String</code> is sufficient in precision to be relied upon for an access control decision by an {@link
     * AccessDecisionManager} (or delegate), this method should return such a <code>String</code>.
     * <p>
     * If the <code>GrantedAuthority</code> cannot be expressed with sufficient precision as a <code>String</code>,
     * <code>null</code> should be returned. Returning <code>null</code> will require an
     * <code>AccessDecisionManager</code> (or delegate) to specifically support the <code>GrantedAuthority</code>
     * implementation, so returning <code>null</code> should be avoided unless actually required.
     *
     * @return a representation of the granted authority (or <code>null</code> if the granted authority cannot be
     *         expressed as a <code>String</code> with sufficient precision).
     */
	@Override
	public String getAuthority() {
		return this.getName();
	}
	
	@Override
	public int compareTo(Role o) {
		if (o instanceof BaseObject && this.getId() != null && o.getId() != null) {
            return ((Long)this.getId()).compareTo(o.getId());
		} else if (this.getName() != null ) {
			return this.getName().compareTo(o.getName());
		} else return 0;
	}
}
