/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
 package com.topcoder.util.dependency.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.TestResult;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(BaseDependenciesEntryExtractorFailureTest.suite());
        suite.addTest(BinaryFileDependenciesEntryPersistenceFailureTest.suite());
        suite.addTest(ComponentDependencyFailureTest.suite());
        suite.addTest(ComponentFailureTest.suite());
        suite.addTest(DependenciesEntryFailureTest.suite());
        suite.addTest(DefaultXmlDependenciesEntryPersistenceFailureTest.suite());
        suite.addTest(DotNetDependenciesEntryExtractorFailureTest.suite());
        suite.addTest(JavaDependenciesEntryExtractorFailureTest.suite());
        suite.addTest(LocalBuildFileProviderFailureTest.suite());
        suite.addTest(MultipleFormatDependenciesEntryExtractorFailureTest.suite());
        suite.addTest(ZipBuildFileProviderFailureTest.suite());
        return suite;
    }

}
