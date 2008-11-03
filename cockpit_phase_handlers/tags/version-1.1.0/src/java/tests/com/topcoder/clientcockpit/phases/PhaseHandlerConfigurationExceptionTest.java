/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import junit.framework.TestCase;

/**
 * <p>
 * Tests the functionality of <code>{@link PhaseHandlerConfigurationException}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PhaseHandlerConfigurationExceptionTest extends TestCase {

    /**
     * <p>
     * Accuracy test of
     * <code>{@link PhaseHandlerConfigurationException#
     * PhaseHandlerConfigurationException(String message)}</code> constructor.
     * Creates an instance and get its attributes for test.
     * </p>
     */
    public void testPhaseHandlerConfigurationException_accuracy_1() {
        String msg = "msg";
        PhaseHandlerConfigurationException e = new PhaseHandlerConfigurationException(msg);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link PhaseHandlerConfigurationException#
     * PhaseHandlerConfigurationException(String message, Throwable cause)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     */
    public void testPhaseHandlerConfigurationException_accuracy_2() {
        String msg = "msg";
        Throwable t = new RuntimeException();
        PhaseHandlerConfigurationException e = new PhaseHandlerConfigurationException(msg, t);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("throwable should be set and obtained properly", t, e.getCause());
    }
}
