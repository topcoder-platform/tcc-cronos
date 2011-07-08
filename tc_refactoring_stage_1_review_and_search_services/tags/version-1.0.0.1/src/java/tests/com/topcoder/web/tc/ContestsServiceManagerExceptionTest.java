/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Tests the ContestsServiceManagerException class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class ContestsServiceManagerExceptionTest {

    /**
     * <p>
     * Tests that ContestsServiceManagerException is extended from BaseException.
     * </p>
     */
    @Test
    public void testExtends() {
        assertTrue("ContestsServiceManagerException should be extended from BaseException",
                new ContestsServiceManagerException("error") instanceof BaseException);
    }

    /**
     * <p>
     * Tests the ContestsServiceManagerException(String) constructor. It verifies that the error message is set
     * correctly.
     * </p>
     */
    @Test
    public void testCtor1() {
        ContestsServiceManagerException exception = new ContestsServiceManagerException("error");
        assertEquals("message should be set", "error", exception.getMessage());
    }

    /**
     * <p>
     * Tests the ContestsServiceManagerException(String, Throwable) constructor. It verifies that the error message and
     * cause are set correctly.
     * </p>
     */
    @Test
    public void testCtor2() {
        IllegalArgumentException cause = new IllegalArgumentException();
        ContestsServiceManagerException exception = new ContestsServiceManagerException("error", cause);
        assertEquals("message should be set", "error", exception.getMessage());
        assertEquals("cause should be set", cause, exception.getCause());
    }

    /**
     * <p>
     * Tests the ContestsServiceManagerException(String, ExceptionData) constructor. It verifies that the error message
     * and exception data are set correctly.
     * </p>
     */
    @Test
    public void testCtor3() {
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("app");
        ContestsServiceManagerException exception = new ContestsServiceManagerException("error", data);
        assertEquals("message should be set", "error", exception.getMessage());
        assertEquals("exception data should be set", "app", exception.getApplicationCode());
    }

    /**
     * <p>
     * Tests the ContestsServiceManagerException(String, Throwable, ExceptionData) constructor. It verifies that the
     * error message, cause, and exception data are set correctly.
     * </p>
     */
    @Test
    public void testCtor4() {
        IllegalArgumentException cause = new IllegalArgumentException();
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("app");
        ContestsServiceManagerException exception = new ContestsServiceManagerException("error", cause, data);
        assertEquals("message should be set", "error", exception.getMessage());
        assertEquals("cause should be set", cause, exception.getCause());
        assertEquals("exception data should be set", "app", exception.getApplicationCode());
    }
}
