/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.failuretests;

import com.cronos.onlinereview.autoscreening.tool.rules.PoseidonExtractRule;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all failure test cases.
 * </p>
 * @author urtks
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * Aggregates all unit tests in this component.
     * @return test suite aggregating all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ArchiveFileRuleTest.class);
        suite.addTestSuite(CheckStyleRuleTest.class);
        suite.addTestSuite(ComponentSpecificationRuleTest.class);
        suite.addTestSuite(DAOLoggerTest.class);
        suite.addTestSuite(DirectoryStructureRuleTest.class);
        suite.addTestSuite(EarliestTaskChooserTest.class);
        suite.addTestSuite(FileExtensionRuleTest.class);
        suite.addTestSuite(PersonalInfoRuleTest.class);
        suite.addTestSuite(PoseidonDiagramRuleTest.class);
        suite.addTestSuite(PoseidonExtractRuleTest.class);
        suite.addTestSuite(RuleResultTest.class);
        suite.addTestSuite(ScreenerTest.class);
        suite.addTestSuite(ScreeningDataTest.class);
        suite.addTestSuite(ScreeningLogicTest.class);
        suite.addTestSuite(ScreeningResponseTest.class);
        suite.addTestSuite(ScreeningTaskTest.class);
        return suite;
    }
}
