/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

/**
 * <p>
 * Accuracy tests for <code>AuditException</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AuditExceptionTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor1 : <code>AuditException(String)</code>,
     * with correct message. The message can be retrieved correctly later.
     * </p>
     */
    public void testCtor1() {
        AuditException e = new AuditException(ERROR_MESSAGE);

        assertNotNull("Successfully to instantiate AuditException.", e);
        this.assertErrorMessagePropagated(e);
    }

    /**
     * <p>
     * Test constructor2: <code>AuditException(String, Throwable)</code>,
     * with correct error message and cause. The message and cause can be retrieved correctly later.
     * </p>
     */
    public void testCtor2() {
        AuditException e = new AuditException(ERROR_MESSAGE, CAUSE);

        assertNotNull("Successfully to instantiate AuditException.", e);
        this.assertErrorMessagePropagated(e);
        this.assertErrorCausePropagated(e);
    }

}