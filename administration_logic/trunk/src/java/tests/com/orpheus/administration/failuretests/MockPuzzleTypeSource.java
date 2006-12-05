/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.failuretests;

import com.topcoder.util.puzzle.PuzzleType;
import com.topcoder.util.puzzle.PuzzleTypeSource;

/**
 * <p>
 * A mock class that implements PuzzleTypeSource for testing purpose.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class MockPuzzleTypeSource implements PuzzleTypeSource {

    /**
     * <p>
     * Mock method that do nothing.
     * </p>
     *
     * @param name name of the puzzle type
     * @return the named PuzzleType
     */
    public PuzzleType getPuzzleType(String name) {
        return null;
    }

    /**
     * <p>
     * Mock method that do nothing.
     * </p>
     *
     * @param name name of the puzzle type
     * @return true if a puzzle type corresponding to the given name is available from this resource
     */
    public boolean hasPuzzleType(String name) {
        return false;
    }
}
