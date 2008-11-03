/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.accuracytests;

import com.topcoder.clientcockpit.phases.messagegenerators.DefaultEmailMessageGenerator;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;

/**
 * <p>
 * Accuracy tests for the <code>DefaultEmailMessageGenerator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultEmailMessageGeneratorAccuracyTest extends BaseTestCase {

    /**
     * <p>
     * Represents the <code>TimeFieldHandler</code> instance for test.
     * </p>
     */
    private DefaultEmailMessageGenerator generator;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        generator = new DefaultEmailMessageGenerator();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DefaultEmailMessageGenerator()</code>, expects the instance is
     * created properly.
     * </p>
     */
    public void testCtorAccuracy() {
        assertNotNull("Expects the instance is created properly.", generator);
    }

    /**
     * <p>
     * Accuracy test for the <code>generateMessage(Template, Phase)</code> method, expects the message is generated
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGenerateMessageAccuracy() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.OPEN, TestHelper.CockpitPhaseType.DRAFT);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);

        DocumentGenerator documentGenerator = DocumentGenerator.getInstance();
        Template template = documentGenerator.getTemplate("startTemplateSource", "AnyName");

        String message = generator.generateMessage(template, phase);
        assertEquals("Expects the message is generated properly.", message, "Date is " + DATE
                + ", operator is guy, cost is 2$.");
    }
}
