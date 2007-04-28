/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.project.db.DbProjectWorkerDAO;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for ProjectWorkerUtilityImpl.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ProjectWorkerUtilityImplTests extends TestCase {
    /**
     * <p>
     * The ProjectWorkerUtilityImpl instance for testing.
     * </p>
     */
    private ProjectWorkerUtilityImpl impl;

    /**
     * <p>
     * The DbProjectWorkerDAO instance for testing.
     * </p>
     */
    private DbProjectWorkerDAO dao;

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
        dao = new DbProjectWorkerDAO("tt_project", dbFactory, TestHelper.SEARCH_NAMESPACE, "ProjectWorkerSearchBundle",
            auditManager);

        impl = new ProjectWorkerUtilityImpl(dao);
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

        impl = null;
        dao = null;
        dbFactory = null;
        auditManager = null;

    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ProjectWorkerUtilityImplTests.class);
    }

    /**
     * <p>
     * Tests ctor ProjectWorkerUtilityImpl#ProjectWorkerUtilityImpl(ProjectWorkerDAO) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created ProjectWorkerUtilityImpl instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new ProjectWorkerUtilityImpl instance.", impl);
    }

    /**
     * <p>
     * Tests ctor ProjectWorkerUtilityImpl#ProjectWorkerUtilityImpl(ProjectWorkerDAO) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when workerDao is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullWorkerDao() {
        try {
            new ProjectWorkerUtilityImpl(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#addProjectWorker(ProjectWorker,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilityImpl#addProjectWorker(ProjectWorker,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectWorker() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        impl.addProjectWorker(worker, true);

        ProjectWorker[] projectWorkers = impl.enumerateProjectWorkers();
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
     * Tests ProjectWorkerUtilityImpl#addProjectWorker(ProjectWorker,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when worker is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddProjectWorker_NullWorker() throws Exception {
        try {
            impl.addProjectWorker(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#updateProjectWorker(ProjectWorker,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilityImpl#updateProjectWorker(ProjectWorker,boolean) is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorker() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        impl.addProjectWorker(worker, true);

        worker.setCreationUser("topcoder");
        worker.setModificationUser("admin");
        worker.setPayRate(0.98);

        impl.updateProjectWorker(worker, true);

        ProjectWorker[] projectWorkers = impl.enumerateProjectWorkers();
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
     * Tests ProjectWorkerUtilityImpl#updateProjectWorker(ProjectWorker,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when worker is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorker_NullWorker() throws Exception {
        try {
            impl.updateProjectWorker(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#removeProjectWorker(long,long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilityImpl#removeProjectWorker(long,long,boolean) is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testRemoveProjectWorker() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        impl.addProjectWorker(worker, true);

        impl.removeProjectWorker(worker.getProjectId(), worker.getUserId(), true);

        ProjectWorker[] projectWorkers = impl.enumerateProjectWorkers();
        assertEquals("Failed to remove the project workers.", 0, projectWorkers.length);
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#addProjectWorkers(ProjectWorker[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilityImpl#addProjectWorkers(ProjectWorker[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectWorkers() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        impl.addProjectWorkers(new ProjectWorker[] {worker}, true);

        ProjectWorker[] projectWorkers = impl.enumerateProjectWorkers();
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
     * Tests ProjectWorkerUtilityImpl#addProjectWorkers(ProjectWorker[],boolean) for failure.
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
            impl.addProjectWorkers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#addProjectWorkers(ProjectWorker[],boolean) for failure.
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
            impl.addProjectWorkers(new ProjectWorker[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#addProjectWorkers(ProjectWorker[],boolean) for failure.
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
            impl.addProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#addProjectWorkers(ProjectWorker[],boolean) for failure.
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
            impl.addProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#addProjectWorkers(ProjectWorker[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectWorkers_EmptyDatabase() throws Exception {
        dao = new DbProjectWorkerDAO("empty", dbFactory, TestHelper.SEARCH_NAMESPACE, "ProjectWorkerSearchBundle",
            auditManager);
        impl = new ProjectWorkerUtilityImpl(dao);
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);

        try {
            impl.addProjectWorkers(new ProjectWorker[] {worker}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#updateProjectWorkers(ProjectWorker[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilityImpl#updateProjectWorkers(ProjectWorker[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorkers() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        impl.addProjectWorkers(new ProjectWorker[] {worker}, true);

        worker.setCreationUser("topcoder");
        worker.setModificationUser("admin");
        worker.setPayRate(0.98);

        impl.updateProjectWorkers(new ProjectWorker[] {worker}, true);

        ProjectWorker[] projectWorkers = impl.enumerateProjectWorkers();
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
     * Tests ProjectWorkerUtilityImpl#updateProjectWorkers(ProjectWorker[],boolean) for failure.
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
            impl.updateProjectWorkers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#updateProjectWorkers(ProjectWorker[],boolean) for failure.
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
            impl.updateProjectWorkers(new ProjectWorker[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#updateProjectWorkers(ProjectWorker[],boolean) for failure.
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
            impl.updateProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#updateProjectWorkers(ProjectWorker[],boolean) for failure.
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
            impl.updateProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#updateProjectWorkers(ProjectWorker[],boolean) for failure.
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
        impl.addProjectWorkers(new ProjectWorker[] {worker}, true);

        dao = new DbProjectWorkerDAO("empty", dbFactory, TestHelper.SEARCH_NAMESPACE, "ProjectWorkerSearchBundle",
            auditManager);
        impl = new ProjectWorkerUtilityImpl(dao);
        try {
            impl.updateProjectWorkers(new ProjectWorker[] {worker}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#updateProjectWorkers(ProjectWorker[],boolean) for failure.
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
            impl.updateProjectWorkers(new ProjectWorker[] {TestHelper.createTestingProjectWorker(null)}, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#removeProjectWorkers(long[],long[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilityImpl#removeProjectWorkers(long[],long[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectWorkers() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        impl.addProjectWorkers(new ProjectWorker[] {worker}, true);

        impl.removeProjectWorkers(new long[] {worker.getProjectId()}, new long[] {worker.getUserId()}, true);

        ProjectWorker[] projectWorkers = impl.enumerateProjectWorkers();
        assertEquals("Failed to remove the project workers.", 0, projectWorkers.length);
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#removeProjectWorkers(long[],long[],boolean) for failure.
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
            impl.removeProjectWorkers(null, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#removeProjectWorkers(long[],long[],boolean) for failure.
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
            impl.removeProjectWorkers(new long[] {1}, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#removeProjectWorkers(long[],long[],boolean) for failure.
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
            impl.removeProjectWorkers(new long[] {1}, new long[] {-1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#removeProjectWorkers(long[],long[],boolean) for failure.
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
            impl.removeProjectWorkers(new long[] {-1}, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#removeProjectWorkers(long[],long[],boolean) for failure.
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
            impl.removeProjectWorkers(new long[] {1, 2}, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#removeProjectWorkers(long[],long[],boolean) for failure.
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
        impl.addProjectWorkers(new ProjectWorker[] {worker}, true);

        dao = new DbProjectWorkerDAO("empty", dbFactory, TestHelper.SEARCH_NAMESPACE, "ProjectWorkerSearchBundle",
            auditManager);
        impl = new ProjectWorkerUtilityImpl(dao);
        try {
            impl.removeProjectWorkers(new long[] {1}, new long[] {1}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#searchProjectWorkers(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilityImpl#searchProjectWorkers(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjectWorkers() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        impl.addProjectWorkers(new ProjectWorker[] {worker}, true);
        Filter filter = impl.getProjectWorkerFilterFactory().createProjectIdFilter(1);

        ProjectWorker[] projectWorkers = impl.searchProjectWorkers(filter);
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
     * Tests ProjectWorkerUtilityImpl#searchProjectWorkers(Filter) for failure.
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
            impl.searchProjectWorkers(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#searchProjectWorkers(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchProjectWorkers_DataAccessException() {
        Filter filter = new EqualToFilter("unknown", "unknown");
        try {
            impl.searchProjectWorkers(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#enumerateProjectWorkers() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilityImpl#enumerateProjectWorkers() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjectWorkers() throws Exception {
        ProjectWorker[] projectWorkers = impl.enumerateProjectWorkers();

        assertEquals("Failed to get the project workers.", 0, projectWorkers.length);
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#enumerateProjectWorkers() for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the database doesn't contain any table and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjectWorkers_EmptyDatabase() throws Exception {
        dao = new DbProjectWorkerDAO("empty", dbFactory, TestHelper.SEARCH_NAMESPACE, "ProjectWorkerSearchBundle",
            auditManager);
        impl = new ProjectWorkerUtilityImpl(dao);
        try {
            impl.enumerateProjectWorkers();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilityImpl#getProjectWorkerFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilityImpl#getProjectWorkerFilterFactory() is correct.
     * </p>
     */
    public void testGetProjectWorkerFilterFactory() {
        assertNotNull("Failed to get the project worker filter factory.", impl.getProjectWorkerFilterFactory());
    }

}