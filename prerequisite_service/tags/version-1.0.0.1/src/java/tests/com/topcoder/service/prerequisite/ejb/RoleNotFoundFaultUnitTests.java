/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link RoleNotFoundFault}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RoleNotFoundFaultUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>RoleNotFoundFault</code> instance used in tests.
     * </p>
     */
    private RoleNotFoundFault roleNotFoundFault;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(RoleNotFoundFaultUnitTests.class);
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

        roleNotFoundFault = new RoleNotFoundFault();
    }

    /**
     * <p>
     * Unit test for <code>RoleNotFoundFault#RoleNotFoundFault()</code> constructor.
     * </p>
     * <p>
     * Instance should be always created.
     * </p>
     */
    public void testRoleNotFoundFault_accuracy() {
        assertNotNull("Instance should be always created.", roleNotFoundFault);
    }

    /**
     * <p>
     * Unit test for <code>RoleNotFoundFault#getRoleIdNotFound()</code> method.
     * </p>
     * <p>
     * It should return 0, if not set.
     * </p>
     */
    public void testGetRoleIdNotFound_default() {
        assertEquals("Should return 0.", 0, roleNotFoundFault.getRoleIdNotFound());
    }

    /**
     * <p>
     * Unit test for <code>RoleNotFoundFault#setRoleIdNotFound(long)</code> method.
     * </p>
     * <p>
     * All value are valid to set.
     * </p>
     */
    public void testSetRoleIdNotFound_accuracy() {
        roleNotFoundFault.setRoleIdNotFound(-1);
        assertEquals("Incorrect role id.", -1, roleNotFoundFault.getRoleIdNotFound());

        roleNotFoundFault.setRoleIdNotFound(0);
        assertEquals("Incorrect role id.", 0, roleNotFoundFault.getRoleIdNotFound());

        roleNotFoundFault.setRoleIdNotFound(1);
        assertEquals("Incorrect role id.", 1, roleNotFoundFault.getRoleIdNotFound());
    }
}
