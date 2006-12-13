/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests;

import com.topcoder.util.puzzle.PuzzleType;
import com.topcoder.util.puzzle.PuzzleTypeSource;
/**
 * Just a Mock class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPuzzleTypeSource implements PuzzleTypeSource {
    /**
     * Just a mock.
     * @param name just a stub
     * @return just a stub
     */
    public PuzzleType getPuzzleType(String name) {
        return new MockPuzzleType();
    }
    /**
     * Just a mock.
     * @param name just a stub
     * @return just a stub
     */
    public boolean hasPuzzleType(String name) {
        return false;
    }
}
