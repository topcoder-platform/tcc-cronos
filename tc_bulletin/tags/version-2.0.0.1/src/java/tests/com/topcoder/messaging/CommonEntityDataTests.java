/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import junit.framework.TestCase;

import java.util.Date;


/**
 * The unit test for the class CommonEntityData.
 * @author yqw
 * @version 2.0
 */
public class CommonEntityDataTests extends TestCase {
    /**
     * The Response instance for test.
     */
    private Response instance;
    /**
     * the test for test.
     */
    private Date date = new Date();

    /**
     * sets up the test environment.
     */
    protected void setUp() {
        instance = new Response();
    }

    /**
     * tear down the test environment
     */
    protected void tearDown() {
        instance = null;
    }

    /**
     * The accuracy test for the default constructor {@link CommonEntityData#CommonEntityData()}.
     */
    public void testCtor() {
        assertEquals("The id is incorrect.", -1, instance.getId());
    }

    /**
     * The accuracy test for the constructor
     * {@link CommonEntityData#CommonEntityData(String name, Date date, String message)}.
     */
    public void testCtor1() {
        instance = new Response("name", date, "message");
        assertEquals("The name is incorrect.", "name", instance.getName());
        assertEquals("The date is incorrect.", date, instance.getDate());
        assertEquals("The message is incorrect.", "message",
            instance.getMessage());
    }

    /**
     * The failure test for the constructor. The name is null. IllegalArgumentException should be thrown.
     */
    public void testCtor1_name_null() {
        try {
            new Response(null, date, "message");
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
            new Response("", date, "message");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor. The date is null. IllegalArgumentException should be thrown.
     */
    public void testCtor1_date_null() {
        try {
            new Response("name", null, "message");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor. The message is null. IllegalArgumentException should be thrown.
     */
    public void testCtor1_message_null() {
        try {
            new Response("name", date, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor. The message is empty. IllegalArgumentException should be thrown.
     */
    public void testCtor1_message_empty() {
        try {
            new Response("name", date, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }
    /**
     * The accuracy test for the constructor
     * {@link CommonEntityData#CommonEntityData(long id, String name, Date date, String message)}.
     */
    public void testCtor2() {
        instance = new Response(2L,"name", date, "message");
        assertEquals("The id is incorrect.", 2L, instance.getId());
        assertEquals("The name is incorrect.", "name", instance.getName());
        assertEquals("The date is incorrect.", date, instance.getDate());
        assertEquals("The message is incorrect.", "message",
            instance.getMessage());
    }

    /**
     * The failure test for the constructor. The id is negative.
     * IllegalArgumentException should be thrown.
     */
    public void testCtor2_id_Negative() {
        try {
            instance = new Response(-2L, "name", date, "message");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }


    /**
     * The failure test for the constructor. The name is null. IllegalArgumentException should be thrown.
     */
    public void testCtor2_name_null() {
        try {
            new Response(2L,null, date, "message");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor. The name is empty. IllegalArgumentException should be thrown.
     */
    public void testCtor2_name_empty() {
        try {
            new Response(2L,"", date, "message");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor. The date is null. IllegalArgumentException should be thrown.
     */
    public void testCtor2_date_null() {
        try {
            new Response(2L,"name", null, "message");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor. The message is null. IllegalArgumentException should be thrown.
     */
    public void testCtor2_message_null() {
        try {
            new Response(2L,"name", date, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for the constructor. The message is empty. IllegalArgumentException should be thrown.
     */
    public void testCtor2_message_empty() {
        try {
            new Response(2L,"name", date, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for the method{@link CommonEntityData#getName()}
     */
    public void testGetName() {
        instance.setName("name");
        assertEquals("The name is incorrect.", "name", instance.getName());
    }

    /**
     * The accuracy test for the method{@link CommonEntityData#setName(String name)}
     */
    public void testSetName() {
        instance.setName("name");
        assertEquals("The name is incorrect.", "name", instance.getName());
    }

    /**
     * The failure test for setName(String name). The name is null. IllegalArgumentException should be thrown.
     */
    public void testSetName_null() {
        try {
            instance.setName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for setName(String name). The name is empty. IllegalArgumentException should be thrown.
     */
    public void testSetName_empty() {
        try {
            instance.setName("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for the method{@link CommonEntityData#getDate()}
     */
    public void testGetDate() {
        instance.setDate(date);
        assertEquals("The date is incorrect", date, instance.getDate());
    }

    /**
     * The accuracy test for the method {@link CommonEntityData#setDate(Date)}
     */
    public void testSetDate() {
        instance.setDate(date);
        assertEquals("The date is incorrect", date, instance.getDate());
    }

    /**
     * The failure test for setDate(Date date).The date is null.IllegalArgumentException should be thrown.
     */
    public void testSetDate_null() {
        try {
            instance.setDate(null);
            fail("The date should not be null .");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The accuracy test for the method{@link CommonEntityData#getMessage()}
     */
    public void testGetMessage() {
        instance.setMessage("message");
        assertEquals("The message is incorrect", "message",
            instance.getMessage());
    }

    /**
     * The failure test for setMessage(String Message). The Message is null. IllegalArgumentException should be thrown.
     */
    public void testSetMessage_null() {
        try {
            instance.setMessage(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * The failure test for setMessage(String Message). The Message is empty. IllegalArgumentException should be thrown.
     */
    public void testSetMessage_empty() {
        try {
            instance.setMessage("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }
}
