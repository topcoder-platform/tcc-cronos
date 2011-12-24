/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Test suite for the failure tests.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(AmazonPaymentManagerImplFailureTests.suite());
        suite.addTest(DatabaseAuthorizationPersistenceFailureTests.suite());
        suite.addTest(DatabasePaymentPersistenceFailureTests.suite());
        suite.addTest(BaseDatabasePersistenceFailureTests.suite());
        suite.addTest(JMSAmazonPaymentEventReceiverFailureTests.suite());
        suite.addTest(JMSAmazonPaymentEventSubscriberFailureTests.suite());

        return suite;
    }

}
