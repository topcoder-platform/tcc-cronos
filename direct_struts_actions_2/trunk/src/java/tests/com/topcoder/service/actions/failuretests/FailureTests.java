/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all failure test cases.</p>
 *
 * @author moon.river
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * The test suite.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ContestActionTest.class);
        suite.addTestSuite(StudioOrSoftwareContestActionTest.class);
        suite.addTestSuite(DeleteDocumentContestActionTest.class);
        suite.addTestSuite(FileUploadAttachContestFileActionTest.class);
        suite.addTestSuite(GetContestCategoriesActionTest.class);
        suite.addTestSuite(GetContestTechnologiesActionTest.class);
        suite.addTestSuite(GetDocumentFileContestActionTest.class);
        suite.addTestSuite(GetDocumentsContestActionTest.class);
        suite.addTestSuite(MimeTypeRetrieverTest.class);
        suite.addTestSuite(PayByBillingAccountActionTest.class);
        suite.addTestSuite(PayByCreditCardActionTest.class);

        return suite;
    }
}
