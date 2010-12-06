/**
 * ContestData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class ContestData  implements java.io.Serializable {
    private long contestId;

    private java.lang.String name;

    private long projectId;

    private long tcDirectProjectId;

    private com.liquid.portal.service.PrizeData[] prizes;

    private java.lang.String launchDateAndTime;

    private java.lang.String winnerAnnoucementDeadline;

    private double durationInHours;

    private com.liquid.portal.service.UploadedDocument[] documentationUploads;

    private com.liquid.portal.service.ContestPayload[] contestPayloads;

    private java.lang.String shortSummary;

    private java.lang.String contestDescriptionAndRequirements;

    private java.lang.String requiredOrRestrictedColors;

    private java.lang.String requiredOrRestrictedFonts;

    private java.lang.String sizeRequirements;

    private java.lang.String otherRequirementsOrRestrictions;

    private long creatorUserId;

    private java.lang.String finalFileFormat;

    private java.lang.String otherFileFormats;

    private long statusId;

    private long detailedStatusId;

    private long submissionCount;

    private long contestTypeId;

    private long contestChannelId;

    private java.lang.String eligibility;

    private java.lang.String notesOnWinnerSelection;

    private java.lang.String prizeDescription;

    private int forumPostCount;

    private long forumId;

    private com.liquid.portal.service.MediumData[] media;

    private double drPoints;

    private double contestAdministrationFee;

    private boolean launchImmediately;

    private boolean requiresPreviewImage;

    private boolean requiresPreviewFile;

    private long maximumSubmissions;

    private long numberOfRegistrants;

    private com.liquid.portal.service.ContestPaymentData[] payments;

    private java.lang.String tcDirectProjectName;

    private long billingProject;

    private boolean multiRound;

    private com.liquid.portal.service.MilestonePrizeData milestonePrizeData;

    private com.liquid.portal.service.ContestMultiRoundInformationData multiRoundData;

    private com.liquid.portal.service.ContestGeneralInfoData generalInfo;

    private com.liquid.portal.service.ContestSpecificationsData specifications;

    private boolean nonWinningSubmissionsPurchased;

    public ContestData() {
    }

    public ContestData(
           long contestId,
           java.lang.String name,
           long projectId,
           long tcDirectProjectId,
           com.liquid.portal.service.PrizeData[] prizes,
           java.lang.String launchDateAndTime,
           java.lang.String winnerAnnoucementDeadline,
           double durationInHours,
           com.liquid.portal.service.UploadedDocument[] documentationUploads,
           com.liquid.portal.service.ContestPayload[] contestPayloads,
           java.lang.String shortSummary,
           java.lang.String contestDescriptionAndRequirements,
           java.lang.String requiredOrRestrictedColors,
           java.lang.String requiredOrRestrictedFonts,
           java.lang.String sizeRequirements,
           java.lang.String otherRequirementsOrRestrictions,
           long creatorUserId,
           java.lang.String finalFileFormat,
           java.lang.String otherFileFormats,
           long statusId,
           long detailedStatusId,
           long submissionCount,
           long contestTypeId,
           long contestChannelId,
           java.lang.String eligibility,
           java.lang.String notesOnWinnerSelection,
           java.lang.String prizeDescription,
           int forumPostCount,
           long forumId,
           com.liquid.portal.service.MediumData[] media,
           double drPoints,
           double contestAdministrationFee,
           boolean launchImmediately,
           boolean requiresPreviewImage,
           boolean requiresPreviewFile,
           long maximumSubmissions,
           long numberOfRegistrants,
           com.liquid.portal.service.ContestPaymentData[] payments,
           java.lang.String tcDirectProjectName,
           long billingProject,
           boolean multiRound,
           com.liquid.portal.service.MilestonePrizeData milestonePrizeData,
           com.liquid.portal.service.ContestMultiRoundInformationData multiRoundData,
           com.liquid.portal.service.ContestGeneralInfoData generalInfo,
           com.liquid.portal.service.ContestSpecificationsData specifications,
           boolean nonWinningSubmissionsPurchased) {
           this.contestId = contestId;
           this.name = name;
           this.projectId = projectId;
           this.tcDirectProjectId = tcDirectProjectId;
           this.prizes = prizes;
           this.launchDateAndTime = launchDateAndTime;
           this.winnerAnnoucementDeadline = winnerAnnoucementDeadline;
           this.durationInHours = durationInHours;
           this.documentationUploads = documentationUploads;
           this.contestPayloads = contestPayloads;
           this.shortSummary = shortSummary;
           this.contestDescriptionAndRequirements = contestDescriptionAndRequirements;
           this.requiredOrRestrictedColors = requiredOrRestrictedColors;
           this.requiredOrRestrictedFonts = requiredOrRestrictedFonts;
           this.sizeRequirements = sizeRequirements;
           this.otherRequirementsOrRestrictions = otherRequirementsOrRestrictions;
           this.creatorUserId = creatorUserId;
           this.finalFileFormat = finalFileFormat;
           this.otherFileFormats = otherFileFormats;
           this.statusId = statusId;
           this.detailedStatusId = detailedStatusId;
           this.submissionCount = submissionCount;
           this.contestTypeId = contestTypeId;
           this.contestChannelId = contestChannelId;
           this.eligibility = eligibility;
           this.notesOnWinnerSelection = notesOnWinnerSelection;
           this.prizeDescription = prizeDescription;
           this.forumPostCount = forumPostCount;
           this.forumId = forumId;
           this.media = media;
           this.drPoints = drPoints;
           this.contestAdministrationFee = contestAdministrationFee;
           this.launchImmediately = launchImmediately;
           this.requiresPreviewImage = requiresPreviewImage;
           this.requiresPreviewFile = requiresPreviewFile;
           this.maximumSubmissions = maximumSubmissions;
           this.numberOfRegistrants = numberOfRegistrants;
           this.payments = payments;
           this.tcDirectProjectName = tcDirectProjectName;
           this.billingProject = billingProject;
           this.multiRound = multiRound;
           this.milestonePrizeData = milestonePrizeData;
           this.multiRoundData = multiRoundData;
           this.generalInfo = generalInfo;
           this.specifications = specifications;
           this.nonWinningSubmissionsPurchased = nonWinningSubmissionsPurchased;
    }


    /**
     * Gets the contestId value for this ContestData.
     * 
     * @return contestId
     */
    public long getContestId() {
        return contestId;
    }


    /**
     * Sets the contestId value for this ContestData.
     * 
     * @param contestId
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }


    /**
     * Gets the name value for this ContestData.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this ContestData.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the projectId value for this ContestData.
     * 
     * @return projectId
     */
    public long getProjectId() {
        return projectId;
    }


    /**
     * Sets the projectId value for this ContestData.
     * 
     * @param projectId
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }


    /**
     * Gets the tcDirectProjectId value for this ContestData.
     * 
     * @return tcDirectProjectId
     */
    public long getTcDirectProjectId() {
        return tcDirectProjectId;
    }


    /**
     * Sets the tcDirectProjectId value for this ContestData.
     * 
     * @param tcDirectProjectId
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }


    /**
     * Gets the prizes value for this ContestData.
     * 
     * @return prizes
     */
    public com.liquid.portal.service.PrizeData[] getPrizes() {
        return prizes;
    }


    /**
     * Sets the prizes value for this ContestData.
     * 
     * @param prizes
     */
    public void setPrizes(com.liquid.portal.service.PrizeData[] prizes) {
        this.prizes = prizes;
    }

    public com.liquid.portal.service.PrizeData getPrizes(int i) {
        return this.prizes[i];
    }

    public void setPrizes(int i, com.liquid.portal.service.PrizeData _value) {
        this.prizes[i] = _value;
    }


    /**
     * Gets the launchDateAndTime value for this ContestData.
     * 
     * @return launchDateAndTime
     */
    public java.lang.String getLaunchDateAndTime() {
        return launchDateAndTime;
    }


    /**
     * Sets the launchDateAndTime value for this ContestData.
     * 
     * @param launchDateAndTime
     */
    public void setLaunchDateAndTime(java.lang.String launchDateAndTime) {
        this.launchDateAndTime = launchDateAndTime;
    }


    /**
     * Gets the winnerAnnoucementDeadline value for this ContestData.
     * 
     * @return winnerAnnoucementDeadline
     */
    public java.lang.String getWinnerAnnoucementDeadline() {
        return winnerAnnoucementDeadline;
    }


    /**
     * Sets the winnerAnnoucementDeadline value for this ContestData.
     * 
     * @param winnerAnnoucementDeadline
     */
    public void setWinnerAnnoucementDeadline(java.lang.String winnerAnnoucementDeadline) {
        this.winnerAnnoucementDeadline = winnerAnnoucementDeadline;
    }


    /**
     * Gets the durationInHours value for this ContestData.
     * 
     * @return durationInHours
     */
    public double getDurationInHours() {
        return durationInHours;
    }


    /**
     * Sets the durationInHours value for this ContestData.
     * 
     * @param durationInHours
     */
    public void setDurationInHours(double durationInHours) {
        this.durationInHours = durationInHours;
    }


    /**
     * Gets the documentationUploads value for this ContestData.
     * 
     * @return documentationUploads
     */
    public com.liquid.portal.service.UploadedDocument[] getDocumentationUploads() {
        return documentationUploads;
    }


    /**
     * Sets the documentationUploads value for this ContestData.
     * 
     * @param documentationUploads
     */
    public void setDocumentationUploads(com.liquid.portal.service.UploadedDocument[] documentationUploads) {
        this.documentationUploads = documentationUploads;
    }

    public com.liquid.portal.service.UploadedDocument getDocumentationUploads(int i) {
        return this.documentationUploads[i];
    }

    public void setDocumentationUploads(int i, com.liquid.portal.service.UploadedDocument _value) {
        this.documentationUploads[i] = _value;
    }


    /**
     * Gets the contestPayloads value for this ContestData.
     * 
     * @return contestPayloads
     */
    public com.liquid.portal.service.ContestPayload[] getContestPayloads() {
        return contestPayloads;
    }


    /**
     * Sets the contestPayloads value for this ContestData.
     * 
     * @param contestPayloads
     */
    public void setContestPayloads(com.liquid.portal.service.ContestPayload[] contestPayloads) {
        this.contestPayloads = contestPayloads;
    }

    public com.liquid.portal.service.ContestPayload getContestPayloads(int i) {
        return this.contestPayloads[i];
    }

    public void setContestPayloads(int i, com.liquid.portal.service.ContestPayload _value) {
        this.contestPayloads[i] = _value;
    }


    /**
     * Gets the shortSummary value for this ContestData.
     * 
     * @return shortSummary
     */
    public java.lang.String getShortSummary() {
        return shortSummary;
    }


    /**
     * Sets the shortSummary value for this ContestData.
     * 
     * @param shortSummary
     */
    public void setShortSummary(java.lang.String shortSummary) {
        this.shortSummary = shortSummary;
    }


    /**
     * Gets the contestDescriptionAndRequirements value for this ContestData.
     * 
     * @return contestDescriptionAndRequirements
     */
    public java.lang.String getContestDescriptionAndRequirements() {
        return contestDescriptionAndRequirements;
    }


    /**
     * Sets the contestDescriptionAndRequirements value for this ContestData.
     * 
     * @param contestDescriptionAndRequirements
     */
    public void setContestDescriptionAndRequirements(java.lang.String contestDescriptionAndRequirements) {
        this.contestDescriptionAndRequirements = contestDescriptionAndRequirements;
    }


    /**
     * Gets the requiredOrRestrictedColors value for this ContestData.
     * 
     * @return requiredOrRestrictedColors
     */
    public java.lang.String getRequiredOrRestrictedColors() {
        return requiredOrRestrictedColors;
    }


    /**
     * Sets the requiredOrRestrictedColors value for this ContestData.
     * 
     * @param requiredOrRestrictedColors
     */
    public void setRequiredOrRestrictedColors(java.lang.String requiredOrRestrictedColors) {
        this.requiredOrRestrictedColors = requiredOrRestrictedColors;
    }


    /**
     * Gets the requiredOrRestrictedFonts value for this ContestData.
     * 
     * @return requiredOrRestrictedFonts
     */
    public java.lang.String getRequiredOrRestrictedFonts() {
        return requiredOrRestrictedFonts;
    }


    /**
     * Sets the requiredOrRestrictedFonts value for this ContestData.
     * 
     * @param requiredOrRestrictedFonts
     */
    public void setRequiredOrRestrictedFonts(java.lang.String requiredOrRestrictedFonts) {
        this.requiredOrRestrictedFonts = requiredOrRestrictedFonts;
    }


    /**
     * Gets the sizeRequirements value for this ContestData.
     * 
     * @return sizeRequirements
     */
    public java.lang.String getSizeRequirements() {
        return sizeRequirements;
    }


    /**
     * Sets the sizeRequirements value for this ContestData.
     * 
     * @param sizeRequirements
     */
    public void setSizeRequirements(java.lang.String sizeRequirements) {
        this.sizeRequirements = sizeRequirements;
    }


    /**
     * Gets the otherRequirementsOrRestrictions value for this ContestData.
     * 
     * @return otherRequirementsOrRestrictions
     */
    public java.lang.String getOtherRequirementsOrRestrictions() {
        return otherRequirementsOrRestrictions;
    }


    /**
     * Sets the otherRequirementsOrRestrictions value for this ContestData.
     * 
     * @param otherRequirementsOrRestrictions
     */
    public void setOtherRequirementsOrRestrictions(java.lang.String otherRequirementsOrRestrictions) {
        this.otherRequirementsOrRestrictions = otherRequirementsOrRestrictions;
    }


    /**
     * Gets the creatorUserId value for this ContestData.
     * 
     * @return creatorUserId
     */
    public long getCreatorUserId() {
        return creatorUserId;
    }


    /**
     * Sets the creatorUserId value for this ContestData.
     * 
     * @param creatorUserId
     */
    public void setCreatorUserId(long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }


    /**
     * Gets the finalFileFormat value for this ContestData.
     * 
     * @return finalFileFormat
     */
    public java.lang.String getFinalFileFormat() {
        return finalFileFormat;
    }


    /**
     * Sets the finalFileFormat value for this ContestData.
     * 
     * @param finalFileFormat
     */
    public void setFinalFileFormat(java.lang.String finalFileFormat) {
        this.finalFileFormat = finalFileFormat;
    }


    /**
     * Gets the otherFileFormats value for this ContestData.
     * 
     * @return otherFileFormats
     */
    public java.lang.String getOtherFileFormats() {
        return otherFileFormats;
    }


    /**
     * Sets the otherFileFormats value for this ContestData.
     * 
     * @param otherFileFormats
     */
    public void setOtherFileFormats(java.lang.String otherFileFormats) {
        this.otherFileFormats = otherFileFormats;
    }


    /**
     * Gets the statusId value for this ContestData.
     * 
     * @return statusId
     */
    public long getStatusId() {
        return statusId;
    }


    /**
     * Sets the statusId value for this ContestData.
     * 
     * @param statusId
     */
    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }


    /**
     * Gets the detailedStatusId value for this ContestData.
     * 
     * @return detailedStatusId
     */
    public long getDetailedStatusId() {
        return detailedStatusId;
    }


    /**
     * Sets the detailedStatusId value for this ContestData.
     * 
     * @param detailedStatusId
     */
    public void setDetailedStatusId(long detailedStatusId) {
        this.detailedStatusId = detailedStatusId;
    }


    /**
     * Gets the submissionCount value for this ContestData.
     * 
     * @return submissionCount
     */
    public long getSubmissionCount() {
        return submissionCount;
    }


    /**
     * Sets the submissionCount value for this ContestData.
     * 
     * @param submissionCount
     */
    public void setSubmissionCount(long submissionCount) {
        this.submissionCount = submissionCount;
    }


    /**
     * Gets the contestTypeId value for this ContestData.
     * 
     * @return contestTypeId
     */
    public long getContestTypeId() {
        return contestTypeId;
    }


    /**
     * Sets the contestTypeId value for this ContestData.
     * 
     * @param contestTypeId
     */
    public void setContestTypeId(long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }


    /**
     * Gets the contestChannelId value for this ContestData.
     * 
     * @return contestChannelId
     */
    public long getContestChannelId() {
        return contestChannelId;
    }


    /**
     * Sets the contestChannelId value for this ContestData.
     * 
     * @param contestChannelId
     */
    public void setContestChannelId(long contestChannelId) {
        this.contestChannelId = contestChannelId;
    }


    /**
     * Gets the eligibility value for this ContestData.
     * 
     * @return eligibility
     */
    public java.lang.String getEligibility() {
        return eligibility;
    }


    /**
     * Sets the eligibility value for this ContestData.
     * 
     * @param eligibility
     */
    public void setEligibility(java.lang.String eligibility) {
        this.eligibility = eligibility;
    }


    /**
     * Gets the notesOnWinnerSelection value for this ContestData.
     * 
     * @return notesOnWinnerSelection
     */
    public java.lang.String getNotesOnWinnerSelection() {
        return notesOnWinnerSelection;
    }


    /**
     * Sets the notesOnWinnerSelection value for this ContestData.
     * 
     * @param notesOnWinnerSelection
     */
    public void setNotesOnWinnerSelection(java.lang.String notesOnWinnerSelection) {
        this.notesOnWinnerSelection = notesOnWinnerSelection;
    }


    /**
     * Gets the prizeDescription value for this ContestData.
     * 
     * @return prizeDescription
     */
    public java.lang.String getPrizeDescription() {
        return prizeDescription;
    }


    /**
     * Sets the prizeDescription value for this ContestData.
     * 
     * @param prizeDescription
     */
    public void setPrizeDescription(java.lang.String prizeDescription) {
        this.prizeDescription = prizeDescription;
    }


    /**
     * Gets the forumPostCount value for this ContestData.
     * 
     * @return forumPostCount
     */
    public int getForumPostCount() {
        return forumPostCount;
    }


    /**
     * Sets the forumPostCount value for this ContestData.
     * 
     * @param forumPostCount
     */
    public void setForumPostCount(int forumPostCount) {
        this.forumPostCount = forumPostCount;
    }


    /**
     * Gets the forumId value for this ContestData.
     * 
     * @return forumId
     */
    public long getForumId() {
        return forumId;
    }


    /**
     * Sets the forumId value for this ContestData.
     * 
     * @param forumId
     */
    public void setForumId(long forumId) {
        this.forumId = forumId;
    }


    /**
     * Gets the media value for this ContestData.
     * 
     * @return media
     */
    public com.liquid.portal.service.MediumData[] getMedia() {
        return media;
    }


    /**
     * Sets the media value for this ContestData.
     * 
     * @param media
     */
    public void setMedia(com.liquid.portal.service.MediumData[] media) {
        this.media = media;
    }

    public com.liquid.portal.service.MediumData getMedia(int i) {
        return this.media[i];
    }

    public void setMedia(int i, com.liquid.portal.service.MediumData _value) {
        this.media[i] = _value;
    }


    /**
     * Gets the drPoints value for this ContestData.
     * 
     * @return drPoints
     */
    public double getDrPoints() {
        return drPoints;
    }


    /**
     * Sets the drPoints value for this ContestData.
     * 
     * @param drPoints
     */
    public void setDrPoints(double drPoints) {
        this.drPoints = drPoints;
    }


    /**
     * Gets the contestAdministrationFee value for this ContestData.
     * 
     * @return contestAdministrationFee
     */
    public double getContestAdministrationFee() {
        return contestAdministrationFee;
    }


    /**
     * Sets the contestAdministrationFee value for this ContestData.
     * 
     * @param contestAdministrationFee
     */
    public void setContestAdministrationFee(double contestAdministrationFee) {
        this.contestAdministrationFee = contestAdministrationFee;
    }


    /**
     * Gets the launchImmediately value for this ContestData.
     * 
     * @return launchImmediately
     */
    public boolean isLaunchImmediately() {
        return launchImmediately;
    }


    /**
     * Sets the launchImmediately value for this ContestData.
     * 
     * @param launchImmediately
     */
    public void setLaunchImmediately(boolean launchImmediately) {
        this.launchImmediately = launchImmediately;
    }


    /**
     * Gets the requiresPreviewImage value for this ContestData.
     * 
     * @return requiresPreviewImage
     */
    public boolean isRequiresPreviewImage() {
        return requiresPreviewImage;
    }


    /**
     * Sets the requiresPreviewImage value for this ContestData.
     * 
     * @param requiresPreviewImage
     */
    public void setRequiresPreviewImage(boolean requiresPreviewImage) {
        this.requiresPreviewImage = requiresPreviewImage;
    }


    /**
     * Gets the requiresPreviewFile value for this ContestData.
     * 
     * @return requiresPreviewFile
     */
    public boolean isRequiresPreviewFile() {
        return requiresPreviewFile;
    }


    /**
     * Sets the requiresPreviewFile value for this ContestData.
     * 
     * @param requiresPreviewFile
     */
    public void setRequiresPreviewFile(boolean requiresPreviewFile) {
        this.requiresPreviewFile = requiresPreviewFile;
    }


    /**
     * Gets the maximumSubmissions value for this ContestData.
     * 
     * @return maximumSubmissions
     */
    public long getMaximumSubmissions() {
        return maximumSubmissions;
    }


    /**
     * Sets the maximumSubmissions value for this ContestData.
     * 
     * @param maximumSubmissions
     */
    public void setMaximumSubmissions(long maximumSubmissions) {
        this.maximumSubmissions = maximumSubmissions;
    }


    /**
     * Gets the numberOfRegistrants value for this ContestData.
     * 
     * @return numberOfRegistrants
     */
    public long getNumberOfRegistrants() {
        return numberOfRegistrants;
    }


    /**
     * Sets the numberOfRegistrants value for this ContestData.
     * 
     * @param numberOfRegistrants
     */
    public void setNumberOfRegistrants(long numberOfRegistrants) {
        this.numberOfRegistrants = numberOfRegistrants;
    }


    /**
     * Gets the payments value for this ContestData.
     * 
     * @return payments
     */
    public com.liquid.portal.service.ContestPaymentData[] getPayments() {
        return payments;
    }


    /**
     * Sets the payments value for this ContestData.
     * 
     * @param payments
     */
    public void setPayments(com.liquid.portal.service.ContestPaymentData[] payments) {
        this.payments = payments;
    }

    public com.liquid.portal.service.ContestPaymentData getPayments(int i) {
        return this.payments[i];
    }

    public void setPayments(int i, com.liquid.portal.service.ContestPaymentData _value) {
        this.payments[i] = _value;
    }


    /**
     * Gets the tcDirectProjectName value for this ContestData.
     * 
     * @return tcDirectProjectName
     */
    public java.lang.String getTcDirectProjectName() {
        return tcDirectProjectName;
    }


    /**
     * Sets the tcDirectProjectName value for this ContestData.
     * 
     * @param tcDirectProjectName
     */
    public void setTcDirectProjectName(java.lang.String tcDirectProjectName) {
        this.tcDirectProjectName = tcDirectProjectName;
    }


    /**
     * Gets the billingProject value for this ContestData.
     * 
     * @return billingProject
     */
    public long getBillingProject() {
        return billingProject;
    }


    /**
     * Sets the billingProject value for this ContestData.
     * 
     * @param billingProject
     */
    public void setBillingProject(long billingProject) {
        this.billingProject = billingProject;
    }


    /**
     * Gets the multiRound value for this ContestData.
     * 
     * @return multiRound
     */
    public boolean isMultiRound() {
        return multiRound;
    }


    /**
     * Sets the multiRound value for this ContestData.
     * 
     * @param multiRound
     */
    public void setMultiRound(boolean multiRound) {
        this.multiRound = multiRound;
    }


    /**
     * Gets the milestonePrizeData value for this ContestData.
     * 
     * @return milestonePrizeData
     */
    public com.liquid.portal.service.MilestonePrizeData getMilestonePrizeData() {
        return milestonePrizeData;
    }


    /**
     * Sets the milestonePrizeData value for this ContestData.
     * 
     * @param milestonePrizeData
     */
    public void setMilestonePrizeData(com.liquid.portal.service.MilestonePrizeData milestonePrizeData) {
        this.milestonePrizeData = milestonePrizeData;
    }


    /**
     * Gets the multiRoundData value for this ContestData.
     * 
     * @return multiRoundData
     */
    public com.liquid.portal.service.ContestMultiRoundInformationData getMultiRoundData() {
        return multiRoundData;
    }


    /**
     * Sets the multiRoundData value for this ContestData.
     * 
     * @param multiRoundData
     */
    public void setMultiRoundData(com.liquid.portal.service.ContestMultiRoundInformationData multiRoundData) {
        this.multiRoundData = multiRoundData;
    }


    /**
     * Gets the generalInfo value for this ContestData.
     * 
     * @return generalInfo
     */
    public com.liquid.portal.service.ContestGeneralInfoData getGeneralInfo() {
        return generalInfo;
    }


    /**
     * Sets the generalInfo value for this ContestData.
     * 
     * @param generalInfo
     */
    public void setGeneralInfo(com.liquid.portal.service.ContestGeneralInfoData generalInfo) {
        this.generalInfo = generalInfo;
    }


    /**
     * Gets the specifications value for this ContestData.
     * 
     * @return specifications
     */
    public com.liquid.portal.service.ContestSpecificationsData getSpecifications() {
        return specifications;
    }


    /**
     * Sets the specifications value for this ContestData.
     * 
     * @param specifications
     */
    public void setSpecifications(com.liquid.portal.service.ContestSpecificationsData specifications) {
        this.specifications = specifications;
    }


    /**
     * Gets the nonWinningSubmissionsPurchased value for this ContestData.
     * 
     * @return nonWinningSubmissionsPurchased
     */
    public boolean isNonWinningSubmissionsPurchased() {
        return nonWinningSubmissionsPurchased;
    }


    /**
     * Sets the nonWinningSubmissionsPurchased value for this ContestData.
     * 
     * @param nonWinningSubmissionsPurchased
     */
    public void setNonWinningSubmissionsPurchased(boolean nonWinningSubmissionsPurchased) {
        this.nonWinningSubmissionsPurchased = nonWinningSubmissionsPurchased;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContestData)) return false;
        ContestData other = (ContestData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.contestId == other.getContestId() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            this.projectId == other.getProjectId() &&
            this.tcDirectProjectId == other.getTcDirectProjectId() &&
            ((this.prizes==null && other.getPrizes()==null) || 
             (this.prizes!=null &&
              java.util.Arrays.equals(this.prizes, other.getPrizes()))) &&
            ((this.launchDateAndTime==null && other.getLaunchDateAndTime()==null) || 
             (this.launchDateAndTime!=null &&
              this.launchDateAndTime.equals(other.getLaunchDateAndTime()))) &&
            ((this.winnerAnnoucementDeadline==null && other.getWinnerAnnoucementDeadline()==null) || 
             (this.winnerAnnoucementDeadline!=null &&
              this.winnerAnnoucementDeadline.equals(other.getWinnerAnnoucementDeadline()))) &&
            this.durationInHours == other.getDurationInHours() &&
            ((this.documentationUploads==null && other.getDocumentationUploads()==null) || 
             (this.documentationUploads!=null &&
              java.util.Arrays.equals(this.documentationUploads, other.getDocumentationUploads()))) &&
            ((this.contestPayloads==null && other.getContestPayloads()==null) || 
             (this.contestPayloads!=null &&
              java.util.Arrays.equals(this.contestPayloads, other.getContestPayloads()))) &&
            ((this.shortSummary==null && other.getShortSummary()==null) || 
             (this.shortSummary!=null &&
              this.shortSummary.equals(other.getShortSummary()))) &&
            ((this.contestDescriptionAndRequirements==null && other.getContestDescriptionAndRequirements()==null) || 
             (this.contestDescriptionAndRequirements!=null &&
              this.contestDescriptionAndRequirements.equals(other.getContestDescriptionAndRequirements()))) &&
            ((this.requiredOrRestrictedColors==null && other.getRequiredOrRestrictedColors()==null) || 
             (this.requiredOrRestrictedColors!=null &&
              this.requiredOrRestrictedColors.equals(other.getRequiredOrRestrictedColors()))) &&
            ((this.requiredOrRestrictedFonts==null && other.getRequiredOrRestrictedFonts()==null) || 
             (this.requiredOrRestrictedFonts!=null &&
              this.requiredOrRestrictedFonts.equals(other.getRequiredOrRestrictedFonts()))) &&
            ((this.sizeRequirements==null && other.getSizeRequirements()==null) || 
             (this.sizeRequirements!=null &&
              this.sizeRequirements.equals(other.getSizeRequirements()))) &&
            ((this.otherRequirementsOrRestrictions==null && other.getOtherRequirementsOrRestrictions()==null) || 
             (this.otherRequirementsOrRestrictions!=null &&
              this.otherRequirementsOrRestrictions.equals(other.getOtherRequirementsOrRestrictions()))) &&
            this.creatorUserId == other.getCreatorUserId() &&
            ((this.finalFileFormat==null && other.getFinalFileFormat()==null) || 
             (this.finalFileFormat!=null &&
              this.finalFileFormat.equals(other.getFinalFileFormat()))) &&
            ((this.otherFileFormats==null && other.getOtherFileFormats()==null) || 
             (this.otherFileFormats!=null &&
              this.otherFileFormats.equals(other.getOtherFileFormats()))) &&
            this.statusId == other.getStatusId() &&
            this.detailedStatusId == other.getDetailedStatusId() &&
            this.submissionCount == other.getSubmissionCount() &&
            this.contestTypeId == other.getContestTypeId() &&
            this.contestChannelId == other.getContestChannelId() &&
            ((this.eligibility==null && other.getEligibility()==null) || 
             (this.eligibility!=null &&
              this.eligibility.equals(other.getEligibility()))) &&
            ((this.notesOnWinnerSelection==null && other.getNotesOnWinnerSelection()==null) || 
             (this.notesOnWinnerSelection!=null &&
              this.notesOnWinnerSelection.equals(other.getNotesOnWinnerSelection()))) &&
            ((this.prizeDescription==null && other.getPrizeDescription()==null) || 
             (this.prizeDescription!=null &&
              this.prizeDescription.equals(other.getPrizeDescription()))) &&
            this.forumPostCount == other.getForumPostCount() &&
            this.forumId == other.getForumId() &&
            ((this.media==null && other.getMedia()==null) || 
             (this.media!=null &&
              java.util.Arrays.equals(this.media, other.getMedia()))) &&
            this.drPoints == other.getDrPoints() &&
            this.contestAdministrationFee == other.getContestAdministrationFee() &&
            this.launchImmediately == other.isLaunchImmediately() &&
            this.requiresPreviewImage == other.isRequiresPreviewImage() &&
            this.requiresPreviewFile == other.isRequiresPreviewFile() &&
            this.maximumSubmissions == other.getMaximumSubmissions() &&
            this.numberOfRegistrants == other.getNumberOfRegistrants() &&
            ((this.payments==null && other.getPayments()==null) || 
             (this.payments!=null &&
              java.util.Arrays.equals(this.payments, other.getPayments()))) &&
            ((this.tcDirectProjectName==null && other.getTcDirectProjectName()==null) || 
             (this.tcDirectProjectName!=null &&
              this.tcDirectProjectName.equals(other.getTcDirectProjectName()))) &&
            this.billingProject == other.getBillingProject() &&
            this.multiRound == other.isMultiRound() &&
            ((this.milestonePrizeData==null && other.getMilestonePrizeData()==null) || 
             (this.milestonePrizeData!=null &&
              this.milestonePrizeData.equals(other.getMilestonePrizeData()))) &&
            ((this.multiRoundData==null && other.getMultiRoundData()==null) || 
             (this.multiRoundData!=null &&
              this.multiRoundData.equals(other.getMultiRoundData()))) &&
            ((this.generalInfo==null && other.getGeneralInfo()==null) || 
             (this.generalInfo!=null &&
              this.generalInfo.equals(other.getGeneralInfo()))) &&
            ((this.specifications==null && other.getSpecifications()==null) || 
             (this.specifications!=null &&
              this.specifications.equals(other.getSpecifications()))) &&
            this.nonWinningSubmissionsPurchased == other.isNonWinningSubmissionsPurchased();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += new Long(getContestId()).hashCode();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        _hashCode += new Long(getProjectId()).hashCode();
        _hashCode += new Long(getTcDirectProjectId()).hashCode();
        if (getPrizes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPrizes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPrizes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLaunchDateAndTime() != null) {
            _hashCode += getLaunchDateAndTime().hashCode();
        }
        if (getWinnerAnnoucementDeadline() != null) {
            _hashCode += getWinnerAnnoucementDeadline().hashCode();
        }
        _hashCode += new Double(getDurationInHours()).hashCode();
        if (getDocumentationUploads() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDocumentationUploads());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDocumentationUploads(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getContestPayloads() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getContestPayloads());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getContestPayloads(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getShortSummary() != null) {
            _hashCode += getShortSummary().hashCode();
        }
        if (getContestDescriptionAndRequirements() != null) {
            _hashCode += getContestDescriptionAndRequirements().hashCode();
        }
        if (getRequiredOrRestrictedColors() != null) {
            _hashCode += getRequiredOrRestrictedColors().hashCode();
        }
        if (getRequiredOrRestrictedFonts() != null) {
            _hashCode += getRequiredOrRestrictedFonts().hashCode();
        }
        if (getSizeRequirements() != null) {
            _hashCode += getSizeRequirements().hashCode();
        }
        if (getOtherRequirementsOrRestrictions() != null) {
            _hashCode += getOtherRequirementsOrRestrictions().hashCode();
        }
        _hashCode += new Long(getCreatorUserId()).hashCode();
        if (getFinalFileFormat() != null) {
            _hashCode += getFinalFileFormat().hashCode();
        }
        if (getOtherFileFormats() != null) {
            _hashCode += getOtherFileFormats().hashCode();
        }
        _hashCode += new Long(getStatusId()).hashCode();
        _hashCode += new Long(getDetailedStatusId()).hashCode();
        _hashCode += new Long(getSubmissionCount()).hashCode();
        _hashCode += new Long(getContestTypeId()).hashCode();
        _hashCode += new Long(getContestChannelId()).hashCode();
        if (getEligibility() != null) {
            _hashCode += getEligibility().hashCode();
        }
        if (getNotesOnWinnerSelection() != null) {
            _hashCode += getNotesOnWinnerSelection().hashCode();
        }
        if (getPrizeDescription() != null) {
            _hashCode += getPrizeDescription().hashCode();
        }
        _hashCode += getForumPostCount();
        _hashCode += new Long(getForumId()).hashCode();
        if (getMedia() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMedia());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMedia(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Double(getDrPoints()).hashCode();
        _hashCode += new Double(getContestAdministrationFee()).hashCode();
        _hashCode += (isLaunchImmediately() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isRequiresPreviewImage() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isRequiresPreviewFile() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += new Long(getMaximumSubmissions()).hashCode();
        _hashCode += new Long(getNumberOfRegistrants()).hashCode();
        if (getPayments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPayments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPayments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTcDirectProjectName() != null) {
            _hashCode += getTcDirectProjectName().hashCode();
        }
        _hashCode += new Long(getBillingProject()).hashCode();
        _hashCode += (isMultiRound() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getMilestonePrizeData() != null) {
            _hashCode += getMilestonePrizeData().hashCode();
        }
        if (getMultiRoundData() != null) {
            _hashCode += getMultiRoundData().hashCode();
        }
        if (getGeneralInfo() != null) {
            _hashCode += getGeneralInfo().hashCode();
        }
        if (getSpecifications() != null) {
            _hashCode += getSpecifications().hashCode();
        }
        _hashCode += (isNonWinningSubmissionsPurchased() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContestData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projectId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tcDirectProjectId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tcDirectProjectId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prizes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prizes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "prizeData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("launchDateAndTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "launchDateAndTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("winnerAnnoucementDeadline");
        elemField.setXmlName(new javax.xml.namespace.QName("", "winnerAnnoucementDeadline"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("durationInHours");
        elemField.setXmlName(new javax.xml.namespace.QName("", "durationInHours"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentationUploads");
        elemField.setXmlName(new javax.xml.namespace.QName("", "documentationUploads"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "uploadedDocument"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestPayloads");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestPayloads"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestPayload"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shortSummary");
        elemField.setXmlName(new javax.xml.namespace.QName("", "shortSummary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestDescriptionAndRequirements");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestDescriptionAndRequirements"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requiredOrRestrictedColors");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requiredOrRestrictedColors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requiredOrRestrictedFonts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requiredOrRestrictedFonts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sizeRequirements");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sizeRequirements"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherRequirementsOrRestrictions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "otherRequirementsOrRestrictions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creatorUserId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creatorUserId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finalFileFormat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "finalFileFormat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherFileFormats");
        elemField.setXmlName(new javax.xml.namespace.QName("", "otherFileFormats"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statusId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detailedStatusId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "detailedStatusId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("submissionCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "submissionCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestChannelId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestChannelId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eligibility");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eligibility"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notesOnWinnerSelection");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notesOnWinnerSelection"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prizeDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prizeDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("forumPostCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "forumPostCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("forumId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "forumId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("media");
        elemField.setXmlName(new javax.xml.namespace.QName("", "media"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "mediumData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drPoints");
        elemField.setXmlName(new javax.xml.namespace.QName("", "drPoints"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestAdministrationFee");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestAdministrationFee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("launchImmediately");
        elemField.setXmlName(new javax.xml.namespace.QName("", "launchImmediately"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requiresPreviewImage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requiresPreviewImage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requiresPreviewFile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requiresPreviewFile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maximumSubmissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maximumSubmissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfRegistrants");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numberOfRegistrants"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payments");
        elemField.setXmlName(new javax.xml.namespace.QName("", "payments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestPaymentData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tcDirectProjectName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tcDirectProjectName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingProject");
        elemField.setXmlName(new javax.xml.namespace.QName("", "billingProject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("multiRound");
        elemField.setXmlName(new javax.xml.namespace.QName("", "multiRound"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("milestonePrizeData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "milestonePrizeData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "milestonePrizeData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("multiRoundData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "multiRoundData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestMultiRoundInformationData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("generalInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "generalInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestGeneralInfoData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specifications");
        elemField.setXmlName(new javax.xml.namespace.QName("", "specifications"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestSpecificationsData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nonWinningSubmissionsPurchased");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nonWinningSubmissionsPurchased"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
