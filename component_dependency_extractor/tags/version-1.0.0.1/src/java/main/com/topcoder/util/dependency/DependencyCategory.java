/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

/**
 * <p>
 * The enumeration for component dependency categories. Currently only two categories are provided: compile and test. It
 * is used in ComponentDependency class to specify the category of the dependency.
 * </p>
 * <p>
 * Thread Safety: It is enum type so it should be thread safe for each value.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public enum DependencyCategory {

    /**
     * <p>
     * Represents the compile component dependency category. This category is used when one component requires some
     * other component to be properly compiled.
     * </p>
     */
    COMPILE,

    /**
     * <p>
     * Represents the test component dependency category. This category is used when one component is required when
     * executing tests of another component.
     * </p>
     */
    TEST
}
