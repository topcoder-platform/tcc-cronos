/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.failuretests;

import com.cronos.onlinereview.autoscreening.tool.ScreeningResponse;

import junit.framework.TestCase;

/**
 * Failure tests for <code>ScreeningResponse</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class ScreeningResponseTest extends TestCase {

    /**
     * Represents the response to test.
     */
    private ScreeningResponse response;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig();
        response = new ScreeningResponse();
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
     * Test method for setDetailMessage(java.lang.String).
     * In this case, the message is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetDetailMessage_Null() {
        try {
            response.setDetailMessage(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setDetailMessage(java.lang.String).
     * In this case, the message is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetDetailMessage_Empty() {
        try {
            response.setDetailMessage(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setCreateUser(java.lang.String).
     * In this case, the message is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetCreateUser_Null() {
        try {
            response.setCreateUser(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setCreateUser(java.lang.String).
     * In this case, the message is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetCreateUser_Empty() {
        try {
            response.setCreateUser(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setCreateDate(java.util.Date).
     * In this case, the date is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetCreateDate_Null() {
        try {
            response.setCreateDate(null);
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
            response.setModificationUser(null);
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
            response.setModificationUser(" ");
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
            response.setModificationDate(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
