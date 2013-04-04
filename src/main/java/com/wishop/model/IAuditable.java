package com.wishop.model;

import java.io.Serializable;

public interface IAuditable<ID extends Serializable> {
	public ID getId();
	public boolean isNew();
	public void setAuditInfo(AuditInfo<ID> auditInfo);
    public AuditInfo<ID> getAuditInfo();
}
