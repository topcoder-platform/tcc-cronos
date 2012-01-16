/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.milestone.AuthorizationException;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementConfigurationException;
import com.topcoder.direct.services.view.util.DirectUtils;

/**
 * <p>
 * Failure test cases for {@link DeleteMilestonesAction}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class DeleteMilestonesActionFailureUnitTests extends BaseFailureTest {
    /**
     * <p>
     * The private DeleteMilestonesAction action instance.
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
        super.setUp();
        instance = new DeleteMilestonesAction();
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
     * Failure test for {@link DeleteMilestonesAction#afterPropertiesSet()}.
     * </p>
     */
    @Test(expected = ProjectMilestoneManagementConfigurationException.class)
    public void testAfterPropertiesSetFailure1() {
        instance.setMilestoneService(null);
        instance.afterPropertiesSet();
    }

    /**
     * <p>
     * Failure test for {@link DeleteMilestonesAction#executionAction()}.
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
     * Failure test for {@link DeleteMilestonesAction#executionAction()}.
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
     * Failure test for {@link DeleteMilestonesAction#executionAction()}.
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
     * Failure test for {@link DeleteMilestonesAction#executionAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE4() throws Exception {
        instance.setMilestoneIds(Arrays.asList(1L, 1L));
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link DeleteMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testExecuteActionAuthFailure1() throws Exception {
        try {
            DirectUtils.exception = true;
            instance.setProjectId(1);
            instance.setMilestoneIds(Arrays.asList(100L, 101L));
            instance.executeAction();
            Assert.fail("fail to test");
        } catch (AuthorizationException e) {
            // good
        } finally {
            DirectUtils.exception = false;
        }
    }

    /**
     * <p>
     * Failure test for {@link DeleteMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = AuthorizationException.class)
    public void testExecuteActionAuthFailure2() throws Exception {
        DirectUtils.permission = false;
        instance.setProjectId(2);
        instance.setMilestoneIds(Arrays.asList(100L, 101L));
        instance.executeAction();
    }
}
