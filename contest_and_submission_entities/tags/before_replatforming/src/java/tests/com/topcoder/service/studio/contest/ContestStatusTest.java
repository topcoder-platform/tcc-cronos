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
 * Tests the functionality of {@link ContestStatus} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class ContestStatusTest extends TestCase {

    /**
     * Represents the <code>ContestStatus</code> instance to test.
     */
    private ContestStatus contestStatus = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        contestStatus = new ContestStatus();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        contestStatus = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestStatusTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestStatus#getContestStatusId()} and
     * {@link ContestStatus#setContestStatusId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getContestStatusId() {
        // set the value to test
        contestStatus.setContestStatusId(new Long(1));
        assertEquals("getContestStatusId and setContestStatusId failure occured", new Long(1), contestStatus
                .getContestStatusId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestStatus#setContestStatusId(Long)} and
     * {@link ContestStatus#getContestStatusId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContestStatusId() {
        // set the value to test
        contestStatus.setContestStatusId(1L);
        assertEquals("getContestStatusId and setContestStatusId failure occured", 1L, (long) contestStatus
                .getContestStatusId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestStatus#getDescription()} and {@link ContestStatus#setDescription(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDescription() {
        // set the value to test
        contestStatus.setDescription(null);
        assertEquals("getDescription and setDescription failure occured", null, contestStatus.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestStatus#setDescription(String)} and {@link ContestStatus#getDescription()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        contestStatus.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", contestStatus.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestStatus#getStatuses()} and
     * {@link ContestStatus#setStatuses(List)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getStatuses() {
        // set the value to test
        contestStatus.setStatuses(null);
        assertEquals("getStatuses and setStatuses failure occured", null, contestStatus.getStatuses());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestStatus#setStatuses(List)} and
     * {@link ContestStatus#getStatuses()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setStatuses() {
        // set the value to test
        List<ContestStatus> stats = new ArrayList<ContestStatus>();
        stats.add(new ContestStatus());
        contestStatus.setStatuses(stats);
        assertEquals("getStatuses and setStatuses failure occured", stats.size(), contestStatus.getStatuses()
                .size());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestStatus#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestStatus status = new ContestStatus();
        status.setContestStatusId(1L);
        contestStatus.setContestStatusId(1L);
        assertTrue("failed equals", status.equals(contestStatus));
        assertTrue("failed hashCode", status.hashCode() == contestStatus.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestStatus#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        ContestStatus status = new ContestStatus();
        status.setContestStatusId(2L);
        contestStatus.setContestStatusId(1L);
        assertFalse("failed equals", status.equals(contestStatus));
        assertFalse("failed hashCode", status.hashCode() == contestStatus.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestStatus#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_9() {
        Object status = new Object();
        contestStatus.setContestStatusId(1L);
        assertFalse("failed equals", contestStatus.equals(status));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ContestStatus}</code>.
     * </p>
     */
    public void test_persistence() {
        try {
            HibernateUtil.getManager().getTransaction().begin();
            ContestStatus entity = new ContestStatus();
            entity.setDescription("description");
            entity.setName("Name");
            entity.setStatusId(1L);
            entity.setContestStatusId(10L);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            ContestStatus persisted = (ContestStatus) HibernateUtil.getManager().find(ContestStatus.class,
                    entity.getContestStatusId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());
            assertEquals("Failed to persist - name mismatch", entity.getName(), persisted
                    .getName());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (ContestStatus) HibernateUtil.getManager().find(ContestStatus.class,
                    entity.getContestStatusId());
            assertEquals("Failed to update - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
