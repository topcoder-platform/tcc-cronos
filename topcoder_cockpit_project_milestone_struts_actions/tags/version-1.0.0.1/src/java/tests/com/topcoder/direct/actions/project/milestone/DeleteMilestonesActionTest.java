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

import com.topcoder.direct.services.project.milestone.AuthorizationException;
import com.topcoder.direct.services.project.milestone.MilestoneService;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementConfigurationException;
import com.topcoder.direct.services.project.milestone.mock.MockMilestoneService;
import com.topcoder.direct.services.view.util.DirectUtils;

/**
 * <p>
 * Unit test cases for <code>DeleteMilestonesAction</code> class.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
public class DeleteMilestonesActionTest {
    /**
     * <p>
     * The DeleteMilestonesAction instance used for test.
     * </p>
     */
    private DeleteMilestonesAction instance;

    /**
     * <p>
     * Set up test environment.
     * </p>
     */
    @Before
    public void setUp() {
        instance = new DeleteMilestonesAction();
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
     * The instance of <code>DeleteMilestonesAction</code> should be created.
     * </p>
     */
    @Test
    public void testCtr() {
        Assert.assertNotNull("the instance of DeleteMilestonesAction should be created", instance);
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
     * Accuracy test for <code>getMilestoneIds</code> method. It verifies the returned value is correct.
     * </p>
     */
    @Test
    public void testGetMilestoneIds() {
        Assert.assertEquals("Fail to get milestoneIds filed.", null, instance.getMilestoneIds());
    }

    /**
     * <p>
     * Accuracy test for <code>setMilestoneIds</code> method. It verifies the assigned value is correct.
     * </p>
     */
    @Test
    public void testSetMilestoneIds() {
        List<Long> expected = new ArrayList<Long>();
        instance.setMilestoneIds(expected);
        Assert.assertEquals("Fail to set the milestoneIds filed.", expected, instance.getMilestoneIds());
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
        instance = (DeleteMilestonesAction) TestHelper.APP_CONTEXT.getBean("deleteMilestonesAction");
        instance.setMilestoneIds(TestHelper.createMilestoneIds());
        MockMilestoneService milestoneService = (MockMilestoneService) instance.getMilestoneService();

        // fill data for test
        milestoneService.fillData();

        // execute the action
        instance.executeAction();

        Assert.assertEquals("fail to delete", 0, milestoneService.getMilestones().size());
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestoneIds can not be null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure1() throws Exception {
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestoneIds can not be empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure2() throws Exception {
        instance.setMilestoneIds(new ArrayList<Long>());
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestoneIds can not contains the null element.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure3() throws Exception {
        List<Long> list = TestHelper.createMilestoneIds();
        list.add(null);
        instance.setMilestoneIds(list);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestoneIds can not contains duplicated element.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure4() throws Exception {
        List<Long> list = TestHelper.createMilestoneIds();
        list.add(1l);
        instance.setMilestoneIds(list);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected AuthorizationException because the current user has not write permission.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecuteActionFailure5() throws Exception {
        try {
            DirectUtils.permission = false;

            MockMilestoneService milestoneService = new MockMilestoneService();
            instance.setMilestoneService(milestoneService);

            // fill data for test
            milestoneService.fillData();

            instance.setMilestoneIds(TestHelper.createMilestoneIds());

            // execute the action
            instance.executeAction();
            Assert.fail("fail to test");
        } catch (AuthorizationException e) {
            DirectUtils.permission = true;
        }
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected AuthorizationException because fail to get permission.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecuteActionFailure6() throws Exception {
        try {
            DirectUtils.exception = true;
            MockMilestoneService milestoneService = new MockMilestoneService();
            instance.setMilestoneService(milestoneService);

            // fill data for test
            milestoneService.fillData();

            instance.setMilestoneIds(TestHelper.createMilestoneIds());

            // execute the action
            instance.executeAction();
            Assert.fail("fail to test");
        } catch (AuthorizationException e) {
            DirectUtils.exception = false;
        }
    }
}
