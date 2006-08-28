/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.rules;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.onlinereview.autoscreening.tool.BaseTestCase;
import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.Screener;
import com.cronos.onlinereview.autoscreening.tool.ScreeningRule;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.topcoder.servlet.request.FileUpload;
import com.topcoder.servlet.request.LocalFileUpload;
import com.topcoder.servlet.request.UploadedFile;

/**
 * Unit tests for <code>ArchiveFileRule</code>.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ArchiveFileRuleTest extends BaseTestCase {

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ArchiveFileRuleTest.class);
    }

    /**
     * Sets up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        File tmpDir = new File(TMP_DIR);
        RuleUtils.deleteDir(tmpDir);
        tmpDir.mkdir();
    }

    /**
     * Clean up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>ArchiveFileRule(String archiveExtension)</code>.
     * </p>
     * <p>
     * An instance of ArchiveFileRule should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorA1() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar");

        assertNotNull("check the instance", rule);
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>ArchiveFileRule(String archiveExtension)</code>.
     * </p>
     * <p>
     * archiveExtension is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorA1() throws Exception {

        try {
            new ArchiveFileRule(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "archiveExtension should not be null.", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>ArchiveFileRule(String archiveExtension)</code>.
     * </p>
     * <p>
     * archiveExtension is empty. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorA2() throws Exception {

        try {
            new ArchiveFileRule("   ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "archiveExtension should not be empty (trimmed).",
                e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>ArchiveFileRule(String archiveExtension, String temporaryDir)</code>.
     * </p>
     * <p>
     * An instance of ArchiveFileRule should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorB1() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", TMP_DIR);

        assertNotNull("check the instance", rule);
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>ArchiveFileRule(String archiveExtension, String temporaryDir)</code>.
     * </p>
     * <p>
     * An instance of ArchiveFileRule should be created successfully.
     * temporaryDir can be null.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorB2() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", null);

        assertNotNull("check the instance", rule);
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>ArchiveFileRule(String archiveExtension, String temporaryDir)</code>.
     * </p>
     * <p>
     * archiveExtension is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB1() throws Exception {

        try {
            new ArchiveFileRule(null, TMP_DIR);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "archiveExtension should not be null.", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>ArchiveFileRule(String archiveExtension, String temporaryDir)</code>.
     * </p>
     * <p>
     * archiveExtension is empty. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB2() throws Exception {

        try {
            new ArchiveFileRule("    ", TMP_DIR);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "archiveExtension should not be empty (trimmed).",
                e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>ArchiveFileRule(String archiveExtension, String temporaryDir)</code>.
     * </p>
     * <p>
     * temporaryDir is empty. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtorB3() throws Exception {

        try {
            new ArchiveFileRule(".jar", "    ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "temporaryDir should not be empty (trimmed).", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * property [screening_submission] does not exist. The tester should also
     * check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen1() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", TMP_DIR);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertNotNull("check error", results[0].getError());
        assertEquals("check error message",
            "The property [screening_submission] does not exist in the screening context"
                + " or its value is null.", results[0].getError().getMessage());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * property [screening_submission] is not of type UploadedFile.The tester
     * should also check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen2() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", TMP_DIR);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();
        context.put(Screener.SUBMISSION_KEY, TMP_DIR + "/do_not_exist.jar");

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertNotNull("check error result", results[0].getError());
        assertEquals("check error message", "The property [screening_submission] should be of type "
            + "com.topcoder.servlet.request.UploadedFile.", results[0].getError().getMessage());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * the submission does not end with .zip. The tester should also check the
     * console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen3() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", TMP_DIR);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        FileUpload fileUpload = new LocalFileUpload(LocalFileUpload.class.getName());
        UploadedFile uploadedFile = fileUpload.getUploadedFile("1002_submission2.zip");

        context.put(Screener.SUBMISSION_KEY, uploadedFile);

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertEquals("check result status", false, results[0].isSuccessful());
        assertEquals("check result message",
            "The submission file [submission2.zip] does not have .jar as its extension.",
            results[0].getMessage());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * the submission is not a zip archive. The tester should also check the
     * console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen4() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", TMP_DIR);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        FileUpload fileUpload = new LocalFileUpload(LocalFileUpload.class.getName());
        UploadedFile uploadedFile = fileUpload.getUploadedFile("1003_rarfile1.jar");

        context.put(Screener.SUBMISSION_KEY, uploadedFile);

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertEquals("check result status", false, results[0].isSuccessful());
        assertEquals("check result message",
            "The submission file [rarfile1.jar] is not a zip archive.", results[0].getMessage());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * the temporary submission file should be deleted.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen5() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", TMP_DIR);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        FileUpload fileUpload = new LocalFileUpload(LocalFileUpload.class.getName());
        UploadedFile uploadedFile = fileUpload.getUploadedFile("1001_submission1.jar");

        context.put(Screener.SUBMISSION_KEY, uploadedFile);

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertEquals("check status", true, results[0].isSuccessful());
        assertTrue("check message", results[0].getMessage().startsWith(
            "OK. The submission archive was successfully unzipped to ["));
    }

    /**
     * <p>
     * Failure test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * screeningTask is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureScreen1() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", TMP_DIR);

        Map context = new HashMap();

        try {
            rule.screen(null, context);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "screeningTask should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * context is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureScreen2() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", TMP_DIR);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        try {
            rule.screen(task, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "context should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>void cleanup(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * property [unzipped_submission_directory] does not exist. The tester
     * should check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCleanup1() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", TMP_DIR);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();
        rule.cleanup(task, context);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>void cleanup(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * property [unzipped_submission_directory] is not java.io.File. The tester
     * should check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCleanup2() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", TMP_DIR);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();
        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY,
            "d:/do_not_exist/do_not_exist.do_not_exist");

        rule.cleanup(task, context);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>void cleanup(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * the specified directory should be deleted. The tester should check the
     * console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCleanup3() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", TMP_DIR);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        File dir = new File(TMP_DIR + "/submission1.jar.contents");
        dir.mkdir();

        Map context = new HashMap();
        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, dir);

        rule.cleanup(task, context);

        assertFalse("check directory deleted", dir.exists());
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void cleanup(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * screeningTask is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCleanup1() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", TMP_DIR);

        Map context = new HashMap();

        try {
            rule.cleanup(null, context);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "screeningTask should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>void cleanup(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * context is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCleanup2() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", TMP_DIR);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        try {
            rule.cleanup(task, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("check error message", "context should not be null.", e.getMessage());
        }
    }
}