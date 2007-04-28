/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.accuracytests.searchflters;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.timetracker.project.searchfilters.CompareOperation;

/**
 * The class <code>CompareOperationAccuracytest</code> contains tests for the class
 * {@link <code>CompareOperation</code>}.
 * @author FireIce
 * @version 1.1
 */
public class CompareOperationAccuracytest extends TestCase {
    /**
     * accuracy test for enum constants.
     */
    public void testConstants() {
        assertNotNull(CompareOperation.EQUAL);
        assertEquals("incorrect name value", "=", CompareOperation.EQUAL.toString());

        assertNotNull(CompareOperation.GREATER);
        assertEquals("incorrect name value", ">", CompareOperation.GREATER.toString());

        assertNotNull(CompareOperation.GREATER_OR_EQUAL);
        assertEquals("incorrect name value", ">=", CompareOperation.GREATER_OR_EQUAL.toString());

        assertNotNull(CompareOperation.LESS);
        assertEquals("incorrect name value", "<", CompareOperation.LESS.toString());

        assertNotNull(CompareOperation.LESS_OR_EQUAL);
        assertEquals("incorrect name value", "<=", CompareOperation.LESS_OR_EQUAL.toString());

        assertNotNull(CompareOperation.LIKE);
        assertEquals("incorrect name value", "like", CompareOperation.LIKE.toString());
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(CompareOperationAccuracytest.class);
    }
}
