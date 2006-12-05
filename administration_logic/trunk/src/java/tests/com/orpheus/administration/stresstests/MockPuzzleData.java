/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleResource;

/**
 * <p>
 * Mock implementation of <code>{@link com.topcoder.util.puzzle.PuzzleData}</code> method.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class MockPuzzleData implements PuzzleData {

    /**
     * <p>
     * Mock method.
     * </p>
     */
    public String getName() {
        return "test";
    }

    /**
     * <p>
     * Mock method.
     * </p>
     */
    public String getAttribute(String name) {
        return "test";
    }

    /**
     * <p>
     * Mock method.
     * </p>
     */
    public Map getAllAttributes() {
        return new HashMap();
    }

    /**
     * <p>
     * Mock method.
     * </p>
     */
    public PuzzleResource getResource(String name) {
        return null;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     */
    public Map getAllResources() {
        return null;
    }

}
