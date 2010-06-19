/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import junit.framework.TestCase;

/**
 * <p>
 * Test class: <code>ProjectContestFee</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class ProjectContestFeeTest extends TestCase {
    /**
     * <p>
     * An instance of <code>ProjectContestFee</code> which is tested.
     * </p>
     */
    private ProjectContestFee instance = null;

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
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ProjectContestFee</code> subclasses <code>AuditableEntity</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ProjectContestFee does not subclasses AuditableEntity.",
            instance instanceof ProjectContestFeeAudit);
    }

    /**
     * <p>
     * Tests the <code>ProjectContestFee()</code> for proper behavior. Verifies that Verifies that
     * instance should be created.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConstructor() throws Exception {
        assertNotNull("Instance should be created.", instance);
    }

    /**
     * <p>
     * Tests <code>getContestFee()</code> method.
     * </p>
     * <p>
     * It should return zero, by default.
     * </p>
     */
    public void testGetContestFee_default() {
        assertEquals("Should be default to zero", 0.0, instance.getContestFee());
    }

    /**
     * <p>
     * Tests <code>setContestFee(double)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetContestFee() {
        double value = 1.1;
        instance.setContestFee(value);

        assertEquals("Incorrect return value.", value, instance.getContestFee());
    }

    /**
     * <p>
     * Tests <code>getContestTypeId()</code> method.
     * </p>
     * <p>
     * It should return zero, by default.
     * </p>
     */
    public void testGetContestTypeId_default() {
        assertEquals("Should be default to zero", 0L, instance.getContestTypeId());
    }

    /**
     * <p>
     * Tests <code>setContestTypeId(long)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetContestType() {
        long value = 1;
        instance.setContestType(value);

        assertEquals("Incorrect return value.", value, instance.getContestTypeId());
    }

    /**
     * <p>
     * Tests <code>getId()</code> method.
     * </p>
     * <p>
     * It should return zero, by default.
     * </p>
     */
    public void testGetId_default() {
        assertEquals("Should be default to zero", 0L, instance.getId());
    }

    /**
     * <p>
     * Tests <code>setId(long)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetId() {
        long value = 1;
        instance.setId(value);

        assertEquals("Incorrect return value.", value, instance.getId());
    }

    /**
     * <p>
     * Tests <code>getProjectId()</code> method.
     * </p>
     * <p>
     * It should return zero, by default.
     * </p>
     */
    public void testGetProjectId_default() {
        assertEquals("Should be default to zero", 0L, instance.getProjectId());
    }

    /**
     * <p>
     * Tests <code>setProjectId(long)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetProjectId() {
        long value = 2;
        instance.setProjectId(value);

        assertEquals("Incorrect return value.", value, instance.getProjectId());
    }

    /**
     * <p>
     * Tests <code>isStudio()</code> method.
     * </p>
     * <p>
     * It should return false, by default.
     * </p>
     */
    public void testIsStudio_default() {
        assertEquals("Should be default to false", false, instance.isStudio());
    }

    /**
     * <p>
     * Tests <code>setStudio(boolean)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetStudio() {
        instance.setStudio(true);

        assertEquals("Incorrect return value.", true, instance.isStudio());
    }
}
