/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests;

import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleException;
import com.topcoder.util.puzzle.PuzzleGenerator;
import com.topcoder.util.puzzle.PuzzleRenderer;
import com.topcoder.util.puzzle.PuzzleType;

/**
 * Just a Mock class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPuzzleType implements PuzzleType {
    /**
     * mock
     * @return a mock class.
     */
    public PuzzleGenerator createGenerator() {
        return new MockPuzzleGenerator();
    }

    /**
     * mock
     * @return a mock class
     */
    public PuzzleRenderer createRenderer(String medium) throws PuzzleException {
        return new MockPuzzleRenderer();
    }

    /**
     * mock
     * @return the name
     */
    public String getName() {
        return null;
    }

    /**
     * mock
     * @return the string array.
     */
    public String[] getSupportedMedia() {
        return null;
    }

    /**
     * mock
     * @return the boolean
     */
    public boolean isPuzzleValid(PuzzleData puzzle) throws PuzzleException {
        return false;
    }

}
