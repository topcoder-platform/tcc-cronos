/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
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
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
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
     * Accuracy test for {@link ContestChannel#ContestChannel()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_ContestChannel() {
        // check for null
        assertNotNull("ContestChannel creation failed", contestChannel);
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
    public void test_accuracy_getContestChannelId() {
        // set the value to test
        contestChannel.setContestChannelId(new Long(1));
        assertEquals("getContestChannelId and setContestChannelId failure occured", new Long(1), contestChannel
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
    public void test_accuracy_setContestChannelId() {
        // set the value to test
        contestChannel.setContestChannelId(1L);
        assertEquals("getContestChannelId and setContestChannelId failure occured", 1L, (long) contestChannel
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
     * Accuracy test for {@link ContestChannel#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestChannel channel = new ContestChannel();
        channel.setContestChannelId(1L);
        contestChannel.setContestChannelId(1L);
        assertTrue("failed equals", channel.equals(contestChannel));
        assertTrue("failed hashCode", channel.hashCode() == contestChannel.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        ContestChannel channel = new ContestChannel();
        channel.setContestChannelId(2L);
        contestChannel.setContestChannelId(1L);
        assertFalse("failed equals", channel.equals(contestChannel));
        assertFalse("failed hashCode", channel.hashCode() == contestChannel.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChannel#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object channel = new Object();
        contestChannel.setContestChannelId(1L);
        assertFalse("failed equals", contestChannel.equals(channel));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ContestChannel}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();

            ContestChannel entity = new ContestChannel();
            TestHelper.populateContestChannel(entity);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            ContestChannel persisted = (ContestChannel) HibernateUtil.getManager().find(ContestChannel.class,
                    entity.getContestChannelId());

            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (ContestChannel) HibernateUtil.getManager().find(ContestChannel.class,
                    entity.getContestChannelId());

            assertEquals("Failed to update - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
