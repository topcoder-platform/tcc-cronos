/*
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.cronos.timetracker.project.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.cronos.timetracker.project.InsufficientDataException;
import com.cronos.timetracker.project.Project;
import com.cronos.timetracker.project.ProjectPersistenceManager;
import com.cronos.timetracker.project.ProjectUtility;
import com.cronos.timetracker.project.ProjectWorker;
import com.cronos.timetracker.project.searchfilters.Filter;

/**
 * Failure tests for ProjectUtility implementation.
 *
 * @author dmks
 * @version 1.0
 * @author kr00tki
 * @version 1.1
 * @author costty000
 * @version 2.0
 */
public class ProjectUtilityFailureTest extends TestCase {
    /**
     * The ProjectUtility instance to test against.
     */
    private ProjectUtility utility = null;

    /**
     * Prepares a ProjectUtility for testing. Also prepares the method arguments, the persistence manager and the
     * persistence.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void setUp() throws Exception {
        FailureTestHelper.loadConfig();
        ProjectPersistenceManager manager = new ProjectPersistenceManager(FailureTestHelper.NAMESPACE);
        utility = new ProjectUtility(manager);
    }

    /**
     * Clears all the namespaces.
     *
     * @throws Exception if any unexpected exception occurs.
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
     * Test of addProject method with null project. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProject_NullProject() throws Exception {
        try {
            utility.addProject(null);
            fail("Adds null project");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addProject method with illegal project. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProject_IllegalProject() throws Exception {
        try {
            utility.addProject(FailureTestHelper.createIllegalProject());
            fail("Adds illegal project");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of addProject method with project with insufficient data. Expects InsufficientDataException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddProject_InsufficientProject() throws Exception {
        try {
            utility.addProject(FailureTestHelper.createInsufficientProject());
            fail("Adds project with insufficient data");
        } catch (InsufficientDataException e) {
        }
    }

    /**
     * Test of updateProject method with null project. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testUpdateProject_NullProject() throws Exception {
        try {
            utility.updateProject(null);
            fail("Updates null project");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of updateProject method with project with insufficient data. Expects InsufficientDataException.
     *
     * @throws Exception if any unexpected exception occurs.
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
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAssignClient_NullUser() throws Exception {
        try {
            utility.assignClient(-1, -1, null);
            fail("Assigns client with null user");
        } catch (NullPointerException e) {
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of assignClient method with empty user. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAssignClient_EmptyUser() throws Exception {
        try {
            utility.assignClient(-1, -1, " ");
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
            utility.assignProjectManager(null);
            fail("Assigns null project manager");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of assignProjectManager method with illegal manager. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAssignProjectManager_IllegalManager() throws Exception {
        try {
            utility.assignProjectManager(FailureTestHelper.createIllegalManager());
            fail("Assigns illegal project manager");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of assignProjectManager method with manager with insufficient data. Expects InsufficientDataException.
     *
     * @throws Exception if any unexpected exception occurs.
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
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddWorker_NullWorker() throws Exception {
        try {
            utility.addWorker(null);
            fail("Adds null worker");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of addWorker method with illegal worker. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddWorker_IllegalWorker() throws Exception {
        try {
            utility.addWorker(FailureTestHelper.createIllegalWorker());
            fail("Adds illegal worker");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of addWorker method with worker with insufficient data. Expects InsufficientDataException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddWorker_InsufficientWorker() throws Exception {
        try {
            utility.addWorker(FailureTestHelper.createInsufficientWorker());
            fail("Adds worker with insufficient data");
        } catch (InsufficientDataException e) {
        }
    }

    /**
     * Test of updateWorker method with null worker. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testUpdateWorker_NullWorker() throws Exception {
        try {
            utility.updateWorker(null);
            fail("Updates null worker");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of updateWorker method with worker with insufficient data. Expects InsufficientDataException.
     *
     * @throws Exception if any unexpected exception occurs.
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
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddTimeEntry_NullUser() throws Exception {
        try {
            utility.addTimeEntry(1, 1, null);
            fail("Adds time entry with null user");
        } catch (NullPointerException e) {
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of addTimeEntry method with empty user. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddTimeEntry_EmptyUser() throws Exception {
        try {
            utility.addTimeEntry(-1, -1, "");
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
            utility.addExpenseEntry(-1, -1, null);
            fail("Adds expense entry with null user");
        } catch (NullPointerException e) {
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of addExpenseEntry method with empty user. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testAddExpenseEntry_EmptyUser() throws Exception {
        try {
            utility.addExpenseEntry(-1, -1, " ");
            fail("Adds expense entry with empty user");
        } catch (IllegalArgumentException e) {
        }
    }

    // since 1.1

    /**
     * Tests the {@link ProjectUtility#searchProjects(Filter)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testSearchProjects_NullFilter() throws Exception {
        try {
            utility.searchProjects(null);
            fail("Null filter, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addProjects(Project[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddProjects_NullArray() throws Exception {
        try {
            utility.addProjects(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addProjects(Project[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddProjects_EmptyArray() throws Exception {
        try {
            utility.addProjects(new Project[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addProjects(Project[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddProjects_ArrayWithNull() throws Exception {
        try {
            utility.addProjects(new Project[] {null}, true);
            fail("Array with null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addProjects(Project[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddProjects_InsufficientData() throws Exception {
        try {
            utility.addProjects(new Project[] {FailureTestHelper.createInsufficientProject()}, true);
            fail("Insufficient project data, IAE expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addProjects(Project[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddProjects_IllegalData() throws Exception {
        try {
            utility.addProjects(new Project[] {FailureTestHelper.createIllegalProject()}, true);
            fail("Too much data, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#removeProjects(int[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveProjects_NullArray() throws Exception {
        try {
            utility.removeProjects(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#removeProjects(int[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveProjects_EmptyArray() throws Exception {
        try {
            utility.removeProjects(new int[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#updateProjects(Project[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateProjects_NullArray() throws Exception {
        try {
            utility.updateProjects(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#updateProjects(Project[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateProjects_EmptyArray() throws Exception {
        try {
            utility.updateProjects(new Project[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#updateProjects(Project[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateProjects_ArrayWithNull() throws Exception {
        try {
            utility.updateProjects(new Project[] {null}, true);
            fail("Array with null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#updateProjects(Project[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateProjects_InsufficientData() throws Exception {
        try {
            utility.updateProjects(new Project[] {FailureTestHelper.createInsufficientProject()}, true);
            fail("Insufficient project data, IAE expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#getProjects(int[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetProjects_NullArray() throws Exception {
        try {
            utility.getProjects(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#getProjects(int[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetProjects_EmptyArray() throws Exception {
        try {
            utility.getProjects(new int[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addWorkers(ProjectWorker[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddWorkers_NullArray() throws Exception {
        try {
            utility.addWorkers(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addWorkers(ProjectWorker[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddWorkers_EmptyArray() throws Exception {
        try {
            utility.addWorkers(new ProjectWorker[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addWorkers(ProjectWorker[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddWorkers_ArrayWithNull() throws Exception {
        try {
            utility.addWorkers(new ProjectWorker[] {null}, true);
            fail("Array with null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addWorkers(ProjectWorker[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddWorkers_InsufficientData() throws Exception {
        try {
            utility.addWorkers(new ProjectWorker[] {FailureTestHelper.createInsufficientWorker()}, true);
            fail("Insufficient worker data, IAE expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addWorkers(ProjectWorker[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddWorkers_IllegalData() throws Exception {
        try {
            utility.addWorkers(new ProjectWorker[] {FailureTestHelper.createIllegalWorker()}, true);
            fail("Illegal worker data, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#removeWorkers(int[], int, boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveWorkers_NullArray() throws Exception {
        utility.addProject(FailureTestHelper.createProject(1, 1));
        try {
            utility.removeWorkers(null, 1, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#removeWorkers(int[], int, boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveWorkers_EmptyArray() throws Exception {
        try {
            utility.removeWorkers(new int[0], 1, true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#updateWorkers(ProjectWorker[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateWorkers_NullArray() throws Exception {
        try {
            utility.updateWorkers(null, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#updateWorkers(ProjectWorker[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateWorkers_EmptyArray() throws Exception {
        try {
            utility.updateWorkers(new ProjectWorker[0], true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#updateWorkers(ProjectWorker[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateWorkers_ArrayWithNull() throws Exception {
        try {
            utility.updateWorkers(new ProjectWorker[] {null}, true);
            fail("Array with null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#updateWorkers(ProjectWorker[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testUpdateWorkers_InsufficientData() throws Exception {
        try {
            utility.addWorkers(new ProjectWorker[] {FailureTestHelper.createInsufficientWorker()}, true);
            fail("Insufficient data, IAE expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#getWorkers(int[], int, boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetWorkers_NullArray() throws Exception {
        try {
            utility.getWorkers(null, 1, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#getWorkers(int[], int, boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testGetWorkers_EmptyArray() throws Exception {
        try {
            utility.getWorkers(new int[0], 1, true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addTimeEntries(int[], int, String, boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddTimeEntries_NullIds() throws Exception {
        try {
            utility.addTimeEntries(null, 1, "kr", true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addTimeEntries(int[], int, String, boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddTimeEntries_NullUser() throws Exception {
        try {
            utility.addTimeEntries(new int[] {500}, 1, null, true);
            fail("Null user, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addTimeEntries(int[], int, String, boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddTimeEntries_EmptyUser() throws Exception {
        try {
            utility.addTimeEntries(new int[] {500}, 1, " ", true);
            fail("Empty user, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#removeTimeEntries(int[], int, boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveTimeEntries_NullArray() throws Exception {
        try {
            utility.removeTimeEntries(null, 1, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#removeTimeEntries(int[], int, boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveTimeEntries_EmptyArray() throws Exception {
        try {
            utility.removeTimeEntries(new int[0], 1, true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addExpenseEntries(int[], int, String, boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddExpenseEntries_NullIds() throws Exception {
        try {
            utility.addExpenseEntries(null, 1, "kr", true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addExpenseEntries(int[], int, String, boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddExpenseEntries_NullUser() throws Exception {
        try {
            utility.addExpenseEntries(new int[] {0}, 1, null, true);
            fail("Null user, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addExpenseEntries(int[], int, String, boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testAddExpenseEntries_EmptyUser() throws Exception {
        try {
            utility.addExpenseEntries(new int[] {0}, 1, " ", true);
            fail("Empty user, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#removeExpenseEntries(int[], int, boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveExpenseEntries_NullIds() throws Exception {
        try {
            utility.removeExpenseEntries(null, 1, true);
            fail("Null array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#removeExpenseEntries(int[], int, boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testRemoveExpenseEntries_EmptyIds() throws Exception {
        try {
            utility.removeExpenseEntries(new int[0], 1, true);
            fail("Empty array, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    // since 2.0
    /**
     * Tests the {@link ProjectUtility#addProjects(Project[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testAddProjects_ManagerFromDifferentCompany() throws Exception {
        Project project = FailureTestHelper.createProject(10, 2);
        project.setManagerId(3);

        try {
            utility.addProjects(new Project[] {project}, true);
            fail("PM is from other company than project.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addProjects(Project[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testAddProjects_ExpenseEntryFromDifferentCompany() throws Exception {
        Project project = FailureTestHelper.createProject(10, 1);
        project.addExpenseEntry(503);
        project.setManagerId(3);

        try {
            utility.addProjects(new Project[] {project}, true);
            fail("Expense is from other company than project.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addProjects(Project[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testAddProjects_TimeEntryFromDifferentCompany() throws Exception {
        Project project = FailureTestHelper.createProject(10, 1);
        project.addTimeEntry(503);
        project.setManagerId(3);

        try {
            utility.addProjects(new Project[] {project}, true);
            fail("Time is from other company than project.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addProjects(Project[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testAddProjects_WorkerFromDifferentCompany() throws Exception {
        Project project = FailureTestHelper.createProject(10, 1);
        project.addWorkerId(503);
        project.setManagerId(3);

        try {
            utility.addProjects(new Project[] {project}, true);
            fail("Worker is from other company than project.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addProject(Project)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testAddProject_ExpenseEntryFromDifferentCompany() throws Exception {
        Project project = FailureTestHelper.createProject(10, 1);
        project.addExpenseEntry(503);
        project.setManagerId(3);

        try {
            utility.addProject(project);
            fail("Expense is from other company than project.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addProject(Project)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testAddProject_TimeEntryFromDifferentCompany() throws Exception {
        Project project = FailureTestHelper.createProject(10, 1);
        project.addTimeEntry(503);
        project.setManagerId(3);

        try {
            utility.addProject(project);
            fail("Time is from other company than project.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#addProject(Project)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testAddProject_WorkerFromDifferentCompany() throws Exception {
        Project project = FailureTestHelper.createProject(10, 1);
        project.addWorkerId(503);
        project.setManagerId(3);

        try {
            utility.addProject(project);
            fail("Worker is from other company than project.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link ProjectUtility#updateProjects(Project[], boolean)} method failure.
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testUpdateProjects_ManagerFromDifferentCompany() throws Exception {
        Project project = FailureTestHelper.createProject(10, 2);
        project.setManagerId(3);
        project.setId(1);
        project.setModificationDate(new Date());
        project.setCreationDate(new Date());

        try {
            utility.updateProjects(new Project[] {project}, true);
            fail("PM is from other company than project.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
}
