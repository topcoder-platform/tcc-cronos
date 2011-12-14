/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.accuracy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.utilities.cp.entities.DirectProjectCPConfig;
import com.topcoder.utilities.cp.services.DirectProjectCPConfigService;
import com.topcoder.utilities.cp.services.impl.DirectProjectCPConfigServiceImpl;

/**
 * <p>
 * Accuracy test {@link DirectProjectCPConfigServiceImpl}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class DirectProjectCPConfigServiceImplUnitTests extends BaseAccuracyUnitTests {
    /**
     * <p>
     * Represents DirectProjectCPConfigServiceImpl instance for testing.
     * </p>
     */
    private DirectProjectCPConfigService instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        instance = (DirectProjectCPConfigService) ACCURAY_APP_CONTEXT.getBean("directProjectCPConfigService");
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectCPConfigServiceImpl#DirectProjectCPConfigServiceImpl()}.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull(instance);
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectCPConfigServiceImpl#create(DirectProjectCPConfig)}
     * and {@link DirectProjectCPConfigServiceImpl#get(long)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCreateAndGet() throws Exception {
        DirectProjectCPConfig config = new DirectProjectCPConfig();
        config.setDirectProjectId(4);
        config.setUseCP(true);
        config.setAvailableImmediateBudget(1.5);
        long id = instance.create(config);
        assertEquals(4, id);

        DirectProjectCPConfig newConfig = instance.get(id);
        assertTrue(newConfig.isUseCP());
        assertTrue(1.5f == newConfig.getAvailableImmediateBudget());
        assertEquals(id, newConfig.getDirectProjectId());
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectCPConfigServiceImpl#update(DirectProjectCPConfig)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testUpdate() throws Exception {
        DirectProjectCPConfig config = new DirectProjectCPConfig();
        config.setDirectProjectId(4);
        config.setUseCP(true);
        config.setAvailableImmediateBudget(1.5);
        instance.create(config);

        config.setUseCP(false);
        config.setAvailableImmediateBudget(0);
        instance.update(config);

        DirectProjectCPConfig newConfig = instance.get(4);
        assertFalse(newConfig.isUseCP());
        assertTrue(0 == newConfig.getAvailableImmediateBudget());
        assertEquals(4, newConfig.getDirectProjectId());
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectCPConfigServiceImpl#get(long)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGet() throws Exception {
        assertNull(instance.get(1));
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectCPConfigServiceImpl#delete(long)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDelete() throws Exception {
        DirectProjectCPConfig config = new DirectProjectCPConfig();
        config.setDirectProjectId(4);
        config.setUseCP(true);
        config.setAvailableImmediateBudget(1.5);
        instance.create(config);
        assertNotNull(instance.get(4));
        instance.delete(4);
        assertNull(instance.get(4));
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectCPConfigServiceImpl#getAvailableInitialPayments}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testgetAvailableInitialPaymentsAccuracy1() throws Exception {
        assertTrue(0 == instance.getAvailableInitialPayments(1));
    }

    /**
     * <p>
     * Accuracy test for {@link DirectProjectCPConfigServiceImpl#getAvailableInitialPayments}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testgetAvailableInitialPaymentsAccuracy2() throws Exception {
        DirectProjectCPConfig config = new DirectProjectCPConfig();
        config.setDirectProjectId(4);
        config.setUseCP(true);
        config.setAvailableImmediateBudget(1.5);
        instance.create(config);
        assertTrue(1.5f == instance.getAvailableInitialPayments(4));
    }
}
