/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.rejectreason.RejectEmail;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy tests for <code>RejectEmail</code>.
 * </p>
 *
 * @author kzhu
 * @version 3.2
 */
public class RejectEmailAccuracyTests extends TestCase {
    /** Represents private instance used for test. */
    private RejectEmail re = null;

    /**
     * Setup the environment.
     */
    protected void setUp() {
        re = new RejectEmail();
    }

    /**
     * <p>
     * Accuracy test for constructor.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testInheritanceAccuracy() throws Exception {
        assertTrue("RejectEmail should inherit from TimeTrackerBean.", re instanceof TimeTrackerBean);
    }

    /**
     * <p>
     * Accuracy test for method Constructor.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("The instance should be created.", re);
    }

    /**
     * <p>
     * Accuracy test for method <code>getCompanyId</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetCompanyIdAccuracy() throws Exception {
        re.setCompanyId(2);

        assertEquals("The id retrieve should be 2.", 2, re.getCompanyId());
    }

    /**
     * <p>
     * Accuracy test for method <code>setCompanyId</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetCompanyIdAccuracy() throws Exception {
        re.setCompanyId(2);

        assertTrue("The bean has been changed.", re.isChanged());
        assertEquals("The company id is 2.", 2, re.getCompanyId());
    }

    /**
     * <p>
     * Accuracy test for method <code>getBody</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetBodyAccuracy() throws Exception {
        re.setBody("This is body.");

        assertEquals("The body should be set.", "This is body.", re.getBody());
    }

    /**
     * <p>
     * Accuracy test for method <code>setBody</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetBodyAccuracy() throws Exception {
        re.setBody("This is body.");

        assertTrue("The bean has been changed.", re.isChanged());
        assertEquals("The body should be set.", "This is body.", re.getBody());
    }
}
