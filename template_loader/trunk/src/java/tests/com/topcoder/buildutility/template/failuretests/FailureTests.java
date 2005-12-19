/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
 package com.topcoder.buildutility.template.failuretests;

import com.topcoder.buildutility.template.failuretests.persistence.JDBCPersistenceFailureTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        
        suite.addTestSuite(TemplateFailureTests.class);
        suite.addTestSuite(TemplateHierarchyFailureTests.class);
        suite.addTestSuite(TemplateLoaderFailureTests.class);
        suite.addTestSuite(JDBCPersistenceFailureTests.class);
        
        return suite;
    }

}
