/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.accuracytests;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.service.prerequisite.document.model.DocumentName;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;

/**
 * <p>
 * Accuracy test for <code>{@link DocumentName}</code> class.
 * </p>
 *
 * @author liuliquan
 * @version 1.0
 */
public class DocumentNameAccTests extends TestCase {

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
        // explicitly set null.
        documentName = null;

        super.tearDown();
    }

    /**
     * <p>
     * Unit test for <code>DocumentName#DocumentName()</code> constructor.
     * </p>
     * <p>
     * This method simply do nothing, should always create the instance.
     * </p>
     */
    public void testDocumentNameAccuracy() {
        assertNotNull("Instance should always create.", documentName);
    }

    /**
     * <p>
     * Unit test for <code>DocumentName#getDocumentNameId()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetDocumentNameId_default() {
        assertNull("Should return null.", documentName.getDocumentNameId());
    }

    /**
     * <p>
     * Unit test for <code>DocumentName#setDocumentNameId(Long)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetDocumentNameId_notNull() {
        Long documentNameId = 1L;

        documentName.setDocumentNameId(documentNameId);

        assertEquals("The document id is not set properly.", documentNameId, documentName.getDocumentNameId());
    }

    /**
     * <p>
     * Unit test for <code>DocumentName#getName()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetName_default() {
        assertNull("Should return null.", documentName.getName());
    }

    /**
     * <p>
     * Unit test for <code>DocumentName#setName(String)</code> method.
     * </p>
     * <p>
     * If name is meaningful (not null or empty), should set internally.
     * </p>
     */
    public void testSetName_meaningful() {
        String name = "Test name";
        documentName.setName(name);

        assertEquals("The name is not set properly.", name, documentName.getName());
    }

    /**
     * <p>
     * Unit test for <code>DocumentName#getDocumentVersions()</code> method.
     * </p>
     * <p>
     * It should return empty set, if not set.
     * </p>
     */
    public void testGetDocumentVersions_default() {
        Set<DocumentVersion> documentVersions = documentName.getDocumentVersions();
        assertNotNull("It should never return null.", documentVersions);
        assertTrue("It should be empty by default.", documentVersions.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>DocumentName#setDocumentVersions(Set&lt;DocumentVersion&gt;)</code> method.
     * </p>
     * <p>
     * Set a non-empty set of documentVersions.
     * </p>
     */
    public void testSetDocumentVersions_accuracy() {
        Set<DocumentVersion> documentVersions = new HashSet<DocumentVersion>();
        documentVersions.add(new DocumentVersion());

        documentName.setDocumentVersions(documentVersions);
        documentVersions = documentName.getDocumentVersions();
        assertEquals("There should be 1 DocumentVersion.", 1, documentVersions.size());

        documentName.setDocumentVersions(documentVersions);
        documentVersions = documentName.getDocumentVersions();
        assertEquals("There should be 1 DocumentVersion.", 1, documentVersions.size());
    }
}
