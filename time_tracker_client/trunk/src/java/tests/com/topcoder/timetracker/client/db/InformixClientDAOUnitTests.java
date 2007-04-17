/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.db;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.client.BatchOperationException;
import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.client.ClientDAO;
import com.topcoder.timetracker.client.ClientFilterFactory;
import com.topcoder.timetracker.client.ClientPersistenceException;
import com.topcoder.timetracker.client.ConfigurationException;
import com.topcoder.timetracker.client.UnitTestHelper;
import com.topcoder.timetracker.client.depth.ClientIDOnlyDepth;
import com.topcoder.timetracker.project.Project;


/**
 * Unit test for the <code>InformixClientDAO</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class InformixClientDAOUnitTests extends TestCase {
    /** Represents the private instance used for test. */
    private InformixClientDAO dao = null;

    /** DB connection factory. */
    private DBConnectionFactory dbFactory = null;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        UnitTestHelper.clearConfig();

        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_strategy.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_bundle.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "InformixClientDAO_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");

        dao = new InformixClientDAO();

        dbFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        UnitTestHelper.clearDatabase(dbFactory, "informix_connect");
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
     * Accuracy test Inheritance, should inherit from ClientDAO.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testInheritanceAccuracy() throws Exception {
        assertTrue("InformixClientDAO should inherit from ClientDAO", dao instanceof ClientDAO);
    }

    /**
     * <p>
     * Failure test for the first constructor. Construct with not exist namespace is illegal. ConfigurationException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructor1WithNotExist() throws Exception {
        try {
            UnitTestHelper.clearConfig();
            new InformixClientDAO();
            fail("Construct with not exist namespace is illegal, ConfigureationException should be thrown");
        } catch (ConfigurationException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for Constructor.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructor1Accuracy() throws Exception {
        assertNotNull("Instance should be created.", dao);
    }

    /**
     * <p>
     * Failure test for Constructor. Constructor with null is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructor2WithNull() throws Exception {
        try {
            new InformixClientDAO(null);
            fail("Constructor with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for Constructor. Constructor with empty is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructor2WithEmpty() throws Exception {
        try {
            new InformixClientDAO("    ");
            fail("Constructor with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for Constructor. Constructor with not exist is illegal. Configuration is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructor2WithNotExist() throws Exception {
        try {
            new InformixClientDAO("notexist");
            fail("Constructor with null is illegal, IllegalArgumentException should be thrown");
        } catch (ConfigurationException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>addClient</code>. Add with null is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddClientWithNull() throws Exception {
        try {
            dao.addClient(null, true);
            fail("Add with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>addClient</code>. Add to not exist database is illegal. ClientPersistenceException
     * is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddClientWithEmptydatabase() throws Exception {
        try {
            UnitTestHelper.clearConfig();

            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_strategy.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_bundle.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "empty_InformixClientDAO_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");

            dao = new InformixClientDAO();

            dao.addClient(UnitTestHelper.createCient(1), true);
            fail("Add to not exist database is illegal, ClientPersistenceException should be thrown");
        } catch (ClientPersistenceException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>addClient</code>. Without audit
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddClientAccuracyWithoutAudit() throws Exception {
        dao.addClient(UnitTestHelper.createCient(1), false);
    }

    /**
     * <p>
     * Accuracy test for method <code>addClient</code>. Without audit
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddClientAccuracyWithAudit() throws Exception {
        Client toAdd = UnitTestHelper.createCient(1);
        dao.addClient(toAdd, true);

        Client[] clients = dao.getAllClients();

        assertEquals("clients has one element.", 1, clients.length);
        assertTrue("Clients should be equal.", UnitTestHelper.isClientEquals(toAdd, clients[0]));
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
            dao.addClients(null, false);
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
    public void testaddClientsWithIllegal() throws Exception {
        try {
            dao.addClients(new Client[] {null }, false);
            fail("Add with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>addClients</code>. Add with empty database is illegal. ClientPersistenceException
     * is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddClientsWithEmptyDatabase() throws Exception {
        try {
            UnitTestHelper.clearConfig();

            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_strategy.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_bundle.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "empty_InformixClientDAO_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");
            dao = new InformixClientDAO();

            dao.addClients(new Client[] {UnitTestHelper.createCient(1) }, true);
            fail("Add with empty database is illegal, ClientPersistenceException should be thrown");
        } catch (ClientPersistenceException e) {
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
    public void testaddClientsAccuracy1() throws Exception {
        dao.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client[] clients = dao.getAllClients();
        assertEquals("The client should have 2 record.", 2, clients.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>addClients</code>. Test if the BatchOperationException thrown has correct
     * setting.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddClientsAccuracy2() throws Exception {
        try {
            dao.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(1) }, false);

            Client[] clients = dao.getAllClients();
        } catch (BatchOperationException boe) {
            boolean[] success = boe.getResult();

            assertTrue("The first insert should success.", success[0]);
            assertFalse("The second one should fail.", success[1]);
        }
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
            dao.retrieveClient(0);
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
    public void testretrieveClientWithNegative() throws Exception {
        try {
            dao.retrieveClient(-1);
            fail("Retrieve with negative is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>retrieveClient</code>. Retrieve with empty database is illegal.
     * ClientPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveClientWithEmptyDatabase() throws Exception {
        try {
            UnitTestHelper.clearConfig();

            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_strategy.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_bundle.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "empty_InformixClientDAO_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");
            dao = new InformixClientDAO();

            dao.retrieveClient(1);
            fail("Retrieve with empty database is illegal, ClientPersistenceException should be thrown");
        } catch (ClientPersistenceException e) {
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
    public void testretrieveClientAccuracy1() throws Exception {
        Client toAdd = UnitTestHelper.createCient(1);
        dao.addClients(new Client[] {toAdd, UnitTestHelper.createCient(2) }, false);

        Client client = dao.retrieveClient(1);

        assertTrue("The client with id 1 should be get.", UnitTestHelper.isClientEquals(toAdd, client));
    }

    /**
     * <p>
     * Accuracy test for method <code>retrieveClient</code>. Retrieve not exist one should return null.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveClientAccuracy2() throws Exception {
        dao.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client client = dao.retrieveClient(3);

        assertNull("The client with id 3 should not exist.", client);
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
            dao.retrieveClients(null);
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
    public void testretrieveClientsWithInvalid() throws Exception {
        try {
            dao.retrieveClients(new long[] {1, -1 });
            fail("Retrieve with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>retrieveClients</code>. Retrieve from emtpy Database is illegal.
     * ClientPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveClientsWithEmptyDatabase()
        throws Exception {
        try {
            UnitTestHelper.clearConfig();

            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_strategy.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_bundle.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "empty_InformixClientDAO_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");
            dao = new InformixClientDAO();

            dao.retrieveClients(new long[] {1, 2 });
            fail("Retrieve from emtpy Database is illegal, ClientPersistenceException should be thrown");
        } catch (ClientPersistenceException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>retrieveClients</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveClientsAccuracy1() throws Exception {
        dao.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client[] clients = dao.retrieveClients(new long[] {1, 2 });

        assertEquals("The returned array should be of size 2.", 2, clients.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>retrieveClients</code>. Retrieve with not exist.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveClientsAccuracy2() throws Exception {
        dao.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client[] clients = dao.retrieveClients(new long[] {1, 3 });

        assertEquals("The returned array should be of size 1.", 1, clients.length);
    }

    /**
     * <p>
     * Accuracy test for method <code>retrieveClients</code>. Retrieve with not exist. Should return null.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testretrieveClientsAccuracy3() throws Exception {
        dao.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client[] clients = dao.retrieveClients(new long[] {4, 3 });

        assertNotNull("The returned array should be not null.", clients);
        assertEquals("The returned array should be of size 0.", 0, clients.length);
    }

    /**
     * <p>
     * Failure test for method <code>removeClient</code>. Remove with zero id is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremoveClientWithZero() throws Exception {
        try {
            dao.removeClient(0, false);
            fail("Remove with zero id is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>removeClient</code>. Remove with negative id is illegal. IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremoveClientWithNegative() throws Exception {
        try {
            dao.removeClient(-1, false);
            fail("Remove with negative id is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>removeClient</code>. Remove from empty database is illegal.
     * ClientPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremoveClientWithEmptyDatabase() throws Exception {
        try {
            UnitTestHelper.clearConfig();

            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_strategy.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_bundle.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "empty_InformixClientDAO_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");
            dao = new InformixClientDAO();

            dao.removeClient(1, false);
            fail("Remove from empty database is illegal, ClientPersistenceException should be thrown");
        } catch (ClientPersistenceException e) {
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
        dao.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client[] clients = dao.getAllClients();

        assertEquals("The client should be of size 2.", 2, clients.length);

        dao.removeClient(1, false);

        clients = dao.getAllClients();

        assertEquals("The client should be of size 1 after remove.", 1, clients.length);
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
            dao.removeClients(null, false);
            fail("Remove with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>RemoveClients</code>. Remove with invalid is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRemoveClientsWithInvalid() throws Exception {
        try {
            dao.removeClients(new long[] {1, -1 }, false);
            fail("Remove with invalid is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>removeClients</code>. Remove from empty database is illegal.
     * ClientPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremoveClientsWithEmptyDatabase() throws Exception {
        try {
            UnitTestHelper.clearConfig();

            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_strategy.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_bundle.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "empty_InformixClientDAO_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");

            dao = new InformixClientDAO();

            dao.removeClients(new long[] {1, 2 }, false);
            fail("Remove from empty database is illegal, ClientPersistenceException should be thrown");
        } catch (ClientPersistenceException e) {
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
        dao.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client[] clients = dao.getAllClients();

        assertEquals("The client should be of size 2.", 2, clients.length);

        dao.removeClients(new long[] {1 }, false);

        clients = dao.getAllClients();

        assertEquals("The client should be of size 1 after remove.", 1, clients.length);
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
            dao.updateClient(null, false);
            fail("Update with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>updateClient</code>. Update with empty database is illegal.
     * ClientPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateClientWithEmptydDatabase() throws Exception {
        try {
            UnitTestHelper.clearConfig();

            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_strategy.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_bundle.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "empty_InformixClientDAO_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");

            dao = new InformixClientDAO();

            dao.updateClient(UnitTestHelper.createCient(1), false);
            fail("Update with empty database is illegal, ClientPersistenceException should be thrown");
        } catch (ClientPersistenceException e) {
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
    public void testupdateClientAccuracy1() throws Exception {
        dao.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client client = UnitTestHelper.createCient(1);

        client.setName("my new name.");

        dao.updateClient(client, false);

        Client client1 = dao.retrieveClient(1);

        assertEquals("The client should have the name.", "my new name.", client1.getName());
    }

    /**
     * <p>
     * Accuracy test for method <code>updateClient</code>. With audit on
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateClientAccuracy2() throws Exception {
        dao.addClients(new Client[] {UnitTestHelper.createCient(1), UnitTestHelper.createCient(2) }, false);

        Client client = UnitTestHelper.createCient(1);

        client.setName("my new name.");

        dao.updateClient(client, true);

        Client client1 = dao.retrieveClient(1);

        assertEquals("The client should have the name.", "my new name.", client1.getName());
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
            dao.updateClients(null, false);
            fail("Update with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>updateClients</code>. Update with invalid list is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateClientsWithInvalid() throws Exception {
        try {
            dao.updateClients(new Client[] {UnitTestHelper.createCient(1), null }, false);
            fail("Update with invalid list is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>updateClients</code>. Update with empty Database is illegal.
     * ClientPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateClientsWithEmptyDatabase() throws Exception {
        try {
            UnitTestHelper.clearConfig();

            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_strategy.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_bundle.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "empty_InformixClientDAO_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");

            dao = new InformixClientDAO();

            dao.updateClients(new Client[] {UnitTestHelper.createCient(1) }, false);

            fail("Update with empty Database is illegal, ClientPersistenceException should be thrown");
        } catch (ClientPersistenceException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>updateClients</code>. Without audit.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateClientsAccuracy1() throws Exception {
        dao.addClient(UnitTestHelper.createCient(1), false);

        Client client = dao.retrieveClient(1);

        client.setName("new name");

        dao.updateClients(new Client[] {client }, false);

        client = dao.retrieveClient(1);

        assertEquals("The client should be update.", "new name", client.getName());
    }

    /**
     * <p>
     * Accuracy test for method <code>updateClients</code>. With audit on.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testupdateClientsAccuracy2() throws Exception {
        dao.addClient(UnitTestHelper.createCient(1), false);

        Client client = dao.retrieveClient(1);

        client.setName("new name");

        dao.updateClients(new Client[] {client }, true);

        client = dao.retrieveClient(1);

        assertEquals("The client should be update.", "new name", client.getName());
    }

    /**
     * <p>
     * Failure test for method <code>getAllClients</code>. Get form empty database is illegal.
     * ClientPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetAllClientsWithEmptyDatabase() throws Exception {
        try {
            UnitTestHelper.clearConfig();

            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_strategy.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "search_bundle.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "empty_InformixClientDAO_config.xml");
            UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");

            dao = new InformixClientDAO();

            dao.getAllClients();
            fail("Get form empty database is illegal, ClientPersistenceException should be thrown");
        } catch (ClientPersistenceException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>getAllClients</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetAllClientsAccuracy() throws Exception {
        dao.addClient(UnitTestHelper.createCient(1), false);

        Client[] clients = dao.getAllClients();

        assertEquals("The clients should be of size 1.", 1, clients.length);
        assertEquals("The id of the client is 1.", 1, clients.length);
    }

    /**
     * <p>
     * Failure test for method <code>searchClient</code>. Search with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchClientWithNull1() throws Exception {
        try {
            dao.searchClient(null, new ClientIDOnlyDepth());
            fail("Search with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>searchClient</code>. Search with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsearchClientWithNull2() throws Exception {
        try {
            dao.searchClient(ClientFilterFactory.createNameKeywordFilter("user"), null);
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
        dao.searchClient(ClientFilterFactory.createNameKeywordFilter("user"), new ClientIDOnlyDepth());
    }

    /**
     * <p>
     * Failure test for method <code>addProjectToClient</code>. Add with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddProjectToClientWithNull1() throws Exception {
        try {
            dao.addProjectToClient(UnitTestHelper.createCient(1), null, false);
            fail("Add with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>addProjectToClient</code>. Add with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testaddProjectToClientWithNull2() throws Exception {
        try {
            dao.addProjectToClient(null, new Project(), false);
            fail("Add with null is illegal, IllegalArgumentException should be thrown");
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
        Project project = new Project();

        project.setId(1);

        dao.addClient(UnitTestHelper.createCient(1), false);

        dao.addProjectToClient(UnitTestHelper.createCient(1), project, false);
    }

    /**
     * <p>
     * Failure test for method <code>removeProjectFromClient</code>. Remove with null is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremoveProjectFromClientWithnull() throws Exception {
        try {
            dao.removeProjectFromClient(null, 1, false);
            fail("Remove with negative is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>removeProjectFromClient</code>. Remove with negative is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testremoveProjectFromClientWithNegative2()
        throws Exception {
        try {
            dao.removeProjectFromClient(UnitTestHelper.createCient(1), -1, false);
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
        Project project = new Project();

        project.setId(1);

        dao.addClient(UnitTestHelper.createCient(1), false);

        dao.addProjectToClient(UnitTestHelper.createCient(1), project, false);

        dao.removeProjectFromClient(UnitTestHelper.createCient(1), 1, false);
    }

    /**
     * <p>
     * Failure test for method <code>getAllProjectIdsOfClient</code>. Get with zero is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetAllProjectIdsOfClientWithZero()
        throws Exception {
        try {
            dao.getAllProjectIDsOfClient(0);
            fail("Get with zero is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>getAllProjectIdsOfClient</code>. Get with negative is illegal.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetAllProjectIdsOfClientWithNegative()
        throws Exception {
        try {
            dao.getAllProjectIDsOfClient(-1);
            fail("Get with negative is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>getAllProjectIDsOfClient</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetAllProjectIDsOfClientAccuracy()
        throws Exception {
        Project project = new Project();

        project.setId(1);

        dao.addClient(UnitTestHelper.createCient(1), false);

        dao.addProjectToClient(UnitTestHelper.createCient(1), project, false);

        long[] ids = dao.getAllProjectIDsOfClient(1);

        assertEquals("Ids should have length 1.", 1, ids.length);
        assertEquals("The project id is 1.", 1, ids[0]);
    }
}
