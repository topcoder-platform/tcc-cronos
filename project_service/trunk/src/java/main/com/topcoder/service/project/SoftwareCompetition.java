/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

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
    "id", "assetDTO", "projectHeader", "projectHeaderReason", "projectPhases", "projectResources", "projectData", "type"}
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
    public void setProjectPhases(com.topcoder.project.phases.Project projectPhases) {
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
    public double getAdminFee() {
        //TODO currently it is an empty implementation
        return 0;
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
    public void setAdminFee(double fee) {
        //TODO currently it is an empty implementation
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
}
