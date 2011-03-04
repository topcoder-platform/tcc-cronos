/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.failuretests.impl.calculators;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.reliability.failuretests.FailureTestHelper;
import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.UserProjectReliabilityData;
import com.topcoder.reliability.impl.UserReliabilityCalculationException;
import com.topcoder.reliability.impl.calculators.BaseUserReliabilityCalculator;

/**
 * Failure tests for BaseUserReliabilityCalculator class.
 * 
 * @author Yeung
 * @version 1.0
 */
public class BaseUserReliabilityCalculatorFailureTests {
    /**
     * The BaseUserReliabilityCalculator instance to test against.
     */
    private BaseUserReliabilityCalculator instance;

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
        config = FailureTestHelper.getConfig("BaseUserReliabilityCalculator");

        instance = new BaseUserReliabilityCalculatorTester();
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
     * Tests the method calculate with null projects. Expect IllegalArgumentException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithNullProjects() throws Exception {
        instance.calculate(null);
    }

    /**
     * Tests the method calculate with empty projects. Expect IllegalArgumentException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithEmptyProjects() throws Exception {
        instance.calculate(new ArrayList<UserProjectParticipationData>());
    }

    /**
     * Tests the method calculate with null project. Expect IllegalArgumentException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithNullProject() throws Exception {
        List<UserProjectParticipationData> list = new ArrayList<UserProjectParticipationData>();
        UserProjectParticipationData project1 = new UserProjectParticipationData();
        UserProjectParticipationData project2 = new UserProjectParticipationData();
        project1.setUserRegistrationDate(new Date());
        project2.setUserRegistrationDate(new Date());
        project1.setResolutionDate(new Date());
        project2.setResolutionDate(new Date());
        list.add(project1);
        list.add(project2);
        list.add(null);
        instance.calculate(list);
    }

    /**
     * Tests the method calculate with null userRegistrationDate. Expect IllegalArgumentException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithNullUserRegistrationDate() throws Exception {
        List<UserProjectParticipationData> list = new ArrayList<UserProjectParticipationData>();
        UserProjectParticipationData project1 = new UserProjectParticipationData();
        UserProjectParticipationData project2 = new UserProjectParticipationData();
        project1.setUserRegistrationDate(new Date());
        project2.setUserRegistrationDate(null);
        project1.setResolutionDate(new Date());
        project2.setResolutionDate(new Date());
        list.add(project1);
        list.add(project2);
        instance.calculate(list);
    }
    
    /**
     * Tests the method calculate with null resolutionDate. Expect IllegalArgumentException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithNullResolutionDate() throws Exception {
        List<UserProjectParticipationData> list = new ArrayList<UserProjectParticipationData>();
        UserProjectParticipationData project1 = new UserProjectParticipationData();
        UserProjectParticipationData project2 = new UserProjectParticipationData();
        project1.setUserRegistrationDate(new Date());
        project2.setUserRegistrationDate(null);
        project1.setResolutionDate(new Date());
        project2.setResolutionDate(new Date());
        list.add(project1);
        list.add(project2);
        instance.calculate(list);
    }

    /**
     * Tests the method calculate with calculation error. Expect UserReliabilityCalculationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = UserReliabilityCalculationException.class)
    public void testCalculateWithCalculationError() throws Exception {
        List<UserProjectParticipationData> list = new ArrayList<UserProjectParticipationData>();
        UserProjectParticipationData project1 = new UserProjectParticipationData();
        UserProjectParticipationData project2 = new UserProjectParticipationData();
        project1.setUserRegistrationDate(new Date());
        project2.setUserRegistrationDate(new Date());
        project1.setResolutionDate(new Date());
        project2.setResolutionDate(new Date());
        list.add(project1);
        list.add(project2);
        instance = new BaseUserReliabilityCalculatorTester(true);
        instance.configure(config);
        instance.calculate(list);
    }

    /**
     * This tester class exposes the protected part in BaseUserReliabilityCalculator class. And implements the abstract
     * method.
     * 
     * @author Yeung
     * @version 1.0
     */
    private class BaseUserReliabilityCalculatorTester extends BaseUserReliabilityCalculator {
        /**
         * Represents whether the method calculateReliabilityAfterProjects is abnormal. if yes,
         * UserReliabilityCalculationException is thrown.
         */
        private boolean abnormal = false;

        /**
         * Exposes the protected ctor of super class.
         */
        public BaseUserReliabilityCalculatorTester() {
        }

        /**
         * Initializes a new instance of BaseUserReliabilityCalculatorTester class with abnormal flag.
         * 
         * @param abnormal
         *            The abnormal flag for calculateReliabilityAfterProjects method.
         */
        public BaseUserReliabilityCalculatorTester(boolean abnormal) {
            this.abnormal = abnormal;
        }

        /**
         * Calculates the reliability after projects.
         * 
         * @param reliabilityData
         *            The reliability data.
         */
        @Override
        protected void calculateReliabilityAfterProjects(List<UserProjectReliabilityData> reliabilityData)
                throws UserReliabilityCalculationException {
            if (abnormal) {
                throw new UserReliabilityCalculationException("error");
            }
        }
    }
}
