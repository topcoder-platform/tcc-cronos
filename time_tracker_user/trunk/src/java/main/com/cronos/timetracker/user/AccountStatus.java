/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.user;

import com.cronos.timetracker.common.TimeTrackerBean;
import com.cronos.timetracker.common.Utils;

/**
 * <p>
 * This bean represents a possible status that may be assigned to a user account.
 * </p>
 *
 * <p>
 * Thread Safety: - This class is mutable, and not thread-safe. Multiple threads are advised to work with their own
 * instance.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class AccountStatus extends TimeTrackerBean {

    /**
     * <p>
     * A human-readable String description of this Status. It may be null when the AccountStatus object is
     * initially constructed, but it may not be set to a null or empty String afterwards.
     * </p>
     *
     *
     */
    private String description;

    /**
     * <p>
     * Default Constructor.
     * </p>
     *
     */
    public AccountStatus() {
        // empty
    }

    /**
     * <p>
     * Retrieves the human-readable String description of this Status. It may be null when the AccountStatus object
     * is initially constructed, but it may not be set to a null or empty String afterwards.
     * </p>
     *
     *
     *
     * @return a human-readable String description of this Status.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets a human-readable String description of this Status. It may be null when the AccountStatus object is
     * initially constructed, but it may not be set to a null or empty String afterwards.
     * </p>
     *
     * @param description a human-readable String description of this Status.
     * @throws IllegalArgumentException if description is null or an empty String.
     */
    public void setDescription(String description) {
        Utils.checkString(description, "description", false);
        if (!description.equals(this.description)) {
            this.description = description;
            setChanged(true);
        }
    }
}
