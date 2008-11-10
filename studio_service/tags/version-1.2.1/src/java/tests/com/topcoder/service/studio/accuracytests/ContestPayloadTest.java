/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import junit.framework.TestCase;

import com.topcoder.service.studio.ContestPayload;

/**
 * <p>
 * Accuracy tests for ContestPayload class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestPayloadTest extends TestCase {
    /**
     * <p>
     * Represents the payload to test.
     * </p>
     */
    private ContestPayload payload;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        payload = new ContestPayload();
    }

    /**
     * <p>
     * Tests setter/getter for name field.
     * </p>
     */
    public void testName() {
        payload.setName("test");
        assertEquals("The name is wrong.", "test", payload.getName());
    }

    /**
     * <p>
     * Tests setter/getter for value field.
     * </p>
     */
    public void testValue() {
        payload.setValue("test");
        assertEquals("The value is wrong.", "test", payload.getValue());
    }

    /**
     * <p>
     * Tests setter/getter for required field.
     * </p>
     */
    public void testRequired() {
        payload.setRequired(true);
        assertTrue("The flag is wrong.", payload.isRequired());
    }
}
