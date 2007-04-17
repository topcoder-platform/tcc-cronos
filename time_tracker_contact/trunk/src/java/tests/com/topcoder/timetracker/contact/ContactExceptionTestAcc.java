/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

/**
 * <p>
 * Accuracy tests for <code>ContactException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ContactExceptionTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor1 : <code>ContactException(String)</code>,
     * with correct message. The message can be retrieved correctly later.
     * </p>
     */
    public void testCtor1() {
        ContactException e = new ContactException(ERROR_MESSAGE);

        assertNotNull("Successfully to instantiate ContactException.", e);
        this.assertErrorMessagePropagated(e);
    }

    /**
     * <p>
     * Test constructor2: <code>ContactException(String, Throwable)</code>,
     * with correct error message and cause. The message and cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor2() {
        ContactException e = new ContactException(ERROR_MESSAGE, CAUSE);

        assertNotNull("Successfully to instantiate ContactException.", e);
        this.assertErrorMessagePropagated(e);
        this.assertErrorCausePropagated(e);
    }

}
