/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

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

        suite.addTestSuite(ActionHelperUnitTest.class);
        suite.addTestSuite(ContestActionUnitTest.class);
        suite.addTestSuite(DeleteDocumentContestActionUnitTest.class);
        suite.addTestSuite(FileUploadAttachContestFileActionUnitTest.class);
        suite.addTestSuite(GetDocumentFileContestActionUnitTest.class);
        suite.addTestSuite(GetDocumentsContestActionUnitTest.class);
        suite.addTestSuite(MimeTypeRetrieverUnitTest.class);
        suite.addTestSuite(PayByBillingAccountActionUnitTest.class);
        suite.addTestSuite(PayByCreditCardActionUnitTest.class);
        suite.addTestSuite(GetContestCategoriesActionUnitTest.class);
        suite.addTestSuite(GetContestTechnologiesActionUnitTest.class);
        suite.addTestSuite(PayContestActionUnitTest.class);
        suite.addTestSuite(StudioOrSoftwareContestActionUnitTest.class);

        return suite;
    }
}
