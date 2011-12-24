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
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.payments.amazonfps.AmazonPaymentManager;
import com.topcoder.payments.amazonfps.AmazonPaymentManagerImpl;
import com.topcoder.payments.amazonfps.model.Authorization;
import com.topcoder.payments.amazonfps.model.Payment;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationData;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationRequest;
import com.topcoder.payments.amazonfps.model.PaymentDetails;
import com.topcoder.payments.amazonfps.model.PaymentStatus;
import com.topcoder.payments.amazonfps.persistence.db.DatabaseAuthorizationPersistence;
import com.topcoder.payments.amazonfps.persistence.db.DatabasePaymentPersistence;
import com.topcoder.payments.amazonfps.subscribers.PaymentEventSubscriber;
import com.topcoder.payments.amazonfps.subscribers.jms.JMSAmazonPaymentEventSubscriber;

/**
 * <p>
 * Accuracy tests for class <code>AmazonPaymentManagerImpl</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class AmazonPaymentManagerImplAccTest extends BaseAccTest {
    /**
     * Represents the <code>AmazonPaymentManagerImpl</code> instance used to test against.
     */
    private AmazonPaymentManagerImpl impl;

    /**
     * <p>
     * Represents the <code>DatabaseAuthorizationPersistence</code> instance used in tests.
     * </p>
     */
    private DatabaseAuthorizationPersistence authorizationPersistence;
    /**
     * <p>
     * Represents the <code>DatabasePaymentPersistence</code> instance used in tests.
     * </p>
     */
    private DatabasePaymentPersistence paymentPersistence;

    /**
     * Constant used for testing. Represents total future charges threshold.
     */
    private static final BigDecimal TOTAL_CHARGES_THRESHOLD = BigDecimal.valueOf(1000000.0);

    /**
     * Constant used for testing. Represents amount.
     */
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(12.0);

    /**
     * Delay between successive operations on the same payment. When issuing several payment
     * operations in order on the same payment Amazon can refuse one of these operations since the
     * previous one has not finished and some resource is locked. To have some guarantee that tests
     * will run successfully when need this delay constant. This should be increased if for test
     * session failures in payment operations take place. The delay is used only in few places, so
     * in general it takes not too much time.
     */
    private static final long PAYMENT_OPERATION_DELAY = 5000;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(AmazonPaymentManagerImplAccTest.class);
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
        impl = new AmazonPaymentManagerImpl(getConfig());
        authorizationPersistence = (DatabaseAuthorizationPersistence) getField(impl,
            "authorizationPersistence");
        paymentPersistence = (DatabasePaymentPersistence) getField(impl, "paymentPersistence");
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
    }

    /**
     * Inheritance test, verifies <code>AmazonPaymentManagerImpl</code> subclasses should be
     * correct.
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof AmazonPaymentManager);
    }

    /**
     * Accuracy test for the constructor <code>AmazonPaymentManagerImpl()</code>.<br>
     * Instance should be created successfully.
     */
    @Test
    public void testCtor() {
        impl = new AmazonPaymentManagerImpl();
        assertNotNull("Instance should be created", impl);
    }

    /**
     * Accuracy test for the constructor <code>AmazonPaymentManagerImpl(String,
     String)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testCtor2() {
        impl = new AmazonPaymentManagerImpl(
            "test_files/accuracytests/AmazonPaymentManagerImpl.properties",
            AmazonPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
        assertNotNull("Instance should be created", impl);
    }

    /**
     * Accuracy test for the constructor <code>AmazonPaymentManagerImpl(ConfigurationObject)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCtor3() throws Exception {
        impl = new AmazonPaymentManagerImpl(getConfig());
        assertNotNull("Instance should be created", impl);
    }

    /**
     * Accuracy test for the method <code>processAuthorizedPayment(long, PaymentDetails)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testProcessAuthorizedPayment1() throws Exception {
        Authorization authorization = getAuthorization(true);
        authorizationPersistence.createAuthorization(authorization);

        Payment payment = getPayment(authorization.getId());
        paymentPersistence.createPayment(payment);

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAmount(AMOUNT);
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("p1", "v1");
        parameters.put("p2", "v2");
        paymentDetails.setParameters(parameters);
        paymentDetails.setReservation(true);

        long paymentId = impl.processAuthorizedPayment(authorization.getId(), paymentDetails);

        // check that payment failed
        Payment paymentRes = paymentPersistence.getPayment(paymentId);
        assertEquals("Payment status should be RESERVED", PaymentStatus.RESERVED,
            paymentRes.getStatus());

        // check that we spent some money
        Authorization res = authorizationPersistence.getAuthorization(payment.getAuthorizationId());
        BigDecimal newAmount = TOTAL_CHARGES_THRESHOLD.subtract(AMOUNT);
        assertEquals("New amount should be correct", newAmount.longValue(), res
            .getAuthorizedAmountLeft().longValue());
    }

    /**
     * Accuracy test for the method <code>processAuthorizedPayment(long, PaymentDetails)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testProcessAuthorizedPayment2() throws Exception {
        Authorization authorization = getAuthorization(false);
        authorizationPersistence.createAuthorization(authorization);

        PaymentDetails paymentDetails = new PaymentDetails();
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("projectId", "1");
        parameters.put("clientId", "1");
        paymentDetails.setParameters(parameters);
        paymentDetails.setAmount(BigDecimal.valueOf(100));

        long paymentId = impl.processAuthorizedPayment(authorization.getId(), paymentDetails);

        // check that payment failed
        Payment paymentRes = paymentPersistence.getPayment(paymentId);
        assertEquals("Payment status should be FAILED", PaymentStatus.FAILED,
            paymentRes.getStatus());

        Authorization res = authorizationPersistence.getAuthorization(authorization.getId());
        assertNull("'processAuthorizedPayment' should be correct.", res.getTokenId());
    }

    /**
     * Accuracy test for the method <code>processAuthorizedPayment(long, PaymentDetails)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testProcessAuthorizedPayment3() throws Exception {
        Authorization authorization = getAuthorization(true);
        authorizationPersistence.createAuthorization(authorization);

        PaymentDetails paymentDetails = new PaymentDetails();
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("projectId", "1");
        parameters.put("clientId", "1");
        paymentDetails.setParameters(parameters);
        paymentDetails.setAmount(BigDecimal.valueOf(500000));

        long paymentId = impl.processAuthorizedPayment(authorization.getId(), paymentDetails);

        // check that payment failed
        Payment paymentRes = paymentPersistence.getPayment(paymentId);
        assertEquals("Payment status should be FAILED", PaymentStatus.FAILED,
            paymentRes.getStatus());

        Authorization res = authorizationPersistence.getAuthorization(authorization.getId());
        assertEquals("'processAuthorizedPayment' should be correct.", TOKEN_ID, res.getTokenId());
    }

    /**
     * Accuracy test for the method <code>processAuthorizedPayment(long, PaymentDetails)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testProcessAuthorizedPayment4() throws Exception {
        Authorization authorization = getAuthorization(true);
        authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(50));
        authorizationPersistence.createAuthorization(authorization);

        PaymentDetails paymentDetails = new PaymentDetails();
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("projectId", "1");
        parameters.put("clientId", "1");
        paymentDetails.setParameters(parameters);
        paymentDetails.setAmount(BigDecimal.valueOf(100));

        long paymentId = impl.processAuthorizedPayment(authorization.getId(), paymentDetails);

        // check that payment failed
        Payment paymentRes = paymentPersistence.getPayment(paymentId);
        assertEquals("Payment status should be FAILED", PaymentStatus.FAILED,
            paymentRes.getStatus());

        Authorization res = authorizationPersistence.getAuthorization(authorization.getId());
        assertEquals("'processAuthorizedPayment' should be correct.", TOKEN_ID, res.getTokenId());
    }

    // /**
    // * Accuracy test for the method <code>processAuthorizedPayment(long,
    // PaymentDetails)</code>.<br>
    // * Expects no error occurs.
    // *
    // * @throws Exception
    // * to JUnit
    // */
    // @Test
    // public void testProcessAuthorizedPayment5() throws Exception {
    // Authorization authorization = getAuthorization(true);
    // authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(50));
    // authorizationPersistence.createAuthorization(authorization);
    //
    // Payment payment = getPayment(authorization.getId());
    // paymentPersistence.createPayment(payment);
    //
    // PaymentDetails paymentDetails = new PaymentDetails();
    // paymentDetails.setAmount(AMOUNT);
    // Map<String, String> parameters = new HashMap<String, String>();
    // parameters.put("p1", "v1");
    // parameters.put("p2", "v2");
    // paymentDetails.setParameters(parameters);
    //
    // long paymentId = impl.processAuthorizedPayment(authorization.getId(), paymentDetails);
    //
    // // check that payment failed
    // payment = paymentPersistence.getPayment(paymentId);
    // assertEquals("Payment status should be COMPLETED", PaymentStatus.COMPLETED,
    // payment.getStatus());
    //
    // // check that we spent some money
    // Authorization res = authorizationPersistence.getAuthorization(payment.getAuthorizationId());
    // BigDecimal newAmount = TOTAL_CHARGES_THRESHOLD.subtract(AMOUNT);
    // assertEquals("New amount should be correct", newAmount, res.getAuthorizedAmountLeft());
    // }

    /**
     * Accuracy test for the method
     * <code>initiatePaymentAuthorization(PaymentAuthorizationRequest)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testInitiatePaymentAuthorization() throws Exception {
        PaymentAuthorizationRequest request = new PaymentAuthorizationRequest();
        PaymentDetails paymentDetails = new PaymentDetails();
        request.setPaymentDetails(paymentDetails);
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("projectId", "1");
        parameters.put("clientId", "1");
        paymentDetails.setParameters(parameters);
        request.setRedirectUrl("http://www.google.com");
        paymentDetails.setAmount(BigDecimal.valueOf(100));
        request.setFutureChargesFixedAmount(BigDecimal.valueOf(100));
        PaymentAuthorizationData data = impl.initiatePaymentAuthorization(request);

        assertTrue("'initiatePaymentAuthorization' should be correct.",
            data.getAuthorizationId() > 0);
    }

    /**
     * Accuracy test for the method
     * <code>initiatePaymentAuthorization(PaymentAuthorizationRequest)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testInitiatePaymentAuthorization2() throws Exception {
        PaymentAuthorizationRequest request = new PaymentAuthorizationRequest();
        PaymentDetails paymentDetails = new PaymentDetails();
        request.setPaymentDetails(paymentDetails);
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("projectId", "1");
        parameters.put("clientId", "1");
        paymentDetails.setParameters(parameters);
        request.setRedirectUrl("http://www.google.com");
        paymentDetails.setAmount(BigDecimal.valueOf(100));
        request.setFutureChargesFixedAmount(BigDecimal.valueOf(100));
        request.setFutureChargesAuthorizationRequired(true);
        request.setTotalChargesThreshold(BigDecimal.valueOf(1000000.0));
        PaymentAuthorizationData data = impl.initiatePaymentAuthorization(request);

        assertTrue("'initiatePaymentAuthorization' should be correct.",
            data.getAuthorizationId() > 0);
    }

    /**
     * Accuracy test for the method
     * <code>handleRequestFromCoBrandedService(Map&lt;Object,Object&gt;)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testHandleRequestFromCoBrandedService() throws Exception {
        Authorization authorization = getAuthorization(true);
        authorizationPersistence.createAuthorization(authorization);

        Payment payment = getPayment(authorization.getId());
        paymentPersistence.createPayment(payment);

        Map<Object, Object> requestParams = new HashMap<Object, Object>();
        requestParams.put("authorizationId", new String[] { "" + authorization.getId() });
        requestParams.put("paymentId", new String[] { "" + payment.getId() });
        requestParams.put("reserve", new String[] { "0" });
        requestParams.put("status", new String[] { "SA" });
        requestParams.put("tokenID", new String[] { TOKEN_ID });
        requestParams.put("warningMessage", new String[] { "The warning message" });

        impl.handleRequestFromCoBrandedService(requestParams);

        Authorization res = authorizationPersistence.getAuthorization(payment.getAuthorizationId());
        assertEquals("tokenId should not be stored to persistence", TOKEN_ID, res.getTokenId());
    }

    /**
     * Accuracy test for the method
     * <code>handleRequestFromCoBrandedService(Map&lt;Object,Object&gt;)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testHandleRequestFromCoBrandedService2() throws Exception {
        Authorization authorization = getAuthorization(true);
        authorizationPersistence.createAuthorization(authorization);

        Payment payment = getPayment(authorization.getId());
        paymentPersistence.createPayment(payment);

        Map<Object, Object> requestParams = new HashMap<Object, Object>();
        requestParams.put("authorizationId", new String[] { "" + authorization.getId() });
        requestParams.put("paymentId", new String[] { "" + payment.getId() });
        requestParams.put("reserve", new String[] { "0" });
        requestParams.put("status", new String[] { "invalid" });
        requestParams.put("tokenID", new String[] { TOKEN_ID });
        requestParams.put("warningMessage", new String[] { "The warning message" });

        impl.handleRequestFromCoBrandedService(requestParams);

        Authorization res = authorizationPersistence.getAuthorization(payment.getAuthorizationId());
        assertEquals("tokenId should not be stored to persistence", TOKEN_ID, res.getTokenId());
    }

    /**
     * Accuracy test for the method <code>cancelPayment(long)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCancelPayment() throws Exception {
        Authorization authorization = getAuthorization(true);
        authorizationPersistence.createAuthorization(authorization);

        Payment payment = getPayment(authorization.getId());
        paymentPersistence.createPayment(payment);

        impl.cancelPayment(payment.getId());
        Payment res = paymentPersistence.getPayment(payment.getId());

        // check that payment status has not been changed
        assertEquals("payment status should be NOT_INITIATED", PaymentStatus.NOT_INITIATED,
            res.getStatus());
    }

    // /**
    // * Accuracy test for the method <code>cancelPayment(long)</code>.<br>
    // * Expects no error occurs.
    // *
    // * @throws Exception
    // * to JUnit
    // */
    // @Test
    // public void testCancelPayment2() throws Exception {
    // Authorization authorization = getAuthorization(true);
    // authorizationPersistence.createAuthorization(authorization);
    //
    // Payment payment = getPayment(authorization.getId());
    // paymentPersistence.createPayment(payment);
    //
    // PaymentDetails paymentDetails = new PaymentDetails();
    // paymentDetails.setAmount(AMOUNT);
    // Map<String, String> parameters = new HashMap<String, String>();
    // parameters.put("p1", "v1");
    // parameters.put("p2", "v2");
    // paymentDetails.setParameters(parameters);
    // paymentDetails.setReservation(true);
    // long paymentId = impl
    // .processAuthorizedPayment(payment.getAuthorizationId(), paymentDetails);
    //
    // Payment res = paymentPersistence.getPayment(paymentId);
    // assertEquals("payment status should be RESERVED", PaymentStatus.RESERVED, res.getStatus());
    //
    // Thread.sleep(PAYMENT_OPERATION_DELAY);
    // impl.cancelPayment(paymentId);
    //
    // res = paymentPersistence.getPayment(paymentId);
    // assertEquals("payment status should be CANCELLED", PaymentStatus.CANCELLED, res.getStatus());
    // }

    /**
     * Accuracy test for the method <code>settlePayment(long)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSettlePayment() throws Exception {
        Authorization authorization = getAuthorization(true);
        authorizationPersistence.createAuthorization(authorization);

        Payment payment = getPayment(authorization.getId());
        paymentPersistence.createPayment(payment);

        impl.settlePayment(payment.getId());
        Payment res = paymentPersistence.getPayment(payment.getId());

        // check that payment status has not been changed
        assertEquals("payment status should be NOT_INITIATED", PaymentStatus.NOT_INITIATED,
            res.getStatus());
    }

    // /**
    // * Accuracy test for the method <code>settlePayment(long)</code>.<br>
    // * Expects no error occurs.
    // *
    // * @throws Exception
    // * to JUnit
    // */
    // @Test
    // public void testSettlePayment2() throws Exception {
    // Authorization authorization = getAuthorization(true);
    // authorizationPersistence.createAuthorization(authorization);
    //
    // Payment payment = getPayment(authorization.getId());
    // paymentPersistence.createPayment(payment);
    //
    // PaymentDetails paymentDetails = new PaymentDetails();
    // paymentDetails.setAmount(AMOUNT);
    // Map<String, String> parameters = new HashMap<String, String>();
    // parameters.put("p1", "v1");
    // parameters.put("p2", "v2");
    // paymentDetails.setParameters(parameters);
    // paymentDetails.setReservation(true);
    // long paymentId = impl
    // .processAuthorizedPayment(payment.getAuthorizationId(), paymentDetails);
    //
    // Payment res = paymentPersistence.getPayment(paymentId);
    // assertEquals("payment status should be RESERVED", PaymentStatus.RESERVED, res.getStatus());
    //
    // Thread.sleep(PAYMENT_OPERATION_DELAY);
    // impl.settlePayment(paymentId);
    //
    // res = paymentPersistence.getPayment(paymentId);
    // assertEquals("payment status should be COMPLETED", PaymentStatus.COMPLETED, res.getStatus());
    // }
    //
    // /**
    // * THIS TEST CAN BE RUN SEPERATE.<br>
    // * Accuracy test for the method <code>refundPayment(long)</code>.<br>
    // * Expects no error occurs.
    // *
    // * @throws Exception
    // * to JUnit
    // */
    // @Test
    // public void testRefundPayment() throws Exception {
    // Authorization authorization = getAuthorization(true);
    // authorizationPersistence.createAuthorization(authorization);
    //
    // Payment payment = getPayment(authorization.getId());
    // paymentPersistence.createPayment(payment);
    //
    // PaymentDetails paymentDetails = new PaymentDetails();
    // paymentDetails.setAmount(AMOUNT);
    // Map<String, String> parameters = new HashMap<String, String>();
    // parameters.put("p1", "v1");
    // parameters.put("p2", "v2");
    // paymentDetails.setParameters(parameters);
    // paymentDetails.setReservation(true);
    // long paymentId = impl
    // .processAuthorizedPayment(payment.getAuthorizationId(), paymentDetails);
    //
    // Payment res = paymentPersistence.getPayment(paymentId);
    // assertEquals("payment status should be RESERVED", PaymentStatus.RESERVED, res.getStatus());
    //
    // impl.refundPayment(paymentId);
    //
    // // check that payment status has not been changed
    // res = paymentPersistence.getPayment(payment.getId());
    // assertEquals("payment status should be NOT_INITIATED", PaymentStatus.NOT_INITIATED,
    // res.getStatus());
    // }
    //
    //
    // /**
    // * Accuracy test for the method <code>refundPayment(long)</code>.<br>
    // * Expects no error occurs.
    // *
    // * @throws Exception
    // * to JUnit
    // */
    // @Test
    // public void testRefundPayment2() throws Exception {
    // Authorization authorization = getAuthorization(true);
    // authorizationPersistence.createAuthorization(authorization);
    //
    // Payment payment = getPayment(authorization.getId());
    // paymentPersistence.createPayment(payment);
    //
    // PaymentDetails paymentDetails = new PaymentDetails();
    // paymentDetails.setAmount(AMOUNT);
    // Map<String, String> parameters = new HashMap<String, String>();
    // parameters.put("p1", "v1");
    // parameters.put("p2", "v2");
    // paymentDetails.setParameters(parameters);
    // long paymentId = impl
    // .processAuthorizedPayment(payment.getAuthorizationId(), paymentDetails);
    //
    // Payment res = paymentPersistence.getPayment(paymentId);
    // assertEquals("payment status should be COMPLETED", PaymentStatus.COMPLETED, res.getStatus());
    //
    // Thread.sleep(PAYMENT_OPERATION_DELAY);
    // impl.refundPayment(paymentId);
    //
    // Payment res = paymentPersistence.getPayment(paymentId);
    // assertEquals("payment status should be REFUNDED", PaymentStatus.REFUNDED, res.getStatus());
    //
    // // check that payment status has not been changed
    // res = paymentPersistence.getPayment(payment.getId());
    // assertEquals("payment status should be RESERVED", PaymentStatus.RESERVED, res.getStatus());
    // }

    /**
     * Accuracy test for the method <code>cancelAuthorization(long)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCancelAuthorization() throws Exception {
        Authorization authorization = getAuthorization(true);
        authorizationPersistence.createAuthorization(authorization);

        impl.cancelAuthorization(authorization.getId());

        Authorization res = authorizationPersistence.getAuthorization(authorization.getId());

        assertTrue("The authorization's cancelled flag should be true", res.isCancelled());
    }

    /**
     * Accuracy test for the method
     * <code>registerPaymentEventSubscriber(PaymentEventSubscriber)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testRegisterPaymentEventSubscriber() throws Exception {
        PaymentEventSubscriber subscriber = new JMSAmazonPaymentEventSubscriber();
        List<PaymentEventSubscriber> subscribers = (List<PaymentEventSubscriber>) getField(impl,
            "subscribers");
        impl.registerPaymentEventSubscriber(subscriber);
        assertTrue("subscriber was added", subscribers.contains(subscriber));
    }

    /**
     * Creates an instance of Authorization.
     *
     * @param setToken
     *            defines whether to set token id for the authorization
     * @return the Authorization instance.
     */
    private static Authorization getAuthorization(boolean setToken) {
        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(true);
        authorization.setTokenId(setToken ? TOKEN_ID : null);
        authorization.setAuthorizedAmountLeft(TOTAL_CHARGES_THRESHOLD);

        return authorization;
    }

    /**
     * Creates an instance of Payment.
     *
     * @param authorizationId
     *            the authorization id.
     *
     * @return the Payment instance.
     */
    private static Payment getPayment(long authorizationId) {
        Payment payment = new Payment();
        payment.setAuthorizationId(authorizationId);
        payment.setAmount(AMOUNT);
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("p1", "v1");
        parameters.put("p2", "v2");
        payment.setParameters(parameters);
        payment.setTransactionId("1");
        payment.setStatus(PaymentStatus.NOT_INITIATED);

        return payment;
    }

}
