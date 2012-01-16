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
import com.topcoder.direct.services.project.milestone.model.SortOrder;

/**
 * <p>
 * Unit test cases for <code>GetAllMilestonesAction</code> class.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
public class GetAllMilestonesActionTest {
    /**
     * <p>
     * The GetAllMilestonesAction instance used for test.
     * </p>
     */
    private GetAllMilestonesAction instance;

    /**
     * <p>
     * Set up test environment.
     * </p>
     */
    @Before
    public void setUp() {
        instance = new GetAllMilestonesAction();
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
     * The instance of <code>GetAllMilestonesAction</code> should be created.
     * </p>
     */
    @Test
    public void testCtr() {
        Assert.assertNotNull("the instance of GetAllMilestonesAction should be created", instance);
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
     * Accuracy test for <code>getSortOrder</code> method. It verifies the returned value is correct.
     * </p>
     */
    @Test
    public void testGetSortOrder() {
        Assert.assertEquals("Fail to get sortOrder filed.", null, instance.getSortOrder());
    }

    /**
     * <p>
     * Accuracy test for <code>setSortOrder</code> method. It verifies the assigned value is correct.
     * </p>
     */
    @Test
    public void testSetSortOrder() {
        SortOrder expected = SortOrder.ASCENDING;
        instance.setSortOrder(expected);
        Assert.assertEquals("Fail to set the sortOrder filed.", expected, instance.getSortOrder());
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
        instance = (GetAllMilestonesAction) TestHelper.APP_CONTEXT.getBean("getAllMilestonesAction");
        MockMilestoneService milestoneService = (MockMilestoneService) instance.getMilestoneService();

        // set up the test environment
        milestoneService.fillData();

        instance.setRequestedStatuses(TestHelper.createMilestoneStatuses());
        instance.setSortOrder(SortOrder.DESCENDING);

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
}
