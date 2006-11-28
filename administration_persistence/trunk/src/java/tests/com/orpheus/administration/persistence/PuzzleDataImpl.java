/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleResource;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>An implementation of <code>PuzzleData</code> that retrieves attributes and resources from <code>Map</code>
 * instances passed to the constructor.</p>
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and thread safe.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class PuzzleDataImpl implements PuzzleData, Serializable {
    /**
     * Mapping from non-<code>null</code> strings to non-<code>null</code> serializable attribute values. This map
     * is initialized in the constructor, is immutable, and cannot be modified.
     */
    private final Map attributes;

    /**
     * Mapping from non-<code>null</code> strings to non-<code>null</code> serializable {@link PuzzleResource
     * PuzzleResource} values. This map is initialized in the constructor, is immutable, and cannot be modified.
     */
    private final Map resources;

    /**
     * Creates a new <code>PuzzleDataImpl</code> based on the specified attribute and resource maps. The
     * constructor makes a copy of the maps, so any subsequent changes made by the caller will not affect this
     * <code>PuzzleDataImpl</code>.
     *
     * @param attributes the attribute map whose keys are non-<code>null</code> non-empty strings and whose values
     *   are non-<code>null</code>{@link Serializable Serializable} objects of any type
     * @param resources the resource map whose keys are non-<code>null</code> non-empty strings and whose values
     *   are non-<code>null</code> {@link Serializable Serializable} {@link PuzzleResource puzzle resources}
     * @throws IllegalArgumentException if either argument is <code>null</code>
     * @throws IllegalArgumentException if any key in either map is not a non-empty <code>String</code>
     * @throws IllegalArgumentException if any value in either map is <code>null</code>
     * @throws IllegalArgumentException if any value in either map is not serializable
     * @throws IllegalArgumentException if any value in <code>resources</code> is not an instance of {@link
     *   PuzzleResource PuzzleResource}
     */
    public PuzzleDataImpl(Map attributes, Map resources) {
        if (attributes == null) {
            throw new IllegalArgumentException("the attribute map must not be null");
        }

        if (resources == null) {
            throw new IllegalArgumentException("the resource map must not be null");
        }

        for (Iterator it = attributes.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            checkKey(entry.getKey(), "attribute map");
            checkValue(entry.getValue(), "attribute map");
        }

        for (Iterator it = resources.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            checkKey(entry.getKey(), "resource map");

            Object value = entry.getValue();

            checkValue(value, "resource map");

            if (!(value instanceof PuzzleResource)) {
                throw new IllegalArgumentException("resource map values must be PuzzleResource instances");
            }
        }

        this.attributes = Collections.unmodifiableMap(new HashMap(attributes));
        this.resources = Collections.unmodifiableMap(new HashMap(resources));
    }

    /**
     * Returns the value of {@link #PUZZLE_TYPE_ATTRIBUTE PUZZLE_TYPE_ATTRIBUTE} attribute, or <code>null</code> if
     * this attribute does not exist. Equivalent to {@link #getAttribute(String)
     * getAttribute(PUZZLE_TYPE_ATTRIBUTE)}.
     *
     * @return the value of <code>PUZZLE_TYPE_ATTRIBUTE</code> attribute, or <code>null</code> if this attribute
     *   does not exist
     */
    public String getName() {
        return getAttribute(PUZZLE_TYPE_ATTRIBUTE);
    }

    /**
     * Returns the string representation of the value of the named attribute, or <code>null</code> if the attribute
     * has no value.
     *
     * @param name the name of the attribute to retrieve
     * @return the string representation of the value of the named attribute, or <code>null</code> if the attribute
     *   has no value
     * @throws IllegalArgumentException if <code>name</code> is <code>null</code> or an empty string
     */
    public String getAttribute(String name) {
        if (name == null) {
            throw new IllegalArgumentException("attribute name must not be null");
        }

        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("attribute name must not be an empty string");
        }

        Object attribute = attributes.get(name);

        return (attribute != null ? attribute.toString() : null);
    }

    /**
     * Returns an unmodifiable <code>Map</code> of all attributes.
     *
     * @return an unmodifiable <code>Map</code> of all attributes
     */
    public Map getAllAttributes() {
        return this.attributes;
    }

    /**
     * Returns the named resource, or <code>null</code> if no such resource exists.
     *
     * @param name the name of the resource to retrieve
     * @return the named resource, or <code>null</code> if no such resource exists
     * @throws IllegalArgumentException if <code>name</code> is <code>null</code> or an empty string
     */
    public PuzzleResource getResource(String name) {
        if (name == null) {
            throw new IllegalArgumentException("resource name must not be null");
        }

        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("resource name must not be an empty string");
        }

        return (PuzzleResource) resources.get(name);
    }

    /**
     * Returns an unmodifiable <code>Map</code> of all resources.
     *
     * @return an unmodifiable <code>Map</code> of all resources
     */
    public Map getAllResources() {
        return this.resources;
    }

    /**
     * Helper method that checks the map keys for validity.
     *
     * @param key the key to check for validity
     * @param map the name of the map
     * @throws IllegalArgumentException if <code>key</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>key</code> is not a <code>String</code>
     * @throws IllegalArgumentException if <code>key</code> is an empty string
     */
    private static void checkKey(Object key, String map) {
        if (key == null) {
            throw new IllegalArgumentException(map + " keys must not be null");
        }

        if (!(key instanceof String)) {
            throw new IllegalArgumentException(map + " keys must be String instances");
        }

        if (((String) key).trim().length() == 0) {
            throw new IllegalArgumentException(map + " keys must not be empty strings");
        }
    }

    /**
     * Helper method that checks the map values for validity.
     *
     * @param value the value to check for validity
     * @param map the map name
     * @throws IllegalArgumentException if <code>value</code> if <code>null</code>
     * @throws IllegalArgumentException if <code>value</code> is not serializable
     */
    private static void checkValue(Object value, String map) {
        if (value == null) {
            throw new IllegalArgumentException(map + " values must be non-null");
        }

        if (!(value instanceof Serializable)) {
            throw new IllegalArgumentException(map + " values must be serializable");
        }
    }
}
