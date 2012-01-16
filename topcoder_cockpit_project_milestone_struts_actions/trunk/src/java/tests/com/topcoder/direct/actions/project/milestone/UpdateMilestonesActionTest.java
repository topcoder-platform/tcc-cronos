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
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;
import com.topcoder.direct.services.view.util.DirectUtils;

/**
 * <p>
 * Unit test cases for <code>UpdateMilestonesAction</code> class.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
public class UpdateMilestonesActionTest {
    /**
     * <p>
     * The UpdateMilestonesAction instance used for test.
     * </p>
     */
    private UpdateMilestonesAction instance;

    /**
     * <p>
     * Set up test environment.
     * </p>
     */
    @Before
    public void setUp() {
        instance = new UpdateMilestonesAction();
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
     * The instance of <code>UpdateMilestonesAction</code> should be created.
     * </p>
     */
    @Test
    public void testCtr() {
        Assert.assertNotNull("the instance of UpdateMilestonesAction should be created", instance);
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
        instance = (UpdateMilestonesAction) TestHelper.APP_CONTEXT.getBean("updateMilestonesAction");
        List<Milestone> list = new ArrayList<Milestone>();
        Milestone milestone1 = TestHelper.createMilestone();
        milestone1.setName("update1");
        Milestone milestone2 = TestHelper.createMilestone();
        milestone2.setName("update2");
        list.add(milestone1);
        list.add(milestone2);
        instance.setMilestones(list);

        MockMilestoneService milestoneService = (MockMilestoneService) instance.getMilestoneService();

        // set up the test environment for test.
        milestoneService.getMilestones().clear();

        // execute the action
        instance.executeAction();
        List<Milestone> list2 = milestoneService.getMilestones();
        Assert.assertEquals("fail to update", "update1", list2.get(0).getName());
        Assert.assertEquals("fail to update", "update2", list2.get(1).getName());
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestones can not be null.
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
     * Expected IllegalArgumentException because the milestones can not be empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure2() throws Exception {
        instance.setMilestones(new ArrayList<Milestone>());
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestones can not contained the null element.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure3() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        milestones.add(null);
        instance.setMilestones(milestones);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestone#name can not null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure4() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        Milestone milestone = TestHelper.createMilestone();
        milestone.setName(null);
        milestones.add(milestone);
        instance.setMilestones(milestones);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestone#name can not empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure5() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        Milestone milestone = TestHelper.createMilestone();
        milestone.setName("\t");
        milestones.add(milestone);
        instance.setMilestones(milestones);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestone#description can not null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure6() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        Milestone milestone = TestHelper.createMilestone();
        milestone.setDescription(null);
        milestones.add(milestone);
        instance.setMilestones(milestones);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestone#description can not empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure7() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        Milestone milestone = TestHelper.createMilestone();
        milestone.setDescription("");
        milestones.add(milestone);
        instance.setMilestones(milestones);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestone#dueDate can not null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure8() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        Milestone milestone = TestHelper.createMilestone();
        milestone.setDueDate(null);
        milestones.add(milestone);
        instance.setMilestones(milestones);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestone#status can not null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure9() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        Milestone milestone = TestHelper.createMilestone();
        milestone.setStatus(null);
        milestones.add(milestone);
        instance.setMilestones(milestones);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestone#owners can not empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure10() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        Milestone milestone = TestHelper.createMilestone();
        milestone.setOwners(new ArrayList<ResponsiblePerson>());
        milestones.add(milestone);
        instance.setMilestones(milestones);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestone#owners can not contain null element.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure11() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        Milestone milestone = TestHelper.createMilestone();
        List<ResponsiblePerson> list = milestone.getOwners();
        list.add(null);
        milestones.add(milestone);
        instance.setMilestones(milestones);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestone#owners#name can not be null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure12() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        Milestone milestone = TestHelper.createMilestone();
        List<ResponsiblePerson> list = milestone.getOwners();
        ResponsiblePerson person = TestHelper.createPerson();
        person.setName(null);
        list.add(person);
        milestones.add(milestone);
        instance.setMilestones(milestones);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for <code>executeAction</code> method.<br>
     * Expected IllegalArgumentException because the milestone#owners#name can not be empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionFailure13() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        Milestone milestone = TestHelper.createMilestone();
        List<ResponsiblePerson> list = milestone.getOwners();
        ResponsiblePerson person = TestHelper.createPerson();
        person.setName(" ");
        list.add(person);
        milestones.add(milestone);
        instance.setMilestones(milestones);
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
    public void testExecuteActionFailure14() throws Exception {
        try {
            DirectUtils.permission = false;

            List<Milestone> list = new ArrayList<Milestone>();
            list.add(TestHelper.createMilestone());
            list.add(TestHelper.createMilestone());
            instance.setMilestones(list);

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
    public void testExecuteActionFailure15() throws Exception {
        try {
            DirectUtils.exception = true;

            List<Milestone> list = new ArrayList<Milestone>();
            list.add(TestHelper.createMilestone());
            list.add(TestHelper.createMilestone());
            instance.setMilestones(list);

            instance.executeAction();
            Assert.fail("fail to test");
        } catch (AuthorizationException e) {
            DirectUtils.exception = false;
        }
    }
}
