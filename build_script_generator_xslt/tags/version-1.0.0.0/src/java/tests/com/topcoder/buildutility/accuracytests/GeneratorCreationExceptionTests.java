/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 *
 * TCS Build Script Generator XSLT 1.0 (Accuracy Test)
 */
package com.topcoder.buildutility.accuracytests;

import junit.framework.TestCase;

import com.topcoder.buildutility.GeneratorCreationException;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>Unit test case for GeneratorCreationException class.</p>
 * 
 * <p>Simple tests verify that the exception can be instantiated with supplied arguments. </p>
 * 
 * @author matmis
 * @version 1.0
 */
public class GeneratorCreationExceptionTests extends TestCase {

    /** The error message used for testing. */
    private static final String MESSAGE = "test message";

    /**
     * Creation test for GeneratorCreationException with message.
     */
    public void testGeneratorCreationException1() {
        BaseException e = new GeneratorCreationException(MESSAGE);
        assertEquals("Error message is not properly set.", MESSAGE, e.getMessage());
        assertNull("Exception cause is not properly set.", e.getCause());
    }

    /**
     * Creation test for BuildScriptGeneratorException with message and cause.
     */
    public void testGeneratorCreationException2() {
        Throwable cause = new Exception();
        BaseException e = new GeneratorCreationException(MESSAGE, cause);
        assertEquals("Error message is not properly set.", MESSAGE + ", caused by " + cause.getMessage(), e
            .getMessage());
        assertSame("Exception cause is not properly set.", e.getCause(), cause);
    }
}
