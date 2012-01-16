/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.persistence.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.commons.utils.JDBCUtility;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException;
import com.topcoder.payments.amazonfps.PaymentNotFoundException;
import com.topcoder.payments.amazonfps.TestHelper;
import com.topcoder.payments.amazonfps.model.Authorization;
import com.topcoder.payments.amazonfps.model.Payment;
import com.topcoder.payments.amazonfps.model.PaymentOperation;
import com.topcoder.payments.amazonfps.model.PaymentOperationType;
import com.topcoder.payments.amazonfps.model.PaymentStatus;
import com.topcoder.payments.amazonfps.persistence.AuthorizationPersistence;
import com.topcoder.payments.amazonfps.persistence.PaymentPersistenceException;
import com.topcoder.util.idgenerator.IDGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * Unit tests for {@link DatabasePaymentPersistence} class.
 * </p>
 *
 * @author KennyAlive
 * @version 1.0
 */
public class DatabasePaymentPersistenceTest {
    /**
     * Constant for configuration namespace for accuracy tests.
     */
    private static final String CONFIGURATION = "PersistenceTest";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_1 = "DatabasePaymentPersistenceFailure1";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_2 = "DatabasePaymentPersistenceFailure2";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_3 = "DatabasePaymentPersistenceFailure3";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_4 = "DatabasePaymentPersistenceFailure4";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_5 = "PersistenceFailure";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_6 = "PersistenceFailure2";

    /**
     * Constant used for testing. Represents amount.
     */
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(1250.25);

    /**
     * Transaction id constant for testing.
     */
    private static final String TRANSACTION_ID = "myTransactionId";

    /**
     * Request id constant for testing.
     */
    private static final String REQUEST_ID = "myRequestId";

    /**
     * Payment parameters for testing.
     */
    private Map<String, String> parameters;

    /**
     * Payment instance used for testing.
     */
    private Payment payment;

    /**
     * Payment operation instance use for testing.
     */
    private PaymentOperation paymentOperation;

    /**
     * The {@code MockBaseDatabasePersistence} instance used for getting database connection
     * for testing, since this mock class exposes public getConnection() method which is what we need.
     */
    private MockBaseDatabasePersistence testConnectionProvider;

    /**
     * Authorization persistence used for testing.
     */
    private AuthorizationPersistence authorizationPersistence;

    /**
     * Represents the Connection used in tests.
     */
    private Connection connection;

    /**
     * Authorization id used for testing.
     */
    private long authorizationId;


    /**
     * The {@code DatabasePaymentPersistence} instance used for testing.
     */
    private DatabasePaymentPersistence instance;

    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DatabasePaymentPersistenceTest.class);
    }

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        ConfigurationObject configuration = TestHelper.getConfiguration(CONFIGURATION);

        testConnectionProvider = new MockBaseDatabasePersistence();
        testConnectionProvider.configure(configuration);

        authorizationPersistence = new DatabaseAuthorizationPersistence();
        authorizationPersistence.configure(configuration);

        connection = testConnectionProvider.createConnection();
        TestHelper.clearDB(connection);

        instance = new DatabasePaymentPersistence();

        // create authorization in persistence that can be referenced by payment being tested
        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(true);
        authorization.setTokenId("myToken");
        authorization.setAuthorizedAmountLeft(BigDecimal.valueOf(100));
        authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(50));
        authorization.setCancelled(false);

        authorizationPersistence.createAuthorization(authorization);
        authorizationId = authorization.getId();

        parameters = new HashMap<String, String>();
        parameters.put("key1", "value1");
        parameters.put("key2", "value2");
        parameters.put("key3", "value3");

        payment = new Payment();
        paymentOperation = new PaymentOperation();

        payment.setId(0L);
        payment.setAuthorizationId(authorizationId);
        payment.setAmount(AMOUNT);
        payment.setParameters(parameters);
        payment.setTransactionId(TRANSACTION_ID);
        payment.setStatus(PaymentStatus.RESERVED);

        paymentOperation.setType(PaymentOperationType.SETTLE);
        paymentOperation.setRequestId(REQUEST_ID);
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

    /**
     * Accuracy test for {@code DatabasePaymentPersistence} constructor. <br/>
     * Check that the fields are initialized to correct default values.
     */
    @Test
    public void test_constructorDatabasePaymentPersistence() {
        assertNull("The payment id generator should be null",
                TestHelper.getField(instance, "paymentIdGenerator"));
        assertNull("The payment operation id generator should be null",
                TestHelper.getField(instance, "paymentOperationIdGenerator"));
    }

    //-------------------------------------------------------------------------
    // configure() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code configure} method. <br/>
     * Check that fields are initialized according to configuration.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_configure_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        IDGenerator idGenerator =
                (IDGenerator) TestHelper.getField(instance, "paymentIdGenerator");

        assertNotNull("The payment id generator should not be null", idGenerator);

        IDGenerator idGenerator2 =
                (IDGenerator) TestHelper.getField(instance, "paymentOperationIdGenerator");

        assertNotNull("The payment operation id generator should not be null", idGenerator2);
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * Set null configuration.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_configureFailure_1() throws Exception {
        instance.configure(null);
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * Payment ID generator name is not specified.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_configureFailure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_1));
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * Payment operation ID generator name is not specified.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_configureFailure_3() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_2));
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * Payment ID generator name is empty.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_configureFailure_4() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_3));
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * Payment operation ID generator name is empty.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_configureFailure_5() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_4));
    }

    //-------------------------------------------------------------------------
    // createPayment() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code createPayment} method. <br/>
     * Create payment in persistence and check that is was created successfully.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_createPayment_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        // before payment is created in persistence id parameter is 0
        assertEquals("Payment id should be zero", 0L, payment.getId());

        // create payment in persistence
        instance.createPayment(payment);

        // check that payment id was updated
        assertTrue("Payment id should be positive", payment.getId() > 0L);

        // validate payment in persistence
        Object[][] result = JDBCUtility.executeQuery(connection, "SELECT * FROM payments",
                new int[0], new Object[0],
                new Class<?>[] {Long.class, Long.class, Double.class, String.class, String.class, Date.class},
                Exception.class);

        assertEquals("There should be 1 payment", 1, result.length);

        assertEquals("Payment id should be correct",
                Long.valueOf(payment.getId()), result[0][0]);

        assertEquals("Authorization id should be correct",
                Long.valueOf(authorizationId), result[0][1]);

        assertEquals("Amount should be correct",
                Double.valueOf(AMOUNT.doubleValue()), result[0][2]);

        assertEquals("Transaction id should be correct", TRANSACTION_ID, result[0][3]);

        assertEquals("Status should be correct", PaymentStatus.RESERVED.name(), result[0][4]);
    }

    /**
     * Accuracy test for {@code createPayment} method. <br/>
     * Create that payments parameters is created correctly in persistence.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_createPayment_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.createPayment(payment);

        // validate payment parameters in persistence
        Object[][] result = JDBCUtility.executeQuery(connection, "SELECT * FROM payment_parameters",
                new int[0], new Object[0],
                new Class<?>[] {Long.class, String.class, String.class, Date.class},
                Exception.class);

        assertEquals("Payment parameters count should be correct", parameters.size(), result.length);
        for (int i = 0; i < result.length; i++) {
            String key = (String) result[i][1];
            String value = (String) result[i][2];
            assertEquals("Payment parameter should be correct", value, parameters.get(key));
        }
    }

    /**
     * Failure test for {@code createPayment} method. <br/>
     * Pass null payment instance.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_createPaymentFailure_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.createPayment(null);
    }

    /**
     * Failure test for {@code createPayment} method. <br/>
     * Set zero authorization id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_createPaymentFailure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        payment.setAuthorizationId(0L);
        instance.createPayment(payment);
    }

    /**
     * Failure test for {@code createPayment} method. <br/>
     * Set negative authorization id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_createPaymentFailure_3() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        payment.setAuthorizationId(-5L);
        instance.createPayment(payment);
    }

    /**
     * Failure test for {@code createPayment} method. <br/>
     * Set null amount.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_createPaymentFailure_4() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        payment.setAmount(null);
        instance.createPayment(payment);
    }

    /**
     * Failure test for {@code createPayment} method. <br/>
     * Set negative amount.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_createPaymentFailure_5() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        payment.setAmount(BigDecimal.valueOf(-1000));
        instance.createPayment(payment);
    }

    /**
     * Failure test for {@code createPayment} method. <br/>
     * Set null status.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_createPaymentFailure_6() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        payment.setStatus(null);
        instance.createPayment(payment);
    }

    /**
     * Failure test for {@code createPayment} method. <br/>
     * Persistence instance is not configured.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalStateException.class)
    public void test_createPaymentFailure_7() throws Exception {
        instance.createPayment(payment);
    }

    /**
     * Failure test for {@code createPayment} method. <br/>
     * Incorrect connection parameters.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = PaymentPersistenceException.class)
    public void test_createPaymentFailure_8() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_5));
        instance.createPayment(payment);
    }

    /**
     * Failure test for {@code createPayment} method. <br/>
     * Incorrect id generator name.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = PaymentPersistenceException.class)
    public void test_createPaymentFailure_9() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_6));
        instance.createPayment(payment);
    }

    //-------------------------------------------------------------------------
    // updatePayment() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code updatePayment} method. <br/>
     * At first create 'empty' record, then update it and then check result
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_updatePayment_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        // create empty payment record in persistence
        Payment payment2 = new Payment();
        payment2.setAuthorizationId(authorizationId);
        payment2.setAmount(BigDecimal.valueOf(0.01));
        payment2.setStatus(PaymentStatus.NOT_INITIATED);
        instance.createPayment(payment2);

        Long id = payment2.getId();

        // update payment in persistence
        payment.setId(id);
        instance.updatePayment(payment);

        // retrieve 'payments' table content for validation
        Object[][] result = JDBCUtility.executeQuery(connection, "SELECT * FROM payments",
                new int[0], new Object[0],
                new Class<?>[] {Long.class, Long.class, Double.class, String.class, String.class, Date.class},
                Exception.class);

        // validate updated record
        assertEquals("There should be 1 payment", 1, result.length);
        assertEquals("Payment id should be correct", Long.valueOf(id), result[0][0]);
        assertEquals("Authorization id should be correct", Long.valueOf(authorizationId), result[0][1]);
        assertEquals("Amount should be correct", Double.valueOf(AMOUNT.doubleValue()), result[0][2]);
        assertEquals("Transaction id should be correct", TRANSACTION_ID, result[0][3]);
        assertEquals("Transaction id should be correct", PaymentStatus.RESERVED.name(), result[0][4]);
    }

    /**
     * Accuracy test for {@code updatePayment} method. <br/>
     * Create a few records and update some of them
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_updatePayment_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        final int updateCount = 2;
        long[] updateIds = new long[2];

        // create payment with varying parameters
        final int count = 5;
        for (int i = 0; i < count; i++) {
            String transactionId = "transaction" + i;
            payment.setId(0L);
            payment.setTransactionId(transactionId);
            payment.setAmount(BigDecimal.valueOf(i + 1));
            instance.createPayment(payment);
            if (i < updateCount) {
                updateIds[i] = payment.getId();
            }
        }

        // update amount for the first updateCount records
        for (int i = 0; i < updateCount; i++) {
            Payment payment2 = instance.getPayment(updateIds[i]);
            payment2.setAmount(BigDecimal.valueOf(100 + i));
            instance.updatePayment(payment2);
        }

        // retrieve 'payments' table content for validation
        Object[][] result = JDBCUtility.executeQuery(connection, "SELECT * FROM payments",
                new int[0], new Object[0],
                new Class<?>[] {Long.class, Long.class, Double.class, String.class, String.class, Date.class},
                Exception.class);

        // validate amount
        for (int i = 0; i < count; i++) {
            BigDecimal amount = BigDecimal.valueOf(i < updateCount ? (100 + i) : (i + 1));
            assertEquals("Amount should be correct", amount.doubleValue(), result[i][2]);
        }
    }

    /**
     * Failure test for {@code updatePayment} method. <br/>
     * Pass null payment instance.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_updatePaymentFailure_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.updatePayment(null);
    }

    /**
     * Failure test for {@code updatePayment} method. <br/>
     * Set zero id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_updatePaymentFailure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        payment.setId(0L);
        instance.updatePayment(payment);
    }

    /**
     * Failure test for {@code updatePayment} method. <br/>
     * Set negative id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_updatePaymentFailure_3() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        payment.setId(-2L);
        instance.updatePayment(payment);
    }

    /**
     * Failure test for {@code updatePayment} method. <br/>
     * Set zero authorization id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_updatePaymentFailure_4() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        payment.setAuthorizationId(0L);
        instance.updatePayment(payment);
    }

    /**
     * Failure test for {@code updatePayment} method. <br/>
     * Set negative authorization id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_updatePaymentFailure_5() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        payment.setAuthorizationId(-5L);
        instance.updatePayment(payment);
    }

    /**
     * Failure test for {@code updatePayment} method. <br/>
     * Set null amount.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_updatePaymentFailure_6() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        payment.setAmount(null);
        instance.updatePayment(payment);
    }

    /**
     * Failure test for {@code updatePayment} method. <br/>
     * Set negative amount.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_updatePaymentFailure_7() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        payment.setAmount(BigDecimal.valueOf(-1000));
        instance.updatePayment(payment);
    }

    /**
     * Failure test for {@code updatePayment} method. <br/>
     * Set null status.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_updatePaymentFailure_8() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        payment.setStatus(null);
        instance.updatePayment(payment);
    }

    /**
     * Failure test for {@code updatePayment} method. <br/>
     * Persistence instance is not configured.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalStateException.class)
    public void test_updatePaymentFailure_9() throws Exception {
        payment.setId(1L);
        instance.updatePayment(payment);
    }

    /**
     * Failure test for {@code updatePayment} method. <br/>
     * Pass not existed payment id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = PaymentNotFoundException.class)
    public void test_updatePaymentFailure_10() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.createPayment(payment);
        payment.setId(payment.getId() + 1);
        instance.updatePayment(payment);
    }

    /**
     * Failure test for {@code updatePayment} method. <br/>
     * Incorrect connection parameters.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = PaymentPersistenceException.class)
    public void test_updatePaymentFailure_11() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.createPayment(payment);
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_5));
        instance.updatePayment(payment);
    }

    //-------------------------------------------------------------------------
    // getPayment() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code getPayment} method. <br/>
     * Null should be returned if payment doesn't exist.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPayment_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.createPayment(payment);
        Payment payment2 = instance.getPayment(payment.getId() + 1);
        assertNull("Payment should be null", payment2);
    }

    /**
     * Accuracy test for {@code getPayment} method. <br/>
     * Request existed payment, check that correct object is returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPayment_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.createPayment(payment);

        Payment payment2 = instance.getPayment(payment.getId());
        assertNotNull("Payment should not be null", payment2);

        assertEquals("Id should be correct", payment.getId(), payment2.getId());
        assertEquals("Authorization id should be correct", authorizationId, payment2.getAuthorizationId());
        assertEquals("Amount should be correct", AMOUNT, payment2.getAmount());
        assertEquals("Transaction id should be correct", TRANSACTION_ID, payment2.getTransactionId());
        assertEquals("Status should be correct", payment.getStatus().name(), payment2.getStatus().name());

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String value = payment2.getParameters().get(entry.getKey());
            assertEquals("Payment parameter should be correcdt", entry.getValue(), value);
        }
    }

    /**
     * Failure test for {@code getPayment} method. <br/>
     * Payment id is zero.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_getPaymentFailure_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.getPayment(0L);
    }

    /**
     * Failure test for {@code getPayment} method. <br/>
     * Payment id is negative.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_getPaymentFailure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.getPayment(-2L);
    }

    /**
     * Failure test for {@code getPayment} method. <br/>
     * Persistence instance is not configured.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalStateException.class)
    public void test_getPaymentFailure_3() throws Exception {
        instance.getPayment(1L);
    }

    /**
     * Failure test for {@code getPayment} method. <br/>
     * Incorrect connection parameters.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = PaymentPersistenceException.class)
    public void test_getPaymentFailure_4() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_5));
        instance.getPayment(1L);
    }

    //-------------------------------------------------------------------------
    // getAllPayments() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code getAllPayments} method. <br/>
     * Empty list should be returned if there are no payments.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getAllPayments_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        List<Payment> result = instance.getAllPayments();
        assertTrue("List should be empty", result.isEmpty());
    }

    /**
     * Accuracy test for {@code getAllPayments} method. <br/>
     * Request all payments, check that result is correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getAllPayments_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.createPayment(payment);
        long id1 = payment.getId();

        Payment payment2 = new Payment();
        payment2.setAuthorizationId(authorizationId);
        payment2.setAmount(AMOUNT);
        payment2.setStatus(PaymentStatus.REFUNDED);
        instance.createPayment(payment2);
        long id2 = payment2.getId();

        List<Payment> result = instance.getAllPayments();

        assertEquals("The should be 2 payments", 2, result.size());
        assertNotNull("The first payment should not be null", result.get(0));
        assertNotNull("The second payment should not be null", result.get(1));

        if (result.get(0).getId() != id1) {
            result.set(0, result.set(1, result.get(0))); // swap
        }

        payment2 = result.get(0);
        assertEquals("Id should be correct", id1, payment2.getId());
        assertEquals("Authorization id should be correct", authorizationId, payment2.getAuthorizationId());
        assertEquals("Amount should be correct", AMOUNT, payment2.getAmount());
        assertEquals("Transaction id should be correct", TRANSACTION_ID, payment2.getTransactionId());
        assertEquals("Status should be correct", payment.getStatus().name(), payment2.getStatus().name());

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String value = payment2.getParameters().get(entry.getKey());
            assertEquals("Payment parameter should be correcdt", entry.getValue(), value);
        }

        payment2 = result.get(1);
        assertEquals("Id should be correct", id2, payment2.getId());
        assertEquals("Authorization id should be correct", authorizationId, payment2.getAuthorizationId());
        assertEquals("Amount should be correct", AMOUNT, payment2.getAmount());
        assertEquals("Status should be correct", PaymentStatus.REFUNDED.name(), payment2.getStatus().name());

        assertNull("Parameters should be null", payment2.getParameters());
    }

    /**
     * Failure test for {@code getAllPayments} method. <br/>
     * Persistence instance is not configured.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalStateException.class)
    public void test_getAllAuthorizationsFailure_1() throws Exception {
        instance.getAllPayments();
    }

    /**
     * Failure test for {@code getAllPayments} method. <br/>
     * Incorrect connection parameters.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = PaymentPersistenceException.class)
    public void test_getAllPaymentsFailure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_5));
        instance.getAllPayments();
    }

    //-------------------------------------------------------------------------
    // getPaymentsByAuthorization() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code getPaymentsByAuthorization} method. <br/>
     * Empty list should be returned if none payments is found.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPaymentsByAuthorization_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        List<Payment> result = instance.getPaymentsByAuthorization(authorizationId + 1);
        assertTrue("List should be empty", result.isEmpty());
    }

    /**
     * Accuracy test for {@code getPaymentsByAuthorization} method. <br/>
     * Create 3 payments: 2 for the first authorization and 1 for the second. Then request
     * payments for the first authorization. 2 payments should be retrieved.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPaymentsByAuthorization_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        // the first payment for auth1
        instance.createPayment(payment);
        long id1 = payment.getId();

        // the second payment for auth1
        Payment payment2 = new Payment();
        payment2.setAuthorizationId(authorizationId);
        payment2.setAmount(AMOUNT);
        payment2.setStatus(PaymentStatus.REFUNDED);
        payment2.setParameters(parameters);
        instance.createPayment(payment2);
        long id2 = payment2.getId();

        // create auth2
        Authorization authorization2 = new Authorization();
        authorization2.setMultipleUseAuthorization(true);
        authorization2.setTokenId("myToken");
        authorization2.setAuthorizedAmountLeft(BigDecimal.valueOf(100));
        authorization2.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(50));
        authorization2.setCancelled(false);

        authorizationPersistence.createAuthorization(authorization2);
        long authorizationId2 = authorization2.getId();

        // the third payment for auth2
        Payment payment3 = new Payment();
        payment3.setAuthorizationId(authorizationId2);
        payment3.setAmount(AMOUNT);
        payment3.setStatus(PaymentStatus.RESERVED);
        instance.createPayment(payment3);
        long id3 = payment3.getId();

        List<Payment> result = instance.getPaymentsByAuthorization(authorizationId);
        assertEquals("There should be 2 authorizations", 2, result.size());

        if (result.get(0).getId() != id1) {
            result.set(0, result.set(1, result.get(0))); // swap
        }

        Payment testPayment = result.get(0);
        assertEquals("Id should be correct", id1, testPayment.getId());
        assertEquals("Authorization id should be correct", authorizationId, testPayment.getAuthorizationId());

        testPayment = result.get(1);
        assertEquals("Id should be correct", id2, testPayment.getId());
        assertEquals("Authorization id should be correct", authorizationId, testPayment.getAuthorizationId());

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String value = testPayment.getParameters().get(entry.getKey());
            assertEquals("Payment parameter should be correcdt", entry.getValue(), value);
        }

        List<Payment> result2 = instance.getPaymentsByAuthorization(authorizationId2);
        assertEquals("There should be 1 authorization", 1, result2.size());

        testPayment = result2.get(0);
        assertEquals("Id should be correct", id3, testPayment.getId());
        assertEquals("Authorization id should be correct", authorizationId2, testPayment.getAuthorizationId());
    }

    /**
     * Failure test for {@code getPaymentsByAuthorization} method. <br/>
     * authorization id is zero.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_getPaymentsByAuthorizationFailure_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.getPaymentsByAuthorization(0L);
    }

    /**
     * Failure test for {@code getPaymentsByAuthorization} method. <br/>
     * authorization id is negative.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_getPaymentsByAuthorizationFailure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.getPaymentsByAuthorization(-2L);
    }

    /**
     * Failure test for {@code getPaymentsByAuthorization} method. <br/>
     * Persistence instance is not configured.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalStateException.class)
    public void test_getPaymentsByAuthorizationFailure_3() throws Exception {
        instance.getPaymentsByAuthorization(authorizationId);
    }

    /**
     * Failure test for {@code getPaymentsByAuthorization} method. <br/>
     * Incorrect connection parameters.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = PaymentPersistenceException.class)
    public void test_getPaymentsByAuthorizationFailure_4() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_5));
        instance.getPaymentsByAuthorization(1L);
    }

    //-------------------------------------------------------------------------
    // createPaymentOperation() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code createPaymentOperation} method. <br/>
     * Create payment operation in persistence and check that is was created successfully.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_createPaymentOperation_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.createPayment(payment);

        paymentOperation.setPaymentId(payment.getId());
        paymentOperation.setSuccessful(true);
        instance.createPaymentOperation(paymentOperation);

        // check that payment operation id was updated
        assertTrue("Payment operation id should be positive", paymentOperation.getId() > 0L);

        // validate payment in persistence
        Object[][] result = JDBCUtility.executeQuery(connection, "SELECT * FROM payment_operations",
                new int[0], new Object[0],
                new Class<?>[] {Long.class, Long.class, String.class, String.class, Boolean.class, Date.class},
                Exception.class);

        assertEquals("There should be 1 payment operation", 1, result.length);
        assertEquals("Payment operation id should be correct", Long.valueOf(paymentOperation.getId()), result[0][0]);
        assertEquals("Payment id should be correct", Long.valueOf(payment.getId()), result[0][1]);
        assertEquals("Request id should be correct", REQUEST_ID, result[0][2]);
        assertEquals("Operation type should be correct", paymentOperation.getType().name(), result[0][3]);
        assertEquals("Successful flag should be correct", Boolean.TRUE, result[0][4]);
    }

    /**
     * Failure test for {@code createPaymentOperation} method. <br/>
     * Payment operation is null.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_createPaymentOperationFailure_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.createPaymentOperation(null);
    }

    /**
     * Failure test for {@code createPaymentOperation} method. <br/>
     * Payment id is zero.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_createPaymentOperationFailure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        paymentOperation.setPaymentId(0L);
        instance.createPaymentOperation(paymentOperation);
    }

    /**
     * Failure test for {@code createPaymentOperation} method. <br/>
     * Payment id is negative.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_createPaymentOperationFailure_3() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        paymentOperation.setPaymentId(-32L);
        instance.createPaymentOperation(paymentOperation);
    }

    /**
     * Failure test for {@code createPaymentOperation} method. <br/>
     * Type is null.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_createPaymentOperationFailure_4() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.createPayment(payment);

        paymentOperation.setPaymentId(payment.getId());
        paymentOperation.setType(null);
        instance.createPaymentOperation(paymentOperation);
    }

    /**
     * Failure test for {@code createPaymentOperation} method. <br/>
     * Persistence instance is not configured.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalStateException.class)
    public void test_createPaymentOperationFailure_5() throws Exception {
        paymentOperation.setPaymentId(1L);
        instance.createPaymentOperation(paymentOperation);
    }

    /**
     * Failure test for {@code createPaymentOperation} method. <br/>
     * Incorrect connection parameters.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = PaymentPersistenceException.class)
    public void test_createPaymentOperationFailure_6() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_5));
        paymentOperation.setPaymentId(1L);
        instance.createPaymentOperation(paymentOperation);
    }

    /**
     * Failure test for {@code createPaymentOperation} method. <br/>
     * Incorrect id generator name.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = PaymentPersistenceException.class)
    public void test_createPaymentOperationFailure_7() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_6));
        paymentOperation.setPaymentId(1L);
        instance.createPaymentOperation(paymentOperation);
    }
}
