/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit tests for <code>EntityType</code> enum class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EntityTypeUnitTest extends TestCase {
    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(EntityTypeUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for <code>EntityType</code>'s enumeration definition, to verify
     * that EntityType.FORUM_CATEGORY exist.
     * </p>
     */
    public void testFORUM_CATEGORY() {
        assertNotNull("The EntityType.FORUM_CATEGORY should exist.",
            EntityType.valueOf("FORUM_CATEGORY"));
    }

    /**
     * <p>
     * Accuracy test for <code>EntityType</code>'s enumeration definition, to verify
     * that EntityType.FORUM exist.
     * </p>
     */
    public void testFORUM() {
        assertNotNull("The EntityType.FORUM should exist.",
            EntityType.valueOf("FORUM"));
    }

    /**
     * <p>
     * Accuracy test for <code>EntityType</code>'s enumeration definition, to verify
     * that EntityType.FORUM_THREAD exist.
     * </p>
     */
    public void testFORUM_THREAD() {
        assertNotNull("The EntityType.FORUM_THREAD should exist.",
            EntityType.valueOf("FORUM_THREAD"));
    }

    /**
     * <p>
     * Failure test for <code>EntityType</code>'s enumeration definition, to verify
     * that EntityType.OTHER doesn't exist.
     * </p>
     */
    public void testOTHER() {
        try {
            EntityType.valueOf("OTHER");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }
}
