/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.stresstests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.impl.ReliabilityCalculatorImpl;

/**
 * <p>
 * This class contains Stress test for ReliabilityCalculatorImpl.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReliabilityCalculatorImplStressTest extends BaseStressTest {

    /**
     * <p>
     * Represents ReliabilityCalculatorImpl instance for testing.
     * </p>
     */
    private ReliabilityCalculatorImpl calculator;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        calculator = new ReliabilityCalculatorImpl();
        ConfigurationObject config = getConfigurationObject(new String[] {"reliabilityCalculator1", "config"});
        calculator.configure(config);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        calculator = null;
    }

    /**
     * <p>
     * Tests {@link ReliabilityCalculatorImpl#calculate(long, boolean)} with valid arguments passed.
     * </p>
     * <p>
     * Small data set is used. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCalculate_Small() throws Exception {
        for (int i = 0; i < getRunCount(); i++) {
            calculator.calculate(1, true);
            assertEquals("getIdsOfUsersWithReliability() should be called only once per run.", i + 1,
                    MockStressPersistence.getIdsOfUsersWithReliabilityMethodRun);
            assertEquals("getUserParticipationData() should be called user_count times per run.", (i + 1)
                    * MockStressPersistence.USER_COUNT, MockStressPersistence.getUserParticipationDataMethodRun);
            assertEquals("updateCurrentUserReliability() should called user_count times per run.", (i + 1)
                    * MockStressPersistence.USER_COUNT, MockStressPersistence.updateCurrentUserReliabilityMethodRun);
        }
    }

    /**
     * <p>
     * Tests {@link ReliabilityCalculatorImpl#calculate(long, boolean)} with valid arguments passed.
     * </p>
     * <p>
     * Large data set is used. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCalculate_Large() throws Exception {
        for (int i = 0; i < getRunCount(); i++) {
            calculator.calculate(1, true);
            assertEquals("getIdsOfUsersWithReliability() should be called only once per run.", i + 1,
                    MockStressPersistence.getIdsOfUsersWithReliabilityMethodRun);
            assertEquals("getUserParticipationData() should be called user_count times per run.", (i + 1)
                    * MockStressPersistence.USER_COUNT, MockStressPersistence.getUserParticipationDataMethodRun);
            assertEquals("updateCurrentUserReliability() should called user_count times per run.", (i + 1)
                    * MockStressPersistence.USER_COUNT, MockStressPersistence.updateCurrentUserReliabilityMethodRun);
        }
    }

    /**
     * <p>
     * Tests {@link ReliabilityCalculatorImpl#calculate(long, boolean)} with valid arguments passed and a lot of users
     * retrieved from persistence layer.
     * </p>
     * <p>
     * Small data set is used. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCalculate_SmallLotsOfUsers1() throws Exception {
        // case in which we have a lots of users and each user has in average 50 projects to update
        MockStressPersistence.USER_COUNT = 5000;
        MockStressPersistence.USER_PROJECT_COUNT = 50;
        for (int i = 0; i < getRunCount(); i++) {
            calculator.calculate(1, true);
            assertEquals("getIdsOfUsersWithReliability() should be called only once per run.", i + 1,
                    MockStressPersistence.getIdsOfUsersWithReliabilityMethodRun);
            assertEquals("getUserParticipationData() should be called user_count times per run.", (i + 1)
                    * MockStressPersistence.USER_COUNT, MockStressPersistence.getUserParticipationDataMethodRun);
            assertEquals("updateCurrentUserReliability() should called user_count times per run.", (i + 1)
                    * MockStressPersistence.USER_COUNT, MockStressPersistence.updateCurrentUserReliabilityMethodRun);
        }
    }

    /**
     * <p>
     * Tests {@link ReliabilityCalculatorImpl#calculate(long, boolean)} with valid arguments passed and a lot of users
     * retrieved from persistence layer.
     * </p>
     * <p>
     * Small data set is used. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCalculate_SmallLotsOfUsers2() throws Exception {
        // case in which we have all active users of topcoder competitions
        MockStressPersistence.USER_COUNT = 50000;
        MockStressPersistence.USER_PROJECT_COUNT = 50;
        for (int i = 0; i < getRunCount(); i++) {
            calculator.calculate(1, true);
            assertEquals("getIdsOfUsersWithReliability() should be called only once per run.", i + 1,
                    MockStressPersistence.getIdsOfUsersWithReliabilityMethodRun);
            assertEquals("getUserParticipationData() should be called user_count times per run.", (i + 1)
                    * MockStressPersistence.USER_COUNT, MockStressPersistence.getUserParticipationDataMethodRun);
            assertEquals("updateCurrentUserReliability() should called user_count times per run.", (i + 1)
                    * MockStressPersistence.USER_COUNT, MockStressPersistence.updateCurrentUserReliabilityMethodRun);
        }
    }
}
