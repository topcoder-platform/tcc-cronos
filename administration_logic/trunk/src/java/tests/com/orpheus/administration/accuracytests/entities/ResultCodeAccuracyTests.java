/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.entities;

import com.orpheus.administration.entities.ResultCode;

import junit.framework.TestCase;
/**
 * <p>
 * Test the <code>ResultCode</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResultCodeAccuracyTests extends TestCase {
    /**
     * <p>
     * Tests that the PuzzleTypeEnum actually represents
     * an enumeration.
     * </p>
     * @throws Exception to JUnit
     */
    public void testEnumeration() throws Exception {
        assertEquals(ResultCode.APPROVAL_NOT_PENDING, ResultCode.APPROVAL_NOT_PENDING);
        assertEquals(ResultCode.BLOCK_INFO_LENGTH_NOT_MATCH, ResultCode.BLOCK_INFO_LENGTH_NOT_MATCH);
        assertEquals(ResultCode.CANNOT_MOVE_SLOT_BEYOND_LAST, ResultCode.CANNOT_MOVE_SLOT_BEYOND_LAST);
        assertEquals(ResultCode.EXCEPTION_OCCURRED, ResultCode.EXCEPTION_OCCURRED);
        assertEquals(ResultCode.IMAGE_NOT_BELONG_TO_DOMAIN, ResultCode.IMAGE_NOT_BELONG_TO_DOMAIN);
        assertEquals(ResultCode.MISSING_PARAMETERS, ResultCode.MISSING_PARAMETERS);
        assertEquals(ResultCode.NO_MATCHING_BALLCOLOR, ResultCode.NO_MATCHING_BALLCOLOR);
        assertEquals(ResultCode.NO_MATCHING_PENDING_WINNER, ResultCode.NO_MATCHING_PENDING_WINNER);
        assertEquals(ResultCode.SLOT_FINISHED_HOSTING, ResultCode.SLOT_FINISHED_HOSTING);
        assertEquals(ResultCode.SLOT_STARTED_HOSTING, ResultCode.SLOT_STARTED_HOSTING);
        assertEquals(ResultCode.SPONSOR_NOT_BELONG_TO_DOMAIN, ResultCode.SPONSOR_NOT_BELONG_TO_DOMAIN);
        assertEquals(ResultCode.WINNER_NOT_FIRST, ResultCode.WINNER_NOT_FIRST);
        assertEquals(ResultCode.PARAMETER_NOT_DATE, ResultCode.PARAMETER_NOT_DATE);
        assertEquals(ResultCode.PARAMETER_NOT_INTEGER, ResultCode.PARAMETER_NOT_INTEGER);
        assertEquals(ResultCode.PARAMETER_NOT_LONG, ResultCode.PARAMETER_NOT_LONG);
    }
}
