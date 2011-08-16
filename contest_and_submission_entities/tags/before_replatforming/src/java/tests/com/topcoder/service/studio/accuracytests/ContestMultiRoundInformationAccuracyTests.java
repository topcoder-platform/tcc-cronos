/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.Session;

import com.topcoder.service.studio.contest.ContestMultiRoundInformation;


/**
 * <p>
 * Accuracy test for <code>ContestMultiRoundInformation</code> class.
 * </p>
 *
 * @author onsky
 * @version 1.0
 */
public class ContestMultiRoundInformationAccuracyTests extends TestCase {
    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestMultiRoundInformationAccuracyTests.class);
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

            ContestMultiRoundInformation entity = new ContestMultiRoundInformation();
            entity.setMilestoneDate(new Date());
            entity.setRoundOneIntroduction("test introduction1");
            entity.setRoundTwoIntroduction("test introduction2");
            entity.setSubmittersLockedBetweenRounds(false);

            // save the entity
            session.save(entity);
            assertNotNull("entity must be persisted", entity.getContestMultiRoundInformationId());

            // load the persisted object
            ContestMultiRoundInformation persisted = (ContestMultiRoundInformation) session.get(
            		ContestMultiRoundInformation.class, entity.getContestMultiRoundInformationId());
            assertEquals("the entity must be persisted",
            		entity.getRoundOneIntroduction(), persisted.getRoundOneIntroduction());
            // update the entity
            entity.setRoundOneIntroduction("updated");
            session.merge(entity);

            persisted = (ContestMultiRoundInformation) session.get(
            		ContestMultiRoundInformation.class, entity.getContestMultiRoundInformationId());
            assertEquals("Failed to update - value mismatch",
            		entity.getRoundOneIntroduction(), persisted.getRoundOneIntroduction());

            // delete the entity
            session.delete(entity);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
