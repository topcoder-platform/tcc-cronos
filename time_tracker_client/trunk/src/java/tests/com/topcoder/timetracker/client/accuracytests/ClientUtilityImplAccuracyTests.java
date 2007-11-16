/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.client.ClientFilterFactory;
import com.topcoder.timetracker.client.ClientUtilityImpl;
import com.topcoder.timetracker.client.depth.ClientIDOnlyDepth;
import com.topcoder.timetracker.project.Project;

/**
 * <p>
 * Accuracy Unit test cases for ClientUtilityImpl.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ClientUtilityImplAccuracyTests extends TestCase {
    /**
     * <p>
     * ClientUtilityImpl instance for testing.
     * </p>
     */
    private ClientUtilityImpl instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        instance = new ClientUtilityImpl();
        AccuracyTestHelper.setUpDatabase();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
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
        return new TestSuite(ClientUtilityImplAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor ClientUtilityImpl#ClientUtilityImpl(String) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception {
        assertNotNull("Failed to create ClientUtilityImpl instance.", new ClientUtilityImpl(
            "com.topcoder.timetracker.client.ClientUtilityImpl"));
    }

    /**
     * <p>
     * Tests ctor ClientUtilityImpl#ClientUtilityImpl() for accuracy.
     * </p>
     */
    public void testCtor2() {
        assertNotNull("Failed to create ClientUtilityImpl instance.", instance);
    }

    /**
     * <p>
     * Tests ClientUtilityImpl#addClient(Client,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddClient() throws Exception {
        Client client = AccuracyTestHelper.createClient(1);
        instance.addClient(client, true);
        AccuracyTestHelper.assertClients(client, instance.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilityImpl#addClients(Client[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddClients() throws Exception {
        Client client = AccuracyTestHelper.createClient(1);
        instance.addClients(new Client[] {client}, true);
        AccuracyTestHelper.assertClients(client, instance.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilityImpl#retrieveClient(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveClient() throws Exception {
        Client client = AccuracyTestHelper.createClient(1);
        instance.addClient(client, true);
        AccuracyTestHelper.assertClients(client, instance.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilityImpl#retrieveClients(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveClients() throws Exception {
        Client client = AccuracyTestHelper.createClient(1);
        instance.addClient(client, true);
        AccuracyTestHelper.assertClients(client, instance.retrieveClients(new long[] {client.getId()})[0]);
    }

    /**
     * <p>
     * Tests ClientUtilityImpl#removeClient(long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveClient() throws Exception {
        Client client = AccuracyTestHelper.createClient(1);
        instance.addClient(client, true);
        instance.removeClient(client.getId(), true);
        assertNull("Failed to remove client.", instance.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilityImpl#removeClients(long[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveClients() throws Exception {
        Client client = AccuracyTestHelper.createClient(1);
        instance.addClient(client, true);
        instance.removeClients(new long[] {client.getId()}, true);
        assertNull("Failed to remove client.", instance.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilityImpl#updateClient(Client,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateClient() throws Exception {
        Client client = AccuracyTestHelper.createClient(1);
        instance.addClient(client, true);

        client.setName("name" + 2);
        client.setActive(false);

        instance.updateClient(client, true);

        AccuracyTestHelper.assertClients(client, instance.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilityImpl#updateClients(Client[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateClients() throws Exception {
        Client client = AccuracyTestHelper.createClient(1);
        instance.addClient(client, true);

        client.setName("name" + 2);
        client.setActive(false);

        instance.updateClients(new Client[] {client}, true);

        AccuracyTestHelper.assertClients(client, instance.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilityImpl#getAllClients() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllClients() throws Exception {
        Client client = AccuracyTestHelper.createClient(1);
        instance.addClient(client, true);
        AccuracyTestHelper.assertClients(client, instance.getAllClients()[0]);
    }

    /**
     * <p>
     * Tests ClientUtilityImpl#searchClient(Filter,Depth) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchClient() throws Exception {
        Client client = AccuracyTestHelper.createClient(1);
        instance.addClient(client, true);

        Client[] clients = instance.searchClient(ClientFilterFactory.createCompanyIdFilter(1),
            new ClientIDOnlyDepth());
        assertNotNull("Failed to search client.", clients[0]);
    }

    /**
     * <p>
     * Tests ClientUtilityImpl#addProjectToClient(Client,Project,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectToClient() throws Exception {
        Client client = AccuracyTestHelper.createClient(1);
        Project project = new Project();
        project.setId(2);

        instance.addClient(client, true);
        instance.addProjectToClient(client, project, true);
        client.setProjects(new Project[] {project});

        AccuracyTestHelper.assertClients(client, instance.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilityImpl#removeProjectFromClient(Client,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectFromClient() throws Exception {
        Client client = AccuracyTestHelper.createClient(1);
        Project project = new Project();
        project.setId(2);

        instance.addClient(client, true);
        instance.addProjectToClient(client, project, true);
        instance.removeProjectFromClient(client, 2, true);

        assertEquals("Failed to remove project from client.", 0, instance.getAllProjectsOfClient(1).length);
    }

    /**
     * <p>
     * Tests ClientUtilityImpl#getAllProjectsOfClient(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllProjectsOfClient() throws Exception {
        Client client = AccuracyTestHelper.createClient(1);
        Project project = new Project();
        project.setId(2);

        instance.addClient(client, true);
        instance.addProjectToClient(client, project, true);

        assertEquals("Failed to get all the project ids.", 1,
            instance.getAllProjectsOfClient(client.getId())[0].getId());
    }

}