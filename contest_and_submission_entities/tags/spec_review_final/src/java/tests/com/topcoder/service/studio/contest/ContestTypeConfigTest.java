/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import javax.persistence.Query;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link ContestTypeConfig} class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class ContestTypeConfigTest extends TestCase {

    /**
     * Represents the <code>ContestTypeConfig</code> instance to test.
     */
    private ContestTypeConfig typeConfig = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        typeConfig = new ContestTypeConfig();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        typeConfig = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestTypeConfigTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestTypeConfig#ContestTypeConfig()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_ContestTypeConfig() {
        // check for null
        assertNotNull("ContestTypeConfig creation failed", typeConfig);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestTypeConfig#setProperty(ContestProperty)} and
     * {@link ContestTypeConfig#getProperty()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving.
     * </p>
     */
    public void test_accuracy_setProperty() {
        // set the value to test
        ContestProperty property = new ContestProperty();
        property.setPropertyId(1L);
        typeConfig.setProperty(property);
        assertEquals("getProperty and setProperty failure occured", 1L, typeConfig.getProperty().getPropertyId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestTypeConfig#setType(ContestType)} and {@link ContestTypeConfig#getType()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving.
     * </p>
     */
    public void test_accuracy_setType() {
        // set the value to test
        ContestType type = new ContestType();
        type.setContestType(1L);
        typeConfig.setType(type);
        assertEquals("getType and setType failure occured", 1L, (long) typeConfig.getType().getContestType());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestTypeConfig#setPropertyValue(String)} and
     * {@link ContestTypeConfig#getPropertyValue()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving.
     * </p>
     */
    public void test_accuracy_setPropertyValue() {
        // set the value to test
        typeConfig.setPropertyValue("test");
        assertEquals("getPropertyValue and setPropertyValue failure occured", "test", typeConfig
                .getPropertyValue());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestTypeConfig#setRequired(boolean)} and {@link ContestTypeConfig#isRequired()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving.
     * </p>
     */
    public void test_accuracy_setRequired() {
        // set the value to test
        typeConfig.setRequired(true);
        assertEquals("isRequired and setRequired failure occured", true, typeConfig.isRequired());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestTypeConfig#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestTypeConfig typeConfig1 = new ContestTypeConfig();
        ContestProperty property = new ContestProperty();
        property.setPropertyId(1L);
        ContestType contestType = new ContestType();
        contestType.setContestType(1L);
        typeConfig1.setProperty(property);
        typeConfig.setProperty(property);
        typeConfig1.setType(contestType);
        typeConfig.setType(contestType);
        assertTrue("failed equals", typeConfig1.equals(typeConfig));
        assertTrue("failed hashCode", typeConfig1.hashCode() == typeConfig.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestTypeConfig#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        ContestTypeConfig typeConfig1 = new ContestTypeConfig();
        ContestProperty property = new ContestProperty();
        property.setPropertyId(1L);
        ContestType contestType = new ContestType();
        contestType.setContestType(1L);
        typeConfig1.setProperty(property);
        typeConfig.setProperty(property);
        typeConfig1.setType(contestType);
        assertFalse("failed equals", typeConfig1.equals(typeConfig));
        assertFalse("failed hashCode", typeConfig1.hashCode() == typeConfig.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestTypeConfig#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object typeConfig1 = new Object();
        ContestProperty property = new ContestProperty();
        property.setPropertyId(1L);
        ContestType contestType = new ContestType();
        contestType.setContestType(1L);
        typeConfig.setProperty(property);
        assertFalse("failed equals", typeConfig.equals(typeConfig1));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ContestTypeConfig}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            ContestType contestType = new ContestType();
            TestHelper.populateContestType(contestType);
            HibernateUtil.getManager().persist(contestType);

            ContestProperty property = new ContestProperty();
            property.setDescription("description");
            HibernateUtil.getManager().persist(property);

            ContestTypeConfig entity = new ContestTypeConfig();
            entity.setProperty(property);
            entity.setType(contestType);
            entity.setPropertyValue("propertyValue");
            entity.setRequired(true);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            Query query = HibernateUtil.getManager().createQuery("from ContestTypeConfig as c");

            ContestTypeConfig persisted = (ContestTypeConfig) query.getResultList().get(0);

            assertEquals("Failed to persist - property mismatch", entity.getProperty(), persisted.getProperty());
            assertEquals("Failed to persist - type mismatch", entity.getType(), persisted.getType());
            assertEquals("Failed to persist - value mismatch", entity.getPropertyValue(), persisted
                    .getPropertyValue());
            assertEquals("Failed to persist - required mismatch", entity.isRequired(), persisted.isRequired());

            // update the entity
            entity.setPropertyValue("new propertyValue");
            HibernateUtil.getManager().merge(entity);

            persisted = (ContestTypeConfig) query.getResultList().get(0);

            assertEquals("Failed to update - property mismatch", entity.getProperty(), persisted.getProperty());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
            HibernateUtil.getManager().remove(property);
            HibernateUtil.getManager().remove(contestType);
        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
