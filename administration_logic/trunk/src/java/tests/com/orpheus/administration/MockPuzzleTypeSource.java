/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import com.orpheus.administration.stresstests.MockPuzzleGenerator;
import com.orpheus.administration.stresstests.MockPuzzleRenderer;
import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleException;
import com.topcoder.util.puzzle.PuzzleGenerator;
import com.topcoder.util.puzzle.PuzzleRenderer;
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
     * Just a mock.
     * @param name just a stub
     * @return just a stub
     */
    public boolean hasPuzzleType(String name) {

        return false;
    }

}
