/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import junit.framework.TestCase;


/**
 * The unit test for the class {@link MessageAttribute}.
 * @author yqw
 * @version 2.0
 */
public class MessageAttributeTests extends TestCase {
    /**
     * The MessageAttribute instance for test.
     */
    private MessageAttribute instance;

    /**
     * sets up the test environment.
     */
    protected void setUp() {
        instance = new MessageAttribute();
    }

    /**
     * tear down the test environment
     */
    protected void tearDown() {
        instance = null;
    }

    /**
     * The accuracy test for the default constructor {@link MessageAttribute#MessageAttribute()}.
     */
    public void testCtor() {
        assertNotNull("The instance should not be null.");
    }

    /**
     * The accuracy test for constructor
     * {@link MessageAttribute#MessageAttribute(String name, String value)}.
     */
    public void testCtor1() {
        instance = new MessageAttribute("name", "value");
        assertEquals("The name is incorrect.", "name", instance.getName());
        assertEquals("The value is incorrect.", "value", instance.getValue());
    }

    /**
     * The failure test for the constructor. The name is null. IllegalArgumentException should be thrown.
     */
    public void testCtor1_name_null() {
        try {
            new MessageAttribute(null, "value");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor. The name is empty. IllegalArgumentException should be thrown.
     */
    public void testCtor1_name_empty() {
        try {
            new MessageAttribute("", "value");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor. The value is null. IllegalArgumentException should be thrown.
     */
    public void testCtor1_value_null() {
        try {
            new MessageAttribute("name", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor. The value is empty. IllegalArgumentException should be thrown.
     */
    public void testCtor1_value_empty() {
        try {
            new MessageAttribute("name", "");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for method {@link MessageAttribute#getName()}
     */
    public void testGetName() {
        instance.setName("test");
        assertEquals("The name is incorrect.", "test", instance.getName());
    }

    /**
     * The accuracy test for the method {@link MessageAttribute#setName(String name)}.
     */
    public void testSetName() {
        instance.setName("test");
        assertEquals("The name is incorrect.", "test", instance.getName());
    }

    /**
     * The failure test for method
     * {@link MessageAttribute#setName(String name)}. The name is null. IllegalArgumentException should be thrown.
     */
    public void testSetName_name_null() {
        try {
            instance.setName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for method {@link MessageAttribute#setName(String name)}. The name is empty. IllegalArgumentException should be thrown.
     */
    public void testSetName_name_empty() {
        try {
            instance.setName("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for method {@link MessageAttribute#getValue()}
     */
    public void testGetValue() {
        instance.setValue("test");
        assertEquals("The Value is incorrect.", "test", instance.getValue());
    }

    /**
     * The accuracy test for the method {@link MessageAttribute#setValue(String value)}.
     */
    public void testSetValue() {
        instance.setValue("test");
        assertEquals("The Value is incorrect.", "test", instance.getValue());
    }

    /**
     * The failure test for method {@link MessageAttribute#setValue(String value)}. The Value is null. IllegalArgumentException should be thrown.
     */
    public void testSetValue_Value_null() {
        try {
            instance.setValue(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for method {@link MessageAttribute#setValue(String value)}. The Value is empty. IllegalArgumentException should be thrown.
     */
    public void testSetValue_Value_empty() {
        try {
            instance.setValue("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }
}
