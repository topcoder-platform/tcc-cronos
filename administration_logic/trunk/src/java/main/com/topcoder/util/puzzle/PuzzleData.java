package com.topcoder.util.puzzle;

import java.util.Map;

/**
 * <p> An interface defining the behavior of puzzle data, which is a named collection of named attributes and resources. 
 * Attributes are simply name / value pairs, whereas resources are named blocks of binary data bearing appropriate MIME 
 * media types that describe the resource MIME type (for rendering purposes).  
 * 
 * PuzzleData implementations or usage environments may restrict attribute names, attribute values, and resource names to 
 * be relatively short strings, but details may vary.  Users are advised to keep these strings to less than 256 characters, but please 
 * note that it is not guaranteed to be a sufficiently restrictive upper bound for every environment.
 * </p>
 * 
 * 
 */
public interface PuzzleData {

    /**
     * <p>Represents the standard attribute name used to store a puzzle type's name among a puzzle's attributes.
     * </p>
     * 
     */
    public static final String PUZZLE_TYPE_ATTRIBUTE = "puzzle.type.attribute.NAME";

    /**
     * <p>Gets the name of the puzzle represented by this data.
     * </p>
     * 
     * 
     * @return name of the puzzle represented by this data
     */
    public String getName();

    /**
     * <p>Returns the value of the named attribute of this puzzle data, or null if this data has no attribute of the specified name.
     * </p>
     * 
     * 
     * @param name named of the attribute
     * @return attribute value for this name; null if not found
     */
    public String getAttribute(String name);

    /**
     * <p>Returns all the attributes of this puzzle data in the form of an unmodifiable Map from attribute names to attribute values.
     * </p>
     * 
     * 
     * @return unmodifiable Map which maps attribute names to attribute values
     */
    public Map getAllAttributes();

    /**
     * <p>Returns the named resource of this puzzle data, or null if  
     * this data has no resource of the specified name.
     * </p>
     * 
     * 
     * @param name resource name
     * @return PuzzleResource instance mapped to this name
     */
    public PuzzleResource getResource(String name);

    /**
     * <p>Returns all the resources of this puzzle data in the form of an unmodifiable Map from resource names to 
     * PuzzleResource objects.
     * </p>
     * 
     * 
     * @return unmodifiable map which maps resource names to PuzzleResource instances
     */
    public Map getAllResources();
}
