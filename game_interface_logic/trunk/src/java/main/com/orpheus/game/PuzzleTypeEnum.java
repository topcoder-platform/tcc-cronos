/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * This provides an enumeration of puzzle types used in this component. The
 * class name has been suffixed with 'Enum' to avoid confusion with PuzzleType
 * interface defined in the Puzzle Framework component.<br/> Thread-Safety:
 * This class is immutable and hence thread-safe.
 *
 * @author TCSDESIGNER, KKD
 * @version 1.0
 */
public class PuzzleTypeEnum extends Enum {

    /**
     * denotes the jigsaw puzzle type.
     *
     */
    public static final PuzzleTypeEnum JIGSAW = new PuzzleTypeEnum();

    /**
     * denotes the sliding tile puzzle type.
     *
     */
    public static final PuzzleTypeEnum SLIDING_TILE = new PuzzleTypeEnum();

    /**
     * denotes the missing letter puzzle type.
     *
     */
    public static final PuzzleTypeEnum MISSING_LETTER = new PuzzleTypeEnum();

    /**
     * denotes the letter scramble puzzle type.
     *
     */
    public static final PuzzleTypeEnum LETTER_SCRAMBLE = new PuzzleTypeEnum();

    /**
     * private do-nothing constructor to prevent instantiation.
     *
     *
     */
    private PuzzleTypeEnum() {

    }
}
