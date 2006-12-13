/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.entities;

import com.orpheus.administration.entities.PuzzleTypeEnum;

import junit.framework.TestCase;

/**
 * <p>
 * Test the <code>PuzzleTypeEnum</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PuzzleTypeEnumAccuracyTests extends TestCase {
    /**
     * <p>
     * Tests that the PuzzleTypeEnum actually represents
     * an enumeration.
     * </p>
     * @throws Exception to JUnit
     */
    public void testEnumeration() throws Exception {
        assertEquals(PuzzleTypeEnum.JIGSAW, PuzzleTypeEnum.JIGSAW);
        assertEquals(PuzzleTypeEnum.LETTER_SCRAMBLE, PuzzleTypeEnum.LETTER_SCRAMBLE);
        assertEquals(PuzzleTypeEnum.MISSING_LETTER, PuzzleTypeEnum.MISSING_LETTER);
        assertEquals(PuzzleTypeEnum.SLIDING_TILE, PuzzleTypeEnum.SLIDING_TILE);
    }

    /**
     * <p>
     * Tests the equals method of the Enumeration.
     * A member of the Enumeration should be equal to
     * itself.
     * </p>
     * @throws Exception to JUnit
     */
    public void testEquals() throws Exception {
        assertTrue(PuzzleTypeEnum.JIGSAW.equals(PuzzleTypeEnum.JIGSAW));
        assertFalse(PuzzleTypeEnum.JIGSAW.equals(PuzzleTypeEnum.LETTER_SCRAMBLE));
        assertFalse(PuzzleTypeEnum.JIGSAW.equals(PuzzleTypeEnum.MISSING_LETTER));
        assertFalse(PuzzleTypeEnum.JIGSAW.equals(PuzzleTypeEnum.SLIDING_TILE));

        assertTrue(PuzzleTypeEnum.LETTER_SCRAMBLE.equals(PuzzleTypeEnum.LETTER_SCRAMBLE));
        assertFalse(PuzzleTypeEnum.LETTER_SCRAMBLE.equals(PuzzleTypeEnum.JIGSAW));
        assertFalse(PuzzleTypeEnum.LETTER_SCRAMBLE.equals(PuzzleTypeEnum.MISSING_LETTER));
        assertFalse(PuzzleTypeEnum.LETTER_SCRAMBLE.equals(PuzzleTypeEnum.SLIDING_TILE));

        assertTrue(PuzzleTypeEnum.MISSING_LETTER.equals(PuzzleTypeEnum.MISSING_LETTER));
        assertFalse(PuzzleTypeEnum.MISSING_LETTER.equals(PuzzleTypeEnum.JIGSAW));
        assertFalse(PuzzleTypeEnum.MISSING_LETTER.equals(PuzzleTypeEnum.LETTER_SCRAMBLE));
        assertFalse(PuzzleTypeEnum.MISSING_LETTER.equals(PuzzleTypeEnum.SLIDING_TILE));

        assertTrue(PuzzleTypeEnum.SLIDING_TILE.equals(PuzzleTypeEnum.SLIDING_TILE));
        assertFalse(PuzzleTypeEnum.SLIDING_TILE.equals(PuzzleTypeEnum.JIGSAW));
        assertFalse(PuzzleTypeEnum.SLIDING_TILE.equals(PuzzleTypeEnum.LETTER_SCRAMBLE));
        assertFalse(PuzzleTypeEnum.SLIDING_TILE.equals(PuzzleTypeEnum.MISSING_LETTER));
    }
}
