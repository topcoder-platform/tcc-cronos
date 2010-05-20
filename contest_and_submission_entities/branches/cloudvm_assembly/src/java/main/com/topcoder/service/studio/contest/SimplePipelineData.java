/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;

/**
 * <p>
 * Represents the entity class for pipeline info DTO
 * </p>
 * 
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 * 
 * <p>
 * Version 1.0.1 (Cockpit Pipeline Manager Widget Release Assembly V1.0) Change Notes:
 * - Change sname from string to long.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0.1
 * @since Cockpit Pipeline Release Assembly 1 v1.0 
 */
@SqlResultSetMapping(name = "SimplePipelineDataResults", entities = { @EntityResult(entityClass = SimplePipelineData.class, fields = {
        @FieldResult(name = "pipelineInfoId", column = "id"),
        @FieldResult(name = "contestId", column = "contest_id"),
        @FieldResult(name = "projectId", column = "project_id"), 
        @FieldResult(name = "pname", column = "pname"),
        @FieldResult(name = "cname", column = "cname"), 
        
        @FieldResult(name = "cversion", column = "cversion"),
        
        @FieldResult(name = "statusId", column = "statusId"),
        
        @FieldResult(name = "contestType", column = "contest_type_desc"),
        @FieldResult(name = "contestCategory", column = "category"),
        
        @FieldResult(name = "startDate", column = "start_time"), 
        @FieldResult(name = "endDate", column = "end_time"),
        @FieldResult(name = "durationStartTime", column = "duration_start_time"), 
        @FieldResult(name = "durationEndTime", column = "duration_end_time"),
        @FieldResult(name = "createTime", column = "creation_time"),
        @FieldResult(name = "modifyTime", column = "modification_time"),
        
        @FieldResult(name = "clientName", column = "client_name"),
        
        @FieldResult(name = "reviewPayment", column = "review_payment"),
        @FieldResult(name = "specReviewPayment", column = "specification_review_payment"),
        @FieldResult(name = "totalPrize", column = "tot_prize"),
        @FieldResult(name = "dr", column = "dr"),
        @FieldResult(name = "contestFee", column = "contest_fee"),
/*        
        @FieldResult(name = "shortDesc", column = "short_desc"),
        @FieldResult(name = "longDesc", column = "long_desc"),
        @FieldResult(name = "eligibility", column = "eligibility"),
*/        
        @FieldResult(name = "manager", column = "manager"),
        @FieldResult(name = "reviewer", column = "reviewer"),
        @FieldResult(name = "architect", column = "architect"),
        @FieldResult(name = "salesPerson", column = "sales_person"),
        
        @FieldResult(name = "clientApproval", column = "client_approval"),
        @FieldResult(name = "pricingApproval", column = "pricing_approval"),
        @FieldResult(name = "hasWikiSpecification", column = "has_wiki_specification"),
        @FieldResult(name = "passedSpecReview", column = "passed_spec_review"),
        @FieldResult(name = "hasDependentCompetitions", column = "has_dependent_competitions"),
        @FieldResult(name = "wasReposted", column = "was_reposted"),
        @FieldResult(name = "notes", column = "notes"),
        
        @FieldResult(name = "cperm", column = "cperm"),
        @FieldResult(name = "pperm", column = "pperm"),
        @FieldResult(name = "cpname", column = "cpname")}) })
@Entity
public class SimplePipelineData {

    /** Generated serial version id. */
    private static final long serialVersionUID = 1L;

	/** Represents the client project name. */
    private String cpname;
	
    /** Represents the pipeline info id. */
    private Long pipelineInfoId;

    @Id
    /** Represents the contest id. */
    private Long contestId;

    /** Represents the project id. */
    private Long projectId;

    /** Represents the project name. */
    private String pname;

    /** Represents the contest name. */
    private String cname;

    /** Represents the contest version. */
    private String cversion;

    /** Represents the start date. */
    private Date startDate;

    /** Represents the end date. */
    private Date endDate;

    /** The duration start time. */
    private Date durationStartTime;

    /** The duration end time. */
    private Date durationEndTime;

    /** Represents the status name. */
    @Transient
    private String sname;

    /** Represents the type of contest. */
    private String contestType;

    /** Represents the category of contest. */
    private String contestCategory;

    /** The create time. */
    private Date createTime;

    /** The modify time. */
    private Date modifyTime;

    /** The client name. */
    private String clientName;

    /** The review payment. */
    private Double reviewPayment;

    /** The spec review payment. */
    private Double specReviewPayment;

    /** The total prize. */
    private Double totalPrize;

    /** The dr. */
    private Double dr;

    /** The contest fee. */
    private Double contestFee;

    /** The short desc. */
//    private String shortDesc;

    /** The long desc. */
//    private String longDesc;

    /** The eligibility. */
//    private String eligibility;

    /** The manager. */
    private String manager;

    /** The reviewer. */
    private String reviewer;

    /** The architect. */
    private String architect;

    /** The sales person. */
    private String salesPerson;

    /** The client approval. */
    private Boolean clientApproval;

    /** The pricing approval. */
    private Boolean pricingApproval;

    /** The has wiki specification. */
    private Boolean hasWikiSpecification;

    /** The passed spec review. */
    private Boolean passedSpecReview;

    /** The has dependent competitions. */
    private Boolean hasDependentCompetitions;

    /** The was reposted. */
    private Boolean wasReposted;

    /** The notes. */
    private String notes;

    /** Represents the permission for contest. */
    private String cperm;

    /** Represents the permission for project. */
    private String pperm;
    
    /**
     * The status id.
     */
    private Long statusId;

	/**
     * Returns the client project name.
     * 
     * @return the client project name.
     */
    public String getCpname() {
        return cpname;
    }
	/**
     * Updates the client project name with the specified value.
     * 
     * @param cpname
     *            the client project name to set.
     */
    public void setCpname(String cpname) {
        this.cpname = cpname;
    }
	
    /**
     * Returns the value of status.
     * @return the status
     */
    public Long getStatusId() {
        return statusId;
    }

    /**
     * Set the value to  status field.
     * @param status the status to set
     */
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

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
     * <p>
     * Gets the type of contest.
     * </p>
     * 
     * @return the contestType
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
     */
    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    /**
     * Gets the pipeline info id.
     * 
     * @return the pipeline info id
     */
    public Long getPipelineInfoId() {
        return this.pipelineInfoId;
    }

    /**
     * Sets the pipeline info id.
     * 
     * @param pipelineInfoId
     *            the new pipeline info id
     */
    public void setPipelineInfoId(Long pipelineInfoId) {
        this.pipelineInfoId = pipelineInfoId;
    }

    /**
     * Gets the project id.
     * 
     * @return the project id
     */
    public Long getProjectId() {
        return this.projectId;
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
     * Gets the cversion.
     * 
     * @return the cversion
     */
    public String getCversion() {
        return this.cversion;
    }

    /**
     * Sets the cversion.
     * 
     * @param cversion
     *            the new cversion
     */
    public void setCversion(String cversion) {
        this.cversion = cversion;
    }

    /**
     * Gets the duration start time.
     * 
     * @return the duration start time
     */
    public Date getDurationStartTime() {
        return this.durationStartTime;
    }

    /**
     * Sets the duration start time.
     * 
     * @param durationStartTime
     *            the new duration start time
     */
    public void setDurationStartTime(Date durationStartTime) {
        this.durationStartTime = durationStartTime;
    }

    /**
     * Gets the duration end time.
     * 
     * @return the duration end time
     */
    public Date getDurationEndTime() {
        return this.durationEndTime;
    }

    /**
     * Sets the duration end time.
     * 
     * @param durationEndTime
     *            the new duration end time
     */
    public void setDurationEndTime(Date durationEndTime) {
        this.durationEndTime = durationEndTime;
    }

    /**
     * Gets the contest category.
     * 
     * @return the contest category
     */
    public String getContestCategory() {
        return this.contestCategory;
    }

    /**
     * Sets the contest category.
     * 
     * @param contestCategory
     *            the new contest category
     */
    public void setContestCategory(String contestCategory) {
        this.contestCategory = contestCategory;
    }

    /**
     * Gets the creates the time.
     * 
     * @return the creates the time
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * Sets the creates the time.
     * 
     * @param createTime
     *            the new creates the time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Gets the modify time.
     * 
     * @return the modify time
     */
    public Date getModifyTime() {
        return this.modifyTime;
    }

    /**
     * Sets the modify time.
     * 
     * @param modifyTime
     *            the new modify time
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * Gets the client name.
     * 
     * @return the client name
     */
    public String getClientName() {
        return this.clientName;
    }

    /**
     * Sets the client name.
     * 
     * @param clientName
     *            the new client name
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Gets the review payment.
     * 
     * @return the review payment
     */
    public Double getReviewPayment() {
        return this.reviewPayment;
    }

    /**
     * Sets the review payment.
     * 
     * @param reviewPayment
     *            the new review payment
     */
    public void setReviewPayment(Double reviewPayment) {
        this.reviewPayment = reviewPayment;
    }

    /**
     * Gets the spec review payment.
     * 
     * @return the spec review payment
     */
    public Double getSpecReviewPayment() {
        return this.specReviewPayment;
    }

    /**
     * Sets the spec review payment.
     * 
     * @param specReviewPayment
     *            the new spec review payment
     */
    public void setSpecReviewPayment(Double specReviewPayment) {
        this.specReviewPayment = specReviewPayment;
    }

    /**
     * Gets the total prize.
     * 
     * @return the total prize
     */
    public Double getTotalPrize() {
        return this.totalPrize;
    }

    /**
     * Sets the total prize.
     * 
     * @param totalPrize
     *            the new total prize
     */
    public void setTotalPrize(Double totalPrize) {
        this.totalPrize = totalPrize;
    }

    /**
     * Gets the dr.
     * 
     * @return the dr
     */
    public Double getDr() {
        return this.dr;
    }

    /**
     * Sets the dr.
     * 
     * @param dr
     *            the new dr
     */
    public void setDr(Double dr) {
        this.dr = dr;
    }

    /**
     * Gets the contest fee.
     * 
     * @return the contest fee
     */
    public Double getContestFee() {
        return this.contestFee;
    }

    /**
     * Sets the contest fee.
     * 
     * @param contestFee
     *            the new contest fee
     */
    public void setContestFee(Double contestFee) {
        this.contestFee = contestFee;
    }

    /**
     * Gets the short desc.
     * 
     * @return the short desc
     
    public String getShortDesc() {
        return this.shortDesc;
    }*/

    /**
     * Sets the short desc.
     * 
     * @param shortDesc
     *            the new short desc
     
    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }*/

    /**
     * Gets the long desc.
     * 
     * @return the long desc
     
    public String getLongDesc() {
        return this.longDesc;
    }*/

    /**
     * Sets the long desc.
     * 
     * @param longDesc
     *            the new long desc
     
    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }*/

    /**
     * Gets the eligibility.
     * 
     * @return the eligibility
     
    public String getEligibility() {
        return this.eligibility;
    }*/

    /**
     * Sets the eligibility.
     * 
     * @param eligibility
     *            the new eligibility
    
    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    } */

    /**
     * Gets the manager.
     * 
     * @return the manager
     */
    public String getManager() {
        return this.manager;
    }

    /**
     * Sets the manager.
     * 
     * @param manager
     *            the new manager
     */
    public void setManager(String manager) {
        this.manager = manager;
    }

    /**
     * Gets the reviewer.
     * 
     * @return the reviewer
     */
    public String getReviewer() {
        return this.reviewer;
    }

    /**
     * Sets the reviewer.
     * 
     * @param reviewer
     *            the new reviewer
     */
    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    /**
     * Gets the architect.
     * 
     * @return the architect
     */
    public String getArchitect() {
        return this.architect;
    }

    /**
     * Sets the architect.
     * 
     * @param architect
     *            the new architect
     */
    public void setArchitect(String architect) {
        this.architect = architect;
    }

    /**
     * Gets the sales person.
     * 
     * @return the sales person
     */
    public String getSalesPerson() {
        return this.salesPerson;
    }

    /**
     * Sets the sales person.
     * 
     * @param salesPerson
     *            the new sales person
     */
    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    /**
     * Gets the client approval.
     * 
     * @return the client approval
     */
    public Boolean getClientApproval() {
        return this.clientApproval;
    }

    /**
     * Sets the client approval.
     * 
     * @param clientApproval
     *            the new client approval
     */
    public void setClientApproval(Boolean clientApproval) {
        this.clientApproval = clientApproval;
    }

    /**
     * Gets the pricing approval.
     * 
     * @return the pricing approval
     */
    public Boolean getPricingApproval() {
        return this.pricingApproval;
    }

    /**
     * Sets the pricing approval.
     * 
     * @param pricingApproval
     *            the new pricing approval
     */
    public void setPricingApproval(Boolean pricingApproval) {
        this.pricingApproval = pricingApproval;
    }

    /**
     * Gets the checks for wiki specification.
     * 
     * @return the checks for wiki specification
     */
    public Boolean getHasWikiSpecification() {
        return this.hasWikiSpecification;
    }

    /**
     * Sets the checks for wiki specification.
     * 
     * @param hasWikiSpecification
     *            the new checks for wiki specification
     */
    public void setHasWikiSpecification(Boolean hasWikiSpecification) {
        this.hasWikiSpecification = hasWikiSpecification;
    }

    /**
     * Gets the passed spec review.
     * 
     * @return the passed spec review
     */
    public Boolean getPassedSpecReview() {
        return this.passedSpecReview;
    }

    /**
     * Sets the passed spec review.
     * 
     * @param passedSpecReview
     *            the new passed spec review
     */
    public void setPassedSpecReview(Boolean passedSpecReview) {
        this.passedSpecReview = passedSpecReview;
    }

    /**
     * Gets the checks for dependent competitions.
     * 
     * @return the checks for dependent competitions
     */
    public Boolean getHasDependentCompetitions() {
        return this.hasDependentCompetitions;
    }

    /**
     * Sets the checks for dependent competitions.
     * 
     * @param hasDependentCompetitions
     *            the new checks for dependent competitions
     */
    public void setHasDependentCompetitions(Boolean hasDependentCompetitions) {
        this.hasDependentCompetitions = hasDependentCompetitions;
    }

    /**
     * Gets the was reposted.
     * 
     * @return the was reposted
     */
    public Boolean getWasReposted() {
        return this.wasReposted;
    }

    /**
     * Sets the was reposted.
     * 
     * @param wasReposted
     *            the new was reposted
     */
    public void setWasReposted(Boolean wasReposted) {
        this.wasReposted = wasReposted;
    }

    /**
     * Gets the notes.
     * 
     * @return the notes
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * Sets the notes.
     * 
     * @param notes
     *            the new notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Gets the cperm.
     * 
     * @return the cperm
     */
    public String getCperm() {
        return this.cperm;
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
        return this.pperm;
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
     * Gets the start date.
     * 
     * @return the start date
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Sets the start date.
     * 
     * @param startDate
     *            the new start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     * 
     * @return the end date
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * Sets the end date.
     * 
     * @param endDate
     *            the new end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
