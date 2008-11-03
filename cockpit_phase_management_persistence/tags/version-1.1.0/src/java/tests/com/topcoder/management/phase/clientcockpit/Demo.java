/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit;

import com.topcoder.management.phase.PhaseManager;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestStatus;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

import java.util.Map;


/**
 * <p>
 * A test case which demonstrates the usage of the component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(Demo.class);

        return suite;
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "cockpit-demo.xml");
        TestHelper.loadXMLConfig("test_files" + File.separator + "object-factory.xml");
        TestHelper.loadXMLConfig("test_files" + File.separator + "jndi-utils.xml");
        TestHelper.loadXMLConfig("test_files" + File.separator + "memory-usage.xml");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Demonstrates the usage of CockpitPhaseManager.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testDemo() throws Exception {
        PhaseManager manager = new CockpitPhaseManager("CockpitPhaseManager");
        ContestManager contestManager = ((CockpitPhaseManager) manager).getContestManager();
        TestHelper.initContestStatuses(contestManager);

        // Suppose that a Contest exists in the persistence layer
        // This contest has the name "Kyx Persistence", the id=17 and the status is "Draft"
        ContestStatus draft = contestManager.getContestStatus(1);
        Contest contest = TestHelper.createContest(17, TestHelper.createDate(2008, 3, 20), draft);
        contest.setName("Kyx Persistence");
        contest.setEventId(1L);
        contestManager.createContest(contest);

        // Get the phases, get the contest data
        Project project = manager.getPhases(17);

        // the data of the contest is in the Project entity
        // get the phases
        Phase[] phases = project.getAllPhases();

        // the phases contains two items: "Draft" and "Scheduled" phases
        // these phases have the ids equal to the ContestStatus "Draft" and "Scheduled"

        // get the attributes
        Map attributes = project.getAttributes();

        // these attributes contains the data from the Contest
        // there are two attributes (in addition to the other attributes)
        // these attributes are: "contestName" --> "Kyx Persistence",
        // "contestEventId" --> 1

        // get all phase types
        PhaseType[] allPhaseTypes = manager.getAllPhaseTypes();

        // the phase types are retrieved
        // these phase types contain the information of all Contest Statuses
        // the id and the names are equal to the Contest Statues, they will be "Draft",
        // "Action Required" etc..

        // get the all phase status
        PhaseStatus[] phaseStatus = manager.getAllPhaseStatuses();

        // the statuses are the same, retrieved from the values of the PhaseStatus class

        // Suppose that the scheduled phase is going to be managed.
        Phase scheduledPhase = TestHelper.findPhaseByTypeName(phases, "Scheduled");

        // start the scheduled phase to the contest
        // this scheduled phase has the PhaseType.name equal to "Scheduled"

        // set the phase status
        scheduledPhase.setPhaseStatus(PhaseStatus.SCHEDULED);

        // start the "Scheduled" phase
        manager.start(scheduledPhase, "TCS");
        // the handler with the related
        // HandlerRegistryInfo(scheduledPhase,PhaseOperation.START) will be called

        // now the "Scheduled" phase is started
        // now the ContestStatus has the "Scheduled" contest status

        // end the "Scheduled" phase
        manager.end(scheduledPhase, "TCS");

        // the handler with the related
        // HandlerRegistryInfo(scheduledPhase,PhaseOperation.END) will be called
        // now the "Scheduled" phase is ended
        // now the ContestStatus has already "Scheduled" contest status
    }
}
