/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.autoscreening.tool.rules.ComponentSpecificationRule;
import com.topcoder.util.config.ConfigManager;

/**
 * The unit test cases for class ScreeningLogic.
 * @author urtks
 * @version 1.0
 */
public class ScreeningLogicTest extends TestCase {

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ScreeningLogicTest.class);
    }

    /**
     * Sets up the environments.
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        ConfigManager.getInstance().add(BaseTestCase.LOGGING_WRAPPER_CONFIG_FILE);
    }

    /**
     * Clears all the test environments.
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        BaseTestCase.clearNamespaces();
    }

    /**
     * <p>
     * Accuracy Test for the constructor
     * <code>ScreeningLogic(ScreeningRule screeningRule, ResponseLevel successSeverity,
     * ResponseLevel failureSeverity, ResponseLevel errorSeverity)</code>.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorA1() throws Exception {
        ScreeningRule rule = new ComponentSpecificationRule();
        ScreeningLogic screeningLogic = new ScreeningLogic(rule, ResponseLevel.PASS,
            ResponseLevel.WARN, ResponseLevel.FAIL);

        assertNotNull("check instance", screeningLogic);
        assertEquals("check rule", rule, screeningLogic.getScreeningRule());
        assertEquals("check success level", ResponseLevel.PASS, screeningLogic.getSuccessLevel());
        assertEquals("check warning level", ResponseLevel.WARN, screeningLogic.getFailureLevel());
        assertEquals("check error level", ResponseLevel.FAIL, screeningLogic.getErrorLevel());
    }

    /**
     * <p>
     * Failure Test for the constructor
     * <code>ScreeningLogic(ScreeningRule screeningRule, ResponseLevel successSeverity,
     * ResponseLevel failureSeverity, ResponseLevel errorSeverity)</code>.
     * </p>
     * <p>
     * screeningRule is null. IllegalException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorA1() throws Exception {
        try {
            new ScreeningLogic(null, ResponseLevel.PASS, ResponseLevel.WARN, ResponseLevel.FAIL);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "screeningRule should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the constructor
     * <code>ScreeningLogic(ScreeningRule screeningRule, ResponseLevel successSeverity,
     * ResponseLevel failureSeverity, ResponseLevel errorSeverity)</code>.
     * </p>
     * <p>
     * successSeverity is null. IllegalException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorA2() throws Exception {
        try {
            new ScreeningLogic(new ComponentSpecificationRule(), null, ResponseLevel.WARN,
                ResponseLevel.FAIL);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "successSeverity should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the constructor
     * <code>ScreeningLogic(ScreeningRule screeningRule, ResponseLevel successSeverity,
     * ResponseLevel failureSeverity, ResponseLevel errorSeverity)</code>.
     * </p>
     * <p>
     * failureSeverity is null. IllegalException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorA3() throws Exception {
        try {
            new ScreeningLogic(new ComponentSpecificationRule(), ResponseLevel.WARN, null,
                ResponseLevel.FAIL);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "failureSeverity should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the constructor
     * <code>ScreeningLogic(ScreeningRule screeningRule, ResponseLevel successSeverity,
     * ResponseLevel failureSeverity, ResponseLevel errorSeverity)</code>.
     * </p>
     * <p>
     * errorSeverity is null. IllegalException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorA4() throws Exception {
        try {
            new ScreeningLogic(new ComponentSpecificationRule(), ResponseLevel.WARN,
                ResponseLevel.FAIL, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "errorSeverity should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the constructor
     * <code> ScreeningLogic(ScreeningRule screeningRule, ResponseLevel successSeverity,
     * ResponseLevel failureSeverity, ResponseLevel errorSeverity, long successCode,
     * long failureCode, long errorCode)</code>.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorB1() throws Exception {
        ScreeningRule rule = new ComponentSpecificationRule();
        ScreeningLogic screeningLogic = new ScreeningLogic(rule, ResponseLevel.PASS,
            ResponseLevel.WARN, ResponseLevel.FAIL, 1, 2, 3);

        assertNotNull("check instance", screeningLogic);
        assertEquals("check rule", rule, screeningLogic.getScreeningRule());
        assertEquals("check success level", ResponseLevel.PASS, screeningLogic.getSuccessLevel());
        assertEquals("check warning level", ResponseLevel.WARN, screeningLogic.getFailureLevel());
        assertEquals("check error level", ResponseLevel.FAIL, screeningLogic.getErrorLevel());
        assertEquals("check success code", 1, screeningLogic.getSuccessResponseCode());
        assertEquals("check warning code", 2, screeningLogic.getFailureResponseCode());
        assertEquals("check error code", 3, screeningLogic.getErrorResponseCode());
    }

    /**
     * <p>
     * Failure Test for the constructor
     * <code> ScreeningLogic(ScreeningRule screeningRule, ResponseLevel successSeverity,
     * ResponseLevel failureSeverity, ResponseLevel errorSeverity, long successCode,
     * long failureCode, long errorCode)</code>.
     * </p>
     * <p>
     * screeningRule is null. IllegalException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB1() throws Exception {
        try {
            new ScreeningLogic(null, ResponseLevel.PASS, ResponseLevel.WARN, ResponseLevel.FAIL, 1,
                2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "screeningRule should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the constructor
     * <code> ScreeningLogic(ScreeningRule screeningRule, ResponseLevel successSeverity,
     * ResponseLevel failureSeverity, ResponseLevel errorSeverity, long successCode,
     * long failureCode, long errorCode)</code>.
     * </p>
     * <p>
     * successSeverity is null. IllegalException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB2() throws Exception {
        try {
            new ScreeningLogic(new ComponentSpecificationRule(), null, ResponseLevel.WARN,
                ResponseLevel.FAIL, 1, 2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "successSeverity should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the constructor
     * <code> ScreeningLogic(ScreeningRule screeningRule, ResponseLevel successSeverity,
     * ResponseLevel failureSeverity, ResponseLevel errorSeverity, long successCode,
     * long failureCode, long errorCode)</code>.
     * </p>
     * <p>
     * failureSeverity is null. IllegalException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB3() throws Exception {
        try {
            new ScreeningLogic(new ComponentSpecificationRule(), ResponseLevel.WARN, null,
                ResponseLevel.FAIL, 1, 2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "failureSeverity should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the constructor
     * <code> ScreeningLogic(ScreeningRule screeningRule, ResponseLevel successSeverity,
     * ResponseLevel failureSeverity, ResponseLevel errorSeverity, long successCode,
     * long failureCode, long errorCode)</code>.
     * </p>
     * <p>
     * errorSeverity is null. IllegalException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB4() throws Exception {
        try {
            new ScreeningLogic(new ComponentSpecificationRule(), ResponseLevel.WARN,
                ResponseLevel.FAIL, null, 1, 2, 3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "errorSeverity should not be null.", e.getMessage());
        }
    }
}