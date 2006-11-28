/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import java.io.Serializable;

/**
 * <p>An interface that represents summary administrative information. It will include the number of pending sponsors
 * and winners, as well as active games. It is used in the DTO in the AdminData EJBs, but the EJBs do not operate
 * on it, instead letting the DAOs work with them.
 *
 * <p><strong>Thread Safety</strong>: Implementations are expected to be thread-safe.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public interface AdminSummary extends Serializable {
    /**
     * <p>Gets the pending sponsor count.</p>
     *
     * @return the pending sponsor count
     */
    public int getPendingSponsorCount();

    /**
     * <p>Gets the pending winner count.</p>
     *
     * @return the pending winner count
     */
    public int getPendingWinnerCount();

    /**
     * <p>Gets the number of active games.</p>
     *
     * @return the number of active games
     */
    public int getActiveGameCount();
}


