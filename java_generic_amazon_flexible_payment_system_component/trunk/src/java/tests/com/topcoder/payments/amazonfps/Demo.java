/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.payments.amazonfps.model.PaymentAuthorizationData;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationRequest;
import com.topcoder.payments.amazonfps.model.PaymentDetails;

/**
 * <p>
 * This demo shows API usages for Amazon FPS component.
 * </p>
 *
 * @author KennyAlive
 * @version 1.0
 */
public class Demo {
    /**
     * Amazon payment manager used in this demo.
     */
    private AmazonPaymentManager paymentManager;

    /**
     * Represents client's authorization request.
     */
    private PaymentAuthorizationRequest authRequest;


    /**
     * The payment details.
     */
    private PaymentDetails paymentDetails;

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(Demo.class);
    }

    /**
     * Sets up the test environment: set request parameters common for all scenarios.
     * In the real world the configuration parameter should be retrieved from HttpServletRequest object
     */
    @Before
    public void setUp() {
        // instantiate payment manager
        paymentManager = new AmazonPaymentManagerImpl();

        // create authorization request
        authRequest = new PaymentAuthorizationRequest();
        paymentDetails = new PaymentDetails();
        authRequest.setPaymentDetails(paymentDetails);

        // set parameters
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("projectId", "10"); // request.getAttribute("projectId");
        parameters.put("clientId", "102"); // request.getAttribute("clientId");
        paymentDetails.setParameters(parameters);

        // set the redirect URL which will be used when authorization is complete or failed
        authRequest.setRedirectUrl("https://myserver.com/processAuthResult.do");
        paymentDetails.setAmount(BigDecimal.valueOf(100)); // request.getAttribute("amount") ...
    }

    /**
     * The situation when the client clicks 'Pay' button and is immediately charged with specific amount (just once).
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_demoScenario1() throws Exception {
        // initiate request for payment authorization
        PaymentAuthorizationData paymentAuthorizationData
            = paymentManager.initiatePaymentAuthorization(authRequest);

        // store received ids and go to authorization url
        processAuthorizationData(paymentAuthorizationData);
    }

    /**
     * The situation when the client clicks 'Pay' button and is immediately charged with specific amount. Later
     * the client can be charged with fixed authorized amount.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_demoScenario2() throws Exception {
        // configure multi-use payments with fixed charges
        authRequest.setFutureChargesAuthorizationRequired(true);
        authRequest.setFutureChargesFixedAmount(BigDecimal.valueOf(20)); // request.getAttribute("futureAmount")
        authRequest.setTotalChargesThreshold(BigDecimal.valueOf(150)); // request.getAttribute("threshold")

        // initiate request for payment authorization
        PaymentAuthorizationData paymentAuthorizationData
            = paymentManager.initiatePaymentAuthorization(authRequest);

        // store received ids and go to authorization url
        processAuthorizationData(paymentAuthorizationData);
    }

    /**
     * The situation when the client clicks 'Pay' button and is immediately charged with specific amount. Later
     * the client can be charged with variable authorized amount.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_demoScenario3() throws Exception {
        // configure multi-use payments with variable charges
        authRequest.setFutureChargesAuthorizationRequired(true);
        authRequest.setTotalChargesThreshold(BigDecimal.valueOf(5)); // request.getAttribute("threshold")

        // initiate request for payment authorization
        PaymentAuthorizationData paymentAuthorizationData
            = paymentManager.initiatePaymentAuthorization(authRequest);

        // store received ids and go to authorization url
        processAuthorizationData(paymentAuthorizationData);
    }

    /**
     * The payment amount is reserved for the client immediately. Later the reservation can be settled or cancelled.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_demoScenario4() throws Exception {
        // Request reservation, not payment
        paymentDetails.setReservation(true);

        // initiate request for reservation authorization
        PaymentAuthorizationData paymentAuthorizationData
            = paymentManager.initiatePaymentAuthorization(authRequest);

        // store received ids and go to authorization url
        processAuthorizationData(paymentAuthorizationData);
    }

    /**
     * The following steps demonstrate what we can do with received authorization data.
     *
     * @param paymentAuthorizationData
     *              the authorization data
     */
    private void processAuthorizationData(PaymentAuthorizationData paymentAuthorizationData) {
        @SuppressWarnings("unused")
        long authorizationId = paymentAuthorizationData.getAuthorizationId();

        @SuppressWarnings("unused")
        long paymentId = paymentAuthorizationData.getPaymentId();
        // save paymentId and authorizationId, e.g. to project or contest

        String authorizationUrl = paymentAuthorizationData.getAuthorizationUrl();
        // send the client to the authorization page: response.sendRedirect(authorizationUrl);
        System.out.println("AuthorizationUrl: " + authorizationUrl);
    }
}
