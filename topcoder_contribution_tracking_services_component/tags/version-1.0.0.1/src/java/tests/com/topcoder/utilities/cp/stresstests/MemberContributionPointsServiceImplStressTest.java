/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.stresstests;

import org.junit.Assert;

import com.topcoder.utilities.cp.entities.MemberContributionPoints;
import com.topcoder.utilities.cp.services.MemberContributionPointsService;

/**
 * <p>
 * Stress tests for MemberContributionPointsServiceImpl class.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
public class MemberContributionPointsServiceImplStressTest extends BaseStressTest {
    /**
     * <p>
     * Represents the MemberContributionPointsService for testing.
     * </p>
     */
    private MemberContributionPointsService instance;

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
        instance = (MemberContributionPointsService) APP_CONTEXT.getBean("memberContributionPointsService");
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
     * Stress test for MemberContributionPointsServiceImpl#create(MemberContributionPoints).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreate() throws Exception {
        start();
        for (int i = 0; i < LOOP; i++) {
            MemberContributionPoints memberContributionPoints = new MemberContributionPoints();
            memberContributionPoints.setUserId(1);
            memberContributionPoints.setContestId(1 + i);
            memberContributionPoints.setContributionPoints(10);
            memberContributionPoints.setContributionType("ct1");

            long id = instance.create(memberContributionPoints);
            Assert.assertEquals("'create' should be correct.", memberContributionPoints.getId(), id);

        }
        printResult("MemberContributionPointsServiceImpl#create(MemberContributionPoints)", LOOP);
    }

    /**
     * <p>
     * Stress test for MemberContributionPointsServiceImpl#update(MemberContributionPoints).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdate() throws Exception {
        MemberContributionPoints memberContributionPoints = new MemberContributionPoints();
        memberContributionPoints.setUserId(1);
        memberContributionPoints.setContestId(1);
        memberContributionPoints.setContributionPoints(10);
        memberContributionPoints.setContributionType("ct1");
        instance.create(memberContributionPoints);
        start();
        for (int i = 0; i < LOOP; i++) {
            memberContributionPoints.setContributionType("stressTests" + i);
            instance.update(memberContributionPoints);
            Assert.assertEquals("fail to update", "stressTests" + i, instance.getByContestId(1).get(0)
                    .getContributionType());

        }

        printResult("MemberContributionPointsServiceImpl#update(MemberContributionPoints)", LOOP);
    }

    /**
     * <p>
     * Stress test for MemberContributionPointsServiceImpl#getByUserAndContest(long, long).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetByUserAndContest() throws Exception {
        MemberContributionPoints memberContributionPoints = new MemberContributionPoints();
        memberContributionPoints.setUserId(1);
        memberContributionPoints.setContestId(1);
        memberContributionPoints.setContributionPoints(10);
        memberContributionPoints.setContributionType("ct1");
        instance.create(memberContributionPoints);
        start();

        for (int i = 0; i < LOOP; i++) {

            Assert.assertEquals("fail to getByUserAndContest", 1, instance.getByUserAndContest(1, 1).size());

        }

        printResult("MemberContributionPointsServiceImpl#getByUserAndContest(long, long)", LOOP);
    }

    /**
     * <p>
     * Stress test for MemberContributionPointsServiceImpl#getByUserId(long).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetByUserId() throws Exception {
        MemberContributionPoints memberContributionPoints = new MemberContributionPoints();
        memberContributionPoints.setUserId(1);
        memberContributionPoints.setContestId(1);
        memberContributionPoints.setContributionPoints(10);
        memberContributionPoints.setContributionType("ct1");
        instance.create(memberContributionPoints);
        start();

        for (int i = 0; i < LOOP; i++) {
            Assert.assertEquals("fail to getByUserId", 1, instance.getByUserId(1).size());
        }

        printResult("MemberContributionPointsServiceImpl#getByUserId(long)", LOOP);
    }

    /**
     * <p>
     * Stress test for MemberContributionPointsServiceImpl#getByProjectId(long).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetByProjectId() throws Exception {
        MemberContributionPoints memberContributionPoints = new MemberContributionPoints();
        memberContributionPoints.setUserId(1);
        memberContributionPoints.setContestId(1);
        memberContributionPoints.setContributionPoints(10);
        memberContributionPoints.setContributionType("ct1");
        instance.create(memberContributionPoints);
        start();
        for (int i = 0; i < LOOP; i++) {
            Assert.assertEquals("fail to getByProjectId", 1, instance.getByProjectId(1).size());
        }

        printResult("MemberContributionPointsServiceImpl#getByProjectId(long)", LOOP);
    }

    /**
     * <p>
     * Stress test for MemberContributionPointsServiceImpl#getByContestId(long).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetByContestId() throws Exception {
        MemberContributionPoints memberContributionPoints = new MemberContributionPoints();
        memberContributionPoints.setUserId(1);
        memberContributionPoints.setContestId(1);
        memberContributionPoints.setContributionPoints(10);
        memberContributionPoints.setContributionType("ct1");
        instance.create(memberContributionPoints);
        start();
        for (int i = 0; i < LOOP; i++) {
            Assert.assertEquals("fail to getByContestId", 1, instance.getByContestId(1).size());
        }

        printResult("MemberContributionPointsServiceImpl#getByContestId(long)", LOOP);
    }

    /**
     * <p>
     * Stress test for MemberContributionPointsServiceImpl#delete(long).
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDelete() throws Exception {
        start();
        for (int i = 0; i < LOOP; i++) {
            final MemberContributionPoints memberContributionPoints = new MemberContributionPoints();
            memberContributionPoints.setUserId(1);
            memberContributionPoints.setContestId(1 + i);
            memberContributionPoints.setContributionPoints(10);
            memberContributionPoints.setContributionType("ct1");
            long id = instance.create(memberContributionPoints);
            long contestId = memberContributionPoints.getContestId();
            instance.delete(id);
            Assert.assertEquals("fail to delete", 0, instance.getByContestId(contestId).size());
        }

        printResult("MemberContributionPointsServiceImpl#delete(long)", LOOP);
    }
}
