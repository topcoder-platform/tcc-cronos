/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.registration.service.impl.RemovalResultImpl;

/**
 * Accuracy test for <code>{@link com.topcoder.registration.service.impl.RemovalResultImpl}</code> class.
 * 
 * @author mittu
 * @version 1.0
 */
public class RemovalResultImplTest extends TestCase {
    /**
     * <p>
     * Represents the RemovalResultImpl to test.
     * </p>
     */
    private RemovalResultImpl impl1, impl2;

    /**
     * <p>
     * Represents the errors used for testing.
     * </p>
     */
    private String[] errors;

    /**
     * Sets up test environment.
     * 
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        impl1 = new RemovalResultImpl();
        errors = new String[] { "no error" };
        impl2 = new RemovalResultImpl(false, errors);
    }

    /**
     * Accuracy test for <code>{@link RemovalResultImpl#RemovalResultImpl()}</code>.
     */
    public void testConstructor() {
        assertNotNull("Failed to create RemovalResultImpl", impl1);
    }

    /**
     * Accuracy test for <code>{@link RemovalResultImpl#RemovalResultImpl(boolean,String[])}</code>.
     */
    public void testConstructor_boolean_StringArray() {
        assertNotNull("Failed to create RemovalResultImpl", impl2);
    }

    /**
     * Accuracy test for <code>{@link RemovalResultImpl#setSuccessful(boolean)}</code>.
     */
    public void testMethodSetSuccessful_boolean() {
        impl1.setSuccessful(true);
        assertTrue("Failed to set successful", impl1.isSuccessful());
    }

    /**
     * Accuracy test for <code>{@link RemovalResultImpl#setErrors(String[])}</code>.
     */
    public void testMethodSetErrors_StringArray() {
        impl1.setErrors(null);
        impl1.setSuccessful(false);
        impl1.setErrors(errors);
        assertEquals("Failed to set errors", "no error", impl1.getErrors()[0]);
    }

    /**
     * Accuracy test for <code>{@link RemovalResultImpl#isSuccessful()}</code>.
     */
    public void testMethodIsSuccessful() {
        assertFalse("Failed to check successful", impl2.isSuccessful());
    }

    /**
     * Accuracy test for <code>{@link RemovalResultImpl#getErrors()}</code>.
     */
    public void testMethodGetErrors() {
        assertEquals("Failed to get errors", "no error", impl2.getErrors()[0]);
    }

    /**
     * Returns all tests.
     * 
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(RemovalResultImplTest.class);
    }
}
