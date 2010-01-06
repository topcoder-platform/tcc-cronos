/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.accuracytests;

import java.util.HashMap;
import java.util.Map;

import com.cronos.onlinereview.phases.AbstractPhaseHandler;
import com.cronos.onlinereview.phases.EmailOptions;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;

/**
 * Accuracy tests for V1.2 <code>AbstractPhaseHandler</code>.
 * @author assistant
 * @version 1.2
 */
public class AbstractPhaseHandlerTestV12 extends BaseTestCase {

    /**
     * Instance to test.
     */
    private AbstractPhaseHandler instance;

    /**
     * Sets up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        instance = new AbstractPhaseHandler("com.cronos.onlinereview.phases.AbstractPhaseHandler") {
            public boolean canPerform(Phase arg0) throws PhaseHandlingException {
                return false;
            }
            public void perform(Phase arg0, String arg1) throws PhaseHandlingException {
            }};
    }

    /**
     * Cleans up the environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for {@link com.cronos.onlinereview.phases.AbstractPhaseHandler
     * #AbstractPhaseHandler(java.lang.String)}.
     */
    public void testAbstractPhaseHandler() {
        // check whether the configuration about email is loaded
        Map<String, EmailOptions> startPhaseEmailOptions =
            (Map<String, EmailOptions>) getPrivateField(
                    AbstractPhaseHandler.class, instance, "startPhaseEmailOptions");
        assertEquals("There should be 6 roles.", 6, startPhaseEmailOptions.size());

        // check the default configurations for phase start
        EmailOptions defaultOpt = startPhaseEmailOptions.get("default");
        assertNotNull("The default email options are missing.", defaultOpt);
        assertEquals("The start phase configuration is wrong.",
                "topcoder.developer@gmail.com", defaultOpt.getFromAddress());
        assertEquals("The start phase configuration is wrong.",
                "Phase Start", defaultOpt.getSubject());
        assertEquals("The start phase configuration is wrong.",
                "test_files/valid_email_template.txt", defaultOpt.getTemplateName());

        // check the default configurations for phase end
        Map<String, EmailOptions> endPhaseEmailOptions =
            (Map<String, EmailOptions>) getPrivateField(
                    AbstractPhaseHandler.class, instance, "endPhaseEmailOptions");
        assertEquals("There should be 6 roles.", 6, startPhaseEmailOptions.size());
        defaultOpt = endPhaseEmailOptions.get("default");

        assertNotNull("The default email options are missing.", defaultOpt);
        assertEquals("The end phase configuration is wrong.",
                "topcoder.developer@gmail.com", defaultOpt.getFromAddress());
        assertEquals("The end phase configuration is wrong.",
                "Phase End", defaultOpt.getSubject());
        assertEquals("The end phase configuration is wrong.",
                "test_files/valid_email_template.txt", defaultOpt.getTemplateName());

        // check the concrete roles
        assertNotNull("The manager emails are not configured.", startPhaseEmailOptions.get("Manager"));
        assertNotNull("The reviewer emails are not configured.", startPhaseEmailOptions.get("Reviewer"));
        assertNull("The submitter emails are not configured.", startPhaseEmailOptions.get("Submitter"));
        assertNotNull("The observer emails are not configured.", startPhaseEmailOptions.get("Observer"));
        assertNotNull("The final reviewer emails are not configured.", startPhaseEmailOptions.get("Final Reviewer"));
        assertNotNull("The approver emails are not configured.", startPhaseEmailOptions.get("Approver"));
        assertNotNull("The manager emails are not configured.", endPhaseEmailOptions.get("Manager"));
        assertNotNull("The reviewer emails are not configured.", endPhaseEmailOptions.get("Reviewer"));
        assertNull("The submitter emails are not configured.", endPhaseEmailOptions.get("Submitter"));
        assertNotNull("The observer emails are not configured.", endPhaseEmailOptions.get("Observer"));
        assertNotNull("The final reviewer emails are not configured.", endPhaseEmailOptions.get("Final Reviewer"));
        assertNotNull("The approver emails are not configured.", endPhaseEmailOptions.get("Approver"));
    }

    /**
     * Test method for {@link com.cronos.onlinereview.phases.AbstractPhaseHandler
     * #sendEmail(com.topcoder.project.phases.Phase, java.util.Map)}.
     * Because it's hard to test the email sent, the email content is checked manually.
     * @throws Exception to JUnit
     */
    public void testSendEmail() throws Exception {

        // set up the notification resources. roles. projects. phases
        setupProjectResourcesNotification("All", false);

        Phase phase = createPhase(1, 2, "Open", 1, "Registration");

        instance.sendEmail(phase, new HashMap());
    }

}
