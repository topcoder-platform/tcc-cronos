/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.messaging.accuracytests;


import com.topcoder.messaging.MessageAttribute;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the accuracy test cases to test the
 * <code>MessageAttribute</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestMessageAttribute extends TestCase {

    /**
     * <p>
     * Represents the instance of message attribute to be used for testing.
     * </p>
     */
    private MessageAttribute messageAttr = new MessageAttribute();

    /**
     * <p>
     * This method tests the default constructor to set the fields to null.
     * </p>
     */
    public void testDefaultCtor() {
        MessageAttribute messageAttr = new MessageAttribute();
        assertNull("Name not set propery by the constructor", messageAttr.getName());
        assertNull("Value not set propery by the constructor", messageAttr.getValue());
    }

    /**
     * <p>
     * This method tests the ctor with name, date and message argument to
     * set the fields proeprly.
     * </p>
     */
    public void testDefaultCtor2() {
        MessageAttribute messageAttr = new MessageAttribute("some name", "some value");
        assertEquals("name not set propery by the constructor", "some name", messageAttr.getName());
        assertEquals("message not set propery by the constructor", "some value", messageAttr.getValue());
    }

    /**
     * <p>
     * This method tests the setter and getter of the name field to be working
     * properly.
     * </p>
     */
    public void testGetSetName() {
        messageAttr.setName("some name");
        assertEquals("Name getter setter not proper", "some name", messageAttr.getName());
    }

    /**
     * <p>
     * This method tests the setter and getter of the message field to be working
     * properly.
     * </p>
     */
    public void testGetSetMessage() {
        messageAttr.setValue("some message");
        assertEquals("Value getter setter not proper", "some message", messageAttr.getValue());
    }

}
