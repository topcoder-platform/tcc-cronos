/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * It is the DTO class which is used to transfer contest data. The information
 * can be null or can be empty, therefore this check is not present in the
 * setters. It's the related to the equivalent Contest entity.
 * </p>
 *
 * <p>
 * Updated for Cockpit Release Assembly for Receipts - Added TC Direct Project
 * Name property.
 * </p>
 * <p>
 * Changes in v1.3: Added the following fields with the corresponding getters
 * and setters: generalInfo, specifications, multiRoundData, milestonePrizeData
 * and nonWinningSubmissionsPurchased. And 'XmlType' annotation was updated with
 * new fields.
 * </p>
 *
 * <p>
 * Changes in v1.4 (Studio Multi-Rounds Assembly - Launch Contest): Added multiRound
 * flag field with corresponding getter and setter. 'XmlType' was also reorganized.
 * Default serialVersionUID was also added.
 * </p>
 *
 * <p>
 * This class is not thread safe because it's highly mutable
 * </p>
 *
 * @author fabrizyo, pulky
 * @version 1.4
 * @since 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestData", propOrder = { "contestId", "name", "projectId", "tcDirectProjectId", "prizes",
        "launchDateAndTime", "winnerAnnoucementDeadline", "durationInHours", "documentationUploads",
        "contestPayloads", "shortSummary", "contestDescriptionAndRequirements", "requiredOrRestrictedColors",
        "requiredOrRestrictedFonts", "sizeRequirements", "otherRequirementsOrRestrictions", "creatorUserId",
        "finalFileFormat", "otherFileFormats", "statusId", "detailedStatusId", "submissionCount",
        "contestTypeId", "contestChannelId", "eligibility", "notesOnWinnerSelection", "prizeDescription",
        "forumPostCount", "forumId", "media", "drPoints", "contestAdministrationFee", "launchImmediately",
        "requiresPreviewImage", "requiresPreviewFile", "maximumSubmissions", "numberOfRegistrants",
        "payments", "tcDirectProjectName", "billingProject", "multiRound", "milestonePrizeData", "multiRoundData",
        "generalInfo", "specifications", "nonWinningSubmissionsPurchased"})
public class ContestData implements Serializable {
    /**
     * Default serial version id.
     *
     * @since 1.4
     */
    private static final long serialVersionUID = 6506612111714972592L;

    /**
     * <p>
     * Represents the contest Id.
     * </p>
     */
    private long contestId = -1;

    /**
     * <p>
     * Represents the content name.
     * </p>
     */
    private String name;

    /**
     * <p>
     * Represents the project Id.
     * </p>
     */
    private long projectId = -1;

    /**
     * <p>
     * Represents the forum Id.
     * </p>
     */
    private long forumId = -1;

    /**
     * <p>
     * Represents the forum post count.
     * </p>
     */
    private int forumPostCount = -1;

    /**
     * <p>
     * Represents the TC Direct Project Id.
     * </p>
     */
    private long tcDirectProjectId = -1;

    /**
     * <p>
     * Represents the TC Direct Project Name.
     * </p>
     *
     * @since Cockpit Release Assembly for Receipts.
     */
    private String tcDirectProjectName = null;

    /**
     * <p>
     * Represents the billing project
     * @since Release 5 Assembly
     * </p>
     *
     */
    private long billingProject;

    /**
     * <p>
     * Represents multi round format flag
     * </p>
     *
     * @since 1.4
     */
    private boolean multiRound;

    /**
     * <p>
     * Represents the prizes
     * </p>
     */
    private final List<PrizeData> prizes = new ArrayList<PrizeData>();

    /**
     * <p>
     * Represents the media.
     * </p>
     */
    private final List<MediumData> media = new ArrayList<MediumData>();

    /**
     * <p>
     * Represents the date of contest launch.
     * </p>
     */
    private XMLGregorianCalendar launchDateAndTime;

    /**
     * <p>
     * Represents the winner announcement date.
     * </p>
     */
    private XMLGregorianCalendar winnerAnnoucementDeadline;

    /**
     * <p>
     * Represents the duration In Hours.
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
     * Represents the id of the type of contest.
     * </p>
     * [27074484-20]
     */
    private long contestTypeId = -1;

    /**
     * <p>
     * Represents the id of the channel of contest.
     * </p>
     * [TCCC-147 ]
     */
    private long contestChannelId = -1;

    /**
     * <p>
     * Represents the documentation to Upload.
     * </p>
     */
    private final List<UploadedDocument> documentationUploads = new ArrayList<UploadedDocument>();

    /**
     * <p>
     * Represents the contest Payload.
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
     * Represents the contest Description And Requirements.
     * </p>
     */
    private String contestDescriptionAndRequirements;

    /**
     * <p>
     * Represents the required or restricted colors.
     * </p>
     */
    private String requiredOrRestrictedColors;

    /**
     * <p>
     * Represents the required or restricted fonts.
     * </p>
     */
    private String requiredOrRestrictedFonts;

    /**
     * <p>
     * Represents the size Requirements.
     * </p>
     */
    private String sizeRequirements;

    /**
     * <p>
     * Represents the other requirements and description.
     * </p>
     */
    private String otherRequirementsOrRestrictions;

    /**
     * <p>
     * Represents the creator User Id.
     * </p>
     */
    private long creatorUserId = -1;

    /**
     * <p>
     * Represents the default file format, the same as in category.
     * </p>
     */
    private String finalFileFormat;

    /**
     * <p>
     * Represents the list of all supported file types separated by commas.
     * </p>
     */
    private String otherFileFormats;

    /**
     * Represents eligibility.
     */
    private String eligibility;

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
     * Represents the status id.
     */
    private long statusId;

    /**
     * Represents the detailed status id.
     */
    private long detailedStatusId;

    /**
     * Represents number of registrants.
     */
    private long numberOfRegistrants;

    /**
     * Represents the payments for this contest.
     *
     * @since BUGR-1363
     */
    private List<ContestPaymentData> payments = new ArrayList<ContestPaymentData>();

    /**
     * The general contest info. Can be any value. Has getter and setter.
     *
     * @since 1.3
     */
    private ContestGeneralInfoData generalInfo;

    /**
     * The contest specifications data. Can be any value. Has getter and setter.
     *
     * @since 1.3
     */
    private ContestSpecificationsData specifications;

    /**
     * The contest multi round data. Can be any value. Has getter and setter.
     *
     * @since 1.3
     */
    @XmlElement(nillable=true)
    private ContestMultiRoundInformationData multiRoundData;

    /**
     * The milestone prize data. Can be any value. Has getter and setter.
     *
     * @since 1.3
     */
    @XmlElement(nillable=true)
    private MilestonePrizeData milestonePrizeData;

    /**
     * The flag that indicates whether non-winning submission is purchased. Can
     * be any value. Has getter and setter.
     *
     * @since 1.3
     */
    private boolean nonWinningSubmissionsPurchased;

    /**
     * Represents notes on winner selection.
     */
    private String notesOnWinnerSelection;

    /**
     * Represents submission count of this contest.
     */
    private long submissionCount;

    /**
     * Represents prize description.
     */
    private String prizeDescription;

    /**
     * Represents whether the contest should be launched immediately after
     * creation.
     */
    private boolean launchImmediately;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public ContestData() {
    }

    /**
     * Returns eligibility.
     *
     * @return the eligibility
     */
    public String getEligibility() {
        return eligibility;
    }

    /**
     * Sets eligibility.
     *
     * @param eligibility the eligibility to set
     */
    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

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
     * @param notesOnWinnerSelection the notesOnWinnerSelection to set
     */
    public void setNotesOnWinnerSelection(String notesOnWinnerSelection) {
        this.notesOnWinnerSelection = notesOnWinnerSelection;
    }

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
     * @param prizeDescription the prizeDescription to set
     */
    public void setPrizeDescription(String prizeDescription) {
        this.prizeDescription = prizeDescription;
    }

    /**
     * @return the requiresPreviewImage
     */
    public boolean isRequiresPreviewImage() {
        return requiresPreviewImage;
    }

    /**
     * @param requiresPreviewImage the requiresPreviewImage to set
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
     * @param requiresPreviewFile the requiresPreviewFile to set
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
     * @param maximumSubmissions the maximumSubmissions to set
     */
    public void setMaximumSubmissions(long maximumSubmissions) {
        this.maximumSubmissions = maximumSubmissions;
    }

    /**
     * <p>
     * Return the contestId.
     * </p>
     *
     * @return the contestId
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * <p>
     * Set the contestId.
     * </p>
     *
     * @param contestId the contestId to set.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>
     * Return the name.
     * </p>
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Set the name.
     * </p>
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Return the projectId.
     * </p>
     *
     * @return the projectId.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Set the projectId.
     * </p>
     *
     * @param projectId the projectId to set.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Return the tcDirectProjectId.
     * </p>
     *
     * @return the tcDirectProjectId.
     */
    public long getTcDirectProjectId() {
        return tcDirectProjectId;
    }

    /**
     * <p>
     * Set the tcDirectProjectId.
     * </p>
     *
     * @param tcDirectProjectId the tcDirectProjectId to set.
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }

    /**
     * <p>
     * Return the prizes.
     * </p>
     *
     * @return the prizes.
     */
    public List<PrizeData> getPrizes() {
        return new ArrayList<PrizeData>(prizes);
    }

    /**
     * <p>
     * Set the prizes.
     * </p>
     *
     * @param prizes the prizes to set.
     * @throws IllegalArgumentException if the argument is null.
     */
    public void setPrizes(List<PrizeData> prizes) {
        Util.checkNull("prizes", prizes);
        this.prizes.clear();
        this.prizes.addAll(prizes);
    }

    /**
     * <p>
     * Return the media.
     * </p>
     *
     * @return the media.
     */
    public List<MediumData> getMedia() {
        return new ArrayList<MediumData>(media);
    }

    /**
     * <p>
     * Set the media.
     * </p>
     *
     * @param mediums the media to set.
     * @throws IllegalArgumentException if the argument is null
     */
    public void setMedia(List<MediumData> mediums) {
        Util.checkNull("media", mediums);
        this.media.clear();
        this.media.addAll(mediums);
    }

    /**
     * <p>
     * Return the launchDateAndTime.
     * </p>
     *
     * @return the launchDateAndTime.
     */
    public XMLGregorianCalendar getLaunchDateAndTime() {
        return launchDateAndTime;
    }

    /**
     * <p>
     * Set the launchDateAndTime.
     * </p>
     *
     * @param launchDateAndTime the launchDateAndTime to set.
     */
    public void setLaunchDateAndTime(XMLGregorianCalendar launchDateAndTime) {
        this.launchDateAndTime = launchDateAndTime;
    }

    /**
     * <p>
     * Return the winnerAnnoucementDeadline.
     * </p>
     *
     * @return the winnerAnnoucementDeadline.
     */
    public XMLGregorianCalendar getWinnerAnnoucementDeadline() {
        return winnerAnnoucementDeadline;
    }

    /**
     * <p>
     * Set the winnerAnnoucementDeadline.
     * </p>
     *
     * @param winnerAnnoucementDeadline the winnerAnnoucementDeadline to set.
     */
    public void setWinnerAnnoucementDeadline(XMLGregorianCalendar winnerAnnoucementDeadline) {
        this.winnerAnnoucementDeadline = winnerAnnoucementDeadline;
    }

    /**
     * <p>
     * Return the durationInHours.
     * </p>
     *
     * @return the durationInHours.
     */
    public double getDurationInHours() {
        return durationInHours;
    }

    /**
     * <p>
     * Set the durationInHours.
     * </p>
     *
     * @param durationInHours the durationInHours to set.
     */
    public void setDurationInHours(double durationInHours) {
        this.durationInHours = durationInHours;
    }

    /**
     * <p>
     * Return the documentationUploads. Make a shallow copy.
     * </p>
     *
     * @return the documentationUploads.
     */
    public List<UploadedDocument> getDocumentationUploads() {
        return new ArrayList<UploadedDocument>(documentationUploads);
    }

    /**
     * <p>
     * Set the documentationUploads. Make a shallow copy.
     * </p>
     *
     * @param documentationUploads the documentationUploads to set
     * @throws IllegalArgumentException if the argument is null
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
     * @return the contestPayloads.
     */
    public List<ContestPayload> getContestPayloads() {
        return new ArrayList<ContestPayload>(contestPayloads);
    }

    /**
     * <p>
     * Set the contestPayloads. Use a shallow copy.
     * </p>
     *
     * @param contestPayloads the contestPayloads to set
     * @throws IllegalArgumentException if the argument is null
     */
    public void setContestPayloads(List<ContestPayload> contestPayloads) {
        Util.checkNull("contestPayloads", contestPayloads);
        this.contestPayloads.clear();
        this.contestPayloads.addAll(contestPayloads);
    }

    /**
     * <p>
     * Return the shortSummary.
     * </p>
     *
     * @return the shortSummary.
     */
    public String getShortSummary() {
        return shortSummary;
    }

    /**
     * <p>
     * Set the shortSummary.
     * </p>
     *
     * @param shortSummary the shortSummary to set.
     */
    public void setShortSummary(String shortSummary) {
        this.shortSummary = shortSummary;
    }

    /**
     * <p>
     * Return the contestDescriptionAndRequirements.
     * </p>
     *
     * @return the contestDescriptionAndRequirements.
     */
    public String getContestDescriptionAndRequirements() {
        return contestDescriptionAndRequirements;
    }

    /**
     * <p>
     * Set the contestDescriptionAndRequirements.
     * </p>
     *
     * @param contestDescriptionAndRequirements the
     *        contestDescriptionAndRequirements to set.
     */
    public void setContestDescriptionAndRequirements(String contestDescriptionAndRequirements) {
        this.contestDescriptionAndRequirements = contestDescriptionAndRequirements;
    }

    /**
     * <p>
     * Return the requiredOrRestrictedColors.
     * </p>
     *
     * @return the requiredOrRestrictedColors.
     */
    public String getRequiredOrRestrictedColors() {
        return requiredOrRestrictedColors;
    }

    /**
     * <p>
     * Set the requiredOrRestrictedColors.
     * </p>
     *
     * @param requiredOrRestrictedColors the requiredOrRestrictedColors to set.
     */
    public void setRequiredOrRestrictedColors(String requiredOrRestrictedColors) {
        this.requiredOrRestrictedColors = requiredOrRestrictedColors;
    }

    /**
     * <p>
     * Return the requiredOrRestrictedFonts.
     * </p>
     *
     * @return the requiredOrRestrictedFonts.
     */
    public String getRequiredOrRestrictedFonts() {
        return requiredOrRestrictedFonts;
    }

    /**
     * <p>
     * Set the requiredOrRestrictedFonts.
     * </p>
     *
     * @param requiredOrRestrictedFonts the requiredOrRestrictedFonts to set.
     */
    public void setRequiredOrRestrictedFonts(String requiredOrRestrictedFonts) {
        this.requiredOrRestrictedFonts = requiredOrRestrictedFonts;
    }

    /**
     * <p>
     * Return the sizeRequirements.
     * </p>
     *
     * @return the sizeRequirements.
     */
    public String getSizeRequirements() {
        return sizeRequirements;
    }

    /**
     * <p>
     * Set the sizeRequirements.
     * </p>
     *
     * @param sizeRequirements the sizeRequirements to set.
     */
    public void setSizeRequirements(String sizeRequirements) {
        this.sizeRequirements = sizeRequirements;
    }

    /**
     * <p>
     * Return the otherRequirementsOrRestrictions.
     * </p>
     *
     * @return the otherRequirementsOrRestrictions.
     */
    public String getOtherRequirementsOrRestrictions() {
        return otherRequirementsOrRestrictions;
    }

    /**
     * <p>
     * Set the otherRequirementsOrRestrictions.
     * </p>
     *
     * @param otherRequirementsOrRestrictions the
     *        otherRequirementsOrRestrictions to set.
     */
    public void setOtherRequirementsOrRestrictions(String otherRequirementsOrRestrictions) {
        this.otherRequirementsOrRestrictions = otherRequirementsOrRestrictions;
    }

    /**
     * <p>
     * Return the creatorUserId.
     * </p>
     *
     * @return the creatorUserId.
     */
    public long getCreatorUserId() {
        return creatorUserId;
    }

    /**
     * <p>
     * Set the creatorUserId.
     * </p>
     *
     * @param creatorUserId the creatorUserId to set.
     */
    public void setCreatorUserId(long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    /**
     * <p>
     * Return the finalFileFormat.
     * </p>
     *
     * @return the finalFileFormat.
     */
    public String getFinalFileFormat() {
        return finalFileFormat;
    }

    /**
     * <p>
     * Set the finalFileFormat.
     * </p>
     *
     * @param finalFileFormat the finalFileFormat to set.
     */
    public void setFinalFileFormat(String finalFileFormat) {
        this.finalFileFormat = finalFileFormat;
    }

    /**
     * <p>
     * Return the otherFileFormats.
     * </p>
     *
     * @return the otherFileFormats.
     */
    public String getOtherFileFormats() {
        return otherFileFormats;
    }

    /**
     * <p>
     * Set the otherFileFormats.
     * </p>
     *
     * @param otherFileFormats the otherFileFormats to set.
     */
    public void setOtherFileFormats(String otherFileFormats) {
        this.otherFileFormats = otherFileFormats;
    }

    /**
     * Returns status id.
     *
     * @return the statusId.
     */
    public long getStatusId() {
        return statusId;
    }

    /**
     * Sets status id.
     *
     * @param statusId the statusId to set.
     */
    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    /**
     * Returns detailed status id.
     *
     * @return the detailedstatusId.
     */
    public long getDetailedStatusId() {
        return detailedStatusId;
    }

    /**
     * Sets detailed status id.
     *
     * @param statusId the detailedstatusId to set.
     */
    public void setDetailedStatusId(long statusId) {
        this.detailedStatusId = statusId;
    }

    /**
     * Returns forum id.
     *
     * @return the forumId.
     */
    public long getForumId() {
        return forumId;
    }

    /**
     * Sets forum id.
     *
     * @param forumId the forum to set.
     */
    public void setForumId(long forumId) {
        this.forumId = forumId;
    }

    /**
     * Returns forum PostCount.
     *
     * @return the forumPostCount.
     */
    public int getForumPostCount() {
        return forumPostCount;
    }

    /**
     * Sets forum PostCount.
     *
     * @param forumPostCount the PostCount to set
     */
    public void setForumPostCount(int forumPostCount) {
        this.forumPostCount = forumPostCount;
    }

    /**
     * Get contestTypeId.
     *
     * @return the contestTypeId.
     */
    public long getContestTypeId() {
        return contestTypeId;
    }

    /**
     * Set contestTypeId.
     *
     * @param contestTypeId the contestTypeId to set.
     */
    public void setContestTypeId(long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }

    /**
     * Get contestChannelId.
     *
     * @return the contestChannelId.
     */
    public long getContestChannelId() {
        return contestChannelId;
    }

    /**
     * Set contestChannelId.
     *
     * @param contestChannelId the contestChannelId to set.
     */
    public void setContestChannelId(long contestChannelId) {
        this.contestChannelId = contestChannelId;
    }

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
     * @param submissionCount submission count of this contest.
     */
    public void setSubmissionCount(long submissionCount) {
        this.submissionCount = submissionCount;
    }

    /**
     * Sets number of registrants.
     *
     * @param numberOfRegistrants the numberOfRegistrants to set
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
     * @return the drPoints.
     */
    public double getDrPoints() {
        return drPoints;
    }

    /**
     * @param drPoints the drPoints to set.
     */
    public void setDrPoints(double drPoints) {
        this.drPoints = drPoints;
    }

    /**
     * @return the contestAdministrationFee.
     */
    public double getContestAdministrationFee() {
        return contestAdministrationFee;
    }

    /**
     * @param contestAdministrationFee the contestAdministrationFee to set.
     */
    public void setContestAdministrationFee(double contestAdministrationFee) {
        this.contestAdministrationFee = contestAdministrationFee;
    }

    /**
     * Updates the LaunchImmediately with the specified value.
     *
     * @param launchImmediately the launchImmediately to set.
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

    /**
     * Returns the payments.
     *
     * @return the payments.
     * @since BUGR-1363
     */
    public List<ContestPaymentData> getPayments() {
        return new ArrayList<ContestPaymentData>(payments);
    }

    /**
     * Updates the payments with the specified value.
     *
     * @param payments the payments to set.
     * @since BUGR-1363
     */
    public void setPayments(List<ContestPaymentData> payments) {
        this.payments.clear();
        this.payments.addAll(payments);
    }

    /**
     * <p>
     * Gets the tc direct project name.
     * </p>
     *
     * @return the tc direct project name.
     * @since Cockpit Release Assembly for Receipts.
     */
    public String getTcDirectProjectName() {
        return tcDirectProjectName;
    }

    /**
     * <p>
     * Sets the tc direct project name.
     * </p>
     *
     * @param tcDirectProjectName the tc direct project name.
     * @since Cockpit Release Assembly for Receipts.
     */
    public void setTcDirectProjectName(String tcDirectProjectName) {
        this.tcDirectProjectName = tcDirectProjectName;
    }

     /**
     * <p>
     * Gets the tc direct project name.
     * </p>
     *
     * @return the tc direct project name.
     * @since Release 5 Assembly
     */
    public long getBillingProject() {
        return billingProject;
    }

    /**
     * <p>
     * Sets the tc direct project name.
     * </p>
     *
     * @param billingProject the billing project .
     * @since Release 5 Assembly
     */
    public void setBillingProject(long billingProject) {
        this.billingProject = billingProject;
    }

     /**
     * Gets the value of the multi round flag
     *
     * @return true if the contest is a multi round format, false otherwise.
     *
     * @since 1.4
     */
    public boolean isMultiRound() {
        return multiRound;
    }

     /**
     * Gets the value of the multi round flag
     *
     * @return true if the contest is a multi round format, false otherwise.
     * @since 1.4
     */
    public boolean getMultiRound() {
        return multiRound;
    }

    /**
     * Sets the value of the multi round flag
     *
     * @param multiRound the new value for the multi round flag
     *
     * @since 1.4
     */
    public void setMultiRound(boolean multiRound) {
        this.multiRound = multiRound;
    }

    /**
     * Gets the value of the general contest info.
     *
     * @return the value of the general contest info attribute.
     * @since 1.3
     */
    public ContestGeneralInfoData getGeneralInfo() {
        return this.generalInfo;
    }

    /**
     * Sets the value of the general contest info.
     *
     * @param generalInfo the new value for the general contest info attribute.
     * @since 1.3
     */
    public void setGeneralInfo(ContestGeneralInfoData generalInfo) {
        this.generalInfo = generalInfo;
    }

    /**
     * Gets the value of the contest specifications data attribute.
     *
     * @return the value of the contest specifications data attribute.
     * @since 1.3
     */
    public ContestSpecificationsData getSpecifications() {
        return this.specifications;
    }

    /**
     * Sets the value of the contest specifications data attribute.
     *
     * @param specifications the new value for the contest specifications data
     *        attribute.
     * @since 1.3
     */
    public void setSpecifications(ContestSpecificationsData specifications) {
        this.specifications = specifications;
    }

    /**
     * Gets the value of the contest multi round data attribute.
     *
     * @return the value of the contest multi round data attribute.
     * @since 1.3
     */
    public ContestMultiRoundInformationData getMultiRoundData() {
        return this.multiRoundData;
    }

    /**
     * Sets the value of the contest multi round data attribute.
     *
     * @param multiRoundData the new value for the contest multi round data
     *        attribute.
     * @since 1.3
     */
    public void setMultiRoundData(ContestMultiRoundInformationData multiRoundData) {
        this.multiRoundData = multiRoundData;
    }

    /**
     * Gets the value of the milestone prize data attribute.
     *
     * @return the value of the milestone prize data attribute.
     * @since 1.3
     */
    public MilestonePrizeData getMilestonePrizeData() {
        return this.milestonePrizeData;
    }

    /**
     * Sets the value of the milestone prize data attribute.
     *
     * @param milestonePrizeData the new value for the milestone prize data
     *        attribute.
     * @since 1.3
     */
    public void setMilestonePrizeData(MilestonePrizeData milestonePrizeData) {
        this.milestonePrizeData = milestonePrizeData;
    }

    /**
     * Gets the value of the milestone prize data attribute.
     *
     * @return the value of the milestone prize data attribute
     * @since 1.3
     */
    public boolean isNonWinningSubmissionsPurchased() {
        return this.nonWinningSubmissionsPurchased;
    }

    /**
     * Sets the value of the flag that indicates whether non-winning submission
     * is purchased.
     *
     * @param nonWinningSubmissionsPurchased the new flag that indicates whether
     *        non-winning submission is purchased.
     * @since 1.3
     */
    public void setNonWinningSubmissionsPurchased(boolean nonWinningSubmissionsPurchased) {
        this.nonWinningSubmissionsPurchased = nonWinningSubmissionsPurchased;
    }
}
