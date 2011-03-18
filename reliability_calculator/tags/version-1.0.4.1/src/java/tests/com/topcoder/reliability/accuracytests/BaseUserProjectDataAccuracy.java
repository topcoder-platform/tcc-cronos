/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.reliability.impl.BaseUserProjectData;


/**
 * Accuracy test for <code>BaseUserProjectData</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseUserProjectDataAccuracy extends TestCase {
    /**
     * The <code>BaseUserProjectData</code> instance for testing.
     */
    private BaseUserProjectData instance = new MockBaseUserProjectData();

    /**
     * Accuracy test for <code>getProjectId()</code>.
     */
    public void testGetProjectId() {
        assertEquals("The default value is 0", 0, instance.getProjectId());

        instance.setProjectId(1);
        assertEquals("The result should match.", 1, instance.getProjectId());

        instance.setProjectId(100);
        assertEquals("The result should match.", 100, instance.getProjectId());

        instance.setProjectId(-1);
        assertEquals("The result should match.", -1, instance.getProjectId());
    }

    /**
     * Accuracy test for <code>setProjectId(long)</code>.
     */
    public void testSetProjectId() {
        instance.setProjectId(1);
        assertEquals("The result should match.", 1, instance.getProjectId());

        instance.setProjectId(100);
        assertEquals("The result should match.", 100, instance.getProjectId());

        instance.setProjectId(-1);
        assertEquals("The result should match.", -1, instance.getProjectId());
    }

    /**
     * Accuracy test for <code>getUserId()</code>.
     */
    public void testGetUserId() {
        assertEquals("The default value is 0", 0, instance.getUserId());

        instance.setUserId(1);
        assertEquals("The result should match.", 1, instance.getUserId());

        instance.setUserId(100);
        assertEquals("The result should match.", 100, instance.getUserId());

        instance.setUserId(-1);
        assertEquals("The result should match.", -1, instance.getUserId());
    }

    /**
     * Accuracy test for <code>setUserId(long)</code>.
     */
    public void testSetUserId() {
        instance.setUserId(1);
        assertEquals("The result should match.", 1, instance.getUserId());

        instance.setUserId(100);
        assertEquals("The result should match.", 100, instance.getUserId());

        instance.setUserId(-1);
        assertEquals("The result should match.", -1, instance.getUserId());
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
}
