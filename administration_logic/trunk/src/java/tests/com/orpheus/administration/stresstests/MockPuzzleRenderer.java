/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import java.io.OutputStream;
import java.io.Writer;

import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleRenderer;
import com.topcoder.util.puzzle.SolutionTester;

/**
 * <p>
 * Mock implementation of <code>{@link com.topcoder.util.puzzle.PuzzleRenderer}</code> interface.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class MockPuzzleRenderer implements PuzzleRenderer {
    /**
     * <p>
     * Mock method.
     * </p>
     */
    public String getMediumName() {
        return "test";
    }
    /**
     * <p>
     * Mock method.
     * </p>
     */
    public SolutionTester renderPuzzle(PuzzleData puzzle, OutputStream target) {
        return new SolutionTester() {

            public boolean testSolution(Object solution) {
                return false;
            }
            
        };
    }
    /**
     * <p>
     * Mock method.
     * </p>
     */
    public SolutionTester renderPuzzle(PuzzleData puzzle, Writer target) {
        return new SolutionTester() {

            public boolean testSolution(Object solution) {
                return false;
            }
            
        };
    }
    /**
     * <p>
     * Mock method.
     * </p>
     */
    public void setAttribute(String name, Object value) {
    }

}
