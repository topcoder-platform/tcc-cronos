/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker.failuretests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.management.deliverable.latetracker.processors.EmailSendingException;
import com.topcoder.management.deliverable.latetracker.processors.EmailSendingUtility;


/**
 * Failure test cases <code>EmailSendingUtility</code>.
 *
 * @author gjw99
 * @version 1.0
 */
public class EmailSendingUtilityFailureTests extends TestCase {
    /** Represents the instance to be tested. */
    private EmailSendingUtility instance;

    /**
     * Setup the test environment.
     */
    public void setUp() {
        this.instance = new EmailSendingUtility("user1", null);
    }

    /**
     * Tear down the environment
     */
    public void tearDown() {
        this.instance = null;
    }

    /**
     * Test EmailSendingUtility.
     *
     * @throws Exception if any error
     */
    public void test_EmailSendingUtility1() throws Exception {
        try {
            new EmailSendingUtility(null, null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test EmailSendingUtility.
     *
     * @throws Exception if any error
     */
    public void test_EmailSendingUtility2() throws Exception {
        try {
            new EmailSendingUtility(" ", null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test sendEmail.
     *
     * @throws Exception if any error
     */
    public void test_sendEmail1() throws Exception {
        try {
        	Map<String, String> params = new HashMap<String, String>();
            instance.sendEmail(null, "bodyTemplatePath", "user@tc.com", params);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test sendEmail.
     *
     * @throws Exception if any error
     */
    public void test_sendEmail2() throws Exception {
        try {
        	Map<String, String> params = new HashMap<String, String>();
            instance.sendEmail("subjectTemplateText", null, "user@tc.com", params);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test sendEmail.
     *
     * @throws Exception if any error
     */
    public void test_sendEmail3() throws Exception {
        try {
        	Map<String, String> params = new HashMap<String, String>();
            instance.sendEmail("subjectTemplateText", " ", "user@tc.com", params);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test sendEmail.
     *
     * @throws Exception if any error
     */
    public void test_sendEmail4() throws Exception {
        try {
        	Map<String, String> params = new HashMap<String, String>();
            instance.sendEmail("subjectTemplateText", "bodyTemplatePath", null, params);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test sendEmail.
     *
     * @throws Exception if any error
     */
    public void test_sendEmail5() throws Exception {
        try {
        	Map<String, String> params = new HashMap<String, String>();
            instance.sendEmail("subjectTemplateText", "bodyTemplatePath", " ", params);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test sendEmail.
     *
     * @throws Exception if any error
     */
    public void test_sendEmail6() throws Exception {
        try {
            instance.sendEmail("subjectTemplateText", "bodyTemplatePath", "user@tc.com", null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test sendEmail.
     *
     * @throws Exception if any error
     */
    public void test_sendEmail7() throws Exception {
        try {
        	Map<String, String> params = new HashMap<String, String>();
        	params.put(null, "failure");
            instance.sendEmail("subjectTemplateText", "bodyTemplatePath", "user@tc.com", params);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test sendEmail.
     *
     * @throws Exception if any error
     */
    public void test_sendEmail8() throws Exception {
        try {
        	Map<String, String> params = new HashMap<String, String>();
        	params.put(" ", "failure");
            instance.sendEmail("subjectTemplateText", "bodyTemplatePath", "user@tc.com", params);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test sendEmail.
     *
     * @throws Exception if any error
     */
    public void test_sendEmail9() throws Exception {
        try {
        	Map<String, String> params = new HashMap<String, String>();
        	params.put("failure", null);
            instance.sendEmail("subjectTemplateText", "bodyTemplatePath", "user@tc.com", params);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test sendEmail.
     *
     * @throws Exception if any error
     */
    public void test_sendEmai20() throws Exception {
        try {
        	Map<String, String> params = new HashMap<String, String>();
            instance.sendEmail("subjectTemplateText", "bodyTemplatePath", "user@tc.com", params);
            fail("EmailSendingException should be thrown.");
        } catch (EmailSendingException e) {
            // pass
        }
    }
}
