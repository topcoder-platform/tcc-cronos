/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service.accuracytests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.liquid.portal.service.Result;
import com.liquid.portal.service.Warning;

/**
 * Accuracy tests for {@link Result}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResultAccuracyTest extends TestCase {
    /**
     * <p>
     * Represents the Result. Just for test.
     * </p>
     */
    private Result instance;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
        instance = new Result();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void tearDown() throws Exception {
        instance = null;
        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test for constructor. It verifies the new instance is created.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("should not be null", instance);
        assertTrue("should be true", instance instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for setter and getter for warnings filed.
     * </p>
     */
    public void testSetterAndGetterFor_warnings() {
        List<Warning> warnings = new ArrayList<Warning>();
        instance.setWarnings(warnings);
        assertTrue("should be true", instance.getWarnings().equals(warnings));
    }
}