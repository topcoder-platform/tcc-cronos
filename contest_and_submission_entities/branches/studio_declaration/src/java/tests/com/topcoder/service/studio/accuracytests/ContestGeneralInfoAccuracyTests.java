/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.Session;

import com.topcoder.service.studio.contest.ContestGeneralInfo;


/**
 * <p>
 * Accuracy test for <code>ContestGeneralInfo</code> class.
 * </p>
 *
 * @author onsky
 * @version 1.0
 */
public class ContestGeneralInfoAccuracyTests extends TestCase {
    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestGeneralInfoAccuracyTests.class);
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

            ContestGeneralInfo entity = new ContestGeneralInfo();
            entity.setGoals("accuracy test goals");
            entity.setBrandingGuidelines("accuracy branding guidelines");
            entity.setDislikedDesignsWebsites("accuracy test");
            entity.setOtherInstructions("accuracy other instructions");
            entity.setTargetAudience("accuracy target audience");
            entity.setWinningCriteria("accuracy winning criteria");

            // save the entity
            session.save(entity);
            assertNotNull("entity must be persisted", entity.getContestGeneralInfoId());

            // load the persisted object
            ContestGeneralInfo persisted = (ContestGeneralInfo) session.get(
            		ContestGeneralInfo.class, entity.getContestGeneralInfoId());
            assertEquals("the entity must be persisted", entity.getGoals(), persisted.getGoals());

            // update the entity
            entity.setGoals("updated");
            session.merge(entity);

            persisted = (ContestGeneralInfo) session.get(
            		ContestGeneralInfo.class, entity.getContestGeneralInfoId());
            assertEquals("Failed to update - value mismatch", entity.getGoals(), persisted.getGoals());

            // delete the entity
            session.delete(entity);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
