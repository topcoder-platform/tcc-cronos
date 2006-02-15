/*
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.project.InsufficientDataException;
import com.topcoder.timetracker.project.ProjectPersistenceManager;
import com.topcoder.timetracker.project.ProjectUtility;

/**
 * Failure tests for ProjectUtility implementation.
 * 
 * @author dmks
 * @version 1.0
 */
public class ProjectUtilityFailureTest extends TestCase {
    /**
     * The ProjectUtility instance to test against.
     */
    private ProjectUtility utility = null;

    /**
     * Prepares a ProjectUtility for testing. Also prepares the method
     * arguments, the persistence manager and the persistence.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void setUp() throws Exception {
        FailureTestHelper.loadConfig();
        ProjectPersistenceManager manager = new ProjectPersistenceManager(FailureTestHelper.NAMESPACE);
        utility = new ProjectUtility(manager);
    }

    /**
     * Clears all the namespaces.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void tearDown() throws Exception {
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test of constructor with null manager. Expects NullPointerException.
     */
    public void testConstructor_NullManager() {
        try {
            new ProjectUtility(null);
            fail("Creates ProjectUtility with null manager");
        } catch (NullPointerException e) {
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
            utility.addProject(null);
            fail("Adds null project");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addProject method with illegal project. Expects
     * IllegalArgumentException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProject_IllegalProject() throws Exception {
        try {
            utility.addProject(FailureTestHelper.createIllegalProject());
            fail("Adds illegal project");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of addProject method with project with insufficient data. Expects
     * InsufficientDataException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProject_InsufficientProject() throws Exception {
        try {
            utility.addProject(FailureTestHelper.createInsufficientProject());
            fail("Adds project with insufficient data");
        } catch (InsufficientDataException e) {
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
            utility.updateProject(null);
            fail("Updates null project");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of updateProject method with project with insufficient data. Expects
     * InsufficientDataException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateProject_InsufficientProject() throws Exception {
        try {
            utility.updateProject(FailureTestHelper.createInsufficientProject());
            fail("Updates project with insufficient data");
        } catch (InsufficientDataException e) {
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
            utility.assignClient(-1, -1, null);
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
            utility.assignClient(-1, -1, " ");
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
            utility.assignProjectManager(null);
            fail("Assigns null project manager");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of assignProjectManager method with illegal manager. Expects
     * IllegalArgumentException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAssignProjectManager_IllegalManager() throws Exception {
        try {
            utility.assignProjectManager(FailureTestHelper.createIllegalManager());
            fail("Assigns illegal project manager");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of assignProjectManager method with manager with insufficient data.
     * Expects InsufficientDataException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAssignProjectManager_InsufficientManager() throws Exception {
        try {
            utility.assignProjectManager(FailureTestHelper.createInsufficientManager());
            fail("Assigns project manager with insufficient data");
        } catch (InsufficientDataException e) {
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
            utility.addWorker(null);
            fail("Adds null worker");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addWorker method with illegal worker. Expects
     * IllegalArgumentException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddWorker_IllegalWorker() throws Exception {
        try {
            utility.addWorker(FailureTestHelper.createIllegalWorker());
            fail("Adds illegal worker");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of addWorker method with worker with insufficient data. Expects
     * InsufficientDataException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddWorker_InsufficientWorker() throws Exception {
        try {
            utility.addWorker(FailureTestHelper.createInsufficientWorker());
            fail("Adds worker with insufficient data");
        } catch (InsufficientDataException e) {
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
            utility.updateWorker(null);
            fail("Updates null worker");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of updateWorker method with worker with insufficient data. Expects
     * InsufficientDataException.
     * 
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateWorker_InsufficientWorker() throws Exception {
        try {
            utility.updateWorker(FailureTestHelper.createInsufficientWorker());
            fail("Updates worker with insufficient data");
        } catch (InsufficientDataException e) {
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
            utility.addTimeEntry(-1, -1, null);
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
            utility.addTimeEntry(-1, -1, "");
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
            utility.addExpenseEntry(-1, -1, null);
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
            utility.addExpenseEntry(-1, -1, " ");
            fail("Adds expense entry with empty user");
        } catch (IllegalArgumentException e) {
        }
    }
}
