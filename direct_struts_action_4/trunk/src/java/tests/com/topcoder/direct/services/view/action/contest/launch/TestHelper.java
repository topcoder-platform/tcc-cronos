/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import junit.framework.TestCase;

/**
 * Test Helper for unit tests.
 * @author zju_jay
 * @version 1.0
 */
public class TestHelper extends TestCase {
    /**
     * Asserts that inheritance is correct for a class by checking that the class extends the given
     * superclass.
     * @param clazz the class to check
     * @param expectedSuperclass the expected superclass that the class should extend
     */
    public static void assertSuperclass(Class < ? > clazz, Class < ? > expectedSuperclass) {
        assertTrue("The inheritance is wrong, the class doesn't extend "
            + expectedSuperclass.getName(), clazz.getSuperclass().equals(expectedSuperclass));
    }
}
