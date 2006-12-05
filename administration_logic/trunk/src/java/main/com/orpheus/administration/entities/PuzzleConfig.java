/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.entities;

import com.orpheus.administration.Helper;

/**
 * Encapsulates configuration data for various puzzle types. This class holds
 * the name of the puzzle type as configured in the Puzzle Framework component.
 * In case of jigsaw and sliding tile puzzles, this class will also hold their
 * width and height configuration values. In case of missing letter and letter
 * scramble puzzles, this class will hold the puzzle series size.<br/> This is
 * used when regenerating puzzles and brainteasers.<br/> Thread-Safety: This
 * class is immutable and hence thread-safe.
 *
 * @author bose_java, KKD
 * @version 1.0
 */
public class PuzzleConfig {

    /**
     * name of the puzzle type as configured in Puzzle framework component.<br/>
     * It is initialized in the constructor and does not change after that.<br/>
     * It will never be null or empty.<br/>
     *
     */
    private final String puzzleTypeName;

    /**
     * The width of the desired puzzle, in pieces.<br/> It is initialized in
     * the constructor and does not change after that.<br/> May be null in case
     * of brain teaser puzzle types.<br/>
     *
     */
    private final Integer width;

    /**
     * The height of the desired puzzle, in pieces.<br/> It is initialized in
     * the constructor and does not change after that.<br/> May be null in case
     * of brain teaser puzzle types.<br/>
     *
     */
    private final Integer height;

    /**
     * The size of the puzzle series to be generated.<br/> It is initialized in
     * the constructor and does not change after that.<br/> May be 0 in case of
     * non-brain teaser puzzle types.<br/>
     *
     */
    private final int puzzleSeriesSize;

    /**
     * Creates a PuzzleConfig instance with given values.
     *
     *
     * @param puzzleTypeName
     *            puzzle type name.
     * @param width
     *            width of the desired puzzle, in pieces.
     * @param height
     *            height of the desired puzzle, in pieces.
     * @param puzzleSeriesSize
     *            size of the puzzle series to be generated.
     * @throws IllegalArgumentException
     *             if puzzleTypeName is null or empty.
     */
    public PuzzleConfig(String puzzleTypeName, Integer width, Integer height,
            int puzzleSeriesSize) {
        Helper.checkString(puzzleTypeName, "puzzleTypeName");

        this.puzzleTypeName = puzzleTypeName;
        this.width = width;
        this.height = height;
        this.puzzleSeriesSize = puzzleSeriesSize;
    }

    /**
     * Returns the puzzle type name.
     *
     *
     * @return the puzzle type name.
     */
    public String getPuzzleTypeName() {
        return puzzleTypeName;
    }

    /**
     * returns width of the desired puzzle, in pieces.
     *
     *
     * @return width of the desired puzzle, in pieces.
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * returns height of the desired puzzle, in pieces.
     *
     *
     * @return height of the desired puzzle, in pieces.
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * Returns size of the puzzle series to be generated.
     *
     *
     * @return size of the puzzle series to be generated.
     */
    public int getPuzzleSeriesSize() {
        return puzzleSeriesSize;
    }
}
