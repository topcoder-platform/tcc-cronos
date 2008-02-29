/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document;

import com.topcoder.service.prerequisite.document.ejb.DocumentManagerBeanInvalidParametersUnitTests;
import com.topcoder.service.prerequisite.document.ejb.DocumentManagerBeanNotAuthorizedUnitTests;
import com.topcoder.service.prerequisite.document.ejb.DocumentManagerBeanUnitTests;
import com.topcoder.service.prerequisite.document.model.CompetitionDocumentUnitTests;
import com.topcoder.service.prerequisite.document.model.DocumentNameUnitTests;
import com.topcoder.service.prerequisite.document.model.DocumentUnitTests;
import com.topcoder.service.prerequisite.document.model.DocumentVersionUnitTests;
import com.topcoder.service.prerequisite.document.model.MemberAnswerUnitTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all unit tests.
     * </p>
     *
     * @return test suite aggregating all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(HelperUnitTests.suite());

        suite.addTest(DocumentManagerExceptionUnitTests.suite());
        suite.addTest(AuthorizationExceptionUnitTests.suite());
        suite.addTest(ConfigurationExceptionUnitTests.suite());
        suite.addTest(DocumentPersistenceExceptionUnitTests.suite());
        suite.addTest(CompetitionDocumentAnsweredExceptionUnitTests.suite());

        // entities
        suite.addTest(DocumentUnitTests.suite());
        suite.addTest(DocumentNameUnitTests.suite());
        suite.addTest(CompetitionDocumentUnitTests.suite());
        suite.addTest(MemberAnswerUnitTests.suite());
        suite.addTest(DocumentVersionUnitTests.suite());

        suite.addTest(DocumentManagerBeanUnitTests.suite());
        suite.addTest(DocumentManagerBeanNotAuthorizedUnitTests.suite());
        suite.addTest(DocumentManagerBeanInvalidParametersUnitTests.suite());
        suite.addTestSuite(Demo.class);
        return suite;
    }

}
