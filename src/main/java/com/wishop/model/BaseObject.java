package com.wishop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Type;


/**
 * Base Wishop object
 * @author Paulo Monteiro
 */
@MappedSuperclass
public abstract class BaseObject<T, ID extends Serializable> implements Auditable<ID>, Serializable, Comparable<T>, Cloneable
{
	private static final long serialVersionUID = -1687484459562393898L;
	
	@Id
	@Type(type="long")
	@Column(nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE, generator="generatorName")
	@TableGenerator(name="generatorName", allocationSize=1) 
	protected ID id;
	
	@Column(nullable=false)
    protected boolean deleted;
	
	@Embedded
	protected AuditInfo<ID> auditInfo = new AuditInfo<ID>();
	
    /**
	 * @param id the id to set
	 */
	public void setId(ID id) {
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	public ID getId() {
		return this.id;
	}
	
	/**
	 * Checks to see if the object is new.<br>
	 * This is useful for the Bean Form Controllers
	 * @return true - if <b>this.id</b> is null, false otherwise
	 */
	public boolean isNew() {
		return (this.id == null);
	}
    
    public boolean isDeleted()
    {
        return this.deleted;
    }

    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
	@Override
	@SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if( !(obj instanceof BaseObject) ) return false;
        BaseObject<T,ID> genericObject = (BaseObject<T,ID>) obj;
        if(this.getId() == null || genericObject.getId() == null) return false;
        return this.getId().equals(genericObject.getId());
    }
	
	/**
	 * Returns a hash code value for the object. This method is supported for the benefit of hashtables such 
	 * as those provided by java.util.Hashtable.
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		if(this.getId() != null) {
			return 1612 * this.getId().hashCode();
		} else {
			return super.hashCode();
		}
	}

	@SuppressWarnings("unchecked")
	public int compareTo(T o) {
		if (o instanceof BaseObject && this.getId() != null && ((BaseObject<T,ID>)o).getId() != null) {
            return ((Long)this.getId()).compareTo((Long)((BaseObject<T,ID>)o).getId());
		} else return 0;
	}
    
    public Object clone() throws CloneNotSupportedException
    {
    	return super.clone();
    }

	public AuditInfo<ID> getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(AuditInfo<ID> auditInfo) {
		this.auditInfo = auditInfo;
	}	
}
