/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import junit.framework.JUnit4TestAdapter;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.topcoder.utilities.cp.BaseUnitTests;
import com.topcoder.utilities.cp.entities.DirectProjectCPConfig;
import com.topcoder.utilities.cp.services.ContributionServiceInitializationException;

/**
 * <p>
 * Unit tests for {@link BaseService} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class BaseServiceUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>BaseService</code> instance used in tests.
     * </p>
     */
    private MockService instance;

    /**
     * <p>
     * Represents the logger name used in tests.
     * </p>
     */
    private String loggerName = BaseService.class.getName();

    /**
     * <p>
     * Represents the session factory used in tests.
     * </p>
     */
    private SessionFactory sessionFactory;

    /**
     * <p>
     * Represents the entity used in tests.
     * </p>
     */
    private DirectProjectCPConfig entity;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseServiceUnitTests.class);
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

        instance = (MockService) APP_CONTEXT.getBean("mockService");

        entity = new DirectProjectCPConfig();
        entity.setDirectProjectId(1);
        entity.setUseCP(true);
        entity.setAvailableImmediateBudget(10);
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
     * Accuracy test for the constructor <code>BaseService()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new MockService();

        assertNull("'log' should be correct.", BaseUnitTests.getField(instance, "log"));
        assertNull("'sessionFactory' should be correct.", BaseUnitTests.getField(instance, "sessionFactory"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInit()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_checkInit() {
        instance.checkInit();

        assertNotNull("'checkInit' should be correct.", getField(instance, "sessionFactory"));
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> with sessionFactory is <code>null</code>.<br>
     * <code>ContributionServiceInitializationException</code> is expected.
     * </p>
     */
    @Test(expected = ContributionServiceInitializationException.class)
    public void test_checkInit_sessionFactoryNull() {
        instance.setSessionFactory(null);

        instance.checkInit();
    }

    /**
     * <p>
     * Accuracy test for the method <code>setLoggerName(String loggerName)</code> with loggerName is
     * <code>null</code>.<br>
     * The result should be correct: the logger is <code>null</code>.
     * </p>
     */
    @Test
    public void test_setLoggerName_Null() {
        instance.setLoggerName(null);

        assertNull("'setLoggerName' should be correct.", BaseUnitTests.getField(instance, "log"));
    }

    /**
     * <p>
     * Failure test for the method <code>setLoggerName(String loggerName)</code> with loggerName is empty.<br>
     * <code>ContributionServiceInitializationException</code> is expected.
     * </p>
     */
    @Test(expected = ContributionServiceInitializationException.class)
    public void test_setLoggerName_Empty() {
        instance.setLoggerName(EMPTY_STRING);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLog()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getLog() throws Exception {
        instance.setLoggerName(loggerName);

        assertNotNull("'getLog' should be correct.", instance.getLog());
    }


    /**
     * <p>
     * Accuracy test for the method <code>getSessionFactory()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getSessionFactory() {
        instance.setSessionFactory(sessionFactory);

        assertSame("'getSessionFactory' should be correct.",
            sessionFactory, instance.getSessionFactory());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSessionFactory(SessionFactory sessionFactory)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setSessionFactory() {
        instance.setSessionFactory(sessionFactory);

        assertSame("'setSessionFactory' should be correct.",
            sessionFactory, BaseUnitTests.getField(instance, "sessionFactory"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>createEntity(String signature, Object entity, String entityName, long id,
     * String idName)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Transactional
    @Test
    public void test_createEntity() throws Exception {
        long res = instance.createEntity("signature", entity, "entityName", entity.getDirectProjectId(), "idName");

        assertEquals("'createEntity' should be correct.", entity.getDirectProjectId(), res);

        DirectProjectCPConfig config = instance.getEntity("signature", DirectProjectCPConfig.class, res, "idName");
        assertEquals("'createEntity' should be correct.",
            entity.getAvailableImmediateBudget(), config.getAvailableImmediateBudget(), 0.001);
    }

    /**
     * <p>
     * Failure test for the method <code>createEntity(String signature, Object entity, String entityName, long id,
     * String idName)</code> with entity is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createEntity_entityNull() throws Exception {
        entity = null;

        instance.createEntity("signature", entity, "entityName", 0, "idName");
    }

    /**
     * <p>
     * Failure test for the method <code>createEntity(String signature, Object entity, String entityName, long id,
     * String idName)</code> with id is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createEntity_idNegative() throws Exception {
        entity.setDirectProjectId(-1);

        instance.createEntity("signature", entity, "entityName", entity.getDirectProjectId(), "idName");
    }

    /**
     * <p>
     * Failure test for the method <code>createEntity(String signature, Object entity, String entityName, long id,
     * String idName)</code> with id is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_createEntity_idZero() throws Exception {
        entity.setDirectProjectId(0);

        instance.createEntity("signature", entity, "entityName", entity.getDirectProjectId(), "idName");
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateEntity(String signature, Object entity, String entityName, long id,
     * String idName)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Transactional
    @Test
    public void test_updateEntity() throws Exception {
        long id = instance.createEntity("signature", entity, "entityName", entity.getDirectProjectId(), "idName");

        entity.setAvailableImmediateBudget(20);
        instance.updateEntity("signature", entity, "entityName", entity.getDirectProjectId(), "idName");

        DirectProjectCPConfig res = instance.getEntity("signature", DirectProjectCPConfig.class, id, "idName");
        assertEquals("'updateEntity' should be correct.",
            entity.getAvailableImmediateBudget(), res.getAvailableImmediateBudget(), 0.001);
    }

    /**
     * <p>
     * Failure test for the method <code>updateEntity(String signature, Object entity, String entityName, long id,
     * String idName)</code> with entity is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateEntity_entityNull() throws Exception {
        entity = null;

        instance.updateEntity("signature", entity, "entityName", 0, "idName");
    }

    /**
     * <p>
     * Failure test for the method <code>updateEntity(String signature, Object entity, String entityName, long id,
     * String idName)</code> with id is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateEntity_idNegative() throws Exception {
        entity.setDirectProjectId(-1);

        instance.updateEntity("signature", entity, "entityName", entity.getDirectProjectId(), "idName");
    }

    /**
     * <p>
     * Failure test for the method <code>updateEntity(String signature, Object entity, String entityName, long id,
     * String idName)</code> with id is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateEntity_idZero() throws Exception {
        entity.setDirectProjectId(0);

        instance.updateEntity("signature", entity, "entityName", entity.getDirectProjectId(), "idName");
    }

    /**
     * <p>
     * Accuracy test for the method <code>getEntity(String signature, Class&lt;T&gt; clazz, long id,
     * String idName)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Transactional
    @Test
    public void test_deleteEntity() throws Exception {
        long id = instance.createEntity("signature", entity, "entityName", entity.getDirectProjectId(), "idName");

        instance.deleteEntity("signature", DirectProjectCPConfig.class, id, "idName");

        DirectProjectCPConfig res = instance.getEntity("signature", DirectProjectCPConfig.class, id, "idName");
        assertNull("'deleteEntity' should be correct.", res);
    }

    /**
     * <p>
     * Failure test for the method <code>getEntity(String signature, Class&lt;T&gt; clazz, long id,
     * String idName)</code> with id is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteEntity_idNegative() throws Exception {
        instance.deleteEntity("signature", DirectProjectCPConfig.class, -1, "idName");
    }

    /**
     * <p>
     * Failure test for the method <code>getEntity(String signature, Class&lt;T&gt; clazz, long id,
     * String idName)</code> with id is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteEntity_idZero() throws Exception {
        instance.deleteEntity("signature", DirectProjectCPConfig.class, 0, "idName");
    }

    /**
     * <p>
     * Accuracy test for the method <code>getEntity(String signature, Class&lt;T&gt; clazz, long id,
     * String idName)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Transactional
    @Test
    public void test_getEntity1() throws Exception {
        long id = instance.createEntity("signature", entity, "entityName", entity.getDirectProjectId(), "idName");

        DirectProjectCPConfig config = instance.getEntity("signature", DirectProjectCPConfig.class, id, "idName");
        assertEquals("'getEntity' should be correct.",
            entity.getAvailableImmediateBudget(), config.getAvailableImmediateBudget(), 0.001);
    }

    /**
     * <p>
     * Failure test for the method <code>getEntity(String signature, Class&lt;T&gt; clazz, long id,
     * String idName)</code> with id is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getEntity1_idNegative() throws Exception {
        instance.getEntity("signature", DirectProjectCPConfig.class, -1, "idName");
    }

    /**
     * <p>
     * Failure test for the method <code>getEntity(String signature, Class&lt;T&gt; clazz, long id,
     * String idName)</code> with id is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getEntity1_idZero() throws Exception {
        instance.getEntity("signature", DirectProjectCPConfig.class, 0, "idName");
    }

    /**
     * <p>
     * Accuracy test for the method <code>getEntity(Class&lt;T&gt; claz, long id, boolean required)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Transactional
    @Test
    public void test_getEntity2() throws Exception {
        long id = instance.createEntity("signature", entity, "entityName", entity.getDirectProjectId(), "idName");

        DirectProjectCPConfig config = instance.getEntity(DirectProjectCPConfig.class, id, true);
        assertEquals("'getEntity' should be correct.",
            entity.getAvailableImmediateBudget(), config.getAvailableImmediateBudget(), 0.001);
    }

    /**
     * <p>
     * Failure test for the method <code>getEntity(Class&lt;T&gt; claz, long id, boolean required)</code> with id is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getEntity2_idNegative() throws Exception {
        instance.getEntity("signature", DirectProjectCPConfig.class, -1, "idName");
    }

    /**
     * <p>
     * Failure test for the method <code>getEntity(Class&lt;T&gt; claz, long id, boolean required)</code> with id is
     * zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getEntity2_idZero() throws Exception {
        instance.getEntity("signature", DirectProjectCPConfig.class, 0, "idName");
    }
}