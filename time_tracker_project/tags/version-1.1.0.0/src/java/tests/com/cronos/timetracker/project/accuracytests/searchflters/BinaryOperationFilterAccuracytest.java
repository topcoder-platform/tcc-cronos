/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.accuracytests.searchflters;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.timetracker.project.searchfilters.BinaryOperation;
import com.cronos.timetracker.project.searchfilters.BinaryOperationFilter;
import com.cronos.timetracker.project.searchfilters.Filter;

/**
 * The class <code>BinaryOperationFilterAccuracytest</code> contains tests for the class
 * {@link <code>BinaryOperationFilter</code>}.
 * @author FireIce
 * @version 1.1
 */
public class BinaryOperationFilterAccuracytest extends TestCase {

    /**
     * Represents the left operand used in tests.
     */
    private Filter leftOperand = new MockFilter();

    /**
     * Represents the right operand used in tests.
     */
    private Filter rightOperand = new MockFilter();

    /**
     * Represents the BinaryOperationFilter instance used in tests.
     */
    private BinaryOperationFilter instance = new BinaryOperationFilter(BinaryOperation.OR, leftOperand, rightOperand);

    /**
     * accuracy test for constructor.
     */
    public void testCtor() {
        Filter leftFilter = new MockFilter();
        Filter rightFilter = new MockFilter();

        BinaryOperationFilter binaryOperationFilter = new BinaryOperationFilter(BinaryOperation.AND, leftFilter,
                rightFilter);

        assertTrue(binaryOperationFilter instanceof Filter);

        assertSame("leftOperand field not set correctly", leftFilter, binaryOperationFilter.getLeftOperand());
        assertSame("rightOperand field not set correctly", rightFilter, binaryOperationFilter.getRightOperand());
        assertSame("operation field not set correctly", BinaryOperation.AND, binaryOperationFilter.getOperation());
    }

    /**
     * accuracy test for getLeftOperand method.
     */
    public void testGetLeftOperand() {
        assertSame("leftOperand field not set correctly in constructor", leftOperand, instance.getLeftOperand());
    }

    /**
     * accuracy test for getRightOperand method.
     */
    public void testGetRightOperand() {
        assertSame("rightOperand field not set correctly in constructor", rightOperand, instance.getRightOperand());
    }

    /**
     * accuracy test for getOperation method.
     */
    public void testGetOperation() {
        assertSame("operation field not set correctly in constructor", BinaryOperation.OR, instance.getOperation());
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(BinaryOperationFilterAccuracytest.class);
    }
}
