/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.accuracytests;

import junit.framework.TestCase;

import com.topcoder.registration.team.service.UnknownEntityException;

/**
 * <p>
 * Accuracy unit tests for <code>UnknownEntityException</code> class.
 * </p>
 * @author 80x86
 * @version 1.0
 */
public class UnknownEntityExceptionUnitTests extends TestCase {

    /**
     * <p>
     * Accuracy test for constructor with message.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithMsgAccuracy() throws Exception {
        Exception ex = new UnknownEntityException("msgtest");
        assertNotNull("'ex' should not be null.", ex);
        assertEquals("Exception message mismatched.", "msgtest", ex.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor with message and cause.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithMsgAndCauseAccuracy() throws Exception {
        Exception e = new Exception("exception");
        Exception ex = new UnknownEntityException("messagetest", e);
        assertNotNull("'ex' should not be null.", ex);
        assertEquals("Exception message mismatched.", "messagetest, caused by exception", ex.getMessage());
    }
}
