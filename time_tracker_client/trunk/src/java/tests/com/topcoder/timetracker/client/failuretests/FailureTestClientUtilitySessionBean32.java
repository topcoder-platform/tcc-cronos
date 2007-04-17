/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.failuretests;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.client.ClientPersistenceException;
import com.topcoder.timetracker.client.ClientUtility;
import com.topcoder.timetracker.client.depth.ClientIDOnlyDepth;
import com.topcoder.timetracker.client.ejb.ClientUtilityLocal;
import com.topcoder.timetracker.client.ejb.ClientUtilityLocalHome;
import com.topcoder.timetracker.client.ejb.ClientUtilitySessionBean;
import com.topcoder.timetracker.project.Project;
/**
 * This class contains unit tests for <code>ClientUtilitySessionBean</code> class.
 *
 * @author TCSDEVELOPER
 * @versio 3.2
 */
public class FailureTestClientUtilitySessionBean32 extends TestCase {
    
    /** Represents the private instance used for test. */
    private ClientUtility impl = null;
    
    /**
     * Set Up the test environment before testing.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        //setup the JNDI environment.
        MockContextFactory.setAsInitial();

        //Create the initial context that will be used for binding EJBs
        Context ctx = new InitialContext();

        //  Create an instance of the MockContainer
        MockContainer mc = new MockContainer(ctx);

        SessionBeanDescriptor dd = new SessionBeanDescriptor("java:com/topcoder/timetracker/client",
                ClientUtilityLocalHome.class, ClientUtilityLocal.class,
                new ClientUtilitySessionBean());

        mc.deploy(dd);

        // we use MockTransaction outside of the app server.
        MockUserTransaction mockTransaction = new MockUserTransaction();
        ctx.rebind("javax.transaction.UserTransaction", mockTransaction);

        FailureTestHelper.removeNamespaces();
        FailureTestHelper.loadConfig("test_files/failure/conf/db_config.xml");
        FailureTestHelper.loadConfig("test_files/failure/conf/search_strategy.xml");
        FailureTestHelper.loadConfig("test_files/failure/conf/search_bundle.xml");
        FailureTestHelper.loadConfig("test_files/failure/conf/InformixClientDAO_config.xml");
        FailureTestHelper.loadConfig("test_files/failure/conf/ClientUtilityImpl_config.xml");
        FailureTestHelper.loadConfig("test_files/failure/conf/object_factory_config.xml");
        
        // Look up the home
        Context ctx2 = new InitialContext();
        Object ejbObj = ctx2.lookup("java:com/topcoder/timetracker/client");

        ClientUtilityLocalHome home = (ClientUtilityLocalHome) PortableRemoteObject.narrow(ejbObj,
                ClientUtilityLocalHome.class);

        impl = home.create();
    }

    /**
     * Clean up the test environment after testing.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.removeNamespaces();
    }
    
    /**
     * Tests <code>addClient(Client client, boolean doAudit)</code> method
     * for failure with invalid ClientUtility.
     * ClientPersistenceException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testAddClient_InvalidClientUtility() throws Exception {
        try {
        	impl.addClient(FailureTestHelper.createCient(1), true);
            fail("testAddClient_InvalidClientUtility is failure.");
        } catch (ClientPersistenceException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>addClient(Client[] clients, boolean doAudit)</code> method
     * for failure with invalid ClientUtility.
     * ClientPersistenceException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testAddClients_InvalidClientUtility() throws Exception {
        try {
        	impl.addClients(new Client[] {FailureTestHelper.createCient(1)}, true);
            fail("testAddClients_InvalidClientUtility is failure.");
        } catch (ClientPersistenceException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>removeClient(long id, boolean doAudit)</code> method
     * for failure with invalid ClientUtility.
     * ClientPersistenceException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testRemoveClient_InvalidClientUtility() throws Exception {
        try {
        	impl.removeClient(1, true);
            fail("testRemoveClient_InvalidClientUtility is failure.");
        } catch (ClientPersistenceException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>removeClients(long[] ids, boolean doAudit)</code> method
     * for failure with invalid ClientUtility.
     * ClientPersistenceException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testRemoveClients_InvalidClientUtility() throws Exception {
        try {
        	impl.removeClients(new long[]{1}, true);
            fail("testRemoveClient_InvalidClientUtility is failure.");
        } catch (ClientPersistenceException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>retrieveClient(long id)</code> method
     * for failure with invalid ClientUtility.
     * ClientPersistenceException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testRetrieveClient_InvalidClientUtility() throws Exception {
        try {
        	impl.retrieveClient(1);
            fail("testRetrieveClient_InvalidClientUtility is failure.");
        } catch (ClientPersistenceException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>retrieveClients(long[] ids)</code> method
     * for failure with invalid ClientUtility.
     * ClientPersistenceException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testRetrieveClients_InvalidClientUtility() throws Exception {
        try {
        	impl.retrieveClients(new long[]{1});
            fail("testRetrieveClients_InvalidClientUtility is failure.");
        } catch (ClientPersistenceException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>updateClient(Client client, boolean doAudit)</code> method
     * for failure with invalid ClientUtility.
     * ClientPersistenceException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testUpdateClient_InvalidClientUtility() throws Exception {
        try {
        	impl.updateClient(FailureTestHelper.createCient(1), false);
            fail("testUpdateClient_InvalidClientUtility is failure.");
        } catch (ClientPersistenceException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>updateClients(Client[] clients, boolean doAudit)</code> method
     * for failure with invalid ClientUtility.
     * ClientPersistenceException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testUpdateClients_InvalidClientUtility() throws Exception {
        try {
        	impl.updateClients(new Client[]{FailureTestHelper.createCient(1)}, false);
            fail("testUpdateClients_InvalidClientUtility is failure.");
        } catch (ClientPersistenceException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>searchClient(Filter filter, Depth depth)</code> method
     * for failure with invalid ClientUtility.
     * ClientPersistenceException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testSearchClient_InvalidClientUtility() throws Exception {
        try {
        	impl.searchClient(new LessThanOrEqualToFilter("1", "a"), new ClientIDOnlyDepth());
            fail("testRetrieveClient_InvalidClientUtility is failure.");
        } catch (ClientPersistenceException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>getAllProjectsOfClient(long clientId)</code> method
     * for failure with invalid ClientUtility.
     * ClientPersistenceException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetAllProjectsOfClient_InvalidClientUtility() throws Exception {
        try {
        	impl.getAllProjectsOfClient(1);
            fail("testGetAllProjectsOfClient_InvalidClientUtility is failure.");
        } catch (ClientPersistenceException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>removeProjectFromClient(Client client, long projectId, boolean doAudit)</code> method
     * for failure with invalid ClientUtility.
     * ClientPersistenceException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testRemoveProjectFromClient_InvalidClientUtility() throws Exception {
        try {
        	impl.removeProjectFromClient(FailureTestHelper.createCient(1), 1, false);
            fail("testRemoveProjectFromClient_InvalidClientUtility is failure.");
        } catch (ClientPersistenceException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>addProjectToClient(Client client, Project project, boolean doAudit)</code> method
     * for failure with invalid ClientUtility.
     * ClientPersistenceException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testAddProjectToClient_InvalidClientUtility() throws Exception {
        try {
        	impl.addProjectToClient(FailureTestHelper.createCient(1), new Project(), false);
            fail("testAddProjectToClient_InvalidClientUtility is failure.");
        } catch (ClientPersistenceException iae) {
            // pass
        }
    }
}