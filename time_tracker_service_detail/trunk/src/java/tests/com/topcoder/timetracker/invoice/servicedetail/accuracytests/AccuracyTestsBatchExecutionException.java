/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.accuracytests;

import com.topcoder.timetracker.invoice.servicedetail.BatchExecutionException;

import junit.framework.TestCase;


/**
 * <p>
 * The accuracy tests for the class BatchExecutionException.
 * </p>
 *
 * @author KLW
 * @version 1.1
 */
public class AccuracyTestsBatchExecutionException extends TestCase {
    /** the array for accuracy test. */
    private long[] ids = new long[] { 1, 2, 3 };

    /**
     * <p>
     * Accuracy test for the BatchExecutionException.
     * </p>
     */
    public void testBatchExecutionException() {
        BatchExecutionException exception = new BatchExecutionException(ids);
        assertNotNull("It should not be null.", exception);

        long[] ids = exception.getIds();
        assertEquals("the id array should contain 3 elements.", 3, ids.length);
        assertEquals("the second element should be 2.", 2, ids[1]);
    }
}
