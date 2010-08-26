/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.stresstests;

import junit.framework.TestCase;
import finance.tools.asia_pacific.salesperformance.assessment.model.TransactionSummary;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.util.TransactionSummaryToJsonConverter;

/**
 * <p>
 * Stress Test cases of the <code>TransactionSummaryToJsonConverter</code> class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TransactionSummaryToJsonConverterTests extends TestCase{
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
     * Accuracy test case for convertTransactionToJson.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_convertTransactionToJson_Stress() throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            TransactionSummaryToJsonConverter.convertTransactionToJson(new TransactionSummary());
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Total convertTransactionToJson time: " + time
            + "ms for running " + COUNT + " times.");
    }
}
