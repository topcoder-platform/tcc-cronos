/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.failuretests;

import junit.framework.TestCase;

import com.cronos.im.messenger.DateFormatContext;

/**
 * Tests the {@link DateFormatContext} for failure.
 *
 * @author mittu
 * @version 1.0
 */
public class DateFormatContextFailure extends TestCase {

    /**
     * Represents the DateFormatContext.
     */
    private DateFormatContext dateFormatContext;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        dateFormatContext = new DateFormatContext();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void tearDown() throws Exception {
        dateFormatContext = null;
    }

    /**
     * <p>
     * Tests the getAttribute method of <code>DateFormatContext</code>.
     * </p>
     *
     * <p>
     * Sets the attribute name as null.
     * </p>
     *
     */
    public void testGetAttribute() {
        try {
            dateFormatContext.getAttribute(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the getAttribute method of <code>DateFormatContext</code>.
     * </p>
     *
     * <p>
     * Sets the attribute name as empty string.
     * </p>
     *
     */
    public void testGetAttribute1() {
        try {
            dateFormatContext.getAttribute("   ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the removeAttribute method of <code>DateFormatContext</code>.
     * </p>
     *
     * <p>
     * Sets the attribute name as null.
     * </p>
     *
     */
    public void testRemoveAttribute() {
        try {
            dateFormatContext.removeAttribute(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the removeAttribute method of <code>DateFormatContext</code>.
     * </p>
     *
     * <p>
     * Sets the attribute name as empty string.
     * </p>
     *
     */
    public void testRemoveAttribute1() {
        try {
            dateFormatContext.removeAttribute("   ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the addAttribute method of <code>DateFormatContext</code>.
     * </p>
     *
     * <p>
     * Sets the attribute name as null.
     * </p>
     *
     */
    public void testAddAttribute() {
        try {
            dateFormatContext.removeAttribute(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the addAttribute method of <code>DateFormatContext</code>.
     * </p>
     *
     * <p>
     * Sets the attribute name as empty string.
     * </p>
     *
     */
    public void testAddAttribute1() {
        try {
            dateFormatContext.removeAttribute("   ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the contains method of <code>DateFormatContext</code>.
     * </p>
     *
     * <p>
     * Sets the attribute name as null.
     * </p>
     *
     */
    public void testContains() {
        try {
            dateFormatContext.removeAttribute(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * <p>
     * Tests the contains method of <code>DateFormatContext</code>.
     * </p>
     *
     * <p>
     * Sets the attribute name as empty string.
     * </p>
     *
     */
    public void testContains1() {
        try {
            dateFormatContext.removeAttribute("   ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

}
