/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

/**
 * <p>
 * Accuracy tests for <code>BatchOperationException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class BatchOperationExceptionTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor1 : <code>BatchOperationException(String)</code>,
     * with correct message. The message can be retrieved correctly later.
     * </p>
     */
    public void testCtor1() {
        BatchOperationException e = new BatchOperationException(ERROR_MESSAGE, null);

        assertNotNull("Successfully to instantiate BatchOperationException.", e);
        this.assertErrorMessagePropagated(e);
        assertNull(e.getResult());
    }

    /**
     * <p>
     * Test constructor2: <code>BatchOperationException(String, Throwable)</code>,
     * with correct error message and cause. The message and cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor2() {
        BatchOperationException e = new BatchOperationException(ERROR_MESSAGE, CAUSE, new boolean[]{true, false});

        assertNotNull("Successfully to instantiate BatchOperationException.", e);
        this.assertErrorMessagePropagated(e);
        this.assertErrorCausePropagated(e);
        assertEquals(2, e.getResult().length);
        assertTrue(e.getResult()[0]);
        assertFalse(e.getResult()[1]);
    }

}
