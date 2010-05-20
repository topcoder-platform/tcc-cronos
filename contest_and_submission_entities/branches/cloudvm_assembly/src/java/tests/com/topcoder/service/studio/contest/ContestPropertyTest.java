/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link ContestProperty} class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class ContestPropertyTest extends TestCase {

    /**
     * Represents the <code>ContestProperty</code> instance to test.
     */
    private ContestProperty property = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        property = new ContestProperty();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        property = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestPropertyTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestProperty#ContestProperty()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_ContestProperty() {
        // check for null
        assertNotNull("ContestProperty creation failed", property);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestProperty#getPropertyId()} and {@link ContestProperty#setPropertyId(Long)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getPropertyId() {
        // set the value to test
        property.setPropertyId(new Long(1));
        assertEquals("getPropertyId and setPropertyId failure occured", 1L, (long) property.getPropertyId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestProperty#getDescription()} and
     * {@link ContestProperty#setDescription(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDescription() {
        // set the value to test
        property.setDescription(null);
        assertEquals("getDescription and setDescription failure occured", null, property.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestProperty#setDescription(String)} and
     * {@link ContestProperty#getDescription()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        property.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", property.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestProperty#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestProperty prizeType1 = new ContestProperty();
        prizeType1.setPropertyId(1L);
        property.setPropertyId(1L);
        assertTrue("failed equals", prizeType1.equals(property));
        assertTrue("failed hashCode", prizeType1.hashCode() == property.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestProperty#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        ContestProperty prizeType1 = new ContestProperty();
        prizeType1.setPropertyId(2L);
        property.setPropertyId(1L);
        assertFalse("failed equals", prizeType1.equals(property));
        assertFalse("failed hashCode", prizeType1.hashCode() == property.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestProperty#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object prizeType1 = new Object();
        property.setPropertyId(1L);
        assertFalse("failed equals", property.equals(prizeType1));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ContestProperty}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            ContestProperty entity = new ContestProperty();
            entity.setDescription("description");

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            ContestProperty persisted = (ContestProperty) HibernateUtil.getManager().find(ContestProperty.class,
                    entity.getPropertyId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (ContestProperty) HibernateUtil.getManager().find(ContestProperty.class,
                    entity.getPropertyId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
