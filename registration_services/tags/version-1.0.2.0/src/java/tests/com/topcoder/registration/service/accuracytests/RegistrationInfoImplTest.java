/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.registration.service.impl.RegistrationInfoImpl;

/**
 * Accuracy test for <code>{@link com.topcoder.registration.service.impl.RegistrationInfoImpl}</code> class.
 * 
 * @author mittu
 * @version 1.0
 */
public class RegistrationInfoImplTest extends TestCase {
    /**
     * <p>
     * Represents the RegistrationInfoImpl to test.
     * </p>
     */
    private RegistrationInfoImpl impl;

    /**
     * Sets up test environment.
     * 
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        impl = new RegistrationInfoImpl();
    }

    /**
     * Accuracy test for <code>{@link RegistrationInfoImpl#RegistrationInfoImpl()}</code>.
     */
    public void testConstructor() {
        assertNotNull("Failed to create RegistrationInfoImpl", impl);
    }

    /**
     * Accuracy test for <code>{@link RegistrationInfoImpl#RegistrationInfoImpl(long,long,long)}</code>.
     */
    public void testConstructor_long_long_long() {
        impl = new RegistrationInfoImpl(101, 10, 1);
        assertNotNull("Failed to create RegistrationInfoImpl", impl);
    }

    /**
     * Accuracy test for <code>{@link RegistrationInfoImpl#setUserId(long)}</code> and
     * <code>{@link RegistrationInfoImpl#getUserId()}</code>.
     */
    public void testMethodSetUserId_long() {
        impl.setUserId(10);
        assertEquals("Failed to set/get the user id", 10, impl.getUserId());
    }

    /**
     * Accuracy test for <code>{@link RegistrationInfoImpl#setProjectId(long)}</code> and
     * <code>{@link RegistrationInfoImpl#getProjectId()}</code>.
     */
    public void testMethodSetProjectId_long() {
        impl.setProjectId(101);
        assertEquals("Failed to set/get the project id", 101, impl.getProjectId());
    }

    /**
     * Accuracy test for <code>{@link RegistrationInfoImpl#setRoleId(long)}</code> and
     * <code>{@link RegistrationInfoImpl#getRoleId()}</code>.
     */
    public void testMethodSetRoleId_long() {
        impl.setRoleId(1);
        assertEquals("Failed to set/get the role id", 1, impl.getRoleId());
    }

    /**
     * Returns all tests.
     * 
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(RegistrationInfoImplTest.class);
    }
}
