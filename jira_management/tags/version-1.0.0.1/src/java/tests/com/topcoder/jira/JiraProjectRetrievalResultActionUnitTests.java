/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>JiraProjectRetrievalResultAction</code> enum.
 *
 * @author agh
 * @version 1.0
 */
public class JiraProjectRetrievalResultActionUnitTests extends TestCase {

    /**
     * Tests API.
     */
    public void testAPI() {
        assertEquals("Enum has incorrect number of values", 3, JiraProjectRetrievalResultAction
            .values().length);

        assertEquals("Enum has incorrect value", JiraProjectRetrievalResultAction
            .valueOf("PROJECT_AND_VERSION_FOUND"),
            JiraProjectRetrievalResultAction.PROJECT_AND_VERSION_FOUND);
        assertEquals("Enum has incorrect value", JiraProjectRetrievalResultAction
            .valueOf("PROJECT_FOUND_NOT_VERSION"),
            JiraProjectRetrievalResultAction.PROJECT_FOUND_NOT_VERSION);
        assertEquals("Enum has incorrect value", JiraProjectRetrievalResultAction
            .valueOf("PROJECT_NOT_FOUND"), JiraProjectRetrievalResultAction.PROJECT_NOT_FOUND);
    }
}
