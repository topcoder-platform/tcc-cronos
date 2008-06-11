/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

/**
 * <p>
 * This class is a container for information that can identify some component. Currently all components are identified
 * with name, version and programming language.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because it has mutable attributes. The user must synchronize all calls
 * to its methods to use it in thread safe manner.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class Component {
    /**
     * <p>
     * The name of the component. Is initialized in the constructor. Cannot be null or empty. Has corresponding getter
     * and setter.
     * </p>
     */
    private String name;

    /**
     * <p>
     * The version of the component. Is initialized in the constructor. Cannot be null. Can be empty if not defined. Has
     * corresponding getter and setter.
     * </p>
     */
    private String version;

    /**
     * <p>
     * The programming language of the component. Is initialized in the constructor. Cannot be null. Has corresponding
     * getter and setter.
     * </p>
     */
    private ComponentLanguage language;

    /**
     * <p>
     * Creates an instance of Component with the given name, version and language.
     * </p>
     *
     * @param name the name of the component (cannot be null or empty)
     * @param version the version of the component (cannot be null)
     * @param language the programming language of the component (cannot be null)
     *
     * @throws IllegalArgumentException if name, version or language is null or name is empty
     */
    public Component(String name, String version, ComponentLanguage language) {
        Utils.checkStringNullOrEmpty(name, "name");
        Utils.checkNull(version, "version");
        Utils.checkNull(language, "language");
        this.name = name;
        this.version = version;
        this.language = language;
    }

    /**
     * <p>
     * Retrieves the name of the component.
     * </p>
     *
     * @return the name of the component (cannot be null or empty)
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>
     * Retrieves the version of the component.
     * </p>
     *
     * @return the version of the component (cannot be null)
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * <p>
     * Retrieves the language of the component.
     * </p>
     *
     * @return the programming language of the component (cannot be null)
     */
    public ComponentLanguage getLanguage() {
        return this.language;
    }

    /**
     * <p>
     * Sets the name of the component.
     * </p>
     *
     * @param name the name of the component (cannot be null or empty)
     *
     * @throws IllegalArgumentException if name is null or empty
     */
    public void setName(String name) {
        Utils.checkStringNullOrEmpty(name, "name");
        this.name = name;
    }

    /**
     * <p>
     * Sets the version of the component.
     * </p>
     *
     * @param version the version of the component (cannot be null)
     *
     * @throws IllegalArgumentException if version is null
     */
    public void setVersion(String version) {
        Utils.checkNull(version, "version");
        this.version = version;
    }

    /**
     * <p>
     * Sets the language of the component.
     * </p>
     *
     * @param language the programming language of the component (cannot be null)
     *
     * @throws IllegalArgumentException if language is null
     */
    public void setLanguage(ComponentLanguage language) {
        Utils.checkNull(language, "language");
        this.language = language;
    }
}
