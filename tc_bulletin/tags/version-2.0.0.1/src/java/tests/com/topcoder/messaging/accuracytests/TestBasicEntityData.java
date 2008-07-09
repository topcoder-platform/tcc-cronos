/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.messaging.accuracytests;


import java.util.Date;

import com.topcoder.messaging.BasicEntityData;
import com.topcoder.messaging.Message;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the accuracy test cases to test the
 * <code>BasicEntityData</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestBasicEntityData extends TestCase {

    /**
     * <p>
     * Represents the instance of <code>BasicEntityData</code> to be used for
     * testing.
     * </p>
     */
    private BasicEntityData entityData = new Message();

    /**
     * <p>
     * This method tests the default constructor to set the id to -1.
     * </p>
     */
    public void testDefaultCtor() {
        this.entityData = new Message();
        assertEquals("Id not set propery by the default constructor", -1, this.entityData.getId());
    }

    /**
     * <p>
     * This method tests the ctor with ID argument to set the ID proerly.
     * </p>
     */
    public void testDefaultCtorId() {
        this.entityData = new Message(1, "some name", new Date(), "some message");
        assertEquals("Id not set propery by the constructor", 1, this.entityData.getId());
    }

    /**
     * <p>
     * This method tests the setter and getter of the id field to be working
     * properly.
     * </p>
     */
    public void testGetSetId() {
        this.entityData.setId(1);
        assertEquals("Id getter setter not proper", 1, this.entityData.getId());
    }

}
