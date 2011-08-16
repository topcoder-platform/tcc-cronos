/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link DocumentType} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class DocumentTypeTest extends TestCase {

    /**
     * Represents the <code>DocumentType</code> instance to test.
     */
    private DocumentType documentType = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        documentType = new DocumentType();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        documentType = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(DocumentTypeTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link DocumentType#DocumentType()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_DocumentType() {
        // check for null
        assertNotNull("DocumentType creation failed", documentType);
    }

    /**
     * <p>
     * Accuracy test for {@link DocumentType#getDocumentTypeId()} and {@link DocumentType#setDocumentTypeId(Long)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getDocumentTypeId() {
        // set the value to test
        documentType.setDocumentTypeId(new Long(1));
        assertEquals("getDocumentTypeId and setDocumentTypeId failure occured", new Long(1), documentType
                .getDocumentTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link DocumentType#setDocumentTypeId(Long)} and {@link DocumentType#getDocumentTypeId()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setDocumentTypeId() {
        // set the value to test
        documentType.setDocumentTypeId(1L);
        assertEquals("getDocumentTypeId and setDocumentTypeId failure occured", 1L, (long) documentType
                .getDocumentTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link DocumentType#getDescription()} and {@link DocumentType#setDescription(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDescription() {
        // set the value to test
        documentType.setDescription(null);
        assertEquals("getDescription and setDescription failure occured", null, documentType.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link DocumentType#setDescription(String)} and {@link DocumentType#getDescription()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        documentType.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", documentType.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link DocumentType#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        DocumentType type = new DocumentType();
        type.setDocumentTypeId(1L);
        documentType.setDocumentTypeId(1L);
        assertTrue("failed equals", type.equals(documentType));
        assertTrue("failed hashCode", type.hashCode() == documentType.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link DocumentType#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        DocumentType type = new DocumentType();
        type.setDocumentTypeId(2L);
        documentType.setDocumentTypeId(1L);
        assertFalse("failed equals", type.equals(documentType));
        assertFalse("failed hashCode", type.hashCode() == documentType.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link DocumentType#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object type = new Object();
        documentType.setDocumentTypeId(1L);
        assertFalse("failed equals", documentType.equals(type));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link DocumentType}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            DocumentType entity = new DocumentType();
            entity.setDescription("description");
            entity.setDocumentTypeId(1L);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            DocumentType persisted = (DocumentType) HibernateUtil.getManager().find(DocumentType.class,
                    entity.getDocumentTypeId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (DocumentType) HibernateUtil.getManager().find(DocumentType.class,
                    entity.getDocumentTypeId());
            assertEquals("Failed to update - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
