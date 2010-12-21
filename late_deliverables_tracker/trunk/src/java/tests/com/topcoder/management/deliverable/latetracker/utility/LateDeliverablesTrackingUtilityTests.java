/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import com.topcoder.management.deliverable.latetracker.BaseTestCase;
import com.topcoder.management.deliverable.latetracker.LateDeliverableData;

/**
 * Unit tests for <code>{@link LateDeliverablesTrackingUtility}</code> class.
 *
 * @author myxgyy, sparemax
 * @version 1.1
 */
public class LateDeliverablesTrackingUtilityTests extends BaseTestCase {
    /**
     * The instance to store the original system <code>SecurityManager</code>.
     */
    private SecurityManager sm;

    /**
     * The guard file name.
     */
    private String guardFileName = "test_files/guard.txt";

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception throws exception if any.
     */
    protected void setUp() throws Exception {
        super.setUp();
        sm = System.getSecurityManager();
        System.setSecurityManager(new NotExitSecurityManager());

        new File(guardFileName).delete();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception throws exception if any.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        System.setSecurityManager(sm);

        new File(guardFileName).delete();
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

        runMain(new String[] {"-interval=20", "-guardFile=" + guardFileName, "-background=true"});

        new File(guardFileName).createNewFile();


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
     * <p>
     * Failure test case for main method.
     * </p>
     * <p>
     * The configuration file does not exist.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_main_7() throws Exception {
        String result = callMainOut(new String[] {"-c=notexist.properties", "-c=notexist.properties"});
        assertTrue("check the output message", result.contains("Fails to parse the arguments."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>The arguments is invalid.</p>
     *
     * @throws Exception to JUnit.
     */
    public void test_main_8() throws Exception {
        String result = callMainOut(new String[] {"-interval=xxx", "-interval=123"});
        assertTrue("check the output message", result.contains("Fails to validate the value of interval argument."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>The interval cannot parse to integer.</p>
     *
     * @throws Exception to JUnit.
     */
    public void test_main_9() throws Exception {
        String result = callMainOut(new String[] {"-interval=xxx"});
        assertTrue("check the output message", result.contains("Fails to validate the value of interval argument."));
    }

    /**
     * <p>Failure test case for main method.</p>
     * <p>The interval value is not positive.</p>
     *
     * @throws Exception to JUnit.
     */
    public void test_main_10() throws Exception {
        String result = callMainOut(new String[] {"-interval=-15"});
        assertTrue("check the output message", result.contains("Fails to validate the value of interval argument."));
    }

    /**
     * <p>Failure test case for main method.</p>
     *
     * @throws Exception to JUnit.
     */
    public void test_main_11() throws Exception {
        new File(guardFileName).createNewFile();

        callMainOut(new String[] {"-interval=20", "-guardFile=" + guardFileName, "-background=true"});
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

        } catch (SecurityException e) {
            // Ignore
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
        return os.toString();
    }

}
