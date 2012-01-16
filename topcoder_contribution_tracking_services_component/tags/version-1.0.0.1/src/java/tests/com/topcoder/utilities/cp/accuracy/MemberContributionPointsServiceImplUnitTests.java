/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.accuracy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.utilities.cp.entities.MemberContributionPoints;
import com.topcoder.utilities.cp.services.MemberContributionPointsService;
import com.topcoder.utilities.cp.services.impl.MemberContributionPointsServiceImpl;

/**
 * <p>
 * Accuracy test {@link MemberContributionPointsServiceImpl}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class MemberContributionPointsServiceImplUnitTests extends BaseAccuracyUnitTests {
    /**
     * <p>
     * Represents MemberContributionPointsServiceImpl instance for testing.
     * </p>
     */
    private MemberContributionPointsService instance;

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
        instance = (MemberContributionPointsService) ACCURAY_APP_CONTEXT.getBean("memberContributionPointsService");
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
     * Accuracy test for {@link MemberContributionPointsServiceImpl#MemberContributionPointsServiceImpl()}.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull(instance);
    }

    /**
     * <p>
     * Accuracy test for {@link MemberContributionPointsServiceImpl#getByUserAndContest(long, long)}
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetByUserAndContest() throws Exception {
        List<MemberContributionPoints>  list = instance.getByUserAndContest(1, 1);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0).getId());
        assertTrue(50f == list.get(0).getContributionPoints());
        assertEquals("type1", list.get(0).getContributionType());
        assertEquals(2, list.get(1).getId());
        assertTrue(100f == list.get(1).getContributionPoints());
        assertEquals("type2", list.get(1).getContributionType());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberContributionPointsServiceImpl#getByUserId(long)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetByUserIdAccuracy1() throws Exception {
        List<MemberContributionPoints>  list = instance.getByUserId(1);
        assertEquals(7, list.size());
        assertTrue(50f == list.get(0).getContributionPoints());
        assertEquals("type1", list.get(0).getContributionType());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberContributionPointsServiceImpl#getByUserId(long)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetByUserIdAccuracy2() throws Exception {
        List<MemberContributionPoints>  list = instance.getByUserId(2);
        assertEquals(0, list.size());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberContributionPointsServiceImpl#getByProjectId(long)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetByProjectId() throws Exception {
        List<MemberContributionPoints> list = instance.getByProjectId(4);
        assertEquals(2, list.size());
        assertTrue(100f == list.get(0).getContributionPoints());
        assertEquals("type6", list.get(0).getContributionType());
        assertTrue(50f == list.get(1).getContributionPoints());
        assertEquals("type7", list.get(1).getContributionType());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberContributionPointsServiceImpl#getByContestId(long)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGetByContestId() throws Exception {
        List<MemberContributionPoints> list = instance.getByContestId(1);
        assertEquals(2, list.size());
        assertTrue(50f == list.get(0).getContributionPoints());
        assertEquals("type1", list.get(0).getContributionType());
        assertTrue(100f == list.get(1).getContributionPoints());
        assertEquals("type2", list.get(1).getContributionType());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberContributionPointsServiceImpl#create(MemberContributionPoints)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCreate() throws Exception {
        MemberContributionPoints points = new MemberContributionPoints();
        points.setUserId(2);
        points.setContestId(1);
        points.setContributionPoints(100);
        points.setContributionType("type");
        instance.create(points);

        MemberContributionPoints newPoints = instance.getByUserId(2).get(0);
        assertTrue(newPoints.getId() > 0);
        assertEquals(2, newPoints.getUserId());
        assertEquals(1, newPoints.getContestId());
        assertTrue(100f == newPoints.getContributionPoints());
        assertEquals("type", newPoints.getContributionType());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberContributionPointsServiceImpl#update(MemberContributionPoints)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testUpdate() throws Exception{
        MemberContributionPoints points = new MemberContributionPoints();
        points.setUserId(2);
        points.setContestId(1);
        points.setContributionPoints(100);
        points.setContributionType("type");
        instance.create(points);

        points.setContestId(3);
        points.setContributionType("newType");
        instance.update(points);
        MemberContributionPoints newPoints = instance.getByUserId(2).get(0);
        assertTrue(newPoints.getId() > 0);
        assertEquals(2, newPoints.getUserId());
        assertEquals(3, newPoints.getContestId());
        assertTrue(100f == newPoints.getContributionPoints());
        assertEquals("newType", newPoints.getContributionType());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberContributionPointsServiceImpl#delete(long)}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testDelete() throws Exception{
        MemberContributionPoints points = new MemberContributionPoints();
        points.setUserId(2);
        points.setContestId(1);
        points.setContributionPoints(100);
        points.setContributionType("type");
        instance.create(points);
        assertNotNull(instance.getByUserId(2).get(0));
        instance.delete(points.getId());
        assertEquals(0, instance.getByUserId(2).size());
    }
}
