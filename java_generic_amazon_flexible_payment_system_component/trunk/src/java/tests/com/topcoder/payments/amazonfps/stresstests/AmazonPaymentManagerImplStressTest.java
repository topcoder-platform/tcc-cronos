/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.stresstests;

import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentManagementException;
import com.topcoder.payments.amazonfps.AmazonPaymentManagerImpl;
import com.topcoder.payments.amazonfps.model.PaymentAuthorizationRequest;
import com.topcoder.payments.amazonfps.model.PaymentDetails;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * <p>Stress test case of {@link AmazonPaymentManagerImpl}.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class AmazonPaymentManagerImplStressTest extends BaseStressTest {
    private final static Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(new FileInputStream("test_files/stress/db.properties"));
        } catch (FileNotFoundException e) {
            // ignore
        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * <p>The AmazonPaymentManagerImpl instance to test.</p>
     */
    private AmazonPaymentManagerImpl instance;

    /** Represents the JDBC connection. */
    private Connection connection;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        connection = createConnection();
        executeSqlFile(connection, "test_files/stress/clear.sql");
        executeSqlFile(connection, "test_files/stress/insert.sql");
        instance = new AmazonPaymentManagerImpl("test_files/stress/stress.properties", "AmazonPaymentManagerImpl4Stress");
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
        executeSqlFile(connection, "test_files/stress/clear.sql");

        if (connection != null) {
            connection.close();
        }
    }

    /**
     * <p>Creates a test suite for the tests in this test case.</p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(AmazonPaymentManagerImplStressTest.class);

        return suite;
    }

    /**
     * <p>Stress test for method AmazonPaymentManagerImpl#initiatePaymentAuthorization().</p>
     *
     * @throws Throwable to junit
     */
    public void test_initiatePaymentAuthorization() throws Throwable {
        Thread[] thread = new Thread[testCount];

        for (int i = 0; i < testCount; i++) {
            thread[i] = new TestThread(i) {
                        public void run() {
                            try {
                                instance.initiatePaymentAuthorization(getPaymentAuthorizationRequest());
                            } catch (AmazonFlexiblePaymentManagementException e) {
                                lastError = e;
                            }
                        }
                    };
            thread[i].start();
        }

        for (int i = 0; i < testCount; i++) {
            // wait to end
            thread[i].join();
        }

        if (lastError != null) {
            throw lastError;
        }

        System.out.println("Run initiatePaymentAuthorization for  " + testCount + " times takes " +
            (new Date().getTime() - start) + "ms");
    }

    /**
     * <p>Stress test for method AmazonPaymentManagerImpl#handleRequestFromCoBrandedService().</p>
     *
     * @throws Throwable to junit
     */
    public void test_handleRequestFromCoBrandedService()
        throws Throwable {
        Thread[] thread = new Thread[testCount];

        for (int i = 0; i < testCount; i++) {
            thread[i] = new TestThread(i) {
                        public void run() {
                            try {
                                instance.handleRequestFromCoBrandedService(getRequestParams());
                            } catch (AmazonFlexiblePaymentManagementException e) {
                                lastError = e;
                            }
                        }
                    };
            thread[i].start();
        }

        for (int i = 0; i < testCount; i++) {
            // wait to end
            thread[i].join();
        }

        if (lastError != null) {
            throw lastError;
        }

        System.out.println("Run handleRequestFromCoBrandedService for  " + testCount + " times takes " +
            (new Date().getTime() - start) + "ms");
    }

    /**
     * <p>Stress test for method AmazonPaymentManagerImpl#processAuthorizedPayment().</p>
     *
     * @throws Throwable to junit
     */
    public void test_processAuthorizedPayment() throws Throwable {
        Thread[] thread = new Thread[testCount];

        for (int i = 0; i < testCount; i++) {
            thread[i] = new TestThread(i) {
                        public void run() {
                            try {
                                PaymentDetails details = new PaymentDetails();
                                details.setAmount(new BigDecimal(200));
                                instance.processAuthorizedPayment(1000, details);
                            } catch (AmazonFlexiblePaymentManagementException e) {
                                lastError = e;
                            }
                        }
                    };
            thread[i].start();
        }

        for (int i = 0; i < testCount; i++) {
            // wait to end
            thread[i].join();
        }

        if (lastError != null) {
            throw lastError;
        }

        System.out.println("Run processAuthorizedPayment for  " + testCount + " times takes " +
            (new Date().getTime() - start) + "ms");
    }

    /**
     * <p>Stress test for method AmazonPaymentManagerImpl#cancelPayment().</p>
     *
     * @throws Throwable to junit
     */
    public void test_cancelPayment() throws Throwable {
        Thread[] thread = new Thread[testCount];

        for (int i = 0; i < testCount; i++) {
            thread[i] = new TestThread(i) {
                        public void run() {
                            try {
                                instance.cancelPayment(1000);
                            } catch (AmazonFlexiblePaymentManagementException e) {
                                lastError = e;
                            }
                        }
                    };
            thread[i].start();
        }

        for (int i = 0; i < testCount; i++) {
            // wait to end
            thread[i].join();
        }

        if (lastError != null) {
            throw lastError;
        }

        System.out.println("Run cancelPayment for  " + testCount + " times takes " + (new Date().getTime() - start) +
            "ms");
    }

    /**
     * <p>Stress test for method AmazonPaymentManagerImpl#settlePayment().</p>
     *
     * @throws Throwable to junit
     */
    public void test_settlePayment() throws Throwable {
        Thread[] thread = new Thread[testCount];

        for (int i = 0; i < testCount; i++) {
            thread[i] = new TestThread(i) {
                        public void run() {
                            try {
                                instance.settlePayment(1000);
                            } catch (AmazonFlexiblePaymentManagementException e) {
                                lastError = e;
                            }
                        }
                    };
            thread[i].start();
        }

        for (int i = 0; i < testCount; i++) {
            // wait to end
            thread[i].join();
        }

        if (lastError != null) {
            throw lastError;
        }

        System.out.println("Run settlePayment for  " + testCount + " times takes " + (new Date().getTime() - start) +
            "ms");
    }

    /**
     * <p>Stress test for method AmazonPaymentManagerImpl#refundPayment().</p>
     *
     * @throws Throwable to junit
     */
    public void test_refundPayment() throws Throwable {
        Thread[] thread = new Thread[testCount];

        for (int i = 0; i < testCount; i++) {
            thread[i] = new TestThread(i) {
                        public void run() {
                            try {
                                instance.refundPayment(1000);
                            } catch (AmazonFlexiblePaymentManagementException e) {
                                lastError = e;
                            }
                        }
                    };
            thread[i].start();
        }

        for (int i = 0; i < testCount; i++) {
            // wait to end
            thread[i].join();
        }

        if (lastError != null) {
            throw lastError;
        }

        System.out.println("Run refundPayment for  " + testCount + " times takes " + (new Date().getTime() - start) +
            "ms");
    }

    /**
     * <p>Stress test for method AmazonPaymentManagerImpl#cancelAuthorization().</p>
     *
     * @throws Throwable to junit
     */
    public void test_cancelAuthorization() throws Throwable {
        Thread[] thread = new Thread[testCount];

        for (int i = 0; i < testCount; i++) {
            thread[i] = new TestThread(i) {
                        public void run() {
                            try {
                                instance.cancelAuthorization(1000);
                            } catch (AmazonFlexiblePaymentManagementException e) {
                                lastError = e;
                            }
                        }
                    };
            thread[i].start();
        }

        for (int i = 0; i < testCount; i++) {
            // wait to end
            thread[i].join();
        }

        if (lastError != null) {
            throw lastError;
        }

        System.out.println("Run cancelAuthorization for  " + testCount + " times takes " +
            (new Date().getTime() - start) + "ms");
    }

    /**
     * Get the request params map.
     *
     * @return the request params map.
     */
    private Map<Object, Object> getRequestParams() {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("authorizationId", "1000");
        map.put("paymentId", "1000");
        map.put("reserve", "1");

        return map;
    }

    /**
     * Get the PaymentAuthorizationRequest for test.
     *
     * @return the PaymentAuthorizationRequest instance.
     */
    private PaymentAuthorizationRequest getPaymentAuthorizationRequest() {
        PaymentAuthorizationRequest request = new PaymentAuthorizationRequest();
        request.setFutureChargesAuthorizationRequired(false);
        request.setFutureChargesFixedAmount(new BigDecimal(200));
        request.setRedirectUrl("http://www.topcoder.com/tc");

        PaymentDetails details = new PaymentDetails();
        details.setAmount(new BigDecimal(150));
        request.setPaymentDetails(details);

        return request;
    }

    /**
     * <p>Create a new db connection to return.</p>
     *
     * @return the db connection.
     *
     * @throws Exception to JUnit
     */
    private static Connection createConnection() throws Exception {
        String dbUrl = PROPERTIES.getProperty("dburl");
        String dbUser = PROPERTIES.getProperty("dbuser");
        String dbPassword = PROPERTIES.getProperty("dbpassword");
        String dbClass = PROPERTIES.getProperty("dbdriver");

        Class.forName(dbClass);

        if ((dbUser != null) && (!(dbUser.trim().length() == 0))) {
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } else {
            return DriverManager.getConnection(dbUrl);
        }
    }
}
