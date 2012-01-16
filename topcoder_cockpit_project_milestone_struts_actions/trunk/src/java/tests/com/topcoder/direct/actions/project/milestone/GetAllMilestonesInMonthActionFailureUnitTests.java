/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementConfigurationException;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;

/**
 * <p>
 * Failure test cases for {@link GetAllMilestonesInMonthAction}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class GetAllMilestonesInMonthActionFailureUnitTests extends BaseFailureTest {
    /**
     * <p>
     * The private GetAllMilestonesInMonthAction action instance.
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
        super.setUp();
        instance = new GetAllMilestonesInMonthAction();
        instance.setMilestoneService(getMockMilestoneService());
    }

    /**
     * <p>
     * Tear down test environment.
     * </p>
     */
    @After
    public void tearDown() {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Failure test for {@link GetAllMilestonesInMonthAction#afterPropertiesSet()}.
     * </p>
     */
    @Test(expected = ProjectMilestoneManagementConfigurationException.class)
    public void testAfterPropertiesSetFailure() {
        instance.setMilestoneService(null);
        instance.afterPropertiesSet();
    }

    /**
     * <p>
     * Failure test for {@link GetAllMilestonesInMonthAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE1() throws Exception {
        instance.setMonth(10);
        instance.setRequestedStatuses(Arrays.asList(null, MilestoneStatus.COMPLETED));
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link GetAllMilestonesInMonthAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE2() throws Exception {
        instance.setMonth(10);
        instance.setRequestedStatuses(Arrays.asList(MilestoneStatus.COMPLETED, MilestoneStatus.COMPLETED));
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link GetAllMilestonesInMonthAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE3() throws Exception {
        instance.setMonth(0);
        instance.executeAction();
    }
}
