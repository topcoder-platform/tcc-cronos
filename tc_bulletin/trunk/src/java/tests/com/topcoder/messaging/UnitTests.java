/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import com.topcoder.messaging.persistence.InformixMessageBoardPersistenceAccTests;
import com.topcoder.messaging.persistence.InformixMessageBoardPersistenceFailureTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author yqw
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Returns all Unit test cases.
     * </p>
     *
     * @return all Unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(BasicEntityDataTests.class);
        suite.addTestSuite(CommonEntityDataTests.class);
        suite.addTestSuite(EntityNotFoundExceptionTests.class);
        suite.addTestSuite(ErrorMessagesCacheTests.class);
        suite.addTestSuite(HelperTests.class);
        suite.addTestSuite(MessageAttributeTests.class);
        suite.addTestSuite(MessageBoardAccTests.class);
        suite.addTestSuite(MessageBoardConfigurationExceptionTests.class);
        suite.addTestSuite(MessageBoardFailureTests.class);
        suite.addTestSuite(MessageBoardManagementExceptionTests.class);
        suite.addTestSuite(MessageBoardPersistenceExceptionTests.class);
        suite.addTestSuite(MessageTests.class);
        suite.addTestSuite(ResponseTests.class);
        suite.addTestSuite(ThreadTests.class);
        suite.addTestSuite(Demo.class);
        suite.addTestSuite(InformixMessageBoardPersistenceAccTests.class);
        suite.addTestSuite(InformixMessageBoardPersistenceFailureTests.class);
        
        return suite;
    }

}
