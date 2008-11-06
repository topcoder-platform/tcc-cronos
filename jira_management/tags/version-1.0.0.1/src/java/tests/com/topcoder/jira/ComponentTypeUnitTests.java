/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>ComponentType</code> enum.
 *
 * @author agh
 * @version 1.0
 */
public class ComponentTypeUnitTests extends TestCase {

    /**
     * Tests API.
     */
    public void testAPI() {
        assertEquals("Enum has incorrect number of values", 3, ComponentType.values().length);

        assertEquals("Enum has incorrect value", ComponentType.valueOf("GENERIC_COMPONENT"),
            ComponentType.GENERIC_COMPONENT);
        assertEquals("Enum has incorrect value", ComponentType.valueOf("CUSTOM_COMPONENT"),
            ComponentType.CUSTOM_COMPONENT);
        assertEquals("Enum has incorrect value", ComponentType.valueOf("APPLICATION"),
            ComponentType.APPLICATION);
    }
}
