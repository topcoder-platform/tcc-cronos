/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.entities;

import junit.framework.TestCase;

/**
 * <p>
 * Test the <code>PuzzleTypeEnum</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PuzzleTypeEnumUnitTests extends TestCase {
    /**
     * <p>
     * Accuracy test. Tests the <code>PuzzleTypeEnum</code> for
     * define correctly enum.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testEnumNumber_1_accuracy() throws Exception {
        assertEquals("4 enum expected.", 4, PuzzleTypeEnum.getEnumList(PuzzleTypeEnum.class)
                .size());
    }
}
