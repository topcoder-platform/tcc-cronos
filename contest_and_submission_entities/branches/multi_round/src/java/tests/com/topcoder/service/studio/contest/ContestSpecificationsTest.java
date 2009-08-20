/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link ContestSpecifications} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since 1.2
 */
public class ContestSpecificationsTest extends TestCase {

    /**
     * Represents the <code>ContestSpecifications</code> instance to test.
     */
    private ContestSpecifications contestSpecifications = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        contestSpecifications = new ContestSpecifications();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        contestSpecifications = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestSpecificationsTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestSpecifications#ContestSpecifications()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_PrizeType() {
        // check for null
        assertNotNull("ContestSpecifications creation failed", contestSpecifications);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestSpecifications#getContestSpecificationsId()} and
     * {@link ContestSpecifications#setContestSpecificationsId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getContestSpecificationsId() {
        // set the value to test
        contestSpecifications.setContestSpecificationsId(new Long(1));
        assertEquals("getContestSpecificationsId and setContestSpecificationsId failure occured", new Long(1),
                contestSpecifications.getContestSpecificationsId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestSpecifications#setContestSpecificationsId(Long)} and
     * {@link ContestSpecifications#getContestSpecificationsId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContestSpecificationsId() {
        // set the value to test
        contestSpecifications.setContestSpecificationsId(1L);
        assertEquals("getContestSpecificationsId and setContestSpecificationsId failure occured", 1L,
                (long) contestSpecifications.getContestSpecificationsId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestSpecifications#getColors()} and {@link ContestSpecifications#setColors(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getColors() {
        // set the value to test
        contestSpecifications.setColors(null);
        assertEquals("getColors and setColors failure occured", null, contestSpecifications.getColors());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestSpecifications#setColors(String)} and {@link ContestSpecifications#getColors()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setColors() {
        // set the value to test
        contestSpecifications.setColors("test");
        assertEquals("getColors and setColors failure occured", "test", contestSpecifications.getColors());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestSpecifications#getFonts()} and {@link ContestSpecifications#setFonts(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getFonts() {
        // set the value to test
        contestSpecifications.setFonts(null);
        assertEquals("getFonts and setFonts failure occured", null, contestSpecifications.getFonts());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestSpecifications#setFonts(String)} and {@link ContestSpecifications#getFonts()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setFonts() {
        // set the value to test
        contestSpecifications.setFonts("test");
        assertEquals("getFonts and setFonts failure occured", "test", contestSpecifications.getFonts());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestSpecifications#setLayoutAndSize(String)} and
     * {@link ContestSpecifications#getLayoutAndSize()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setLayoutAndSize() {
        // set the value to test
        contestSpecifications.setLayoutAndSize("test");
        assertEquals("getLayoutAndSize and setLayoutAndSize failure occured", "test", contestSpecifications
                .getLayoutAndSize());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestSpecifications#getLayoutAndSize()} and
     * {@link ContestSpecifications#setLayoutAndSize(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getLayoutAndSize() {
        // set the value to test
        contestSpecifications.setLayoutAndSize(null);
        assertEquals("getLayoutAndSize and setLayoutAndSize failure occured", null, contestSpecifications
                .getLayoutAndSize());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestSpecifications#getAdditionalRequirementsAndRestrictions()} and
     * {@link ContestSpecifications#setAdditionalRequirementsAndRestrictions(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getAdditionalRequirementsAndRestrictions() {
        // set the value to test
        contestSpecifications.setAdditionalRequirementsAndRestrictions(null);
        assertEquals(
                "getAdditionalRequirementsAndRestrictions and setAdditionalRequirementsAndRestrictions failure occured",
                null, contestSpecifications.getAdditionalRequirementsAndRestrictions());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestSpecifications#setAdditionalRequirementsAndRestrictions(String)} and
     * {@link ContestSpecifications#getAdditionalRequirementsAndRestrictions()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setAdditionalRequirementsAndRestrictions() {
        // set the value to test
        contestSpecifications.setAdditionalRequirementsAndRestrictions("test");
        assertEquals(
                "getAdditionalRequirementsAndRestrictions and setAdditionalRequirementsAndRestrictions failure occured",
                "test", contestSpecifications.getAdditionalRequirementsAndRestrictions());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestSpecifications#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestSpecifications resource = new ContestSpecifications();
        resource.setContestSpecificationsId(1L);
        contestSpecifications.setContestSpecificationsId(1L);
        assertTrue("equals", resource.equals(contestSpecifications));
        assertTrue("hashCode", resource.hashCode() == contestSpecifications.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestSpecifications#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        ContestSpecifications resource = new ContestSpecifications();
        resource.setContestSpecificationsId(2L);
        contestSpecifications.setContestSpecificationsId(1L);
        assertFalse("failed equals", resource.equals(contestSpecifications));
        assertFalse("failed hashCode", resource.hashCode() == contestSpecifications.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestSpecifications#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object specifications = new Object();
        contestSpecifications.setContestSpecificationsId(1L);
        assertFalse("failed equals", contestSpecifications.equals(specifications));
    }

    /**
     * <p>
     * Persistence tests for the entity
     * <code>{@link ContestSpecifications}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            ContestSpecifications entity = new ContestSpecifications();
            entity.setLayoutAndSize("abc");
            entity.setAdditionalRequirementsAndRestrictions("abc");
            entity.setColors("abc");
            entity.setFonts("abc");

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            ContestSpecifications persisted =
                HibernateUtil.getManager().find(ContestSpecifications.class,
                    entity.getContestSpecificationsId());
            assertEquals("Failed to persist - layoutAndSize mismatch", entity
                .getLayoutAndSize(), persisted.getLayoutAndSize());
            assertEquals(
                "Failed to persist - additionalRequirementsAndRestrictions mismatch",
                entity.getAdditionalRequirementsAndRestrictions(), persisted
                    .getAdditionalRequirementsAndRestrictions());
            assertEquals("Failed to persist - colors mismatch", entity
                .getColors(), persisted.getColors());
            assertEquals("Failed to persist - fonts mismatch", entity
                .getFonts(), persisted.getFonts());

            // update the entity
            entity.setLayoutAndSize("new");

            HibernateUtil.getManager().merge(entity);

            persisted =
                HibernateUtil.getManager().find(ContestSpecifications.class,
                    entity.getContestSpecificationsId());
            assertEquals("Failed to update - brandingGuidelines mismatch",
                entity.getLayoutAndSize(), persisted.getLayoutAndSize());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
