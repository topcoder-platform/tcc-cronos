/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.studio.accuracytests;

import com.topcoder.service.studio.accuracytests.contest.ConfigUnitTest;
import com.topcoder.service.studio.accuracytests.contest.MimeTypeUnitTest;
import com.topcoder.service.studio.accuracytests.contest.StudioFileTypeUnitTest;
import com.topcoder.service.studio.accuracytests.submission.ContestResultUnitTest;
import com.topcoder.service.studio.accuracytests.submission.PrizeUnitTest;
import com.topcoder.service.studio.accuracytests.submission.SubmissionPaymentUnitTest;
import com.topcoder.service.studio.accuracytests.submission.SubmissionReviewUnitTest;
import com.topcoder.service.studio.accuracytests.submission.SubmissionUnitTest;

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

        suite.addTest(ConfigUnitTest.suite());
        suite.addTest(MimeTypeUnitTest.suite());
        suite.addTest(StudioFileTypeUnitTest.suite());

        suite.addTest(ContestResultUnitTest.suite());
        suite.addTest(PrizeUnitTest.suite());
        suite.addTest(SubmissionPaymentUnitTest.suite());
        suite.addTest(SubmissionReviewUnitTest.suite());
        suite.addTest(SubmissionUnitTest.suite());

        return suite;
    }
}
