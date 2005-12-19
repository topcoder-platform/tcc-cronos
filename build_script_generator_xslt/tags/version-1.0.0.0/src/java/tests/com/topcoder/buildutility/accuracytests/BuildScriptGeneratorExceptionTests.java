/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 *
 * TCS Build Script Generator XSLT 1.0 (Accuracy Test)
 */
package com.topcoder.buildutility.accuracytests;

import junit.framework.TestCase;

import com.topcoder.buildutility.BuildScriptGeneratorException;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>Unit test case for BuildScriptGeneratorException class.</p>
 * 
 * <p>Simple tests verify that the exception can be instantiated with supplied arguments. </p>
 * 
 * @author matmis
 * @version 1.0
 */
public class BuildScriptGeneratorExceptionTests extends TestCase {

    /** The error message used for testing. */
    private static final String MESSAGE = "test message";

    /**
     * Creation test for BuildScriptGeneratorException with message.
     */
    public void testBuildScriptGeneratorException1() {
        BaseException e = new BuildScriptGeneratorException(MESSAGE);
        assertEquals("Error message is not properly set.", MESSAGE, e.getMessage());
        assertNull("Exception cause is not properly set.", e.getCause());
    }

    /**
     * Creation test for BuildScriptGeneratorException with message and cause.
     */
    public void testBuildScriptGeneratorException2() {
        Throwable cause = new Exception();
        BaseException e = new BuildScriptGeneratorException(MESSAGE, cause);
        assertEquals("Error message is not properly set.", MESSAGE + ", caused by " + cause.getMessage(), e
            .getMessage());
        assertSame("Exception cause is not properly set.", e.getCause(), cause);
    }
}
