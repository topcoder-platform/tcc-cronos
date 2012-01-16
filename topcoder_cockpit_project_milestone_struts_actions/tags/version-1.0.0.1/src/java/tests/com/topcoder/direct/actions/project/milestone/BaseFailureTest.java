/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;

import com.topcoder.direct.services.project.milestone.MilestoneService;
import com.topcoder.direct.services.project.milestone.ResponsiblePersonService;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;
import com.topcoder.direct.services.view.util.DirectUtils;

/**
 * <p>
 * This is the base class for all failure unit test case.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class BaseFailureTest {
    /**
     * <p>
     * This is a mock MilestoneService.
     * </p>
     */
    private MilestoneService mockMilestoneService;

    /**
     * <p>
     * This is a mock ResponsiblePersonService.
     * </p>
     */
    private ResponsiblePersonService mockResponsiblePersonService;

    /**
     * <p>
     * Represents a list of Milestone to be used in failure test.
     * </p>
     */
    private List<Milestone> milestones;

    /**
     * <p>
     * Set up test environment.
     * </p>
     */
    @Before
    public void setUp() {
        DirectUtils.permission = true;
        mockMilestoneService = EasyMock.createMock(MilestoneService.class);
        mockResponsiblePersonService = EasyMock.createMock(ResponsiblePersonService.class);
        milestones = new ArrayList<Milestone>();
        milestones.add(new Milestone());
        milestones.add(new Milestone());
        milestones.get(0).setDescription("description1");
        milestones.get(0).setDueDate(new Date());
        milestones.get(0).setName("name1");
        milestones.get(0).setOwners(new ArrayList<ResponsiblePerson>());
        milestones.get(0).getOwners().add(new ResponsiblePerson());
        milestones.get(0).getOwners().get(0).setName("name");
        milestones.get(0).setProjectId(100);
        milestones.get(0).setStatus(MilestoneStatus.COMPLETED);
        milestones.get(1).setDescription("description2");
        milestones.get(1).setDueDate(new Date());
        milestones.get(1).setName("name2");
        milestones.get(1).setProjectId(101);
        milestones.get(1).setStatus(MilestoneStatus.COMPLETED);
    }

    /**
     * <p>
     * Tear down test environment.
     * </p>
     */
    @After
    public void tearDown() {
        mockMilestoneService = null;
        mockResponsiblePersonService = null;
        milestones = null;
    }

    /**
     * <p>
     * This is the getter for mock MilestoneService.
     * </p>
     *
     * @return the mock MilestoneService
     */
    protected MilestoneService getMockMilestoneService() {
        return mockMilestoneService;
    }

    /**
     * <p>
     * This is the getter for mock ResponsiblePersonService.
     * </p>
     *
     * @return the mock ResponsiblePersonService
     */
    protected ResponsiblePersonService getMockResponsiblePersonService() {
        return mockResponsiblePersonService;
    }

    /**
     * <p>
     * This is the getter for a list of Milestone to be used in failure test.
     * </p>
     *
     * @return a list of Milestone to be used in failure test
     */
    protected List<Milestone> getMilestones() {
        return milestones;
    }
}
