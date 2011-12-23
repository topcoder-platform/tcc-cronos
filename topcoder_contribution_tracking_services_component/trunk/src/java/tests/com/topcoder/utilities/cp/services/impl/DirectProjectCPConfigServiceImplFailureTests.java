/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services.impl;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.utilities.cp.entities.DirectProjectCPConfig;
import com.topcoder.utilities.cp.services.ContributionServiceEntityNotFoundException;
import com.topcoder.utilities.cp.services.ContributionServiceException;
import com.topcoder.utilities.cp.services.ContributionServiceInitializationException;
import com.topcoder.utilities.cp.services.DirectProjectCPConfigService;
import com.topcoder.utilities.cp.services.failuretests.impl.BaseFailureTests;

/**
 * <p>
 * Failure test for DirectProjectCPConfigServiceImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DirectProjectCPConfigServiceImplFailureTests extends BaseFailureTests {
    /**
     * <p>
     * Represents the instance of DirectProjectCPConfigServiceImpl used in test.
     * </p>
     */
    private DirectProjectCPConfigService instance;

    /**
     * <p>
     * Represents the instance of DirectProjectCPConfig used in tests.
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
        return new JUnit4TestAdapter(DirectProjectCPConfigServiceImplFailureTests.class);
    }

    /**
     * <p>
     * Sets up for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();

        instance = (DirectProjectCPConfigService) APP_CONTEXT.getBean("directProjectCPConfigService");

        config = new DirectProjectCPConfig();
        config.setAvailableImmediateBudget(500);
        config.setDirectProjectId(1);
        config.setUseCP(true);
    }

    /**
     * <p>
     * Tears down for each test.
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
        new DirectProjectCPConfigServiceImpl().checkInit();
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
        new DirectProjectCPConfigServiceImpl().setLoggerName(" \r\n\t ");
    }

    /**
     * <p>
     * Failure test for create(DirectProjectCPConfig config). When config is null, throws
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
     * Failure test for create(DirectProjectCPConfig config). When config's directProjectId is negative,
     * throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreate_DirectProjectIdIsNegative() throws Exception {
        config.setDirectProjectId(-1);
        instance.create(config);
    }

    /**
     * <p>
     * Failure test for create(DirectProjectCPConfig config). When config's directProjectId is zero, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreate_DirectProjectIdIsZero() throws Exception {
        config.setDirectProjectId(0);
        instance.create(config);
    }

    /**
     * <p>
     * Failure test for create(DirectProjectCPConfig config). When error from hibernate, throws
     * ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void test_create_Error() throws Exception {
        DirectProjectCPConfigService invalidService = (DirectProjectCPConfigService) APP_CONTEXT_INVALID
            .getBean("directProjectCPConfigService");
        invalidService.create(config);
    }

    /**
     * <p>
     * Failure test for update(DirectProjectCPConfig config). When config is null, throws
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
     * Failure test for update(DirectProjectCPConfig config). When config's directProjectId is negative,
     * throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_DirectProjectIdIsNegative() throws Exception {
        config.setDirectProjectId(-1);
        instance.update(config);
    }

    /**
     * <p>
     * Failure test for update(DirectProjectCPConfig config). When config's directProjectId is zero, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_DirectProjectIdIsZero() throws Exception {
        config.setDirectProjectId(0);
        instance.update(config);
    }

    /**
     * <p>
     * Failure test for update(DirectProjectCPConfig config). When directProjectId cannot be found, throws
     * ContributionServiceEntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceEntityNotFoundException.class)
    public void testUpdate_DirectProjectIdNotFound() throws Exception {
        instance.update(config);
    }

    /**
     * <p>
     * Failure test for update(DirectProjectCPConfig config). When error from hibernate, throws
     * ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void testUpdate_HibernateError() throws Exception {
        instance.create(config);

        DirectProjectCPConfigService invalidService = (DirectProjectCPConfigService) APP_CONTEXT_INVALID
            .getBean("directProjectCPConfigService");

        invalidService.update(config);
    }

    /**
     * <p>
     * Failure test for delete(long directProjectId). When directProjectId is negative, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDelete_DirectProjectIdIsNegative() throws Exception {
        instance.delete(-1);
    }

    /**
     * <p>
     * Failure test for delete(long directProjectId). When directProjectId is zero, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDelete_DirectProjectIdIsZero() throws Exception {
        instance.delete(0);
    }

    /**
     * <p>
     * Failure test for delete(long directProjectId). When directProjectId cannot be found, throws
     * ContributionServiceEntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceEntityNotFoundException.class)
    public void testDelete_DirectProjectIdNotFound() throws Exception {
        instance.delete(Long.MAX_VALUE);
    }

    /**
     * <p>
     * Failure test for delete(long directProjectId). When error from hibernate, throws
     * ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void testDelete_HibernateError() throws Exception {
        long contestId = instance.create(config);

        DirectProjectCPConfigService invalidService = (DirectProjectCPConfigService) APP_CONTEXT_INVALID
            .getBean("directProjectCPConfigService");

        invalidService.delete(contestId);
    }

    /**
     * <p>
     * Failure test for get(long directProjectId). When directProjectId is negative, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGet_DirectProjectIdIsNegative() throws Exception {
        instance.get(-1);
    }

    /**
     * <p>
     * Failure test for get(long directProjectId). When directProjectId is zero, throws
     * IllegalArgumentException.
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
     * Failure test for get(long directProjectId). When error from hibernate, throws
     * ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void testGet_HibernateError() throws Exception {
        long contestId = instance.create(config);
        DirectProjectCPConfigService invalidService = (DirectProjectCPConfigService) APP_CONTEXT_INVALID
            .getBean("directProjectCPConfigService");
        invalidService.get(contestId);
    }

    /**
     * <p>
     * Failure test for getAvailableInitialPayments(long directProjetId). When directProjetId is negative,
     * throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAvailableInitialPayments_DirectProjectIdIsNegative() throws Exception {
        instance.getAvailableInitialPayments(-1);
    }

    /**
     * <p>
     * Failure test for getAvailableInitialPayments(long directProjetId). When directProjetId is zero, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAvailableInitialPayments_ContestIdIsZero() throws Exception {
        instance.getAvailableInitialPayments(0);
    }

    /**
     * <p>
     * Failure test for getAvailableInitialPayments(long directProjetId). When error from hibernate, throws
     * ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void testGetAvailableInitialPayments_HibernateError() throws Exception {
        long directProjetId = instance.create(config);
        DirectProjectCPConfigService invalidService = (DirectProjectCPConfigService) APP_CONTEXT_INVALID
            .getBean("directProjectCPConfigService");
        invalidService.getAvailableInitialPayments(directProjetId);
    }
}