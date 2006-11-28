/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.util.puzzle;

import java.util.Map;

/**
 * An interface defining the behavior of puzzle data, which is a named collection of named attributes and
 * resources.  Attributes are simply name / value pairs, whereas resources are named blocks of binary data bearing
 * appropriate MIME media types that.  PuzzleData implementations or usage environments may restrict attribute
 * names, attribute values, and resource names to be relatively short strings, but details may vary.  Users are
 * advised to keep these strings to less than 256 characters, though that is not guaranteed to be a sufficiently
 * restrictive upper bound for every environment.
 *
 * @author TopCoder, TCSDEVELOPER
 * @version 1.0
 */

public interface PuzzleData {
    /**
     * The standard attribute name used to store a puzzle type's name among a puzzle's attributes.
     */
    public static final String PUZZLE_TYPE_ATTRIBUTE = "PuzzleType";

    /**
     * Gets the name of the puzzle represented by this data.
     *
     * @return the name of the puzzle represented by this data
     */
    public String getName();

    /**
     * Returns the value of the named attribute of this puzzle data, or <code>null</code> if this data has no
     * attribute of the specified name.
     *
     * @param name the name of the attribute to retrieve
     * @return the value of the named attribute of this puzzle data, or <code>null</code> if this data has no
     *   attribute of the specified name
     */
    public String getAttribute(String name);

    /**
     * Returns all the attributes of this puzzle data in the form of an unmodifiable <code>Map</code> from
     * attribute names to attribute values.
     *
     * @return all the attributes of this puzzle data in the form of an unmodifiable <code>Map</code> from
     *   attribute names to attribute values
     */
    public Map getAllAttributes();

    /**
     * Returns the named resource of this puzzle data, or <code>null</code> if this data has no resource of the
     * specified name.
     *
     * @param name the name of the resource to retrieve
     * @return the named resource of this puzzle data, or <code>null</code> if this data has no resource of the
     *   specified name
     */
    public PuzzleResource getResource(String name);

    /**
     * Returns all theresources of this puzzle data in the form of an unmodifiable <code>Map</code> from resource
     * names to <code>PuzzleResource</code> objects.
     *
     * @return all theresources of this puzzle data in the form of an unmodifiable <code>Map</code> from resource
     *   names to <code>PuzzleResource</code> objects
     */
    public Map getAllResources();
}
