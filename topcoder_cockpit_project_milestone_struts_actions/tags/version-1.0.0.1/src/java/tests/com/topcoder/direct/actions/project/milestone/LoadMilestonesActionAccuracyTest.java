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
import com.topcoder.direct.services.project.milestone.ResponsiblePersonService;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;

/**
 * Accuracy test for {@link LoadMilestonesAction}.
 * @author BlackMagic
 * @version 1.0
 */
public class LoadMilestonesActionAccuracyTest {
    /**
     * Represents the instance used for test.
     */
    private LoadMilestonesAction instance;
    /**
     * Represents the {@link IMocksControl} instance used for test.
     */
    private IMocksControl control = EasyMock.createControl();
    /**
     * Represents the {@link MilestoneService} instance used for test.
     */
    private MilestoneService milestoneService;
    /**
     * Represents the {@link ResponsiblePersonService} instance used for test.
     */
    private ResponsiblePersonService responsiblePersonService;
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(LoadMilestonesActionAccuracyTest.class);
    }
    /**
     * Initializes the instance used for test.
     */
    @Before
    public void setUp() {
        instance = new LoadMilestonesAction();
        milestoneService = control.createMock(MilestoneService.class);
        instance.setMilestoneService(milestoneService);
        responsiblePersonService = control.createMock(ResponsiblePersonService.class);
        instance.setResponsiblePersonService(responsiblePersonService);
        List<Long> milestoneIds = new ArrayList<Long>();
        milestoneIds.add(0L);
        instance.setMilestoneIds(milestoneIds);
    }
    /**
     * Accuracy test for {@link LoadMilestonesAction#executeAction()}.
     * The result should be correct.
     * @throws Exception to JUnit.
     */
    @Test
    public void testExecuteActionAccuracy() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        List<ResponsiblePerson> persons = new ArrayList<ResponsiblePerson>();
        milestoneService.get(instance.getMilestoneIds());
        EasyMock.expectLastCall().andReturn(milestones).times(1);
        responsiblePersonService.getAllResponsiblePeople(0);
        EasyMock.expectLastCall().andReturn(persons).times(1);
        control.replay();
        instance.executeAction();
        assertEquals("The result should be correct.", persons, instance.getResponsiblePeople());
        assertEquals("The result should be correct.", milestones, instance.getMilestones());
        control.verify();
    }
}
