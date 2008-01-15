/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link UserClientPK}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserClientPKUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>UserClientPK</code> instance used in tests.
     * </p>
     */
    private UserClientPK userClientPK;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(UserClientPKUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        userClientPK = new UserClientPK();
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClientPK#UserClientPK()}</code> constructor.
     * </p>
     * <p>
     * All the fields have default value.
     * </p>
     */
    public void testUserClientPK1_accuracy() {
        userClientPK = new UserClientPK();

        assertNotNull("Instance should always created.", userClientPK);
        assertNull("userId field should be null.", userClientPK.getUserId());
        assertNull("clientId field should be null.", userClientPK.getClientId());
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClientPK#UserClientPK(Long, Integer)}</code> constructor.
     * </p>
     * <p>
     * The passed arguments should be set properly.
     * </p>
     */
    public void testUserClientPK2_accuracy() {
        userClientPK = new UserClientPK(1l, 2);

        assertNotNull("Instance should always created.", userClientPK);
        assertEquals("userId field is improperly set.", new Long(1), userClientPK.getUserId());
        assertEquals("clientId field is improperly set..", new Integer(2), userClientPK.getClientId());
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClientPK#getUserId()}</code> method.
     * </p>
     * <p>
     * Return the default user id - null.
     * </p>
     */
    public void testGetUserId_default() {
        assertNull("userId field should be null.", userClientPK.getUserId());
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClientPK#getUserId()}</code> method.
     * </p>
     * <p>
     * Should return the user id previously set.
     * </p>
     */
    public void testSetUserId_accuracy() {
        userClientPK.setUserId(1l);
        assertEquals("userId field is improperly set.", new Long(1), userClientPK.getUserId());
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClientPK#getClientId()}</code> method.
     * </p>
     * <p>
     * Return the default client id - null.
     * </p>
     */
    public void testGetClientId_default() {
        assertNull("clientId field should be null.", userClientPK.getClientId());
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClientPK#getClientId()}</code> method.
     * </p>
     * <p>
     * Should return the client id previously set.
     * </p>
     */
    public void testSetClientId_accuracy() {
        userClientPK.setClientId(2);
        assertEquals("clientId field is improperly set.", new Integer(2), userClientPK.getClientId());
    }
}
