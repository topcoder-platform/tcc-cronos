/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test case for <code>CustomResourceProperties</code>.
 * </p>
 * @author moonli
 * @version 1.0
 */
public class CustomResourcePropertiesTest extends TestCase {

    /**
     * <p>
     * Test for EXTERNAL_REFERENCE_ID.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testExternalReferenceId() throws Exception {
        assertEquals("External reference id name mismatched.", "External reference ID",
            CustomResourceProperties.EXTERNAL_REFERENCE_ID);
    }

    /**
     * <p>
     * Test for HANDLE.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testHandleName() throws Exception {
        assertEquals("Handle name mismatched.", "Handle", CustomResourceProperties.HANDLE);
    }

    /**
     * <p>
     * Test for EMAIL.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testEmail() throws Exception {
        assertEquals("Email name mismatched.", "Email", CustomResourceProperties.EMAIL);
    }

    /**
     * <p>
     * Test for REGISTRATION_DATE.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testRegistrationDate() throws Exception {
        assertEquals("Registration date mismatched.", "Registration Date",
            CustomResourceProperties.REGISTRATION_DATE);
    }
}
