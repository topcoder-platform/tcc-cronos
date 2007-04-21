/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>InvoiceUnrecognizedEntityException</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class InvoiceUnrecognizedEntityExceptionTest extends TestCase {

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(InvoiceUnrecognizedEntityExceptionTest.class);
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: all fields are set as expected.
     */
    public void testInvoiceUnrecognizedEntityException() {
        InvoiceUnrecognizedEntityException exception = new InvoiceUnrecognizedEntityException(12, "test");

        assertEquals("The message is not set", "test", exception.getMessage());
        assertEquals("The entity id is not set", 12, exception.getId());

    }

    /**
     * Test <code>getId</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetId() {
        InvoiceUnrecognizedEntityException exception = new InvoiceUnrecognizedEntityException(12, "test");

        assertEquals("The entity id is not set", 12, exception.getId());
    }

}
