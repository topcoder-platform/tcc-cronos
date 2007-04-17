/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

/**
 * <p>
 * Accuracy tests for <code>PersistenceException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class PersistenceExceptionTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor1 : <code>PersistenceException(String)</code>,
     * with correct message. The message can be retrieved correctly later.
     * </p>
     */
    public void testCtor1() {
        PersistenceException e = new PersistenceException(ERROR_MESSAGE);

        assertNotNull("Successfully to instantiate PersistenceException.", e);
        this.assertErrorMessagePropagated(e);
    }

    /**
     * <p>
     * Test constructor2: <code>PersistenceException(String, Throwable)</code>,
     * with correct error message and cause. The message and cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor2() {
        PersistenceException e = new PersistenceException(ERROR_MESSAGE, CAUSE);

        assertNotNull("Successfully to instantiate PersistenceException.", e);
        this.assertErrorMessagePropagated(e);
        this.assertErrorCausePropagated(e);
    }

}
