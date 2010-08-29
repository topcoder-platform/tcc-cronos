/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.stresstests;

import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.persist.TransactionAssessmentViewDetailsDaoImpl;
import finance.tools.asia_pacific.salesperformance.base.persist.DataAccess;
import junit.framework.TestCase;

/**
 * <p>
 * Stress Test cases of the <code>TransactionAssessmentViewDetailsDaoImpl</code> class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TransactionAssessmentViewDetailsDaoImplTests extends TestCase {

    /**
     * <p>
     * Executing number count.
     * </p>
     */
    private static final int COUNT = 100;

    /**
     * <p>
     * The <code>TransactionAssessmentViewDetailsDaoImpl</code> instance to be tested.
     * </p>
     */
    private TransactionAssessmentViewDetailsDaoImpl instance;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    @Override
    protected void setUp() throws Exception {
        instance = new TransactionAssessmentViewDetailsDaoImpl();
        TestHelper.setPrivateField(DataAccess.class, null, "dataSource", TestHelper.obtainDataSource());
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