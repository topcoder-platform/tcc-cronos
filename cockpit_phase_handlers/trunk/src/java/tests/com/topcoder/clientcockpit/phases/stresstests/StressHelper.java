/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.stresstests;

import java.util.Iterator;

import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.smtp.server.test.TestSMTPServer;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

public class StressHelper {

    /**
     * <p>
     * The <code>TestSMTPServer</code> instance represents for the SMTP server for stress tests.
     * </p>
     */
    private static TestSMTPServer testSMTPServer;

    /**
     * <p>
     * Represents a mock instance of <code>ContestManager</code> for stress tests.
     * </p>
     */
    public static final ContestManager CONTEST_MANAGER = new MockContestManagerForStress();

    /**
     * <p>
     * Get the <code>TestSMTPServer</code>.
     * </p>
     *
     * @return <code>TestSMTPServer</code>.
     */
    public static TestSMTPServer getTestSMTPServer() {
        if (testSMTPServer == null) {
            try {
                testSMTPServer = new TestSMTPServer();
            } catch (ConfigManagerException cme) {
                cme.printStackTrace();
            }
        }

        return testSMTPServer;
    }

    /**
     * <p>
     * Initialize the config manager with the config files located in the /test_files.
     * </p>
     *
     * @throws ConfigManagerException
     *             to JUnit.
     */
    public static void setUpConfig() throws ConfigManagerException {
        clearConfig();

        ConfigManager manager = ConfigManager.getInstance();

        manager.add(StressHelper.class.getResource("/stresstests/config.xml"));
        // manager.add(StressHelper.class.getResource("/stresstests/ObjectPool.xml"));
        // manager.add(StressHelper.class.getResource("/stresstests/EmailEngine.xml"));
        // manager.add(StressHelper.class.getResource("/stresstests/DocumentManager.xml"));
        // manager.add(StressHelper.class.getResource("/stresstests/PhaseHandler.xml"));
        // manager.add(StressHelper.class.getResource("/stresstests/ObjectFactory.xml"));
    }

    /**
     * <p>
     * Clear all the namespaces in the config manager.
     * </p>
     *
     * @throws ConfigManagerException
     *             to JUnit.
     */
    public static void clearConfig() throws ConfigManagerException {
        ConfigManager manager = ConfigManager.getInstance();

        Iterator namespaceIterator = manager.getAllNamespaces();

        while (namespaceIterator.hasNext()) {
            manager.removeNamespace((String) namespaceIterator.next());
        }
    }
}
