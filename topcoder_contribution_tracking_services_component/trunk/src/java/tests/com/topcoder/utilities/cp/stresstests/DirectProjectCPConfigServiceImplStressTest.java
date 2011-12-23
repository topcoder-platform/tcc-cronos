/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.stresstests;

import org.junit.Assert;

import com.topcoder.utilities.cp.entities.DirectProjectCPConfig;
import com.topcoder.utilities.cp.services.DirectProjectCPConfigService;

/**
 * <p>
 * Stress tests for DirectProjectCPConfigServiceImpl class.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
public class DirectProjectCPConfigServiceImplStressTest extends BaseStressTest {
    /**
     * <p>
     * Represents the DirectProjectCPConfigServiceImpl for testing.
     * </p>
     */
    private DirectProjectCPConfigService instance;

    /**
     * <p>
     * Setup the environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
        instance = (DirectProjectCPConfigService) APP_CONTEXT.getBean("directProjectCPConfigService");
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Stress test for DirectProjectCPConfigServiceImple#create(DirectProjectCPConfig).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreate() throws Exception {
        start();
        for (int i = 0; i < LOOP; i++) {
            DirectProjectCPConfig directProjectCPConfig = new DirectProjectCPConfig();
            directProjectCPConfig.setDirectProjectId(i + 1);
            directProjectCPConfig.setUseCP(true);
            directProjectCPConfig.setAvailableImmediateBudget(1);

            long id = instance.create(directProjectCPConfig);
            Assert.assertEquals("'create' should be correct.", directProjectCPConfig.getDirectProjectId(), id);
        }
        printResult("DirectProjectCPConfigServiceImpl#create(DirectProjectCPConfig)", LOOP);
    }

    /**
     * <p>
     * Stress test for DirectProjectCPConfigServiceImple#get(long).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGet() throws Exception {
        DirectProjectCPConfig directProjectCPConfig = new DirectProjectCPConfig();
        directProjectCPConfig.setDirectProjectId(1);
        directProjectCPConfig.setUseCP(true);
        directProjectCPConfig.setAvailableImmediateBudget(1);
        long id = instance.create(directProjectCPConfig);
        start();
        for (int i = 0; i < LOOP; i++) {
            Assert.assertNotNull("fail to get", instance.get(id));
        }

        printResult("DirectProjectCPConfigServiceImpl#get(long)", LOOP);
    }

    /**
     * <p>
     * Stress test for DirectProjectCPConfigServiceImple#update(DirectProjectCPConfig).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdate() throws Exception {
        DirectProjectCPConfig directProjectCPConfig = new DirectProjectCPConfig();
        directProjectCPConfig.setDirectProjectId(1);
        directProjectCPConfig.setUseCP(true);
        directProjectCPConfig.setAvailableImmediateBudget(1);
        instance.create(directProjectCPConfig);
        start();
        for (int i = 0; i < LOOP; i++) {

            directProjectCPConfig.setAvailableImmediateBudget(Double.valueOf(i));
            instance.update(directProjectCPConfig);
            Assert.assertTrue("fail to update", instance.getAvailableInitialPayments(1) == Double.valueOf(i));
        }

        printResult("DirectProjectCPConfigServiceImpl#update(DirectProjectCPConfig)", LOOP);
    }

    /**
     * <p>
     * Stress test for DirectProjectCPConfigServiceImple#getAvailableInitialPayments(long).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetAvailableInitialPayments() throws Exception {
        DirectProjectCPConfig directProjectCPConfig = new DirectProjectCPConfig();
        directProjectCPConfig.setDirectProjectId(1);
        directProjectCPConfig.setUseCP(true);
        directProjectCPConfig.setAvailableImmediateBudget(1);
        instance.create(directProjectCPConfig);
        start();
        for (int i = 0; i < LOOP; i++) {
            Assert.assertTrue("fail to getAvailableInitialPayments", 1.0 == instance.getAvailableInitialPayments(1));

        }
        printResult("DirectProjectCPConfigServiceImpl#getAvailableInitialPayments(long)", LOOP);
    }

    /**
     * <p>
     * Stress test for DirectProjectCPConfigServiceImple#delete(long).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDelte() throws Exception {
        start();
        for (int i = 0; i < LOOP; i++) {
            final DirectProjectCPConfig directProjectCPConfig = new DirectProjectCPConfig();
            directProjectCPConfig.setDirectProjectId(i + 1);
            directProjectCPConfig.setUseCP(true);
            directProjectCPConfig.setAvailableImmediateBudget(1);

            instance.create(directProjectCPConfig);
            instance.delete(directProjectCPConfig.getDirectProjectId());
        }

        printResult("DirectProjectCPConfigServiceImpl#delete(long)", LOOP);
    }
}
