/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import junit.framework.TestCase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The unit test for the class {@link Message}.
 * 
 * @author yqw
 * @version 2.0
 */
public class MessageTests extends TestCase {
    /**
     * The Message instance for test.
     */
    private Message instance;
    /**
     * the map.
     */
    private Map<String, MessageAttribute> attributes = new HashMap<String, MessageAttribute>();
    /**
     * id for test.
     */
    private long id = 2L;
    /**
     * string name for test.
     */
    private String name = "name";
    /**
     * date value.
     */
    private Date date = new Date();
    /**
     * message value.
     */
    private String message = "message";
    /**
     * the MessageAttribute instance for test.
     */
    private MessageAttribute attribute = new MessageAttribute("name", "value");

    /**
     * sets up the test environment.
     */
    protected void setUp() {
        instance = new Message();
    }

    /**
     * tear down the test environment
     */
    protected void tearDown() {
        instance = null;
    }

    /**
     * The accuracy test for default constructor{@link Message#Message()}
     */
    public void testCtor() {
        assertEquals("The attributes is incorrect.", attributes, instance
                .getAllAttributes());
    }

    /**
     * The accuracy test for constructor{@link Message#Message(String name, Date date, String message)}
     */
    public void testCtor1() {
        instance = new Message(name, date, message);
        assertEquals("The name is incorrect.", name, instance.getName());
        assertEquals("The date is incorrect.", date, instance.getDate());
        assertEquals("The message is incorrect.", message, instance
                .getMessage());
        assertEquals("The attributes is incorrect.", attributes, instance
                .getAllAttributes());
    }

    /**
     * The failure test for the constructor. The name is null. IllegalArgumentException should be
     * thrown.
     */
    public void testCtor1_name_null() {
        try {
            new Message(null, date, "message");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The failure test for the constructor. The name is empty. IllegalArgumentException should be
     * thrown.
     */
    public void testCtor1_name_empty() {
        try {
            new Message("", date, "message");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The failure test for the constructor. The date is null. IllegalArgumentException should be
     * thrown.
     */
    public void testCtor1_date_null() {
        try {
            new Message("name", null, "message");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The failure test for the constructor. The message is null. IllegalArgumentException should be
     * thrown.
     */
    public void testCtor1_message_null() {
        try {
            new Message("name", date, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The failure test for the constructor. The message is empty. IllegalArgumentException should
     * be thrown.
     */
    public void testCtor1_message_empty() {
        try {
            new Message("name", date, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The accuracy test for the constructor
     * {@link Message#Message(long id, String name, Date date, String message)}.
     */
    public void testCtor2() {
        instance = new Message(id, "name", date, "message");
        assertEquals("The id is incorrect.", id, instance.getId());
        assertEquals("The name is incorrect.", "name", instance.getName());
        assertEquals("The date is incorrect.", date, instance.getDate());
        assertEquals("The message is incorrect.", "message", instance
                .getMessage());
    }

    /**
     * The failure test for the constructor. The id is negative. IllegalArgumentException should be
     * thrown.
     */
    public void testCtor2_id_Negative() {
        try {
            instance = new Message(-id, "name", date, "message");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The failure test for the constructor. The name is null. IllegalArgumentException should be
     * thrown.
     */
    public void testCtor2_name_null() {
        try {
            new Message(id, null, date, "message");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The failure test for the constructor. The name is empty. IllegalArgumentException should be
     * thrown.
     */
    public void testCtor2_name_empty() {
        try {
            new Message(id, "", date, "message");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The failure test for the constructor. The date is null. IllegalArgumentException should be
     * thrown.
     */
    public void testCtor2_date_null() {
        try {
            new Message(id, "name", null, "message");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The failure test for the constructor. The message is null. IllegalArgumentException should be
     * thrown.
     */
    public void testCtor2_message_null() {
        try {
            new Message(id, "name", date, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The failure test for the constructor. The message is empty. IllegalArgumentException should
     * be thrown.
     */
    public void testCtor2_message_empty() {
        try {
            new Message(id, "name", date, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * This method tests the default constructor to set the fields properly.
     * </p>
     */
    public void testCtor3() {
        Date someDate = new Date();
        Map<String, MessageAttribute> attributes = new HashMap<String, MessageAttribute>();
        attributes.put("some attr", new MessageAttribute());
        Response response = new Response();
        Message message = new Message("some name", someDate, "some message",
                attributes, response);
        assertNotNull("attributes not set propery by the constructor", message
                .getAllAttributes());
        assertEquals("attributes not set propery by the constructor",
                attributes, message.getAllAttributes());
        assertEquals("Response not set propery by the constructor", response,
                message.getResponse());

        assertEquals("Id not set propery by the constructor", -1, message
                .getId());
        assertEquals("name not set propery by the constructor", "some name",
                message.getName());
        assertEquals("date not set propery by the constructor", someDate,
                message.getDate());
        assertEquals("message not set propery by the constructor",
                "some message", message.getMessage());
    }

    /**
     * <p>
     * This method tests the default constructor to set the fields properly.
     * </p>
     */
    public void testCtor4() {
        Date someDate = new Date();
        Map<String, MessageAttribute> attributes = new HashMap<String, MessageAttribute>();
        attributes.put("some name", new MessageAttribute("some name",
                "some value"));
        Response response = new Response();
        Message message = new Message(1, "some name", someDate, "some message",
                attributes, response);
        assertNotNull("attributes not set propery by the constructor", message
                .getAllAttributes());
        assertEquals("attributes not set propery by the constructor",
                attributes, message.getAllAttributes());
        assertEquals("Response not set propery by the constructor", response,
                message.getResponse());

        assertEquals("Id not set propery by the constructor", 1, message
                .getId());
        assertEquals("name not set propery by the constructor", "some name",
                message.getName());
        assertEquals("date not set propery by the constructor", someDate,
                message.getDate());
        assertEquals("message not set propery by the constructor",
                "some message", message.getMessage());
    }

    /**
     * The accuracy test for method {@link Message#addAttribute(MessageAttribute)}
     */
    public void testAddAttribute() {
        instance.addAttribute(attribute);
        assertEquals("The attribute is incorrect.", attribute, instance
                .getAttribute("name"));
    }

    /**
     * The failure test for the constructor. The attribute is null. IllegalArgumentException should
     * be thrown.
     */
    public void testAddAttribute_null() {
        try {
            instance.addAttribute(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The accuracy test for method
     * {@link Message#setAttributes(Map<String, MessageAttribute> attributes)}
     */
    public void testSetAttributes() {
        attributes.put("key", new MessageAttribute());
        instance.setAttributes(attributes);
    }

    /**
     * The accuracy test for method {@link Message#removeAttribute(String)}
     */
    public void testremoveAttribute() {
        instance.addAttribute(attribute);
        MessageAttribute attr = instance.removeAttribute(attribute.getName());
        assertEquals("The attribute is incorrect.", attribute, attr);
    }

    /**
     * The accuracy test for method {@link Message#clearAttributes()}
     */
    public void testclear() {
        instance.addAttribute(attribute);

        instance.clearAttributes();
        MessageAttribute attr = instance.removeAttribute(attribute.getName());
        assertNull("The attibute should be null.", attr);
    }

    /**
     * The accuracy test for method {@link Message#getAttribute(String)}
     */
    public void testgetAttribute() {
        instance.addAttribute(attribute);
        MessageAttribute attr = instance.removeAttribute(attribute.getName());
        assertEquals("The attribute is incorrect.", attribute, attr);
    }

    /**
     * The accuracy test for method {@link Message#getAllAttributes()}
     */
    public void testGetAllAttributes() {
        Map<String, MessageAttribute> map = instance.getAllAttributes();
        assertEquals("the size is incorrect.", 0, map.size());
    }

    /**
     * The accuracy test for method {@link Message#containsAttribute(String)}
     */
    public void testcontainsAttribute() {
        instance.addAttribute(attribute);
        boolean result = instance.containsAttribute(attribute.getName());
        assertTrue("the result should be true.", result);
    }

    /**
     * The accuracy test for method {@link Message#getResponse()}
     */
    public void testgetResponse() {
        Response r = instance.getResponse();
        assertNull(r);
        r = new Response();
        instance.setResponse(r);
        Response retrieved = instance.getResponse();
        assertEquals("the result is incorrect.", retrieved, r);
    }

}
