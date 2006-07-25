/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.management.scorecard.persistence;

import junit.framework.TestCase;

/**
 * The unit tests for {@link com.topcoder.management.scorecard.persistence.PersistenceException} class.
 *
 * @author kr00tki
 * @version 1.0
 */
public class PersistenceExceptionTest extends TestCase {
    /**
     * The test message used in tests.
     */
    private static final String MESSAGE = "test message";

    /**
     * Tests the {@link PersistenceException#PersistenceException(String)} constructor.
     */
    public void testPersistenceExceptionString() {
        PersistenceException ex = new PersistenceException(MESSAGE);
        assertEquals("Incorrect error message.", MESSAGE, ex.getMessage());
    }

    /**
     * Tests the {@link PersistenceException#PersistenceException(String, Throwable)} constructor.
     */
    public void testPersistenceExceptionStringThrowable() {
        Throwable cause = new IllegalAccessException();
        PersistenceException ex = new PersistenceException(MESSAGE, cause);
        assertTrue("Incorrect error message.", ex.getMessage().indexOf(MESSAGE) != -1);
        assertSame("Incorrect cause", cause, ex.getCause());
    }

}
