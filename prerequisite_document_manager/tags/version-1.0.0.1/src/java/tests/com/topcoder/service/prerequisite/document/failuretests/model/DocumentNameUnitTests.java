/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.failuretests.model;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.service.prerequisite.document.model.DocumentName;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;

/**
 * <p>
 * Failure test for DocumentName class.
 * </p>
 * 
 * @author 80x86
 * @version 1.0
 */
public class DocumentNameUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>DocumentName</code> instance for testing.
     * </p>
     */
    private DocumentName documentName;

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

        documentName = new DocumentName();
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
        documentName = null;

        super.tearDown();
    }

    /**
     * Failure test for setDocumentNameId with null, IllegalArgumentException is expected.
     */
    public void testSetDocumentNameId_Null() {
        try {
            documentName.setDocumentNameId(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setName with null, IllegalArgumentException is expected.
     */
    public void testSetName_Null() {
        try {
            documentName.setName(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setName with empty string, IllegalArgumentException is expected.
     */
    public void testSetName_Empty() {
        try {
            documentName.setName("     ");
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
            documentName.setDocumentVersions(null);
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
            documentName.setDocumentVersions(documentVersions);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
