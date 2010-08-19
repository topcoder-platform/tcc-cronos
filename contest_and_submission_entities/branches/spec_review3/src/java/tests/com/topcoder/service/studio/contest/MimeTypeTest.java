/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link MimeType} class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class MimeTypeTest extends TestCase {

    /**
     * Represents the <code>MimeType</code> instance to test.
     */
    private MimeType mimeType = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        mimeType = new MimeType();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        mimeType = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(MimeTypeTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link MimeType#MimeType()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_MimeType() {
        // check for null
        assertNotNull("MimeType creation failed", mimeType);
    }

    /**
     * <p>
     * Accuracy test for {@link MimeType#getMimeTypeId()} and {@link MimeType#setMimeTypeId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getMimeTypeId() {
        // set the value to test
        mimeType.setMimeTypeId(new Long(1));
        assertEquals("getMimeTypeId and setMimeTypeId failure occured", new Long(1), mimeType.getMimeTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link MimeType#setMimeTypeId(Long)} and {@link MimeType#getMimeTypeId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setMimeTypeId() {
        // set the value to test
        mimeType.setMimeTypeId(1L);
        assertEquals("getMimeTypeId and setMimeTypeId failure occured", 1L, (long) mimeType.getMimeTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link MimeType#getDescription()} and {@link MimeType#setDescription(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDescription() {
        // set the value to test
        mimeType.setDescription(null);
        assertEquals("getDescription and setDescription failure occured", null, mimeType.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link MimeType#setDescription(String)} and {@link MimeType#getDescription()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        mimeType.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", mimeType.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link MimeType#getStudioFileType()} and
     * {@link MimeType#setStudioFileType(StudioFileType)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getStudioFileType() {
        // set the value to test
        mimeType.setStudioFileType(null);
        assertEquals("getStudioFileType and setStudioFileType failure occured", null, mimeType.getStudioFileType());
    }

    /**
     * <p>
     * Accuracy test for {@link MimeType#setStudioFileType(StudioFileType)} and
     * {@link MimeType#getStudioFileType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setStudioFileType() {
        // set the value to test
        StudioFileType type = new StudioFileType();
        type.setStudioFileType(1L);
        mimeType.setStudioFileType(type);
        assertEquals("getStudioFileType and setStudioFileType failure occured", type.getStudioFileType(), mimeType
                .getStudioFileType().getStudioFileType());
    }

    /**
     * <p>
     * Accuracy test for {@link MimeType#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        MimeType type = new MimeType();
        type.setMimeTypeId(1L);
        mimeType.setMimeTypeId(1L);
        assertTrue("failed equals", type.equals(mimeType));
        assertTrue("failed hashCode", type.hashCode() == mimeType.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link MimeType#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        MimeType type = new MimeType();
        type.setMimeTypeId(2L);
        mimeType.setMimeTypeId(1L);
        assertFalse("failed equals", type.equals(mimeType));
        assertFalse("failed hashCode", type.hashCode() == mimeType.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link MimeType#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object type = new Object();
        mimeType.setMimeTypeId(1L);
        assertFalse("failed equals", mimeType.equals(type));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link MimeType}</code>.
     * </p>
     */
    public void test_persistence() {
        try {
            HibernateUtil.getManager().getTransaction().begin();
            StudioFileType type = new StudioFileType();
            TestHelper.populateStudioFileType(type);
            HibernateUtil.getManager().persist(type);

            MimeType entity = new MimeType();
            entity.setMimeTypeId(1L);
            entity.setDescription("description");
            entity.setStudioFileType(type);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            MimeType persisted = (MimeType) HibernateUtil.getManager().find(MimeType.class,
                    new Long(entity.getMimeTypeId()));
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());
            assertEquals("Failed to persist - studioFileType mismatch", entity.getStudioFileType(), persisted
                    .getStudioFileType());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (MimeType) HibernateUtil.getManager().find(MimeType.class,
                    new Long(entity.getMimeTypeId()));
            assertEquals("Failed to update - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
            HibernateUtil.getManager().remove(type);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
