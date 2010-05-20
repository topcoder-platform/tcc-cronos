/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

/**
 * <p>
 * This enum represents the dependency category.
 * <li>COMPILE. Represents the compile component dependency category. This
 * category is used when one component requires some other component to be
 * properly compiled.</li>
 * <li>TEST. Represents the test component dependency category. This category
 * is used when one component is required when executing tests of another
 * component.</li>
 * </p>
 * <p>
 * It is used by <code>ComponentDependency</code> to represent the category of
 * the dependency.
 * </p>
 * <p>
 * <strong>Thread safe:</strong> enum are thread safe.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public enum DependencyCategory {
    /**
     * Represents the compile component dependency category. This category is
     * used when one component requires some other component to be properly
     * compiled.
     */
    COMPILE,
    /**
     * Represents the test component dependency category. This category is used
     * when one component is required when executing tests of another component.
     */
    TEST
}
