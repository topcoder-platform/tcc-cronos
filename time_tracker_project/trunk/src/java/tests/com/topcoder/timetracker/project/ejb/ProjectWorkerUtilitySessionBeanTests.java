/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.ejb;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.TestHelper;
import com.topcoder.timetracker.project.UnrecognizedEntityException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for ProjectWorkerUtilitySessionBean.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ProjectWorkerUtilitySessionBeanTests extends TestCase {
    /**
     * <p>
     * The ProjectWorkerUtilitySessionBean instance for testing.
     * </p>
     */
    private ProjectWorkerUtilitySessionBean bean;

    /**
     * <p>
     * The ProjectWorkerUtilityDelegate instance for testing.
     * </p>
     */
    private ProjectWorkerUtilityDelegate delegate;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.SEARCH_CONFIG_FILE);
        TestHelper.setUpDataBase();

        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // we use MockTransaction outside of the app server
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        bean = new ProjectWorkerUtilitySessionBean();

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("projectWorkerDelegateLocalHome",
            ProjectWorkerUtilityLocalHome.class, ProjectWorkerUtilityLocal.class, bean);

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        delegate = new ProjectWorkerUtilityDelegate(
            "com.topcoder.timetracker.project.ejb.ProjectWorkerUtilityDelegate");

    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        delegate = null;
        bean = null;

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
        return new TestSuite(ProjectWorkerUtilitySessionBeanTests.class);
    }

    /**
     * <p>
     * Tests ctor ProjectWorkerUtilitySessionBean#ProjectWorkerUtilitySessionBean() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created ProjectWorkerUtilitySessionBean instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new ProjectWorkerUtilitySessionBean instance.", delegate);
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#addProjectWorker(ProjectWorker,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#addProjectWorker(ProjectWorker,boolean) is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddProjectWorker() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        delegate.addProjectWorker(worker, true);

        ProjectWorker[] projectWorkers = delegate.enumerateProjectWorkers();
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
     * Tests ProjectWorkerUtilitySessionBean#addProjectWorker(ProjectWorker,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when worker is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddProjectWorker_NullWorker() throws Exception {
        try {
            delegate.addProjectWorker(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#updateProjectWorker(ProjectWorker,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#updateProjectWorker(ProjectWorker,boolean) is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorker() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        delegate.addProjectWorker(worker, true);

        worker.setCreationUser("topcoder");
        worker.setModificationUser("admin");
        worker.setPayRate(0.98);

        delegate.updateProjectWorker(worker, true);

        ProjectWorker[] projectWorkers = delegate.enumerateProjectWorkers();
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
     * Tests ProjectWorkerUtilitySessionBean#updateProjectWorker(ProjectWorker,boolean) for failure.
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
            delegate.updateProjectWorker(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#removeProjectWorker(long,long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#removeProjectWorker(long,long,boolean) is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testRemoveProjectWorker() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        delegate.addProjectWorker(worker, true);

        delegate.removeProjectWorker(worker.getProjectId(), worker.getUserId(), true);

        ProjectWorker[] projectWorkers = delegate.enumerateProjectWorkers();
        assertEquals("Failed to remove the project workers.", 0, projectWorkers.length);
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#addProjectWorkers(ProjectWorker[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#addProjectWorkers(ProjectWorker[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectWorkers() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        delegate.addProjectWorkers(new ProjectWorker[] {worker}, true);

        ProjectWorker[] projectWorkers = delegate.enumerateProjectWorkers();
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
     * Tests ProjectWorkerUtilitySessionBean#addProjectWorkers(ProjectWorker[],boolean) for failure.
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
            delegate.addProjectWorkers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#addProjectWorkers(ProjectWorker[],boolean) for failure.
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
            delegate.addProjectWorkers(new ProjectWorker[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#addProjectWorkers(ProjectWorker[],boolean) for failure.
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
            delegate.addProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#addProjectWorkers(ProjectWorker[],boolean) for failure.
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
            delegate.addProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#updateProjectWorkers(ProjectWorker[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#updateProjectWorkers(ProjectWorker[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectWorkers() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        delegate.addProjectWorkers(new ProjectWorker[] {worker}, true);

        worker.setCreationUser("topcoder");
        worker.setModificationUser("admin");
        worker.setPayRate(0.98);

        delegate.updateProjectWorkers(new ProjectWorker[] {worker}, true);

        ProjectWorker[] projectWorkers = delegate.enumerateProjectWorkers();
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
     * Tests ProjectWorkerUtilitySessionBean#updateProjectWorkers(ProjectWorker[],boolean) for failure.
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
            delegate.updateProjectWorkers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#updateProjectWorkers(ProjectWorker[],boolean) for failure.
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
            delegate.updateProjectWorkers(new ProjectWorker[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#updateProjectWorkers(ProjectWorker[],boolean) for failure.
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
            delegate.updateProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#updateProjectWorkers(ProjectWorker[],boolean) for failure.
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
            delegate.updateProjectWorkers(new ProjectWorker[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#updateProjectWorkers(ProjectWorker[],boolean) for failure.
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
            delegate.updateProjectWorkers(new ProjectWorker[] {TestHelper.createTestingProjectWorker(null)}, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#removeProjectWorkers(long[],long[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#removeProjectWorkers(long[],long[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectWorkers() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        delegate.addProjectWorkers(new ProjectWorker[] {worker}, true);

        delegate.removeProjectWorkers(new long[] {worker.getProjectId()}, new long[] {worker.getUserId()}, true);

        ProjectWorker[] projectWorkers = delegate.enumerateProjectWorkers();
        assertEquals("Failed to remove the project workers.", 0, projectWorkers.length);
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#removeProjectWorkers(long[],long[],boolean) for failure.
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
            delegate.removeProjectWorkers(null, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#removeProjectWorkers(long[],long[],boolean) for failure.
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
            delegate.removeProjectWorkers(new long[] {1}, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#removeProjectWorkers(long[],long[],boolean) for failure.
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
            delegate.removeProjectWorkers(new long[] {1}, new long[] {-1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#removeProjectWorkers(long[],long[],boolean) for failure.
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
            delegate.removeProjectWorkers(new long[] {-1}, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#removeProjectWorkers(long[],long[],boolean) for failure.
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
            delegate.removeProjectWorkers(new long[] {1, 2}, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#searchProjectWorkers(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#searchProjectWorkers(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjectWorkers() throws Exception {
        ProjectWorker worker = TestHelper.createTestingProjectWorker(null);
        delegate.addProjectWorkers(new ProjectWorker[] {worker}, true);
        Filter filter = delegate.getProjectWorkerFilterFactory().createProjectIdFilter(1);

        ProjectWorker[] projectWorkers = delegate.searchProjectWorkers(filter);
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
     * Tests ProjectWorkerUtilitySessionBean#searchProjectWorkers(Filter) for failure.
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
            delegate.searchProjectWorkers(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#searchProjectWorkers(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchProjectWorkers_DataAccessException() {
        Filter filter = new EqualToFilter("unknown", "unknown");
        try {
            delegate.searchProjectWorkers(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#enumerateProjectWorkers() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#enumerateProjectWorkers() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjectWorkers() throws Exception {
        ProjectWorker[] projectWorkers = delegate.enumerateProjectWorkers();

        assertEquals("Failed to get the project workers.", 0, projectWorkers.length);
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#getProjectWorkerFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#getProjectWorkerFilterFactory() is correct.
     * </p>
     */
    public void testGetProjectWorkerFilterFactory() {
        assertNotNull("Failed to get the project worker filter factory.", delegate.getProjectWorkerFilterFactory());
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#ejbCreate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#ejbCreate() is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testEjbCreate() throws Exception {
        bean.ejbCreate();

        // no assertion here because when the object created in this method cannot be accessed outside
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#ejbActivate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#ejbActivate() is correct.
     * </p>
     */
    public void testEjbActivate() {
        bean.ejbActivate();
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#ejbPassivate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#ejbPassivate() is correct.
     * </p>
     */
    public void testEjbPassivate() {
        bean.ejbPassivate();
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#ejbRemove() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#ejbRemove() is correct.
     * </p>
     */
    public void testEjbRemove() {
        bean.ejbRemove();
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#setSessionContext(SessionContext) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#setSessionContext(SessionContext) is correct.
     * </p>
     */
    public void testSetSessionContext() {
        bean.setSessionContext(null);

        assertNull("Failed to set the session context.", bean.getSessionContext());
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#getSessionContext() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#getSessionContext() is correct.
     * </p>
     */
    public void testGetSessionContext() {
        assertNotNull("Failed to get the session context.", bean.getSessionContext());
    }

    /**
     * <p>
     * Tests ProjectWorkerUtilitySessionBean#getImpl() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectWorkerUtilitySessionBean#getImpl() is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetImpl() throws Exception {
        assertNotNull("Failed to get the instance correctly.", bean.getImpl());
    }

}