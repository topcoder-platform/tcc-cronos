/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.failuretests;

import java.math.BigDecimal;
import java.sql.Connection;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException;
import com.topcoder.payments.amazonfps.PaymentNotFoundException;
import com.topcoder.payments.amazonfps.model.Payment;
import com.topcoder.payments.amazonfps.model.PaymentOperation;
import com.topcoder.payments.amazonfps.model.PaymentOperationType;
import com.topcoder.payments.amazonfps.model.PaymentStatus;
import com.topcoder.payments.amazonfps.persistence.PaymentPersistenceException;
import com.topcoder.payments.amazonfps.persistence.db.DatabasePaymentPersistence;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for DatabasePaymentPersistence.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class DatabasePaymentPersistenceFailureTests extends TestCase {

    /**
     * <p>
     * Represent the DatabasePaymentPersistence instance for testing.
     * </p>
     */
    private DatabasePaymentPersistence instance;

    /**
     * <p>
     * Represent the ConfigurationObject instance for testing.
     * </p>
     */
    private ConfigurationObject configuration;

    /**
     * <p>
     * Represent the Connection instance for testing.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Represent the payment instance for testing.
     * </p>
     */
    private Payment payment;

    /**
     * <p>
     * Represent the PaymentOperation instance for testing.
     * </p>
     */
    private PaymentOperation paymentOperation;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        connection = FailureTestHelper.getConnection();
        FailureTestHelper.clearDB(connection);
        FailureTestHelper.loadDB(connection);

        instance = new DatabasePaymentPersistence();
        configuration = FailureTestHelper.getConfig("com.topcoder.payments.amazonfps.AmazonPaymentManagerImpl").getChild("paymentPersistenceConfig");

        payment = new Payment();
        payment.setId(1);
        payment.setAuthorizationId(1);
        payment.setAmount(BigDecimal.valueOf(100));
        payment.setStatus(PaymentStatus.COMPLETED);

        paymentOperation = new PaymentOperation();
        paymentOperation.setPaymentId(1);
        paymentOperation.setType(PaymentOperationType.PAY);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        try {
            FailureTestHelper.clearDB(connection);
        } finally {
            FailureTestHelper.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DatabasePaymentPersistenceFailureTests.class);
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#getPayment(long) for failure.
     * It tests the case that when paymentId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetPayment_ZeroPaymentId() throws Exception {
        try {
            instance.getPayment(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#getPayment(long) for failure.
     * It tests the case that when paymentIdGenerator is null and expects IllegalStateException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetPayment_NullPaymentIdGenerator() throws Exception {
        try {
            instance.getPayment(1);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#getPayment(long) for failure.
     * Expects PaymentPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetPayment_PaymentPersistenceException() throws Exception {
        configuration.setPropertyValue("connectionName", "invalid");
        instance.configure(configuration);
        try {
            instance.getPayment(1);
            fail("PaymentPersistenceException expected.");
        } catch (PaymentPersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#createPayment(Payment) for failure.
     * It tests the case that when payment is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreatePayment_NullPayment() throws Exception {
        try {
            instance.createPayment(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#createPayment(Payment) for failure.
     * It tests the case that when payment.getAuthorizationId() &lt;= 0 and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreatePayment_ZeroAuthorizationId() throws Exception {
        payment.setAuthorizationId(0);
        try {
            instance.createPayment(payment);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#createPayment(Payment) for failure.
     * It tests the case that when payment.getAmount() is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreatePayment_NullAmount() throws Exception {
        payment.setAmount(null);
        try {
            instance.createPayment(payment);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#createPayment(Payment) for failure.
     * It tests the case that when payment.getAmount().compareTo(BigDecimal.ZERO) &lt;= 0 and
     * expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreatePayment_ZeroAmount() throws Exception {
        payment.setAmount(BigDecimal.valueOf(0));
        try {
            instance.createPayment(payment);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#createPayment(Payment) for failure.
     * It tests the case that when payment.getStatus() is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreatePayment_NullStatus() throws Exception {
        payment.setStatus(null);
        try {
            instance.createPayment(payment);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#createPayment(Payment) for failure.
     * It tests the case that when paymentIdGenerator is null and expects IllegalStateException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreatePayment_NullpaymentIdGenerator() throws Exception {
        try {
            instance.createPayment(payment);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#createPayment(Payment) for failure.
     * Expects PaymentPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreatePayment_PaymentPersistenceException() throws Exception {
        configuration.setPropertyValue("connectionName", "invalid");
        instance.configure(configuration);
        try {
            instance.createPayment(payment);
            fail("PaymentPersistenceException expected.");
        } catch (PaymentPersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#updatePayment(Payment) for failure.
     * It tests the case that when payment is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdatePayment_NullPayment() throws Exception {
        try {
            instance.updatePayment(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#updatePayment(Payment) for failure.
     * It tests the case that when payment.getAuthorizationId() &lt;= 0 and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdatePayment_ZeroAuthorizationId() throws Exception {
        payment.setAuthorizationId(0);
        try {
            instance.updatePayment(payment);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#updatePayment(Payment) for failure.
     * It tests the case that when payment.getId() &lt;= 0 and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdatePayment_ZeroPaymentId() throws Exception {
        payment.setId(0);
        instance.configure(configuration);
        try {
            instance.updatePayment(payment);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#updatePayment(Payment) for failure.
     * It tests the case that when payment.getAmount() is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdatePayment_NullAmount() throws Exception {
        payment.setAmount(null);
        try {
            instance.updatePayment(payment);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#updatePayment(Payment) for failure.
     * It tests the case that when payment.getAmount().compareTo(BigDecimal.ZERO) &lt;= 0 and
     * expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdatePayment_ZeroAmount() throws Exception {
        payment.setAmount(BigDecimal.valueOf(0));
        try {
            instance.updatePayment(payment);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#updatePayment(Payment) for failure.
     * It tests the case that when payment.getStatus() is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdatePayment_NullStatus() throws Exception {
        payment.setStatus(null);
        try {
            instance.updatePayment(payment);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#updatePayment(Payment) for failure.
     * It tests the case that when paymentIdGenerator is null and expects IllegalStateException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdatePayment_NullpaymentIdGenerator() throws Exception {
        try {
            instance.updatePayment(payment);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#updatePayment(Payment) for failure.
     * Expects PaymentPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdatePayment_PaymentPersistenceException() throws Exception {
        configuration.setPropertyValue("connectionName", "invalid");
        instance.configure(configuration);
        try {
            instance.updatePayment(payment);
            fail("PaymentPersistenceException expected.");
        } catch (PaymentPersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#updatePayment(Payment) for failure.
     * Expects PaymentNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdatePayment_PaymentNotFoundException() throws Exception {
        instance.configure(configuration);
        try {
            instance.updatePayment(payment);
            fail("PaymentNotFoundException expected.");
        } catch (PaymentNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#createPaymentOperation(PaymentOperation) for failure.
     * It tests the case that when paymentOperation is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreatePaymentOperation_NullPaymentOperation() throws Exception {
        try {
            instance.createPaymentOperation(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#createPaymentOperation(PaymentOperation) for failure.
     * It tests the case that when PaymentId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreatePaymentOperation_ZeroPaymentId() throws Exception {
        paymentOperation.setPaymentId(0);
        try {
            instance.createPaymentOperation(paymentOperation);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#createPaymentOperation(PaymentOperation) for failure.
     * It tests the case that when  paymentOperation.getType() is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreatePaymentOperation_NullType() throws Exception {
        paymentOperation.setType(null);
        try {
            instance.createPaymentOperation(paymentOperation);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#createPaymentOperation(PaymentOperation) for failure.
     * It tests the case that when paymentOperationIdGenerator is null and expects IllegalStateException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreatePaymentOperation_NullpaymentOperationIdGenerator() throws Exception {
        try {
            instance.createPaymentOperation(paymentOperation);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#createPaymentOperation(PaymentOperation) for failure.
     * Expects PaymentPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreatePaymentOperation_PaymentPersistenceException() throws Exception {
        configuration.setPropertyValue("connectionName", "invalid");
        instance.configure(configuration);
        try {
            instance.createPaymentOperation(paymentOperation);
            fail("PaymentPersistenceException expected.");
        } catch (PaymentPersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#configure(ConfigurationObject) for failure.
     * It tests the case that when configuration is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConfigure_NullConfiguration() throws Exception {
        try {
            instance.configure(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabasePaymentPersistence#configure(ConfigurationObject) for failure.
     * It tests the case that when authorizationIdGeneratorName is null and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConfigure_NullauthorizationIdGeneratorName() throws Exception {
        try {
            instance.configure(new DefaultConfigurationObject("test"));
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

}