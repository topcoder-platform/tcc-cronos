/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.stresstests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clientcockpit.phases.CompletedPhaseHandler;
import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.service.studio.contest.Contest;

/**
 * <p>
 * Stress test for <code>CompletedPhaseHandler</code> class. Mainly focus on the following
 * function's performance:
 * <ul>
 * <li>CompletedPhaseHandler#perform(Phase, String)</li>
 * </ul>
 * </p>
 *
 * @author Littleken
 * @version 1.0
 */
public class CompletedPhaseHandlerStressTest extends StressTestCase {

    /**
     * <p>
     * Test method <code>CompletedPhaseHandler#perform(Phase, String)</code> for stress tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCompletedPhaseHandlerPerformStress() throws Exception {
        long totalTime = 0;

        // Starts the SMTP server.
        startServer();

        CompletedPhaseHandler handler = new CompletedPhaseHandler();

        try {
            for (int i = 0; i < LOOP; ++i) {

                // Gets phase.
                Phase phase = getPhase();

                // Creates contest.
                Contest contest = new Contest();
                List<String> toAddresses = new ArrayList<String>();

                // Adds emails to be sent.
                toAddresses.add(EMAIL_ADDRESS);

                phase.getProject().setAttribute(RESOURCE_EMAILS, (ArrayList) toAddresses);
                phase.getProject().setAttribute(CONTEST, contest);

                // Contest status has not been update, should be null.
                assertNull("Contest status should be updated", contest.getStatus());

                start();
                handler.perform(phase, "operator");
                stop();

                totalTime += getMilliseconds();

                // Contest status has been update as "Completed", should not be null.
                assertNotNull("Contest status should not be null", contest.getStatus());
            }
        } finally {

            stopServer();
        }

        System.out.println("Run CompletedPhaseHandler#perform(Phase, String) for " + LOOP + " times consumes "
                + totalTime + " milliseconds.");
    }

    /**
     * <p>
     * Creates <code>Phase</code> instance for stress tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    private Phase getPhase() throws Exception {
        // Creates Project instance.
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(DATE, workdays);

        // Creates Phase instance.
        Phase phase = new Phase(project, System.currentTimeMillis());
        phase.setAttribute("date", DATE);
        phase.setAttribute("operator", OPERATOR);
        phase.getProject().setAttribute("cost", COST);

        // Adds phase to project.
        project.addPhase(phase);

        // Sets phase status to be SCHEDULED represents that the phase to be start.
        phase.setPhaseStatus(PhaseStatus.SCHEDULED);

        // Sets phase type.
        PhaseType abandonedPhaseType = new PhaseType(5, "Completed");
        phase.setPhaseType(abandonedPhaseType);

        return phase;
    }
}
