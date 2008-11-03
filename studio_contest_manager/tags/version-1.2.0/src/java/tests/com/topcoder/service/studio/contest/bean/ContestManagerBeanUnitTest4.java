/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.TestHelper;
import com.topcoder.service.studio.contest.utils.ContestFilterFactory;

/**
 * <p>
 * Unit tests for <code>ContestManagerBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestManagerBeanUnitTest4 extends TestCase {
    /**
     * <p>
     * The <code>ContestManagerBean</code> instance for testing.
     * </p>
     */
    private ContestManagerBean bean;

    /**
     * <p>
     * The mock <code>SessionContext</code> for simulating the ejb environment.
     * </p>
     */
    private MockSessionContext context;

    /**
     * <p>
     * The mock <code>EntityManager</code> for testing.
     * </p>
     */
    private MockEntityManager entityManager;

    /**
     * <p>
     * entities created during tests. They will be removed in the end of test.
     * </p>
     */
    private List entities = new ArrayList();

    /**
     * <p>
     * Sets up the environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        bean = new ContestManagerBean();

        context = new MockSessionContext();

        Field field = bean.getClass().getDeclaredField("sessionContext");
        field.setAccessible(true);
        field.set(bean, context);

        initContext();
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        if (entityManager != null) {
            try {
                TestHelper.removeContests(entityManager, entities);
                entityManager.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * <p>
     * Gets the test suite for <code>ContestManagerBean</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>ContestManagerBean</code> class.
     */
    public static Test suite() {
        return new TestSuite(ContestManagerBeanUnitTest4.class);
    }

    /**
     * <p>
     * Initialize the context.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    private void initContext() throws Exception {
        context.addEntry("unitName", "contestManager");
        context.addEntry("activeContestStatusId", new Long(1));
        context.addEntry("defaultDocumentPathId", new Long(1));
        context.addEntry("loggerName", "contestManager");
        context.addEntry("documentContentManagerClassName",
            "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        context.addEntry("documentContentManagerAttributeKeys", "serverAddress,serverPort");
        context.addEntry("serverAddress", "127.0.0.1");
        context.addEntry("serverPort", new Integer(40000));

        Method method = bean.getClass().getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);
        method.invoke(bean, new Object[0]);

        entityManager = new MockEntityManager();
        context.addEntry("contestManager", entityManager);
    }

    /**
     * <p>
     * Accuracy test for <code>getAllContests()</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetAllContests_Accuracy() throws Exception {
        TestHelper.initContests(entityManager, entities);
        List<Contest> allContests = bean.getAllContests();
        assertEquals("The size should be 2.", 2, allContests.size());
    }

    /**
     * <p>
     * Failure1 test for <code>getAllContests()</code>.
     * </p>
     *
     * <p>
     * If any transaction exception throws, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContests_Failure1() throws Exception {
        try {
            entityManager.enableTransactionException(true);
            bean.getAllContests();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure1 test for <code>getAllContests()</code>.
     * </p>
     *
     * <p>
     * If any persistence exception throws, <code>ContestManagementException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllContests_Failure2() throws Exception {
        try {
            entityManager.enablePersistenceException(true);
            bean.getAllContests();
            fail("ContestManagementException is expected.");
        } catch (ContestManagementException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>searchContests(Filter)</code>.
     * </p>
     * <p>
     * Search by contest name. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testSearchContests_Accuracy1() throws Exception {
        TestHelper.initContests(entityManager, entities);
        List<Contest> list = bean.searchContests(ContestFilterFactory.createStudioContestDirectProjectIdFilter(1));
        assertEquals("Should return 2 contests.", 2, list.size());
    }

    /**
     * <p>
     * Accuracy test for <code>searchContests(Filter)</code>.
     * </p>
     * <p>
     * Search by contest name and others. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testSearchContests_Accuracy2() throws Exception {
        TestHelper.initContests(entityManager, entities);
        Filter filter = ContestFilterFactory.createAndFilter(new Filter[] {
            ContestFilterFactory.createStudioContestNameFilter("contest1"),
            ContestFilterFactory.createStudioContestForumIdFilter(1),
            ContestFilterFactory.createStudioContestDirectProjectIdFilter(1)});
        List<Contest> list = bean.searchContests(filter);
        assertEquals("Should return 1 contest.", 1, list.size());
    }

    /**
     * <p>
     * Accuracy test for <code>searchContests(Filter)</code>.
     * </p>
     * <p>
     * Search by contest name and other criteria. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testSearchContests_Accuracy3() throws Exception {
        TestHelper.initContests(entityManager, entities);
        Filter andfilter = ContestFilterFactory.createAndFilter(new Filter[] {
            ContestFilterFactory.createStudioContestNameFilter("contest1"),
            ContestFilterFactory.createStudioContestForumIdFilter(1),
            ContestFilterFactory.createStudioContestDirectProjectIdFilter(1)});
        Filter filter = ContestFilterFactory.createOrFilter(new Filter[] {andfilter,
            ContestFilterFactory.createStudioContestChannelNameFilter("channel")});
        List<Contest> list = bean.searchContests(filter);
        assertEquals("Should return 2 contests.", 2, list.size());
    }

    /**
     * <p>
     * Failure test for <code>searchContests(Filter)</code>.
     * </p>
     * <p>
     * Passes in null filter value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testSearchContests_Failure1() throws Exception {
        try {
            bean.searchContests(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>searchContests(Filter)</code>.
     * </p>
     * <p>
     * entityManager is closed, <code>ContestManagementException</code> should be thrown.
     * </p>
     */
    public void testSearchContests_Failure2() {
        try {
            entityManager.enablePersistenceException(true);
            bean.searchContests(ContestFilterFactory.createStudioContestChannelIdFilter(1L));
            fail("ContestManagementException should be thrown.");
        } catch (ContestManagementException e) {
            // pass
        }
    }
}
