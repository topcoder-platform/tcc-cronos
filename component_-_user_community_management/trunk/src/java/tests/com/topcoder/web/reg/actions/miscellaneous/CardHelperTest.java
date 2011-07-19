/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p> Unit test case of {@link CardHelper}. </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class CardHelperTest extends TestCase {

    /**
     * <p> Creates a test suite for the tests in this test case. </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(CardHelperTest.class);
        return suite;
    }

    /**
     * <p>
     * Accuracy test for method isUnlocked.
     *
     * It verifies the return value is correct.
     * </p>
     * @throws Exception to Junit
     */
    public void test_isUnlocked() throws Exception {
        assertFalse("incorrect result", CardHelper.isUnlocked(100));
        CardHelper.setUnlocked(100, true);
        assertTrue("incorrect result", CardHelper.isUnlocked(100));
        CardHelper.setUnlocked(100, false);
        assertFalse("incorrect result", CardHelper.isUnlocked(100));
    }
    /**
     * <p>
     * Failure test for method isUnlocked with invalid parameter..
     *
     * Expects IllegalArgumentException if the parameter is invalid.
     * </p>
     * @throws Exception to Junit
     */
    public void test_isUnlocked2() throws Exception {
        try {
            CardHelper.isUnlocked(-1);
            fail("Expects an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * <p>
     * Accuracy test for method isUnlocked.
     *
     * It verifies the Unlocked flag is correctly set.
     * </p>
     * @throws Exception to Junit
     */
    public void test_setUnlocked() throws Exception {
        CardHelper.setUnlocked(100, true);
        assertTrue("incorrect result", CardHelper.isUnlocked(100));
        CardHelper.setUnlocked(100, false);
        assertFalse("incorrect result", CardHelper.isUnlocked(100));
    }
    /**
     * <p>
     * Failure test for method setUnlocked with invalid parameter..
     *
     * Expects IllegalArgumentException if the parameter is invalid.
     * </p>
     * @throws Exception to Junit
     */
    public void test_setUnlocked3() throws Exception {
        try {
            CardHelper.setUnlocked(-1, true);
            fail("Expects an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
