/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import java.util.Date;

import junit.framework.TestCase;

/**
 * <p>
 * Test class: <code>ProjectContestFeeAudit</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class ProjectContestFeeAuditTest extends TestCase {
    /**
     * <p>
     * An instance of <code>ProjectContestFeeAudit</code> which is tested.
     * </p>
     */
    private ProjectContestFeeAudit instance = null;

    /**
     * <p>
     * setUp() routine.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        instance = new ProjectContestFee();
    }

    /**
     * <p>
     * Tests <code>getCreateUsername()</code> method.
     * </p>
     * <p>
     * It should return null, by default.
     * </p>
     */
    public void testGetCreateUsername_default() {
        assertNull("Should be default to null", instance.getCreateUsername());
    }

    /**
     * <p>
     * Tests <code>setCreateUsername(String)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetCreateUsername_null() {
        instance.setCreateUsername(null);

        assertNull("Incorrect return value.", instance.getCreateUsername());
    }

    /**
     * <p>
     * Tests <code>setCreateUsername(String)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetCreateUsername_empty() {
        String value = "";
        instance.setCreateUsername(value);

        assertEquals("Incorrect return value.", value, instance.getCreateUsername());
    }

    /**
     * <p>
     * Tests <code>setCreateUsername(String)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetCreateUsername_trimmedEmpty() {
        String value = "  ";
        instance.setCreateUsername(value);

        assertEquals("Incorrect return value.", value, instance.getCreateUsername());
    }

    /**
     * <p>
     * Tests <code>setCreateUsername(String)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetCreateUsername() {
        String value = "Unit";
        instance.setCreateUsername(value);

        assertEquals("Incorrect return value.", value, instance.getCreateUsername());
    }

    /**
     * <p>
     * Tests <code>getCreateDate()</code> method.
     * </p>
     * <p>
     * It should return null, by default.
     * </p>
     */
    public void testGetCreateDate_default() {
        assertNull("Should be default to null", instance.getCreateDate());
    }

    /**
     * <p>
     * Tests <code>setCreateDate(Date)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetCreateDate_null() {
        instance.setCreateDate(null);

        assertNull("Incorrect return value.", instance.getCreateDate());
    }

    /**
     * <p>
     * Tests <code>setCreateDate(Date)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetCreateDate() {
        Date value = new Date();
        instance.setCreateDate(value);

        assertEquals("Incorrect return value.", value, instance.getCreateDate());
    }

    /**
     * <p>
     * Tests <code>isDeleted()</code> method.
     * </p>
     * <p>
     * It should return null, by default.
     * </p>
     */
    public void testIsDeleted_default() {
        assertNull("Should be default to null", instance.isDeleted());
    }

    /**
     * <p>
     * Tests <code>setDeleted()</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetDeleted_null() {
        instance.setDeleted(null);

        assertNull("Incorrect return value.", instance.isDeleted());
    }

    /**
     * <p>
     * Tests <code>setDeleted()</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetDeleted() {
        Boolean value = true;
        instance.setDeleted(value);

        assertEquals("Incorrect return value.", value, instance.isDeleted());
    }

    /**
     * <p>
     * Tests <code>getModifyDate()</code> method.
     * </p>
     * <p>
     * It should return null, by default.
     * </p>
     */
    public void testGetModifyDate_default() {
        assertNull("Should be default to null", instance.getModifyDate());
    }

    /**
     * <p>
     * Tests <code>setModifyDate(Date)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetModifyDate_null() {
        instance.setModifyDate(null);

        assertNull("Incorrect return value.", instance.getModifyDate());
    }

    /**
     * <p>
     * Tests <code>setModifyDate(Date)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetModifyDate() {
        Date value = new Date();
        instance.setModifyDate(value);

        assertEquals("Incorrect return value.", value, instance.getModifyDate());
    }

    /**
     * <p>
     * Tests <code>getModifyUsername()</code> method.
     * </p>
     * <p>
     * It should return null, by default.
     * </p>
     */
    public void testGetModifyUsername_default() {
        assertNull("Should be default to null", instance.getModifyUsername());
    }

    /**
     * <p>
     * Tests <code>setModifyUsername(String)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetModifyUsername_null() {
        instance.setModifyUsername(null);

        assertNull("Incorrect return value.", instance.getModifyUsername());
    }

    /**
     * <p>
     * Tests <code>setModifyUsername(String)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetModifyUsername_empty() {
        String value = "";
        instance.setModifyUsername(value);

        assertEquals("Incorrect return value.", value, instance.getModifyUsername());
    }

    /**
     * <p>
     * Tests <code>setModifyUsername(String)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetModifyUsername_trimmedEmpty() {
        String value = "  ";
        instance.setModifyUsername(value);

        assertEquals("Incorrect return value.", value, instance.getModifyUsername());
    }

    /**
     * <p>
     * Tests <code>setModifyUsername(String)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetModifyUsername() {
        String value = "Unit";
        instance.setModifyUsername(value);

        assertEquals("Incorrect return value.", value, instance.getModifyUsername());
    }

    /**
     * <p>
     * Tests <code>getName()</code> method.
     * </p>
     * <p>
     * It should return null, by default.
     * </p>
     */
    public void testGetName_default() {
        assertNull("Should be default to null", instance.getName());
    }

    /**
     * <p>
     * Tests <code>setName(String)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetName_null() {
        instance.setName(null);

        assertNull("Incorrect return value.", instance.getName());
    }

    /**
     * <p>
     * Tests <code>setName(String)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetName_empty() {
        String value = "";
        instance.setName(value);

        assertEquals("Incorrect return value.", value, instance.getName());
    }

    /**
     * <p>
     * Tests <code>setName(String)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetName_trimmedEmpty() {
        String value = "  ";
        instance.setName(value);

        assertEquals("Incorrect return value.", value, instance.getName());
    }

    /**
     * <p>
     * Tests <code>setName(String)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetName() {
        String value = "Unit";
        instance.setName(value);

        assertEquals("Incorrect return value.", value, instance.getName());
    }

}
