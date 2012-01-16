/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.milestone.MilestoneService;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;

/**
 * Accuracy test for {@link GetAllMilestonesInMonthAction}.
 * @author BlackMagic
 * @version 1.0
 */
public class GetAllMilestonesInMonthActionAccuracyTest {
    /**
     * Represents the instance used for test.
     */
    private GetAllMilestonesInMonthAction instance;
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
        return new JUnit4TestAdapter(GetAllMilestonesInMonthActionAccuracyTest.class);
    }
    /**
     * Initializes the instance used for test.
     */
    @Before
    public void setUp() {
        instance = new GetAllMilestonesInMonthAction();
        milestoneService = control.createMock(MilestoneService.class);
        instance.setMilestoneService(milestoneService);
        List<MilestoneStatus> statuses = new ArrayList<MilestoneStatus>();
        instance.setRequestedStatuses(statuses);
        statuses.add(MilestoneStatus.OVERDUE);
        instance.setMonth(12);
    }
    /**
     * Accuracy test for {@link GetAllMilestonesInMonthAction#executeAction()}.
     * The result should be correct.
     * @throws Exception to JUnit.
     */
    @Test
    public void testExecuteActionAccuracy() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        milestoneService.getAllInMonth(0, 12, 0, instance.getRequestedStatuses());
        EasyMock.expectLastCall().andReturn(milestones).times(1);
        control.replay();
        instance.executeAction();
        assertEquals("The result should be correct.", milestones, instance.getMilestones());
        control.verify();
    }
}
