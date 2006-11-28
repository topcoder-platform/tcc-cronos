/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;

import com.orpheus.administration.persistence.impl.filter.LikeFilter;

import junit.framework.TestCase;


/**
 * Unit tests for LikeFilter class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestLikeFilter extends TestCase {
    /**
     * Tests LikeFilter(String name, String value) method with null String name, Expected
     * IllegalArgumentException.
     */
    public void testLikeFilter1_NullName() {
        try {
            new LikeFilter(null, "SW:A");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LikeFilter(String name, String value) method with empty String name, Expected
     * IllegalArgumentException.
     */
    public void testLikeFilter1_EmptyName() {
        try {
            new LikeFilter(" ", "SW:A");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LikeFilter(String name, String value) method with null String value, Expected
     * IllegalArgumentException.
     */
    public void testLikeFilter1_NullValue() {
        try {
            new LikeFilter("like", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LikeFilter(String name, String value) method with empty String value, Expected
     * IllegalArgumentException.
     */
    public void testLikeFilter1_EmptyValue() {
        try {
            new LikeFilter("like", " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LikeFilter(String name, String value) method with invalid String value,
     * Expected IllegalArgumentException.
     */
    public void testLikeFilter1_InvalidValue1() {
        try {
            new LikeFilter("like", "V");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LikeFilter(String name, String value) method with invalid String value,
     * Expected IllegalArgumentException.
     */
    public void testLikeFilter1_InvalidValue2() {
        try {
            new LikeFilter("like", "SS:");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LikeFilter(String name, String value, char escapeCharacter) method with null
     * String name, Expected IllegalArgumentException.
     */
    public void testLikeFilter2_NullName() {
        try {
            new LikeFilter(null, "SW:A", '\\');
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LikeFilter(String name, String value, char escapeCharacter) method with empty
     * String name, Expected IllegalArgumentException.
     */
    public void testLikeFilter2_EmptyName() {
        try {
            new LikeFilter(" ", "SW:A", '\\');
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LikeFilter(String name, String value, char escapeCharacter) method with null
     * String value, Expected IllegalArgumentException.
     */
    public void testLikeFilter2_NullValue() {
        try {
            new LikeFilter("like", null, '\\');
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LikeFilter(String name, String value, char escapeCharacter) method with empty
     * String value, Expected IllegalArgumentException.
     */
    public void testLikeFilter2_EmptyValue() {
        try {
            new LikeFilter("like", " ", '\\');
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LikeFilter(String name, String value, char escapeCharacter) method with
     * invalid String value, Expected IllegalArgumentException.
     */
    public void testLikeFilter2_InvalidValue1() {
        try {
            new LikeFilter("like", "V", '\\');
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LikeFilter(String name, String value, char escapeCharacter) method with
     * invalid String value, Expected IllegalArgumentException.
     */
    public void testLikeFilter2_InvalidValue2() {
        try {
            new LikeFilter("like", "SS:", '\\');
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests LikeFilter(String name, String value, char escapeCharacter) method with
     * invalid char escapeCharacter, Expected IllegalArgumentException.
     */
    public void testLikeFilter2_InvalidEscapeCharacter() {
        try {
            new LikeFilter("like", "SW:A", '*');
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
