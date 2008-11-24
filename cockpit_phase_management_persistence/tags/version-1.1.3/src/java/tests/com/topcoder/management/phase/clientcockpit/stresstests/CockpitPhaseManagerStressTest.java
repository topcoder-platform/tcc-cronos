/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.stresstests;

import java.io.File;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.management.phase.clientcockpit.CockpitPhaseManager;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.util.cache.Cache;
import com.topcoder.util.cache.SimpleCache;
import com.topcoder.util.config.ConfigManager;

/**
 * Stress tests for class {@link CockpitPhaseManager}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CockpitPhaseManagerStressTest extends BaseStressTests {

    /**
     * Represents {@link CockpitPhaseManager} instance for test.
     */
    private CockpitPhaseManager manager;

    /**
     * Represents {@link ContestManager} instance for test.
     */
    private MockContestManager contestManager;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void setUp() throws Exception {
        manager = new CockpitPhaseManager();
        contestManager = new MockContestManager();
        loadConfig("config.xml");
        super.setUp();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        clearConfig();
        super.tearDown();
    }

    /**
     * Stress test for constructor method CockpitPhaseManager(String namespace).
     *
     * @throws Exception
     *             to junit
     */
    public void testCockpitPhaseManager_Namespace_Stress() throws Exception {
        beginTest();
        for (int i = 0; i < RUN_TIMES; i++) {
            manager = new CockpitPhaseManager("com.topcoder.management.phase.clientcockpit.CockpitPhaseManager");
            assertNotNull("manager should be created", manager);
        }
        endTest("CockpitPhaseManager(String namespace)", 100);
    }

    /**
     * Stress test for constructor method CockpitPhaseManager(ContestManager, String, Cache).
     *
     * @throws Exception
     *             to junit
     */
    public void testCockpitPhaseManager_ContestManager_String_Cache_Stress() throws Exception {
        beginTest();
        String logName = "CockpitPhaseManager";
        Cache cachedContestStatuses = new SimpleCache();

        for (int i = 0; i < RUN_TIMES; i++) {
            manager = new CockpitPhaseManager(contestManager, logName, cachedContestStatuses);
            assertNotNull("manager should be created", manager);
        }
        endTest("CockpitPhaseManager(String namespace)", 1000);
    }

    /**
     * Stress test for method start(Phase, String).
     *
     * @throws Exception
     *             to junit
     */
    public void testStart_Stress() throws Exception {
        manager.setContestManager(contestManager);
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.set(2008, 1, 1, 1, 1, 1);
        Project project = new Project(cal.getTime(), new DefaultWorkdays());
        project.setId(0);
        PhaseType type = new PhaseType(0, "Draft");
        Phase phase = new Phase(project, 0);
        phase.setPhaseType(type);
        
        MockPhaseHandler handler = new MockPhaseHandler();
        manager.registerHandler(handler, type, PhaseOperationEnum.START);

        beginTest();
        for (int i = 0; i < RUN_TIMES; i++) {

            manager.start(phase, "operator");

            assertEquals("getUpdatedContestId", 0, contestManager.getUpdatedContestId());

            assertEquals("getOperator", "operator", handler.getOperator());
            assertEquals("getPhaseStatus", PhaseStatus.OPEN, phase.getPhaseStatus());
        }
        endTest("start(Phase, String)", 1000);
    }

    /**
     * Stress test for method end(Phase, String).
     *
     * @throws Exception
     *             to junit
     */
    public void testEnd_Stress() throws Exception {
        manager.setContestManager(contestManager);
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.set(2008, 1, 1, 1, 1, 1);
        Project project = new Project(cal.getTime(), new DefaultWorkdays());
        project.setId(0);
        PhaseType type = new PhaseType(0, "Draft");
        Phase phase = new Phase(project, 0);
        phase.setPhaseType(type);
        
        MockPhaseHandler handler = new MockPhaseHandler();
        manager.registerHandler(handler, type, PhaseOperationEnum.END);

        beginTest();
        for (int i = 0; i < RUN_TIMES; i++) {

            manager.end(phase, "operator");

            assertEquals("getUpdatedContestId", 0, contestManager.getUpdatedContestId());

            assertEquals("getOperator", "operator", handler.getOperator());
            assertEquals("getPhaseStatus", PhaseStatus.CLOSED, phase.getPhaseStatus());
        }
        endTest("end(Phase, String)", 1000);
    }

    /**
     * Removes all namespace.
     *
     * @throws Exception
     *             to junit
     */
    private static void clearConfig() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();
        for (Iterator iterator = manager.getAllNamespaces(); iterator.hasNext();) {
            String namespace = (String) iterator.next();
            manager.removeNamespace(namespace);
        }
    }

    /**
     * Add config file.
     *
     * @param filename
     *            the config file name.
     * @throws Exception
     *             to junit
     */
    private static void loadConfig(String filename) throws Exception {
        ConfigManager manager = ConfigManager.getInstance();
        manager.add(new File("test_files" + File.separator + "stresstests" + File.separator + filename)
                .getCanonicalPath());
    }

}
