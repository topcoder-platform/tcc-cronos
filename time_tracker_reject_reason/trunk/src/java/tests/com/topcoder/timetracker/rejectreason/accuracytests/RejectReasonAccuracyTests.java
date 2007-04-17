/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.rejectreason.RejectReason;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy tests for <code>RejectReason</code>.
 * </p>
 *
 * @author kzhu
 * @version 3.2
 */
public class RejectReasonAccuracyTests extends TestCase {
    /** Represents private instance used for test. */
    private RejectReason rr = null;

    /**
     * Setup the environment.
     */
    protected void setUp() {
        rr = new RejectReason();
    }

    /**
     * <p>
     * Accuracy test for method <code>Inheritance</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testInheritanceAccuracy() throws Exception {
        assertTrue("RejectReason should inherit from TimeTrackerBean.", rr instanceof TimeTrackerBean);
    }

    /**
     * <p>
     * Accuracy test for method <code>Constructor</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("The instance should be created.", rr);
    }

    /**
     * <p>
     * Accuracy test for method <code>getCompanyId</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetCompanyIdAccuracy() throws Exception {
        rr.setCompanyId(2);

        assertEquals("The id retrieve should be 2.", 2, rr.getCompanyId());
    }

    /**
     * <p>
     * Accuracy test for method <code>setCompanyId</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetCompanyIdAccuracy() throws Exception {
        rr.setCompanyId(2);

        assertTrue("The bean has been changed.", rr.isChanged());
        assertEquals("The company id is 2.", 2, rr.getCompanyId());
    }

    /**
     * <p>
     * Accuracy test for method <code>getDescription</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetDescriptionAccuracy() throws Exception {
        rr.setDescription("This is description.");

        assertEquals("The description should be set.", "This is description.", rr.getDescription());
    }

    /**
     * <p>
     * Accuracy test for method <code>setDescription</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetDescriptionAccuracy() throws Exception {
        rr.setDescription("This is description.");

        assertTrue("The bean has been changed.", rr.isChanged());
        assertEquals("The description should be set.", "This is description.", rr.getDescription());
    }

    /**
     * <p>
     * Accuracy test for method <code>getActive</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetActiveAccuracy() throws Exception {
        rr.setActive(true);

        assertTrue("Active should be true.", rr.getActive());
    }

    /**
     * <p>
     * Accuracy test for method <code>setActive</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetActiveAccuracy() throws Exception {
        rr.setActive(true);

        assertTrue("The bean has been changed.", rr.isChanged());
        assertTrue("The active should be set.", rr.getActive());
    }

    /**
     * <p>
     * Accuracy test for method <code>isActive</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testisActiveAccuracy() throws Exception {
        rr.setActive(true);

        assertTrue("The active should be set.", rr.isActive());
    }
}
