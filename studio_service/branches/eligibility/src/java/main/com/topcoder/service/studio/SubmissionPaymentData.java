/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Submission Data, basically used for purchased / ranked submissions when the
 * contest has been made completed from 'action required'.
 * </p>
 *
 * <p>
 * Changes v1.1 (Prototype Conversion Studio Multi-Rounds Assembly - Submission Viewer UI) : 
 * - awardMilestonePrize flag was added.
 * </p>
 *
 * @author shailendra_80, pulky
 * @version 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "submissionPaymentData", propOrder = { "id", "rank", "amount", "awardMilestonePrize", "paymentTypeId",
        "paymentReferenceNumber", "paymentStatusId" })
public class SubmissionPaymentData implements Serializable {

    /**
     * Generated serial version uid of this class.
     */
    private static final long serialVersionUID = 4234558355500444560L;

    /**
     * Submission Identifier.
     */
    private long id;

    /**
     * Rank of the submission.
     */
    private int rank = 0;

    /**
     * Amount at which submission is being purchased.
     */
    private double amount = 0;

    /**
     * Flag representing whether this submission should be awarded a milestone prize or not
     * 
     * @since 1.1
     */
    private boolean awardMilestonePrize = false;

    /**
     * <p>
     * Identifier of the payment-type. This value is set once the submission has
     * been purchased.
     * </p>
     */
    private long paymentTypeId = 0;

    /**
     * <p>
     * Payment reference number. This value is set once the submission has been
     * purchased.
     * </p>
     */
    private String paymentReferenceNumber = null;

    /**
     * Represents payment status id.
     */
    private Long paymentStatusId;

    /**
     * <p>
     * Gets the identifier of this submission.
     * </p>
     *
     * @return the identifier of this submission.
     */
    public long getId() {
        return this.id;
    }

    /**
     * <p>
     * Sets the identifier of this submission.
     * </p>
     *
     * @param id identifier to be set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Rank of this submission. Rank is index 1 based. If this submission is not
     * ranked then rank would be zero.
     * </p>
     *
     * @return 1 index based rank of this submission.
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * <p>
     * Sets the rank of this submission. Rank is index 1 based. If this
     * submission is not ranked then rank would be zero.
     * </p>
     *
     * @param rank 1 index based rank of this submission.
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * <p>
     * Gets the purchase amount of this submission, if any. Amount would be zero
     * if this submission is not purchased.
     * </p>
     *
     * @return purchase amount of this submission.
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * <p>
     * Sets the purchase amount of this submission. Amount would be set to zero
     * if this submission is not purchased.
     * </p>
     *
     * @param amount the purchase amount of this submission.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * <p>
     * Gets the award milestone prize flag
     * </p>
     *
     * @return true if the submission should be awarded a milestone prize, false otherwise
     * 
     * @since 1.1
     */
    public boolean getAwardMilestonePrize() {
        return this.awardMilestonePrize;
    }

    /**
     * <p>
     * Gets the award milestone prize flag
     * </p>
     *
     * @return true if the submission should be awarded a milestone prize, false otherwise
     * 
     * @since 1.1
     */
    public boolean isAwardMilestonePrize() {
        return this.awardMilestonePrize;
    }

    /**
     * <p>
     * Sets the award milestone prize flag
     * </p>
     *
     * @param awardMilestonePrize true if the submission should be awarded a milestone prize, false otherwise
     * 
     * @since 1.1
     */
    public void setAwardMilestonePrize(boolean awardMilestonePrize) {
        this.awardMilestonePrize = awardMilestonePrize;
    }

    /**
     * <p>
     * Gets if this submission is additional purchase or part of the prize. If
     * amount > 0 and rank <= 0, then true, else false/
     * </p>
     *
     * @return if this submission is additional purchase.
     */
    public boolean isAdditionalPurchase() {
        return this.amount > 0 && this.rank <= 0;
    }

    /**
     * <p>
     * Gets if this submission is purchased. If amount > 0, then true else false
     * </p>
     *
     * @return if this submission is purchased.
     */
    public boolean isPurchased() {
        return this.amount > 0;
    }

    /**
     * <p>
     * Gets if this submission is ranked. If rank > 0, then true else false.
     * </p>
     *
     * @return if this submission is ranked.
     */
    public boolean isRanked() {
        return this.rank > 0;
    }

    /**
     * <p>
     * Gets the payment type identifier. This value is set only when the
     * submission has been purchased.
     * </p>
     *
     * @return The payment type identifier.
     */
    public long getPaymentTypeId() {
        return this.paymentTypeId;
    }

    /**
     * <p>
     * Sets the payment type identifier. This value is set only when the
     * submission has been purchased.
     * </p>
     *
     * @param paymentTypeId the payment type identifier.
     */
    public void setPaymentTypeId(long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    /**
     * <p>
     * Gets the payment reference number. This value is set only when the
     * submission has been purchased.
     * </p>
     *
     * @return payment reference number.
     */
    public String getPaymentReferenceNumber() {
        return this.paymentReferenceNumber;
    }

    /**
     * <p>
     * Sets the payment reference number. This value is set only when the
     * submission has been purchased.
     * </p>
     *
     * @param paymentReferenceNumber payment reference number.
     */
    public void setPaymentReferenceNumber(String paymentReferenceNumber) {
        this.paymentReferenceNumber = paymentReferenceNumber;
    }

    /**
     * Returns paymentStatusId.
     *
     * @return the paymentStatusId
     */
    public Long getPaymentStatusId() {
        return paymentStatusId;
    }

    /**
     * Sets paymentStatusId.
     *
     * @param paymentStatusId the paymentStatusId to set
     */
    public void setPaymentStatusId(Long paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }
}
