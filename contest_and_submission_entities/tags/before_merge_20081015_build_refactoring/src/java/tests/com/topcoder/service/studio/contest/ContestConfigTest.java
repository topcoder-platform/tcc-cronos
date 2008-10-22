/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;

import javax.persistence.Query;

import com.topcoder.service.studio.contest.ContestConfig.Identifier;

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
     * Accuracy test for {@link ContestConfig#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestConfig config1 = new ContestConfig();
        ContestProperty property = new ContestProperty();
        property.setPropertyId(1L);
        Contest contest = new Contest();
        contest.setContestId(1L);
        
        Identifier id = new Identifier();
        id.setContest(contest);
        id.setProperty(property);
		config1.setId(id);
		config.setId(id);
		
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
        Contest contest1 = new Contest();
        contest1.setContestId(1L);

        Identifier id = new Identifier();
        id.setContest(contest1);
        id.setProperty(property);
		config1.setId(id);

        Contest contest2 = new Contest();
        contest2.setContestId(2L);
		
        Identifier id2 = new Identifier();
        id2.setContest(contest2);
        id2.setProperty(property);
		
        config.setId(id2);
		
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
            channel.setContestChannelId(10L);
            HibernateUtil.getManager().persist(channel);

            ContestType contestType = new ContestType();
            TestHelper.populateContestType(contestType);
            contestType.setContestType(10L);
            HibernateUtil.getManager().persist(contestType);

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("Name");
            status.setContestStatusId(10L);
            HibernateUtil.getManager().persist(status);

            Contest contest = new Contest();

            TestHelper.populateContest(contest, date, channel, contestType, status);
            HibernateUtil.getManager().persist(contest);

            ContestConfig entity = new ContestConfig();
            Identifier id = new Identifier ();
            id.setContest(contest);
            id.setProperty(property);
			entity.setId(id );
            entity.setValue("value");
            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object

            Query query = HibernateUtil.getManager().createQuery("from ContestConfig as c");
            ContestConfig persisted = (ContestConfig) query.getResultList().get(0);

            assertEquals("Failed to persist - value mismatch", entity.getValue(), persisted.getValue());
            assertEquals("Failed to persist - contest mismatch", entity.getId().getContest(), persisted.getId().getContest());
            assertEquals("Failed to persist - property mismatch", entity.getId().getProperty(), persisted.getId().getProperty());

            entity.setValue(null);
            HibernateUtil.getManager().persist(entity);            
            assertNull("Failed to persist - value mismatch", persisted.getValue());
            
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
