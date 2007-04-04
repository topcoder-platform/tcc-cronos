/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.stresstests;

import com.topcoder.timetracker.audit.persistence.MockContainer;

import junit.framework.TestCase;

/**
 * <p>
 * This class is the stress test for Time Tracker Notification 3.2.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class ValueContainerStressTest extends TestCase {

    /**
     * The looping count for testing
     */
    private static final int[] COUNTS = new int[]{1, 20, 400 };

    /**
     * The start time for the stress test.
     */
    private long startTime = 0;

    /**
     * The end time for the stress test.
     */
    private long endTime = 0;

    /**
     * The value container instance for the stress test.
     */
    private MockContainer container = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        StressTestHelper.clearConfig();
        StressTestHelper.loadConfig();
    }

    /**
     * <p>
     * Clears the test environment.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        StressTestHelper.clearConfig();
    }

    /**
     * <p>
     * This method tests the functionality of container with the valid namespace in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testContainerStressForValidNamespace() throws Exception {
        startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNTS.length; ++i) {
            for (int j = 0; j < COUNTS[i]; ++j) {
                container = new MockContainer();

                assertEquals("The id value should be got properly.", 100, container.getId());
                assertEquals("The newValue value should be got properly.", "newValue", container.getNewValue());
                assertEquals("The newValue value should be got properly.", "oldValue", container.getOldValue());
                assertEquals("The newValue value should be got properly.", "columnName", container.getColumnName());

                container = null;
            }
        }

        endTime = System.currentTimeMillis();
        System.out.println("The stress test for container with valid namespace costs: " + (endTime - startTime)
            + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of empty container in high stress.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testContainerStressForEmptyContainer() throws Exception {
        startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNTS.length; ++i) {
            for (int j = 0; j < COUNTS[i]; ++j) {
                container = new MockContainer(".Empty");

                assertEquals("The id value should be got properly.", -1, container.getId());
                assertEquals("The newValue value should be got properly.", null, container.getNewValue());
                assertEquals("The newValue value should be got properly.", null, container.getOldValue());
                assertEquals("The newValue value should be got properly.", null, container.getColumnName());

                container = null;
            }
        }
        
        endTime = System.currentTimeMillis();
        System.out.println("The stress test for empty container costs: " + (endTime - startTime)
            + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of container with invalid namespace in high stress.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testContainerStressForContainerWithInvalidNamespaceContainer() throws Exception {
        startTime = System.currentTimeMillis();
        
        for (int i = 0; i < COUNTS.length; ++i) {
            for (int j = 0; j < COUNTS[i]; ++j) {
                container = new MockContainer("Invalid");

                assertEquals("The id value should be got properly.", -1, container.getId());
                assertEquals("The newValue value should be got properly.", null, container.getNewValue());
                assertEquals("The newValue value should be got properly.", null, container.getOldValue());
                assertEquals("The newValue value should be got properly.", null, container.getColumnName());

                container = null;
            }
        }
        
        endTime = System.currentTimeMillis();
        System.out.println("The stress test for container with invalid namespace costs: " + (endTime - startTime)
            + " milliseconds.");
    }
}
