/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests;

import junit.framework.TestCase;

import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.beans.ProjectStatusServiceBean;

/**
 * Failure tests for {@link ProjectStatusServiceBean}.
 *
 * @author iRabbit
 * @version 1.0
 */
public class ProjectStatusServiceBeanFailureTests extends TestCase {

    /**
     * {@link ProjectStatusServiceBean} used in testing.
     */
    private ProjectStatusServiceBean instance = new ProjectStatusServiceBean();

    /**
     * Failure test for {@link ProjectStatusServiceBean#createProjectStatus(ProjectStatus)}.
     *
     * @throws Exception to junit
     */
    public void testCreateProjectStatus_Failure1() throws Exception {
        try {
            instance.createProjectStatus(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link ProjectStatusServiceBean#createProjectStatus(ProjectStatus)}.
     *
     * @throws Exception to junit
     */
    public void testCreateProjectStatus_Failure2() throws Exception {
        try {
            instance.createProjectStatus(new ProjectStatus());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link ProjectStatusServiceBean#updateProjectStatus(ProjectStatus)}.
     *
     * @throws Exception to junit
     */
    public void testUpdateProjectStatus_Failure1() throws Exception {
        try {
            instance.updateProjectStatus(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link ProjectStatusServiceBean#updateProjectStatus(ProjectStatus)}.
     *
     * @throws Exception to junit
     */
    public void testUpdateProjectStatus_Failure2() throws Exception {
        try {
            instance.updateProjectStatus(new ProjectStatus());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * Failure test for {@link ProjectStatusServiceBean#deleteProjectStatus(ProjectStatus)}.
     *
     * @throws Exception to junit
     */
    public void testDeleteProjectStatus_Failure1() throws Exception {
        try {
            instance.deleteProjectStatus(null);
            fail("IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for {@link ProjectStatusServiceBean#deleteProjectStatus(ProjectStatus)}.
     *
     * @throws Exception to junit
     */
    public void testDeleteProjectStatus_Failure2() throws Exception {
        try {
            instance.deleteProjectStatus(new ProjectStatus());
            fail("ISE is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }
}
