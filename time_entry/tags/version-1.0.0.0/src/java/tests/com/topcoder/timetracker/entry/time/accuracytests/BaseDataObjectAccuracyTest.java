/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.BaseDataObject;

/**
 * <p>
 * Test the abstract class <code>BaseDataObject</code>.
 * </p>
 * @author fuyun
 * @version 1.0
 */
public class BaseDataObjectAccuracyTest extends TestCase {

    /**
     * <p>
     * the <code>BaseDataObject</code> instance used in test.
     */
    private BaseDataObject baseDataObject;

    /**
     * the description string used for test.
     */
    private final String desc = "topcoder";

    /**
     * the string represent the create user.
     */
    private final String createUser = "create user";

    /**
     * the string represent the modify user.
     */
    private final String modifyUser = "modify user";

    /**
     * <p>
     * Sets up the test environment. The <code>MockBaseDataObject</code> instance is created.
     * </p>
     * @throws Exception if any problem occurs.
     */
    protected void setUp() throws Exception {
        baseDataObject = new MockBaseDataObject();
    }

    /**
     * <p>
     * Tests the constructor BasebaseDataObject().
     * </p>
     */
    public void testBasebaseDataObject() {
        assertNotNull("The constructor does not work well", this.baseDataObject);
    }

    /**
     * <p>
     * Tests the getDescription() method.
     * </p>
     */
    public void testGetDescription() {
        assertNull("description is not properly got", baseDataObject.getDescription());
    }

    /**
     * <p>
     * Tests the setDescription(String) method.
     * </p>
     */
    public void testSetDescription1() {
        baseDataObject.setDescription(desc);
        assertEquals("description is not properly set", desc, baseDataObject.getDescription());
    }

    /**
     * <p>
     * Tests the setDescription(String) method. the <code>null</code> argument is acceptable
     * </p>
     */
    public void testSetDescription2() {
        baseDataObject.setDescription(null);
        assertEquals("description is not properly set", null, baseDataObject.getDescription());
    }

    /**
     * <p>
     * Tests the getCreationUser() method.
     * </p>
     */
    public void testGetCreationUser() {
        assertNull("creationUser is not properly got", baseDataObject.getCreationUser());
    }

    /**
     * <p>
     * Tests the setCreationUser(String) method.
     * </p>
     */
    public void testSetCreationUser1() {
        baseDataObject.setCreationUser(createUser);
        assertEquals("creationUser is not properly set", createUser, baseDataObject
                .getCreationUser());
    }

    /**
     * <p>
     * Tests the setCreationUser(String) method. the <code>null</code> is acceptable.
     * </p>
     */
    public void testSetCreationUser2() {
        baseDataObject.setCreationUser(null);
        assertEquals("creationUser is not properly set", null, baseDataObject.getCreationUser());
    }

    /**
     * <p>
     * Tests the getCreationDate() method.
     * </p>
     */
    public void testGetCreationDate() {
        assertNull("creationDate is not properly got, the default value is null", baseDataObject
                .getCreationDate());
    }

    /**
     * <p>
     * Tests the setCreationDate(Date) method.
     * </p>
     */
    public void testSetCreationDate1() {
        Date date = new Date();
        baseDataObject.setCreationDate(date);
        assertEquals("creationDate is not properly got", date, baseDataObject.getCreationDate());
    }

    /**
     * <p>
     * Tests the setCreationDate(Date) method. the <code>null</code> argument is acceptable.
     * </p>
     */
    public void testSetCreationDate2() {
        Date date = null;
        baseDataObject.setCreationDate(date);
        assertEquals("creationDate is not properly got", date, baseDataObject.getCreationDate());
    }

    /**
     * <p>
     * Tests the getModificationUser() method.
     * </p>
     */
    public void testGetModificationUser() {
        assertNull("modificationUser is not properly got, the default value is null",
                baseDataObject.getModificationUser());
    }

    /**
     * <p>
     * Tests the setModificationUser(String) method.
     * </p>
     */
    public void testSetModificationUser1() {
        baseDataObject.setModificationUser(modifyUser);
        assertEquals("modificationUser is not properly set", modifyUser, baseDataObject
                .getModificationUser());
    }

    /**
     * <p>
     * Tests the setModificationUser(String) method. the <code>null</code> argument is acceptable.
     * </p>
     */
    public void testSetModificationUser2() {
        baseDataObject.setModificationUser(null);
        assertEquals("modificationUser is not properly set", null, baseDataObject
                .getModificationUser());
    }

    /**
     * <p>
     * Tests the getModificationDate() method.
     * </p>
     */
    public void testGetModificationDate() {
        assertNull("modificationDate is not properly got, the default value is null",
                baseDataObject.getModificationDate());
    }

    /**
     * <p>
     * Tests the setModificationDate(Date) method.
     * </p>
     */
    public void testSetModificationDate1() {
        Date date = new Date();
        baseDataObject.setModificationDate(date);
        assertEquals("modificationDate is not properly set", date, baseDataObject
                .getModificationDate());
    }

    /**
     * <p>
     * Tests the setModificationDate(Date) method. the <code>null</code> argument is acceptable.
     * </p>
     */
    public void testSetModificationDate2() {
        Date date = null;
        baseDataObject.setModificationDate(date);
        assertEquals("modificationDate is not properly set", date, baseDataObject
                .getModificationDate());
    }

}
