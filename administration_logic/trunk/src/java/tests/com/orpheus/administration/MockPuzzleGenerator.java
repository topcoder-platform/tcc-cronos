/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleException;
import com.topcoder.util.puzzle.PuzzleGenerator;

/**
 * <p>
 * Mock implementation of <code>{@link com.topcoder.util.puzzle.PuzzleGenerator}</code> interface.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class MockPuzzleGenerator implements PuzzleGenerator {
    /**
     * <p>
     * Mock method.
     * </p>
     */
    public PuzzleData generatePuzzle() throws PuzzleException {
        return new MockPuzzleData();
    }

    /**
     * <p>
     * Mock method.
     * </p>
     */
    public PuzzleData[] generatePuzzleSeries(int size) throws PuzzleException {
        PuzzleData[] data = new PuzzleData[size];
        for (int i = 0; i < data.length; i++) {
            data[i] = new MockPuzzleData();
        }
        return data;
    }

    /**
     * <p>
     * Mock method.
     * </p>
     */
    public void setAttribute(String name, Object value) throws PuzzleException {
    }

}
