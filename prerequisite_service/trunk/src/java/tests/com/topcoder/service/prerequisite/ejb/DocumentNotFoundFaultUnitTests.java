/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link DocumentNotFoundFault}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DocumentNotFoundFaultUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>DocumentNotFoundFault</code> instance used in tests.
     * </p>
     */
    private DocumentNotFoundFault documentNotFoundFault;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DocumentNotFoundFaultUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        documentNotFoundFault = new DocumentNotFoundFault();
    }

    /**
     * <p>
     * Unit test for <code>DocumentNotFoundFault#DocumentNotFoundFault()</code> constructor.
     * </p>
     * <p>
     * Instance should be always created.
     * </p>
     */
    public void testDocumentNotFoundFault_accuracy() {
        assertNotNull("Instance should be always created.", documentNotFoundFault);
    }

    /**
     * <p>
     * Unit test for <code>DocumentNotFoundFault#getDocumentIdNotFound()</code> method.
     * </p>
     * <p>
     * It should return 0, if not set.
     * </p>
     */
    public void testGetDocumentIdNotFound_default() {
        assertEquals("Should return 0.", 0, documentNotFoundFault.getDocumentIdNotFound());
    }

    /**
     * <p>
     * Unit test for <code>DocumentNotFoundFault#setDocumentIdNotFound(long)</code> method.
     * </p>
     * <p>
     * All value are valid to set.
     * </p>
     */
    public void testSetDocumentIdNotFound_accuracy() {
        documentNotFoundFault.setDocumentIdNotFound(-1);
        assertEquals("Incorrect document id.", -1, documentNotFoundFault.getDocumentIdNotFound());

        documentNotFoundFault.setDocumentIdNotFound(0);
        assertEquals("Incorrect document id.", 0, documentNotFoundFault.getDocumentIdNotFound());

        documentNotFoundFault.setDocumentIdNotFound(1);
        assertEquals("Incorrect document id.", 1, documentNotFoundFault.getDocumentIdNotFound());
    }
}
