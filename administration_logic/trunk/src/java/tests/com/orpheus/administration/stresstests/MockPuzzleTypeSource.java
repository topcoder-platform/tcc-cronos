/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleException;
import com.topcoder.util.puzzle.PuzzleGenerator;
import com.topcoder.util.puzzle.PuzzleRenderer;
import com.topcoder.util.puzzle.PuzzleType;
import com.topcoder.util.puzzle.PuzzleTypeSource;

/**
 * <p>
 * Mock implementation of <code>{@link com.topcoder.util.puzzle.PuzzleTypeSource}</code> method.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class MockPuzzleTypeSource implements PuzzleTypeSource {

    /**
     * <p>
     * Mock method.
     * </p>
     */
    public PuzzleType getPuzzleType(String name) {
        return new PuzzleType() {

            public String getName() {
                return "mock";
            }

            public PuzzleGenerator createGenerator() {
                return new MockPuzzleGenerator();
            }

            public PuzzleRenderer createRenderer(String medium) throws PuzzleException {
                return new MockPuzzleRenderer();
            }

            public String[] getSupportedMedia() {
                return null;
            }

            public boolean isPuzzleValid(PuzzleData puzzle) throws PuzzleException {
                return false;
            }
        };
    }

    /**
     * <p>
     * Mock method.
     * </p>
     */
    public boolean hasPuzzleType(String name) {
        return false;
    }

}
