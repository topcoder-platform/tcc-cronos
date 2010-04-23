/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.actions.accuracytests;

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
        suite.addTestSuite(FileUploadAttachContestFileActionTests.class);
        suite.addTestSuite(ContestActionTests.class);
        suite.addTestSuite(DeleteDocumentContestActionTests.class);
        suite.addTestSuite(GetContestCategoriesActionTests.class);
        suite.addTestSuite(GetContestTechnologiesActionTests.class);
        suite.addTestSuite(GetDocumentsContestActionTests.class);
        suite.addTestSuite(MimeTypeRetrieverTests.class);
        suite.addTestSuite(StudioOrSoftwareContestActionTests.class);
        return suite;
    }

}
