/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>Result</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResultTests extends TestCase {
    /**
     * <p>
     * Represents the instance of <code>Result</code>.
     * </p>
     */
    private Result result;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        result = new Result();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * <p>
     * Accuracy test case for <code>setWarnings(List)</code>
     * and <code>getWarnings()</code>.
     * </p>
     */
    public void testSetGetWarnings() {
        result.setWarnings(null);
        assertNull(result.getWarnings());
        List<Warning> list = new ArrayList<Warning>();
        result.setWarnings(list);
        assertEquals(list, result.getWarnings());
    }
}
