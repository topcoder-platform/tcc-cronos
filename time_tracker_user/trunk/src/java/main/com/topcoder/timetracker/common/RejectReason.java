/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * This bean represents a possible reason why a time or expense entry may be rejected by the Project Manager. Each
 * company has a different set of policies on which entries will be rejected, so each RejectReason is associated
 * with a company.
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
public class RejectReason extends TimeTrackerBean {

    /**
     * <p>
     * The id of the company with which this RejectReason is associated with.
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
     * Textual description of the RejectReason. It may be null when the RejectReason object is initially
     * constructed, but it may not be set to a null or empty String afterwards.
     * </p>
     * <p>
     * Initialized In: setDescription
     * </p>
     * <p>
     * Modified In: setDescription
     * </p>
     * <p>
     * Accessed In: getDescription
     * </p>
     *
     *
     */
    private String description;

    /**
     * <p>
     * Indicates whether the RejectReason is active or not.
     * </p>
     * <p>
     * Initialized In: setActive
     * </p>
     * <p>
     * Modified In: setActive
     * </p>
     * <p>
     * Accessed In: isActive
     * </p>
     *
     *
     */
    private boolean active;

    /**
     * <p>
     * Default constructor.
     * </p>
     *
     */
    public RejectReason() {
        // your code here
    }

    /**
     * <p>
     * Retrieves the id of the company with which this RejectReason is associated with.
     * </p>
     *
     *
     *
     * @return the id of the company with which this RejectReason is associated with.
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Sets the id of the company with which this RejectReason is associated with.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     *
     * @param companyId the id of the company with which this RejectReason is associated with.
     * @throws IllegalArgumentException if the companyId is <=0.
     */
    public void setCompanyId(long companyId) {
        Utils.checkPositive(companyId, "companyId");
        if (companyId != this.companyId) {
            this.companyId = companyId;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the textual description of the RejectReason. It may be null when the RejectReason object is
     * initially constructed, but it may not be set to a null or empty String afterwards.
     * </p>
     *
     *
     *
     * @return the textual description of the RejectReason.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets the textual description of the RejectReason. It may be null when the RejectReason object is initially
     * constructed, but it may not be set to a null or empty String afterwards.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     * @param description Textual description of the RejectReason.
     * @throws IllegalArgumentException if description is null or an empty String.
     */
    public void setDescription(String description) {
        Utils.checkString(description, "description", false);
        if (!description.equals(this.description)) {
            this.description = description;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Indicates whether the RejectReason is active or not.
     * </p>
     *
     *
     *
     * @return whether the RejectReason is active or not.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * <p>
     * Sets whether the RejectReason is active or not.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     * @param active True if the RejectReason is active; false otherwise.
     */
    public void setActive(boolean active) {
        if (active != this.active) {
            this.active = active;
            setChanged(true);
        }
    }
}
