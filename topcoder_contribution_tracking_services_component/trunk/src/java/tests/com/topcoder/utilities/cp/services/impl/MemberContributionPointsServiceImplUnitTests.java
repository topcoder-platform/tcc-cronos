/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.utilities.cp.BaseUnitTests;
import com.topcoder.utilities.cp.entities.MemberContributionPoints;
import com.topcoder.utilities.cp.services.ContributionServiceEntityNotFoundException;
import com.topcoder.utilities.cp.services.ContributionServiceException;
import com.topcoder.utilities.cp.services.MemberContributionPointsService;

/**
 * <p>
 * Unit tests for {@link MemberContributionPointsServiceImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class MemberContributionPointsServiceImplUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>MemberContributionPointsServiceImpl</code> instance used in tests.
     * </p>
     */
    private MemberContributionPointsService instance;

    /**
     * <p>
     * Represents the session factory used in tests.
     * </p>
     */
    private SessionFactory sessionFactory;

    /**
     * <p>
     * Represents the member contribution points used in tests.
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
        return new JUnit4TestAdapter(MemberContributionPointsServiceImplUnitTests.class);
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

        instance = (MemberContributionPointsService) APP_CONTEXT.getBean("memberContributionPointsService");

        memberContributionPoints = new MemberContributionPoints();
        memberContributionPoints.setUserId(1);
        memberContributionPoints.setContestId(1);
        memberContributionPoints.setContributionPoints(10);
        memberContributionPoints.setContributionType("ct1");
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
     * Accuracy test for the constructor <code>MemberContributionPointsServiceImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new MemberContributionPointsServiceImpl();

        assertNull("'log' should be correct.", BaseUnitTests.getField(instance, "log"));
        assertNull("'sessionFactory' should be correct.", BaseUnitTests.getField(instance, "sessionFactory"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getByUserAndContest(long userId, long contestId)</code> with 1 item.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getByUserAndContest_1() throws Exception {
        instance.create(memberContributionPoints);

        List<MemberContributionPoints> res = instance.getByUserAndContest(1, 1);

        assertEquals("'getByUserAndContest' should be correct.", 1, res.size());

        MemberContributionPoints entity = res.get(0);
        assertEquals("'getByUserAndContest' should be correct.", memberContributionPoints.getId(), entity.getId());
        assertEquals("'getByUserAndContest' should be correct.", memberContributionPoints.getUserId(), entity
            .getUserId());
        assertEquals("'getByUserAndContest' should be correct.", memberContributionPoints.getContestId(), entity
            .getContestId());
        assertEquals("'getByUserAndContest' should be correct.", memberContributionPoints.getContributionPoints(),
            entity.getContributionPoints(), 0.001);
        assertEquals("'getByUserAndContest' should be correct.", memberContributionPoints.getContributionType(),
            entity.getContributionType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getByUserAndContest(long userId, long contestId)</code> with 2 items.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getByUserAndContest_2() throws Exception {
        instance.create(memberContributionPoints);

        memberContributionPoints.setId(0);
        instance.create(memberContributionPoints);

        List<MemberContributionPoints> res = instance.getByUserAndContest(1, 1);

        assertEquals("'getByUserAndContest' should be correct.", 2, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getByUserAndContest(long userId, long contestId)</code> with no result.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getByUserAndContest_3() throws Exception {
        List<MemberContributionPoints> res = instance.getByUserAndContest(Long.MAX_VALUE, Long.MAX_VALUE);

        assertEquals("'getByUserAndContest' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getByUserAndContest(long userId, long contestId)</code> with userId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getByUserAndContest_userIdNegative() throws Exception {
        instance.getByUserAndContest(-1, 1);
    }

    /**
     * <p>
     * Failure test for the method <code>getByUserAndContest(long userId, long contestId)</code> with userId is
     * zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getByUserAndContest_userIdZero() throws Exception {
        instance.getByUserAndContest(0, 1);
    }

    /**
     * <p>
     * Failure test for the method <code>getByUserAndContest(long userId, long contestId)</code> with contestId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getByUserAndContest_contestIdNegative() throws Exception {
        instance.getByUserAndContest(1, -1);
    }

    /**
     * <p>
     * Failure test for the method <code>getByUserAndContest(long userId, long contestId)</code> with contestId is
     * zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getByUserAndContest_contestIdZero() throws Exception {
        instance.getByUserAndContest(1, 0);
    }

    /**
     * <p>
     * Failure test for the method <code>getByUserAndContest(long userId, long contestId)</code> with an error
     * occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_getByUserAndContest_Error() throws Exception {
        instance.create(memberContributionPoints);

        instance = (MemberContributionPointsService) APP_CONTEXT_INVALID.getBean("memberContributionPointsService");

        instance.getByUserAndContest(1, 1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getByUserId(long userId)</code> with 1 item.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getByUserId_1() throws Exception {
        instance.create(memberContributionPoints);

        List<MemberContributionPoints> res = instance.getByUserId(1);

        assertEquals("'getByUserId' should be correct.", 1, res.size());

        MemberContributionPoints entity = res.get(0);
        assertEquals("'getByUserId' should be correct.", memberContributionPoints.getId(), entity.getId());
        assertEquals("'getByUserId' should be correct.", memberContributionPoints.getUserId(), entity.getUserId());
        assertEquals("'getByUserId' should be correct.", memberContributionPoints.getContestId(), entity
            .getContestId());
        assertEquals("'getByUserId' should be correct.", memberContributionPoints.getContributionPoints(), entity
            .getContributionPoints(), 0.001);
        assertEquals("'getByUserId' should be correct.", memberContributionPoints.getContributionType(), entity
            .getContributionType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getByUserId(long userId)</code> with 2 items.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getByUserId_2() throws Exception {
        instance.create(memberContributionPoints);

        memberContributionPoints.setId(0);
        instance.create(memberContributionPoints);

        List<MemberContributionPoints> res = instance.getByUserId(1);

        assertEquals("'getByUserId' should be correct.", 2, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getByUserId(long userId)</code> with no result.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getByUserId_3() throws Exception {
        List<MemberContributionPoints> res = instance.getByUserId(Long.MAX_VALUE);

        assertEquals("'getByUserId' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getByUserId(long userId)</code> with userId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getByUserId_userIdNegative() throws Exception {
        instance.getByUserId(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>getByUserId(long userId)</code> with userId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getByUserId_userIdZero() throws Exception {
        instance.getByUserId(0);
    }

    /**
     * <p>
     * Failure test for the method <code>getByUserId(long userId)</code> with an error occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_getByUserId_Error() throws Exception {
        instance.create(memberContributionPoints);

        instance = (MemberContributionPointsService) APP_CONTEXT_INVALID.getBean("memberContributionPointsService");

        instance.getByUserId(1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getByProjectId(long directProjectId)</code> with 1 item.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getByProjectId_1() throws Exception {
        instance.create(memberContributionPoints);

        List<MemberContributionPoints> res = instance.getByProjectId(1);

        assertEquals("'getByProjectId' should be correct.", 1, res.size());

        MemberContributionPoints entity = res.get(0);
        assertEquals("'getByProjectId' should be correct.", memberContributionPoints.getId(), entity.getId());
        assertEquals("'getByProjectId' should be correct.", memberContributionPoints.getUserId(), entity.getUserId());
        assertEquals("'getByProjectId' should be correct.", memberContributionPoints.getContestId(), entity
            .getContestId());
        assertEquals("'getByProjectId' should be correct.", memberContributionPoints.getContributionPoints(), entity
            .getContributionPoints(), 0.001);
        assertEquals("'getByProjectId' should be correct.", memberContributionPoints.getContributionType(), entity
            .getContributionType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getByProjectId(long directProjectId)</code> with 2 items.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getByProjectId_2() throws Exception {
        instance.create(memberContributionPoints);

        memberContributionPoints.setId(0);
        instance.create(memberContributionPoints);

        List<MemberContributionPoints> res = instance.getByProjectId(1);

        assertEquals("'getByProjectId' should be correct.", 2, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getByProjectId(long directProjectId)</code> with no result.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getByProjectId_3() throws Exception {
        List<MemberContributionPoints> res = instance.getByProjectId(Long.MAX_VALUE);

        assertEquals("'getByProjectId' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getByProjectId(long directProjectId)</code> with directProjectId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getByProjectId_directProjectIdNegative() throws Exception {
        instance.getByProjectId(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>getByProjectId(long directProjectId)</code> with directProjectId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getByProjectId_directProjectIdZero() throws Exception {
        instance.getByProjectId(0);
    }

    /**
     * <p>
     * Failure test for the method <code>getByProjectId(long directProjectId)</code> with an error occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_getByProjectId_Error() throws Exception {
        instance.create(memberContributionPoints);

        instance = (MemberContributionPointsService) APP_CONTEXT_INVALID.getBean("memberContributionPointsService");

        instance.getByProjectId(1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getByContestId(long contestId)</code> with 1 item.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getByContestId_1() throws Exception {
        instance.create(memberContributionPoints);

        List<MemberContributionPoints> res = instance.getByContestId(1);

        assertEquals("'getByContestId' should be correct.", 1, res.size());

        MemberContributionPoints entity = res.get(0);
        assertEquals("'getByContestId' should be correct.", memberContributionPoints.getId(), entity.getId());
        assertEquals("'getByContestId' should be correct.", memberContributionPoints.getUserId(), entity.getUserId());
        assertEquals("'getByContestId' should be correct.", memberContributionPoints.getContestId(), entity
            .getContestId());
        assertEquals("'getByContestId' should be correct.", memberContributionPoints.getContributionPoints(), entity
            .getContributionPoints(), 0.001);
        assertEquals("'getByContestId' should be correct.", memberContributionPoints.getContributionType(), entity
            .getContributionType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getByContestId(long contestId)</code> with 2 items.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getByContestId_2() throws Exception {
        instance.create(memberContributionPoints);

        memberContributionPoints.setId(0);
        instance.create(memberContributionPoints);

        List<MemberContributionPoints> res = instance.getByContestId(1);

        assertEquals("'getByContestId' should be correct.", 2, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getByContestId(long contestId)</code> with no result.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getByContestId_3() throws Exception {
        List<MemberContributionPoints> res = instance.getByContestId(Long.MAX_VALUE);

        assertEquals("'getByContestId' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getByContestId(long contestId)</code> with contestId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getByContestId_contestIdNegative() throws Exception {
        instance.getByContestId(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>getByContestId(long contestId)</code> with contestId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getByContestId_contestIdZero() throws Exception {
        instance.getByContestId(0);
    }

    /**
     * <p>
     * Failure test for the method <code>getByContestId(long contestId)</code> with an error occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_getByContestId_Error() throws Exception {
        instance.create(memberContributionPoints);

        instance = (MemberContributionPointsService) APP_CONTEXT_INVALID.getBean("memberContributionPointsService");

        instance.getByContestId(1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>create(MemberContributionPoints memberContributionPoints)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_create() throws Exception {
        long res = instance.create(memberContributionPoints);

        assertEquals("'create' should be correct.", memberContributionPoints.getId(), res);

        MemberContributionPoints entity = getEntity(sessionFactory, MemberContributionPoints.class, res);

        assertEquals("'create' should be correct.", memberContributionPoints.getId(), entity.getId());
        assertEquals("'create' should be correct.", memberContributionPoints.getUserId(), entity.getUserId());
        assertEquals("'create' should be correct.", memberContributionPoints.getContestId(), entity.getContestId());
        assertEquals("'create' should be correct.", memberContributionPoints.getContributionPoints(), entity
            .getContributionPoints(), 0.001);
        assertEquals("'create' should be correct.", memberContributionPoints.getContributionType(), entity
            .getContributionType());
    }

    /**
     * <p>
     * Failure test for the method <code>create(MemberContributionPoints memberContributionPoints)</code> with
     * memberContributionPoints is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_create_memberContributionPointsNull() throws Exception {
        memberContributionPoints = null;

        instance.create(memberContributionPoints);
    }

    /**
     * <p>
     * Failure test for the method <code>create(MemberContributionPoints memberContributionPoints)</code> with
     * memberContributionPoints#id is positive.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_create_idPositive() throws Exception {
        memberContributionPoints.setId(1);

        instance.create(memberContributionPoints);
    }

    /**
     * <p>
     * Failure test for the method <code>create(MemberContributionPoints memberContributionPoints)</code> with an
     * error occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_create_Error() throws Exception {
        instance = (MemberContributionPointsService) APP_CONTEXT_INVALID.getBean("memberContributionPointsService");

        instance.create(memberContributionPoints);
    }

    /**
     * <p>
     * Accuracy test for the method <code>update(MemberContributionPoints memberContributionPoints)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_update() throws Exception {
        long id = instance.create(memberContributionPoints);

        memberContributionPoints.setContributionPoints(20);
        instance.update(memberContributionPoints);

        MemberContributionPoints entity = getEntity(sessionFactory, MemberContributionPoints.class, id);

        assertEquals("'update' should be correct.", memberContributionPoints.getId(), entity.getId());
        assertEquals("'update' should be correct.", memberContributionPoints.getUserId(), entity.getUserId());
        assertEquals("'update' should be correct.", memberContributionPoints.getContestId(), entity.getContestId());
        assertEquals("'update' should be correct.", memberContributionPoints.getContributionPoints(), entity
            .getContributionPoints(), 0.001);
        assertEquals("'update' should be correct.", memberContributionPoints.getContributionType(), entity
            .getContributionType());
    }

    /**
     * <p>
     * Failure test for the method <code>update(MemberContributionPoints memberContributionPoints)</code> with
     * memberContributionPoints is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_memberContributionPointsNull() throws Exception {
        memberContributionPoints = null;

        instance.update(memberContributionPoints);
    }

    /**
     * <p>
     * Failure test for the method <code>update(MemberContributionPoints memberContributionPoints)</code> with
     * memberContributionPoints#id is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_idNegative() throws Exception {
        memberContributionPoints.setId(-1);

        instance.update(memberContributionPoints);
    }

    /**
     * <p>
     * Failure test for the method <code>update(MemberContributionPoints memberContributionPoints)</code> with
     * memberContributionPoints#id is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_idZero() throws Exception {
        memberContributionPoints.setId(0);

        instance.update(memberContributionPoints);
    }

    /**
     * <p>
     * Failure test for the method <code>update(MemberContributionPoints memberContributionPoints)</code> with the
     * entity cannot be found.<br>
     * <code>ContributionServiceEntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceEntityNotFoundException.class)
    public void test_update_NotFoundError() throws Exception {
        memberContributionPoints.setId(Long.MAX_VALUE);

        instance.update(memberContributionPoints);
    }

    /**
     * <p>
     * Failure test for the method <code>update(MemberContributionPoints memberContributionPoints)</code> with an
     * error occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_update_Error() throws Exception {
        instance.create(memberContributionPoints);

        instance = (MemberContributionPointsService) APP_CONTEXT_INVALID.getBean("memberContributionPointsService");

        instance.update(memberContributionPoints);
    }

    /**
     * <p>
     * Accuracy test for the method <code>delete(long id)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_delete() throws Exception {
        long id = instance.create(memberContributionPoints);

        instance.delete(id);

        MemberContributionPoints entity = getEntity(sessionFactory, MemberContributionPoints.class, id);

        assertNull("'delete' should be correct.", entity);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long id)</code> with id is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_delete_idNegative() throws Exception {
        instance.delete(-1);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long id)</code> with id is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_delete_idZero() throws Exception {
        instance.delete(0);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long id)</code> with the entity cannot be found.<br>
     * <code>ContributionServiceEntityNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceEntityNotFoundException.class)
    public void test_delete_NotFoundError() throws Exception {
        instance.delete(Long.MAX_VALUE);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long id)</code> with an error occurred.<br>
     * <code>ContributionServiceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContributionServiceException.class)
    public void test_delete_Error() throws Exception {
        long id = instance.create(memberContributionPoints);

        instance = (MemberContributionPointsService) APP_CONTEXT_INVALID.getBean("memberContributionPointsService");

        instance.delete(id);
    }
}