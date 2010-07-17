/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure tests suite.
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * <p>
     * Aggregates all failure test cases.
     * </p>
     * 
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        
        suite.addTestSuite(ResubmitSpecificationReviewActionFailureTest.class);
        suite.addTestSuite(SaveSpecificationReviewCommentActionFailureTest.class);
        suite.addTestSuite(SaveSpecificationReviewCommentActionResultDataFailureTest.class);
        suite.addTestSuite(StartSpecificationReviewActionFailureTest.class);
        suite.addTestSuite(ViewSpecificationReviewActionFailureTest.class);
        suite.addTestSuite(ViewSpecificationReviewActionResultDataFailureTest.class);

        return suite;
    }
}
