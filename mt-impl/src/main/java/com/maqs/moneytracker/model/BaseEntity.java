package com.maqs.moneytracker.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.maqs.moneytracker.common.transferobjects.Entity;

@JsonInclude(Include.NON_NULL)
public abstract class BaseEntity extends Entity implements Serializable {

	/**
	 * 
	 */
	private transient static final long serialVersionUID = -1500088764415870866L;

	protected Date createdOn = new Date(System.currentTimeMillis());

	protected Date lastModifiedOn = new Date(System.currentTimeMillis());

	protected transient Logger logger = Logger.getLogger(getClass());

	public BaseEntity() {
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);//
	}
}
