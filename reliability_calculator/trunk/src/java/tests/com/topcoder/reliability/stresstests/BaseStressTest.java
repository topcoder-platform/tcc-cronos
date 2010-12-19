/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.stresstests;

import java.io.File;
import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;
import com.topcoder.reliability.impl.UserProjectParticipationData;

/**
 * <p>
 * This is base test case for Stress tests.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class BaseStressTest extends TestCase {

    /**
     * <p>
     * Represents constant path to stress test directory.
     * </p>
     */
    protected static final String STRESS_TEST_DIR =
            System.getProperty("user.dir") + File.separator + "test_files" + File.separator + "stresstests"
                    + File.separator;

    /**
     * <p>
     * Represents run count for small test.
     * </p>
     */
    protected static final int SMALL_TEST = 2;

    /**
     * <p>
     * Represents run count for large test.
     * </p>
     */
    protected static final int LARGE_TEST = 2;

    /**
     * <p>
     * Represents stress test configuration file name.
     * </p>
     */
    private static final String STRESS_TEST_CONFIG_FILE = STRESS_TEST_DIR + "config.xml";

    /**
     * <p>
     * Represents stress test default configuration namespace.
     * </p>
     */
    private static final String STRESS_TEST_DEFAULT_NAMESPACE =
            "com.topcoder.reliability.ReliabilityCalculationUtility";

    /**
     * <p>
     * Represents run count of test case.
     * </p>
     */
    private int runCount;

    /**
     * <p>
     * Represents start time of test case.
     * </p>
     */
    private long startTime;

    /**
     * <p>
     * Represents root ConfigurationObject instance created from "/test_files/stresstests/config.xml".
     * </p>
     */
    private ConfigurationObject configuration;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        String testName = getName();
        // check test type
        if (testName.contains("Small")) {
            runCount = SMALL_TEST;
        } else {
            runCount = LARGE_TEST;
        }
        startTime = System.currentTimeMillis();
        XMLFilePersistence filePersistence = new XMLFilePersistence();
        configuration =
                filePersistence.loadFile(STRESS_TEST_DEFAULT_NAMESPACE, new File(STRESS_TEST_CONFIG_FILE)).getChild(
                        STRESS_TEST_DEFAULT_NAMESPACE);
        initPersistence();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        long workingTime = (System.currentTimeMillis() - startTime);
        System.out.println("Test " + getName() + " running time " + workingTime + " ms for run count " + runCount
                + ". Average time per run - " + workingTime / runCount + " ms/run.");
        configuration = null;
    }

    /**
     * <p>
     * Sets test run count.
     * </p>
     * @param runCount the run count to set
     */
    protected void setRunCount(int runCount) {
        this.runCount = runCount;
    }

    /**
     * <p>
     * Retrieves test run count.
     * </p>
     * @return test run count
     */
    protected int getRunCount() {
        return runCount;
    }

    /**
     * <p>
     * Retrieves last child ConfigurationObject from main stress test configuration.
     * </p>
     * @param childHierarchy the child sequence to get config from main configuration
     * @return last child ConfigurationObject from main stress test configuration
     * @throws Exception if any error occurs
     */
    protected ConfigurationObject getConfigurationObject(String[] childHierarchy) throws Exception {
        ConfigurationObject result = configuration;
        for (String childName : childHierarchy) {
            result = result.getChild(childName);
        }
        return result;
    }

    /**
     * <p>
     * Creates UserProjectParticipationData with given resolution and project id.
     * </p>
     * @param resolutionDate the resolution date
     * @param projectId the project id
     * @param passedReview the passed review flag
     * @return UserProjectParticipationData with given resolution and project id
     */
    protected UserProjectParticipationData createUserProjectParticipationData(Date resolutionDate, long projectId,
            boolean passedReview) {
        UserProjectParticipationData data = new UserProjectParticipationData();
        data.setResolutionDate(resolutionDate);
        // resolution date is after registration
        data.setUserRegistrationDate(new Date(resolutionDate.getTime() - 1));
        data.setProjectId(projectId);
        data.setPassedReview(passedReview);
        return data;
    }
    /**
     * <p>
     * Initializes MockStressPersistence method run values with zeros and user count and user project count values.
     * </p>
     */
    private void initPersistence() {
        MockStressPersistence.getIdsOfUsersWithReliabilityMethodRun = 0;
        MockStressPersistence.getUserParticipationDataMethodRun = 0;
        MockStressPersistence.updateCurrentUserReliabilityMethodRun = 0;
        // in each category we have 100 users and in average 15 projects
        MockStressPersistence.USER_COUNT = 100;
        MockStressPersistence.USER_PROJECT_COUNT = 15;
    }

}
