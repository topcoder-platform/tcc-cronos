/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import static org.easymock.EasyMock.createNiceMock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topcoder.clientcockpit.phases.TestHelper.CockpitContestStatus;
import com.topcoder.clientcockpit.phases.TestHelper.CockpitPhaseType;
import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.management.phase.DefaultPhaseManager;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.management.phase.PhasePersistence;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.util.idgenerator.IDGenerator;


/**
 * <p>
 * Demo the usage of this component.
 * </p>
 *
 * <p>
 * Not all the handlers defined in this component will be added to the <code>PhaseManager</code>.
 * The scenario that will be presented is enough to understand how the handlers work with a
 * <code>PhaseManager</code> implementation and how the contest status will change from "Draft"
 * phase to "Completed" phase.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends BaseTestCase {

    /**
     * <p>
     * The contest duration(Contest.endDate - Contest.startDate).
     * </p>
     */
    private static final int CONTEST_DURATION = 2000;

    /**
     * <p>
     * The <code>PhaseManager</code> used for test.
     * </p>
     */
    private static PhaseManager manager;

    /**
     * <p>
     * The Draft phase used for test.
     * </p>
     */
    private static Phase draftPhase;

    /**
     * <p>
     * The Scheduled phase used for test.
     * </p>
     */
    private static Phase scheduledPhase;

    /**
     * <p>
     * The Active phase used for test.
     * </p>
     */
    private static Phase activePhase;

    /**
     * <p>
     * The Action Required phase used for test.
     * </p>
     */
    private static Phase actionRequiredPhase;

    /**
     * <p>
     * The Completed phase used for test.
     * </p>
     */
    private static Phase completedPhase;

    /**
     * <p>
     * Set up the test case.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.startSMTPServer();
        this.mockContestManager(false);
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Override
    public void tearDown() throws Exception {
        this.stopSMTPServer();
        super.tearDown();
    }

    /**
     * <p>
     * Create the handlers and add them to <code>PhaseManager</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void registerHandlers() throws Exception {
        //Create a DefaultPhaseManager instance
        manager = new DefaultPhaseManager(createNiceMock(PhasePersistence.class),
                createNiceMock(IDGenerator.class));

        //Create and add handlers to the manager

        //Create a handler for the "Draft" phase
        PhaseHandler draftHandler = new DraftPhaseHandler();

        //Add this handler to the manager and make this handler handle the starting and
        //ending of the "Draft" phase
        PhaseType draftPhaseType = CockpitPhaseType.DRAFT;
        manager.registerHandler(draftHandler, draftPhaseType, PhaseOperationEnum.START);
        manager.registerHandler(draftHandler, draftPhaseType, PhaseOperationEnum.END);

        //Create a handler for the "Scheduled" phase
        PhaseHandler scheduledHandler = new ScheduledPhaseHandler();

        //Add this handler to the manager and make this handler handle the starting and
        //ending of the "Scheduled" phase
        PhaseType scheduledPhaseType = CockpitPhaseType.SCHEDULED;
        manager.registerHandler(scheduledHandler, scheduledPhaseType, PhaseOperationEnum.START);
        manager.registerHandler(scheduledHandler, scheduledPhaseType, PhaseOperationEnum.END);

        //Create a handler for the "Active" phase
        PhaseHandler activeHandler = new ActivePhaseHandler();

        //Add this handler to the manager and make this handler handle the starting and
        //ending of the "Active" phase
        PhaseType activePhaseType = CockpitPhaseType.ACTIVE;
        manager.registerHandler(activeHandler, activePhaseType, PhaseOperationEnum.START);
        manager.registerHandler(activeHandler, activePhaseType, PhaseOperationEnum.END);

        //Create a handler for the "Action Required" phase
        PhaseHandler actionRequiredHandler = new ActionRequiredPhaseHandler();

        //Add this handler to the manager and make this handler handle the starting and
        //ending of the "Action Required" phase
        PhaseType actionRequiredPhaseType = CockpitPhaseType.ACTION_REQUIRED;
        manager.registerHandler(actionRequiredHandler, actionRequiredPhaseType, PhaseOperationEnum.START);
        manager.registerHandler(actionRequiredHandler, actionRequiredPhaseType, PhaseOperationEnum.END);

        //Create a handler for the "Completed" phase
        PhaseHandler completedHandler = new CompletedPhaseHandler();

        //Add this handler to the manager and make this handler handle the starting of the
        //"Completed" phase (this phase is an end phase)
        PhaseType completedPhaseType = CockpitPhaseType.COMPLETED;
        manager.registerHandler(completedHandler, completedPhaseType, PhaseOperationEnum.START);
    }

    /**
     * <p>
     * Create the phases and add them to <code>Project</code>.
     * </p>
     *
     * <p>
     * All the phases' status are initial to be <code>PhaseStatus.SCHEDULED</code>.
     * </p>
     *
     * @param project <code>Project</code>.
     */
    private void addPhases(Project project) {

        //Create a "Draft" phase
        draftPhase = new Phase(project, ONE_HOUR);
        draftPhase.setPhaseType(CockpitPhaseType.DRAFT);
        draftPhase.setPhaseStatus(PhaseStatus.SCHEDULED);
        //Add the phase to the project
        project.addPhase(draftPhase);

        //Create a "Scheduled" phase
        scheduledPhase = new Phase(project, ONE_HOUR);
        scheduledPhase.setPhaseType(CockpitPhaseType.SCHEDULED);
        scheduledPhase.setPhaseStatus(PhaseStatus.SCHEDULED);
        //Add the phase to the project
        project.addPhase(scheduledPhase);

        //Create a "Active" phase
        activePhase = new Phase(project, ONE_HOUR);
        activePhase.setPhaseType(CockpitPhaseType.ACTIVE);
        activePhase.setPhaseStatus(PhaseStatus.SCHEDULED);
        //Add the phase to the project
        project.addPhase(activePhase);

        //Create a "Action Required" phase
        actionRequiredPhase = new Phase(project, ONE_HOUR);
        actionRequiredPhase.setPhaseType(CockpitPhaseType.ACTION_REQUIRED);
        actionRequiredPhase.setPhaseStatus(PhaseStatus.SCHEDULED);
        //Add the phase to the project
        project.addPhase(actionRequiredPhase);

        //create a "Completed" phase
        completedPhase = new Phase(project, ONE_HOUR);
        completedPhase.setPhaseType(CockpitPhaseType.COMPLETED);
        completedPhase.setPhaseStatus(PhaseStatus.SCHEDULED);
        //Add the phase to the project
        project.addPhase(completedPhase);
    }

    /**
     * <p>
     * Create a <code>Project</code>, set necessary attributes.
     * And add phases to project.
     * </p>
     *
     * @return <code>Project</code> created.
     */
    private Project createProject() {

        //create a Project instance
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);

        //These attributes will be used as email template data
        project.setAttribute("date", project.getStartDate());
        project.setAttribute("operator", "guy");
        project.setAttribute("cost", 2L);

        //Create phases and add them to Project, and add project to phase.
        this.addPhases(project);

        //Set project attribute: required minimum submissions as 1
        project.setAttribute("MinimumSubmissions", 1);

        //Set project attribute: resource email
        @SuppressWarnings("unchecked")
        List < String > toAddresses = new ArrayList();
        toAddresses.add("smith@topcoder.com");
        toAddresses.add("john@topcoder.com");
        project.setAttribute("ResourceEmails", (ArrayList) toAddresses);

        //Create a contest instance and set its fields...
        Contest contest = new Contest();
        contest.setStartDate(new Date());
        contest.setEndDate(new Date(System.currentTimeMillis() + CONTEST_DURATION));
        final int moreThan24 = 25;
        contest.setWinnerAnnoucementDeadline(new Date(System.currentTimeMillis() + moreThan24 * ONE_HOUR));

        //Add the contest instance to the project
        project.setAttribute("contest", contest);
        return project;
    }

    /**
     * <p>
     * Demo the usage of this component.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDemo() throws Exception {

        //Create handlers and register them to PhaseManager
        this.registerHandlers();

        //Create project
        Project project = this.createProject();
        Contest contest = (Contest) project.getAttribute("contest");

        //Start "Draft" phase; when the manager.start call is made the status of the contest
        //will be moved to "Draft" and a notification email will be sent
        if (manager.canStart(draftPhase)) {
            manager.start(draftPhase, "operator");
            System.out.println("Demo: Draft phase started");
        }

        //End "Draft" phase; when the manager.end call is made a notification email will be sent
        if (manager.canEnd(draftPhase)) {
            manager.end(draftPhase, "operator");
            System.out.println("Demo: Draft phase ended");
        }

        //Start "Scheduled" phase; when the manager.start call is made the status of the
        //contest will be moved to "Scheduled" and a notification email will be sent
        if (manager.canStart(scheduledPhase)) {
            manager.start(scheduledPhase, "operator");
            System.out.println("Demo: Scheduled phase started");
        }

        //The ScheduledPhaseHandler will not update contest status, it's updated externally
        contest.setStatus(CockpitContestStatus.SCHEDULED);

        //End "Scheduled" phase; when the manager.end call is made a notification email will be sent
        if (manager.canEnd(scheduledPhase)) {
            manager.end(scheduledPhase, "operator");
            System.out.println("Demo: Scheduled phase ended");
        }

        //Start "Active" phase; when the manager.start call is made the status of the
        //contest will be moved to "Active" and a notification email will be sent
        if (manager.canStart(activePhase)) {
            manager.start(activePhase, "operator");
            System.out.println("Demo: Active phase started");
        }

        //Within "Active" phase, the developer submits to the contest
        this.developerSubmits(contest);

        //End "Active" phase; when the manager.end call is made a notification email will be sent
        if (manager.canEnd(activePhase)) {
            manager.end(activePhase, "operator");
            System.out.println("Demo: Active phase ended");
        }

        //Start "Action Required" phase; when the manager.start call is made the status of
        //the contest will be moved to "ActionRequired" and a notification email will be sent
        if (manager.canStart(actionRequiredPhase)) {
            manager.start(actionRequiredPhase, "operator");
            System.out.println("Demo: Action Required phase started");
        }

        //Within "Action Required" phase, the client takes action to choose a winner
        this.clientChoosesWinner(contest);

        //End "Action Required" phase; when the manager.end call is made a notification email will be sent
        if (manager.canEnd(actionRequiredPhase)) {
            manager.end(actionRequiredPhase, "operator");
            System.out.println("Demo: Action Required phase ended");
        }

        //Start "Completed" phase; when the manager.start call is made the status of the
        //contest will be moved to "Completed" and a notification email will be sent
        if (manager.canStart(completedPhase)) {
            manager.start(completedPhase, "operator");
            System.out.println("Demo: Completed phase started");
        }
    }

    /**
     * <p>
     * The developer submits to the contest.
     * </p>
     *
     * @param contest contest.
     *
     * @throws Exception to JUnit.
     */
    private void developerSubmits(Contest contest) throws Exception {

        //Wait to the Contest.endDate
        Thread.sleep(CONTEST_DURATION);

        //Now the contest has submission...
        @SuppressWarnings("unchecked")
        Set < Submission > submissions = new HashSet();
        submissions.add(new Submission());
        contest.setSubmissions(submissions);
    }

    /**
     * <p>
     * The client chooses a winner of the contest.
     * </p>
     *
     * @param contest contest.
     */
    private void clientChoosesWinner(Contest contest) {
        @SuppressWarnings("unchecked")
        Set < ContestResult > results = new HashSet();
        results.add(new ContestResult());
        contest.setResults(results);
    }
}
