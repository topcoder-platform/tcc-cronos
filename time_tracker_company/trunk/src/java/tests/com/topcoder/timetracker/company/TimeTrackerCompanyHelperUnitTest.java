/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>TimeTrackerCompanyHelper</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class TimeTrackerCompanyHelperUnitTest extends TestCase {
    /**
     * Accuracy test for the method <code>validatePositive(long value, String name)</code> when the given value is not
     * positive, IllegalArgumentException is expected.
     */
    public void testValidatePositive_NotPositive() {
        try {
            TimeTrackerCompanyHelper.validatePositive(-1, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validatePositive(long value, String name)</code> when the given value is
     * positive.
     */
    public void testValidatePositive_Accuracy() {
        TimeTrackerCompanyHelper.validatePositive(1, "name");
    }

    /**
     * Accuracy test for the method <code>validateNotNull(Object, String)</code> when the given object is null,
     * IllegalArgumentException is expected.
     */
    public void testValidateNotNull_NullObject() {
        try {
            TimeTrackerCompanyHelper.validateNotNull(null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateNotNull(Object, String)</code> when the given object is not null.
     */
    public void testValidateNotNull_Accuracy() {
        TimeTrackerCompanyHelper.validateNotNull(new Object(), "name");
    }

    /**
     * Accuracy test for the method <code>validateString(String, String)</code> when the given String is null,
     * IllegalArgumentException is expected.
     */
    public void testValidateString_NullString() {
        try {
            TimeTrackerCompanyHelper.validateString(null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateString(String, String)</code> when the given String is empty,
     * IllegalArgumentException is expected.
     */
    public void testValidateString_EmptyString() {
        try {
            TimeTrackerCompanyHelper.validateString(" ", "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateString(String, String)</code> when the given string is correct.
     */
    public void testValidateString_Accuracy() {
        TimeTrackerCompanyHelper.validateString("string", "name");
    }

    /**
     * Test the method of <code>getExceptionStaceTrace(Exception)</code> for accuracy.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetExceptionStaceTrace_Accuracy() throws Exception {
        TimeTrackerCompanyHelper.getExceptionStaceTrace(new NullPointerException("null"));
    }
}
