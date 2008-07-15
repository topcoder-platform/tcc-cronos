/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.failuretests.dao.implementations;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.topcoder.service.digitalrun.track.dao.implementations.AbstractDAO;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackStatusDAO;

/**
 * Some tests for AbstractDAO class.
 *
 * @author Orange_Cloud
 * @version 1.0
 */
public class AbstractDAOTest extends TestCase {
    /**
     * Instance to test.
     */
    private AbstractDAO target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(AbstractDAOTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        target = new JPADigitalRunTrackStatusDAO();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests setLogger method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testSetLoggerForNull() throws Exception {
        try {
            target.setLogger(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests setSessionContext method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testSetSessionContextForNull() throws Exception {
        try {
            target.setSessionContext(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests setUnitName method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testSetUnitNameForNull() throws Exception {
        try {
            target.setUnitName(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests setUnitName method for empty string as parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testSetUnitNameForEmpty() throws Exception {
        try {
            target.setUnitName(" \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }
}