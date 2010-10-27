/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.Session;

import com.topcoder.service.studio.contest.ContestResource;


/**
 * <p>
 * Accuracy test for <code>ContestResource</code> class.
 * </p>
 *
 * @author onsky
 * @version 1.0
 */
public class ContestResourceAccuracyTests extends TestCase {
    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestResourceAccuracyTests.class);
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

            ContestResource entity = new ContestResource();
            entity.setName("test name");

            // save the entity
            session.save(entity);
            assertNotNull("entity must be persisted", entity.getResourceId());

            // load the persisted object
            ContestResource persisted = (ContestResource) session.get(
            		ContestResource.class, entity.getResourceId());
            assertEquals("the entity must be persisted", entity.getName(), persisted.getName());

            // update the entity
            entity.setName("updated");
            session.merge(entity);

            persisted = (ContestResource) session.get(
            		ContestResource.class, entity.getResourceId());
            assertEquals("Failed to update - value mismatch", entity.getName(), persisted.getName());

            // delete the entity
            session.delete(entity);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
