/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Status;
import com.topcoder.catalog.service.AssetDTO;

import com.topcoder.management.project.Project;
import com.topcoder.management.resource.Resource;

import com.topcoder.project.service.FullProjectData;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>
 * It is the DTO class which is used to transfer software competition data. The information can be null or can be
 * empty, therefore this check is not present in the setters. It's the related to the equivalent Contest entity.
 * </p>
 *
 * <p>
 * This class is not thread safe because it's highly mutable.
 * </p>
 *
 * @author PE
 * @version 1.0
 *
 * @since TopCoder Service Layer Integration 3 Assembly
 */
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "softwareCompetition", propOrder =  {
    "id", "assetDTO", "projectHeader", "projectHeaderReason",
    "projectPhases", "projectResources", "projectData", "type", "adminFee",
    "clientApproval", "clientName", "reviewPayment", "specificationReviewPayment", "contestFee", "status",
    "category", "confidence", "pricingApproval", "hasWikiSpecification", "passedSpecReview", "hasDependentCompetitions",
    "wasReposted", "notes"}
)
public class SoftwareCompetition extends Competition {
    /**
     * <p>
     * Represents the assetDTO.
     * </p>
     */
    private AssetDTO assetDTO;

    /**
     * <p>
     * Represents the projectHeader.
     * </p>
     */
    private Project projectHeader;

    /**
     * <p>
     * Represents the projectHeaderReason.
     * </p>
     */
    private String projectHeaderReason;

    /**
     * <p>
     * Represents the projectPhases.
     * </p>
     */
    private com.topcoder.project.phases.Project projectPhases;

    /**
     * <p>
     * Represents the projectResources.
     * </p>
     */
    private Resource[] projectResources;

    /**
     * <p>
     * Represents the projectData.
     * </p>
     */
    private FullProjectData projectData;

    /**
     * <p>
     * Represents the id.
     * </p>
     */
    private long id;

    /**
     * <p>
     * Represents the competition type.
     * </p>
     */
    private CompetionType type;

    /**
     * <p>The admin fee.</p>
     */
    private Double adminFee;

    /**
     * <p>Represents the flag for client approval.</p>
     */
    private boolean clientApproval;

    /**
     * <p>Represents the name of the client.</p>
     */
    private String clientName;

    /**
     * <p>Represents the review payment.</p>
    */
    private double reviewPayment;

    /**
     * <p>Represents the specification review payment.</p>
    */
    private double specificationReviewPayment;

    /**
     * <p>Represents the contest fee.</p>
    */
    private double contestFee;

    /**
     * <p>Represents the status.</p>
    */
    private Status status;

    /**
     * <p>Represents the category.</p>
    */
    private Category category;

    /**
     * <p>Represents the confidence.</p>
    */
    private int confidence;

    /**
     * <p>Represents the pricing approval flag.</p>
    */
    private boolean pricingApproval;

    /**
     * <p>Represents has wiki specification flag.</p>
    */
    private boolean hasWikiSpecification;

    /**
     * <p>Represents the passed specification review flag.</p>
    */
    private boolean passedSpecReview;

    /**
     * <p>Represents the has dependent competition flag.</p>
    */
    private boolean hasDependentCompetitions;

    /**
     * <p>Represents the was reposted flag.</p>
    */
    private boolean wasReposted;

    /**
     * <p>Represents the notes.</p>
    */
    private String notes;

    /**
     * <p>
     * Default no-arg constructor.
     * </p>
     */
    public SoftwareCompetition() {
    }

    /**
     * Gets the assetDTO.
     *
     * @return the assetDTO.
     */
    public AssetDTO getAssetDTO() {
        return assetDTO;
    }

    /**
     * Sets the assetDTO.
     *
     * @param assetDTO the assetDTO to set.
     */
    public void setAssetDTO(AssetDTO assetDTO) {
        this.assetDTO = assetDTO;
    }

    /**
     * Gets the projectHeader.
     *
     * @return the projectHeader.
     */
    public Project getProjectHeader() {
        return projectHeader;
    }

    /**
     * Sets the projectHeader.
     *
     * @param projectHeader the projectHeader to set.
     */
    public void setProjectHeader(Project projectHeader) {
        this.projectHeader = projectHeader;
    }

    /**
     * Gets the projectHeaderReason.
     *
     * @return the projectHeaderReason.
     */
    public String getProjectHeaderReason() {
        return projectHeaderReason;
    }

    /**
     * Sets the projectHeaderReason.
     *
     * @param projectHeaderReason the projectHeaderReason to set.
     */
    public void setProjectHeaderReason(String projectHeaderReason) {
        this.projectHeaderReason = projectHeaderReason;
    }

    /**
     * Gets the projectPhases.
     *
     * @return the projectPhases.
     */
    public com.topcoder.project.phases.Project getProjectPhases() {
        return projectPhases;
    }

    /**
     * Sets the projectPhases.
     *
     * @param projectPhases the projectPhases to set.
     */
    public void setProjectPhases(
        com.topcoder.project.phases.Project projectPhases) {
        this.projectPhases = projectPhases;
    }

    /**
     * Gets the projectResources.
     *
     * @return the projectResources.
     */
    public Resource[] getProjectResources() {
        return projectResources;
    }

    /**
     * Sets the projectResources.
     *
     * @param projectResources the projectResources to set.
     */
    public void setProjectResources(Resource[] projectResources) {
        this.projectResources = projectResources;
    }

    /**
     * Gets the projectData.
     *
     * @return the projectData.
     */
    public FullProjectData getProjectData() {
        return projectData;
    }

    /**
     * Sets the projectData.
     *
     * @param projectData the projectData to set.
     */
    public void setProjectData(FullProjectData projectData) {
        this.projectData = projectData;
    }

    /**
     * <p>
     * Returns the admin fee for the competition.
     * </p>
     *
     * @return the admin fee for the competition.
     */
    @Override
    public Double getAdminFee() {
        return this.adminFee;
    }

    /**
     * <p>
     * Returns the creator user id of the competition.
     * </p>
     *
     * @return the creator user id of the competition.
     */
    @Override
    public long getCreatorUserId() {
        //TODO currently it is an empty implementation
        return 0;
    }

    /**
	 * Returns the value of status.
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Set the value to  status field.
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Returns the value of category.
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Set the value to  category field.
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
     * <p>
     * Returns the digital run points for the competition.
     * </p>
     *
     * @return the digital run points for the competition.
     */
    @Override
    public double getDrPoints() {
        //TODO currently it is an empty implementation
        return 0;
    }

    /**
     * <p>
     * Returns the eligibility of the competition.
     * </p>
     *
     * @return the eligibility of the competition.
     */
    @Override
    public String getEligibility() {
        //TODO currently it is an empty implementation
        return null;
    }

    /**
     * <p>
     * Returns the end time of the competition.
     * </p>
     *
     * @return the end time of the competition.
     */
    @Override
    public XMLGregorianCalendar getEndTime() {
        //TODO currently it is an empty implementation
        return null;
    }

    /**
     * <p>
     * Returns the id.
     * </p>
     *
     * @return the id.
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Returns the prizes for the competition.
     * </p>
     *
     * @return the prizes for the competition.
     */
    @Override
    public List<CompetitionPrize> getPrizes() {
        //TODO currently it is an empty implementation
        return null;
    }

    /**
     * <p>
     * Returns the short summary of the competition.
     * </p>
     *
     * @return the short summary of the competition.
     */
    @Override
    public String getShortSummary() {
        //TODO currently it is an empty implementation
        return null;
    }

    /**
     * <p>
     * Returns the start time of the competition.
     * </p>
     *
     * @return the start time of the competition.
     */
    @Override
    public XMLGregorianCalendar getStartTime() {
        //TODO currently it is an empty implementation
        return null;
    }

    /**
     * <p>
     * Returns the competition type.
     * </p>
     *
     * @return the competition type.
     */
    @Override
    public CompetionType getType() {
        return type;
    }

    /**
     * <p>
     * Sets the creator user id of the competition.
     * </p>
     *
     * @param creatorUserId the creator user id of the competition.
     */
    @Override
    public void setCreatorUserId(long creatorUserId) {
        //TODO currently it is an empty implementation
    }

    /**
     * <p>
     * Sets the digital run points for the competition.
     * </p>
     *
     * @param drPoints the digital run points for the competition.
     */
    @Override
    public void setDrPoints(double drPoints) {
        //TODO currently it is an empty implementation
    }

    /**
     * <p>
     * Sets the eligibility of the competition.
     * </p>
     *
     * @param eligibility the eligibility of the competition.
     */
    @Override
    public void setEligibility(String eligibility) {
        //TODO currently it is an empty implementation
    }

    /**
     * <p>
     * Sets the end time of the competition.
     * </p>
     *
     * @param endTime the endTime
     */
    @Override
    public void setEndTime(XMLGregorianCalendar endTime) {
        //TODO currently it is an empty implementation
    }

    /**
     * <p>
     * Sets the id.
     * </p>
     *
     * @param id id.
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Sets the prizes for the competition.
     * </p>
     *
     * @param prizes the prizes for the competition.
     */
    @Override
    public void setPrizes(List<CompetitionPrize> prizes) {
        //TODO currently it is an empty implementation
    }

    /**
     * <p>
     * Sets the short summary of the competition.
     * </p>
     *
     * @param shortSummary the short summary of the competition.
     */
    @Override
    public void setShortSummary(String shortSummary) {
        //TODO currently it is an empty implementation
    }

    /**
     * <p>
     * Sets the start time of the competition.
     * </p>
     *
     * @param startTime the start time of the competition.
     */
    @Override
    public void setStartTime(XMLGregorianCalendar startTime) {
        //TODO currently it is an empty implementation
    }

    /**
     * <p>
     * Returns the admin fee for the competition.
     * </p>
     *
     * @param fee the admin fee
     */
    @Override
    public void setAdminFee(Double fee) {
        this.adminFee = fee;
    }

    /**
     * <p>
     * Sets the competition type.
     * </p>
     *
     * @param type the competition type.
     */
    @Override
    public void setType(CompetionType type) {
        this.type = type;
    }

    /**
     * <p>
     * Gets the client approval flag.
     * </p>
     * @param true if it is client approval competition
     */
    @Override
    public boolean getClientApproval() {
        return this.clientApproval;
    }

    @Override
    public String getClientName() {
        return clientName;
    }

    @Override
    public int getConfidence() {
        return this.confidence;
    }

    @Override
    public double getContestFee() {
        return this.contestFee;
    }

    @Override
    public boolean getHasDependentCompetitions() {
        return hasDependentCompetitions;
    }

    @Override
    public boolean getHasWikiSpecification() {
        return hasWikiSpecification;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public boolean getPassedSpecReview() {
        return passedSpecReview;
    }

    @Override
    public boolean getPricingApproval() {
        return this.pricingApproval;
    }

    @Override
    public Resource[] getResources() {
        return projectResources;
    }

    @Override
    public double getReviewPayment() {
        return this.reviewPayment;
    }

    @Override
    public double getSpecificationReviewPayment() {
        return this.specificationReviewPayment;
    }

    @Override
    public boolean getWasReposted() {
        return this.wasReposted;
    }

    @Override
    public void setClientApproval(boolean clientApproval) {
        this.clientApproval = clientApproval;
    }

    @Override
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    @Override
    public void setContestFee(double contestFee) {
        this.contestFee = contestFee;
    }

    @Override
    public void setHasDependentCompetitions(boolean hasDependentCompetitions) {
        this.hasDependentCompetitions = hasDependentCompetitions;
    }

    @Override
    public void setHasWikiSpecification(boolean hasWikiSpecification) {
        this.hasWikiSpecification = hasWikiSpecification;
    }

    @Override
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public void setPassedSpecReview(boolean passedSpecReview) {
        this.passedSpecReview = passedSpecReview;
    }

    @Override
    public void setPricingApproval(boolean pricingApproval) {
    	this.pricingApproval = pricingApproval;
    }

    @Override
    public void setResources(Resource[] resources) {
        this.projectResources = resources;
    }

    @Override
    public void setReviewPayment(double reviewPayment) {
        this.reviewPayment = reviewPayment;
    }

    @Override
    public void setSpecificationReviewPayment(double specificationReviewPayment) {
        this.specificationReviewPayment = specificationReviewPayment;
    }

    @Override
    public void setWasReposted(boolean wasReposted) {
    	this.wasReposted = wasReposted;
    }
}
