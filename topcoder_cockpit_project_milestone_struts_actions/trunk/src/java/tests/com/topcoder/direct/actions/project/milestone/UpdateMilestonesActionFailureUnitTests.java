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
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementConfigurationException;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;
import com.topcoder.direct.services.view.util.DirectUtils;

/**
 * <p>
 * Failure test cases for {@link UpdateMilestonesAction}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class UpdateMilestonesActionFailureUnitTests extends BaseFailureTest {
    /**
     * <p>
     * The private UpdateMilestonesAction action instance.
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
        super.setUp();
        instance = new UpdateMilestonesAction();
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
     * Failure test for {@link UpdateMilestonesAction#afterPropertiesSet()}.
     * </p>
     */
    @Test(expected = ProjectMilestoneManagementConfigurationException.class)
    public void testAfterPropertiesSetFailure() {
        instance.setMilestoneService(null);
        instance.afterPropertiesSet();
    }

    /**
     * <p>
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
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
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE2() throws Exception {
        instance.setMilestones(new ArrayList<Milestone>());
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE3() throws Exception {
        List<Milestone> list = getMilestones();
        list.add(null);
        instance.setMilestones(list);
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE4() throws Exception {
        getMilestones().get(0).setName(null);
        instance.setMilestones(getMilestones());
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE5() throws Exception {
        getMilestones().get(0).setName("    ");
        instance.setMilestones(getMilestones());
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE6() throws Exception {
        getMilestones().get(0).setDescription(null);
        instance.setMilestones(getMilestones());
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE7() throws Exception {
        getMilestones().get(0).setDescription("    ");
        instance.setMilestones(getMilestones());
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE8() throws Exception {
        getMilestones().get(0).setStatus(null);
        instance.setMilestones(getMilestones());
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE9() throws Exception {
        getMilestones().get(0).setOwners(new ArrayList<ResponsiblePerson>());
        instance.setMilestones(getMilestones());
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE10() throws Exception {
        getMilestones().get(0).getOwners().add(null);
        instance.setMilestones(getMilestones());
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE11() throws Exception {
        getMilestones().get(0).getOwners().get(0).setName(null);
        instance.setMilestones(getMilestones());
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE12() throws Exception {
        getMilestones().get(0).getOwners().get(0).setName("    ");
        instance.setMilestones(getMilestones());
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExecuteActionIAE13() throws Exception {
        getMilestones().get(0).setDueDate(null);
        instance.setMilestones(getMilestones());
        instance.executeAction();
    }

    /**
     * <p>
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testExecuteActionAuthFailure1() throws Exception {
        try {
            DirectUtils.exception = true;
            getMilestones().get(0).setProjectId(1);
            instance.setMilestones(getMilestones());
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
     * Failure test for {@link UpdateMilestonesAction#executeAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = AuthorizationException.class)
    public void testExecuteActionAuthFailure2() throws Exception {
        DirectUtils.permission = false;
        instance.setMilestones(getMilestones());
        instance.executeAction();
    }
}
