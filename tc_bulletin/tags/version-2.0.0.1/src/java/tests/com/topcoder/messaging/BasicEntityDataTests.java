/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import junit.framework.TestCase;

import java.util.Date;


/**
 * The unit test for the class {@link BasicEntityData}.
 * @author yqw
 * @version 2.0
 */
public class BasicEntityDataTests extends TestCase {
    /**
     * The Response instance for test.
     */
    private Response instance;

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
     * The accuracy test for the default constructor {@link BasicEntityData#BasicEntityData()}.
     */
    public void testCtor() {
        instance = new Response();
        assertEquals("The id is incorrect.", -1, instance.getId());
    }

    /**
     * The accuracy test for the constructor{@link BasicEntityData#BasicEntityData(long id)}.
     */
    public void testCtor_id() {
        Date date = new Date();
        instance = new Response(2L, "name", date, "message");
        assertEquals("The id is incorrect.", 2L, instance.getId());
    }

    /**
     * The failure test for the constructor. The id is negative.
     * IllegalArgumentException should be thrown.
     */
    public void testCtor_id_Negative() {
        try {
            Date date = new Date();
            new Response(-2L, "name", date, "mesage");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The accuracy test for the method {@link BasicEntityData#getId()}.
     */
    public void testGetId() {
        assertEquals("The id is incorrect.", -1, instance.getId());
        instance.setId(2L);
        assertEquals("The id is incorrect.", 2L, instance.getId());
    }

    /**
     * The accuracy test for the method {@link BasicEntityData#setId()}.
     */
    public void testSetId() {
        instance.setId(2L);
        assertEquals("The id is incorrect.", 2L, instance.getId());
    }
}
