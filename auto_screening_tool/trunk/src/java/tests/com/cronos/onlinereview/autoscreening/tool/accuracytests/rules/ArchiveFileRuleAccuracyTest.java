/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.accuracytests.rules;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.cronos.onlinereview.autoscreening.tool.BaseTestCase;
import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.Screener;
import com.cronos.onlinereview.autoscreening.tool.ScreeningRule;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.accuracytests.TestHelper;
import com.cronos.onlinereview.autoscreening.tool.rules.ArchiveFileRule;
import com.topcoder.servlet.request.FileUpload;
import com.topcoder.servlet.request.LocalFileUpload;
import com.topcoder.servlet.request.UploadedFile;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>
 * Accuracy test cases for <code>ArchiveFileRule</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ArchiveFileRuleAccuracyTest extends BaseTestCase {

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ArchiveFileRuleAccuracyTest.class);
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
    public void testAccuracyCtorA() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".zip");

        assertNotNull("check the instance", rule);
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
     * Accuracy test of the method
     * <code>RuleResult[] screen(ScreeningTask screeningTask, Map context)</code>.
     * </p>
     * <p>
     * This rule will be successful. see out console.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen() throws Exception {
        ScreeningRule rule = new ArchiveFileRule(".jar", TMP_DIR);

        ScreeningTask task = new ScreeningTask();
        task.setId(1);
        task.setScreenerId(2);

        Map context = new HashMap();

        FileUpload fileUpload = new LocalFileUpload(LocalFileUpload.class.getName());
        UploadedFile uploadedFile = fileUpload.getUploadedFile("accuracytest_submission.jar");

        context.put(Screener.SUBMISSION_KEY, uploadedFile);

        RuleResult[] results = rule.screen(task, context);
        assertEquals("check # of results", 1, results.length);
        assertEquals("check status", true, results[0].isSuccessful());
        assertTrue("check message", results[0].getMessage().startsWith(
            "OK. The submission archive was successfully unzipped"));
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
    public void testAccuracyCleanup() throws Exception {
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
}
