/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link ContestResource} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since 1.2
 */
public class ContestResourceTest extends TestCase {

    /**
     * Represents the <code>ContestResource</code> instance to test.
     */
    private ContestResource contestResource = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        contestResource = new ContestResource();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        contestResource = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestResourceTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResource#ContestResource()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_PrizeType() {
        // check for null
        assertNotNull("ContestResource creation failed", contestResource);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResource#getResourceId()} and {@link ContestResource#setResourceId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getResourceId() {
        // set the value to test
        contestResource.setResourceId(new Long(1));
        assertEquals("getResourceId and setResourceId failure occured", new Long(1), contestResource.getResourceId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResource#setResourceId(Long)} and {@link ContestResource#getResourceId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setResourceId() {
        // set the value to test
        contestResource.setResourceId(1L);
        assertEquals("getResourceId and setResourceId failure occured", 1L, (long) contestResource.getResourceId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResource#getName()} and {@link ContestResource#setName(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getName() {
        // set the value to test
        contestResource.setName(null);
        assertEquals("getName and setName failure occured", null, contestResource.getName());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResource#setName(String)} and {@link ContestResource#getName()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setName() {
        // set the value to test
        contestResource.setName("test");
        assertEquals("getName and setName failure occured", "test", contestResource.getName());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResource#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestResource resource = new ContestResource();
        resource.setResourceId(1L);
        contestResource.setResourceId(1L);
        assertTrue("failed equals", resource.equals(contestResource));
        assertTrue("failed hashCode", resource.hashCode() == contestResource.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResource#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        ContestResource resource = new ContestResource();
        resource.setResourceId(2L);
        contestResource.setResourceId(1L);
        assertFalse("failed equals", resource.equals(contestResource));
        assertFalse("failed hashCode", resource.hashCode() == contestResource.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResource#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object resource = new Object();
        contestResource.setResourceId(1L);
        assertFalse("failed equals", contestResource.equals(resource));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ContestResource}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            ContestResource entity = new ContestResource();
            entity.setName("description");

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            ContestResource persisted = (ContestResource) HibernateUtil.getManager().find(ContestResource.class,
                    entity.getResourceId());
            assertEquals("Failed to persist - description mismatch", entity.getName(), persisted.getName());

            // update the entity
            entity.setName("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (ContestResource) HibernateUtil.getManager()
                    .find(ContestResource.class, entity.getResourceId());
            assertEquals("Failed to persist - name mismatch", entity.getName(), persisted.getName());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
