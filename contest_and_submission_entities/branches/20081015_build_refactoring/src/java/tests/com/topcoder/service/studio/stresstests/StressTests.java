/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * All stress test cases.
     * </p>
     *
     * @return stress tests suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(new TestSuite(ConfigStressTest.class));
        suite.addTest(new TestSuite(ContestCategoryStressTest.class));
        suite.addTest(new TestSuite(ContestResultStressTest.class));
        suite.addTest(new TestSuite(ContestStatusStressTest.class));
        suite.addTest(new TestSuite(ContestStressTest.class));
        suite.addTest(new TestSuite(ContestTypeStressTest.class));
        suite.addTest(new TestSuite(DocumentStressTest.class));
        suite.addTest(new TestSuite(DocumentTypeStressTest.class));
        suite.addTest(new TestSuite(FilePathStressTest.class));
        suite.addTest(new TestSuite(MimeTypeStressTest.class));
        suite.addTest(new TestSuite(PaymentStatusStressTest.class));
        suite.addTest(new TestSuite(PrizeStressTest.class));
        suite.addTest(new TestSuite(PrizeTypeStressTest.class));
        suite.addTest(new TestSuite(ReviewStatusStressTest.class));
        suite.addTest(new TestSuite(StudioFileTypeStressTest.class));
        suite.addTest(new TestSuite(SubmissionPaymentStressTest.class));
        suite.addTest(new TestSuite(SubmissionReviewStressTest.class));
        suite.addTest(new TestSuite(SubmissionStatusStressTest.class));
        suite.addTest(new TestSuite(SubmissionStressTest.class));
        suite.addTest(new TestSuite(SubmissionTypeStressTest.class));

        return suite;
    }
}
