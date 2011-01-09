/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.stresstests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.management.project.ApplicationsManagerImpl;
import com.topcoder.management.project.ReviewApplication;

/**
 * <p>
 * Stress tests for the ApplicationsManagerImpl class.
 * </p>
 *
 * @author morehappiness
 * @version 1.1
 */
public class ApplicationsManagerImplStressTest extends TestCase {
    /**
     * Represents the ApplicationsManagerImpl instance used for testing.
     */
    private ApplicationsManagerImpl manager = null;

    /**
     * Set up the test fixture.
     *
     * @throws Exception
     *             any exception to JUnit.
     */
    protected void setUp() throws Exception {
        StressHelper.unloadConfig();
        StressTestHelper.addConfig();
        manager = new ApplicationsManagerImpl(ApplicationsManagerImpl.class.getName());
        StressTestHelper.clearAllTestRecords();
    }

    /**
     * Tear down the test fixture.
     *
     * @throws Exception
     *             any exception to JUnit.
     */
    protected void tearDown() throws Exception {
        StressTestHelper.clearAllTestRecords();
        StressTestHelper.clearConfig();
    }

    /**
     * Stress Test on the persistent methods for 100 times.
     *
     * @throws Exception
     *             any exception to JUnit.
     */
    public void testStress() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            StressTestHelper.clearAllTestRecords();

            ReviewApplication ra = new ReviewApplication();

            // set properties of ReviewApplication
            ra.setApplicationDate(new Date());
            ra.setReviewerId(19);
            ra.setProjectId(1);
            ra.setAcceptPrimary(true);

            // create ReviewApplication
            ra = manager.create(ra);
            assertEquals("Should be the same", true, ra.getId() > 0);

            // retrieve ReviewApplication
            assertEquals("Should be the same", ra.getId(), manager.retrieve(ra.getId()).getId());

            // delete ReviewApplication
            assertEquals("Should be the same", true, manager.delete(ra.getId()));

            for (int j = 0; j < 100; j++) {
                ra = new ReviewApplication();

                // set properties of ReviewApplication
                ra.setApplicationDate(new Date());
                ra.setReviewerId(j + 1);
                ra.setProjectId(2);
                ra.setAcceptPrimary(true);

                manager.create(ra);
            }

            // get primary applications
            assertEquals("Should be the same", 100, manager.getPrimaryApplications(ra.getProjectId()).length);

            for (int j = 0; j < 100; j++) {
                ra = new ReviewApplication();

                // set properties of ReviewApplication
                ra.setApplicationDate(new Date());
                ra.setReviewerId(j + 1);
                ra.setProjectId(3);
                ra.setAcceptPrimary(false);

                manager.create(ra);
            }
            // get secondary applications
            assertEquals("Should be the same", 100, manager.getSecondaryApplications(ra.getProjectId()).length);

            // get all
            assertEquals("Should be the same", 100, manager.getAllApplications(ra.getProjectId()).length);
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("ApplicationsManagerImpl#createProject(Project, String) tested for 100 times, " + "takes "
            + Long.toString(dure) + " ms.");
    }

}
