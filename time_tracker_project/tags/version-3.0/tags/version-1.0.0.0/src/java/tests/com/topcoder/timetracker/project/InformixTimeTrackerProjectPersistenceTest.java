/*
 * InformixTimeTrackerProjectPersistenceTest.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project;

import com.topcoder.timetracker.project.persistence.InformixTimeTrackerProjectPersistence;
import com.topcoder.timetracker.project.persistence.PersistenceException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.math.BigDecimal;

import java.util.List;


/**
 * Unit tests for InformixTimeTrackerProjectPersistence implementation.
 *
 * @author TCSDEVELOPER
 * @version 1.0
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
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(InformixTimeTrackerProjectPersistenceTest.class);

        return suite;
    }

    /**
     * Prepares an InformixTimeTrackerProjectPersistence instance for testing. Also prepares the method arguments.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfig();
        DBHelper.clearTables();

        // prepare the ids for client, project, manager, worker and entries
        clientId = 100;
        projectId = 200;
        managerId = 300;
        workerId = 400;
        entryId = 500;
        user = "user";

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

        persistence = new InformixTimeTrackerProjectPersistence(TestHelper.DB_NAMESPACE,
                TestHelper.CONNECTION_PRODUCER_NAME);
    }

    /**
     * Clears all the namespaces and tables in the database.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void tearDown() throws Exception {
        DBHelper.clearTables();
        TestHelper.unloadConfig();
        persistence.closeConnection();
    }

    /**
     * Test of constructor with null db namespace. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_NullDbNamespace() throws Exception {
        // try one-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence(null);
            fail("Creates InformixTimeTrackerProjectPersistence with null db namespace");
        } catch (NullPointerException e) {}

        // try two-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence(null, TestHelper.CONNECTION_PRODUCER_NAME);
            fail("Creates InformixTimeTrackerProjectPersistence with null db namespace");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of constructor with empty db namespace. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_EmptyDbNamespace() throws Exception {
        // try one-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence("");
            fail("Creates InformixTimeTrackerProjectPersistence with empty db namespace");
        } catch (IllegalArgumentException e) {}

        // try two-argument constructor
        try {
            new InformixTimeTrackerProjectPersistence("", TestHelper.CONNECTION_PRODUCER_NAME);
            fail("Creates InformixTimeTrackerProjectPersistence with empty db namespace");
        } catch (IllegalArgumentException e) {}
    }

    /**
     * Test of constructor with null connection producer name. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_NullConnProdName() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(TestHelper.DB_NAMESPACE, null);
            fail("Creates InformixTimeTrackerProjectPersistence with null connection producer name");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of constructor with empty connection producer name. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_EmptyConnProdName() throws Exception {
        try {
            new InformixTimeTrackerProjectPersistence(TestHelper.DB_NAMESPACE, "");
            fail("Creates InformixTimeTrackerProjectPersistence with empty connection producer name");
        } catch (IllegalArgumentException e) {}
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
        } catch (NullPointerException e) {}
    }

    /**
     * Test of addClient method with existing client. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddClient_ExistClient() throws Exception {
        persistence.addClient(client);

        assertFalse("Adds an existing client", persistence.addClient(client));
    }

    /**
     * Test of addClient method with non-existing client. Adds a project to the client. Verifies if true is returned.
     * Also verifies if the client was added to the Clients table, the project was added to the Projects table, and
     * the project was assigned the client in the ClientProjects table. Lastly, verifies if the date fields of the
     * Client instance are populated.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddClient_NonExistClient() throws Exception {
        client.addProject(project);

        assertTrue("Fails to add non-existing client", persistence.addClient(client));
        assertNotNull("Fails to add to Clients table", persistence.getClient(clientId));
        assertNotNull("Fails to add to Projects table", persistence.getProject(projectId));
        assertNotNull("Fails to add to ClientProjects table", persistence.getClientProject(clientId, projectId));

        assertNotNull("Fails to populate CreationDate", client.getCreationDate());
        assertNotNull("Fails to populate ModificationDate", client.getModificationDate());
    }

    /**
     * Test of removeClient method with non-existing client id. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveClient_NonExistClient() throws Exception {
        assertFalse("Removes non-existing client", persistence.removeClient(-1));
    }

    /**
     * Test of removeClient method with existing client id. Adds a project to the client. Verifies if true is returned.
     * Also verifies if the client was removed from the Clients table, and the project was removed from the client in
     * the ClientProjects table.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveClient_ExistClient() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        assertTrue("Fails to remove existing client", persistence.removeClient(clientId));
        assertNull("Fails to remove from Clients table", persistence.getClient(clientId));
        assertNull("Fails to remove from ClientProjects table", persistence.getClientProject(clientId, projectId));
    }

    /**
     * Test of removeAllClients method with empty clients. Verifies if the Clients and ClientProjects tables are still
     * empty after calling it.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveAllClients_EmptyClients() throws Exception {
        persistence.removeAllClients();

        assertTrue("Fails to clear Clients table", DBHelper.isEmptyTable("Clients"));
        assertTrue("Fails to clear ClientProjects table", DBHelper.isEmptyTable("ClientProjects"));
    }

    /**
     * Test of removeAllClients method with non-empty clients. Verifies if the Clients and ClientProjects tables are
     * empty after calling it.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveAllClients_NonEmptyClients()
        throws Exception
    {
        for (int i = 0; i < 3; i++) {
            Project project = TestHelper.createProject(i);
            Client client = TestHelper.createClient(i);

            client.addProject(project);
            persistence.addClient(client);
        }

        persistence.removeAllClients();

        assertTrue("Fails to clear Clients table", DBHelper.isEmptyTable("Clients"));
        assertTrue("Fails to clear ClientProjects table", DBHelper.isEmptyTable("ClientProjects"));
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
        } catch (NullPointerException e) {}
    }

    /**
     * Test of updateClient method with non-existing client. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testUpdateClient_NonExistClient() throws Exception {
        assertFalse("Updates a non-existing client", persistence.updateClient(client));
    }

    /**
     * Test of updateClient method with existing client. Updates the name field. Verifies if true is returned. Also
     * verifies if the name field was updated in the Clients table.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testUpdateClient_ExistClient() throws Exception {
        persistence.addClient(client);

        // update the name field
        client.setName("update");

        assertTrue("Fails to update existing client", persistence.updateClient(client));

        Client result = persistence.getClient(clientId);

        assertEquals("Fails to update Clients table", "update", result.getName());
    }

    /**
     * Test of addProjectToClient method with null project. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProjectToClient_NullProject() throws Exception {
        try {
            persistence.addProjectToClient(clientId, null, user);
            fail("Adds null project to client");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of addProjectToClient method with null user. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProjectToClient_NullUser() throws Exception {
        try {
            persistence.addProjectToClient(clientId, project, null);
            fail("Adds project to client with null user");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of addProjectToClient method with empty user. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProjectToClient_EmptyUser() throws Exception {
        try {
            persistence.addProjectToClient(clientId, project, "");
            fail("Adds project to client with empty user");
        } catch (IllegalArgumentException e) {}
    }

    /**
     * Test of addProjectToClient method with existing project to the client. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProjectToClient_ExistProject() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        assertFalse("Adds existing project to the client", persistence.addProjectToClient(clientId, project, user));
    }

    /**
     * Test of addProjectToClient method with non-existing project to the client. Verifies if true is returned. Also
     * verifies if the project was added to the Projects table, and the project was assigned the client in the
     * ClientProjects table.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProjectToClient_NonExistProject()
        throws Exception
    {
        persistence.addClient(client);

        assertTrue("Fails to add non-existing project to the client",
            persistence.addProjectToClient(clientId, project, user));
        assertNotNull("Fails to add to Projects table", persistence.getProject(projectId));
        assertNotNull("Fails to add to ClientProjects table", persistence.getClientProject(clientId, projectId));
    }

    /**
     * Test of getClientProject method with non-existing project to the client. Verifies if null is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetClientProject_NonExistProject()
        throws Exception
    {
        assertNull("Gets non-existing project to the client", persistence.getClientProject(clientId, projectId));
    }

    /**
     * Test of getClientProject method with existing project to the client. Verifies if the fields of the returned
     * project are populated.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetClientProject_ExistProject() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        Project result = persistence.getClientProject(clientId, projectId);

        assertTrue("Returns an incorrect client project", TestHelper.compareProjects(project, result));
    }

    /**
     * Test of getAllClientProjects method with non-existing projects to the client. Verifies if an empty list is
     * returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetAllClientProjects_NonExistProjects()
        throws Exception
    {
        assertTrue("Returns an incorrect list of client projects", persistence.getAllClientProjects(clientId).isEmpty());
    }

    /**
     * Test of getAllClientProjects method with existing projects to the client. Verifies if it returns a list
     * containing those projects. Verifies if the fields of the returned projects are populated.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetAllClientProjects_ExistProjects()
        throws Exception
    {
        client.addProject(project);
        persistence.addClient(client);

        List results = persistence.getAllClientProjects(clientId);

        assertEquals("Returns an incorrect list of client projects", 1, results.size());

        Project result = (Project) results.get(0);

        assertTrue("Returns an incorrect list of client projects", TestHelper.compareProjects(project, result));
    }

    /**
     * Test of getClient method with non-existing client. Verifies if null is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetClient_NonExistClient() throws Exception {
        assertNull("Returns an incorrect client", persistence.getClient(clientId));
    }

    /**
     * Test of getClient method with existing client. Verifies if the client is returned. Verifies if the fields of the
     * returned client are populated.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetClient_ExistClient() throws Exception {
        persistence.addClient(client);

        Client result = persistence.getClient(clientId);

        assertTrue("Returns an incorrect client", TestHelper.compareClients(client, result));
    }

    /**
     * Test of getAllClients method with empty clients. Verifies if an empty list is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetAllClients_EmptyClients() throws Exception {
        assertTrue("Returns an incorrect list of clients", persistence.getAllClients().isEmpty());
    }

    /**
     * Test of getAllClients method with non-empty clients. Verifies if it returns a list containing those clients.
     * Verifies if the fields of the returned clients are populated.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetAllClients_NonEmptyClients() throws Exception {
        persistence.addClient(client);

        List results = persistence.getAllClients();

        assertEquals("Returns an incorrect list of clients", 1, results.size());

        Client result = (Client) results.get(0);

        assertTrue("Returns an incorrect list of clients", TestHelper.compareClients(client, result));
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
        } catch (NullPointerException e) {}
    }

    /**
     * Test of addProject method with existing project. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProject_ExistProject() throws Exception {
        persistence.addProject(project);

        assertFalse("Adds an existing project", persistence.addProject(project));
    }

    /**
     * Test of addProject method with non-existing project. Verifies if true is returned. Also verifies if the project
     * was added to the Projects table. Lastly, verifies if the date fields of the Project instance are populated.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProject_NonExistProject() throws Exception {
        assertTrue("Fails to add non-existing project", persistence.addProject(project));
        assertNotNull("Fails to add to Projects table", persistence.getProject(projectId));

        assertNotNull("Fails to populate CreationDate", project.getCreationDate());
        assertNotNull("Fails to populate ModificationDate", project.getModificationDate());
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
        } catch (NullPointerException e) {}
    }

    /**
     * Test of updateProject method with non-existing project. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testUpdateProject_NonExistProject() throws Exception {
        assertFalse("Updates a non-existing project", persistence.updateProject(project));
    }

    /**
     * Test of updateProject method with existing project. Updates the name field. Verifies if true is returned. Also
     * verifies if the name field was updated in the Projects table.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testUpdateProject_ExistProject() throws Exception {
        persistence.addProject(project);

        // update the name field
        project.setName("update");

        assertTrue("Fails to update existing project", persistence.updateProject(project));

        Project result = persistence.getProject(projectId);

        assertEquals("Fails to update Projects table", "update", result.getName());
    }

    /**
     * Test of removeProject method with non-existing project. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveProject_NonExistProject() throws Exception {
        assertFalse("Removes non-existing project", persistence.removeProject(-1));
    }

    /**
     * Test of removeProject method with existing project. Adds some client, worker, manager, time entry and expense
     * entry to the project. Verifies if true is returned. Also verifies if the corresponding rows in the
     * ClientProjects, ProjectWorkers, ProjectManagers, ProjectTimes, ProjectExpenses and Projects tables were
     * removed.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveProject_ExistProject() throws Exception {
        client.addProject(project);

        persistence.addClient(client);
        persistence.addWorker(projectWorker);
        persistence.assignProjectManager(projectManager);
        persistence.addTimeEntry(entryId, projectId, user);
        persistence.addExpenseEntry(entryId, projectId, user);

        assertTrue("Fails to remove existing project", persistence.removeProject(projectId));
        assertNull("Fails to remove from ClientProjects table", persistence.getClientProject(clientId, projectId));
        assertTrue("Fails to remove from ProjectWorkers table", persistence.getWorkers(projectId).isEmpty());
        assertNull("Fails to remove from ProjectManagers table", persistence.getProjectManager(projectId));
        assertTrue("Fails to remove from ProjectTimes table", persistence.getTimeEntries(projectId).isEmpty());
        assertTrue("Fails to remove from ProjectExpenses table", persistence.getExpenseEntries(projectId).isEmpty());
        assertNull("Fails to remove from Projects table", persistence.getProject(projectId));
    }

    /**
     * Test of removeAllProjects method with empty projects. Verifies if the ClientProjects, ProjectWorkers,
     * ProjectManagers, ProjectTimes, ProjectExpenses and Projects tables are still empty after calling it.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveAllProjects_EmptyProjects() throws Exception {
        persistence.removeAllProjects();

        assertTrue("Fails to clear ClientProjects table", DBHelper.isEmptyTable("ClientProjects"));
        assertTrue("Fails to clear ProjectWorkers table", DBHelper.isEmptyTable("ProjectWorkers"));
        assertTrue("Fails to clear ProjectManagers table", DBHelper.isEmptyTable("ProjectManagers"));
        assertTrue("Fails to clear ProjectTimes table", DBHelper.isEmptyTable("ProjectTimes"));
        assertTrue("Fails to clear ProjectExpenses table", DBHelper.isEmptyTable("ProjectExpenses"));
        assertTrue("Fails to clear Projects table", DBHelper.isEmptyTable("Projects"));
    }

    /**
     * Test of removeAllProjects method with non-empty projects. Verifies if the ClientProjects, ProjectWorkers,
     * ProjectManagers, ProjectTimes, ProjectExpenses and Projects tables are empty after calling it.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveAllProjects_NonEmptyProjects()
        throws Exception
    {
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

        assertTrue("Fails to clear ClientProjects table", DBHelper.isEmptyTable("ClientProjects"));
        assertTrue("Fails to clear ProjectWorkers table", DBHelper.isEmptyTable("ProjectWorkers"));
        assertTrue("Fails to clear ProjectManagers table", DBHelper.isEmptyTable("ProjectManagers"));
        assertTrue("Fails to clear ProjectTimes table", DBHelper.isEmptyTable("ProjectTimes"));
        assertTrue("Fails to clear ProjectExpenses table", DBHelper.isEmptyTable("ProjectExpenses"));
        assertTrue("Fails to clear Projects table", DBHelper.isEmptyTable("Projects"));
    }

    /**
     * Test of assignClient method with null user. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAssignClient_NullUser() throws Exception {
        try {
            persistence.assignClient(projectId, clientId, null);
            fail("Assigns client with null user");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of assignClient method with empty user. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAssignClient_EmptyUser() throws Exception {
        try {
            persistence.assignClient(projectId, clientId, "");
            fail("Assigns client with empty user");
        } catch (IllegalArgumentException e) {}
    }

    /**
     * Test of assignClient method with existing client to the project. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAssignClient_ExistClient() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        assertFalse("Adds existing client to the project", persistence.assignClient(projectId, clientId, user));
    }

    /**
     * Test of assignClient method with non-existing client to the project. Verifies if true is returned. Also verifies
     * if the client was added to the ClientProjects table.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAssignClient_NonExistClient() throws Exception {
        persistence.addClient(client);
        persistence.addProject(project);

        assertTrue("Fails to add non-existing client to the project",
            persistence.assignClient(projectId, clientId, user));
        assertNotNull("Fails to add to ClientProjects table", persistence.getClientProject(clientId, projectId));
    }

    /**
     * Test of getProjectClient method with non-existing client for the project. Verifies if null is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetProjectClient_NonExistClient() throws Exception {
        assertNull("Returns an incorrect client for the project", persistence.getProjectClient(projectId));
    }

    /**
     * Test of getProjectClient method with existing client for the project. Verifies if the fields of the returned
     * client are populated.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetProjectClient_ExistClient() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        Client result = persistence.getProjectClient(projectId);

        assertTrue("Returns an incorrect client for the project", TestHelper.compareClients(client, result));
    }

    /**
     * Test of assignProjectManager method with null manager. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAssignProjectManager_NullManager()
        throws Exception
    {
        try {
            persistence.assignProjectManager(null);
            fail("Assigns null project manager");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of assignProjectManager method with existing manager. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAssignProjectManager_ExistManager()
        throws Exception
    {
        persistence.addProject(project);
        persistence.assignProjectManager(projectManager);

        assertFalse("Adds an existing manager", persistence.assignProjectManager(projectManager));
    }

    /**
     * Test of assignProjectManager method with non-existing manager. Verifies if true is returned. Also verifies if
     * the manager was added to the ProjectManagers table. Lastly, verifies if the date fields of the ProjectManager
     * instance are populated.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAssignProjectManager_NonExistManager()
        throws Exception
    {
        persistence.addProject(project);

        assertTrue("Fails to add non-existing manager", persistence.assignProjectManager(projectManager));
        assertNotNull("Fails to add to ProjectManagers table", persistence.getProjectManager(projectId));

        assertNotNull("Fails to populate CreationDate", projectManager.getCreationDate());
        assertNotNull("Fails to populate ModificationDate", projectManager.getModificationDate());
    }

    /**
     * Test of getProjectManager method with non-existing manager. Verifies if null is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetProjectManager_NonExistManager()
        throws Exception
    {
        assertNull("Returns an incorrect manager", persistence.getProjectManager(projectId));
    }

    /**
     * Test of getProjectManager method with existing manager. Verifies if the fields of the returned manager are
     * populated.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetProjectManager_ExistManager() throws Exception {
        persistence.addProject(project);
        persistence.assignProjectManager(projectManager);

        project.setManagerId(managerId);

        ProjectManager result = persistence.getProjectManager(projectId);

        assertTrue("Returns an incorrect manager", TestHelper.compareProjectManagers(projectManager, result));
    }

    /**
     * Test of removeProjectManager method with non-existing manager. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveProjectManager_NonExistManager()
        throws Exception
    {
        assertFalse("Removes a non-existing manager", persistence.removeProjectManager(managerId, projectId));
    }

    /**
     * Test of removeProjectManager method with existing manager. Verifies if true is returned. Also verifies if the
     * manager was removed from the ProjectManagers table.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveProjectManager_ExistManager()
        throws Exception
    {
        persistence.addProject(project);
        persistence.assignProjectManager(projectManager);

        assertTrue("Fails to remove existing manager", persistence.removeProjectManager(managerId, projectId));
        assertNull("Fails to remove from ProjectManagers table", persistence.getProjectManager(projectId));
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
        } catch (NullPointerException e) {}
    }

    /**
     * Test of addWorker method with existing worker. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddWorker_ExistWorker() throws Exception {
        persistence.addProject(project);
        persistence.addWorker(projectWorker);

        assertFalse("Adds an existing worker", persistence.addWorker(projectWorker));
    }

    /**
     * Test of addWorker method with non-existing worker. Verifies if true is returned. Also verifies if the worker was
     * added to the ProjectWorkers table. Lastly, verifies if the date fields of the ProjectWorker instance are
     * populated.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddWorker_NonExistWorker() throws Exception {
        persistence.addProject(project);

        assertTrue("Fails to add non-existing worker", persistence.addWorker(projectWorker));
        assertNotNull("Fails to add to ProjectWorkers table", persistence.getWorker(workerId, projectId));

        assertNotNull("Fails to populate CreationDate", projectWorker.getCreationDate());
        assertNotNull("Fails to populate ModificationDate", projectWorker.getModificationDate());
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
        } catch (NullPointerException e) {}
    }

    /**
     * Test of updateWorker method with non-existing worker. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testUpdateWorker_NonExistWorker() throws Exception {
        assertFalse("Updates a non-existing worker", persistence.updateWorker(projectWorker));
    }

    /**
     * Test of updateWorker method with existing worker. Updates the pay rate field. Verifies if true is returned. Also
     * verifies if the pay rate field was updated in the ProjectWorkers table.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testUpdateWorker_ExistWorker() throws Exception {
        persistence.addProject(project);
        persistence.addWorker(projectWorker);

        // update the pay rate field
        projectWorker.setPayRate(new BigDecimal(20));

        assertTrue("Fails to update existing worker", persistence.updateWorker(projectWorker));

        ProjectWorker result = persistence.getWorker(workerId, projectId);

        assertTrue("Fails to update ProjectWorkers table", result.getPayRate().compareTo(new BigDecimal(20)) == 0);
    }

    /**
     * Test of removeWorker method with non-existing worker. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveWorker_NonExistWorker() throws Exception {
        assertFalse("Removes a non-existing worker", persistence.removeWorker(workerId, projectId));
    }

    /**
     * Test of removeWorker method with existing worker. Verifies if true is returned. Also verifies if the worker was
     * removed from the ProjectWorkers table.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveWorker_ExistWorker() throws Exception {
        persistence.addProject(project);
        persistence.addWorker(projectWorker);

        assertTrue("Fails to remove existing worker", persistence.removeWorker(workerId, projectId));
        assertNull("Fails to remove from ProjectWorkers table", persistence.getWorker(workerId, projectId));
    }

    /**
     * Test of removeWorkers method with non-existing workers. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveWorkers_NonExistWorkers() throws Exception {
        assertFalse("Removes non-existing workers", persistence.removeWorkers(projectId));
    }

    /**
     * Test of removeWorkers method with existing workers. Verifies if true is returned. Also verifies if the workers
     * were removed from the ProjectWorkers table.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveWorkers_ExistWorkers() throws Exception {
        persistence.addProject(project);
        persistence.addWorker(projectWorker);

        assertTrue("Fails to remove existing workers", persistence.removeWorkers(projectId));
        assertTrue("Fails to remove from ProjectWorkers table", persistence.getWorkers(projectId).isEmpty());
    }

    /**
     * Test of getWorker method with non-existing worker. Verifies if null is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetWorker_NonExistWorker() throws Exception {
        assertNull("Returns an incorrect worker", persistence.getWorker(workerId, projectId));
    }

    /**
     * Test of getWorker method with existing worker. Verifies if the fields of the returned worker are populated.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetWorker_ExistWorker() throws Exception {
        persistence.addProject(project);
        persistence.addWorker(projectWorker);

        project.addWorkerId(workerId);

        ProjectWorker result = persistence.getWorker(workerId, projectId);

        assertTrue("Returns an incorrect worker", TestHelper.compareProjectWorkers(projectWorker, result));
    }

    /**
     * Test of getWorkers method with non-existing workers. Verifies if an empty list is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetWorkers_NonExistWorkers() throws Exception {
        assertTrue("Returns an incorrect list of workers", persistence.getWorkers(projectId).isEmpty());
    }

    /**
     * Test of getWorkers method with existing workers. Verifies if it returns a list containing those workers.
     * Verifies if the fields of the returned workers are populated.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetWorkers_ExistWorkers() throws Exception {
        persistence.addProject(project);
        persistence.addWorker(projectWorker);

        project.addWorkerId(workerId);

        List results = persistence.getWorkers(projectId);

        assertEquals("Returns an incorrect list of workers", 1, results.size());

        ProjectWorker result = (ProjectWorker) results.get(0);

        assertTrue("Returns an incorrect list of workers", TestHelper.compareProjectWorkers(projectWorker, result));
    }

    /**
     * Test of addTimeEntry method with null user. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddTimeEntry_NullUser() throws Exception {
        try {
            persistence.addTimeEntry(entryId, projectId, null);
            fail("Adds time entry with null user");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of addTimeEntry method with empty user. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddTimeEntry_EmptyUser() throws Exception {
        try {
            persistence.addTimeEntry(entryId, projectId, "");
            fail("Adds time entry with empty user");
        } catch (IllegalArgumentException e) {}
    }

    /**
     * Test of addTimeEntry method with existing time entry. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddTimeEntry_ExistEntry() throws Exception {
        persistence.addProject(project);
        persistence.addTimeEntry(entryId, projectId, user);

        assertFalse("Adds an existing time entry", persistence.addTimeEntry(entryId, projectId, user));
    }

    /**
     * Test of addTimeEntry method with non-existing time entry. Verifies if true is returned. Also verifies if the
     * time entry was added to the ProjectTimes table.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddTimeEntry_NonExistEntry() throws Exception {
        persistence.addProject(project);

        assertTrue("Fails to add non-existing time entry", persistence.addTimeEntry(entryId, projectId, user));
        assertFalse("Fails to add to ProjectTimes table", persistence.getTimeEntries(projectId).isEmpty());
    }

    /**
     * Test of removeTimeEntry method with non-existing time entry. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveTimeEntry_NonExistEntry() throws Exception {
        assertFalse("Removes a non-existing time entry", persistence.removeTimeEntry(entryId, projectId));
    }

    /**
     * Test of removeTimeEntry method with existing time entry. Verifies if true is returned. Also verifies if the time
     * entry was removed from the ProjectTimes table.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveTimeEntry_ExistEntry() throws Exception {
        persistence.addProject(project);
        persistence.addTimeEntry(entryId, projectId, user);

        assertTrue("Fails to remove existing time entry", persistence.removeTimeEntry(entryId, projectId));
        assertTrue("Fails to remove from ProjectTimes table", persistence.getTimeEntries(projectId).isEmpty());
    }

    /**
     * Test of getTimeEntries method with empty time entries. Verifies if an empty list is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetTimeEntries_EmptyEntries() throws Exception {
        assertTrue("Returns an incorrect list of time entries", persistence.getTimeEntries(projectId).isEmpty());
    }

    /**
     * Test of getTimeEntries method with non-empty time entries. Verifies if it returns a list containing those time
     * entries.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetTimeEntries_NonEmptyEntries() throws Exception {
        persistence.addProject(project);

        for (int i = 0; i < 3; i++) {
            persistence.addTimeEntry(i, projectId, user);
        }

        List results = persistence.getTimeEntries(projectId);

        assertEquals("Returns an incorrect list of time entries", 3, results.size());
        for (int i = 0; i < 3; i++) {
            assertTrue("Returns an incorrect list of time entries", results.remove(new Integer(i)));
        }
    }

    /**
     * Test of addExpenseEntry method with null user. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddExpenseEntry_NullUser() throws Exception {
        try {
            persistence.addExpenseEntry(entryId, projectId, null);
            fail("Adds expense entry with null user");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of addExpenseEntry method with empty user. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddExpenseEntry_EmptyUser() throws Exception {
        try {
            persistence.addExpenseEntry(entryId, projectId, "");
            fail("Adds expense entry with empty user");
        } catch (IllegalArgumentException e) {}
    }

    /**
     * Test of addExpenseEntry method with existing expense entry. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddExpenseEntry_ExistEntry() throws Exception {
        persistence.addProject(project);
        persistence.addExpenseEntry(entryId, projectId, user);

        assertFalse("Adds an existing expense entry", persistence.addExpenseEntry(entryId, projectId, user));
    }

    /**
     * Test of addExpenseEntry method with non-existing expense entry. Verifies if true is returned. Also verifies if
     * the expense entry was added to the ProjectExpenses table.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddExpenseEntry_NonExistEntry() throws Exception {
        persistence.addProject(project);

        assertTrue("Fails to add non-existing expense entry", persistence.addExpenseEntry(entryId, projectId, user));
        assertFalse("Fails to add to ProjectExpenses table", persistence.getExpenseEntries(projectId).isEmpty());
    }

    /**
     * Test of removeExpenseEntry method with non-existing expense entry. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveExpenseEntry_NonExistEntry()
        throws Exception
    {
        assertFalse("Removes a non-existing expense entry", persistence.removeExpenseEntry(entryId, projectId));
    }

    /**
     * Test of removeExpenseEntry method with existing expense entry. Verifies if true is returned. Also verifies if
     * the expense entry was removed from the ProjectExpenses table.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveExpenseEntry_ExistEntry() throws Exception {
        persistence.addProject(project);
        persistence.addExpenseEntry(entryId, projectId, user);

        assertTrue("Fails to remove existing expense entry", persistence.removeExpenseEntry(entryId, projectId));
        assertTrue("Fails to remove from ProjectExpenses table", persistence.getExpenseEntries(projectId).isEmpty());
    }

    /**
     * Test of getExpenseEntries method with empty expense entries. Verifies if an empty list is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetExpenseEntries_EmptyEntries() throws Exception {
        assertTrue("Returns an incorrect list of expense entries", persistence.getExpenseEntries(projectId).isEmpty());
    }

    /**
     * Test of getExpenseEntries method with non-empty expense entries. Verifies if it returns a list containing those
     * expense entries.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetExpenseEntries_NonEmptyEntries()
        throws Exception
    {
        persistence.addProject(project);

        for (int i = 0; i < 3; i++) {
            persistence.addExpenseEntry(i, projectId, user);
        }

        List results = persistence.getExpenseEntries(projectId);

        assertEquals("Returns an incorrect list of expense entries", 3, results.size());
        for (int i = 0; i < 3; i++) {
            assertTrue("Returns an incorrect list of expense entries", results.remove(new Integer(i)));
        }
    }

    /**
     * Test of getProject method with non-existing project. Verifies if null is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetProject_NonExistProject() throws Exception {
        assertNull("Returns an incorrect project", persistence.getProject(projectId));
    }

    /**
     * Test of getProject method with existing project. Adds some worker, manager, time entry and expense entry to the
     * project. Verifies if the fields of the returned project are populated.
     *
     * @throws Exception if any unexpected exception occurs.
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

        assertTrue("Returns an incorrect project", TestHelper.compareProjects(project, result));
    }

    /**
     * Test of getAllProjects method with empty projects. Verifies if an empty list is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetAllProjects_EmptyProjects() throws Exception {
        assertTrue("Returns an incorrect list of projects", persistence.getAllProjects().isEmpty());
    }

    /**
     * Test of getAllProjects method with non-empty projects. Verifies if it returns a list containing those projects.
     * Verifies if the fields of the returned projects are populated.
     *
     * @throws Exception if any unexpected exception occurs.
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

        assertTrue("Returns an incorrect list of projects", TestHelper.compareProjects(project, result));
    }

    /**
     * Test of removeProjectFromClient method with non-existing project from client. Verifies if false is returned.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveProjectFromClient_NonExistProject()
        throws Exception
    {
        assertFalse("Removes a non-existing project from client",
            persistence.removeProjectFromClient(clientId, projectId));
    }

    /**
     * Test of removeProjectFromClient method with existing project from client. Verifies if true is returned. Also
     * verifies if the project was removed from the ClientProjects table.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testRemoveProjectFromClient_ExistProject()
        throws Exception
    {
        client.addProject(project);
        persistence.addClient(client);

        assertTrue("Fails to remove existing project from client",
            persistence.removeProjectFromClient(clientId, projectId));
        assertNull("Fails to remove from ClientProjects table", persistence.getClientProject(clientId, projectId));
    }

    /**
     * Test of closeConnection method. Adds the client after calling this method. Expects PersistenceException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testCloseConnection() throws Exception {
        persistence.closeConnection();

        try {
            persistence.addClient(client);
            fail("Fails to close connection");
        } catch (PersistenceException e) {}
    }
}
