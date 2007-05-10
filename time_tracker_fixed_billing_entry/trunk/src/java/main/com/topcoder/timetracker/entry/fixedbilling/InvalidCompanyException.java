/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

/**
 * This exception is thrown when there is an attempt to relate a Time Tracker entities to a FixedBillingEntry whose
 * companies do not match. For example, adding a RejectReason to a FixedBillingEntry when their companies are
 * different.
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class InvalidCompanyException extends DataAccessException {

    /**
     * Automatically generated unique ID for use with serialization.
     */
	private static final long serialVersionUID = 1862467743351602029L;

	/**
     * <p>
     * This is the company id of the FixedBillingEntry being associated with an entity.
     * </p>
     */
    private final long fixedBillingEntryCompanyId;

    /**
     * <p>
     * This is the company id of the other Time Tracker entity that was being associated with the time entry.
     * </p>
     */
    private final long otherEntityCompanyId;

    /**
     * <p>
     * Constructor with default message.
     * </p>
     *
     * @param entryId the company id of the entry being associated with an entity.
     * @param otherId the company id of the other Time Tracker entity that was being associated with the time entry.
     */
    public InvalidCompanyException(long entryId, long otherId) {
        this(entryId, otherId, "The companies of entries do not match.");
    }

    /**
     * <p>
     * Constructor with custom message.
     * </p>
     *
     * @param entryId the company id of the entry being associated with an entity.
     * @param otherId the company id of the other Time Tracker entity that was being associated with the time entry.
     * @param message A custom message.
     */
    public InvalidCompanyException(long entryId, long otherId, String message) {
        super(message);
        this.fixedBillingEntryCompanyId = entryId;
        this.otherEntityCompanyId = otherId;
    }

    /**
     * <p>
     * Gets the company id of the FixedBillingEntry being associated with an entity.
     * </p>
     *
     * @return the company id of the entry being associated with an entity.
     */
    public long getFixedBillingEntryCompanyId() {
        return fixedBillingEntryCompanyId;
    }

    /**
     * <p>
     * Gets the company id of the other Time Tracker entity that was being associated with the time entry.
     * </p>
     *
     * @return the company id of the other Time Tracker entity that was being associated with the time entry.
     */
    public long getOtherEntityCompanyId() {
        return otherEntityCompanyId;
    }
}
