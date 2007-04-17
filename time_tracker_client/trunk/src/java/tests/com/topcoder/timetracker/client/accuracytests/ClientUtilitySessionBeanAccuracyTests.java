/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.client.ClientFilterFactory;
import com.topcoder.timetracker.client.depth.ClientIDOnlyDepth;
import com.topcoder.timetracker.client.ejb.ClientUtilityDelegate;
import com.topcoder.timetracker.client.ejb.ClientUtilityLocal;
import com.topcoder.timetracker.client.ejb.ClientUtilityLocalHome;
import com.topcoder.timetracker.client.ejb.ClientUtilitySessionBean;
import com.topcoder.timetracker.project.Project;

/**
 * <p>
 * Accuracy Unit test cases for ClientUtilitySessionBean.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ClientUtilitySessionBeanAccuracyTests extends TestCase {
    /**
     * <p>
     * ClientUtilitySessionBean instance for testing.
     * </p>
     */
    private ClientUtilitySessionBean bean;

    /**
     * <p>
     * ClientUtilityDelegate instance for testing.
     * </p>
     */
    private ClientUtilityDelegate delegate;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        //instance = new ClientUtilitySessionBean();

        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // we use MockTransaction outside of the app server
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        bean = new ClientUtilitySessionBean();

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor(
            "java:com/topcoder/timetracker/client", ClientUtilityLocalHome.class, ClientUtilityLocal.class, bean);

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        delegate = new ClientUtilityDelegate();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        delegate = null;
        bean = null;

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
        return new TestSuite(ClientUtilitySessionBeanAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor ClientUtilitySessionBean#ClientUtilitySessionBean() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create ClientUtilitySessionBean instance.", delegate);
    }

    /**
     * <p>
     * Tests ClientUtilitySessionBean#addClient(Client,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        delegate.addClient(client, true);
        AccuracyTestHelper.assertClients(client, delegate.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilitySessionBean#addClients(Client[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddClients() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        delegate.addClients(new Client[] {client}, true);
        AccuracyTestHelper.assertClients(client, delegate.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilitySessionBean#retrieveClient(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        delegate.addClient(client, true);
        AccuracyTestHelper.assertClients(client, delegate.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilitySessionBean#retrieveClients(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveClients() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        delegate.addClient(client, true);
        AccuracyTestHelper.assertClients(client, delegate.retrieveClients(new long[] {client.getId()})[0]);
    }

    /**
     * <p>
     * Tests ClientUtilitySessionBean#removeClient(long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        delegate.addClient(client, true);
        delegate.removeClient(client.getId(), true);
        assertNull("Failed to remove client.", delegate.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilitySessionBean#removeClients(long[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveClients() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        delegate.addClient(client, true);
        delegate.removeClients(new long[] {client.getId()}, true);
        assertNull("Failed to remove client.", delegate.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilitySessionBean#updateClient(Client,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        delegate.addClient(client, true);

        client.setId(50);
        client.setActive(false);
        delegate.updateClient(client, true);

        AccuracyTestHelper.assertClients(client, delegate.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilitySessionBean#updateClients(Client[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateClients() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        delegate.addClient(client, true);

        client.setId(50);
        client.setActive(false);
        delegate.updateClients(new Client[] {client}, true);

        AccuracyTestHelper.assertClients(client, delegate.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilitySessionBean#getAllClients() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllClients() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        delegate.addClient(client, true);
        AccuracyTestHelper.assertClients(client, delegate.getAllClients()[0]);
    }

    /**
     * <p>
     * Tests ClientUtilitySessionBean#searchClient(Filter,Depth) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        delegate.addClient(client, true);

        Client[] clients = delegate.searchClient(ClientFilterFactory.createCompanyIdFilter(20),
            new ClientIDOnlyDepth());
        assertNotNull("Failed to search client.", clients);
    }

    /**
     * <p>
     * Tests ClientUtilitySessionBean#addProjectToClient(Client,Project,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectToClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        Project project = new Project();
        project.setId(10);

        delegate.addClient(client, true);
        delegate.addProjectToClient(client, project, true);
        client.setProjects(new Project[] {project});

        AccuracyTestHelper.assertClients(client, delegate.retrieveClient(client.getId()));
    }

    /**
     * <p>
     * Tests ClientUtilitySessionBean#removeProjectFromClient(Client,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectFromClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        Project project = new Project();
        project.setId(10);

        delegate.addClient(client, true);
        delegate.addProjectToClient(client, project, true);
        delegate.removeProjectFromClient(client, project.getId(), true);

        assertEquals("Failed to remove project from client.", 1,
            delegate.getAllProjectsOfClient(client.getId()).length);
    }

    /**
     * <p>
     * Tests ClientUtilitySessionBean#getAllProjectsOfClient(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllProjectsOfClient() throws Exception {
        Client client = AccuracyTestHelper.createCient(20);
        Project project = new Project();
        project.setId(10);

        delegate.addClient(client, true);
        delegate.addProjectToClient(client, project, true);

        assertEquals("Failed to get all the project ids.", 20,
            delegate.getAllProjectsOfClient(client.getId())[0].getId());
    }

}