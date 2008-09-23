/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link CompClientPK}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompClientPKUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>CompClientPK</code> instance used in tests.
     * </p>
     */
    private CompClientPK compClientPK;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(CompClientPKUnitTests.class);
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

        compClientPK = new CompClientPK();
    }

    /**
     * <p>
     * Unit test for <code>{@link CompClientPK#CompClientPK()}</code> constructor.
     * </p>
     * <p>
     * All the fields have default value.
     * </p>
     */
    public void testCompClientPK1_accuracy() {
        compClientPK = new CompClientPK();

        assertNotNull("Instance should always created.", compClientPK);
        assertNull("componentId field should be null.", compClientPK.getComponentId());
        assertNull("clientId field should be null.", compClientPK.getClientId());
    }

    /**
     * <p>
     * Unit test for <code>{@link CompClientPK#CompClientPK(Long, Integer)}</code> constructor.
     * </p>
     * <p>
     * The passed arguments should be set properly.
     * </p>
     */
    public void testCompClientPK2_accuracy() {
        compClientPK = new CompClientPK(1l, 2);

        assertNotNull("Instance should always created.", compClientPK);
        assertEquals("componentId field is improperly set.", new Long(1), compClientPK.getComponentId());
        assertEquals("clientId field is improperly set..", new Integer(2), compClientPK.getClientId());
    }

    /**
     * <p>
     * Unit test for <code>{@link CompClientPK#getComponentId()}</code> method.
     * </p>
     * <p>
     * Return the default component id - null.
     * </p>
     */
    public void testGetComponentId_default() {
        assertNull("componentId field should be null.", compClientPK.getComponentId());
    }

    /**
     * <p>
     * Unit test for <code>{@link CompClientPK#getComponentId()}</code> method.
     * </p>
     * <p>
     * Should return the component id previously set.
     * </p>
     */
    public void testSetComponentId_accuracy() {
        compClientPK.setComponentId(1l);
        assertEquals("componentId field is improperly set.", new Long(1), compClientPK.getComponentId());
    }

    /**
     * <p>
     * Unit test for <code>{@link CompClientPK#getClientId()}</code> method.
     * </p>
     * <p>
     * Return the default client id - null.
     * </p>
     */
    public void testGetClientId_default() {
        assertNull("clientId field should be null.", compClientPK.getClientId());
    }

    /**
     * <p>
     * Unit test for <code>{@link CompClientPK#getClientId()}</code> method.
     * </p>
     * <p>
     * Should return the client id previously set.
     * </p>
     */
    public void testSetClientId_accuracy() {
        compClientPK.setClientId(2);
        assertEquals("clientId field is improperly set.", new Integer(2), compClientPK.getClientId());
    }
}
