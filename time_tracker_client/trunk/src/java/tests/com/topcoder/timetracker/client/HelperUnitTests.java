/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import junit.framework.TestCase;


/**
 * Unit test for the <code>Helper</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class HelperUnitTests extends TestCase {
    /**
     * <p>
     * Failure test for method <code>checkNull</code>. Check with null is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcheckNullWith() throws Exception {
        try {
            Helper.checkNull(null, "null");
            fail("Check with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>checkString</code>. Check with empty is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcheckStringWithEmpty() throws Exception {
        try {
            Helper.checkString("      ", "empty");
            fail("Check with empty is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>checkPositive</code>. Check with negative is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcheckPositiveWith() throws Exception {
        try {
            Helper.checkPositive(-1, "negative");
            fail("Check with negative is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }
}
