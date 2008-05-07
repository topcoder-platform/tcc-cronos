/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import com.topcoder.service.prerequisite.ejb.CompetitionNotFoundFaultUnitTests;
import com.topcoder.service.prerequisite.ejb.DocumentNotFoundFaultUnitTests;
import com.topcoder.service.prerequisite.ejb.GetAllPrerequisiteDocumentsRequestUnitTests;
import com.topcoder.service.prerequisite.ejb.GetAllPrerequisiteDocumentsResponseUnitTests;
import com.topcoder.service.prerequisite.ejb.GetPrerequisiteDocumentRequestUnitTests;
import com.topcoder.service.prerequisite.ejb.GetPrerequisiteDocumentResponseUnitTests;
import com.topcoder.service.prerequisite.ejb.GetPrerequisiteDocumentsRequestUnitTests;
import com.topcoder.service.prerequisite.ejb.GetPrerequisiteDocumentsResponseUnitTests;
import com.topcoder.service.prerequisite.ejb.IllegalArgumentWSFaultUnitTests;
import com.topcoder.service.prerequisite.ejb.PersistenceFaultUnitTests;
import com.topcoder.service.prerequisite.ejb.PrerequisiteElementsFactoryUnitTests;
import com.topcoder.service.prerequisite.ejb.PrerequisiteServiceBeanUnitTests;
import com.topcoder.service.prerequisite.ejb.RecordMemberAnswerRequestUnitTests;
import com.topcoder.service.prerequisite.ejb.RecordMemberAnswerResponseUnitTests;
import com.topcoder.service.prerequisite.ejb.RoleNotFoundFaultUnitTests;
import com.topcoder.service.prerequisite.ejb.UserAlreadyAnsweredDocumentFaultUnitTests;
import com.topcoder.service.prerequisite.ejb.UserNotAuthorizedFaultUnitTests;

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

        suite.addTest(PrerequisiteServiceBeanUnitTests.suite());

        suite.addTest(CompetitionNotFoundExceptionUnitTests.suite());
        suite.addTest(PrerequisiteServiceExceptionUnitTests.suite());
        suite.addTest(DocumentNotFoundExceptionUnitTests.suite());
        suite.addTest(IllegalArgumentWSExceptionUnitTests.suite());
        suite.addTest(PersistenceExceptionUnitTests.suite());
        suite.addTest(RoleNotFoundExceptionUnitTests.suite());
        suite.addTest(UserAlreadyAnsweredDocumentExceptionUnitTests.suite());
        suite.addTest(UserNotAuthorizedExceptionUnitTests.suite());
        suite.addTest(PrerequisiteDocumentUnitTests.suite());

        suite.addTest(CompetitionNotFoundFaultUnitTests.suite());
        suite.addTest(DocumentNotFoundFaultUnitTests.suite());
        suite.addTest(IllegalArgumentWSFaultUnitTests.suite());
        suite.addTest(PersistenceFaultUnitTests.suite());
        suite.addTest(RoleNotFoundFaultUnitTests.suite());
        suite.addTest(UserAlreadyAnsweredDocumentFaultUnitTests.suite());
        suite.addTest(UserNotAuthorizedFaultUnitTests.suite());
        suite.addTest(GetAllPrerequisiteDocumentsRequestUnitTests.suite());
        suite.addTest(GetAllPrerequisiteDocumentsResponseUnitTests.suite());
        suite.addTest(GetPrerequisiteDocumentsRequestUnitTests.suite());
        suite.addTest(GetPrerequisiteDocumentsResponseUnitTests.suite());
        suite.addTest(GetPrerequisiteDocumentRequestUnitTests.suite());
        suite.addTest(GetPrerequisiteDocumentResponseUnitTests.suite());
        suite.addTest(RecordMemberAnswerResponseUnitTests.suite());
        suite.addTest(RecordMemberAnswerRequestUnitTests.suite());
        suite.addTest(PrerequisiteElementsFactoryUnitTests.suite());

        suite.addTest(Demo.suite());
        return suite;
    }

}
