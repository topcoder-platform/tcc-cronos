/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;

/**
 * <p> It is the DTO class which is used to transfer submission data. The information can be null or can be empty,
 * therefore this check is not present in the setters.  It's the related to the equivalent Submission entity.</p>
 *
 * <p> This class is not thread safe because it's highly mutable</p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "submissionData",
        propOrder = {"submissionId", "submitterId", "contestId", "submittedDate", "submissionContent", "passedScreening", "placement", "paidFor", "price", "markedForPurchase"})
public class SubmissionData implements Serializable {
    /**
     * Represents submission rank.
     */
    private Integer rank;
    
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
     * <p> Represents the submission Id</p>
     */
    private long submissionId = -1;

    /**
     * <p> Represents the submitter Id</p>
     */
    private long submitterId = -1;

    /**
     * <p> Represents the contes tId</p>
     */
    private long contestId = -1;

    /**
     * <p> Represents the removed.</p>
     * 
     * @since TCCC-411
     */
    private boolean removed = false;
    
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
     * <p> Represents the Submitted date</p>
     */
    private XMLGregorianCalendar submittedDate;

    /**
     * <p> Represents the URL of submission</p>
     */
    private String submissionContent;

    /**
     * <p> Represents the Flag if submission past screening</p>
     */
    private boolean passedScreening = false;

    /**
     * <p> Represents the Placement (rank) of submission</p>
     */
    private int placement = -1;

    /**
     * <p> Represents the Check if submission being paid</p>
     */
    private boolean paidFor = false;

    /**
     * <p> Represents the Price of submission</p>
     */
    private double price = -1;

    /**
     * <p> Represents the Check if submission marked for purchase</p>
     */
    private boolean markedForPurchase = false;

    /**
     * <p> This is the default constructor. It does nothing.</p>
     */
    public SubmissionData() {
    }

    /**
     * <p> Return the submissionId</p>
     *
     * @return the submissionId
     */
    public long getSubmissionId() {
        return submissionId;
    }

    /**
     * <p> Set the submissionId</p>
     *
     * @param submissionId the submissionId to set
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * <p> Return the submitterId</p>
     *
     * @return the submitterId
     */
    public long getSubmitterId() {
        return submitterId;
    }

    /**
     * <p> Set the submitterId</p>
     *
     * @param submitterId the submitterId to set
     */
    public void setSubmitterId(long submitterId) {
        this.submitterId = submitterId;
    }

    /**
     * <p> Return the contestId</p>
     *
     * @return the contestId
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * <p> Set the contestId</p>
     *
     * @param contestId the contestId to set
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p> Return the submittedDate</p>
     *
     * @return the submittedDate
     */
    public XMLGregorianCalendar getSubmittedDate() {
        return submittedDate;
    }

    /**
     * <p> Set the submittedDate</p>
     *
     * @param submittedDate the submittedDate to set
     */
    public void setSubmittedDate(XMLGregorianCalendar submittedDate) {
        this.submittedDate = submittedDate;
    }

    /**
     * <p> Return the submissionContent</p>
     *
     * @return the submissionContent
     */
    public String getSubmissionContent() {
        return submissionContent;
    }

    /**
     * <p> Set the submissionContent</p>
     *
     * @param submissionContent the submissionContent to set
     */
    public void setSubmissionContent(String submissionContent) {
        this.submissionContent = submissionContent;
    }

    /**
     * <p> Return the passedScreening</p>
     *
     * @return the passedScreening
     */
    public boolean isPassedScreening() {
        return passedScreening;
    }

    /**
     * <p> Set the passedScreening</p>
     *
     * @param passedScreening the passedScreening to set
     */
    public void setPassedScreening(boolean passedScreening) {
        this.passedScreening = passedScreening;
    }

    /**
     * <p> Return the placement</p>
     *
     * @return the placement
     */
    public int getPlacement() {
        return placement;
    }

    /**
     * <p> Set the placement</p>
     *
     * @param placement the placement to set
     */
    public void setPlacement(int placement) {
        this.placement = placement;
    }

    /**
     * <p> Return the paidFor</p>
     *
     * @return the paidFor
     */
    public boolean isPaidFor() {
        return paidFor;
    }

    /**
     * <p> Set the paidFor</p>
     *
     * @param paidFor the paidFor to set
     */
    public void setPaidFor(boolean paidFor) {
        this.paidFor = paidFor;
    }

    /**
     * <p> Return the price</p>
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * <p> Set the price</p>
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * <p> Return the markedForPurchase</p>
     *
     * @return the markedForPurchase
     */
    public boolean isMarkedForPurchase() {
        return markedForPurchase;
    }

    /**
     * <p> Set the markedForPurchase</p>
     *
     * @param markedForPurchase the markedForPurchase to set
     */
    public void setMarkedForPurchase(boolean markedForPurchase) {
        this.markedForPurchase = markedForPurchase;
    }
}