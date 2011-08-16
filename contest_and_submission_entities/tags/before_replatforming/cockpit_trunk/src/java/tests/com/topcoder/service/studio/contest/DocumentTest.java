/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link Document} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class DocumentTest extends TestCase {

    /**
     * Represents the <code>Document</code> instance to test.
     */
    private Document document = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        document = new Document();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        document = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(DocumentTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link Document#Document()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_Document() {
        // check for null
        assertNotNull("Document creation failed", document);
    }

    /**
     * <p>
     * Accuracy test for {@link Document#getDocumentId()} and {@link Document#setDocumentId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getDocumentId() {
        // set the value to test
        document.setDocumentId(new Long(1));
        assertEquals("getDocumentId and setDocumentId failure occured", new Long(1), document.getDocumentId());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#setDocumentId(Long)} and {@link Document#getDocumentId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setDocumentId() {
        // set the value to test
        document.setDocumentId(1L);
        assertEquals("getDocumentId and setDocumentId failure occured", 1L, (long) document.getDocumentId());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#getContests()} and {@link Document#setContests(Set)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getContests() {
        // set the value to test
        document.setContests(null);
        assertEquals("getContests and setContests failure occured", null, document.getContests());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#setContests(Set)} and {@link Document#getContests()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContests() {
        // set the value to test
        Set<Contest> contests = new HashSet<Contest>();
        contests.add(new Contest());

        document.setContests(contests);
        assertEquals("getContests and setContests failure occured", contests, document.getContests());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#getOriginalFileName()} and {@link Document#setOriginalFileName(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getOriginalFileName() {
        // set the value to test
        document.setOriginalFileName(null);
        assertEquals("getOriginalFileName and setOriginalFileName failure occured", null, document
                .getOriginalFileName());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#setOriginalFileName(String)} and {@link Document#getOriginalFileName()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setOriginalFileName() {
        // set the value to test
        document.setOriginalFileName("test");
        assertEquals("getOriginalFileName and setOriginalFileName failure occured", "test", document
                .getOriginalFileName());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#getSystemFileName()} and {@link Document#setSystemFileName(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getSystemFileName() {
        // set the value to test
        document.setSystemFileName(null);
        assertEquals("getSystemFileName and setSystemFileName failure occured", null, document.getSystemFileName());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#setSystemFileName(String)} and {@link Document#getSystemFileName()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setSystemFileName() {
        // set the value to test
        document.setSystemFileName("test");
        assertEquals("getSystemFileName and setSystemFileName failure occured", "test", document
                .getSystemFileName());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#getPath()} and {@link Document#setPath(FilePath)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPath() {
        // set the value to test
        document.setPath(null);
        assertEquals("getPath and setPath failure occured", null, document.getPath());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#setPath(FilePath)} and {@link Document#getPath()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setPath() {
        // set the value to test
        FilePath path = new FilePath();
        path.setFilePathId(1L);
        document.setPath(path);
        assertEquals("getPath and setPath failure occured", path.getFilePathId(), document.getPath()
                .getFilePathId());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#getMimeType()} and {@link Document#setMimeType(MimeType)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getMimeType() {
        // set the value to test
        document.setMimeType(null);
        assertEquals("getMimeType and setMimeType failure occured", null, document.getMimeType());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#setMimeType(MimeType)} and {@link Document#getMimeType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setMimeType() {
        // set the value to test
        MimeType type = new MimeType();
        type.setMimeTypeId(1L);

        document.setMimeType(type);
        assertEquals("getMimeType and setMimeType failure occured", type.getMimeTypeId(), document.getMimeType()
                .getMimeTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#getType()} and {@link Document#setType(DocumentType)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getType() {
        // set the value to test
        document.setType(null);
        assertEquals("getType and setType failure occured", null, document.getType());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#setType(DocumentType)} and {@link Document#getType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setType() {
        // set the value to test
        DocumentType type = new DocumentType();
        type.setDocumentTypeId(1L);
        document.setType(type);
        assertEquals("getType and setType failure occured", type.getDocumentTypeId(), document.getType()
                .getDocumentTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#getCreateDate()} and {@link Document#setCreateDate(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getCreateDate() {
        // set the value to test
        document.setCreateDate(null);
        assertEquals("getCreateDate and setCreateDate failure occured", null, document.getType());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#getCreateDate()} and {@link Document#setCreateDate(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setCreateDate() {
        // set the value to test
        Date date = new Date();
        document.setCreateDate(date);
        assertEquals("getCreateDate and setCreateDate failure occured", date, document.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        Document doc = new Document();
        doc.setDocumentId(1L);
        document.setDocumentId(1L);
        assertTrue("failed equals", doc.equals(document));
        assertTrue("failed hashCode", doc.hashCode() == document.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        Document doc = new Document();
        doc.setDocumentId(2L);
        document.setDocumentId(1L);
        assertFalse("failed equals", doc.equals(document));
        assertFalse("failed hashCode", doc.hashCode() == document.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link Document#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object doc = new Object();
        document.setDocumentId(1L);
        assertFalse("failed equals", document.equals(doc));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link Document}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            FilePath path = new FilePath();
            path.setModifyDate(new Date());
            path.setPath("path");
            HibernateUtil.getManager().persist(path);

            StudioFileType studioFileType = new StudioFileType();
            studioFileType.setDescription("description");
            studioFileType.setExtension("extension");
            studioFileType.setImageFile(true);
            studioFileType.setSort(1);
            HibernateUtil.getManager().persist(studioFileType);

            MimeType mimeType = new MimeType();
            mimeType.setDescription("description");
            mimeType.setStudioFileType(studioFileType);
            mimeType.setMimeTypeId(1L);
            HibernateUtil.getManager().persist(mimeType);

            DocumentType type = new DocumentType();
            type.setDescription("description");
            type.setDocumentTypeId(1L);
            HibernateUtil.getManager().persist(type);

            Document entity = new Document();
            entity.setOriginalFileName("originalFileName");
            entity.setSystemFileName("systemFileName");
            entity.setPath(path);
            entity.setMimeType(mimeType);
            entity.setType(type);
            entity.setCreateDate(new Date());

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            Document persisted = (Document) HibernateUtil.getManager()
                    .find(Document.class, entity.getDocumentId());
            assertEquals("Failed to persist - systemFileName mismatch", entity.getSystemFileName(), persisted
                    .getSystemFileName());
            assertEquals("Failed to persist - originalFileName mismatch", entity.getOriginalFileName(), persisted
                    .getOriginalFileName());
            assertEquals("Failed to persist - path mismatch", entity.getPath(), persisted.getPath());
            assertEquals("Failed to persist - mimeType mismatch", entity.getMimeType(), persisted.getMimeType());
            assertEquals("Failed to persist - type mismatch", entity.getType(), persisted.getType());
            assertEquals("Failed to persist - createDate mismatch", entity.getCreateDate(), persisted
                    .getCreateDate());

            // update the entity
            entity.setSystemFileName("new systemFileName");
            HibernateUtil.getManager().merge(entity);

            persisted = (Document) HibernateUtil.getManager().find(Document.class, entity.getDocumentId());
            assertEquals("Failed to update - systemFileName mismatch", entity.getSystemFileName(), persisted
                    .getSystemFileName());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
            HibernateUtil.getManager().remove(path);
            HibernateUtil.getManager().remove(mimeType);
            HibernateUtil.getManager().remove(studioFileType);
            HibernateUtil.getManager().remove(type);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
