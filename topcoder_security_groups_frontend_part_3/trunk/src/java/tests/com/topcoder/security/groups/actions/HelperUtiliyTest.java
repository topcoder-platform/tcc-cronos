/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

/**
 * <p>
 * Unit tests for the {@link HelperUtiliy}.
 * </p>
 *
 * @author progloco
 * @version 1.0
 */
public class HelperUtiliyTest {

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(HelperUtiliyTest.class);
    }

    /**
     * <p>
     * Failure test for {@link Helper#checkSingleElementList()}.
     * </p>
     * <p>
     * Argument is null.SecurityGroupsActionException will throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_checkSingleElementList() throws Exception {
        HelperUtiliy.checkSingleElementList(null, "test");
    }
}