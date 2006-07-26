/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

/**
 * <p>
 * The ResourceRole class is the second modeling class in this component. It represents a type of resource and is used
 * to tag instances of the Resource class as playing a certain role. The ResourceRole class is simply a container for a
 * few basic data fields. All data fields in this class are mutable and have get and set methods.
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
public class ResourceRole extends AuditableResourceStructure {
    /**
     * The value that the id field will have (and that the getId method will return) when the id field has not been set
     * in the constructor or through the setId method.
     */
    public static final long UNSET_ID = -1;

    /**
     * The id of the ResourceRole.
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
     * The name of the ResourceRole.
     *
     * <p>
     * This field is set in the constructor and is mutable. Any value is allowed for this field. It can be null or
     * non-null, and any value is allowed when non-null (empty string, all whitespace, etc). The default value of this
     * field is null, which indicates that the name has not yet been set (or has been unset).
     * </p>
     *
     * This field is set in the constructor and setName method and is retrieved through the getName method.
     *
     */
    private String name = null;

    /**
     * The description of the ResourceRole. T
     *
     * <p>
     * This field is set in the constructor and is mutable. Any value is allowed for this field. It can be null or
     * non-null, and any value is allowed when non-null (empty string, all whitespace, etc). The default value of this
     * field is null, which indicates that the name has not yet been set (or has been unset).
     * </p>
     *
     *
     * This field is set in the constructor and setDescription method and is retrieved through the getDescription
     * method.
     *
     */
    private String description = null;

    /**
     * The identifier of the phase type for this ResourceRole.
     *
     * <p>
     * The value can be null or non-null. This field is mutable and its default value is null. Null indicates that no
     * phase is associated with the resource. When non-null, the value of this field will be >0.
     * </p>
     *
     * This field is set in the setPhase method and retrieved through the getPhase method.
     *
     */
    private Long phaseType = null;

    /**
     * Creates a new ResourceRole instance.
     *
     */
    public ResourceRole() {
        // empty
    }

    /**
     * Create a new ResourceRole with id.
     *
     * @param id
     *            The id for the resource role
     * @throws IllegalArgumentException
     *             If id is <= 0
     */
    public ResourceRole(long id) {
        setId(id);
    }

    /**
     * Creates a new ResourceRole with id, name and phaseType.
     *
     * @param id
     *            The id for the resource role
     * @param name
     *            The name for the resource role
     * @param phaseType
     *            The identifier of the phase type
     * @throws IllegalArgumentException
     *             If id or phaseType is <= 0, or name is null.
     */
    public ResourceRole(long id, String name, long phaseType) {
        Helper.checkNull(name, "name");
        Helper.checkPositiveValue(phaseType, "phaseType");

        this.name = name;
        this.phaseType = new Long(phaseType);

        setId(id);
    }

    /**
     * Sets the id of the resource role. The setId method only allows the id to be set once. After that the id value is
     * locked in and can not be changed.
     *
     * @param id
     *            The id for the resource role
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
     * Gets the id of the resource role. The return is either UNSET_ID_VALUE or > 0.
     *
     * @return The id of the resource role
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the name of the resource role. Any value, null or non-null is allowed
     *
     * @param name
     *            The name for the resource role.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the resource role. This method may return null or any other string value.
     *
     * @return The name of the resource role
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the description of the resource role. Any value, null or non-null is allowed
     *
     * @param description
     *            The description for the resource role.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the resource role. This method may return null or any other string value.
     *
     * @return The description of the resource role
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the phase type identifier for the ResourceRole.
     *
     * @param phaseType
     *            The identifier of the phase type, can be null
     * @throws IllegalArgumentException
     *             If phaseType is not null and <= 0
     */
    public void setPhaseType(Long phaseType) {
        Helper.checkLongValue(phaseType, "phaseType");

        this.phaseType = phaseType;
    }

    /**
     * Gets the phase type identifier for the ResourceRole. The value will either be > 0 or null.
     *
     * @return The phase type identifier
     */
    public Long getPhaseType() {
        return phaseType;
    }
}
