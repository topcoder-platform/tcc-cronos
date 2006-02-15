/*
 * <p>Copyright (c) 2005, TopCoder, Inc. All rights reserved</p>
 */
package com.topcoder.timetracker.project.accuracytests;

import com.topcoder.timetracker.project.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectPersistenceManager;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.persistence.PersistenceException;
import com.topcoder.timetracker.project.persistence.TimeTrackerProjectPersistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Connection;

import java.util.List;


/**
 * <p>
 * This class contains the accuracy unit tests for InformixTimeTrackerProjectPersistence.java.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class InformixTimeTrackerProjectPersistenceTest extends TestCase {
    /** The InformixTimeTrackerProjectPersistence instance for testing. */
    private TimeTrackerProjectPersistence persistence = null;

    /** connection for testing. */
    private Connection connection = null;

    /** Project instance for testing. */
    private Project project = null;

    /** Client instance for testing. */
    private Client client = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception throw to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig();
        connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
        project = TestHelper.createProject(1);
        client = TestHelper.createClient(1);
        persistence = new ProjectPersistenceManager(TestHelper.NAMESPACE).getPersistence();
    }

    /**
     * <p>
     * Clear the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        TestHelper.clearTables(connection);
        TestHelper.closeResources(null, null, connection);
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(InformixTimeTrackerProjectPersistenceTest.class);
    }

    /**
     * <p>
     * Tests accuracy of addClient() method. Simple Client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddClientAccuracy1() throws Exception {
        Client client = TestHelper.createClient(1);
        assertTrue("client should be added successfully", this.persistence.addClient(client));
        assertEquals("one record should be added into Client", 1, TestHelper.countTableRows("Clients", connection));
        assertFalse("repeated client should not be added successfully", this.persistence.addClient(client));
    }

    /**
     * <p>
     * Tests accuracy of addClient() method. Complex client.
     * </p>
     *
     * @param count the count of records
     * @param tableName the table name
     *
     * @throws Exception exception to JUnit
     */
    private void addClientAccuracy(int count, String tableName)
        throws Exception {
        client.addProject(project);
        this.persistence.addClient(client);
        assertEquals(count + " record should be added into " + tableName, count,
            TestHelper.countTableRows(tableName, connection));
    }

    /**
     * <p>
     * Tests accuracy of addClient() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddClient_ClientsTable() throws Exception {
        addClientAccuracy(1, "Clients");
    }

    /**
     * <p>
     * Tests accuracy of addClient() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddClient_ClientProjectsTable() throws Exception {
        addClientAccuracy(1, "ClientProjects");
    }

    /**
     * <p>
     * Tests accuracy of addClient() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddClient_ProjectsTable() throws Exception {
        addClientAccuracy(1, "Projects");
    }

    /**
     * <p>
     * Tests accuracy of removeClient() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testRemoveClientAccuracy() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        assertTrue("Fails to remove existing client", persistence.removeClient(client.getId()));
        assertEquals("Fails to remove from the Clients table", 0, TestHelper.countTableRows("Clients", connection));
        assertEquals("Fails to remove from the ClientProjects table", 0,
            TestHelper.countTableRows("ClientProjects", connection));
    }

    /**
     * <p>
     * Tests accuracy of updateClient() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testUpdateClientAccuracy() throws Exception {
        persistence.addClient(client);

        // update the name field
        client.setName("update");

        assertTrue("Fails to update the client", persistence.updateClient(client));

        assertEquals("Fails to update the Clients table", "update", persistence.getClient(client.getId()).getName());
    }

    /**
     * <p>
     * Tests accuracy of addProjectToClient() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddProjectToClientAccuracy() throws Exception {
        persistence.addClient(client);

        assertTrue("Fails to add project to the client", persistence.addProjectToClient(client.getId(), project, "user"));
        assertEquals("Fails to insert the new project into Projects table", 1,
            TestHelper.countTableRows("Projects", connection));
    }

    /**
     * <p>
     * Tests accuracy of getClientProject() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testGetClientProjectAccuracy() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        Project result = persistence.getClientProject(client.getId(), project.getId());
        TestHelper.assertProjectsEquals(project, result);
    }

    /**
     * <p>
     * Tests accuracy of getAllClientProjects() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testGetAllClientProjectsAccuracy() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        List results = persistence.getAllClientProjects(client.getId());

        assertEquals(1, results.size());

        TestHelper.assertProjectsEquals(project, (Project) results.get(0));
    }

    /**
     * <p>
     * Tests accuracy of getClient() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testGetClientAccuracy() throws Exception {
        persistence.addClient(client);

        Client result = persistence.getClient(client.getId());

        assertNotNull("client was not properly got", result);
    }

    /**
     * <p>
     * Tests accuracy of getAllClients() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testGetAllClientsAccuracy() throws Exception {
        for (int i = 0; i < 4; i++) {
            client = TestHelper.createClient(i);
            persistence.addClient(client);
        }

        List result = persistence.getAllClients();
        assertEquals("clients were not properly got", 4, result.size());
    }

    /**
     * <p>
     * Tests accuracy of addProject() method. Complex Project.
     * </p>
     *
     * @param count the count of records
     * @param tableName the table name
     *
     * @throws Exception exception to JUnit
     */
    private void addProjectAccuracy(int count, String tableName)
        throws Exception {
        project = TestHelper.createProject(1);

        for (int i = 0; i < 4; i++) {
            project.addExpenseEntry(i);
            project.addTimeEntry(i);
            project.addWorkerId(i);
        }

        project.setManagerId(0);
        this.persistence.addProject(project);
        assertEquals(count + " record should be added into " + tableName, count,
            TestHelper.countTableRows(tableName, connection));
    }

    /**
     * <p>
     * Tests accuracy of addProject() method. Complex Project.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddProject_ProjectTimesTable() throws Exception {
        addProjectAccuracy(4, "ProjectTimes");
    }

    /**
     * <p>
     * Tests accuracy of addProject() method. Complex Project.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddProject_ProjectExpensesTable() throws Exception {
        addProjectAccuracy(4, "ProjectExpenses");
    }

    /**
     * <p>
     * Tests accuracy of addProject() method. Complex Project.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddProject_ProjectWorkersTable() throws Exception {
        addProjectAccuracy(4, "ProjectWorkers");
    }

    /**
     * <p>
     * Tests accuracy of addProject() method. Complex Project.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddProject_ProjectManagersTable() throws Exception {
        addProjectAccuracy(1, "ProjectManagers");
    }

    /**
     * <p>
     * Tests accuracy of updateProject() method.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testUpdateProjectAccuracy() throws Exception {
        persistence.addProject(project);

        project.setName("update");

        assertTrue("Fails to update the project", persistence.updateProject(project));

        assertEquals("Fails to update the Projects table", "update", persistence.getProject(project.getId()).getName());
    }

    /**
     * <p>
     * Tests accuracy of removeProject() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testRemoveProjectAccuracy() throws Exception {
        for (int i = 0; i < 4; i++) {
            project.addExpenseEntry(i);
            project.addTimeEntry(i);
            project.addWorkerId(i);
        }

        persistence.addProject(project);
        assertTrue("Fails to remove existing project", persistence.removeProject(project.getId()));

        assertEquals("Fails to remove from the Projects table", 0, TestHelper.countTableRows("Projects", connection));
        assertEquals("Fails to remove from the ProjectExpenses table", 0,
            TestHelper.countTableRows("ProjectExpenses", connection));
        assertEquals("Fails to remove from the ProjectManagers table", 0,
            TestHelper.countTableRows("ProjectManagers", connection));
        assertEquals("Fails to remove from the ProjectTimes table", 0,
            TestHelper.countTableRows("ProjectTimes", connection));
    }

    /**
     * <p>
     * Tests accuracy of removeAllProjects() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testRemoveAllProjectsAccuracy() throws Exception {
        for (int i = 0; i < 4; i++) {
            project = TestHelper.createProject(i);
            persistence.addProject(project);
        }

        persistence.removeAllProjects();
        assertEquals("Fails to remove from the Projects table", 0, TestHelper.countTableRows("Projects", connection));
    }

    /**
     * <p>
     * Tests accuracy of assignClient() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAssignClientAccuracy() throws Exception {
        persistence.addClient(client);
        persistence.addProject(project);

        assertTrue("Fails to add the client to the project",
            persistence.assignClient(project.getId(), client.getId(), "user"));
        assertEquals("Fails to add to ClientProjects table", 1, TestHelper.countTableRows("ClientProjects", connection));
    }

    /**
     * <p>
     * Tests accuracy of getProjectClient() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testGetProjectClientAccuracy() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        assertNotNull("Fails to get the client", persistence.getProjectClient(project.getId()));
    }

    /**
     * <p>
     * Tests accuracy of assignProjectManager() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAssignProjectManagerAccuracy() throws Exception {
        persistence.addProject(project);

        ProjectManager projectManager = TestHelper.createManager();
        projectManager.setProject(project);
        projectManager.setManagerId(0);

        assertTrue("Fails to assign the project to manager", persistence.assignProjectManager(projectManager));
    }

    /**
     * <p>
     * Tests accuracy of getProjectManager() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testGetProjectManagerAccuracy() throws Exception {
        project.setManagerId(0);
        persistence.addProject(project);

        assertNotNull("Fails to get the projectManager", persistence.getProjectManager(project.getId()));
    }

    /**
     * <p>
     * Tests accuracy of addWorker() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddWorkerAccuracy() throws Exception {
        persistence.addProject(project);

        ProjectWorker projectWorker = TestHelper.createWorker();
        projectWorker.setWorkerId(0);
        projectWorker.setProject(project);

        assertTrue("Fails to add the worker", persistence.addWorker(projectWorker));
    }

    /**
     * <p>
     * Tests accuracy of addExpenseEntry() method.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddExpenseEntryAccuracy() throws Exception {
        project = TestHelper.createProject(1);
        persistence.addProject(project);

        persistence.addExpenseEntry(1, 1, "user");
        assertEquals("ProjectExpenses table should be updated", 1,
            TestHelper.countTableRows("ProjectExpenses", connection));
    }

    /**
     * <p>
     * Tests accuracy of addTimeEntry() method.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddTimeEntryAccuracy() throws Exception {
        project = TestHelper.createProject(1);
        persistence.addProject(project);

        persistence.addTimeEntry(1, 1, "user");
        assertEquals("ProjectTimes table should be updated", 1, TestHelper.countTableRows("ProjectTimes", connection));
    }

    /**
     * <p>
     * Tests accuracy of getProject() method.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testGetProjectAccuracy() throws Exception {
        project = TestHelper.createProject(1);
        persistence.addProject(project);
        TestHelper.assertProjectsEquals(project, persistence.getProject(project.getId()));
    }

    /**
     * <p>
     * Tests accuracy of getAllProjects() method.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testGetAllProjectsAccuracy() throws Exception {
        Project[] projects = new Project[2];

        for (int i = 0; i < 2; i++) {
            projects[i] = TestHelper.createProject(i);
            persistence.addProject(projects[i]);
        }

        List receive_projects = persistence.getAllProjects();
        assertEquals("wrong in getAllProjects", 2, receive_projects.size());
    }

    /**
     * <p>
     * Tests accuracy of RemoveProjectFromClient() method.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testRemoveProjectFromClientAccuracy() throws Exception {
        client.addProject(project);
        persistence.addClient(client);

        assertTrue("Fails to remove existing project from client", persistence.removeProjectFromClient(1, 1));
    }

    /**
     * <p>
     * Tests accuracy of closeConnection() method.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testCloseConnectionAccuary() throws Exception {
        persistence.closeConnection();

        try {
            persistence.addClient(new Client(1));
            fail("Fails to close connection");
        } catch (PersistenceException e) {
            // good
        }
    }
}
