/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import java.util.Date;

import javax.persistence.Id;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Represents the common entity class for contest info for SimpleProjectContestData.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author will.xie
 * @version 1.0
 */

public class CommonProjectContestData {
	/**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6991488651979864256L;


    /**
     * Represents the project id.
     */

    private Long projectId;

    /**
     * Represents the phrase name.
     */
    private String pname;

    /**
     * Represents the contest name.
     */
    private String cname;

    /**
     * Represents the start date.
     */
    private XMLGregorianCalendar startDate;

    /**
     * Represents the end date.
     */
    private XMLGregorianCalendar endDate;

    /**
     * Represents the Registrants number.
     */
    private Integer num_reg;

    /**
     * Represents the submission number.
     */
    private Integer num_sub;

    /**
     * Represents the post number in forum.
     */
    private Integer num_for;

    /**
     * Represents the status name.
     */
    private String sname;
    
    /**
     * Represents the contest type.
     */
    private String type;
    
    /**
     * Represents the contest id.
     */
    private Long contestId;
    
    /**
     * Represents the forum id.
     */
    private Integer forumId;

    /**
     * Represents the description for project.
     */
    private String description;

	 /**
     * Represents the create user of contest.
     * 
     * @since My Projects Overhaul Assembly.
     */
    private String createUser;


	/**
     * Represents the permission for contest.
     */
    private String cperm;
    
    /**
     * Represents the permissionfor project.
     */
    private String pperm;

	 /** 
     * Represents the status for spec reviews. 
     * 
     * @since Cockpit Launch Contest - Inline Spec Reviews part 2
     */
    private String specReviewStatus;
    
    /**
     * Returns the contestId.
     *
     * @return the contestId.
     */
	public Long getContestId() {
		return contestId;
	}
	 /**
     * Updates the contestId with the specified value.
     *
     * @param contestId
     *            the contestId to set.
     */
	public void setContestId(Long contestId) {
		this.contestId = contestId;
	}

	/**
     * Gets the cname.
     * 
     * @return the cname
     */
	public String getCname() {
		return cname;
	}

	/**
     * Sets the cname.
     * 
     * @param cname
     *            the new cname
     */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
     * Gets the end date.
     * 
     * @return the end date
     */
	public XMLGregorianCalendar getEndDate() {
		return endDate;
	}

	/**
     * Sets the end date.
     * 
     * @param endDate
     *            the new end date
     */
	public void setEndDate(XMLGregorianCalendar endDate) {
		this.endDate = endDate;
	}

	/**
     * Gets the num_for.
     * 
     * @return the num_for
     */
	public Integer getNum_for() {
		return num_for;
	}

	/**
     * Sets the num_for.
     * 
     * @param num_for
     *            the new num_for
     */
	public void setNum_for(Integer num_for) {
		this.num_for = num_for;
	}

	/**
     * Gets the num_reg.
     * 
     * @return the num_reg
     */
	public Integer getNum_reg() {
		return num_reg;
	}

	/**
     * Sets the num_reg.
     * 
     * @param num_reg
     *            the new num_reg
     */
	public void setNum_reg(Integer num_reg) {
		this.num_reg = num_reg;
	}

	/**
     * Gets the num_sub.
     * 
     * @return the num_sub
     */
	public Integer getNum_sub() {
		return num_sub;
	}

	/**
     * Sets the num_sub.
     * 
     * @param num_sub
     *            the new num_sub
     */
	public void setNum_sub(Integer num_sub) {
		this.num_sub = num_sub;
	}

	/**
     * Gets the pname.
     * 
     * @return the pname
     */
	public String getPname() {
		return pname;
	}

	/**
     * Sets the pname.
     * 
     * @param pname
     *            the new pname
     */
	public void setPname(String pname) {
		this.pname = pname;
	}

	/**
     * Gets the project id.
     * 
     * @return the project id
     */
	public Long getProjectId() {
		return projectId;
	}

	/**
     * Sets the project id.
     * 
     * @param projectId
     *            the new project id
     */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
     * Gets the sname.
     * 
     * @return the sname
     */
	public String getSname() {
		return sname;
	}

	/**
     * Sets the sname.
     * 
     * @param sname
     *            the new sname
     */
	public void setSname(String sname) {
		this.sname = sname;
	}

	/**
     * Gets the start date.
     * 
     * @return the start date
     */
	public XMLGregorianCalendar getStartDate() {
		return startDate;
	}

	/**
     * Sets the start date.
     * 
     * @param startDate
     *            the new start date
     */
	public void setStartDate(XMLGregorianCalendar startDate) {
		this.startDate = startDate;
	}

	/**
     * Gets the type.
     * 
     * @return the type
     */
	public String getType() {
		return type;
	}

	/**
     * Sets the type.
     * 
     * @param type
     *            the new type
     */
	public void setType(String type) {
		this.type = type;
	}
	/**
     * Returns the description.
     *
     * @return the description.
     */
	public String getDescription() {
		return description;
	}
	 /**
     * Updates the description with the specified value.
     *
     * @param description
     *            the description to set.
     */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
     * Returns the forum id.
     *
     * @return the forum id.
     */
	public Integer getForumId() {
		return forumId;
	}
	 /**
     * Updates the forumId with the specified value.
     *
     * @param forumId
     *            the forumId to set.
     */
	public void setForumId(Integer forumId) {
		this.forumId = forumId;
	}


	/**
     * <p>
     * Gets the create user.
     * </p>
     * 
     * @return the create user
     * @since My Projects Overhaul Assembly
     */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
     * <p>
     * Sets the create user.
     * </p>
     * 
     * @param createUser
     *            the create user to set
     * @since My Projects Overhaul Assembly.
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

	/**
     * Gets the cperm.
     * 
     * @return the cperm
     */
	public String getCperm() {
		return cperm;
	}
	
	/**
     * Sets the cperm.
     * 
     * @param cperm
     *            the new cperm
     */
	public void setCperm(String cperm) {
		this.cperm = cperm;
	}
	
	/**
     * Gets the pperm.
     * 
     * @return the pperm
     */
	public String getPperm() {
		return pperm;
	}
	
	/**
     * Sets the pperm.
     * 
     * @param pperm
     *            the new pperm
     */
	public void setPperm(String pperm) {
		this.pperm = pperm;
	}
	
	/**
     * Gets the spec review status.
     * 
     * @return the spec review status
     * 
     * @since Cockpit Launch Contest - Inline Spec Reviews part 2
     */
    public String getSpecReviewStatus() {
        return specReviewStatus;
    }

    /**
     * Sets the spec review status.
     * 
     * @param specReviewStatus
     *            the new spec review status
     * 
     * @since Cockpit Launch Contest - Inline Spec Reviews part 2
     */
    public void setSpecReviewStatus(String specReviewStatus) {
        this.specReviewStatus = specReviewStatus;
    }
}
