/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.confluence.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ConfluencePageCreationResultFailureTests.class);
        suite.addTestSuite(PageFailureTests.class);
        suite.addTestSuite(DefaultConfluenceManagerFailureTests.class);

        return suite;
    }

}
