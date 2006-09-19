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
        File contentsDir = new File(TMP_DIR, "submission.jar.contents");
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
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen() throws Exception {
        ScreeningRule rule = new PoseidonExtractRule(TMP_DIR);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        context.put(ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, new File(TMP_DIR,
            "submission.jar.contents"));

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertEquals("check result status", true, results[0].isSuccessful());
        assertEquals("check result message",
            "OK. The 'zuml' or 'zargo' file is unzipped successfully.", results[0].getMessage());
    }

}
