/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;

import javax.persistence.Query;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link ContestConfig} class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class ContestConfigTest extends TestCase {

    /**
     * Represents the <code>ContestConfig</code> instance to test.
     */
    private ContestConfig config = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        config = new ContestConfig();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        config = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestConfigTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestConfig#Config()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_Config() {
        // check for null
        assertNotNull("Config creation failed", config);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestConfig#getValue()} and {@link ContestConfig#setValue(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getValue() {
        // set the value to test
        config.setValue(null);
        assertEquals("getValue and setValue failure occured", null, config.getValue());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestConfig#setValue(String)} and {@link ContestConfig#getValue()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setValue() {
        // set the value to test
        config.setValue("test");
        assertEquals("getValue and setValue failure occured", "test", config.getValue());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestConfig#getContest()} and {@link ContestConfig#setContest(Contest)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getContest() {
        // set the value to test
        config.setContest(null);
        assertEquals("getContest and setContest failure occured", null, config.getContest());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestConfig#setContest(Contest)} and {@link ContestConfig#getContest()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContest() {
        // set the value to test
        Contest contest = new Contest();
        contest.setContestId(1L);
        config.setContest(contest);
        assertEquals("getContest and setContest failure occured", 1L, (long) config.getContest().getContestId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestConfig#getProperty()} and {@link ContestConfig#setProperty(ContestProperty)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getProperty() {
        // set the value to test
        config.setContest(null);
        assertEquals("getProperty and setProperty failure occured", null, config.getContest());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestConfig#setProperty(ContestProperty)} and {@link ContestConfig#getProperty()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setProperty() {
        // set the value to test
        ContestProperty property = new ContestProperty();
        property.setPropertyId(1L);
        config.setProperty(property);
        assertEquals("getProperty and setProperty failure occured", 1L, (long) config.getProperty()
                .getPropertyId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestConfig#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestConfig config1 = new ContestConfig();
        ContestProperty property = new ContestProperty();
        property.setPropertyId(1L);
        Contest contest = new Contest();
        contest.setContestId(1L);
        config1.setContest(contest);
        config1.setProperty(property);
        config.setContest(contest);
        config.setProperty(property);
        assertTrue("failed equals", config1.equals(config));
        assertTrue("failed hashCode", config1.hashCode() == config.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestConfig#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        ContestConfig config1 = new ContestConfig();
        ContestProperty property = new ContestProperty();
        property.setPropertyId(1L);
        Contest contest = new Contest();
        contest.setContestId(1L);
        config1.setContest(contest);
        config1.setProperty(property);
        config.setProperty(property);
        assertFalse("failed equals", config1.equals(config));
        assertFalse("failed hashCode", config1.hashCode() == config.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestConfig#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object config1 = new Object();
        assertFalse("failed equals", config.equals(config1));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ContestConfig}</code>.
     * </p>
     */
    public void test_persistence() {
        try {
            HibernateUtil.getManager().getTransaction().begin();
            ContestProperty property = new ContestProperty();
            property.setDescription("description");
            HibernateUtil.getManager().persist(property);

            Date date = new Date();
            StudioFileType fileType = new StudioFileType();
            TestHelper.populateStudioFileType(fileType);
            HibernateUtil.getManager().persist(fileType);

            ContestChannel channel = new ContestChannel();
            TestHelper.populateContestCategory(channel, fileType);
            HibernateUtil.getManager().persist(channel);

            ContestType contestType = new ContestType();
            TestHelper.populateContestType(contestType);
            HibernateUtil.getManager().persist(contestType);

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("Name");
            HibernateUtil.getManager().persist(status);

            Contest contest = new Contest();

            TestHelper.populateContest(contest, date, channel, contestType, status);
            HibernateUtil.getManager().persist(contest);

            ContestConfig entity = new ContestConfig();
            entity.setContest(contest);
            entity.setProperty(property);
            entity.setValue("value");
            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object

            Query query = HibernateUtil.getManager().createQuery("from ContestConfig as c");
            ContestConfig persisted = (ContestConfig) query.getResultList().get(0);

            assertEquals("Failed to persist - value mismatch", entity.getValue(), persisted.getValue());
            assertEquals("Failed to persist - contest mismatch", entity.getContest(), persisted.getContest());
            assertEquals("Failed to persist - property mismatch", entity.getProperty(), persisted.getProperty());

            // update the entity
            entity.setValue("new value");
            HibernateUtil.getManager().merge(entity);

            persisted = (ContestConfig) query.getResultList().get(0);

            assertEquals("Failed to update - value mismatch", entity.getValue(), persisted.getValue());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
            HibernateUtil.getManager().remove(property);
            HibernateUtil.getManager().remove(contest);
            HibernateUtil.getManager().remove(status);
            HibernateUtil.getManager().remove(contestType);
            HibernateUtil.getManager().remove(channel);
            HibernateUtil.getManager().remove(fileType);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
