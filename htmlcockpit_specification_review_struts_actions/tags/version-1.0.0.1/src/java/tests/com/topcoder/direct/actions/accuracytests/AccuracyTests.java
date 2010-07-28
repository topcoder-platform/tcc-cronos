/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.direct.actions.accuracytests;

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
        suite.addTestSuite(ResubmitSpecificationReviewActionAccuracyTest.class);
        suite.addTestSuite(SaveSpecificationReviewCommentActionAccuracyTest.class);
        suite.addTestSuite(StartSpecificationReviewActionAccuracyTest.class);
        suite.addTestSuite(ViewSpecificationReviewActionAccuracyTest.class);
        return suite;
    }

}
