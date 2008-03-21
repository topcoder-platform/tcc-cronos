/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link PersistenceFault}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PersistenceFaultUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>PersistenceFault</code> instance used in tests.
     * </p>
     */
    private PersistenceFault persistenceFault;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PersistenceFaultUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        persistenceFault = new PersistenceFault();
    }

    /**
     * <p>
     * Unit test for <code>PersistenceFault#PersistenceFault()</code> constructor.
     * </p>
     * <p>
     * Instance should be always created.
     * </p>
     */
    public void testPersistenceFault_accuracy() {
        assertNotNull("Instance should be always created.", persistenceFault);
    }

    /**
     * <p>
     * Unit test for <code>PersistenceFault#getPersistenceMessage()</code> method.
     * </p>
     * <p>
     * It should return null, if not set.
     * </p>
     */
    public void testGetPersistenceMessage_default() {
        assertNull("Should return null.", persistenceFault.getPersistenceMessage());
    }

    /**
     * <p>
     * Unit test for <code>PersistenceFault#setPersistenceMessage(String)</code> method.
     * </p>
     * <p>
     * All value are valid to set.
     * </p>
     */
    public void testSetPersistenceMessage_accuracy() {
        persistenceFault.setPersistenceMessage(null);
        assertNull("Should return null.", persistenceFault.getPersistenceMessage());

        persistenceFault.setPersistenceMessage("");
        assertEquals("Incorrect persistence message.", "", persistenceFault.getPersistenceMessage());

        persistenceFault.setPersistenceMessage("  ");
        assertEquals("Incorrect persistence message.", "  ", persistenceFault.getPersistenceMessage());

        persistenceFault.setPersistenceMessage("Hello");
        assertEquals("Incorrect persistence message.", "Hello", persistenceFault.getPersistenceMessage());
    }
}
