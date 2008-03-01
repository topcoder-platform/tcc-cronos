/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.failuretests.model;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;

/**
 * <p>
 * Failure test for DocumentVersion class.
 * </p>
 * 
 * @author 80x86
 * @version 1.0
 */
public class DocumentVersionUnitTests extends TestCase {
    /**
     * <p>
     * Represents the <code>DocumentVersion</code> instance for testing.
     * </p>
     */
    private DocumentVersion documentVersion;

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
        documentVersion = new DocumentVersion();
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
        documentVersion = null;
        super.tearDown();
    }

    /**
     * Failure test for setDocumentVersionId with null, IllegalArgumentException is expected.
     */
    public void testSetDocumentVersionId_Null() {
        try {
            documentVersion.setDocumentVersionId(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setDocument with null, IllegalArgumentException is expected.
     */
    public void testSetDocument_Null() {
        try {
            documentVersion.setDocument(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setDocumentName with null, IllegalArgumentException is expected.
     */
    public void testSetDocumentName_Null() {
        try {
            documentVersion.setDocumentName(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setDocumentVersion with null, IllegalArgumentException is expected.
     */
    public void testSetDocumentVersion_Null() {
        try {
            documentVersion.setDocumentVersion(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setVersionDate with null, IllegalArgumentException is expected.
     */
    public void testSetVersionDate_Null() {
        try {
            documentVersion.setVersionDate(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setContent with null, IllegalArgumentException is expected.
     */
    public void testSetContent_Null() {
        try {
            documentVersion.setContent(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setContent with empty string, IllegalArgumentException is expected.
     */
    public void testSetContent_Empty() {
        try {
            documentVersion.setContent("   ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setCompetitionDocuments with null, IllegalArgumentException is expected.
     */
    public void testSetCompetitionDocuments_Null() {
        try {
            documentVersion.setCompetitionDocuments(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setCompetitionDocuments with competitionDocuments contains null, IllegalArgumentException is
     * expected.
     */
    public void testSetCompetitionDocuments_NullElement() {
        Set<CompetitionDocument> competitionDocuments = new HashSet<CompetitionDocument>();
        competitionDocuments.add(null);
        try {
            documentVersion.setCompetitionDocuments(competitionDocuments);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
