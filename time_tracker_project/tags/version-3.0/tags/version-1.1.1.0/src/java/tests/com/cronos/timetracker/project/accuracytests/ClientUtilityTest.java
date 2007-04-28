/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.accuracytests;

import com.cronos.timetracker.project.ClientUtility;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * <p>
 * This class contains the accuracy unit tests for ClientUtility.java.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class ClientUtilityTest {
    /**
     * <p>
     * Represents the ClientUtility instance for testing.
     * </p>
     */
    private ClientUtility utility = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception throw to JUnit
     */
    protected void setUp() throws Exception {
        Helper.clearConfig();
        Helper.addConfig();
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(ClientUtilityTest.class);
    }
}
