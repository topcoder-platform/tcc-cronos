/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.accuracytests.rules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.onlinereview.autoscreening.tool.BaseTestCase;
import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.ScreeningRule;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.accuracytests.TestHelper;
import com.cronos.onlinereview.autoscreening.tool.rules.ArchiveFileRule;
import com.cronos.onlinereview.autoscreening.tool.rules.PoseidonDiagramRule;
import com.cronos.onlinereview.autoscreening.tool.rules.PoseidonExtractRule;

import com.topcoder.util.archiving.ArchiveUtility;
import com.topcoder.util.archiving.ZipArchiver;

/**
 * <p>
 * Accuracy test cases for <code>PoseidonExtractRule</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PoseidonExtractRuleAccuracyTest extends BaseTestCase {

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PoseidonExtractRuleAccuracyTest.class);
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
     * Accuracy test of the constructor
     * <code>PoseidonDiagramRule(int useCaseDiagramCount, int classDiagramCount,
     * int sequenceDiagramCount)</code>.
     * </p>
     * <p>
     * An instance of PoseidonDiagramRule should be created successfully. any
     * value is allowed.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorA() throws Exception {
        ScreeningRule rule = new PoseidonDiagramRule(Integer.MIN_VALUE, 0, Integer.MAX_VALUE);

        assertNotNull("check the instance", rule);
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>PoseidonExtractRule(String temporaryDir)</code>.
     * </p>
     * <p>
     * An instance of ArchiveFileRule should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorB1() throws Exception {
        ScreeningRule rule = new PoseidonExtractRule(TMP_DIR);

        assertNotNull("check the instance", rule);
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>PoseidonExtractRule(String temporaryDir)</code>.
     * </p>
     * <p>
     * An instance of PoseidonExtractRule should be created successfully.
     * temporaryDir can be null.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtorB2() throws Exception {
        ScreeningRule rule = new PoseidonExtractRule(null);

        assertNotNull("check the instance", rule);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * the zuml was unzipped successfully. The tester should also check the
     * console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen1() throws Exception {
        ScreeningRule rule = new PoseidonExtractRule(TMP_DIR);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, new File(TMP_DIR,
            "submission1.jar.contents/conf"));

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertEquals("check result status", false, results[0].isSuccessful());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * no xmi or proj3 file. The tester should also check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen2() throws Exception {
        ScreeningRule rule = new PoseidonExtractRule(TMP_DIR);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        File contentsDir = new File(TMP_DIR, "submission1.jar.contents");
        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, contentsDir);

        FileChannel in = new FileInputStream(new File(FILES_DIR, "accuracytest_submission.jar"))
            .getChannel();
        FileChannel out = new FileOutputStream(new File(contentsDir, "conf/Job_Scheduler.zuml"))
            .getChannel();
        in.transferTo(0, in.size(), out);

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertEquals("check result status", false, results[0].isSuccessful());
        assertTrue("check result message", results[0].getMessage().startsWith(
            "No or duplicate 'zuml' or 'zargo' files are found under directory ["));
    }
}
