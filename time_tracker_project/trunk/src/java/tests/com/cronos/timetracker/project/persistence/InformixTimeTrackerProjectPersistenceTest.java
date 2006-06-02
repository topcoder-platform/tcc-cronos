/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.persistence;

import com.cronos.timetracker.project.Client;
import com.cronos.timetracker.project.Project;
import com.cronos.timetracker.project.ProjectManager;
import com.cronos.timetracker.project.ProjectWorker;
import com.cronos.timetracker.project.TestHelper;
import com.cronos.timetracker.project.accuracytests.Helper;
import com.cronos.timetracker.project.searchfilters.CompareOperation;
import com.cronos.timetracker.project.searchfilters.Filter;
import com.cronos.timetracker.project.searchfilters.ValueFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.math.BigDecimal;

import java.util.List;

/**
 * Unit tests for InformixTimeTrackerProjectPersistence implementation.
 *
 * @author colau, costty000
 * @author TCSDEVELOPER
 * @version 2.0
 *
 * @since 1.0
 */
public class InformixTimeTrackerProjectPersistenceTest extends TestCase {
    /**
     * The InformixTimeTrackerProjectPersistence instance to test against.
     */
    private InformixTimeTrackerProjectPersistence persistence = null;

    /**
     * The valid client argument supplied to the methods.
     */
    private Client client = null;

    /**
     * The valid project argument supplied to the methods.
     */
    private Project project = null;

    /**
     * The valid project manager argument supplied to the methods.
     */
    private ProjectManager projectManager = null;

    /**
     * The valid project worker argument supplied to the methods.
     */
    private ProjectWorker projectWorker = null;

    /**
     * The client id argument supplied to the methods.
     */
    private int clientId = -1;

    /**
     * The project id argument supplied to the methods.
     */
    private int projectId = -1;

    /**
     * The project manager id argument supplied to the methods.
     */
    private int managerId = -1;

    /**
     * The project worker id argument supplied to the methods.
     */
    private int workerId = -1;

    /**
     * The entry id argument supplied to the methods.
     */
    private int entryId = -1;

    /**
     * The valid user argument supplied to the methods.
     */
    private String user = null;

    /**
     * The client ids argument supplied to the methods.
     */
    private int[] clientIds = null;

    /**
     * The project ids argument supplied to the methods.
     */
    private int[] projectIds = null;

    /**
     * The worker ids argument supplied to the methods.
     */
    private int[] workerIds = null;

    /**
     * The entry ids argument supplied to the methods.
     */
    private int[] entryIds = null;

    /**
     * The valid clients argument supplied to the methods.
     */
    private Client[] clients = null;

    /**
     * The valid projects argument supplied to the methods.
     */
    private Project[] projects = null;

    /**
     * The valid workers argument supplied to the methods.
     */
    private ProjectWorker[] workers = null;

    /**
     * The valid filter argument supplied to the methods.
     */
    private Filter filter = null;

    /**
     * The atomic argument supplied to the methods.
     */
    private boolean atomic = true;

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(
                InformixTimeTrackerProjectPersistenceTest.class);

        return suite;
    }

    /**
     * Prepares an InformixTimeTrackerProjectPersistence instance for testing.
     * Also prepares the method arguments.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfig();
        DBHelper.clearTables();

        // prepare the user, filter
        user = "user";
        filter = new ValueFilter(CompareOperation.LIKE, "Name", "name%");

        // prepare the ids for client, project, manager, worker and entries
        clientId = 100;
        projectId = 200;
        managerId = 300;
        workerId = 400;
        entryId = 500;

        // prepare the ids for clients, projects, workers and entries
        clientIds = TestHelper.createIds(100);
        projectIds = TestHelper.createIds(200);
        workerIds = TestHelper.createIds(400);
        entryIds = TestHelper.createIds(500);

        // prepare the client, project, manager, worker
        client = TestHelper.createClient(clientId);
        project = TestHelper.createProject(projectId);

        projectManager = TestHelper.createManager();
        projectWorker = TestHelper.createWorker();

        // set the id and project of manager and worker
        projectManager.setManagerId(managerId);
        projectManager.setProject(project);
        projectWorker.setWorkerId(workerId);
        projectWorker.setProject(project);

        // prepare the clients, projects, workers
        clients = TestHelper.createClients();
        projects = TestHelper.createProjects();
        workers = TestHelper.createWorkers();

        for (int i = 0; i < TestHelper.ARRAY_SIZE; i++) {
            clients[i].setId(clientIds[i]);
            projects[i].setId(projectIds[i]);
            workers[i].setWorkerId(workerIds[i]);
            workers[i].setProject(project);
        }

        persistence = new InformixTimeTrackerProjectPersistence(
                TestHelper.DB_NAMESPACE, TestHelper.CONNECTION_PRODUCER_NAME);
    }

    /**
     * Clears all the namespaces and tables in the database.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void tearDown() throws Exception {
        DBHelper.clearTables();
        TestHelper.unloadConfig();
        persistence.closeConnection();
    }

    /**
     * Test of constructor with null db namespace. Expects NullPointerException
     * for one-argument constructor and two-argument constructor. Expects
     * IllegalArgumentException for four-argument constructor.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testConstructor_NullDbNamespace() throws Exception {
        // try one-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence(null);
            fail("Creates InformixTimeTrackerProjectPersistence with null db namespace");
        } catch (NullPointerException e) {
            // good
        }

        // try two-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence(null,
                    TestHelper.CONNECTION_PRODUCER_NAME);
            fail("Creates InformixTimeTrackerProjectPersistence with null db namespace");
        } catch (NullPointerException e) {
            // good
        }

        // try four-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence(null,
                    TestHelper.CONNECTION_PRODUCER_NAME,
                    TestHelper.PROJECT_SEARCH_UTILITY_NAMESPACE,
                    TestHelper.CLIENT_SEARCH_UTILITY_NAMESPACE);
            fail("Creates InformixTimeTrackerProjectPersistence with null db namespace");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with empty db namespace. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testConstructor_EmptyDbNamespace() throws Exception {
        // try one-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence("");
            fail("Creates InformixTimeTrackerProjectPersistence with empty db namespace");
        } catch (IllegalArgumentException e) {
            // good
        }

        // try two-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence("",
                    TestHelper.CONNECTION_PRODUCER_NAME);
            fail("Creates InformixTimeTrackerProjectPersistence with empty db namespace");
        } catch (IllegalArgumentException e) {
            // good
        }

        // try four-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence("",
                    TestHelper.CONNECTION_PRODUCER_NAME,
                    TestHelper.PROJECT_SEARCH_UTILITY_NAMESPACE,
                    TestHelper.CLIENT_SEARCH_UTILITY_NAMESPACE);
            fail("Creates InformixTimeTrackerProjectPersistence with empty db namespace");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with null connection producer name. Expects
     * NullPointerException for two-argument constructor. Expects
     * IllegalArgumentException for four-argument constructor.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testConstructor_NullConnProdName() throws Exception {
        // try two-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence(TestHelper.DB_NAMESPACE,
                    null);
            fail("Creates InformixTimeTrackerProjectPersistence with null connection producer name");
        } catch (NullPointerException e) {
            // good
        }

        // try four-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence(TestHelper.DB_NAMESPACE,
                    null, TestHelper.PROJECT_SEARCH_UTILITY_NAMESPACE,
                    TestHelper.CLIENT_SEARCH_UTILITY_NAMESPACE);
            fail("Creates InformixTimeTrackerProjectPersistence with null connection producer name");
        } catch (IllegalArgumentException e) {
            // good
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
        // try two-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence(TestHelper.DB_NAMESPACE,
                    "");
            fail("Creates InformixTimeTrackerProjectPersistence with empty connection producer name");
        } catch (IllegalArgumentException e) {
            // good
        }

        // try four-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence(TestHelper.DB_NAMESPACE,
                    "", TestHelper.PROJECT_SEARCH_UTILITY_NAMESPACE,
                    TestHelper.CLIENT_SEARCH_UTILITY_NAMESPACE);
            fail("Creates InformixTimeTrackerProjectPersistence with empty connection producer name");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with null project search utility namespace. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testConstructor_NullProjectNamespace() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(TestHelper.DB_NAMESPACE,
                    TestHelper.CONNECTION_PRODUCER_NAME, null,
                    TestHelper.CLIENT_SEARCH_UTILITY_NAMESPACE);
            fail("Creates InformixTimeTrackerProjectPersistence with null project search utility namespace");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with empty project search utility namespace. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testConstructor_EmptyProjectNamespace() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(TestHelper.DB_NAMESPACE,
                    TestHelper.CONNECTION_PRODUCER_NAME, "",
                    TestHelper.CLIENT_SEARCH_UTILITY_NAMESPACE);
            fail("Creates InformixTimeTrackerProjectPersistence with empty project search utility namespace");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with null client search utility namespace. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testConstructor_NullClientNamespace() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(TestHelper.DB_NAMESPACE,
                    TestHelper.CONNECTION_PRODUCER_NAME,
                    TestHelper.PROJECT_SEARCH_UTILITY_NAMESPACE, null);
            fail("Creates InformixTimeTrackerProjectPersistence with null client search utility namespace");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of constructor with empty client search utility namespace. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testConstructor_EmptyClientNamespace() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(TestHelper.DB_NAMESPACE,
                    TestHelper.CONNECTION_PRODUCER_NAME,
                    TestHelper.PROJECT_SEARCH_UTILITY_NAMESPACE, "");
            fail("Creates InformixTimeTrackerProjectPersistence with empty client search utility namespace");
        } catch (IllegalArgumentException e) {
            // good
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
            // good
        }
    }

    /**
     * Test of addClient method with existing client. Verifies if false is
     * returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddClient_ExistClient() throws Exception {
        persistence.addClient(client);

        assertFalse("Adds an existing client", persistence.addClient(client));
    }

    /**
     * Test of addClient method with non-existing client. Adds a project to the
     * client. Verifies if true is returned. Also verifies if the client was
     * added to the client table, the project was added to the project table,
     * and the project was assigned the client in the client_project table.
     * Lastly, verifies if the date fields of the Client instance are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddClient_NonExistClient() throws Exception {
        client.addProject(project);

        assertTrue("Fails to add non-existing client", persistence
                .addClient(client));
        assertNotNull("Fails to add to client table", persistence
                .getClient(clientId));
        assertNotNull("Fails to add to project table", persistence
                .getProject(projectId));
        assertNotNull("Fails to add to client_project table", persistence
                .getClientProject(clientId, projectId));

        assertNotNull("Fails to populate CreationDate", client
                .getCreationDate());
        assertNotNull("Fails to populate ModificationDate", client
                .getModificationDate());
    }

    /**
     * Test of removeClient method with non-existing client id. Verifies if
     * false is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveClient_NonExistClient() throws Exception {
        assertFalse("Removes non-existing client", persistence.removeClient(-1));
    }

    /**
     * Test of removeClient method with existing client id. Adds a project to
     * the client. Verifies if true is returned. Also verifies if the client was
     * removed from the client table, and the project was removed from the
     * client in the client_project table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveClient_ExistClient() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        assertTrue("Fails to remove existing client", persistence
                .removeClient(clientId));
        assertNull("Fails to remove from client table", persistence
                .getClient(clientId));
        assertNull("Fails to remove from client_project table", persistence
                .getClientProject(clientId, projectId));
    }

    /**
     * Test of removeAllClients method with empty clients. Verifies if the
     * client and client_project tables are still empty after calling it.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveAllClients_EmptyClients() throws Exception {
        persistence.removeAllClients();

        assertTrue("Fails to clear client table", DBHelper
                .isEmptyTable("client"));
        assertTrue("Fails to clear client_project table", DBHelper
                .isEmptyTable("client_project"));
    }

    /**
     * Test of removeAllClients method with non-empty clients. Verifies if the
     * client and client_project tables are empty after calling it.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveAllClients_NonEmptyClients() throws Exception {
        for (int i = 0; i < 3; i++) {
            Project project = TestHelper.createProject(i);
            Client client = TestHelper.createClient(i);

            client.addProject(project);
            persistence.addClient(client);
        }

        persistence.removeAllClients();

        assertTrue("Fails to clear client table", DBHelper
                .isEmptyTable("client"));
        assertTrue("Fails to clear client_project table", DBHelper
                .isEmptyTable("client_project"));
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
            // good
        }
    }

    /**
     * Test of updateClient method with non-existing client. Verifies if false
     * is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateClient_NonExistClient() throws Exception {
        assertFalse("Updates a non-existing client", persistence
                .updateClient(client));
    }

    /**
     * Test of updateClient method with existing client. Updates the name field.
     * Verifies if true is returned. Also verifies if the name field was updated
     * in the client table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateClient_ExistClient() throws Exception {
        persistence.addClient(client);

        // update the name field
        client.setName("update");

        assertTrue("Fails to update existing client", persistence
                .updateClient(client));

        Client result = persistence.getClient(clientId);

        assertEquals("Fails to update client table", "update", result.getName());
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
            persistence.addProjectToClient(clientId, null, user);
            fail("Adds null project to client");
        } catch (NullPointerException e) {
            // good
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
            persistence.addProjectToClient(clientId, project, null);
            fail("Adds project to client with null user");
        } catch (NullPointerException e) {
            // good
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
            persistence.addProjectToClient(clientId, project, "");
            fail("Adds project to client with empty user");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addProjectToClient method with existing project to the client.
     * Verifies if false is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProjectToClient_ExistProject() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        assertFalse("Adds existing project to the client", persistence
                .addProjectToClient(clientId, project, user));
    }

    /**
     * Test of addProjectToClient method with non-existing project to the
     * client. Verifies if true is returned. Also verifies if the project was
     * added to the project table, and the project was assigned the client in
     * the client_project table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProjectToClient_NonExistProject() throws Exception {
        persistence.addClient(client);

        assertTrue("Fails to add non-existing project to the client",
                persistence.addProjectToClient(clientId, project, user));
        assertNotNull("Fails to add to project table", persistence
                .getProject(projectId));
        assertNotNull("Fails to add to client_project table", persistence
                .getClientProject(clientId, projectId));
    }

    /**
     * Test of getClientProject method with non-existing project to the client.
     * Verifies if null is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetClientProject_NonExistProject() throws Exception {
        assertNull("Gets non-existing project to the client", persistence
                .getClientProject(clientId, projectId));
    }

    /**
     * Test of getClientProject method with existing project to the client.
     * Verifies if the fields of the returned project are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetClientProject_ExistProject() throws Exception {
        client.addProject(project);
        persistence.addClient(client);
/*        ProjectManager m1 = TestHelper.createManager();
        m1.setManagerId(1);
        m1.setProject(project);
        persistence.assignProjectManager(m1);
*/
        Project result = persistence.getClientProject(clientId, projectId);

        assertTrue("Returns an incorrect client project", TestHelper
                .compareProjects(project, result));
    }

    /**
     * Test of getAllClientProjects method with non-existing projects to the
     * client. Verifies if an empty list is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetAllClientProjects_NonExistProjects() throws Exception {
        assertTrue("Returns an incorrect list of client projects", persistence
                .getAllClientProjects(clientId).isEmpty());
    }

    /**
     * Test of getAllClientProjects method with existing projects to the client.
     * Verifies if it returns a list containing those projects. Verifies if the
     * fields of the returned projects are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetAllClientProjects_ExistProjects() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

/*//        ProjectManager m1 = TestHelper.createManager();
//        m1.setManagerId(1);
//        m1.setProject(project);
//        persistence.assignProjectManager(m1);
*/
        List results = persistence.getAllClientProjects(clientId);

        assertEquals("Returns an incorrect list of client projects", 1, results
                .size());

        Project result = (Project) results.get(0);

        assertTrue("Returns an incorrect list of client projects", TestHelper
                .compareProjects(project, result));
    }

    /**
     * Test of getClient method with non-existing client. Verifies if null is
     * returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetClient_NonExistClient() throws Exception {
        assertNull("Returns an incorrect client", persistence
                .getClient(clientId));
    }

    /**
     * Test of getClient method with existing client. Verifies if the client is
     * returned. Verifies if the fields of the returned client are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetClient_ExistClient() throws Exception {
        persistence.addClient(client);

        Client result = persistence.getClient(clientId);

        assertTrue("Returns an incorrect client", TestHelper.compareClients(
                client, result));
    }

    /**
     * Test of getAllClients method with empty clients. Verifies if an empty
     * list is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetAllClients_EmptyClients() throws Exception {
        assertTrue("Returns an incorrect list of clients", persistence
                .getAllClients().isEmpty());
    }

    /**
     * Test of getAllClients method with non-empty clients. Verifies if it
     * returns a list containing those clients. Verifies if the fields of the
     * returned clients are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetAllClients_NonEmptyClients() throws Exception {
        persistence.addClient(client);

        List results = persistence.getAllClients();

        assertEquals("Returns an incorrect list of clients", 1, results.size());

        Client result = (Client) results.get(0);

        assertTrue("Returns an incorrect list of clients", TestHelper
                .compareClients(client, result));
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
            // good
        }
    }

    /**
     * Test of addProject method with existing project. Verifies if false is
     * returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProject_ExistProject() throws Exception {
        persistence.addProject(project);

        assertFalse("Adds an existing project", persistence.addProject(project));
    }

    /**
     * Test of addProject method with non-existing project. Verifies if true is
     * returned. Also verifies if the project was added to the project table.
     * Lastly, verifies if the date fields of the Project instance are
     * populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProject_NonExistProject() throws Exception {
        assertTrue("Fails to add non-existing project", persistence
                .addProject(project));
        assertNotNull("Fails to add to project table", persistence
                .getProject(projectId));

        assertNotNull("Fails to populate CreationDate", project
                .getCreationDate());
        assertNotNull("Fails to populate ModificationDate", project
                .getModificationDate());
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
            // good
        }
    }

    /**
     * Test of updateProject method with non-existing project. Verifies if false
     * is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateProject_NonExistProject() throws Exception {
        assertFalse("Updates a non-existing project", persistence
                .updateProject(project));
    }

    /**
     * Test of updateProject method with existing project. Updates the name
     * field. Verifies if true is returned. Also verifies if the name field was
     * updated in the project table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateProject_ExistProject() throws Exception {
        persistence.addProject(project);

        // update the name field
        project.setName("update");

        assertTrue("Fails to update existing project", persistence
                .updateProject(project));

        Project result = persistence.getProject(projectId);

        assertEquals("Fails to update project table", "update", result
                .getName());
    }

    /**
     * Test of removeProject method with non-existing project. Verifies if false
     * is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveProject_NonExistProject() throws Exception {
        assertFalse("Removes non-existing project", persistence
                .removeProject(-1));
    }

    /**
     * Test of removeProject method with existing project. Adds some client,
     * worker, manager, time entry and expense entry to the project. Verifies if
     * true is returned. Also verifies if the corresponding rows in the
     * client_project, project_worker, project_manager, project_time,
     * project_expense and project tables were removed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveProject_ExistProject() throws Exception {
        client.addProject(project);

        persistence.addClient(client);
        persistence.addWorker(projectWorker);
        persistence.assignProjectManager(projectManager);
        persistence.addTimeEntry(entryId, projectId, user);
        persistence.addExpenseEntry(entryId, projectId, user);

        assertTrue("Fails to remove existing project", persistence
                .removeProject(projectId));
        assertNull("Fails to remove from client_project table", persistence
                .getClientProject(clientId, projectId));
        assertTrue("Fails to remove from project_worker table", persistence
                .getWorkers(projectId).isEmpty());
        assertNull("Fails to remove from project_manager table", persistence
                .getProjectManager(projectId));
        assertTrue("Fails to remove from project_time table", persistence
                .getTimeEntries(projectId).isEmpty());
        assertTrue("Fails to remove from project_expense table", persistence
                .getExpenseEntries(projectId).isEmpty());
        assertNull("Fails to remove from project table", persistence
                .getProject(projectId));
    }

    /**
     * Test of removeAllProjects method with empty projects. Verifies if the
     * client_project, project_worker, project_manager, project_time,
     * project_expense and project tables are still empty after calling it.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveAllProjects_EmptyProjects() throws Exception {
        persistence.removeAllProjects();

        assertTrue("Fails to clear client_project table", DBHelper
                .isEmptyTable("client_project"));
        assertTrue("Fails to clear project_worker table", DBHelper
                .isEmptyTable("project_worker"));
        assertTrue("Fails to clear project_manager table", DBHelper
                .isEmptyTable("project_manager"));
        assertTrue("Fails to clear project_time table", DBHelper
                .isEmptyTable("project_time"));
        assertTrue("Fails to clear project_expense table", DBHelper
                .isEmptyTable("project_expense"));
        assertTrue("Fails to clear project table", DBHelper
                .isEmptyTable("project"));
    }

    /**
     * Test of removeAllProjects method with non-empty projects. Verifies if the
     * client_project, project_worker, project_manager, project_time,
     * project_expense and project tables are empty after calling it.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveAllProjects_NonEmptyProjects() throws Exception {
        client.addProject(project);

        persistence.addClient(client);
        persistence.addWorker(projectWorker);
        persistence.assignProjectManager(projectManager);
        persistence.addTimeEntry(entryId, projectId, user);
        persistence.addExpenseEntry(entryId, projectId, user);

        // add more projects, time entries and expense entries
        for (int i = 0; i < 3; i++) {
            persistence.addProject(TestHelper.createProject(i));
            persistence.addTimeEntry(i, projectId, user);
            persistence.addExpenseEntry(i, projectId, user);
        }

        persistence.removeAllProjects();

        assertTrue("Fails to clear client_project table", DBHelper
                .isEmptyTable("client_project"));
        assertTrue("Fails to clear project_worker table", DBHelper
                .isEmptyTable("project_worker"));
        assertTrue("Fails to clear project_manager table", DBHelper
                .isEmptyTable("project_manager"));
        assertTrue("Fails to clear project_time table", DBHelper
                .isEmptyTable("project_time"));
        assertTrue("Fails to clear project_expense table", DBHelper
                .isEmptyTable("project_expense"));
        assertTrue("Fails to clear project table", DBHelper
                .isEmptyTable("project"));
    }

    /**
     * Test of assignClient method with null user. Expects NullPointerException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAssignClient_NullUser() throws Exception {
        try {
            persistence.assignClient(projectId, clientId, null);
            fail("Assigns client with null user");
        } catch (NullPointerException e) {
            // good
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
            persistence.assignClient(projectId, clientId, "");
            fail("Assigns client with empty user");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of assignClient method with existing client to the project. Verifies
     * if false is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAssignClient_ExistClient() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        assertFalse("Adds existing client to the project", persistence
                .assignClient(projectId, clientId, user));
    }

    /**
     * Test of assignClient method with non-existing client to the project.
     * Verifies if true is returned. Also verifies if the client was added to
     * the client_project table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAssignClient_NonExistClient() throws Exception {
        persistence.addClient(client);
        persistence.addProject(project);

        assertTrue("Fails to add non-existing client to the project",
                persistence.assignClient(projectId, clientId, user));
        assertNotNull("Fails to add to client_project table", persistence
                .getClientProject(clientId, projectId));
    }

    /**
     * Test of getProjectClient method with non-existing client for the project.
     * Verifies if null is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetProjectClient_NonExistClient() throws Exception {
        assertNull("Returns an incorrect client for the project", persistence
                .getProjectClient(projectId));
    }

    /**
     * Test of getProjectClient method with existing client for the project.
     * Verifies if the fields of the returned client are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetProjectClient_ExistClient() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        Client result = persistence.getProjectClient(projectId);

        assertTrue("Returns an incorrect client for the project", TestHelper
                .compareClients(client, result));
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
            // good
        }
    }

    /**
     * Test of assignProjectManager method with existing manager. Verifies if
     * false is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAssignProjectManager_ExistManager() throws Exception {
        persistence.addProject(project);
        persistence.assignProjectManager(projectManager);

        assertFalse("Adds an existing manager", persistence
                .assignProjectManager(projectManager));
    }

    /**
     * Test of assignProjectManager method with non-existing manager. Verifies
     * if true is returned. Also verifies if the manager was added to the
     * project_manager table. Lastly, verifies if the date fields of the
     * ProjectManager instance are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAssignProjectManager_NonExistManager() throws Exception {
        persistence.addProject(project);

        assertTrue("Fails to add non-existing manager", persistence
                .assignProjectManager(projectManager));
        assertNotNull("Fails to add to project_manager table", persistence
                .getProjectManager(projectId));

        assertNotNull("Fails to populate CreationDate", projectManager
                .getCreationDate());
        assertNotNull("Fails to populate ModificationDate", projectManager
                .getModificationDate());
    }

    /**
     * Test of getProjectManager method with non-existing manager. Verifies if
     * null is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetProjectManager_NonExistManager() throws Exception {
        assertNull("Returns an incorrect manager", persistence
                .getProjectManager(projectId));
    }

    /**
     * Test of getProjectManager method with existing manager. Verifies if the
     * fields of the returned manager are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetProjectManager_ExistManager() throws Exception {
        persistence.addProject(project);
        persistence.assignProjectManager(projectManager);

        project.setManagerId(managerId);

        ProjectManager result = persistence.getProjectManager(projectId);

        assertTrue("Returns an incorrect manager", TestHelper
                .compareProjectManagers(projectManager, result));
    }

    /**
     * Test of removeProjectManager method with non-existing manager. Verifies
     * if false is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveProjectManager_NonExistManager() throws Exception {
        assertFalse("Removes a non-existing manager", persistence
                .removeProjectManager(managerId, projectId));
    }

    /**
     * Test of removeProjectManager method with existing manager. Verifies if
     * true is returned. Also verifies if the manager was removed from the
     * project_manager table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveProjectManager_ExistManager() throws Exception {
        persistence.addProject(project);
        persistence.assignProjectManager(projectManager);

        assertTrue("Fails to remove existing manager", persistence
                .removeProjectManager(managerId, projectId));
        assertNull("Fails to remove from project_manager table", persistence
                .getProjectManager(projectId));
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
            // good
        }
    }

    /**
     * Test of addWorker method with existing worker. Verifies if false is
     * returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddWorker_ExistWorker() throws Exception {
        persistence.addProject(project);
        persistence.addWorker(projectWorker);

        assertFalse("Adds an existing worker", persistence
                .addWorker(projectWorker));
    }

    /**
     * Test of addWorker method with non-existing worker. Verifies if true is
     * returned. Also verifies if the worker was added to the project_worker
     * table. Lastly, verifies if the date fields of the ProjectWorker instance
     * are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddWorker_NonExistWorker() throws Exception {
        persistence.addProject(project);

        assertTrue("Fails to add non-existing worker", persistence
                .addWorker(projectWorker));
        assertNotNull("Fails to add to project_worker table", persistence
                .getWorker(workerId, projectId));

        assertNotNull("Fails to populate CreationDate", projectWorker
                .getCreationDate());
        assertNotNull("Fails to populate ModificationDate", projectWorker
                .getModificationDate());
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
            // good
        }
    }

    /**
     * Test of updateWorker method with non-existing worker. Verifies if false
     * is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateWorker_NonExistWorker() throws Exception {
        assertFalse("Updates a non-existing worker", persistence
                .updateWorker(projectWorker));
    }

    /**
     * Test of updateWorker method with existing worker. Updates the pay rate
     * field. Verifies if true is returned. Also verifies if the pay rate field
     * was updated in the project_worker table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateWorker_ExistWorker() throws Exception {
        persistence.addProject(project);
        persistence.addWorker(projectWorker);

        // update the pay rate field
        projectWorker.setPayRate(new BigDecimal(20));

        assertTrue("Fails to update existing worker", persistence
                .updateWorker(projectWorker));

        ProjectWorker result = persistence.getWorker(workerId, projectId);

        assertTrue("Fails to update project_worker table", result.getPayRate()
                .compareTo(new BigDecimal(20)) == 0);
    }

    /**
     * Test of removeWorker method with non-existing worker. Verifies if false
     * is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveWorker_NonExistWorker() throws Exception {
        assertFalse("Removes a non-existing worker", persistence.removeWorker(
                workerId, projectId));
    }

    /**
     * Test of removeWorker method with existing worker. Verifies if true is
     * returned. Also verifies if the worker was removed from the project_worker
     * table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveWorker_ExistWorker() throws Exception {
        persistence.addProject(project);
        persistence.addWorker(projectWorker);

        assertTrue("Fails to remove existing worker", persistence.removeWorker(
                workerId, projectId));
        assertNull("Fails to remove from project_worker table", persistence
                .getWorker(workerId, projectId));
    }

    /**
     * Test of removeWorkers method with non-existing workers. Verifies if false
     * is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveWorkers_NonExistWorkers() throws Exception {
        assertFalse("Removes non-existing workers", persistence
                .removeWorkers(projectId));
    }

    /**
     * Test of removeWorkers method with existing workers. Verifies if true is
     * returned. Also verifies if the workers were removed from the
     * project_worker table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveWorkers_ExistWorkers() throws Exception {
        persistence.addProject(project);
        persistence.addWorker(projectWorker);

        assertTrue("Fails to remove existing workers", persistence
                .removeWorkers(projectId));
        assertTrue("Fails to remove from project_worker table", persistence
                .getWorkers(projectId).isEmpty());
    }

    /**
     * Test of getWorker method with non-existing worker. Verifies if null is
     * returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetWorker_NonExistWorker() throws Exception {
        assertNull("Returns an incorrect worker", persistence.getWorker(
                workerId, projectId));
    }

    /**
     * Test of getWorker method with existing worker. Verifies if the fields of
     * the returned worker are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetWorker_ExistWorker() throws Exception {
        persistence.addProject(project);
        persistence.addWorker(projectWorker);

        project.addWorkerId(workerId);

        ProjectWorker result = persistence.getWorker(workerId, projectId);

        assertTrue("Returns an incorrect worker", TestHelper
                .compareProjectWorkers(projectWorker, result));
    }

    /**
     * Test of getWorkers method with non-existing workers. Verifies if an empty
     * list is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetWorkers_NonExistWorkers() throws Exception {
        assertTrue("Returns an incorrect list of workers", persistence
                .getWorkers(projectId).isEmpty());
    }

    /**
     * Test of getWorkers method with existing workers. Verifies if it returns a
     * list containing those workers. Verifies if the fields of the returned
     * workers are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetWorkers_ExistWorkers() throws Exception {
        persistence.addProject(project);
        persistence.addWorker(projectWorker);

        project.addWorkerId(workerId);

        List results = persistence.getWorkers(projectId);

        assertEquals("Returns an incorrect list of workers", 1, results.size());

        ProjectWorker result = (ProjectWorker) results.get(0);

        assertTrue("Returns an incorrect list of workers", TestHelper
                .compareProjectWorkers(projectWorker, result));
    }

    /**
     * Test of addTimeEntry method with null user. Expects NullPointerException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddTimeEntry_NullUser() throws Exception {
        try {
            persistence.addTimeEntry(entryId, projectId, null);
            fail("Adds time entry with null user");
        } catch (NullPointerException e) {
            // good
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
            persistence.addTimeEntry(entryId, projectId, "");
            fail("Adds time entry with empty user");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addTimeEntry method with existing time entry. Verifies if false
     * is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddTimeEntry_ExistEntry() throws Exception {
        persistence.addProject(project);
        persistence.addTimeEntry(entryId, projectId, user);

        assertFalse("Adds an existing time entry", persistence.addTimeEntry(
                entryId, projectId, user));
    }

    /**
     * Test of addTimeEntry method with non-existing time entry. Verifies if
     * true is returned. Also verifies if the time entry was added to the
     * project_time table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddTimeEntry_NonExistEntry() throws Exception {
        persistence.addProject(project);

        assertTrue("Fails to add non-existing time entry", persistence
                .addTimeEntry(entryId, projectId, user));
        assertFalse("Fails to add to project_time table", persistence
                .getTimeEntries(projectId).isEmpty());
    }

    /**
     * Test of removeTimeEntry method with non-existing time entry. Verifies if
     * false is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveTimeEntry_NonExistEntry() throws Exception {
        assertFalse("Removes a non-existing time entry", persistence
                .removeTimeEntry(entryId, projectId));
    }

    /**
     * Test of removeTimeEntry method with existing time entry. Verifies if true
     * is returned. Also verifies if the time entry was removed from the
     * project_time table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveTimeEntry_ExistEntry() throws Exception {
        persistence.addProject(project);
        persistence.addTimeEntry(entryId, projectId, user);

        assertTrue("Fails to remove existing time entry", persistence
                .removeTimeEntry(entryId, projectId));
        assertTrue("Fails to remove from project_time table", persistence
                .getTimeEntries(projectId).isEmpty());
    }

    /**
     * Test of getTimeEntries method with empty time entries. Verifies if an
     * empty list is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetTimeEntries_EmptyEntries() throws Exception {
        assertTrue("Returns an incorrect list of time entries", persistence
                .getTimeEntries(projectId).isEmpty());
    }

    /**
     * Test of getTimeEntries method with non-empty time entries. Verifies if it
     * returns a list containing those time entries.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetTimeEntries_NonEmptyEntries() throws Exception {
        persistence.addProject(project);

        for (int i = 0; i < 3; i++) {
            persistence.addTimeEntry(i, projectId, user);
        }

        List results = persistence.getTimeEntries(projectId);

        assertEquals("Returns an incorrect list of time entries", 3, results
                .size());
        for (int i = 0; i < 3; i++) {
            assertTrue("Returns an incorrect list of time entries", results
                    .remove(new Integer(i)));
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
            persistence.addExpenseEntry(entryId, projectId, null);
            fail("Adds expense entry with null user");
        } catch (NullPointerException e) {
            // good
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
            persistence.addExpenseEntry(entryId, projectId, "");
            fail("Adds expense entry with empty user");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addExpenseEntry method with existing expense entry. Verifies if
     * false is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddExpenseEntry_ExistEntry() throws Exception {
        persistence.addProject(project);
        persistence.addExpenseEntry(entryId, projectId, user);

        assertFalse("Adds an existing expense entry", persistence
                .addExpenseEntry(entryId, projectId, user));
    }

    /**
     * Test of addExpenseEntry method with non-existing expense entry. Verifies
     * if true is returned. Also verifies if the expense entry was added to the
     * project_expense table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddExpenseEntry_NonExistEntry() throws Exception {
        persistence.addProject(project);

        assertTrue("Fails to add non-existing expense entry", persistence
                .addExpenseEntry(entryId, projectId, user));
        assertFalse("Fails to add to project_expense table", persistence
                .getExpenseEntries(projectId).isEmpty());
    }

    /**
     * Test of removeExpenseEntry method with non-existing expense entry.
     * Verifies if false is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveExpenseEntry_NonExistEntry() throws Exception {
        assertFalse("Removes a non-existing expense entry", persistence
                .removeExpenseEntry(entryId, projectId));
    }

    /**
     * Test of removeExpenseEntry method with existing expense entry. Verifies
     * if true is returned. Also verifies if the expense entry was removed from
     * the project_expense table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveExpenseEntry_ExistEntry() throws Exception {
        persistence.addProject(project);
        persistence.addExpenseEntry(entryId, projectId, user);

        assertTrue("Fails to remove existing expense entry", persistence
                .removeExpenseEntry(entryId, projectId));
        assertTrue("Fails to remove from project_expense table", persistence
                .getExpenseEntries(projectId).isEmpty());
    }

    /**
     * Test of getExpenseEntries method with empty expense entries. Verifies if
     * an empty list is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetExpenseEntries_EmptyEntries() throws Exception {
        assertTrue("Returns an incorrect list of expense entries", persistence
                .getExpenseEntries(projectId).isEmpty());
    }

    /**
     * Test of getExpenseEntries method with non-empty expense entries. Verifies
     * if it returns a list containing those expense entries.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetExpenseEntries_NonEmptyEntries() throws Exception {
        persistence.addProject(project);

        for (int i = 0; i < 3; i++) {
            persistence.addExpenseEntry(i, projectId, user);
        }

        List results = persistence.getExpenseEntries(projectId);

        assertEquals("Returns an incorrect list of expense entries", 3, results
                .size());
        for (int i = 0; i < 3; i++) {
            assertTrue("Returns an incorrect list of expense entries", results
                    .remove(new Integer(i)));
        }
    }

    /**
     * Test of getProject method with non-existing project. Verifies if null is
     * returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetProject_NonExistProject() throws Exception {
        assertNull("Returns an incorrect project", persistence
                .getProject(projectId));
    }

    /**
     * Test of getProject method with existing project. Adds some worker,
     * manager, time entry and expense entry to the project. Verifies if the
     * fields of the returned project are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetProject_ExistProject() throws Exception {
        persistence.addProject(project);
        persistence.addWorker(projectWorker);
        persistence.assignProjectManager(projectManager);
        persistence.addTimeEntry(entryId, projectId, user);
        persistence.addExpenseEntry(entryId, projectId, user);

        // populate the source project fields for comparison
        project.addWorkerId(workerId);
        project.setManagerId(managerId);
        project.addTimeEntry(entryId);
        project.addExpenseEntry(entryId);

        Project result = persistence.getProject(projectId);

        assertTrue("Returns an incorrect project", TestHelper.compareProjects(
                project, result));
    }

    /**
     * Test of getAllProjects method with empty projects. Verifies if an empty
     * list is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetAllProjects_EmptyProjects() throws Exception {
        assertTrue("Returns an incorrect list of projects", persistence
                .getAllProjects().isEmpty());
    }

    /**
     * Test of getAllProjects method with non-empty projects. Verifies if it
     * returns a list containing those projects. Verifies if the fields of the
     * returned projects are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetAllProjects_NonEmptyProjects() throws Exception {
        persistence.addProject(project);
        persistence.addWorker(projectWorker);
        persistence.assignProjectManager(projectManager);
        persistence.addTimeEntry(entryId, projectId, user);
        persistence.addExpenseEntry(entryId, projectId, user);

        // populate the source project fields for comparison
        project.addWorkerId(workerId);
        project.setManagerId(managerId);
        project.addTimeEntry(entryId);
        project.addExpenseEntry(entryId);

        List results = persistence.getAllProjects();

        assertEquals("Returns an incorrect list of projects", 1, results.size());

        Project result = (Project) results.get(0);

        assertTrue("Returns an incorrect list of projects", TestHelper
                .compareProjects(project, result));
    }

    /**
     * Test of removeProjectFromClient method with non-existing project from
     * client. Verifies if false is returned.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveProjectFromClient_NonExistProject() throws Exception {
        assertFalse("Removes a non-existing project from client", persistence
                .removeProjectFromClient(clientId, projectId));
    }

    /**
     * Test of removeProjectFromClient method with existing project from client.
     * Verifies if true is returned. Also verifies if the project was removed
     * from the client_project table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveProjectFromClient_ExistProject() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        assertTrue("Fails to remove existing project from client", persistence
                .removeProjectFromClient(clientId, projectId));
        assertNull("Fails to remove from client_project table", persistence
                .getClientProject(clientId, projectId));
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
            persistence.addClient(client);
            fail("Fails to close connection");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Test of searchForProjects method with null filter. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testSearchForProjects_NullFilter() throws Exception {
        try {
            persistence.searchForProjects(null);
            fail("Search projects with null filter");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of searchForProjects method with matching filter. Verifies if it
     * returns the matching projects.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testSearchForProjects_MatchFilter() throws Exception {
        // add a project to match
        persistence.addProject(project);

        Project[] projects = persistence.searchForProjects(filter);

        assertEquals("Fails to search projects", 1, projects.length);
        assertTrue("Fails to search projects", TestHelper.compareProjects(
                project, projects[0]));
    }

    /**
     * Test of searchForProjects method with non-matching filter. Verifies if it
     * returns an empty array.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testSearchForProjects_NonMatchFilter() throws Exception {
        Project[] projects = persistence.searchForProjects(filter);

        assertEquals("Fails to search projects", 0, projects.length);
    }

    /**
     * Test of searchForClients method with null filter. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testSearchForClients_NullFilter() throws Exception {
        try {
            persistence.searchForClients(null);
            fail("Search clients with null filter");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of searchForClients method with matching filter. Verifies if it
     * returns the matching clients.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testSearchForClients_MatchFilter() throws Exception {
        // add a client to match
        persistence.addClient(client);

        Client[] clients = persistence.searchForClients(filter);

        assertEquals("Fails to search clients", 1, clients.length);
        assertTrue("Fails to search clients", TestHelper.compareClients(client,
                clients[0]));
    }

    /**
     * Test of searchForClients method with non-matching filter. Verifies if it
     * returns an empty array.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testSearchForClients_NonMatchFilter() throws Exception {
        Client[] clients = persistence.searchForClients(filter);

        assertEquals("Fails to search clients", 0, clients.length);
    }

    /**
     * Test of addClients method with null clients. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddClients_NullClients() throws Exception {
        try {
            persistence.addClients(null, atomic);
            fail("Adds null clients");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addClients method with empty clients. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddClients_EmptyClients() throws Exception {
        try {
            persistence.addClients(new Client[0], atomic);
            fail("Adds empty clients");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addClients method with bad clients containing null element.
     * Expects IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddClients_BadClients() throws Exception {
        try {
            persistence.addClients(new Client[] { null }, atomic);
            fail("Adds bad clients containing null element");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addClients method with non-existing clients. Verifies if the
     * clients were added to the client table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddClients_NonExistClients() throws Exception {
        persistence.addClients(clients, atomic);

        for (int i = 0; i < clientIds.length; i++) {
            assertNotNull("Fails to add clients", persistence
                    .getClient(clientIds[i]));
        }
    }

    /**
     * Test of addClients method with existing clients in atomic mode. Expects
     * BatchOperationException. Also verifies if the non-existing clients were
     * not added to the client table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddClients_ExistClients_Atomic() throws Exception {
        // make sure some client exists
        persistence.addClient(clients[0]);

        try {
            persistence.addClients(clients, true);
            fail("Adds existing clients");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < clientIds.length; i++) {
            assertNull("Fails to add clients", persistence
                    .getClient(clientIds[i]));
        }
    }

    /**
     * Test of addClients method with existing clients in non-atomic mode.
     * Expects BatchOperationException. Also verifies if the non-existing
     * clients were added to the client table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddClients_ExistClients_NonAtomic() throws Exception {
        // make sure some client exists
        persistence.addClient(clients[0]);

        try {
            persistence.addClients(clients, false);
            fail("Adds existing clients");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < clientIds.length; i++) {
            assertNotNull("Fails to add clients", persistence
                    .getClient(clientIds[i]));
        }
    }

    /**
     * Test of removeClients method with null client ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveClients_NullClientIds() throws Exception {
        try {
            persistence.removeClients(null, atomic);
            fail("Removes clients with null client ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of removeClients method with empty client ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveClients_EmptyClientIds() throws Exception {
        try {
            persistence.removeClients(new int[0], atomic);
            fail("Removes clients with empty client ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of removeClients method with existing client ids. Verifies if the
     * clients were removed from the client table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveClients_ExistClientIds() throws Exception {
        // make sure the clients exist
        persistence.addClients(clients, atomic);

        persistence.removeClients(clientIds, atomic);

        for (int i = 0; i < clientIds.length; i++) {
            assertNull("Fails to remove clients", persistence
                    .getClient(clientIds[i]));
        }
    }

    /**
     * Test of removeClients method with non-existing client ids in atomic mode.
     * Expects BatchOperationException. Also verifies if the existing clients
     * were not removed from the client table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveClients_NonExistClientIds_Atomic() throws Exception {
        // make sure some client does not exist
        persistence.addClients(clients, atomic);
        persistence.removeClient(clientIds[0]);

        try {
            persistence.removeClients(clientIds, true);
            fail("Removes clients with non-existing client ids");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < clientIds.length; i++) {
            assertNotNull("Fails to remove clients", persistence
                    .getClient(clientIds[i]));
        }
    }

    /**
     * Test of removeClients method with non-existing client ids in non-atomic
     * mode. Expects BatchOperationException. Also verifies if the existing
     * clients were removed from the client table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveClients_NonExistClientIds_NonAtomic()
            throws Exception {
        // make sure some client does not exist
        persistence.addClients(clients, atomic);
        persistence.removeClient(clientIds[0]);

        try {
            persistence.removeClients(clientIds, false);
            fail("Removes clients with non-existing client ids");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < clientIds.length; i++) {
            assertNull("Fails to remove clients", persistence
                    .getClient(clientIds[i]));
        }
    }

    /**
     * Test of updateClients method with null clients. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateClients_NullClients() throws Exception {
        try {
            persistence.updateClients(null, atomic);
            fail("Updates null clients");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of updateClients method with empty clients. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateClients_EmptyClients() throws Exception {
        try {
            persistence.updateClients(new Client[0], atomic);
            fail("Updates empty clients");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of updateClients method with bad clients containing null element.
     * Expects IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateClients_BadClients() throws Exception {
        try {
            persistence.updateClients(new Client[] { null }, atomic);
            fail("Updates bad clients containing null element");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of updateClients method with existing clients. Updates the name
     * fields. Verifies if the name fields were updated in the client table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateClients_ExistClients() throws Exception {
        // make sure the clients exist
        persistence.addClients(clients, atomic);

        // update the name fields
        for (int i = 0; i < clients.length; i++) {
            clients[i].setName("update" + "_" + i);
        }

        persistence.updateClients(clients, atomic);

        for (int i = 0; i < clientIds.length; i++) {
            assertEquals("Fails to update clients", "update" + "_" + i,
                    persistence.getClient(clientIds[i]).getName());
        }
    }

    /**
     * Test of updateClients method with non-existing clients in atomic mode.
     * Expects BatchOperationException. Verifies if the existing clients were
     * not updated in the client table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateClients_NonExistClients_Atomic() throws Exception {
        // make sure some client does not exist
        persistence.addClients(clients, atomic);
        persistence.removeClient(clientIds[0]);

        // update the name fields
        for (int i = 0; i < clients.length; i++) {
            clients[i].setName("update");
        }

        try {
            persistence.updateClients(clients, true);
            fail("Updates non-existing clients");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < clientIds.length; i++) {
            assertEquals("Fails to update clients", "name" + "_-1_" + i,
                    persistence.getClient(clientIds[i]).getName());
        }
    }

    /**
     * Test of updateClients method with non-existing clients in non-atomic
     * mode. Expects BatchOperationException. Verifies if the existing clients
     * were updated in the client table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateClients_NonExistClients_NonAtomic() throws Exception {
        // make sure some client does not exist
        persistence.addClients(clients, atomic);
        persistence.removeClient(clientIds[0]);

        // update the name fields
        for (int i = 0; i < clients.length; i++) {
            clients[i].setName("update");
        }

        try {
            persistence.updateClients(clients, false);
            fail("Updates non-existing clients");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < clientIds.length; i++) {
            if (i == 1) {
                assertEquals("Fails to update clients", "update", persistence
                        .getClient(clientIds[i]).getName());
            } else {
                assertEquals("Fails to update clients", "name" + "_-1_" + i,
                        persistence.getClient(clientIds[i]).getName());
            }
        }
    }

    /**
     * Test of getClients method with null client ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetClients_NullClientIds() throws Exception {
        try {
            persistence.getClients(null, atomic);
            fail("Gets clients with null client ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getClients method with empty client ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetClients_EmptyClientIds() throws Exception {
        try {
            persistence.getClients(new int[0], atomic);
            fail("Gets clients with empty client ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getClients method with existing client ids. Verifies if the
     * clients are returned. Verifies if the fields of the returned clients are
     * populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetClients_ExistClientIds() throws Exception {
        // make sure the clients exist
        persistence.addClients(clients, atomic);

        Client[] clients = persistence.getClients(clientIds, atomic);

        for (int i = 0; i < clients.length; i++) {
            assertTrue("Fails to get clients", TestHelper.compareClients(
                    this.clients[i], clients[i]));
        }
    }

    /**
     * Test of getClients method with non-existing client ids in atomic mode.
     * Expects BatchOperationException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetClients_NonExistClientIds_Atomic() throws Exception {
        // make sure some client does not exist
        persistence.addClients(clients, atomic);
        persistence.removeClient(clientIds[0]);

        try {
            persistence.getClients(clientIds, true);
            fail("Gets clients with non-existing client ids");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * Test of getClients method with some non-existing client ids in non-atomic
     * mode. Verifies if the clients are returned, with null element for
     * non-existing client. Verifies if the fields of the returned clients are
     * populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetClients_SomeNonExistClientIds_NonAtomic()
            throws Exception {
        // make sure some client does not exist
        persistence.addClients(clients, atomic);
        persistence.removeClient(clientIds[0]);

        Client[] clients = persistence.getClients(clientIds, false);

        assertNull("Fails to get clients", clients[0]);

        for (int i = 1; i < clients.length; i++) {
            assertTrue("Fails to get clients", TestHelper.compareClients(
                    this.clients[i], clients[i]));
        }
    }

    /**
     * Test of getClients method with all non-existing client ids in non-atomic
     * mode. Expects BatchOperationException.
     *
     * @since 1.1
     */
    public void testGetClients_AllNonExistClientIds_NonAtomic() {
        try {
            persistence.getClients(clientIds, false);
            fail("Gets clients with non-existing client ids");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * Test of addProjects method with null projects. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddProjects_NullProjects() throws Exception {
        try {
            persistence.addProjects(null, atomic);
            fail("Adds null projects");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addProjects method with empty projects. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddProjects_EmptyProjects() throws Exception {
        try {
            persistence.addProjects(new Project[0], atomic);
            fail("Adds empty projects");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addProjects method with bad projects containing null element.
     * Expects IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddProjects_BadProjects() throws Exception {
        try {
            persistence.addProjects(new Project[] { null }, atomic);
            fail("Adds bad projects containing null element");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addProjects method with non-existing projects. Verifies if the
     * projects were added to the project table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddProjects_NonExistProjects() throws Exception {
        persistence.addProjects(projects, atomic);

        for (int i = 0; i < projectIds.length; i++) {
            assertNotNull("Fails to add projects", persistence
                    .getProject(projectIds[i]));
        }
    }

    /**
     * Test of addProjects method with existing projects in atomic mode. Expects
     * BatchOperationException. Also verifies if the non-existing projects were
     * not added to the project table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddProjects_ExistProjects_Atomic() throws Exception {
        // make sure some project exists
        persistence.addProject(projects[0]);

        try {
            persistence.addProjects(projects, true);
            fail("Adds existing projects");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < projectIds.length; i++) {
            assertNull("Fails to add projects", persistence
                    .getProject(projectIds[i]));
        }
    }

    /**
     * Test of addProjects method with existing projects in non-atomic mode.
     * Expects BatchOperationException. Also verifies if the non-existing
     * projects were added to the project table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddProjects_ExistProjects_NonAtomic() throws Exception {
        // make sure some project exists
        persistence.addProject(projects[0]);

        try {
            persistence.addProjects(projects, false);
            fail("Adds existing projects");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < projectIds.length; i++) {
            assertNotNull("Fails to add projects", persistence
                    .getProject(projectIds[i]));
        }
    }

    /**
     * Test of removeProjects method with null project ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveProjects_NullProjectIds() throws Exception {
        try {
            persistence.removeProjects(null, atomic);
            fail("Removes projects with null project ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of removeProjects method with empty project ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveProjects_EmptyProjectIds() throws Exception {
        try {
            persistence.removeProjects(new int[0], atomic);
            fail("Removes projects with empty project ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of removeProjects method with existing project ids. Verifies if the
     * projects were removed from the project table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveProjects_ExistProjectIds() throws Exception {
        // make sure the projects exist
        persistence.addProjects(projects, atomic);

        persistence.removeProjects(projectIds, atomic);

        for (int i = 0; i < projectIds.length; i++) {
            assertNull("Fails to remove projects", persistence
                    .getProject(projectIds[i]));
        }
    }

    /**
     * Test of removeProjects method with non-existing project ids in atomic
     * mode. Expects BatchOperationException. Also verifies if the existing
     * projects were not removed from the project table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveProjects_NonExistProjectIds_Atomic() throws Exception {
        // make sure some project does not exist
        persistence.addProjects(projects, atomic);
        persistence.removeProject(projectIds[0]);

        try {
            persistence.removeProjects(projectIds, true);
            fail("Removes projects with non-existing project ids");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < projectIds.length; i++) {
            assertNotNull("Fails to remove projects", persistence
                    .getProject(projectIds[i]));
        }
    }

    /**
     * Test of removeProjects method with non-existing project ids in non-atomic
     * mode. Expects BatchOperationException. Also verifies if the existing
     * projects were removed from the project table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveProjects_NonExistProjectIds_NonAtomic()
            throws Exception {
        // make sure some project does not exist
        persistence.addProjects(projects, atomic);
        persistence.removeProject(projectIds[0]);

        try {
            persistence.removeProjects(projectIds, false);
            fail("Removes projects with non-existing project ids");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < projectIds.length; i++) {
            assertNull("Fails to remove projects", persistence
                    .getProject(projectIds[i]));
        }
    }

    /**
     * Test of updateProjects method with null projects. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateProjects_NullProjects() throws Exception {
        try {
            persistence.updateProjects(null, atomic);
            fail("Updates null projects");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of updateProjects method with empty projects. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateProjects_EmptyProjects() throws Exception {
        try {
            persistence.updateProjects(new Project[0], atomic);
            fail("Updates empty projects");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of updateProjects method with bad projects containing null element.
     * Expects IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateProjects_BadProjects() throws Exception {
        try {
            persistence.updateProjects(new Project[] { null }, atomic);
            fail("Updates bad projects containing null element");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of updateProjects method with existing projects. Updates the name
     * fields. Verifies if the name fields were updated in the project table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateProjects_ExistProjects() throws Exception {
        // make sure the projects exist
        persistence.addProjects(projects, atomic);

        // update the name fields
        for (int i = 0; i < projects.length; i++) {
            projects[i].setName("update");
        }

        persistence.updateProjects(projects, atomic);

        for (int i = 0; i < projectIds.length; i++) {
            assertEquals("Fails to update projects", "update", persistence
                    .getProject(projectIds[i]).getName());
        }
    }

    /**
     * Test of updateProjects method with non-existing projects in atomic mode.
     * Expects BatchOperationException. Verifies if the existing projects were
     * not updated in the project table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateProjects_NonExistProjects_Atomic() throws Exception {
        // make sure some project does not exist
        persistence.addProjects(projects, atomic);
        persistence.removeProject(projectIds[0]);

        // update the name fields
        for (int i = 0; i < projects.length; i++) {
            projects[i].setName("update");
        }

        try {
            persistence.updateProjects(projects, true);
            fail("Updates non-existing projects");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < projectIds.length; i++) {
            assertEquals("Fails to update projects", "name", persistence
                    .getProject(projectIds[i]).getName());
        }
    }

    /**
     * Test of updateProjects method with non-existing projects in non-atomic
     * mode. Expects BatchOperationException. Verifies if the existing projects
     * were updated in the project table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateProjects_NonExistProjects_NonAtomic()
            throws Exception {
        // make sure some project does not exist
        persistence.addProjects(projects, atomic);
        persistence.removeProject(projectIds[0]);

        // update the name fields
        for (int i = 0; i < projects.length; i++) {
            projects[i].setName("update");
        }

        try {
            persistence.updateProjects(projects, false);
            fail("Updates non-existing projects");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < projectIds.length; i++) {
            assertEquals("Fails to update projects", "update", persistence
                    .getProject(projectIds[i]).getName());
        }
    }

    /**
     * Test of getProjects method with null project ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetProjects_NullProjectIds() throws Exception {
        try {
            persistence.getProjects(null, atomic);
            fail("Gets projects with null project ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getProjects method with empty project ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetProjects_EmptyProjectIds() throws Exception {
        try {
            persistence.getProjects(new int[0], atomic);
            fail("Gets projects with empty project ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getProjects method with existing project ids. Verifies if the
     * projects are returned. Verifies if the fields of the returned projects
     * are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetProjects_ExistProjectIds() throws Exception {
        // make sure the projects exist
        persistence.addProjects(projects, atomic);

        Project[] projects = persistence.getProjects(projectIds, atomic);

        for (int i = 0; i < projects.length; i++) {
            assertTrue("Fails to get projects", TestHelper.compareProjects(
                    this.projects[i], projects[i]));
        }
    }

    /**
     * Test of getProjects method with non-existing project ids in atomic mode.
     * Expects BatchOperationException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetProjects_NonExistProjectIds_Atomic() throws Exception {
        // make sure some project does not exist
        persistence.addProjects(projects, atomic);
        persistence.removeProject(projectIds[0]);

        try {
            persistence.getProjects(projectIds, true);
            fail("Gets projects with non-existing project ids");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * Test of getProjects method with some non-existing project ids in
     * non-atomic mode. Verifies if the projects are returned, with null element
     * for non-existing project. Verifies if the fields of the returned projects
     * are populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetProjects_SomeNonExistProjectIds_NonAtomic()
            throws Exception {
        // make sure some project does not exist
        persistence.addProjects(projects, atomic);
        persistence.removeProject(projectIds[0]);

        Project[] projects = persistence.getProjects(projectIds, false);

        assertNull("Fails to get projects", projects[0]);

        for (int i = 1; i < projects.length; i++) {
            assertTrue("Fails to get projects", TestHelper.compareProjects(
                    this.projects[i], projects[i]));
        }
    }

    /**
     * Test of getProjects method with all non-existing project ids in
     * non-atomic mode. Expects BatchOperationException.
     *
     * @since 1.1
     */
    public void testGetProjects_AllNonExistProjectIds_NonAtomic() {
        try {
            persistence.getProjects(projectIds, false);
            fail("Gets projects with non-existing project ids");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * Test of addWorkers method with null workers. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddWorkers_NullWorkers() throws Exception {
        try {
            persistence.addWorkers(null, atomic);
            fail("Adds null workers");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addWorkers method with empty workers. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddWorkers_EmptyWorkers() throws Exception {
        try {
            persistence.addWorkers(new ProjectWorker[0], atomic);
            fail("Adds empty workers");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addWorkers method with bad workers containing null element.
     * Expects IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddWorkers_BadWorkers() throws Exception {
        try {
            persistence.addWorkers(new ProjectWorker[] { null }, atomic);
            fail("Adds bad workers containing null element");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addWorkers method with non-existing workers. Verifies if the
     * workers were added to the project_worker table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddWorkers_NonExistWorkers() throws Exception {
        persistence.addProject(project);
        persistence.addWorkers(workers, atomic);

        for (int i = 0; i < workerIds.length; i++) {
            assertNotNull("Fails to add workers", persistence.getWorker(
                    workerIds[i], projectId));
        }
    }

    /**
     * Test of addWorkers method with existing workers in atomic mode. Expects
     * BatchOperationException. Verifies if the non-existing workers were not
     * added to the project_worker table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddWorkers_ExistWorkers_Atomic() throws Exception {
        // make sure some worker exists
        persistence.addProject(project);
        persistence.addWorker(workers[0]);

        try {
            persistence.addWorkers(workers, true);
            fail("Adds existing workers");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < workerIds.length; i++) {
            assertNull("Fails to add workers", persistence.getWorker(
                    workerIds[i], projectId));
        }
    }

    /**
     * Test of addWorkers method with existing workers in non-atomic mode.
     * Expects BatchOperationException. Verifies if the non-existing workers
     * were added to the project_worker table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddWorkers_ExistWorkers_NonAtomic() throws Exception {
        // make sure some worker exists
        persistence.addProject(project);
        persistence.addWorker(workers[0]);

        try {
            persistence.addWorkers(workers, false);
            fail("Adds existing workers");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < workerIds.length; i++) {
            assertNotNull("Fails to add workers", persistence.getWorker(
                    workerIds[i], projectId));
        }
    }

    /**
     * Test of removeWorkers method with null worker ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveWorkers_NullWorkerIds() throws Exception {
        try {
            persistence.removeWorkers(null, projectId, atomic);
            fail("Removes workers with null worker ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of removeWorkers method with empty worker ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveWorkers_EmptyWorkerIds() throws Exception {
        try {
            persistence.removeWorkers(new int[0], projectId, atomic);
            fail("Removes workers with empty worker ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of removeWorkers method with existing worker ids. Verifies if the
     * workers were removed from the project_worker table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveWorkers_ExistWorkerIds() throws Exception {
        // make sure the workers exist
        persistence.addProject(project);
        persistence.addWorkers(workers, atomic);

        persistence.removeWorkers(workerIds, projectId, atomic);

        for (int i = 0; i < workerIds.length; i++) {
            assertNull("Fails to remove workers", persistence.getWorker(
                    workerIds[i], projectId));
        }
    }

    /**
     * Test of removeWorkers method with non-existing worker ids in atomic mode.
     * Expects BatchOperationException. Verifies if the existing workers were
     * not removed from the project_worker table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveWorkers_NonExistWorkerIds_Atomic() throws Exception {
        // make sure some worker does not exist
        persistence.addProject(project);
        persistence.addWorkers(workers, atomic);
        persistence.removeWorker(workerIds[0], projectId);

        try {
            persistence.removeWorkers(workerIds, projectId, true);
            fail("Removes workers with non-existing worker ids");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < workerIds.length; i++) {
            assertNotNull("Fails to remove workers", persistence.getWorker(
                    workerIds[i], projectId));
        }
    }

    /**
     * Test of removeWorkers method with non-existing worker ids in non-atomic
     * mode. Expects BatchOperationException. Verifies if the existing workers
     * were removed from the project_worker table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveWorkers_NonExistWorkerIds_NonAtomic()
            throws Exception {
        // make sure some worker does not exist
        persistence.addProject(project);
        persistence.addWorkers(workers, atomic);
        persistence.removeWorker(workerIds[0], projectId);

        try {
            persistence.removeWorkers(workerIds, projectId, false);
            fail("Removes workers with non-existing worker ids");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < workerIds.length; i++) {
            assertNull("Fails to remove workers", persistence.getWorker(
                    workerIds[i], projectId));
        }
    }

    /**
     * Test of updateWorkers method with null workers. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateWorkers_NullWorkers() throws Exception {
        try {
            persistence.updateWorkers(null, atomic);
            fail("Updates null workers");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of updateWorkers method with empty workers. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateWorkers_EmptyWorkers() throws Exception {
        try {
            persistence.updateWorkers(new ProjectWorker[0], atomic);
            fail("Updates empty workers");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of updateWorkers method with bad workers containing null element.
     * Expects IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateWorkers_BadWorkers() throws Exception {
        try {
            persistence.updateWorkers(new ProjectWorker[] { null }, atomic);
            fail("Updates bad workers containing null element");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of updateWorkers method with existing workers. Updates the pay rate
     * fields. Verifies if the pay rate fields were updated in the
     * project_worker table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateWorkers_ExistWorkers() throws Exception {
        // make sure the workers exist
        persistence.addProject(project);
        persistence.addWorkers(workers, atomic);

        // update the pay rate fields
        for (int i = 0; i < workers.length; i++) {
            workers[i].setPayRate(new BigDecimal(20));
        }

        persistence.updateWorkers(workers, atomic);

        for (int i = 0; i < workerIds.length; i++) {
            ProjectWorker worker = persistence.getWorker(workerIds[i],
                    projectId);

            assertTrue("Fails to update workers", worker.getPayRate()
                    .compareTo(new BigDecimal(20)) == 0);
        }
    }

    /**
     * Test of updateWorkers method with non-existing workers in atomic mode.
     * Expects BatchOperationException. Verifies if the existing workers were
     * not updated in the project_worker table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateWorkers_NonExistWorkers_Atomic() throws Exception {
        // make sure some worker does not exist
        persistence.addProject(project);
        persistence.addWorkers(workers, atomic);
        persistence.removeWorker(workerIds[0], projectId);

        // update the pay rate fields
        for (int i = 0; i < workers.length; i++) {
            workers[i].setPayRate(new BigDecimal(20));
        }

        try {
            persistence.updateWorkers(workers, true);
            fail("Updates non-existing workers");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < workerIds.length; i++) {
            ProjectWorker worker = persistence.getWorker(workerIds[i],
                    projectId);

            assertTrue("Fails to update workers", worker.getPayRate()
                    .compareTo(new BigDecimal(10)) == 0);
        }
    }

    /**
     * Test of updateWorkers method with non-existing workers in non-atomic
     * mode. Expects BatchOperationException. Verifies if the existing workers
     * were updated in the project_worker table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateWorkers_NonExistWorkers_NonAtomic() throws Exception {
        // make sure some worker does not exist
        persistence.addProject(project);
        persistence.addWorkers(workers, atomic);
        persistence.removeWorker(workerIds[0], projectId);

        // update the pay rate fields
        for (int i = 0; i < workers.length; i++) {
            workers[i].setPayRate(new BigDecimal(20));
        }

        try {
            persistence.updateWorkers(workers, false);
            fail("Updates non-existing workers");
        } catch (BatchOperationException e) {
            // good
        }

        for (int i = 1; i < workerIds.length; i++) {
            ProjectWorker worker = persistence.getWorker(workerIds[i],
                    projectId);

            assertTrue("Fails to update workers", worker.getPayRate()
                    .compareTo(new BigDecimal(20)) == 0);
        }
    }

    /**
     * Test of getWorkers method with null worker ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetWorkers_NullWorkerIds() throws Exception {
        try {
            persistence.getWorkers(null, projectId, atomic);
            fail("Gets workers with null worker ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getWorkers method with empty worker ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetWorkers_EmptyWorkerIds() throws Exception {
        try {
            persistence.getWorkers(new int[0], projectId, atomic);
            fail("Gets workers with empty worker ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getWorkers method with existing worker ids. Verifies if the
     * workers are returned. Verifies if the fields of the returned workers are
     * populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetWorkers_ExistWorkerIds() throws Exception {
        // make sure the workers exist
        persistence.addProject(project);
        persistence.addWorkers(workers, atomic);

        for (int i = 0; i < workerIds.length; i++) {
            project.addWorkerId(workerIds[i]);
        }

        ProjectWorker[] workers = persistence.getWorkers(workerIds, projectId,
                atomic);

        for (int i = 0; i < workers.length; i++) {
            assertTrue("Fails to get workers", TestHelper
                    .compareProjectWorkers(this.workers[i], workers[i]));
        }
    }

    /**
     * Test of getWorkers method with non-existing worker ids in atomic mode.
     * Expects BatchOperationException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetWorkers_NonExistWorkerIds_Atomic() throws Exception {
        // make sure some worker does not exist
        persistence.addProject(project);
        persistence.addWorkers(workers, atomic);
        persistence.removeWorker(workerIds[0], projectId);

        try {
            persistence.getWorkers(workerIds, projectId, true);
            fail("Gets workers with non-existing worker ids");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * Test of getWorkers method with some non-existing worker ids in non-atomic
     * mode. Verifies if the workers are returned, with null element for
     * non-existing worker. Verifies if the fields of the returned workers are
     * populated.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetWorkers_SomeNonExistWorkerIds_NonAtomic()
            throws Exception {
        // make sure some worker does not exist
        persistence.addProject(project);
        persistence.addWorkers(workers, atomic);
        persistence.removeWorker(workerIds[0], projectId);

        for (int i = 1; i < workerIds.length; i++) {
            project.addWorkerId(workerIds[i]);
        }

        ProjectWorker[] workers = persistence.getWorkers(workerIds, projectId,
                false);

        assertNull("Fails to get workers", workers[0]);

        for (int i = 1; i < workers.length; i++) {
            assertTrue("Fails to get workers", TestHelper
                    .compareProjectWorkers(this.workers[i], workers[i]));
        }
    }

    /**
     * Test of getWorkers method with all non-existing worker ids in non-atomic
     * mode. Expects BatchOperationException.
     *
     * @since 1.1
     */
    public void testGetWorkers_AllNonExistWorkerIds_NonAtomic() {
        try {
            persistence.getWorkers(workerIds, projectId, false);
            fail("Gets workers with non-existing worker ids");
        } catch (BatchOperationException e) {
            // good
        }
    }

    /**
     * Test of addTimeEntries method with null entry ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddTimeEntries_NullEntryIds() throws Exception {
        try {
            persistence.addTimeEntries(null, projectId, user, atomic);
            fail("Adds time entries with null entry ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addTimeEntries method with empty entry ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddTimeEntries_EmptyEntryIds() throws Exception {
        try {
            persistence.addTimeEntries(new int[0], projectId, user, atomic);
            fail("Adds time entries with empty entry ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addTimeEntries method with null user. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddTimeEntries_NullUser() throws Exception {
        try {
            persistence.addTimeEntries(entryIds, projectId, null, atomic);
            fail("Adds time entries with null user");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addTimeEntries method with empty user. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddTimeEntries_EmptyUser() throws Exception {
        try {
            persistence.addTimeEntries(entryIds, projectId, "", atomic);
            fail("Adds time entries with empty user");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addTimeEntries method with non-existing entry ids. Verifies if
     * the time entries were added to the project_time table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddTimeEntries_NonExistEntryIds() throws Exception {
        persistence.addProject(project);
        persistence.addTimeEntries(entryIds, projectId, user, atomic);

        assertEquals("Fails to add time entries", entryIds.length, persistence
                .getTimeEntries(projectId).size());
    }

    /**
     * Test of addTimeEntries method with existing entry ids in atomic mode.
     * Expects BatchOperationException. Verifies if the non-existing time
     * entries were not added to the project_time table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddTimeEntries_ExistEntryIds_Atomic() throws Exception {
        // make sure some time entry exists
        persistence.addProject(project);
        persistence.addTimeEntry(entryIds[0], projectId, user);

        try {
            persistence.addTimeEntries(entryIds, projectId, user, true);
            fail("Adds time entries with existing entry ids");
        } catch (BatchOperationException e) {
            // good
        }

        assertEquals("Fails to add time entries", 1, persistence
                .getTimeEntries(projectId).size());
    }

    /**
     * Test of addTimeEntries method with existing entry ids in non-atomic mode.
     * Expects BatchOperationException. Verifies if the non-existing time
     * entries were added to the project_time table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddTimeEntries_ExistEntryIds_NonAtomic() throws Exception {
        // make sure some time entry exists
        persistence.addProject(project);
        persistence.addTimeEntry(entryIds[0], projectId, user);

        try {
            persistence.addTimeEntries(entryIds, projectId, user, false);
            fail("Adds time entries with existing entry ids");
        } catch (BatchOperationException e) {
            // good
        }

        assertEquals("Fails to add time entries", entryIds.length, persistence
                .getTimeEntries(projectId).size());
    }

    /**
     * Test of removeTimeEntries method with null entry ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveTimeEntries_NullEntryIds() throws Exception {
        try {
            persistence.removeTimeEntries(null, projectId, atomic);
            fail("Removes time entries with null entry ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of removeTimeEntries method with empty entry ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveTimeEntries_EmptyEntryIds() throws Exception {
        try {
            persistence.removeTimeEntries(new int[0], projectId, atomic);
            fail("Removes time entries with empty entry ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of removeTimeEntries method with existing entry ids. Verifies if the
     * time entries were removed from the project_time table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveTimeEntries_ExistEntryIds() throws Exception {
        // make sure the time entries exist
        persistence.addProject(project);
        persistence.addTimeEntries(entryIds, projectId, user, atomic);

        persistence.removeTimeEntries(entryIds, projectId, atomic);

        assertTrue("Fails to remove time entries", persistence.getTimeEntries(
                projectId).isEmpty());
    }

    /**
     * Test of removeTimeEntries method with non-existing entry ids in atomic
     * mode. Expects BatchOperationException. Verifies if the existing time
     * entries were not removed from the project_time table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveTimeEntries_NonExistEntryIds_Atomic()
            throws Exception {
        // make sure some time entry does not exist
        persistence.addProject(project);
        persistence.addTimeEntries(entryIds, projectId, user, atomic);
        persistence.removeTimeEntry(entryIds[0], projectId);

        try {
            persistence.removeTimeEntries(entryIds, projectId, true);
            fail("Removes time entries with non-existing entry ids");
        } catch (BatchOperationException e) {
            // good
        }

        assertEquals("Fails to remove time entries", entryIds.length - 1,
                persistence.getTimeEntries(projectId).size());
    }

    /**
     * Test of removeTimeEntries method with non-existing entry ids in
     * non-atomic mode. Expects BatchOperationException. Verifies if the
     * existing time entries were removed from the project_time table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveTimeEntries_NonExistEntryIds_NonAtomic()
            throws Exception {
        // make sure some time entry does not exist
        persistence.addProject(project);
        persistence.addTimeEntries(entryIds, projectId, user, atomic);
        persistence.removeTimeEntry(entryIds[0], projectId);

        try {
            persistence.removeTimeEntries(entryIds, projectId, false);
            fail("Removes time entries with non-existing entry ids");
        } catch (BatchOperationException e) {
            // good
        }

        assertTrue("Fails to remove time entries", persistence.getTimeEntries(
                projectId).isEmpty());
    }

    /**
     * Test of addExpenseEntries method with null entry ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddExpenseEntries_NullEntryIds() throws Exception {
        try {
            persistence.addExpenseEntries(null, projectId, user, atomic);
            fail("Adds expense entries with null entry ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addExpenseEntries method with empty entry ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddExpenseEntries_EmptyEntryIds() throws Exception {
        try {
            persistence.addExpenseEntries(new int[0], projectId, user, atomic);
            fail("Adds expense entries with empty entry ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addExpenseEntries method with null user. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddExpenseEntries_NullUser() throws Exception {
        try {
            persistence.addExpenseEntries(entryIds, projectId, null, atomic);
            fail("Adds expense entries with null user");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addExpenseEntries method with empty user. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddExpenseEntries_EmptyUser() throws Exception {
        try {
            persistence.addExpenseEntries(entryIds, projectId, "", atomic);
            fail("Adds expense entries with empty user");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addExpenseEntries method with non-existing entry ids. Verifies if
     * the expense entries were added to the project_expense table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddExpenseEntries_NonExistEntryIds() throws Exception {
        persistence.addProject(project);
        persistence.addExpenseEntries(entryIds, projectId, user, atomic);

        assertEquals("Fails to add expense entries", entryIds.length,
                persistence.getExpenseEntries(projectId).size());
    }

    /**
     * Test of addExpenseEntries method with existing entry ids in atomic mode.
     * Expects BatchOperationException. Verifies if the non-existing expense
     * entries were not added to the project_expense table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddExpenseEntries_ExistEntryIds_Atomic() throws Exception {
        // make sure some expense entry exists
        persistence.addProject(project);
        persistence.addExpenseEntry(entryIds[0], projectId, user);

        try {
            persistence.addExpenseEntries(entryIds, projectId, user, true);
            fail("Adds expense entries with existing entry ids");
        } catch (BatchOperationException e) {
            // good
        }

        assertEquals("Fails to add expense entries", 1, persistence
                .getExpenseEntries(projectId).size());
    }

    /**
     * Test of addExpenseEntries method with existing entry ids in non-atomic
     * mode. Expects BatchOperationException. Verifies if the non-existing
     * expense entries were added to the project_expense table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddExpenseEntries_ExistEntryIds_NonAtomic()
            throws Exception {
        // make sure some expense entry exists
        persistence.addProject(project);
        persistence.addExpenseEntry(entryIds[0], projectId, user);

        try {
            persistence.addExpenseEntries(entryIds, projectId, user, false);
            fail("Adds expense entries with existing entry ids");
        } catch (BatchOperationException e) {
            // good
        }

        assertEquals("Fails to add expense entries", entryIds.length,
                persistence.getExpenseEntries(projectId).size());
    }

    /**
     * Test of removeExpenseEntries method with null entry ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveExpenseEntries_NullEntryIds() throws Exception {
        try {
            persistence.removeExpenseEntries(null, projectId, atomic);
            fail("Removes expense entries with null entry ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of removeExpenseEntries method with empty entry ids. Expects
     * IllegalArgumentException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveExpenseEntries_EmptyEntryIds() throws Exception {
        try {
            persistence.removeExpenseEntries(new int[0], projectId, atomic);
            fail("Removes expense entries with empty entry ids");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of removeExpenseEntries method with existing entry ids. Verifies if
     * the expense entries were removed from the project_expense table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveExpenseEntries_ExistEntryIds() throws Exception {
        // make sure the expense entries exist
        persistence.addProject(project);
        persistence.addExpenseEntries(entryIds, projectId, user, atomic);

        persistence.removeExpenseEntries(entryIds, projectId, atomic);

        assertTrue("Fails to remove expense entries", persistence
                .getExpenseEntries(projectId).isEmpty());
    }

    /**
     * Test of removeExpenseEntries method with non-existing entry ids in atomic
     * mode. Expects BatchOperationException. Verifies if the existing expense
     * entries were not removed from the project_expense table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveExpenseEntries_NonExistEntryIds_Atomic()
            throws Exception {
        // make sure some expense entry does not exist
        persistence.addProject(project);
        persistence.addExpenseEntries(entryIds, projectId, user, atomic);
        persistence.removeExpenseEntry(entryIds[0], projectId);

        try {
            persistence.removeExpenseEntries(entryIds, projectId, true);
            fail("Removes expense entries with non-existing entry ids");
        } catch (BatchOperationException e) {
            // good
        }

        assertEquals("Fails to remove expense entries", entryIds.length - 1,
                persistence.getExpenseEntries(projectId).size());
    }

    /**
     * Test of removeExpenseEntries method with non-existing entry ids in
     * non-atomic mode. Expects BatchOperationException. Verifies if the
     * existing expense entries were removed from the project_expense table.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveExpenseEntries_NonExistEntryIds_NonAtomic()
            throws Exception {
        // make sure some expense entry does not exist
        persistence.addProject(project);
        persistence.addExpenseEntries(entryIds, projectId, user, atomic);
        persistence.removeExpenseEntry(entryIds[0], projectId);

        try {
            persistence.removeExpenseEntries(entryIds, projectId, false);
            fail("Removes expense entries with non-existing entry ids");
        } catch (BatchOperationException e) {
            // good
        }

        assertTrue("Fails to remove expense entries", persistence
                .getExpenseEntries(projectId).isEmpty());
    }
    /**
     * accuracy test for getCompanyIdForProject method
     *
     * @throws Exception
     *             to JUnit
     *
     * @since 2.0
     */
    public void testGetCompanyIdForProject() throws Exception {
        persistence.addProject(project);
        int companyId = persistence.getCompanyIdForProject(project.getId());
        assertEquals("Wrong company id for inserted project", companyId, 1);
    }

    /**
     * accuracy test for getCompanyIdForClient method
     *
     * @throws Exception
     *             to JUnit
     *
     * @since 2.0
     */
    public void testGetCompanyIdForClient() throws Exception {
        persistence.addClient(client);
        int companyId = persistence.getCompanyIdForClient(client.getId());
        assertEquals("Wrong company id for inserted client", companyId, 1);
    }

    /**
     * accuracy test for getCompanyIdForTimeEntry method
     *
     * @throws Exception
     *             to JUnit
     *
     * @since 2.0
     */
    public void testGetCompanyIdForTimeEntry() throws Exception {
        // get company id for time entry that exist in db
        int companyId = persistence.getCompanyIdForTimeEntry(1);
        assertEquals("Wrong company id for inserted time entry", companyId, 1);
    }

    /**
     * accuracy test for getCompanyIdForExpenseEntry method
     *
     * @throws Exception
     *             to JUnit
     *
     * @since 2.0
     */
    public void testGetCompanyIdForExpenseEntry() throws Exception {
        // get company id for expense entry that exist in db
        int companyId = persistence.getCompanyIdForExpenseEntry(1);
        assertEquals("Wrong company id for inserted expense entry", companyId,
                1);
    }

    /**
     * accuracy test for getCompanyIdForUserAccount method
     *
     * @throws Exception
     *             to JUnit
     *
     * @since 2.0
     */
    public void testGetCompanyIdForUserAccount() throws Exception {
        persistence.addProject(project);
        ProjectWorker worker = TestHelper.createWorker(1, project);
        persistence.addWorker(worker);
        int companyId = persistence.getCompanyIdForUserAccount(worker
                .getWorkerId());
        assertEquals("Wrong company id for inserted worker", companyId, 1);
    }

    /**
     * accuracy test for existClientWithNameForCompany method
     *
     * @throws Exception
     *             to JUnit
     *
     * @since 2.0
     */
    public void existClientWithNameForCompany() throws Exception {
        persistence.addClient(client);

        boolean existClient = persistence.existClientWithNameForCompany(client,
                false);
        assertEquals("Wrong checking existance of client name ", existClient,
                false);

        existClient = persistence.existClientWithNameForCompany(client, true);
        assertEquals("Wrong checking existance of client name ", existClient,
                true);
    }

}
