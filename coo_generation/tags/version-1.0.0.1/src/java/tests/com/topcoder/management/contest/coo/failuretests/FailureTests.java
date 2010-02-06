/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.failuretests;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all failure test cases.
 * </p>
 * @author vilain
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * Returns suite for all failure tests.
     * @return suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(new JUnit4TestAdapter(BaseCOOReportSerializerFailureTests.class));
        suite.addTest(new JUnit4TestAdapter(BaseDBConnectorFailureTests.class));
        suite.addTest(new JUnit4TestAdapter(ComponentDependencyFailureTests.class));
        suite.addTest(new JUnit4TestAdapter(ContestDataFailureTests.class));
        suite.addTest(new JUnit4TestAdapter(COOReportFailureTests.class));
        suite.addTest(new JUnit4TestAdapter(DBComponentManagerFailureTests.class));
        suite.addTest(new JUnit4TestAdapter(DBContestDataRetrieverFailureTests.class));
        suite.addTest(new JUnit4TestAdapter(DefaultCOOReportGeneratorFailureTests.class));
        suite.addTest(new JUnit4TestAdapter(DotNetComponentDependencyExtractorFailureTests.class));
        suite.addTest(new JUnit4TestAdapter(JavaComponentDependencyExtractorFailureTests.class));
        suite.addTest(new JUnit4TestAdapter(PDFCOOReportSerializerFailureTests.class));
        suite.addTest(new JUnit4TestAdapter(XMLCOOReportSerializerFailureTests.class));
        return suite;
    }
}