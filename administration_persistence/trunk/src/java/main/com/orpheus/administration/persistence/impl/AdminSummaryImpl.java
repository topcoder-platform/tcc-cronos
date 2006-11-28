/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.orpheus.administration.persistence.AdminSummary;

/**
 * <p>A simple data structure implementation of the <code>AdminSummary</code> interface.
 *
 * <p><strong>Thread Safety</strong>: This class is thread safe by virtue of being immutable.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class AdminSummaryImpl implements AdminSummary {

    /**
     * <p>The number of sponsors whose approval is pending. It is set in the constructor and is immutable. This
     * value is expected to be non-negative but this constraint is not enforced.
     */
    private final int pendingSponsorCount;

    /**
     * <p>The number of pending winners in all games. It is set in the constructor and is immutable. This value is
     * expected to be non-negative but this constraint is not enforced.
     */
    private final int pendingWinnerCount;

    /**
     * <p>The number of active games. It is set in the constructor and is immutable. This value is expected to be
     * non-negative but this constraint is not enforced.
     */
    private final int activeGameCount;

    /**
     * Constructs a new <code>AdminSummaryImpl</code> with the specified attributes.
     *
     * @param pendingSponsorCount the number of pending sponsors
     * @param pendingWinnerCount the number of pending winners
     * @param activeGameCount the number of active games
     */
    public AdminSummaryImpl(int pendingSponsorCount, int pendingWinnerCount, int activeGameCount) {
        this.pendingSponsorCount = pendingSponsorCount;
        this.pendingWinnerCount = pendingWinnerCount;
        this.activeGameCount = activeGameCount;
    }

    /**
     * <p>Gets the pending sponsor count.</p>
     *
     * @return the pending sponsor count
     */
    public int getPendingSponsorCount() {
        return pendingSponsorCount;
    }

    /**
     * <p>Gets the pending winner count.</p>
     *
     * @return the pending winner count
     */
    public int getPendingWinnerCount() {
        return pendingWinnerCount;
    }

    /**
     * <p>Gets the number of active games.</p>
     *
     * @return the number of active games
     */
    public int getActiveGameCount() {
        return activeGameCount;
    }
}
