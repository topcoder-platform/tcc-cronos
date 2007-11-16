/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.stresstests;

import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.client.ClientFilterFactory;
import com.topcoder.timetracker.client.db.InformixClientDAO;
import com.topcoder.timetracker.client.depth.ClientIDOnlyDepth;
import com.topcoder.timetracker.project.Project;

import junit.framework.TestCase;

import java.io.File;


/**
 * <p>
 * Stress test cases for the this component.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class TimeTrackerClientStressTests extends TestCase {
    /**
     * The number of the loop.
     */
    private static int COUNT_NUMBER = 50;

    /**
     * Represents the InformixClientDAO instance used for testing.
     */
    private InformixClientDAO dao;

    /**
     * <p>
     * Set up the test enviroment.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();

        TestHelper.loadXMLConfig("test_files" + File.separatorChar + "Stress"
        		+ File.separatorChar + "db_config.xml");
        TestHelper.loadXMLConfig("test_files" + File.separatorChar + "Stress"
        		+ File.separatorChar + "search_strategy.xml");
        TestHelper.loadXMLConfig("test_files" + File.separatorChar + "Stress"
        		+ File.separatorChar + "search_bundle.xml");
        TestHelper.loadXMLConfig("test_files" + File.separatorChar + "Stress"
        		+ File.separatorChar + "InformixClientDAO_config.xml");
        TestHelper.loadXMLConfig("test_files" + File.separatorChar + "Stress"
        		+ File.separatorChar + "object_factory_config.xml");

        TestHelper.tearDownDataBase();
        TestHelper.setUpDatabase();

        dao = new InformixClientDAO();
    }

    /**
     * <p>
     * Tear down the test enviroment.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
    }

    /**
     * Stree test the addClient(Client client, boolean audit) method.
     *
     * @throws Exception to JUnit.
     */
    public void testAddClient() throws Exception {
        Client[] clients = new Client[COUNT_NUMBER];

        for (int i = 0; i < COUNT_NUMBER; i++) {
            clients[i] = TestHelper.createClient(i + 1);
        }

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.addClient(clients[i], false);
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("testAddClient() tested: " + COUNT_NUMBER
        		+ " times, " + dure + " ms.");

        assertEquals("addClient error.", 50, dao.getAllClients().length);
    }

    /**
     * Stree test the addClients(Client[] clients, boolean audit) method.
     *
     * @throws Exception to JUnit.
     */
    public void testAddClients() throws Exception {
        Client[][] clients = new Client[COUNT_NUMBER][10];

        for (int i = 0; i < COUNT_NUMBER; i++) {
            for (int j = 0; j < 10; j++) {
                clients[i][j] = TestHelper.createClient((i * 10) + j + 1);
            }
        }

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.addClients(clients[i], false);
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("testAddClients() tested: " + COUNT_NUMBER
        		+ " times, " + dure + " ms.");

        assertEquals("addClient error.", 500, dao.getAllClients().length);
    }

    /**
     * Stree test the removeClient(long id, boolean doAudit) method.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveClient() throws Exception {
        Client[] clients = new Client[COUNT_NUMBER];

        for (int i = 0; i < COUNT_NUMBER; i++) {
            clients[i] = TestHelper.createClient(i + 1);
        }

        dao.addClients(clients, false);
        assertEquals("addClient error.", 50, dao.getAllClients().length);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.removeClient(i + 1, false);
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("testRemoveClient() tested: " + COUNT_NUMBER
        		+ " times, " + dure + " ms.");

        assertEquals("addClient error.", 0, dao.getAllClients().length);
    }

    /**
     * Stree test the removeClients(long[] ids, boolean doAudit) method.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveClients() throws Exception {
        Client[][] clients = new Client[COUNT_NUMBER][10];

        for (int i = 0; i < COUNT_NUMBER; i++) {
            for (int j = 0; j < 10; j++) {
                clients[i][j] = TestHelper.createClient((i * 10) + j + 1);
            }
        }

        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.addClients(clients[i], false);
        }

        assertEquals("addClient error.", 500, dao.getAllClients().length);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT_NUMBER; i++) {
            long[] ids = new long[10];

            for (int j = 1; j < 11; j++) {
                ids[j - 1] = (i * 10) + j;
            }

            dao.removeClients(ids, false);
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("testRemoveClients() tested: " + COUNT_NUMBER
        		+ " times, " + dure + " ms.");

        assertEquals("addClient error.", 0, dao.getAllClients().length);
    }

    /**
     * Stree test the retrieveClient(long id) method.
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveClient() throws Exception {
        Client[] clients = new Client[COUNT_NUMBER];

        for (int i = 0; i < COUNT_NUMBER; i++) {
            clients[i] = TestHelper.createClient(i + 1);
        }

        dao.addClients(clients, false);
        assertEquals("addClient error.", 50, dao.getAllClients().length);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.retrieveClient(i + 1);
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("testRetrieveClient() tested: " + COUNT_NUMBER
        		+ " times, " + dure + " ms.");
    }

    /**
     * Stree test the retrieveClient(long[] ids) method.
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveClients() throws Exception {
        Client[][] clients = new Client[COUNT_NUMBER][10];

        for (int i = 0; i < COUNT_NUMBER; i++) {
            for (int j = 0; j < 10; j++) {
                clients[i][j] = TestHelper.createClient((i * 10) + j + 1);
            }
        }

        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.addClients(clients[i], false);
        }

        assertEquals("addClient error.", 500, dao.getAllClients().length);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT_NUMBER; i++) {
            long[] ids = new long[10];

            for (int j = 1; j < 11; j++) {
                ids[j - 1] = (i * 10) + j;
            }

            dao.retrieveClients(ids);
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("testRetrieveClients() tested: " + COUNT_NUMBER
        		+ " times, " + dure + " ms.");
    }

    /**
     * Stree test the updateClient(Client client, boolean doAudit) method.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateClient() throws Exception {
        Client[] clients = new Client[COUNT_NUMBER];

        for (int i = 0; i < COUNT_NUMBER; i++) {
            clients[i] = TestHelper.createClient(i + 1);
        }

        dao.addClients(clients, false);
        assertEquals("addClient error.", 50, dao.getAllClients().length);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.updateClient(clients[i], false);
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("testUpdateClient() tested: " + COUNT_NUMBER
        		+ " times, " + dure + " ms.");
    }

    /**
     * Stree test the updateClients(Client[] clients, boolean doAudit) method.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateClients() throws Exception {
        Client[][] clients = new Client[COUNT_NUMBER][10];

        for (int i = 0; i < COUNT_NUMBER; i++) {
            for (int j = 0; j < 10; j++) {
                clients[i][j] = TestHelper.createClient((i * 10) + j + 1);
            }
        }

        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.addClients(clients[i], false);
        }

        assertEquals("addClient error.", 500, dao.getAllClients().length);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.updateClients(clients[i], false);
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("testUpdateClients() tested: " + COUNT_NUMBER
        		+ " times, " + dure + " ms.");
    }

    /**
     * Stree test the getAllClients() method.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllClients() throws Exception {
        Client[] clients = new Client[COUNT_NUMBER];

        for (int i = 0; i < COUNT_NUMBER; i++) {
            clients[i] = TestHelper.createClient(i + 1);
        }

        dao.addClients(clients, false);
        assertEquals("addClient error.", 50, dao.getAllClients().length);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.getAllClients();
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("testGetAllClients() tested: " + COUNT_NUMBER
        		+ " times, " + dure + " ms.");
    }

    /**
     * Stree test the searchClient(Filter filter, Depth depth) method.
     *
     * @throws Exception to JUnit.
     *
    public void testSearchClient() throws Exception {
        Client[] clients = new Client[COUNT_NUMBER];

        for (int i = 0; i < COUNT_NUMBER; i++) {
            clients[i] = TestHelper.createClient(i + 1);
        }

        dao.addClients(clients, false);
        assertEquals("addClient error.", 50, dao.getAllClients().length);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.searchClient(ClientFilterFactory.createCompanyIdFilter(i + 1),
                new ClientIDOnlyDepth());
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("testSearchClient() tested: " + COUNT_NUMBER
        		+ " times, " + dure + " ms.");
    }*/

    /**
     * Stree test the removeProjectFromClient(Client client, long projectId, boolean doAudit) method.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveProjectFromClient() throws Exception {
    	Project project = new Project();
        project.setId(1);

        Client[] clients = new Client[COUNT_NUMBER];

        for (int i = 0; i < COUNT_NUMBER; i++) {
            clients[i] = TestHelper.createClient(i + 1);
        }

        dao.addClients(clients, false);
        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.addProjectToClient(clients[i], project, false);
        }
        assertEquals("addClient error.", 50, dao.getAllClients().length);
        for (int i = 0; i < COUNT_NUMBER; i++) {
            long[] ids = dao.getAllProjectIDsOfClient(i + 1);
            assertEquals(ids.length, 1);
        }

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.removeProjectFromClient(clients[i], 1, false);
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("testRemoveProjectFromClient() tested: "
        		+ COUNT_NUMBER + " times, " + dure + " ms.");

        for (int i = 0; i < COUNT_NUMBER; i++) {
            long[] ids = dao.getAllProjectIDsOfClient(i + 1);
            assertEquals(ids.length, 0);
        }
    }

    /**
     * Stree test the addProjectToClient(Client client, Project project, boolean doAudit) method.
     *
     * @throws Exception to JUnit.
     */
    public void testAddProjectFromClient() throws Exception {
        Project project = new Project();
        project.setId(2);

        Client[] clients = new Client[COUNT_NUMBER];

        for (int i = 0; i < COUNT_NUMBER; i++) {
            clients[i] = TestHelper.createClient(i + 1);
        }

        dao.addClients(clients, false);

        assertEquals("addClient error.", 50, dao.getAllClients().length);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.addProjectToClient(clients[i], project, false);
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("testAddProjectFromClient() tested: "
        		+ COUNT_NUMBER + " times, " + dure + " ms.");

        for (int i = 0; i < COUNT_NUMBER; i++) {
            long[] ids = dao.getAllProjectIDsOfClient(i + 1);
            assertEquals(ids.length, 1);
        }
    }

    /**
     * Stree test the getAllProjectIDsOfClient(long clientId) method.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllProjectIDsOfClient() throws Exception {
        Client[] clients = new Client[COUNT_NUMBER];

        for (int i = 0; i < COUNT_NUMBER; i++) {
            clients[i] = TestHelper.createClient(i + 1);
        }

        dao.addClients(clients, false);
        assertEquals("addClient error.", 50, dao.getAllClients().length);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT_NUMBER; i++) {
            dao.getAllProjectIDsOfClient(i + 1);
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("testGetAllProjectIDsOfClient() tested: "
        		+ COUNT_NUMBER + " times, " + dure + " ms.");
    }
}
