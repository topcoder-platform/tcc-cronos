/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.stresstests;

import junit.framework.TestCase;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.TransactionAssessmentViewDetailsManager;
import finance.tools.asia_pacific.salesperformance.base.persist.DataAccess;
import finance.tools.asia_pacific.salesperformance.base.util.Configuration;

/**
 * <p>
 * Stress Test cases of the <code>TransactionAssessmentViewDetailsManager</code> class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TransactionAssessmentViewDetailsManagerTests extends TestCase {
    /**
     * <p>
     * Represents the <code>TransactionAssessmentViewDetailsManager</code> instance used for test.
     * </p>
     */
    private TransactionAssessmentViewDetailsManager instance;
    /**
     * <p>
     * Executing number count.
     * </p>
     */
    private static final int COUNT = 100;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    @Override
    public void setUp() throws Exception {
        Configuration.setResourceFile("stressConfig");
        TestHelper.setPrivateField(DataAccess.class, null, "dataSource", TestHelper.obtainDataSource());
        instance = new TransactionAssessmentViewDetailsManager();
        super.setUp();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Stress test case for retrieveTransactionsByEmployeeIncentiveElementKeys.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_retrieveTransactionsByEmployeeIncentiveElementKeys() throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            instance.retrieveTransactionsByEmployeeIncentiveElementKeys(new long[] {1l});
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Total retrieveTransactionsByEmployeeIncentiveElementKeys time: " + time
            + "ms for running " + COUNT + " times.");
    }

    /**
     * <p>
     * Stress test case for retrieveTransactionsByTransactionNumbers.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_retrieveTransactionsByTransactionNumbers() throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            instance.retrieveTransactionsByTransactionNumbers(new short[] {1});
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Total retrieveTransactionsByTransactionNumbers time: " + time + "ms for running "
            + COUNT + " times.");
    }

    /**
     * <p>
     * Stress test case for retrieveTransactionsDetailsByEmployeeIncentiveElementKeys.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_retrieveTransactionsDetailsByEmployeeIncentiveElementKeys() throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            instance.retrieveTransactionsDetailsByEmployeeIncentiveElementKeys(new long[] {1l});
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Total retrieveTransactionsDetailsByEmployeeIncentiveElementKeys time: " + time
            + "ms for running " + COUNT + " times.");
    }

    /**
     * <p>
     * Stress test case for retrieveTransactionsDetailsByTransactionNumbers.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_retrieveTransactionsDetailsByTransactionNumbers() throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            instance.retrieveTransactionsDetailsByTransactionNumbers(new short[] {1});
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Total retrieveTransactionsDetailsByTransactionNumbers time: " + time
            + "ms for running " + COUNT + " times.");
    }
}
