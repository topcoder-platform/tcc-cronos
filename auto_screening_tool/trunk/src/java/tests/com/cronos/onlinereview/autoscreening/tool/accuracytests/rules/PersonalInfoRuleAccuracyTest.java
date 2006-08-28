/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.accuracytests.rules;

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
import com.cronos.onlinereview.autoscreening.tool.accuracytests.TestHelper;
import com.cronos.onlinereview.autoscreening.tool.rules.ArchiveFileRule;
import com.cronos.onlinereview.autoscreening.tool.rules.PersonalInfoRule;
import com.topcoder.util.archiving.ArchiveUtility;
import com.topcoder.util.archiving.ZipArchiver;

/**
 * <p>
 * Accuracy test cases for <code>PersonalInfoRule</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PersonalInfoRuleAccuracyTest extends BaseTestCase {

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PersonalInfoRuleAccuracyTest.class);
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
        File contentsDir = new File(TMP_DIR, "submission1.jar.contents");
        contentsDir.mkdir();

        // unzip the submission file into the contents directory.
        ArchiveUtility archiveUtility = new ArchiveUtility(new File(FILES_DIR,
            "accuracytest_submission.jar"), new ZipArchiver());
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
     * Accuracy test of the constructor <code>PersonalInfoRule()</code>.
     * </p>
     * <p>
     * An instance of PersonalInfoRule should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor() throws Exception {
        ScreeningRule rule = new PersonalInfoRule();

        assertNotNull("check the instance", rule);
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
    public void testAccuracyScreen1() throws Exception {
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
    public void testAccuracyScreen2() throws Exception {
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
        assertEquals("check # of results", 43, results.length);
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
    public void testAccuracyScreen3() throws Exception {
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
        assertEquals("check # of results", 15, results.length);
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
    public void testAccuracyScreen4() throws Exception {
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
        assertEquals("check # of results", 38, results.length);
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
    public void testAccuracyScreen5() throws Exception {
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
    public void testAccuracyScreen6() throws Exception {
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
}
