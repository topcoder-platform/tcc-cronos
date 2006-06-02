/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project;

import com.cronos.timetracker.project.persistence.SimplePersistence;
import com.cronos.timetracker.project.searchfilters.CompareOperation;
import com.cronos.timetracker.project.searchfilters.Filter;
import com.cronos.timetracker.project.searchfilters.ValueFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit tests for ProjectUtility implementation.
 *
 * <p>
 * Here we assume that the methods return the result of the call to the
 * underlying persistence, and that the PersistenceException is just propagated
 * as usual. For simplicity, tests on method returns and PersistenceException
 * are skipped here.
 * </p>
 *
 * @author colau
 * @author TCSDEVELOPER
 * @author costty000
 * @version 2.0
 *
 * @since 1.0
 */
public class ProjectUtilityTest extends TestCase {
    /**
     * The ProjectUtility instance to test against.
     */
    private ProjectUtility utility = null;

    /**
     * The ProjectPersistenceManager used by the ProjectUtility.
     */
    private ProjectPersistenceManager manager = null;

    /**
     * The underlying persistence used by the ProjectPersistenceManager.
     */
    private SimplePersistence persistence = null;

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
    private boolean atomic = false;

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ProjectUtilityTest.class);

        return suite;
    }

    /**
     * Prepares a ProjectUtility for testing. Also prepares the method
     * arguments, the persistence manager and the persistence.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfig();
        manager = new ProjectPersistenceManager(TestHelper.SIMPLE_NAMESPACE);
        persistence = (SimplePersistence) manager.getPersistence();

        // prepare the single arguments
        project = TestHelper.createProject();

        // from 2.0: add a specific project that will be assigned to the project
        // manager and project worker
        Project assignedProject = TestHelper.createProject(1000);
        persistence.addProject(assignedProject);

        projectManager = TestHelper.createManager();
        // from 2.0: assign a specific project to project manager
        projectManager.setProject(assignedProject);
        projectWorker = TestHelper.createWorker();
        // from 2.0: assign a specific project to worker
        projectWorker.setProject(assignedProject);
        clientId = 100;
        projectId = 200;
        managerId = 300;
        workerId = 400;
        entryId = 500;
        user = "user";
        filter = new ValueFilter(CompareOperation.EQUAL, "name", "value");
        atomic = true;

        // prepare the array arguments
        projects = TestHelper.createProjects();
        workers = TestHelper.createWorkers();
        projectIds = TestHelper.createIds(100);
        workerIds = TestHelper.createIds(200);
        entryIds = TestHelper.createIds(300);

        utility = new ProjectUtility(manager);
    }

    /**
     * Clears all the namespaces.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void tearDown() throws Exception {
        TestHelper.unloadConfig();
    }

    /**
     * Test of constructor with null manager. Expects NullPointerException.
     */
    public void testConstructor_NullManager() {
        try {
            new ProjectUtility(null);
            fail("Creates ProjectUtility with null manager");
        } catch (NullPointerException e) {
            // good
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
            // good
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
            utility.addProject(TestHelper.createIllegalProject());
            fail("Adds illegal project");
        } catch (IllegalArgumentException e) {
            // good
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
            utility.addProject(TestHelper.createInsufficientProject());
            fail("Adds project with insufficient data");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * Test of addProject method with valid project. Verifies if the persistence
     * method was called appropriately by checking the last method being called
     * and the arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddProject_ValidProject() throws Exception {
        utility.addProject(project);

        assertEquals("Fails to call method", "addProject", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", project, persistence
                .getLastProject());
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
            // good
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
            utility.updateProject(TestHelper.createInsufficientProject());
            fail("Updates project with insufficient data");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * Test of updateProject method with valid project. Verifies if the
     * persistence method was called appropriately by checking the last method
     * being called and the arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateProject_ValidProject() throws Exception {
        utility.updateProject(project);

        assertEquals("Fails to call method", "updateProject", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", project, persistence
                .getLastProject());
    }

    /**
     * Test of removeProject method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveProject() throws Exception {
        utility.removeProject(projectId);

        assertEquals("Fails to call method", "removeProject", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
    }

    /**
     * Test of removeAllProjects method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveAllProjects() throws Exception {
        utility.removeAllProjects();

        assertEquals("Fails to call method", "removeAllProjects", persistence
                .getLastMethod());
    }

    /**
     * Test of assignClient method with valid user. Verifies if the persistence
     * method was called appropriately by checking the last method being called
     * and the arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAssignClient_ValidUser() throws Exception {
        utility.assignClient(projectId, clientId, user);

        assertEquals("Fails to call method", "assignClient", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
        assertEquals("Fails to pass arguments", clientId, persistence
                .getLastClientId());
        assertEquals("Fails to pass arguments", user, persistence.getLastUser());
    }

    /**
     * Test of getProjectClient method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetProjectClient() throws Exception {
        utility.getProjectClient(projectId);

        assertEquals("Fails to call method", "getProjectClient", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
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
            // good
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
            utility.assignProjectManager(TestHelper.createIllegalManager());
            fail("Assigns illegal project manager");
        } catch (IllegalArgumentException e) {
            // good
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
            utility
                    .assignProjectManager(TestHelper
                            .createInsufficientManager());
            fail("Assigns project manager with insufficient data");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * Test of assignProjectManager method with valid manager. Verifies if the
     * persistence method was called appropriately by checking the last method
     * being called and the arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAssignProjectManager_ValidManager() throws Exception {
        utility.assignProjectManager(projectManager);

        assertEquals("Fails to call method", "assignProjectManager",
                persistence.getLastMethod());
        assertEquals("Fails to pass arguments", projectManager, persistence
                .getLastManager());
    }

    /**
     * Test of getProjectManager method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetProjectManager() throws Exception {
        utility.getProjectManager(projectId);

        assertEquals("Fails to call method", "getProjectManager", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
    }

    /**
     * Test of removeProjectManager method. Verifies if the persistence method
     * was called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveProjectManager() throws Exception {
        utility.removeProjectManager(managerId, projectId);

        assertEquals("Fails to call method", "removeProjectManager",
                persistence.getLastMethod());
        assertEquals("Fails to pass arguments", managerId, persistence
                .getLastManagerId());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
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
            // good
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
            utility.addWorker(TestHelper.createIllegalWorker());
            fail("Adds illegal worker");
        } catch (IllegalArgumentException e) {
            // good
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
            utility.addWorker(TestHelper.createInsufficientWorker());
            fail("Adds worker with insufficient data");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * Test of addWorker method with valid worker. Verifies if the persistence
     * method was called appropriately by checking the last method being called
     * and the arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddWorker_ValidWorker() throws Exception {
        utility.addWorker(projectWorker);

        assertEquals("Fails to call method", "addWorker", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projectWorker, persistence
                .getLastWorker());
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
            // good
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
            utility.updateWorker(TestHelper.createInsufficientWorker());
            fail("Updates worker with insufficient data");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * Test of updateWorker method with valid worker. Verifies if the
     * persistence method was called appropriately by checking the last method
     * being called and the arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testUpdateWorker_ValidWorker() throws Exception {
        utility.updateWorker(projectWorker);

        assertEquals("Fails to call method", "updateWorker", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projectWorker, persistence
                .getLastWorker());
    }

    /**
     * Test of removeWorker method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveWorker() throws Exception {
        utility.removeWorker(workerId, projectId);

        assertEquals("Fails to call method", "removeWorker", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", workerId, persistence
                .getLastWorkerId());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
    }

    /**
     * Test of removeWorkers method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveWorkers() throws Exception {
        utility.removeWorkers(projectId);

        assertEquals("Fails to call method", "removeWorkers", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
    }

    /**
     * Test of getWorker method. Verifies if the persistence method was called
     * appropriately by checking the last method being called and the arguments
     * passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetWorker() throws Exception {
        utility.getWorker(workerId, projectId);

        assertEquals("Fails to call method", "getWorker", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", workerId, persistence
                .getLastWorkerId());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
    }

    /**
     * Test of getWorkers method. Verifies if the persistence method was called
     * appropriately by checking the last method being called and the arguments
     * passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetWorkers() throws Exception {
        utility.getWorkers(projectId);

        assertEquals("Fails to call method", "getWorkers", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
    }

    /**
     * Test of addTimeEntry method with valid user. Verifies if the persistence
     * method was called appropriately by checking the last method being called
     * and the arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddTimeEntry_ValidUser() throws Exception {
        utility.addTimeEntry(entryId, projectId, user);

        assertEquals("Fails to call method", "addTimeEntry", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", entryId, persistence
                .getLastEntryId());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
        assertEquals("Fails to pass arguments", user, persistence.getLastUser());
    }

    /**
     * Test of removeTimeEntry method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveTimeEntry() throws Exception {
        utility.removeTimeEntry(entryId, projectId);

        assertEquals("Fails to call method", "removeTimeEntry", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", entryId, persistence
                .getLastEntryId());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
    }

    /**
     * Test of getTimeEntries method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetTimeEntries() throws Exception {
        utility.getTimeEntries(projectId);

        assertEquals("Fails to call method", "getTimeEntries", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
    }

    /**
     * Test of addExpenseEntry method with valid user. Verifies if the
     * persistence method was called appropriately by checking the last method
     * being called and the arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testAddExpenseEntry_ValidUser() throws Exception {
        utility.addExpenseEntry(entryId, projectId, user);

        assertEquals("Fails to call method", "addExpenseEntry", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", entryId, persistence
                .getLastEntryId());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
        assertEquals("Fails to pass arguments", user, persistence.getLastUser());
    }

    /**
     * Test of removeExpenseEntry method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testRemoveExpenseEntry() throws Exception {
        utility.removeExpenseEntry(entryId, projectId);

        assertEquals("Fails to call method", "removeExpenseEntry", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", entryId, persistence
                .getLastEntryId());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
    }

    /**
     * Test of getExpenseEntries method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetExpenseEntries() throws Exception {
        utility.getExpenseEntries(projectId);

        assertEquals("Fails to call method", "getExpenseEntries", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
    }

    /**
     * Test of getProject method. Verifies if the persistence method was called
     * appropriately by checking the last method being called and the arguments
     * passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetProject() throws Exception {
        utility.getProject(projectId);

        assertEquals("Fails to call method", "getProject", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
    }

    /**
     * Test of getAllProjects method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public void testGetAllProjects() throws Exception {
        utility.getAllProjects();

        assertEquals("Fails to call method", "getAllProjects", persistence
                .getLastMethod());
    }

    /**
     * Test of getPersistenceManager method. Verifies if it returns the
     * persistence manager set in the constructor.
     */
    public void testGetPersistenceManager() {
        assertEquals("Returns an incorrect persistence manager", manager,
                utility.getPersistenceManager());
    }

    /**
     * Test of searchProjects method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testSearchProjects() throws Exception {
        utility.searchProjects(filter);

        assertEquals("Fails to call method", "searchForProjects", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", filter, persistence
                .getLastFilter());
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
            utility.addProjects(null, atomic);
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
            utility.addProjects(new Project[0], atomic);
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
            utility.addProjects(new Project[] { null }, atomic);
            fail("Adds bad projects containing null element");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addProjects method with projects with insufficient data. Expects
     * InsufficientDataException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddProjects_InsufficientProjects() throws Exception {
        try {
            utility.addProjects(new Project[] { TestHelper
                    .createInsufficientProject() }, atomic);
            fail("Adds projects with insufficient data");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * Test of addProjects method with valid projects. Verifies if the
     * persistence method was called appropriately by checking the last method
     * being called and the arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddProjects_ValidProjects() throws Exception {
        utility.addProjects(projects, atomic);

        assertEquals("Fails to call method", "addProjects", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projects, persistence
                .getLastProjects());
        assertEquals("Fails to pass arguments", atomic, persistence
                .getLastAtomic());
    }

    /**
     * Test of removeProjects method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveProjects() throws Exception {
        utility.removeProjects(projectIds, atomic);

        assertEquals("Fails to call method", "removeProjects", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projectIds, persistence
                .getLastProjectIds());
        assertEquals("Fails to pass arguments", atomic, persistence
                .getLastAtomic());
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
            utility.updateProjects(null, atomic);
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
            utility.updateProjects(new Project[0], atomic);
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
            utility.updateProjects(new Project[] { null }, atomic);
            fail("Updates bad projects containing null element");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of updateProjects method with projects with insufficient data.
     * Expects InsufficientDataException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateProjects_InsufficientProjects() throws Exception {
        try {
            utility.updateProjects(new Project[] { TestHelper
                    .createInsufficientProject() }, atomic);
            fail("Updates projects with insufficient data");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * Test of updateProjects method with valid projects. Verifies if the
     * persistence method was called appropriately by checking the last method
     * being called and the arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateProjects_ValidProjects() throws Exception {
        utility.updateProjects(projects, atomic);

        assertEquals("Fails to call method", "updateProjects", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projects, persistence
                .getLastProjects());
        assertEquals("Fails to pass arguments", atomic, persistence
                .getLastAtomic());
    }

    /**
     * Test of getProjects method. Verifies if the persistence method was called
     * appropriately by checking the last method being called and the arguments
     * passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetProjects() throws Exception {
        utility.getProjects(projectIds, atomic);

        assertEquals("Fails to call method", "getProjects", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", projectIds, persistence
                .getLastProjectIds());
        assertEquals("Fails to pass arguments", atomic, persistence
                .getLastAtomic());
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
            utility.addWorkers(null, atomic);
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
            utility.addWorkers(new ProjectWorker[0], atomic);
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
            utility.addWorkers(new ProjectWorker[] { null }, atomic);
            fail("Adds bad workers containing null element");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of addWorkers method with workers with insufficient data. Expects
     * InsufficientDataException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddWorkers_InsufficientWorkers() throws Exception {
        try {
            utility.addWorkers(new ProjectWorker[] { TestHelper
                    .createInsufficientWorker() }, atomic);
            fail("Adds workers with insufficient data");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * Test of addWorkers method with valid workers. Verifies if the persistence
     * method was called appropriately by checking the last method being called
     * and the arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddWorkers_ValidWorkers() throws Exception {
        Project project = TestHelper.createProject(1);
        utility.addProject(project);
        utility.addWorkers(workers, atomic);

        assertEquals("Fails to call method", "addWorkers", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", workers, persistence
                .getLastWorkers());
        assertEquals("Fails to pass arguments", atomic, persistence
                .getLastAtomic());
    }

    /**
     * Test of removeWorkers method with worker ids. Verifies if the persistence
     * method was called appropriately by checking the last method being called
     * and the arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveWorkers_WorkerIds() throws Exception {
        utility.removeWorkers(workerIds, projectId, atomic);

        assertEquals("Fails to call method", "removeWorkers", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", workerIds, persistence
                .getLastWorkerIds());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
        assertEquals("Fails to pass arguments", atomic, persistence
                .getLastAtomic());
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
            utility.updateWorkers(null, atomic);
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
            utility.updateWorkers(new ProjectWorker[0], atomic);
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
            utility.updateWorkers(new ProjectWorker[] { null }, atomic);
            fail("Updates bad workers containing null element");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of updateWorkers method with workers with insufficient data. Expects
     * InsufficientDataException.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateWorkers_InsufficientWorkers() throws Exception {
        try {
            utility.updateWorkers(new ProjectWorker[] { TestHelper
                    .createInsufficientWorker() }, atomic);
            fail("Updates workers with insufficient data");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * Test of updateWorkers method with valid workers. Verifies if the
     * persistence method was called appropriately by checking the last method
     * being called and the arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testUpdateWorkers_ValidWorkers() throws Exception {
        Project project = TestHelper.createProject(1);
        utility.addProject(project);
        utility.updateWorkers(workers, atomic);

        assertEquals("Fails to call method", "updateWorkers", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", workers, persistence
                .getLastWorkers());
        assertEquals("Fails to pass arguments", atomic, persistence
                .getLastAtomic());
    }

    /**
     * Test of getWorkers method with worker ids. Verifies if the persistence
     * method was called appropriately by checking the last method being called
     * and the arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testGetWorkers_WorkerIds() throws Exception {
        utility.getWorkers(workerIds, projectId, atomic);

        assertEquals("Fails to call method", "getWorkers", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", workerIds, persistence
                .getLastWorkerIds());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
        assertEquals("Fails to pass arguments", atomic, persistence
                .getLastAtomic());
    }

    /**
     * Test of addTimeEntries method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddTimeEntries() throws Exception {
        utility.addTimeEntries(entryIds, projectId, user, atomic);

        assertEquals("Fails to call method", "addTimeEntries", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", entryIds, persistence
                .getLastEntryIds());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
        assertEquals("Fails to pass arguments", user, persistence.getLastUser());
        assertEquals("Fails to pass arguments", atomic, persistence
                .getLastAtomic());
    }

    /**
     * Test of removeTimeEntries method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveTimeEntries() throws Exception {
        utility.removeTimeEntries(entryIds, projectId, atomic);

        assertEquals("Fails to call method", "removeTimeEntries", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", entryIds, persistence
                .getLastEntryIds());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
        assertEquals("Fails to pass arguments", atomic, persistence
                .getLastAtomic());
    }

    /**
     * Test of addExpenseEntries method. Verifies if the persistence method was
     * called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testAddExpenseEntries() throws Exception {
        utility.addExpenseEntries(entryIds, projectId, user, atomic);

        assertEquals("Fails to call method", "addExpenseEntries", persistence
                .getLastMethod());
        assertEquals("Fails to pass arguments", entryIds, persistence
                .getLastEntryIds());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
        assertEquals("Fails to pass arguments", user, persistence.getLastUser());
        assertEquals("Fails to pass arguments", atomic, persistence
                .getLastAtomic());
    }

    /**
     * Test of removeExpenseEntries method. Verifies if the persistence method
     * was called appropriately by checking the last method being called and the
     * arguments passed.
     *
     * @throws Exception
     *             if any unexpected exception occurs.
     *
     * @since 1.1
     */
    public void testRemoveExpenseEntries() throws Exception {
        utility.removeExpenseEntries(entryIds, projectId, atomic);

        assertEquals("Fails to call method", "removeExpenseEntries",
                persistence.getLastMethod());
        assertEquals("Fails to pass arguments", entryIds, persistence
                .getLastEntryIds());
        assertEquals("Fails to pass arguments", projectId, persistence
                .getLastProjectId());
        assertEquals("Fails to pass arguments", atomic, persistence
                .getLastAtomic());
    }

    /**
     * Test to see if project with no company can be saved
     *
     * @throws Exception
     * @since 2.0
     */
    public void testProjectMustHaveCompanyId() throws Exception {
        try {
            Project project = new Project();
            project.setId(2000);
            utility.addProject(project);
            fail("InsufficientDataException expected.");
        } catch (InsufficientDataException ex) {
            // ok
        }
    }
}
