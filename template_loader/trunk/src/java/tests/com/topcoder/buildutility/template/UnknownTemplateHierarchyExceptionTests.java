/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0 (Unit Test)
 */
package com.topcoder.buildutility.template;

import junit.framework.TestCase;
import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>Unit test cases for UnknownTemplateHierarchyException.</p>
 *
 * <p>This class is pretty simple. The test cases simply verifies the exception can be instantiated with the
 * hierarchy name properly, and that it comes with correct inheritance.</p>
 *
 * @author oldbig
 *
 * @version 1.0
 */
public class UnknownTemplateHierarchyExceptionTests extends TestCase {

    /**
     * <p>Creation test.</p>
     *
     * <p>Verifies the hierarchy name is properly set.</p>
     */
    public void testUnknownTemplateHierarchyException() {
        UnknownTemplateHierarchyException doe = new UnknownTemplateHierarchyException("foo");

        assertNotNull("Unable to instantiate UnknownTemplateHierarchyException.", doe);
        assertEquals("Hierarchy name could not be properly get.", "foo", doe.getHierarchyName());
    }


    /**
     * <p>Inheritance test.</p>
     *
     * <p>Verifies UnknownTemplateHierarchyException subclasses BaseRuntimeException.</p>
     */
    public void testUnknownTemplateHierarchyExceptionInheritance() {
        assertTrue("UnknownTemplateHierarchyException does not subclass BaseRuntimeException.",
            new UnknownTemplateHierarchyException("foo") instanceof BaseRuntimeException);
    }

    /**
     * <p>Tests the getHierarchyName method.</p>
     *
     * <p>Verifies the hierarchy name could be properly get.</p>
     */
    public void getHierarchyName() {
        assertEquals("Hierarchy name could not be properly get.",
            "foo", new UnknownTemplateHierarchyException("foo").getHierarchyName());
    }

}
