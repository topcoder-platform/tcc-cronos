/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability;

import java.sql.Connection;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.impl.ReliabilityCalculatorImpl;

/**
 * <p>
 * Shows usage for the component.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public class Demo {
    /**
     * <p>
     * Represents the calculator configuration used in tests.
     * </p>
     */
    private ConfigurationObject calculatorConfig;

    /**
     * <p>
     * Represents the connection used in tests.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(Demo.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        connection = TestsHelper.getConnection();
        TestsHelper.clearDB(connection);
        TestsHelper.loadDB(connection);

        calculatorConfig = TestsHelper.getConfig().getChild("reliabilityCalculator1").getChild("config");
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        TestsHelper.clearDB(connection);
        TestsHelper.closeConnection(connection);

        connection = null;
    }

    /**
     * <p>
     * Demo API usage of <code></code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testDemo() throws Exception {
        // Create an instance of ReliabilityCalculatorImpl
        ReliabilityCalculator reliabilityCalculator = new ReliabilityCalculatorImpl();

        // Configure reliability calculator
        ConfigurationObject config = calculatorConfig;
        reliabilityCalculator.configure(config);

        // Calculate reliability for "Design" project category (with ID=1) and
        // update current reliability ratings for all users
        reliabilityCalculator.calculate(1, true);
    }
}
