/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for UploadedDocument class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UploadedDocumentTest extends TestCase {
    /**
     * Bean to test.
     */
    private UploadedDocument target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(UploadedDocumentTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new UploadedDocument();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests setter/getter for documentId field.
     */
    public void testDocumentId() {
        assertEquals("default value", -1, target.getDocumentId());
        target.setDocumentId(35);
        assertEquals("new value", 35, target.getDocumentId());
    }

    /**
     * Tests setter/getter for contestId field.
     */
    public void testContestId() {
        assertEquals("default value", -1, target.getContestId());
        target.setContestId(35);
        assertEquals("new value", 35, target.getContestId());
    }

    /**
     * Tests setter/getter for documentTypeId field.
     */
    public void testDocumentTypeId() {
        assertEquals("default value", -1, target.getDocumentTypeId());
        target.setDocumentTypeId(35);
        assertEquals("new value", 35, target.getDocumentTypeId());
    }

    /**
     * Tests setter/getter for mimeTypeId field.
     */
    public void testMimeTypeId() {
        assertEquals("default value", -1, target.getMimeTypeId());
        target.setMimeTypeId(35);
        assertEquals("new value", 35, target.getMimeTypeId());
    }

    /**
     * Tests setter/getter for description field.
     */
    public void testDescription() {
        assertNull("default value", target.getDescription());
        target.setDescription("abc");
        assertEquals("new value", "abc", target.getDescription());
    }

    /**
     * Tests setter/getter for fileName field.
     */
    public void testFileName() {
        assertNull("default value", target.getFileName());
        target.setFileName("abc");
        assertEquals("new value", "abc", target.getFileName());
    }

    /**
     * Tests setter/getter for path field.
     */
    public void testPath() {
        assertNull("default value", target.getPath());
        target.setPath("abc");
        assertEquals("new value", "abc", target.getPath());
    }

    /**
     * Tests setter/getter for path field.
     */
    public void testFile() {
        assertNull("default value", target.getFile());
        target.setFile("abc".getBytes());
        assertEquals("new value", "abc", new String(target.getFile()));
    }
}
