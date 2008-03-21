/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link Helper}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperUnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(HelperUnitTests.class);
    }

    /**
     * <p>
     * Unit test for <code>Helper#checkNull(Object, String)</code> method.
     * </p>
     * <p>
     * If the object is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testCheckNull_Null() {
        try {
            Helper.checkNull(null, "null");
            fail("If the object is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>Helper#checkNull(Object, String)</code> method.
     * </p>
     * <p>
     * If the object is not null, should never throw IllegalArgumentException.
     * </p>
     */
    public void testCheckNull_NotNull() {
        try {
            Helper.checkNull(new Object(), "object");
        } catch (IllegalArgumentException e) {
            fail("If the object is not null, should never throw IllegalArgumentException.");
        }
    }

    /**
     * <p>
     * Unit test for <code>Helper#checkNullOrEmpty(String, String)</code> method.
     * </p>
     * <p>
     * If the string is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testCheckNullOrEmpty_Null() {
        try {
            Helper.checkNullOrEmpty(null, "null");
            fail("If the string is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>Helper#checkNullOrEmpty(String, String)</code> method.
     * </p>
     * <p>
     * If the string is empty, should throw IllegalArgumentException.
     * </p>
     */
    public void testCheckNullOrEmpty_Empty() {
        try {
            Helper.checkNullOrEmpty("", "empty");
            fail("If the string is empty, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>Helper#checkNullOrEmpty(String, String)</code> method.
     * </p>
     * <p>
     * If the string is trimmed empty, should throw IllegalArgumentException.
     * </p>
     */
    public void testCheckNullOrEmpty_TrimmedEmpty() {
        try {
            Helper.checkNullOrEmpty(" ", "trimmed empty");
            fail("If the string is trimmed empty, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>Helper#checkNullOrEmpty(String, String)</code> method.
     * </p>
     * <p>
     * If the string is valid, should not throw IllegalArgumentException.
     * </p>
     */
    public void testCheckNullOrEmpty_Valid() {
        try {
            Helper.checkNullOrEmpty("Hello", "hi");
        } catch (IllegalArgumentException e) {
            fail("If the string is valid, should not throw IllegalArgumentException.");
        }
    }

    /**
     * <p>
     * Unit test for <code>Helper#checkCollection(Collection, String)</code> method.
     * </p>
     * <p>
     * If the collection is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testCheckCollection_Null() {
        try {
            Helper.checkCollection(null, "null");
            fail("If the collection is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>Helper#checkCollection(Collection, String)</code> method.
     * </p>
     * <p>
     * If the collection contains null element, should throw IllegalArgumentException.
     * </p>
     */
    public void testCheckCollection_ContainsNull() {
        Collection<?> collection = new ArrayList<Object>();
        collection.add(null);
        try {
            Helper.checkCollection(collection, "list");
            fail("If the collection contains null element, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>Helper#checkCollection(Collection, String)</code> method.
     * </p>
     * <p>
     * If the collection is valid, should not throw IllegalArgumentException.
     * </p>
     */
    public void testCheckCollection_Valid() {
        try {
            Helper.checkCollection(new ArrayList<Object>(), "list");
        } catch (IllegalArgumentException e) {
            fail("If the collection is valid, should not throw IllegalArgumentException.");
        }
    }
}
