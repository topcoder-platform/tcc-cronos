/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit test cases for DataObject.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the get and set process.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DataObjectUnitTest extends TestCase {
    /**
     * <p>
     * The MockDataObject instance for testing.
     * </p>
     */
    private DataObject dataObject = null;

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(DataObjectUnitTest.class);
    }

    /**
     * <p>
     * Set up the environment for testing.
     * </p>
     */
    protected void setUp() {
        this.dataObject = new MockDataObject();
    }

    /**
     * <p>
     * Tests the constructor - DataObject().
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDataObject() throws Exception {
        assertNotNull("The constructor does not work well", this.dataObject);
    }

    /**
     * <p>
     * Tests the getPrimaryId() method.
     * </p>
     */
    public void testGetPrimaryId() {
        assertEquals("primaryId is not properly got", 0, dataObject.getPrimaryId());
    }

    /**
     * <p>
     * Tests the setPrimaryId(Int id) method.
     * </p>
     */
    public void testSetPrimaryId() {
        int primaryId = 5;
        dataObject.setPrimaryId(primaryId);
        assertEquals("primaryId is not properly set", primaryId, dataObject.getPrimaryId());
    }
}


/**
 * <p>
 * A mock MockDataObject that extends the DataObject. This class is used for testing only.
 * </p>
 */
class MockDataObject extends DataObject {
}
