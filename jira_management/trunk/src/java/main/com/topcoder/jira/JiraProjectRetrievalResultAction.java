/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

/**
 * Enumeration of available JIRA project retrieval results.
 * <p>
 * Thread-safety. As an enumeration, the types are created statically, and never change.
 *
 * @author Mafy, agh
 * @version 1.0
 */
public enum JiraProjectRetrievalResultAction {
    /**
     * Indicates that project and version were found.
     */
    PROJECT_AND_VERSION_FOUND,

    /**
     * Indicates that project was found and version was not.
     */
    PROJECT_FOUND_NOT_VERSION,

    /**
     * Indicates that project was not found.
     */
    PROJECT_NOT_FOUND;
}
