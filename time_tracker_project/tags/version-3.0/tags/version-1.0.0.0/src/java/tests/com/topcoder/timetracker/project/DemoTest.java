/*
 * DemoTest.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.math.BigDecimal;

import java.util.List;


/**
 * A simple demonstration of the Time Tracker Project component.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest extends TestCase {
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
     * Prepares the namespace of the configuration file.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfig();
        DBHelper.clearTables();
    }

    /**
     * Clears all the namespaces and tables in the database.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void tearDown() throws Exception {
        DBHelper.clearTables();
        TestHelper.unloadConfig();
    }

    /**
     * Demonstrates the usage of the ProjectUtility and ClientUtility classes, with the ProjectPersistenceManager set
     * in the constructor. Shows the manipulation of clients, projects, project managers, project workers, time
     * entries and expense entries using these utilities.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testDemo() throws Exception {
        //create a ProjectPersistenceManager
        ProjectPersistenceManager pm = new ProjectPersistenceManager(TestHelper.NAMESPACE);

        //create a Project instance using the parameterized constructor
        Project project = TestHelper.createProject(1);

        //using the setters the fields should be initialized; it is not necessary to perform
        //this task in this demo since it is a trivial one, and the demo will get very big
        //create a ProjectUtility
        ProjectUtility pu = new ProjectUtility(pm);

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

        //create a ClientUtility instance
        ClientUtility cu = new ClientUtility(pm);

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

        //close the connection after use
        pm.getPersistence().closeConnection();
    }
}
