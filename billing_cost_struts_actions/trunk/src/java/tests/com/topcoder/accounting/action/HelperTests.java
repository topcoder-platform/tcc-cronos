/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import org.junit.Test;

import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for {@link Helper} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperTests {

    /**
     * <p>
     * Failure test of <code>checkConfig</code> method.<br>
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test(expected = BillingCostActionConfigurationException.class)
    public void test_checkConfig_fail() throws Exception {
        Helper.checkConfig(null, "");
    }

    /**
     * <p>
     * Accuracy test of <code>checkConfig</code> method.<br>
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_checkConfig() throws Exception {
        Helper.checkConfig("", null);
    }

    /**
     * <p>
     * Accuracy test of <code>logEnterMethod</code> method.<br>
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_logEnterMethod() throws Exception {
        Log logger = LogManager.getLog(HelperTests.class.toString());
        Helper.logEnterMethod(logger, "test_logEnterMethod");
    }

    /**
     * <p>
     * Accuracy test of <code>logExitMethod</code> method.<br>
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_logExitMethod() throws Exception {
        Log logger = LogManager.getLog(HelperTests.class.toString());
        Helper.logExitMethod(logger, "test_logExitMethod", null);
    }

    /**
     * <p>
     * Failure test of <code>logAndThrow</code> method.<br>
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test(expected = Exception.class)
    public void test_logAndThrow() throws Exception {
        Log logger = LogManager.getLog(HelperTests.class.toString());
        Helper.logAndThrow(logger, "test_logAndThrow", new Exception());
    }
}
