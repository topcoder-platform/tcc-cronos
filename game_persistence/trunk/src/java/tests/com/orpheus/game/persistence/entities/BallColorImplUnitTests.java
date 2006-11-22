/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.BallColor;

import junit.framework.TestCase;


/**
 * Unit test for <code>BallColorImpl</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 * 
 */
public class BallColorImplUnitTests extends TestCase {
    /** represents the Id constants for testing. */
    public static final Long ID = new Long(1);

    /** represents the name constants for testing. */
    public static final String NAME = "RED";

    /** represents the imageId constants for testing. */
    public static final long IMAGE_ID = 1000L;

    /** BallColorImpl instance to test against. */
    private BallColor ballColorImpl = null;

    /**
     * create instance.
     */
    protected void setUp() {
        ballColorImpl = new BallColorImpl(ID, NAME, IMAGE_ID);
    }

    /**
     * test the ctor, simply verify the instance.
     */
    public void testCtor() {
        assertNotNull("The BallColorImpl can not be instantiate.", ballColorImpl);
    }

    /**
     * test the ctor, the id can be null, it is an accuracy test case.
     */
    public void testCtor_nullId() {
        try {
            new BallColorImpl(null, NAME, IMAGE_ID);
        } catch (Exception e) {
            fail("The id can be null.");
        }
    }

    /**
     * test the ctor, the id is not positive, iae expected.
     */
    public void testCtor_notPositiveId() {
        try {
            new BallColorImpl(new Long(0), NAME, IMAGE_ID);
            fail("The id should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the imageId is not positive, iae expected.
     */
    public void testCtor_notPositiveImageId() {
        try {
            new BallColorImpl(ID, NAME, -3L);
            fail("The imageId should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the name is null, iae expected.
     */
    public void testCtor_nullName() {
        try {
            new BallColorImpl(ID, null, IMAGE_ID);
            fail("The name  should not be null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the name is empty, iae expected.
     */
    public void testCtor_emptyName() {
        try {
            new BallColorImpl(ID, " ", IMAGE_ID);
            fail("The name  should not be empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * simply verify the getId method.
     */
    public void testGetId() {
        assertEquals("The id to set is the not one get.", ID, this.ballColorImpl.getId());
    }

    /**
     * simply verify the getName method.
     */
    public void testGetName() {
        assertEquals("The name to set is the not one get.", NAME, this.ballColorImpl.getName());
    }

    /**
     * simply verify the getImageId method.
     */
    public void testGetImageId() {
        assertEquals("The imageId to set is the not one get.", IMAGE_ID, this.ballColorImpl.getImageId());
    }
}
