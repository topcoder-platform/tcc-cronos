/*
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.persistence.InformixTimeTrackerProjectPersistence;
import com.topcoder.timetracker.project.persistence.PersistenceException;
import com.topcoder.util.config.ConfigManager;

/**
 * Failure tests for InformixTimeTrackerProjectPersistenceFailureTest
 * implementation.
 * 
 * @author dmks
 * @version 1.0
 */
public class InformixTimeTrackerProjectPersistenceFailureTest extends TestCase {
    /**
     * The InformixTimeTrackerProjectPersistence instance to test against.
     */
    private InformixTimeTrackerProjectPersistence persistence = null;

    /**
     * Prepares an InformixTimeTrackerProjectPersistence instance for testing.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void setUp() throws Exception {
        FailureTestHelper.loadConfig();

        persistence = new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE,
                FailureTestHelper.CONNECTION_PRODUCER_NAME);
    }

    /**
     * Clears all the namespaces and tables in the database.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void tearDown() throws Exception {
        FailureTestHelper.unloadConfig();
        persistence.closeConnection();
    }

    /**
     * Test of constructor with null db namespace. Expects NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testConstructor_NullDbNamespace1() throws Exception {
        // try one-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence(null);
            fail("Creates InformixTimeTrackerProjectPersistence with null db namespace");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of constructor with null db namespace. Expects NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testConstructor_NullDbNamespace2() throws Exception {
        // try two-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence(null, FailureTestHelper.CONNECTION_PRODUCER_NAME);
            fail("Creates InformixTimeTrackerProjectPersistence with null db namespace");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of constructor with empty db namespace. Expects
     * IllegalArgumentException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testConstructor_EmptyDbNamespace1() throws Exception {
        // try one-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence("");
            fail("Creates InformixTimeTrackerProjectPersistence with empty db namespace");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of constructor with empty db namespace. Expects
     * IllegalArgumentException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testConstructor_EmptyDbNamespace2() throws Exception {
        // try two-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence("", FailureTestHelper.CONNECTION_PRODUCER_NAME);
            fail("Creates InformixTimeTrackerProjectPersistence with empty db namespace");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of constructor with null connection producer name. Expects
     * NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testConstructor_NullConnProdName() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE, null);
            fail("Creates InformixTimeTrackerProjectPersistence with null connection producer name");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of constructor with empty connection producer name. Expects
     * IllegalArgumentException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testConstructor_EmptyConnProdName() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE, " ");
            fail("Creates InformixTimeTrackerProjectPersistence with empty connection producer name");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of constructor with non-existed db namespace. Expects
     * PersistenceException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testConstructor_NonexistedDbNamespace() throws Exception {
        ConfigManager.getInstance().removeNamespace(FailureTestHelper.DB_NAMESPACE);
        // try two-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE,
                    FailureTestHelper.CONNECTION_PRODUCER_NAME);
            fail("Creates InformixTimeTrackerProjectPersistence with nonexisted db namespace");
        } catch (PersistenceException e) {
        }
    }

    /**
     * Test of constructor with non-existed connection producer. Expects
     * PersistenceException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testConstructor_NonexistedConnectioniProducer() throws Exception {
        // try two-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE, "NonexistedProducer");
            fail("Creates InformixTimeTrackerProjectPersistence with nonexisted producer");
        } catch (PersistenceException e) {
        }
    }

    /**
     * Test of addClient method with null client. Expects NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddClient_NullClient() throws Exception {
        try {
            persistence.addClient(null);
            fail("Adds null client");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of updateClient method with null client. Expects
     * NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateClient_NullClient() throws Exception {
        try {
            persistence.updateClient(null);
            fail("Updates null client");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addProjectToClient method with null project. Expects
     * NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProjectToClient_NullProject() throws Exception {
        try {
            persistence.addProjectToClient(100, null, "user");
            fail("Adds null project to client");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addProjectToClient method with null user. Expects
     * NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProjectToClient_NullUser() throws Exception {
        try {
            persistence.addProjectToClient(100, new Project(), null);
            fail("Adds project to client with null user");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addProjectToClient method with empty user. Expects
     * IllegalArgumentException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProjectToClient_EmptyUser() throws Exception {
        try {
            persistence.addProjectToClient(100, new Project(), " ");
            fail("Adds project to client with empty user");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of addProject method with null project. Expects
     * NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProject_NullProject() throws Exception {
        try {
            persistence.addProject(null);
            fail("Adds null project");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of updateProject method with null project. Expects
     * NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateProject_NullProject() throws Exception {
        try {
            persistence.updateProject(null);
            fail("Updates null project");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of assignClient method with null user. Expects NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAssignClient_NullUser() throws Exception {
        try {
            persistence.assignClient(100, 100, null);
            fail("Assigns client with null user");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of assignClient method with empty user. Expects
     * IllegalArgumentException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAssignClient_EmptyUser() throws Exception {
        try {
            persistence.assignClient(100, 100, " ");
            fail("Assigns client with empty user");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of assignProjectManager method with null manager. Expects
     * NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAssignProjectManager_NullManager() throws Exception {
        try {
            persistence.assignProjectManager(null);
            fail("Assigns null project manager");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addWorker method with null worker. Expects NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddWorker_NullWorker() throws Exception {
        try {
            persistence.addWorker(null);
            fail("Adds null worker");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of updateWorker method with null worker. Expects
     * NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateWorker_NullWorker() throws Exception {
        try {
            persistence.updateWorker(null);
            fail("Updates null worker");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addTimeEntry method with null user. Expects NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddTimeEntry_NullUser() throws Exception {
        try {
            persistence.addTimeEntry(300, 100, null);
            fail("Adds time entry with null user");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addTimeEntry method with empty user. Expects
     * IllegalArgumentException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddTimeEntry_EmptyUser() throws Exception {
        try {
            persistence.addTimeEntry(100, 100, " ");
            fail("Adds time entry with empty user");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of addExpenseEntry method with null user. Expects
     * NullPointerException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddExpenseEntry_NullUser() throws Exception {
        try {
            persistence.addExpenseEntry(100, 200, null);
            fail("Adds expense entry with null user");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addExpenseEntry method with empty user. Expects
     * IllegalArgumentException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddExpenseEntry_EmptyUser() throws Exception {
        try {
            persistence.addExpenseEntry(100, 200, " ");
            fail("Adds expense entry with empty user");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of closeConnection method. Adds the client after calling this
     * method. Expects PersistenceException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testCloseConnection() throws Exception {
        persistence.closeConnection();

        try {
            persistence.addProject(FailureTestHelper.createProject(100));
            fail("Fails to close connection");
        } catch (PersistenceException e) {
        }
    }

}
