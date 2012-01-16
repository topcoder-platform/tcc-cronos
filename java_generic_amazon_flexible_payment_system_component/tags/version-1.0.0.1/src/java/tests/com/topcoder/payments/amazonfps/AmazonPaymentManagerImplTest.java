/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.PropertyTypeMismatchException;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.payments.amazonfps.model.Authorization;
import com.topcoder.payments.amazonfps.model.Payment;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationData;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationRequest;
import com.topcoder.payments.amazonfps.model.PaymentDetails;
import com.topcoder.payments.amazonfps.model.PaymentStatus;
import com.topcoder.payments.amazonfps.persistence.AuthorizationPersistence;
import com.topcoder.payments.amazonfps.persistence.AuthorizationPersistenceException;
import com.topcoder.payments.amazonfps.persistence.PaymentPersistence;
import com.topcoder.payments.amazonfps.persistence.PaymentPersistenceException;
import com.topcoder.payments.amazonfps.persistence.db.MockBaseDatabasePersistence;
import com.topcoder.payments.amazonfps.subscribers.PaymentEventSubscriber;
import com.topcoder.payments.amazonfps.subscribers.jms.JMSAmazonPaymentEventSubscriber;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;

import static org.junit.Assert.assertFalse;

/**
 * <p>
 * Unit tests for {@link AmazonPaymentManagerImpl} class. <br/>
 * </p>
 *
 * @author KennyAlive
 * @version 1.0
 */
public class AmazonPaymentManagerImplTest {
    /**
     * Configuration namespace for configuring MockBaseDatabasePersistence (to get connections for testing).
     */
    private static final String TEST_CONNECTION_CONFIGURATION = "PersistenceTest";

    /**
     * Constant for alternative configuration filepath.
     */
    private static final String NOT_DEFAULT_CONFIGURATION = "test.properties";

    /**
     * Constant for alternative configuration namespace.
     */
    private static final String NOT_DEFAULT_NAMESPACE = "myNamespace";

    /**
     * Constant for invalid configuration namespace.
     * Object factory is note defined.
     */
    private static final String INVALID_CONFIG_NAMESPACE_1 = "myInvalidConfig1";
    /**
     * Constant for invalid configuration namespace.
     * authorizationPersistenceKey is missing.
     */
    private static final String INVALID_CONFIG_NAMESPACE_2 = "myInvalidConfig2";
    /**
     * Constant for invalid configuration namespace.
     * authorizationPersistenceConfig is missing.
     */
    private static final String INVALID_CONFIG_NAMESPACE_3 = "myInvalidConfig3";
    /**
     * Constant for invalid configuration namespace.
     * paymentPersistenceKey is missing.
     */
    private static final String INVALID_CONFIG_NAMESPACE_4 = "myInvalidConfig4";
    /**
     * Constant for invalid configuration namespace.
     * paymentPersistenceConfig is missing.
     */
    private static final String INVALID_CONFIG_NAMESPACE_5 = "myInvalidConfig5";
    /**
     * Constant for invalid configuration namespace.
     * awsAccessKey is missing.
     */
    private static final String INVALID_CONFIG_NAMESPACE_6 = "myInvalidConfig6";
    /**
     * Constant for invalid configuration namespace.
     * awsSecretKey is missing.
     */
    private static final String INVALID_CONFIG_NAMESPACE_7 = "myInvalidConfig7";
    /**
     * Constant for invalid configuration namespace.
     * paymentMethods is missing.
     */
    private static final String INVALID_CONFIG_NAMESPACE_8 = "myInvalidConfig8";
    /**
     * Constant for invalid configuration namespace.
     * authorizationPersistenceKey is an empty.
     */
    private static final String INVALID_CONFIG_NAMESPACE_9 = "myInvalidConfig9";
    /**
     * Constant for invalid configuration namespace.
     * paymentPersistenceKey is an empty.
     */
    private static final String INVALID_CONFIG_NAMESPACE_10 = "myInvalidConfig10";
    /**
     * Constant for invalid configuration namespace.
     * awsAccessKey is an empty.
     */
    private static final String INVALID_CONFIG_NAMESPACE_11 = "myInvalidConfig11";
    /**
     * Constant for invalid configuration namespace.
     * awsSecretKey is an empty.
     */
    private static final String INVALID_CONFIG_NAMESPACE_12 = "myInvalidConfig12";
    /**
     * Constant for invalid configuration namespace.
     * unknown payment method
     */
    private static final String INVALID_CONFIG_NAMESPACE_13 = "myInvalidConfig13";
    /**
     * Constant for invalid configuration namespace.
     * incorrect class for authorization persistence: causes ClassCastException.
     */
    private static final String INVALID_CONFIG_NAMESPACE_14 = "myInvalidConfig14";
    /**
     * Constant for invalid configuration namespace.
     * unknown class for authorization persistence: causes InvalidClassSpecificationException.
     */
    private static final String INVALID_CONFIG_NAMESPACE_15 = "myInvalidConfig15";


    /**
     * Constant used for testing. Represent redirect url.
     */
    private static final String REDIRECT_URL = "http://www.google.com";

    /**
     * Constant for paymentId request parameter.
     */
    private static final String PAYMENT_ID = "paymentId";
    /**
     * Constant for reserve request parameter.
     */
    private static final String RESERVE = "reserve";
    /**
     * Constant for status request parameter.
     */
    private static final String STATUS = "status";
    /**
     * Constant for tokenID request parameter.
     */
    private static final String TOKEN_ID = "tokenID";

    /**
     * Constant used for testing. Represents amount.
     */
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(12.0);

    /**
     * Constant used for testing. Represents total future charges threshold.
     */
    private static final BigDecimal TOTAL_CHARGES_THRESHOLD = BigDecimal.valueOf(1000000.0);

    /**
     * Delay between successive operations on the same payment. When issuing several payment operations in order
     * on the same payment Amazon can refuse one of these operations since the previous one has not finished and
     * some resource is locked. To have some guarantee that tests will run successfully when need this delay constant.
     * This should be increased if for test session failures in payment operations take place.
     * The delay is used only in few places, so in general it takes not too much time.
     */
    private static final long PAYMENT_OPERATION_DELAY = 5000;

    /**
     * Payment authorization request data for testing.
     */
    private PaymentAuthorizationRequest authorizationRequest;

    /**
     * Payment details for testing.
     */
    private PaymentDetails paymentDetails;

    /**
     * Payment parameters for testing.
     */
    private Map<String, String> parameters;

    /**
     * Request parameters for testing.
     */
    private Map<Object, Object> requestParams;

    /**
     * Multi-use tokenId used for testing.
     */
    private String multiUseTokenId;

    /**
     * The {@code MockBaseDatabasePersistence} instance used for getting database connection
     * for testing, since this mock class exposes public getConnection() method which is what we need.
     */
    private MockBaseDatabasePersistence testConnectionProvider;

    /**
     * Represents the Connection used in tests.
     */
    private Connection connection;

    /**
     * Authorization persistence for testing.
     */
    private AuthorizationPersistence authorizationPersistence;

    /**
     * Payment persistence for testing.
     */
    private PaymentPersistence paymentPersistence;

    /**
     * The {@code AmazonPaymentManagerImpl} instance used for testing.
     */
    private AmazonPaymentManagerImpl instance;

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AmazonPaymentManagerImplTest.class);
    }

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
     // create object factory defined in configuration of AmazonPaymentManagerImpl
        ConfigurationObject configuration = getConfiguration(
                AmazonPaymentManagerImpl.DEFAULT_CONFIG_FILENAME,
                AmazonPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
        ConfigurationObject objectFactoryConfig = configuration.getChild("objectFactoryConfig");
        ConfigurationObjectSpecificationFactory cosf =
                new ConfigurationObjectSpecificationFactory(objectFactoryConfig);
        ObjectFactory objectFactory = new ObjectFactory(cosf);

        testConnectionProvider = new MockBaseDatabasePersistence();
        testConnectionProvider.configure(TestHelper.getConfiguration(TEST_CONNECTION_CONFIGURATION));

        connection = testConnectionProvider.createConnection();
        TestHelper.clearDB(connection);

        // create authorization and payment persistence for testing
        authorizationPersistence = createAuthorizationPersistence(objectFactory, configuration);
        paymentPersistence = createPaymentPersistence(objectFactory, configuration);

        instance = new AmazonPaymentManagerImpl();

        Properties properties = TestHelper.loadProperties("tokenid.properties");
        multiUseTokenId = properties.getProperty("multiUseTokenId");

        requestParams = new HashMap<Object, Object>();
        requestParams.put("paymentId", new String[] {"1"});
        requestParams.put("reserve", new String[] {"0"});
        requestParams.put("status", new String[] {"SA"});
        requestParams.put("tokenID", new String[] {multiUseTokenId});

        parameters = new HashMap<String, String>();
        parameters.clear();
        parameters.put("key1", "value1");
        parameters.put("key2", "value2");
        parameters.put("key3", "value3");

        paymentDetails = new PaymentDetails();
        paymentDetails.setAmount(AMOUNT);
        paymentDetails.setParameters(parameters);

        authorizationRequest = new PaymentAuthorizationRequest();
        authorizationRequest.setRedirectUrl(REDIRECT_URL);
        authorizationRequest.setPaymentDetails(paymentDetails);
        authorizationRequest.setTotalChargesThreshold(null);
    }

    /**
     * Cleans up the test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        TestHelper.clearDB(connection);
        connection.close();
        connection = null;
    }

    //-------------------------------------------------------------------------
    // constructors tests
    //-------------------------------------------------------------------------

    //---------------------- AmazonPaymentManagerImpl() ------------------------------------
    /**
     * Accuracy test for AmazonPaymentManagerImpl no-args constructor. <br/>
     * Check that field are initialized according to configuration constraints.
     */
    @Test
    public void test_constructorAmazonPaymentManagerImpl_noArgs() {
        validateAmazonPaymentMangerManagerImplConstruction(instance);
    }

    //--------- AmazonPaymentManagerImpl(String filePath, String namespace) Tests  -------------
    /**
     * Accuracy test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     * Check that field are initialize according to configuration constraints.
     */
    @Test
    public void test_constructorAmazonPaymentManagerImpl_filePathAndNamespace() {
        AmazonPaymentManagerImpl instance2 =
                new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, NOT_DEFAULT_NAMESPACE);

        validateAmazonPaymentMangerManagerImplConstruction(instance2);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! Object factory configuration is missing. !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_1() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_1);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! authorizationPersistenceKey is missing. !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_2() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_2);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! authorizationPersistenceConfig is missing. !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_3() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_3);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! paymentPersistenceKey is missing. !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_4() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_4);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! paymentPersistenceConfig is missing. !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_5() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_5);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! awsAccessKey is missing. !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_6() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_6);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! awsSecretKey is missing. !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_7() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_7);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! paymentMethods is missing. !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_8() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_8);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! authorizationPersistenceKey is an empty. !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_9() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_9);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! paymentPersistenceKey is an empty. !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_10() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_10);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! awsAccessKey is an empty. !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_11() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_11);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! awsSecretKey is an empty. !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_12() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_12);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! unknown payment method. !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_13() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_13);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! incorrect class for authorization persistence: causes ClassCastException !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_14() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_14);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! unknown class for authorization persistence: causes InvalidClassSpecificationException  !!!
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_15() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_15);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! null filename. !!!
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_16() {
        new AmazonPaymentManagerImpl(null, NOT_DEFAULT_NAMESPACE);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! empty filename. !!!
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_17() {
        new AmazonPaymentManagerImpl("  ", NOT_DEFAULT_NAMESPACE);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! null namespace. !!!
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_18() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, null);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration filepath
     * and configuration namespace. <br/>
     *
     * !!! empty namespace. !!!
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_filePathAndNamespace_19() {
        new AmazonPaymentManagerImpl(NOT_DEFAULT_CONFIGURATION, "  ");
    }

    //--------- AmazonPaymentManagerImpl(ConfigurationObject configuration) Tests  -------------
    /**
     * Accuracy test for AmazonPaymentManagerImpl constructor which accepts configuration object. <br/>
     * Check that field are initialize according to configuration constraints.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_constructorAmazonPaymentManagerImpl_configurationObject() throws Exception {
        AmazonPaymentManagerImpl instance2 =
                new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, NOT_DEFAULT_NAMESPACE));

        validateAmazonPaymentMangerManagerImplConstruction(instance2);
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! Object factory configuration is missing. !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_1() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_1));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! authorizationPersistenceKey is missing. !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_2() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_2));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! authorizationPersistenceConfig is missing. !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_3() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_3));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! paymentPersistenceKey is missing. !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_4() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_4));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! paymentPersistenceConfig is missing. !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_5() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_5));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! awsAccessKey is missing. !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_6() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_6));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! awsSecretKey is missing. !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_7() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_7));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! paymentMethods is missing. !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_8() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_8));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! authorizationPersistenceKey is an empty. !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_9() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_9));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! paymentPersistenceKey is an empty. !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_10() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_10));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! awsAccessKey is an empty. !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_11() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_11));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! awsSecretKey is an empty. !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_12() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_12));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! unknown payment method. !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_13() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_13));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! incorrect class for authorization persistence: causes ClassCastException !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_14() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_14));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * !!! unknown class for authorization persistence: causes InvalidClassSpecificationException  !!!
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_15() throws Exception {
        new AmazonPaymentManagerImpl(getConfiguration(NOT_DEFAULT_CONFIGURATION, INVALID_CONFIG_NAMESPACE_15));
    }

    /**
     * Failure test for AmazonPaymentManagerImpl constructor which accepts configuration object.
     *
     * null configuration object
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_constructorAmazonPaymentManagerImplFailure_configurationObject_16() throws Exception {
        new AmazonPaymentManagerImpl(null);
    }


    /**
     * Checks that AmazonPaymentManagerImpl instance was constructed correctly according to
     * configuration restrictions.
     *
     * @param instance
     *              the AmazonPaymentManagerImpl instance
     */
    private static void validateAmazonPaymentMangerManagerImplConstruction(AmazonPaymentManagerImpl instance) {
        // 1. authorization persistence should be provided
        AuthorizationPersistence authorizationPersistence =
                (AuthorizationPersistence) TestHelper.getField(instance, "authorizationPersistence");
        assertNotNull("The authorization persistence should not be null", authorizationPersistence);

        // 2. payment persistence should be provided
        PaymentPersistence paymentPersistence =
                (PaymentPersistence) TestHelper.getField(instance, "paymentPersistence");
        assertNotNull("The payment persistence should not be null", paymentPersistence);

        // 3. awsAccessKey is required
        String awsAccessKey = (String) TestHelper.getField(instance, "awsAccessKey");
        assertNotNull("The awsAccessKey should not be null", awsAccessKey);
        assertFalse("The awsAccessKey should not be empty", awsAccessKey.isEmpty());

        // 4. awsSecretKey is required
        String awsSecretKey = (String) TestHelper.getField(instance, "awsSecretKey");
        assertNotNull("The awsSecretKey should not be null", awsSecretKey);
        assertFalse("The awsSecretKey should not be empty", awsSecretKey.isEmpty());

        // 5. paymentMethods is required
        String paymentMethods = (String) TestHelper.getField(instance, "paymentMethods");
        assertNotNull("The paymentMethods should not be null", paymentMethods);
        assertFalse("The awsSecretKey should not be empty", paymentMethods.isEmpty());

        // 6. Subscribers list should not be null but can be empty. Can not contain null values.
        @SuppressWarnings("unchecked")
        List<PaymentEventSubscriber> subscribers =
            (List<PaymentEventSubscriber>) TestHelper.getField(instance, "subscribers");
        assertNotNull("The subscribers should not be null", subscribers);
        for (PaymentEventSubscriber subscriber : subscribers) {
            assertNotNull("the subscriber should not be null", subscriber);
        }

        // 7. Logger is optional: can be any value, don't check it
    }

    //-------------------------------------------------------------------------
    // initiatePaymentAuthorization tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for initiatePaymentAuthorization method. <br/>
     * Do not set optional parameters for PaymentAuthorizationRequest: Amazon FPS Single-use pipeline will be used.
     *
     * @throws Exception toJUnit
     */
    @Test
    public void test_initiatePaymentAuthorization_1() throws Exception {
        PaymentAuthorizationData result = instance.initiatePaymentAuthorization(authorizationRequest);

        assertNotNull("The authorization data should not be null", result);
        assertTrue("Authorization id should be set", result.getAuthorizationId() > 0L);
        assertTrue("Payment id should be set", result.getPaymentId() > 0L);
        assertNotNull("authorizationUrl should not be null", result.getAuthorizationUrl());
        assertFalse("authorizationUrl should not be empty", result.getAuthorizationUrl().trim().isEmpty());
    }

    /**
     * Accuracy test for initiatePaymentAuthorization method. <br/>
     * Set optional parameters for PaymentAuthorizationRequest: Amazon FPS Multi-use pipeline will be used.
     *
     * @throws Exception toJUnit
     */
    @Test
    public void test_initiatePaymentAuthorization_2() throws Exception {
        authorizationRequest.setFutureChargesAuthorizationRequired(true);
        authorizationRequest.setTotalChargesThreshold(TOTAL_CHARGES_THRESHOLD);
        PaymentAuthorizationData result = instance.initiatePaymentAuthorization(authorizationRequest);

        assertNotNull("The authorization data should not be null", result);
        assertTrue("Authorization id should be set", result.getAuthorizationId() > 0L);
        assertTrue("Payment id should be set", result.getPaymentId() > 0L);
        assertNotNull("authorizationUrl should not be null", result.getAuthorizationUrl());
        assertFalse("authorizationUrl should not be empty", result.getAuthorizationUrl().trim().isEmpty());
    }

    /**
     * Failure test for initiatePaymentAuthorization method. <br/>
     * Pass null request data.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_initiatePaymentAuthorizationFailure_1() throws Exception {
        instance.initiatePaymentAuthorization(null);
    }

    /**
     * Failure test for initiatePaymentAuthorization method. <br/>
     * null requestUrl.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_initiatePaymentAuthorizationFailure_2() throws Exception {
        authorizationRequest.setRedirectUrl(null);
        instance.initiatePaymentAuthorization(authorizationRequest);
    }

    /**
     * Failure test for initiatePaymentAuthorization method. <br/>
     * empty requestUrl.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_initiatePaymentAuthorizationFailure_3() throws Exception {
        authorizationRequest.setRedirectUrl("  ");
        instance.initiatePaymentAuthorization(authorizationRequest);
    }

    /**
     * Failure test for initiatePaymentAuthorization method. <br/>
     * null payment details.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_initiatePaymentAuthorizationFailure_4() throws Exception {
        authorizationRequest.setPaymentDetails(null);
        instance.initiatePaymentAuthorization(authorizationRequest);
    }

    /**
     * Failure test for initiatePaymentAuthorization method. <br/>
     * null payment amount.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_initiatePaymentAuthorizationFailure_5() throws Exception {
        authorizationRequest.getPaymentDetails().setAmount(null);
        instance.initiatePaymentAuthorization(authorizationRequest);
    }

    /**
     * Failure test for initiatePaymentAuthorization method. <br/>
     * negative payment amount.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_initiatePaymentAuthorizationFailure_6() throws Exception {
        authorizationRequest.getPaymentDetails().setAmount(BigDecimal.valueOf(-100));
        instance.initiatePaymentAuthorization(authorizationRequest);
    }

    /**
     * Failure test for initiatePaymentAuthorization method. <br/>
     * null key in payment parameters  map is present.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_initiatePaymentAuthorizationFailure_7() throws Exception {
        authorizationRequest.getPaymentDetails().getParameters().put(null, "value");
        instance.initiatePaymentAuthorization(authorizationRequest);
    }

    /**
     * Failure test for initiatePaymentAuthorization method. <br/>
     * this condition: request.isFutureChargesAuthorizationRequired() and request.getTotalChargesThreshold() is null.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_initiatePaymentAuthorizationFailure_8() throws Exception {
        authorizationRequest.setFutureChargesAuthorizationRequired(true);
        instance.initiatePaymentAuthorization(authorizationRequest);
    }

    /**
     * Failure test for initiatePaymentAuthorization method. <br/>
     * this condition: request.getTotalChargesThreshold() not null and request.getTotalChargesThreshold() is not
     * positive.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_initiatePaymentAuthorizationFailure_9() throws Exception {
        authorizationRequest.setTotalChargesThreshold(BigDecimal.valueOf(-10));
        instance.initiatePaymentAuthorization(authorizationRequest);
    }

    /**
     * Failure test for initiatePaymentAuthorization method. <br/>
     * this condition: request.getFutureChargesFixedAmount() not null and request.getFutureChargesFixedAmount() is
     * not positive.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_initiatePaymentAuthorizationFailure_10() throws Exception {
        authorizationRequest.setFutureChargesFixedAmount(BigDecimal.valueOf(-30));
        instance.initiatePaymentAuthorization(authorizationRequest);
    }

    //-------------------------------------------------------------------------
    // handleRequestFromCoBrandedService tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for handleRequestFromCoBrandedService method. <br/>
     * Authorization failure: status is not one of: SA, SB, SC. Token id should not be stored in persistence.
     *
     * @throws Exception toJUnit
     */
    @Test
    public void test_handleRequestFromCoBrandedService_1() throws Exception {
        Payment payment = createAuthorizationAndPayment(false);

        requestParams.put("paymentId", new String[] {"" + payment.getId()});
        requestParams.put("status", new String[] {"X"});
        requestParams.put("warningMessage", new String[] {"it's some warning!!!"});

        instance.handleRequestFromCoBrandedService(requestParams);

        Authorization authorization = authorizationPersistence.getAuthorization(payment.getAuthorizationId());
        assertNull("tokenId should not be stored to persistence", authorization.getTokenId());
    }

    /**
     * Accuracy test for handleRequestFromCoBrandedService method. <br/>
     * Authorization success: status is one of: SA, SB, SC. Check that token id was stored in persistence.
     *
     * @throws Exception toJUnit
     */
    @Test
    public void test_handleRequestFromCoBrandedService_2() throws Exception {
        Payment payment = createAuthorizationAndPayment(false);

        requestParams.put("paymentId", new String[] {"" + payment.getId()});
        requestParams.put("status", new String[] {"SC"});

        instance.handleRequestFromCoBrandedService(requestParams);

        Authorization authorization = authorizationPersistence.getAuthorization(payment.getAuthorizationId());
        assertNotNull("tokenId should be stored to persistence", authorization.getTokenId());
    }

    /**
     * Accuracy test for handleRequestFromCoBrandedService method. <br/>
     * Make payment using Amazon FPS Sandbox.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_handleRequestFromCoBrandedService_3() throws Exception {
        Payment payment = createAuthorizationAndPayment(false);

        requestParams.put("paymentId", new String[] {"" + payment.getId()});
        requestParams.put("warningMessage", new String[] {"just to put this warning into the log"});
        requestParams.put("errorMessage", new String[] {"some error message just for testing"});

        instance.handleRequestFromCoBrandedService(requestParams);

        // check that payment status was updated
        payment = paymentPersistence.getPayment(payment.getId());
        assertEquals("Payment status should be COMPLETED", PaymentStatus.COMPLETED, payment.getStatus());

        // check that we spent some money
        Authorization authorization = authorizationPersistence.getAuthorization(payment.getAuthorizationId());
        BigDecimal newAmount = TOTAL_CHARGES_THRESHOLD.subtract(AMOUNT);
        assertEquals("New amount should be correct", newAmount, authorization.getAuthorizedAmountLeft());
    }

    /**
     * Accuracy test for handleRequestFromCoBrandedService method. <br/>
     * Make reservation using Amazon FPS Sandbox.
     *
     * @throws Exception toJUnit
     */
    @Test
    public void test_handleRequestFromCoBrandedService_4() throws Exception {
        Payment payment = createAuthorizationAndPayment(false);

        requestParams.put("paymentId", new String[] {"" + payment.getId()});
        requestParams.put("reserve", new String[] {"1"});
        requestParams.put("errorMessage", new String[0]); // this should work - means no error message
        requestParams.put("warningMessage", new String[0]); // this should work - means no warning message

        instance.handleRequestFromCoBrandedService(requestParams);

        // check that payment status was updated
        payment = paymentPersistence.getPayment(payment.getId());
        assertEquals("Payment status should be RESERVED", PaymentStatus.RESERVED, payment.getStatus());

        // check that we spent some money
        Authorization authorization = authorizationPersistence.getAuthorization(payment.getAuthorizationId());
        BigDecimal newAmount = TOTAL_CHARGES_THRESHOLD.subtract(AMOUNT);
        assertEquals("New amount should be correct", newAmount, authorization.getAuthorizedAmountLeft());
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Pass null request parameters.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_1() throws Exception {
        instance.handleRequestFromCoBrandedService(null);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Do not specify payment id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_2() throws Exception {
        requestParams.remove(PAYMENT_ID);
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Do not specify reserve parameter.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_3() throws Exception {
        requestParams.remove(RESERVE);
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Do not specify status parameter.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_4() throws Exception {
        requestParams.remove(STATUS);
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Do not specify tokenID parameter.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_5() throws Exception {
        requestParams.remove(TOKEN_ID);
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Set incorrect payment id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_6() throws Exception {
        requestParams.put(PAYMENT_ID, new String[] {"123bad"});
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Set incorrect reserve (should be 0 or 1).
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_7() throws Exception {
        requestParams.put(RESERVE, new String[] {"2"});
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Set not existed payment id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_8() throws Exception {
        requestParams.put(PAYMENT_ID, new String[] {"123111"});
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Set empty array for payment id parameter.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_9() throws Exception {
        requestParams.put(PAYMENT_ID, new String[0]);
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Set null array for payment id parameter.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_10() throws Exception {
        requestParams.put(PAYMENT_ID, null);
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Set negative payment id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_11() throws Exception {
        requestParams.put(PAYMENT_ID, new String[] {"-123"});
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Set null array for status parameter.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_12() throws Exception {
        requestParams.put(PAYMENT_ID, new String[] {"1"});
        requestParams.put(STATUS, null);
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Set empty array for status parameter.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_13() throws Exception {
        requestParams.put(PAYMENT_ID, new String[] {"1"});
        requestParams.put(STATUS, new String[0]);
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Set null array for tokenID parameter.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_14() throws Exception {
        requestParams.put(PAYMENT_ID, new String[] {"1"});
        requestParams.put(TOKEN_ID, null);
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Set empty array for tokenID parameter.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_15() throws Exception {
        requestParams.put(PAYMENT_ID, new String[] {"1"});
        requestParams.put(TOKEN_ID, new String[0]);
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Set null array for reserve parameter.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_16() throws Exception {
        requestParams.put(PAYMENT_ID, new String[] {"1"});
        requestParams.put(RESERVE, null);
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    /**
     * Failure test for handleRequestFromCoBrandedService method. <br/>
     * Set empty array for reserve parameter.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentManagementException.class)
    public void test_handleRequestFromCoBrandedServiceFailure_17() throws Exception {
        requestParams.put(PAYMENT_ID, new String[] {"1"});
        requestParams.put(RESERVE, new String[0]);
        instance.handleRequestFromCoBrandedService(requestParams);
    }

    //-------------------------------------------------------------------------
    // processAuthorizedPayment tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for processAuthorizedPayment method. <br/>
     * for null token id processAuthorizedPayment() operation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processAuthorizedPayment_1() throws Exception {
        Payment payment = createAuthorizationAndPayment(false);

        long paymentId = instance.processAuthorizedPayment(payment.getAuthorizationId(), paymentDetails);

        // check that payment failed
        payment = paymentPersistence.getPayment(paymentId);
        assertEquals("Payment status should be FAILED", PaymentStatus.FAILED, payment.getStatus());
    }

    /**
     * Accuracy test for processAuthorizedPayment method. <br/>
     * The common situation when client don't have enough money. Check that payment failed.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processAuthorizedPayment_2() throws Exception {
        Payment payment = createAuthorizationAndPayment(true);

        paymentDetails.setAmount(BigDecimal.valueOf(50000000));
        long paymentId = instance.processAuthorizedPayment(payment.getAuthorizationId(), paymentDetails);

        // check that payment failed
        payment = paymentPersistence.getPayment(paymentId);
        assertEquals("Payment status should be FAILED", PaymentStatus.FAILED, payment.getStatus());
    }

    /**
     * Accuracy test for processAuthorizedPayment method. <br/>
     * The common situation when client don't have enough money. Check that reservation failed.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processAuthorizedPayment_22() throws Exception {
        Payment payment = createAuthorizationAndPayment(true);

        paymentDetails.setAmount(BigDecimal.valueOf(50000000));
        paymentDetails.setReservation(true);
        long paymentId = instance.processAuthorizedPayment(payment.getAuthorizationId(), paymentDetails);

        // check that payment failed
        payment = paymentPersistence.getPayment(paymentId);
        assertEquals("Payment status should be FAILED", PaymentStatus.FAILED, payment.getStatus());
    }

    /**
     * Accuracy test for processAuthorizedPayment method. <br/>
     * Incorrect amount of fixed payment. Check that operation failed.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processAuthorizedPayment_3() throws Exception {
        Payment payment = createAuthorizationAndPayment(true);

        Authorization authorization = authorizationPersistence.getAuthorization(payment.getAuthorizationId());
        authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(50));
        authorizationPersistence.updateAuthorization(authorization);

        paymentDetails.setAmount(BigDecimal.valueOf(100));
        long paymentId = instance.processAuthorizedPayment(authorization.getId(), paymentDetails);

        // check that payment failed
        payment = paymentPersistence.getPayment(paymentId);
        assertEquals("Payment status should be FAILED", PaymentStatus.FAILED, payment.getStatus());
    }

    /**
     * Accuracy test for processAuthorizedPayment method. <br/>
     * Make successful payment using Amazon FPS Sandbox. check that we spent some money
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processAuthorizedPayment_4() throws Exception {
        Payment payment = createAuthorizationAndPayment(true);

        long paymentId = instance.processAuthorizedPayment(payment.getAuthorizationId(), paymentDetails);

        // check that payment status was updated
        payment = paymentPersistence.getPayment(paymentId);
        assertEquals("Payment status should be COMPLETED", PaymentStatus.COMPLETED, payment.getStatus());

        // check that we spent some money
        Authorization authorization = authorizationPersistence.getAuthorization(payment.getAuthorizationId());
        BigDecimal newAmount = TOTAL_CHARGES_THRESHOLD.subtract(AMOUNT);
        assertEquals("New amount should be correct", newAmount, authorization.getAuthorizedAmountLeft());
    }

    /**
     * Accuracy test for processAuthorizedPayment method. <br/>
     * Make successful reservation using Amazon FPS Sandbox: check that we spent some money
     *
     * @throws Exception toJUnit
     */
    @Test
    public void test_processAuthorizedPayment_5() throws Exception {
        Payment payment = createAuthorizationAndPayment(true);

        paymentDetails.setReservation(true);
        long paymentId = instance.processAuthorizedPayment(payment.getAuthorizationId(), paymentDetails);

        // check that payment status was updated
        payment = paymentPersistence.getPayment(paymentId);
        assertEquals("Payment status should be RESERVED", PaymentStatus.RESERVED, payment.getStatus());

        // check that we spent some money
        Authorization authorization = authorizationPersistence.getAuthorization(payment.getAuthorizationId());
        BigDecimal newAmount = TOTAL_CHARGES_THRESHOLD.subtract(AMOUNT);
        assertEquals("New amount should be correct", newAmount, authorization.getAuthorizedAmountLeft());
    }

    /**
     * Accuracy test for processAuthorizedPayment method. <br/>
     * Check that operation fails for singl-use authorization.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processAuthorizedPayment_6() throws Exception {
        Payment payment = createAuthorizationAndPayment(true);
        Authorization authorization = authorizationPersistence.getAuthorization(payment.getAuthorizationId());
        authorization.setMultipleUseAuthorization(false);
        authorizationPersistence.updateAuthorization(authorization);

        long paymentId = instance.processAuthorizedPayment(payment.getAuthorizationId(), paymentDetails);

        // check that payment failed
        payment = paymentPersistence.getPayment(paymentId);
        assertEquals("Payment status should be FAILED", PaymentStatus.FAILED, payment.getStatus());
    }

    /**
     * Failure test for processAuthorizedPayment method. <br/>
     * Pass zero authorization id.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_processAuthorizedPaymentFailure_1() throws Exception {
        instance.processAuthorizedPayment(0L, paymentDetails);
    }

    /**
     * Failure test for processAuthorizedPayment method. <br/>
     * Pass negative authorization id.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_processAuthorizedPaymentFailure_2() throws Exception {
        instance.processAuthorizedPayment(-1L, paymentDetails);
    }

    /**
     * Failure test for processAuthorizedPayment method. <br/>
     * null payment details.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_processAuthorizedPaymentFailure_3() throws Exception {
        instance.processAuthorizedPayment(1L, null);
    }

    /**
     * Failure test for processAuthorizedPayment method. <br/>
     * negative amount.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_processAuthorizedPaymentFailure_4() throws Exception {
        paymentDetails.setAmount(BigDecimal.valueOf(-20));
        instance.processAuthorizedPayment(2L, paymentDetails);
    }

    /**
     * Failure test for processAuthorizedPayment method. <br/>
     * parameters contains null key.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_processAuthorizedPaymentFailure_5() throws Exception {
        paymentDetails.getParameters().put(null, "value2");
        instance.processAuthorizedPayment(4L, paymentDetails);
    }

    /**
     * Failure test for processAuthorizedPayment method. <br/>
     * authorization with the given id doesn't exist.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = AuthorizationNotFoundException.class)
    public void test_processAuthorizedPaymentFailure_6() throws Exception {
        PaymentAuthorizationData result = instance.initiatePaymentAuthorization(authorizationRequest);
        instance.processAuthorizedPayment(result.getAuthorizationId() + 1, paymentDetails);
    }

    //-------------------------------------------------------------------------
    // cancelPayment tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for cancelPayment method. <br/>
     * We can not cancel not initiated payment.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_cancelPayment_1() throws Exception {
        Payment payment = createAuthorizationAndPayment(true);

        instance.cancelPayment(payment.getId());
        payment = paymentPersistence.getPayment(payment.getId());

        // check that payment status has not been changed
        assertEquals("payment status should be NOT_INITIATED", PaymentStatus.NOT_INITIATED, payment.getStatus());
    }

    /**
     * Accuracy test for cancelPayment method. <br/>
     * Check that we can cancel reserved payment.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_cancelPayment_2() throws Exception {
        Payment payment = createAuthorizationAndPayment(true);

        paymentDetails.setReservation(true);
        long paymentId = instance.processAuthorizedPayment(payment.getAuthorizationId(), paymentDetails);

        payment = paymentPersistence.getPayment(paymentId);
        assertEquals("payment status should be RESERVED", PaymentStatus.RESERVED, payment.getStatus());

        Thread.sleep(PAYMENT_OPERATION_DELAY);
        instance.cancelPayment(paymentId);

        payment = paymentPersistence.getPayment(paymentId);
        assertEquals("payment status should be CANCELLED", PaymentStatus.CANCELLED, payment.getStatus());
    }

    /**
     * Failure test for cancelPayment method. <br/>
     * Pass zero payment id.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_cancelPaymentFailure_1() throws Exception {
        instance.cancelPayment(0L);
    }

    /**
     * Failure test for cancelPayment method. <br/>
     * Pass negative payment id.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_cancelPaymentFailure_2() throws Exception {
        instance.cancelPayment(-5L);
    }

    /**
     * Failure test for cancelPayment method. <br/>
     * Payment with the given id doesn't exist
     *
     * @throws Exception toJUnit
     */
    @Test (expected = PaymentNotFoundException.class)
    public void test_cancelPaymentFailure_3() throws Exception {
        instance.cancelPayment(10L);
    }

    //-------------------------------------------------------------------------
    // settlePayment tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for settlePayment method. <br/>
     * Check that if payment is not reserved (for example, not initiated) then we can not settle it.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_settlePayment_1() throws Exception {
        Payment payment = createAuthorizationAndPayment(true);

        instance.settlePayment(payment.getId());
        payment = paymentPersistence.getPayment(payment.getId());

        // check that payment status has not been changed
        assertEquals("payment status should be NOT_INITIATED", PaymentStatus.NOT_INITIATED, payment.getStatus());
    }

    /**
     * Accuracy test for settlePayment method. <br/>
     * At first reserve payment, then settle it. Check that payment is completed.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_settlePayment_2() throws Exception {
        Payment payment = createAuthorizationAndPayment(true);

        paymentDetails.setReservation(true);
        long paymentId = instance.processAuthorizedPayment(payment.getAuthorizationId(), paymentDetails);

        payment = paymentPersistence.getPayment(paymentId);
        assertEquals("payment status should be RESERVED", PaymentStatus.RESERVED, payment.getStatus());

        Thread.sleep(PAYMENT_OPERATION_DELAY);
        instance.settlePayment(paymentId);

        payment = paymentPersistence.getPayment(paymentId);
        assertEquals("payment status should be COMPLETED", PaymentStatus.COMPLETED, payment.getStatus());
    }

    /**
     * Failure test for settlePayment method. <br/>
     * Pass zero payment id.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_settlePaymentFailure_1() throws Exception {
        instance.settlePayment(0L);
    }

    /**
     * Failure test for settlePayment method. <br/>
     * Pass negative payment id.
     *
     * @throws Exception toJUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_settlePaymentFailure_2() throws Exception {
        instance.settlePayment(-1L);
    }

    /**
     * Failure test for settlePayment method. <br/>
     * Payment with the given id doesn't exist
     *
     * @throws Exception toJUnit
     */
    @Test (expected = PaymentNotFoundException.class)
    public void test_settlePaymentFailure_3() throws Exception {
        instance.settlePayment(1L);
    }

    //-------------------------------------------------------------------------
    // refundPayment tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for refundPayment method. <br/>
     * Check that if payment is not completed (for example, reserved) we can not refund it.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_refundPayment_1() throws Exception {
        Payment payment = createAuthorizationAndPayment(true);

        paymentDetails.setReservation(true);
        long paymentId = instance.processAuthorizedPayment(payment.getAuthorizationId(), paymentDetails);

        payment = paymentPersistence.getPayment(paymentId);
        assertEquals("payment status should be RESERVED", PaymentStatus.RESERVED, payment.getStatus());

        instance.refundPayment(paymentId);

        // check that payment status has not been changed
        payment = paymentPersistence.getPayment(payment.getId());
        assertEquals("payment status should be RESERVED", PaymentStatus.RESERVED, payment.getStatus());
    }

    /**
     * Accuracy test for refundPayment method. <br/>
     * At first complete payment, then refund it. Check that payment is refunded.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_refundPayment_2() throws Exception {
        Payment payment = createAuthorizationAndPayment(true);
        long paymentId = instance.processAuthorizedPayment(payment.getAuthorizationId(), paymentDetails);

        payment = paymentPersistence.getPayment(paymentId);
        assertEquals("payment status should be COMPLETED", PaymentStatus.COMPLETED, payment.getStatus());

        Thread.sleep(PAYMENT_OPERATION_DELAY);
        instance.refundPayment(paymentId);

        payment = paymentPersistence.getPayment(paymentId);
        assertEquals("payment status should be REFUNDED", PaymentStatus.REFUNDED, payment.getStatus());
    }

    /**
     * Failure test for refundPayment method. <br/>
     * Pass zero payment id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_refundPaymentFailure_1() throws Exception {
        instance.refundPayment(0L);
    }

    /**
     * Failure test for refundPayment method. <br/>
     * Pass negative payment id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_refundPaymentFailure_2() throws Exception {
        instance.refundPayment(-2L);
    }

    /**
     * Failure test for refundPayment method. <br/>
     * Payment with the given id doesn't exist
     *
     * @throws Exception to JUnit
     */
    @Test (expected = PaymentNotFoundException.class)
    public void test_refundPaymentFailure_3() throws Exception {
        instance.refundPayment(3L);
    }

    //-------------------------------------------------------------------------
    // cancelAuthorization tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for cancelAuthorization method. <br/>
     * Check that authorization successfully cancelled.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_cancelAuthorization_1() throws Exception {
        // since cancel authorization invalidates tokenID we should use separate token ID for this test,
        // otherwise other tests will be broken.
        final String multiUseTokenId2 = "P6ATQZF6APBEDBMGE736FAYULM9IANHHUJPHPDKINNG7QTQA8H83FSBRXQF3CREQ";

        Authorization authorization = createAuthorization(false);
        authorization.setTokenId(multiUseTokenId2);
        authorizationPersistence.updateAuthorization(authorization);

        assertFalse("Authorization should not be cancelled", authorization.isCancelled());
        instance.cancelAuthorization(authorization.getId());
        authorization = authorizationPersistence.getAuthorization(authorization.getId());
        assertTrue("Authorization should be cancelled", authorization.isCancelled());
    }

    /**
     * Accuracy test for cancelAuthorization method. <br/>
     * Check that when we cancel already cancelled authorization there is no exception and
     * only failure payment event is generated.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_cancelAuthorization_2() throws Exception {
        // since cancel authorization invalidates tokenID we should use separate token ID for this test,
        // otherwise other tests will be broken.
        final String multiUseTokenId2 = "P6ATQZF6APBEDBMGE736FAYULM9IANHHUJPHPDKINNG7QTQA8H83FSBRXQF3CREQ";

        Authorization authorization = createAuthorization(false);
        authorization.setTokenId(multiUseTokenId2);
        authorizationPersistence.updateAuthorization(authorization);

        assertFalse("Authorization should not be cancelled", authorization.isCancelled());
        instance.cancelAuthorization(authorization.getId());
        authorization = authorizationPersistence.getAuthorization(authorization.getId());
        assertTrue("Authorization should be cancelled", authorization.isCancelled());

        // cancel authorization once more - cancel flag should not be changed, there should not be exceptions.
        instance.cancelAuthorization(authorization.getId());
        authorization = authorizationPersistence.getAuthorization(authorization.getId());
        assertTrue("Authorization should be cancelled", authorization.isCancelled());
    }

    /**
     * Failure test for cancelAuthorization method. <br/>
     * Pass zero authorization id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_cancelAuthorizationFailure_1() throws Exception {
        instance.cancelAuthorization(0L);
    }

    /**
     * Failure test for cancelAuthorization method. <br/>
     * Pass negative authorization id.
     *
     * @throws Exception t JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_cancelAuthorizationFailure_2() throws Exception {
        instance.cancelAuthorization(-2L);
    }

    /**
     * Failure test for cancelAuthorization method. <br/>
     * Authorization with the given id doesn't exist.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AuthorizationNotFoundException.class)
    public void test_cancelAuthorizationFailure_3() throws Exception {
        instance.cancelAuthorization(3L);
    }

    //-------------------------------------------------------------------------
    // registerPaymentEventSubscriber tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for registerPaymentEventSubscriber method. <br/>
     * Register new subscriber and check it was added to the list
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_registerPaymentEventSubscriber_1() throws Exception {
        PaymentEventSubscriber subscriber = new JMSAmazonPaymentEventSubscriber();
        @SuppressWarnings("unchecked")
        List<PaymentEventSubscriber> subscribers =
                (List<PaymentEventSubscriber>) TestHelper.getField(instance, "subscribers");
        instance.registerPaymentEventSubscriber(subscriber);
        assertTrue("subscriber was added", subscribers.contains(subscriber));
    }

    /**
     * Failure test for registerPaymentEventSubscriber method. <br/>
     * Pass null subscriber.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_registerPaymentEventSubscriberFailure_1() throws Exception {
        instance.registerPaymentEventSubscriber(null);
    }

    //-------------------------------------------------------------------------
    // getAllAuthorizations/getAllPayments/getPaymentsByAuthorization tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for getAllAuthorizations method. <br/>
     * Empty list should be returned when no authorization is registered.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getAllAuthorizations_1() throws Exception {
        List<Authorization> result = instance.getAllAuthorizations();
        assertTrue("List should be empty", result.isEmpty());
    }

    /**
     * Accuracy test for getAllAuthorizations method. <br/>
     * Create one authorization, check that result is correct
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getAllAuthorizations_2() throws Exception {
        createAuthorization(false);
        List<Authorization> result = instance.getAllAuthorizations();
        assertEquals("There should be 1 authorization", 1, result.size());
    }

    /**
     * Accuracy test for getAllAuthorizations method. <br/>
     * Create two authorizations, check that result is correct
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getAllAuthorizations_3() throws Exception {
        createAuthorization(false);
        createAuthorization(false);
        List<Authorization> result = instance.getAllAuthorizations();
        assertEquals("There should be 2 authorization", 2, result.size());
    }

    /**
     * Accuracy test for {@code getAllPayments} method. <br/>
     * Empty list should be returned if there are no payments.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getAllPayments_1() throws Exception {
        List<Payment> result = instance.getAllPayments();
        assertTrue("List should be empty", result.isEmpty());
    }

    /**
     * Accuracy test for {@code getAllPayments} method. <br/>
     * Create two payments, check that result is correct
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getAllPayments_2() throws Exception {
        createAuthorizationAndPayment(false);
        createAuthorizationAndPayment(false);
        List<Payment> result = instance.getAllPayments();
        assertEquals("There should be 2 payments", 2, result.size());
    }

    /**
     * Accuracy test for {@code getPaymentsByAuthorization} method. <br/>
     * Empty list should be returned if none payments is found.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPaymentsByAuthorization_1() throws Exception {
        List<Payment> result = instance.getPaymentsByAuthorization(123456L);
        assertTrue("List should be empty", result.isEmpty());
    }

    /**
     * Accuracy test for {@code getPaymentsByAuthorization} method. <br/>
     * Create 2 payments for the first authorization and 1 payment for the second,
     * check that result is correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPaymentsByAuthorization_2() throws Exception {
        Payment p1 = createAuthorizationAndPayment(false);
        createPayment(p1.getAuthorizationId());
        Payment p3 = createAuthorizationAndPayment(false);

        List<Payment> result = instance.getPaymentsByAuthorization(p1.getAuthorizationId());
        assertEquals("There should be 2 payments", 2, result.size());

        result = instance.getPaymentsByAuthorization(p3.getAuthorizationId());
        assertEquals("There should be 1 payment", 1, result.size());
    }

    /**
     * Failure test for getPaymentsByAuthorization method. <br/>
     * authorization id is zero.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_getPaymentsByAuthorizationFailure_1() throws Exception {
        instance.getPaymentsByAuthorization(0L);
    }

    /**
     * Failure test for getPaymentsByAuthorization method. <br/>
     * authorization id is negative.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_getPaymentsByAuthorizationFailure_2() throws Exception {
        instance.getPaymentsByAuthorization(-5L);
    }

    //-----------------------------------------------------------------------------
    // Private helper methods
    //-----------------------------------------------------------------------------
    /**
     * Creates authorization instance.
     *
     * @param setToken
     *          defines whether to set token id for the authorization
     *
     * @return the authorization instance
     *
     * @throws AuthorizationPersistenceException
     *              if authorization persistence error occurred
     */
    private Authorization createAuthorization(boolean setToken) throws AuthorizationPersistenceException {
        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(true);
        authorization.setAuthorizedAmountLeft(TOTAL_CHARGES_THRESHOLD);
        authorization.setTokenId(setToken ? multiUseTokenId : null);
        authorizationPersistence.createAuthorization(authorization);
        return authorization;
    }

    /**
     * Creates payment instance.
     *
     * @param authorizationId
     *          the authorization id
     *
     * @return the authorization instance
     *
     * @throws PaymentPersistenceException
     *              if payment persistence error occurred
     */
    private Payment createPayment(long authorizationId) throws PaymentPersistenceException {
        Payment payment = new Payment();
        payment.setAmount(AMOUNT);
        payment.setParameters(parameters);
        payment.setStatus(PaymentStatus.NOT_INITIATED);
        payment.setAuthorizationId(authorizationId);
        paymentPersistence.createPayment(payment);
        return payment;
    }

    /**
     * Creates both authorization and payment instances.
     *
     * @param setToken
     *          defines whether to set token id for the authorization
     *
     * @return the payment instance
     *
     * @throws AuthorizationPersistenceException
     *              if authorization persistence error occurred
     * @throws PaymentPersistenceException
     *              if payment persistence error occurred
     */
    private Payment createAuthorizationAndPayment(boolean setToken)
        throws AuthorizationPersistenceException, PaymentPersistenceException {
        Authorization authorization = createAuthorization(setToken);
        Payment payment = createPayment(authorization.getId());
        return payment;
    }

    /**
     * Helper method for getting the configuration object from the specified namespace.
     *
     * @param filepath
     *          the filepath of the configuration file
     * @param namespace
     *          the configuration namespace
     *
     * @return the first configuration object in the given namespace
     *
     * @throws Exception to JUnit
     */
    private ConfigurationObject getConfiguration(String filepath, String namespace) throws Exception {
        ConfigurationFileManager configurationFileManager = new ConfigurationFileManager(filepath);
        ConfigurationObject configuration = configurationFileManager.getConfiguration(namespace);
        return configuration.getChild(namespace);
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
     * @throws PropertyTypeMismatchException
     *              if failed to get a configuration property as string object
     * @throws ConfigurationAccessException
     *              if error occurred while accessing configuration
     * @throws InvalidClassSpecificationException
     *              if error occurred while creating object by the object factory
     * @throws ClassCastException
     *              if failed to cast the object created by Object Factory to persistence type
     */
    private AuthorizationPersistence createAuthorizationPersistence(ObjectFactory objectFactory,
        ConfigurationObject configuration) throws PropertyTypeMismatchException, ConfigurationAccessException,
        InvalidClassSpecificationException {
        String authorizationPersistenceKey =
                configuration.getPropertyValue("authorizationPersistenceKey", String.class);
        AuthorizationPersistence persistence =
                (AuthorizationPersistence) objectFactory.createObject(authorizationPersistenceKey);
        ConfigurationObject authorizationPersistenceConfig =
                configuration.getChild("authorizationPersistenceConfig");
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
     * @throws PropertyTypeMismatchException
     *              if failed to get a configuration property as string object
     * @throws ConfigurationAccessException
     *              if error occurred while accessing configuration
     * @throws InvalidClassSpecificationException
     *              if error occurred while creating object by the object factory
     * @throws ClassCastException
     *              if failed to cast the object created by Object Factory to persistence type
     */
    private PaymentPersistence createPaymentPersistence(ObjectFactory objectFactory,
        ConfigurationObject configuration) throws PropertyTypeMismatchException, ConfigurationAccessException,
        InvalidClassSpecificationException {
        String paymentPersistenceKey = configuration.getPropertyValue("paymentPersistenceKey", String.class);
        PaymentPersistence persistence = (PaymentPersistence) objectFactory.createObject(paymentPersistenceKey);
        ConfigurationObject paymentPersistenceConfig = configuration.getChild("paymentPersistenceConfig");
        persistence.configure(paymentPersistenceConfig);
        return persistence;
    }
}
