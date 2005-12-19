/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0 (Unit Test)
 */
package com.topcoder.buildutility.template;

import junit.framework.TestCase;
import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>Unit test cases for DuplicateObjectException.</p>
 *
 * <p>This class is pretty simple. The test cases simply verifies the exception can be instantiated with the
 * error message and id, and that it comes with correct inheritance.</p>
 *
 * @author oldbig
 *
 * @version 1.0
 */
public class DuplicateObjectExceptionTests extends TestCase {

    /**
     * <p>The error message used for testing.</p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>Creation test.</p>
     *
     * <p>Verifies the error message is properly propagated.</p>
     */
    public void testDuplicateObjectException() {
        DuplicateObjectException doe = new DuplicateObjectException(ERROR_MESSAGE, 1);

        assertNotNull("Unable to instantiate DuplicateObjectException.", doe);
        assertEquals("Error message is not properly propagated to super class.",
            ERROR_MESSAGE, doe.getMessage());
        assertEquals("id is not properly set.", 1, doe.getId());
    }


    /**
     * <p>Inheritance test.</p>
     *
     * <p>Verifies DuplicateObjectException subclasses BaseRuntimeException.</p>
     */
    public void testDuplicateObjectExceptionInheritance() {
        assertTrue("DuplicateObjectException does not subclass BaseRuntimeException.",
            new DuplicateObjectException(ERROR_MESSAGE, 2) instanceof BaseRuntimeException);
    }

    /**
     * <p>Tests the getId method.</p>
     *
     * <p>Verifies the id is properly set.</p>
     */
    public void testGetId() {
        DuplicateObjectException doe = new DuplicateObjectException(ERROR_MESSAGE, 10);
        assertEquals("id is not properly set.", 10, doe.getId());
    }

}
