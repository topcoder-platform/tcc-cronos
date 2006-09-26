/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.failuretests;

import com.cronos.onlinereview.autoscreening.tool.ScreeningData;

import junit.framework.TestCase;

/**
 * Failure tests for <code>ScreeningData</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class ScreeningDataTest extends TestCase {

    /**
     * Represents the data.
     */
    ScreeningData data;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig();
        data = new ScreeningData();
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
     * Test method for setFileIdentifier(java.lang.String).
     * In this case, the identifier is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetFileIdentifier_Null() {
        try {
            data.setFileIdentifier(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setFileIdentifier(java.lang.String).
     * In this case, the identifier is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetFileIdentifier_Empty() {
        try {
            data.setFileIdentifier(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setSubmitterAlternativeEmails(java.lang.String[]).
     * In this case, the emails is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetSubmitterAlternativeEmails_Null() {
        try {
            data.setSubmitterAlternativeEmails(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setSubmitterAlternativeEmails(java.lang.String[]).
     * In this case, the emails contains null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetSubmitterAlternativeEmails_ContainsNull() {
        try {
            data.setSubmitterAlternativeEmails(new String[1]);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setSubmitterAlternativeEmails(java.lang.String[]).
     * In this case, the emails contains empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetSubmitterAlternativeEmails_ContainsEmpty() {
        try {
            data.setSubmitterAlternativeEmails(new String[] {" "});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setSubmitterEmail(java.lang.String).
     * In this case, the email is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetSubmitterEmail_Null() {
        try {
            data.setSubmitterEmail(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setSubmitterEmail(java.lang.String).
     * In this case, the email is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetSubmitterEmail_Empty() {
        try {
            data.setSubmitterEmail(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setSubmitterFirstName(java.lang.String).
     * In this case, the email is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetSubmitterFirstName_Null() {
        try {
            data.setSubmitterFirstName(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setSubmitterFirstName(java.lang.String).
     * In this case, the email is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetSubmitterFirstName_Empty() {
        try {
            data.setSubmitterFirstName(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setSubmitterHandle(java.lang.String).
     * In this case, the handle is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetSubmitterHandle_Null() {
        try {
            data.setSubmitterHandle(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setSubmitterHandle(java.lang.String).
     * In this case, the handle is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetSubmitterHandle_Emtpy() {
        try {
            data.setSubmitterHandle(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setSubmitterLastName(java.lang.String).
     * In this case, the handle is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetSubmitterLastName_Null() {
        try {
            data.setSubmitterLastName(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for setSubmitterLastName(java.lang.String).
     * In this case, the handle is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testSetSubmitterLastName_Empty() {
        try {
            data.setSubmitterLastName(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
