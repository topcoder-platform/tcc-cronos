/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.messagegenerators;

import com.topcoder.clientcockpit.phases.BaseTestCase;
import com.topcoder.clientcockpit.phases.TestHelper.CockpitPhaseType;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;

/**
 * <p>
 * Accuracy tests for <code>DefaultEmailMessageGenerator</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultEmailMessageGeneratorTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link DefaultEmailMessageGenerator#DefaultEmailMessageGenerator()}.
     * </p>
     *
     * <p>
     * The instance of <code>DefaultEmailMessageGenerator</code> should be created.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("DefaultEmailMessageGenerator should be created.", new DefaultEmailMessageGenerator());
    }

    /**
     * <p>
     * Test {@link DefaultEmailMessageGenerator#generateMessage(
     * com.topcoder.util.file.Template, com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateMessage() throws Exception {

        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.DRAFT);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);

        DocumentGenerator documentGenerator = DocumentGenerator.getInstance();
        Template template = documentGenerator.getTemplate("startTemplateSource", "AnyName");

        String message = new DefaultEmailMessageGenerator().generateMessage(template, phase);
        assertEquals(message, "Date is " + DATE + ", operator is guy, cost is 2$.");
    }
}
