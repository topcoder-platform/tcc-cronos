/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.impl;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test case for <code>Util</code> class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UtilTest extends TestCase {
    /**
     * <p>
     * Test for <code>checkObjNotNull</code> method.
     * </p>
     * <p>
     * Tests it against null object. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCheckObjNotNullWithNullObj() throws Exception {
        try {
            Util.checkObjNotNull(null, "null");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>checkObjNotNull</code> method.
     * </p>
     * <p>
     * Tests it with non-null object, no exception should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCheckObjNotNullWithNonNullObj() throws Exception {
        Util.checkObjNotNull(new Object(), "non_null");
    }

    /**
     * <p>
     * Test for <code>checkStrNotNullOrEmpty</code> method.
     * </p>
     * <p>
     * Tests it against null string. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCheckStrNotNullOrEmptyWithNullStr() throws Exception {
        try {
            Util.checkStrNotNullOrEmpty(null, "null");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>checkStrNotNullOrEmpty</code> method.
     * </p>
     * <p>
     * Tests it against empty string. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCheckStrNotNullOrEmptyWithEmptyStr() throws Exception {
        try {
            Util.checkStrNotNullOrEmpty("    ", "empty");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>checkStrNotNullOrEmpty</code> method.
     * </p>
     * <p>
     * Tests it with non-null and non-empty string. no exception should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCheckStrNotNullOrEmpty() throws Exception {
        Util.checkStrNotNullOrEmpty("he", "non");
    }

    /**
     * <p>
     * Test for <code>checkIDNotNegative</code> method.
     * </p>
     * <p>
     * Tests it with negative ID. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCheckIDNotNegativeWithNegID() throws Exception {
        try {
            Util.checkIDNotNegative(-1, "negID");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>checkErrorsArray(errors)</code> method.
     * </p>
     * <p>
     * Tests it with errors array having null elements. expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCheckErrorsArrayWithArrayHasNullEle() throws Exception {
        try {
            Util.checkErrorsArray(new String[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>checkErrorsArray(errors)</code> method.
     * </p>
     * <p>
     * Tests it with errors array having empty elements. expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCheckErrorsArrayWithArrayHasEmptyEle() throws Exception {
        try {
            Util.checkErrorsArray(new String[] {"     "});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }
}
