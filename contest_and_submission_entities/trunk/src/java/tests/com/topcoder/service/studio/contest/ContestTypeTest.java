/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link ContestType} class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class ContestTypeTest extends TestCase {

    /**
     * Represents the <code>ContestType</code> instance to test.
     */
    private ContestType contestType = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        contestType = new ContestType();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        contestType = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestTypeTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestType#ContestType()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_ContestType() {
        // check for null
        assertNotNull("ContestType creation failed", contestType);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestType#getContestType()} and {@link ContestType#setContestType(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getContestType() {
        // set the value to test
        contestType.setContestType(new Long(1));
        assertEquals("getContestType and setContestType failure occured", new Long(1), contestType
                .getContestType());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestType#setContestType(Long)} and {@link ContestType#getContestType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContestType() {
        // set the value to test
        contestType.setContestType(1L);
        assertEquals("getContestType and setContestType failure occured", 1L, (long) contestType.getContestType());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestType#getDescription()} and {@link ContestType#setDescription(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDescription() {
        // set the value to test
        contestType.setDescription(null);
        assertEquals("getDescription and setDescription failure occured", null, contestType.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestType#setDescription(String)} and {@link ContestType#getDescription()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        contestType.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", contestType.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestType#isRequirePreviewFile()} and
     * {@link ContestType#setRequirePreviewFile(boolean)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is false.
     * </p>
     */
    public void test_accuracy_isRequirePreviewFile() {
        // set the value to test
        contestType.setRequirePreviewFile(false);
        assertEquals("isRequirePreviewFile and setRequirePreviewFile failure occured", false, contestType
                .isRequirePreviewFile());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestType#setRequirePreviewFile(boolean)} and
     * {@link ContestType#isRequirePreviewFile()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is true.
     * </p>
     */
    public void test_accuracy_setRequirePreviewFile() {
        // set the value to test
        contestType.setRequirePreviewFile(true);
        assertEquals("isRequirePreviewFile and setRequirePreviewFile failure occured", true, contestType
                .isRequirePreviewFile());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestType#isRequirePreviewImage()} and
     * {@link ContestType#setRequirePreviewImage(boolean)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is false.
     * </p>
     */
    public void test_accuracy_isRequirePreviewImage() {
        // set the value to test
        contestType.setRequirePreviewImage(false);
        assertEquals("isRequirePreviewImage and setRequirePreviewImage failure occured", false, contestType
                .isRequirePreviewImage());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestType#setRequirePreviewImage(boolean)} and
     * {@link ContestType#isRequirePreviewImage()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is true.
     * </p>
     */
    public void test_accuracy_setRequirePreviewImage() {
        // set the value to test
        contestType.setRequirePreviewImage(true);
        assertEquals("isRequirePreviewImage and setRequirePreviewImage failure occured", true, contestType
                .isRequirePreviewImage());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestType#getConfig()} and {@link ContestType#setConfig(List)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getConfig() {
        // set the value to test
        contestType.setConfig(null);
        assertEquals("getConfig and setConfig failure occured", null, contestType.getConfig());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestType#setConfig(List)} and {@link ContestType#getConfig()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setConfig() {
        // set the value to test
        List<ContestTypeConfig> config = new ArrayList<ContestTypeConfig>();
        config.add(new ContestTypeConfig());
        contestType.setConfig(config);
        assertEquals("getConfig and setConfig failure occured", config.size(), contestType.getConfig().size());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestType#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestType type = new ContestType();
        type.setContestType(1L);
        contestType.setContestType(1L);
        assertTrue("failed equals", type.equals(contestType));
        assertTrue("failed hashCode", type.hashCode() == contestType.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestType#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        ContestType type = new ContestType();
        type.setContestType(2L);
        contestType.setContestType(1L);
        assertFalse("failed equals", type.equals(contestType));
        assertFalse("failed hashCode", type.hashCode() == contestType.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestType#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object type = new Object();
        contestType.setContestType(1L);
        assertFalse("failed equals", contestType.equals(type));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ContestType}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            ContestType entity = new ContestType();
            TestHelper.populateContestType(entity);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            ContestType persisted = (ContestType) HibernateUtil.getManager().find(ContestType.class,
                    entity.getContestType());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());
            assertEquals("Failed to persist - requirePreviewFile mismatch", entity.isRequirePreviewFile(),
                    persisted.isRequirePreviewFile());
            assertEquals("Failed to persist - requirePreviewImage mismatch", entity.isRequirePreviewImage(),
                    persisted.isRequirePreviewImage());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (ContestType) HibernateUtil.getManager().find(ContestType.class, entity.getContestType());
            assertEquals("Failed to update - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
