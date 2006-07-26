/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * The Resource class is the main modeling class in this component. It represents any arbitrary resource. The Resource
 * class is simply a container for a few basic data fields. All data fields in this class are mutable and have get and
 * set methods.
 * </p>
 *
 * <p>
 * The only thing to take note of when developing this class is that the setId method throws the IdAlreadySetException.
 * </p>
 *
 * <p>
 * This class is mutable because its base class is mutable. Hence it is not thread-safe.
 * </p>
 *
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class Resource extends AuditableResourceStructure {
    /**
     * The value that the id field will have (and that the getId method will return) when the id field has not been set
     * in the constructor or through the setId method.
     */
    public static final long UNSET_ID = -1;

    /**
     * The id of the Resource.
     *
     * <p>
     * This field is set in the constructor and is mutable. The value must always be >0 or UNSET_ID. The default value
     * is UNSET_ID. Once set to a value besides UNSET_ID, the id can not be set to another value. This allows the class
     * to have a Java Bean API but still have an essentially unchangeable id.
     * </p>
     *
     * This field is set in the constructor and setId method and is retrieved through the getId method.
     *
     */
    private long id = UNSET_ID;

    /**
     * The role of the resource, as given by a ResourceRole instance.
     *
     * <p>
     * This field is set in the constructor and is mutable. The value can be null or non-null. The default value is
     * null, which indicates that the role has not been set.
     * </p>
     *
     * This field is set in the setResourceRole method and retrieved through the getResourceRole method.
     *
     */
    private ResourceRole resourceRole = null;

    /**
     * The identifier of the project that the resource belongs to.
     *
     * <p>
     * The value can be null or non-null. This field is mutable and its default is null. Null indicates that the
     * resource is not associated with a project. When non-null, the value of this field will be >0.
     * </p>
     *
     * This field is set in the setProject method and retrieved through the getProject method.
     *
     */
    private Long project = null;

    /**
     * The phase that the resource is associated with.
     *
     * <p>
     * The value can be null or non-null. This field is mutable and its default value is null. Null indicates that no
     * phase is associated with the resource. When non-null, the value of this field will be >0.
     * </p>
     *
     * This field is set in the setPhase method and retrieved through the getPhase method.
     *
     */
    private Long phase = null;

    /**
     * The submission that the resource is associated with.
     *
     * <p>
     * The value can be null or non-null. This field is mutable and its default value is null. Null indicates that no
     * submission is associated with this resource. When non-null, the value of this field will be >0.
     * </p>
     *
     * The field is set in the setSubmission method and retrieved through the getSubmission method.
     *
     */
    private Long submission = null;

    /**
     * The map of properties that are associated with this resource.
     *
     * <p>
     * The keys of the map are all required to be non-null String instances (no other condition is enforced). The values
     * in the map can be of any type. However, persistence mechanisms are only required to support saving the toString()
     * string of each value.
     * </p>
     *
     * This field is immutable, but its contents can be manipulated through the setProperty method. The values in this
     * map can be retrieved through the getProperty and getAllProperties methods.
     *
     */
    private Map properties = new HashMap();

    /**
     * Creates a new Resource instance.
     *
     */
    public Resource() {
        // empty.
    }

    /**
     * Creates a new Resource with id.
     *
     * @param id
     *            The id of the resource
     * @throws IllegalArgumentException
     *             If id is <= 0
     */
    public Resource(long id) {
        setId(id);
    }

    /**
     * Creates a new Resource with id and ResourceRole.
     *
     * @param id
     *            The id of the resource
     * @param resourceRole
     *            The role of the resource
     * @throws IllegalArgumentException
     *             If id is <= 0 or resourceRole is null
     */
    public Resource(long id, ResourceRole resourceRole) {
        Helper.checkNull(resourceRole, "resourceRole");
        this.resourceRole = resourceRole;

        setId(id);
    }

    /**
     * Sets the id of the resource. The setId method only allows the id to be set once. After that the id value is
     * locked in and can not be changed.
     *
     * @param id
     *            The id for the resource
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @throws IdAlreadySetException
     *             If the id has already been set (i.e. the id field is not UNSET_ID)
     */
    public void setId(long id) {
        Helper.checkPositiveValue(id, "id");

        if (this.id != UNSET_ID) {
            throw new IdAlreadySetException("The id has already been set.");
        }

        this.id = id;
    }

    /**
     * Gets the id of the resource. The return is either UNSET_ID_VALUE or > 0.
     *
     * @return The id of the resource
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the role of the resource. The ResourceRole value can be null or non-null.
     *
     * @param resourceRole
     *            The role of the resource
     */
    public void setResourceRole(ResourceRole resourceRole) {
        this.resourceRole = resourceRole;
    }

    /**
     * Gets the role of the resource. The return ResourceRole can be null or non-null.
     *
     * @return The role of the resource.
     */
    public ResourceRole getResourceRole() {
        return resourceRole;
    }

    /**
     * Sets the project that the resource is associated with. The project can be null, or positive value.
     *
     * @param project
     *            The identifier of the project the resource is associate with (or null)
     * @throws IllegalArgumentException
     *             If project is a wrapper of a value <= 0
     */
    public void setProject(Long project) {
        Helper.checkLongValue(project, "project");

        this.project = project;
    }

    /**
     * Gets the project that the resource is associated with. The returned project can be null, or positive value.
     *
     * @return The identifier of the project the resource is associated with
     */
    public Long getProject() {
        return project;
    }

    /**
     * Sets the phase that the resource is associated with. The phase can be null, or positive value.
     *
     * @param phase
     *            The identifier of the phase the resource is associated with (or null)
     * @throws IllegalArgumentException
     *             If phase is a wrapper of a value <= 0
     */
    public void setPhase(Long phase) {
        Helper.checkLongValue(phase, "phase");

        this.phase = phase;
    }

    /**
     * Gets the identifier of the phase that the resource is associated with. The phase returned will be null, or
     * positive value.
     *
     * @return The identifier of the phase the resource is associated with
     */
    public Long getPhase() {
        return phase;
    }

    /**
     * Sets the submission that the resource is associated with. The submission can be null, or positive value.
     *
     * @param submission
     *            The identifier of the submission the resource is associated with (or null)
     * @throws IllegalArgumentException
     *             If submission is a wrapper of a value <= 0
     */
    public void setSubmission(Long submission) {
        Helper.checkLongValue(submission, "submission");

        this.submission = submission;
    }

    /**
     * Gets the identifier of the submission that is associated with the resource. The returned submission can be null,
     * or positive value.
     *
     * @return The identifier of the submission the resource is associated with
     */
    public Long getSubmission() {
        return submission;
    }

    /**
     * Sets a property associated with the resource. If there is already a value associated with the name, it will be
     * replaced.
     *
     * @param name
     *            The name of the property to set.
     * @param value
     *            The property value to associate with the name.
     * @throws IllegalArgumentException
     *             If name is null or value is null
     */
    public void setProperty(String name, Object value) {
        Helper.checkNull(name, "name");
        Helper.checkNull(value, "value");

        properties.put(name, value);
    }

    /**
     * Tells if a property has been set for this Resource. Returns true if there is an entry in the properties map for
     * name.
     *
     * @return True if there is a value associated with the property.
     * @param name
     *            The name of the property
     * @throws IllegalArgumentException
     *             If name is null.
     */
    public boolean hasProperty(String name) {
        Helper.checkNull(name, "name");

        return properties.containsKey(name);
    }

    /**
     * Gets the property value for the given name. Return the entry in the properties map for name.
     *
     * @return The value associated with the property name. Null if no value is associated with the property.
     * @param name
     *            The name of the property.
     * @throws IllegalArgumentException
     *             If name is null.
     */
    public Object getProperty(String name) {
        Helper.checkNull(name, "name");

        return properties.get(name);
    }

    /**
     * Gets all the properties that are associated with the Resource.
     * The returned map has all non-null String keys.
     * The values can be any type, but none of them will be null.
     *
     * @return The map of properties associated with the resource.
     */
    public Map getAllProperties() {
        return new HashMap(properties);
    }
}
