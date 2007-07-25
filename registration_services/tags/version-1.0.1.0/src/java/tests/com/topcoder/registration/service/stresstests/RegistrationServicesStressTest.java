/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.stresstests;

import java.io.File;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.RegistrationPosition;
import com.topcoder.registration.service.RegistrationResult;
import com.topcoder.registration.service.RemovalResult;
import com.topcoder.registration.service.impl.RegistrationInfoImpl;
import com.topcoder.registration.service.impl.RegistrationServicesImpl;

import junit.framework.TestCase;

/**
 * <p>
 * This class is the stress test for Registration Services 1.0.
 * </p>
 *
 * @author Hacker_QC
 * @version 1.0
 */
public class RegistrationServicesStressTest extends TestCase {

    /**
     * The looping count for testing
     */
    private static final int COUNT = 200;

    /**
     * The start time for the stress test.
     */
    private long startTime = 0;

    /**
     * The end time for the stress test.
     */
    private long endTime = 0;

    /**
     * <p>
     * Represents an instance of <code>RegistrationServicesImpl</code> class.
     * </p>
     */
    private RegistrationServicesImpl services = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        StressTestHelper.clearConfig();
        StressTestHelper.loadXMLConfig("test_files\\stresstests\\config.xml");
        services = new RegistrationServicesImpl();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        StressTestHelper.clearConfig();
        services = null;
    }

    /**
     * <p>
     * This method tests the functionality of registering for project in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRegistrationServicesStressForRegistering() throws Exception {
        startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            RegistrationInfo registrationInfo = new RegistrationInfoImpl(11, 3, 2);
            RegistrationResult result = services.registerForProject(registrationInfo);
        }
        endTime = System.currentTimeMillis();
        System.out.println("The stress test for registering for project costs: " + (endTime - startTime)
            + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of validating registration in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRegistrationServicesStressForValidating() throws Exception {
        startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            RegistrationInfo registrationInfo = new RegistrationInfoImpl(11, 3, 2);
            RegistrationResult result2 = services.validateRegistration(registrationInfo);
        }

        endTime = System.currentTimeMillis();
        System.out.println("The stress test for validating registration costs: " + (endTime - startTime)
            + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of removing registration in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRegistrationServicesStressForRemoving() throws Exception {
        startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            RegistrationInfo registrationInfo = new RegistrationInfoImpl(11, 3, 2);
            RegistrationInfo retrievedRegistrationInfo = services.getRegistration(3, 11);
            RegistrationResult result3 = services.registerForProject(new RegistrationInfoImpl(11, 3, 1));
            RemovalResult result4 = services.removeRegistration(new RegistrationInfoImpl(1, 3, 1), 4);
        }

        endTime = System.currentTimeMillis();
        System.out.println("The stress test for removing registration costs: " + (endTime - startTime)
            + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of getting registered resources in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRegistrationServicesStressForGettingRegisteredResources() throws Exception {
        startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            ProjectCategory category = new ProjectCategory(1, "DesignCategory", new ProjectType(1, "JAVA"));
            RegistrationPosition[] positions = services.findAvailableRegistrationPositions(category);
            Project[] projects = services.getRegisteredProjects(1);
            Resource[] resources = services.getRegisteredResources(10);
        }

        endTime = System.currentTimeMillis();
        System.out.println("The stress test for getting registered resource costs: " + (endTime - startTime)
            + " milliseconds.");
    }
}
