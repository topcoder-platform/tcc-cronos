/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link FilePath} class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class FilePathTest extends TestCase {

    /**
     * Represents the <code>FilePath</code> instance to test.
     */
    private FilePath filePath = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        filePath = new FilePath();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        filePath = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(FilePathTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link FilePath#FilePath()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_FilePath() {
        // check for null
        assertNotNull("FilePath creation failed", filePath);
    }

    /**
     * <p>
     * Accuracy test for {@link FilePath#getFilePathId()} and {@link FilePath#setFilePathId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getFilePathId() {
        // set the value to test
        filePath.setFilePathId(new Long(1));
        assertEquals("getFilePathId and setFilePathId failure occured", new Long(1), filePath.getFilePathId());
    }

    /**
     * <p>
     * Accuracy test for {@link FilePath#setFilePathId(Long)} and {@link FilePath#getFilePathId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setFilePathId() {
        // set the value to test
        filePath.setFilePathId(1L);
        assertEquals("getFilePathId and setFilePathId failure occured", 1L, (long) filePath.getFilePathId());
    }

    /**
     * <p>
     * Accuracy test for {@link FilePath#getPath()} and {@link FilePath#setPath(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPath() {
        // set the value to test
        filePath.setPath(null);
        assertEquals("getPath and setPath failure occured", null, filePath.getPath());
    }

    /**
     * <p>
     * Accuracy test for {@link FilePath#setPath(String)} and {@link FilePath#getPath()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setPath() {
        // set the value to test
        filePath.setPath("test");
        assertEquals("getPath and setPath failure occured", "test", filePath.getPath());
    }

    /**
     * <p>
     * Accuracy test for {@link FilePath#getModifyDate()} and {@link FilePath#setModifyDate(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getModifyDate() {
        // set the value to test
        filePath.setModifyDate(null);
        assertEquals("getModifyDate and setModifyDate failure occured", null, filePath.getPath());
    }

    /**
     * <p>
     * Accuracy test for {@link FilePath#getModifyDate()} and {@link FilePath#setModifyDate(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is current date.
     * </p>
     */
    public void test_accuracy_setModifyDate() {
        // set the value to test
        Date now = new Date();
        filePath.setModifyDate(now);
        assertEquals("getModifyDate and setModifyDate failure occured", now, filePath.getModifyDate());
    }

    /**
     * <p>
     * Accuracy test for {@link FilePath#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        FilePath path = new FilePath();
        path.setFilePathId(1L);
        filePath.setFilePathId(1L);
        assertTrue("failed equals", path.equals(filePath));
        assertTrue("failed hashCode", path.hashCode() == filePath.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link FilePath#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        FilePath path = new FilePath();
        path.setFilePathId(1L);
        filePath.setFilePathId(2L);
        assertFalse("failed equals", path.equals(filePath));
        assertFalse("failed hashCode", path.hashCode() == filePath.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link FilePath#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object path = new Object();
        filePath.setFilePathId(2L);
        assertFalse("failed equals", filePath.equals(path));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link FilePath}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            FilePath entity = new FilePath();
            entity.setModifyDate(new Date());
            entity.setPath("path");

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            FilePath persisted = (FilePath) HibernateUtil.getManager()
                    .find(FilePath.class, entity.getFilePathId());
            assertEquals("Failed to persist - path mismatch", entity.getPath(), persisted.getPath());
            assertEquals("Failed to persist - modifyDate mismatch", entity.getModifyDate(), persisted
                    .getModifyDate());

            // update the entity
            entity.setPath("new path");
            HibernateUtil.getManager().merge(entity);

            persisted = (FilePath) HibernateUtil.getManager().find(FilePath.class, entity.getFilePathId());
            assertEquals("Failed to update - path mismatch", entity.getPath(), persisted.getPath());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
