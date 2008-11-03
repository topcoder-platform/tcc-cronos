/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This enum represents the catalog, that can be either java or .net.
 * </p>
 * <p>
 * We won't specify how each enum name is mapped to xml schema so enum names will be mapped as enum values.
 * </p>
 * <p>
 * <b>Thread Safety:</b> this class is immutable so thread-safe.
 * </p>
 *
 * @author Margarita, TCSDEVELOPER
 * @version 1.0
 */
@XmlRootElement(name = "catalog")
@XmlType
@XmlEnum(String.class)
public enum ConfluenceCatalog {

    /**
     * <p>
     * This field corresponds to .net catalog.
     * </p>
     */
    DOT_NET(".NET"),

    /**
     * <p>
     * This field corresponds to java catalog.
     * </p>
     */
    JAVA("Java");

    /**
     * <p>
     * This field stores the string name associated with the current enum instance.
     * </p>
     * <p>
     * It is initialized in the constructor and never changed after that.
     * </p>
     * <p>
     * cannot be null.
     * </p>
     * <p>
     * accessed through getter
     * </p>
     */
    private final String stringName;

    /**
     * <p>
     * creates the confluence catalog instance with given name.
     * </p>
     *
     * @param stringName
     *            the string name for this enum instance
     */
    private ConfluenceCatalog(String stringName) {
        this.stringName = stringName;
    }

    /**
     * <p>
     * This method returns string value of the enum item.
     * </p>
     *
     * @return string value associated with the enum item
     */
    public String getStringName() {
        return stringName;
    }
}
