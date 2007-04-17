/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

/**
 * <p>
 * Accuracy tests for <code>IDGenerationException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class IDGenerationExceptionTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor1 : <code>IDGenerationException(String)</code>,
     * with correct message. The message can be retrieved correctly later.
     * </p>
     */
    public void testCtor1() {
        IDGenerationException e = new IDGenerationException(ERROR_MESSAGE);

        assertNotNull("Successfully to instantiate IDGenerationException.", e);
        this.assertErrorMessagePropagated(e);
    }

    /**
     * <p>
     * Test constructor2: <code>IDGenerationException(String, Throwable)</code>,
     * with correct error message and cause. The message and cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor2() {
        IDGenerationException e = new IDGenerationException(ERROR_MESSAGE, CAUSE);

        assertNotNull("Successfully to instantiate IDGenerationException.", e);
        this.assertErrorMessagePropagated(e);
        this.assertErrorCausePropagated(e);
    }

}
