/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests;

import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleException;
import com.topcoder.util.puzzle.PuzzleGenerator;

/**
 * Mock class
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPuzzleGenerator implements PuzzleGenerator {
    /**
     * mock
     */
    private MockPuzzleData data = new MockPuzzleData();

    /**
     * mock
     * @return the data.
     */
    public PuzzleData generatePuzzle() throws PuzzleException {
        return data;
    }

    /**
     * mock
     * @return the array of data.
     * @param size the size;
     */
    public PuzzleData[] generatePuzzleSeries(int size) throws PuzzleException {
        return new PuzzleData[] {data};
    }

    /**
     * mock
     * @param name the name
     * @param value the value
     */
    public void setAttribute(String name, Object value) throws PuzzleException {
        data.setAttribute(name, value);
    }

}
