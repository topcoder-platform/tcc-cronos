/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.persistence;

import com.topcoder.timetracker.audit.UnitTestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>DefaultValuesContainer</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DefaultValuesContainerUnitTest extends TestCase {
    /** Represents the <code>DefaultValuesContainer</code> instance used for testing. */
    private DefaultValuesContainer container = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();
        container = new DefaultValuesContainer(DefaultValuesContainer.class.getName());
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        UnitTestHelper.clearConfig();
    }

    /**
     * Test the constructor <code>DefaultValuesContainer(String)</code> when the given namespace is null,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDefaultValuesContainer_NullNamespace() throws Exception {
        try {
            new DefaultValuesContainer(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the constructor <code>DefaultValuesContainer(String)</code> when the given namespace is an empty string,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDefaultValuesContainer_EmptyNamespace() throws Exception {
        try {
            new DefaultValuesContainer(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the constructor <code>DefaultValuesContainer(String)</code> for accuracy when all the property values
     * exist.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDefaultValuesContainer_AllExistAccuracy() throws Exception {
        assertEquals("The id value should be got properly.", 100, container.getId());
        assertEquals("The newValue value should be got properly.", "newValue", container.getNewValue());
        assertEquals("The newValue value should be got properly.", "oldValue", container.getOldValue());
        assertEquals("The newValue value should be got properly.", "columnName", container.getColumnName());
    }

    /**
     * Test the constructor <code>DefaultValuesContainer(String)</code> for accuracy when all the property values do
     * not exist, the default values should be used.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDefaultValuesContainer_AllNotExistAccuracy() throws Exception {
        container = new DefaultValuesContainer(DefaultValuesContainer.class.getName() + ".Empty");

        assertEquals("The id value should be got properly.", -1, container.getId());
        assertEquals("The newValue value should be got properly.", null, container.getNewValue());
        assertEquals("The newValue value should be got properly.", null, container.getOldValue());
        assertEquals("The newValue value should be got properly.", null, container.getColumnName());
    }

    /**
     * Test the constructor <code>DefaultValuesContainer(String)</code> for accuracy when the namespace does not exist,
     * the default values should be used.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDefaultValuesContainer_NamespaceNotExistAccuracy() throws Exception {
        container = new DefaultValuesContainer("NotExist");

        assertEquals("The id value should be got properly.", -1, container.getId());
        assertEquals("The newValue value should be got properly.", null, container.getNewValue());
        assertEquals("The newValue value should be got properly.", null, container.getOldValue());
        assertEquals("The newValue value should be got properly.", null, container.getColumnName());
    }

    /**
     * Test the constructor <code>DefaultValuesContainer(String)</code> for accuracy when the id property is invalid,
     * the default values should be used.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDefaultValuesContainer_IdInvalidAccuracy() throws Exception {
        container = new DefaultValuesContainer(DefaultValuesContainer.class.getName() + ".InvalidId");

        assertEquals("The id value should be got properly.", -1, container.getId());
        assertEquals("The newValue value should be got properly.", null, container.getNewValue());
        assertEquals("The newValue value should be got properly.", null, container.getOldValue());
        assertEquals("The newValue value should be got properly.", null, container.getColumnName());
    }
}
