/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link StudioFileType} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class StudioFileTypeTest extends TestCase {

    /**
     * Represents the <code>StudioFileType</code> instance to test.
     */
    private StudioFileType studioFileType = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        studioFileType = new StudioFileType();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        studioFileType = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(StudioFileTypeTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link StudioFileType#StudioFileType()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_StudioFileType() {
        // check for null
        assertNotNull("StudioFileType creation failed", studioFileType);
    }

    /**
     * <p>
     * Accuracy test for {@link StudioFileType#getStudioFileType()} and
     * {@link StudioFileType#setStudioFileType(long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is 0.
     * </p>
     */
    public void test_accuracy_getStudioFileType() {
        // set the value to test
        studioFileType.setStudioFileType(0);
        assertEquals("getStudioFileType and setStudioFileType failure occured", 0, studioFileType
                .getStudioFileType());
    }

    /**
     * <p>
     * Accuracy test for {@link StudioFileType#setStudioFileType(long)} and
     * {@link StudioFileType#getStudioFileType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is 1.
     * </p>
     */
    public void test_accuracy_setStudioFileType() {
        // set the value to test
        studioFileType.setStudioFileType(1);
        assertEquals("getStudioFileType and setStudioFileType failure occured", 1, studioFileType
                .getStudioFileType());
    }

    /**
     * <p>
     * Accuracy test for {@link StudioFileType#getMimeTypes()} and
     * {@link StudioFileType#setMimeTypes(Set)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getMimeTypes() {
        // set the value to test
        studioFileType.setMimeTypes(null);
        assertEquals("getMimeTypes and setMimeTypes failure occured", null, studioFileType.getMimeTypes());
    }

    /**
     * <p>
     * Accuracy test for {@link StudioFileType#setMimeTypes(Set)} and
     * {@link StudioFileType#getMimeTypes()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setMimeTypes() {
        // set the value to test
        Set<MimeType> types = new HashSet<MimeType>();
        types.add(new MimeType());
        studioFileType.setMimeTypes(types);
        assertEquals("getMimeTypes and setMimeTypes failure occured", types.size(), studioFileType.getMimeTypes()
                .size());
    }

    /**
     * <p>
     * Accuracy test for {@link StudioFileType#getSort()} and {@link StudioFileType#setSort(Integer)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getSort() {
        // set the value to test
        studioFileType.setSort(null);
        assertEquals("getSort and setSort failure occured", null, studioFileType.getSort());
    }

    /**
     * <p>
     * Accuracy test for {@link StudioFileType#setSort(Integer)} and {@link StudioFileType#getSort()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setSort() {
        // set the value to test
        studioFileType.setSort(1);
        assertEquals("getSort and setSort failure occured", 1, (int) studioFileType.getSort());
    }

    /**
     * <p>
     * Accuracy test for {@link StudioFileType#isImageFile()} and {@link StudioFileType#setImageFile(Boolean)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_isImageFile() {
        // set the value to test
        studioFileType.setImageFile(null);
        assertEquals("isImageFile and setImageFile failure occured", null, studioFileType.isImageFile());
    }

    /**
     * <p>
     * Accuracy test for {@link StudioFileType#setImageFile(Boolean)} and {@link StudioFileType#isImageFile()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setImageFile() {
        // set the value to test
        studioFileType.setImageFile(true);
        assertEquals("getImageFile and setImageFile failure occured", true, (boolean) studioFileType.isImageFile());
    }

    /**
     * <p>
     * Accuracy test for {@link StudioFileType#getExtension()} and {@link StudioFileType#setExtension(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getExtension() {
        // set the value to test
        studioFileType.setExtension(null);
        assertEquals("getExtension and setExtension failure occured", null, studioFileType.getExtension());
    }

    /**
     * <p>
     * Accuracy test for {@link StudioFileType#setExtension(String)} and {@link StudioFileType#getExtension()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setExtension() {
        // set the value to test
        studioFileType.setExtension("test");
        assertEquals("getExtension and setExtension failure occured", "test", studioFileType.getExtension());
    }

    /**
     * <p>
     * Accuracy test for {@link StudioFileType#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        StudioFileType type = new StudioFileType();
        type.setStudioFileType(1L);
        studioFileType.setStudioFileType(1L);
        assertTrue("failed equals", type.equals(studioFileType));
        assertTrue("failed hashCode", type.hashCode() == studioFileType.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link MimeType#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        StudioFileType type = new StudioFileType();
        type.setStudioFileType(1L);
        studioFileType.setStudioFileType(2L);
        assertFalse("failed equals", type.equals(studioFileType));
        assertFalse("failed hashCode", type.hashCode() == studioFileType.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link MimeType#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object type = new Object();
        studioFileType.setStudioFileType(2L);
        assertFalse("failed equals", studioFileType.equals(type));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link StudioFileType}</code>.
     * </p>
     */
    public void test_persistence() {
        try {
            HibernateUtil.getManager().getTransaction().begin();

            StudioFileType entity = new StudioFileType();
            TestHelper.populateStudioFileType(entity);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            StudioFileType persisted = (StudioFileType) HibernateUtil.getManager().find(StudioFileType.class,
                    entity.getStudioFileType());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());
            assertEquals("Failed to persist - extension mismatch", entity.getExtension(), persisted.getExtension());
            assertEquals("Failed to persist - sort mismatch", entity.getSort(), persisted.getSort());
            assertEquals("Failed to persist - imageFile mismatch", entity.isImageFile(), persisted.isImageFile());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (StudioFileType) HibernateUtil.getManager().find(StudioFileType.class,
                    entity.getStudioFileType());
            assertEquals("Failed to update - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
