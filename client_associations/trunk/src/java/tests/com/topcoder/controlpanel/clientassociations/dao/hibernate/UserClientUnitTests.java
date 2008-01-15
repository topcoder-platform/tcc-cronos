/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link UserClient}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserClientUnitTests extends TestCase {
    /**
     * <p>
     * Represents the <code>UserClient</code> instance used in tests.
     * </p>
     */
    private UserClient userClient;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(UserClientUnitTests.class);
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

        userClient = new UserClient();
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClient#UserClient()}</code> constructor.
     * </p>
     * <p>
     * All the fields has default value.
     * </p>
     */
    public void testUserClient1_accuracy() {
        userClient = new UserClient();

        assertNotNull("Instance should be always created.", userClient);
        assertNull("comp_id field should be null.", userClient.getComp_id());
        assertNull("adminInd field should be null.", userClient.getAdminInd());
        assertNull("client field should be null.", userClient.getClient());
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClient#UserClient(UserClientPK)}</code> constructor.
     * </p>
     * <p>
     * The passed primary key should be set properly.
     * </p>
     */
    public void testUserClient2_accuracy() {
        UserClientPK compClientPK = new UserClientPK();

        userClient = new UserClient(compClientPK);

        assertNotNull("Instance should be always created.", userClient);
        assertNotNull("comp_id field should not be null.", userClient.getComp_id());
        assertSame("comp_id field is not set properly.", compClientPK, userClient.getComp_id());
        assertNull("adminInd field should be null.", userClient.getAdminInd());
        assertNull("client field should be null.", userClient.getClient());
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClient#UserClient(UserClientPK, Integer, Client)}</code> constructor.
     * </p>
     * <p>
     * The passed primary key and client should be set properly.
     * </p>
     */
    public void testUserClient3_accuracy() {
        UserClientPK compClientPK = new UserClientPK();
        Client client = new Client();

        userClient = new UserClient(compClientPK, 1, client);

        assertNotNull("Instance should be always created.", userClient);
        assertNotNull("comp_id field should not be null.", userClient.getComp_id());
        assertSame("comp_id field is not set properly.", compClientPK, userClient.getComp_id());
        assertNotNull("adminInd field should not be null.", userClient.getAdminInd());
        assertEquals("adminInd field is not set properly.", new Integer(1), userClient.getAdminInd());
        assertNotNull("client field should not be null.", userClient.getClient());
        assertSame("client field is not set properly.", client, userClient.getClient());
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClient#getComp_id()}</code> method.
     * </p>
     * <p>
     * Should return the default primary key - null.
     * </p>
     */
    public void testGetComp_id_default() {
        assertNull("comp_id field should be null.", userClient.getComp_id());
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClient#setComp_id(UserClientPK)}</code> method.
     * </p>
     * <p>
     * The passed to comp_id should be set properly.
     * </p>
     */
    public void testSetComp_id_accuracy() {
        UserClientPK compClientPK = new UserClientPK();

        userClient.setComp_id(compClientPK);

        assertNotNull("comp_id field should not be null.", userClient.getComp_id());
        assertSame("comp_id field is not set properly.", compClientPK, userClient.getComp_id());
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClient#getAdminInd()}</code> method.
     * </p>
     * <p>
     * Should return the default adminInd field - null.
     * </p>
     */
    public void testGetAdminInd_default() {
        assertNull("adminInd field should be null.", userClient.getAdminInd());
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClient#setAdminInd(Integer)}</code> method.
     * </p>
     * <p>
     * The adminInd field should be set properly.
     * </p>
     */
    public void testSetAdminInd_accuracy() {
        userClient.setAdminInd(1);

        assertNotNull("adminInd field should not be null.", userClient.getAdminInd());
        assertEquals("adminInd field is not set properly.", new Integer(1), userClient.getAdminInd());
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClient#getClient()}</code> method.
     * </p>
     * <p>
     * Should return the default client - null.
     * </p>
     */
    public void testGetClient_default() {
        assertNull("client field should be null.", userClient.getClient());
    }

    /**
     * <p>
     * Unit test for <code>{@link UserClient#setClient(Client)}</code> method.
     * </p>
     * <p>
     * The passed to client should be set properly.
     * </p>
     */
    public void testSetClient_accuracy() {
        Client client = new Client();

        userClient.setClient(client);

        assertNotNull("client field should not be null.", userClient.getClient());
        assertSame("client field is not set properly.", client, userClient.getClient());
    }
}
