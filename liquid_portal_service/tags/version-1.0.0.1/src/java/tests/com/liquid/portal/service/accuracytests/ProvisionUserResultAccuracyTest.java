/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service.accuracytests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.liquid.portal.service.ProvisionUserResult;
import com.topcoder.clients.model.Project;
import com.topcoder.service.project.ProjectData;

/**
 * Accuracy tests for {@link ProvisionUserResult}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProvisionUserResultAccuracyTest extends TestCase {
    /**
     * <p>
     * Represents the ProvisionUserResult. Just for test.
     * </p>
     */
    private ProvisionUserResult instance;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
        instance = new ProvisionUserResult();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void tearDown() throws Exception {
        instance = null;
        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test for constructor. It verifies the new instance is created.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("should not be null", instance);
        assertTrue("should be true", instance instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for setter and getter for billingProjects filed.
     * </p>
     */
    public void testSetterAndGetterFor_billingProjects() {
        List<Project> billingProjects = new ArrayList<Project>();
        instance.setBillingProjects(billingProjects);
        assertTrue("should be true", instance.getBillingProjects().equals(billingProjects));
    }

    /**
     * <p>
     * Accuracy test for setter and getter for cockpitProjects filed.
     * </p>
     */
    public void testSetterAndGetterFor_cockpitProjects() {
        List<ProjectData> cockpitProjects = new ArrayList<ProjectData>();
        instance.setCockpitProjects(cockpitProjects);
        assertTrue("should be true", instance.getCockpitProjects().equals(cockpitProjects));
    }
}