/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * The test suite.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(SaveSpecificationReviewCommentActionUnitTest.class);
        suite.addTestSuite(StartSpecificationReviewActionUnitTest.class);
        suite.addTestSuite(ViewSpecificationReviewActionUnitTest.class);
        suite.addTestSuite(ResubmitSpecificationReviewActionUnitTest.class);
        suite.addTestSuite(SpecificationReviewActionUnitTest.class);
        suite.addTestSuite(SessionAwareActionUnitTest.class);

        suite.addTestSuite(SaveSpecificationReviewCommentActionExceptionUnitTest.class);
        suite.addTestSuite(ResubmitSpecificationReviewActionExceptionUnitTest.class);
        suite.addTestSuite(StartSpecificationReviewActionExceptionUnitTest.class);
        suite.addTestSuite(ViewSpecificationReviewActionExceptionUnitTest.class);
        suite.addTestSuite(SpecificationReviewActionExceptionUnitTest.class);

        suite.addTestSuite(ViewSpecificationReviewActionResultDataUnitTest.class);
        suite.addTestSuite(SaveSpecificationReviewCommentActionResultDataUnitTest.class);

        suite.addTestSuite(HelperUnitTest.class);

        return suite;
    }

}
