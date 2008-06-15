/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

/**
 * <p>
 * The enumeration for component dependency types. Currently only two types are provided: internal and external. It is
 * used in ComponentDependency class to specify the type of the dependency component.
 * </p>
 * <p>
 * Thread Safety: It is enum type so it should be thread safe for each value.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public enum DependencyType {

    /**
     * <p>
     * Represents the internal component dependency type. This type is used when dependency component is located in
     * TopCoder software catalog.
     * </p>
     */
    INTERNAL,

    /**
     * <p>
     * Represents the external component dependency type. This type is used when dependency component is a third-party
     * one (is not located in TopCoder software catalog).
     * </p>
     */
    EXTERNAL
}
