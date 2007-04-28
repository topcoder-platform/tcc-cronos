/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.project.ConfigurationException;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.MockAuditManager;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.TestHelper;
import com.topcoder.timetracker.project.UnrecognizedEntityException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbProjectManagerDAO.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbProjectManagerDAOTests extends TestCase {
    /**
     * <p>
     * The DbProjectManagerDAO instance for testing.
     * </p>
     */
    private DbProjectManagerDAO managerDao;

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory dbFactory;

    /**
     * <p>
     * The AuditManager instance for testing.
     * </p>
     */
    private AuditManager auditManager;

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

        auditManager = new MockAuditManager();
        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);

        managerDao = new DbProjectManagerDAO("tt_project", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectManagerSearchBundle", auditManager);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();

        dbFactory = null;
        auditManager = null;
        managerDao = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DbProjectManagerDAOTests.class);
    }

    /**
     * <p>
     * Tests ctor DbProjectManagerDAO#DbProjectManagerDAO(String,DBConnectionFactory,
     * String,AuditManager) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbProjectManagerDAO instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbProjectManagerDAO instance.", managerDao);
    }

    /**
     * <p>
     * Tests ctor DbProjectManagerDAO#DbProjectManagerDAO(String,DBConnectionFactory,
     * String,AuditManager) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case that when connName is null and expects success.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullConnName() throws Exception {
        assertNotNull("Failed to create a new DbProjectManagerDAO instance.", new DbProjectManagerDAO(null, dbFactory,
            TestHelper.SEARCH_NAMESPACE, "ProjectManagerSearchBundle", auditManager));
    }

    /**
     * <p>
     * Tests ctor DbProjectManagerDAO#DbProjectManagerDAO(String,DBConnectionFactory,
     * String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connName is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_EmptyConnName() throws Exception {
        try {
            new DbProjectManagerDAO("  ", dbFactory, TestHelper.SEARCH_NAMESPACE, "ProjectManagerSearchBundle",
                auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectManagerDAO#DbProjectManagerDAO(String,DBConnectionFactory,
     * String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connFactory is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullConnFactory() throws Exception {
        try {
            new DbProjectManagerDAO("tt_project", null, TestHelper.SEARCH_NAMESPACE, "ProjectManagerSearchBundle",
                auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectManagerDAO#DbProjectManagerDAO(String,DBConnectionFactory,
     * String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchStrategyNamespace is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullSearchStrategyNamespace() throws Exception {
        try {
            new DbProjectManagerDAO("tt_project", dbFactory, null, "ProjectManagerSearchBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectManagerDAO#DbProjectManagerDAO(String,DBConnectionFactory,
     * String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchStrategyNamespace is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_EmptySearchStrategyNamespace() throws Exception {
        try {
            new DbProjectManagerDAO("tt_project", dbFactory, "   ", "ProjectManagerSearchBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectManagerDAO#DbProjectManagerDAO(String,DBConnectionFactory,
     * String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when auditor is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullAuditor() throws Exception {
        try {
            new DbProjectManagerDAO("tt_project", dbFactory, TestHelper.SEARCH_NAMESPACE, "ProjectManagerSearchBundle",
                null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectManagerDAO#DbProjectManagerDAO(String,DBConnectionFactory,
     * String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the search namespace is unknown and expects for ConfigurationException.
     * </p>
     */
    public void testCtor_UnknownSearchNamespace() {
        try {
            new DbProjectManagerDAO("tt_project", dbFactory, "HelloWorld", "ProjectManagerSearchBundle", auditManager);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#addProjectManagers(ProjectManager[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectManagerDAO#addProjectManagers(ProjectManager[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectManagers() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        managerDao.addProjectManagers(new ProjectManager[] {manager}, true);

        ProjectManager[] projectManagers = managerDao.enumerateProjectManagers();
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
     * Tests DbProjectManagerDAO#addProjectManagers(ProjectManager[],boolean) for failure.
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
            managerDao.addProjectManagers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#addProjectManagers(ProjectManager[],boolean) for failure.
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
            managerDao.addProjectManagers(new ProjectManager[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#addProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some manager has null creation date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectManagers_NullCreationDate() throws Exception {
        ProjectManager pm = TestHelper.createTestingProjectManager("CreationDate");
        try {
            managerDao.addProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#addProjectManagers(ProjectManager[],boolean) for failure.
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
            managerDao.addProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#addProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some manager has null modification date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectManagers_NullModificationDate() throws Exception {
        ProjectManager pm = TestHelper.createTestingProjectManager("ModificationDate");
        try {
            managerDao.addProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#addProjectManagers(ProjectManager[],boolean) for failure.
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
            managerDao.addProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#addProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectManagers_InvalidConnectionName() throws Exception {
        managerDao = new DbProjectManagerDAO("unknown", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectManagerSearchBundle", auditManager);
        ProjectManager manager = TestHelper.createTestingProjectManager(null);

        try {
            managerDao.addProjectManagers(new ProjectManager[] {manager}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#addProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectManagers_EmptyDatabase() throws Exception {
        managerDao = new DbProjectManagerDAO("empty", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectManagerSearchBundle", auditManager);
        ProjectManager manager = TestHelper.createTestingProjectManager(null);

        try {
            managerDao.addProjectManagers(new ProjectManager[] {manager}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#updateProjectManagers(ProjectManager[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectManagerDAO#updateProjectManagers(ProjectManager[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManagers() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        managerDao.addProjectManagers(new ProjectManager[] {manager}, true);

        manager.setCreationUser("topcoder");
        manager.setModificationUser("admin");

        managerDao.updateProjectManagers(new ProjectManager[] {manager}, true);

        ProjectManager[] projectManagers = managerDao.enumerateProjectManagers();
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
     * Tests DbProjectManagerDAO#updateProjectManagers(ProjectManager[],boolean) for failure.
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
            managerDao.updateProjectManagers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#updateProjectManagers(ProjectManager[],boolean) for failure.
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
            managerDao.updateProjectManagers(new ProjectManager[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#updateProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some manager has null creation date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManagers_NullCreationDate() throws Exception {
        ProjectManager pm = TestHelper.createTestingProjectManager("CreationDate");
        try {
            managerDao.updateProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#updateProjectManagers(ProjectManager[],boolean) for failure.
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
            managerDao.updateProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#updateProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some manager has null modification date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManagers_NullModificationDate() throws Exception {
        ProjectManager pm = TestHelper.createTestingProjectManager("ModificationDate");
        try {
            managerDao.updateProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#updateProjectManagers(ProjectManager[],boolean) for failure.
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
            managerDao.updateProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#updateProjectManagers(ProjectManager[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManagers_InvalidConnectionName() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        managerDao.addProjectManagers(new ProjectManager[] {manager}, true);

        managerDao = new DbProjectManagerDAO("unknown", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectManagerSearchBundle", auditManager);
        try {
            managerDao.updateProjectManagers(new ProjectManager[] {manager}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#updateProjectManagers(ProjectManager[],boolean) for failure.
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
        managerDao.addProjectManagers(new ProjectManager[] {manager}, true);

        managerDao = new DbProjectManagerDAO("empty", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectManagerSearchBundle", auditManager);
        try {
            managerDao.updateProjectManagers(new ProjectManager[] {manager}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#updateProjectManagers(ProjectManager[],boolean) for failure.
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
            managerDao.updateProjectManagers(new ProjectManager[] {TestHelper.createTestingProjectManager(null)}, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#removeProjectManagers(long[],long[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectManagerDAO#removeProjectManagers(long[],long[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectManagers() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        managerDao.addProjectManagers(new ProjectManager[] {manager}, true);

        managerDao.removeProjectManagers(new long[] {manager.getProjectId()}, new long[] {manager.getUserId()}, true);

        ProjectManager[] projectManagers = managerDao.enumerateProjectManagers();
        assertEquals("Failed to remove the project managers.", 0, projectManagers.length);
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#removeProjectManagers(long[],long[],boolean) for failure.
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
            managerDao.removeProjectManagers(null, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#removeProjectManagers(long[],long[],boolean) for failure.
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
            managerDao.removeProjectManagers(new long[] {1}, new long[] {-1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#removeProjectManagers(long[],long[],boolean) for failure.
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
            managerDao.removeProjectManagers(new long[] {1}, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#removeProjectManagers(long[],long[],boolean) for failure.
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
            managerDao.removeProjectManagers(new long[] {-1}, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#removeProjectManagers(long[],long[],boolean) for failure.
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
            managerDao.removeProjectManagers(new long[] {1, 2}, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#removeProjectManagers(long[],long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectManagers_InvalidConnectionName() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        managerDao.addProjectManagers(new ProjectManager[] {manager}, true);

        managerDao = new DbProjectManagerDAO("unknown", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectManagerSearchBundle", auditManager);
        try {
            managerDao.removeProjectManagers(new long[] {1}, new long[] {1}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#removeProjectManagers(long[],long[],boolean) for failure.
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
        managerDao.addProjectManagers(new ProjectManager[] {manager}, true);

        managerDao = new DbProjectManagerDAO("empty", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectManagerSearchBundle", auditManager);
        try {
            managerDao.removeProjectManagers(new long[] {1}, new long[] {1}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#searchProjectManagers(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectManagerDAO#searchProjectManagers(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjectManagers() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        managerDao.addProjectManagers(new ProjectManager[] {manager}, true);
        Filter filter = managerDao.getProjectManagerFilterFactory().createProjectIdFilter(1);

        ProjectManager[] projectManagers = managerDao.searchProjectManagers(filter);

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
     * Tests DbProjectManagerDAO#searchProjectManagers(Filter) for failure.
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
            managerDao.searchProjectManagers(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#searchProjectManagers(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchProjectManagers_DataAccessException() {
        Filter filter = new EqualToFilter("unknown", "unknown");
        try {
            managerDao.searchProjectManagers(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#enumerateProjectManagers() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectManagerDAO#enumerateProjectManagers() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjectManagers() throws Exception {
        ProjectManager[] projectManagers = managerDao.enumerateProjectManagers();

        assertEquals("Failed to get the project managers.", 0, projectManagers.length);
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#enumerateProjectManagers() for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjectManagers_InvalidConnectionName() throws Exception {
        managerDao = new DbProjectManagerDAO("unknown", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectManagerSearchBundle", auditManager);
        try {
            managerDao.enumerateProjectManagers();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#enumerateProjectManagers() for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjectManagers_EmptyDatabase() throws Exception {
        managerDao = new DbProjectManagerDAO("empty", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectManagerSearchBundle", auditManager);
        try {
            managerDao.enumerateProjectManagers();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectManagerDAO#getProjectManagerFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectManagerDAO#getProjectManagerFilterFactory() is correct.
     * </p>
     */
    public void testGetProjectManagerFilterFactory() {
        assertNotNull("Failed to get the project manager filter factory.", managerDao.getProjectManagerFilterFactory());
    }

}