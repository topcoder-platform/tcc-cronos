/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * 
 * @author TopCoder
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * Suite all stress test class.
     * @return stress test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(CsvDependencyReportGeneratorTests.class);
        suite.addTestSuite(XmlDependencyReportGeneratorTests.class);
        suite.addTestSuite(HtmlDependencyReportGeneratorTests.class);

        return suite;
    }
}
