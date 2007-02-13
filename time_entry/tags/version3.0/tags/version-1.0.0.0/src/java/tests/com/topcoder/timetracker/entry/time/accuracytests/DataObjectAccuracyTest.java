/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.DataObject;

/**
 * <p>
 * Test the abstract class <code>DataObject</code>
 * </p>
 * @author fuyun
 * @version 1.0
 */
public class DataObjectAccuracyTest extends TestCase {

    /**
     * The data object instance used in tests.
     */
    private DataObject dataObject;

    /**
     * <p>
     * Sets up the test environment. The <code>MockDataObject</code> instance is created.
     * </p>
     */
    protected void setUp() {
        dataObject = new MockDataObject();
    }

    /**
     * <p>
     * Test method getPrimaryId(). The default value is 0.
     * </p>
     */
    public void testGetPrimaryId1() {
        assertEquals("the default value for primary id should be 0", 0, dataObject.getPrimaryId());
    }

    /**
     * Test the method for setPrimaryId(int id).
     */
    public void testSetPrimaryId() {
        for (int i = 0; i < 10; i++) {
            dataObject.setPrimaryId(i);
            assertEquals("the primary id is not set correctly", i, dataObject.getPrimaryId());
        }
    }

}
