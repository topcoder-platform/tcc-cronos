/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

/**
 * <p>
 * The NotificationType class is the third modeling class in this component. It represents a type of notification. It is
 * orthogonal to the Resource and ResourceRole classes. This class is simply a container for a few basic data fields.
 * All data fields in this class are mutable and have get and set methods.
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
public class NotificationType extends AuditableResourceStructure {
    /**
     * The value that the id field will have (and that the getId method will return) when the id field has not been set
     * in the constructor or through the setId method.
     *
     */
    public static final long UNSET_ID = -1;

    /**
     * The id of the NotificationType.
     *
     * <p>
     * This field is set in the constructor and is mutable. The value must always be > 0 or UNSET_ID. The default value
     * is UNSET_ID. Once set to a value besides UNSET_ID, the id can not be set to another value. This allows the class
     * to have a Java Bean API but still have an essentially unchangeable id.
     * </p>
     *
     * This field is set in the constructor and setId method and is retrieved through the getId method.
     *
     */
    private long id = UNSET_ID;

    /**
     * The name of the NotificationType.
     *
     * <p>
     * This field is set in the constructor and is mutable. Any value is allowed for this field. It can be null or
     * non-null, and any value is allowed when non-null (empty string, all whitespace, etc). The default value of this
     * field is null, which indicates that the name has not yet been set (or has been unset).
     * </p>
     *
     * This field is set in the constructor and the setName method and is retrieved through the getName method.
     *
     */
    private String name = null;

    /**
     * The description of the NotificationType.
     *
     * <p>
     * This field is set in the constructor and is mutable. Any value is allowed for this field. It can be null or
     * non-null, and any value is allowed when non-null (empty string, all whitespace, etc). The default value of this
     * field is null, which indicates that the name has not yet been set (or has been unset).
     * </p>
     *
     * This field is set in the constructor and setDescription method and is retrieved through the getDescription
     * method.
     *
     */
    private String description = null;

    /**
     * Creates a new NotificationType instance.
     */
    public NotificationType() {
        // empty.
    }

    /**
     * Create a new NotificationType with id.
     *
     * @param id
     *            The id for the notification type
     * @throws IllegalArgumentException
     *             If id is <= 0
     */
    public NotificationType(long id) {
        setId(id);
    }

    /**
     * Creates a new NotificationType instance with id and name.
     *
     * @param id
     *            The id for the notification type
     * @param name
     *            The name for the notification type.
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @throws IllegalArgumentException
     *             If name is null
     */
    public NotificationType(long id, String name) {
        Helper.checkNull(name, "name");
        this.name = name;

        setId(id);
    }

    /**
     * Sets the id of the notification type. The setId method only allows the id to be set once. After that the id value
     * is locked in and can not be changed.
     *
     * @param id
     *            The id for the notification type
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @throws IdAlreadySetException
     *             If the id has already been set (i.e. the id field is not UNSET_ID)
     */
    public void setId(long id) {
        Helper.checkPositiveValue(id, "id");

        if (this.id != UNSET_ID) {
            throw new IdAlreadySetException("The id has been set already.");
        }

        this.id = id;
    }

    /**
     * Gets the id of the notification type. The return is either UNSET_ID_VALUE or > 0.
     *
     * @return The id of the notification type
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the description of the notification type. Any value, null or non-null is allowed
     *
     * @param description
     *            The description for the notification type
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the notification type. This method may return null or any other string value.
     *
     * @return The description of the notification type
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the name of the notification type. Any value, null or non-null is allowed
     *
     * @param name
     *            The name for the notification type.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the notification type. This method may return null or any other string value.
     *
     * @return The name of the notification type
     */
    public String getName() {
        return name;
    }
}
