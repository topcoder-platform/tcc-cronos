/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test case of {@link ComponentDependency}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentDependencyTest extends TestCase {
    /**
     * <p>
     * Create an instance to test against.
     * </p>
     */
    private ComponentDependency instance;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        instance = new ComponentDependency();
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
     * Test method for {@link ComponentDependency#ComponentDependency()}. It
     * verifies the new instance is created.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testComponentDependency() throws Exception {
        assertNotNull("Unable to instantiate ComponentDependency", instance);
    }

    /**
     * <p>
     * Test method for {@link ComponentDependency#getComponent()}.It verifies
     * the assigned value is correct.
     * </p>
     */
    public void testGetComponent() {
        Component test = new Component();
        instance.setComponent(test);
        assertEquals("fail to set/get the field.", test, instance.getComponent());
    }

    /**
     * <p>
     * Test method for {@link ComponentDependency#setComponent(Component)}.It
     * verifies the assigned value is correct.
     * </p>
     * <p>
     * argument is null,IAE throw.
     * </p>
     */
    public void testSetComponent() {
        try {
            instance.setComponent(null);
            fail("IAE should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * Test method for {@link ComponentDependency#getCategory()}.It verifies
     * the assigned value is correct.
     * </p>
     */
    public void testGetCategory() {
        DependencyCategory test = DependencyCategory.TEST;
        instance.setCategory(test);
        assertEquals("fail to set/get the field.", test, instance.getCategory());
    }

    /**
     * <p>
     * Test method for
     * {@link ComponentDependency#setCategory(DependencyCategory)}.It verifies
     * the assigned value is correct.
     * </p>
     * <p>
     * argument is null,IAE throw.
     * </p>
     */
    public void testSetCategory() {
        try {
            instance.setCategory(null);
            fail("IAE should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * <p>
     * Test method for {@link ComponentDependency#getType()}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testGetType() {
        DependencyType test = DependencyType.EXTERNAL;
        instance.setType(test);
        assertEquals("fail to set/get the field.", test, instance.getType());
    }

    /**
     * <p>
     * Test method for {@link ComponentDependency#setType(DependencyType)}.It
     * verifies the assigned value is correct.
     * </p>
     * <p>
     * argument is null,IAE throw.
     * </p>
     */
    public void testSetType() {
        try {
            instance.setType(null);
            fail("IAE should throw.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
}
