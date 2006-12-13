/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.failuretests.entities;

import com.orpheus.administration.entities.PuzzleConfig;
import junit.framework.TestCase;


/**
 * <p>
 * Failure test for <code>PuzzleConfig</code> class.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class PuzzleConfigFailureTests extends TestCase {

    /**
     * <p>
     * Failure test of the constructor that receives 2 parameters.
     * In this test case the 'puzzleTypeName' parameter is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructor1NullTypeName() throws Exception {
        try {
            new PuzzleConfig(null, new Integer(100), new Integer(100), 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor that receives 2 parameters.
     * In this test case the 'puzzleTypeName' parameter is empty. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructor1EmptyTypeName() throws Exception {
        try {
            new PuzzleConfig(" ", new Integer(100), new Integer(100), 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }
}
