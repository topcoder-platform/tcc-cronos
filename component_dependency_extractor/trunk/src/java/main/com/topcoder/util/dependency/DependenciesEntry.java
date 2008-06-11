/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class represents the dependencies for a single component. It holds the information about the component and all
 * its dependencies.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because it has mutable attributes. The user must synchronize all calls
 * to its methods to use it in thread safe manner.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class DependenciesEntry {
    /**
     * <p>
     * The information about the target component. Is initialized in the constructor. Cannot be null. Has corresponding
     * getter and setter.
     * </p>
     */
    private Component targetComponent;

    /**
     * <p>
     * The list of dependencies for the target component. Collection instance is initialized in the constructor and
     * never changed after that. Cannot be null. Cannot contain null. Content of this collection can be retrieved and
     * modified through the corresponding getter and setter.
     * </p>
     */
    private final List<ComponentDependency> dependencies;

    /**
     * <p>
     * Creates an instance of DependenciesEntry with the given parameters.
     * </p>
     *
     * @param dependencies the list of dependencies for the target component (cannot be null; cannot contain null)
     * @param targetComponent the information about the target component (cannot be null)
     *
     * @throws IllegalArgumentException if targetComponent or dependencies is null or dependencies contains null
     */
    public DependenciesEntry(Component targetComponent, List<ComponentDependency> dependencies) {
        Utils.checkNull(targetComponent, "target component");
        Utils.checkCollection(dependencies, "dependencies", true);

        this.targetComponent = targetComponent;
        // shallow copy of dependencies collection
        this.dependencies = new ArrayList<ComponentDependency>(dependencies);
    }

    /**
     * <p>
     * Retrieves the information about the target component.
     * </p>
     *
     * @return the information about the target component (cannot be null)
     */
    public Component getTargetComponent() {
        return this.targetComponent;
    }

    /**
     * <p>
     * Retrieves the list of dependencies for the target component.
     * </p>
     *
     * @return the list of dependencies for the target component (cannot be null; cannot contain null)
     */
    public List<ComponentDependency> getDependencies() {
        // returns a shallow copy of dependencies collection.
        return new ArrayList<ComponentDependency>(this.dependencies);
    }

    /**
     * <p>
     * Sets the information about the target component.
     * </p>
     *
     * @param targetComponent the information about the target component (cannot be null)
     *
     * @throws IllegalArgumentException if targetComponent is null
     */
    public void setTargetComponent(Component targetComponent) {
        Utils.checkNull(targetComponent, "targetComponent");
        this.targetComponent = targetComponent;
    }

    /**
     * <p>
     * Sets the list of dependencies for the target component.
     * </p>
     *
     * @param dependencies the list of dependencies for the target component (cannot be null; cannot contain null)
     *
     * @throws IllegalArgumentException if dependencies is null or contains null
     */
    public void setDependencies(List<ComponentDependency> dependencies) {
        Utils.checkCollection(dependencies, "dependencies", true);
        this.dependencies.clear();
        this.dependencies.addAll(dependencies);
    }
}
