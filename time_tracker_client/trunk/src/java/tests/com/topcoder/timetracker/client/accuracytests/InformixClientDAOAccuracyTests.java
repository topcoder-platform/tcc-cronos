/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.client.ClientFilterFactory;
import com.topcoder.timetracker.client.db.InformixClientDAO;
import com.topcoder.timetracker.client.depth.ClientIDOnlyDepth;
import com.topcoder.timetracker.project.Project;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

/**
 * <p>
 * Accuracy Unit test cases for InformixClientDAO.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class InformixClientDAOAccuracyTests extends TestCase {
    /**
     * <p>
     * InformixClientDAO instance for testing.
     * </p>
     */
    private InformixClientDAO instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        instance = new InformixClientDAO();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        instance = null;

        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(InformixClientDAOAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor InformixClientDAO#InformixClientDAO() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create InformixClientDAO instance.", instance);
    }

    /**
     * <p>
     * Tests ctor InformixClientDAO#InformixClientDAO(String) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception {
        assertNotNull("Failed to create InformixClientDAO instance.", new InformixClientDAO(
            "com.topcoder.timetracker.client.db.InformixClientDAO"));
    }

    /**
     * <p>
     * Tests InformixClientDAO#addClient(Client,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        instance.addClient(client, true);
        AccuracyTestHelper.assertClients(client, instance.retrieveClient(20));
    }

    /**
     * <p>
     * Tests InformixClientDAO#addClients(Client[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddClients() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        instance.addClients(new Client[] {client}, true);
        AccuracyTestHelper.assertClients(client, instance.retrieveClient(20));
    }

    /**
     * <p>
     * Tests InformixClientDAO#retrieveClient(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        instance.addClient(client, true);
        AccuracyTestHelper.assertClients(client, instance.retrieveClient(20));
    }

    /**
     * <p>
     * Tests InformixClientDAO#retrieveClients(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveClients() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        instance.addClient(client, true);
        AccuracyTestHelper.assertClients(client, instance.retrieveClients(new long[] {20})[0]);
    }

    /**
     * <p>
     * Tests InformixClientDAO#removeClient(long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        instance.addClient(client, true);
        instance.removeClient(20, true);
        assertNull("Failed to remove client.", instance.retrieveClient(20));
    }

    /**
     * <p>
     * Tests InformixClientDAO#removeClients(long[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveClients() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        instance.addClient(client, true);
        instance.removeClients(new long[] {20}, true);
        assertNull("Failed to remove client.", instance.retrieveClient(20));
    }

    /**
     * <p>
     * Tests InformixClientDAO#updateClient(Client,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        instance.addClient(client, true);

        client.setActive(false);
        instance.updateClient(client, true);

        AccuracyTestHelper.assertClients(client, instance.retrieveClient(20));
    }

    /**
     * <p>
     * Tests InformixClientDAO#updateClients(Client[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateClients() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        instance.addClient(client, true);

        client.setActive(false);
        instance.updateClients(new Client[] {client}, true);

        AccuracyTestHelper.assertClients(client, instance.retrieveClient(20));
    }

    /**
     * <p>
     * Tests InformixClientDAO#getAllClients() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllClients() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        instance.addClient(client, true);
        AccuracyTestHelper.assertClients(client, instance.getAllClients()[0]);
    }

    /**
     * <p>
     * Tests InformixClientDAO#searchClient(Filter,Depth) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        instance.addClient(client, true);

        CustomResultSet result = instance.searchClient(ClientFilterFactory.createCompanyIdFilter(20),
            new ClientIDOnlyDepth());
        assertNotNull("Failed to search client.", result);
    }

    /**
     * <p>
     * Tests InformixClientDAO#addProjectToClient(Client,Project,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectToClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        Project project = new Project();
        project.setId(10);

        instance.addClient(client, true);
        instance.addProjectToClient(client, project, true);
        client.setProjects(new Project[] {project});

        AccuracyTestHelper.assertClients(client, instance.retrieveClient(20));
    }

    /**
     * <p>
     * Tests InformixClientDAO#removeProjectFromClient(Client,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectFromClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        Project project = new Project();
        project.setId(10);

        instance.addClient(client, true);
        instance.addProjectToClient(client, project, true);
        instance.removeProjectFromClient(client, 10, true);

        assertEquals("Failed to remove project from client.", 0, instance.getAllProjectIDsOfClient(20).length);
    }

    /**
     * <p>
     * Tests InformixClientDAO#getAllProjectIDsOfClient(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllProjectIDsOfClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        Project project = new Project();
        project.setId(10);

        instance.addClient(client, true);
        instance.addProjectToClient(client, project, true);

        assertEquals("Failed to get all the project ids.", 10, instance.getAllProjectIDsOfClient(20)[0]);
    }

}