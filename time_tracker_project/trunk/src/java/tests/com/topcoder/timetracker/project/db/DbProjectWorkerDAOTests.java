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
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.TestHelper;
import com.topcoder.timetracker.project.UnrecognizedEntityException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbProjectWorkerDAO.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbProjectWorkerDAOTests extends TestCase {
    /**
     * <p>
     * The DbProjectWorkerDAO instance for testing.
     * </p>
     */
    private DbProjectWorkerDAO workerDao;

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

        workerDao = new DbProjectWorkerDAO("tt_project", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectWorkerSearchBundle", auditManager);
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
        workerDao = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DbProjectWorkerDAOTests.class);
    }

    /**
     * <p>
     * Tests ctor DbProjectWorkerDAO#DbProjectWorkerDAO(String,DBConnectionFactory,
     * String,AuditManager) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbProjectWorkerDAO instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbProjectWorkerDAO instance.", workerDao);
    }

    /**
     * <p>
     * Tests ctor DbProjectWorkerDAO#DbProjectWorkerDAO(String,DBConnectionFactory,
     * String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connName is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullConnName() throws Exception {
        assertNotNull("Failed to create a new DbProjectWorkerDAO instance.", new DbProjectWorkerDAO(null, dbFactory,
            TestHelper.SEARCH_NAMESPACE, "ProjectWorkerSearchBundle", auditManager));
    }

    /**
     * <p>
     * Tests ctor DbProjectWorkerDAO#DbProjectWorkerDAO(String,DBConnectionFactory,
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
            new DbProjectWorkerDAO("  ", dbFactory, TestHelper.SEARCH_NAMESPACE, "ProjectWorkerSearchBundle",
                auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectWorkerDAO#DbProjectWorkerDAO(String,DBConnectionFactory,
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
            new DbProjectWorkerDAO("tt_project", null, TestHelper.SEARCH_NAMESPACE, "ProjectWorkerSearchBundle",
                auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectWorkerDAO#DbProjectWorkerDAO(String,DBConnectionFactory,
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
            new DbProjectWorkerDAO("tt_project", dbFactory, null, "ProjectWorkerSearchBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectWorkerDAO#DbProjectWorkerDAO(String,DBConnectionFactory,
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
            new DbProjectWorkerDAO("tt_project", dbFactory, "  ", "ProjectWorkerSearchBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectWorkerDAO#DbProjectWorkerDAO(String,DBConnectionFactory,
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
            new DbProjectWorkerDAO("tt_project", dbFactory, TestHelper.SEARCH_NAMESPACE, "ProjectWorkerSearchBundle",
                null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbProjectWorkerDAO#DbProjectWorkerDAO(String,DBConnectionFactory,
     * String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the search namespace is unknown and expects for ConfigurationException.
     * </p>
     */
    public void testCtor_UnknownSearchNamespace() {
        try {
            new DbProjectManagerDAO("tt_project", dbFactory, "HelloWorld", "ProjectWorkerSearchBundle", auditManager);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#addProjectWorkers(ProjectWorker[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectWorkerDAO#addProjectWorkers(ProjectWorker[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectWorkers() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        workerDao.addProjectWorkers(new ProjectWorker[] {worker}, true);

        ProjectWorker[] projectWorkers = workerDao.enumerateProjectWorkers();
        assertEquals("Failed to add the project workers.", 1, projectWorkers.length);
        assertEquals("Failed to add the project workers.", worker.getUserId(), projectWorkers[0].getId());
        assertEquals("Failed to add the project workers.", worker.getPayRate(), projectWorkers[0].getPayRate(), 0.01);
        assertEquals("Failed to add the project workers.", worker.getProjectId(), projectWorkers[0].getProjectId());
        assertEquals("Failed to add the project workers.", worker.getUserId(), projectWorkers[0].getUserId());
        assertEquals("Failed to add the project workers.", worker.getCreationUser(),
            projectWorkers[0].getCreationUser());
        assertEquals("Failed to add the project workers.", worker.getModificationUser(),
            projectWorkers[0].getModificationUser());
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#addProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when workers is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectWorkers_NullWorkers() throws Exception {
        try {
            workerDao.addProjectWorkers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#addProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when workers contains null worker and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectWorkers_NullInWorkers() throws Exception {
        try {
            workerDao.addProjectWorkers(new ProjectWorker[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#addProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some worker has null creation date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectWorkers_NullCreationDate() throws Exception {
        ProjectWorker pm = TestHelper.createTestingProjectWorker("CreationDate");
        try {
            workerDao.addProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#addProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some worker has null creation user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectWorkers_NullCreationUser() throws Exception {
        ProjectWorker pm = TestHelper.createTestingProjectWorker("CreationUser");
        try {
            workerDao.addProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#addProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some worker has null modification date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectWorkers_NullModificationDate() throws Exception {
        ProjectWorker pm = TestHelper.createTestingProjectWorker("ModificationDate");
        try {
            workerDao.addProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#addProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some worker has null modification user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectWorkers_NullModificationUser() throws Exception {
        ProjectWorker pm = TestHelper.createTestingProjectWorker("ModificationUser");
        try {
            workerDao.addProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#addProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectWorkers_InvalidConnectionName() throws Exception {
        workerDao = new DbProjectWorkerDAO("unknown", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectWorkerSearchBundle", auditManager);
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);

        try {
            workerDao.addProjectWorkers(new ProjectWorker[] {worker}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#addProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectWorkers_EmptyDatabase() throws Exception {
        workerDao = new DbProjectWorkerDAO("empty", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectWorkerSearchBundle", auditManager);
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);

        try {
            workerDao.addProjectWorkers(new ProjectWorker[] {worker}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#updateProjectWorkers(ProjectWorker[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectWorkerDAO#updateProjectWorkers(ProjectWorker[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorkers() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        workerDao.addProjectWorkers(new ProjectWorker[] {worker}, true);

        worker.setCreationUser("topcoder");
        worker.setModificationUser("admin");
        worker.setPayRate(0.98);

        workerDao.updateProjectWorkers(new ProjectWorker[] {worker}, true);

        ProjectWorker[] projectWorkers = workerDao.enumerateProjectWorkers();
        assertEquals("Failed to add the project workers.", 1, projectWorkers.length);
        assertEquals("Failed to add the project workers.", worker.getUserId(), projectWorkers[0].getId());
        assertEquals("Failed to add the project workers.", worker.getPayRate(), projectWorkers[0].getPayRate(), 0.01);
        assertEquals("Failed to add the project workers.", worker.getProjectId(), projectWorkers[0].getProjectId());
        assertEquals("Failed to add the project workers.", worker.getUserId(), projectWorkers[0].getUserId());
        assertEquals("Failed to add the project workers.", worker.getCreationUser(),
            projectWorkers[0].getCreationUser());
        assertEquals("Failed to add the project workers.", worker.getModificationUser(),
            projectWorkers[0].getModificationUser());
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#updateProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when workers is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorkers_NullWorkers() throws Exception {
        try {
            workerDao.updateProjectWorkers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#updateProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when workers contains null worker and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorkers_NullInWorkers() throws Exception {
        try {
            workerDao.updateProjectWorkers(new ProjectWorker[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#updateProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some worker has null creation date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorkers_NullCreationDate() throws Exception {
        ProjectWorker pm = TestHelper.createTestingProjectWorker("CreationDate");
        try {
            workerDao.updateProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#updateProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some worker has null creation user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorkers_NullCreationUser() throws Exception {
        ProjectWorker pm = TestHelper.createTestingProjectWorker("CreationUser");
        try {
            workerDao.updateProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#updateProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some worker has null modification date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorkers_NullModificationDate() throws Exception {
        ProjectWorker pm = TestHelper.createTestingProjectWorker("ModificationDate");
        try {
            workerDao.updateProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#updateProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some worker has null modification user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorkers_NullModificationUser() throws Exception {
        ProjectWorker pm = TestHelper.createTestingProjectWorker("ModificationUser");
        try {
            workerDao.updateProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#updateProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorkers_InvalidConnectionName() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        workerDao.addProjectWorkers(new ProjectWorker[] {worker}, true);

        workerDao = new DbProjectWorkerDAO("unknown", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectWorkerSearchBundle", auditManager);
        try {
            workerDao.updateProjectWorkers(new ProjectWorker[] {worker}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#updateProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorkers_EmptyDatabase() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        workerDao.addProjectWorkers(new ProjectWorker[] {worker}, true);

        workerDao = new DbProjectWorkerDAO("empty", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectWorkerSearchBundle", auditManager);
        try {
            workerDao.updateProjectWorkers(new ProjectWorker[] {worker}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#updateProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorkers_UnrecognizedEntityException() throws Exception {
        try {
            workerDao.updateProjectWorkers(new ProjectWorker[] {TestHelper.createTestingProjectWorker(null)}, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#removeProjectWorkers(long[],long[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectWorkerDAO#removeProjectWorkers(long[],long[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectWorkers() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        workerDao.addProjectWorkers(new ProjectWorker[] {worker}, true);

        workerDao.removeProjectWorkers(new long[] {worker.getProjectId()}, new long[] {worker.getUserId()}, true);

        ProjectWorker[] projectWorkers = workerDao.enumerateProjectWorkers();
        assertEquals("Failed to remove the project workers.", 0, projectWorkers.length);
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#removeProjectWorkers(long[],long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when projectIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectWorkers_NullProjectIds() throws Exception {
        try {
            workerDao.removeProjectWorkers(null, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#removeProjectWorkers(long[],long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectWorkers_NullUserIds() throws Exception {
        try {
            workerDao.removeProjectWorkers(new long[] {1}, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#removeProjectWorkers(long[],long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some project id is negative and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectWorkers_NegativeProjectId() throws Exception {
        try {
            workerDao.removeProjectWorkers(new long[] {1}, new long[] {-1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#removeProjectWorkers(long[],long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectWorkers_NegativeUserId() throws Exception {
        try {
            workerDao.removeProjectWorkers(new long[] {-1}, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#removeProjectWorkers(long[],long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userIds and projectIds don't have same length and expects
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectWorkers_NotSameLength() throws Exception {
        try {
            workerDao.removeProjectWorkers(new long[] {1, 2}, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#removeProjectWorkers(long[],long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectWorkers_InvalidConnectionName() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        workerDao.addProjectWorkers(new ProjectWorker[] {worker}, true);

        workerDao = new DbProjectWorkerDAO("unknown", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectWorkerSearchBundle", auditManager);
        try {
            workerDao.removeProjectWorkers(new long[] {1}, new long[] {1}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#removeProjectWorkers(long[],long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectWorkers_EmptyDatabase() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        workerDao.addProjectWorkers(new ProjectWorker[] {worker}, true);

        workerDao = new DbProjectWorkerDAO("empty", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectWorkerSearchBundle", auditManager);
        try {
            workerDao.removeProjectWorkers(new long[] {1}, new long[] {1}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#searchProjectWorkers(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectWorkerDAO#searchProjectWorkers(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjectWorkers() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        workerDao.addProjectWorkers(new ProjectWorker[] {worker}, true);
        Filter filter = workerDao.getProjectWorkerFilterFactory().createProjectIdFilter(1);

        ProjectWorker[] projectWorkers = workerDao.searchProjectWorkers(filter);
        assertEquals("Failed to add the project workers.", 1, projectWorkers.length);
        assertEquals("Failed to add the project workers.", worker.getUserId(), projectWorkers[0].getId());
        assertEquals("Failed to add the project workers.", worker.getPayRate(), projectWorkers[0].getPayRate(), 0.01);
        assertEquals("Failed to add the project workers.", worker.getProjectId(), projectWorkers[0].getProjectId());
        assertEquals("Failed to add the project workers.", worker.getUserId(), projectWorkers[0].getUserId());
        assertEquals("Failed to add the project workers.", worker.getCreationUser(),
            projectWorkers[0].getCreationUser());
        assertEquals("Failed to add the project workers.", worker.getModificationUser(),
            projectWorkers[0].getModificationUser());
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#searchProjectWorkers(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjectWorkers_NullFilter() throws Exception {
        try {
            workerDao.searchProjectWorkers(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#searchProjectWorkers(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchProjectWorkers_DataAccessException() {
        Filter filter = new EqualToFilter("unknown", "unknown");
        try {
            workerDao.searchProjectWorkers(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#enumerateProjectWorkers() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectWorkerDAO#enumerateProjectWorkers() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjectWorkers() throws Exception {
        ProjectWorker[] projectWorkers = workerDao.enumerateProjectWorkers();

        assertEquals("Failed to get the project workers.", 0, projectWorkers.length);
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#enumerateProjectWorkers() for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjectWorkers_InvalidConnectionName() throws Exception {
        workerDao = new DbProjectWorkerDAO("unknown", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectWorkerSearchBundle", auditManager);
        try {
            workerDao.enumerateProjectWorkers();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#enumerateProjectWorkers() for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjectWorkers_EmptyDatabase() throws Exception {
        workerDao = new DbProjectWorkerDAO("empty", dbFactory, TestHelper.SEARCH_NAMESPACE,
            "ProjectWorkerSearchBundle", auditManager);
        try {
            workerDao.enumerateProjectWorkers();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbProjectWorkerDAO#getProjectWorkerFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbProjectWorkerDAO#getProjectWorkerFilterFactory() is correct.
     * </p>
     */
    public void testGetProjectWorkerFilterFactory() {
        assertNotNull("Failed to get the project worker filter factory.", workerDao.getProjectWorkerFilterFactory());
    }

}