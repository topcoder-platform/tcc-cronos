/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

/**
 * <p>
 * This class is a container for information about component dependency. It extends Component class to hold the
 * dependency component information. Additionally it holds information about dependency component path, dependency type
 * and category.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because its base class is not thread safe. Additionally this class also
 * provides some mutable attributes. The user must synchronize all calls to its methods to use it in thread safe manner.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class ComponentDependency extends Component {

    /**
     * <p>
     * The component dependency category. Is initialized in the constructor. Cannot be null. Has corresponding getter
     * and setter.
     * </p>
     */
    private DependencyCategory category;

    /**
     * <p>
     * The dependency component type. Is initialized in the constructor. Cannot be null. Has corresponding getter and
     * setter.
     * </p>
     */
    private DependencyType type;

    /**
     * <p>
     * The path of the dependency component. Is initialized in the constructor. Cannot be null or empty. Has
     * corresponding getter and setter.
     * </p>
     */
    private String path;

    /**
     * <p>
     * Creates an instance of ComponentDependency with the given parameters.
     * </p>
     *
     * @param name the name of the component (cannot be null or empty)
     * @param version the version of the component (cannot be null)
     * @param language the programming language of the component (cannot be null)
     * @param category the component dependency category (cannot be null)
     * @param type the dependency component type (cannot be null)
     * @param path the path of the dependency component (cannot be null or empty)
     *
     * @throws IllegalArgumentException if any argument is null or if name or path is empty
     */
    public ComponentDependency(String name, String version, ComponentLanguage language, DependencyCategory category,
        DependencyType type, String path) {
        super(name, version, language);

        Utils.checkNull(category, "category");
        Utils.checkNull(type, "type");
        Utils.checkStringNullOrEmpty(path, "path");
        this.category = category;
        this.type = type;
        this.path = path;
    }

    /**
     * <p>
     * Retrieves the component dependency category.
     * </p>
     *
     * @return the component dependency category (cannot be null)
     */
    public DependencyCategory getCategory() {
        return this.category;
    }

    /**
     * <p>
     * Retrieves the dependency component type.
     * </p>
     *
     * @return the dependency component type (cannot be null)
     */
    public DependencyType getType() {
        return this.type;
    }

    /**
     * <p>
     * Retrieves the path of the dependency component.
     * </p>
     *
     * @return the path of the dependency component (cannot be null or empty)
     */
    public String getPath() {
        return this.path;
    }

    /**
     * <p>
     * Sets the component dependency category.
     * </p>
     *
     * @param category the component dependency category (cannot be null)
     *
     * @throws IllegalArgumentException if category is null
     */
    public void setCategory(DependencyCategory category) {
        Utils.checkNull(category, "category");
        this.category = category;
    }

    /**
     * <p>
     * Sets the dependency component type.
     * </p>
     *
     * @param type the dependency component type (cannot be null)
     *
     * @throws IllegalArgumentException if type is null
     */
    public void setType(DependencyType type) {
        Utils.checkNull(type, "type");
        this.type = type;
    }

    /**
     * <p>
     * Sets the path of the dependency component.
     * </p>
     *
     * @param path the path of the dependency component (cannot be null or empty)
     *
     * @throws IllegalArgumentException if path is null or empty
     */
    public void setPath(String path) {
        Utils.checkStringNullOrEmpty(path, "path");
        this.path = path;
    }
}
