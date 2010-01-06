/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.stresstests;

import java.util.HashMap;

import com.cronos.onlinereview.phases.AbstractPhaseHandler;
import com.cronos.onlinereview.phases.BaseTest;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;
import com.topcoder.util.config.ConfigManager;


/**
 * The stress tests for this components.
 *
 * @author KLW, moon.river
 * @version 1.2
 * @since 1.1
 */
public class OnlineReviewStressTests extends BaseTest {
    /**
     * sets up the test environment.
     *
     * @throws Exception
     *             if any exception occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();

        ConfigManager configManager = ConfigManager.getInstance();

        configManager.add(PHASE_HANDLER_CONFIG_FILE);

        configManager.add(MANAGER_HELPER_CONFIG_FILE);

        // add the component configurations as well
        for (int i = 0; i < COMPONENT_FILE_NAMES.length; i++) {
            configManager.add(COMPONENT_FILE_NAMES[i]);
        }
    }

    /**
     * tears down the test environment.
     *
     * @throws Exception
     *             if any exception occurs.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * stress test for this componment.
     *
     * @throws Exception
     *             if any exception occurs.
     */
    public void testCreateHandler() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            new AbstractPhaseHandler("com.cronos.onlinereview.phases.AbstractPhaseHandler") {
                public boolean canPerform(Phase arg0) throws PhaseHandlingException {
                    return false;
                }
                public void perform(Phase arg0, String arg1) throws PhaseHandlingException {
                }};
        }
        System.out.println("Creating handler used : " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * stress test for this componment.
     *
     * @throws Exception
     *             if any exception occurs.
     */
    public void testSendMail() throws Exception {
        // set up the notification resources. roles. projects. phases
        setupProjectResourcesNotification("All", false);

        Phase phase = createPhase(1, 2, "Open", 1, "Registration");

        
        AbstractPhaseHandler handler =
            new AbstractPhaseHandler("com.cronos.onlinereview.phases.AbstractPhaseHandler") {
            public boolean canPerform(Phase arg0) throws PhaseHandlingException {
                return false;
            }
            public void perform(Phase arg0, String arg1) throws PhaseHandlingException {
            }};

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            handler.sendEmail(phase, new HashMap());
        }
        System.out.println("Sending email used : " + (System.currentTimeMillis() - start) + "ms");
    }
}
