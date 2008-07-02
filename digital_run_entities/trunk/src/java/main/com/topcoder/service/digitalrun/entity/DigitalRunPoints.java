/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity;

import java.util.Date;

/**
 * <p>
 * The <code>DigitalRunPoints</code> entity. Plus the attributes defined in its base entity, it holds the
 * attributes track, digital run points status, digital run points type, digital run points reference type, digital
 * run points operation, user id, amount, application date, award date, reference id and track.
 * </p>
 *
 * <p>
 * Thread Safety: It's mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunPoints extends BaseEntity {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6879738678940001562L;

    /**
     * Represents the track attribute of the DigitalRunPoints entity. It should be non-null after set.
     */
    private Track track;

    /**
     * Represents the digital run points status attribute of the DigitalRunPoints entity. It should be non-null
     * after set.
     */
    private DigitalRunPointsStatus digitalRunPointsStatus;

    /**
     * Represents the digital run points type attribute of the DigitalRunPoints entity. It should be non-null after
     * set.
     */
    private DigitalRunPointsType digitalRunPointsType;

    /**
     * Represents the digital run points reference type attribute of the DigitalRunPoints entity. It should be
     * non-null after set.
     */
    private DigitalRunPointsReferenceType digitalRunPointsReferenceType;

    /**
     * Represents the digital run points operation attribute of the DigitalRunPoints entity. It should be non-null
     * after set.
     */
    private DigitalRunPointsOperation digitalRunPointsOperation;

    /**
     * Represents the user id attribute of the DigitalRunPoints entity. It should be positive after set.
     */
    private long userId;

    /**
     * Represents the amount attribute of the DigitalRunPoints entity. It should be positive after set.
     */
    private double amount;

    /**
     * Represents the application date attribute of the DigitalRunPoints entity. It should be non-null after set.
     */
    private Date applicationDate;

    /**
     * Represents the award date attribute of the DigitalRunPoints entity. It should be non-null after set.
     */
    private Date awardDate;

    /**
     * Represents the reference id attribute of the DigitalRunPoints entity. It should be positive after set.
     */
    private long referenceId;

    /**
     * Represents the is potential attribute of the DigitalRunPoints entity.
     */
    private boolean potential;

    /**
     * Creates the instance. Empty constructor.
     */
    public DigitalRunPoints() {
        // empty
    }

    /**
     * Get track.
     *
     * @return the track
     */
    public Track getTrack() {
        return track;
    }

    /**
     * Set track.
     *
     * @param track
     *            the track
     */
    public void setTrack(Track track) {
        Helper.checkNull(track, "track");
        this.track = track;
    }

    /**
     * Gets digital run points status.
     *
     * @return the digital run points status
     */
    public DigitalRunPointsStatus getDigitalRunPointsStatus() {
        return digitalRunPointsStatus;
    }

    /**
     * Sets digital run points status.
     *
     * @param digitalRunPointsStatus
     *            the digital run points status
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setDigitalRunPointsStatus(DigitalRunPointsStatus digitalRunPointsStatus) {
        Helper.checkNull(digitalRunPointsStatus, "digitalRunPointsStatus");
        this.digitalRunPointsStatus = digitalRunPointsStatus;
    }

    /**
     * Gets digital run points type.
     *
     * @return the digital run points type
     */
    public DigitalRunPointsType getDigitalRunPointsType() {
        return digitalRunPointsType;
    }

    /**
     * Sets digital run points type.
     *
     * @param digitalRunPointsType
     *            the digital run points type
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setDigitalRunPointsType(DigitalRunPointsType digitalRunPointsType) {
        Helper.checkNull(digitalRunPointsType, "digitalRunPointsType");
        this.digitalRunPointsType = digitalRunPointsType;
    }

    /**
     * Gets digital run points reference type.
     *
     * @return the digital run points reference type
     */
    public DigitalRunPointsReferenceType getDigitalRunPointsReferenceType() {
        return digitalRunPointsReferenceType;
    }

    /**
     * Sets digital run points reference type.
     *
     * @param digitalRunPointsReferenceType
     *            the digital run points reference type
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setDigitalRunPointsReferenceType(DigitalRunPointsReferenceType digitalRunPointsReferenceType) {
        Helper.checkNull(digitalRunPointsReferenceType, "digitalRunPointsReferenceType");
        this.digitalRunPointsReferenceType = digitalRunPointsReferenceType;
    }

    /**
     * Gets digital run points operation.
     *
     * @return the digital run points operation
     */
    public DigitalRunPointsOperation getDigitalRunPointsOperation() {
        return digitalRunPointsOperation;
    }

    /**
     * Sets digital run points operation.
     *
     * @param digitalRunPointsOperation
     *            the digital run points operation
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setDigitalRunPointsOperation(DigitalRunPointsOperation digitalRunPointsOperation) {
        Helper.checkNull(digitalRunPointsOperation, "digitalRunPointsOperation");
        this.digitalRunPointsOperation = digitalRunPointsOperation;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId
     *            the user id
     * @throws IllegalArgumentException
     *             if the argument is not positive
     */
    public void setUserId(long userId) {
        Helper.checkPositive(userId, "userId");
        this.userId = userId;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount
     *            the amount
     * @throws IllegalArgumentException
     *             if the argument is not positive
     */
    public void setAmount(double amount) {
        Helper.checkPositive(amount, "amount");
        this.amount = amount;
    }

    /**
     * Gets application date.
     *
     * @return the application date
     */
    public Date getApplicationDate() {
        return applicationDate;
    }

    /**
     * Sets application date.
     *
     * @param applicationDate
     *            the application date
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setApplicationDate(Date applicationDate) {
        Helper.checkNull(applicationDate, "applicationDate");
        this.applicationDate = applicationDate;
    }

    /**
     * Gets award date.
     *
     * @return the award date
     */
    public Date getAwardDate() {
        return awardDate;
    }

    /**
     * Sets award date.
     *
     * @param awardDate
     *            the award date
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setAwardDate(Date awardDate) {
        Helper.checkNull(awardDate, "awardDate");
        this.awardDate = awardDate;
    }

    /**
     * Gets reference id.
     *
     * @return the reference id
     */
    public long getReferenceId() {
        return referenceId;
    }

    /**
     * Sets reference id.
     *
     * @param referenceId
     *            the reference id
     * @throws IllegalArgumentException
     *             if the argument is not positive
     */
    public void setReferenceId(long referenceId) {
        Helper.checkPositive(referenceId, "referenceId");
        this.referenceId = referenceId;
    }

    /**
     * Gets potential flag.
     *
     * @return the boolean
     */
    public boolean isPotential() {
        return potential;
    }

    /**
     * Sets is potential.
     *
     * @param isPotential
     *            the is potential
     */
    public void setPotential(boolean isPotential) {
        this.potential = isPotential;
    }
}
