/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.stresstests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clientcockpit.phases.messagegenerators.DefaultEmailMessageGenerator;
import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;

/**
 * <p>
 * Stress test for <code>DefaultEmailMessageGenerator</code> class. Mainly focus on the following
 * function's performance:
 * <ul>
 * <li>DefaultEmailMessageGenerator#generateMessage(Template, Phase)</li>
 * </ul>
 * </p>
 *
 * @author Littleken
 * @version 1.0
 */
public class DefaultEmailMessageGeneratorStressTest extends StressTestCase {

    /**
     * <p>
     * Test method <code>DefaultEmailMessageGenerator#generateMessage(Template, Phase)</code> for
     * stress tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGenerateMessageStress() throws Exception {
        long totalTime = 0;

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

            DocumentGenerator documentGenerator = DocumentGenerator.getInstance();
            Template template = documentGenerator.getTemplate("startTemplateSource", "AnyName");

            start();
            String message = new DefaultEmailMessageGenerator().generateMessage(template, phase);
            stop();

            totalTime += getMilliseconds();

            assertTrue(message.indexOf("operator is littleken") != -1);
        }

        System.out.println("Run DefaultEmailMessageGenerator#generateMessage(Template, Phase) for " + LOOP
            + " times consumes " + totalTime + " milliseconds.");
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

        // Sets phase status to be OPEN represents that the phase to be closed.
        phase.setPhaseStatus(PhaseStatus.OPEN);

        // Sets phase type.
        PhaseType abandonedPhaseType = new PhaseType(6, "Draft");
        phase.setPhaseType(abandonedPhaseType);

        return phase;
    }
}
