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
import com.topcoder.direct.services.project.milestone.ResponsiblePersonService;
import com.topcoder.direct.services.project.milestone.mock.MockMilestoneService;
import com.topcoder.direct.services.project.milestone.mock.MockResponsiblePersonService;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;

/**
 * <p>
 * Unit test cases for <code>LoadMilestonesAction</code> class.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
public class LoadMilestonesActionTest {
    /**
     * <p>
     * The LoadMilestonesAction instance used for test.
     * </p>
     */
    private LoadMilestonesAction instance;

    /**
     * <p>
     * Set up test environment.
     * </p>
     */
    @Before
    public void setUp() {
        instance = new LoadMilestonesAction();
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
     * The instance of <code>LoadMilestonesAction</code> should be created.
     * </p>
     */
    @Test
    public void testCtr() {
        Assert.assertNotNull("the instance of LoadMilestonesAction should be created", instance);
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
     * Accuracy test for <code>getResponsiblePersonService</code> method. It verifies the returned value is correct.
     * </p>
     */
    @Test
    public void testGetResponsiblePersonService() {
        Assert.assertEquals("Fail to get responsiblePersonService filed.", null,
                instance.getResponsiblePersonService());
    }

    /**
     * <p>
     * Accuracy test for <code>setResponsiblePersonService</code> method. It verifies the assigned value is correct.
     * </p>
     */
    @Test
    public void testSetResponsiblePersonService() {
        ResponsiblePersonService expected = new MockResponsiblePersonService();
        instance.setResponsiblePersonService(expected);
        Assert.assertEquals("Fail to set the responsiblePersonService filed.", expected,
                instance.getResponsiblePersonService());
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
     * Accuracy test for <code>getResponsiblePeople</code> method. It verifies the returned value is correct.
     * </p>
     */
    @Test
    public void testGetResponsiblePeople() {
        Assert.assertEquals("Fail to get responsiblePeople filed.", null, instance.getResponsiblePeople());
    }

    /**
     * <p>
     * Accuracy test for <code>setResponsiblePeople</code> method. It verifies the assigned value is correct.
     * </p>
     */
    @Test
    public void testSetResponsiblePeople() {
        List<ResponsiblePerson> expected = new ArrayList<ResponsiblePerson>();
        instance.setResponsiblePeople(expected);
        Assert.assertEquals("Fail to set the responsiblePeople filed.", expected, instance.getResponsiblePeople());
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
    public void testAfterPropertiesSetFailure1() throws Exception {
        instance.setResponsiblePersonService(new MockResponsiblePersonService());
        instance.afterPropertiesSet();
    }

    /**
     * <p>
     * Failure test for <code>afterPropertiesSet</code> method.<br>
     * Expected ProjectMilestoneManagementConfigurationException because the responsiblePersonService should not be
     * null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementConfigurationException.class)
    public void testAfterPropertiesSetFailure2() throws Exception {
        instance.setMilestoneService(new MockMilestoneService());
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
        instance = (LoadMilestonesAction) TestHelper.APP_CONTEXT.getBean("loadMilestonesAction");
        MockMilestoneService milestoneService = (MockMilestoneService) instance.getMilestoneService();
        MockResponsiblePersonService responsiblePersonService = (MockResponsiblePersonService) instance
                .getResponsiblePersonService();

        // set up the test environment
        milestoneService.fillData();
        responsiblePersonService.fillData();

        instance.setMilestoneIds(TestHelper.createMilestoneIds());

        instance.executeAction();

        Assert.assertEquals("fail to load", 2, instance.getMilestones().size());
        Assert.assertEquals("fail to load", 2, instance.getResponsiblePeople().size());
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
}
