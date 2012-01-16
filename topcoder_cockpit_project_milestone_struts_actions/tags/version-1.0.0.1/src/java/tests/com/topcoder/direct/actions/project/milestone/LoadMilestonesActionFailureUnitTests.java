/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementConfigurationException;

/**
 * <p>
 * Failure test cases for {@link LoadMilestonesAction}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class LoadMilestonesActionFailureUnitTests extends BaseFailureTest {
    /**
     * <p>
     * The private LoadMilestonesAction action instance.
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
        super.setUp();
        instance = new LoadMilestonesAction();
        instance.setMilestoneService(getMockMilestoneService());
        instance.setResponsiblePersonService(getMockResponsiblePersonService());
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
     * Failure test for {@link LoadMilestonesAction#afterPropertiesSet()}.
     * </p>
     */
    @Test(expected = ProjectMilestoneManagementConfigurationException.class)
    public void testAfterPropertiesSetFailure1() {
        instance.setMilestoneService(null);
        instance.afterPropertiesSet();
    }

    /**
     * <p>
     * Failure test for {@link LoadMilestonesAction#afterPropertiesSet()}.
     * </p>
     */
    @Test(expected = ProjectMilestoneManagementConfigurationException.class)
    public void testAfterPropertiesSetFailure2() {
        instance.setResponsiblePersonService(null);
        instance.afterPropertiesSet();
    }

    /**
     * <p>
     * Failure test for {@link LoadMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE1() throws Exception {
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link LoadMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE2() throws Exception {
        instance.setMilestoneIds(new ArrayList<Long>());
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link LoadMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE3() throws Exception {
        instance.setMilestoneIds(Arrays.asList(1L, null));
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link LoadMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE4() throws Exception {
        instance.setMilestoneIds(Arrays.asList(1L, 1L));
        instance.executeAction();
    }
}
