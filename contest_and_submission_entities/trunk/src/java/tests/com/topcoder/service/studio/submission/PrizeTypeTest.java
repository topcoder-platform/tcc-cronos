/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.HibernateUtil;

/**
 * <p>
 * Tests the functionality of {@link PrizeType} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class PrizeTypeTest extends TestCase {

    /**
     * Represents the <code>PrizeType</code> instance to test.
     */
    private PrizeType prizeType = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        prizeType = new PrizeType();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        prizeType = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(PrizeTypeTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link PrizeType#PrizeType()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_PrizeType() {
        // check for null
        assertNotNull("PrizeType creation failed", prizeType);
    }

    /**
     * <p>
     * Accuracy test for {@link PrizeType#getPrizeTypeId()} and {@link PrizeType#setPrizeTypeId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getPrizeTypeId() {
        // set the value to test
        prizeType.setPrizeTypeId(new Long(1));
        assertEquals("getPrizeTypeId and setPrizeTypeId failure occured", new Long(1), prizeType.getPrizeTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link PrizeType#setPrizeTypeId(Long)} and {@link PrizeType#getPrizeTypeId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setPrizeTypeId() {
        // set the value to test
        prizeType.setPrizeTypeId(1L);
        assertEquals("getPrizeTypeId and setPrizeTypeId failure occured", 1L, (long) prizeType.getPrizeTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link PrizeType#getDescription()} and {@link PrizeType#setDescription(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDescription() {
        // set the value to test
        prizeType.setDescription(null);
        assertEquals("getDescription and setDescription failure occured", null, prizeType.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link PrizeType#setDescription(String)} and {@link PrizeType#getDescription()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        prizeType.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", prizeType.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link PrizeType#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        PrizeType prizeType1 = new PrizeType();
        prizeType1.setPrizeTypeId(1L);
        prizeType.setPrizeTypeId(1L);
        assertTrue("failed equals", prizeType1.equals(prizeType));
        assertTrue("failed hashCode", prizeType1.hashCode() == prizeType.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link PrizeType#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        PrizeType prizeType1 = new PrizeType();
        prizeType1.setPrizeTypeId(2L);
        prizeType.setPrizeTypeId(1L);
        assertFalse("failed equals", prizeType1.equals(prizeType));
        assertFalse("failed hashCode", prizeType1.hashCode() == prizeType.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link PrizeType#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object prizeType1 = new Object();
        prizeType.setPrizeTypeId(1L);
        assertFalse("failed equals", prizeType.equals(prizeType1));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link PrizeType}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            PrizeType entity = new PrizeType();
            entity.setDescription("description");
            entity.setPrizeTypeId(1L);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            PrizeType persisted = (PrizeType) HibernateUtil.getManager().find(PrizeType.class,
                    entity.getPrizeTypeId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (PrizeType) HibernateUtil.getManager().find(PrizeType.class, entity.getPrizeTypeId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
