/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.accuracytests.rules;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.onlinereview.autoscreening.tool.accuracytests.BaseTestCase;
import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.ScreeningRule;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.accuracytests.TestHelper;
import com.cronos.onlinereview.autoscreening.tool.rules.ArchiveFileRule;
import com.cronos.onlinereview.autoscreening.tool.rules.CheckStyleRule;
import com.topcoder.util.archiving.ArchiveUtility;
import com.topcoder.util.archiving.ZipArchiver;


/**
 * <p>
 * Accuracy test cases for <code>CheckStyleRule</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CheckStyleRuleAccuracyTest extends BaseTestCase {

    /**
     * Represents the command to check style.
     */
    private static final String CHECK_STYLE_COMMAND = "test_files\\checkstyle.bat";

    /**
     * Represents the start path to check the style of main source.
     */
    private static final String MAIN_SOURCE_DIRECTORY = "src/java/main";

    /**
     * Represents the start path to check the style of tests source.
     */
    private static final String TESTS_SOURCE_DIRECTORY = "src/java/tests";

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(CheckStyleRuleAccuracyTest.class);
    }

    /**
     * Sets up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        File tmpDir = new File(TMP_DIR);
        TestHelper.deleteDir(tmpDir);
        tmpDir.mkdir();

        // create the contents directory for the unzip the submission file
        File contentsDir = new File(TMP_DIR, "accuracy.jar.contents");
        contentsDir.mkdir();

        // unzip the submission file into the contents directory.
        ArchiveUtility archiveUtility = new ArchiveUtility(new File(FILES_DIR,
            "accuracytest_submission.JAR"), new ZipArchiver());
        archiveUtility.extractContents(contentsDir);
    }

    /**
     * Clean up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void tearDown() throws Exception {
        File tmpDir = new File(TMP_DIR);
        TestHelper.deleteDir(tmpDir);
        tmpDir.mkdir();

        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>CheckStyleRule(String checkStyleCommand, String startDirectoryName)</code>.
     * </p>
     * <p>
     * An instance of CheckStyleRule should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor() throws Exception {
        ScreeningRule rule = new CheckStyleRule(CHECK_STYLE_COMMAND, MAIN_SOURCE_DIRECTORY);

        assertNotNull("check the instance", rule);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * the main source is checked.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen1() throws Exception {
        ScreeningRule rule = new CheckStyleRule(CHECK_STYLE_COMMAND, MAIN_SOURCE_DIRECTORY);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, new File(TMP_DIR,
            "accuracy.jar.contents"));

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        for (int i = 0; i < results.length; ++i) {
            assertEquals("check result status", false, results[i].isSuccessful());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * the test source is checked.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen2() throws Exception {
        ScreeningRule rule = new CheckStyleRule(CHECK_STYLE_COMMAND, TESTS_SOURCE_DIRECTORY);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, new File(TMP_DIR,
            "accuracy.jar.contents"));

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 2, results.length);
        for (int i = 0; i < results.length; ++i) {
            assertEquals("check result status", false, results[i].isSuccessful());
        }
    }
}