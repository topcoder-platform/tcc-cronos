/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * This is a data object that represents a software component that was written by Topcoder, and is part of the Topcoder
 * component catalog. This object contains properties to meta-information such as the identifier, name, description, and
 * version of the component.
 * </p>
 * <p>
 * Since the more complex components utilize other components to help as building blocks for higher level operations,
 * this class maintains a reference to which components that this one is dependent on. It also maintains the technology
 * categories of which this component belongs to.
 * </p>
 * <p>
 * Thread Safety: - This object is not inherently thread safe, and it is expected for each thread to work on its own
 * instance, or if sharing instances, then to refrain from modifying the object state.
 * </p>
 * <p><b>Note: This is only a mock-up of this class, because the Component Version Loader component has not finished
 * development yet. This is by no means a complete and adequate implementation of that design, only the methods that
 * really had to be implemented to develop this component have been included.</b></p>
 *
 * @author ShindouHikaru, marijnk
 * @version 1.0
 * @copyright &copy; 2005, TopCoder, Inc. All rights reserved.
 */
public class ComponentVersion {
    /**
     * <p>
     * This is the unique identifier that represents this component. Each component version should have a different
     * identifier. It is initialized in the constructor, accessed via getId, and changed afterwards.
     * </p>
     */
    private final long id;

    /**
     * <p>
     * This is the name of the component. It is initialized in the constructor, accessed via getName, and changed via
     * setName. It may not be null or an empty String.
     * </p>
     */
    private String name;

    /**
     * <p>
     * This is a String that contains human-readable text describing the component. It is initialized in the
     * constructor, accessed via getDescription, and changed via getDescription. It may be a null String, since its an
     * optional property.
     * </p>
     */
    private String description;

    /**
     * <p>
     * This is the version of the component. It is initialized in the constructor, accessed via getVersion, and changed
     * via setVersion.
     * </p>
     */
    private String version;

    /**
     * <p>
     * This is a map that contains the different customizable attributes that may be associated with the component
     * version. The keys of this map are Strings which are the names of the attribute, and the values of this Map are
     * the attribute values. The keys or values may not be null or empty Strings. The Map instance itself is initialized
     * in the constructor (suggested to a new HashMap), but is never changed or accessed. The *contents* of the Map are
     * changed via addAttribute, removeAttribute and clearAttribute methods, and accessed via getAttribute and
     * getAttributeNames methods.
     * </p>
     */
    private final Map attributes = new HashMap();

    /**
     * <p>
     * This is a list that contains ExternalVersion objects representing the external components that this component is
     * dependent on for some area of functionality. It may never contain null elements. The List instance is initialized
     * during construction (suggested type is ArrayList), and is not changed or accessed afterwards. The *contents* of
     * this List are accessed via getExternalComponentDependencies, and modified via addExternalComponentDependency,
     * removeExternalComponentDependency and clearExternalComponentDependencies.
     * </p>
     */
    private final List externalComponentDependencies = new ArrayList();

    /**
     * <p>
     * This is a list that contains TechnologyType objects representing the technology categories that this component
     * belongs to. It may never contain null elements. The List instance is initialized during construction (suggested
     * type is ArrayList), and is not changed or accessed afterwards. The *contents* of this List are accessed via
     * getTechnologyTypes, and modified via addTechnologyType, removeTechnologyType and clearTechnologyTypes.
     * </p>
     */
    private final List technologyTypes = new ArrayList();

    /**
     * <p>
     * This is a list that contains other ComponentVersion objects representing the Topcoder components that this
     * component is dependent on for some area of functionality. It may never contain null elements. The List instance
     * is initialized during construction (suggested type is ArrayList), and is not changed or accessed afterwards. The
     * *contents* of this List are accessed via getComponentDependencies, and modified via addComponentDependency,
     * removeComponentDependency and clearComponentDependencies.
     * </p>
     */
    private final List componentDependencies = new ArrayList();

    /**
     * <p>
     * Constructor that initializes the ComponentVersion using the constructor parameter arguments.
     * </p>
     *
     * @param id the unique identifier that represents this component.
     * @param name the name of the component.
     * @param description the description of the external component or null if none exists
     * @param version the version of the component.
     * @throws NullPointerException if name or version is null.
     * @throws IllegalArgumentException if name or version is an empty String.
     */
    public ComponentVersion(long id, String name, String description, String version) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.version = version;
    }

    /**
     * <p>
     * Retrieves the unique identifier that represents this component.
     * </p>
     *
     * @return the unique identifier that represents this component.
     */
    public Long getId() {
        return new Long(id);
    }

    /**
     * <p>
     * Retrieves the name of the component.
     * </p>
     *
     * @return the name of the component.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Retrieves the description of the external component. This attribute is optional, and null may be returned.
     * </p>
     *
     * @return the description of the external component or null if none exists
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Retrieves the version of the component.
     * </p>
     *
     * @return the version of the component.
     */
    public String getVersion() {
        return version;
    }

    /**
     * <p>
     * Retrieves the component attribute with the given name.
     * </p>
     *
     * @return The value of the given attribute.
     * @param attributeName The component attribute with given name.
     */
    public String getAttribute(String attributeName) {
        return (String) attributes.get(attributeName);
    }

    /**
     * <p>
     * Retrieves an array containing all the attribute names of this component. Any changes to the given array do not
     * change the state of this ComponentVersion object.
     * </p>
     *
     * @return an array containing all the attribute names of this component.
     */
    public String[] getAttributeNames() {
        return (String[]) attributes.keySet().toArray(new String[0]);
    }

    /**
     * <p>
     * Retrieves an array containing all the external component dependencies of this component. Any modifications to the
     * returned array will not result in a change in this ComponentVersion.
     * </p>
     *
     * @return an array containing all the external component dependencies of this component.
     */
    public ExternalComponentVersion[] getExternalComponentDependencies() {
        return (ExternalComponentVersion[]) externalComponentDependencies.toArray(new ExternalComponentVersion[0]);
    }

    /**
     * <p>
     * Retrieves an array containing all the technology types for which this component belongs. Any modifications to the
     * returned array will not result in a change in this ComponentVersion.
     * </p>
     *
     * @return an array containing all the technology types for which this component belongs.
     */
    public TechnologyType[] getTechnologyTypes() {
        return (TechnologyType[]) technologyTypes.toArray(new TechnologyType[0]);
    }

    /**
     * <p>
     * Retrieves an array containing all the direct Topcoder component dependencies of this component. Any modifications
     * to the returned array will not result in a change in this ComponentVersion. Note that this method only refers to
     * DIRECT component dependencies. If the components that this component is dependent on, are, in turn, dependent on
     * yet other components, then those components will not be returned.
     * </p>
     *
     * @return an array containing all the Topcoder component dependencies of this component.
     */
    public ComponentVersion[] getComponentDependencies() {
        return (ComponentVersion[]) componentDependencies.toArray(new ComponentVersion[0]);
    }

    /**
     * <p>
     * Retrieves an array containing all the indirect Topcoder component dependencies of this component. Any
     * modifications to the returned array will not result in a change in this ComponentVersion. Note that this method
     * only refers to INDIRECT component dependencies. This refers to the component dependencies that the direct
     * component dependencies are dependent on. For example, Route Instance Manager uses the UserProfile, which, in
     * turn, uses the Lightweight XML Parser. In this case, the Route Instance Manager has the Lightweight XML Parser as
     * an INDIRECT component dependency.
     * </p>
     *
     * @return The indirect dependencies of this component.
     */
    public ComponentVersion[] getIndirectDependencies() {
        Set dependencies = new HashSet();
        for (Iterator it = componentDependencies.iterator(); it.hasNext();) {
            ComponentVersion v = (ComponentVersion) it.next();
            dependencies.addAll(Arrays.asList(v.getAllTopcoderDependencies()));
        }
        return (ComponentVersion[]) dependencies.toArray(new ComponentVersion[0]);
    }

    /**
     * <p>
     * Retrieves an array containing all the Topcoder component dependencies, both direct and indirect of this
     * component. Any modifications to the returned array will not result in a change in this ComponentVersion.
     * </p>
     *
     * @return All TC Dependencies of this component.
     */
    public ComponentVersion[] getAllTopcoderDependencies() {
        // This is not a proper implementation, but it will do in this mock version
        Set dependencies = new HashSet();
        dependencies.addAll(Arrays.asList(getComponentDependencies()));
        dependencies.addAll(Arrays.asList(getIndirectDependencies()));
        return (ComponentVersion[]) dependencies.toArray(new ComponentVersion[0]);
    }

    /**
     * <p>
     * Adds a component attribute with the given attribute name and value.
     * </p>
     *
     * @param attrName The name of the attribute to add.
     * @param attrValue The value of the attribute to add.
     * @throws NullPointerException if the attrName or attrValue is null.
     * @throws IllegalArgumentException if the trimmed attrName or trimmed attrValue is an empty String.
     */
    public void addAttribute(String attrName, String attrValue) {
        attributes.put(attrName, attrValue);
    }

    /**
     * <p>
     * Adds a new external component dependency to this component.
     * </p>
     *
     * @param dependency The component to add.
     * @throws NullPointerException if the externalComponentDependency is null.
     */
    public void addExternalComponentDependency(ExternalComponentVersion dependency) {
        externalComponentDependencies.add(dependency);
    }

    /**
     * <p>
     * Adds a new component dependency to this component.
     * </p>
     *
     * @param dependency The component to add.
     * @throws NullPointerException if the dependency is null.
     */
    public void addComponentDependency(ComponentVersion dependency) {
        componentDependencies.add(dependency);
    }

    /**
     * <p>
     * Adds a new technology type to this component.
     * </p>
     *
     * @param techType The TechnologyType to add.
     * @throws NullPointerException if the technologyType is null.
     */
    public void addTechnologyType(TechnologyType techType) {
        technologyTypes.add(techType);
    }

    /**
     * <p>
     * Sets the name of the component.
     * </p>
     *
     * @param name the name of the component.
     * @throws NullPointerException if the name is null.
     * @throws IllegalArgumentException if the trimmed name is an empty String.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Sets the description of the external component. It may be null since this is an optional property.
     * </p>
     *
     * @param description the description of the external component.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Sets the version of the component.
     * </p>
     *
     * @param version the version of the component.
     * @throws NullPointerException if the version is null.
     * @throws IllegalArgumentException if the trimmed version is an empty String.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * <p>
     * Removes a component attribute with given name from this component.
     * </p>
     *
     * @return True if the attribute was removed, false if it wasn't removed because it didn't exist in the first place.
     * @param attrName The name of the attribute to remove.
     * @throws NullPointerException if the attrName is null.
     * @throws IllegalArgumentException if the trimmed attrName is an empty String.
     */
    public boolean removeAttribute(String attrName) {
        return (attributes.remove(attrName) != null);
    }

    /**
     * <p>
     * Removes the specified external component dependency from this component.
     * </p>
     *
     * @return true if the dependency was successfully removed, false if it wasn't removed because it didn't exist in
     *         this component in the first place.
     * @param dependency the dependency to remove.
     * @throws NullPointerException if the dependency is null.
     */
    public boolean removeExternalComponentDependency(ExternalComponentVersion dependency) {
        return externalComponentDependencies.remove(dependency);
    }

    /**
     * <p>
     * Removes the specified component dependency from this component.
     * </p>
     *
     * @return true if the dependency was successfully removed, false if it wasn't removed because it didn't exist in
     *         this component in the first place.
     * @param dependency the dependency to remove.
     * @throws NullPointerException if the dependency is null.
     */
    public boolean removeComponentDependency(ComponentVersion dependency) {
        return componentDependencies.remove(dependency);
    }

    /**
     * <p>
     * Removes the specified technology type from this component.
     * </p>
     *
     * @return true if the technology type was successfully removed, false if it wasn't removed because it didn't exist
     *         in this component in the first place.
     * @param technologyType The technology type to remove.
     * @throws NullPointerException if the technologyType is null.
     */
    public boolean removeTechnologyType(TechnologyType technologyType) {
        return technologyTypes.remove(technologyType);
    }

    /**
     * <p>
     * Removes all attributes from this component.
     * </p>
     */
    public void clearAttributes() {
        attributes.clear();
    }

    /**
     * <p>
     * Removes all external component dependencies from this component.
     * </p>
     */
    public void clearExternalComponentDependencies() {
        externalComponentDependencies.clear();
    }

    /**
     * <p>
     * Removes all component dependencies from this component.
     * </p>
     */
    public void clearComponentDependencies() {
        componentDependencies.clear();
    }

    /**
     * <p>
     * Removes all technology types from this component.
     * </p>
     */
    public void clearTechnologyTypes() {
        technologyTypes.clear();
    }

    /**
     * <p>
     * An overridden equals method to indicate equality to other ComponentVersion objects based on their identifier.
     * Another object is equal to this object if and only if the other object is also a ComponentVersion, and the id of
     * both objects are equal.
     * </p>
     *
     * @return True if the two objects are equal, false otherwise.
     * @param o The object to compare equality to.
     */
    public boolean equals(Object o) {
        if (!(o instanceof ComponentVersion)) {
            return false;
        }
        return ((ComponentVersion) o).id == id;
    }

    /**
     * <p>
     * An overridden hashCode, as a companion to the modification of the equals method.
     * </p>
     *
     * @return a hash code value for this object.
     */
    public int hashCode() {
        return new Long(id).hashCode();
    }
}
