/*
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project.failuretests;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.project.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.persistence.BatchOperationException;
import com.topcoder.timetracker.project.persistence.InformixTimeTrackerProjectPersistence;
import com.topcoder.timetracker.project.persistence.PersistenceException;
import com.topcoder.timetracker.project.searchfilters.Filter;
import com.topcoder.util.config.ConfigManager;

/**
 * Failure tests for InformixTimeTrackerProjectPersistenceFailureTest implementation.
 *
 * @author dmks
 * @author kr00tki
 * @version 2.0
 * @since 1.0
 */
public class InformixTimeTrackerProjectPersistenceFailureTest extends TestCase {
    /**
     * Test user name.
     */
    private static final String USER = "kr";

    /**
     * The InformixTimeTrackerProjectPersistence instance to test against.
     */
    private InformixTimeTrackerProjectPersistence persistence = null;

    /**
     * Prepares an InformixTimeTrackerProjectPersistence instance for testing.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void setUp() throws Exception {
        FailureTestHelper.loadConfig();
        FailureTestHelper.clearTables();

        persistence = new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE,
                FailureTestHelper.CONNECTION_PRODUCER_NAME);

        persistence.addProject(FailureTestHelper.createProject(1, 1));
    }

    /**
     * Clears all the namespaces and tables in the database.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void tearDown() throws Exception {
        FailureTestHelper.clearTables();
        FailureTestHelper.unloadConfig();
        persistence.closeConnection();
    }

    /**
     * Test of constructor with null db namespace. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
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
     * @throws Exception if any unexpected exception occurs.
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
     * Test of constructor with empty db namespace. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
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
     * Test of constructor with empty db namespace. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
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
     * Test of constructor with null connection producer name. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_NullConnProdName() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE, null);
            fail("Creates InformixTimeTrackerProjectPersistence with null connection producer name");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of constructor with empty connection producer name. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_EmptyConnProdName() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE, " ");
            fail("Creates InformixTimeTrackerProjectPersistence with empty connection producer name");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of constructor with non-existed db namespace. Expects PersistenceException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_NonexistedDbNamespace() throws Exception {
        ConfigManager.getInstance().removeNamespace(FailureTestHelper.DB_NAMESPACE);
        // try two-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE,
                    FailureTestHelper.CONNECTION_PRODUCER_NAME);
            fail("Creates InformixTimeTrackerProjectPersistence with nonexisted db namespace");
        } catch (PersistenceException e) {
        } finally {
            ConfigManager.getInstance().add(FailureTestHelper.DB_CONFIG_FILE);
        }
    }

    /**
     * Test of constructor with non-existed connection producer. Expects PersistenceException.
     *
     * @throws Exception if any unexpected exception occurs.
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
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddClient_NullClient() throws Exception {
        try {
            persistence.addClient(null);
            fail("Adds null client");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of updateClient method with null client. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testUpdateClient_NullClient() throws Exception {
        try {
            persistence.updateClient(null);
            fail("Updates null client");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addProjectToClient method with null project. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProjectToClient_NullProject() throws Exception {
        try {
            persistence.addProjectToClient(100, null, "user");
            fail("Adds null project to client");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addProjectToClient method with null user. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProjectToClient_NullUser() throws Exception {
        try {
            persistence.addProjectToClient(100, new Project(), null);
            fail("Adds project to client with null user");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addProjectToClient method with empty user. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProjectToClient_EmptyUser() throws Exception {
        try {
            persistence.addProjectToClient(100, new Project(), " ");
            fail("Adds project to client with empty user");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of addProject method with null project. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProject_NullProject() throws Exception {
        try {
            persistence.addProject(null);
            fail("Adds null project");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of updateProject method with null project. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
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
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAssignClient_NullUser() throws Exception {
        try {
            persistence.assignClient(100, 100, null);
            fail("Assigns client with null user");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of assignClient method with empty user. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAssignClient_EmptyUser() throws Exception {
        try {
            persistence.assignClient(100, 100, " ");
            fail("Assigns client with empty user");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of assignProjectManager method with null manager. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
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
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddWorker_NullWorker() throws Exception {
        try {
            persistence.addWorker(null);
            fail("Adds null worker");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of updateWorker method with null worker. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
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
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddTimeEntry_NullUser() throws Exception {
        try {
            persistence.addTimeEntry(300, 100, null);
            fail("Adds time entry with null user");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addTimeEntry method with empty user. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddTimeEntry_EmptyUser() throws Exception {
        try {
            persistence.addTimeEntry(100, 100, " ");
            fail("Adds time entry with empty user");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of addExpenseEntry method with null user. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddExpenseEntry_NullUser() throws Exception {
        try {
            persistence.addExpenseEntry(100, 200, null);
            fail("Adds expense entry with null user");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addExpenseEntry method with empty user. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddExpenseEntry_EmptyUser() throws Exception {
        try {
            persistence.addExpenseEntry(100, 200, " ");
            fail("Adds expense entry with empty user");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of closeConnection method. Adds the client after calling this method. Expects PersistenceException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testCloseConnection() throws Exception {
        persistence.closeConnection();

        try {
            persistence.addProject(FailureTestHelper.createProject(100, 1));
            fail("Fails to close connection");
        } catch (PersistenceException e) {
        }
    }

    // since 1.1

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#
     * InformixTimeTrackerProjectPersistence(String, String, String, String)} constructor.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testConstructor_NullProjectNamepace() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE,
                    FailureTestHelper.CONNECTION_PRODUCER_NAME, null,
                    InformixTimeTrackerProjectPersistence.DEFAULT_CLIENT_SEARCH_UTILITY_NAMESPACE);

            fail("Null project namespace, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#
     * InformixTimeTrackerProjectPersistence(String, String, String, String)} constructor.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testConstructor_NullClientNamepace() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE,
                    FailureTestHelper.CONNECTION_PRODUCER_NAME,
                    InformixTimeTrackerProjectPersistence.DEFAULT_PROJECT_SEARCH_UTILITY_NAMESPACE, null);

            fail("Null client namespace, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#
     * InformixTimeTrackerProjectPersistence(String, String, String, String)} constructor.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testConstructor_EmptyProjectNamepace() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE,
                    FailureTestHelper.CONNECTION_PRODUCER_NAME, " ",
                    InformixTimeTrackerProjectPersistence.DEFAULT_CLIENT_SEARCH_UTILITY_NAMESPACE);

            fail("Empty project namespace, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#
     * InformixTimeTrackerProjectPersistence(String, String, String, String)} constructor.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testConstructor_EmptyClientNamepace() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE,
                    FailureTestHelper.CONNECTION_PRODUCER_NAME,
                    InformixTimeTrackerProjectPersistence.DEFAULT_PROJECT_SEARCH_UTILITY_NAMESPACE, " ");

            fail("Empty client namespace, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#
     * InformixTimeTrackerProjectPersistence(String, String, String, String)} constructor.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testConstructor_InvalidProjectNamepace() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE,
                    FailureTestHelper.CONNECTION_PRODUCER_NAME, FailureTestHelper.DB_NAMESPACE,
                    InformixTimeTrackerProjectPersistence.DEFAULT_CLIENT_SEARCH_UTILITY_NAMESPACE);

            fail("Invalid project config, PE expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#
     * InformixTimeTrackerProjectPersistence(String, String, String, String)} constructor.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testConstructor_InvalidClientNamepace() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(FailureTestHelper.DB_NAMESPACE,
                    FailureTestHelper.CONNECTION_PRODUCER_NAME,
                    InformixTimeTrackerProjectPersistence.DEFAULT_PROJECT_SEARCH_UTILITY_NAMESPACE,
                    FailureTestHelper.DB_NAMESPACE);

            fail("Invalid client namespace, PE expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#searchForProjects(Filter)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testSearchForProjects_NullFilter() throws Exception {
        try {
            persistence.searchForProjects(null);
            fail("Null filter, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#searchForProjects(Filter)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testSearchForProjects_IllegalFilter() throws Exception {
        try {
            persistence.searchForProjects(new Filter() {
            });
            fail("Illegal filter, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#searchForClients(Filter)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testSearchForClients_NullFilter() throws Exception {
        try {
            persistence.searchForClients(null);
            fail("Null filter, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#searchForClients(Filter)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testSearchForClients_IllegalFilter() throws Exception {
        try {
            persistence.searchForClients(new Filter() {
            });
            fail("Illegal filter, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addClients(Client[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddClients_NullArray() throws Exception {
        try {
            persistence.addClients(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addClients(Client[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddClients_EmptyArray() throws Exception {
        try {
            persistence.addClients(new Client[] {}, true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addClients(Client[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddClients_ArrayWithNull() throws Exception {
        try {
            persistence.addClients(new Client[] { null }, true);
            fail("Array with null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeClients(int[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveClients_NullArray() throws Exception {
        try {
            persistence.removeClients(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeClients(int[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveClients_EmptyArray() throws Exception {
        try {
            persistence.removeClients(new int[] {}, true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeClients(int[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveClients_NoClient() throws Exception {
        try {
            persistence.removeClients(new int[] { 1 }, true);
            fail("No such client, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#updateClients(Client[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateClients_NullArray() throws Exception {
        try {
            persistence.updateClients(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#updateClients(Client[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateClients_EmptyArray() throws Exception {
        try {
            persistence.updateClients(new Client[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#updateClients(Client[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateClients_ArrayWithNull() throws Exception {
        try {
            persistence.updateClients(new Client[] { null }, true);
            fail("Array with null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#updateClients(Client[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateClients_NoClient() throws Exception {
        try {
            persistence.updateClients(new Client[] { new Client() }, true);
            fail("Client not exists, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getClients(int[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetClients_NullArray() throws Exception {
        try {
            persistence.getClients(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getClients(int[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetClients_EmptyArray() throws Exception {
        try {
            persistence.getClients(new int[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getClients(int[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetClients_NoClient() throws Exception {
        try {
            persistence.getClients(new int[] { 1 }, true);
            fail("No such client, IAE expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addProjects(Project[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddProjects_NullArray() throws Exception {
        try {
            persistence.addProjects(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addProjects(Project[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddProjects_EmptyArray() throws Exception {
        try {
            persistence.addProjects(new Project[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addProjects(Project[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddProjects_ArrayWithNull() throws Exception {
        try {
            persistence.addProjects(new Project[] { null }, true);
            fail("Array with null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeProjects(int[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveProjects_NullArray() throws Exception {
        try {
            persistence.removeProjects(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeProjects(int[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveProjects_EmptyArray() throws Exception {
        try {
            persistence.removeProjects(new int[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeProjects(int[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveProjects_NoProject() throws Exception {
        try {
            persistence.removeProjects(new int[] { 10 }, true);
            fail("No such project, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#updateProjects(Project[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateProjects_NullArray() throws Exception {
        try {
            persistence.updateProjects(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#updateProjects(Project[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateProjects_EmptyArray() throws Exception {
        try {
            persistence.updateProjects(new Project[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#updateProjects(Project[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateProjects_ArrayWithNull() throws Exception {
        try {
            persistence.updateProjects(new Project[] { null }, true);
            fail("Array with null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#updateProjects(Project[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateProjects_NoProject() throws Exception {
        try {
            persistence.updateProjects(new Project[] { FailureTestHelper.createProject(10, 1) }, true);
            fail("No project to update, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getProjects(int[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetProjects_NullArray() throws Exception {
        try {
            persistence.getProjects(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getProjects(int[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetProjects_EmptyArray() throws Exception {
        try {
            persistence.getProjects(new int[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getProjects(int[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetProjects_NoProject() throws Exception {
        try {
            persistence.getProjects(new int[] { 10 }, true);
            fail("No such project, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addWorkers(ProjectWorker[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddWorkers_NullArray() throws Exception {
        try {
            persistence.addWorkers(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addWorkers(ProjectWorker[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddWorkers_EmptyArray() throws Exception {
        try {
            persistence.addWorkers(new ProjectWorker[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addWorkers(ProjectWorker[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddWorkers_ArrayWithNull() throws Exception {
        try {
            persistence.addWorkers(new ProjectWorker[] { null }, true);
            fail("Array with null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeWorkers(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveWorkers_NullArray() throws Exception {
        persistence.addProject(FailureTestHelper.createProject(1, 1));
        try {
            persistence.removeWorkers(null, 1, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeWorkers(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveWorkers_NoProject() throws Exception {
        Project project = FailureTestHelper.createProject(1, 1);
        persistence.addProject(project);

        ProjectWorker worker = new ProjectWorker(project, 1);
        worker.setStartDate(new Date());
        worker.setEndDate(new Date());
        worker.setWorkerId(300);
        worker.setPayRate(new BigDecimal(1.0));
        worker.setCreationUser("kr");
        persistence.addWorker(worker);
        try {
            persistence.removeWorkers(new int[] { 1 }, 2, true);
            fail("No such project, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeWorkers(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveWorkers_EmptyArray() throws Exception {
        try {
            persistence.removeWorkers(new int[0], 1, true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeWorkers(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveWorkers_NoSuchWorker() throws Exception {
        try {
            persistence.removeWorkers(new int[] { 0 }, 1, true);
            fail("No such worker, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#updateWorkers(ProjectWorker[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateWorkers_NullArray() throws Exception {
        try {
            persistence.updateWorkers(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#updateWorkers(ProjectWorker[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateWorkers_EmptyArray() throws Exception {
        try {
            persistence.updateWorkers(new ProjectWorker[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#updateWorkers(ProjectWorker[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateWorkers_ArrayWithNull() throws Exception {
        try {
            persistence.updateWorkers(new ProjectWorker[] { null }, true);
            fail("Array with null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#updateWorkers(ProjectWorker[], boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateWorkers_NoWorker() throws Exception {
        try {
            persistence.updateWorkers(new ProjectWorker[] { FailureTestHelper.createIllegalWorker() }, true);
            fail("No such worker, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getWorkers(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetWorkers_NullArray() throws Exception {
        try {
            persistence.getWorkers(null, 1, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getWorkers(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetWorkers_EmptyArray() throws Exception {
        try {
            persistence.getWorkers(new int[0], 1, true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getWorkers(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetWorkers_NoWorker() throws Exception {
        try {
            persistence.getWorkers(new int[] { 3 }, 1, true);
            fail("No worker, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getWorkers(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetWorkers_NoProject() throws Exception {
        Project project = FailureTestHelper.createProject(1, 1);
        persistence.addProject(project);

        ProjectWorker worker = new ProjectWorker(project, 1);
        worker.setStartDate(new Date());
        worker.setEndDate(new Date());
        worker.setWorkerId(300);
        worker.setPayRate(new BigDecimal(1.0));
        worker.setCreationUser("kr");
        persistence.addWorker(worker);
        try {
            persistence.getWorkers(new int[] { 3 }, 3, true);
            fail("No project, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addTimeEntries(int[], int, String, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddTimeEntries_NullIds() throws Exception {
        try {
            persistence.addTimeEntries(null, 1, USER, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addTimeEntries(int[], int, String, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddTimeEntries_NullUser() throws Exception {
        try {
            persistence.addTimeEntries(new int[] { 500 }, 1, null, true);
            fail("Null user, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addTimeEntries(int[], int, String, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddTimeEntries_EmptyUser() throws Exception {
        try {
            persistence.addTimeEntries(new int[] { 500 }, 1, " ", true);
            fail("Empty user, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addTimeEntries(int[], int, String, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddTimeEntries_InvalidProjectId() throws Exception {
        try {
            persistence.addTimeEntries(new int[] { 500 }, 2, USER, true);
            fail("Invalid project, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addTimeEntries(int[], int, String, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddTimeEntries_InvalidEntryId() throws Exception {
        try {
            persistence.addTimeEntries(new int[] { -500 }, 2, USER, true);
            fail("Invalid entry, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeTimeEntries(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveTimeEntries_NullArray() throws Exception {
        try {
            persistence.removeTimeEntries(null, 1, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeTimeEntries(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveTimeEntries_EmptyArray() throws Exception {
        try {
            persistence.removeTimeEntries(new int[0], 1, true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeTimeEntries(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveTimeEntries_InvalidProjectId() throws Exception {
        try {
            persistence.removeTimeEntries(new int[]{500}, 2, true);
            fail("No such project, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeTimeEntries(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveTimeEntries_InvalidEntryId() throws Exception {
        try {
            persistence.removeTimeEntries(new int[]{-500}, 2, true);
            fail("No such entry id, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addExpenseEntries(int[], int, String, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddExpenseEntries_NullIds() throws Exception {
        try {
            persistence.addExpenseEntries(null, 1, USER, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addExpenseEntries(int[], int, String, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddExpenseEntries_NullUser() throws Exception {
        try {
            persistence.addExpenseEntries(new int[]{0}, 1, null, true);
            fail("Null user, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addExpenseEntries(int[], int, String, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddExpenseEntries_EmptyUser() throws Exception {
        try {
            persistence.addExpenseEntries(new int[]{0}, 1, " ", true);
            fail("Empty user, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addExpenseEntries(int[], int, String, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddExpenseEntries_InvalidProjectId() throws Exception {
        try {
            persistence.addExpenseEntries(new int[]{0}, 2, USER, true);
            fail("No such project, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#addExpenseEntries(int[], int, String, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddExpenseEntries_InvalidEntryId() throws Exception {
        try {
            persistence.addExpenseEntries(new int[]{111}, 1, USER, true);
            fail("No such entry id, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeExpenseEntries(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveExpenseEntries_NullIds() throws Exception {
        try {
            persistence.removeExpenseEntries(null, 1, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeExpenseEntries(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveExpenseEntries_EmptyIds() throws Exception {
        try {
            persistence.removeExpenseEntries(new int[0], 1, true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeExpenseEntries(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveExpenseEntries_InvalidProjectId() throws Exception {
        try {
            persistence.removeExpenseEntries(new int[]{1}, 2, true);
            fail("No such project, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#removeExpenseEntries(int[], int, boolean)} method.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveExpenseEntries_InvalidEntryId() throws Exception {
        try {
            persistence.removeExpenseEntries(new int[]{111}, 1, true);
            fail("N such entry, BatchOperationException expected.");
        } catch (BatchOperationException ex) {
            // ok
        }
    }

    // since 2.0

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getCompanyIdForClient(int)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForClient_IllegalId() throws Exception {
        try {
            persistence.getCompanyIdForClient(-1);
            fail("Id == -1, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getCompanyIdForProject(int)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForProject_IllegalId() throws Exception {
        try {
            persistence.getCompanyIdForProject(-1);
            fail("Id == -1, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getCompanyIdForTimeEntry(int)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForTimeEntry_IllegalId() throws Exception {
        try {
            persistence.getCompanyIdForTimeEntry(-1);
            fail("Id == -1, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getCompanyIdForExpenseEntry(int)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForExpenseEntry_IllegalId() throws Exception {
        try {
            persistence.getCompanyIdForExpenseEntry(-1);
            fail("Id == -1, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#getCompanyIdForUserAccount(int)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForUserAccount_IllegalId() throws Exception {
        try {
            persistence.getCompanyIdForUserAccount(-1);
            fail("Id == -1, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#existClientWithNameForCompany(Client, boolean)}
     * method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testExistClientWithNameForCompany_NullClient() throws Exception {
        try {
            persistence.existClientWithNameForCompany(null, true);
            fail("Null client, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link InformixTimeTrackerProjectPersistence#existClientWithNameForCompany(Client, boolean)}
     * method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testExistClientWithNameForCompany_NullName() throws Exception {
        Client client = FailureTestHelper.createInsufficientClient();
        try {
            persistence.existClientWithNameForCompany(client, true);
            fail("Null client, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
}
