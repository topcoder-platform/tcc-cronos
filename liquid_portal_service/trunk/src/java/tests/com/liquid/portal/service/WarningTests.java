/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>Warning</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class WarningTests extends TestCase {
    /**
     * <p>
     * Represents the error message for testing.
     * </p>
     */
    private static final String MESSAGE = "error message";

    /**
     * <p>
     * Tests accuracy of <code>Warning()</code> constructor.
     * </p>
     */
    public void testCtorAccuracy() {
        Warning warning = new Warning();
        assertNotNull("Unable to instantiate Warning", warning);
        assertNull(warning.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>setMessage(String)</code> and
     * <code>getMessage()</code> methods.
     * </p>
     */
    public void testMessageAccuracy() {
        Warning warning = new Warning();
        warning.setMessage(MESSAGE);
        assertEquals(MESSAGE, warning.getMessage());
    }
}
