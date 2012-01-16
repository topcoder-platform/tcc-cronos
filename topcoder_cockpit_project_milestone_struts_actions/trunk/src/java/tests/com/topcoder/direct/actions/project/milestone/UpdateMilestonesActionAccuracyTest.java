/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.milestone.AuthorizationException;
import com.topcoder.direct.services.project.milestone.MilestoneService;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.view.util.DirectUtils;

/**
 * Accuracy test for {@link UpdateMilestonesAction}.
 * @author BlackMagic
 * @version 1.0
 */
public class UpdateMilestonesActionAccuracyTest {
    /**
     * Represents the instance used for test.
     */
    private UpdateMilestonesAction instance;
    /**
     * Represents the {@link IMocksControl} instance used for test.
     */
    private IMocksControl control = EasyMock.createControl();
    /**
     * Represents the {@link MilestoneService} instance used for test.
     */
    private MilestoneService milestoneService;
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UpdateMilestonesActionAccuracyTest.class);
    }
    /**
     * Initializes the instance used for test.
     */
    @Before
    public void setUp() {
        instance = new UpdateMilestonesAction();
        milestoneService = control.createMock(MilestoneService.class);
        instance.setMilestoneService(milestoneService);
        List<Milestone> milestones = new ArrayList<Milestone>();
        instance.setMilestones(milestones);
        Milestone milestone = new Milestone();
        milestone.setDueDate(new Date());
        milestones.add(milestone);
        milestone.setName("tc");
        milestone.setDescription("tc");
        milestone.setStatus(MilestoneStatus.OVERDUE);
    }
    /**
     * Accuracy test for {@link UpdateMilestonesAction#executeAction()}.
     * It has write permission, no exception will be thrown.
     * @throws Exception to JUnit.
     */
    @Test
    public void testExecuteActionAccuracy() throws Exception {
        List<Milestone> milestones = instance.getMilestones();
        Milestone milestone = milestones.get(0);
        milestone.setProjectId(1);
        milestoneService.update(milestones);
        EasyMock.expectLastCall().times(1);
        control.replay();
        instance.executeAction();
        control.verify();
    }
    /**
     * Accuracy test for {@link UpdateMilestonesAction#executeAction()}.
     * It hasn't write permission, AuthorizationException is expected.
     * @throws Exception to JUnit.
     */
    @Test
    public void testExecuteActionAccuracy2() throws Exception {
        try {
            milestoneService.update(instance.getMilestones());
            DirectUtils.exception = true;
            instance.executeAction();
            Assert.fail("fail to test");
        } catch (AuthorizationException e) {
            // good
        } finally {
            DirectUtils.exception = false;
        }
    }

}
