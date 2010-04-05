/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service.accuracytests;

import java.io.Serializable;

import junit.framework.TestCase;

import com.liquid.portal.service.Warning;

/**
 * Accuracy tests for {@link Warning}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class WarningAccuracyTest extends TestCase {
    /**
     * <p>
     * Represents the Warning. Just for test.
     * </p>
     */
    private Warning instance;

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
        instance = new Warning();
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
     * Accuracy test for setter and getter for message filed.
     * </p>
     */
    public void testSetterAndGetterFor_message() {
        instance.setMessage("tc");
        assertTrue("should be true", instance.getMessage().equals("tc"));
    }
}