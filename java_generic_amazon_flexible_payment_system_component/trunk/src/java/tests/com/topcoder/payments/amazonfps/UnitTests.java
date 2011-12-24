/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps;

import com.topcoder.payments.amazonfps.model.AuthorizationTest;
import com.topcoder.payments.amazonfps.model.MapAdapterTest;
import com.topcoder.payments.amazonfps.model.MapEntryTest;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationDataTest;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationRequestTest;
import com.topcoder.payments.amazonfps.model.PaymentDetailsTest;
import com.topcoder.payments.amazonfps.model.PaymentEventTest;
import com.topcoder.payments.amazonfps.model.PaymentEventTypeTest;
import com.topcoder.payments.amazonfps.model.PaymentOperationTest;
import com.topcoder.payments.amazonfps.model.PaymentOperationTypeTest;
import com.topcoder.payments.amazonfps.model.PaymentStatusTest;
import com.topcoder.payments.amazonfps.model.PaymentTest;
import com.topcoder.payments.amazonfps.persistence.AuthorizationPersistenceExceptionTest;
import com.topcoder.payments.amazonfps.persistence.PaymentPersistenceExceptionTest;
import com.topcoder.payments.amazonfps.persistence.db.BaseDatabasePersistenceTest;
import com.topcoder.payments.amazonfps.persistence.db.DatabaseAuthorizationPersistenceTest;
import com.topcoder.payments.amazonfps.persistence.db.DatabasePaymentPersistenceTest;
import com.topcoder.payments.amazonfps.subscribers.jms.AmazonPaymentEventReceivingExceptionTest;
import com.topcoder.payments.amazonfps.subscribers.jms.JMSAmazonPaymentEventReceiverTest;
import com.topcoder.payments.amazonfps.subscribers.jms.JMSAmazonPaymentEventSubscriberTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This test case aggregates all Unit test cases.
 *
 * @author KennyAlive
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Gets test suite with all unit test cases.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // Exceptions
        suite.addTest(AmazonFlexiblePaymentManagementExceptionTest.suite());
        suite.addTest(AmazonFlexiblePaymentSystemComponentConfigurationExceptionTest.suite());
        suite.addTest(AmazonPaymentEventReceivingExceptionTest.suite());
        suite.addTest(AuthorizationNotFoundExceptionTest.suite());
        suite.addTest(AuthorizationPersistenceExceptionTest.suite());
        suite.addTest(PaymentNotFoundExceptionTest.suite());
        suite.addTest(PaymentPersistenceExceptionTest.suite());

        // Model
        suite.addTest(AuthorizationTest.suite());
        suite.addTest(MapAdapterTest.suite());
        suite.addTest(MapEntryTest.suite());
        suite.addTest(PaymentTest.suite());
        suite.addTest(PaymentAuthorizationDataTest.suite());
        suite.addTest(PaymentAuthorizationRequestTest.suite());
        suite.addTest(PaymentDetailsTest.suite());
        suite.addTest(PaymentEventTest.suite());
        suite.addTest(PaymentEventTypeTest.suite());
        suite.addTest(PaymentOperationTest.suite());
        suite.addTest(PaymentOperationTypeTest.suite());
        suite.addTest(PaymentStatusTest.suite());

        // Helper
        suite.addTest(HelperTest.suite());

        // Event subscriber/receiver
        suite.addTest(JMSAmazonPaymentEventSubscriberTest.suite());
        suite.addTest(JMSAmazonPaymentEventReceiverTest.suite());

        // Persistence
        suite.addTest(BaseDatabasePersistenceTest.suite());
        suite.addTest(DatabaseAuthorizationPersistenceTest.suite());
        suite.addTest(DatabasePaymentPersistenceTest.suite());

        // AmazonPaymentManagerImpl
        suite.addTest(AmazonPaymentManagerImplTest.suite());

        // Demo
        suite.addTest(Demo.suite());
        return suite;
    }
}
