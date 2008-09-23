/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link Client}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>Client</code> instance used in tests.
     * </p>
     */
    private Client client;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ClientUnitTests.class);
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

        client = new Client();
    }

    /**
     * <p>
     * Unit test for <code>{@link Client#Client()}</code> constructor.
     * </p>
     * <p>
     * All the fields are set by default value.
     * </p>
     */
    public void testClient1_accuracy() {
        client = new Client();

        assertNotNull("Instance should be always created.", client);
        assertNull("clientId field should default to null.", client.getClientId());
        assertNull("name field should default to null.", client.getName());
        assertNotNull("compClients field should not null.", client.getCompClients());
        assertTrue("compClients set should be empty.", client.getCompClients().isEmpty());
        assertNotNull("userClients field should not null.", client.getUserClients());
        assertTrue("userClients set should be empty.", client.getUserClients().isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>{@link Client#Client(Integer, String)}</code> constructor.
     * </p>
     * <p>
     * client id and client name should be set, properly.
     * </p>
     */
    public void testClient2_accuracy() {
        client = new Client(1, "ivern");

        assertNotNull("Instance should be always created.", client);
        assertEquals("clientId field is not set properly.", new Integer(1), client.getClientId());
        assertEquals("name field is not set properly.", "ivern", client.getName());
        assertNotNull("compClients field should not null.", client.getCompClients());
        assertTrue("compClients set should be empty.", client.getCompClients().isEmpty());
        assertNotNull("userClients field should not null.", client.getUserClients());
        assertTrue("userClients set should be empty.", client.getUserClients().isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>{@link Client#Client(Integer, String, Set, Set)}</code> constructor.
     * </p>
     * <p>
     * All fields should be set properly.
     * </p>
     */
    public void testClient3_accuracy() {
        client = new Client(1, "ivern", new HashSet<CompClient>(), new HashSet<UserClient>());

        assertNotNull("Instance should be always created.", client);
        assertEquals("clientId field is not set properly.", new Integer(1), client.getClientId());
        assertEquals("name field is not set properly.", "ivern", client.getName());
        assertNotNull("compClients field should not null.", client.getCompClients());
        assertTrue("compClients set should be empty.", client.getCompClients().isEmpty());
        assertNotNull("userClients field should not null.", client.getUserClients());
        assertTrue("userClients set should be empty.", client.getUserClients().isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>{@link Client#getClientId()}</code> method.
     * </p>
     * <p>
     * Should return the default client id (null).
     * </p>
     */
    public void testGetClientId_default() {
        assertNull("clientId field should default to null.", client.getClientId());
    }

    /**
     * <p>
     * Unit test for <code>{@link Client#setClientId(Integer)}</code> method.
     * </p>
     * <p>
     * The client id should always be set internally.
     * </p>
     */
    public void testSetClientId_accuracy() {
        client.setClientId(1);

        assertEquals("clientId field is not set properly.", new Integer(1), client.getClientId());
    }

    /**
     * <p>
     * Unit test for <code>{@link Client#getName()}</code> method.
     * </p>
     * <p>
     * Should return the default client name - null.
     * </p>
     */
    public void testGetName_default() {
        assertNull("name field should default to null.", client.getName());
    }

    /**
     * <p>
     * Unit test for <code>{@link Client#setName(String)}</code> method.
     * </p>
     * <p>
     * Should return the client name - previously set.
     * </p>
     */
    public void testSetName_accuracy() {
        client.setName("ivern");

        assertEquals("name field is not set properly.", "ivern", client.getName());
    }

    /**
     * <p>
     * Unit test for <code>{@link Client#getCompClients()}</code>
     * </p>
     * <p>
     * Should return the default value of compClients.
     * </p>
     */
    public void testGetCompClients_default() {
        assertNotNull("compClients field should not null.", client.getCompClients());
        assertTrue("compClients set should be empty.", client.getCompClients().isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>{@link Client#setCompClients(Set)}</code>
     * </p>
     * <p>
     * Should return the value of compClients.
     * </p>
     */
    public void testSetCompClients_accuracy() {
        Set<CompClient> compClients = new HashSet<CompClient>();
        compClients.add(new CompClient());

        client.setCompClients(compClients);

        assertNotNull("compClients field should not null.", client.getCompClients());
        assertEquals("compClients set contains one element.", 1, client.getCompClients().size());
    }

    /**
     * <p>
     * Unit test for <code>{@link Client#getUserClients()}</code>
     * </p>
     * <p>
     * Should return the default value of userClients.
     * </p>
     */
    public void testGetUserClients_default() {
        assertNotNull("userClients field should not null.", client.getUserClients());
        assertTrue("userClients set should be empty.", client.getUserClients().isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>{@link Client#setUserClients(Set)}</code>
     * </p>
     * <p>
     * Should return the value of userClients.
     * </p>
     */
    public void testSetUserClients_accuracy() {
        Set<UserClient> userClients = new HashSet<UserClient>();
        userClients.add(new UserClient());

        client.setUserClients(userClients);

        assertNotNull("userClients field should not null.", client.getUserClients());
        assertEquals("userClients set contains one element.", 1, client.getUserClients().size());
    }
}
