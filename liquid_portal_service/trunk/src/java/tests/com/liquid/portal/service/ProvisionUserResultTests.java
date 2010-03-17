/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.clients.model.Project;
import com.topcoder.service.project.ProjectData;

/**
 * <p>
 * UnitTest cases of the <code>ProvisionUserResult</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProvisionUserResultTests extends TestCase {
    /**
     * <p>
     * Represents the instance of <code>ProvisionUserResult</code>.
     * </p>
     */
    private ProvisionUserResult result;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        result = new ProvisionUserResult();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {

    }

    /**
     * <p>
     * Accuracy test case for <code>getCockpitProjects()</code>
     * and <code>getCockpitProjects(List&lt;ProjectData&gt;)</code>.
     * </p>
     */
    public void testSetGetCockpitProjects() {
        result.setCockpitProjects(null);
        assertNull(result.getCockpitProjects());
        List<ProjectData> list = new ArrayList<ProjectData>();
        result.setCockpitProjects(list);
        assertEquals(list, result.getCockpitProjects());
    }

    /**
     * <p>
     * Accuracy test case for <code>getBillingProjects</code>
     * and <code>setBillingProjects(List&lt;Project&gt;)</code>.
     * </p>
     */
    public void testBillingProjects() {
        result.setBillingProjects(null);
        assertNull(result.getBillingProjects());
        List<Project> list = new ArrayList<Project>();
        result.setBillingProjects(list);
        assertEquals(list, result.getBillingProjects());
    }
}
