/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.stresstests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.UserProjectReliabilityData;
import com.topcoder.reliability.impl.UserReliabilityCalculationException;
import com.topcoder.reliability.impl.calculators.BaseUserReliabilityCalculator;

/**
 * <p>
 * This class contains Stress tests for BaseUserReliabilityCalculator.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class BaseUserReliabilityCalculatorStressTest extends BaseStressTest {

    /**
     * <p>
     * Represents project count constant.
     * </p>
     */
    private static final int PROJECT_COUNT = 100;

    /**
     * <p>
     * Represents BaseUserReliabilityCalculator concrete implementation for testing.
     * </p>
     */
    private BaseUserReliabilityCalculator calculator;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        calculator = new BaseUserReliabilityCalculator() {

            /**
             * <p>
             * Do nothing.
             * </p>
             * @param reliabilityData the reliability date
             * @throws UserReliabilityCalculationException never
             */
            protected void calculateReliabilityAfterProjects(List < UserProjectReliabilityData > reliabilityData)
                throws UserReliabilityCalculationException {
            }
        };
        calculator.configure(new DefaultConfigurationObject("someName"));
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
     * Tests {@link BaseUserReliabilityCalculator#calculate(List)} with valid arguments passed and small test.
     * </p>
     * <p>
     * List of UserProjectReliabilityData should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCalculate_Small() throws Exception {
        List < UserProjectParticipationData > projects = new ArrayList < UserProjectParticipationData >();
        for (int i = 0; i < PROJECT_COUNT; i++) {
            projects.add(createUserProjectParticipationData(new Date(), i, true));
        }
        for (int i = 0; i < getRunCount(); i++) {
            assertEquals("For all projects data should be created.", PROJECT_COUNT, calculator.calculate(projects)
                    .size());
        }
    }

    /**
     * <p>
     * Tests {@link BaseUserReliabilityCalculator#calculate(List)} with valid arguments passed and large test.
     * </p>
     * <p>
     * List of UserProjectReliabilityData should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCalculate_Large() throws Exception {
        List < UserProjectParticipationData > projects = new ArrayList < UserProjectParticipationData >();
        for (int i = 0; i < PROJECT_COUNT * 10; i++) {
            projects.add(createUserProjectParticipationData(new Date(), i, true));
        }
        for (int i = 0; i < getRunCount(); i++) {
            assertEquals("For all projects data should be created.", PROJECT_COUNT * 10, calculator
                    .calculate(projects).size());
        }
    }
}
