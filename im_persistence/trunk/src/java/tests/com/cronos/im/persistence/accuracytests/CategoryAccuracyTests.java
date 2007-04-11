/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.accuracytests;

import com.cronos.im.persistence.rolecategories.Category;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for <code>{@link Category}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class CategoryAccuracyTests extends TestCase {

    /**
     * <p>
     * Represents the Category instance used in tests.
     * </p>
     */
    private Category category;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        category = new Category(1, "TopCoder", "Have Fun.", true);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link Category#Category(long, String, String, boolean)}</code> constructor.
     * </p>
     */
    public void testCategoryAccuracy() {
        category = new Category(2, "TC", "TopCoder", false);

        assertNotNull("instance not created.", category);

        assertEquals("id field not set correctly.", 2, category.getId());
        assertEquals("name field not set correctly.", "TC", category.getName());
        assertEquals("description field not set correctly.", "TopCoder", category.getDescription());
        assertFalse("should be unchatable.", category.getChattable());
    }

    /**
     * <p>
     * Accuracy test for <code>{@link Category#getId()}</code> method.
     * </p>
     */
    public void testGetIdAccuracy() {
        assertEquals("id field not set correctly.", 1, category.getId());
    }

    /**
     * <p>
     * Accuracy test for <code>{@link Category#getName()}</code> method.
     * </p>
     */
    public void testGetNameAccuracy() {
        assertEquals("name field not set correctly.", "TopCoder", category.getName());
    }

    /**
     * <p>
     * Accuracy test for <code>{@link Category#getDescription()}</code> method.
     * </p>
     */
    public void testGetDescriptionAccuracy() {
        assertEquals("description field not set correctly.", "Have Fun.", category.getDescription());
    }

    /**
     * <p>
     * Accuracy test for <code>{@link Category#getChattable()}</code> method.
     * </p>
     */
    public void testGetChattableAccuracy() {
        assertTrue("chattable field not set correctly.", category.getChattable());
    }
}
