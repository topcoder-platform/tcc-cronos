/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.accuracytests;

import com.topcoder.confluence.entities.ConfluenceAssetType;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for the <code>ConfluenceAssetType</code> enum.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceAssetTypeAccuracyTest extends TestCase {

    /**
     * <p>
     * Accuracy test for the ConfluenceAssetType enum.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testEnumAccuracy() throws Exception {
        assertEquals("The enum name is not correct.", "COMPONENT_DESIGN", ConfluenceAssetType.COMPONENT_DESIGN.name());
        assertEquals("The enum name is not correct.", "COMPONENT_DEVELOPMENT",
                ConfluenceAssetType.COMPONENT_DEVELOPMENT.name());
        assertEquals("The enum name is not correct.", "APPLICATION_SPECIFICATION",
                ConfluenceAssetType.APPLICATION_SPECIFICATION.name());
        assertEquals("The enum name is not correct.", "APPLICATION_ARCHITECTURE",
                ConfluenceAssetType.APPLICATION_ARCHITECTURE.name());
        assertEquals("The enum name is not correct.", "APPLICATION_ASSEMBLY",
                ConfluenceAssetType.APPLICATION_ASSEMBLY.name());
        assertEquals("The enum name is not correct.", "APPLICATION_TESTING", ConfluenceAssetType.APPLICATION_TESTING
                .name());
    }
}
