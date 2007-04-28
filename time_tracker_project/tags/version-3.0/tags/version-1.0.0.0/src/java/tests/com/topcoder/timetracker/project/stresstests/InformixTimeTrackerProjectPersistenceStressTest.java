/*
 * InformixTimeTrackerProjectPersistenceStressTest.java    Created on 2005-7-16
 *
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 * 
 * @author qiucx0161
 *
 * @version 1.0
 */
package com.topcoder.timetracker.project.stresstests;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import com.topcoder.timetracker.project.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.persistence.InformixTimeTrackerProjectPersistence;
import com.topcoder.timetracker.project.persistence.TimeTrackerProjectPersistence;

import junit.framework.TestCase;


/**
 * Test <code>InformixTimeTrackerProjectPersistenceStressTest.java</code> class.
 */
public class InformixTimeTrackerProjectPersistenceStressTest extends TestCase {
    
    /**
     * The namespace of <code>DBConnectionFactory</code>.
     */
    private static final String DB_NS = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
    
    /**
     * <p>
     * Represents an instance of InformixTimeTrackerProjectPersistence to be used for testing.
     * </p>
     */
    private TimeTrackerProjectPersistence persistence = null;

    /**
     * Setup the test environment, it will remove the namespace first of <code>DBConnectionFactory</code>,
     * and load it. Next, the <code>InformixTimeTrackerProjectPersistence</code>instance will be initialized.
     * And all the tables in db will be clear.
     */
    protected void setUp() throws Exception {
        
        StressHelper.loadAllConfiguration();
        persistence = new InformixTimeTrackerProjectPersistence(DB_NS);
        
        StressHelper.clearTables();
    }

    /**
     * Remove all the namespaces and clear all the tables in db.
     */
    protected void tearDown() throws Exception {
        StressHelper.clearTables();
        StressHelper.releaseNamespaces();
        
        
    }

    /**
     * Update the client in the db. First, it will create 3000 Clients and put them in memory,
     * next, insert them into db via <code>addClient</code>, and update all the clients in memory,
     * update the clients in db at last.
     * @throws Exception to JUnit.
     */
    public void testClientManipulate() throws Exception {
        
        // Create 3000 Clients and put them in set.
        Collection clients = new HashSet();
        for (int i = 1; i < 3001; ++i) {
            clients.add(StressHelper.createClient(i));
        }
        
        // Add 3000 clients.
        long startCreateClient = System.currentTimeMillis();
        for (Iterator it = clients.iterator(); it.hasNext();) {
            persistence.addClient((Client) it.next());
        }
        long endCreate = System.currentTimeMillis() - startCreateClient;
        System.out.println("Create 3000 client takes " + endCreate + " million seconds.");
        
        assertTrue("The number of created clients is not correct.", persistence.getAllClients().size() == 3000);
        
        // update the properties of all the client
        for (Iterator it = clients.iterator(); it.hasNext();) {
            Client c = (Client) it.next();
            c.setName("name" + c.getId());
            c.setCreationDate(c.getCreationDate());
            c.setCreationUser(c.getCreationUser());
            c.setModificationDate(c.getModificationDate());
            c.setModificationUser(c.getModificationUser());
        }
        
        // Update all the clients
        long startUpdate = System.currentTimeMillis();
        for (Iterator it = clients.iterator(); it.hasNext();) {
            persistence.updateClient((Client) it.next());
        }
        long endUpdate = System.currentTimeMillis() - startUpdate;
        System.out.println("Update 3000 client takes " + endUpdate + " million seconds.");
        
        // Remove 1000 clients.
        long startRemove = System.currentTimeMillis();
        for (int i = 1; i < 1001; ++i) {
            persistence.removeClient(i);
        }
        long endRemove = System.currentTimeMillis() - startRemove;
        System.out.println("Remove 1000 client takes " + endRemove + " million seconds.");
        
        assertTrue("1000 clients should be removed.", persistence.getAllClients().size() == 2000);
        
        // Remove all the rest clients
        long startClear = System.currentTimeMillis();
        persistence.removeAllClients();
        long endClear = System.currentTimeMillis() - startClear;
        System.out.println("Clear 2000 client takes " + endClear + " million seconds.");
        
        assertTrue("All clients should be clear.", persistence.getAllClients().size() == 0);
        
        // Clear the clients in memory.
        clients.clear();
    }
    
    /**
     * Update the project in the db. First, it will create 3000 projects and put them in memory,
     * next, insert them into db via <code>addProject</code>, and update all the projects in memory,
     * update the projects in db at last.
     * @throws Exception to JUnit.
     */
    public void testProjectManipulate() throws Exception {
        // Create 3000 Projects and put them in set.
        Collection projects = new HashSet();
        for (int i = 1; i < 3001; ++i) {
            projects.add(StressHelper.createProject(i));
        }
        
        // Add 3000 projects.
        long startCreateProject = System.currentTimeMillis();
        for (Iterator it = projects.iterator(); it.hasNext();) {
            persistence.addProject((Project) it.next());
        }
        long endCreate = System.currentTimeMillis() - startCreateProject;
        System.out.println("Create 3000 project takes " + endCreate + " million seconds.");
        
        assertTrue("The number of created projects is not correct.", persistence.getAllProjects().size() == 3000);
        
        // update the properties of all the project
        for (Iterator it = projects.iterator(); it.hasNext();) {
            Project p = (Project) it.next();
            p.setName("name" + p.getId());
            p.setStartDate(p.getStartDate());
            p.setEndDate(p.getEndDate());
            p.setCreationDate(p.getCreationDate());
            p.setCreationUser(p.getCreationUser());
            p.setModificationDate(p.getModificationDate());
            p.setModificationUser(p.getModificationUser());
        }
        
        // Update all the projects
        long startUpdate = System.currentTimeMillis();
        for (Iterator it = projects.iterator(); it.hasNext();) {
            persistence.updateProject((Project) it.next());
        }
        long endUpdate = System.currentTimeMillis() - startUpdate;
        System.out.println("Update 3000 project takes " + endUpdate + " million seconds.");
        
        // Remove 1000 projects.
        long startRemove = System.currentTimeMillis();
        for (int i = 1; i <1001; ++i) {
            persistence.removeProject(i);
        }
        long endRemove = System.currentTimeMillis() - startRemove;
        System.out.println("Remove 1000 project takes " + endRemove + " million seconds.");
        
        assertTrue("All projects should be clear.", persistence.getAllProjects().size() == 2000);
        
        // Clear all the rest projects
        long startClear = System.currentTimeMillis();
        persistence.removeAllProjects();
        long endClear = System.currentTimeMillis() - startClear;
        System.out.println("Clear 2000 project takes " + endClear + " million seconds.");
        
        assertTrue("All projects should be clear.", persistence.getAllProjects().size() == 0);
		projects.clear();
    }
    
    
    /**
     * Update the projectWorker in the db. First, it will create 3000 projectWorkers and put them in memory,
     * next, insert them into db via <code>addProjectWorker</code>, and update all the projectWorkers in memory,
     * update the projectWorkers in db at last.
     * @throws Exception to JUnit.
     */
    public void testProjectWorkerManipulate() throws Exception {
        
        // Insert a project into db.
        Project pro = StressHelper.createProject(999999);
        persistence.addProject(pro);
        
        // Create 1000 ProjectWorkers and put them in set.
        Collection projectWorkers = new HashSet();
        for (int i = 1; i < 1001; ++i) {
            ProjectWorker worker = StressHelper.createProjectWorker(i);
            worker.setProject(pro);
            projectWorkers.add(worker);
        }
        
        // Add 1000 projectWorkers.
        long startCreateProjectWorker = System.currentTimeMillis();
        for (Iterator it = projectWorkers.iterator(); it.hasNext();) {
            persistence.addWorker((ProjectWorker) it.next());
        }
        long endCreate = System.currentTimeMillis() - startCreateProjectWorker;
        System.out.println("Create 1000 projectWorker takes " + endCreate + " million seconds.");
        
        assertTrue("The number of created clients is not correct.",
                persistence.getWorkers(999999).size() == 1000);
        
        // update the properties of all the projectWorker
        for (Iterator it = projectWorkers.iterator(); it.hasNext();) {
            ProjectWorker p = (ProjectWorker) it.next();
            p.setStartDate(p.getStartDate());
            p.setEndDate(p.getEndDate());
            p.setCreationDate(p.getCreationDate());
            p.setCreationUser(p.getCreationUser());
            p.setModificationDate(p.getModificationDate());
            p.setModificationUser(p.getModificationUser());
        }
        
        // Update all the projectWorkers
        long startUpdate = System.currentTimeMillis();
        for (Iterator it = projectWorkers.iterator(); it.hasNext();) {
            persistence.updateWorker((ProjectWorker) it.next());
        }
        long endUpdate = System.currentTimeMillis() - startUpdate;
        System.out.println("Update 1000 projectWorker takes " + endUpdate + " million seconds.");
        
        // Remove 1000 the projectWorkers
        long startRemove = System.currentTimeMillis();
        for (int i = 1; i < 501; ++i) {
            persistence.removeWorker(i, 999999);
        }
        long endRemove = System.currentTimeMillis() - startRemove;
        System.out.println("Remove 500 projectWorker takes " + endRemove + " million seconds.");
        
        assertTrue("All projects should be clear.", persistence.getWorkers(999999).size() == 500);
        
        // Clear all the rest projectWorkers
        long startClear = System.currentTimeMillis();
        persistence.removeWorkers(999999);
        long endClear = System.currentTimeMillis() - startClear;
        System.out.println("Clear 500 projectWorker takes " + endClear + " million seconds.");
        
        assertTrue("All projects should be clear.", persistence.getWorkers(999999).size() == 0);
        persistence.removeProject(999999);
    }
    
    /**
     * Manipulate the TimeEntry. First, 3000 time entry entities will be created in db
     * via <code>addTimeEntry</code> and the number will be gotten via <code>getTimeEntries</code>,
     * the gotten number should equal to 3000. And then, all the time entry entities will be
     * removed by <code>removeTimeEntry</code>method.
     *
     * @throws Exception to JUnit.
     */
    public void testTimeEntryManipulate() throws Exception {

        // Create project instance
        Project project = StressHelper.createProject(888888);
        persistence.addProject(project);
        
        // add 3000 time entry into db
        long startAdd = System.currentTimeMillis();
        for (int i = 1; i < 3001; ++i) {
            persistence.addTimeEntry(i, 888888, "user" + i);
        }
        long endAdd = System.currentTimeMillis() - startAdd;
        System.out.println("Create 3000 time entry takes" + endAdd + " million seconds.");
        
        assertTrue("The created time entry number isnot 3000.",
                persistence.getTimeEntries(888888).size() == 3000);
        
        // remove all the time entries.
        long startRemove = System.currentTimeMillis();
        for (int i = 1; i < 3001; ++i) {
            persistence.removeTimeEntry(i, 888888);
        }
        long endRemove = System.currentTimeMillis() - startRemove;
        System.out.println("Remove 3000 time entry takes" + endRemove + " million seconds.");
        assertTrue("All the time entry entities should be removed.",
                persistence.getTimeEntries(888888).size() == 0);
        persistence.removeProject(888888);
    }
    
    /**
     * Manipulate the ExpenseEntry. First, 3000 expense entry entities will be created in db
     * via <code>addExpenseEntry</code> and the number will be gotten via <code>getTimeEntries</code>,
     * the gotten number should equal to 3000. And then, all the expense entry entities will be
     * removed by <code>removeExpenseEntry</code>method.
     *
     * @throws Exception to JUnit.
     */
    public void testExpenseEntryManipulate() throws Exception {

        // Create project instance
        Project project = StressHelper.createProject(888888);
        persistence.addProject(project);
        
        // add 3000 expense entry into db
        long startAdd = System.currentTimeMillis();
        for (int i = 1; i < 3001; ++i) {
            persistence.addExpenseEntry(i, 888888, "user" + i);
        }
        long endAdd = System.currentTimeMillis() - startAdd;
        System.out.println("Create 3000 expense entry takes" + endAdd + " million seconds.");
        
        assertTrue("The created expense entry number isnot 3000.",
                persistence.getExpenseEntries(888888).size() == 3000);
        
        // remove all the expense entries.
        long startRemove = System.currentTimeMillis();
        for (int i = 1; i < 3001; ++i) {
            persistence.removeExpenseEntry(i, 888888);
        }
        long endRemove = System.currentTimeMillis() - startRemove;
        System.out.println("Remove 3000 expense entry takes" + endRemove + " million seconds.");
        assertTrue("All the expense entry entities should be removed.",
                persistence.getExpenseEntries(888888).size() == 0);
        persistence.removeProject(888888);
    }
    
    /**
     * The <code>Project</code>s will be assigned to <code>Client</code> via <code>addProjectToClient</code>
     * method, and then, remove all the project via <code>removeProjectFromClient</code>method.
     * 
     * @throws Exception
     */
    public void testProjectToClientManipulate() throws Exception {
        // Create a client
        Client client = StressHelper.createClient(999999);
        persistence.addClient(client);
        
        // Create 3000 Projects and put them into db.
        Collection projects = new HashSet();
        for (int i = 1; i < 3001; ++i) {
            projects.add(StressHelper.createProject(i));
        }
        System.out.println("Create 3000 projects successfully.");
        
        // Assign 3000 project to client
        long startAssign = System.currentTimeMillis();
        for (Iterator iter = projects.iterator(); iter.hasNext();) {
            persistence.addProjectToClient(999999, (Project) iter.next(), "user");
        }
        long endAssign = System.currentTimeMillis() - startAssign;
        System.out.println("Assign 3000 projects takes" + endAssign + " million seconds.");
        
        assertTrue("The number of projects owned by client isnot 3000.",
                persistence.getAllClientProjects(999999).size() == 3000);
        
        // Remove all the projects from client
        long startRemove = System.currentTimeMillis();
        for (int i = 1; i < 3001; ++i) {
            persistence.removeProjectFromClient(999999, i);
        }
        long endRemove = System.currentTimeMillis() - startRemove;
        System.out.println("Remove 3000 projects takes" + endRemove + " million seconds.");
        
        assertTrue("All the projects of client are removed.",
                persistence.getAllClientProjects(999999).size() == 0);
        
        persistence.removeProject(999999);
    }


}
