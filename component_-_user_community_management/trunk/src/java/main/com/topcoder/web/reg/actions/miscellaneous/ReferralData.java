/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.Date;

/**
 * This class is a container for information about a single referred coder. It is a simple JavaBean (POJO) that
 * provides getters and setters for all private attributes and performs no argument validation in the setters. This
 * class is used to pass referral data from Struts action to JSP.
 * <p>
 * Thread Safety:
 * This class is mutable and not thread safe.
 *
 * @author saarixx, mumujava
 * @version 1.0
 */
public class ReferralData {
    /**
     * The ID of the referred coder. Can be any value. Has getter and setter.
     */
    private long coderId;

    /**
     * The rating of the referred coder. Can be any value. Has getter and setter.
     */
    private int rating;

    /**
     * The handle of the referred coder. Can be any value. Has getter and setter.
     */
    private String handle;

    /**
     * The registration date of the referred coder. Can be any value. Has getter and setter.
     */
    private Date memberSince;

    /**
     * Creates an instance of ReferralData.
     */
    public ReferralData() {
    }

    /**
     * Retrieves the ID of the referred coder.
     *
     * @return the ID of the referred coder
     */
    public long getCoderId() {
        return coderId;
    }

    /**
     * Sets the ID of the referred coder.
     *
     * @param coderId the ID of the referred coder
     */
    public void setCoderId(long coderId) {
        this.coderId = coderId;
    }

    /**
     * Retrieves the rating of the referred coder.
     *
     * @return the rating of the referred coder
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating of the referred coder.
     *
     * @param rating the rating of the referred coder
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Retrieves the handle of the referred coder.
     *
     * @return the handle of the referred coder
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the handle of the referred coder.
     *
     * @param handle the handle of the referred coder
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Retrieves the registration date of the referred coder.
     *
     * @return the registration date of the referred coder
     */
    public Date getMemberSince() {
        return memberSince;
    }

    /**
     * Sets the registration date of the referred coder.
     *
     * @param memberSince the registration date of the referred coder
     */
    public void setMemberSince(Date memberSince) {
        this.memberSince = memberSince;
    }
}
