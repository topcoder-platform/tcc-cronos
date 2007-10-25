/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.accuracytests;

import junit.framework.TestCase;

import com.topcoder.registration.team.service.TeamServicesException;

/**
 * <p>
 * Accuracy unit tests for for <code>TeamServicesException</code> class.
 * </p>
 * @author 80x86
 * @version 1.0
 */
public class TeamServicesExceptionUnitTests extends TestCase {

    /**
     * <p>
     * Accuracy test for constructor with message.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithMsg() throws Exception {
        Exception ex = new TeamServicesException("msgtest");
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
    public void testCtorWithMsgAndCause() throws Exception {
        Exception e = new Exception("exception");
        Exception ex = new TeamServicesException("messagetest", e);
        assertNotNull("'ex' should not be null.", ex);
        assertEquals("Exception message mismatched.", "messagetest, caused by exception", ex.getMessage());
    }
}
