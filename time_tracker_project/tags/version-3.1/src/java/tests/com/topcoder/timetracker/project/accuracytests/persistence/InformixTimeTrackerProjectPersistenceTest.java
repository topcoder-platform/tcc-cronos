/*
 * <p>Copyright (c) 2005, TopCoder, Inc. All rights reserved</p>
 */
package com.topcoder.timetracker.project.accuracytests.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.project.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectPersistenceManager;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.accuracytests.Helper;
import com.topcoder.timetracker.project.persistence.PersistenceException;
import com.topcoder.timetracker.project.persistence.TimeTrackerProjectPersistence;
import com.topcoder.timetracker.project.searchfilters.BinaryOperation;
import com.topcoder.timetracker.project.searchfilters.BinaryOperationFilter;
import com.topcoder.timetracker.project.searchfilters.CompareOperation;
import com.topcoder.timetracker.project.searchfilters.Filter;
import com.topcoder.timetracker.project.searchfilters.MultiValueFilter;
import com.topcoder.timetracker.project.searchfilters.NotFilter;
import com.topcoder.timetracker.project.searchfilters.ValueFilter;

/**
 * <p>
 * This class contains the accuracy unit tests for InformixTimeTrackerProjectPersistence.java.
 * </p>
 *
 * @author FireIce
 * @author PE
 * @author costty000
 * @author kr00tki
 * @version 2.0
 * @since 1.0
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
        Helper.addConfig();
        connection = Helper.getConnection();
        project = Helper.createProject(1);
        client = Helper.createClient(1);
        persistence = new ProjectPersistenceManager(Helper.NAMESPACE).getPersistence();
    }

    /**
     * <p>
     * Clear the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        persistence.closeConnection();
        Helper.clearTables();
        Helper.closeResources(null, null, connection);
        Helper.clearConfig();
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
        Client client = Helper.createClient(1);
        assertTrue("client should be added successfully", this.persistence.addClient(client));
        assertEquals("one record should be added into Client", 1, Helper.countTableRows("client", connection));
        assertFalse("repeated client should not be added successfully", this.persistence.addClient(client));
    }

    /**
     * <p>
     * Tests accuracy of addClient() method. Complex client.
     * </p>
     *
     * @param count the count of records
     * @param tableName the table name
     * @throws Exception exception to JUnit
     */
    private void addClientAccuracy(int count, String tableName) throws Exception {
        client.addProject(project);
        this.persistence.addClient(client);
        assertEquals(count + " record should be added into " + tableName, count, Helper.countTableRows(tableName,
                connection));
    }

    /**
     * <p>
     * Tests accuracy of addClient() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddClient_ClientsTable() throws Exception {
        addClientAccuracy(1, "client");
    }

    /**
     * <p>
     * Tests accuracy of addClient() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddClient_ClientProjectsTable() throws Exception {
        addClientAccuracy(1, "client_project");
    }

    /**
     * <p>
     * Tests accuracy of addClient() method. Complex client.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddClient_ProjectsTable() throws Exception {
        addClientAccuracy(1, "project");
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
        assertEquals("Fails to remove from the client table", 0, Helper.countTableRows("client", connection));
        assertEquals("Fails to remove from the client_project table", 0, Helper.countTableRows("client_project",
                connection));
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

        assertEquals("Fails to update the client table", "update", persistence.getClient(client.getId()).getName());
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

        assertTrue("Fails to add project to the client", persistence.addProjectToClient(client.getId(), project,
                "user"));
        assertEquals("Fails to insert the new project into project table", 1, Helper.countTableRows("project",
                connection));
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
        Helper.assertProjectsEquals(project, result);
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

        Helper.assertProjectsEquals(project, (Project) results.get(0));
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
            client = Helper.createClient(i);
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
     * @throws Exception exception to JUnit
     */
    private void addProjectAccuracy(int count, String tableName) throws Exception {
        project = Helper.createProject(1);

        for (int i = 0; i < 4; i++) {
            project.addExpenseEntry(i);
            project.addTimeEntry(i);
            project.addWorkerId(i);
        }

        project.setManagerId(0);
        this.persistence.addProject(project);
        assertEquals(count + " record should be added into " + tableName, count, Helper.countTableRows(tableName,
                connection));
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

        assertEquals("Fails to update the project table", "update", persistence.getProject(project.getId())
                .getName());
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

        assertEquals("Fails to remove from the project table", 0, Helper.countTableRows("project", connection));
        assertEquals("Fails to remove from the project_expense table", 0, Helper.countTableRows("project_expense",
                connection));
        assertEquals("Fails to remove from the project_manager table", 0, Helper.countTableRows("project_manager",
                connection));
        assertEquals("Fails to remove from the project_time table", 0, Helper.countTableRows("project_time",
                connection));
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
            project = Helper.createProject(i);
            persistence.addProject(project);
        }

        persistence.removeAllProjects();
        assertEquals("Fails to remove from the project table", 0, Helper.countTableRows("project", connection));
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

        assertTrue("Fails to add the client to the project", persistence.assignClient(project.getId(), client
                .getId(), "user"));
        assertEquals("Fails to add to client_project table", 1, Helper
                .countTableRows("client_project", connection));
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

        ProjectManager projectManager = Helper.createManager();
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

        ProjectManager projectManager = Helper.createManager();
        projectManager.setProject(project);
        projectManager.setManagerId(0);

        persistence.assignProjectManager(projectManager);
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
        ProjectWorker projectWorker = Helper.createWorker();
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
        project = Helper.createProject(1);
        persistence.addProject(project);
        persistence.addExpenseEntry(1, 1, "user");
        assertEquals("project_expense table should be updated", 1, Helper.countTableRows("project_expense",
                connection));
    }

    /**
     * <p>
     * Tests accuracy of addTimeEntry() method.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testAddTimeEntryAccuracy() throws Exception {
        project = Helper.createProject(1);
        persistence.addProject(project);

        persistence.addTimeEntry(1, 1, "user");
        assertEquals("project_time table should be updated", 1, Helper.countTableRows("project_time", connection));
    }

    /**
     * <p>
     * Tests accuracy of getProject() method.
     * </p>
     *
     * @throws Exception exception to JUnit
     */
    public void testGetProjectAccuracy() throws Exception {
        project = Helper.createProject(1);
        persistence.addProject(project);
        Helper.assertProjectsEquals(project, persistence.getProject(project.getId()));
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
            projects[i] = Helper.createProject(i);
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

    /**
     * accuracy test for searchForProjects method.
     *
     * @throws Excpetion to Junit
     */
    public void testSearchForProjectsAccuracy() throws Exception {
        // make user the table has no data.

        assertEquals("project table not empty", 0, Helper.countTableRows("project", connection));

        Filter filter = new ValueFilter(CompareOperation.LIKE, "Creation User", "creation%");
        Project[] projects = persistence.searchForProjects(filter);
        assertNotNull(projects);
        assertEquals("should return 0-length array", 0, projects.length);

        filter = new ValueFilter(CompareOperation.LIKE, "Creation User", "mock%");
        projects = persistence.searchForProjects(filter);
        assertNotNull(projects);
        assertEquals("should return 0-length array", 0, projects.length);

        filter = new MultiValueFilter("Creation User", new Object[] {"creationUser", "modificationUser"});
        projects = persistence.searchForProjects(filter);
        assertNotNull(projects);
        assertEquals("should return 0-length array", 0, projects.length);

        Filter leftOperand = new ValueFilter(CompareOperation.EQUAL, "Name", "name");
        Filter rightOperand = new ValueFilter(CompareOperation.EQUAL, "Creation User", "creationUser");

        filter = new BinaryOperationFilter(BinaryOperation.AND, leftOperand, rightOperand);
        projects = persistence.searchForProjects(filter);
        assertNotNull(projects);
        assertEquals("should return 0-length array", 0, projects.length);

        Filter operand = new ValueFilter(CompareOperation.EQUAL, "Name", "abc");
        filter = new NotFilter(operand);
        projects = persistence.searchForProjects(filter);
        assertNotNull(projects);
        assertEquals("should return 0-length array", 0, projects.length);

        // add project items
        project = Helper.createProject(1);
        project.setManagerId(0);
        persistence.addProject(project);

        project = Helper.createProject(3);
        project.setManagerId(0);
        project.setName("abc");
        project.setDescription("description");
        project.setCreationUser("modificationUser");
        project.setModificationUser("creationUser");
        project.setStartDate(new Date());
        project.setEndDate(new Date());

        persistence.addProject(project);

        assertEquals("project table not empty", 2, Helper.countTableRows("project", connection));

        filter = new ValueFilter(CompareOperation.EQUAL, "Name", "name");
        projects = persistence.searchForProjects(filter);
        assertNotNull(projects);
        assertEquals("should contains one element", 1, projects.length);
        assertEquals("incorrect item", 1, projects[0].getId());

        filter = new ValueFilter(CompareOperation.LIKE, "Creation User", "mock%");
        projects = persistence.searchForProjects(filter);
        assertNotNull(projects);
        assertEquals("should return 0-length array", 0, projects.length);

        filter = new MultiValueFilter("Creation User", new Object[] {"creationUser", "modificationUser"});
        projects = persistence.searchForProjects(filter);
        assertNotNull(projects);
        assertEquals("should contains two elements", 2, projects.length);

        filter = new BinaryOperationFilter(BinaryOperation.AND, leftOperand, rightOperand);
        projects = persistence.searchForProjects(filter);
        assertNotNull(projects);
        assertEquals("should contains one element", 1, projects.length);

        filter = new NotFilter(operand);
        projects = persistence.searchForProjects(filter);
        assertNotNull(projects);
        assertEquals("should contains one element", 1, projects.length);
    }

    /**
     * accuracy test for searchForClients method.
     *
     * @throws Excpetion to Junit
     */
    public void testSearchForClientsAccuracy() throws Exception {
        // make user the table has no data.

        assertEquals("client table not empty", 0, Helper.countTableRows("project_time", connection));

        Filter filter = new ValueFilter(CompareOperation.LIKE, "Creation User", "creation%");
        Client[] clients = persistence.searchForClients(filter);
        assertNotNull(clients);
        assertEquals("should return 0-length array", 0, clients.length);

        filter = new ValueFilter(CompareOperation.LIKE, "Creation User", "mock%");
        clients = persistence.searchForClients(filter);
        assertNotNull(clients);
        assertEquals("should return 0-length array", 0, clients.length);

        filter = new MultiValueFilter("Creation User", new Object[] {"creationUser", "modificationUser"});
        clients = persistence.searchForClients(filter);
        assertNotNull(clients);
        assertEquals("should return 0-length array", 0, clients.length);

        Filter leftOperand = new ValueFilter(CompareOperation.EQUAL, "Name", "name_1");
        Filter rightOperand = new ValueFilter(CompareOperation.EQUAL, "Creation User", "creationUser");

        filter = new BinaryOperationFilter(BinaryOperation.AND, leftOperand, rightOperand);
        clients = persistence.searchForClients(filter);
        assertNotNull(clients);
        assertEquals("should return 0-length array", 0, clients.length);

        Filter operand = new ValueFilter(CompareOperation.EQUAL, "Name", "abc");
        filter = new NotFilter(operand);
        clients = persistence.searchForClients(filter);
        assertNotNull(clients);
        assertEquals("should return 0-length array", 0, clients.length);

        // add clients
        Client client = Helper.createClient(1);
        persistence.addClient(client);

        client = Helper.createClient(3);
        client.setName("abc");
        client.setCreationUser("modificationUser");
        client.setModificationUser("creationUser");
        client.setCreationDate(new Date());
        client.setModificationDate(new Date());

        persistence.addClient(client);

        assertEquals("client table not empty", 2, Helper.countTableRows("client", connection));

        filter = new ValueFilter(CompareOperation.LIKE, "Creation User", "creation%");
        clients = persistence.searchForClients(filter);
        assertNotNull(clients);
        assertEquals("should contains one element", 1, clients.length);
        assertEquals("incorrect item", 1, clients[0].getId());

        filter = new ValueFilter(CompareOperation.LIKE, "Creation User", "mock%");
        clients = persistence.searchForClients(filter);
        assertNotNull(clients);
        assertEquals("should return 0-length array", 0, clients.length);

        filter = new MultiValueFilter("Creation User", new Object[] {"creationUser", "modificationUser"});
        clients = persistence.searchForClients(filter);
        assertNotNull(clients);
        assertEquals("should contains two elements", 2, clients.length);

        filter = new BinaryOperationFilter(BinaryOperation.AND, leftOperand, rightOperand);
        clients = persistence.searchForClients(filter);
        assertNotNull(clients);
        assertEquals("should contains one element", 1, clients.length);

        filter = new NotFilter(operand);
        clients = persistence.searchForClients(filter);
        assertNotNull(clients);
        assertEquals("should contains one element", 1, clients.length);
    }

    /**
     * accuracy test for addProjects(Projects[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectsAccuracyFalse() throws Exception {
        assertEquals("project table not empty", 0, Helper.countTableRows("project", connection));
        int size = 10;
        Project[] projects = new Project[size];

        for (int i = 0; i < size; i++) {
            projects[i] = Helper.createProject(i);
        }

        persistence.addProjects(projects, false);

        assertEquals("project table not empty", size, Helper.countTableRows("project", connection));
    }

    /**
     * accuracy test for addProjects(Projects[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectsAccuracyTrue() throws Exception {
        assertEquals("project table not empty", 0, Helper.countTableRows("project", connection));
        int size = 10;
        Project[] projects = new Project[size];

        for (int i = 0; i < size; i++) {
            projects[i] = Helper.createProject(i);
        }

        persistence.addProjects(projects, true);

        assertEquals("project table not empty", size, Helper.countTableRows("project", connection));
    }

    /**
     * accuracy test for addClients(Client[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testAddClientsAccuracyFalse() throws Exception {
        assertEquals("client table not empty", 0, Helper.countTableRows("client", connection));
        int size = 10;
        Client[] clients = new Client[size];

        for (int i = 0; i < size; i++) {
            clients[i] = Helper.createClient(i);
        }

        persistence.addClients(clients, false);

        assertEquals("client table not empty", size, Helper.countTableRows("client", connection));
    }

    /**
     * accuracy test for addProjects(Projects[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testAddClientsAccuracyTrue() throws Exception {
        assertEquals("client table not empty", 0, Helper.countTableRows("client", connection));
        int size = 10;
        Client[] clients = new Client[size];

        for (int i = 0; i < size; i++) {
            clients[i] = Helper.createClient(i);
        }

        persistence.addClients(clients, true);

        assertEquals("client table not empty", size, Helper.countTableRows("client", connection));
    }

    /**
     * accuracy test for removeProjects(int[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectsAccuracyTrue() throws Exception {
        assertEquals("project table not empty", 0, Helper.countTableRows("project", connection));
        int size = 10;
        Project[] projects = new Project[size];

        for (int i = 0; i < size; i++) {
            projects[i] = Helper.createProject(i);
        }

        persistence.addProjects(projects, false);

        assertEquals("project table not empty", size, Helper.countTableRows("project", connection));

        int[] part = new int[size / 2];
        for (int i = 0; i < size / 2; i++) {
            part[i] = i;
        }
        persistence.removeProjects(part, true);

        assertEquals("project table not empty", size - size / 2, Helper.countTableRows("project", connection));
    }

    /**
     * accuracy test for removeProjects(Projects[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectsAccuracyFalse() throws Exception {
        assertEquals("project table not empty", 0, Helper.countTableRows("project", connection));
        int size = 10;
        Project[] projects = new Project[size];

        for (int i = 0; i < size; i++) {
            projects[i] = Helper.createProject(i);
        }

        persistence.addProjects(projects, true);

        assertEquals("project table not empty", size, Helper.countTableRows("project", connection));

        int[] part = new int[size / 2];
        for (int i = 0; i < size / 2; i++) {
            part[i] = i;
        }
        persistence.removeProjects(part, false);

        assertEquals("project table not empty", size - size / 2, Helper.countTableRows("project", connection));
    }

    /**
     * accuracy test for removeClients(int[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveClientsAccuracyTrue() throws Exception {
        assertEquals("client table not empty", 0, Helper.countTableRows("client", connection));
        int size = 10;
        Client[] clients = new Client[size];

        for (int i = 0; i < size; i++) {
            clients[i] = Helper.createClient(i);
        }

        persistence.addClients(clients, false);

        assertEquals("client table not empty", size, Helper.countTableRows("client", connection));

        int[] part = new int[size / 2];
        for (int i = 0; i < size / 2; i++) {
            part[i] = i;
        }
        persistence.removeClients(part, true);

        assertEquals("client table not empty", size - size / 2, Helper.countTableRows("client", connection));
    }

    /**
     * accuracy test for removeProjects(Projects[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveClientsAccuracyFalse() throws Exception {
        assertEquals("client table not empty", 0, Helper.countTableRows("client", connection));
        int size = 10;
        Client[] clients = new Client[size];

        for (int i = 0; i < size; i++) {
            clients[i] = Helper.createClient(i);
        }

        persistence.addClients(clients, false);

        assertEquals("client table not empty", size, Helper.countTableRows("client", connection));

        int[] part = new int[size / 2];
        for (int i = 0; i < size / 2; i++) {
            part[i] = i;
        }
        persistence.removeClients(part, true);

        assertEquals("client table not empty", size - size / 2, Helper.countTableRows("client", connection));
    }

    /**
     * accuracy test for updateProjects(int[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectsAccuracyFalse() throws Exception {
        assertEquals("project table not empty", 0, Helper.countTableRows("project", connection));
        int size = 10;
        Project[] projects = new Project[size];

        for (int i = 0; i < size; i++) {
            projects[i] = Helper.createProject(i);
        }

        persistence.addProjects(projects, false);

        assertEquals("project table not empty", size, Helper.countTableRows("project", connection));
        Project[] toUpdate = new Project[1];
        toUpdate[0] = projects[2];

        toUpdate[0].setModificationUser("FireIce");

        persistence.updateProjects(toUpdate, false);

        Project pro = persistence.getProject(2);
        assertNotNull(pro);
        assertEquals("FireIce", pro.getModificationUser());
    }

    /**
     * accuracy test for updateProjects(project[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectsAccuracyTrue() throws Exception {
        assertEquals("project table not empty", 0, Helper.countTableRows("project", connection));
        int size = 10;
        Project[] projects = new Project[size];

        for (int i = 0; i < size; i++) {
            projects[i] = Helper.createProject(i);
        }

        persistence.addProjects(projects, true);

        assertEquals("project table not empty", size, Helper.countTableRows("project", connection));
        Project[] toUpdate = new Project[1];
        toUpdate[0] = projects[2];

        toUpdate[0].setModificationUser("FireIce");

        persistence.updateProjects(toUpdate, true);

        Project pro = persistence.getProject(2);
        assertNotNull(pro);
        assertEquals("FireIce", pro.getModificationUser());
    }

    /**
     * accuracy test for updateClients(Client[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateClientsAccuracyFalse() throws Exception {
        assertEquals("client table not empty", 0, Helper.countTableRows("client", connection));
        int size = 10;
        Client[] clients = new Client[size];

        for (int i = 0; i < size; i++) {
            clients[i] = Helper.createClient(i);
        }

        persistence.addClients(clients, false);

        assertEquals("client table not empty", size, Helper.countTableRows("client", connection));
        Client[] toUpdate = new Client[1];
        toUpdate[0] = clients[2];

        toUpdate[0].setModificationUser("FireIce");

        persistence.updateClients(toUpdate, false);

        Client cli = persistence.getClient(2);
        assertNotNull(cli);
        assertEquals("FireIce", cli.getModificationUser());
    }

    /**
     * accuracy test for updateClients(Client[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateClientsAccuracyTrue() throws Exception {
        assertEquals("client table not empty", 0, Helper.countTableRows("client", connection));
        int size = 10;
        Client[] clients = new Client[size];

        for (int i = 0; i < size; i++) {
            clients[i] = Helper.createClient(i);
        }

        persistence.addClients(clients, false);

        assertEquals("client table not empty", size, Helper.countTableRows("client", connection));
        Client[] toUpdate = new Client[1];
        toUpdate[0] = clients[2];

        toUpdate[0].setModificationUser("FireIce");

        persistence.updateClients(toUpdate, true);

        Client cli = persistence.getClient(2);
        assertNotNull(cli);
        assertEquals("FireIce", cli.getModificationUser());
    }

    /**
     * accuracy test for getProjects(int[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testGetProjectsAccuracyFalse() throws Exception {
        assertEquals("project table not empty", 0, Helper.countTableRows("project", connection));
        int size = 10;
        Project[] projects = new Project[size];

        for (int i = 0; i < size; i++) {
            projects[i] = Helper.createProject(i);
        }

        persistence.addProjects(projects, false);

        assertEquals("project table not empty", size, Helper.countTableRows("project", connection));

        int[] ids = new int[5];
        for (int i = 0; i < 5; i++) {
            ids[i] = i + 2;
        }

        Project[] result = persistence.getProjects(ids, false);

        assertEquals(5, result.length);
    }

    /**
     * accuracy test for updateProjects(Projects[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testGetProjectsAccuracyTrue() throws Exception {
        assertEquals("project table not empty", 0, Helper.countTableRows("project", connection));
        int size = 10;
        Project[] projects = new Project[size];

        for (int i = 0; i < size; i++) {
            projects[i] = Helper.createProject(i);
        }

        persistence.addProjects(projects, true);

        assertEquals("project table not empty", size, Helper.countTableRows("project", connection));

        int[] ids = new int[5];
        for (int i = 0; i < 5; i++) {
            ids[i] = i + 2;
        }

        Project[] result = persistence.getProjects(ids, true);

        assertEquals(5, result.length);
    }

    /**
     * accuracy test for getClients(int[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testGetClientsAccuracyFalse() throws Exception {
        assertEquals("client table not empty", 0, Helper.countTableRows("client", connection));
        int size = 10;
        Client[] clients = new Client[size];

        for (int i = 0; i < size; i++) {
            clients[i] = Helper.createClient(i);
        }

        persistence.addClients(clients, false);

        assertEquals("client table not empty", size, Helper.countTableRows("client", connection));

        int[] ids = new int[5];
        for (int i = 0; i < 5; i++) {
            ids[i] = i + 2;
        }

        Client[] result = persistence.getClients(ids, false);

        assertEquals(5, result.length);
    }

    /**
     * accuracy test for getClients(int[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testGetClientsAccuracyTrue() throws Exception {
        assertEquals("client table not empty", 0, Helper.countTableRows("client", connection));
        int size = 10;
        Client[] clients = new Client[size];

        for (int i = 0; i < size; i++) {
            clients[i] = Helper.createClient(i);
        }

        persistence.addClients(clients, false);

        assertEquals("client table not empty", size, Helper.countTableRows("client", connection));

        int[] ids = new int[5];
        for (int i = 0; i < 5; i++) {
            ids[i] = i + 2;
        }

        Client[] result = persistence.getClients(ids, true);

        assertEquals(5, result.length);
    }

    /**
     * accuracy test for addWorkers(ProjectWorker[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testAddWorkersAccuracyFalse() throws Exception {
        assertEquals("project_worker table not empty", 0, Helper.countTableRows("project_worker", connection));
        int size = 4;
        ProjectWorker[] workers = new ProjectWorker[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            workers[i] = Helper.createWorker();
            workers[i].setWorkerId(i);
            workers[i].setProject(project);
        }

        persistence.addWorkers(workers, false);

        assertEquals("project_worker table not empty", size, Helper.countTableRows("project_worker", connection));
    }

    /**
     * accuracy test for addWorkers(ProjectWorker[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testAddWorkersAccuracyTrue() throws Exception {
        assertEquals("project_worker table not empty", 0, Helper.countTableRows("project_worker", connection));
        int size = 4;
        ProjectWorker[] workers = new ProjectWorker[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            workers[i] = Helper.createWorker();
            workers[i].setWorkerId(i);
            workers[i].setProject(project);
        }

        persistence.addWorkers(workers, true);

        assertEquals("project_worker table not empty", size, Helper.countTableRows("project_worker", connection));
    }

    /**
     * accuracy test for removeWorkers(int[],int, boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveWorkersAccuracyFalse() throws Exception {
        assertEquals("project_worker table not empty", 0, Helper.countTableRows("project_worker", connection));
        int size = 4;
        ProjectWorker[] workers = new ProjectWorker[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            workers[i] = Helper.createWorker();
            workers[i].setWorkerId(i);
            workers[i].setProject(project);
        }

        persistence.addWorkers(workers, false);

        assertEquals("project_worker table not empty", size, Helper.countTableRows("project_worker", connection));

        int[] workerIds = new int[size / 2];

        for (int i = 0; i < size / 2; i++) {
            workerIds[i] = i;
        }

        persistence.removeWorkers(workerIds, project.getId(), false);

        assertEquals("project_worker table not empty", size - size / 2, Helper.countTableRows("project_worker",
                connection));
    }

    /**
     * accuracy test for removeWorkers(int[],int, boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveWorkersAccuracyTrue() throws Exception {
        assertEquals("project_worker table not empty", 0, Helper.countTableRows("project_worker", connection));
        int size = 4;
        ProjectWorker[] workers = new ProjectWorker[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            workers[i] = Helper.createWorker();
            workers[i].setWorkerId(i);
            workers[i].setProject(project);
        }

        persistence.addWorkers(workers, true);

        assertEquals("project_worker table not empty", size, Helper.countTableRows("project_worker", connection));

        int[] workerIds = new int[size / 2];

        for (int i = 0; i < size / 2; i++) {
            workerIds[i] = i;
        }

        persistence.removeWorkers(workerIds, project.getId(), true);

        assertEquals("project_worker table not empty", size - size / 2, Helper.countTableRows("project_worker",
                connection));
    }

    /**
     * accuracy test for updateWorkers(ProjectWorker[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateWorkersAccuracyFalse() throws Exception {
        assertEquals("project_worker table not empty", 0, Helper.countTableRows("project_worker", connection));
        int size = 4;
        ProjectWorker[] workers = new ProjectWorker[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            workers[i] = Helper.createWorker();
            workers[i].setWorkerId(i);
            workers[i].setProject(project);
        }

        persistence.addWorkers(workers, false);

        assertEquals("project_worker table not empty", size, Helper.countTableRows("project_worker", connection));

        for (int i = 0; i < size; i++) {
            workers[i].setModificationUser("FireIce");
        }

        persistence.updateWorkers(workers, false);

        assertEquals("project_worker table not empty", size, Helper.countTableRows("project_worker", connection));

        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery(
                    "SELECT count(*) FROM project_worker WHERE project_worker.modification_user = 'FireIce'");
            assertTrue(rs.next());
            assertEquals("query result not correct", size, rs.getInt(1));
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    /**
     * accuracy test for updateWorkers(ProjectWorker[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateWorkersAccuracyTrue() throws Exception {
        assertEquals("project_worker table not empty", 0, Helper.countTableRows("project_worker", connection));
        int size = 4;
        ProjectWorker[] workers = new ProjectWorker[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            workers[i] = Helper.createWorker();
            workers[i].setWorkerId(i);
            workers[i].setProject(project);
        }

        persistence.addWorkers(workers, true);

        assertEquals("project_worker table not empty", size, Helper.countTableRows("project_worker", connection));

        for (int i = 0; i < size; i++) {
            workers[i].setModificationUser("FireIce");
        }

        persistence.updateWorkers(workers, true);

        assertEquals("project_worker table not empty", size, Helper.countTableRows("project_worker", connection));

        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery(
                    "SELECT count(*) FROM project_worker WHERE project_worker.modification_user = 'FireIce'");
            assertTrue(rs.next());
            assertEquals("query result not correct", size, rs.getInt(1));
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    /**
     * accuracy test for getWorkers(int[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testGetWorkersAccuracyFalse() throws Exception {
        assertEquals("project_worker table not empty", 0, Helper.countTableRows("project_worker", connection));
        int size = 4;
        ProjectWorker[] workers = new ProjectWorker[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            workers[i] = Helper.createWorker();
            workers[i].setWorkerId(i);
            workers[i].setProject(project);
        }

        persistence.addWorkers(workers, false);

        assertEquals("project_worker table not empty", size, Helper.countTableRows("project_worker", connection));

        int[] ids = new int[2];
        ids[0] = 1;
        ids[1] = 3;

        ProjectWorker[] result = persistence.getWorkers(ids, project.getId(), false);
        assertNotNull(result);
        assertEquals("returned array size right", 2, result.length);
    }

    /**
     * accuracy test for getWorkers(int[], boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testGetWorkersAccuracyTrue() throws Exception {
        assertEquals("project_worker table not empty", 0, Helper.countTableRows("project_worker", connection));
        int size = 4;
        ProjectWorker[] workers = new ProjectWorker[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            workers[i] = Helper.createWorker();
            workers[i].setWorkerId(i);
            workers[i].setProject(project);
        }

        persistence.addWorkers(workers, true);

        assertEquals("project_worker table not empty", size, Helper.countTableRows("project_worker", connection));

        int[] ids = new int[2];
        ids[0] = 1;
        ids[1] = 3;

        ProjectWorker[] result = persistence.getWorkers(ids, project.getId(), true);
        assertNotNull(result);
        assertEquals("returned array size right", 2, result.length);
    }

    /**
     * accuracy test for addTimeEntries(int[], int, String, boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testAddTimeEntriesAccuracyFalse() throws Exception {
        assertEquals("project_time table not empty", 0, Helper.countTableRows("project_time", connection));
        int size = 4;
        int[] entryids = new int[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            entryids[i] = i;
        }

        persistence.addTimeEntries(entryids, project.getId(), "FireIce", false);

        assertEquals("project_time table not empty", size, Helper.countTableRows("project_time", connection));
    }

    /**
     * accuracy test for addTimeEntries(int[], int, String, boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testAddTimeEntriesAccuracyTrue() throws Exception {
        assertEquals("project_time table not empty", 0, Helper.countTableRows("project_time", connection));
        int size = 4;
        int[] entryids = new int[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            entryids[i] = i;
        }

        persistence.addTimeEntries(entryids, project.getId(), "FireIce", true);

        assertEquals("project_time table not empty", size, Helper.countTableRows("project_time", connection));
    }

    /**
     * accuracy test for removeTimeEntries(int[], int, boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveTimeEntriesAccuracyFalse() throws Exception {
        assertEquals("project_time table not empty", 0, Helper.countTableRows("project_time", connection));
        int size = 4;
        int[] entryids = new int[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            entryids[i] = i;
        }

        persistence.addTimeEntries(entryids, project.getId(), "FireIce", false);

        assertEquals("project_time table not empty", size, Helper.countTableRows("project_time", connection));

        entryids = new int[size / 2];

        for (int i = 0; i < size / 2; i++) {
            entryids[i] = 2 * i;
        }

        persistence.removeTimeEntries(entryids, project.getId(), false);

        assertEquals("project_time table not empty", size - size / 2, Helper.countTableRows("project_time",
                connection));
    }

    /**
     * accuracy test for removeTimeEntries(int[], int, boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveTimeEntriesAccuracyTrue() throws Exception {
        assertEquals("project_time table not empty", 0, Helper.countTableRows("project_time", connection));
        int size = 4;
        int[] entryids = new int[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            entryids[i] = i;
        }

        persistence.addTimeEntries(entryids, project.getId(), "FireIce", true);

        assertEquals("project_time table not empty", size, Helper.countTableRows("project_time", connection));

        entryids = new int[size / 2];

        for (int i = 0; i < size / 2; i++) {
            entryids[i] = 2 * i;
        }

        persistence.removeTimeEntries(entryids, project.getId(), true);

        assertEquals("project_time table not empty", size - size / 2, Helper.countTableRows("project_time",
                connection));
    }

    /**
     * accuracy test for addExpenseEntries(int[], int, String, boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testAddExpenseEntriesAccuracyFalse() throws Exception {
        assertEquals("project_expense table not empty", 0, Helper.countTableRows("project_expense", connection));
        int size = 4;
        int[] entryids = new int[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            entryids[i] = i;
        }

        persistence.addExpenseEntries(entryids, project.getId(), "FireIce", false);

        assertEquals("project_expense table not empty", size, Helper.countTableRows("project_expense", connection));
    }

    /**
     * accuracy test for addExpenseEntries(int[], int, String, boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testAddExpenseEntriesAccuracyTrue() throws Exception {
        assertEquals("project_expense table not empty", 0, Helper.countTableRows("project_expense", connection));
        int size = 4;
        int[] entryids = new int[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            entryids[i] = i;
        }

        persistence.addExpenseEntries(entryids, project.getId(), "FireIce", true);

        assertEquals("project_expense table not empty", size, Helper.countTableRows("project_expense", connection));
    }

    /**
     * accuracy test for removeExpenseEntries(int[], int, boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveExpenseEntriesAccuracyFalse() throws Exception {
        assertEquals("project_expense table not empty", 0, Helper.countTableRows("project_expense", connection));
        int size = 4;
        int[] entryids = new int[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            entryids[i] = i;
        }

        persistence.addExpenseEntries(entryids, project.getId(), "FireIce", false);

        assertEquals("project_expense table not empty", size, Helper.countTableRows("project_expense", connection));

        entryids = new int[size / 2];

        for (int i = 0; i < size / 2; i++) {
            entryids[i] = 2 * i;
        }

        persistence.removeExpenseEntries(entryids, project.getId(), false);

        assertEquals("project_expense table not empty", size - size / 2, Helper.countTableRows("project_expense",
                connection));
    }

    /**
     * accuracy test for removeExpenseEntries(int[], int, boolean) method.
     *
     * @throws Exception to JUnit
     */
    public void testRemoveExpenseEntriesAccuracyTrue() throws Exception {
        assertEquals("project_expense table not empty", 0, Helper.countTableRows("project_expense", connection));
        int size = 4;
        int[] entryids = new int[size];
        // add project
        persistence.addProject(project);
        for (int i = 0; i < size; i++) {
            entryids[i] = i;
        }

        persistence.addExpenseEntries(entryids, project.getId(), "FireIce", true);

        assertEquals("project_expense table not empty", size, Helper.countTableRows("project_expense", connection));

        entryids = new int[size / 2];

        for (int i = 0; i < size / 2; i++) {
            entryids[i] = 2 * i;
        }

        persistence.removeExpenseEntries(entryids, project.getId(), true);

        assertEquals("project_expense table not empty", size - size / 2, Helper.countTableRows("project_expense",
                connection));
    }

    // since 2.0
    /**
     * <c>
     * Tests the accuracy of <code>getCompanyIdForClient</code> method.
     * </c>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForClient() throws Exception {
        Client client = Helper.createClient(1);
        client.setCompanyId(2);
        persistence.addClient(client);

        // get the company id.
        assertEquals("Incorrect company id.", 2, persistence.getCompanyIdForClient(1));
    }

    /**
     * <c>
     * Tests the accuracy of <code>getCompanyIdForClient</code> method.
     * </c>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForClient_NotFound() throws Exception {
        assertEquals("Client not exists", -1, persistence.getCompanyIdForClient(5));
    }

    /**
     * <c>
     * Tests the accuracy of <code>getCompanyIdForProject</code> method.
     * </c>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForProject() throws Exception {
        Project project = Helper.createProject(10);
        project.setCompanyId(1);
        persistence.addProject(project);

        // get the company id
        assertEquals("Incorrect company id.", 1, persistence.getCompanyIdForProject(10));
    }

    /**
     * <c>
     * Tests the accuracy of <code>getCompanyIdForProject</code> method.
     * </c>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForProject_NotFound() throws Exception {
        assertEquals("Project not exists", -1, persistence.getCompanyIdForProject(5));
    }

    /**
     * <c>
     * Tests the accuracy of <code>getCompanyIdForTimeEntry</code> method.
     * </c>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForTimeEntry() throws Exception {
        // this time entry is loaded in the init script
        assertEquals("Incorrect company id.", 1, persistence.getCompanyIdForTimeEntry(0));
    }

    /**
     * <c>
     * Tests the accuracy of <code>getCompanyIdForTimeEntry</code> method.
     * </c>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForTimeEntry_NotFound() throws Exception {
        assertEquals("Time entry not exists.", -1, persistence.getCompanyIdForTimeEntry(110));
    }

    /**
     * <c>
     * Tests the accuracy of <code>getCompanyIdForExpenseEntry</code> method.
     * </c>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForExpenseEntry() throws Exception {
        // this expense entry is loaded in the init script
        assertEquals("Incorrect company id.", 1, persistence.getCompanyIdForExpenseEntry(0));
    }

    /**
     * <c>
     * Tests the accuracy of <code>getCompanyIdForExpenseEntry</code> method.
     * </c>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForExpenseEntry_NotFound() throws Exception {
        assertEquals("Expense entry not exists.", -1, persistence.getCompanyIdForExpenseEntry(110));
    }

    /**
     * <c>
     * Tests the accuracy of <code>getCompanyIdForUserAccount</code> method.
     * </c>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForUserAccount() throws Exception {
        // this expense entry is loaded in the init script
        assertEquals("Incorrect company id.", 1, persistence.getCompanyIdForUserAccount(400));
    }

    /**
     * <c>
     * Tests the accuracy of <code>getCompanyIdForUserAccount</code> method.
     * </c>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testGetCompanyIdForUserAccount_NotFound() throws Exception {
        assertEquals("User account not exists.", -1, persistence.getCompanyIdForUserAccount(110));
    }

    /**
     * <c>
     * Tests the accuracy of <code>existClientWithNameForCompany</code> method.
     * </c>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testExistClientWithNameForCompany_IsNew() throws Exception {
        Client client = Helper.createClient(10);
        client.setName("kr");
        client.setCompanyId(1);

        assertFalse("Client should not exist", persistence.existClientWithNameForCompany(client, true));

        // 2nd case, name exists
        persistence.addClient(client);
        assertTrue("Client should exist", persistence.existClientWithNameForCompany(client, true));
    }

    /**
     * <c>
     * Tests the accuracy of <code>existClientWithNameForCompany</code> method.
     * </c>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testExistClientWithNameForCompany_IsNotNew() throws Exception {
        Client client = Helper.createClient(10);
        client.setName("kr");
        client.setCompanyId(1);

        assertFalse("Client should not exist", persistence.existClientWithNameForCompany(client, false));

        // 2nd case, name should not exists
        persistence.addClient(client);
        assertFalse("Client shouldn't exist", persistence.existClientWithNameForCompany(client, false));
    }
}
