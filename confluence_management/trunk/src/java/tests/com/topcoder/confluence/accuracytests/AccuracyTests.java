/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.confluence.accuracytests;

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
     *
     * @return Accuracy test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ComponentTypeAccuracyTest.class);
        suite.addTestSuite(ConfluenceAssetTypeAccuracyTest.class);
        suite.addTestSuite(ConfluenceCatalogAccuracyTest.class);
        suite.addTestSuite(ConfluencePageCreatedActionAccuracyTest.class);
        suite.addTestSuite(ConfluencePageCreationResultAccuracyTest.class);
        suite.addTestSuite(DefaultConfluenceManagerAccuracyTest.class);
        suite.addTestSuite(PageAccuracyTest.class);

        return suite;
    }

}
