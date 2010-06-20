/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import com.topcoder.clients.dao.ejb3.TestBase;

/**
 * <p>
 * Unit test for <code>{@link ProjectContestFee}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.2
 * @since 1.2
 */
public class ProjectContestFeeTest extends TestBase {
    /**
     * <p>
     * An instance of <code>ProjectContestFee</code> which is tested.
     * </p>
     */
    private ProjectContestFee target = null;

    /**
     * <p>
     * setUp() routine.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        target = new ProjectContestFee();
    }

    /**
     * <p>
     * Tests the <code>getId()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetId_default() throws Exception {
        assertEquals("the default value is 0.", 0L, target.getId());
    }

    /**
     * <p>
     * Tests the <code>setId(long)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetId_long() throws Exception {
        target.setId(10L);
        assertEquals("the id is not set", 10L, target.getId());
    }

    /**
     * <p>
     * Tests the <code>getProjectId()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetProjectId_default() throws Exception {
        assertEquals("the default value should be 0", 0L, target.getProjectId());
    }

    /**
     * <p>
     * Tests the <code>setProjectId(long)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetProjectId_long() throws Exception {
        target.setProjectId(10L);
        assertEquals("the project id is not set.", 10L, target.getProjectId());
    }

    /**
     * <p>
     * Tests the <code>getContestTypeId()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetContestType_default() throws Exception {
        assertEquals("the default value should be zero.", 0L, target.getContestTypeId());
    }

    /**
     * <p>
     * Tests the <code>setContestType(long)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetContestType_long() throws Exception {
        target.setContestType(10L);
        assertEquals("the contest type id is not set", 10L, target.getContestTypeId());
    }

    /**
     * <p>
     * Tests the <code>setStudio(boolean)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetStudio_boolean() throws Exception {
        target.setStudio(true);
        assertTrue("the value is not set.", target.isStudio());
    }

    /**
     * <p>
     * Tests the <code>isStudio()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodIsStudio_default() throws Exception {
        assertFalse("the default value should be false.", target.isStudio());
    }

    /**
     * <p>
     * Tests the <code>getContestFee()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetContestFee_default() throws Exception {
        assertEquals("the default value should be zero.", 0f, target.getContestFee(), 1e-5);
    }

    /**
     * <p>
     * Tests the <code>setContestFee(double)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetContestFee_double() throws Exception {
        target.setContestFee(10.0f);
        assertEquals("the contest fee is not set", 10.0f, target.getContestFee(), 1e-5);
    }

}
