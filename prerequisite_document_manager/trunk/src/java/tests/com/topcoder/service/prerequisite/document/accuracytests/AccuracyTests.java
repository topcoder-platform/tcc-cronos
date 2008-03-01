/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all accuracy test cases.</p>
 *
 * @author liuliquan
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * Returns the accuracy test suite.
     * @return the accuracy test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AuthorizationExceptionAccTests.class);
        suite.addTestSuite(CompetitionDocumentAnsweredExceptionAccTests.class);
        suite.addTestSuite(ConfigurationExceptionAccTests.class);
        suite.addTestSuite(DocumentManagerExceptionAccTests.class);
        suite.addTestSuite(DocumentPersistenceExceptionAccTests.class);

        suite.addTestSuite(DocumentAccTests.class);
        suite.addTestSuite(DocumentNameAccTests.class);
        suite.addTestSuite(DocumentVersionAccTests.class);
        suite.addTestSuite(CompetitionDocumentAccTests.class);
        suite.addTestSuite(MemberAnswerAccTests.class);

        suite.addTestSuite(DocumentManagerBeanAccTest.class);
        return suite;
    }
}
