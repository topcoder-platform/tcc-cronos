/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility.component;

/**
 * <p>
 * This represents a technology category to which a component can belong to. A component that contains a specific
 * TechnologyType means that the given component is associated with the technology being described by the said
 * TechnologyType.
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
public class TechnologyType {
    /**
     * <p>
     * This is the name of the technology type. It is initialized in the constructor, accessed via getName, and changed
     * via setName. It may not be null or an empty String.
     * </p>
     */
    private String name;

    /**
     * <p>
     * This is a String that contains human-readable text describing the technology type. It is initialized in the
     * constructor, accessed via getDescription, and changed via getDescription. It may be a null String, since its an
     * optional property.
     * </p>
     */
    private String description;

    /**
     * <p>
     * This is the unique identifier that represents this technology type. Each technology type should have a different
     * identifier. It is initialized in the constructor, accessed via getId, and changed afterwards.
     * </p>
     */
    private final long id;

    /**
     * <p>
     * This is a boolean that indicates whether a technology type has been deprecated or not. It is initialized in the
     * cosntructor, accessed via isDeprecated, and modified via setDeprecated.
     * </p>
     */
    private boolean deprecatedStatus;

    /**
     * <p>
     * Constructor that initializes the TechnologyType using the constructor parameter arguments.
     * </p>
     *
     * @param id The unique identifier of the TechnologyType
     * @param name the name of the TechnologyType.
     * @param description the description of the TechnologyType.
     * @param deprecated whether a technology type has been deprecated or not.
     * @throws NullPointerException if name is null.
     * @throws IllegalArgumentException if trimmed name is an empty String.
     */
    public TechnologyType(long id, String name, String description, boolean deprecated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deprecatedStatus = deprecated;
    }

    /**
     * <p>
     * Retrieves the unique identifier that represents this technology type.
     * </p>
     *
     * @return the unique identifier that represents this technology type.
     */
    public Long getId() {
        return new Long(id);
    }

    /**
     * <p>
     * Retrieves the name of the TechnologyType.
     * </p>
     *
     * @return the name of the TechnologyType.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Retrieves the description of the TechnologyType.
     * </p>
     *
     * @return the description of the TechnologyType.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Indicates whether a technology type has been deprecated or not. True if it is deprecated, false otherwise.
     * </p>
     *
     * @return True if it is deprecated, false otherwise.
     */
    public boolean isDeprecated() {
        return deprecatedStatus;
    }

    /**
     * <p>
     * Sets the name of the TechnologyType.
     * </p>
     *
     * @param name the name of the TechnologyType.
     * @throws NullPointerException if the name is null.
     * @throws IllegalArgumentException if the trimmed name is an empty String.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Sets the description of the TechnologyType. It may be null since this is an optional property.
     * </p>
     *
     * @param description the description of the TechnologyType.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Sets whether a technology type has been deprecated or not. A True indicates that it is deprecated, false
     * indicates otherwise.
     * </p>
     *
     * @param deprecated whether a technology type has been deprecated or not.
     */
    public void setDeprecated(boolean deprecated) {
        this.deprecatedStatus = deprecated;
    }

    /**
     * <p>
     * An overridden equals method to indicate equality to other TechnologyType objects based on their identifier.
     * Another object is equal to this object if and only if the other object is also a TechnologyType, and the id of
     * both objects are equal.
     * </p>
     *
     * @return true if the two objects are equal; false otherwise.
     * @param o The object to compare this to.
     */
    public boolean equals(Object o) {
        if (!(o instanceof TechnologyType)) {
            return false;
        }
        return ((TechnologyType) o).id == id;
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
