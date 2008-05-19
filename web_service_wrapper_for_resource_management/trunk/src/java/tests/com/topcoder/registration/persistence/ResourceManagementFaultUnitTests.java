/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test for <code>{@link ResourceManagementFault}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourceManagementFaultUnitTests extends TestCase {

    /**
     * <p>
     * Test constructor {@link ResourceManagementFault#ResourceManagementFault()}.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("ResourceManagementFault should be created.", new ResourceManagementFault());
    }

    /**
     * <p>
     * Test {@link ResourceManagementFault#setStackTrace(String)} and
     * {@link ResourceManagementFault#getStackTrace()}.
     * </p>
     */
    public void testGetterAndSetter() {
        ResourceManagementFault fault = new ResourceManagementFault();
        assertNull("The stack trace is initially null.", fault.getStackTrace());
        fault.setStackTrace("stack trace");
        assertEquals("stack trace should be set", "stack trace", fault.getStackTrace());
    }
}
