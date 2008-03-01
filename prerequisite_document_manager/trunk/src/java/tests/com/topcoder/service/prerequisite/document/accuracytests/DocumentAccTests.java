/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.accuracytests;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.service.prerequisite.document.model.Document;
import com.topcoder.service.prerequisite.document.model.DocumentVersion;

/**
 * <p>
 * Accuracy test for <code>{@link Document}</code> class.
 * </p>
 *
 * @author liuliquan
 * @version 1.0
 */
public class DocumentAccTests extends TestCase {

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
        // explicitly set null.
        document = null;

        super.tearDown();
    }

    /**
     * <p>
     * Unit test for <code>Document#Document()</code> constructor.
     * </p>
     * <p>
     * This method simply do nothing, should always create the instance.
     * </p>
     */
    public void testDocumentAccuracy() {
        assertNotNull("Instance should always create.", document);
    }

    /**
     * <p>
     * Unit test for <code>Document#getDocumentId()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetDocumentId_default() {
        assertNull("Should return null.", document.getDocumentId());
    }

    /**
     * <p>
     * Unit test for <code>Document#setDocumentId(Long)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetDocumentId_notNull() {
        Long documentId = 1L;

        document.setDocumentId(documentId);

        assertEquals("The document id is not set properly.", documentId, document.getDocumentId());
    }

    /**
     * <p>
     * Unit test for <code>Document#getCreateDate()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetCreateDate_default() {
        assertNull("Should return null.", document.getCreateDate());
    }

    /**
     * <p>
     * Unit test for <code>Document#setCreateDate(Date)</code> method.
     * </p>
     * <p>
     * If the parameter is not null, should set internally.
     * </p>
     */
    public void testSetCreateDate_notNull() {
        Date createDate = new Date();
        document.setCreateDate(createDate);

        assertSame("The create date is not set properly.", createDate, document.getCreateDate());
    }

    /**
     * <p>
     * Unit test for <code>Document#getDescription()</code> method.
     * </p>
     * <p>
     * It should return null if not set.
     * </p>
     */
    public void testGetDescription_default() {
        assertNull("Should return null.", document.getDescription());
    }

    /**
     * <p>
     * Unit test for <code>Document#setDescription(String)</code> method.
     * </p>
     * <p>
     * null is valid.
     * </p>
     */
    public void testSetDescription_null() {
        document.setDescription(null);

        assertNull("The description should set to null.", document.getDescription());
    }

    /**
     * <p>
     * Unit test for <code>Document#setDescription(String)</code> method.
     * </p>
     * <p>
     * description can be empty.
     * </p>
     */
    public void testSetDescription_empty() {
        String desc = "";
        document.setDescription(desc);

        assertEquals("The description is not set properly.", desc, document.getDescription());
    }

    /**
     * <p>
     * Unit test for <code>Document#setDescription(String)</code> method.
     * </p>
     * <p>
     * description can be trimmed empty.
     * </p>
     */
    public void testSetDescription_trimmedEmpty() {
        String desc = "  ";
        document.setDescription(desc);

        assertEquals("The description is not set properly.", desc, document.getDescription());
    }

    /**
     * <p>
     * Unit test for <code>Document#setDescription(String)</code> method.
     * </p>
     * <p>
     * If description is meaningful (not null or empty), should set internally.
     * </p>
     */
    public void testSetDescription_meaningful() {
        String desc = "Test doc";
        document.setDescription(desc);

        assertEquals("The description is not set properly.", desc, document.getDescription());
    }

    /**
     * <p>
     * Unit test for <code>Document#getDocumentVersions()</code> method.
     * </p>
     * <p>
     * It should return empty set, if not set.
     * </p>
     */
    public void testGetDocumentVersions_default() {
        Set<DocumentVersion> documentVersions = document.getDocumentVersions();
        assertNotNull("It should never return null.", documentVersions);
        assertTrue("It should be empty by default.", documentVersions.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>Document#setDocumentVersions(Set&lt;DocumentVersion&gt;)</code> method.
     * </p>
     * <p>
     * Set a non-empty set of documentVersions.
     * </p>
     */
    public void testSetDocumentVersions_accuracy() {
        Set<DocumentVersion> documentVersions = new HashSet<DocumentVersion>();
        documentVersions.add(new DocumentVersion());

        document.setDocumentVersions(documentVersions);
        documentVersions = document.getDocumentVersions();
        assertEquals("There should be 1 DocumentVersion.", 1, documentVersions.size());

        document.setDocumentVersions(documentVersions);
        documentVersions = document.getDocumentVersions();
        assertEquals("There should be 1 DocumentVersion.", 1, documentVersions.size());
    }
}
