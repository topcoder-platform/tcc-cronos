/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.common;

/**
 * <p>
 * This bean represents an email template that is sent to the contractor when a time or expense entry has been
 * rejected by the Project Manager. Each company has a different email template.
 * </p>
 * <p>
 * Thread Safety: - This class is mutable, and not thread-safe. Multiple threads are advised to work with their own
 * instance.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class RejectEmail extends TimeTrackerBean {

    /**
     * <p>
     * The id of the company with which this RejectEmail is associated with.
     * </p>
     * <p>
     * Initialized In: setCompanyId
     * </p>
     * <p>
     * Modified In: setCompanyId
     * </p>
     * <p>
     * Accessed In: getCompanyId
     * </p>
     *
     *
     */
    private long companyId;

    /**
     * <p>
     * The body template of the RejectEmail. It may be null when the RejectEmailobject is initially constructed,
     * but it may not be set to a null or empty String afterwards.
     * </p>
     * <p>
     * Initialized In: setBody
     * </p>
     * <p>
     * Modified In: setBody
     * </p>
     * <p>
     * Accessed In: getBody
     * </p>
     *
     *
     */
    private String body;

    /**
     * <p>
     * Default constructor.
     * </p>
     *
     */
    public RejectEmail() {
        // your code here
    }

    /**
     * <p>
     * Retrieves the id of the company with which this RejectEmail is associated with.
     * </p>
     *
     *
     *
     * @return the id of the company with which this RejectEmail is associated with.
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Sets the id of the company with which this RejectEmail is associated with.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     * @param companyId the id of the company with which this RejectEmail is associated with.
     * @throws IllegalArgumentException if the companyId is <=0.
     */
    public void setCompanyId(long companyId) {
        Utils.checkPositive(companyId, "companyId");
        if (this.companyId != companyId) {
            this.companyId = companyId;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the body template of the RejectEmail. It may be null when the RejectEmailobject is initially
     * constructed, but it may not be set to a null or empty String afterwards.
     * </p>
     *
     *
     *
     * @return the body template of the RejectEmail.
     */
    public String getBody() {
        return body;
    }

    /**
     * <p>
     * Sets the body template of the RejectEmail. It may be null when the RejectEmailobject is initially
     * constructed, but it may not be set to a null or empty String afterwards.
     * </p>
     *
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     * @param body the body template of the RejectEmail.
     * @throws IllegalArgumentException if the body is a null or empty String.
     */
    public void setBody(String body) {
        Utils.checkString(body, "body", false);
        if (!body.equals(this.body)) {
            this.body = body;
            setChanged(true);
        }
    }
}
