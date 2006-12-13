/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests;

import java.io.OutputStream;
import java.io.Writer;

import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleRenderer;
import com.topcoder.util.puzzle.SolutionTester;

/**
 * Mock class
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPuzzleRenderer implements PuzzleRenderer {
    /**
     * Mock
     * @return name
     */
    public String getMediumName() {
        return null;
    }

    /**
     * Mock
     * @param  puzzle the puzzle
     * @param target the target
     * @return the null
     */
    public SolutionTester renderPuzzle(PuzzleData puzzle, OutputStream target) {
        return null;
    }

    /**
     * Mock
     * @param  puzzle the puzzle
     * @param target the target
     * @return the null
     */
    public SolutionTester renderPuzzle(PuzzleData puzzle, Writer target) {
        return null;
    }

    /**
     * Mock
     * @param name the puzzle
     * @param value the target
     */
    public void setAttribute(String name, Object value) {

    }

}
