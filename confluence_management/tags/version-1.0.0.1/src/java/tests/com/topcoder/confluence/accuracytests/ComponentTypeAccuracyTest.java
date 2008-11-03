/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.accuracytests;

import com.topcoder.confluence.entities.ComponentType;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for the <code>ComponentType</code> enum.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentTypeAccuracyTest extends TestCase {

    /**
     * <p>
     * Accuracy test for the method <code>getStringName()</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetStringNameAccuracy() throws Exception {
        assertEquals("Should be 'Custom'.", "Custom", ComponentType.CUSTOM.getStringName());
        assertEquals("Should be 'Generic'.", "Generic", ComponentType.GENERIC.getStringName());
    }
}
