/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.topcoder.util.auction.AuctionPersistenceException;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for <code>TranslationException</code>.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error message
 * and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TranslationExceptionUnitTest extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testTranslationException1() {
        TranslationException ce = new TranslationException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate TranslationException.", ce);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ce.getMessage());
    }

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testTranslationException2() {
        Exception cause = new Exception();
        TranslationException ce = new TranslationException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate TranslationException.", ce);
        assertEquals("Cause is not properly propagated to super class.", cause, ce.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies TranslationException subclasses AuctionPersistenceException.
     * </p>
     */
    public void testTranslationExceptionInheritance1() {
        assertTrue("TranslationException does not subclass AuctionPersistenceException.",
            new TranslationException(ERROR_MESSAGE) instanceof AuctionPersistenceException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies TranslationException subclasses AuctionPersistenceException.
     * </p>
     */
    public void testTranslationExceptionInheritance2() {
        assertTrue("TranslationException does not subclass AuctionPersistenceException.",
            new TranslationException(ERROR_MESSAGE, new Exception()) instanceof AuctionPersistenceException);
    }
}
