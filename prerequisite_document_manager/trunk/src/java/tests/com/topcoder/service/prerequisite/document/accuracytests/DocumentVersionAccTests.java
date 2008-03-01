/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.accuracytests;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.service.prerequisite.document.model.CompetitionDocument;
import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentName;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;

/**
 * <p>
 * Accuracy test for <code>{@link DocumentVersion}</code> class.
 * </p>
 *
 * @author liuliquan
 * @version 1.0
 */
public class DocumentVersionAccTests extends TestCase {
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
        // explicitly set null.
        documentVersion = null;

        super.tearDown();
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#DocumentVersion()</code> constructor.
     * </p>
     * <p>
     * This method simply do nothing, should always create the instance.
     * </p>
     */
    public void testDocumentNameAccuracy() {
        assertNotNull("Instance should always create.", documentVersion);
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#getDocumentVersionId()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetDocumentVersionId_default() {
        assertNull("Should return null.", documentVersion.getDocumentVersionId());
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#setDocumentVersionId(Long)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetDocumentVersionId_notNull() {
        Long documentVersionId = 1L;

        documentVersion.setDocumentVersionId(documentVersionId);

        assertEquals("The document version id is not set properly.", documentVersionId, documentVersion
                .getDocumentVersionId());
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#getDocument()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetDocument_default() {
        assertNull("Should return null.", documentVersion.getDocument());
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#setDocument(Document)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetDocument_notNull() {
        Document document = new Document();

        documentVersion.setDocument(document);

        assertEquals("The document is not set properly.", document, documentVersion.getDocument());
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#getDocumentName()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetDocumentName_default() {
        assertNull("Should return null.", documentVersion.getDocumentName());
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#setDocumentName(DocumentName)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetDocumentName_notNull() {
        DocumentName documentName = new DocumentName();

        documentVersion.setDocumentName(documentName);

        assertEquals("The document name is not set properly.", documentName, documentVersion.getDocumentName());
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#getDocumentVersion()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetDocumentVersion_default() {
        assertNull("Should return null.", documentVersion.getDocumentVersion());
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#setDocumentVersion(Integer)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetDocumentVersion_notNull() {
        Integer version = 1;

        documentVersion.setDocumentVersion(version);

        assertEquals("The document version is not set properly.", version, documentVersion.getDocumentVersion());
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#getVersionDate()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetVersionDate_default() {
        assertNull("Should return null.", documentVersion.getVersionDate());
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#setVersionDate(Date)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetVersionDate_notNull() {
        Date versionDate = new Date();

        documentVersion.setVersionDate(versionDate);

        assertEquals("The version date is not set properly.", versionDate, documentVersion.getVersionDate());
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#getContent()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetContent_default() {
        assertNull("Should return null.", documentVersion.getContent());
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#setContent(String)</code> method.
     * </p>
     * <p>
     * If content is meaningful (not null or empty), should set internally.
     * </p>
     */
    public void testSetContent_meaningful() {
        String content = "Test doc";
        documentVersion.setContent(content);

        assertEquals("The description is not set properly.", content, documentVersion.getContent());
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#getCompetitionDocuments()</code> method.
     * </p>
     * <p>
     * It should return empty set, if not set.
     * </p>
     */
    public void testGetCompetitionDocuments_default() {
        Set<CompetitionDocument> competitionDocuments = documentVersion.getCompetitionDocuments();
        assertNotNull("It should never return null.", competitionDocuments);
        assertTrue("It should be empty by default.", competitionDocuments.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>DocumentVersion#setCompetitionDocuments(Set&lt;CompetitionDocument&gt;)</code> method.
     * </p>
     * <p>
     * Set a non-empty set of competitionDocuments.
     * </p>
     */
    public void testSetCompetitionDocuments_accuracy() {
        Set<CompetitionDocument> competitionDocuments = new HashSet<CompetitionDocument>();
        competitionDocuments.add(new CompetitionDocument());

        documentVersion.setCompetitionDocuments(competitionDocuments);
        competitionDocuments = documentVersion.getCompetitionDocuments();
        assertEquals("There should be 1 CompetitionDocument.", 1, competitionDocuments.size());

        documentVersion.setCompetitionDocuments(competitionDocuments);
        competitionDocuments = documentVersion.getCompetitionDocuments();
        assertEquals("There should be 1 CompetitionDocument.", 1, competitionDocuments.size());
    }
}
