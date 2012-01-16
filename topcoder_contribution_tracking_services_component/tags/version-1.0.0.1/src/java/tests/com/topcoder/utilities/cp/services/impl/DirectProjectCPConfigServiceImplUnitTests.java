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
import com.topcoder.utilities.cp.entities.DirectProjectCPConfig;
import com.topcoder.utilities.cp.services.ContributionServiceEntityNotFoundException;
import com.topcoder.utilities.cp.services.ContributionServiceException;
import com.topcoder.utilities.cp.services.DirectProjectCPConfigService;

/**
 * <p>
 * Unit tests for {@link DirectProjectCPConfigServiceImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class DirectProjectCPConfigServiceImplUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>DirectProjectCPConfigServiceImpl</code> instance used in tests.
     * </p>
     */
    private DirectProjectCPConfigService instance;

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
    private DirectProjectCPConfig config;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectProjectCPConfigServiceImplUnitTests.class);
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

        instance = (DirectProjectCPConfigService) APP_CONTEXT.getBean("directProjectCPConfigService");

        config = new DirectProjectCPConfig();
        config.setDirectProjectId(1);
        config.setUseCP(true);
        config.setAvailableImmediateBudget(10);
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
     * Accuracy test for the constructor <code>DirectProjectCPConfigServiceImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new DirectProjectCPConfigServiceImpl();

        assertNull("'log' should be correct.", BaseUnitTests.getField(instance, "log"));
        assertNull("'sessionFactory' should be correct.", BaseUnitTests.getField(instance, "sessionFactory"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>create(DirectProjectCPConfig config)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_create() throws Exception {
        long res = instance.create(config);

        assertEquals("'create' should be correct.", config.getDirectProjectId(), res);

        DirectProjectCPConfig entity = getEntity(sessionFactory, DirectProjectCPConfig.class, res);

        assertEquals("'create' should be correct.", config.getDirectProjectId(), entity.getDirectProjectId());
        assertEquals("'create' should be correct.", config.getAvailableImmediateBudget(), entity
            .getAvailableImmediateBudget(), 0.001);
        assertEquals("'create' should be correct.", config.isUseCP(), entity.isUseCP());
    }

    /**
     * <p>
     * Failure test for the method <code>create(DirectProjectCPConfig config)</code> with config is
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
     * Failure test for the method <code>create(DirectProjectCPConfig config)</code> with config#directProjectId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_create_directProjectIdNegative() throws Exception {
        config.setDirectProjectId(-1);

        instance.create(config);
    }

    /**
     * <p>
     * Failure test for the method <code>create(DirectProjectCPConfig config)</code> with config#directProjectId is
     * zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_create_directProjectIdZero() throws Exception {
        config.setDirectProjectId(0);

        instance.create(config);
    }

    /**
     * <p>
     * Failure test for the method <code>create(DirectProjectCPConfig config)</code> with config#directProjectId
     * doesn't exist in database.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_create_directProjectIdNotExist() throws Exception {
        config.setDirectProjectId(Long.MAX_VALUE);

        instance.create(config);
    }

    /**
     * <p>
     * Failure test for the method <code>create(DirectProjectCPConfig config)</code> with entity with the ID already
     * exists.<br>
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
     * Failure test for the method <code>create(DirectProjectCPConfig config)</code> with an error occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_create_Error() throws Exception {
        instance = (DirectProjectCPConfigService) APP_CONTEXT_INVALID.getBean("directProjectCPConfigService");

        instance.create(config);
    }

    /**
     * <p>
     * Accuracy test for the method <code>update(DirectProjectCPConfig config)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_update() throws Exception {
        long directProjectId = instance.create(config);

        config.setAvailableImmediateBudget(15);
        instance.update(config);

        DirectProjectCPConfig entity = getEntity(sessionFactory, DirectProjectCPConfig.class, directProjectId);

        assertEquals("'update' should be correct.", config.getDirectProjectId(), entity.getDirectProjectId());
        assertEquals("'update' should be correct.", config.getAvailableImmediateBudget(), entity
            .getAvailableImmediateBudget(), 0.001);
        assertEquals("'update' should be correct.", config.isUseCP(), entity.isUseCP());
    }

    /**
     * <p>
     * Failure test for the method <code>update(DirectProjectCPConfig config)</code> with config is
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
     * Failure test for the method <code>update(DirectProjectCPConfig config)</code> with config#directProjectId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_directProjectIdNegative() throws Exception {
        config.setDirectProjectId(-1);

        instance.update(config);
    }

    /**
     * <p>
     * Failure test for the method <code>update(DirectProjectCPConfig config)</code> with config#directProjectId is
     * zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_directProjectIdZero() throws Exception {
        config.setDirectProjectId(0);

        instance.update(config);
    }

    /**
     * <p>
     * Failure test for the method <code>update(DirectProjectCPConfig config)</code> with config#directProjectId
     * doesn't exist in database.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_update_directProjectIdNotExist() throws Exception {
        config.setDirectProjectId(Long.MAX_VALUE);

        instance.update(config);
    }

    /**
     * <p>
     * Failure test for the method <code>update(DirectProjectCPConfig config)</code> with the entity cannot be
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
     * Failure test for the method <code>update(DirectProjectCPConfig config)</code> with an error occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_update_Error() throws Exception {
        instance.create(config);

        instance = (DirectProjectCPConfigService) APP_CONTEXT_INVALID.getBean("directProjectCPConfigService");

        instance.update(config);
    }

    /**
     * <p>
     * Accuracy test for the method <code>delete(long directProjectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_delete() throws Exception {
        long directProjectId = instance.create(config);

        instance.delete(directProjectId);

        DirectProjectCPConfig entity = getEntity(sessionFactory, DirectProjectCPConfig.class, directProjectId);

        assertNull("'delete' should be correct.", entity);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long directProjectId)</code> with directProjectId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_delete_directProjectIdNegative() throws Exception {
        instance.delete(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long directProjectId)</code> with directProjectId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_delete_directProjectIdZero() throws Exception {
        instance.delete(0);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long directProjectId)</code> with the entity cannot be found.<br>
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
     * Failure test for the method <code>delete(long directProjectId)</code> with an error occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_delete_Error() throws Exception {
        long directProjectId = instance.create(config);

        instance = (DirectProjectCPConfigService) APP_CONTEXT_INVALID.getBean("directProjectCPConfigService");

        instance.delete(directProjectId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>get(long directProjectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_get_1() throws Exception {
        long directProjectId = instance.create(config);

        DirectProjectCPConfig res = instance.get(directProjectId);

        assertEquals("'get' should be correct.", config.getDirectProjectId(), res.getDirectProjectId());
        assertEquals("'get' should be correct.", config.getAvailableImmediateBudget(), res
            .getAvailableImmediateBudget(), 0.001);
        assertEquals("'get' should be correct.", config.isUseCP(), res.isUseCP());
    }

    /**
     * <p>
     * Accuracy test for the method <code>get(long directProjectId)</code>
     * with entity doesn't exist.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_get_2() throws Exception {
        DirectProjectCPConfig res = instance.get(Long.MAX_VALUE);

        assertNull("'get' should be correct.", res);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long directProjectId)</code> with directProjectId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_get_directProjectIdNegative() throws Exception {
        instance.get(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long directProjectId)</code> with directProjectId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_get_directProjectIdZero() throws Exception {
        instance.get(0);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long directProjectId)</code> with an error occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_get_Error() throws Exception {
        long directProjectId = instance.create(config);

        instance = (DirectProjectCPConfigService) APP_CONTEXT_INVALID.getBean("directProjectCPConfigService");

        instance.get(directProjectId);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAvailableInitialPayments(long directProjectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAvailableInitialPayments_1() throws Exception {
        long directProjectId = instance.create(config);

        double res = instance.getAvailableInitialPayments(directProjectId);

        assertEquals("'getAvailableInitialPayments' should be correct.",
            config.getAvailableImmediateBudget(), res, 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAvailableInitialPayments(long directProjectId)</code>
     * with entity doesn't exist.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getAvailableInitialPayments_2() throws Exception {
        double res = instance.getAvailableInitialPayments(Long.MAX_VALUE);

        assertEquals("'getAvailableInitialPayments' should be correct.", 0, res, 0.001);
    }

    /**
     * <p>
     * Failure test for the method <code>getAvailableInitialPayments(long directProjectId)</code> with
     * directProjectId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAvailableInitialPayments_directProjectIdNegative() throws Exception {
        instance.getAvailableInitialPayments(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>getAvailableInitialPayments(long directProjectId)</code> with
     * directProjectId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getAvailableInitialPayments_directProjectIdZero() throws Exception {
        instance.getAvailableInitialPayments(0);
    }

    /**
     * <p>
     * Failure test for the method <code>getAvailableInitialPayments(long directProjectId)</code> with an error
     * occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_getAvailableInitialPayments_Error() throws Exception {
        long directProjectId = instance.create(config);

        instance = (DirectProjectCPConfigService) APP_CONTEXT_INVALID.getBean("directProjectCPConfigService");

        instance.getAvailableInitialPayments(directProjectId);
    }
}