/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.payments.amazonfps.model.Authorization;
import com.topcoder.payments.amazonfps.model.Payment;
import com.topcoder.payments.amazonfps.model.PaymentOperation;
import com.topcoder.payments.amazonfps.model.PaymentOperationType;
import com.topcoder.payments.amazonfps.model.PaymentStatus;
import com.topcoder.payments.amazonfps.persistence.PaymentPersistence;
import com.topcoder.payments.amazonfps.persistence.db.BaseDatabasePersistence;
import com.topcoder.payments.amazonfps.persistence.db.DatabaseAuthorizationPersistence;
import com.topcoder.payments.amazonfps.persistence.db.DatabasePaymentPersistence;

/**
 * <p>
 * Accuracy tests for class <code>DatabasePaymentPersistence</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class DatabasePaymentPersistenceAccTest extends BaseAccTest {
    /**
     * Represents the <code>DatabasePaymentPersistence</code> instance used to test against.
     */
    private DatabasePaymentPersistence impl;
    /**
     * Represents the authorization id used to test against.
     */
    private long authorizationId;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(DatabasePaymentPersistenceAccTest.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        impl = new DatabasePaymentPersistence();

        DatabaseAuthorizationPersistence databaseAuthorizationPersistence = new DatabaseAuthorizationPersistence();
        ConfigurationObject configuration = getConfig().getChild("authorizationPersistenceConfig");
        databaseAuthorizationPersistence.configure(configuration);

        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(true);
        authorization.setCancelled(false);
        authorization.setTokenId("tokenId1");
        authorization.setAuthorizedAmountLeft(BigDecimal.valueOf(100));
        authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(200));

        databaseAuthorizationPersistence.createAuthorization(authorization);

        this.authorizationId = authorization.getId();
    }

    /**
     * Tear down the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        impl = null;
        this.authorizationId = 0;
    }

    /**
     * Inheritance test, verifies <code>DatabasePaymentPersistence</code> subclasses should be
     * correct.
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.",
            impl instanceof BaseDatabasePersistence);
        assertTrue("The instance's subclass is not correct.", impl instanceof PaymentPersistence);
    }

    /**
     * Accuracy test for the constructor <code>DatabasePaymentPersistence()</code>.<br>
     * Instance should be created successfully.
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * Accuracy test for the method <code>createPayment(Payment)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCreatePayment() throws Exception {
        ConfigurationObject configuration = getConfig().getChild("paymentPersistenceConfig");

        impl.configure(configuration);

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(100));
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("key1", "value1");
        parameters.put("key2", "value2");
        parameters.put("key3", "value3");
        payment.setParameters(parameters);
        payment.setStatus(PaymentStatus.RESERVED);
        payment.setTransactionId("transactionId1");
        payment.setAuthorizationId(authorizationId);

        impl.createPayment(payment);

        assertTrue("'createPayment' should be correct.", payment.getId() > 0);

        Payment res = impl.getPayment(payment.getId());

        assertEquals("'createPayment' should be correct.", 100L, res.getAmount().longValue());
        assertEquals("'createPayment' should be correct.", 3, res.getParameters().size());
        assertEquals("'createPayment' should be correct.", "value1", res.getParameters()
            .get("key1"));
        assertEquals("'createPayment' should be correct.", "value2", res.getParameters()
            .get("key2"));
        assertEquals("'createPayment' should be correct.", "value3", res.getParameters()
            .get("key3"));
        assertEquals("'createPayment' should be correct.", PaymentStatus.RESERVED, res.getStatus());
        assertEquals("'createPayment' should be correct.", "transactionId1", res.getTransactionId());
        assertEquals("'createPayment' should be correct.", authorizationId,
            res.getAuthorizationId());
    }

    // /**
    // * Accuracy test for the method <code>createPayment(Payment)</code>.<br>
    // * Expects no error occurs.
    // *
    // * @throws Exception
    // * to JUnit
    // */
    // @Test
    // public void testCreatePayment2() throws Exception {
    // ConfigurationObject configuration = getConfig().getChild("paymentPersistenceConfig");
    //
    // impl.configure(configuration);
    //
    // Payment payment = new Payment();
    // payment.setAmount(BigDecimal.valueOf(100));
    // Map<String, String> parameters = new HashMap<String, String>();
    // parameters.put("key1", "");
    // parameters.put("", "value2");
    // parameters.put("   ", null);
    // parameters.put("key4", "   ");
    // payment.setParameters(parameters);
    // payment.setStatus(PaymentStatus.RESERVED);
    // payment.setTransactionId(null);
    // payment.setAuthorizationId(authorizationId);
    //
    // impl.createPayment(payment);
    //
    // assertTrue("'createPayment' should be correct.", payment.getId() > 0);
    //
    // Payment res = impl.getPayment(payment.getId());
    //
    // assertEquals("'createPayment' should be correct.", 100L, res.getAmount().longValue());
    // assertEquals("'createPayment' should be correct.", 3, res.getParameters().size());
    // assertEquals("'createPayment' should be correct.", "", res.getParameters().get("key1"));
    // assertEquals("'createPayment' should be correct.", "value2", res.getParameters().get(""));
    // assertNull("'createPayment' should be correct.", res.getParameters().get("   "));
    // assertEquals("'createPayment' should be correct.", "   ", res.getParameters().get("key4"));
    // assertEquals("'createPayment' should be correct.", PaymentStatus.RESERVED, res.getStatus());
    // assertNull("'createPayment' should be correct.", res.getTransactionId());
    // assertEquals("'createPayment' should be correct.", authorizationId,
    // res.getAuthorizationId());
    // }

    /**
     * Accuracy test for the method <code>updatePayment(Payment)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testUpdatePayment() throws Exception {
        ConfigurationObject configuration = getConfig().getChild("paymentPersistenceConfig");

        impl.configure(configuration);

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(100));
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("key1", "value1");
        parameters.put("key2", "value2");
        parameters.put("key3", "value3");
        payment.setParameters(parameters);
        payment.setStatus(PaymentStatus.RESERVED);
        payment.setTransactionId("transactionId1");
        payment.setAuthorizationId(authorizationId);

        impl.createPayment(payment);

        assertTrue("'updatePayment' should be correct.", payment.getId() > 0);

        Payment res = impl.getPayment(payment.getId());

        assertEquals("'updatePayment' should be correct.", 100L, res.getAmount().longValue());
        assertEquals("'updatePayment' should be correct.", 3, res.getParameters().size());
        assertEquals("'updatePayment' should be correct.", "value1", res.getParameters()
            .get("key1"));
        assertEquals("'updatePayment' should be correct.", "value2", res.getParameters()
            .get("key2"));
        assertEquals("'updatePayment' should be correct.", "value3", res.getParameters()
            .get("key3"));
        assertEquals("'updatePayment' should be correct.", PaymentStatus.RESERVED, res.getStatus());
        assertEquals("'updatePayment' should be correct.", "transactionId1", res.getTransactionId());
        assertEquals("'updatePayment' should be correct.", authorizationId,
            res.getAuthorizationId());

        payment.setAmount(BigDecimal.valueOf(200));
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setTransactionId("transactionId2");

        impl.updatePayment(payment);

        res = impl.getPayment(payment.getId());

        assertEquals("'updatePayment' should be correct.", 200L, res.getAmount().longValue());
        assertEquals("'updatePayment' should be correct.", 3, res.getParameters().size());
        assertEquals("'updatePayment' should be correct.", "value1", res.getParameters()
            .get("key1"));
        assertEquals("'updatePayment' should be correct.", "value2", res.getParameters()
            .get("key2"));
        assertEquals("'updatePayment' should be correct.", "value3", res.getParameters()
            .get("key3"));
        assertEquals("'updatePayment' should be correct.", PaymentStatus.COMPLETED, res.getStatus());
        assertEquals("'updatePayment' should be correct.", "transactionId2", res.getTransactionId());
        assertEquals("'updatePayment' should be correct.", authorizationId,
            res.getAuthorizationId());
    }

    /**
     * Accuracy test for the method <code>getPayment(long)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetPayment() throws Exception {
        ConfigurationObject configuration = getConfig().getChild("paymentPersistenceConfig");

        impl.configure(configuration);

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(100));
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("key1", "value1");
        parameters.put("key2", "value2");
        parameters.put("key3", "value3");
        payment.setParameters(parameters);
        payment.setStatus(PaymentStatus.RESERVED);
        payment.setTransactionId("transactionId1");
        payment.setAuthorizationId(authorizationId);

        impl.createPayment(payment);

        assertTrue("'createPayment' should be correct.", payment.getId() > 0);

        Payment res = impl.getPayment(payment.getId());

        assertEquals("'getPayment' should be correct.", 100L, res.getAmount().longValue());
        assertEquals("'getPayment' should be correct.", 3, res.getParameters().size());
        assertEquals("'getPayment' should be correct.", "value1", res.getParameters().get("key1"));
        assertEquals("'getPayment' should be correct.", "value2", res.getParameters().get("key2"));
        assertEquals("'getPayment' should be correct.", "value3", res.getParameters().get("key3"));
        assertEquals("'getPayment' should be correct.", PaymentStatus.RESERVED, res.getStatus());
        assertEquals("'getPayment' should be correct.", "transactionId1", res.getTransactionId());
        assertEquals("'getPayment' should be correct.", authorizationId, res.getAuthorizationId());

        res = impl.getPayment(99999L);

        assertNull("'getPayment' should be correct.", res);
    }

    /**
     * Accuracy test for the method <code>createPaymentOperation(PaymentOperation)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCreatePaymentOperation() throws Exception {
        ConfigurationObject configuration = getConfig().getChild("paymentPersistenceConfig");

        impl.configure(configuration);

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(100));
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("key1", "value1");
        parameters.put("key2", "value2");
        parameters.put("key3", "value3");
        payment.setParameters(parameters);
        payment.setStatus(PaymentStatus.RESERVED);
        payment.setTransactionId("transactionId1");
        payment.setAuthorizationId(authorizationId);

        impl.createPayment(payment);

        PaymentOperation paymentOperation = new PaymentOperation();
        paymentOperation.setPaymentId(payment.getId());
        paymentOperation.setRequestId("requestId1");
        paymentOperation.setSuccessful(true);
        paymentOperation.setType(PaymentOperationType.RESERVE);

        impl.createPaymentOperation(paymentOperation);

        assertTrue("'createPaymentOperation' should be correct.", paymentOperation.getId() > 0);

        PaymentOperation paymentOperation2 = new PaymentOperation();
        paymentOperation2.setPaymentId(payment.getId());
        paymentOperation2.setRequestId(null);
        paymentOperation2.setSuccessful(false);
        paymentOperation2.setType(PaymentOperationType.CANCEL);

        impl.createPaymentOperation(paymentOperation2);

        assertTrue("'createPaymentOperation' should be correct.", paymentOperation2.getId() > 0);
    }

}
