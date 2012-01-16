/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.failuretests;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentManagementException;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException;
import com.topcoder.payments.amazonfps.AmazonPaymentManagerImpl;
import com.topcoder.payments.amazonfps.AuthorizationNotFoundException;
import com.topcoder.payments.amazonfps.PaymentNotFoundException;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationRequest;
import com.topcoder.payments.amazonfps.model.PaymentDetails;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for AmazonPaymentManagerImpl.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class AmazonPaymentManagerImplFailureTests extends TestCase {

    /**
     * <p>
     * Represent the namespace for testing.
     * </p>
     */
    private static final String NAMESPACE = "com.topcoder.payments.amazonfps.AmazonPaymentManagerImpl";

    /**
     * <p>
     * Represent the AmazonPaymentManagerImpl instance for testing.
     * </p>
     */
    private AmazonPaymentManagerImpl instance;

    /**
     * <p>
     * Represent the ConfigurationObject instance for testing.
     * </p>
     */
    private ConfigurationObject configuration;

    /**
     * <p>
     * Represent the PaymentAuthorizationRequest instance for testing.
     * </p>
     */
    private PaymentAuthorizationRequest request;

    /**
     * <p>
     * Represent the requestParams map for testing.
     * </p>
     */
    private Map<Object, Object> requestParams;

    /**
     * <p>
     * Represent the PaymentDetails instance for testing.
     * </p>
     */
    private PaymentDetails paymentDetails;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        configuration = FailureTestHelper.getConfig(NAMESPACE);
        instance = new AmazonPaymentManagerImpl(configuration);

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("projectId", "1");
        paymentDetails = new PaymentDetails();
        paymentDetails.setParameters(parameters);
        paymentDetails.setAmount(BigDecimal.valueOf(100));

        request = new PaymentAuthorizationRequest();
        request.setPaymentDetails(paymentDetails);
        request.setRedirectUrl("http://www.topcoder.com/");
        request.setFutureChargesFixedAmount(BigDecimal.valueOf(100));

        requestParams = new HashMap<Object, Object>();
        requestParams.put("authorizationId", new String[] {"1"});
        requestParams.put("paymentId", new String[] {"1"});
        requestParams.put("reserve", new String[] {"0"});
        requestParams.put("status", new String[] {"SA"});
        requestParams.put("tokenID", new String[] {"2"});
        requestParams.put("warningMessage", new String[] {"message"});
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AmazonPaymentManagerImplFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(String,String) for failure.
     * It tests the case that when filePath is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullFilePath() {
        try {
            new AmazonPaymentManagerImpl(null, NAMESPACE);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(String,String) for failure.
     * It tests the case that when filePath is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyFilePath() {
        try {
            new AmazonPaymentManagerImpl(" ", NAMESPACE);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(String,String) for failure.
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullNamespace() {
        try {
            new AmazonPaymentManagerImpl("test_files", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(String,String) for failure.
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyNamespace() {
        try {
            new AmazonPaymentManagerImpl("test_files", " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(ConfigurationObject) for failure.
     * It tests the case that when configuration is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NullConfiguration() {
        try {
            new AmazonPaymentManagerImpl(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(ConfigurationObject) for failure.
     * It tests the case that when loggerName is empty and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_EmptyloggerName() throws Exception {
        configuration.setPropertyValue("loggerName", " ");
        try {
            new AmazonPaymentManagerImpl(configuration);
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(ConfigurationObject) for failure.
     * It tests the case that when objectFactoryConfig is null and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_NullobjectFactoryConfig() throws Exception {
        configuration.removeChild("objectFactoryConfig");
        try {
            new AmazonPaymentManagerImpl(configuration);
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(ConfigurationObject) for failure.
     * It tests the case that when authorizationPersistenceKey is null and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_NullauthorizationPersistenceKey() throws Exception {
        configuration.removeProperty("authorizationPersistenceKey");
        try {
            new AmazonPaymentManagerImpl(configuration);
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(ConfigurationObject) for failure.
     * It tests the case that when authorizationPersistenceKey is empty and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_EmptyauthorizationPersistenceKey() throws Exception {
        configuration.setPropertyValue("authorizationPersistenceKey", " ");
        try {
            new AmazonPaymentManagerImpl(configuration);
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(ConfigurationObject) for failure.
     * It tests the case that when authorizationPersistenceKey is invalid and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_InvalidauthorizationPersistenceKey() throws Exception {
        configuration.setPropertyValue("authorizationPersistenceKey", "invalid");
        try {
            new AmazonPaymentManagerImpl(configuration);
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(ConfigurationObject) for failure.
     * It tests the case that when authorizationPersistenceConfig is null and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_NullauthorizationPersistenceConfig() throws Exception {
        configuration.removeChild("authorizationPersistenceConfig");
        try {
            new AmazonPaymentManagerImpl(configuration);
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(ConfigurationObject) for failure.
     * It tests the case that when awsAccessKey is null and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_NullawsAccessKey() throws Exception {
        configuration.removeProperty("awsAccessKey");
        try {
            new AmazonPaymentManagerImpl(configuration);
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(ConfigurationObject) for failure.
     * It tests the case that when awsAccessKey is empty and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_EmptyawsAccessKey() throws Exception {
        configuration.setPropertyValue("awsAccessKey", " ");
        try {
            new AmazonPaymentManagerImpl(configuration);
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(ConfigurationObject) for failure.
     * It tests the case that when awsSecretKey is null and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_NullawsSecretKey() throws Exception {
        configuration.removeProperty("awsSecretKey");
        try {
            new AmazonPaymentManagerImpl(configuration);
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(ConfigurationObject) for failure.
     * It tests the case that when awsSecretKey is empty and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_EmptyawsSecretKey() throws Exception {
        configuration.setPropertyValue("awsSecretKey", " ");
        try {
            new AmazonPaymentManagerImpl(configuration);
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(ConfigurationObject) for failure.
     * It tests the case that when paymentMethods is null and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_NullpaymentMethods() throws Exception {
        configuration.removeProperty("paymentMethods");
        try {
            new AmazonPaymentManagerImpl(configuration);
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AmazonPaymentManagerImpl#AmazonPaymentManagerImpl(ConfigurationObject) for failure.
     * It tests the case that when paymentMethods is empty and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_EmptypaymentMethods() throws Exception {
        configuration.setPropertyValue("paymentMethods", " ");
        try {
            new AmazonPaymentManagerImpl(configuration);
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#initiatePaymentAuthorization(PaymentAuthorizationRequest) for failure.
     * It tests the case that when request is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testInitiatePaymentAuthorization_NullRequest() throws Exception {
        try {
            instance.initiatePaymentAuthorization(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#initiatePaymentAuthorization(PaymentAuthorizationRequest) for failure.
     * It tests the case that when request.getRedirectUrl() is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testInitiatePaymentAuthorization_NullRedirectUrl() throws Exception {
        request.setRedirectUrl(null);
        try {
            instance.initiatePaymentAuthorization(request);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#initiatePaymentAuthorization(PaymentAuthorizationRequest) for failure.
     * It tests the case that when request.getRedirectUrl() is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testInitiatePaymentAuthorization_EmptyRedirectUrl() throws Exception {
        request.setRedirectUrl(" ");
        try {
            instance.initiatePaymentAuthorization(request);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#initiatePaymentAuthorization(PaymentAuthorizationRequest) for failure.
     * It tests the case that when request.getPaymentDetails() is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testInitiatePaymentAuthorization_NullPaymentDetails() throws Exception {
        request.setPaymentDetails(null);
        try {
            instance.initiatePaymentAuthorization(request);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#initiatePaymentAuthorization(PaymentAuthorizationRequest) for failure.
     * It tests the case that when  request.getPaymentDetails().getAmount() is null
     * and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testInitiatePaymentAuthorization_NullAmount() throws Exception {
        request.getPaymentDetails().setAmount(null);
        try {
            instance.initiatePaymentAuthorization(request);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#initiatePaymentAuthorization(PaymentAuthorizationRequest) for failure.
     * It tests the case that when  request.getPaymentDetails().getAmount() is not positive
     * and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testInitiatePaymentAuthorization_ZeroAmount() throws Exception {
        request.getPaymentDetails().setAmount(BigDecimal.valueOf(0));
        try {
            instance.initiatePaymentAuthorization(request);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#initiatePaymentAuthorization(PaymentAuthorizationRequest) for failure.
     * It tests the case that when  request.getPaymentDetails().getParameters() contains null key
     * and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testInitiatePaymentAuthorization_NullInParameters() throws Exception {
        request.getPaymentDetails().getParameters().put(null, "value");
        try {
            instance.initiatePaymentAuthorization(request);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#initiatePaymentAuthorization(PaymentAuthorizationRequest) for failure.
     * It tests the case that when request.isFutureChargesAuthorizationRequired() and
     * request.getTotalChargesThreshold() is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testInitiatePaymentAuthorization_NullTotalChargesThreshold() throws Exception {
        request.setFutureChargesAuthorizationRequired(true);
        try {
            instance.initiatePaymentAuthorization(request);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#initiatePaymentAuthorization(PaymentAuthorizationRequest) for failure.
     * It tests the case that when request.getTotalChargesThreshold() != null and request.getTotalChargesThreshold()
     * is not positive and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testInitiatePaymentAuthorization_ZeroTotalChargesThreshold() throws Exception {
        request.setTotalChargesThreshold(BigDecimal.valueOf(0));
        try {
            instance.initiatePaymentAuthorization(request);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#initiatePaymentAuthorization(PaymentAuthorizationRequest) for failure.
     * It tests the case that when request.getFutureChargesFixedAmount() != null and
     * request.getFutureChargesFixedAmount() is not positive and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testInitiatePaymentAuthorization_ZeroFutureChargesFixedAmount() throws Exception {
        request.setFutureChargesFixedAmount(BigDecimal.valueOf(0));
        try {
            instance.initiatePaymentAuthorization(request);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#initiatePaymentAuthorization(PaymentAuthorizationRequest) for failure.
     * Expects AmazonFlexiblePaymentManagementException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testInitiatePaymentAuthorization_AmazonFlexiblePaymentManagementException() throws Exception {
        configuration.getChild("authorizationPersistenceConfig").setPropertyValue("connectionName", "invalid");
        instance = new AmazonPaymentManagerImpl(configuration);
        try {
            instance.initiatePaymentAuthorization(request);
            fail("AmazonFlexiblePaymentManagementException expected.");
        } catch (AmazonFlexiblePaymentManagementException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#handleRequestFromCoBrandedService(Map) for failure.
     * It tests the case that when requestParams is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testHandleRequestFromCoBrandedService_NullRequestParams() throws Exception {
        try {
            instance.handleRequestFromCoBrandedService(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#handleRequestFromCoBrandedService(Map) for failure.
     * It tests the case that when authorizationId is null and expects AmazonFlexiblePaymentManagementException.
     * </p>
     */
    public void testHandleRequestFromCoBrandedService_NullauthorizationId() {
        requestParams.remove("authorizationId");
        try {
            instance.handleRequestFromCoBrandedService(requestParams);
            fail("AmazonFlexiblePaymentManagementException expected.");
        } catch (AmazonFlexiblePaymentManagementException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#handleRequestFromCoBrandedService(Map) for failure.
     * It tests the case that when authorizationId is empty and expects AmazonFlexiblePaymentManagementException.
     * </p>
     */
    public void testHandleRequestFromCoBrandedService_EmptyauthorizationId() {
        requestParams.put("authorizationId", " ");
        try {
            instance.handleRequestFromCoBrandedService(requestParams);
            fail("AmazonFlexiblePaymentManagementException expected.");
        } catch (AmazonFlexiblePaymentManagementException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#handleRequestFromCoBrandedService(Map) for failure.
     * It tests the case that when authorizationId is invalid and expects AmazonFlexiblePaymentManagementException.
     * </p>
     */
    public void testHandleRequestFromCoBrandedService_InvalidauthorizationId() {
        requestParams.put("authorizationId", "invalid");
        try {
            instance.handleRequestFromCoBrandedService(requestParams);
            fail("AmazonFlexiblePaymentManagementException expected.");
        } catch (AmazonFlexiblePaymentManagementException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#handleRequestFromCoBrandedService(Map) for failure.
     * It tests the case that when authorizationId is zero and expects AmazonFlexiblePaymentManagementException.
     * </p>
     */
    public void testHandleRequestFromCoBrandedService_ZeroauthorizationId() {
        requestParams.put("authorizationId", "0");
        try {
            instance.handleRequestFromCoBrandedService(requestParams);
            fail("AmazonFlexiblePaymentManagementException expected.");
        } catch (AmazonFlexiblePaymentManagementException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#handleRequestFromCoBrandedService(Map) for failure.
     * It tests the case that when reserve is null and expects AmazonFlexiblePaymentManagementException.
     * </p>
     */
    public void testHandleRequestFromCoBrandedService_Nullreserve() {
        requestParams.remove("reserve");
        try {
            instance.handleRequestFromCoBrandedService(requestParams);
            fail("AmazonFlexiblePaymentManagementException expected.");
        } catch (AmazonFlexiblePaymentManagementException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#handleRequestFromCoBrandedService(Map) for failure.
     * It tests the case that when reserve is empty and expects AmazonFlexiblePaymentManagementException.
     * </p>
     */
    public void testHandleRequestFromCoBrandedService_Emptyreserve() {
        requestParams.put("reserve", new String[] {});
        try {
            instance.handleRequestFromCoBrandedService(requestParams);
            fail("AmazonFlexiblePaymentManagementException expected.");
        } catch (AmazonFlexiblePaymentManagementException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#handleRequestFromCoBrandedService(Map) for failure.
     * It tests the case that when reserve is invalid and expects AmazonFlexiblePaymentManagementException.
     * </p>
     */
    public void testHandleRequestFromCoBrandedService_Invalidreserve() {
        requestParams.put("reserve", new String[] {"2"});
        try {
            instance.handleRequestFromCoBrandedService(requestParams);
            fail("AmazonFlexiblePaymentManagementException expected.");
        } catch (AmazonFlexiblePaymentManagementException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#processAuthorizedPayment(long,PaymentDetails) for failure.
     * It tests the case that when authorizationId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testProcessAuthorizedPayment_ZeroauthorizationId() throws Exception {
        try {
            instance.processAuthorizedPayment(0, paymentDetails);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#processAuthorizedPayment(long,PaymentDetails) for failure.
     * It tests the case that when paymentDetails is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testProcessAuthorizedPayment_NullPaymentDetails() throws Exception {
        try {
            instance.processAuthorizedPayment(1, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#processAuthorizedPayment(long,PaymentDetails) for failure.
     * It tests the case that when paymentDetails.getAmount() is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testProcessAuthorizedPayment_NullAmount() throws Exception {
        paymentDetails.setAmount(null);
        try {
            instance.processAuthorizedPayment(1, paymentDetails);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#processAuthorizedPayment(long,PaymentDetails) for failure.
     * It tests the case that when paymentDetails.getAmount() is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testProcessAuthorizedPayment_ZeroAmount() throws Exception {
        paymentDetails.setAmount(BigDecimal.valueOf(0));
        try {
            instance.processAuthorizedPayment(1, paymentDetails);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#processAuthorizedPayment(long,PaymentDetails) for failure.
     * It tests the case that when paymentDetails.getParameters() contains null key and
     * expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testProcessAuthorizedPayment_NullInParameters() throws Exception {
        paymentDetails.getParameters().put(null, "value");
        try {
            instance.processAuthorizedPayment(1, paymentDetails);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#processAuthorizedPayment(long,PaymentDetails) for failure.
     * Expects AmazonFlexiblePaymentManagementException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testProcessAuthorizedPayment_AmazonFlexiblePaymentManagementException() throws Exception {
        try {
            instance.processAuthorizedPayment(1, paymentDetails);
            fail("AmazonFlexiblePaymentManagementException expected.");
        } catch (AmazonFlexiblePaymentManagementException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#cancelPayment(long) for failure.
     * It tests the case that when paymentId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCancelPayment_ZeroPaymentId() throws Exception {
        try {
            instance.cancelPayment(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#cancelPayment(long) for failure.
     * It tests the case that when paymentId is not found and expects PaymentNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCancelPayment_NotFoundPaymentId() throws Exception {
        try {
            instance.cancelPayment(1);
            fail("PaymentNotFoundException expected.");
        } catch (PaymentNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#cancelPayment(long) for failure.
     * Expects AmazonFlexiblePaymentManagementException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCancelPayment_AmazonFlexiblePaymentManagementException() throws Exception {
        configuration.getChild("authorizationPersistenceConfig").setPropertyValue("connectionName", "invalid");
        instance = new AmazonPaymentManagerImpl(configuration);
        try {
            instance.cancelPayment(1);
            fail("AmazonFlexiblePaymentManagementException expected.");
        } catch (AmazonFlexiblePaymentManagementException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#settlePayment(long) for failure.
     * It tests the case that when paymentId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testSettlePayment_ZeroPaymentId() throws Exception {
        try {
            instance.settlePayment(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#settlePayment(long) for failure.
     * It tests the case that when paymentId is not found and expects PaymentNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testSettlePayment_NotFoundPaymentId() throws Exception {
        try {
            instance.settlePayment(1);
            fail("PaymentNotFoundException expected.");
        } catch (PaymentNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#settlePayment(long) for failure.
     * Expects AmazonFlexiblePaymentManagementException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testSettlePayment_AmazonFlexiblePaymentManagementException() throws Exception {
        configuration.getChild("authorizationPersistenceConfig").setPropertyValue("connectionName", "invalid");
        instance = new AmazonPaymentManagerImpl(configuration);
        try {
            instance.settlePayment(1);
            fail("AmazonFlexiblePaymentManagementException expected.");
        } catch (AmazonFlexiblePaymentManagementException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#refundPayment(long) for failure.
     * It tests the case that when paymentId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testRefundPayment_ZeroPaymentId() throws Exception {
        try {
            instance.refundPayment(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#refundPayment(long) for failure.
     * It tests the case that when paymentId is not found and expects PaymentNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testRefundPayment_NotFoundPaymentId() throws Exception {
        try {
            instance.refundPayment(1);
            fail("PaymentNotFoundException expected.");
        } catch (PaymentNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#refundPayment(long) for failure.
     * Expects AmazonFlexiblePaymentManagementException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testRefundPayment_AmazonFlexiblePaymentManagementException() throws Exception {
        configuration.getChild("authorizationPersistenceConfig").setPropertyValue("connectionName", "invalid");
        instance = new AmazonPaymentManagerImpl(configuration);
        try {
            instance.refundPayment(1);
            fail("AmazonFlexiblePaymentManagementException expected.");
        } catch (AmazonFlexiblePaymentManagementException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#cancelAuthorization(long) for failure.
     * It tests the case that when authorizationId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCancelAuthorization_ZeroPaymentId() throws Exception {
        try {
            instance.cancelAuthorization(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#cancelAuthorization(long) for failure.
     * It tests the case that when authorizationId is not found and expects AuthorizationNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCancelAuthorization_NotFoundPaymentId() throws Exception {
        try {
            instance.cancelAuthorization(1);
            fail("AuthorizationNotFoundException expected.");
        } catch (AuthorizationNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#cancelAuthorization(long) for failure.
     * Expects AmazonFlexiblePaymentManagementException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCancelAuthorization_AmazonFlexiblePaymentManagementException() throws Exception {
        configuration.getChild("authorizationPersistenceConfig").setPropertyValue("connectionName", "invalid");
        instance = new AmazonPaymentManagerImpl(configuration);
        try {
            instance.cancelAuthorization(1);
            fail("AmazonFlexiblePaymentManagementException expected.");
        } catch (AmazonFlexiblePaymentManagementException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests AmazonPaymentManagerImpl#registerPaymentEventSubscriber(PaymentEventSubscriber) for failure.
     * It tests the case that when subscriber is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testRegisterPaymentEventSubscriber_NullSubscriber() throws Exception {
        try {
            instance.registerPaymentEventSubscriber(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}