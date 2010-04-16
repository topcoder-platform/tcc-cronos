/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * <p>
 * Unit tests for <code>ActionHelper</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ActionHelperUnitTest extends TestCase {

    /**
     * Accuracy test for checkInjectedFieldNull. The field is not null, so no exception should be thrown.
     */
    @Test
    public void test_checkInjectedFieldNull_Accuracy() {
        ActionHelper.checkInjectedFieldNull(new Object(), "field1");
    }

    /**
     * Failure test for checkInjectedFieldNull. The value is null and IllegalStateException is expected.
     */
    @Test
    public void test_checkInjectedFieldNull_Failure() {
        try {
            ActionHelper.checkInjectedFieldNull(null, "field1");
            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // success
        }
    }
}
