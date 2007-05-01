/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for MessagePoolDetector class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MessagePoolDetectorUnitTests extends TestCase {

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Tests the main method. Test printing help message. No exception should be thrown.
     */
    public void test_print_help1() {
        MessagePoolDetector.main(new String[] {"-h"});
    }

    /**
     * Tests the main method. Test printing help message. No exception should be thrown.
     */
    public void test_print_help2() {
        MessagePoolDetector.main(new String[] {"-help"});
    }

    /**
     * Tests the main method. Default namespace should be used. No exception should be thrown.
     */
    public void test_print_help3() {
        MessagePoolDetector.main(new String[] {});
    }

    /**
     * Accuracy test for the main method. No exception should be thrown.
     */
    public void test_main_accuracy() {
        MessagePoolDetector.main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector"});
        
        // verify the results
        String s = MockChatSessionManager.getMessage();
        assertTrue(s.endsWith("removeUserFromSession(..., 1)requestUserToSessions(1)removeUserFromSession(..., 1)requestUserToSessions(1)"));
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception should be thrown, but
     * error message should be logged or printed to console.
     */
    public void test_main_failure1() {
        MessagePoolDetector.main(new String[] {"-namespacecom.cronos.im.logic.unknown"});
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception should be thrown, but
     * error message should be logged or printed to console.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_main_failure2() throws Exception {
        TestHelper.clearConfig();
        MessagePoolDetector.main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector"});
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception should be thrown, but
     * error message should be logged or printed to console.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_main_failure3() throws Exception {
        MessagePoolDetector
                .main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid1"});
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception should be thrown, but
     * error message should be logged or printed to console.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_main_failure4() throws Exception {
        MessagePoolDetector
                .main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid2"});
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception should be thrown, but
     * error message should be logged or printed to console.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_main_failure5() throws Exception {
        MessagePoolDetector
                .main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid3"});
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception should be thrown, but
     * error message should be logged or printed to console.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_main_failure6() throws Exception {
        MessagePoolDetector
                .main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid4"});
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception should be thrown, but
     * error message should be logged or printed to console.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_main_failure7() throws Exception {
        MessagePoolDetector
                .main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid5"});
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception should be thrown, but
     * error message should be logged or printed to console.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_main_failure8() throws Exception {
        MessagePoolDetector
                .main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid6"});
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception should be thrown, but
     * error message should be logged or printed to console.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_main_failure9() throws Exception {
        MessagePoolDetector
                .main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid7"});
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception should be thrown, but
     * error message should be logged or printed to console.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_main_failure10() throws Exception {
        MessagePoolDetector
                .main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid8"});
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception should be thrown, but
     * error message should be logged or printed to console.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_main_failure11() throws Exception {
        MessagePoolDetector
                .main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid9"});
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception should be thrown, but
     * error message should be logged or printed to console.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_main_failure12() throws Exception {
        MessagePoolDetector
                .main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid10"});
    }

}
