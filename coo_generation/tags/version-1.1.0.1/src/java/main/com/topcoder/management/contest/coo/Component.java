/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

/**
 * <p>
 * Represents a component. A component has the following properties:
 * <li> component name</li>
 * <li> component version</li>
 * <li> component description</li>
 * <li> the URL of the component</li>
 * <li> the license of the component</li>
 * </p>
 * <p>
 * This class is used by <code>ComponentDependency</code> class to represent the dependent
 * component.
 * </p>
 * <p>
 * <strong>Thread safe:</strong> This class is not thread safe since it is
 * mutable.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public class Component {
    /**
     * Represents the component name. can be null or empty.
     */
    private String name;
    /**
     * Represents the component version. can be null or empty.
     */
    private String version;
    /**
     * Represents the component description. can be null or empty.
     *
     */
    private String description;
    /**
     * Represents the URL of the component. can be null or empty.
     */
    private String url;
    /**
     * Represents the license of the component. can be null or empty.
     *
     */
    private String license;

    /**
     * Represents the full name of the component. can be null or empty.
     */
    private String fullName;

    /**
     * Empty constructor.
     */
    public Component() {
    }

    /**
     * Getter for the name field.
     *
     * @return The value of the name field.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name field. can be null or empty.
     *
     * @param name The component name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the version field.
     *
     * @return The value of the version field.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Setter for the version field. can be null or empty.
     *
     * @param version The component version.
     *
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Getter for the description field.
     *
     * @return The value of the description field.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description field. can be null or empty.
     *
     * @param description The component description.
     *
     *
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the the URL field.
     *
     * @return The value of the the URL field.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter for the URL field. can be null or empty.
     *
     * @param url The component URL.
     *
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter for the license field.
     *
     * @return The value of the license field.
     */
    public String getLicense() {
        return license;
    }

    /**
     * Setter for the license field. can be null or empty.
     *
     * @param license The component license.
     *
     */
    public void setLicense(String license) {
        this.license = license;
    }

    /**
     * Getter for the fullName field.
     *
     * @return The value of the fullName field.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Setter for the fullName field. can be null or empty.
     * @param fullName The component fullName.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
