/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.stresstests;

import org.junit.Assert;

import com.topcoder.utilities.cp.entities.OriginalPayment;
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

            OriginalPayment originalPayment = new OriginalPayment();
            originalPayment.setContestId(1);
            originalPayment.setPrize1st(500);
            originalPayment.setPrize2nd(250);
            originalPayment.setPrize3rd(100);
            originalPayment.setPrize4th(100);
            originalPayment.setPrize5th(100);
            originalPayment.setPrizeCopilot(150);
            originalPayment.setPrizeMilestone(50);
            originalPayment.setSpecReviewCost(50);
            originalPayment.setReviewCost(200);
            projectContestCPConfig.setOriginalPayment(originalPayment);

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

        OriginalPayment originalPayment = new OriginalPayment();
        originalPayment.setContestId(1);
        originalPayment.setPrize1st(500);
        originalPayment.setPrize2nd(250);
        originalPayment.setPrize3rd(100);
        originalPayment.setPrize4th(100);
        originalPayment.setPrize5th(100);
        originalPayment.setPrizeCopilot(150);
        originalPayment.setPrizeMilestone(50);
        originalPayment.setSpecReviewCost(50);
        originalPayment.setReviewCost(200);
        projectContestCPConfig.setOriginalPayment(originalPayment);
        instance.create(projectContestCPConfig);
        start();
        for (int i = 0; i < LOOP; i++) {
            projectContestCPConfig.getOriginalPayment().setPrize1st(500 + i);
            instance.update(projectContestCPConfig);
            Assert.assertTrue("failt to update", Double.valueOf(500 + i)
                == instance.get(1).getOriginalPayment().getPrize1st());

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
        OriginalPayment originalPayment = new OriginalPayment();
        originalPayment.setContestId(1);
        originalPayment.setPrize1st(500);
        originalPayment.setPrize2nd(250);
        originalPayment.setPrize3rd(100);
        originalPayment.setPrize4th(100);
        originalPayment.setPrize5th(100);
        originalPayment.setPrizeCopilot(150);
        originalPayment.setPrizeMilestone(50);
        originalPayment.setSpecReviewCost(50);
        originalPayment.setReviewCost(200);
        projectContestCPConfig.setOriginalPayment(originalPayment);
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
            OriginalPayment originalPayment = new OriginalPayment();
            originalPayment.setContestId(1 + i);
            originalPayment.setPrize1st(500);
            originalPayment.setPrize2nd(250);
            originalPayment.setPrize3rd(100);
            originalPayment.setPrize4th(100);
            originalPayment.setPrize5th(100);
            originalPayment.setPrizeCopilot(150);
            originalPayment.setPrizeMilestone(50);
            originalPayment.setSpecReviewCost(50);
            originalPayment.setReviewCost(200);
            projectContestCPConfig.setOriginalPayment(originalPayment);
            instance.create(projectContestCPConfig);

            instance.delete(projectContestCPConfig.getContestId());
            Assert.assertNull("fail to delete", instance.get(1 + i));
        }

        printResult("ProjectContestCPConfigServiceImpl#delete(long)", LOOP);
    }
}
