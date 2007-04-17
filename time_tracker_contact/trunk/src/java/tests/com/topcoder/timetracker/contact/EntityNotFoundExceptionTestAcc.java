/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

/**
 * <p>
 * Accuracy tests for <code>EntityNotFoundException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class EntityNotFoundExceptionTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor1 : <code>EntityNotFoundException(String)</code>,
     * with correct message. The message can be retrieved correctly later.
     * </p>
     */
    public void testCtor1() {
        EntityNotFoundException e = new EntityNotFoundException(ERROR_MESSAGE);

        assertNotNull("Successfully to instantiate EntityNotFoundException.", e);
        this.assertErrorMessagePropagated(e);
    }

    /**
     * <p>
     * Test constructor2: <code>EntityNotFoundException(String, Throwable)</code>,
     * with correct error message and cause. The message and cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor2() {
        EntityNotFoundException e = new EntityNotFoundException(ERROR_MESSAGE, CAUSE);

        assertNotNull("Successfully to instantiate EntityNotFoundException.", e);
        this.assertErrorMessagePropagated(e);
        this.assertErrorCausePropagated(e);
    }

}
