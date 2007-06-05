/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.resource.Resource;
import com.topcoder.registration.service.impl.RegistrationResultImpl;

/**
 * Accuracy test for <code>{@link com.topcoder.registration.service.impl.RegistrationResultImpl}</code>
 * class.
 * 
 * @author mittu
 * @version 1.0
 */
public class RegistrationResultImplTest extends TestCase {
    /**
     * <p>
     * Represents the RegistrationResultImpl for testing.
     * </p>
     */
    private RegistrationResultImpl impl1, impl2;

    /**
     * <p>
     * Represents the errors used for testing.
     * </p>
     */
    private String[] errors;

    /**
     * <p>
     * Represents the Resource used for testing.
     * </p>
     */
    private Resource resource;

    /**
     * Sets up test environment.
     * 
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        errors = new String[] { "no error" };
        resource = new Resource(1);
        impl1 = new RegistrationResultImpl();
        impl2 = new RegistrationResultImpl(false, errors, null);
    }

    /**
     * Accuracy test for <code>{@link RegistrationResultImpl#RegistrationResultImpl()}</code>.
     */
    public void testConstructor() {
        assertNotNull("Failed to create RegistrationResultImpl", impl1);
    }

    /**
     * Accuracy test for
     * <code>{@link RegistrationResultImpl#RegistrationResultImpl(boolean,String[],Resource)}</code>.
     */
    public void testConstructor_boolean_StringArray_Resource() {
        assertNotNull("Failed to create RegistrationResultImpl", impl1);
    }

    /**
     * Accuracy test for <code>{@link RegistrationResultImpl#isSuccessful()}</code>.
     */
    public void testMethodIsSuccessful() {
        assertFalse("Failed to check isSuccesful", impl2.isSuccessful());
    }

    /**
     * Accuracy test for <code>{@link RegistrationResultImpl#getErrors()}</code>.
     */
    public void testMethodGetErrors() {
        assertEquals("Failed to get errors", "no error", impl2.getErrors()[0]);
    }

    /**
     * Accuracy test for <code>{@link RegistrationResultImpl#setSuccessful(boolean)}</code>.
     */
    public void testMethodSetSuccessful_boolean() {
        impl1.setSuccessful(true);
        assertTrue("Failed to check isSuccesful", impl1.isSuccessful());
    }

    /**
     * Accuracy test for <code>{@link RegistrationResultImpl#getPreviousRegistration()}</code>.
     */
    public void testMethodGetPreviousRegistration() {
        assertNull("Failed to get previous registration", impl2.getPreviousRegistration());
    }

    /**
     * Accuracy test for <code>{@link RegistrationResultImpl#setErrors(String[])}</code>.
     */
    public void testMethodSetErrors_StringArray() {
        impl1.setErrors(null);
        impl1.setSuccessful(false);
        impl1.setErrors(errors);
        assertEquals("Failed to get errors", "no error", impl1.getErrors()[0]);
    }

    /**
     * Accuracy test for <code>{@link RegistrationResultImpl#setPreviousRegistration(Resource)}</code>.
     */
    public void testMethodSetPreviousRegistration_Resource() {
        impl1.setPreviousRegistration(null);
        impl1.setSuccessful(true);
        impl1.setPreviousRegistration(resource);
        assertEquals("Failed to get previous registration", resource, impl1.getPreviousRegistration());
    }

    /**
     * Returns all tests.
     * 
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(RegistrationResultImplTest.class);
    }
}
