/*
 * Copyright (C) 2009-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.SqlResultSetMapping;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 * <p>
 * Represents the entity class for contest info for myproject widget.
 * </p>
 *
 * <p>
 * Changes for My Projects Overhaul Assembly:
 *      Added new field contestType which maps to sql column contest_type_desc.
 *      Added new field createUser which maps to sql column create_user.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * Version 1.1
 *  Add milestoneDate  for Cockpit Release Assembly 10 - My Projects v1.0
 * 
 * Version 1.2
    Add specReviewProjectId/specReviewPending, change specReviewStatus Cockpit Spec Review - part 2 version 1.0
 *
 * Version 1.3
 *  Add contestPayment field and associated setter/getter - Direct Search Assembly
 *
 * @author will.xie, TCSDEVELOPER, murphydog, BeBetter
 * @version 1.4
 */
@SqlResultSetMapping(
        name="ContestForMyProjectResults",
        entities={@EntityResult(entityClass=SimpleProjectContestData.class,
                fields={@FieldResult(name="contestId",      column="contest_id"),
                        @FieldResult(name="projectId", column="project_id"),
                        @FieldResult(name="pname",           column="pname"),
                        @FieldResult(name="cname",           column="cname"),
                        @FieldResult(name="sname",           column="sname"),
                        @FieldResult(name="startDate",           column="start_time"),
                        @FieldResult(name="endDate",           column="end_time"),
                        @FieldResult(name="num_reg",           column="num_reg"),
                        @FieldResult(name="num_sub",           column="num_sub"),
                        @FieldResult(name="num_for",           column="num_for"),
                        @FieldResult(name="forumId",           column="forum_id"),
                        @FieldResult(name="description",           column="description"),
                        @FieldResult(name="contestType",         column="contest_type_desc"),
                        @FieldResult(name="createUser",         column="create_user"),
                        @FieldResult(name="cperm",           column="cperm"),
                        @FieldResult(name="pperm",           column="pperm"),
                        @FieldResult(name = "specReviewProjectId", column = "spr_id"),
                        @FieldResult(name = "specReviewPending", column = "spec_review_pending"),
                        @FieldResult(name = "specReviewStatusTypeId", column = "spec_review_status"),
                        @FieldResult(name = "milestoneDate", column = "milestone_date"),
                        @FieldResult(name = "contestPayment", column = "contest_payment")
                        }
        )})
@Entity
public class SimpleProjectContestData {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6991488651979864256L;


    /**
     * Represents the contest id.
     */
    @Id
    private Long contestId;

    /**
     * Represents the project id.
     */

    private Long projectId;

    /**
     * Represents the project name.
     */
    private String pname;

    /**
     * Represents the contest name.
     */
    private String cname;

    /**
     * Represents the start date.
     */
    private Date startDate;

    /**
     * Represents the end date.
     */
    private Date endDate;

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
     * Represents the forum id.
     */
    private Integer forumId;

    /**
     * Represents the description for project.
     */
    private String description;

    /**
     * Represents the type of contest.
     *
     * @since My Projects Overhaul Assembly
     */
    private String contestType;

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
    @Transient
    private String specReviewStatus;

     /** 
     * Represents the status type id for spec reviews. 
     * 
     * @since Cockpit Launch Contest - Inline Spec Reviews part 2
     */
    private Long specReviewStatusTypeId;
    
    /**
     * The spec review project id
     * @since 1.2
     */
    private Long specReviewProjectId;
    /**
     * The spec review done time.
     * @since 1.2
     */
    private int specReviewPending;

    /**
     * Returns the value of specReviewPending.
     * @return the specReviewPending
     */
    public int getSpecReviewPending() {
        return specReviewPending;
    }
    /**
     * Set the value to  specReviewPending field.
     * @param specReviewPending the specReviewPending to set
     */
    public void setSpecReviewPending(int specReviewPending) {
        this.specReviewPending = specReviewPending;
    }

    /**
     * The date of milestone(if exists).
     * @since 1.1
     */
    private Date milestoneDate;

    /**
     * The contest payment.
     *
     * @since 1.3
     */
    private Double contestPayment;

    /**
     * Returns the contest name.
     *
     * @return the contest name.
     */
    public String getCname() {
        return cname;
    }
     /**
     * Updates the contest name with the specified value.
     *
     * @param cname
     *            the contest name to set.
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

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
     * Returns the endDate.
     *
     * @return the endDate.
     */
    public XMLGregorianCalendar getEndDate() {
        return getXMLGregorianCalendar(endDate);
    }

    /**
     * Updates the endDate with the specified value.
     *
     * @param endDate
     *            the endDate to set.
     */
    public void setEndDate(XMLGregorianCalendar endDate) {
        this.endDate = getDate(endDate);
    }

    /**
     * Returns the num_for.
     *
     * @return the num_for.
     */
    public Integer getNum_for() {
        return num_for;
    }

    /**
     * Returns the num_for.
     *
     * @param num_for the num_for.
     */
    public void setNum_for(Integer num_for) {
        this.num_for = num_for;
    }

     /**
     * Returns the num_reg.
     *
     * @return the num_reg.
     */
    public Integer getNum_reg() {
        return num_reg;
    }

    /**
     * Updates the num_reg.
     *
     * @param num_reg the num_reg.
     */
    public void setNum_reg(Integer num_reg) {
        this.num_reg = num_reg;
    }

    /**
     * Returns the num_sub.
     *
     * @return the num_sub.
     */
    public Integer getNum_sub() {
        return num_sub;
    }

    /**
     * Updates the num_sub.
     *
     * @param num_sub the num_sub.
     */
    public void setNum_sub(Integer num_sub) {
        this.num_sub = num_sub;
    }

    /**
     * Returns the project name.
     *
     * @return the project name.
     */
    public String getPname() {
        return pname;
    }

     /**
     * Updates the project name with the specified value.
     *
     * @param pname
     *            the project name to set.
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * Returns the projectId.
     *
     * @return the projectId.
     */
    public Long getProjectId() {
        return projectId;
    }

     /**
     * Updates the projectId with the specified value.
     *
     * @param projectId
     *            the projectId to set.
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

     /**
     * Returns the startDate.
     *
     * @return the startDate.
     */
    public XMLGregorianCalendar getStartDate() {
        return getXMLGregorianCalendar(startDate);
    }

     /**
     * Updates the startDate with the specified value.
     *
     * @param startDate
     *            the startDate to set.
     */
    public void setStartDate(XMLGregorianCalendar startDate) {
        this.startDate = getDate(startDate);
    }

    /**
     * Returns the status name.
     *
     * @return the status name.
     */
    public String getSname() {
        return sname;
    }

     /**
     * Updates the sname with the specified value.
     *
     * @param sname
     *            the status name to set.
     */
    public void setSname(String sname) {
        this.sname = sname;
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
     * <p>Converts specified <code>XMLGregorianCalendar</code> instance into <code>Date</code> instance.</p>
     *
     * @param calendar an <code>XMLGregorianCalendar</code> representing the date to be converted.
     * @return a <code>Date</code> providing the converted value of specified calendar or <code>null</code> if specified
     *         <code>calendar</code> is <code>null</code>.
     */
    private static Date getDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

    /**
     * <p>
     * Converts specified <code>Date</code> instance into
     * <code>XMLGregorianCalendar</code> instance.
     * </p>
     *
     * @param date
     *            a <code>Date</code> representing the date to be converted.
     * @return a <code>XMLGregorianCalendar</code> providing the converted value
     *         of specified date or <code>null</code> if specified
     *         <code>date</code> is <code>null</code> or if it can't be
     *         converted to calendar.
     */
    private static XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            return null;
        }
    }

    /**
     * <p>
     * Gets the type of contest.
     * </p>
     *
     * @return the contestType
     * @since My Projects Overhaul Assembly
     */
    public String getContestType() {
        return this.contestType;
    }

    /**
     * <p>
     * Sets the type of contest.
     * </p>
     *
     * @param contestType
     *            the contestType to set
     * @since My Projects Overhaul Assembly.
     */
    public void setContestType(String contestType) {
        this.contestType = contestType;
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
     * <p>
     * Gets the cperm.
     * </p>
     *
     * @return the cperm
     */
    public String getCperm() {
        return cperm;
    }

    /**
     * <p>
     * Sets the cperm.
     * </p>
     *
     * @param cperm
     *            the cperm to set
     */
    public void setCperm(String cperm) {
        this.cperm = cperm;
    }

    /**
     * <p>
     * Gets the pperm.
     * </p>
     *
     * @return the pperm
     */
    public String getPperm() {
        return pperm;
    }

    /**
     * <p>
     * Sets the pperm.
     * </p>
     *
     * @param pperm
     *            the pperm to set
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
    @Transient
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
    @Transient
    public void setSpecReviewStatus(String specReviewStatus) {
        this.specReviewStatus = specReviewStatus;
    }

    /**
     * Gets the spec review status.
     * 
     * @return the spec review status
     * 
     * @since Cockpit Launch Contest - Inline Spec Reviews part 2
     */
    public Long getSpecReviewStatusTypeId() {
        return specReviewStatusTypeId;
    }

    /**
     * Sets the spec review status.
     * 
     * @param specReviewStatus
     *            the new spec review status
     *            
     * @since Cockpit Launch Contest - Inline Spec Reviews part 2           
     */
    public void setSpecReviewStatusId(Long specReviewStatusTypeId) {
        this.specReviewStatusTypeId = specReviewStatusTypeId;
    }



    /**
     * Returns the value of specReviewProjectId.
     * @return the specReviewProjectId
     */
    public Long getSpecReviewProjectId() {
        return specReviewProjectId;
    }
    /**
     * Set the value to  specReviewProjectId field.
     * @param specReviewProjectId the specReviewProjectId to set
     */
    public void setSpecReviewProjectId(Long specReviewProjectId) {
        this.specReviewProjectId = specReviewProjectId;
    }

    /**
     * Gets the milestone date.
     * 
     * @return the milestone date
     * 
     * @since 1.1
     */
    public XMLGregorianCalendar getMilestoneDate() {
        return getXMLGregorianCalendar(milestoneDate);
    }

    /**
     * Sets the milestone date.
     * 
     * @param milestoneDate
     *            the new milestone dates
     * 
     * @since 1.1
     */
    public void setMilestoneDate(XMLGregorianCalendar milestoneDate) {
        this.milestoneDate = getDate(milestoneDate);
    }

    /**
     * Gets the value of contest payment.
     *
     * @return the contest payment
     * @since 1.3
     */
    public Double getContestPayment() {
        return contestPayment;
    }

    /**
     * Sets the contest payment.
     *
     * @param contestPayment the contest payment value to set
     * @since 1.3
     */
    public void setContestPayment(Double contestPayment) {
        this.contestPayment = contestPayment;
    }
}
