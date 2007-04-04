/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.failuretests;

import java.io.File;
import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure testing helper class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
class FailureTestHelper {
    public static final String FAILURE_CONFIG_BASE = "failuretests" + File.separator;

    public static final String DBCONNECTIONFACTORY_CONFIG = "DBConnectionFactory_Config.xml";

    public static final String SEARCHBUILDER_CONFIG = FAILURE_CONFIG_BASE + "SearchBuilder.xml";

    public static final String INFORMIXAUDITPERSIST_CONFIG = FAILURE_CONFIG_BASE + "InformixAuditPersistence.xml";

    public static final String LOGGING_CONFIG = "Logging_Wrapper.xml";

    public static final String AUDITDELEGATE_CONFIG = FAILURE_CONFIG_BASE + "AuditDelegate.xml";

    public static final String AUDITSESSIONBEAN_CONFIG = FAILURE_CONFIG_BASE + "AuditSessionBean.xml";

    /**
     * <p>
     * private constructor preventing instantiation.
     * </p>
     */
    private FailureTestHelper() {
    }

    /**
     * <p>
     * clear the testing namespaces.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    static void clearNamespaces() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Loads the testing namespaces.
     * </p>
     * @param filename
     *            the file name
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    static void loadNamesapces(String filename) throws Exception {
        ConfigManager.getInstance().add(filename);
    }
}
