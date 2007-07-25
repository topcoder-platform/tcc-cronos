/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test case for <code>RegistrationInfoImpl</code> class.
 * </p>
 * @author moonli
 * @version 1.0
 */
public class RegistrationInfoImplTest extends TestCase {

    /**
     * <p>
     * Represents an instance of <code>RegistrationInfoImpl</code>.
     * </p>
     */
    private RegistrationInfoImpl regInfo;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        regInfo = new RegistrationInfoImpl(1, 1, 1);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        this.regInfo = null;
    }

    /**
     * <p>
     * Test for default constructor.
     * </p>
     * <p>
     * Tests it for accuracy, non-null instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultCtorAccuracy() throws Exception {
        assertNotNull("'regInfo' should not be null.", new RegistrationInfoImpl());
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it against negative project id. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithNegProjectId() throws Exception {
        try {
            new RegistrationInfoImpl(-1, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it against negative user id. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithNegUserId() throws Exception {
        try {
            new RegistrationInfoImpl(1, -1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it against negative role id. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithNegRoleId() throws Exception {
        try {
            new RegistrationInfoImpl(1, 1, -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it for accuracy with projectId=1, userId=1, roleId=1, non-null instance should be
     * returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithFullParamsAccuracy() throws Exception {
        regInfo = new RegistrationInfoImpl(1, 1, 1);

        assertEquals("Project id mismatched.", 1, regInfo.getProjectId());
        assertEquals("User id mismatched.", 1, regInfo.getUserId());
        assertEquals("Role id mismatched.", 1, regInfo.getRoleId());
    }

    /**
     * <p>
     * Test for <code>getProjectId</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, projectId=1 should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectIdAccuracy() throws Exception {
        assertEquals("Project id mismatched.", 1, regInfo.getProjectId());
    }

    /**
     * <p>
     * Test for <code>getUserId</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, userId=1 should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserIdAccuracy() throws Exception {
        assertEquals("User id mismatched.", 1, regInfo.getUserId());
    }

    /**
     * <p>
     * Test for <code>getRoleId</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, roleId=1 should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetRoleIdAccuracy() throws Exception {
        assertEquals("Role id mismatched.", 1, regInfo.getRoleId());
    }

    /**
     * <p>
     * Test for <code>setProjectId(id)</code> method.
     * </p>
     * <p>
     * Tests it against negative project id. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetProjectIdWithNegId() throws Exception {
        try {
            regInfo.setProjectId(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>setProjectId(id)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, projectId=2 should be set successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetProjectIdAccuracy() throws Exception {
        regInfo.setProjectId(2);
        assertEquals("Project Id mismatched.", 2, regInfo.getProjectId());
    }

    /**
     * <p>
     * Test for <code>setUserId(id)</code> method.
     * </p>
     * <p>
     * Tests it against negative user id. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetUserIdWithNegId() throws Exception {
        try {
            regInfo.setUserId(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>setUserId(id)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, userId=2 should be set successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetUserIdAccuracy() throws Exception {
        regInfo.setUserId(2);
        assertEquals("User Id mismatched.", 2, regInfo.getUserId());
    }

    /**
     * <p>
     * Test for <code>setRoleId(id)</code> method.
     * </p>
     * <p>
     * Tests it against negative role id. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetRoleIdWithNegId() throws Exception {
        try {
            regInfo.setRoleId(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>setRoleId(id)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, roleId=2 should be set successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetRoleIdAccuracy() throws Exception {
        regInfo.setRoleId(2);
        assertEquals("Role Id mismatched.", 2, regInfo.getRoleId());
    }
}
