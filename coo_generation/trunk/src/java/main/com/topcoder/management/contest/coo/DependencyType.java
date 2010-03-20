/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

/**
 * <p>
 * This enum represents the dependency type. A dependency type can be either:
 * <li>external. Represents the external component dependency type. This type
 * is used when dependency component is a third-party one (is not located in
 * TopCoder software catalog).</li>
 * <li>internal. Represents the internal component dependency type. This type
 * is used when dependency component is located in TopCoder software catalog.</li>
 * </p>
 * <p>
 * It is used by <code>ComponentDependency</code> to represent the dependency
 * type.
 * </p>
 * <p>
 * <strong>Thread safe:</strong> enum are thread safe.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public enum DependencyType {
    /**
     * Represents the internal component dependency type. This type is used when
     * dependency component is located in TopCoder software catalog.
     */
    INTERNAL,
    /**
     * Represents the external component dependency type. This type is used when
     * dependency component is a third-party one (is not located in TopCoder
     * software catalog).
     */
    EXTERNAL;
}
