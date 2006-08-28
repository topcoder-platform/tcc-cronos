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
import com.cronos.onlinereview.autoscreening.tool.ScreeningData;
import com.cronos.onlinereview.autoscreening.tool.ScreeningRule;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.topcoder.util.archiving.ArchiveUtility;
import com.topcoder.util.archiving.ZipArchiver;

/**
 * Unit tests for <code>PersonalInfoRule</code>.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PersonalInfoRuleTest extends BaseTestCase {

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PersonalInfoRuleTest.class);
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

        // create the contents directory for the unzip the submission file
        File contentsDir = new File(TMP_DIR, "submission1.jar.contents");
        contentsDir.mkdir();

        // unzip the submission file into the contents directory.
        ArchiveUtility archiveUtility = new ArchiveUtility(new File(FILES_DIR,
            "1001_submission1.jar"), new ZipArchiver());
        archiveUtility.extractContents(contentsDir);
    }

    /**
     * Clean up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void tearDown() throws Exception {
        File tmpDir = new File(TMP_DIR);
        RuleUtils.deleteDir(tmpDir);
        tmpDir.mkdir();

        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test of the constructor <code>PersonalInfoRule()</code>.
     * </p>
     * <p>
     * An instance of PersonalInfoRule should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorA1() throws Exception {
        ScreeningRule rule = new PersonalInfoRule();

        assertNotNull("check the instance", rule);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * property [unzipped_submission_directory] does not exist. The tester
     * should also check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen1() throws Exception {
        ScreeningRule rule = new PersonalInfoRule();

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertNotNull("check error", results[0].getError());
        assertEquals("check error message",
            "The property [unzipped_submission_directory] does not exist in the screening context"
                + " or its value is null.", results[0].getError().getMessage());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * property [unzipped_submission_directory] is not of type UploadedFile.The
     * tester should also check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen2() throws Exception {
        ScreeningRule rule = new PersonalInfoRule();

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();
        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, TMP_DIR + "/do_not_exist.jar");

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertNotNull("check error result", results[0].getError());
        assertEquals("check error message",
            "The property [unzipped_submission_directory] should be of type java.io.File.",
            results[0].getError().getMessage());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * screening data is not specified. The tester should also check the console
     * output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen3() throws Exception {
        ScreeningRule rule = new PersonalInfoRule();

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, new File(TMP_DIR,
            "submission1.jar.contents"));

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertNotNull("check error", results[0].getError());
        assertEquals("check message",
            "The screening data of screeningTask has not been specified.", results[0].getError()
                .getMessage());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * no personal info. The tester should also check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen4() throws Exception {
        ScreeningRule rule = new PersonalInfoRule();

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);
        ScreeningData screeningData = new ScreeningData();
        task.setScreeningData(screeningData);
        screeningData.setSubmitterHandle("dev1");
        screeningData.setSubmitterFirstName("first1");
        screeningData.setSubmitterLastName("second1");
        screeningData.setSubmitterEmail("email1");
        screeningData.setSubmitterAlternativeEmails(new String[] {"alterEmail1", "alterEmail2"});

        Map context = new HashMap();

        File contentsDir = new File(TMP_DIR, "submission1.jar.contents");
        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, contentsDir);

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertEquals("check result status", true, results[0].isSuccessful());
        assertEquals("check message", "OK. There are no personal info in the submission.",
            results[0].getMessage());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * the submission contains submitter's handle. failure result should be
     * returned.The tester should also check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen5() throws Exception {
        ScreeningRule rule = new PersonalInfoRule();

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);
        ScreeningData screeningData = new ScreeningData();
        task.setScreeningData(screeningData);
        screeningData.setSubmitterHandle("topcoder");
        screeningData.setSubmitterFirstName("first1");
        screeningData.setSubmitterLastName("second1");
        screeningData.setSubmitterEmail("email1");
        screeningData.setSubmitterAlternativeEmails(new String[] {"alterEmail1", "alterEmail2"});

        Map context = new HashMap();

        File contentsDir = new File(TMP_DIR, "submission1.jar.contents");
        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, contentsDir);

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 118, results.length);
        for (int i = 0; i < results.length; ++i) {
            assertEquals("check result status", false, results[0].isSuccessful());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * the submission contains submitter's first name. failure result should be
     * returned.The tester should also check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen6() throws Exception {
        ScreeningRule rule = new PersonalInfoRule();

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);
        ScreeningData screeningData = new ScreeningData();
        task.setScreeningData(screeningData);
        screeningData.setSubmitterHandle("dev1");
        screeningData.setSubmitterFirstName("servlet");
        screeningData.setSubmitterLastName("second1");
        screeningData.setSubmitterEmail("email1");
        screeningData.setSubmitterAlternativeEmails(new String[] {"alterEmail1", "alterEmail2"});

        Map context = new HashMap();

        File contentsDir = new File(TMP_DIR, "submission1.jar.contents");
        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, contentsDir);

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 84, results.length);
        for (int i = 0; i < results.length; ++i) {
            assertEquals("check result status", false, results[0].isSuccessful());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * the submission contains submitter's last name. failure result should be
     * returned.The tester should also check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen7() throws Exception {
        ScreeningRule rule = new PersonalInfoRule();

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);
        ScreeningData screeningData = new ScreeningData();
        task.setScreeningData(screeningData);
        screeningData.setSubmitterHandle("dev1");
        screeningData.setSubmitterFirstName("first1");
        screeningData.setSubmitterLastName("config");
        screeningData.setSubmitterEmail("email1");
        screeningData.setSubmitterAlternativeEmails(new String[] {"alterEmail1", "alterEmail2"});

        Map context = new HashMap();

        File contentsDir = new File(TMP_DIR, "submission1.jar.contents");
        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, contentsDir);

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 67, results.length);
        for (int i = 0; i < results.length; ++i) {
            assertEquals("check result status", false, results[0].isSuccessful());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * the submission contains submitter's email. failure result should be
     * returned.The tester should also check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen8() throws Exception {
        ScreeningRule rule = new PersonalInfoRule();

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);
        ScreeningData screeningData = new ScreeningData();
        task.setScreeningData(screeningData);
        screeningData.setSubmitterHandle("dev1");
        screeningData.setSubmitterFirstName("first1");
        screeningData.setSubmitterLastName("last1");
        screeningData.setSubmitterEmail("@topcoder.com");
        screeningData.setSubmitterAlternativeEmails(new String[] {"alterEmail1", "alterEmail2"});

        Map context = new HashMap();

        File contentsDir = new File(TMP_DIR, "submission1.jar.contents");
        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, contentsDir);

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 4, results.length);
        for (int i = 0; i < results.length; ++i) {
            assertEquals("check result status", false, results[0].isSuccessful());
        }
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * the submission contains submitter's alternative emails. failure result
     * should be returned.The tester should also check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen9() throws Exception {
        ScreeningRule rule = new PersonalInfoRule();

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);
        ScreeningData screeningData = new ScreeningData();
        task.setScreeningData(screeningData);
        screeningData.setSubmitterHandle("dev1");
        screeningData.setSubmitterFirstName("first1");
        screeningData.setSubmitterLastName("last1");
        screeningData.setSubmitterEmail("mail1");
        screeningData.setSubmitterAlternativeEmails(new String[] {"microsoft", "sun"});

        Map context = new HashMap();

        File contentsDir = new File(TMP_DIR, "submission1.jar.contents");
        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, contentsDir);

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 5, results.length);
        for (int i = 0; i < results.length; ++i) {
            assertEquals("check result status", false, results[0].isSuccessful());
        }
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
        ScreeningRule rule = new PersonalInfoRule();

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
        ScreeningRule rule = new PersonalInfoRule();

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
     * screeningTask and context can be null.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCleanup1() throws Exception {
        ScreeningRule rule = new PersonalInfoRule();

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();
        rule.cleanup(task, context);
    }
}