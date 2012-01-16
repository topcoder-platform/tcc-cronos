/*
 * Copyright (c) 2011, TopCoder, Inc. All rights reserved
 */
package com.topcoder.payments.amazonfps.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(AmazonPaymentManagerImplAccTest.suite());
        suite.addTest(DatabaseAuthorizationPersistenceAccTest.suite());
        suite.addTest(DatabasePaymentPersistenceAccTest.suite());
        suite.addTest(JMSAmazonPaymentEventReceiverAccTest.suite());
        suite.addTest(JMSAmazonPaymentEventSubscriberAccTest.suite());
        return suite;
    }

}
