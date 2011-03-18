/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.reliability.impl.BaseUserProjectData;
import com.topcoder.reliability.impl.UserProjectReliabilityData;


/**
 * Accuracy test for <code>UserProjectReliabilityData</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserProjectReliabilityDataAccuracy extends TestCase {
    /**
     * The <code>UserProjectReliabilityData</code> instance for testing.
     */
    private UserProjectReliabilityData instance = new UserProjectReliabilityData();

    /**
     * Accuracy test for constructor.
     */
    public void testCtor() {
        assertNotNull("The result should match.", instance);
        assertTrue("The result should match.",
            instance instanceof BaseUserProjectData);
    }

    /**
     * Accuracy test for <code>getReliabilityBeforeResolution()</code>.
     */
    public void testGetReliabilityBeforeResolution() {
        assertNull("The default value is null",
            instance.getReliabilityBeforeResolution());

        instance.setReliabilityBeforeResolution(1.0);
        assertEquals("The result should match.", 1.0,
            instance.getReliabilityBeforeResolution());

        instance.setReliabilityBeforeResolution(100.0);
        assertEquals("The result should match.", 100.0,
            instance.getReliabilityBeforeResolution());

        instance.setReliabilityBeforeResolution(-1.0);
        assertEquals("The result should match.", -1.0,
            instance.getReliabilityBeforeResolution());
    }

    /**
     * Accuracy test for <code>setReliabilityBeforeResolution(long)</code>.
     */
    public void testSetReliabilityBeforeResolution() {
        instance.setReliabilityBeforeResolution(1.0);
        assertEquals("The result should match.", 1.0,
            instance.getReliabilityBeforeResolution());

        instance.setReliabilityBeforeResolution(100.0);
        assertEquals("The result should match.", 100.0,
            instance.getReliabilityBeforeResolution());

        instance.setReliabilityBeforeResolution(-1.0);
        assertEquals("The result should match.", -1.0,
            instance.getReliabilityBeforeResolution());

        instance.setReliabilityBeforeResolution(null);
        assertNull("set to null", instance.getReliabilityBeforeResolution());
    }

    /**
     * Accuracy test for <code>getReliabilityAfterResolution()</code>.
     */
    public void testGetReliabilityAfterResolution() {
        assertEquals("The default value is 0", 0.0,
            instance.getReliabilityAfterResolution());

        instance.setReliabilityAfterResolution(1.0);
        assertEquals("The result should match.", 1.0,
            instance.getReliabilityAfterResolution());

        instance.setReliabilityAfterResolution(100.0);
        assertEquals("The result should match.", 100.0,
            instance.getReliabilityAfterResolution());

        instance.setReliabilityAfterResolution(-1.0);
        assertEquals("The result should match.", -1.0,
            instance.getReliabilityAfterResolution());
    }

    /**
     * Accuracy test for <code>getReliabilityAfterResolution(long)</code>.
     */
    public void testSetReliabilityAfterResolution() {
        instance.setReliabilityAfterResolution(1.0);
        assertEquals("The result should match.", 1.0,
            instance.getReliabilityAfterResolution());

        instance.setReliabilityAfterResolution(100.0);
        assertEquals("The result should match.", 100.0,
            instance.getReliabilityAfterResolution());

        instance.setReliabilityAfterResolution(-1.0);
        assertEquals("The result should match.", -1.0,
            instance.getReliabilityAfterResolution());
    }

    /**
     * Accuracy test for <code>getReliabilityOnRegistration()</code>.
     */
    public void testGetReliabilityOnRegistration() {
        assertNull("The default value is 0",
            instance.getReliabilityOnRegistration());

        instance.setReliabilityOnRegistration(1.0);
        assertEquals("The result should match.", 1.0,
            instance.getReliabilityOnRegistration());

        instance.setReliabilityOnRegistration(100.0);
        assertEquals("The result should match.", 100.0,
            instance.getReliabilityOnRegistration());

        instance.setReliabilityOnRegistration(-1.0);
        assertEquals("The result should match.", -1.0,
            instance.getReliabilityOnRegistration());
    }

    /**
     * Accuracy test for <code>getReliabilityOnRegistration(long)</code>.
     */
    public void testSetReliabilityOnRegistration() {
        instance.setReliabilityOnRegistration(1.0);
        assertEquals("The result should match.", 1.0,
            instance.getReliabilityOnRegistration());

        instance.setReliabilityOnRegistration(100.0);
        assertEquals("The result should match.", 100.0,
            instance.getReliabilityOnRegistration());

        instance.setReliabilityOnRegistration(-1.0);
        assertEquals("The result should match.", -1.0,
            instance.getReliabilityOnRegistration());

        instance.setReliabilityOnRegistration(null);
        assertNull("set to null", instance.getReliabilityOnRegistration());
    }

    /**
     * Accuracy test for <code>getResolutionDate()</code>.
     */
    public void testGetResolutionDate() {
        assertNull("The default value is null", instance.getResolutionDate());

        Date value = new Date();
        instance.setResolutionDate(value);
        assertEquals("The result should match.", value,
            instance.getResolutionDate());

        instance.setResolutionDate(null);
        assertNull("The result should match.", instance.getResolutionDate());
    }

    /**
     * Accuracy test for <code>setResolutionDate(Date)</code>.
     */
    public void testSetResolutionDate() {
        Date value = new Date();
        instance.setResolutionDate(value);
        assertEquals("The result should match.", value,
            instance.getResolutionDate());

        instance.setResolutionDate(null);
        assertNull("The result should match.", instance.getResolutionDate());
    }

    /**
     * Accuracy test for <code>isReliable()</code>.
     */
    public void testIsReliable() {
        assertEquals("The default is fale.", false, instance.isReliable());

        instance.setReliable(true);
        assertEquals("Set to true.", true, instance.isReliable());

        instance.setReliable(false);
        assertEquals("Set to false.", false, instance.isReliable());
    }

    /**
     * Accuracy test for <code>setReliable()</code>.
     */
    public void testSetReliable() {
        instance.setReliable(true);
        assertEquals("Set to true.", true, instance.isReliable());

        instance.setReliable(false);
        assertEquals("Set to false.", false, instance.isReliable());
    }
}
