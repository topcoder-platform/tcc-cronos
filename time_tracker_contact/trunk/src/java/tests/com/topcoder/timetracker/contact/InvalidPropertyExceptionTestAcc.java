/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

/**
 * <p>
 * Accuracy tests for <code>InvalidPropertyException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class InvalidPropertyExceptionTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor1 : <code>InvalidPropertyException(String)</code>,
     * with correct message. The message can be retrieved correctly later.
     * </p>
     */
    public void testCtor1() {
        InvalidPropertyException e = new InvalidPropertyException(ERROR_MESSAGE);

        assertNotNull("Successfully to instantiate InvalidPropertyException.", e);
        this.assertErrorMessagePropagated(e);
    }

    /**
     * <p>
     * Test constructor2: <code>InvalidPropertyException(String, Throwable)</code>,
     * with correct error message and cause. The message and cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor2() {
        InvalidPropertyException e = new InvalidPropertyException(ERROR_MESSAGE, CAUSE);

        assertNotNull("Successfully to instantiate InvalidPropertyException.", e);
        this.assertErrorMessagePropagated(e);
        this.assertErrorCausePropagated(e);
    }

}
