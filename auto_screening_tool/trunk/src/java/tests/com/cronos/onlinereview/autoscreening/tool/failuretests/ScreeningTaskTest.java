/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.failuretests;

import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;

import junit.framework.TestCase;

/**
 * Failure tests for <code>ScreeningTask</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class ScreeningTaskTest extends TestCase {

    /**
     * Represents the task.
     */
    private ScreeningTask task;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig();
        task = new ScreeningTask();
    }

    /**
     * Clean up the environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }

    /**
     * Test method for setCreationDate(java.util.Date).
     * In this case, the date is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetCreationDate() {
        try {
            task.setCreationDate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setCreationUser(java.lang.String).
     * In this case, the user is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetCreationUser_Null() {
        try {
            task.setCreationUser(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setCreationUser(java.lang.String).
     * In this case, the user is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetCreationUser_Empty() {
        try {
            task.setCreationUser(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setModificationDate(java.util.Date).
     * In this case, the date is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetModificationDate() {
        try {
            task.setModificationDate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setModificationUser(java.lang.String).
     * In this case, the user is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetModificationUser_Null() {
        try {
            task.setModificationUser(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setModificationUser(java.lang.String).
     * In this case, the user is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetModificationUser_Empty() {
        try {
            task.setModificationUser(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setScreeningStatus(ScreeningStatus).
     * In this case, the status is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetScreeningStatus() {
        try {
            task.setScreeningStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setScreeningData(ScreeningData).
     * In this case, the status is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetScreeningData() {
        try {
            task.setScreeningData(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
