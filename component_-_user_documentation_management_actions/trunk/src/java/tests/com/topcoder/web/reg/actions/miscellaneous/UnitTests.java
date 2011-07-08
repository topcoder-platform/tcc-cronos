/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Aggregates all Unit tests for this component.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * All unit test cases.
     * </p>
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AffidavitHistoryActionTest.class);
        suite.addTestSuite(AssignmentDocumentHistoryActionTest.class);
        suite.addTestSuite(AffirmAffidavitActionTest.class);
        suite.addTestSuite(BaseActionTest.class);
        suite.addTestSuite(BaseRangeActionTest.class);
        suite.addTestSuite(PaymentHistoryActionTest.class);
        suite.addTestSuite(UserDocumentationManagementActionsConfigurationExceptionTest.class);
        suite.addTestSuite(UserDocumentationManagementActionsExceptionTest.class);
        suite.addTestSuite(VisaLetterStatusActionTest.class);
        suite.addTestSuite(Demo.class);

        return suite;
    }
}
