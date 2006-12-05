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
 * @author myxgyy
 * @version 1.0
 */
public class ResultCodeAccuracyTests extends TestCase {
    /**
     * <p>
     * Accuracy test. Tests the <code>ResultCode</code> for
     * define correctly enum.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testResultCodeNumberAccuracy() throws Exception {
        assertEquals("enum number.", 15, ResultCode.getEnumList(ResultCode.class)
                .size());
    }
}
