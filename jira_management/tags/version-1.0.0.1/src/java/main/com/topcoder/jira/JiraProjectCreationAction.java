/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

/**
 * Enumeration of available JIRA project creation actions.
 * <p>
 * Thread-safety. As an enumeration, the types are created statically, and never change.
 *
 * @author Mafy, agh
 * @version 1.0
 */
public enum JiraProjectCreationAction {
    /**
     * Indicates that project and version were created.
     */
    PROJECT_AND_VERSION_CREATED,

    /**
     * Indicates that project already exists and version was created.
     */
    PROJECT_EXISTED_VERSION_CREATED,

    /**
     * Indicates that both project and version exist.
     */
    PROJECT_AND_VERSION_EXISTED;
}
