/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionData;
import com.topcoder.web.tc.ContestsServiceManagerException;

/**
 * <p>
 * Accuracy test for ContestsServiceManagerException class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestsServiceManagerExceptionAccuracyTest {
    /**
     * <p>
     * Accuracy test for ContestsServiceManagerException(String message). Instance should be created.
     * </p>
     */
    @Test
    public void testCtor1() {
        ContestsServiceManagerException ex = new ContestsServiceManagerException("ex");
        assertEquals("The message is incorrect.", "ex", ex.getMessage());
        assertNull("The cause is incorrect.", ex.getCause());
        assertEquals("The base class is incorrect.", ex.getClass().getSuperclass(), BaseException.class);
    }

    /**
     * <p>
     * Accuracy test for ContestsServiceManagerException(String message, Throwable cause). Instance should be created.
     * </p>
     */
    @Test
    public void testCtor2() {
        Throwable cause = new Exception();
        ContestsServiceManagerException ex = new ContestsServiceManagerException("ex", cause);
        assertEquals("The message is incorrect.", "ex", ex.getMessage());
        assertEquals("The cause is incorrect.", cause, ex.getCause());
    }

    /**
     * <p>
     * Accuracy test for ContestsServiceManagerException(String message, ExceptionData exceptionData). Instance should
     * be created.
     * </p>
     */
    @Test
    public void testCtor3() {
        ExceptionData exceptionData = new ExceptionData();
        exceptionData.setApplicationCode("1");
        ContestsServiceManagerException ex = new ContestsServiceManagerException("ex", exceptionData);
        assertEquals("The message is incorrect.", "ex", ex.getMessage());
        assertEquals("The cause is incorrect.", null, ex.getCause());
        assertEquals("exception data should be set", "1", exceptionData.getApplicationCode());
    }

    /**
     * <p>
     * Accuracy test for ContestsServiceManagerException(String message, Throwable cause, ExceptionData exceptionData).
     * Instance should be created.
     * </p>
     */
    @Test
    public void testCtor4() {
        Throwable cause = new Exception();
        ExceptionData exceptionData = new ExceptionData();
        exceptionData.setApplicationCode("1");
        ContestsServiceManagerException ex = new ContestsServiceManagerException("ex", cause, exceptionData);
        assertEquals("The message is incorrect.", "ex", ex.getMessage());
        assertEquals("The cause is incorrect.", cause, ex.getCause());
        assertEquals("exception data should be set", "1", exceptionData.getApplicationCode());
    }
}
