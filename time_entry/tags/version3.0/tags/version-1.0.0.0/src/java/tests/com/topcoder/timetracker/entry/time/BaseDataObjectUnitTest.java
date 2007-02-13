/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;


/**
 * <p>
 * Unit test cases for BaseDataObject.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the get and set process.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseDataObjectUnitTest extends TestCase {
    /**
     * <p>
     * The Date instance for testing.
     * </p>
     */
    private Date date;

    /**
     * <p>
     * The MockBaseDataObject instance for testing.
     * </p>
     */
    private MockBaseDataObject dataObject = null;

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(BaseDataObjectUnitTest.class);
    }

    /**
     * <p>
     * Set up the environment for testing.
     * </p>
     */
    protected void setUp() {
        this.dataObject = new MockBaseDataObject();
        this.date = new Date();
    }

    /**
     * <p>
     * Tests the constructor - BaseDataObject().
     * </p>
     */
    public void testBaseDataObject() {
        assertNotNull("The constructor does not work well", this.dataObject);
    }

    /**
     * <p>
     * Tests the getDescription() method.
     * </p>
     */
    public void testGetDescription() {
        assertNull("description is not properly got", dataObject.getDescription());
    }

    /**
     * <p>
     * Tests the setDescription(String) method.
     * </p>
     */
    public void testSetDescription() {
        dataObject.setDescription("oof");
        assertEquals("description is not properly set", "oof", dataObject.getDescription());
    }

    /**
     * <p>
     * Tests the getCreationUser() method.
     * </p>
     */
    public void testGetCreationUser() {
        assertNull("creationUser is not properly got", dataObject.getCreationUser());
    }

    /**
     * <p>
     * Tests the setCreationUser(String) method.
     * </p>
     */
    public void testSetCreationUser() {
        dataObject.setCreationUser("oof");
        assertEquals("creationUser is not properly set", "oof", dataObject.getCreationUser());
    }

    /**
     * <p>
     * Tests the getCreationDate() method.
     * </p>
     */
    public void testGetCreationDate1() {
        dataObject.setCreationDate(date);
        assertEquals("creationDate is not properly got", date, dataObject.getCreationDate());
    }

    /**
     * <p>
     * Tests the getCreationDate() method.
     * Confirm the return value is a shallow copy.
     * </p>
     */
    public void testGetCreationDate2() {
        dataObject.setCreationDate(date);
        Date ret = dataObject.getCreationDate();
        ret.setTime(1000);
        assertEquals("creationDate is not properly got", date, dataObject.getCreationDate());
    }

    /**
     * <p>
     * Tests the setCreationDate(Date) method.
     * </p>
     */
    public void testSetCreationDate1() {
        dataObject.setCreationDate(date);
        assertEquals("creationDate is not properly set", date, dataObject.getCreationDate());
    }

    /**
     * <p>
     * Tests the setCreationDate(Date) method.
     * Confirm the set value is a shallow copy
     * </p>
     */
    public void testSetCreationDate2() {
        dataObject.setCreationDate(date);
        Date ret = dataObject.getCreationDate();
        date.setTime(1000);
        assertEquals("creationDate is not properly set", ret, dataObject.getCreationDate());
    }

    /**
     * <p>
     * Tests the getModificationUser() method.
     * </p>
     */
    public void testGetModificationUser() {
        assertNull("modificationUser is not properly got", dataObject.getModificationUser());
    }

    /**
     * <p>
     * Tests the setModificationUser(String) method.
     * </p>
     */
    public void testSetModificationUser() {
        dataObject.setModificationUser("oof");
        assertEquals("modificationUser is not properly set", "oof", dataObject.getModificationUser());
    }

    /**
     * <p>
     * Tests the getModificationDate() method.
     * </p>
     */
    public void testGetModificationDate1() {
        dataObject.setModificationDate(date);
        assertEquals("modificationDate is not properly got", date, dataObject.getModificationDate());
    }

    /**
     * <p>
     * Tests the getModificationDate() method.
     * Confirm the return value is a shallow copy.
     * </p>
     */
    public void testGetModificationDate2() {
        dataObject.setModificationDate(date);
        Date ret = dataObject.getModificationDate();
        ret.setTime(1000);
        assertEquals("modificationDate is not properly got", date, dataObject.getModificationDate());
    }

    /**
     * <p>
     * Tests the setModificationDate(Date) method.
     * </p>
     */
    public void testSetModificationDate1() {
        dataObject.setModificationDate(date);
        assertEquals("modificationDate is not properly set", date, dataObject.getModificationDate());
    }

    /**
     * <p>
     * Tests the setModificationDate(Date) method.
     * Confirm the set value is a shallow copy
     * </p>
     */
    public void testSetModificationDate2() {
        dataObject.setModificationDate(date);
        Date ret = dataObject.getModificationDate();
        date.setTime(1000);
        assertEquals("modificationDate is not properly set", ret, dataObject.getModificationDate());
    }
}


/**
 * <p>
 * A mock MockBaseDataObject that extends the BaseDataObject. This class is used for testing only.
 * </p>
 */
class MockBaseDataObject extends BaseDataObject {
}
