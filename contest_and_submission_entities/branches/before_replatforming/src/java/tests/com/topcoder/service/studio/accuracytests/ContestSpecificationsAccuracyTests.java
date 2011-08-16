/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.Session;

import com.topcoder.service.studio.contest.ContestSpecifications;


/**
 * <p>
 * Accuracy test for <code>ContestSpecifications</code> class.
 * </p>
 *
 * @author onsky
 * @version 1.0
 */
public class ContestSpecificationsAccuracyTests extends TestCase {
    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestSpecificationsAccuracyTests.class);
    }

    /**
     * <p>
     * Test the mapping of this entity.
     * </p>
     */
    public void testPersist() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            ContestSpecifications entity = new ContestSpecifications();
            entity.setAdditionalRequirementsAndRestrictions("test");
            entity.setColors("test colors");
            entity.setFonts("test fonts");
            entity.setLayoutAndSize("test layout");

            // save the entity
            session.save(entity);
            assertNotNull("entity must be persisted", entity.getContestSpecificationsId());

            // load the persisted object
            ContestSpecifications persisted = (ContestSpecifications) session.get(
            		ContestSpecifications.class, entity.getContestSpecificationsId());
            assertEquals("the entity must be persisted", entity.getColors(), persisted.getColors());

            // update the entity
            entity.setColors("updated");
            session.merge(entity);

            persisted = (ContestSpecifications) session.get(
            		ContestSpecifications.class, entity.getContestSpecificationsId());
            assertEquals("Failed to update - value mismatch", entity.getColors(), persisted.getColors());

            // delete the entity
            session.delete(entity);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
