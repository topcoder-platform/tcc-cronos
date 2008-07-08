/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.messaging.accuracytests;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.messaging.Message;
import com.topcoder.messaging.MessageAttribute;
import com.topcoder.messaging.Response;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the accuracy test cases to test the
 * <code>Message</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestMessage extends TestCase {

    /**
     * <p>
     * Represents the instance of message to be used for testing.
     * </p>
     */
    private Message message = new Message();

    /**
     * <p>
     * This method tests the default constructor to set the fields to null.
     * </p>
     */
    public void testDefaultCtor() {
        Message message = new Message();
        assertNotNull("attributes not set propery by the constructor", message.getAllAttributes());
        assertEquals("attributes not set propery by the constructor", 0, message.getAllAttributes().size());
        assertNull("Response not set propery by the constructor", message.getResponse());
    }

    /**
     * <p>
     * This method tests the default constructor to set the fields properly.
     * </p>
     */
    public void testCtor1() {
        Date someDate = new Date();
        Message message = new Message("some name", someDate, "some message");
        assertNotNull("attributes not set propery by the constructor", message.getAllAttributes());
        assertEquals("attributes not set propery by the constructor", 0, message.getAllAttributes().size());
        assertNull("Response not set propery by the constructor", message.getResponse());

        assertEquals("Id not set propery by the constructor", -1, message.getId());
        assertEquals("name not set propery by the constructor", "some name", message.getName());
        assertEquals("date not set propery by the constructor", someDate, message.getDate());
        assertEquals("message not set propery by the constructor", "some message", message.getMessage());
    }

    /**
     * <p>
     * This method tests the default constructor to set the fields properly.
     * </p>
     */
    public void testCtor2() {
        Date someDate = new Date();
        Message message = new Message(1, "some name", someDate, "some message");
        assertNotNull("attributes not set propery by the constructor", message.getAllAttributes());
        assertEquals("attributes not set propery by the constructor", 0, message.getAllAttributes().size());
        assertNull("Response not set propery by the constructor", message.getResponse());

        assertEquals("Id not set propery by the constructor", 1, message.getId());
        assertEquals("name not set propery by the constructor", "some name", message.getName());
        assertEquals("date not set propery by the constructor", someDate, message.getDate());
        assertEquals("message not set propery by the constructor", "some message", message.getMessage());
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
        Message message = new Message("some name", someDate, "some message", attributes, response);
        assertNotNull("attributes not set propery by the constructor", message.getAllAttributes());
        assertEquals("attributes not set propery by the constructor", attributes, message.getAllAttributes());
        assertEquals("Response not set propery by the constructor", response, message.getResponse());

        assertEquals("Id not set propery by the constructor", -1, message.getId());
        assertEquals("name not set propery by the constructor", "some name", message.getName());
        assertEquals("date not set propery by the constructor", someDate, message.getDate());
        assertEquals("message not set propery by the constructor", "some message", message.getMessage());
    }

    /**
     * <p>
     * This method tests the default constructor to set the fields properly.
     * </p>
     */
    public void testCtor4() {
        Date someDate = new Date();
        Map<String, MessageAttribute> attributes = new HashMap<String, MessageAttribute>();
        attributes.put("some name", new MessageAttribute("some name", "some value"));
        Response response = new Response();
        Message message = new Message(1, "some name", someDate, "some message", attributes, response);
        assertNotNull("attributes not set propery by the constructor", message.getAllAttributes());
        assertEquals("attributes not set propery by the constructor", attributes, message.getAllAttributes());
        assertEquals("Response not set propery by the constructor", response, message.getResponse());

        assertEquals("Id not set propery by the constructor", 1, message.getId());
        assertEquals("name not set propery by the constructor", "some name", message.getName());
        assertEquals("date not set propery by the constructor", someDate, message.getDate());
        assertEquals("message not set propery by the constructor", "some message", message.getMessage());
    }

    /**
     * <p>
     * This method tests the addAttribute()and getAttribute() methods to be proper.
     * </p>
     */
    public void testAddGetAttribute() {
        MessageAttribute attribute = new MessageAttribute("some name", "some value");
        this.message.addAttribute(attribute);
        assertEquals("addAttribute()/getAttribute() is not proper", attribute, this.message.getAttribute("some name"));

        MessageAttribute attribute2 = new MessageAttribute("some name", "some value2");
        this.message.addAttribute(attribute2);
        assertEquals("addAttribute()/getAttribute() is not proper", attribute2, this.message.getAttribute("some name"));
    }

    /**
     * <p>
     * This method tests the setAttributes()and getAllAttributes() methods to be proper.
     * </p>
     */
    public void testSetGetAttributes() {
        Map<String, MessageAttribute> attributes = new HashMap<String, MessageAttribute>();
        MessageAttribute attribute = new MessageAttribute("some name", "some value");
        attributes.put("some name", attribute);
        MessageAttribute attribute2 = new MessageAttribute("some name2", "some value2");
        attributes.put("some name2", attribute2);
        this.message.setAttributes(attributes);

        assertEquals("setAttributes()/getAllAttributes() is not proper", attributes, this.message.getAllAttributes());
    }

    /**
     * <p>
     * This method tests the removeAttribute()and getAttribute() methods to be proper.
     * </p>
     */
    public void testRemoveGetAttribute() {
        MessageAttribute attribute = new MessageAttribute("some name", "some value");
        this.message.addAttribute(attribute);
        MessageAttribute attribute2 = new MessageAttribute("some name2", "some value2");
        this.message.addAttribute(attribute2);

        MessageAttribute attr = this.message.removeAttribute("some name");
        assertEquals("removeAttribute() not proper", attribute, attr);
        assertNull("removeAttribute() not proper", this.message.getAttribute("some name"));

        attr = this.message.removeAttribute("some name");
        assertNull("removeAttribute() not proper", attr);
    }

    /**
     * <p>
     * This method tests the clearAttribute()and getAllAttributes() methods to be proper.
     * </p>
     */
    public void testClearAttribute() {
        MessageAttribute attribute = new MessageAttribute("some name", "some value");
        this.message.addAttribute(attribute);
        MessageAttribute attribute2 = new MessageAttribute("some name2", "some value2");
        this.message.addAttribute(attribute2);

        this.message.clearAttributes();
        assertEquals("clearAttributes() not proper", 0, this.message.getAllAttributes().size());
    }

    /**
     * <p>
     * This method tests the containsAttribute() method to be proper.
     * </p>
     */
    public void testContainsAttribute() {
        MessageAttribute attribute = new MessageAttribute("some name", "some value");
        this.message.addAttribute(attribute);
        MessageAttribute attribute2 = new MessageAttribute("some name2", "some value2");
        this.message.addAttribute(attribute2);

        assertTrue("containsAttribute() not proper", this.message.containsAttribute("some name"));
        assertFalse("containsAttribute() not proper", this.message.containsAttribute("some name1"));
    }

    /**
     * <p>
     * This method tests the getter and setter of response to be proper.
     * </p>
     */
    public void testGetSetResponse() {
        Response response = new Response();
        this.message.setResponse(response);
        assertEquals("getter/setter of response not proper", response, this.message.getResponse());
    }

}
