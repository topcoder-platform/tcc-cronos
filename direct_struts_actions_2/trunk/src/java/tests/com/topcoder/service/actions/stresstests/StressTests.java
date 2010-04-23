/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * Suite.
     *
     * @return the test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(GetContestCategoriesActionTest.class);
        suite.addTestSuite(GetContestTechnologiesActionTest.class);
        suite.addTestSuite(DeleteDocumentContestActionTest.class);
        suite.addTestSuite(FileUploadAttachContestFileActionTest.class);
        suite.addTestSuite(PayByBillingAccountActionTest.class);
        suite.addTestSuite(PayByCreditCardActionTest.class);
        suite.addTestSuite(GetDocumentsContestActionTest.class);
        suite.addTestSuite(GetDocumentFileContestActionTest.class);
        suite.addTest(ShowStressResult.suite());
        return suite;
    }
}
