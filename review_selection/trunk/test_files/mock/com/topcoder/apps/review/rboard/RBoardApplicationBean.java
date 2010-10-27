/*
 * Copyright (C) 2004 - 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.apps.review.rboard;

import java.sql.Timestamp;

/**
 * <p>An mocking implementation of the <code>RBoard EJB</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0.13
 */
public class RBoardApplicationBean {
    /**
     * For every active project a reviewer has, they get a compounded delay to sign up to new projects that is equal to
     * this value in milliseconds.
     */
    public static final int APPLICATION_DELAY_PER_ACTIVE_PROJECT = 6 * 60 * 60 * 1000;

    public void RBoardApplicationBean() {
    }

    public void createRBoardApplication(String dataSource, long userId,
        long projectId, int reviewRespId, int phaseId, Timestamp opensOn,
        int reviewTypeId, boolean primary) throws RBoardRegistrationException {
    }

    /**
     * Retrieves the time a reviewer should wait before being able to apply to a project.
     *
     * For every active project (defined as a project the reviewer has signed up for that is active and without a
     * finished review), the reviewer gets a compounded delay of APPLICATION_DELAY_PER_ACTIVE_PROJECT.
     *
     * @param dataSource the datasource being used
     * @param userId the user id to inspect
     * @return the time to wait in milliseconds
     */
    public long getApplicationDelay(String dataSource, long userId) {
        return 0;
    }
}
