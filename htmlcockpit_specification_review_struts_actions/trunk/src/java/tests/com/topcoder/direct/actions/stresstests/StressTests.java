/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all stress test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * The test suite.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(StartSpecificationReviewActionTest.class);
        suite.addTestSuite(ViewSpecificationReviewActionTest.class);
        suite.addTestSuite(ResubmitSpecificationReviewActionTest.class);
        suite.addTestSuite(SpecificationReviewActionTest.class);

        return suite;
    }

}
