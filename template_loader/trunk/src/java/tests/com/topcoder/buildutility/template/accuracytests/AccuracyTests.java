/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.buildutility.template.accuracytests;

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
        
        suite.addTestSuite(TemplateAccuracyTests.class);
        suite.addTestSuite(TemplateHierarchyAccuracyTests.class);
        suite.addTestSuite(TemplateLoaderAccuracyTests.class);
        suite.addTestSuite(JDBCPersistenceAccuracyTests.class);
        
        return suite;
    }

}
