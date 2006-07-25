/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * <p>
 * The NamedDeliverableStructure class extends the AuditedDeliverableStructure
 * class to hold a name and description.
 * </p>
 * <p>
 * Like AuditedDeliverableStructure, it is an abstract class. The
 * NamedDeliverableStructure class is simply a container for the name and
 * description. Both these data fields have the getters and setters.
 * </p>
 * <p>
 * Thread Safety: This class is highly mutable and NOT thread safe. All fields
 * can be changed.
 * </p>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public abstract class NamedDeliverableStructure extends AuditedDeliverableStructure {

    /**
     * name: The name of the structure. This field is set in the constructor and
     * is mutable. Any value is allowed for this field. It can be null or
     * non-null, and any value is allowed when non-null (empty string, all
     * whitespace, etc). The default value of this field is null, which
     * indicates that the name has not yet been set (or has been unset). This
     * field is set in the constructor and the setName method and is retrieved
     * through the getName method.
     */
    private String name = null;

    /**
     * description: The description of the structure. This field is set in the
     * constructor and is mutable. Any value is allowed for this field. It can
     * be null or non-null, and any value is allowed when non-null (empty
     * string, all whitespace, etc). The default value of this field is null,
     * which indicates that the name has not yet been set (or has been unset).
     * This field is set in the constructor and setDescription method and is
     * retrieved through the getDescription method.
     */
    private String description = null;

    /**
     * Creates a new NamedDeliverableStructure, initializing the name field to
     * null.
     */
    protected NamedDeliverableStructure() {
    }

    /**
     * Creates a new NamedDeliverableStructure, passing the id parameter to the
     * base class constructor and initializing the name field to null.
     * @param id
     *            The id of the structure
     * @throws IllegalArgumentException
     *             If id is <= 0
     */
    protected NamedDeliverableStructure(long id) {
        super(id);
    }

    /**
     * Creates a new NamedDeliverableStructure, initializing the name field to
     * the given value.
     * @param id
     *            The id of the structure
     * @param name
     *            The name of the structure
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @throws IllegalArgumentException
     *             If name is null
     */
    protected NamedDeliverableStructure(long id, String name) {
        super(id);

        Helper.assertObjectNotNull(name, "name");
        this.name = name;
    }

    /**
     * Sets the name of the structure. Any value, null or non-null is allowed.
     * @param name
     *            The name for the structure
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the structure. This method may return null or any other
     * string value.
     * @return The name of the structure
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the description of the structure. Any value, null or non-null is
     * allowed.
     * @param description
     *            The description for the structure
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the structure. This method may return null or any
     * other string value.
     * @return The description of the structure
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Tells whether all the required fields of this structure have values set.
     * </p>
     * <p>
     * This method returns true only all of the following are true:
     * super.isValidToPersist returns true, name is not null, description is not
     * null.
     * </p>
     * @return True if all fields required for persistence are present
     */
    public boolean isValidToPersist() {
        if (super.isValidToPersist() && name != null && description != null) {
            return true;
        } else {
            return false;
        }
    }
}
