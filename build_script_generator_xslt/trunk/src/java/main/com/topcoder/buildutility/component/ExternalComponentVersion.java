/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility.component;

/**
 * <p>
 * An ExternalComponentVersion is a data object that represents a dependency to a third-party component that was not
 * created by Topcoder and is not part of Topcoder's component catalog. It maintains a reference to some
 * meta-information such as id, name, description, that will help the developer have an idea of how the external
 * component plays a role in helping a component. It also maintains a reference to a fileName, which is the standard
 * file name of the external component.
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
public class ExternalComponentVersion {
    /**
     * <p>
     * This is the unique identifier that represents this external component. Each external component version should
     * have a different identifier. It is initialized in the constructor, accessed via getId, and changed afterwards.
     * </p>
     */
    private final long id;

    /**
     * <p>
     * This is the name of the external component. It is initialized in the constructor, accessed via getName, and
     * changed via setName. It may not be null or an empty String.
     * </p>
     */
    private String name;

    /**
     * <p>
     * This is the description of the external component. This is a String that contains human-readable text describing
     * the external component. It is initialized in the constructor, accessed via getDescription, and changed via
     * getDescription. It may be a null String, since its an optional property.
     * </p>
     */
    private String description;

    /**
     * <p>
     * This is the version of the external component. It is initialized in the constructor, accessed via getVersion, and
     * changed via setVersion.
     * </p>
     */
    private String version;

    /**
     * <p>
     * This is the filename by which the component regularly goes by. It is initialized in the constructor, accessed via
     * getFilename, and changed via setFilename.
     * </p>
     */
    private String filename;

    /**
     * <p>
     * Constructor that initializes the ExternalComponentVersion using the constructor properties.
     * </p>
     *
     * @param id the id
     * @param name the name
     * @param description the description
     * @param version the version
     * @param filename the filename
     */
    public ExternalComponentVersion(long id, String name, String description, String version, String filename) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.version = version;
        this.filename = filename;
    }

    /**
     * <p>
     * Retrieves the unique identifier that represents this external component.
     * </p>
     *
     * @return the unique identifier that represents this external component.
     */
    public Long getId() {
        return new Long(id);
    }

    /**
     * <p>
     * Retrieves the name of the external component.
     * </p>
     *
     * @return the name of the external component
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Retrieves the description of the external component. This attribute is optional, and null may be returned.
     * </p>
     *
     * @return the description of the external component.
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
     * Retrieves the filename by which the component regularly goes by.
     * </p>
     *
     * @return the filename by which the component regularly goes by.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * <p>
     * Sets the name of the external component.
     * </p>
     *
     * @param name the name of the external component.
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
     * Sets the filename by which the component regularly goes by.
     * </p>
     *
     * @param filename the filename by which the component regularly goes by.
     * @throws NullPointerException if the filename is null.
     * @throws IllegalArgumentException if the trimmed filename is an empty String.
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * <p>
     * An overridden equals method to indicate equality to other ExternalComponentVersion objects based on their
     * identifier. Another object is equal to this object if and only if the other object is also a
     * ExternalComponentVersion, and the id of both objects are equal.
     * </p>
     *
     * @return True if the two objects are equal, false otherwise.
     * @param o The object to compare equality to.
     */
    public boolean equals(Object o) {
        if (!(o instanceof ExternalComponentVersion)) {
            return false;
        }
        return ((ExternalComponentVersion) o).id == id;
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
