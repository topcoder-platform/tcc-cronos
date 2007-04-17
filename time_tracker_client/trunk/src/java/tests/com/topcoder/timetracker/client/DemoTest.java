/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import java.io.File;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.client.depth.ClientIDOnlyDepth;
import com.topcoder.timetracker.client.ejb.ClientUtilityDelegate;
import com.topcoder.timetracker.client.ejb.ClientUtilityLocal;
import com.topcoder.timetracker.client.ejb.ClientUtilityLocalHome;
import com.topcoder.timetracker.client.ejb.ClientUtilitySessionBean;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;
import com.topcoder.timetracker.project.Project;


/**
 * Demo for the component.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DemoTest extends TestCase {
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
        //      setup the JNDI environment.
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
        UnitTestHelper.clearConfig();
        UnitTestHelper.clearDatabase(dbFactory, "informix_connect");
    }

    /**
     * Demo the usage of the ClientUtility which is the main class of the component.
     *
     * @throws Exception to JUnit
     */
    public void testDemoUtility() throws Exception {
        Client[] clients = new Client[2];

        // create the clients
        for (int i = 0; i < 2; i++) {
            clients[i] = new Client();

            clients[i].setActive(true);
            clients[i].setAddress(new Address());
            clients[i].setChanged(true);
            clients[i].setCompanyId(i + 1);
            clients[i].setContact(new Contact());
            clients[i].setCreationDate(new Date());
            clients[i].setCreationUser("creationUser");
            clients[i].setEndDate(new Date());
            clients[i].setId(i + 1);
            clients[i].setModificationDate(new Date());
            clients[i].setModificationUser("modificationUser");
            clients[i].setName("userName");

            PaymentTerm term = new PaymentTerm();
            term.setId(i + 1);
            clients[i].setPaymentTerm(term);
            clients[i].setSalesTax(0.1);
            clients[i].setStartDate(new Date());

            Contact contact = new Contact();
            contact.setId(i + 1);
            clients[i].setContact(contact);

            Address address = new Address();
            address.setId(1);
            clients[i].setId(i + 1);
            clients[i].setAddress(address);

            Project project1 = new Project();
            project1.setId(i + 77);

            Project project2 = new Project();
            project2.setId(i + 144);

            clients[i].setProjects(new Project[] {project1, project2});
        }

        // add the clients
        impl.addClients(clients, false);

        // retrieve the clients
        Client[] getClients = impl.retrieveClients(new long[] {1, 2});

        // get all clients
        getClients = impl.getAllClients();

        // update the clients
        getClients[0].setActive(false);
        impl.updateClients(getClients, false);

        // remove the clients
        impl.removeClient(getClients[0].getId(), false);

        // search the clients
        impl.searchClient(ClientFilterFactory.createActiveFilter(true), new ClientIDOnlyDepth());
    }

    /**
     * Demo the usage of EJB layer.
     *
     * @throws Exception to JUnit
     */
    public void testDelegate() throws Exception {
        ClientUtilityDelegate delegate = new ClientUtilityDelegate();

        // the usage is the same to ClientUtility.
    }
}
