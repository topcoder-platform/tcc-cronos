/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.ejb;

import java.io.File;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.client.ClientFilterFactory;
import com.topcoder.timetracker.client.UnitTestHelper;
import com.topcoder.timetracker.client.depth.ClientIDOnlyDepth;
import com.topcoder.timetracker.project.Project;


/**
 * Unit test for the <code>ClientUtilityDelegate</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ClientUtilityDelegateUnitTests extends TestCase {
    /** Represents the private instance used for test. */
    private ClientUtilityDelegate delegate = null;

    /** Represents an instance of DBConnectionFactory class. */
    private DBConnectionFactory dbFactory = null;

    /**
     * Set up the environment.
     *
     * @throws Exception any exception to JUnit
     */
    protected void setUp() throws Exception {
        //setup the JNDI environment.
        MockContextFactory.setAsInitial();

        //Create the initial context that will be used for binding EJBs
        Context ctx = new InitialContext();

        //  Create an instance of the MockContainer
        MockContainer mc = new MockContainer(ctx);

        SessionBeanDescriptor dd = new SessionBeanDescriptor("java:com/topcoder/timetracker/client",
                ClientUtilityLocalHome.class, ClientUtilityLocal.class, new ClientUtilitySessionBean());

        mc.deploy(dd);

        // we use MockTransaction outside of the app server.
        MockUserTransaction mockTransaction = new MockUserTransaction();
        ctx.rebind("javax.transaction.UserTransaction", mockTransaction);

        UnitTestHelper.clearConfig();
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_strategy.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_bundle.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "InformixClientDAO_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "ClientUtilityImpl_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "cu_factory_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "cu_delegate_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "JNDIUtils.xml");
        dbFactory = new DBConnectionFactoryImpl(UnitTestHelper.DB_FACTORY_NAMESPACE);

        delegate = new ClientUtilityDelegate();

        UnitTestHelper.setUpDatabase(dbFactory, "informix_connect");
    }

    /**
     * Tear down the environment.
     *
     * @throws Exception to Junit
     */
    protected void tearDown() throws Exception {
        UnitTestHelper.clearConfig();
        UnitTestHelper.clearDatabase(dbFactory, "informix_connect");
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
            delegate.addClient(null, false);
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
        delegate.addClient(UnitTestHelper.createCient(1), false);

        Client[] clients = delegate.getAllClients();

        assertEquals("There is only one client.", 1, clients.length);

        Client client = delegate.retrieveClient(clients[0].getId());

        assertEquals("The client get should have the set id.", clients[0].getId(), client.getId());
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
            delegate.addClients(null, false);
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
            delegate.addClients(new Client[] { UnitTestHelper.createCient(1), null }, false);
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
        delegate.addClients(new Client[] { UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client[] clients = delegate.getAllClients();

        assertEquals("Two clients have been added.", 2, clients.length);

        for (int i = 0; i < clients.length; i++) {
            assertNotNull("The contact should be get.", clients[i].getContact());
            assertNotNull("The payment term should be get.", clients[i].getPaymentTerm());
            assertNotNull("The address should be get.", clients[i].getAddress());
            assertNotNull("The project should be get.", clients[i].getProjects());
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
            delegate.removeClient(0, false);
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
            delegate.removeClient(-1, false);
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
        delegate.addClients(new Client[] { UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client[] clients = delegate.getAllClients();

        assertEquals("Two clients have been added.", 2, clients.length);

        // NOTE : should only be called when the project utility is not mock
        /*
        delegate.removeClient(clients[0].getId(), false);

        clients = delegate.getAllClients();

        assertEquals("One client left.", 1, clients.length);*/
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
            delegate.removeClients(null, false);
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
            delegate.removeClients(new long[] { 1, -1 }, false);
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
        delegate.addClients(new Client[] { UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client[] clients = delegate.getAllClients();

        assertEquals("Two clients have been added.", 2, clients.length);

        // NOTE: remove should only be called when project utility is not mock
        /*
        delegate.removeClients(new long[] { clients[0].getId() }, false);

        clients = delegate.getAllClients();

        assertEquals("One client left.", 1, clients.length);*/
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
            delegate.retrieveClient(0);
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
            delegate.retrieveClient(-1);
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
        delegate.addClient(UnitTestHelper.createCient(1), false);

        Client[] clients = delegate.getAllClients();

        assertEquals("There is only one client.", 1, clients.length);

        Client client = delegate.retrieveClient(clients[0].getId());

        assertEquals("The client get should have the set id.", clients[0].getId(), client.getId());
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
            delegate.retrieveClients(null);
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
            delegate.retrieveClients(new long[] { 1, -1 });
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
        delegate.addClient(UnitTestHelper.createCient(1), false);

        Client[] clients = delegate.getAllClients();

        assertEquals("There is only one client.", 1, clients.length);

        Client[] getClients = delegate.retrieveClients(new long[] { clients[0].getId() });

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
            delegate.updateClient(null, false);
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
        delegate.addClient(UnitTestHelper.createCient(1), false);

        Client[] clients = delegate.getAllClients();

        clients[0].setName("new name");

        delegate.updateClient(clients[0], false);

        Client client = delegate.retrieveClient(clients[0].getId());

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
            delegate.updateClients(null, false);
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
            delegate.updateClients(new Client[] { UnitTestHelper.createCient(1), null }, false);
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
        delegate.addClient(UnitTestHelper.createCient(1), false);

        Client[] clients = delegate.getAllClients();

        clients[0].setName("new name");

        delegate.updateClients(new Client[] { clients[0] }, false);

        Client client = delegate.retrieveClient(clients[0].getId());

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
        delegate.addClients(new Client[] { UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client[] clients = delegate.getAllClients();

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
            delegate.searchClient(null, new ClientIDOnlyDepth());
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
            delegate.searchClient(ClientFilterFactory.createActiveFilter(true), null);
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
        delegate.searchClient(ClientFilterFactory.createActiveFilter(true), new ClientIDOnlyDepth());
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
            delegate.getAllProjectsOfClient(0);
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
            delegate.getAllProjectsOfClient(-1);
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
        delegate.addClients(new Client[] { UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client[] clients = delegate.getAllClients();

        Project[] projects = delegate.getAllProjectsOfClient(clients[0].getId());

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
            delegate.removeProjectFromClient(null, 1, false);
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
            delegate.removeProjectFromClient(UnitTestHelper.createCient(1), -1, false);
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
        delegate.addClients(new Client[] { UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client[] clients = delegate.getAllClients();

        Project[] projects = delegate.getAllProjectsOfClient(clients[0].getId());

        assertEquals("The project with the first client is 2.", 2, projects.length);

        delegate.removeProjectFromClient(clients[0], projects[0].getId(), false);

        projects = delegate.getAllProjectsOfClient(clients[0].getId());

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
            delegate.addProjectToClient(null, new Project(), false);
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
            delegate.addProjectToClient(UnitTestHelper.createCient(1), null, false);
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
        delegate.addClients(new Client[] { UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client[] clients = delegate.getAllClients();

        Project[] projects = delegate.getAllProjectsOfClient(clients[0].getId());

        assertEquals("The project with the first client is 2.", 2, projects.length);

        delegate.removeProjectFromClient(clients[0], projects[0].getId(), false);

        Project projectToAdd = projects[0];
        projects = delegate.getAllProjectsOfClient(clients[0].getId());

        assertEquals("The project with the first client is 1.", 1, projects.length);

        // add the project that's deleted
        delegate.addProjectToClient(clients[0], projectToAdd, false);

        projects = delegate.getAllProjectsOfClient(clients[0].getId());

        assertEquals("The project with the client is 2.", 2, projects.length);
    }
}
