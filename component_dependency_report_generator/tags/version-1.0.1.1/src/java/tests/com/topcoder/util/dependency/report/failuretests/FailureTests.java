/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.dependency.report.impl.CsvDependencyReportGeneratorFailureTest;
import com.topcoder.util.dependency.report.impl.HtmlDependencyReportGeneratorFailureTest;
import com.topcoder.util.dependency.report.impl.XmlDependencyReportGeneratorFailureTest;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();


        suite.addTestSuite(BaseDependencyReportGeneratorFailureTest.class);
        suite.addTestSuite(CsvDependencyReportGeneratorFailureTest.class);
        suite.addTestSuite(HtmlDependencyReportGeneratorFailureTest.class);
        suite.addTestSuite(XmlDependencyReportGeneratorFailureTest.class);

        return suite;
    }

}
