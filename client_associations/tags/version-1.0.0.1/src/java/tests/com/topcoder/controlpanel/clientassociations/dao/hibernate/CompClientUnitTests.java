/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link CompClient}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompClientUnitTests extends TestCase {
    /**
     * <p>
     * Represents the <code>CompClient</code> instance used in tests.
     * </p>
     */
    private CompClient compClient;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(CompClientUnitTests.class);
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

        compClient = new CompClient();
    }

    /**
     * <p>
     * Unit test for <code>{@link CompClient#CompClient()}</code> constructor.
     * </p>
     * <p>
     * All the fields has default value.
     * </p>
     */
    public void testCompClient1_accuracy() {
        compClient = new CompClient();

        assertNotNull("Instance should be always created.", compClient);
        assertNull("comp_id field should be null.", compClient.getComp_id());
        assertNull("client field should be null.", compClient.getClient());
    }

    /**
     * <p>
     * Unit test for <code>{@link CompClient#CompClient(CompClientPK)}</code> constructor.
     * </p>
     * <p>
     * The passed primary key should be set properly.
     * </p>
     */
    public void testCompClient2_accuracy() {
        CompClientPK compClientPK = new CompClientPK();

        compClient = new CompClient(compClientPK);

        assertNotNull("Instance should be always created.", compClient);
        assertNotNull("comp_id field should not be null.", compClient.getComp_id());
        assertSame("comp_id field is not set properly.", compClientPK, compClient.getComp_id());
        assertNull("client field should be null.", compClient.getClient());
    }

    /**
     * <p>
     * Unit test for <code>{@link CompClient#CompClient(CompClientPK, Client)}</code> constructor.
     * </p>
     * <p>
     * The passed primary key and client should be set properly.
     * </p>
     */
    public void testCompClient3_accuracy() {
        CompClientPK compClientPK = new CompClientPK();
        Client client = new Client();

        compClient = new CompClient(compClientPK, client);

        assertNotNull("Instance should be always created.", compClient);
        assertNotNull("comp_id field should not be null.", compClient.getComp_id());
        assertSame("comp_id field is not set properly.", compClientPK, compClient.getComp_id());
        assertNotNull("client field should not be null.", compClient.getClient());
        assertSame("client field is not set properly.", client, compClient.getClient());
    }

    /**
     * <p>
     * Unit test for <code>{@link CompClient#getComp_id()}</code> method.
     * </p>
     * <p>
     * Should return the default primary key - null.
     * </p>
     */
    public void testGetComp_id_default() {
        assertNull("comp_id field should be null.", compClient.getComp_id());
    }

    /**
     * <p>
     * Unit test for <code>{@link CompClient#setComp_id(CompClientPK)}</code> method.
     * </p>
     * <p>
     * The passed to comp_id should be set properly.
     * </p>
     */
    public void testSetComp_id_accuracy() {
        CompClientPK compClientPK = new CompClientPK();

        compClient.setComp_id(compClientPK);

        assertNotNull("comp_id field should not be null.", compClient.getComp_id());
        assertSame("comp_id field is not set properly.", compClientPK, compClient.getComp_id());
    }

    /**
     * <p>
     * Unit test for <code>{@link CompClient#getClient()}</code> method.
     * </p>
     * <p>
     * Should return the default client - null.
     * </p>
     */
    public void testGetClient_default() {
        assertNull("client field should be null.", compClient.getClient());
    }

    /**
     * <p>
     * Unit test for <code>{@link CompClient#setClient(Client)}</code> method.
     * </p>
     * <p>
     * The passed to client should be set properly.
     * </p>
     */
    public void testSetClient_accuracy() {
        Client client = new Client();

        compClient.setClient(client);

        assertNotNull("client field should not be null.", compClient.getClient());
        assertSame("client field is not set properly.", client, compClient.getClient());
    }
}
