/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>MessageImpl</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class MessageImplTests extends TestCase {
    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * GUID.
     */
    public void test_ctor_null_guid() {
        try {
            new MessageImpl(null, "category", "content_type", "message", new Date());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed an empty GUID.
     */
    public void test_ctor_empty_guid() {
        try {
            new MessageImpl("  ", "category", "content_type", "message", new Date());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * category.
     */
    public void test_ctor_null_category() {
        try {
            new MessageImpl("guid", null, "content_type", "message", new Date());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed an empty category.
     */
    public void test_ctor_empty_category() {
        try {
            new MessageImpl("guid", "  ", "content_type", "message", new Date());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * content type.
     */
    public void test_ctor_null_content_type() {
        try {
            new MessageImpl("guid", "category", null, "message", new Date());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed an empty content type.
     */
    public void test_ctor_empty_conten_type() {
        try {
            new MessageImpl("guid", "category", "  ", "message", new Date());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * message content.
     */
    public void test_ctor_null_content() {
        try {
            new MessageImpl("guid", "category", "content_type", null, new Date());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed an empty message
     * content.
     */
    public void test_ctor_empty_content() {
        try {
            new MessageImpl("guid", "category", "content_type", "  ", new Date());
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * timestamp.
     */
    public void test_ctor_null_timestamp() {
        try {
            new MessageImpl("guid", "category", "content_type", "content", null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>getContent</code> method.
     */
    public void test_getContent() {
        assertEquals("getContent() should return the content passed to the constructor", "content",
                     new MessageImpl("guid", "category", "content_type", "content", new Date()).getContent());
    }

    /**
     * Tests the <code>getContentType</code> method.
     */
    public void test_getContentType() {
        assertEquals("getContentType() should return the content type passed to the constructor", "content_type",
                     new MessageImpl("guid", "category", "content_type", "content", new Date()).getContentType());
    }

    /**
     * Tests the <code>getGuid</code> method.
     */
    public void test_getGuid() {
        assertEquals("getGuid() should return the content passed to the constructor", "guid",
                     new MessageImpl("guid", "category", "content_type", "content", new Date()).getGuid());
    }

    /**
     * Tests the <code>getCategory</code> method.
     */
    public void test_getCategory() {
        assertEquals("getCategory() should return the content passed to the constructor", "category",
                     new MessageImpl("guid", "category", "content_type", "content", new Date()).getCategory());
    }

    /**
     * Tests the <code>getTimestamp</code> method.
     */
    public void test_getTimestamp() {
        Date timestamp = new Date();
        assertEquals("getTimestamp() should return the content passed to the constructor", timestamp,
                     new MessageImpl("guid", "category", "content_type", "content", timestamp).getTimestamp());
    }
}
