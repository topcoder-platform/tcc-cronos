/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.amazonaws.cbui.AmazonFPSMultiUsePipeline;
import com.amazonaws.cbui.AmazonFPSSingleUsePipeline;
import com.amazonaws.fps.AmazonFPSClient;
import com.amazonaws.fps.AmazonFPSException;
import com.amazonaws.fps.model.Amount;
import com.amazonaws.fps.model.CancelRequest;
import com.amazonaws.fps.model.CancelResponse;
import com.amazonaws.fps.model.CancelTokenRequest;
import com.amazonaws.fps.model.CurrencyCode;
import com.amazonaws.fps.model.PayRequest;
import com.amazonaws.fps.model.PayResponse;
import com.amazonaws.fps.model.PayResult;
import com.amazonaws.fps.model.RefundRequest;
import com.amazonaws.fps.model.RefundResponse;
import com.amazonaws.fps.model.ReserveRequest;
import com.amazonaws.fps.model.ReserveResponse;
import com.amazonaws.fps.model.ReserveResult;
import com.amazonaws.fps.model.SettleRequest;
import com.amazonaws.fps.model.SettleResponse;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationPersistenceException;
import com.topcoder.payments.amazonfps.model.Authorization;
import com.topcoder.payments.amazonfps.model.Payment;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationData;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationRequest;
import com.topcoder.payments.amazonfps.model.PaymentDetails;
import com.topcoder.payments.amazonfps.model.PaymentEvent;
import com.topcoder.payments.amazonfps.model.PaymentEventType;
import com.topcoder.payments.amazonfps.model.PaymentOperation;
import com.topcoder.payments.amazonfps.model.PaymentOperationType;
import com.topcoder.payments.amazonfps.model.PaymentStatus;
import com.topcoder.payments.amazonfps.persistence.AuthorizationPersistence;
import com.topcoder.payments.amazonfps.persistence.AuthorizationPersistenceException;
import com.topcoder.payments.amazonfps.persistence.PaymentPersistence;
import com.topcoder.payments.amazonfps.persistence.PaymentPersistenceException;
import com.topcoder.payments.amazonfps.subscribers.ConfigurablePaymentEventSubscriber;
import com.topcoder.payments.amazonfps.subscribers.PaymentEventSubscriber;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * The {@code AmazonPaymentManagerImpl} class is an implementation of {@code AmazonPaymentManager} that uses pluggable
 * persistence implementations to store authorization and payment data. It uses <i>Amazon FPS Java Library</i> for
 * accessing Amazon services. This class uses <i>Logging Wrapper</i> component to log errors and debug information.
 * This class throws exceptions specific to persistence and illegal usage, all other errors specific to authorization
 * and payment rejection/failure are reported via the corresponding payment events.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em> <br/>
 * For sample configuration, please, examine the following sample configuration file:
 * <b>test_files/test_configs_amazon_payment_manager/AmazonPaymentManagerImpl.xml</b>
 * </p>
 *
 * <p>
 * <em>Sample Usage:</em>
 * <pre>
 * {@code
 * // instantiate payment manager
 * AmazonPaymentManager paymentManager = new AmazonPaymentManagerImpl();
 *
 * // create authorization request
 * authRequest = new PaymentAuthorizationRequest();
 * paymentDetails = new PaymentDetails();
 * authRequest.setPaymentDetails(paymentDetails);
 *
 * // set parameters
 * Map<String, String> parameters = new HashMap<String, String>();
 * parameters.put("projectId", request.getAttribute("projectId"));
 * parameters.put("clientId", "request.getAttribute("clientId"));
 * paymentDetails.setParameters(parameters);
 *
 * // set the redirect URL which will be used when authorization is complete or failed
 * authRequest.setRedirectUrl("https://myserver.com/processAuthResult.do");
 * paymentDetails.setAmount(BigDecimal.valueOf(100));
 *
 * // initiate request for payment authorization
 * PaymentAuthorizationData paymentAuthorizationData = paymentManager.initiatePaymentAuthorization(authRequest);
 *
 * // store ids and authorization url
 * long authorizationId = paymentAuthorizationData.getAuthorizationId();
 * long paymentId = paymentAuthorizationData.getPaymentId();
 * String authorizationUrl = paymentAuthorizationData.getAuthorizationUrl();
 * }
 * </pre>
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is thread safe when collections and entities passed to it are used
 * by the caller in thread safe manner. This class holds a mutable subscribers collection. Thus all access to the
 * content of this collection is synchronized.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public class AmazonPaymentManagerImpl implements AmazonPaymentManager {
    /**
     * The default configuration file path for this class.
     */
    public static final String DEFAULT_CONFIG_FILENAME =
            "com/topcoder/payments/amazonfps/AmazonPaymentManagerImpl.properties";
    /**
     * The default configuration namespace for this class.
     */
    public static final String DEFAULT_CONFIG_NAMESPACE =
            "com.topcoder.payments.amazonfps.AmazonPaymentManagerImpl";

    //-------- Constants for parameters names -----------
    /**
     * Constant for objectFactoryConfig child property name.
     */
    private static final String OBJECT_FACTORY_CONFIG_CHILD = "objectFactoryConfig";
    /**
     * Constant for authorizationPersistenceKey parameter name.
     */
    private static final String AUTHORIZATION_PERSISTENCE_KEY_PARAMETER = "authorizationPersistenceKey";
    /**
     * Constant for authorizationPersistenceConfig child property name.
     */
    private static final String AUTHORIZATION_PERSISTENCE_CONFIG_CHILD = "authorizationPersistenceConfig";
    /**
     * Constant for paymentPersistenceKey parameter name.
     */
    private static final String PAYMENT_PERSISTENCE_KEY_PARAMETER = "paymentPersistenceKey";
    /**
     * Constant for paymentPersistenceConfig child property name.
     */
    private static final String PAYMENT_PERSISTENCE_CONFIG_CHILD = "paymentPersistenceConfig";
    /**
     * Constant for paymentEventSubscriber child property prefix.
     */
    private static final String PAYMENT_EVENT_SUBSCRIBER_CHILD_PREFIX = "paymentEventSubscriber";
    /**
     * Constant for subscriberKey parameter name.
     */
    private static final String SUBSCRIBER_KEY_PARAMETER = "subscriberKey";
    /**
     * Constant for subscriberConfig child property name.
     */
    private static final String SUBSCRIBER_CONFIG_CHILD = "subscriberConfig";
    /**
     * Constant for awsAccessKey parameter name.
     */
    private static final String AWS_ACCESS_KEY_PARAMETER = "awsAccessKey";
    /**
     * Constant for awsSecretKey parameter name.
     */
    private static final String AWS_SECRET_KEY_PARAMETER = "awsSecretKey";
    /**
     * Constant for paymentMethods parameter name.
     */
    private static final String PAYMENT_METHODS_PARAMETER = "paymentMethods";

    //-------- Constants for request statuses -----------
    /**
     * Constant for SA request status.
     */
    private static final String REQUEST_STATUS_SA = "SA";
    /**
     * Constant for SB request status.
     */
    private static final String REQUEST_STATUS_SB = "SB";
    /**
     * Constant for SB request status.
     */
    private static final String REQUEST_STATUS_SC = "SC";

    //-------- Constants for payment methods -----------
    /**
     * Constant for CC payment method.
     */
    private static final String PAYMENT_METHOD_CC = "CC";
    /**
     * Constant for ACH payment method.
     */
    private static final String PAYMENT_METHOD_ACH = "ACH";
    /**
     * Constant for ABT payment method.
     */
    private static final String PAYMENT_METHOD_ABT = "ABT";

    //-------- Constants for error types of payment events -----------
    /**
     * Constant for TokenNotActive_Sender error type.
     */
    private static final String ERROR_TYPE_TOKEN_NOT_ACTIVE_SENDER = "TokenNotActive_Sender";
    /**
     * Constant for AccountLimitsExceeded error type.
     */
    private static final String ERROR_TYPE_ACCOUNT_LIMITS_EXCEEDED = "AccountLimitsExceeded";
    /**
     * Constant for AmountOutOfRange error type.
     */
    private static final String ERROR_TYPE_AMOUNT_OUT_OF_RANGE = "AmountOutOfRange";
    /**
     * Constant for InvalidTransactionState error type.
     */
    private static final String ERROR_TYPE_INVALID_TRANSACTION_STATE = "InvalidTransactionState";
    /**
     * Constant for AuthFailure error type.
     */
    private static final String ERROR_TYPE_AUTH_FAILURE = "AuthFailure";

    /**
     * Represents the class name.
     */
    private static final String CLASS_NAME = AmazonPaymentManagerImpl.class.getName();

    /**
     * The authorization persistence that is used by this class for storing and retrieving authorization specific data.
     * It is initialized in the constructor and never changed after that. Cannot be {@code null}. It is used in
     * initiatePaymentAuthorization(), handleRequestFromCoBrandedService(), processAuthorizedPayment(),
     * cancelPayment(), refundPayment() and cancelAuthorization().
     */
    private final AuthorizationPersistence authorizationPersistence;

    /**
     * The payment persistence that is used by this class for storing and retrieving payment specific data. It is
     * initialized in the constructor and never changed after that. Cannot be {@code null}. It is used in
     * initiatePaymentAuthorization(), handleRequestFromCoBrandedService(), processAuthorizedPayment(),
     * cancelPayment(), settlePayment() and refundPayment().
     */
    private final PaymentPersistence paymentPersistence;

    /**
     * The access key used when accessing Amazon services. It is initialized in the constructor and never changed
     * after that. Cannot be {@code null} or empty. It is used in initiatePaymentAuthorization(),
     * handleRequestFromCoBrandedService(), processAuthorizedPayment(), cancelPayment(), settlePayment(),
     * refundPayment() and cancelAuthorization().
     */
    private final String awsAccessKey;

    /**
     * The secret key used for signing the requests to Amazon services. It is initialized in the constructor and never
     * changed after that. Cannot be {@code null} or empty. It is used in initiatePaymentAuthorization(),
     * handleRequestFromCoBrandedService(), processAuthorizedPayment(), cancelPayment(), settlePayment(),
     * refundPayment() and cancelAuthorization().
     */
    private final String awsSecretKey;

    /**
     * The supported payment methods. Should be a comma-separated list of any of the following values:
     * <ul>
     * <li>CC</li>
     * <li>ACH</li>
     * <li>ABT</li>
     * </ul>
     *
     *  It is initialized in the constructor and never changed after that. Cannot be {@code null} or empty.
     *  It is used in initiatePaymentAuthorization().
     */
    private final String paymentMethods;

    /**
     * The list of registered payment event subscribers. Collection instance is initialized in the constructor and
     * never changed after that. Cannot be {@code null}, cannot contain {@code null}. It is used in
     * registerPaymentEventSubscriber() and processPaymentEvent().
     */
    private final List<PaymentEventSubscriber> subscribers;

    /**
     * The logger used for logging errors and debug information. It is initialized in the constructor and never changed
     * after that. If it is {@code null}, logging is not performed. It is used in all public business methods.
     */
    private final Log log;

    /**
     * Creates an instance of {@code AmazonPaymentManagerImpl} and initializes it from the default configuration file.
     *
     * @throws AmazonFlexiblePaymentSystemComponentConfigurationException
     *            if error occurred while reading the configuration or initializing this class.
     */
    public AmazonPaymentManagerImpl() {
        this(DEFAULT_CONFIG_FILENAME, DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * Creates an instance of {@code AmazonPaymentManagerImpl} and initializes it from the specified configuration
     * file.
     *
     * @param filePath
     *            the path of the configuration file
     * @param namespace
     *            the configuration namespace
     *
     * @throws IllegalArgumentException
     *            if filePath or namespace is {@code null}/empty
     * @throws AmazonFlexiblePaymentSystemComponentConfigurationException
     *            if error occurred while reading the configuration or initializing this class
     */
    public AmazonPaymentManagerImpl(String filePath, String namespace) {
        // NOTE: here we can not check parameters for null/empty values because the first statement
        // should be constructor invocation, but we do this check in getConfiguration() method.
        this(getConfiguration(filePath, namespace));
    }

    /**
     * Creates an instance of {@code AmazonPaymentManagerImpl} and initializes it from the given configuration object.
     *
     * @param configuration
     *            the configuration object for this class
     *
     * @throws IllegalArgumentException
     *            if configuration is {@code null}
     * @throws AmazonFlexiblePaymentSystemComponentConfigurationException
     *            if error occurred while reading the configuration or initializing this class
     */
    public AmazonPaymentManagerImpl(ConfigurationObject configuration) {
        ParameterCheckUtility.checkNotNull(configuration, "configuration");
        try {
            log = Helper.getLog(configuration);

            ObjectFactory objectFactory = createObjectFactory(configuration);
            authorizationPersistence = createAuthorizationPersistence(objectFactory, configuration);
            paymentPersistence = createPaymentPersistence(objectFactory, configuration);
            subscribers = createSubscribers(objectFactory, configuration);

            awsAccessKey = Helper.getProperty(configuration, AWS_ACCESS_KEY_PARAMETER, true);
            awsSecretKey = Helper.getProperty(configuration, AWS_SECRET_KEY_PARAMETER, true);
            paymentMethods = Helper.getProperty(configuration, PAYMENT_METHODS_PARAMETER, true);

            String[] methods = paymentMethods.split(",");
            for (String method : methods) {
                method = method.trim();
                if (!method.equals(PAYMENT_METHOD_CC)
                        && !method.equals(PAYMENT_METHOD_ACH)
                        && !method.equals(PAYMENT_METHOD_ABT)) {
                    throw new AmazonFlexiblePaymentSystemComponentConfigurationException(
                            "Unknown payment method: " + method);
                }
            }
        } catch (InvalidClassSpecificationException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException(
                    "Failed to create object using object factory", e);
        } catch (ClassCastException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException(
                    "Failed to cast the object created by the object factory to target type", e);
        }
    }

    /**
     * Creates and configures authorization persistence instance using provided object factory and
     * configuration object.
     *
     * @param objectFactory
     *              the object factory used for persistence instance creation
     * @param configuration
     *              the configuration object which defines parameters for authorization persistence
     *
     * @return the authorization persistence instance
     *
     * @throws InvalidClassSpecificationException
     *              if error occurred while creating object by the object factory
     * @throws AmazonFlexiblePaymentSystemComponentConfigurationException
     *              if failed to get configuration parameter or child object
     * @throws ClassCastException
     *              if failed to cast the object created by Object Factory to persistence type
     */
    private AuthorizationPersistence createAuthorizationPersistence(ObjectFactory objectFactory,
        ConfigurationObject configuration) throws InvalidClassSpecificationException {

        // get authorization persistence Object Factory key
        String authorizationPersistenceKey = Helper.getProperty(configuration, AUTHORIZATION_PERSISTENCE_KEY_PARAMETER,
                true);

        // create persistence
        AuthorizationPersistence persistence =
                (AuthorizationPersistence) objectFactory.createObject(authorizationPersistenceKey);

        // configure persistence
        ConfigurationObject authorizationPersistenceConfig = Helper.getChildConfiguration(configuration,
                AUTHORIZATION_PERSISTENCE_CONFIG_CHILD);

        persistence.configure(authorizationPersistenceConfig);
        return persistence;
    }

    /**
     * Creates and configures payment persistence instance using provided object factory and
     * configuration object.
     *
     * @param objectFactory
     *              the object factory used for persistence instance creation
     * @param configuration
     *              the configuration object which defines parameters for payment persistence
     *
     * @return the payment persistence instance
     *
     * @throws InvalidClassSpecificationException
     *              if error occurred while creating object by the object factory
     * @throws AmazonFlexiblePaymentSystemComponentConfigurationException
     *              if failed to get configuration parameter or child object
     * @throws ClassCastException
     *              if failed to cast the object created by Object Factory to persistence type
     */
    private PaymentPersistence createPaymentPersistence(ObjectFactory objectFactory,
        ConfigurationObject configuration) throws InvalidClassSpecificationException {

        // get payment persistence Object Factory key
        String paymentPersistenceKey = Helper.getProperty(configuration, PAYMENT_PERSISTENCE_KEY_PARAMETER, true);

        // create persistence
        PaymentPersistence persistence =
                (PaymentPersistence) objectFactory.createObject(paymentPersistenceKey);

        // configure persistence
        ConfigurationObject paymentPersistenceConfig = Helper.getChildConfiguration(configuration,
                PAYMENT_PERSISTENCE_CONFIG_CHILD);

        persistence.configure(paymentPersistenceConfig);
        return persistence;
    }

    /**
     * Creates and configures event subscribers using provided object factory and configuration object.
     *
     * @param objectFactory
     *              the object factory used for subscribers objects creation
     * @param configuration
     *              the configuration object which defines parameters for subscriber objects
     *
     * @return the list of subscriber instances (not {@code null})
     *
     * @throws InvalidClassSpecificationException
     *              if error occurred while creating object by the object factory
     * @throws AmazonFlexiblePaymentSystemComponentConfigurationException
     *              if failed to get configuration parameter or child object
     * @throws ClassCastException
     *              if failed to cast the object created by Object Factory to persistence type
     */
    private List<PaymentEventSubscriber> createSubscribers(ObjectFactory objectFactory,
        ConfigurationObject configuration) throws InvalidClassSpecificationException {
        List<PaymentEventSubscriber> subscribersList = new ArrayList<PaymentEventSubscriber>();
        try {
            for (ConfigurationObject childConfig : configuration.getAllChildren()) {
                if (childConfig.getName().startsWith(PAYMENT_EVENT_SUBSCRIBER_CHILD_PREFIX)) {

                    // get subscriber Object Factory key
                    String subscriberKey = Helper.getProperty(childConfig, SUBSCRIBER_KEY_PARAMETER, true);

                    // create subscriber
                    ConfigurablePaymentEventSubscriber subscriber =
                            (ConfigurablePaymentEventSubscriber) objectFactory.createObject(subscriberKey);

                    // configure subscriber
                    ConfigurationObject subscriberConfig = Helper.getChildConfiguration(childConfig,
                            SUBSCRIBER_CONFIG_CHILD);

                    subscriber.configure(subscriberConfig);
                    subscribersList.add(subscriber);
                }
            }
        } catch (ConfigurationAccessException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException(
                    "An error occurred while accessing the configuration.", e);
        }
        return subscribersList;
    }

    /**
     * Initiates the payment authorization. Prepares the authorization URL to be used for redirecting the client.
     *
     * @param request
     *            the payment authorization request data
     *
     * @return the prepared payment authorization data (not {@code null})
     *
     * @throws IllegalArgumentException
     *            if request is {@code null} <br/>
     *            or request.getRedirectUrl() is {@code null} or empty <br/>
     *            or request.getPaymentDetails() is {@code null} <br/>
     *            or request.getPaymentDetails().getAmount() is {@code null} or not positive <br/>
     *            or request.getPaymentDetails().getParameters() contains {@code null} key <br/>
     *
     *            or request.isFutureChargesAuthorizationRequired()
     *                and request.getTotalChargesThreshold() is {@code null} <br/>
     *
     *            or request.getTotalChargesThreshold() not {@code null}
     *                and request.getTotalChargesThreshold() is not positive <br/>
     *
     *            or request.getFutureChargesFixedAmount() not {@code null}
     *                and request.getFutureChargesFixedAmount() is not positive
     *
     * @throws AuthorizationPersistenceException
     *              if some error occurred when accessing the authorization persistence
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the payment persistence
     * @throws AmazonFlexiblePaymentManagementException
     *            if some critical error occurred
     */
    @Override
    public PaymentAuthorizationData initiatePaymentAuthorization(PaymentAuthorizationRequest request)
        throws AmazonFlexiblePaymentManagementException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".initiatePaymentAuthorization(PaymentAuthorizationRequest request)";

        LoggingWrapperUtility.logEntrance(log, signature,
                new String[] {"request"}, new Object[] {String.valueOf(request)});

        try {
            validateAuthorizationRequest(request);

            // collect necessary data
            String redirectUrl = request.getRedirectUrl();
            PaymentDetails paymentDetails = request.getPaymentDetails();
            boolean authorizeFutureCharges = request.isFutureChargesAuthorizationRequired();
            BigDecimal totalChargesThreshold = request.getTotalChargesThreshold();
            BigDecimal futureChargesFixedAmount = request.getFutureChargesFixedAmount();

            // create authorization and payment in persistence
            Payment payment = createAuthorizationAndPayment(paymentDetails, authorizeFutureCharges,
                    totalChargesThreshold, futureChargesFixedAmount);

            // get authorization URL to be used for redirecting the client to the Amazon Co-Branded service
            String authorizationUrl = getAuthorizationUrl(redirectUrl, payment.getId(), payment.getAuthorizationId(),
                    paymentDetails.isReservation(), authorizeFutureCharges,
                    totalChargesThreshold, paymentDetails.getAmount());

            PaymentAuthorizationData result = new PaymentAuthorizationData();
            result.setAuthorizationId(payment.getAuthorizationId());
            result.setPaymentId(payment.getId());
            result.setAuthorizationUrl(authorizationUrl);

            LoggingWrapperUtility.logExit(log, signature, new Object[] {result}, entranceTimestamp);
            return result;
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        } catch (MalformedURLException e) {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonFlexiblePaymentManagementException(
                    "Failed to get authorization url", e), true, Level.ERROR);
        } catch (SignatureException e) {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonFlexiblePaymentManagementException(
                    "Failed to get authorization url", e), true, Level.ERROR);
        } catch (UnsupportedEncodingException e) {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonFlexiblePaymentManagementException(
                    "Failed to get authorization url", e), true, Level.ERROR);
        } catch (AmazonFlexiblePaymentManagementException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        }
    }

    /**
     * Validates authorization request.
     * It is used by {@code initiatePaymentAuthorization} method to validate passed parameter.
     *
     * @param request
     *            the payment authorization request data
     *
     * @throws IllegalArgumentException
     *            if request is {@code null} <br/>
     *            or request.getRedirectUrl() is {@code null} or empty <br/>
     *            or request.getPaymentDetails() is {@code null} <br/>
     *            or request.getPaymentDetails().getAmount() is {@code null} or not positive <br/>
     *            or request.getPaymentDetails().getParameters() contains {@code null} key <br/>
     *
     *            or request.isFutureChargesAuthorizationRequired()
     *                and request.getTotalChargesThreshold() is {@code null} <br/>
     *
     *            or request.getTotalChargesThreshold() not {@code null}
     *                and request.getTotalChargesThreshold() is not positive <br/>
     *
     *            or request.getFutureChargesFixedAmount() not {@code null}
     *                and request.getFutureChargesFixedAmount() is not positive
     */
    private static void validateAuthorizationRequest(PaymentAuthorizationRequest request) {
        ParameterCheckUtility.checkNotNull(request, "request");

        ValidationUtility.checkNotNullNorEmptyAfterTrimming(request.getRedirectUrl(), "request.redirectUrl",
                IllegalArgumentException.class);

        ValidationUtility.checkNotNull(request.getPaymentDetails(), "request.paymentDetails",
                IllegalArgumentException.class);

        ValidationUtility.checkNotNull(request.getPaymentDetails().getAmount(),
                "request.paymentDetails.amount", IllegalArgumentException.class);

        ValidationUtility.checkPositive(request.getPaymentDetails().getAmount().compareTo(BigDecimal.ZERO),
                "request.paymentDetails.amount", IllegalArgumentException.class);

        if (request.getPaymentDetails().getParameters() != null
                && request.getPaymentDetails().getParameters().containsKey(null)) {
            throw new IllegalArgumentException("request.paymentDetails.parameters contains null key");
        }
        if (request.isFutureChargesAuthorizationRequired()
                && request.getTotalChargesThreshold() == null) {
            throw new IllegalArgumentException(
                    "totalChargesThreshold should not be null when futureChargesAuthorizationRequired is set");
        }
        if (request.getTotalChargesThreshold() != null
                && request.getTotalChargesThreshold().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "totalChargesThreshold should be positive when it is specified");
        }
        if (request.getFutureChargesFixedAmount() != null
                && request.getFutureChargesFixedAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "futureChargesFixedAmount should be positive when it is specified");
        }
    }

    /**
     * <p>
     * Handles the request from the <i>Amazon Co-Branded Service</i>. Checks the authorization response parameters and
     * performs the reservation or payment if authorization was successful.
     * </p>
     *
     * <p>
     * This method can generate the following payment events:
     * <ul>
     * <li>AUTHORIZATION_FAILURE</li>
     * <li>AUTHORIZATION_SUCCESS</li>
     * <li>RESERVATION_FAILURE</li>
     * <li>RESERVATION_SUCCESS</li>
     * <li>PAYMENT_FAILURE</li>
     * <li>PAYMENT_SUCCESS</li>
     * </ul>
     * </p>
     *
     * @param requestParams
     *            the map with HTTP request parameters
     *
     * @throws IllegalArgumentException
     *            if requestParams is {@code null}
     * @throws AuthorizationPersistenceException
     *              if some error occurred when accessing the authorization persistence
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the payment persistence
     * @throws AmazonFlexiblePaymentManagementException
     *            if some critical error occurred
     */
    @Override
    public void handleRequestFromCoBrandedService(Map<Object, Object> requestParams)
        throws AmazonFlexiblePaymentManagementException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".handleRequestFromCoBrandedService(Map<Object, Object> requestParams)";

        LoggingWrapperUtility.logEntrance(log, signature,
                new String[] {"requestParams"},
                new Object[] {Helper.toString(requestParams)});

        try {
            ParameterCheckUtility.checkNotNull(requestParams, "requestParams");

            // 1. parse request parameters and retrieve parsed data
            ParsedRequestParams params = parseRequestParams(requestParams, signature);
            long paymentId = params.getPaymentId();
            boolean reserve = params.isReserve();
            String status = params.getStatus();
            String tokenId = params.getTokenId();
            String errorMessage = params.getErrorMessage();
            String warningMessage = params.getWarningMessage();

            // 2. handle request
            if (warningMessage != null) {
                log.log(Level.WARN, warningMessage);
            }

            Payment payment = paymentPersistence.getPayment(paymentId);
            if (payment == null) {
                throw new AmazonFlexiblePaymentManagementException(
                        "Failed to get payment from persistence for paymentId: " + paymentId);
            }

            long authorizationId = payment.getAuthorizationId();
            Authorization authorization = authorizationPersistence.getAuthorization(authorizationId);
            if (authorization == null) {
                throw new AmazonFlexiblePaymentManagementException(
                        "Failed to get authorization from persistence for authorizationId: " + authorizationId);
            }

            PaymentDetails paymentDetails = getPaymentDetails(payment, reserve);
            if (!status.equals(REQUEST_STATUS_SA)
                && !status.equals(REQUEST_STATUS_SB)
                && !status.equals(REQUEST_STATUS_SC)) {
                // process failure
                processFailurePaymentEvent(PaymentEventType.AUTHORIZATION_FAILURE, paymentDetails,
                        authorizationId, paymentId, status, null, errorMessage);
            } else {
                // process success
                processSuccessPaymentEvent(PaymentEventType.AUTHORIZATION_SUCCESS, paymentDetails,
                        authorizationId, paymentId);

                authorization.setTokenId(tokenId);
                authorizationPersistence.updateAuthorization(authorization);
                performPayOrReserveOperation(payment, authorization, reserve);
            }
            LoggingWrapperUtility.logExit(log, signature, null, entranceTimestamp);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        } catch (AmazonFlexiblePaymentManagementException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        }
    }

    /**
     * Retrieves parameters from the map of HTTP request parameters and validates them. The request itself is
     * a request from <i>Amazon Co-Branded Service</i> and it is a response to the payment authorization initiation.
     *
     * @param requestParams
     *              the request parameters map (note, the "real" type of the map is Map<String, String[]>)
     * @param signature
     *              the signature of the public business method caller
     *
     * @return the object that encapsulates parsed parameters
     *
     * @throws AmazonFlexiblePaymentManagementException
     *              if failed to get required parameter or parameters validation failed
     */
    private ParsedRequestParams parseRequestParams(Map<Object, Object> requestParams, String signature)
        throws AmazonFlexiblePaymentManagementException {
        // 1. get required payment id parameter
        String[] values = (String[]) requestParams.get("paymentId");
        if (values == null || values.length == 0) {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonFlexiblePaymentManagementException(
                    "paymentId parameter is absent"), true, Level.ERROR);
        }
        long paymentId;
        try {
            paymentId = Long.parseLong(values[0]);
        } catch (NumberFormatException e) {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonFlexiblePaymentManagementException(
                    "Failed to parse paymentId request parameter: paymentId = " + values[0], e),
                    true, Level.ERROR);
        }
        if (paymentId <= 0) {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonFlexiblePaymentManagementException(
                    "paymentId parameter is not positive"), true, Level.ERROR);
        }

        // 2. get required reserve parameter
        values = (String[]) requestParams.get("reserve");
        if (values == null || values.length == 0) {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonFlexiblePaymentManagementException(
                    "reserve parameter is absent"), true, Level.ERROR);
        }
        boolean reserve;
        if (values[0].equals("0")) {
            reserve = false;
        } else if (values[0].equals("1")) {
            reserve = true;
        } else {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonFlexiblePaymentManagementException(
                    "Failed to parse reserve parameter: reserve = " + values[0]), true, Level.ERROR);
        }

        // 3. get required status parameter
        values = (String[]) requestParams.get("status");
        if (values == null || values.length == 0) {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonFlexiblePaymentManagementException(
                    "status parameter is absent"), true, Level.ERROR);
        }
        String status = values[0];

        // 4. get required tokenID parameter
        values = (String[]) requestParams.get("tokenID");
        if (values == null || values.length == 0) {
            throw LoggingWrapperUtility.logException(log, signature, new AmazonFlexiblePaymentManagementException(
                    "tokenId parameter is absent"), true, Level.ERROR);
        }
        String tokenId = values[0];

        // 5. get optional parameters
        values = (String[]) requestParams.get("errorMessage");
        String errorMessage = (values == null || values.length == 0) ? null : values[0];
        values = (String[]) requestParams.get("warningMessage");
        String warningMessage = (values == null || values.length == 0) ? null : values[0];

        return new ParsedRequestParams(paymentId, reserve, status, tokenId, errorMessage, warningMessage);
    }

    /**
     * <p>
     * Creates new payment or reservation for existed authorization.
     * </p>
     *
     * <p>
     * This method can generate the following payment events:
     * <ul>
     * <li>RESERVATION_FAILURE</li>
     * <li>PAYMENT_FAILURE</li>
     * <li>RESERVATION_SUCCESS</li>
     * <li>PAYMENT_SUCCESS</li>
     * </ul>
     * </p>
     *
     * @param authorizationId
     *            the ID of the authorization
     * @param paymentDetails
     *            the payment details
     *
     * @return the generated payment ID
     *
     * @throws IllegalArgumentException
     *            if authorizationId is not positive <br/>
     *            or paymentDetails is {@code null} <br/>
     *            or paymentDetails.getAmount() is {@code null} or not positive <br/>
     *            or paymentDetails.getParameters() contains {@code null} key
     *  @throws AuthorizationNotFoundException
     *            if authorization with the specified ID doesn't exist
     * @throws AuthorizationPersistenceException
     *              if some error occurred when accessing the authorization persistence
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the payment persistence
     * @throws AmazonFlexiblePaymentManagementException
     *            if some other critical error occurred
     */
    @Override
    public long processAuthorizedPayment(long authorizationId, PaymentDetails paymentDetails)
        throws AmazonFlexiblePaymentManagementException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME
                + ".processAuthorizedPayment(long authorizationId, PaymentDetails paymentDetails)";

        LoggingWrapperUtility.logEntrance(log, signature,
                new String[] {"authorizationId", "paymentDetails"},
                new Object[] {authorizationId, String.valueOf(paymentDetails)});

        try {
            validateAuthorizedPayment(authorizationId, paymentDetails);

            // 1. get authorization for given id and create payment in persistence
            Authorization authorization = getRequiredAuthorization(authorizationId);
            Payment payment = createPayment(paymentDetails, authorizationId);
            long paymentId = payment.getId();

            // 2. handle pay/reservation failure or perform payment operation
            String[] errorTypeAndMessage = new String[2];

            boolean failure = checkPaymentOrReservationFailure(authorization, paymentDetails.getAmount(),
                    errorTypeAndMessage);

            if (failure) {
                PaymentEventType eventType = paymentDetails.isReservation()
                        ? PaymentEventType.RESERVATION_FAILURE : PaymentEventType.PAYMENT_FAILURE;

                processFailurePaymentEvent(eventType, paymentDetails, authorizationId, paymentId,
                        null, errorTypeAndMessage[0], errorTypeAndMessage[1]);

                payment.setStatus(PaymentStatus.FAILED);
                paymentPersistence.updatePayment(payment);
            } else {
                performPayOrReserveOperation(payment, authorization, paymentDetails.isReservation());
            }
            LoggingWrapperUtility.logExit(log, signature, new Object[] {paymentId}, entranceTimestamp);
            return paymentId;
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        } catch (AmazonFlexiblePaymentManagementException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        }
    }

    /**
     * Validates parameters for processAuthorizedPayment() method.
     *
     * @param authorizationId
     *            the ID of the authorization
     * @param paymentDetails
     *            the payment details
     *
     * @throws IllegalArgumentException
     *            if authorizationId is not positive <br/>
     *            or paymentDetails is {@code null} <br/>
     *            or paymentDetails.getAmount() is {@code null} or not positive <br/>
     *            or paymentDetails.getParameters() contains {@code null} key
     */
    private static void validateAuthorizedPayment(long authorizationId, PaymentDetails paymentDetails) {
        ParameterCheckUtility.checkPositive(authorizationId, "authorizationId");
        ParameterCheckUtility.checkNotNull(paymentDetails, "paymentDetails");

        ValidationUtility.checkNotNull(paymentDetails.getAmount(),
                "paymentDetails.amount", IllegalArgumentException.class);

        ValidationUtility.checkPositive(paymentDetails.getAmount().compareTo(BigDecimal.ZERO),
                "paymentDetails.amount", IllegalArgumentException.class);

        if (paymentDetails.getParameters() != null && paymentDetails.getParameters().containsKey(null)) {
            throw new IllegalArgumentException("paymentDetails.parameters contains null key");
        }
    }

    /**
     * Checks whether pay or reservation operation can be accomplished for the given authorization state and the given
     * amount.
     *
     * @param authorization
     *              the authorization (not {@code null})
     * @param amount
     *              the amount (not {@code null}, positive)
     * @param errorTypeAndMessage
     *              output array with two elements for resulted error type (element 0) and error message (element 1)
     *              in case of failure
     *
     * @return true, if operation can not be accomplished for the given authorization state and amount, otherwise false
     */
    private boolean checkPaymentOrReservationFailure(Authorization authorization, BigDecimal amount,
        String[] errorTypeAndMessage) {
        // check various conditions
        if (authorization.getTokenId() == null) {
            errorTypeAndMessage[0] = ERROR_TYPE_TOKEN_NOT_ACTIVE_SENDER;
            errorTypeAndMessage[1] = "The authorization for future charges failed";
            return true;
        }
        if (!authorization.isMultipleUseAuthorization()) {
            errorTypeAndMessage[0] = ERROR_TYPE_TOKEN_NOT_ACTIVE_SENDER;
            errorTypeAndMessage[1] = "The single use token cannot be reused";
            return true;
        }
        if (authorization.getAuthorizedAmountLeft().compareTo(amount) < 0) {
            errorTypeAndMessage[0] = ERROR_TYPE_ACCOUNT_LIMITS_EXCEEDED;
            errorTypeAndMessage[1] = "The requested amount exceeds the authorized limit";
            return true;
        }
        BigDecimal authorizedFixedFutureAmount = authorization.getAuthorizedFixedFutureAmount();
        if (authorizedFixedFutureAmount != null && !authorizedFixedFutureAmount.equals(amount)) {
            errorTypeAndMessage[0] = ERROR_TYPE_AMOUNT_OUT_OF_RANGE;
            errorTypeAndMessage[1] = "The requested amount is not equal to the authorized fixed amount";
            return true;
        }
        return false;
    }

    /**
     * Performs pay or reserve operation using Amazon FPS Client. This method contains common functionality
     * for handleRequestFromCoBrandedService() and processAuthorizedPayment() methods.
     *
     * @param payment
     *              the payment instance
     * @param authorization
     *              the authorization instance
     * @param reserve
     *              defines whether to perform pay or reserve operation
     *
     * @throws AuthorizationPersistenceException
     *              if some error occurred when accessing the authorization persistence
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the payment persistence
     * @throws AmazonFlexiblePaymentManagementException
     *              if some critical error occurred
     */
    private void performPayOrReserveOperation(Payment payment, Authorization authorization, boolean reserve)
        throws AmazonFlexiblePaymentManagementException {
        String transactionId = null;
        String requestId = null;
        PaymentStatus paymentStatus = null;
        PaymentEventType eventType = null;

        // use Amazon FPS Service to perform payment operation
        try {
            if (reserve) {
                ReserveResponse reserveResponse = doAmazonFPS_Reserve(payment, authorization);
                ReserveResult reserveResult = reserveResponse.getReserveResult();
                transactionId = reserveResult.getTransactionId();
                requestId = reserveResponse.getResponseMetadata().getRequestId();
                paymentStatus = PaymentStatus.RESERVED;
                eventType = PaymentEventType.RESERVATION_SUCCESS;
            } else {
                PayResponse payResponse = doAmazonFPS_Pay(payment, authorization);
                PayResult payResult = payResponse.getPayResult();
                transactionId = payResult.getTransactionId();
                requestId = payResponse.getResponseMetadata().getRequestId();
                paymentStatus = PaymentStatus.COMPLETED;
                eventType = PaymentEventType.PAYMENT_SUCCESS;
            }
        } catch (AmazonFPSException e) {
            processFailurePaymentEvent(reserve
                    ? PaymentEventType.RESERVATION_FAILURE : PaymentEventType.PAYMENT_FAILURE,
                    getPaymentDetails(payment, reserve), authorization.getId(), payment.getId(),
                    e.getErrorCode(), e.getErrorType(), e.getMessage());

            requestId = e.getRequestId();
            paymentStatus = PaymentStatus.FAILED;
        }

        // register payment operation in persistence
        PaymentOperationType type = reserve ? PaymentOperationType.RESERVE : PaymentOperationType.PAY;
        createPaymentOperation(payment.getId(), requestId, type, paymentStatus != PaymentStatus.FAILED);

        // update payment in persistence
        payment.setStatus(paymentStatus);
        payment.setTransactionId(transactionId);
        paymentPersistence.updatePayment(payment);

        // update authorized amount left in case of success
        if (paymentStatus != PaymentStatus.FAILED) {
            BigDecimal newAmount = authorization.getAuthorizedAmountLeft().subtract(payment.getAmount());
            authorization.setAuthorizedAmountLeft(newAmount);
            authorizationPersistence.updateAuthorization(authorization);
            processSuccessPaymentEvent(eventType, getPaymentDetails(payment, reserve),
                    authorization.getId(), payment.getId());
        }
    }

    /**
     * <p>
     * Cancels the payment with the specified ID. This ID is returned from initiatePaymentAuthorization() and
     * processAuthorizedPayment() methods.
     * </p>
     *
     * <p>
     * This method can generate the following payment events:
     * <ul>
     * <li>PAYMENT_CANCELLATION_FAILURE</li>
     * <li>PAYMENT_CANCELLATION_SUCCESS</li>
     * </ul>
     * </p>
     *
     * @param paymentId
     *            the ID of the payment to be cancelled
     *
     * @throws IllegalArgumentException
     *            if paymentId is not positive
     * @throws PaymentNotFoundException
     *            if payment with the specified ID doesn't exist
     * @throws AuthorizationPersistenceException
     *              if some error occurred when accessing the authorization persistence
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the payment persistence
     * @throws AmazonFlexiblePaymentManagementException
     *            if some critical error occurred
     */
    @Override
    public void cancelPayment(long paymentId) throws AmazonFlexiblePaymentManagementException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".cancelPayment(long paymentId)";

        LoggingWrapperUtility.logEntrance(log, signature, new String[] {"paymentId"}, new Object[] {paymentId});

        PaymentDetails paymentDetails = null;
        long authorizationId = 0;
        try {
            ParameterCheckUtility.checkPositive(paymentId, "paymentId");

            Payment payment = getRequiredPayment(paymentId);
            paymentDetails = getPaymentDetails(payment, false);
            authorizationId = payment.getAuthorizationId();

            if (payment.getStatus() != PaymentStatus.RESERVED && payment.getStatus() != PaymentStatus.COMPLETED) {
                // process failure
                processFailurePaymentEvent(PaymentEventType.PAYMENT_CANCELLATION_FAILURE, paymentDetails,
                        authorizationId, paymentId, null,
                        ERROR_TYPE_INVALID_TRANSACTION_STATE,
                        "The payment transaction cannot be cancelled because of invalid state");
            } else {
                // cancel payment
                CancelResponse cancelResponse = doAmazonFPS_Cancel(payment);

                String requestId = cancelResponse.getResponseMetadata().getRequestId();
                createPaymentOperation(paymentId, requestId, PaymentOperationType.CANCEL, true);

                Authorization authorization = authorizationPersistence.getAuthorization(authorizationId);
                ValidationUtility.checkNotNull(authorization, "authorization",
                        AmazonFlexiblePaymentManagementException.class);

                authorization.setAuthorizedAmountLeft(authorization.getAuthorizedAmountLeft().add(payment.getAmount()));
                authorizationPersistence.updateAuthorization(authorization);

                payment.setStatus(PaymentStatus.CANCELLED);
                paymentPersistence.updatePayment(payment);

                processSuccessPaymentEvent(PaymentEventType.PAYMENT_CANCELLATION_SUCCESS,
                        paymentDetails, authorizationId, paymentId);
            }
            LoggingWrapperUtility.logExit(log, signature, null, entranceTimestamp);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        } catch (AmazonFlexiblePaymentManagementException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        } catch (AmazonFPSException e) {
            createPaymentOperation(paymentId, e.getRequestId(), PaymentOperationType.CANCEL, false);

            processFailurePaymentEvent(PaymentEventType.PAYMENT_CANCELLATION_FAILURE, paymentDetails,
                    authorizationId, paymentId,
                    e.getErrorCode(), e.getErrorType(), e.getMessage());
        }
    }

    /**
     * <p>
     * Settles the previously reserved payment with the specified ID. This ID is returned from
     * initiatePaymentAuthorization() and processAuthorizedPayment() methods.
     * </p>
     *
     * <p>
     * This method can generate the following payment events:
     * <ul>
     * <li>SETTLEMENT_FAILURE</li>
     * <li>SETTLEMENT_SUCCESS</li>
     * </ul>
     * </p>
     *
     * @param paymentId
     *            the ID of the payment to be settled
     *
     * @throws IllegalArgumentException
     *            if paymentId is not positive
     * @throws PaymentNotFoundException
     *            if payment with the specified ID doesn't exist
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the payment persistence
     * @throws AmazonFlexiblePaymentManagementException
     *            if some critical error occurred
     */
    @Override
    public void settlePayment(long paymentId) throws AmazonFlexiblePaymentManagementException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".settlePayment(long paymentId)";

        LoggingWrapperUtility.logEntrance(log, signature, new String[] {"paymentId"}, new Object[] {paymentId});

        PaymentDetails paymentDetails = null;
        long authorizationId = 0;
        try {
            ParameterCheckUtility.checkPositive(paymentId, "paymentId");

            Payment payment = getRequiredPayment(paymentId);
            paymentDetails = getPaymentDetails(payment, false);
            authorizationId = payment.getAuthorizationId();

            if (payment.getStatus() != PaymentStatus.RESERVED) {
                // process failure
                processFailurePaymentEvent(PaymentEventType.SETTLEMENT_FAILURE, paymentDetails,
                        authorizationId, paymentId, null,
                        ERROR_TYPE_INVALID_TRANSACTION_STATE,
                        "The payment cannot be settled because of invalid state");
            } else {
                // settle payment
                SettleResponse settleResponse = doAmazonFPS_Settle(payment);

                String requestId = settleResponse.getResponseMetadata().getRequestId();
                createPaymentOperation(paymentId, requestId, PaymentOperationType.SETTLE, true);

                payment.setStatus(PaymentStatus.COMPLETED);
                paymentPersistence.updatePayment(payment);

                processSuccessPaymentEvent(PaymentEventType.SETTLEMENT_SUCCESS, paymentDetails,
                        authorizationId, paymentId);
            }
            LoggingWrapperUtility.logExit(log, signature, null, entranceTimestamp);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        } catch (AmazonFlexiblePaymentManagementException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        } catch (AmazonFPSException e) {
            createPaymentOperation(paymentId, e.getRequestId(), PaymentOperationType.SETTLE, false);

            processFailurePaymentEvent(PaymentEventType.SETTLEMENT_FAILURE, paymentDetails,
                    authorizationId, paymentId,
                    e.getErrorCode(), e.getErrorType(), e.getMessage());
        }
    }

    /**
     * <p>
     * Refunds the previously completed payment with the specified ID. This ID is returned from
     * initiatePaymentAuthorization() and processAuthorizedPayment() methods.
     * </p>
     *
     * <p>
     * This method can generate the following payment events:
     * <ul>
     * <li>REFUND_FAILURE</li>
     * <li>REFUND_SUCCESS</li>
     * </ul>
     * </p>
     *
     * @param paymentId
     *            the ID of the payment to be refunded.
     *
     * @throws IllegalArgumentException
     *            if paymentId is not positive
     * @throws PaymentNotFoundException
     *            if payment with the specified ID doesn't exist
     * @throws AuthorizationPersistenceException
     *              if some error occurred when accessing the authorization persistence
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the payment persistence
     * @throws AmazonFlexiblePaymentManagementException
     *              if some critical error occurred
     */
    @Override
    public void refundPayment(long paymentId) throws AmazonFlexiblePaymentManagementException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".refundPayment(long paymentId)";

        LoggingWrapperUtility.logEntrance(log, signature, new String[] {"paymentId"}, new Object[] {paymentId});

        PaymentDetails paymentDetails = null;
        long authorizationId = 0;
        try {
            ParameterCheckUtility.checkPositive(paymentId, "paymentId");

            Payment payment = getRequiredPayment(paymentId);
            paymentDetails = getPaymentDetails(payment, false);
            authorizationId = payment.getAuthorizationId();

            if (payment.getStatus() != PaymentStatus.COMPLETED) {
                // process failure
                processFailurePaymentEvent(PaymentEventType.REFUND_FAILURE, paymentDetails, authorizationId, paymentId,
                        null, ERROR_TYPE_INVALID_TRANSACTION_STATE,
                        "The payment transaction cannot be refunded because of invalid state");
            } else {
                // refund payment
                RefundResponse refundResponse = doAmazonFPS_Refund(payment);

                String requestId = refundResponse.getResponseMetadata().getRequestId();
                createPaymentOperation(paymentId, requestId, PaymentOperationType.REFUND, true);

                Authorization authorization = authorizationPersistence.getAuthorization(authorizationId);
                ValidationUtility.checkNotNull(authorization, "authorization",
                        AmazonFlexiblePaymentManagementException.class);

                BigDecimal newAmount = authorization.getAuthorizedAmountLeft().add(payment.getAmount());
                authorization.setAuthorizedAmountLeft(newAmount);
                authorizationPersistence.updateAuthorization(authorization);

                payment.setStatus(PaymentStatus.REFUNDED);
                paymentPersistence.updatePayment(payment);

                processSuccessPaymentEvent(PaymentEventType.REFUND_SUCCESS, paymentDetails, authorizationId, paymentId);
            }
            LoggingWrapperUtility.logExit(log, signature, null, entranceTimestamp);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        } catch (AmazonFlexiblePaymentManagementException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        } catch (AmazonFPSException e) {
            createPaymentOperation(paymentId, e.getRequestId(), PaymentOperationType.REFUND, false);

            processFailurePaymentEvent(PaymentEventType.REFUND_FAILURE, paymentDetails,
                    authorizationId, paymentId,
                    e.getErrorCode(), e.getErrorType(), e.getMessage());
        }
    }

    /**
     * <p>
     * Cancels the authorization with the specified ID. This ID is returned from initiatePaymentAuthorization() method.
     * </p>
     *
     * <p>
     * This method can generate the following payment events:
     * <ul>
     * <li>AUTHORIZATION_CANCELLATION_FAILURE</li>
     * <li>AUTHORIZATION_CANCELLATION_SUCCESS</li>
     * </ul>
     * </p>
     *
     *
     * @param authorizationId
     *            the ID of the authorization to be cancelled
     *
     * @throws IllegalArgumentException
     *            if authorizationId is not positive
     * @throws AuthorizationNotFoundException
     *            if authorization with the specified ID doesn't exist
     * @throws AuthorizationPersistenceException
     *              if some error occurred when accessing the authorization persistence
     * @throws AmazonFlexiblePaymentManagementException
     *            if some other critical error occurred
     */
    @Override
    public void cancelAuthorization(long authorizationId) throws AmazonFlexiblePaymentManagementException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".cancelAuthorization(long authorizationId)";

        LoggingWrapperUtility.logEntrance(log, signature,
                new String[] {"authorizationId"},
                new Object[] {authorizationId});

        try {
            ParameterCheckUtility.checkPositive(authorizationId, "authorizationId");

            Authorization authorization = getRequiredAuthorization(authorizationId);

            if (authorization.isCancelled()) {
                // process failure
                processFailurePaymentEvent(PaymentEventType.AUTHORIZATION_CANCELLATION_FAILURE, null,
                        authorizationId, 0L,
                        null, ERROR_TYPE_AUTH_FAILURE, "The authorization is already cancelled");
            } else {
                // cancel authorization
                doAmazonFPS_CancelToken(authorization.getTokenId());

                authorization.setCancelled(true);
                authorizationPersistence.updateAuthorization(authorization);

                processSuccessPaymentEvent(PaymentEventType.AUTHORIZATION_CANCELLATION_SUCCESS, null,
                        authorizationId, 0L);
            }
            LoggingWrapperUtility.logExit(log, signature, null, entranceTimestamp);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        } catch (AmazonFlexiblePaymentManagementException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        } catch (AmazonFPSException e) {
            processFailurePaymentEvent(PaymentEventType.AUTHORIZATION_CANCELLATION_FAILURE, null,
                    authorizationId, 0,
                    e.getErrorCode(), e.getErrorType(), e.getMessage());
        }
    }

    /**
     * Registers the given payment event subscriber so that it can receive payment event notifications from this
     * manager.
     *
     * @param subscriber
     *            the payment event subscriber to be registered
     *
     * @throws IllegalArgumentException
     *            if subscriber is {@code null}
     */
    @Override
    public void registerPaymentEventSubscriber(PaymentEventSubscriber subscriber) {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".registerPaymentEventSubscriber(PaymentEventSubscriber subscriber)";

        LoggingWrapperUtility.logEntrance(log, signature,
                new String[] {"subscriber"}, new Object[] {Helper.toString(subscriber)});

        try {
            ParameterCheckUtility.checkNotNull(subscriber, "subscriber");
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(log, signature, e, true, Level.ERROR);
        }

        synchronized (subscribers) {
            if (!subscribers.contains(subscriber)) {
                subscribers.add(subscriber);
            }
        }
        LoggingWrapperUtility.logExit(log, signature, null, entranceTimestamp);
    }

    /**
     * Retrieves all authorizations. Returns an empty list of none found.
     *
     * @return the retrieved authorizations (not {@code null}, doesn't contain {@code null})
     *
     * @throws AmazonFlexiblePaymentManagementException
     *              if some error occurred when retrieving authorizations
     */
    @Override
    public List<Authorization> getAllAuthorizations() throws AmazonFlexiblePaymentManagementException {
        return authorizationPersistence.getAllAuthorizations();
    }

    /**
     * Retrieves all payments. Returns an empty list if none found.
     *
     * @return the retrieved payments (not {@code null}, doesn't contain {@code null})
     *
     * @throws AmazonFlexiblePaymentManagementException
     *              if some error occurred when retrieving payments
     */
    @Override
    public List<Payment> getAllPayments() throws AmazonFlexiblePaymentManagementException {
        return paymentPersistence.getAllPayments();
    }

    /**
     * Retrieves all payments associated with authorization that has the specified ID. Returns an empty list if
     * none found.
     *
     * @param authorizationId
     *              the ID of the authorization for which payments should be retrieved
     *
     * @return the payments for the specified authorization (not {@code null}, doesn't contain {@code null})
     *
     * @throws IllegalArgumentException
     *              if {@code authorizationId <= 0}
     * @throws AmazonFlexiblePaymentManagementException
     *              if some error occurred when retrieving payments
     */
    @Override
    public List<Payment> getPaymentsByAuthorization(long authorizationId)
        throws AmazonFlexiblePaymentManagementException {
        return paymentPersistence.getPaymentsByAuthorization(authorizationId);
    }

    /**
     * <p>
     * The {@code ParsedRequestParams} class is a helper class that represents container for parameters parsed
     * from the request from <i>Amazon Co-Branded Service</i>.
     * </p>
     *
     * <strong>Thread Safety:</strong> This class is immutable and thread safe.
     *
     * @author KennyAlive
     * @version 1.0
     */
    private static class ParsedRequestParams {
        /**
         * The payment id. It is positive.
         */
        private long paymentId;
        /**
         * Defines whether the payment operation is a pay or reservation.
         */
        private boolean reserve;
        /**
         * Authorization status. Not {@code null}
         */
        private String status;
        /**
         * The token id provided by <i>Amazon Co-Branded Service</i> that should be used for subsequent payment
         * operations. Not {@code null}
         */
        private String tokenId;
        /**
         * The error message. Can be any value.
         */
        private String errorMessage;
        /**
         * The warning message. Can be any value.
         */
        private String warningMessage;

        /**
         * Constructs new {@code ParsedRequestParams} instance.
         *
         * @param paymentId
         *              the payment id
         * @param reserve
         *              the reservation flag
         * @param status
         *              the authorization status
         * @param tokenId
         *              the token id
         * @param errorMessage
         *              the error message
         * @param warningMessage
         *              the warning message
         */
        public ParsedRequestParams(long paymentId, boolean reserve, String status, String tokenId,
            String errorMessage, String warningMessage) {
            this.paymentId = paymentId;
            this.reserve = reserve;
            this.status = status;
            this.tokenId = tokenId;
            this.errorMessage = errorMessage;
            this.warningMessage = warningMessage;
        }

        /**
         * Gets the payment id.
         *
         * @return the payment id
         */
        public long getPaymentId() {
            return paymentId;
        }
        /**
         * Gets the reservation flag.
         *
         * @return the reservation flag
         */
        public boolean isReserve() {
            return reserve;
        }
        /**
         * Gets the authorization status.
         *
         * @return the authorization status
         */
        public String getStatus() {
            return status;
        }
        /**
         * Gets the token id for the authorization in use.
         *
         * @return the token id for the authorization in use
         */
        public String getTokenId() {
            return tokenId;
        }
        /**
         * Gets the error message.
         *
         * @return the error message
         */
        public String getErrorMessage() {
            return errorMessage;
        }
        /**
         * Gets the warning message.
         *
         * @return the warning message
         */
        public String getWarningMessage() {
            return warningMessage;
        }
    }

    /**
     * Creates object factory from the configuration object specification factory.
     *
     * @param configuration
     *              the configuration object
     *
     * @return the object factory
     *
     * @throws AmazonFlexiblePaymentSystemComponentConfigurationException
     *              if error occurred while creating configuration object specification factory or when
     *              failed to get necessary properties from the configuration object
     */
    private ObjectFactory createObjectFactory(ConfigurationObject configuration) {
        // get object factory configuration
        ConfigurationObject objectFactoryConfig = Helper.getChildConfiguration(configuration,
                OBJECT_FACTORY_CONFIG_CHILD);

        // create configuration object specification factory
        ConfigurationObjectSpecificationFactory cosf = null;
        try {
            cosf = new ConfigurationObjectSpecificationFactory(objectFactoryConfig);
        } catch (IllegalReferenceException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException(
                    "Failed to create configuration object specification factory", e);
        } catch (SpecificationConfigurationException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException(
                    "Failed to create configuration object specification factory", e);
        }

        return new ObjectFactory(cosf);
    }

    /**
     * Constructs the Amount instance with the given value in USD.
     *
     * @param value
     *            the value in USD (not {@code null})
     *
     * @return the constructed Amount instance (not {@code null})
     */
    private static Amount constructAmount(BigDecimal value) {
        return new Amount(CurrencyCode.USD, value.toPlainString());
    }

    /**
     * Creates payment event based on provided parameters and notifies all subscribers about it.
     * This method is used for processing failure events for which error-related field should be set.
     *
     * @param eventType
     *              the event type
     * @param paymentDetails
     *              the payment details
     * @param authorizationId
     *              the ID of the authorization
     * @param paymentId
     *              the ID of the payment
     * @param errorCode
     *              the error code
     * @param errorType
     *              the error type
     * @param errorMessage
     *              the error message
     */
    private void processFailurePaymentEvent(PaymentEventType eventType, PaymentDetails paymentDetails,
        long authorizationId, long paymentId, String errorCode, String errorType, String errorMessage) {
        PaymentEvent event = new PaymentEvent();
        event.setEventType(eventType);
        event.setPaymentDetails(paymentDetails);
        event.setAuthorizationId(authorizationId);
        event.setPaymentId(paymentId);
        event.setErrorCode(errorCode);
        event.setErrorType(errorType);
        event.setErrorMessage(errorMessage);

        processPaymentEvent(event);
    }

    /**
     * Creates payment event based on provided parameters and notifies all subscribers about it.
     * This method is used for processing success events for which error-related are not provided.
     *
     * @param eventType
     *              the event type
     * @param paymentDetails
     *              the payment details
     * @param authorizationId
     *              the ID of the authorization
     * @param paymentId
     *              the ID of the payment
     */
    private void processSuccessPaymentEvent(PaymentEventType eventType, PaymentDetails paymentDetails,
        long authorizationId, long paymentId) {
        PaymentEvent event = new PaymentEvent();
        event.setEventType(eventType);
        event.setPaymentDetails(paymentDetails);
        event.setAuthorizationId(authorizationId);
        event.setPaymentId(paymentId);

        processPaymentEvent(event);
    }

    /**
     * Processes the given payment event. Notifies all subscribers about it.
     *
     * @param paymentEvent
     *            the payment event to be processed (not {@code null})
     */
    private void processPaymentEvent(PaymentEvent paymentEvent) {
        log.log(Level.INFO, "Payment event: " + paymentEvent);

        synchronized (subscribers) {
            for (PaymentEventSubscriber subscriber : subscribers) {
                subscriber.processPaymentEvent(paymentEvent);
            }
        }
    }

    /**
     * Creates payment in persistence based on provided parameters.
     *
     * @param paymentDetails
     *              the payment details (not {@code null})
     * @param authorizationId
     *              the id of the authorization that should be associated with the payment
     *
     * @return the payment instance that represents created payment in persistence
     *
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the payment persistence
     */
    private Payment createPayment(PaymentDetails paymentDetails, long authorizationId)
        throws PaymentPersistenceException {
        Payment payment = new Payment();
        payment.setAmount(paymentDetails.getAmount());
        payment.setParameters(paymentDetails.getParameters());
        payment.setStatus(PaymentStatus.NOT_INITIATED);
        payment.setAuthorizationId(authorizationId);
        paymentPersistence.createPayment(payment);
        return payment;
    }

    /**
     * Creates authorization and associated with it payment in persistence based on provided parameters.
     *
     * @param paymentDetails
     *              the payment details (not {@code null})
     * @param authorizeFutureCharges
     *              defines whether to create single-use or multi-use authorization. If it is true, then
     *              multi-use authorization is created, otherwise single-use one
     * @param totalChargesThreshold
     *              the total charges threshold
     * @param futureChargesFixedAmount
     *              the future charges fixed amount for multi-use authorization
     *
     * @return the payment instance that represents created payment in persistence
     *
     * @throws AuthorizationPersistenceException
     *              if some error occurred when accessing the authorization persistence
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the payment persistence
     */
    private Payment createAuthorizationAndPayment(PaymentDetails paymentDetails, boolean authorizeFutureCharges,
        BigDecimal totalChargesThreshold, BigDecimal futureChargesFixedAmount)
        throws AuthorizationPersistenceException, PaymentPersistenceException {

        BigDecimal authorizedAmount = (totalChargesThreshold == null)
                                      ? paymentDetails.getAmount() : totalChargesThreshold;

        // create authorization instance and store it in persistence
        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(authorizeFutureCharges);
        authorization.setAuthorizedAmountLeft(authorizedAmount);
        authorization.setAuthorizedFixedFutureAmount(futureChargesFixedAmount);
        authorizationPersistence.createAuthorization(authorization);

        // create payment instance and store it in persistence
        return createPayment(paymentDetails, authorization.getId());
    }

    /**
     * Creates the payment operation with the specified parameters and stores it in persistence.
     *
     * @param paymentId
     *              the ID of the payment
     * @param requestId
     *              the ID of the request generated by <i>Amazon FPS Service</i>
     * @param type
     *              the type of the operation
     * @param successful
     *              the value indicating whether the payment operation was successful
     *
     * @throws PaymentPersistenceException
     *              if some error occurred in persistence
     */
    private void createPaymentOperation(long paymentId, String requestId, PaymentOperationType type,
        boolean successful) throws PaymentPersistenceException {
        PaymentOperation paymentOperation = new PaymentOperation();
        paymentOperation.setPaymentId(paymentId);
        paymentOperation.setRequestId(requestId);
        paymentOperation.setType(type);
        paymentOperation.setSuccessful(successful);

        paymentPersistence.createPaymentOperation(paymentOperation);
    }

    /**
     * Retrieves authorization from persistence with the given id or throws AuthorizationNotFoundException if it wasn't
     * found.
     *
     * @param authorizationId
     *              the id of the authorization
     *
     * @return the authorization with the given id
     *
     * @throws AuthorizationPersistenceException
     *              if some error occurred when accessing the authorization persistence
     * @throws AuthorizationNotFoundException
     *              if authorization with the given id was not found
     */
    private Authorization getRequiredAuthorization(long authorizationId)
        throws AuthorizationPersistenceException, AuthorizationNotFoundException {
        Authorization authorization = authorizationPersistence.getAuthorization(authorizationId);
        if (authorization == null) {
            throw new AuthorizationNotFoundException("Can not find authorization in persistence", authorizationId);
        }
        return authorization;
    }

    /**
     * Retrieves payment from persistence with the given id or throws PaymentNotFoundException if it wasn't found.
     *
     * @param paymentId
     *              the id of the payment
     *
     * @return the payment with the given id
     *
     * @throws PaymentPersistenceException
     *              if some error occurred when accessing the payment persistence
     * @throws PaymentNotFoundException
     *              if payment with the given id was not found
     */
    private Payment getRequiredPayment(long paymentId)
        throws PaymentPersistenceException, PaymentNotFoundException {
        Payment payment = paymentPersistence.getPayment(paymentId);
        if (payment == null) {
            throw new PaymentNotFoundException("Can not find payment in persistence", paymentId);
        }
        return payment;
    }

    /**
     * Constructs the payment details instance for the given payment instance.
     *
     * @param payment
     *            the payment data
     * @param reservation
     *            true if payment reservation is expected to be performed, false otherwise
     *
     * @return the constructed payment details instance (not {@code null})
     */
    private static PaymentDetails getPaymentDetails(Payment payment, boolean reservation) {
        PaymentDetails result = new PaymentDetails();
        result.setAmount(payment.getAmount());
        result.setParameters(payment.getParameters());
        result.setReservation(reservation);
        return result;
    }

    /**
     * Loads configuration object from the given configuration file.
     * This method is used only by the constructor which accepts configuration file name and configuration namespace.
     *
     * @param filepath
     *              the filepath of the configuration file (should not be null)
     * @param namespace
     *              the configuration namespace (should not be null)
     *
     * @return the retrieved configuration object
     *
     * @throws IllegalArgumentException
     *            if filepath or namespace is {@code null}/empty
     * @throws AmazonFlexiblePaymentSystemComponentConfigurationException
     *              if error occurred while loading configuration object
     */
    private static ConfigurationObject getConfiguration(String filepath, String namespace) {
        ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(filepath, "filepath");
        ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(namespace, "namespace");

        try {
            ConfigurationFileManager manager = new ConfigurationFileManager(filepath);
            ConfigurationObject configuration = manager.getConfiguration(namespace);

            return Helper.getChildConfiguration(configuration, namespace);

        } catch (ConfigurationPersistenceException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException("Failed to get configuration", e);
        } catch (IOException e) {
            throw new AmazonFlexiblePaymentSystemComponentConfigurationException("Failed to get configuration", e);
        }
    }

    /* ------------------------------------------------------------------------------
     * Amazon FPS Client operations wrappers. All work with Amazon FPS Client is
     * done through this small API. It is collected in a single place for better maintenance.
     * ------------------------------------------------------------------------------*/
    /**
     * Reserves payment.
     *
     * @param payment
     *              the payment object
     * @param authorization
     *              the authorization object
     *
     * @return the reserve response
     *
     * @throws AmazonFPSException
     *              if some error occurred within Amazon FPS Client
     */
    private ReserveResponse doAmazonFPS_Reserve(Payment payment, Authorization authorization)
        throws AmazonFPSException {
        AmazonFPSClient fpsClient = new AmazonFPSClient(awsAccessKey, awsSecretKey);
        ReserveRequest reserveRequest = new ReserveRequest();
        reserveRequest.setCallerReference("PAYMENT_" + payment.getId() + "_RESERVE");
        reserveRequest.setSenderTokenId(authorization.getTokenId());
        reserveRequest.setTransactionAmount(constructAmount(payment.getAmount()));
        return fpsClient.reserve(reserveRequest);
    }

    /**
     * Performs payment.
     *
     * @param payment
     *              the payment object
     * @param authorization
     *              the authorization object
     *
     * @return the pay response
     *
     * @throws AmazonFPSException
     *              if some error occurred within Amazon FPS Client
     */
    private PayResponse doAmazonFPS_Pay(Payment payment, Authorization authorization)
        throws AmazonFPSException {
        AmazonFPSClient fpsClient = new AmazonFPSClient(awsAccessKey, awsSecretKey);
        PayRequest payRequest = new PayRequest();
        payRequest.setCallerReference("PAYMENT_" + payment.getId() + "_PAY");
        payRequest.setSenderTokenId(authorization.getTokenId());
        payRequest.setTransactionAmount(constructAmount(payment.getAmount()));
        return fpsClient.pay(payRequest);
    }

    /**
     * Cancels payment.
     *
     * @param payment
     *              the payment object
     *
     * @return the cancel response
     *
     * @throws AmazonFPSException
     *              if some error occurred within Amazon FPS Client
     */
    private CancelResponse doAmazonFPS_Cancel(Payment payment) throws AmazonFPSException {
        AmazonFPSClient fpsClient = new AmazonFPSClient(awsAccessKey, awsSecretKey);
        CancelRequest cancelRequest = new CancelRequest();
        cancelRequest.setTransactionId(payment.getTransactionId());
        return fpsClient.cancel(cancelRequest);
    }

    /**
     * Settles payment.
     *
     * @param payment
     *              the payment object
     *
     * @return the settle response
     *
     * @throws AmazonFPSException
     *              if some error occurred within Amazon FPS Client
     */
    private SettleResponse doAmazonFPS_Settle(Payment payment) throws AmazonFPSException {
        AmazonFPSClient fpsClient = new AmazonFPSClient(awsAccessKey, awsSecretKey);
        SettleRequest settleRequest = new SettleRequest();
        settleRequest.setReserveTransactionId(payment.getTransactionId());
        return fpsClient.settle(settleRequest);
    }

    /**
     * Refunds payment.
     *
     * @param payment
     *              the payment object
     *
     * @return the refund response
     *
     * @throws AmazonFPSException
     *              if some error occurred within Amazon FPS Client
     */
    private RefundResponse doAmazonFPS_Refund(Payment payment) throws AmazonFPSException {
        AmazonFPSClient fpsClient = new AmazonFPSClient(awsAccessKey, awsSecretKey);
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setCallerReference("PAYMENT_" + payment.getId() + "_REFUND");
        refundRequest.setTransactionId(payment.getTransactionId());
        return fpsClient.refund(refundRequest);
    }

    /**
     * Cancels the given token.
     *
     * @param tokenId
     *              the token id to be cancelled
     *
     * @throws AmazonFPSException
     *              if some error occurred within Amazon FPS Client
     */
    private void doAmazonFPS_CancelToken(String tokenId) throws AmazonFPSException {
        AmazonFPSClient fpsClient = new AmazonFPSClient(awsAccessKey, awsSecretKey);
        CancelTokenRequest cancelTokenRequest = new CancelTokenRequest();
        cancelTokenRequest.setTokenId(tokenId);
        fpsClient.cancelToken(cancelTokenRequest);
    }

    /**
     * Returns the authorization URL.
     *
     * @param redirectUrl
     *          the redirect URL defined by the client of this component
     * @param paymentId
     *          the payment id
     * @param authorizationId
     *          the authorization id
     * @param reservation
     *          defines whether payment or reservation is requested
     * @param multiUseAuthorization
     *          defines whether single-use or multi-use authorization is used
     * @param totalChargesThreshold
     *          the total charges threshold
     * @param paymentAmount
     *          the payment amount
     *
     * @return the authorization URL
     *
     * @throws MalformedURLException
     *              if URL is malformed
     * @throws SignatureException
     *              if some generic signature error occurred
     * @throws UnsupportedEncodingException
     *              if encoding is unsupported
     */
    private String getAuthorizationUrl(String redirectUrl, long paymentId, long authorizationId, boolean reservation,
        boolean multiUseAuthorization, BigDecimal totalChargesThreshold, BigDecimal paymentAmount)
        throws MalformedURLException, SignatureException, UnsupportedEncodingException {
        // construct a full return URL
        StringBuilder returnUrlBuilder = new StringBuilder(redirectUrl);
        if (redirectUrl.indexOf('?') < 0) {
            returnUrlBuilder.append('?');
        } else {
            returnUrlBuilder.append('&');
        }
        returnUrlBuilder.append("&paymentId=").append(paymentId);
        returnUrlBuilder.append("&reserve=").append(reservation ? "1" : "0");
        String returnUrl = returnUrlBuilder.toString();

        // get the authorization URL
        String callerReference = "AUTHORIZATION_" + authorizationId;
        if (multiUseAuthorization) {
            AmazonFPSMultiUsePipeline multiUsePipeline = new AmazonFPSMultiUsePipeline(awsAccessKey, awsSecretKey);
            String globalAmountLimit = totalChargesThreshold.toPlainString();
            multiUsePipeline.setMandatoryParameters(callerReference, returnUrl, globalAmountLimit);
            multiUsePipeline.addParameter("paymentMethod", paymentMethods);
            return multiUsePipeline.getUrl();
        } else {
            AmazonFPSSingleUsePipeline singleUsePipeline = new AmazonFPSSingleUsePipeline(awsAccessKey, awsSecretKey);
            String transactionAmount = paymentAmount.toPlainString();
            singleUsePipeline.setMandatoryParameters(callerReference, returnUrl, transactionAmount);
            singleUsePipeline.addParameter("paymentMethod", paymentMethods);
            return singleUsePipeline.getUrl();
        }
    }
}
