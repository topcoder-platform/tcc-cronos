/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.accuracytests;

import com.orpheus.user.persistence.UserConstants;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.config.ConfigManager;
import junit.framework.Assert;

import java.util.Iterator;

/**
 * <p>
 * This class contains helper methods to use in accuracy test cases.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class AccuracyHelper {
    /**
     * <p>
     * Load all neccessary configration files for the component.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public static void loadConfiguration(String fileName) throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        configManager.add(fileName);
    }

    /**
     * <p>
     * Clear all configuration namespaces.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public static void clearAllConfigurationNS() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * Load the configuration files of the base component for testing.
     *
     * @throws Exception to JUnit.
     */
    public static void loadBaseConfig() throws Exception {
        AccuracyHelper.loadConfiguration("test_conf/accuracy/logging.xml");
        AccuracyHelper.loadConfiguration("test_conf/accuracy/profiletypes.xml");
        AccuracyHelper.loadConfiguration("test_conf/accuracy/daofactory.xml");
        AccuracyHelper.loadConfiguration("test_conf/accuracy/dao.xml");
        AccuracyHelper.loadConfiguration("test_conf/accuracy/simplecache.xml");
        AccuracyHelper.loadConfiguration("test_conf/accuracy/dbconnectionfactory.xml");
    }

    /**
     * Compares the general information of the two user profiles.
     * @param expected The expected profile.
     * @param actual The actual profile.
     */
    public static void compareProfiles(UserProfile expected, UserProfile actual) {
        Assert.assertEquals("Not the expected identifier.", expected.getIdentifier(), actual.getIdentifier());

        Assert.assertEquals("Not the expected handle.", expected.getProperty(UserConstants.CREDENTIALS_HANDLE),
                actual.getProperty(UserConstants.CREDENTIALS_HANDLE));
        Assert.assertEquals("Not the expected email address.", expected.getProperty(BaseProfileType.EMAIL_ADDRESS),
                actual.getProperty(BaseProfileType.EMAIL_ADDRESS));
        Assert.assertEquals("Not the expected password.", expected.getProperty(UserConstants.CREDENTIALS_PASSWORD),
                actual.getProperty(UserConstants.CREDENTIALS_PASSWORD));
        Assert.assertEquals("Not the expected active status.", expected.getProperty(UserConstants.CREDENTIALS_IS_ACTIVE),
                actual.getProperty(UserConstants.CREDENTIALS_IS_ACTIVE));
    }
}
