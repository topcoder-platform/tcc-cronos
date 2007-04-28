/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.project.db.DbProjectManagerDAO;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for ProjectManagerUtilityImpl.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ProjectManagerUtilityImplTests extends TestCase {
    /**
     * <p>
     * The ProjectManagerUtilityImpl instance for testing.
     * </p>
     */
    private ProjectManagerUtilityImpl impl;

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory connFactory;

    /**
     * <p>
     * The AuditManager instance for testing.
     * </p>
     */
    private AuditManager auditor;

    /**
     * <p>
     * The ProjectManagerDAO instance for testing.
     * </p>
     */
    private ProjectManagerDAO dao;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.SEARCH_CONFIG_FILE);
        TestHelper.setUpDataBase();

        connFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);
        auditor = new MockAuditManager();
        dao = new DbProjectManagerDAO("tt_project", connFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectManagerSearchBundle", auditor);

        impl = new ProjectManagerUtilityImpl(dao);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        impl = null;
        dao = null;
        auditor = null;
        connFactory = null;

        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ProjectManagerUtilityImplTests.class);
    }

    /**
     * <p>
     * Tests ctor ProjectManagerUtilityImpl#ProjectManagerUtilityImpl(ProjectManagerDAO) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created ProjectManagerUtilityImpl instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new ProjectManagerUtilityImpl instance.", impl);
    }

    /**
     * <p>
     * Tests ctor ProjectManagerUtilityImpl#ProjectManagerUtilityImpl(ProjectManagerDAO) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when managerDao is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullManagerDao() {
        try {
            new ProjectManagerUtilityImpl(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#addProjectManager(ProjectManager,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilityImpl#addProjectManager(ProjectManager,boolean) is correct.
     * </p>
     * @throws DataAccessException to JUnit
     */
    public void testAddProjectManager() throws DataAccessException {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        impl.addProjectManager(manager, true);

        ProjectManager[] projectManagers = impl.enumerateProjectManagers();
        assertEquals("Failed to add the project managers.", 1, projectManagers.length);
        assertEquals("Failed to add the project managers.", manager.getUserId(), projectManagers[0].getId());
        assertEquals("Failed to add the project managers.", manager.getProjectId(), projectManagers[0].getProjectId());
        assertEquals("Failed to add the project managers.", manager.getUserId(), projectManagers[0].getUserId());
        assertEquals("Failed to add the project managers.", manager.getCreationUser(),
            projectManagers[0].getCreationUser());
        assertEquals("Failed to add the project managers.", manager.getModificationUser(),
            projectManagers[0].getModificationUser());
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#addProjectManager(ProjectManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when manager is null and expects IllegalArgumentException.
     * </p>
     * @throws DataAccessException to JUnit
     */
    public void testAddProjectManager_NullManager() throws DataAccessException {
        try {
            impl.addProjectManager(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#addProjectManager(ProjectManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddProjectManager_DataAccessException() throws Exception {
        dao = new DbProjectManagerDAO("empty", connFactory, TestHelper.SEARCH_NAMESPACE, "ProjectManagerSearchBundle",
            auditor);
        impl = new ProjectManagerUtilityImpl(dao);
        ProjectManager manager = TestHelper.createTestingProjectManager(null);

        try {
            impl.addProjectManager(manager, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#updateProjectManager(ProjectManager,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilityImpl#updateProjectManager(ProjectManager,boolean) is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManager() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        impl.addProjectManager(manager, true);

        manager.setCreationUser("topcoder");
        manager.setModificationUser("admin");

        impl.updateProjectManager(manager, true);

        ProjectManager[] projectManagers = impl.enumerateProjectManagers();
        assertEquals("Failed to update the project managers.", 1, projectManagers.length);
        assertEquals("Failed to update the project managers.", manager.getUserId(), projectManagers[0].getId());
        assertEquals("Failed to update the project managers.", manager.getProjectId(),
            projectManagers[0].getProjectId());
        assertEquals("Failed to update the project managers.", manager.getUserId(), projectManagers[0].getUserId());
        assertEquals("Failed to update the project managers.", manager.getCreationUser(),
            projectManagers[0].getCreationUser());
        assertEquals("Failed to update the project managers.", manager.getModificationUser(),
            projectManagers[0].getModificationUser());
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#updateProjectManager(ProjectManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when manager is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManager_NullManager() throws Exception {
        try {
            impl.updateProjectManager(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#updateProjectManager(ProjectManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManager_DataAccessException() throws Exception {
        dao = new DbProjectManagerDAO("empty", connFactory, TestHelper.SEARCH_NAMESPACE, "ProjectManagerSearchBundle",
            auditor);
        impl = new ProjectManagerUtilityImpl(dao);
        ProjectManager manager = TestHelper.createTestingProjectManager(null);

        try {
            impl.updateProjectManager(manager, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#removeProjectManager(long,long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilityImpl#removeProjectManager(long,long,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectManager() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        impl.addProjectManager(manager, true);

        impl.removeProjectManager(manager.getProjectId(), manager.getUserId(), true);

        ProjectManager[] projectManagers = impl.enumerateProjectManagers();
        assertEquals("Failed to remove the project managers.", 0, projectManagers.length);
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#addProjectManagers(ProjectManager[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilityImpl#addProjectManagers(ProjectManager[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectManagers() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        impl.addProjectManagers(new ProjectManager[] {manager}, true);

        ProjectManager[] projectManagers = impl.enumerateProjectManagers();
        assertEquals("Failed to add the project managers.", 1, projectManagers.length);
        assertEquals("Failed to add the project managers.", manager.getUserId(), projectManagers[0].getId());
        assertEquals("Failed to add the project managers.", manager.getProjectId(), projectManagers[0].getProjectId());
        assertEquals("Failed to add the project managers.", manager.getUserId(), projectManagers[0].getUserId());
        assertEquals("Failed to add the project managers.", manager.getCreationUser(),
            projectManagers[0].getCreationUser());
        assertEquals("Failed to add the project managers.", manager.getModificationUser(),
            projectManagers[0].getModificationUser());
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#addProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when managers is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectManagers_NullManagers() throws Exception {
        try {
            impl.addProjectManagers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#addProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when managers contains null manager and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectManagers_NullInManagers() throws Exception {
        try {
            impl.addProjectManagers(new ProjectManager[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#addProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some manager has null creation user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectManagers_NullCreationUser() throws Exception {
        ProjectManager pm = TestHelper.createTestingProjectManager("CreationUser");
        try {
            impl.addProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#addProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some manager has null modification user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectManagers_NullModificationUser() throws Exception {
        ProjectManager pm = TestHelper.createTestingProjectManager("ModificationUser");
        try {
            impl.addProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#addProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectManagers_EmptyDatabase() throws Exception {
        dao = new DbProjectManagerDAO("empty", connFactory, TestHelper.SEARCH_NAMESPACE, "ProjectManagerSearchBundle",
            auditor);
        impl = new ProjectManagerUtilityImpl(dao);
        ProjectManager manager = TestHelper.createTestingProjectManager(null);

        try {
            impl.addProjectManagers(new ProjectManager[] {manager}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#updateProjectManagers(ProjectManager[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilityImpl#updateProjectManagers(ProjectManager[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManagers() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        impl.addProjectManagers(new ProjectManager[] {manager}, true);

        manager.setCreationUser("topcoder");
        manager.setModificationUser("admin");

        impl.updateProjectManagers(new ProjectManager[] {manager}, true);

        ProjectManager[] projectManagers = impl.enumerateProjectManagers();
        assertEquals("Failed to update the project managers.", 1, projectManagers.length);
        assertEquals("Failed to update the project managers.", manager.getUserId(), projectManagers[0].getId());
        assertEquals("Failed to update the project managers.", manager.getProjectId(),
            projectManagers[0].getProjectId());
        assertEquals("Failed to update the project managers.", manager.getUserId(), projectManagers[0].getUserId());
        assertEquals("Failed to update the project managers.", manager.getCreationUser(),
            projectManagers[0].getCreationUser());
        assertEquals("Failed to update the project managers.", manager.getModificationUser(),
            projectManagers[0].getModificationUser());
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#updateProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when managers is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManagers_NullManagers() throws Exception {
        try {
            impl.updateProjectManagers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#updateProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when managers contains null manager and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManagers_NullInManagers() throws Exception {
        try {
            impl.updateProjectManagers(new ProjectManager[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#updateProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some manager has null creation user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManagers_NullCreationUser() throws Exception {
        ProjectManager pm = TestHelper.createTestingProjectManager("CreationUser");
        try {
            impl.updateProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#updateProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some manager has null modification user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManagers_NullModificationUser() throws Exception {
        ProjectManager pm = TestHelper.createTestingProjectManager("ModificationUser");
        try {
            impl.updateProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#updateProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManagers_EmptyDatabase() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        impl.addProjectManagers(new ProjectManager[] {manager}, true);

        dao = new DbProjectManagerDAO("empty", connFactory, TestHelper.SEARCH_NAMESPACE, "ProjectManagerSearchBundle",
            auditor);
        impl = new ProjectManagerUtilityImpl(dao);
        try {
            impl.updateProjectManagers(new ProjectManager[] {manager}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#updateProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManagers_UnrecognizedEntityException() throws Exception {
        try {
            impl.updateProjectManagers(new ProjectManager[] {TestHelper.createTestingProjectManager(null)}, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#removeProjectManagers(long[],long[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilityImpl#removeProjectManagers(long[],long[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectManagers() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        impl.addProjectManagers(new ProjectManager[] {manager}, true);

        impl.removeProjectManagers(new long[] {manager.getProjectId()}, new long[] {manager.getUserId()}, true);

        ProjectManager[] projectManagers = impl.enumerateProjectManagers();
        assertEquals("Failed to remove the project managers.", 0, projectManagers.length);
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#removeProjectManagers(long[],long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projectIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectManagers_NullProjectIds() throws Exception {
        try {
            impl.removeProjectManagers(null, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#removeProjectManagers(long[],long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project id is negative and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectManagers_NegativeProjectId() throws Exception {
        try {
            impl.removeProjectManagers(new long[] {1}, new long[] {-1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#removeProjectManagers(long[],long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectManagers_NullUserIds() throws Exception {
        try {
            impl.removeProjectManagers(new long[] {1}, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#removeProjectManagers(long[],long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectManagers_NegativeUserId() throws Exception {
        try {
            impl.removeProjectManagers(new long[] {-1}, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#removeProjectManagers(long[],long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userIds and projectIds don't have same length and expects
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectManagers_NotSameLength() throws Exception {
        try {
            impl.removeProjectManagers(new long[] {1, 2}, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#removeProjectManagers(long[],long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectManagers_EmptyDatabase() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        impl.addProjectManagers(new ProjectManager[] {manager}, true);

        dao = new DbProjectManagerDAO("empty", connFactory, TestHelper.SEARCH_NAMESPACE, "ProjectManagerSearchBundle",
            auditor);
        impl = new ProjectManagerUtilityImpl(dao);
        try {
            impl.removeProjectManagers(new long[] {1}, new long[] {1}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#searchProjectManagers(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilityImpl#searchProjectManagers(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjectManagers() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        impl.addProjectManagers(new ProjectManager[] {manager}, true);
        Filter filter = impl.getProjectManagerFilterFactory().createProjectIdFilter(1);

        ProjectManager[] projectManagers = impl.searchProjectManagers(filter);

        assertEquals("Failed to search the project managers.", 1, projectManagers.length);
        assertEquals("Failed to search the project managers.", manager.getUserId(), projectManagers[0].getId());
        assertEquals("Failed to search the project managers.", manager.getProjectId(),
            projectManagers[0].getProjectId());
        assertEquals("Failed to search the project managers.", manager.getUserId(), projectManagers[0].getUserId());
        assertEquals("Failed to search the project managers.", manager.getCreationUser(),
            projectManagers[0].getCreationUser());
        assertEquals("Failed to search the project managers.", manager.getModificationUser(),
            projectManagers[0].getModificationUser());
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#searchProjectManagers(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjectManagers_NullFilter() throws Exception {
        try {
            impl.searchProjectManagers(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#searchProjectManagers(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchProjectManagers_DataAccessException() {
        Filter filter = new EqualToFilter("unknown", "unknown");
        try {
            impl.searchProjectManagers(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#enumerateProjectManagers() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilityImpl#enumerateProjectManagers() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjectManagers() throws Exception {
        ProjectManager[] projectManagers = impl.enumerateProjectManagers();

        assertEquals("Failed to get the project managers.", 0, projectManagers.length);
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#enumerateProjectManagers() for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjectManagers_EmptyDatabase() throws Exception {
        dao = new DbProjectManagerDAO("empty", connFactory, TestHelper.SEARCH_NAMESPACE, "ProjectManagerSearchBundle",
            auditor);
        impl = new ProjectManagerUtilityImpl(dao);
        try {
            impl.enumerateProjectManagers();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilityImpl#getProjectManagerFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilityImpl#getProjectManagerFilterFactory() is correct.
     * </p>
     */
    public void testGetProjectManagerFilterFactory() {
        assertNotNull("Failed to get the project manager filter factory.", impl.getProjectManagerFilterFactory());
    }

}