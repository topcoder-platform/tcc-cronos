/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;

/**
 * <p>
 * It is the DTO class which is used to transfer submission data. The
 * information can be null or can be empty, therefore this check is not present
 * in the setters. It's the related to the equivalent Submission entity.
 * </p>
 *
 * <p>
 * Changes v1.3: Added the following field with the corresponding getter and
 * setter: submissionType. Updated 'XmlType' annotation.
 * </p>
 *
 * <p>
 * Changes v1.4 (Prototype Conversion Studio Multi-Rounds Assembly - Submission Viewer UI) :
 * - since submission date is not maintained in the database, create date will be used to fill that value.
 * - awardMilestonePrize flag was added.
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
@XmlType(name = "submissionData", propOrder = { "submissionId", "submitterId", "contestId", "submittedDate",
        "submissionContent", "passedScreening", "placement", "paidFor", "price", "markedForPurchase", "rank",
        "removed", "feedbackText", "feedbackThumb", "submissionUrl", "artifactCount", "userRank",
        "submissionType", "awardMilestonePrize"})
public class SubmissionData implements Serializable {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 5583967615706527054L;

    /**
     * Represents submission rank.
     */
    private Integer rank;

    /**
     * The submission type. Can be any value. Has getter and setter.
     *
     * @since 1.3
     */
    private String submissionType;

    /**
     * <p>
     * Represents the submission Id.
     * </p>
     */
    private long submissionId = -1;

    /**
     * <p>
     * Represents the submitter Id.
     * </p>
     */
    private long submitterId = -1;

    /**
     * <p>
     * Represents the contest Id.
     * </p>
     */
    private long contestId = -1;

    /**
     * <p>
     * Represents the removed.
     * </p>
     *
     * @since TCCC-411
     */
    private boolean removed = false;

    /**
     * <p>
     * Represents the Submitted date.
     * </p>
     */
    private XMLGregorianCalendar submittedDate;

    /**
     * <p>
     * Represents the URL of submission.
     * </p>
     */
    private String submissionContent;

    /**
     * <p>
     * Represents the Flag if submission past screening.
     * </p>
     */
    private boolean passedScreening = false;

    /**
     * <p>
     * Represents the Placement (rank) of submission.
     * </p>
     */
    private int placement = -1;

    /**
     * <p>
     * Represents the Check if submission being paid.
     * </p>
     */
    private boolean paidFor = false;

    /**
     * <p>
     * Represents the Price of submission.
     * </p>
     */
    private double price = -1;

    /**
     * <p>
     * Represents the Check if submission marked for purchase.
     * </p>
     */
    private boolean markedForPurchase = false;

    /**
     * <p>
     * Represents the feedback text for the submission.
     * </p>
     *
     * @since Flex Submission Viewer Overhaul Assembly.
     */
    private String feedbackText;

    /**
     * <p>
     * Represents the feedback thumb for the submission.
     * </p>
     *
     * @since Flex Submission Viewer Overhaul Assembly.
     */
    private int feedbackThumb;

    /**
     * <p>
     * Represents the url of submission.
     * </p>
     *
     * @since Cockpit Submission Viewer Widget Enhancement Part 1.
     */
    private String submissionUrl;

    /**
     * <p>
     * Represents the number of artifacts in submission. For example in multi
     * image submission this value would be number of images.
     * </p>
     *
     * @since Cockpit Submission Viewer Widget Enhancement Part 1.
     */
    private int artifactCount;

    /**
     * <p>
     * Represents the user rank for the submission.
     * </p>
     *
     * @since TCCC-1219
     */
    private int userRank;

    /**
     * Flag representing whether this submission should be awarded a milestone prize or not
     *
     * @since 1.4
     */
    private Boolean awardMilestonePrize = Boolean.FALSE;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public SubmissionData() {
    }

    /**
     * Returns submission rank.
     *
     * @return the rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * Set submission rank.
     *
     * @param rank the rank to set
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * Returns whether has been removed.
     *
     * @return whether has been removed.
     * @since TCCC-411
     */
    public boolean isRemoved() {
        return removed;
    }

    /**
     * Sets removed.
     *
     * @param removed the removed to set
     * @since TCCC-411
     */
    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    /**
     * <p>
     * Return the submissionId.
     * </p>
     *
     * @return the submissionId.
     */
    public long getSubmissionId() {
        return submissionId;
    }

    /**
     * <p>
     * Set the submissionId.
     * </p>
     *
     * @param submissionId the submissionId to set
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * <p>
     * Return the submitterId.
     * </p>
     *
     * @return the submitterId.
     */
    public long getSubmitterId() {
        return submitterId;
    }

    /**
     * <p>
     * Set the submitterId.
     * </p>
     *
     * @param submitterId the submitterId to set.
     */
    public void setSubmitterId(long submitterId) {
        this.submitterId = submitterId;
    }

    /**
     * <p>
     * Return the contestId.
     * </p>
     *
     * @return the contestId.
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
     * Return the submittedDate.
     * </p>
     *
     * @return the submittedDate.
     */
    public XMLGregorianCalendar getSubmittedDate() {
        return submittedDate;
    }

    /**
     * <p>
     * Set the submittedDate.
     * </p>
     *
     * @param submittedDate the submittedDate to set.
     */
    public void setSubmittedDate(XMLGregorianCalendar submittedDate) {
        this.submittedDate = submittedDate;
    }

    /**
     * <p>
     * Return the submissionContent.
     * </p>
     *
     * @return the submissionContent.
     */
    public String getSubmissionContent() {
        return submissionContent;
    }

    /**
     * <p>
     * Set the submissionContent.
     * </p>
     *
     * @param submissionContent the submissionContent to set.
     */
    public void setSubmissionContent(String submissionContent) {
        this.submissionContent = submissionContent;
    }

    /**
     * <p>
     * Return the passedScreening.
     * </p>
     *
     * @return the passedScreening.
     */
    public boolean isPassedScreening() {
        return passedScreening;
    }

    /**
     * <p>
     * Set the passedScreening.
     * </p>
     *
     * @param passedScreening the passedScreening to set.
     */
    public void setPassedScreening(boolean passedScreening) {
        this.passedScreening = passedScreening;
    }

    /**
     * <p>
     * Return the placement.
     * </p>
     *
     * @return the placement.
     */
    public int getPlacement() {
        return placement;
    }

    /**
     * <p>
     * Set the placement.
     * </p>
     *
     * @param placement the placement to set.
     */
    public void setPlacement(int placement) {
        this.placement = placement;
    }

    /**
     * <p>
     * Return the paidFor.
     * </p>
     *
     * @return the paidFor.
     */
    public boolean isPaidFor() {
        return paidFor;
    }

    /**
     * <p>
     * Set the paidFor.
     * </p>
     *
     * @param paidFor the paidFor to set.
     */
    public void setPaidFor(boolean paidFor) {
        this.paidFor = paidFor;
    }

    /**
     * <p>
     * Return the price.
     * </p>
     *
     * @return the price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * <p>
     * Set the price.
     * </p>
     *
     * @param price the price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * <p>
     * Return the markedForPurchase.
     * </p>
     *
     * @return the markedForPurchase.
     */
    public boolean isMarkedForPurchase() {
        return markedForPurchase;
    }

    /**
     * <p>
     * Set the markedForPurchase.
     * </p>
     *
     * @param markedForPurchase the markedForPurchase to set.
     */
    public void setMarkedForPurchase(boolean markedForPurchase) {
        this.markedForPurchase = markedForPurchase;
    }

    /**
     * <p>
     * Gets the feedback text.
     * </p>
     *
     * @return the feedbackText
     * @since Flex Submission Viewer Overhaul Assembly.
     */
    public String getFeedbackText() {
        return this.feedbackText;
    }

    /**
     * <p>
     * Sets the feedback text.
     * </p>
     *
     * @param feedbackText the feedbackText to set
     * @since Flex Submission Viewer Overhaul Assembly.
     */
    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    /**
     * <p>
     * Gets the feedback thumb.
     * </p>
     *
     * @return the feedbackThumb
     * @since Flex Submission Viewer Overhaul Assembly.
     */
    public int getFeedbackThumb() {
        return this.feedbackThumb;
    }

    /**
     * <p>
     * Sets the feedback thumb.
     * </p>
     *
     * @param feedbackThumb the feedbackThumb to set
     * @since Flex Submission Viewer Overhaul Assembly.
     */
    public void setFeedbackThumb(int feedbackThumb) {
        this.feedbackThumb = feedbackThumb;
    }

    /**
     * <p>
     * Gets the submission url.
     * </p>
     *
     * @return the submissionURL
     *
     * @since Cockpit Submission Viewer Widget Enhancement Part 1.
     */
    public String getSubmissionUrl() {
        return this.submissionUrl;
    }

    /**
     * <p>
     * sets the submission url.
     * </p>
     *
     * @param submissionUrl the submissionURL to set
     *
     * @since Cockpit Submission Viewer Widget Enhancement Part 1.
     */
    public void setSubmissionUrl(String submissionUrl) {
        this.submissionUrl = submissionUrl;
    }

    /**
     * <p>
     * Gets the artifact count.
     * </p>
     *
     * @return the artifactCount
     *
     * @since Cockpit Submission Viewer Widget Enhancement Part 1.
     */
    public int getArtifactCount() {
        return this.artifactCount;
    }

    /**
     * <p>
     * Sets the artifact count.
     * </p>
     *
     * @param artifactCount the artifactCount to set
     *
     * @since Cockpit Submission Viewer Widget Enhancement Part 1.
     */
    public void setArtifactCount(int artifactCount) {
        this.artifactCount = artifactCount;
    }

    /**
     * <p>
     * Gets the user rank of this submission.
     * </p>
     *
     * @return the userRank
     * @since TCCC-1219
     */
    public int getUserRank() {
        return this.userRank;
    }

    /**
     * <p>
     * Sets the user rank of this submission.
     * </p>
     *
     * @param userRank the userRank to set
     * @since TCCC-1219
     */
    public void setUserRank(int userRank) {
        this.userRank = userRank;
    }

    /**
     * Gets the value of the submission type attribute.
     *
     * @return the value of the submission type attribute.
     * @since 1.3
     */
    public String getSubmissionType() {
        return this.submissionType;
    }

    /**
     * Sets the value of the submission type attribute.
     *
     * @param submissionType the new value for the submission type attribute.
     * @since 1.3
     */
    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
    }

    /**
     * <p>
     * Gets the award milestone prize flag
     * </p>
     *
     * @return true if the submission should be awarded a milestone prize, false otherwise
     *
     * @since 1.4
     */
    public Boolean getAwardMilestonePrize() {
        return awardMilestonePrize;
    }

    /**
     * <p>
     * Gets the award milestone prize flag
     * </p>
     *
     * @return true if the submission should be awarded a milestone prize, false otherwise
     *
     * @since 1.4
     */
    public Boolean isAwardMilestonePrize() {
        return awardMilestonePrize;
    }

    /**
     * <p>
     * Sets the award milestone prize flag
     * </p>
     *
     * @param awardMilestonePrize true if the submission should be awarded a milestone prize, false otherwise
     *
     * @since 1.4
     */
    public void setAwardMilestonePrize(Boolean awardMilestonePrize) {
        this.awardMilestonePrize = awardMilestonePrize;
    }
}
