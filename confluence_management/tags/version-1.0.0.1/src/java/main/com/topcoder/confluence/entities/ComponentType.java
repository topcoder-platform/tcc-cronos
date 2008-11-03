/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This enum represents the component type, that can be either custom or generic.
 * </p>
 * <p>
 * We won't specify how each enum name is mapped to XML schema so enum names will be mapped as enum values.
 *</p>
 * <p>
 * <b>Thread Safety:</b> this class is immutable so thread-safe.
 * </p>
 *
 * @author Margarita, TCSDEVELOPER
 * @version 1.0
 */
@XmlRootElement(name = "componentType")
@XmlType
@XmlEnum(String.class)
public enum ComponentType {

    /**
     * <p>
     * This field corresponds to custom type of component.
     * </p>
     */
    CUSTOM("Custom"),

    /**
     * <p>
     * This field corresponds to generic type of component.
     * </p>
     */
    GENERIC("Generic");

    /**
     * <p>
     * This field stores the string name associated with the current enum instance. It is initialized in the
     * constructor and never changed after that.
     * </p>
     * <p>
     * Cannot be null.
     * </p>
     * <p>
     * Accessed through getter.
     * </p>
     */
    private final String stringName;

    /**
     *<p>
     * Creates the component type instance with given name.
     * </p>
     *
     * @param stringName
     *            the string name for this enum instance
     */
    private ComponentType(String stringName) {
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
