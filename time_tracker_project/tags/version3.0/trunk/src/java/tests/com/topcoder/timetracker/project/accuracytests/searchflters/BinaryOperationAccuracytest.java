/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.accuracytests.searchflters;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.project.searchfilters.BinaryOperation;

/**
 * The class <code>BinaryOperationAccuracytest</code> contains tests for the class
 * {@link <code>BinaryOperation</code>}.
 * @author FireIce
 * @version 1.1
 */
public class BinaryOperationAccuracytest extends TestCase {
    /**
     * accuracy test for Enum constants.
     */
    public void testStaticEnumConstants() {
        assertNotNull(BinaryOperation.AND);
        assertEquals("name field incorrect", "AND", BinaryOperation.AND.toString());

        assertNotNull(BinaryOperation.OR);
        assertEquals("name field incorrect", "OR", BinaryOperation.OR.toString());
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(BinaryOperationAccuracytest.class);
    }
}
