/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services.impl;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.utilities.cp.entities.ProjectContestCPConfig;
import com.topcoder.utilities.cp.entities.OriginalPayment;
import com.topcoder.utilities.cp.services.ContributionServiceEntityNotFoundException;
import com.topcoder.utilities.cp.services.ContributionServiceException;
import com.topcoder.utilities.cp.services.ContributionServiceInitializationException;
import com.topcoder.utilities.cp.services.ProjectContestCPConfigService;
import com.topcoder.utilities.cp.services.failuretests.impl.BaseFailureTests;

/**
 * <p>
 * Failure test for ProjectContestCPConfigServiceImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectContestCPConfigServiceImplFailureTests extends BaseFailureTests {
    /**
     * <p>
     * Represents the instance of ProjectContestCPConfigServiceImpl used in test.
     * </p>
     */
    private ProjectContestCPConfigService instance;

    /**
     * <p>
     * Represents the instance of ProjectContestCPConfig used in test.
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
        return new JUnit4TestAdapter(ProjectContestCPConfigServiceImplFailureTests.class);
    }

    /**
     * <p>
     * Sets up for each tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        instance = (ProjectContestCPConfigService) APP_CONTEXT.getBean("projectContestCPConfigService");

        config = new ProjectContestCPConfig();
        config.setContestId(1);
        config.setCpRate(2);

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
        config.setOriginalPayment(originalPayment);
    }

    /**
     * <p>
     * Tear down for each tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();

        instance = null;
    }

    /**
     * <p>
     * Failure test for checkInit(). When sessionFactory is null, throws
     * ContributionServiceInitializationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceInitializationException.class)
    public void testCheckInit_configIsNull() throws Exception {
        new ProjectContestCPConfigServiceImpl().checkInit();
    }

    /**
     * <p>
     * Failure test for setLoggerName(String loggerName). When loggerName is empty, throws
     * ContributionServiceInitializationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceInitializationException.class)
    public void testSetLoggerName_LoggerNameIsEmpty() throws Exception {
        new ProjectContestCPConfigServiceImpl().setLoggerName(" \r\n\t ");
    }

    /**
     * <p>
     * Failure test for create(ProjectContestCPConfig config). When config is null, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreate_configIsNull() throws Exception {
        config = null;
        instance.create(config);
    }

    /**
     * <p>
     * Failure test for create(ProjectContestCPConfig config). When config's contestId is null, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreate_ContestIdIsNegative() throws Exception {
        config.setContestId(-1);
        instance.create(config);
    }

    /**
     * <p>
     * Failure test for create(ProjectContestCPConfig config). When config's contestId is zero, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreate_ContestIdIsZero() throws Exception {
        config.setContestId(0);
        instance.create(config);
    }

    /**
     * <p>
     * Failure test for create(ProjectContestCPConfig config). When error from hibernate, throws
     * ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void test_create_Error() throws Exception {
        ProjectContestCPConfigService invalidService = (ProjectContestCPConfigService) APP_CONTEXT_INVALID
            .getBean("projectContestCPConfigService");
        invalidService.create(config);
    }

    /**
     * <p>
     * Failure test for update(ProjectContestCPConfig config). When config is null, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_ConfigIsNull() throws Exception {
        config = null;
        instance.update(config);
    }

    /**
     * <p>
     * Failure test for update(ProjectContestCPConfig config). When config's contestId is negative, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_ContestIdIsNegative() throws Exception {
        config.setContestId(-1);
        instance.update(config);
    }

    /**
     * <p>
     * Failure test for update(ProjectContestCPConfig config). When config's contestId is zero, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_ContestIdIsZero() throws Exception {
        config.setContestId(0);
        instance.update(config);
    }

    /**
     * <p>
     * Failure test for update(ProjectContestCPConfig config). When contestId cannot be found, throws
     * ContributionServiceEntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceEntityNotFoundException.class)
    public void testUpdate_ContestIdNotFound() throws Exception {
        instance.update(config);
    }

    /**
     * <p>
     * Failure test for update(ProjectContestCPConfig config). When error from hibernate, throws
     * ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void testUpdate_HibernateError() throws Exception {
        instance.create(config);

        ProjectContestCPConfigService invalidService = (ProjectContestCPConfigService) APP_CONTEXT_INVALID
            .getBean("projectContestCPConfigService");

        invalidService.update(config);
    }

    /**
     * <p>
     * Failure test for delete(long contestId). When contestId is negative, throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDelete_ContestIdIsNegative() throws Exception {
        instance.delete(-1);
    }

    /**
     * <p>
     * Failure test for delete(long contestId). When contestId is zero, throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDelete_ContestIdIsZero() throws Exception {
        instance.delete(0);
    }

    /**
     * <p>
     * Failure test for delete(long contestId). When contestId cannot be found, throws
     * ContributionServiceEntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceEntityNotFoundException.class)
    public void testDelete_ContestIdNotFound() throws Exception {
        instance.delete(Long.MAX_VALUE);
    }

    /**
     * <p>
     * Failure test for delete(long contestId). When error from hibernate, throws
     * ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void testDelete_HibernateError() throws Exception {
        long contestId = instance.create(config);

        ProjectContestCPConfigService invalidService = (ProjectContestCPConfigService) APP_CONTEXT_INVALID
            .getBean("projectContestCPConfigService");

        invalidService.delete(contestId);
    }

    /**
     * <p>
     * Failure test for get(long contestId). When contestId is negative, throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGet_ContestIdIsNegative() throws Exception {
        instance.get(-1);
    }

    /**
     * <p>
     * Failure test for get(long contestId). When contestId is zero, throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGet_ContestIdIsZero() throws Exception {
        instance.get(0);
    }

    /**
     * <p>
     * Failure test for get(long contestId). When error from hibernate, throws ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void testGet_HibernateError() throws Exception {
        long contestId = instance.create(config);
        ProjectContestCPConfigService invalidService = (ProjectContestCPConfigService) APP_CONTEXT_INVALID
            .getBean("projectContestCPConfigService");
        invalidService.get(contestId);
    }
}