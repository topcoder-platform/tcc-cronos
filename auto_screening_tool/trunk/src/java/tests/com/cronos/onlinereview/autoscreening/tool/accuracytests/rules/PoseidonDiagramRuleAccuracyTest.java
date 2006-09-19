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
import com.cronos.onlinereview.autoscreening.tool.rules.PoseidonDiagramRule;
import com.cronos.onlinereview.autoscreening.tool.rules.PoseidonExtractRule;

import com.topcoder.util.archiving.ArchiveUtility;
import com.topcoder.util.archiving.ZipArchiver;

/**
 * <p>
 * Accuracy test cases for <code>PoseidonDiagramRule</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PoseidonDiagramRuleAccuracyTest extends BaseTestCase {
    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PoseidonDiagramRuleAccuracyTest.class);
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
        File contentsDir = new File(TMP_DIR, "accuracytest.jar.contents");
        contentsDir.mkdir();

        // unzip the submission file into the contents directory.
        ArchiveUtility archiveUtility = new ArchiveUtility(new File(FILES_DIR,
            "accuracytest_submission.jar"), new ZipArchiver());
        archiveUtility.extractContents(contentsDir);

        contentsDir = new File(TMP_DIR, "File_Upload.zuml.contents");
        contentsDir.mkdir();

        // unzip the zuml file into the contents directory.
        archiveUtility = new ArchiveUtility(new File(TMP_DIR,
            "accuracytest.jar.contents/docs/File_Upload.zuml"), new ZipArchiver());
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
    public void testAccuracyCtor() throws Exception {
        ScreeningRule rule = new PoseidonDiagramRule(Integer.MIN_VALUE, 0, Integer.MAX_VALUE);

        assertNotNull("check the instance", rule);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * the number of diagrams meets the requirement.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen1() throws Exception {
        ScreeningRule rule = new PoseidonDiagramRule(1, 1, 1);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        context.put(PoseidonExtractRule.XMI_KEY, new File(TMP_DIR,
            "File_Upload.zuml.contents/File_Upload_2.0.xmi"));

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertEquals("check result status", true, results[0].isSuccessful());
        assertEquals("check message", "Ok. The numbers of use case diagram, class diagram and "
            + "sequence diagram [1, 2, 5] all meet the expected counts [1, 1, 1].", results[0]
            .getMessage());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p> # of use case diagrams does not meet the requirement. The tester
     * should also check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen2() throws Exception {
        ScreeningRule rule = new PoseidonDiagramRule(100, 1, 1);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        context.put(PoseidonExtractRule.XMI_KEY, new File(TMP_DIR,
            "File_Upload.zuml.contents/File_Upload_2.0.xmi"));

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertEquals("check result status", false, results[0].isSuccessful());
        assertEquals("check message",
            "The number of use case diagrams [1] is less than required [100].", results[0]
                .getMessage());
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p> # of use case diagrams and sequence diagrams does not meet the
     * requirement. The tester should also check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen3() throws Exception {
        ScreeningRule rule = new PoseidonDiagramRule(100, 1, 100);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        context.put(PoseidonExtractRule.XMI_KEY, new File(TMP_DIR,
            "File_Upload.zuml.contents/File_Upload_2.0.xmi"));

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 2, results.length);
        assertEquals("check result status", false, results[0].isSuccessful());
        assertEquals("check message",
            "The number of use case diagrams [1] is less than required [100].", results[0]
                .getMessage());

        assertEquals("check result status", false, results[1].isSuccessful());
        assertEquals("check message",
            "The number of sequence diagrams [5] is less than required [100].", results[1]
                .getMessage());
    }

}
