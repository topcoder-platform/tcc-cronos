/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services.impl;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.utilities.cp.entities.MemberContributionPoints;
import com.topcoder.utilities.cp.services.ContributionServiceEntityNotFoundException;
import com.topcoder.utilities.cp.services.ContributionServiceException;
import com.topcoder.utilities.cp.services.ContributionServiceInitializationException;
import com.topcoder.utilities.cp.services.MemberContributionPointsService;
import com.topcoder.utilities.cp.services.failuretests.impl.BaseFailureTests;
import com.topcoder.utilities.cp.services.impl.MemberContributionPointsServiceImpl;

/**
 * <p>
 * Failure test for MemberContributionPointsServiceImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberContributionPointsServiceImplFailureTests extends BaseFailureTests {
    /**
     * <p>
     * Represents the instance of MemberContributionPointsServiceImpl used in test.
     * </p>
     */
    private MemberContributionPointsService instance;

    /**
     * <p>
     * Represents the instance of MemberContributionPoints used in test.
     * </p>
     */
    private MemberContributionPoints memberContributionPoints;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(MemberContributionPointsServiceImplFailureTests.class);
    }

    /**
     * <p>
     * Sets up for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        instance = (MemberContributionPointsService) APP_CONTEXT.getBean("memberContributionPointsService");

        memberContributionPoints = new MemberContributionPoints();
        memberContributionPoints.setUserId(1);
        memberContributionPoints.setContestId(1);
        memberContributionPoints.setContributionPoints(500);
        memberContributionPoints.setContributionType("t1");
    }

    /**
     * <p>
     * Tears down for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
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
        new MemberContributionPointsServiceImpl().checkInit();
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
        new MemberContributionPointsServiceImpl().setLoggerName(" \r\n\t ");
    }

    /**
     * <p>
     * Failure test for create(MemberContributionPoints memberContributionPoints). When
     * memberContributionPoints is null, throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreate_MemberContributionPointsIsNull() throws Exception {
        memberContributionPoints = null;
        instance.create(memberContributionPoints);
    }

    /**
     * <p>
     * Failure test for create(MemberContributionPoints memberContributionPoints). When
     * memberContributionPoints'id is positive, throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreate_IdIsNegative() throws Exception {
        memberContributionPoints.setId(1);
        instance.create(memberContributionPoints);
    }

    /**
     * <p>
     * Failure test for create(MemberContributionPoints memberContributionPoints). When error from hibernate,
     * throws ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void test_create_Error() throws Exception {
        MemberContributionPointsService invalidService = (MemberContributionPointsService) APP_CONTEXT_INVALID
            .getBean("memberContributionPointsService");
        invalidService.create(memberContributionPoints);
    }

    /**
     * <p>
     * Failure test for update(MemberContributionPoints memberContributionPoints). When
     * memberContributionPoints is null, throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_MemberContributionPointsIsNull() throws Exception {
        memberContributionPoints = null;
        instance.update(memberContributionPoints);
    }

    /**
     * <p>
     * Failure test for update(MemberContributionPoints memberContributionPoints). When
     * memberContributionPoints's id is negative, throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_IdIsNegative() throws Exception {
        memberContributionPoints.setId(-1);
        instance.update(memberContributionPoints);
    }

    /**
     * <p>
     * Failure test for update(MemberContributionPoints memberContributionPoints). When id cannot be found,
     * throws ContributionServiceEntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceEntityNotFoundException.class)
    public void testUpdate_IdNotFound() throws Exception {
        memberContributionPoints.setId(1);
        instance.update(memberContributionPoints);
    }

    /**
     * <p>
     * Failure test for update(MemberContributionPoints memberContributionPoints). When error from hibernate,
     * throws ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void testUpdate_HibernateError() throws Exception {
        instance.create(memberContributionPoints);

        MemberContributionPointsService invalidService = (MemberContributionPointsService) APP_CONTEXT_INVALID
            .getBean("memberContributionPointsService");

        invalidService.update(memberContributionPoints);
    }

    /**
     * <p>
     * Failure test for delete(long id). When id is negative, throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDelete_IdIsNegative() throws Exception {
        instance.delete(-1);
    }

    /**
     * <p>
     * Failure test for delete(long id). When id is zero, throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDelete_IdIsZero() throws Exception {
        instance.delete(0);
    }

    /**
     * <p>
     * Failure test for delete(long id). When id cannot be found, throws
     * ContributionServiceEntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceEntityNotFoundException.class)
    public void testDelete_IdNotFound() throws Exception {
        instance.delete(Long.MAX_VALUE);
    }

    /**
     * <p>
     * Failure test for delete(long id). When error from hibernate, throws ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void testDelete_HibernateError() throws Exception {
        long id = instance.create(memberContributionPoints);

        MemberContributionPointsService invalidService = (MemberContributionPointsService) APP_CONTEXT_INVALID
            .getBean("memberContributionPointsService");

        invalidService.delete(id);
    }

    /**
     * <p>
     * Failure test for getByUserAndContest(long userId, long contestId). When userId is negative, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetByUserAndContest_UserIdIsNegative() throws Exception {
        instance.getByUserAndContest(-1, 1);
    }

    /**
     * <p>
     * Failure test for getByUserAndContest(long userId, long contestId). When userId is zero, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetByUserAndContest_UserIdIsZero() throws Exception {
        instance.getByUserAndContest(0, 1);
    }

    /**
     * <p>
     * Failure test for getByUserAndContest(long userId, long contestId). When contestId is negative, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetByUserAndContest_ContestIdIsNegative() throws Exception {
        instance.getByUserAndContest(1, -1);
    }

    /**
     * <p>
     * Failure test for getByUserAndContest(long userId, long contestId). When contestId is zero, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetByUserAndContest_ContestIdIsZero() throws Exception {
        instance.getByUserAndContest(1, 0);
    }

    /**
     * <p>
     * Failure test for getByUserAndContest(long userId, long contestId). When error from hibernate, throws
     * ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void testGetByUserAndContest_HibernateError() throws Exception {
        MemberContributionPointsService invalidService = (MemberContributionPointsService) APP_CONTEXT_INVALID
            .getBean("memberContributionPointsService");
        invalidService.getByUserAndContest(1, 1);
    }

    /**
     * <p>
     * Failure test for getByUserId(long userId). When userId is negative, throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetByUserId_UserIdIsNegative() throws Exception {
        instance.getByUserId(-1);
    }

    /**
     * <p>
     * Failure test for getByUserId(long userId). When userId is zero, throws IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetByUserId_UserIdIsZero() throws Exception {
        instance.getByUserId(0);
    }

    /**
     * <p>
     * Failure test for getByUserId(long userId). When error from hibernate, throws
     * ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void testGetByUserId_HibernateError() throws Exception {
        MemberContributionPointsService invalidService = (MemberContributionPointsService) APP_CONTEXT_INVALID
            .getBean("memberContributionPointsService");
        invalidService.getByUserId(1);
    }

    /**
     * <p>
     * Failure test for getByProjectId(long directProjectId). When directProjectId is negative, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetByProjectId_DirectProjectIdIsNegative() throws Exception {
        instance.getByProjectId(-1);
    }

    /**
     * <p>
     * Failure test for getByProjectId(long directProjectId). When directProjectId is zero, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetByProjectId_DirectProjectIdIsZero() throws Exception {
        instance.getByProjectId(0);
    }

    /**
     * <p>
     * Failure test for getByProjectId(long directProjectId). When error from hibernate, throws
     * ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void testGetByProjectId_HibernateError() throws Exception {
        MemberContributionPointsService invalidService = (MemberContributionPointsService) APP_CONTEXT_INVALID
            .getBean("memberContributionPointsService");
        invalidService.getByProjectId(1);
    }

    /**
     * <p>
     * Failure test for getByContestId(long contestId). When directProjectId is negative, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetByContestId_DirectProjectIdIsNegative() throws Exception {
        instance.getByContestId(-1);
    }

    /**
     * <p>
     * Failure test for getByContestId(long contestId). When directProjectId is zero, throws
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetByContestId_DirectProjectIdIsZero() throws Exception {
        instance.getByContestId(0);
    }

    /**
     * <p>
     * Failure test for getByContestId(long contestId). When error from hibernate, throws
     * ContributionServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContributionServiceException.class)
    public void testGetByContestId_HibernateError() throws Exception {
        MemberContributionPointsService invalidService = (MemberContributionPointsService) APP_CONTEXT_INVALID
            .getBean("memberContributionPointsService");
        invalidService.getByContestId(1);
    }

}