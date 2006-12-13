/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleResource;

/**
 * Mock class
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPuzzleData implements PuzzleData {
    /**
     * Mock
     */
    private Map attributes = new HashMap();

    /**
     * Mock
     * @return the map
     */
    public Map getAllAttributes() {
        return attributes;
    }

    /**
     * Get all resourcce.
     * @return the map.
     */
    public Map getAllResources() {
        return null;
    }

    /**
     * Get the value
     * @param name the name
     * @return the value of the name
     */
    public String getAttribute(String name) {
        return this.attributes.get(name).toString();
    }

    /**
     * Mock
     * @param name the name
     * @param value the value
     * @return the value of the name
     */
    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    /**
     * return the name
     * @return the name
     */
    public String getName() {
        return null;
    }

    /**
     * return the null
     * @param name the name
     * @return the null.
     */
    public PuzzleResource getResource(String name) {
        return null;
    }
}
