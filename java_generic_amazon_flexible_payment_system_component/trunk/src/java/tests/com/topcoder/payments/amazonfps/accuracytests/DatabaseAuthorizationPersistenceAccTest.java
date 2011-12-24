/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.payments.amazonfps.model.Authorization;
import com.topcoder.payments.amazonfps.persistence.AuthorizationPersistence;
import com.topcoder.payments.amazonfps.persistence.db.BaseDatabasePersistence;
import com.topcoder.payments.amazonfps.persistence.db.DatabaseAuthorizationPersistence;

/**
 * <p>
 * Accuracy tests for class <code>DatabaseAuthorizationPersistence</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class DatabaseAuthorizationPersistenceAccTest extends BaseAccTest {

    /**
     * Represents the <code>DatabaseAuthorizationPersistence</code> instance used to test against.
     */
    private DatabaseAuthorizationPersistence impl;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(DatabaseAuthorizationPersistenceAccTest.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        impl = new DatabaseAuthorizationPersistence();
    }

    /**
     * Tear down the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        impl = null;
    }

    /**
     * Inheritance test, verifies <code>DatabaseAuthorizationPersistence</code> subclasses should be
     * correct.
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.",
            impl instanceof BaseDatabasePersistence);
        assertTrue("The instance's subclass is not correct.",
            impl instanceof AuthorizationPersistence);
    }

    /**
     * Accuracy test for the constructor <code>DatabaseAuthorizationPersistenceAcc()</code>.<br>
     * Instance should be created successfully.
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * Accuracy test for the method <code>createAuthorization(Authorization)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCreateAuthorization() throws Exception {
        ConfigurationObject configuration = getConfig().getChild("authorizationPersistenceConfig");

        impl.configure(configuration);

        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(true);
        authorization.setCancelled(false);
        authorization.setTokenId("tokenId1");
        authorization.setAuthorizedAmountLeft(BigDecimal.valueOf(100));
        authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(200));

        impl.createAuthorization(authorization);

        assertTrue("'createAuthorization' should be correct.", authorization.getId() > 0);

        Authorization res = impl.getAuthorization(authorization.getId());

        assertTrue("'createAuthorization' should be correct.", res.isMultipleUseAuthorization());
        assertFalse("'createAuthorization' should be correct.", res.isCancelled());
        assertEquals("'createAuthorization' should be correct.", "tokenId1", res.getTokenId());
        assertEquals("'createAuthorization' should be correct.", 100L, res
            .getAuthorizedAmountLeft().longValue());
        assertEquals("'createAuthorization' should be correct.", 200L, res
            .getAuthorizedFixedFutureAmount().longValue());
    }

    /**
     * Accuracy test for the method <code>createAuthorization(Authorization)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCreateAuthorization2() throws Exception {
        ConfigurationObject configuration = getConfig().getChild("authorizationPersistenceConfig");

        impl.configure(configuration);

        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(false);
        authorization.setCancelled(true);
        authorization.setTokenId(null);
        authorization.setAuthorizedAmountLeft(BigDecimal.valueOf(100));
        authorization.setAuthorizedFixedFutureAmount(null);

        impl.createAuthorization(authorization);

        assertTrue("'createAuthorization' should be correct.", authorization.getId() > 0);

        Authorization res = impl.getAuthorization(authorization.getId());

        assertFalse("'createAuthorization' should be correct.", res.isMultipleUseAuthorization());
        assertTrue("'createAuthorization' should be correct.", res.isCancelled());
        assertNull("'createAuthorization' should be correct.", res.getTokenId());
        assertEquals("'createAuthorization' should be correct.", 100L, res
            .getAuthorizedAmountLeft().longValue());
        assertNull("'createAuthorization' should be correct.", res.getAuthorizedFixedFutureAmount());
    }

    /**
     * Accuracy test for the method <code>updateAuthorization(Authorization)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testUpdateAuthorization() throws Exception {
        ConfigurationObject configuration = getConfig().getChild("authorizationPersistenceConfig");

        impl.configure(configuration);

        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(true);
        authorization.setCancelled(false);
        authorization.setTokenId("tokenId1");
        authorization.setAuthorizedAmountLeft(BigDecimal.valueOf(100));
        authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(200));

        impl.createAuthorization(authorization);

        assertTrue("'updateAuthorization' should be correct.", authorization.getId() > 0);

        Authorization res = impl.getAuthorization(authorization.getId());

        assertTrue("'updateAuthorization' should be correct.", res.isMultipleUseAuthorization());
        assertFalse("'updateAuthorization' should be correct.", res.isCancelled());
        assertEquals("'updateAuthorization' should be correct.", "tokenId1", res.getTokenId());
        assertEquals("'updateAuthorization' should be correct.", 100L, res
            .getAuthorizedAmountLeft().longValue());
        assertEquals("'updateAuthorization' should be correct.", 200L, res
            .getAuthorizedFixedFutureAmount().longValue());

        authorization.setMultipleUseAuthorization(false);
        authorization.setCancelled(true);
        authorization.setTokenId(null);
        authorization.setAuthorizedAmountLeft(BigDecimal.valueOf(300));
        authorization.setAuthorizedFixedFutureAmount(null);

        impl.updateAuthorization(authorization);

        res = impl.getAuthorization(authorization.getId());

        assertFalse("'updateAuthorization' should be correct.", res.isMultipleUseAuthorization());
        assertTrue("'updateAuthorization' should be correct.", res.isCancelled());
        assertNull("'updateAuthorization' should be correct.", res.getTokenId());
        assertEquals("'updateAuthorization' should be correct.", 300L, res
            .getAuthorizedAmountLeft().longValue());
        assertNull("'updateAuthorization' should be correct.", res.getAuthorizedFixedFutureAmount());
    }

    /**
     * Accuracy test for the method <code>getAuthorization(long)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAuthorization() throws Exception {
        ConfigurationObject configuration = getConfig().getChild("authorizationPersistenceConfig");

        impl.configure(configuration);

        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(true);
        authorization.setCancelled(false);
        authorization.setTokenId("tokenId1");
        authorization.setAuthorizedAmountLeft(BigDecimal.valueOf(100));
        authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(200));

        impl.createAuthorization(authorization);

        assertTrue("'getAuthorization' should be correct.", authorization.getId() > 0);

        Authorization res = impl.getAuthorization(authorization.getId());

        assertTrue("'getAuthorization' should be correct.", res.isMultipleUseAuthorization());
        assertFalse("'getAuthorization' should be correct.", res.isCancelled());
        assertEquals("'getAuthorization' should be correct.", "tokenId1", res.getTokenId());
        assertEquals("'getAuthorization' should be correct.", 100L, res.getAuthorizedAmountLeft()
            .longValue());
        assertEquals("'getAuthorization' should be correct.", 200L, res
            .getAuthorizedFixedFutureAmount().longValue());

        res = impl.getAuthorization(99999L);
        assertNull("'getAuthorization' should be correct.", res);
    }

}
