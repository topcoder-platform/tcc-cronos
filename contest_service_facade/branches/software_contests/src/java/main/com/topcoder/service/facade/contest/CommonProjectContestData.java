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

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public XMLGregorianCalendar getEndDate() {
		return endDate;
	}

	public void setEndDate(XMLGregorianCalendar endDate) {
		this.endDate = endDate;
	}

	public Integer getNum_for() {
		return num_for;
	}

	public void setNum_for(Integer num_for) {
		this.num_for = num_for;
	}

	public Integer getNum_reg() {
		return num_reg;
	}

	public void setNum_reg(Integer num_reg) {
		this.num_reg = num_reg;
	}

	public Integer getNum_sub() {
		return num_sub;
	}

	public void setNum_sub(Integer num_sub) {
		this.num_sub = num_sub;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public XMLGregorianCalendar getStartDate() {
		return startDate;
	}

	public void setStartDate(XMLGregorianCalendar startDate) {
		this.startDate = startDate;
	}

	public String getType() {
		return type;
	}

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
}
