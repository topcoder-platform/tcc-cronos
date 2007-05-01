/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.failure;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import junit.framework.TestCase;

import com.cronos.im.logic.MessagePoolDetector;

/**
 * <p>
 * Failure test cases  for MessagePoolDetector class.
 * </p>
 *
 * @author singlewood
 * @version 1.0
 */
public class MessagePoolDetectorFailureTests extends TestCase {
    /**
     * An instance of MyOutputStream for tests.
     */
    private MyOutputStream myOutputStream;

    /**
     * An instance of PrintStream for tests.
     */
    private PrintStream printStream;

    /**
     * Initialize the environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        myOutputStream = new MyOutputStream();
        printStream = new PrintStream(myOutputStream);
        System.setErr(printStream);
        FailureTestHelper.loadConfig();
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.clearConfig();
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception
     * should be thrown, but error message should be logged or printed to console.
     */
    public void testMainFailure1() {
        MessagePoolDetector.main(new String[] {"-namespaceunknown"});
        assertTrue("IMConfigurationException should be detected.", myOutputStream.toString().startsWith(
                "com.cronos.im.logic.IMConfigurationException"));
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception
     * should be thrown, but error message should be logged or printed to console.
     *
     * @throws Exception to JUnit
     */
    public void testMainFailure2() throws Exception {
        MessagePoolDetector.main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid1"});
        assertTrue("IMConfigurationException should be detected.", myOutputStream.toString().startsWith(
        "com.cronos.im.logic.IMConfigurationException"));
    }


    /**
     * Failure test for the main method. If the configuration is invalid, no exception
     * should be thrown, but error message should be logged or printed to console.
     *
     * @throws Exception to JUnit
     */
    public void testMainFailure3() throws Exception {
        MessagePoolDetector.main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid2"});
        assertTrue("IMConfigurationException should be detected.", myOutputStream.toString().startsWith(
        "com.cronos.im.logic.IMConfigurationException"));
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception
     * should be thrown, but error message should be logged or printed to console.
     *
     * @throws Exception to JUnit
     */
    public void testMainFailure4() throws Exception {
        MessagePoolDetector.main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid3"});
        assertTrue("IMConfigurationException should be detected.", myOutputStream.toString().startsWith(
        "com.cronos.im.logic.IMConfigurationException"));
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception
     * should be thrown, but error message should be logged or printed to console.
     *
     * @throws Exception to JUnit
     */
    public void testMainFailure5() throws Exception {
        MessagePoolDetector.main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid4"});
        assertTrue("IMConfigurationException should be detected.", myOutputStream.toString().startsWith(
        "com.cronos.im.logic.IMConfigurationException"));
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception
     * should be thrown, but error message should be logged or printed to console.
     *
     * @throws Exception to JUnit
     */
    public void testMainFailure6() throws Exception {
        MessagePoolDetector.main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid5"});
        assertTrue("IMConfigurationException should be detected.", myOutputStream.toString().startsWith(
        "com.cronos.im.logic.IMConfigurationException"));
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception
     * should be thrown, but error message should be logged or printed to console.
     *
     * @throws Exception to JUnit
     */
    public void testMainFailure7() throws Exception {
        MessagePoolDetector.main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid6"});
        assertTrue("IMConfigurationException should be detected.", myOutputStream.toString().startsWith(
        "com.cronos.im.logic.IMConfigurationException"));
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception
     * should be thrown, but error message should be logged or printed to console.
     *
     * @throws Exception to JUnit
     */
    public void testMainFailure8() throws Exception {
        MessagePoolDetector.main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid7"});
        assertTrue("IMConfigurationException should be detected.", myOutputStream.toString().startsWith(
        "com.cronos.im.logic.IMConfigurationException"));
    }

    /**
     * Failure test for the main method. If the configuration is invalid, no exception
     * should be thrown, but error message should be logged or printed to console.
     *
     * @throws Exception to JUnit
     */
    public void testMainFailure9() throws Exception {
        MessagePoolDetector.main(new String[] {"-namespacecom.cronos.im.logic.MessagePoolDetector.Invalid8"});
        assertTrue("IMConfigurationException should be detected.", myOutputStream.toString().startsWith(
        "com.cronos.im.logic.IMConfigurationException"));
    }

    /**
     * Custom OutputStream used for redirecting Sysout.out to a StringBuffer.
     *
     * @author singlewood
     *
     */
    class MyOutputStream extends OutputStream {

        /**
         * StringBuffer for storing messages.
         */
        private StringBuffer buffer = new StringBuffer();

        /**
         * Writes the specified byte to this output stream. Ignored.
         */
        public void write(int arg0) throws IOException {
        }

        /**
         * Writes the data to this output stream.
         */
        public void write(byte data[]) throws IOException {
            buffer.append(data);
        }

        /**
         * Writes the data to this output stream.
         */
        public void write(byte data[], int off, int len) throws IOException {
            buffer.append(new String(data, off, len));
        }

        /**
         * Return content of StringBuffer.
         */
        public String toString() {
            return buffer.toString();
        }
    }

}
