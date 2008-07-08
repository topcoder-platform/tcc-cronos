/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.messaging.accuracytests;


import java.util.Date;

import com.topcoder.messaging.CommonEntityData;
import com.topcoder.messaging.Message;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the accuracy test cases to test the
 * <code>CommonEntityData</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestCommonEntityData extends TestCase {

    /**
     * <p>
     * Represents the instance of <code>CommonEntityData</code> to be used for
     * testing.
     * </p>
     */
    private CommonEntityData entityData = new Message();

    /**
     * <p>
     * This method tests the default constructor to set the id to -1.
     * </p>
     */
    public void testDefaultCtor() {
        this.entityData = new Message();
        assertEquals("Id not set propery by the default constructor", -1, this.entityData.getId());
        assertNull("Name not set propery by the constructor", this.entityData.getName());
        assertNull("Date not set propery by the constructor", this.entityData.getDate());
        assertNull("Message not set propery by the constructor", this.entityData.getMessage());
    }

    /**
     * <p>
     * This method tests the ctor with name, date and message argument to
     * set the fields proeprly.
     * </p>
     */
    public void testDefaultCtorWithoutId() {
        Date someDate = new Date();
        this.entityData = new Message("some name", someDate, "some message");
        assertEquals("Id not set propery by the constructor", -1, this.entityData.getId());
        assertEquals("name not set propery by the constructor", "some name", this.entityData.getName());
        assertEquals("date not set propery by the constructor", someDate, this.entityData.getDate());
        assertEquals("message not set propery by the constructor", "some message", this.entityData.getMessage());
    }

    /**
     * <p>
     * This method tests the ctor with name, date and message argument to
     * set the fields proeprly.
     * </p>
     */
    public void testDefaultCtorWithId() {
        Date someDate = new Date();
        this.entityData = new Message(1, "some name", someDate, "some message");
        assertEquals("Id not set propery by the constructor", 1, this.entityData.getId());
        assertEquals("name not set propery by the constructor", "some name", this.entityData.getName());
        assertEquals("date not set propery by the constructor", someDate, this.entityData.getDate());
        assertEquals("message not set propery by the constructor", "some message", this.entityData.getMessage());
    }

    /**
     * <p>
     * This method tests the setter and getter of the name field to be working
     * properly.
     * </p>
     */
    public void testGetSetName() {
        this.entityData.setName("some name");
        assertEquals("Name getter setter not proper", "some name", this.entityData.getName());
    }

    /**
     * <p>
     * This method tests the setter and getter of the date field to be working
     * properly.
     * </p>
     */
    public void testGetSetDate() {
        Date someDate = new Date();
        this.entityData.setDate(someDate);
        assertEquals("Date getter setter not proper", someDate, this.entityData.getDate());
    }

    /**
     * <p>
     * This method tests the setter and getter of the message field to be working
     * properly.
     * </p>
     */
    public void testGetSetMessage() {
        this.entityData.setMessage("some message");
        assertEquals("Message getter setter not proper", "some message", this.entityData.getMessage());
    }

}
