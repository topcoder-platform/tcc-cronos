/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;

import com.orpheus.administration.persistence.impl.MessageImpl;

import junit.framework.TestCase;

import java.util.Date;


/**
 * Unit tests for MessageImpl class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestMessageImpl extends TestCase {
    /**
     * Tests MessageImpl(String guid, String category, String contentType, String message, Date
     * timestamp) method with null String guid, Expected IllegalArgumentException.
     */
    public void testMessageImpl_NullGuid() {
        try {
            new MessageImpl(null, "x", "type", "hello", new Date());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests MessageImpl(String guid, String category, String contentType, String message, Date
     * timestamp) method with empty String guid, Expected IllegalArgumentException.
     */
    public void testMessageImpl_EmptyGuid() {
        try {
            new MessageImpl(" ", "x", "type", "hello", new Date());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests MessageImpl(String guid, String category, String contentType, String message, Date
     * timestamp) method with null String category, Expected IllegalArgumentException.
     */
    public void testMessageImpl_NullCategory() {
        try {
            new MessageImpl("11322212", null, "type", "hello", new Date());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests MessageImpl(String guid, String category, String contentType, String message, Date
     * timestamp) method with empty String category, Expected IllegalArgumentException.
     */
    public void testMessageImpl_EmptyCategory() {
        try {
            new MessageImpl("11322212", " ", "type", "hello", new Date());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests MessageImpl(String guid, String category, String contentType, String message, Date
     * timestamp) method with null String contentType, Expected IllegalArgumentException.
     */
    public void testMessageImpl_NullContentType() {
        try {
            new MessageImpl("11322212", "x", null, "hello", new Date());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests MessageImpl(String guid, String category, String contentType, String message, Date
     * timestamp) method with empty String contentType, Expected IllegalArgumentException.
     */
    public void testMessageImpl_EmptyContentType() {
        try {
            new MessageImpl("11322212", "x", " ", "hello", new Date());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests MessageImpl(String guid, String category, String contentType, String message, Date
     * timestamp) method with null String message, Expected IllegalArgumentException.
     */
    public void testMessageImpl_NullMessage() {
        try {
            new MessageImpl("11322212", "x", "type", null, new Date());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests MessageImpl(String guid, String category, String contentType, String message, Date
     * timestamp) method with empty String message, Expected IllegalArgumentException.
     */
    public void testMessageImpl_EmptyMessage() {
        try {
            new MessageImpl("11322212", "x", "type", " ", new Date());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests MessageImpl(String guid, String category, String contentType, String message, Date
     * timestamp) method with null Date timestamp, Expected IllegalArgumentException.
     */
    public void testMessageImpl_NullTimestamp() {
        try {
            new MessageImpl("11322212", "x", "type", "hello", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
