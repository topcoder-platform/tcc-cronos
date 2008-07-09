/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.messaging.accuracytests;


import java.util.Date;

import com.topcoder.messaging.Response;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the accuracy test cases to test the
 * <code>Response</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestResponse extends TestCase {

    /**
     * <p>
     * This method tests the default constructor to set the id to -1.
     * </p>
     */
    public void testDefaultCtor() {
        Response response = new Response();
        assertEquals("Id not set propery by the default constructor", -1, response.getId());
        assertNull("Name not set propery by the constructor", response.getName());
        assertNull("Date not set propery by the constructor", response.getDate());
        assertNull("Message not set propery by the constructor", response.getMessage());
    }

    /**
     * <p>
     * This method tests the ctor with name, date and message argument to
     * set the fields proeprly.
     * </p>
     */
    public void testDefaultCtorWithoutId() {
        Date someDate = new Date();
        Response response = new Response("some name", someDate, "some message");
        assertEquals("Id not set propery by the constructor", -1, response.getId());
        assertEquals("name not set propery by the constructor", "some name", response.getName());
        assertEquals("date not set propery by the constructor", someDate, response.getDate());
        assertEquals("message not set propery by the constructor", "some message", response.getMessage());
    }

    /**
     * <p>
     * This method tests the ctor with name, date and message argument to
     * set the fields proeprly.
     * </p>
     */
    public void testDefaultCtorWithId() {
        Date someDate = new Date();
        Response response = new Response(1, "some name", someDate, "some message");
        assertEquals("Id not set propery by the constructor", 1, response.getId());
        assertEquals("name not set propery by the constructor", "some name", response.getName());
        assertEquals("date not set propery by the constructor", someDate, response.getDate());
        assertEquals("message not set propery by the constructor", "some message", response.getMessage());
    }

}
