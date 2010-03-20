/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

/**
 * <p>
 * Represents component dependency. A component dependency has the following
 * properties
 * <li> component. The dependent component</li>
 * <li> category. The category of the dependency</li>
 * <li> type. The type of the dependency.</li>
 * </p>
 * <p>
 * This class is used by <code>COOReport</code> to list the dependency to be
 * reported.
 * </p>
 * <p>
 * <strong>Thread safe:</strong> This class is not thread safe since it is
 * mutable.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public class ComponentDependency {
    /**
     * <p>
     * Represents the dependent component.
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter/setter, once
     * set: must not be null.
     * </p>
     */
    private Component component;
    /**
     * <p>
     * Represents the category of the dependency.
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter/setter, once
     * set: must not be null.
     * </p>
     */
    private DependencyCategory category;
    /**
     * <p>
     * Represents the type of the dependency.
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter/setter, once
     * set: must not be null.
     * </p>
     */
    private DependencyType type;

    /**
     * Empty constructor.
     */
    public ComponentDependency() {
    }

    /**
     * Getter for the component.
     *
     * @return The value of the namesake variable.
     */
    public Component getComponent() {
        return component;
    }

    /**
     * Setter for the component.
     *
     * @param component The dependent component. must not be null
     * @throws IllegalArgumentException if any argument is invalid.
     */
    public void setComponent(Component component) {
        Helper.checkNull("component", component);
        this.component = component;
    }

    /**
     * Getter for the dependency category.
     *
     * @return The value of the namesake variable.
     */
    public DependencyCategory getCategory() {
        return category;
    }

    /**
     * Setter for the dependency category.
     *
     * @param category The category of the dependency. must not be null
     * @throws IllegalArgumentException if any argument is invalid.
     */
    public void setCategory(DependencyCategory category) {
        Helper.checkNull("dependencyCategory", category);
        this.category = category;
    }

    /**
     * Getter for the dependency type.
     *
     * @return The value of the namesake variable.
     */
    public DependencyType getType() {
        return type;
    }

    /**
     * Setter for the dependency type.
     *
     * @param type The type of the dependency. must not be null
     * @throws IllegalArgumentException if any argument is invalid.
     *
     */
    public void setType(DependencyType type) {
        Helper.checkNull("dependencyType", type);
        this.type = type;
    }
}
