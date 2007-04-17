/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

/**
 * <p>
 * Accuracy tests for <code>AssociationException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AssociationExceptionTestAcc extends BaseTestCase {
    /**
     * <p>
     * Test constructor1 : <code>AssociationException(String)</code>,
     * with correct message. The message can be retrieved correctly later.
     * </p>
     */
    public void testCtor1() {
        AssociationException e = new AssociationException(ERROR_MESSAGE);

        assertNotNull("Successfully to instantiate AssociationException.", e);
        this.assertErrorMessagePropagated(e);
    }

    /**
     * <p>
     * Test constructor2: <code>AssociationException(String, Throwable)</code>,
     * with correct error message and cause. The message and cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor2() {
        AssociationException e = new AssociationException(ERROR_MESSAGE, CAUSE);

        assertNotNull("Successfully to instantiate AssociationException.", e);
        this.assertErrorMessagePropagated(e);
        this.assertErrorCausePropagated(e);
    }
}
