/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import junit.framework.JUnit4TestAdapter;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.utilities.cp.BaseUnitTests;
import com.topcoder.utilities.cp.entities.ProjectContestCPConfig;
import com.topcoder.utilities.cp.services.ContributionServiceEntityNotFoundException;
import com.topcoder.utilities.cp.services.ContributionServiceException;
import com.topcoder.utilities.cp.services.ProjectContestCPConfigService;

/**
 * <p>
 * Unit tests for {@link ProjectContestCPConfigServiceImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ProjectContestCPConfigServiceImplUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>ProjectContestCPConfigServiceImpl</code> instance used in tests.
     * </p>
     */
    private ProjectContestCPConfigService instance;

    /**
     * <p>
     * Represents the session factory used in tests.
     * </p>
     */
    private SessionFactory sessionFactory;

    /**
     * <p>
     * Represents the config used in tests.
     * </p>
     */
    private ProjectContestCPConfig config;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ProjectContestCPConfigServiceImplUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        sessionFactory = (SessionFactory) APP_CONTEXT.getBean("sessionFactory");

        instance = (ProjectContestCPConfigService) APP_CONTEXT.getBean("projectContestCPConfigService");

        config = new ProjectContestCPConfig();
        config.setContestId(1);
        config.setCpRate(2);
        config.setTotalOriginalPayment(100);
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();

        sessionFactory.close();
        sessionFactory = null;
        instance = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectContestCPConfigServiceImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new ProjectContestCPConfigServiceImpl();

        assertNull("'log' should be correct.", BaseUnitTests.getField(instance, "log"));
        assertNull("'sessionFactory' should be correct.", BaseUnitTests.getField(instance, "sessionFactory"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>create(ProjectContestCPConfig config)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_create() throws Exception {
        long res = instance.create(config);

        assertEquals("'create' should be correct.", config.getContestId(), res);

        ProjectContestCPConfig entity = getEntity(sessionFactory, ProjectContestCPConfig.class, res);

        assertEquals("'create' should be correct.", config.getContestId(), entity.getContestId());
        assertEquals("'create' should be correct.", config.getCpRate(), entity
            .getCpRate(), 0.001);
        assertEquals("'create' should be correct.",
            config.getTotalOriginalPayment(), entity.getTotalOriginalPayment(), 0.001);
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectContestCPConfig config)</code> with config is
     * <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_create_configNull() throws Exception {
        config = null;

        instance.create(config);
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectContestCPConfig config)</code> with config#contestId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_create_contestIdNegative() throws Exception {
        config.setContestId(-1);

        instance.create(config);
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectContestCPConfig config)</code> with config#contestId is
     * zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_create_contestIdZero() throws Exception {
        config.setContestId(0);

        instance.create(config);
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectContestCPConfig config)</code> with config#contestId
     * doesn't exist in database.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_create_contestIdNotExist() throws Exception {
        config.setContestId(Long.MAX_VALUE);

        instance.create(config);
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectContestCPConfig config)</code> with entity with the ID
     * already exists.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_create_EntityExists() throws Exception {
        instance.create(config);

        instance.create(config);
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectContestCPConfig config)</code> with an error occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_create_Error() throws Exception {
        instance = (ProjectContestCPConfigService) APP_CONTEXT_INVALID.getBean("projectContestCPConfigService");

        instance.create(config);
    }

    /**
     * <p>
     * Accuracy test for the method <code>update(ProjectContestCPConfig config)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_update() throws Exception {
        long contestId = instance.create(config);

        config.setTotalOriginalPayment(150);
        instance.update(config);

        ProjectContestCPConfig entity = getEntity(sessionFactory, ProjectContestCPConfig.class, contestId);

        assertEquals("'update' should be correct.", config.getContestId(), entity.getContestId());
        assertEquals("'update' should be correct.", config.getCpRate(), entity
            .getCpRate(), 0.001);
        assertEquals("'update' should be correct.",
            config.getTotalOriginalPayment(), entity.getTotalOriginalPayment(), 0.001);
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectContestCPConfig config)</code> with config is
     * <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_configNull() throws Exception {
        config = null;

        instance.update(config);
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectContestCPConfig config)</code> with config#contestId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_contestIdNegative() throws Exception {
        config.setContestId(-1);

        instance.update(config);
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectContestCPConfig config)</code> with config#contestId is
     * zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_contestIdZero() throws Exception {
        config.setContestId(0);

        instance.update(config);
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectContestCPConfig config)</code> with config#contestId
     * doesn't exist in database.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_update_contestIdNotExist() throws Exception {
        config.setContestId(Long.MAX_VALUE);

        instance.update(config);
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectContestCPConfig config)</code> with the entity cannot be
     * found.<br>
     * <code>ContributionServiceEntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceEntityNotFoundException.class)
    public void test_update_NotFoundError() throws Exception {
        instance.update(config);
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectContestCPConfig config)</code> with an error occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_update_Error() throws Exception {
        instance.create(config);

        instance = (ProjectContestCPConfigService) APP_CONTEXT_INVALID.getBean("projectContestCPConfigService");

        instance.update(config);
    }

    /**
     * <p>
     * Accuracy test for the method <code>delete(long contestId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_delete() throws Exception {
        long contestId = instance.create(config);

        instance.delete(contestId);

        ProjectContestCPConfig entity = getEntity(sessionFactory, ProjectContestCPConfig.class, contestId);

        assertNull("'delete' should be correct.", entity);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long contestId)</code> with contestId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_delete_contestIdNegative() throws Exception {
        instance.delete(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long contestId)</code> with contestId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_delete_contestIdZero() throws Exception {
        instance.delete(0);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long contestId)</code> with the entity cannot be found.<br>
     * <code>ContributionServiceEntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceEntityNotFoundException.class)
    public void test_delete_NotFoundError() throws Exception {
        instance.delete(Long.MAX_VALUE);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long contestId)</code> with an error occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_delete_Error() throws Exception {
        long contestId = instance.create(config);

        instance = (ProjectContestCPConfigService) APP_CONTEXT_INVALID.getBean("projectContestCPConfigService");

        instance.delete(contestId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>get(long contestId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_get_1() throws Exception {
        long contestId = instance.create(config);

        ProjectContestCPConfig res = instance.get(contestId);

        assertEquals("'get' should be correct.", config.getContestId(), res.getContestId());
        assertEquals("'get' should be correct.", config.getCpRate(), res
            .getCpRate(), 0.001);
        assertEquals("'get' should be correct.",
            config.getTotalOriginalPayment(), res.getTotalOriginalPayment(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>get(long contestId)</code>
     * with entity doesn't exist.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_get_2() throws Exception {
        ProjectContestCPConfig res = instance.get(Long.MAX_VALUE);

        assertNull("'get' should be correct.", res);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long contestId)</code> with contestId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_get_contestIdNegative() throws Exception {
        instance.get(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long contestId)</code> with contestId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_get_contestIdZero() throws Exception {
        instance.get(0);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long contestId)</code> with an error occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_get_Error() throws Exception {
        long contestId = instance.create(config);

        instance = (ProjectContestCPConfigService) APP_CONTEXT_INVALID.getBean("projectContestCPConfigService");

        instance.get(contestId);
    }
}