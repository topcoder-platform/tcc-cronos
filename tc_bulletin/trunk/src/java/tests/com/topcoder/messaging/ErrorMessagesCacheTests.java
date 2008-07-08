/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import junit.framework.TestCase;


/**
 * The unit test for the class ErrorMessagesCache.
 * @author yqw
 * @version 2.0
 */
public class ErrorMessagesCacheTests extends TestCase {
	/**
	 * The accuracy test for default constructor{@link ErrorMessagesCache#ErrorMessagesCache().
	 */		
	public void testCtor(){
		assertNotNull("The instance should not be null.");
	}
    /**
     * The accuracy test for method {@link ErrorMessagesCache#getErrorMessage(String name)}.
     */
    public void testGetErrorMessage() {
        ErrorMessagesCache.addErrorMessage("name", "value");

        String result = ErrorMessagesCache.getErrorMessage("name");
        assertEquals("The name is incorrect.", "value", result);
    }

    /**
     * The failure test for method {@link ErrorMessagesCache#getErrorMessage(String name)}.
     * The name is null; IllegalArgumentException should be thrown.
     */
    public void testGetErrorMessage_name_null() {
        try {
            ErrorMessagesCache.getErrorMessage(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for for method {@link ErrorMessagesCache#getErrorMessage(String name)}.
     * The name is empty. IllegalArgumentException should be thrown.
     */
    public void testGetErrorMessage_name_empty() {
        try {
            ErrorMessagesCache.getErrorMessage("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for method {@link ErrorMessagesCache#addErrorMessage(String, String)}
     */
    public void testAddErrorMessage() {
        ErrorMessagesCache.addErrorMessage("name", "value");

        String result = ErrorMessagesCache.getErrorMessage("name");
        assertEquals("The name is incorrect.", "value", result);
    }

    /**
     * The failure test for method {@link ErrorMessagesCache#addErrorMessage(String, String)}.
     * The name is null; IllegalArgumentException should be thrown.
     */
    public void testAddErrorMessage_name_null() {
        try {
            ErrorMessagesCache.addErrorMessage(null, "value");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for for method {@link ErrorMessagesCache#addErrorMessage(String, String)}.
     * The name is empty. IllegalArgumentException should be thrown.
     */
    public void testAddErrorMessage_name_empty() {
        try {
            ErrorMessagesCache.addErrorMessage("", "value");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for method {@link ErrorMessagesCache#addErrorMessage(String, String)}.
     * The value is null; IllegalArgumentException should be thrown.
     */
    public void testAddErrorMessage_value_null() {
        try {
            ErrorMessagesCache.addErrorMessage("name", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for for method {@link ErrorMessagesCache#addErrorMessage(String, String)}.
     * The value is empty. IllegalArgumentException should be thrown.
     */
    public void testAddErrorMessage_value_empty() {
        try {
            ErrorMessagesCache.addErrorMessage("name", "");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for method {@link ErrorMessagesCache#removeErrorMessage(String name)}
     */
    public void testRemoveErrorMessage() {
        ErrorMessagesCache.addErrorMessage("name", "value");

        String result = ErrorMessagesCache.removeErrorMessage("name");
        assertEquals("The errorMessage is not removed.", "value", result);
        assertEquals("The errorMessage is not removed.", null,
            ErrorMessagesCache.getErrorMessage("name"));
    }

    /**
     * The failure test for method {@link ErrorMessagesCache#removeErrorMessage(String)}.
     * The name is null; IllegalArgumentException should be thrown.
     */
    public void testRemoveErrorMessage_name_null() {
        try {
            ErrorMessagesCache.removeErrorMessage(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for for method {@link ErrorMessagesCache#removeErrorMessage(String)}.
     * The name is empty. IllegalArgumentException should be thrown.
     */
    public void testRemoveErrorMessage_name_empty() {
        try {
            ErrorMessagesCache.removeErrorMessage("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for method {@link ErrorMessagesCache#clearErrorMessages()}.
     */
    public void testClearErrorMessage() {
        ErrorMessagesCache.addErrorMessage("name1", "value1");
        ErrorMessagesCache.addErrorMessage("name2", "value2");
        ErrorMessagesCache.clearErrorMessages();
        assertEquals("The errorMessage is not cleared.", null,
            ErrorMessagesCache.getErrorMessage("name1"));
        assertEquals("The errorMessage is not cleared.", null,
            ErrorMessagesCache.getErrorMessage("name2"));
    }
}
