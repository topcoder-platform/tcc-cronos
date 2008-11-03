/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.accuracytests;

import com.topcoder.confluence.entities.ConfluencePageCreatedAction;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for the <code>ConfluencePageCreatedAction</code> enum.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluencePageCreatedActionAccuracyTest extends TestCase {

    /**
     * <p>
     * Accuracy test for the ConfluencePageCreatedAction enum.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testEnumAccuracy() throws Exception {
        assertEquals("The enum name is not correct.", "BASE_PAGE_AND_VERSION_CREATED",
                ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED.name());
        assertEquals("The enum name is not correct.", "BASE_PAGE_AND_VERSION_EXISTED",
                ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_EXISTED.name());
        assertEquals("The enum name is not correct.", "BASE_PAGE_EXISTED_VERSION_CREATED",
                ConfluencePageCreatedAction.BASE_PAGE_EXISTED_VERSION_CREATED.name());
    }
}
