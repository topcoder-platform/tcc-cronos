/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.failuretests.impl;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.ProjectCategoryNotSupportedException;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.reliability.failuretests.FailureTestHelper;
import com.topcoder.reliability.impl.ReliabilityCalculatorImpl;
import com.topcoder.reliability.impl.ReliabilityDataPersistenceException;

/**
 * Failure tests for ReliabilityCalculatorImpl class.
 * 
 * @author Yeung
 * @version 1.0
 */
public class ReliabilityCalculatorImplFailureTests {
    /**
     * The ReliabilityCalculatorImpl instance to test against.
     */
    private ReliabilityCalculatorImpl instance;

    /**
     * The configuration object for testing.
     */
    private ConfigurationObject config;

    /**
     * Sets up the test environment.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        config = FailureTestHelper.getConfig("ReliabilityCalculatorImpl");

        instance = new ReliabilityCalculatorImpl();
        instance.configure(config);
    }

    /**
     * Tests the method configure with null config. Expect IllegalArgumentException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConfigureWithNullConfig() throws Exception {
        instance.configure(null);
    }

    /**
     * Tests the method configure with non-string 'loggerName'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithNonStringLoggerName() throws Exception {
        config.setPropertyValue("loggerName", 1);

        instance.configure(config);
    }

    /**
     * Tests the method configure with empty 'loggerName'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithEmptyLoggerName() throws Exception {
        config.setPropertyValue("loggerName", " \t \n ");

        instance.configure(config);
    }

    /**
     * Tests the method configure with missing 'reliabilityDataPersistenceKey'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithMissingReliabilityDataPersistenceKey() throws Exception {
        config.removeProperty("reliabilityDataPersistenceKey");

        instance.configure(config);
    }

    /**
     * Tests the method configure with empty 'reliabilityDataPersistenceKey'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithEmptyReliabilityDataPersistenceKey() throws Exception {
        config.setPropertyValue("reliabilityDataPersistenceKey", " \t \n ");

        instance.configure(config);
    }

    /**
     * Tests the method configure with unknown 'reliabilityDataPersistenceKey'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithUnknownReliabilityDataPersistenceKey() throws Exception {
        config.setPropertyValue("reliabilityDataPersistenceKey", "unknown");

        instance.configure(config);
    }

    /**
     * Tests the method configure with missing 'participationDataComparatorKey'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithMissingParticipationDataComparatorKey() throws Exception {
        config.removeProperty("participationDataComparatorKey");

        instance.configure(config);
    }

    /**
     * Tests the method configure with empty 'participationDataComparatorKey'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithEmptyParticipationDataComparatorKey() throws Exception {
        config.setPropertyValue("participationDataComparatorKey", " \t \n ");

        instance.configure(config);
    }

    /**
     * Tests the method configure with unknown 'participationDataComparatorKey'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithUnknownParticipationDataComparatorKey() throws Exception {
        config.setPropertyValue("participationDataComparatorKey", "unknown");

        instance.configure(config);
    }

    /**
     * Tests the method configure with missing 'objectFactoryConfig'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithMissingObjectFactoryConfig() throws Exception {
        config.removeChild("objectFactoryConfig");

        instance.configure(config);
    }

    /**
     * Tests the method configure with missing 'reliabilityDataPersistenceConfig'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithMissingReliabilityDataPersistenceConfig() throws Exception {
        config.removeChild("reliabilityDataPersistenceConfig");

        instance.configure(config);
    }

    /**
     * Tests the method configure with missing 'projectCategoryIds'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithMissingProjectCategoryIds() throws Exception {
        config.getChild("projectCategoryConfig1").removeProperty("projectCategoryIds");

        instance.configure(config);
    }

    /**
     * Tests the method configure with empty 'projectCategoryIds'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithEmptyProjectCategoryIds() throws Exception {
        config.getChild("projectCategoryConfig1").setPropertyValues("projectCategoryIds", new String[] {});

        instance.configure(config);
    }

    /**
     * Tests the method configure with zero projectCategoryId. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithZeroProjectCategoryId() throws Exception {
        config.getChild("projectCategoryConfig1").setPropertyValues("projectCategoryIds",
                new String[] { "1", "2", "0" });

        instance.configure(config);
    }

    /**
     * Tests the method configure with negative projectCategoryId. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithNegativeProjectCategoryId() throws Exception {
        config.getChild("projectCategoryConfig1").setPropertyValues("projectCategoryIds",
                new String[] { "1", "2", "-1" });

        instance.configure(config);
    }

    /**
     * Tests the method configure with invalid projectCategoryId. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithInvalidProjectCategoryId() throws Exception {
        config.getChild("projectCategoryConfig1").setPropertyValues("projectCategoryIds",
                new String[] { "1", "2", "invalid" });

        instance.configure(config);
    }

    /**
     * Tests the method configure with missing 'reliabilityStartDate'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithMissingReliabilityStartDate() throws Exception {
        config.getChild("projectCategoryConfig1").removeProperty("reliabilityStartDate");

        instance.configure(config);
    }

    /**
     * Tests the method configure with empty 'reliabilityStartDate'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithEmptyReliabilityStartDate() throws Exception {
        config.getChild("projectCategoryConfig1").setPropertyValue("reliabilityStartDate", " \t \n ");

        instance.configure(config);
    }

    /**
     * Tests the method configure with missing 'userReliabilityCalculatorKey'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithMissingUserReliabilityCalculatorKey() throws Exception {
        config.getChild("projectCategoryConfig1").removeProperty("userReliabilityCalculatorKey");

        instance.configure(config);
    }

    /**
     * Tests the method configure with empty 'userReliabilityCalculatorKey'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithEmptyUserReliabilityCalculatorKey() throws Exception {
        config.getChild("projectCategoryConfig1").setPropertyValue("userReliabilityCalculatorKey", " \t \n ");

        instance.configure(config);
    }

    /**
     * Tests the method configure with missing 'resolutionDateDetectorKey'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithMissingResolutionDateDetectorKey() throws Exception {
        config.getChild("projectCategoryConfig1").removeProperty("resolutionDateDetectorKey");

        instance.configure(config);
    }

    /**
     * Tests the method configure with empty 'resolutionDateDetectorKey'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithEmptyResolutionDateDetectorKey() throws Exception {
        config.getChild("projectCategoryConfig1").setPropertyValue("resolutionDateDetectorKey", " \t \n ");

        instance.configure(config);
    }

    /**
     * Tests the method configure with missing 'userReliabilityCalculatorConfig'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithMissingUserReliabilityCalculatorConfig() throws Exception {
        config.getChild("projectCategoryConfig1").removeChild("userReliabilityCalculatorConfig");

        instance.configure(config);
    }

    /**
     * Tests the method configure with missing 'resolutionDateDetectorConfig'. Expect
     * ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithMissingResolutionDateDetectorConfig() throws Exception {
        config.getChild("projectCategoryConfig1").removeChild("resolutionDateDetectorConfig");

        instance.configure(config);
    }

    /**
     * Tests the method calculate with negative projectCategoryId. Expect IllegalArgumentException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithNegativeProjectCategoryId() throws Exception {
        config.removeChild("reliabilityDataPersistenceConfig");

        instance.calculate(-1, true);
    }

    /**
     * Tests the method calculate with zero projectCategoryId. Expect IllegalArgumentException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithZeroProjectCategoryId() throws Exception {
        config.removeChild("reliabilityDataPersistenceConfig");

        instance.calculate(0, true);
    }

    /**
     * Tests the method calculate without configured. Expect IllegalStateException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void testCalculateWithoutConfigured() throws Exception {
        instance = new ReliabilityCalculatorImpl();

        instance.calculate(1, true);
    }

    /**
     * Tests the method calculate with unknown projectCategoryId. Expect ProjectCategoryNotSupportedException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectCategoryNotSupportedException.class)
    public void testCalculateWithUnknownProjectCategoryId() throws Exception {
        instance.calculate(100, true);
    }

    /**
     * Tests the method calculate with persistence error. Expect ReliabilityDataPersistenceException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityDataPersistenceException.class)
    public void testCalculateWithPersistenceError() throws Exception {
        config.getChild("reliabilityDataPersistenceConfig").getChild("dbConnectionFactoryConfig")
                .getChild("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").getChild("connections")
                .getChild("myConnection").getChild("parameters").setPropertyValue("jdbc_url", "invalid");

        instance.configure(config);

        instance.calculate(1, true);
    }
}
