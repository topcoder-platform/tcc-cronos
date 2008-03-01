/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.failuretests.model;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;

/**
 * <p>
 * Failure test for Document class.
 * </p>
 * 
 * @author 80x86
 * @version 1.0
 */
public class DocumentUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>Document</code> instance used for testing.
     * </p>
     */
    private Document document;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * 
     * @throws Exception
     *             If any problem occurs.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        document = new Document();
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     * 
     * @throws Exception
     *             If any problem occurs.
     */
    @Override
    protected void tearDown() throws Exception {
        document = null;

        super.tearDown();
    }

    /**
     * Failure test for setDocumentId with null, IllegalArgumentException is expected.
     */
    public void testSetDocumentId_Null() {
        try {
            document.setDocumentId(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setCreateDate with null, IllegalArgumentException is expected.
     */
    public void testSetCreateDate_Null() {
        try {
            document.setCreateDate(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setDocumentVersions with null, IllegalArgumentException is expected.
     */
    public void testSetDocumentVersions_Null() {
        try {
            document.setDocumentVersions(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setDocumentVersions with documentVersions contains null, IllegalArgumentException is expected.
     */
    public void testSetDocumentVersions_NullElement() {
        Set<DocumentVersion> documentVersions = new HashSet<DocumentVersion>();
        documentVersions.add(null);
        try {
            document.setDocumentVersions(documentVersions);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
