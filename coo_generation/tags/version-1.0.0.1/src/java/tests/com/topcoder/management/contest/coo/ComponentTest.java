/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test case of {@link Component}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentTest extends TestCase {
    /**
     * <p>
     * Create an instance to test against.
     * </p>
     */
    private Component instance;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        instance = new Component();
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
     * Test method for {@link Component#Component()}. It verifies the new
     * instance is created.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    public void testComponent() throws Exception {
        assertNotNull("Unable to instantiate Component", instance);
    }

    /**
     * <p>
     * Test method for {@link Component#getName()}.It verifies the assigned
     * value is correct.
     * </p>
     */
    public void testGetName() {
        instance.setName("hello");
        assertEquals("fail to set/get the field.", "hello", instance.getName());
    }

    /**
     * <p>
     * Test method for {@link Component#setName(String)}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testSetName() {
        instance.setName("good");
        assertEquals("fail to set/get the field.", "good", instance.getName());
    }

    /**
     * <p>
     * Test method for {@link Component#getVersion()}.It verifies the assigned
     * value is correct.
     * </p>
     */
    public void testGetVersion() {
        instance.setVersion("topcoder");
        assertEquals("fail to set/get the field.", "topcoder", instance.getVersion());
    }

    /**
     * <p>
     * Test method for {@link Component#setVersion(String)}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testSetVersion() {
        instance.setVersion("abcde");
        assertEquals("fail to set/get the field.", "abcde", instance.getVersion());
    }

    /**
     * <p>
     * Test method for {@link Component#getDescription()}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testGetDescription() {
        instance.setDescription("tc");
        assertEquals("fail to set/get the field.", "tc", instance.getDescription());
    }

    /**
     * <p>
     * Test method for {@link Component#setDescription(String)}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testSetDescription() {
        instance.setDescription("xxxxx");
        assertEquals("fail to set/get the field.", "xxxxx", instance.getDescription());
    }

    /**
     * <p>
     * Test method for {@link Component#getUrl()}.It verifies the assigned
     * value is correct.
     * </p>
     */
    public void testGetUrl() {
        instance.setUrl("hello");
        assertEquals("fail to set/get the field.", "hello", instance.getUrl());
    }

    /**
     * <p>
     * Test method for {@link Component#setUrl(String)}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testSetUrl() {
        instance.setUrl("good");
        assertEquals("fail to set/get the field.", "good", instance.getUrl());
    }

    /**
     * <p>
     * Test method for {@link Component#getLicense()}.It verifies the assigned
     * value is correct.
     * </p>
     */
    public void testGetLicense() {
        instance.setLicense("topcoder");
        assertEquals("fail to set/get the field.", "topcoder", instance.getLicense());
    }

    /**
     * <p>
     * Test method for {@link Component#setLicense(String)}.It verifies the
     * assigned value is correct.
     * </p>
     */
    public void testSetLicense() {
        instance.setLicense("abcde");
        assertEquals("fail to set/get the field.", "abcde", instance.getLicense());
    }
}
