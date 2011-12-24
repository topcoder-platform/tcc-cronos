/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.model;

import java.io.Serializable;

/**
 * <p>
 * The {@code MapEntry} class represents a single entry of a string map.
 * It is used as a value type by {@code MapAdapter}.
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is mutable and not thread safe.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public class MapEntry implements Serializable {
    /**
     * The class's serialVersionUID for the serialization runtime.
     */
    private static final long serialVersionUID = 3551190441934682497L;

    /**
     * The key of the map entry.
     * Can be any value. Has getter and setter.
     */
    private String key;

    /**
     * The value of the map entry.
     * Can be any value. Has getter and setter.
     */
    private String value;

    /**
     * Constructs new {@code MapEntry} instance with fields initialized to {@code null}.
     */
    public MapEntry() {
        // Empty
    }

    /**
     * Constructs new {@code MapEntry} instance using a given key and value to initialize this instance.
     *
     * @param value
     *              the value of the map entry
     * @param key
     *              the key of the map entry
     */
    public MapEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Retrieves the key of the map entry.
     *
     * @return the key of the map entry
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key of the map entry.
     *
     * @param key
     *              the key of the map entry
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Retrieves the value of the map entry.
     *
     * @return the value of the map entry
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the map entry.
     *
     * @param value
     *              the value of the map entry
     */
    public void setValue(String value) {
        this.value = value;
    }
}
