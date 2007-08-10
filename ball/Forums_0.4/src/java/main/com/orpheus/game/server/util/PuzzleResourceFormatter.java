/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.topcoder.util.format.ObjectFormatter;
import com.topcoder.util.format.ObjectFormatMethod;
import com.topcoder.util.puzzle.PuzzleResource;

/**
 * <p>A custom <code>ObjectFormatter</code> to be used by <code>Jigsaw</code> puzzle implementation for formatting the
 * <code>PuzzleResource</code>. This implementation transforms the puzzle resource data into URL which could be used
 * for retrieving the content of the image corresponding to provided puzzle resource.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PuzzleResourceFormatter implements ObjectFormatter {

    /**
     * <p>Constructs new <code>PuzzleResourceFormatter</code> instance. This implementation does nothing.</p>
     */
    public PuzzleResourceFormatter() {
    }

    /**
     * <p>Gets the format method associated with a particular type. This implementation does nothing.</p>
     *
     * @param type the <code>Class</code> to query for an associated format method.
     * @return the format method associated with <code>type</code>, or <code>null</code> if none exists
     */
    public ObjectFormatMethod getFormatMethodForClass(Class type) {
        return null;
    }

    /**
     * <p>Sets the format method associated with a particular type (and possibly subtypes). This implementation does
     * nothing.</p>
     *
     * @param type the <code>Class</code> (and possibly subtypes) to be associated with this format method.
     * @param ofm the format method for this type (and possibly subtypes).
     * @param fFormatSubtypes <code>true</code> if subtypes of the supplied type should be formatted with this format
     *        method.
     */
    public void setFormatMethodForClass(Class type, ObjectFormatMethod ofm, boolean fFormatSubtypes) {
    }

    /**
     * <p>Removes the associated format method for a particular type. This implementation does nothing.</p>
     *
     * @param type the type whose associated format method should no longer apply to it.
     */
    public void unsetFormatMethodForClass(Class type) throws IllegalArgumentException {
    }

    /**
     * <p>Gets the format method that would be used to format the given object. This implementation does nothing.</p>
     *
     * @param obj an <code>Object</code> that might be formatted.
     * @return <code>null</code> always.
     */
    public ObjectFormatMethod getFormatMethodForObject(Object obj) {
        return null;
    }

    /**
     * <p>Converts the given <code>Object</code> into an appropriate string representation. The format method used is
     * the one that would be returned by <code>getFormatMethodForObject</code>. See the class javadoc above for details
     * on determining the appropriate format method.</p>
     *
     * @param obj an <code>Object</code> to be formatted.
     * @return a formatted string representing <code>obj</code> or <code>null</code> if specified object is <code>null
     *         </code>.
     */
    public String format(Object obj) throws IllegalArgumentException {
        if (obj == null) {
            return null;
        } else {
            PuzzleResource resource = (PuzzleResource) obj;
            return "/server/getPuzzleImage.do?puzzleResource=" + resource.getName();
        }
    }
}
