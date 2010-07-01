/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class represents project contest fee.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This class contains only mutable fields so
 * therefore it is not thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since Configurable Contest Fees v1.0 Assembly
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "projectContestFee")
@Entity
@Table(name = "project_contest_fee")
public class ProjectContestFee extends ProjectContestFeeAudit {
	/**
	 * <p>
	 * Generated serial id.
	 * </p>
	 */
	private static final long serialVersionUID = -3947166443467240318L;

	/**
	 * <p>
	 * The id field.
	 * </p>
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_contest_fee_seq")
	@SequenceGenerator(name = "project_contest_fee_seq", sequenceName = "project_contest_fee_seq", allocationSize = 5)
	@Column(name = "project_contest_fee_id")
	private long id;

	/**
	 * <p>
	 * The projectId field.
	 * </p>
	 */
	@Column(name = "billing_project_id")
	private long projectId;

	/**
	 * <p>
	 * The contestType field.
	 * </p>
	 */
	@Column(name = "contest_type_id")
	private long contestType;

	/**
	 * <p>
	 * The studio field. It represent if this contest type is studio.
	 * </p>
	 */
	@Column(name = "is_studio")
	private Boolean studio;

	/**
	 * <p>
	 * The contestFee field.
	 * </p>
	 */
	@Column(name = "contest_fee")
	private double contestFee;

	/**
	 * <p>
	 * Sets the <code>id</code> field value.
	 * </p>
	 *
	 * @param id
	 *            the value to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Gets the <code>id</code> field value.
	 * </p>
	 *
	 * @return the <code>id</code> field value
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * <p>
	 * Sets the <code>projectId</code> field value.
	 * </p>
	 *
	 * @param projectId
	 *            the value to set
	 */
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	/**
	 * <p>
	 * Gets the <code>projectId</code> field value.
	 * </p>
	 *
	 * @return the <code>projectId</code> field value
	 */
	public long getProjectId() {
		return this.projectId;
	}

	/**
	 * <p>
	 * Sets the <code>contestType</code> field value.
	 * </p>
	 *
	 * @param contestType
	 *            the value to set
	 */
	public void setContestType(long contestType) {
		this.contestType = contestType;
	}

	/**
	 * <p>
	 * Gets the <code>contestType</code> field value.
	 * </p>
	 *
	 * @return the <code>contestType</code> field value
	 */
	public long getContestType() {
		return this.contestType;
	}

	/**
	 * <p>
	 * Sets the <code>studio</code> field value.
	 * </p>
	 *
	 * @param studio
	 *            the value to set
	 */
	public void setStudio(Boolean studio) {
		this.studio = studio;
	}

	/**
	 * <p>
	 * Gets the <code>studio</code> field value.
	 * </p>
	 *
	 * @return the <code>studio</code> field value
	 */
	public Boolean getStudio() {
		return this.studio;
	}

	/**
	 * <p>
	 * Sets the <code>contestFee</code> field value.
	 * </p>
	 *
	 * @param contestFee
	 *            the value to set
	 */
	public void setContestFee(double contestFee) {
		this.contestFee = contestFee;
	}

	/**
	 * <p>
	 * Gets the <code>contestFee</code> field value.
	 * </p>
	 *
	 * @return the <code>contestFee</code> field value
	 */
	public double getContestFee() {
		return this.contestFee;
	}
}
