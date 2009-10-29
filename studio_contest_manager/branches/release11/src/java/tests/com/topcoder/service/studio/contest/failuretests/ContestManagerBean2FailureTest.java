/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.failuretests;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import junit.framework.TestCase;

import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.bean.ContestManagerBean;
import com.topcoder.service.studio.contest.bean.MockEntityManager;
import com.topcoder.service.studio.contest.bean.MockSessionContext;
import com.topcoder.service.studio.contest.utils.ContestFilterFactory;

/**
 * <p>
 * Failure tests for <code>ContestManagerBean</code> class.
 * </p>
 *
 * @author KSD
 * @version 1.1
 */
public class ContestManagerBean2FailureTest extends TestCase {
    /**
     * <p>
     * Represents the <code>ContestManagerBean</code> instance for test.
     * </p>
     */
    private ContestManagerBean contestManagerBean;

    /**
     * <p>
     * Represents the <code>MockSessionContext</code> instance for test.
     * </p>
     */
    private MockSessionContext sessionContext;

    /**
     * <p>
     * Represents the <code>MockEntityManager</code> instance for test.
     * </p>
     */
    private MockEntityManager entityManager;

    /**
     * <p>
     * Sets up the environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        contestManagerBean = new ContestManagerBean();

        sessionContext = new MockSessionContext();
        Field field = contestManagerBean.getClass().getDeclaredField("sessionContext");
        field.setAccessible(true);
        field.set(contestManagerBean, sessionContext);

        initContext();
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        if (entityManager != null) {
            entityManager.close();
        }
    }

    /**
     * <p>
     * Initialize the context.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    private void initContext() throws Exception {
        sessionContext.addEntry("auditChange", new Boolean(false));
        sessionContext.addEntry("unitName", "contestManager");
        sessionContext.addEntry("activeContestStatusId", new Long(1));
        sessionContext.addEntry("defaultDocumentPathId", new Long(1));
        //sessionContext.addEntry("loggerName", "contestManager");
        sessionContext.addEntry("documentContentManagerClassName",
                "com.topcoder.service.studio.contest.documentcontentmanagers.SocketDocumentContentManager");
        sessionContext.addEntry("documentContentManagerAttributeKeys", "serverAddress,serverPort");
        sessionContext.addEntry("serverAddress", "127.0.0.1");
        sessionContext.addEntry("serverPort", new Integer(40000));

        Method method = contestManagerBean.getClass().getDeclaredMethod("initialize", new Class[0]);

        method.setAccessible(true);
        method.invoke(contestManagerBean, new Object[0]);

        entityManager = new MockEntityManager();
        sessionContext.addEntry("contestManager", entityManager);
    }

    /**
     * <p>
     * Failure test for the <code>getAllContests()</code> method with TransactionRequiredException occurs,
     * ContestManagementException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllContestsWithTRE() throws Exception {
        try {
            entityManager.enableTransactionException(true);
            contestManagerBean.getAllContests();
            fail("ContestManagementException should be thrown.");
        } catch (ContestManagementException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getAllContests()</code> method with PersistenceException occurs,
     * ContestManagementException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllContestsWithPE() throws Exception {
        try {
            entityManager.enablePersistenceException(true);
            contestManagerBean.getAllContests();
            fail("ContestManagementException should be thrown.");
        } catch (ContestManagementException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>searchContests(Filter)</code> with the filter is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchContestsWithFilterNull() throws Exception {
        try {
            contestManagerBean.searchContests(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>searchContests(Filter)</code> with the entityManager is closed,
     * ContestManagementException is expected.
     * </p>
     */
    public void testSearchContestsWithEntityManagerClosed() {
        try {
            entityManager.enablePersistenceException(true);
            contestManagerBean.searchContests(ContestFilterFactory.createStudioContestChannelIdFilter(1L));
            fail("ContestManagementException should be thrown.");
        } catch (ContestManagementException e) {
            // expected
        }
    }
}
