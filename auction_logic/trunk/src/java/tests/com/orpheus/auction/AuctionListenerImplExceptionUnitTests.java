/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import junit.framework.TestCase;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * Unit test cases for the AuctionListenerImplException class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AuctionListenerImplExceptionUnitTests extends TestCase {

    /**
     * <p>
     * Error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "error message";

    /**
     * <p>
     * Cause used for testing.
     * </p>
     */
    private static final Exception CAUSE = new Exception();

    /**
     * <p>
     * Tests that AuctionListenerImplException(String) instance is created and message argument is
     * correctly propagated.
     * </p>
     */
    public void testConstructor1_1() {
        AuctionListenerImplException e = new AuctionListenerImplException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate AuctionListenerImplException", e);
        assertEquals("Error message is not properly set", ERROR_MESSAGE, e.getMessage());
    }

    /**
     * <p>
     * Tests that AuctionListenerImplException extends BaseRuntimeException.
     * </p>
     */
    public void testConstructor1_2() {
        assertTrue("AuctionListenerImplException is not subclass of BaseRuntimeException.",
            new AuctionListenerImplException(ERROR_MESSAGE) instanceof BaseRuntimeException);
    }

    /**
     * <p>
     * Tests that AuctionListenerImplException(String, Throwable) instance is created and cause is
     * correctly propagated.
     * </p>
     */
    public void testConstructor2_1() {
        AuctionListenerImplException e = new AuctionListenerImplException(ERROR_MESSAGE, CAUSE);

        assertNotNull("Unable to instantiate AuctionListenerImplException", e);
        assertEquals("Cause is not properly set", CAUSE, e.getCause());
    }
}
