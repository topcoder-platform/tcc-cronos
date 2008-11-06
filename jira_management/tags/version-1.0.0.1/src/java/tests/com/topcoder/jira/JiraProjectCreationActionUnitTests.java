/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>JiraProjectCreationAction</code> enum.
 *
 * @author agh
 * @version 1.0
 */
public class JiraProjectCreationActionUnitTests extends TestCase {

    /**
     * Tests API.
     */
    public void testAPI() {
        assertEquals("Enum has incorrect number of values", 3,
            JiraProjectCreationAction.values().length);

        assertEquals("Enum has incorrect value", JiraProjectCreationAction
            .valueOf("PROJECT_AND_VERSION_CREATED"),
            JiraProjectCreationAction.PROJECT_AND_VERSION_CREATED);
        assertEquals("Enum has incorrect value", JiraProjectCreationAction
            .valueOf("PROJECT_EXISTED_VERSION_CREATED"),
            JiraProjectCreationAction.PROJECT_EXISTED_VERSION_CREATED);
        assertEquals("Enum has incorrect value", JiraProjectCreationAction
            .valueOf("PROJECT_AND_VERSION_EXISTED"),
            JiraProjectCreationAction.PROJECT_AND_VERSION_EXISTED);
    }
}
