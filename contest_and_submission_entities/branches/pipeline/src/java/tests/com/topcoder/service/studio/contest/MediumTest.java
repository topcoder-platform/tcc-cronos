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
 * Tests the functionality of {@link Medium} class.
 * </p>
 *
 * @author TCSDEVELOPER, TCSDEVELOPER
 * @version 1.2
 */
public class MediumTest extends TestCase {

    /**
     * Represents the <code>Medium</code> instance to test.
     */
    private Medium medium = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        medium = new Medium();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        medium = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(MediumTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link Medium#Medium()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_Medium() {
        // check for null
        assertNotNull("Medium creation failed", medium);
    }

    /**
     * <p>
     * Accuracy test for {@link Medium#getMediumId()} and {@link Medium#setMediumId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getMediumId() {
        // set the value to test
        medium.setMediumId(new Long(1));
        assertEquals("getMediumId and setMediumId failure occured", new Long(1), medium.getMediumId());
    }

    /**
     * <p>
     * Accuracy test for {@link Medium#setMediumId(Long)} and {@link Medium#getMediumId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setMediumId() {
        // set the value to test
        medium.setMediumId(1L);
        assertEquals("getMediumId and setMediumId failure occured", 1L, (long) medium.getMediumId());
    }

    /**
     * <p>
     * Accuracy test for {@link Medium#getDescription()} and {@link Medium#setDescription(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDescription() {
        // set the value to test
        medium.setDescription(null);
        assertEquals("getDescription and setDescription failure occured", null, medium.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link Medium#setDescription(String)} and {@link Medium#getDescription()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        medium.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", medium.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link Medium#getContests()} and {@link Medium#setContests(Set)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getContests() {
        // set the value to test
        medium.setContests(null);
        assertEquals("getContests and setContests failure occured", null, medium.getContests());
    }

    /**
     * <p>
     * Accuracy test for {@link Medium#setContests(Set)} and {@link Medium#getContests()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContests() {
        // set the value to test
        Set<Contest> contests = new HashSet<Contest>();
        contests.add(new Contest());

        medium.setContests(contests);
        assertEquals("getContests and setContests failure occured", contests, medium.getContests());
    }

    /**
     * <p>
     * Accuracy test for {@link Medium#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        Medium prizeType1 = new Medium();
        prizeType1.setMediumId(1L);
        medium.setMediumId(1L);
        assertTrue("failed equals", prizeType1.equals(medium));
        assertTrue("failed hashCode", prizeType1.hashCode() == medium.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link Medium#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        Medium prizeType1 = new Medium();
        prizeType1.setMediumId(2L);
        medium.setMediumId(1L);
        assertFalse("failed equals", prizeType1.equals(medium));
        assertFalse("failed hashCode", prizeType1.hashCode() == medium.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link Medium#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object prizeType1 = new Object();
        medium.setMediumId(1L);
        assertFalse("failed equals", medium.equals(prizeType1));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link Medium}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            Medium entity = new Medium();
            entity.setDescription("description");
            entity.setMediumId(10L);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            Medium persisted = (Medium) HibernateUtil.getManager().find(Medium.class,
                    entity.getMediumId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (Medium) HibernateUtil.getManager().find(Medium.class, entity.getMediumId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
