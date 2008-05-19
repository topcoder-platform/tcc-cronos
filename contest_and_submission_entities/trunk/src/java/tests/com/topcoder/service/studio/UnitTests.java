/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.studio;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.ContestChannelTest;
import com.topcoder.service.studio.contest.ContestConfigTest;
import com.topcoder.service.studio.contest.ContestPropertyTest;
import com.topcoder.service.studio.contest.ContestRegistrationTest;
import com.topcoder.service.studio.contest.ContestStatusTest;
import com.topcoder.service.studio.contest.ContestTest;
import com.topcoder.service.studio.contest.ContestTypeConfigTest;
import com.topcoder.service.studio.contest.ContestTypeTest;
import com.topcoder.service.studio.contest.Demo;
import com.topcoder.service.studio.contest.DocumentTest;
import com.topcoder.service.studio.contest.DocumentTypeTest;
import com.topcoder.service.studio.contest.FilePathTest;
import com.topcoder.service.studio.contest.MimeTypeTest;
import com.topcoder.service.studio.contest.StudioFileTypeTest;
import com.topcoder.service.studio.submission.ContestResultTest;
import com.topcoder.service.studio.submission.PaymentStatusTest;
import com.topcoder.service.studio.submission.PrizeTest;
import com.topcoder.service.studio.submission.PrizeTypeTest;
import com.topcoder.service.studio.submission.ReviewStatusTest;
import com.topcoder.service.studio.submission.SubmissionPaymentTest;
import com.topcoder.service.studio.submission.SubmissionReviewTest;
import com.topcoder.service.studio.submission.SubmissionStatusTest;
import com.topcoder.service.studio.submission.SubmissionTest;
import com.topcoder.service.studio.submission.SubmissionTypeTest;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ContestConfigTest.suite());
        suite.addTest(ContestChannelTest.suite());
        suite.addTest(ContestStatusTest.suite());
        suite.addTest(ContestRegistrationTest.suite());
        suite.addTest(ContestTest.suite());
        suite.addTest(ContestTypeTest.suite());
        suite.addTest(Demo.suite());
        suite.addTest(DocumentTest.suite());
        suite.addTest(DocumentTypeTest.suite());
        suite.addTest(FilePathTest.suite());
        suite.addTest(MimeTypeTest.suite());
        suite.addTest(StudioFileTypeTest.suite());
        suite.addTest(ContestResultTest.suite());
        suite.addTest(PaymentStatusTest.suite());
        suite.addTest(PrizeTest.suite());
        suite.addTest(PrizeTypeTest.suite());
        suite.addTest(ReviewStatusTest.suite());
        suite.addTest(SubmissionPaymentTest.suite());
        suite.addTest(SubmissionReviewTest.suite());
        suite.addTest(SubmissionStatusTest.suite());
        suite.addTest(SubmissionTest.suite());
        suite.addTest(SubmissionTypeTest.suite());
        suite.addTest(ContestPropertyTest.suite());
        suite.addTest(ContestTypeConfigTest.suite());
        return suite;
    }
}
