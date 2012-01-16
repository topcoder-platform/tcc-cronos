/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.accuracy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.utilities.cp.entities.OriginalPayment;
import com.topcoder.utilities.cp.entities.ProjectContestCPConfig;
import com.topcoder.utilities.cp.services.ProjectContestCPConfigService;
import com.topcoder.utilities.cp.services.impl.ProjectContestCPConfigServiceImpl;

/**
 * <p>
 * Unit tests for {@link ProjectContestCPConfigServiceImpl} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectContestCPConfigServiceImplUnitTests extends BaseAccuracyUnitTests {
    /**
     * <p>
     * Represents the <code>ProjectContestCPConfigServiceImpl</code> instance used in tests.
     * </p>
     */
    private ProjectContestCPConfigService instance;

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
        instance = (ProjectContestCPConfigService) ACCURAY_APP_CONTEXT.getBean("projectContestCPConfigService");
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
     * Accuracy test for {@link ProjectContestCPConfigServiceImpl#ProjectContestCPConfigServiceImpl()}.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull(instance);
    }

    /**
     * <p>
     * Accuracy test for {@link ProjectContestCPConfigServiceImpl#create(ProjectContestCPConfig)}.
     * </p>
     */
    @Test
    public void testCreateAndGet() throws Exception {
        ProjectContestCPConfig config = new ProjectContestCPConfig();
        config.setContestId(4);
        config.setCpRate(1.5);
        OriginalPayment originalPayment = new OriginalPayment();
        originalPayment.setContestId(4);
        originalPayment.setPrize1st(500);
        originalPayment.setPrize2nd(250);
        originalPayment.setPrize3rd(100);
        originalPayment.setPrize4th(100);
        originalPayment.setPrize5th(100);
        originalPayment.setPrizeCopilot(150);
        originalPayment.setPrizeMilestone(50);
        originalPayment.setSpecReviewCost(50);
        originalPayment.setReviewCost(200);
        config.setOriginalPayment(originalPayment);
        instance.create(config);

        ProjectContestCPConfig newConfig = instance.get(4);
        assertEquals(4, newConfig.getContestId());
        assertTrue(1.5f == newConfig.getCpRate());

        OriginalPayment op = config.getOriginalPayment();
        assertEquals(originalPayment.getContestId(), op.getContestId());
        assertEquals(originalPayment.getPrize1st(), op.getPrize1st(), 0.001);
        assertEquals(originalPayment.getPrize2nd(), op.getPrize2nd(), 0.001);
        assertEquals(originalPayment.getPrize3rd(), op.getPrize3rd(), 0.001);
        assertEquals(originalPayment.getPrize4th(), op.getPrize4th(), 0.001);
        assertEquals(originalPayment.getPrize5th(), op.getPrize5th(), 0.001);
        assertEquals(originalPayment.getPrizeCopilot(), op.getPrizeCopilot(), 0.001);
        assertEquals(originalPayment.getPrizeMilestone(), op.getPrizeMilestone(), 0.001);
        assertEquals(originalPayment.getSpecReviewCost(), op.getSpecReviewCost(), 0.001);
        assertEquals(originalPayment.getReviewCost(), op.getReviewCost(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for {@link ProjectContestCPConfigServiceImpl#update(ProjectContestCPConfig)}.
     * </p>
     */
    @Test
    public void testUpdate() throws Exception {
        ProjectContestCPConfig config = new ProjectContestCPConfig();
        config.setContestId(4);
        config.setCpRate(1.5);
        OriginalPayment originalPayment = new OriginalPayment();
        originalPayment.setContestId(4);
        originalPayment.setPrize1st(500);
        originalPayment.setPrize2nd(250);
        originalPayment.setPrize3rd(100);
        originalPayment.setPrize4th(100);
        originalPayment.setPrize5th(100);
        originalPayment.setPrizeCopilot(150);
        originalPayment.setPrizeMilestone(50);
        originalPayment.setSpecReviewCost(50);
        originalPayment.setReviewCost(200);
        config.setOriginalPayment(originalPayment);
        instance.create(config);

        config.setCpRate(2.5);
        config.getOriginalPayment().setPrize1st(700);
        config.getOriginalPayment().setPrize2nd(350);
        instance.update(config);

        ProjectContestCPConfig newConfig = instance.get(4);
        assertEquals(4, newConfig.getContestId());
        assertTrue(2.5f == newConfig.getCpRate());

        OriginalPayment op = config.getOriginalPayment();
        assertEquals(originalPayment.getContestId(), op.getContestId());
        assertEquals(700, op.getPrize1st(), 0.001);
        assertEquals(350, op.getPrize2nd(), 0.001);
        assertEquals(originalPayment.getPrize3rd(), op.getPrize3rd(), 0.001);
        assertEquals(originalPayment.getPrize4th(), op.getPrize4th(), 0.001);
        assertEquals(originalPayment.getPrize5th(), op.getPrize5th(), 0.001);
        assertEquals(originalPayment.getPrizeCopilot(), op.getPrizeCopilot(), 0.001);
        assertEquals(originalPayment.getPrizeMilestone(), op.getPrizeMilestone(), 0.001);
        assertEquals(originalPayment.getSpecReviewCost(), op.getSpecReviewCost(), 0.001);
        assertEquals(originalPayment.getReviewCost(), op.getReviewCost(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for {@link ProjectContestCPConfigServiceImpl#delete(long)}.
     * </p>
     */
    @Test
    public void testDelete() throws Exception {
        assertNotNull(instance.get(1));
        instance.delete(1);
        assertNull(instance.get(1));
    }

    /**
     * <p>
     * Accuracy test for {@link ProjectContestCPConfigServiceImpl#get(long)}.
     * </p>
     */
    @Test
    public void testGet() throws Exception {
        assertNull(instance.get(5));
    }
}
