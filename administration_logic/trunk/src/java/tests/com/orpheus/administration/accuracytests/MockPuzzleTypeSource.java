/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests;

import com.topcoder.util.puzzle.PuzzleType;
import com.topcoder.util.puzzle.PuzzleTypeSource;
/**
 * Just a Mock class.
 * @author KKD
 * @version 1.0
 */
public class MockPuzzleTypeSource implements PuzzleTypeSource {
    /**
     * Just a mock.
     * @param name just a stub
     * @return just a stub
     */
    public PuzzleType getPuzzleType(String name) {
        return null;
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
