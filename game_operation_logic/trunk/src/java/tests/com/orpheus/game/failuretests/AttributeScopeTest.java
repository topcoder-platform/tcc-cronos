/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.failuretests;

import com.orpheus.game.AttributeScope;

import junit.framework.TestCase;

/**
 * Test case for <code>AttributeScope</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class AttributeScopeTest extends TestCase {

    /**
     * Test method for AttributeScope(java.lang.String, java.lang.String).
     * In this case, the attribute is null.
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
     * Test method for AttributeScope(java.lang.String, java.lang.String).
     * In this case, the attribute is empty.
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
     * Test method for AttributeScope(java.lang.String, java.lang.String).
     * In this case, the scope is null.
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
     * Test method for AttributeScope(java.lang.String, java.lang.String).
     * In this case, the scope is empty.
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
     * Test method for AttributeScope(java.lang.String, java.lang.String).
     * In this case, the scope is empty.
     */
    public void testAttributeScope_InvalidScope() {
        try {
            new AttributeScope("test", "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
