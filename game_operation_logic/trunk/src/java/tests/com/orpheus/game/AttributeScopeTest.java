/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import junit.framework.TestCase;


/**
 * Test case for AttributeScope.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AttributeScopeTest extends TestCase {
    /** Represents AttributeScope instance used in this test. */
    private AttributeScope as;

    /**
     * Test AttributeScope(String, String), AttributeScope should be instantiated successfully.
     */
    public void testAttributeScope() {
        assertNotNull("AttributeScope should be instantiated successfully", as);
    }

    /**
     * Test method for AttributeScope(java.lang.String, java.lang.String). In this case, the attribute is
     * empty.
     */
    public void testAttributeScope_EmptyAttr() {
        try {
            new AttributeScope(" ", "request");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for AttributeScope(java.lang.String, java.lang.String). In this case, the scope is empty.
     */
    public void testAttributeScope_EmptyScope() {
        try {
            new AttributeScope("test", " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for AttributeScope(java.lang.String, java.lang.String). In this case, the scope is empty.
     */
    public void testAttributeScope_InvalidScope() {
        try {
            new AttributeScope("test", "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for AttributeScope(java.lang.String, java.lang.String). In this case, the attribute is null.
     */
    public void testAttributeScope_NullAttr() {
        try {
            new AttributeScope(null, "request");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for AttributeScope(java.lang.String, java.lang.String). In this case, the scope is null.
     */
    public void testAttributeScope_NullScope() {
        try {
            new AttributeScope("test", null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method getAttribute(), expected value should be returned.
     */
    public void testGetAttribute() {
        assertEquals("getAttribute() should return", "attribute", as.getAttribute());
    }

    /**
     * Test method getScope(), expected value should be returned.
     */
    public void testGetScope() {
        assertEquals("getAttribute() should return", "request", as.getScope());
    }

    /**
     * Creates a default AttributeScope object for test.
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        as = new AttributeScope("attribute", "request");
    }
}
