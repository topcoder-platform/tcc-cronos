/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * A bean that describes a technology used in a component competition situation. It has a name and description.
 * </p>
 *
 * <p>
 * It is Serializable and Cloneable.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is mutable but thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class Technology implements Serializable, Cloneable {
    /**
     * Serialization version UID.
     */
    private static final long serialVersionUID = -5632726003787256275L;

    /**
     * Represents the name of the technology, wrapped in an AtomicReference, so it can be accessed in an atomic,
     * thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be any value. The AtomicReference instance
     * will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<String> name = new AtomicReference<String>();

    /**
     * Represents the description of the technology, wrapped in an AtomicReference, so it can be accessed in an atomic,
     * thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be any value. The AtomicReference instance
     * will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<String> description = new AtomicReference<String>();

    /**
     * Default empty constructor.
     */
    public Technology() {
    }

    /**
     * Gets the name field value.
     *
     * @return the name field value
     */
    public String getName() {
        return this.name.get();
    }

    /**
     * Sets the name field value.
     *
     * @param name
     *            the name field value
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Gets the description field value.
     *
     * @return the description field value
     */
    public String getDescription() {
        return this.description.get();
    }

    /**
     * Sets the description field value.
     *
     * @param description
     *            the description field value
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Makes a deep copy of this technology.
     *
     * @return the clone of this technology
     */
    public Object clone() {
        Technology t = new Technology();
        t.setDescription(this.getDescription());
        t.setName(this.getName());
        return t;
    }
}
