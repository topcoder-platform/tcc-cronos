/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.accuracytests;

import junit.framework.TestCase;

import com.cronos.im.persistence.rolecategories.ManagerNotFoundException;

/**
 * <p>
 * Accuracy test for <code>{@link ManagerNotFoundException}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class ManagerNotFoundExceptionAccuracyTests extends TestCase {
    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * Tests accuracy of <code>ManagerNotFoundException(String)</code> constructor. The detail error message should be
     * correct.
     */
    public void testManagerNotFoundExceptionStringAccuracy() {
        // Construct ManagerNotFoundException with a detail message
        ManagerNotFoundException exception = new ManagerNotFoundException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }
}
