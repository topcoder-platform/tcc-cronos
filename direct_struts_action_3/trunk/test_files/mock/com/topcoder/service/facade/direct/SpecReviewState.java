/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import java.io.Serializable;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * This class is a container for specification review state data. It is a simple JavaBean (POJO) that provides getters
 * and setters for all private attributes and performs no validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class SpecReviewState implements Serializable {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = 4895806747490931322L;

    /**
     * <p>
     * The date/time when the specification review will expire. Can be any value. Has getter and setter.
     * </p>
     */
    private XMLGregorianCalendar deadline;

    /**
     * <p>
     * The flag indicating whether the specification review is delayed or not. Can be any value. Has getter and
     * setter.
     * </p>
     */
    private boolean delayed;

    /**
     * <p>
     * The amount of hours for review delay. Can be any value. Has getter and setter.
     * </p>
     */
    private int delayedHour;

    /**
     * <p>
     * The status of the spec review. Can be any value. Has getter and setter.
     * </p>
     */
    private SpecReviewStatus status;

    /**
     * <p>
     * Creates an instance of SpecReviewState.
     * </p>
     */
    public SpecReviewState() {
        // Do nothing.
    }

    /**
     * <p>
     * Retrieves the date/time when the specification review will expire.
     * </p>
     *
     * @return the date/time when the specification review will expire
     */
    public XMLGregorianCalendar getDeadline() {
        return deadline;
    }

    /**
     * <p>
     * Sets the date/time when the specification review will expire.
     * </p>
     *
     * @param deadline
     *            the date/time when the specification review will expire
     */
    public void setDeadline(XMLGregorianCalendar deadline) {
        this.deadline = deadline;
    }

    /**
     * <p>
     * Retrieves the flag indicating whether the specification review is delayed or not.
     * </p>
     *
     * @return the flag indicating whether the specification review is delayed or not
     */
    public boolean isDelayed() {
        return delayed;
    }

    /**
     * <p>
     * Sets the flag indicating whether the specification review is delayed or not.
     * </p>
     *
     * @param delayed
     *            the flag indicating whether the specification review is delayed or not
     */
    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }

    /**
     * <p>
     * Retrieves the amount of hours for review delay.
     * </p>
     *
     * @return the amount of hours for review delay
     */
    public int getDelayedHour() {
        return delayedHour;
    }

    /**
     * <p>
     * Sets the amount of hours for review delay.
     * </p>
     *
     * @param delayedHour
     *            the amount of hours for review delay
     */
    public void setDelayedHour(int delayedHour) {
        this.delayedHour = delayedHour;
    }

    /**
     * <p>
     * Retrieves the status of the spec review.
     * </p>
     *
     * @return the status of the spec review
     */
    public SpecReviewStatus getStatus() {
        return status;
    }

    /**
     * <p>
     * Sets the status of the spec review.
     * </p>
     *
     * @param status
     *            the status of the spec review
     */
    public void setStatus(SpecReviewStatus status) {
        this.status = status;
    }
}
