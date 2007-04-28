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
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.TestHelper;
import com.topcoder.timetracker.project.UnrecognizedEntityException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for ProjectManagerUtilitySessionBean.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ProjectManagerUtilitySessionBeanTests extends TestCase {
    /**
     * <p>
     * The ProjectManagerUtilitySessionBean instance for testing.
     * </p>
     */
    private ProjectManagerUtilitySessionBean bean;

    /**
     * <p>
     * The ProjectManagerUtilityDelegate instance for testing.
     * </p>
     */
    private ProjectManagerUtilityDelegate delegate;

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

        bean = new ProjectManagerUtilitySessionBean();

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("projectManagerDelegateLocalHome",
            ProjectManagerUtilityLocalHome.class, ProjectManagerUtilityLocal.class, bean);

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        delegate = new ProjectManagerUtilityDelegate(
            "com.topcoder.timetracker.project.ejb.ProjectManagerUtilityDelegate");

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
        return new TestSuite(ProjectManagerUtilitySessionBeanTests.class);
    }

    /**
     * <p>
     * Tests ctor ProjectManagerUtilitySessionBean#ProjectManagerUtilitySessionBean() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created ProjectManagerUtilitySessionBean instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new ProjectManagerUtilitySessionBean instance.", delegate);
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#addProjectManager(ProjectManager,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#addProjectManager(ProjectManager,boolean) is correct.
     * </p>
     * @throws DataAccessException to JUnit
     */
    public void testAddProjectManager() throws DataAccessException {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        delegate.addProjectManager(manager, true);

        ProjectManager[] projectManagers = delegate.enumerateProjectManagers();
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
     * Tests ProjectManagerUtilitySessionBean#addProjectManager(ProjectManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when manager is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws DataAccessException to JUnit
     */
    public void testAddProjectManager_NullManager() throws DataAccessException {
        try {
            delegate.addProjectManager(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#updateProjectManager(ProjectManager,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#updateProjectManager(ProjectManager,boolean) is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManager() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        delegate.addProjectManager(manager, true);

        manager.setCreationUser("topcoder");
        manager.setModificationUser("admin");

        delegate.updateProjectManager(manager, true);

        ProjectManager[] projectManagers = delegate.enumerateProjectManagers();
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
     * Tests ProjectManagerUtilitySessionBean#updateProjectManager(ProjectManager,boolean) for failure.
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
            delegate.updateProjectManager(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#removeProjectManager(long,long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#removeProjectManager(long,long,boolean) is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testRemoveProjectManager() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        delegate.addProjectManager(manager, true);

        delegate.removeProjectManager(manager.getProjectId(), manager.getUserId(), true);

        ProjectManager[] projectManagers = delegate.enumerateProjectManagers();
        assertEquals("Failed to remove the project managers.", 0, projectManagers.length);
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#addProjectManagers(ProjectManager[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#addProjectManagers(ProjectManager[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddProjectManagers() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        delegate.addProjectManagers(new ProjectManager[] {manager}, true);

        ProjectManager[] projectManagers = delegate.enumerateProjectManagers();
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
     * Tests ProjectManagerUtilitySessionBean#addProjectManagers(ProjectManager[],boolean) for failure.
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
            delegate.addProjectManagers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#addProjectManagers(ProjectManager[],boolean) for failure.
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
            delegate.addProjectManagers(new ProjectManager[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#addProjectManagers(ProjectManager[],boolean) for failure.
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
            delegate.addProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#addProjectManagers(ProjectManager[],boolean) for failure.
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
            delegate.addProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#updateProjectManagers(ProjectManager[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#updateProjectManagers(ProjectManager[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectManagers() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        delegate.addProjectManagers(new ProjectManager[] {manager}, true);

        manager.setCreationUser("topcoder");
        manager.setModificationUser("admin");

        delegate.updateProjectManagers(new ProjectManager[] {manager}, true);

        ProjectManager[] projectManagers = delegate.enumerateProjectManagers();
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
     * Tests ProjectManagerUtilitySessionBean#updateProjectManagers(ProjectManager[],boolean) for failure.
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
            delegate.updateProjectManagers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#updateProjectManagers(ProjectManager[],boolean) for failure.
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
            delegate.updateProjectManagers(new ProjectManager[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#updateProjectManagers(ProjectManager[],boolean) for failure.
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
            delegate.updateProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#updateProjectManagers(ProjectManager[],boolean) for failure.
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
            delegate.updateProjectManagers(new ProjectManager[] {pm}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#updateProjectManagers(ProjectManager[],boolean) for failure.
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
            delegate.updateProjectManagers(new ProjectManager[] {TestHelper.createTestingProjectManager(null)}, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#removeProjectManagers(long[],long[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#removeProjectManagers(long[],long[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveProjectManagers() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        delegate.addProjectManagers(new ProjectManager[] {manager}, true);

        delegate.removeProjectManagers(new long[] {manager.getProjectId()}, new long[] {manager.getUserId()}, true);

        ProjectManager[] projectManagers = delegate.enumerateProjectManagers();
        assertEquals("Failed to remove the project managers.", 0, projectManagers.length);
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#removeProjectManagers(long[],long[],boolean) for failure.
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
            delegate.removeProjectManagers(null, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#removeProjectManagers(long[],long[],boolean) for failure.
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
            delegate.removeProjectManagers(new long[] {1}, new long[] {-1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#removeProjectManagers(long[],long[],boolean) for failure.
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
            delegate.removeProjectManagers(new long[] {1}, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#removeProjectManagers(long[],long[],boolean) for failure.
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
            delegate.removeProjectManagers(new long[] {-1}, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#removeProjectManagers(long[],long[],boolean) for failure.
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
            delegate.removeProjectManagers(new long[] {1, 2}, new long[] {1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#searchProjectManagers(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#searchProjectManagers(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjectManagers() throws Exception {
        ProjectManager manager = TestHelper.createTestingProjectManager(null);
        delegate.addProjectManagers(new ProjectManager[] {manager}, true);
        Filter filter = delegate.getProjectManagerFilterFactory().createProjectIdFilter(1);

        ProjectManager[] projectManagers = delegate.searchProjectManagers(filter);

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
     * Tests ProjectManagerUtilitySessionBean#searchProjectManagers(Filter) for failure.
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
            delegate.searchProjectManagers(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#searchProjectManagers(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchProjectManagers_DataAccessException() {
        Filter filter = new EqualToFilter("unknown", "unknown");
        try {
            delegate.searchProjectManagers(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#enumerateProjectManagers() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#enumerateProjectManagers() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEnumerateProjectManagers() throws Exception {
        ProjectManager[] projectManagers = delegate.enumerateProjectManagers();

        assertEquals("Failed to get the project managers.", 0, projectManagers.length);
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#getProjectManagerFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#getProjectManagerFilterFactory() is correct.
     * </p>
     */
    public void testGetProjectManagerFilterFactory() {
        assertNotNull("Failed to get the project manager filter factory.", delegate.getProjectManagerFilterFactory());
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#ejbCreate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#ejbCreate() is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testEjbCreate() throws Exception {
        bean.ejbCreate();

        // no assertion here because when the object created in this method cannot be accessed outside
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#ejbActivate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#ejbActivate() is correct.
     * </p>
     */
    public void testEjbActivate() {
        bean.ejbActivate();
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#ejbPassivate() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#ejbPassivate() is correct.
     * </p>
     */
    public void testEjbPassivate() {
        bean.ejbPassivate();
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#ejbRemove() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#ejbRemove() is correct.
     * </p>
     */
    public void testEjbRemove() {
        bean.ejbRemove();
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#setSessionContext(SessionContext) for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#setSessionContext(SessionContext) is correct.
     * </p>
     */
    public void testSetSessionContext() {
        bean.setSessionContext(null);

        assertNull("Failed to set the session context.", bean.getSessionContext());
    }

    /**
     * <p>
     * Tests ProjectManagerUtilitySessionBean#getSessionContext() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ProjectManagerUtilitySessionBean#getSessionContext() is correct.
     * </p>
     */
    public void testGetSessionContext() {
        assertNotNull("Failed to get the session context.", bean.getSessionContext());
    }

}