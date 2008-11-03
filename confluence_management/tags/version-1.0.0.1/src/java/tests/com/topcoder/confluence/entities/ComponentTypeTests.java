/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.entities;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>ComponentType</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentTypeTests extends TestCase {

    /**
     * <p>
     * Accuracy test case for {@link ComponentType#getStringName()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getStringName_Accuracy() throws Exception {
        assertEquals("Should be 'Custom'.", "Custom", ComponentType.CUSTOM.getStringName());
        assertEquals("Should be 'Generic'.", "Generic", ComponentType.GENERIC.getStringName());
    }
}
