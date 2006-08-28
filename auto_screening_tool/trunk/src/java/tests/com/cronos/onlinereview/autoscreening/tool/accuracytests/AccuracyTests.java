/*
 * Copyright (C) 2006 TopCoder Inc., All rights reserved
 */
package com.cronos.onlinereview.autoscreening.tool.accuracytests; 

import com.cronos.onlinereview.autoscreening.tool.accuracytests.rules.ArchiveFileRuleAccuracyTest;
import com.cronos.onlinereview.autoscreening.tool.accuracytests.rules.CheckStyleRuleAccuracyTest;
import com.cronos.onlinereview.autoscreening.tool.accuracytests.rules.ComponentSpecificationRuleAccuracyTest;
import com.cronos.onlinereview.autoscreening.tool.accuracytests.rules.DirectoryStructureRuleAccuracyTest;
import com.cronos.onlinereview.autoscreening.tool.accuracytests.rules.FileExtensionRuleAccuracyTest;
import com.cronos.onlinereview.autoscreening.tool.accuracytests.rules.PersonalInfoRuleAccuracyTest;
import com.cronos.onlinereview.autoscreening.tool.accuracytests.rules.PoseidonDiagramRuleAccuracyTest;
import com.cronos.onlinereview.autoscreening.tool.accuracytests.rules.PoseidonExtractRuleAccuracyTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */ 
public class AccuracyTests extends TestCase {
    /**
     * Accuracy Tests for this component.
     *
     * @return test suite for accuracy test.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        
        suite.addTest(ScreenerAccuracyTest.suite());
        suite.addTest(ScreeningToolAccuracyTest.suite());
        suite.addTest(DAOLoggerAccuracyTest.suite());
        suite.addTest(EarliestTaskChooserAccuracyTest.suite());
      
        suite.addTest(CheckStyleRuleAccuracyTest.suite());
        suite.addTest(ComponentSpecificationRuleAccuracyTest.suite());
        suite.addTest(DirectoryStructureRuleAccuracyTest.suite());
        suite.addTest(FileExtensionRuleAccuracyTest.suite());
        suite.addTest(PersonalInfoRuleAccuracyTest.suite());
        suite.addTest(PoseidonDiagramRuleAccuracyTest.suite());
        suite.addTest(PoseidonExtractRuleAccuracyTest.suite());
        suite.addTest(ArchiveFileRuleAccuracyTest.suite());

        return suite;
    }

} 