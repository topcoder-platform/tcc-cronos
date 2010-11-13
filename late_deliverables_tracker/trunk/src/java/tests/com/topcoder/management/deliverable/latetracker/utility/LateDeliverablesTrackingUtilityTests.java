/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker.utility;

import com.topcoder.management.deliverable.latetracker.BaseTestCase;
import com.topcoder.management.deliverable.latetracker.LateDeliverableData;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import java.util.List;

/**
 * Unit tests for <code>{@link LateDeliverablesTrackingUtility}</code> class.
 *
 * @author myxgyy
 * @version 1.0
 */
public class LateDeliverablesTrackingUtilityTests extends BaseTestCase {
    /**
     * The configuration file contains invalid config to generate failure tests.
     */
    private static final String INVALID_CONFIG = "test_files/invalid_config/LateDeliverablesTrackingUtility.properties";

    /**
     * The instance to store the original system <code>SecurityManager</code>.
     */
    private SecurityManager sm;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception throws exception if any.
     */
    protected void setUp() throws Exception {
        super.setUp();
        sm = System.getSecurityManager();
        System.setSecurityManager(new NotExitSecurityManager());
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception throws exception if any.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        System.setSecurityManager(sm);
    }

    /**
     * <p>Accuracy test case for main method.</p>
     * <p>The job should be executed one time, one record should be written to late
     * deliverable table, and the notification email should be sent.</p>
     *
     * @throws Exception to JUnit
     */
    public void test_main_1() throws Exception {
        setupPhases(new long[] {112L}, new long[] {4L}, new long[] {2L}, true);
        runMain(new String[] {"-interval=20"});

        List<LateDeliverableData> datas = getLateDeliverable();
        assertEquals("should have one record", 0, datas.size());
    }

    /**
     * <p>Accuracy test case for main method.</p>
     * <p>Help switch presents, help message should be printed console.</p>
     *
     * @throws Exception to JUnit.
     */
    public void test_main_2() throws Exception {
        String result = callMainOut(new String[] {"-h"});

        System.err.println(result);
        checkHelperMessage(result);
    }

    /**
     * Checks the output message.
     *
     * @param result the output message.
     */
    private static void checkHelperMessage(String result) {
        assertTrue("check the output message", result.contains("<file_name> Optional. Provides the"
            + " name of the configuration file for this command line application. This file is read with"
            + " use of Configuration Persistence component. Default is \"com/topcoder/management/"
            + "deliverable/latetracker/utility/LateDeliverablesTrackingUtility.properties\"."));
        assertTrue("check the output message", result.contains("<namespace> Optional. The"
            + " namespace in the specified configuration file that contains configuration for this"
            + " command line application. Default is \"com.topcoder.management.deliverable.latetracker"
            + ".utility. LateDeliverablesTrackingUtility\"."));
        assertTrue("check the output message", result.contains("<interval_in_sec> Optional. The"
            + " interval in seconds between checks of projects for late deliverables. If not specified,"
            + " the value from the scheduler configuration is used."));
        assertTrue("check the output message", result.contains("-? -h -help      print this help message"));
    }

    /**
     * <p>Accuracy test case for main method.</p>
     * <p>Help switch presents, help message should be printed console.</p>
     *
     * @throws Exception to JUnit.
     */
    public void test_main_3() throws Exception {
        String result = callMainOut(new String[] {"-help"});
        checkHelperMessage(result);
    }

    /**
     * <p>Accuracy test case for main method.</p>
     * <p>Help switch presents, help message should be printed console.</p>
     *
     * @throws Exception to JUnit.
     */
    public void test_main_4() throws Exception {
        String result = callMainOut(new String[] {"-?"});
        checkHelperMessage(result);
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>Switch value not presents, error message should be printed.</p>
     *
     * @throws Exception to JUnit.
     */
    public void test_main_5() throws Exception {
        String result = callMainOut(new String[] {"-c"});
        assertTrue("check the output message", result.contains("Fails to parse the arguments."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>The configuration file type is invalid.</p>
     * @throws Exception to JUnit.
     */
    public void test_main_6() throws Exception {
        String result = callMainOut(new String[] {"-c=xxx.xxx"});
        assertTrue("check the output message", result.contains("Fails to parse the arguments."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>The configuration file does not exist.</p>
     */
    public void test_main_7() throws Exception {
        String result = callMainOut(new String[] {"-c=notexist.properties"});
        assertTrue("check the output message", result.contains("Fails to parse the arguments."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>The namespace is unknown.</p>
     */
    public void test_main_8() throws Exception {
        String result = callMainOut(new String[] {"-c=" + INVALID_CONFIG, "-ns=unknown"});
        assertTrue("check the output message", result.contains("Fails to parse the arguments."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>Fails to parse the configuration file.</p>
     */
    public void test_main_9() throws Exception {
        String result = callMainOut(new String[] {"-c=test_files/invalid_config/ConfigurationParserException.xml"});
        assertTrue("check the output message", result.contains("Fails to parse the arguments."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>The given file has namespace conflict issue.</p>
     */
    public void test_main_10() throws Exception {
        String result = callMainOut(new String[] {"-c=test_files/invalid_config/namespace_conflict.properties"});
        assertTrue("check the output message", result.contains("Fails to parse the arguments."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>Fails to create scheduler.</p>
     */
    public void test_main_11() throws Exception {
        String result = callMainOut(new String[] {"-c=" + INVALID_CONFIG, "-ns=fail1"});
        assertTrue("check the output message", result.contains("Fails to parse the arguments."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>Fails to create scheduler.</p>
     */
    public void test_main_12() throws Exception {
        String result = callMainOut(new String[] {"-c=" + INVALID_CONFIG, "-ns=fail2"});
        assertTrue("check the output message", result.contains("Fails to parse the arguments."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>The configured job can not be created(reference an class can not be found) in
     * this case.</p>
     */
    public void test_main_13() throws Exception {
        String result = callMainOut(new String[] {"-c=" + INVALID_CONFIG, "-ns=fail3"});
        assertTrue("check the output message", result.contains("Fails to parse the arguments."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>The job name reference an unknown job.</p>
     */
    public void test_main_14() throws Exception {
        String result = callMainOut(new String[] {"-c=" + INVALID_CONFIG, "-ns=fail4"});
        assertTrue("check the output message", result.contains("Fails to parse the arguments."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>The arguments is invalid.</p>
     *
     * @throws Exception to JUnit.
     */
    public void test_main_15() throws Exception {
        String result = callMainOut(new String[] {"-interval=xxx", "-interval=123"});
        assertTrue("check the output message", result.contains("Fails to validate the value of interval argument."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>The interval cannot parse to integer.</p>
     *
     * @throws Exception to JUnit.
     */
    public void test_main_16() throws Exception {
        String result = callMainOut(new String[] {"-interval=xxx"});
        assertTrue("check the output message", result.contains("Fails to validate the value of interval argument."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>The interval value is not positive.</p>
     *
     * @throws Exception to JUnit.
     */
    public void test_main_17() throws Exception {
        String result = callMainOut(new String[] {"-interval=-15"});
        assertTrue("check the output message", result.contains("Fails to validate the value of interval argument."));
    }

    /**
     * Calls the {@link LateDeliverablesTrackingUtility#main(String[])} and return the
     * standard output string.
     *
     * @param args the arguments to call main method.
     *
     * @return the output string.
     *
     * @throws Exception to JUnit.
     */
    private static String callMainOut(String[] args) throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(os);
        PrintStream oldOut = System.out;

        try {
            // Redirect the output stream
            System.setOut(newOut);
            // Call main
            LateDeliverablesTrackingUtility.main(args);

            return os.toString();
        } finally {
            if (os != null) {
                os.close();
            }

            if (newOut != null) {
                newOut.close();
            }

            // Restore
            System.setOut(oldOut);
        }
    }

}
