/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.milestone.MilestoneService;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementConfigurationException;
import com.topcoder.direct.services.project.milestone.mock.MockMilestoneService;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;

/**
 * <p>
 * Unit test cases for <code>GetAllMilestonesInMonthAction</code> class.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
public class GetAllMilestonesInMonthActionTest {
    /**
     * <p>
     * The GetAllMilestonesInMonthAction instance used for test.
     * </p>
     */
    private GetAllMilestonesInMonthAction instance;

    /**
     * <p>
     * Set up test environment.
     * </p>
     */
    @Before
    public void setUp() {
        instance = new GetAllMilestonesInMonthAction();
    }

    /**
     * <p>
     * Tear down test environment.
     * </p>
     */
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * <p>
     * Test the empty construct.<br>
     * The instance of <code>GetAllMilestonesInMonthAction</code> should be created.
     * </p>
     */
    @Test
    public void testCtr() {
        Assert.assertNotNull("the instance of GetAllMilestonesInMonthAction should be created", instance);
    }

    /**
     * <p>
     * Accuracy test for <code>getMilestoneService</code> method. It verifies the returned value is correct.
     * </p>
     */
    @Test
    public void testGetMilestoneService() {
        Assert.assertEquals("Fail to get milestoneService filed.", null, instance.getMilestoneService());
    }

    /**
     * <p>
     * Accuracy test for <code>setMilestoneService</code> method. It verifies the assigned value is correct.
     * </p>
     */
    @Test
    public void testSetMilestoneService() {
        MilestoneService expected = new MockMilestoneService();
        instance.setMilestoneService(expected);
        Assert.assertEquals("Fail to set the milestoneService filed.", expected, instance.getMilestoneService());
    }

    /**
     * <p>
     * Accuracy test for <code>getProjectId</code> method. It verifies the returned value is correct.
     * </p>
     */
    @Test
    public void testGetProjectId() {
        Assert.assertEquals("Fail to get projectId filed.", 0, instance.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for <code>setProjectId</code> method. It verifies the assigned value is correct.
     * </p>
     */
    @Test
    public void testSetProjectId() {
        long expected = 2l;
        instance.setProjectId(expected);
        Assert.assertEquals("Fail to set the projectId filed.", expected, instance.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for <code>getMonth</code> method. It verifies the returned value is correct.
     * </p>
     */
    @Test
    public void testGetMonth() {
        Assert.assertEquals("Fail to get month filed.", 0, instance.getMonth());
    }

    /**
     * <p>
     * Accuracy test for <code>setMonth</code> method. It verifies the assigned value is correct.
     * </p>
     */
    @Test
    public void testSetMonth() {
        int expected = 2;
        instance.setMonth(expected);
        Assert.assertEquals("Fail to set the month filed.", expected, instance.getMonth());
    }

    /**
     * <p>
     * Accuracy test for <code>getYear</code> method. It verifies the returned value is correct.
     * </p>
     */
    @Test
    public void testGetYear() {
        Assert.assertEquals("Fail to get year filed.", 0, instance.getYear());
    }

    /**
     * <p>
     * Accuracy test for <code>setYear</code> method. It verifies the assigned value is correct.
     * </p>
     */
    @Test
    public void testSetYear() {
        int expected = 2;
        instance.setYear(expected);
        Assert.assertEquals("Fail to set the year filed.", expected, instance.getYear());
    }

    /**
     * <p>
     * Accuracy test for <code>getRequestedStatuses</code> method. It verifies the returned value is correct.
     * </p>
     */
    @Test
    public void testGetRequestedStatuses() {
        Assert.assertEquals("Fail to get requestedStatuses filed.", null, instance.getRequestedStatuses());
    }

    /**
     * <p>
     * Accuracy test for <code>setRequestedStatuses</code> method. It verifies the assigned value is correct.
     * </p>
     */
    @Test
    public void testSetRequestedStatuses() {
        List<MilestoneStatus> expected = new ArrayList<MilestoneStatus>();
        instance.setRequestedStatuses(expected);
        Assert.assertEquals("Fail to set the requestedStatuses filed.", expected, instance.getRequestedStatuses());
    }

    /**
     * <p>
     * Accuracy test for <code>getMilestones</code> method. It verifies the returned value is correct.
     * </p>
     */
    @Test
    public void testGetMilestones() {
        Assert.assertEquals("Fail to get milestones filed.", null, instance.getMilestones());
    }

    /**
     * <p>
     * Accuracy test for <code>setMilestones</code> method. It verifies the assigned value is correct.
     * </p>
     */
    @Test
    public void testSetMilestones() {
        List<Milestone> expected = new ArrayList<Milestone>();
        instance.setMilestones(expected);
        Assert.assertEquals("Fail to set the milestones filed.", expected, instance.getMilestones());
    }

    /**
     * <p>
     * Failure test for <code>afterPropertiesSet</code> method.<br>
     * Expected ProjectMilestoneManagementConfigurationException because the milestoneService should not be null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementConfigurationException.class)
    public void testAfterPropertiesSetFailure() throws Exception {
        instance.afterPropertiesSet();
    }

    /**
     * <p>
     * Accuracy test of <code>executeAction</code> method.<br>
     * The result is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecuteActionAccuracy() throws Exception {
        instance = (GetAllMilestonesInMonthAction) TestHelper.APP_CONTEXT.getBean("getAllMilestonesInMonthAction");
        MockMilestoneService milestoneService = (MockMilestoneService) instance.getMilestoneService();

        // set up the test environment
        milestoneService.fillData();

        instance.setRequestedStatuses(TestHelper.createMilestoneStatuses());
        instance.setMonth(2);

        instance.executeAction();

        Assert.assertEquals("fail to create", 2, instance.getMilestones().size());
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the requestedStatuses can not contain null element.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure1() throws Exception {
        List<MilestoneStatus> list = TestHelper.createMilestoneStatuses();
        list.add(null);
        instance.setRequestedStatuses(list);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the requestedStatuses can not contain duplicated element.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure2() throws Exception {
        List<MilestoneStatus> list = TestHelper.createMilestoneStatuses();
        list.add(MilestoneStatus.COMPLETED);
        instance.setRequestedStatuses(list);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the month is 13 not in range of [1, 12].
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure3() throws Exception {
        List<MilestoneStatus> list = TestHelper.createMilestoneStatuses();
        instance.setRequestedStatuses(list);
        instance.setMonth(13);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the month is 0 not in range of [1, 12].
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure4() throws Exception {
        List<MilestoneStatus> list = TestHelper.createMilestoneStatuses();
        instance.setRequestedStatuses(list);
        instance.setMonth(0);
        instance.executeAction();
    }
}
