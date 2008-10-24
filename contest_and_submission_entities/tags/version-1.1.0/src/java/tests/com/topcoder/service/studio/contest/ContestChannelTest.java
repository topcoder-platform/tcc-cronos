/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link ContestChannel} class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class ContestChannelTest extends TestCase {

    /**
     * Represents the <code>ContestChannel</code> instance to test.
     */
    private ContestChannel contestChannel = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        contestChannel = new ContestChannel();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        contestChannel = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestChannelTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#ContestCategory()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_ContestCategory() {
        // check for null
        assertNotNull("ContestCategory creation failed", contestChannel);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#getContestChannelId()} and
     * {@link ContestChannel#setContestChannelId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getContestCategoryId() {
        // set the value to test
        contestChannel.setContestChannelId(new Long(1));
        assertEquals("getContestCategoryId and setContestCategoryId failure occured", new Long(1), contestChannel
                .getContestChannelId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#setContestChannelId(Long)} and
     * {@link ContestChannel#getContestChannelId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContestCategoryId() {
        // set the value to test
        contestChannel.setContestChannelId(1L);
        assertEquals("getContestCategoryId and setContestCategoryId failure occured", 1L, (long) contestChannel
                .getContestChannelId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#getDescription()} and
     * {@link ContestChannel#setDescription(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDescription() {
        // set the value to test
        contestChannel.setDescription(null);
        assertEquals("getDescription and setDescription failure occured", null, contestChannel.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#setDescription(String)} and
     * {@link ContestChannel#getDescription()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        contestChannel.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", contestChannel.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#getName()} and {@link ContestChannel#setName(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getName() {
        // set the value to test
        contestChannel.setName(null);
        assertEquals("getName and setName failure occured", null, contestChannel.getName());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#setName(String)} and {@link ContestChannel#getName()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setName() {
        // set the value to test
        contestChannel.setName("test");
        assertEquals("getName and setName failure occured", "test", contestChannel.getName());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#getParentChannelId()} and
     * {@link ContestChannel#setParentChannelId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getParentCategoryId() {
        // set the value to test
        contestChannel.setParentChannelId(new Long(1));
        assertEquals("getParentCategoryId and setParentCategoryId failure occured", new Long(1), contestChannel
                .getParentChannelId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#setParentChannelId(Long)} and
     * {@link ContestChannel#getParentChannelId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setParentCategoryId() {
        // set the value to test
        contestChannel.setParentChannelId(1L);
        assertEquals("getParentCategoryId and setParentCategoryId failure occured", 1L, (long) contestChannel
                .getParentChannelId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#getFileType()} and
     * {@link ContestChannel#setFileType(StudioFileType)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getFileType() {
        // set the value to test
        contestChannel.setFileType(null);
        assertEquals("getFileType and setFileType failure occured", null, contestChannel.getFileType());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#setFileType(StudioFileType)} and
     * {@link ContestChannel#getFileType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setFileType() {
        // set the value to test
        StudioFileType type = new StudioFileType();
        type.setStudioFileType(1L);
        contestChannel.setFileType(type);
        assertEquals("getFileType and setFileType failure occured", type.getStudioFileType(), contestChannel
                .getFileType().getStudioFileType());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestChannel category = new ContestChannel();
        category.setContestChannelId(1L);
        contestChannel.setContestChannelId(1L);
        assertTrue("failed equals", category.equals(contestChannel));
        assertTrue("failed hashCode", category.hashCode() == contestChannel.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        ContestChannel category = new ContestChannel();
        category.setContestChannelId(2L);
        contestChannel.setContestChannelId(1L);
        assertFalse("failed equals", category.equals(contestChannel));
        assertFalse("failed hashCode", category.hashCode() == contestChannel.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object category = new Object();
        contestChannel.setContestChannelId(1L);
        assertFalse("failed equals", contestChannel.equals(category));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ContestChannel}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            StudioFileType type = new StudioFileType();
            TestHelper.populateStudioFileType(type);
            HibernateUtil.getManager().persist(type);

            ContestChannel entity = new ContestChannel();
            TestHelper.populateContestCategory(entity, type);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            ContestChannel persisted = (ContestChannel) HibernateUtil.getManager().find(ContestChannel.class,
                    entity.getContestChannelId());

            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());
            assertEquals("Failed to persist - fileType mismatch", entity.getFileType(), persisted.getFileType());
            assertEquals("Failed to persist - name mismatch", entity.getName(), persisted.getName());
            assertEquals("Failed to persist - parent category id mismatch", entity.getParentChannelId(),
                    persisted.getParentChannelId());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (ContestChannel) HibernateUtil.getManager().find(ContestChannel.class,
                    entity.getContestChannelId());

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
