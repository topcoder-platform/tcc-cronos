/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.stresstests;

import org.junit.Assert;

import com.topcoder.utilities.cp.entities.ProjectContestCPConfig;
import com.topcoder.utilities.cp.services.ProjectContestCPConfigService;

/**
 * <p>
 * Stress tests for ProjectContestCPConfigServiceImpl class.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
public class ProjectContestCPConfigServiceImplStressTest extends BaseStressTest {
    /**
     * <p>
     * Represents the ProjectContestCPConfigService for testing.
     * </p>
     */
    private ProjectContestCPConfigService instance;

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
        instance = (ProjectContestCPConfigService) APP_CONTEXT.getBean("projectContestCPConfigService");
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
     * Stress test for ProjectContestCPConfigServiceImple#create(ProjectContestCPConfig).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreate() throws Exception {
        start();
        for (int i = 0; i < LOOP; i++) {
            ProjectContestCPConfig projectContestCPConfig = new ProjectContestCPConfig();
            projectContestCPConfig.setContestId(1 + i);
            projectContestCPConfig.setCpRate(2);
            projectContestCPConfig.setTotalOriginalPayment(100);

            long id = instance.create(projectContestCPConfig);
            Assert.assertEquals("'create' should be correct.", projectContestCPConfig.getContestId(), id);
        }
        printResult("ProjectContestCPConfigServiceImpl#create(ProjectContestCPConfig)", LOOP);
    }

    /**
     * <p>
     * Stress test for ProjectContestCPConfigServiceImple#update(ProjectContestCPConfig).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdate() throws Exception {
        ProjectContestCPConfig projectContestCPConfig = new ProjectContestCPConfig();
        projectContestCPConfig.setContestId(1);
        projectContestCPConfig.setCpRate(2);
        projectContestCPConfig.setTotalOriginalPayment(100);
        instance.create(projectContestCPConfig);
        start();
        for (int i = 0; i < LOOP; i++) {
            projectContestCPConfig.setTotalOriginalPayment(100 + i);
            instance.update(projectContestCPConfig);
            Assert.assertTrue("failt to update", Double.valueOf(100 + i) == instance.get(1).getTotalOriginalPayment());

        }

        printResult("ProjectContestCPConfigServiceImpl#update(ProjectContestCPConfig)", LOOP);
    }

    /**
     * <p>
     * Stress test for ProjectContestCPConfigServiceImple#get(long).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGet() throws Exception {
        ProjectContestCPConfig projectContestCPConfig = new ProjectContestCPConfig();
        projectContestCPConfig.setContestId(1);
        projectContestCPConfig.setCpRate(2);
        projectContestCPConfig.setTotalOriginalPayment(100);
        instance.create(projectContestCPConfig);
        start();
        for (int i = 0; i < LOOP; i++) {
            Assert.assertNotNull("fail to get", instance.get(1));
        }
        printResult("ProjectContestCPConfigServiceImpl#get(long)", LOOP);
    }

    /**
     * <p>
     * Stress test for ProjectContestCPConfigServiceImple#delete(long).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDelete() throws Exception {
        start();
        for (int i = 0; i < LOOP; i++) {
            ProjectContestCPConfig projectContestCPConfig = new ProjectContestCPConfig();
            projectContestCPConfig.setContestId(1 + i);
            projectContestCPConfig.setCpRate(2);
            projectContestCPConfig.setTotalOriginalPayment(100);
            instance.create(projectContestCPConfig);
            instance.delete(projectContestCPConfig.getContestId());
            Assert.assertNull("fail to delete", instance.get(1 + i));
        }

        printResult("ProjectContestCPConfigServiceImpl#delete(long)", LOOP);
    }
}
