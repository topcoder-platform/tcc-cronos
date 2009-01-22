/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * It is the DTO class which is used to transfer contest data. The information
 * can be null or can be empty, therefore this check is not present in the
 * setters. It's the related to the equivalent Contest entity.
 * </p>
 * 
 * <p>
 * This class is not thread safe because it's highly mutable
 * </p>
 * 
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestData", propOrder = { "contestId", "name", "projectId", "tcDirectProjectId", "prizes",
        "launchDateAndTime", "winnerAnnoucementDeadline", "durationInHours", "documentationUploads", "contestPayloads",
        "shortSummary", "contestDescriptionAndRequirements", "requiredOrRestrictedColors", "requiredOrRestrictedFonts",
        "sizeRequirements", "otherRequirementsOrRestrictions", "creatorUserId", "finalFileFormat", "otherFileFormats",
        "statusId", "submissionCount", "contestTypeId", "contestChannelId", "eligibility", "notesOnWinnerSelection",
        "prizeDescription", "forumPostCount", "forumId", "media", "drPoints", "contestAdministrationFee", "launchImmediately",
		"requiresPreviewImage", "requiresPreviewFile", "maximumSubmissions", "numberOfRegistrants"})
public class ContestData implements Serializable {
    /**
     * <p>
     * Represents the contest Id
     * </p>
     */
    private long contestId = -1;

    /**
     * <p>
     * Represents the content name
     * </p>
     */
    private String name;

    /**
     * <p>
     * Represents the project Id
     * </p>
     */
    private long projectId = -1;

    /**
     * <p>
     * Represents the forum Id
     * </p>
     */
    private long forumId = -1;

    /**
     * <p>
     * Represents the forum post count
     * </p>
     */
    private int forumPostCount = -1;

    /**
     * <p>
     * Represents the TC Direct Project Id
     * </p>
     */
    private long tcDirectProjectId = -1;

    /**
     * <p>
     * Represents the prizes
     * </p>
     */
    private final List<PrizeData> prizes = new ArrayList<PrizeData>();

    /**
     * <p>
     * Represents the media
     * </p>
     */
    private final List<MediumData> media = new ArrayList<MediumData>();

    /**
     * <p>
     * Represents the date of contest launch
     * </p>
     */
    private XMLGregorianCalendar launchDateAndTime;

    /**
     * <p>
     * Represents the winner annoncement date
     * </p>
     */
    private XMLGregorianCalendar winnerAnnoucementDeadline;

    /**
     * <p>
     * Represents the duration In Hours
     * </p>
     */
    private double durationInHours = -1;

    /**
     * <p>
     * Represents the Digital Run points.
     * </p>
     */
    private double drPoints = -1;

    /**
     * <p>
     * Represents the Contest Administration Fee.
     * </p>
     */
    private double contestAdministrationFee = -1;

    /**
     * <p>
     * Represents the id of the type of contest
     * </p>
     * [27074484-20]
     */
    private long contestTypeId = -1;

    /**
     * <p>
     * Represents the id of the channel of contest
     * </p>
     * [TCCC-147 ]
     */
    private long contestChannelId = -1;

    /**
     * <p>
     * Represents the documentation to Upload
     * </p>
     */
    private final List<UploadedDocument> documentationUploads = new ArrayList<UploadedDocument>();

    /**
     * <p>
     * Represents the contest Payload
     * </p>
     */
    private final List<ContestPayload> contestPayloads = new ArrayList<ContestPayload>();

    /**
     * <p>
     * Represents the short Summary of contest. It's a standard configuration
     * parameter.
     * </p>
     */
    private String shortSummary;

    /**
     * <p>
     * Represents the contest Description And Requirements
     * </p>
     */
    private String contestDescriptionAndRequirements;

    /**
     * <p>
     * Represents the required or restricted colors
     * </p>
     */
    private String requiredOrRestrictedColors;

    /**
     * <p>
     * Represents the required or restricted fonts
     * </p>
     */
    private String requiredOrRestrictedFonts;

    /**
     * <p>
     * Represents the size Requirements
     * </p>
     */
    private String sizeRequirements;

    /**
     * <p>
     * Represents the other requirements and description
     * </p>
     */
    private String otherRequirementsOrRestrictions;

    /**
     * <p>
     * Represents the creator User Id
     * </p>
     */
    private long creatorUserId = -1;

    /**
     * <p>
     * Represents the default file format, the same as in category
     * </p>
     */
    private String finalFileFormat;

    /**
     * <p>
     * Represents the list of all supported file types separated by commas
     * </p>
     */
    private String otherFileFormats;

    /**
     * Represents eligibility.
     */
    private String eligibility;

    /**
     * Returns eligibility.
     * 
     * @return the eligibility
     */
    public String getEligibility() {
        return eligibility;
    }

    /**
     * Represents whether the contest should be launched immediately after creation.
     */
    private boolean launchImmediately;
    
    /**
     * Sets eligibility.
     * 
     * @param eligibility
     *            the eligibility to set
     */
    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    /**
     * Represents notes on winner selection.
     */
    private String notesOnWinnerSelection;

    /**
     * Returns notesOnWinnerSelection.
     * 
     * @return the notesOnWinnerSelection
     */
    public String getNotesOnWinnerSelection() {
        return notesOnWinnerSelection;
    }

    /**
     * Set notesOnWinnerSelection.
     * 
     * @param notesOnWinnerSelection
     *            the notesOnWinnerSelection to set
     */
    public void setNotesOnWinnerSelection(String notesOnWinnerSelection) {
        this.notesOnWinnerSelection = notesOnWinnerSelection;
    }

    /**
     * Represents prize description.
     */
    private String prizeDescription;

    /**
     * Return prizeDescription.
     * 
     * @return the prizeDescription
     */
    public String getPrizeDescription() {
        return prizeDescription;
    }

    /**
     * Set prizeDescription.
     * 
     * @param prizeDescription
     *            the prizeDescription to set
     */
    public void setPrizeDescription(String prizeDescription) {
        this.prizeDescription = prizeDescription;
    }

    /**
     * <p>
     * Requires preview image.
     * </p>
     * 
     * @since TCCC-284
     */
    private boolean requiresPreviewImage;

    /**
     * <p>
     * Requires preview file.
     * </p>
     * 
     * @since TCCC-284
     */
    private boolean requiresPreviewFile;

    /**
     * <p>
     * Represents Maximum Submissions.
     * </p>
     * 
     * @since TCCC-284
     */
    private long maximumSubmissions;

    /**
     * @return the requiresPreviewImage
     */
    public boolean isRequiresPreviewImage() {
        return requiresPreviewImage;
    }

    /**
     * @param requiresPreviewImage
     *            the requiresPreviewImage to set
     */
    public void setRequiresPreviewImage(boolean requiresPreviewImage) {
        this.requiresPreviewImage = requiresPreviewImage;
    }

    /**
     * @return the requiresPreviewFile
     */
    public boolean isRequiresPreviewFile() {
        return requiresPreviewFile;
    }

    /**
     * @param requiresPreviewFile
     *            the requiresPreviewFile to set
     */
    public void setRequiresPreviewFile(boolean requiresPreviewFile) {
        this.requiresPreviewFile = requiresPreviewFile;
    }

    /**
     * @return the maximumSubmissions
     */
    public long getMaximumSubmissions() {
        return maximumSubmissions;
    }

    /**
     * @param maximumSubmissions
     *            the maximumSubmissions to set
     */
    public void setMaximumSubmissions(long maximumSubmissions) {
        this.maximumSubmissions = maximumSubmissions;
    }

    /**
     * Represents the status id.
     */
    private long statusId;

    /**
     * Represents number of registrants.
     */
    private long numberOfRegistrants;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public ContestData() {
    }

    /**
     * <p>
     * Return the contestId
     * </p>
     * 
     * @return the contestId
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * <p>
     * Set the contestId
     * </p>
     * 
     * @param contestId
     *            the contestId to set
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>
     * Return the name
     * </p>
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Set the name
     * </p>
     * 
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Return the projectId
     * </p>
     * 
     * @return the projectId
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Set the projectId
     * </p>
     * 
     * @param projectId
     *            the projectId to set
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Return the tcDirectProjectId
     * </p>
     * 
     * @return the tcDirectProjectId
     */
    public long getTcDirectProjectId() {
        return tcDirectProjectId;
    }

    /**
     * <p>
     * Set the tcDirectProjectId
     * </p>
     * 
     * @param tcDirectProjectId
     *            the tcDirectProjectId to set
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }

    /**
     * <p>
     * Return the prizes
     * </p>
     * 
     * @return the prizes
     */
    public List<PrizeData> getPrizes() {
        return new ArrayList<PrizeData>(prizes);
    }

    /**
     * <p>
     * Set the prizes
     * </p>
     * 
     * @param prizes
     *            the prizes to set
     * @throws IllegalArgumentException
     *             if the argument is null
     */
    public void setPrizes(List<PrizeData> prizes) {
        Util.checkNull("prizes", prizes);
        this.prizes.clear();
        this.prizes.addAll(prizes);
    }

    /**
     * <p>
     * Return the media
     * </p>
     * 
     * @return the media
     */
    public List<MediumData> getMedia() {
        return new ArrayList<MediumData>(media);
    }

    /**
     * <p>
     * Set the media
     * </p>
     * 
     * @param mediums
     *            the media to set
     * @throws IllegalArgumentException
     *             if the argument is null
     */
    public void setMedia(List<MediumData> mediums) {
        Util.checkNull("media", mediums);
        this.media.clear();
        this.media.addAll(mediums);
    }

    /**
     * <p>
     * Return the launchDateAndTime
     * </p>
     * 
     * @return the launchDateAndTime
     */
    public XMLGregorianCalendar getLaunchDateAndTime() {
        return launchDateAndTime;
    }

    /**
     * <p>
     * Set the launchDateAndTime
     * </p>
     * 
     * @param launchDateAndTime
     *            the launchDateAndTime to set
     */
    public void setLaunchDateAndTime(XMLGregorianCalendar launchDateAndTime) {
        this.launchDateAndTime = launchDateAndTime;
    }

    /**
     * <p>
     * Return the winnerAnnoucementDeadline
     * </p>
     * 
     * @return the winnerAnnoucementDeadline
     */
    public XMLGregorianCalendar getWinnerAnnoucementDeadline() {
        return winnerAnnoucementDeadline;
    }

    /**
     * <p>
     * Set the winnerAnnoucementDeadline
     * </p>
     * 
     * @param winnerAnnoucementDeadline
     *            the winnerAnnoucementDeadline to set
     */
    public void setWinnerAnnoucementDeadline(XMLGregorianCalendar winnerAnnoucementDeadline) {
        this.winnerAnnoucementDeadline = winnerAnnoucementDeadline;
    }

    /**
     * <p>
     * Return the durationInHours
     * </p>
     * 
     * @return the durationInHours
     */
    public double getDurationInHours() {
        return durationInHours;
    }

    /**
     * <p>
     * Set the durationInHours
     * </p>
     * 
     * @param durationInHours
     *            the durationInHours to set
     */
    public void setDurationInHours(double durationInHours) {
        this.durationInHours = durationInHours;
    }

    /**
     * <p>
     * Return the documentationUploads. Make a shallow copy.
     * </p>
     * 
     * @return the documentationUploads
     */
    public List<UploadedDocument> getDocumentationUploads() {
        return new ArrayList<UploadedDocument>(documentationUploads);
    }

    /**
     * <p>
     * Set the documentationUploads. Make a shallow copy.
     * </p>
     * 
     * @param documentationUploads
     *            the documentationUploads to set
     * @throws IllegalArgumentException
     *             if the argument is null
     */
    public void setDocumentationUploads(List<UploadedDocument> documentationUploads) {
        Util.checkNull("documentationUploads", documentationUploads);
        this.documentationUploads.clear();
        this.documentationUploads.addAll(documentationUploads);
    }

    /**
     * <p>
     * Return the contestPayloads. Use a shallow copy.
     * </p>
     * 
     * @return the contestPayloads
     */
    public List<ContestPayload> getContestPayloads() {
        return new ArrayList<ContestPayload>(contestPayloads);
    }

    /**
     * <p>
     * Set the contestPayloads. Use a shallow copy.
     * </p>
     * 
     * @param contestPayloads
     *            the contestPayloads to set
     * @throws IllegalArgumentException
     *             if the argument is null
     */
    public void setContestPayloads(List<ContestPayload> contestPayloads) {
        Util.checkNull("contestPayloads", contestPayloads);
        this.contestPayloads.clear();
        this.contestPayloads.addAll(contestPayloads);
    }

    /**
     * <p>
     * Return the shortSummary
     * </p>
     * 
     * @return the shortSummary
     */
    public String getShortSummary() {
        return shortSummary;
    }

    /**
     * <p>
     * Set the shortSummary
     * </p>
     * 
     * @param shortSummary
     *            the shortSummary to set
     */
    public void setShortSummary(String shortSummary) {
        this.shortSummary = shortSummary;
    }

    /**
     * <p>
     * Return the contestDescriptionAndRequirements
     * </p>
     * 
     * @return the contestDescriptionAndRequirements
     */
    public String getContestDescriptionAndRequirements() {
        return contestDescriptionAndRequirements;
    }

    /**
     * <p>
     * Set the contestDescriptionAndRequirements
     * </p>
     * 
     * @param contestDescriptionAndRequirements
     *            the contestDescriptionAndRequirements to set
     */
    public void setContestDescriptionAndRequirements(String contestDescriptionAndRequirements) {
        this.contestDescriptionAndRequirements = contestDescriptionAndRequirements;
    }

    /**
     * <p>
     * Return the requiredOrRestrictedColors
     * </p>
     * 
     * @return the requiredOrRestrictedColors
     */
    public String getRequiredOrRestrictedColors() {
        return requiredOrRestrictedColors;
    }

    /**
     * <p>
     * Set the requiredOrRestrictedColors
     * </p>
     * 
     * @param requiredOrRestrictedColors
     *            the requiredOrRestrictedColors to set
     */
    public void setRequiredOrRestrictedColors(String requiredOrRestrictedColors) {
        this.requiredOrRestrictedColors = requiredOrRestrictedColors;
    }

    /**
     * <p>
     * Return the requiredOrRestrictedFonts
     * </p>
     * 
     * @return the requiredOrRestrictedFonts
     */
    public String getRequiredOrRestrictedFonts() {
        return requiredOrRestrictedFonts;
    }

    /**
     * <p>
     * Set the requiredOrRestrictedFonts
     * </p>
     * 
     * @param requiredOrRestrictedFonts
     *            the requiredOrRestrictedFonts to set
     */
    public void setRequiredOrRestrictedFonts(String requiredOrRestrictedFonts) {
        this.requiredOrRestrictedFonts = requiredOrRestrictedFonts;
    }

    /**
     * <p>
     * Return the sizeRequirements
     * </p>
     * 
     * @return the sizeRequirements
     */
    public String getSizeRequirements() {
        return sizeRequirements;
    }

    /**
     * <p>
     * Set the sizeRequirements
     * </p>
     * 
     * @param sizeRequirements
     *            the sizeRequirements to set
     */
    public void setSizeRequirements(String sizeRequirements) {
        this.sizeRequirements = sizeRequirements;
    }

    /**
     * <p>
     * Return the otherRequirementsOrRestrictions
     * </p>
     * 
     * @return the otherRequirementsOrRestrictions
     */
    public String getOtherRequirementsOrRestrictions() {
        return otherRequirementsOrRestrictions;
    }

    /**
     * <p>
     * Set the otherRequirementsOrRestrictions
     * </p>
     * 
     * @param otherRequirementsOrRestrictions
     *            the otherRequirementsOrRestrictions to set
     */
    public void setOtherRequirementsOrRestrictions(String otherRequirementsOrRestrictions) {
        this.otherRequirementsOrRestrictions = otherRequirementsOrRestrictions;
    }

    /**
     * <p>
     * Return the creatorUserId
     * </p>
     * 
     * @return the creatorUserId
     */
    public long getCreatorUserId() {
        return creatorUserId;
    }

    /**
     * <p>
     * Set the creatorUserId
     * </p>
     * 
     * @param creatorUserId
     *            the creatorUserId to set
     */
    public void setCreatorUserId(long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    /**
     * <p>
     * Return the finalFileFormat
     * </p>
     * 
     * @return the finalFileFormat
     */
    public String getFinalFileFormat() {
        return finalFileFormat;
    }

    /**
     * <p>
     * Set the finalFileFormat
     * </p>
     * 
     * @param finalFileFormat
     *            the finalFileFormat to set
     */
    public void setFinalFileFormat(String finalFileFormat) {
        this.finalFileFormat = finalFileFormat;
    }

    /**
     * <p>
     * Return the otherFileFormats
     * </p>
     * 
     * @return the otherFileFormats
     */
    public String getOtherFileFormats() {
        return otherFileFormats;
    }

    /**
     * <p>
     * Set the otherFileFormats
     * </p>
     * 
     * @param otherFileFormats
     *            the otherFileFormats to set
     */
    public void setOtherFileFormats(String otherFileFormats) {
        this.otherFileFormats = otherFileFormats;
    }

    /**
     * Returns status id.
     * 
     * @return the statusId
     */
    public long getStatusId() {
        return statusId;
    }

    /**
     * Sets status id.
     * 
     * @param statusId
     *            the statusId to set
     */
    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    /**
     * Returns forum id.
     * 
     * @return the forumId
     */
    public long getForumId() {
        return forumId;
    }

    /**
     * Sets forum id.
     * 
     * @param forumId
     *            the forum to set
     */
    public void setForumId(long forumId) {
        this.forumId = forumId;
    }

    /**
     * Returns forum PostCount.
     * 
     * @return the forumPostCount
     */
    public int getForumPostCount() {
        return forumPostCount;
    }

    /**
     * Sets forum PostCount.
     * 
     * @param forumPostCount
     *            the PostCount to set
     */
    public void setForumPostCount(int forumPostCount) {
        this.forumPostCount = forumPostCount;
    }

    /**
     * Get contestTypeId
     * 
     * @return the contestTypeId
     */
    public long getContestTypeId() {
        return contestTypeId;
    }

    /**
     * Set contestTypeId
     * 
     * @param contestTypeId
     *            the contestTypeId to set
     */
    public void setContestTypeId(long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }

    /**
     * Get contestChannelId
     * 
     * @return the contestChannelId
     */
    public long getContestChannelId() {
        return contestChannelId;
    }

    /**
     * Set contestChannelId
     * 
     * @param contestChannelId
     *            the contestChannelId to set
     */
    public void setContestChannelId(long contestChannelId) {
        this.contestChannelId = contestChannelId;
    }

    /**
     * Represents submission count of this contest.
     */
    private long submissionCount;

    /**
     * Returns submission count of this contest.
     * 
     * @return submission count of this contest.
     */
    public long getSubmissionCount() {
        return submissionCount;
    }

    /**
     * Sets submission count of this contest.
     * 
     * @param submissionCount
     *            submission count of this contest.
     */
    public void setSubmissionCount(long submissionCount) {
        this.submissionCount = submissionCount;
    }

    /**
     * Sets number of registrants.
     * 
     * @param numberOfRegistrants
     *            the numberOfRegistrants to set
     */
    public void setNumberOfRegistrants(long numberOfRegistrants) {
        this.numberOfRegistrants = numberOfRegistrants;
    }

    /**
     * Returns number of registrants.
     * 
     * @return the numberOfRegistrants
     */
    public long getNumberOfRegistrants() {
        return numberOfRegistrants;
    }

    /**
     * @return the drPoints
     */
    public double getDrPoints() {
        return drPoints;
    }

    /**
     * @param drPoints the drPoints to set
     */
    public void setDrPoints(double drPoints) {
        this.drPoints = drPoints;
    }

    /**
     * @return the contestAdministrationFee
     */
    public double getContestAdministrationFee() {
        return contestAdministrationFee;
    }

    /**
     * @param contestAdministrationFee the contestAdministrationFee to set
     */
    public void setContestAdministrationFee(double contestAdministrationFee) {
        this.contestAdministrationFee = contestAdministrationFee;
    }
    
    /**
     * Updates the LaunchImmediately with the specified value.
     * 
     * @param launchImmediately
     *            the launchImmediately to set.
     */
    public void setLaunchImmediately(boolean launchImmediately) {
        this.launchImmediately = launchImmediately;
    }

    /**
     * Returns the launchImmediately.
     * 
     * @return the launchImmediately.
     */
    public boolean isLaunchImmediately() {
        return launchImmediately;
    }
}