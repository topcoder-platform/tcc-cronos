/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.accuracytests;

import java.util.Date;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.timetracker.invoice.InvoiceStatus;

/**
 * <p>
 * Accuracy Unit test cases for InvoiceStatus.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class InvoiceStatusAccuracyTests extends TestCase {
    /**
     * <p>
     * InvoiceStatus instance for testing.
     * </p>
     */
    private InvoiceStatus instance;

    /**
     * <p>
     * The creation date for testing.
     * </p>
     */
    private Date creationDate;

    /**
     * <p>
     * The modification date for testing.
     * </p>
     */
    private Date modificationDate;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        creationDate = new Date();
        modificationDate = new Date();
        instance = new InvoiceStatus(8, "description", "user", "user", creationDate, modificationDate);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        instance = null;
        modificationDate = null;
        creationDate = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(InvoiceStatusAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor InvoiceStatus#InvoiceStatus() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create InvoiceStatus instance.", instance);
    }

}