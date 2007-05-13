/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import com.topcoder.timetracker.common.TimeTrackerBean;

/**
 * <p>
 * This bean represents an email template that is sent to the contractor when a time or expense entry has been rejected
 * by the Project Manager. Each company has a different email template.
 * </p>
 *
 * <p>
 * This class is the ValueObject of the DAO pattern.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is mutable, and not thread-safe. Multiple threads are advised to work with their
 * own instance.
 * </p>
 *
 * @author wangqing, TCSDEVELOPER
 * @version 3.2
 */
public class RejectEmail extends TimeTrackerBean {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -3236076887414125898L;

	/**
     * <p>
     * The id of the company with which this RejectEmail is associated with. It will be a positive after set.
     * </p>
     */
    private long companyId = -1;

    /**
     * <p>
     * The body template of the RejectEmail. It may be null when the RejectEmailobject is initially constructed, but it
     * may not be set to a null or empty String afterwards.
     * </p>
     */
    private String body = null;

    /**
     * <p>
     * Creates a new RejectEmail instance.
     * </p>
     */
    public RejectEmail() {
        // Empty.
    }

    /**
     * <p>
     * Retrieves the id of the company with which this RejectEmail is associated with. And its return value is &gt;= 0.
     * </p>
     *
     * @return the id of the company with which this RejectEmail is associated with, it must be &gt;= 0
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Sets the id of the company with which this RejectEmail is associated with.
     * </p>
     *
     * @param companyId the id of the company with which this RejectEmail is associated with.
     *
     * @throws IllegalArgumentException if the companyId is &lt;=0.
     */
    public void setCompanyId(long companyId) {
        if (companyId <= 0) {
            throw new IllegalArgumentException("The companyId must be a positive integer.");
        }

        if (this.companyId != companyId) {
            this.companyId = companyId;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the body template of the RejectEmail. It may be null when the RejectEmailobject is initially
     * constructed, but it will not be a null or empty String after set.
     * </p>
     *
     * @return the body template of the RejectEmail.
     */
    public String getBody() {
        return body;
    }

    /**
     * <p>
     * Sets the body template of the RejectEmail.
     * </p>
     *
     * @param body the body template of the RejectEmail.
     *
     * @throws IllegalArgumentException if the body is a null or empty String.
     */
    public void setBody(String body) {
        if (body == null) {
            throw new IllegalArgumentException("The body is null.");
        }

        if (body.trim().length() == 0) {
            throw new IllegalArgumentException("The body is empty.");
        }

        if (!body.equals(this.body)) {
            this.body = body;
            setChanged(true);
        }
    }
}
