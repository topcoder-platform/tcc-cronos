/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.timetracker.project.persistence.DBHelper;
import com.topcoder.timetracker.project.searchfilters.BinaryOperation;
import com.topcoder.timetracker.project.searchfilters.BinaryOperationFilter;
import com.topcoder.timetracker.project.searchfilters.CompareOperation;
import com.topcoder.timetracker.project.searchfilters.Filter;
import com.topcoder.timetracker.project.searchfilters.MultiValueFilter;
import com.topcoder.timetracker.project.searchfilters.NotFilter;
import com.topcoder.timetracker.project.searchfilters.ValueFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.math.BigDecimal;

import java.util.List;


/**
 * A simple demonstration of the Time Tracker Project component. Includes version 1.0 and version 1.1.
 *
 * @author colau
 * @author TCSDEVELOPER
 * @version 1.1
 *
 * @since 1.0
 */
public class DemoTest extends TestCase {
    /**
     * The ProjectPersistenceManager instance for the demo.
     */
    private ProjectPersistenceManager pm = null;

    /**
     * The ProjectUtility instance to do project-related operations.
     */
    private ProjectUtility pu = null;

    /**
     * The ClientUtility instance to do client-related operations.
     */
    private ClientUtility cu = null;

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(DemoTest.class);

        return suite;
    }

    /**
     * Prepares the namespace of the configuration file. Also prepares the ProjectPersistenceManager and utility
     * instances for the demo.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfig();
        DBHelper.clearTables();

        pm = new ProjectPersistenceManager(TestHelper.NAMESPACE);
        pu = new ProjectUtility(pm);
        cu = new ClientUtility(pm);
    }

    /**
     * Clears all the namespaces and tables in the database.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void tearDown() throws Exception {
        pm.getPersistence().closeConnection();
        DBHelper.clearTables();
        TestHelper.unloadConfig();
    }

    /**
     * Demonstrates the basic usage of the ProjectUtility and ClientUtility classes, with the ProjectPersistenceManager
     * set in the constructor. Shows the manipulation of clients, projects, project managers, project workers, time
     * entries and expense entries using these utilities.
     *
     * @throws Exception if any unexpected exception occurs.
     *
     * @since 1.0
     */
    public void testBasic() throws Exception {
        //create a Project instance using the parameterized constructor
        Project project = TestHelper.createProject(1);

        //add the project to the database
        pu.addProject(project);

        //create a ProjectManager instance
        ProjectManager projectManager = TestHelper.createManager();

        projectManager.setManagerId(300);
        projectManager.setProject(project);

        //assign the projectManager to the project
        pu.assignProjectManager(projectManager);

        //change the description of the project
        project.setDescription("Renault project");

        //update the project
        pu.updateProject(project);

        //query the project manager of the project
        ProjectManager pm1 = pu.getProjectManager(project.getId());

        //create a ProjectWorker
        ProjectWorker worker1 = TestHelper.createWorker();

        worker1.setWorkerId(300);
        worker1.setProject(project);

        //add the worker to the project(in the database)
        pu.addWorker(worker1);

        //create a second ProjectWorker
        ProjectWorker worker2 = TestHelper.createWorker();

        worker2.setWorkerId(400);
        worker2.setProject(project);

        //add the worker to the project(in the database)
        pu.addWorker(worker2);

        //update a worker
        double payRate = 100;

        worker2.setPayRate(new BigDecimal(payRate));
        pu.updateWorker(worker2);

        //get a worker from the project
        ProjectWorker worker3 = pu.getWorker(300, 1);

        //get all the workers from the project
        List l = pu.getWorkers(1);

        //add an expense entry to the project
        pu.addExpenseEntry(0, 1, "John");

        //get all the expense entries from a project
        List expenses = pu.getExpenseEntries(1);

        //add a time entry to the project
        pu.addTimeEntry(1, 1, "John");

        //get all the time entries from a project
        List times = pu.getTimeEntries(1);

        //create a client using the parameterized constructor
        Client client = TestHelper.createClient(5);

        //using setters assume that the fields are initialized
        //add the client to the database
        cu.addClient(client);

        //retrieve the projects of a client
        List proj = cu.getAllClientProjects(client.getId());

        //create another project
        Project project1 = TestHelper.createProject(2);

        //assume that the fields are initialized using the setters
        //add the project to the datatbase
        pu.addProject(project1);

        //assign a client to project1
        pu.assignClient(2, 5, "Tim");

        //query the client of a project
        Client client1 = pu.getProjectClient(project1.getId());

        //remove a worker
        pu.removeWorker(300, 1);

        //remove all workers from a project
        pu.removeWorkers(1);

        //remove a time entry from a project
        pu.removeTimeEntry(1, 1);

        //remove an expense entry from a project
        pu.removeExpenseEntry(0, 1);

        //remove the project manager from a project
        pu.removeProjectManager(300, 1);

        //remove a client
        cu.removeClient(5);

        //remove all the clients
        cu.removeAllClients();
    }

    /**
     * Demonstrates the batch operations for projects and clients. Adds and removes the projects, clients and workers
     * with an array argument. The atomicity can be specified for those batch operations.
     *
     * @throws Exception if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testBatch() throws Exception {
        // create an array of projects
        Project[] projects = TestHelper.createProjects();

        // add new projects to the database, atomic mode
        pu.addProjects(projects, true);

        // create an array of project ids
        int[] projectIds = new int[projects.length];

        for (int i = 0; i < projects.length; i++) {
            projectIds[i] = projects[i].getId();
        }

        // remove projects from the database, non-atomic mode
        pu.removeProjects(projectIds, false);

        // create an array of clients
        Client[] clients = TestHelper.createClients();

        // add new clients to the database, atomic mode
        cu.addClients(clients, true);

        // create an array of client ids
        int[] clientIds = new int[clients.length];

        for (int i = 0; i < clients.length; i++) {
            clientIds[i] = clients[i].getId();
        }

        // remove clients from the database, non-atomic mode
        cu.removeClients(clientIds, false);

        // create an array of workers
        ProjectWorker[] workers = TestHelper.createWorkers();

        // assign workers to a project
        Project project = TestHelper.createProject(1);

        for (int i = 0; i < workers.length; i++) {
            workers[i].setWorkerId(400 + i);
            workers[i].setProject(project);
        }

        // add the project to the database
        pu.addProject(project);

        // add new workers to the database, atomic mode
        pu.addWorkers(workers, true);

        // create an array of worker ids
        int[] workerIds = new int[workers.length];

        for (int i = 0; i < workers.length; i++) {
            workerIds[i] = workers[i].getWorkerId();
        }

        // remove workers from the database, non-atomic mode
        pu.removeWorkers(workerIds, project.getId(), false);
    }

    /**
     * Demonstrates the search functionality with search filter. It can be used to search projects and clients based on
     * the matching criteria specified in the search filter.
     *
     * @throws Exception if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testSearch() throws Exception {
        // create a search filter, filtering the values in the specified column
        // (they should be equal to the given value)
        Filter equalFilter = new ValueFilter(CompareOperation.EQUAL, "Creation User", "creationUser");

        // create a search filter, filtering the values in the specified column
        // (they should match a given pattern)
        Filter likeFilter = new ValueFilter(CompareOperation.LIKE, "Name", "%Some pattern%");

        // create a search filter, filtering the values in the specified column
        // (they should be one of the values)
        Filter multiValueFilter = new MultiValueFilter("Name", new Object[] {"name1", "name2"});

        // create a search filter combining two filters using "OR" operation
        Filter orFilter = new BinaryOperationFilter(BinaryOperation.OR, equalFilter, likeFilter);

        // create a search filter combining two filters using "AND" operation
        Filter andFilter = new BinaryOperationFilter(BinaryOperation.AND, equalFilter, likeFilter);

        // create a search filter negating the given filter
        Filter notAndFilter = new NotFilter(andFilter);

        // search for projects using the filters
        Project[] projects;

        projects = pu.searchProjects(equalFilter);
        projects = pu.searchProjects(likeFilter);
        projects = pu.searchProjects(multiValueFilter);
        projects = pu.searchProjects(orFilter);
        projects = pu.searchProjects(andFilter);
        projects = pu.searchProjects(notAndFilter);

        // search for clients using the filters
        Client[] clients;

        clients = cu.searchClients(equalFilter);
        clients = cu.searchClients(likeFilter);
        clients = cu.searchClients(multiValueFilter);
        clients = cu.searchClients(orFilter);
        clients = cu.searchClients(andFilter);
        clients = cu.searchClients(notAndFilter);
    }
}
