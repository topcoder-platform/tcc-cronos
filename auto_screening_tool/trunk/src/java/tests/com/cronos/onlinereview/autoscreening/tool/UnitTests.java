/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.autoscreening.tool.rules.ArchiveFileRuleTest;
import com.cronos.onlinereview.autoscreening.tool.rules.CheckStyleRuleTest;
import com.cronos.onlinereview.autoscreening.tool.rules.ComponentSpecificationRuleTest;
import com.cronos.onlinereview.autoscreening.tool.rules.DirectoryStructureRuleTest;
import com.cronos.onlinereview.autoscreening.tool.rules.FileExtensionRuleTest;
import com.cronos.onlinereview.autoscreening.tool.rules.PersonalInfoRuleTest;
import com.cronos.onlinereview.autoscreening.tool.rules.PoseidonDiagramRuleTest;
import com.cronos.onlinereview.autoscreening.tool.rules.PoseidonExtractRuleTest;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * Aggregates all unit tests in this component.
     * @return test suite aggregating all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(ConfigurationExceptionTest.suite());
        suite.addTest(DAOExceptionTest.suite());
        suite.addTest(DAOLoggerTest.suite());
        suite.addTest(EarliestTaskChooserTest.suite());
        suite.addTest(ResponseLevelTest.suite());
        suite.addTest(ResultRuleTest.suite());
        suite.addTest(ScreenerTest.suite());
        suite.addTest(ScreeningDataTest.suite());
        suite.addTest(ScreeningExceptionTest.suite());
        suite.addTest(ScreeningLogicTest.suite());
        suite.addTest(ScreeningStatusTest.suite());
        suite.addTest(ScreeningTaskTest.suite());
        suite.addTest(UpdateFailedExceptionTest.suite());

        suite.addTest(ArchiveFileRuleTest.suite());
        suite.addTest(CheckStyleRuleTest.suite());
        suite.addTest(ComponentSpecificationRuleTest.suite());
        suite.addTest(DirectoryStructureRuleTest.suite());
        suite.addTest(FileExtensionRuleTest.suite());
        suite.addTest(PersonalInfoRuleTest.suite());
        suite.addTest(PoseidonDiagramRuleTest.suite());
        suite.addTest(PoseidonExtractRuleTest.suite());

        suite.addTest(ScreeningJobTest.suite());
        suite.addTest(ScreeningToolTest.suite());
        suite.addTest(DemoTest.suite());
        return suite;
    }
}
