/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test case of {@link COOReport}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class COOReportTest extends TestCase {
    /**
     * <p>
     * Create an instance to test against.
     * </p>
     */
    private COOReport instance;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        instance = new COOReport();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * <p>
     * Test method for {@link COOReport#COOReport()}. It verifies the new
     * instance is created.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testCOOReport() throws Exception {
        assertNotNull("Unable to instantiate COOReport", instance);
    }

    /**
     * <p>
     * Test method for {@link COOReport#getContestData()}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testGetContestData() {
        ContestData test = new ContestData();
        instance.setContestData(test);
        assertEquals("fail to set/get the field.", test, instance.getContestData());
    }

    /**
     * <p>
     * Test method for {@link COOReport#getComponentDependencies()}.It verifies
     * the assigned value is correct.
     * </p>
     */
    public void testGetComponentDependencies() {
        List<ComponentDependency> test = new ArrayList<ComponentDependency>();
        instance.setComponentDependencies(test);
        assertEquals("fail to set/get the field.", test, instance.getComponentDependencies());
    }

    /**
     * <p>
     * Test method for {@link COOReport#setContestData(ContestData)}.It
     * verifies the assigned value is correct.
     * </p>
     * <p>
     * argument is null,IAE throw.
     * </p>
     */
    public void testSetContestData() {
        try {
            instance.setContestData(null);
            fail("IllegalArgumentException should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * Test method for
     * {@link COOReport#setComponentDependencies(List)}.It
     * verifies the assigned value is correct.
     * </p>
     * <p>
     * argument is null,IAE throw.
     * </p>
     */
    public void testSetComponentDependencies() {
        try {
            instance.setComponentDependencies(null);
            fail("IllegalArgumentException should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * Test method for {@link COOReport#getProjectId()}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testGetProjectId() {
        instance.setProjectId(123);
        assertEquals("fail to set/get the field.", 123, instance.getProjectId());
    }

    /**
     * <p>
     * Test method for {@link COOReport#setProjectId(long)}.It verifies the
     * assigned value is correct.
     * </p>
     * <p>
     * id is not positive.IAE throw.
     * </p>
     */
    public void testSetProjectId() {
        try {
            instance.setProjectId(-3);
            fail("IllegalArgumentException should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
}
