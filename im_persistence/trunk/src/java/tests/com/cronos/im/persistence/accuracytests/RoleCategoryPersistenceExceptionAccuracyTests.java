/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.accuracytests;

import junit.framework.TestCase;

import com.cronos.im.persistence.rolecategories.RoleCategoryPersistenceException;

/**
 * <p>
 * Accuracy test for <code>{@link RoleCategoryPersistenceException}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class RoleCategoryPersistenceExceptionAccuracyTests extends TestCase {
    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * Represents a throwable cause.
     */
    private static final Exception CAUSE = new Exception();

    /**
     * Tests accuracy of <code>RoleCategoryPersistenceException(String)</code> constructor. The detail error message
     * should be correct.
     */
    public void testRoleCategoryPersistenceExceptionStringAccuracy() {
        // Construct RoleCategoryPersistenceException with a detail message
        RoleCategoryPersistenceException exception = new RoleCategoryPersistenceException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * Tests accuracy of <code>RoleCategoryPersistenceException(String, Throwable)</code> constructor. The detail
     * error message and the original cause of error should be correct.
     */
    public void testRoleCategoryPersistenceExceptionStringThrowableAccuracy() {
        // Construct RoleCategoryPersistenceException with a detail message and a cause
        RoleCategoryPersistenceException exception = new RoleCategoryPersistenceException(DETAIL_MESSAGE, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE + ", caused by null",
            exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertTrue("Cause should be identical.", CAUSE == exception.getCause());
    }
}
