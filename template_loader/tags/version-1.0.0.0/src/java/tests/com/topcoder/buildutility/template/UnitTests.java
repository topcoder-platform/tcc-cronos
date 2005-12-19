/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.buildutility.template;

import com.topcoder.buildutility.template.persistence.JDBCPersistenceTests;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(Demo.class);

        suite.addTestSuite(JDBCPersistenceTests.class);

        suite.addTestSuite(TemplateLoaderTests.class);
        suite.addTestSuite(ConfigurationExceptionTests.class);
        suite.addTestSuite(DuplicateObjectExceptionTests.class);
        suite.addTestSuite(PersistenceExceptionTests.class);
        suite.addTestSuite(DuplicateObjectExceptionTests.class);
        suite.addTestSuite(NullCacheTests.class);
        suite.addTestSuite(TemplateTests.class);
        suite.addTestSuite(TemplateHierarchyTests.class);

        return suite;
    }


}
