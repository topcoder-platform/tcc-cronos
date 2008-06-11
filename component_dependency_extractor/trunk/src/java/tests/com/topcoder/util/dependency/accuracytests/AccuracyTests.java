/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        // persistence
        suite.addTestSuite(BinaryFileDependenciesEntryPersistenceAccuracyTest.class);
        suite.addTestSuite(DefaultXmlDependenciesEntryPersistenceAccuracyTest.class);

        // utility
        suite.addTestSuite(ComponentDependencyExtractorUtilityAccuracyTest.class);

        // extractor
        suite.addTestSuite(DotNetDependenciesEntryExtractorAccuracyTest.class);
        suite.addTestSuite(JavaDependenciesEntryExtractorAccuracyTest.class);
        suite.addTestSuite(MultipleFormatDependenciesEntryExtractorAccuracyTest.class);

        // provider
        suite.addTestSuite(LocalBuildFileProviderAccuracyTest.class);
        suite.addTestSuite(ZipBuildFileProviderAccuracyTest.class);

        return suite;
    }

}
