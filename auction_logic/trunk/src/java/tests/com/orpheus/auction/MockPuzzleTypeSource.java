/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import com.topcoder.util.puzzle.PuzzleType;
import com.topcoder.util.puzzle.PuzzleTypeSource;

/**
 * <p>
 * A mock implementation of the PuzzleTypeSource interface. Allows to have instances of the
 * PuzzleTypeSource type.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPuzzleTypeSource implements PuzzleTypeSource {

    public boolean hasPuzzleType(String puzzleType) {
	return false;
    }

    public PuzzleType getPuzzleType(String typeName) {
	return null;
    }

}
