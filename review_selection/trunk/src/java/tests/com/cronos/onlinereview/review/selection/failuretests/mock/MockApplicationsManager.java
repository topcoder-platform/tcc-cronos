/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.failuretests.mock;

import com.topcoder.management.project.ApplicationsManager;
import com.topcoder.management.project.ApplicationsManagerException;
import com.topcoder.management.project.ReviewApplication;

/**
 * Mock implementation of ApplicationsManager.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockApplicationsManager implements ApplicationsManager {

    /**
     * Empty method.
     *
     * @param reviewApplication
     *            the reviewApplication.
     * @return null.
     */
    public ReviewApplication create(ReviewApplication reviewApplication) {
        return null;
    }

    /**
     * Empty method.
     *
     * @param id
     *            the id.
     * @return false.
     */
    public boolean delete(long id) {
        return false;
    }

    /**
     * Throw Exception.
     *
     * @param projectId
     *            the project id.
     *
     * @throws ApplicationsManagerException
     *             always.
     */
    public ReviewApplication[] getAllApplications(long projectId) throws ApplicationsManagerException {
        throw new ApplicationsManagerException("");
    }

    /**
     * Empty method.
     *
     * @param projectId
     *            the project id.
     *
     * @return null.
     */
    public ReviewApplication[] getPrimaryApplications(long projectId) {
        return null;
    }

    /**
     * Empty method.
     *
     * @param projectId
     *            the project id.
     *
     * @return null.
     */
    public ReviewApplication[] getSecondaryApplications(long projectId) {
        return null;
    }

    /**
     * Empty method.
     *
     * @param id
     *            the id.
     *
     * @return null.
     */
    public ReviewApplication retrieve(long id) {
        return null;
    }

    /**
     * Empty method.
     *
     * @param reviewApplication
     *            the review application.
     * @return null.
     */
    public ReviewApplication update(ReviewApplication reviewApplication) {
        return null;
    }
}
