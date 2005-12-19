/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0 (Unit Test)
 */
package com.topcoder.buildutility.template;

import junit.framework.TestCase;

/**
 * <p>Unit test cases for NullCache.</p>
 *
 * @author oldbig
 *
 * @version 1.0
 */
public class NullCacheTests extends TestCase {

    /**
     * A NullCache instance to test.
     */
    private NullCache cache = new NullCache();

    /**
     * <p>Tests the constructor of NullCache.</p>
     */
    public void testNullCache() {
        assertNotNull("Unable to instantiate NullCache.", new NullCache());
    }

    /**
     * <p>Tests the get method.</p>
     *
     * <p>null should be returned always.</p>
     */
    public void testGet() {
        assertNull("null should be returned always.", cache.get("foo"));
    }

    /**
     * <p>Tests the put method.</p>
     *
     * <p>Nothing should happen.</p>
     */
    public void testPut() {
        // nothing should happen
        cache.put("foo", null);
    }

    /**
     * <p>Tests the remove method.</p>
     *
     * <p>null should be returned always.</p>
     */
    public void testRemove() {
        assertNull("null should be returned always.", cache.remove("foo"));
    }

    /**
     * <p>Tests the clear method.</p>
     *
     * <p>Nothing should happen.</p>
     */
    public void testClear() {
        // nothing should happen
        cache.clear();
    }

    /**
     * <p>Tests the keySet method.</p>
     *
     * <p>An empty set should be returned always.</p>
     */
    public void testKeySet1() {
        assertEquals("empty set should be returned always.", 0, cache.keySet().size());
    }

    /**
     * <p>Tests the keySet method.</p>
     *
     * <p>The returned set should be unmodifiable.</p>
     */
    public void testKeySet2() {
        try {
            // modification to returned set will cause UnsupportedOperationException
            cache.keySet().add("foo");
            fail("UnsupportedOperationException should be thrown");
        } catch (UnsupportedOperationException npe) {
            // success
        }
    }

}
