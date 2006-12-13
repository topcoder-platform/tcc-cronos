/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.entities;

import junit.framework.TestCase;
/**
 * <p>
 * Test the <code>ResultCode</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResultCodeUnitTests extends TestCase {
    /**
     * <p>
     * Accuracy test. Tests the <code>ResultCode</code> for
     * define correctly enum.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testResultCodeNumber_1_accuracy() throws Exception {
        assertEquals("enum number.", 15, ResultCode.getEnumList(ResultCode.class)
                .size());
    }
}
