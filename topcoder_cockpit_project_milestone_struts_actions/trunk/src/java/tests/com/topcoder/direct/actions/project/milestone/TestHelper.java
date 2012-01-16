/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;

/**
 * <p>
 * Defines helper methods used in unit tests of this component.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
public class TestHelper {
    /**
     * <p>
     * Represents the <code>ApplicationContext </code> for tests.
     * </p>
     */
    public static final ApplicationContext APP_CONTEXT;

    /**
     * <p>
     * Initialization.
     * </p>
     */
    static {
        APP_CONTEXT = new ClassPathXmlApplicationContext("beans.xml");
    }

    /**
     * <p>
     * This private constructor prevents the creation of a new instance.
     * </p>
     */
    private TestHelper() {
        // empty
    }

    /**
     * <p>
     * Create the instance of milestone for test.
     * </p>
     *
     * @return the instance of milestone
     */
    public static Milestone createMilestone() {
        Milestone milestone = new Milestone();
        milestone.setDueDate(new Date());
        milestone.setName("test");
        milestone.setDescription("test");
        milestone.setStatus(MilestoneStatus.COMPLETED);
        milestone.setOwners(createResponsiblePersons());
        return milestone;
    }

    /**
     * <p>
     * Create the list of responsiblePersons for test.
     * </p>
     *
     * @return the list of responsiblePersons
     */
    private static List<ResponsiblePerson> createResponsiblePersons() {
        List<ResponsiblePerson> list = new ArrayList<ResponsiblePerson>();
        list.add(createPerson());
        ResponsiblePerson person = createPerson();
        person.setName("test2");
        list.add(person);
        return list;
    }

    /**
     * <p>
     * Create the responsiblePerson for test.
     * </p>
     *
     * @return the instance of responsiblePerson
     */
    public static ResponsiblePerson createPerson() {
        ResponsiblePerson person = new ResponsiblePerson();
        person.setName("test");
        return person;
    }

    /**
     * <p>
     * Create the milestoneIds for test.
     * </p>
     *
     * @return the milestone ids.
     */
    public static List<Long> createMilestoneIds() {
        List<Long> list = new ArrayList<Long>();
        list.add(1l);
        list.add(2l);
        return list;
    }

    /**
     * <p>
     * Create the list of milestoneStatus for test.
     * </p>
     *
     * @return the list of milestoneStatus
     */
    public static List<MilestoneStatus> createMilestoneStatuses() {
        List<MilestoneStatus> list = new ArrayList<MilestoneStatus>();
        list.add(MilestoneStatus.COMPLETED);
        list.add(MilestoneStatus.OVERDUE);
        list.add(MilestoneStatus.UPCOMING);
        return list;
    }
}
