/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.failuretests;

import com.topcoder.service.prerequisite.document.failuretests.ejb.DocumentManagerBeanUnitTests;
import com.topcoder.service.prerequisite.document.failuretests.model.CompetitionDocumentUnitTests;
import com.topcoder.service.prerequisite.document.failuretests.model.DocumentNameUnitTests;
import com.topcoder.service.prerequisite.document.failuretests.model.DocumentUnitTests;
import com.topcoder.service.prerequisite.document.failuretests.model.DocumentVersionUnitTests;
import com.topcoder.service.prerequisite.document.failuretests.model.MemberAnswerUnitTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure tests for Prerequisite_Document_Manager.
 * 
 * @author 80x86
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Runs all failure test
     * 
     * @return instance of Test
     */
    public static Test suite() {
        TestSuite suite = new TestSuite();
        // EJB
        suite.addTestSuite(DocumentManagerBeanUnitTests.class);
        // models
        suite.addTestSuite(CompetitionDocumentUnitTests.class);
        suite.addTestSuite(DocumentNameUnitTests.class);
        suite.addTestSuite(DocumentUnitTests.class);
        suite.addTestSuite(DocumentVersionUnitTests.class);
        suite.addTestSuite(MemberAnswerUnitTests.class);
        return suite;
    }
}
