/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for DocumentNotFoundFault class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DocumentNotFoundFaultTest extends TestCase {
    /**
     * Bean to test.
     */
    private DocumentNotFoundFault target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(DocumentNotFoundFaultTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new DocumentNotFoundFault();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests empty constructor.
     */
    public void testConstructor() {
        assertNotNull("created instance", new DocumentNotFoundFault());
    }

    /**
     * Tests setter/getter for documentIdNotFound field.
     */
    public void testDocumentIdNotFound() {
        assertEquals("default value", 0, target.getDocumentIdNotFound());
        target.setDocumentIdNotFound(35);
        assertEquals("new value", 35, target.getDocumentIdNotFound());
    }
}
