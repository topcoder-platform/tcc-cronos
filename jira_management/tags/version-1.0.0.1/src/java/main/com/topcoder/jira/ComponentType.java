/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

/**
 * Enumeration of available component types. It is used to retrieve notification, permission and
 * security issue schemas.
 * <p>
 * Thread-safety. As an enumeration, the types are created statically, and never change.
 *
 * @author Mafy, agh
 * @version 1.0
 */
public enum ComponentType {
    /**
     * Generic TopCoder component.
     */
    GENERIC_COMPONENT,

    /**
     * Custom TopCoder component.
     */
    CUSTOM_COMPONENT,

    /**
     * Client project.
     */
    APPLICATION;
}
