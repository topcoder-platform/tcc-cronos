/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.client.depth.ClientIDOnlyDepth;
import com.topcoder.timetracker.project.Project;


/**
 * Unit test for the <code>ClientUtilityImpl</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ClientUtilityImplUnitTests extends TestCase {
    /** Represents the private instance used for test. */
    private ClientUtilityImpl impl = null;

    /** DB connection factory. */
    private DBConnectionFactory dbFactory = null;

    /**
     * Set up the test.
     *
     * @throws Exception to Junit
     */
    protected void setUp() throws Exception {
        UnitTestHelper.clearConfig();

        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_strategy.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_bundle.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "InformixClientDAO_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "ClientUtilityImpl_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");
        impl = new ClientUtilityImpl();

        dbFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        UnitTestHelper.clearDatabase(dbFactory, "informix_connect");
    }

    /**
     * Clear the test.
     *
     * @throws Exception to Junit
     */
    protected void tearDown() throws Exception {
        UnitTestHelper.clearDatabase(dbFactory, "informix_connect");
        UnitTestHelper.clearConfig();
    }

    /**
     * <p>
     * Accuracy test for Inheritance.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testtestInheritanceAccuracy() throws Exception {
        assertTrue("ClientUtilityImpl should inherit from ClientUtility.", impl instanceof ClientUtility);
    }

    /**
     * <p>
     * Accuracy test for Constructor.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("The instance should be created.", impl);
    }

    /**
     * <p>
     * Failure test for method <code>addClient</code>. Add with null is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddClientWith() throws Exception {
        try {
            impl.addClient(null, false);
            fail("Add with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>addClient</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddClientAccuracy() throws Exception {
        Client toAdd = UnitTestHelper.createCient(1);
        impl.addClient(toAdd, false);

        Client[] clients = impl.getAllClients();

        assertEquals("There is only one client.", 1, clients.length);

        Client client = impl.retrieveClient(clients[0].getId());

        assertEquals("The client should be equal.", toAdd, client);
    }

    /**
     * <p>
     * Failure test for method <code>addClients</code>. Add with null is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddClientsWithNull() throws Exception {
        try {
            impl.addClients(null, false);
            fail("Add with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>addClients</code>. Add with illegal array is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddClientsWithIvalid() throws Exception {
        try {
            impl.addClients(new Client[] {UnitTestHelper.createCient(1), null}, false);
            fail("Add with illegal array is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>addClients</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddClientsAccuracy() throws Exception {
        impl.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2)}, false);

        Client[] clients = impl.getAllClients();

        assertEquals("Two clients have been added.", 2, clients.length);

        for (int i = 0; i < clients.length; i++) {
            assertNotNull("The contact should be get.", clients[i].getContact());
            assertNotNull("The payment term should be get.", clients[i].getPaymentTerm());
            assertNotNull("The address should be get.", clients[i].getAddress());
            assertEquals("The project should be get.", 2, clients[i].getProjects().length);
        }
    }

    /**
     * <p>
     * Failure test for method <code>removeClient</code>. Remove with zero is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremoveClientWithZero() throws Exception {
        try {
            impl.removeClient(0, false);
            fail("Remove with zero is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>removeClient</code>. Remove with negative is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremoveClientWithNegative() throws Exception {
        try {
            impl.removeClient(-1, false);
            fail("Remove with negative is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>removeClient</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremoveClientAccuracy() throws Exception {
        impl.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2)}, false);

        Client[] clients = impl.getAllClients();

        assertEquals("Two clients have been added.", 2, clients.length);

        impl.removeClient(clients[0].getId(), false);

        clients = impl.getAllClients();

        assertEquals("One client left.", 1, clients.length);

        // make sure the projects is removed.
        assertEquals("No projects with the client.", 0, impl.getAllProjectsOfClient(1).length);
    }

    /**
     * <p>
     * Failure test for method <code>removeClients</code>. Remove with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremoveClientsWithNull() throws Exception {
        try {
            impl.removeClients(null, false);
            fail("Remove with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>removeClients</code>. Remove with negative is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremoveClientsWithInvalid() throws Exception {
        try {
            impl.removeClients(new long[] {1, -1}, false);
            fail("Remove with negative is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>removeClients</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremoveClientsAccuracy() throws Exception {
        impl.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2)}, false);

        Client[] clients = impl.getAllClients();

        assertEquals("Two clients have been added.", 2, clients.length);

        impl.removeClients(new long[] {clients[0].getId()}, false);

        clients = impl.getAllClients();

        assertEquals("One client left.", 1, clients.length);
    }

    /**
     * <p>
     * Failure test for method <code>retrieveClient</code>. Retrieve with zero is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveClientWithZero() throws Exception {
        try {
            impl.retrieveClient(0);
            fail("Retrieve with zero is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>retrieveClient</code>. Retrieve with negative is illegal. IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveClientWith() throws Exception {
        try {
            impl.retrieveClient(-1);
            fail("Retrieve with negative is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>retrieveClient</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveClientAccuracy() throws Exception {
        Client toAdd = UnitTestHelper.createCient(1);
        impl.addClient(toAdd, false);

        Client[] clients = impl.getAllClients();

        assertEquals("There is only one client.", 1, clients.length);

        Client client = impl.retrieveClient(clients[0].getId());

        assertTrue("Two client should be equal.", UnitTestHelper.isClientEquals(toAdd, client));
    }

    /**
     * <p>
     * Failure test for method <code>retrieveClients</code>. Retrieve with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveClientsWithNull() throws Exception {
        try {
            impl.retrieveClients(null);
            fail("Retrieve with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>retrieveClients</code>. Retrieve with invalid is illegal. IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveClientsWith() throws Exception {
        try {
            impl.retrieveClients(new long[] {1, -1});
            fail("Retrieve with invalid is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>retrieveCliets</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveClietsAccuracy() throws Exception {
        impl.addClient(UnitTestHelper.createCient(1), false);

        Client[] clients = impl.getAllClients();

        assertEquals("There is only one client.", 1, clients.length);

        Client[] getClients = impl.retrieveClients(new long[] {clients[0].getId()});

        assertEquals("The clients is of length 1.", 1, getClients.length);
        assertEquals("The client get should have the set id.", clients[0].getId(), getClients[0].getId());
    }

    /**
     * <p>
     * Failure test for method <code>updateClient</code>. Update with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateClientWithNull() throws Exception {
        try {
            impl.updateClient(null, false);
            fail("Update with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>updateClient</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateClientAccuracy() throws Exception {
        impl.addClient(UnitTestHelper.createCient(1), false);

        Client[] clients = impl.getAllClients();

        clients[0].setName("new name");

        impl.updateClient(clients[0], false);

        Client client = impl.retrieveClient(clients[0].getId());

        assertEquals("The client have been updated.", "new name", client.getName());
    }

    /**
     * <p>
     * Failure test for method <code>updateClients</code>. Update with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateClientsWithNull() throws Exception {
        try {
            impl.updateClients(null, false);
            fail("Update with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>updateClients</code>. Update with invalid is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateClientsWithInvalid() throws Exception {
        try {
            impl.updateClients(new Client[] {UnitTestHelper.createCient(1), null}, false);
            fail("Update with invalid is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>updateClients</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateClientsAccuracy() throws Exception {
        impl.addClient(UnitTestHelper.createCient(1), false);

        Client[] clients = impl.getAllClients();

        clients[0].setName("new name");

        impl.updateClients(new Client[] {clients[0]}, false);

        Client client = impl.retrieveClient(clients[0].getId());

        assertEquals("The client have been updated.", "new name", client.getName());
    }

    /**
     * <p>
     * Accuracy test for method <code>getAllClients</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetAllClientsAccuracy() throws Exception {
        impl.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2)}, false);

        Client[] clients = impl.getAllClients();

        assertEquals("There are 2 clients.", 2, clients.length);
    }

    /**
     * <p>
     * Failure test for method <code>searchClients</code>. Search with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchClientsWithNull1() throws Exception {
        try {
            impl.searchClient(null, new ClientIDOnlyDepth());
            fail("Search with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>searchClients</code>. Search with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchClientsWithNull2() throws Exception {
        try {
            impl.searchClient(ClientFilterFactory.createActiveFilter(true), null);
            fail("Search with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>searchClient</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchClientAccuracy() throws Exception {
        impl.searchClient(ClientFilterFactory.createActiveFilter(true), new ClientIDOnlyDepth());
    }

    /**
     * <p>
     * Failure test for method <code>getAllProjectsOfClient</code>. Get with zero is illegal. IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetAllProjectsOfClientWithZero() throws Exception {
        try {
            impl.getAllProjectsOfClient(0);
            fail("Get with zero is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>getAllProjectsOfClient</code>. Get with negative is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetAllProjectsOfClientWithNegative()
        throws Exception {
        try {
            impl.getAllProjectsOfClient(-1);
            fail("Get with negative is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>getAllProectsOfClient</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetAllProectsOfClientAccuracy() throws Exception {
        impl.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2)}, false);

        Client[] clients = impl.getAllClients();

        Project[] projects = impl.getAllProjectsOfClient(clients[0].getId());

        assertEquals("The project with the first client is 2.", 2, projects.length);
    }

    /**
     * <p>
     * Failure test for method <code>remvoeProjectFromClient</code>. Remove null client is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremvoeProjectFromClientWithNull() throws Exception {
        try {
            impl.removeProjectFromClient(null, 1, false);
            fail("Remove null client is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>remvoeProjectFromClient</code>. Remove with negative is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremvoeProjectFromClientWithNegative()
        throws Exception {
        try {
            impl.removeProjectFromClient(UnitTestHelper.createCient(1), -1, false);
            fail("Remove with negative is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>removeProjectFromClient</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremoveProjectFromClientAccuracy() throws Exception {
        impl.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2)}, false);

        Client[] clients = impl.getAllClients();

        Project[] projects = impl.getAllProjectsOfClient(clients[0].getId());

        assertEquals("The project with the first client is 2.", 2, projects.length);

        impl.removeProjectFromClient(clients[0], projects[0].getId(), false);

        projects = impl.getAllProjectsOfClient(clients[0].getId());

        assertEquals("The project with the first client is 1.", 1, projects.length);
    }

    /**
     * <p>
     * Failure test for method <code>addProjectToClient</code>. Add with null client is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddProjectToClientWithNull1() throws Exception {
        try {
            impl.addProjectToClient(null, new Project(), false);
            fail("Add with null client is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>addProjectToClient</code>. Add with null client is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddProjectToClientWithNull2() throws Exception {
        try {
            impl.addProjectToClient(UnitTestHelper.createCient(1), null, false);
            fail("Add with null client is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>addProjectToClient</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddProjectToClientAccuracy() throws Exception {
        impl.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2)}, false);

        Client[] clients = impl.getAllClients();

        Project[] projects = impl.getAllProjectsOfClient(clients[0].getId());

        assertEquals("The project with the first client is 2.", 2, projects.length);

        impl.removeProjectFromClient(clients[0], projects[0].getId(), false);

        Project projectToAdd = projects[0];
        projects = impl.getAllProjectsOfClient(clients[0].getId());

        assertEquals("The project with the first client is 1.", 1, projects.length);

        // add the project that's deleted
        impl.addProjectToClient(clients[0], projectToAdd, false);

        projects = impl.getAllProjectsOfClient(clients[0].getId());

        assertEquals("The project with the client is 2.", 2, projects.length);
    }
}
