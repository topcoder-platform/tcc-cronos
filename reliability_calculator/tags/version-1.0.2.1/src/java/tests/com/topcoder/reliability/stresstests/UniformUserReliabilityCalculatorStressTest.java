/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.stresstests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.UserProjectReliabilityData;
import com.topcoder.reliability.impl.calculators.UniformUserReliabilityCalculator;

/**
 * <p>
 * This class contains Stress tests for UniformUserReliabilityCalculator.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class UniformUserReliabilityCalculatorStressTest extends BaseStressTest {

    /**
     * <p>
     * Represents project count constant.
     * </p>
     */
    private static final int PROJECT_COUNT = 100;

    /**
     * <p>
     * Represents history length.
     * </p>
     */
    private static final int HISTORY_LENGTH = 15;

    /**
     * <p>
     * Represents UniformUserReliabilityCalculator concrete implementation for testing.
     * </p>
     */
    private UniformUserReliabilityCalculator calculator;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        calculator = new UniformUserReliabilityCalculator();
        calculator.configure(getConfigurationObject(new String[] {"reliabilityCalculator1", "config",
                                                                  "projectCategoryConfig1",
                                                                  "userReliabilityCalculatorConfig"}));
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
     * Tests {@link UniformUserReliabilityCalculator#calculate(List)} with valid arguments passed and small test.
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
            List < UserProjectReliabilityData > result = calculator.calculate(projects);
            assertEquals("For all projects data should be created.", PROJECT_COUNT, result.size());
            // all projects should be reliable, as all UserProjectParticipationData passed review
            for (UserProjectReliabilityData userProjectReliabilityData : result) {
                assertEquals("Project should be reliable.", 1.0, userProjectReliabilityData
                        .getReliabilityAfterResolution());
            }
        }
    }

    /**
     * <p>
     * Tests {@link UniformUserReliabilityCalculator#calculate(List)} with valid arguments passed and large test.
     * </p>
     * <p>
     * List of UserProjectReliabilityData should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCalculate_Large() throws Exception {
        List < UserProjectParticipationData > projects = new ArrayList < UserProjectParticipationData >();
        projects.add(createUserProjectParticipationData(new Date(), 0, true));
        for (int i = 1; i < PROJECT_COUNT; i++) {
            projects.add(createUserProjectParticipationData(new Date(), i, false));
        }
        for (int i = 0; i < getRunCount(); i++) {
            List < UserProjectReliabilityData > result = calculator.calculate(projects);
            assertEquals("For all projects data should be created.", PROJECT_COUNT, result.size());
            // all projects should not be reliable, except first, as all UserProjectParticipationData didn't pass
            // review, except first
            assertEquals("Project should be reliable.", 1.0, result.get(0).getReliabilityAfterResolution());
            for (int j = 1; j < getRunCount(); j++) {
                UserProjectReliabilityData userProjectReliabilityData = result.get(j);
                if (j >= HISTORY_LENGTH) {
                    assertEquals("Project should not be reliable.", 0.0, userProjectReliabilityData
                            .getReliabilityAfterResolution());
                } else {
                    assertEquals("Project should be reliable.", 1 / (double) (j + 1), userProjectReliabilityData
                            .getReliabilityAfterResolution());
                }
            }
        }
    }
}
