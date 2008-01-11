/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit tests for <code>CategoryType</code> enum class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CategoryTypeUnitTest extends TestCase {
    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(CategoryTypeUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for <code>CategoryType</code>'s enumeration definition, to verify
     * that CategoryType.APPLICATION exist.
     * </p>
     */
    public void testAPPLICATION() {
        assertNotNull("The CategoryType.APPLICATION should exist.",
            CategoryType.valueOf("APPLICATION"));
    }

    /**
     * <p>
     * Accuracy test for <code>CategoryType</code>'s enumeration definition, to verify
     * that CategoryType.COMPONENT exist.
     * </p>
     */
    public void testCOMPONENT() {
        assertNotNull("The CategoryType.COMPONENT should exist.",
            CategoryType.valueOf("COMPONENT"));
    }

    /**
     * <p>
     * Accuracy test for <code>CategoryType</code>'s enumeration definition, to verify
     * that CategoryType.ASSEMBLY_COMPETITION exist.
     * </p>
     */
    public void testASSEMBLY_COMPETITION() {
        assertNotNull("The CategoryType.ASSEMBLY_COMPETITION should exist.",
            CategoryType.valueOf("ASSEMBLY_COMPETITION"));
    }

    /**
     * <p>
     * Accuracy test for <code>CategoryType</code>'s enumeration definition, to verify
     * that CategoryType.TESTING_COMPETITION exist.
     * </p>
     */
    public void testTESTING_COMPETITION() {
        assertNotNull("The CategoryType.TESTING_COMPETITION should exist.",
            CategoryType.valueOf("TESTING_COMPETITION"));
    }

    /**
     * <p>
     * Failure test for <code>CategoryType</code>'s enumeration definition, to verify
     * that CategoryType.OTHER doesn't exist.
     * </p>
     */
    public void testOTHER() {
        try {
            CategoryType.valueOf("OTHER");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }
}
