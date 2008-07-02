/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.ejb.Ejb3Configuration;

/**
 * <p>
 * Tests the persistence failure functionality of all entities.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PersistenceFailureTests extends TestCase {

    /**
     * Represents the entity manager used for CRUD operations on entity.
     */
    private static EntityManager manager;;

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(PersistenceFailureTests.class);

        TestSetup wrapper = new TestSetup(suite) {

            @Override
            protected void setUp() throws Exception {
                Ejb3Configuration cfg = new Ejb3Configuration();
                EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
                manager = emf.createEntityManager();
            }

            @Override
            protected void tearDown() throws Exception {
                manager.close();
            }
        };

        return wrapper;
    }

    /**
     * Failure persistence test for <code>DigitalRunPointsStatus</code>. Field description not set.
     */
    public void testDigitalRunPointsStatus() {
        DigitalRunPointsStatus entity = new DigitalRunPointsStatus();
        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // expected
            manager.getTransaction().rollback();
        }

    }

    /**
     * Persistence test for <code>DigitalRunPointsOperation</code>. Field description not set.
     */
    public void testDigitalRunPointsOperation() {
        DigitalRunPointsOperation entity = new DigitalRunPointsOperation();
        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // expected
            manager.getTransaction().rollback();
        }
    }

    /**
     * Persistence test for <code>DigitalRunPointsReferenceType</code>. Field description not set.
     */
    public void testDigitalRunPointsReferenceType() {
        DigitalRunPointsReferenceType entity = new DigitalRunPointsReferenceType();
        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // expected
            manager.getTransaction().rollback();
        }
    }

    /**
     * Persistence test for <code>DigitalRunPointsType</code>. Field description not set.
     */
    public void testDigitalRunPointsType() {
        DigitalRunPointsType entity = new DigitalRunPointsType();
        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // expected
            manager.getTransaction().rollback();
        }
    }

    /**
     * Persistence test for <code>TrackContestType</code>. Field description not set.
     */
    public void testTrackContestType() {
        TrackContestType entity = new TrackContestType();
        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // expected
            manager.getTransaction().rollback();
        }
    }

    /**
     * Persistence test for <code>TrackStatus</code>. Field description not set.
     */
    public void testTrackStatus() {
        TrackStatus entity = new TrackStatus();
        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // expected
            manager.getTransaction().rollback();
        }
    }

    /**
     * Persistence test for <code>Track</code>. PointCalculator not set
     */
    public void testTrack() {
        TrackStatus trackStatus = createTrackStatus();
        TrackType trackType = createTrackType();

        Track entity = createTrack(new PointsCalculator(), trackStatus, trackType);

        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // expected
            manager.getTransaction().rollback();
        }
    }

    /**
     * Persistence test for <code>TrackContest</code>. Required description not set.
     */
    public void testTrackContest() {
        TrackContest entity = new TrackContest();
        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // expected
            manager.getTransaction().rollback();
        }
    }

    /**
     * Persistence test for <code>DigitalRunPoints</code>. Required mappings not set.
     */
    public void testDigitalRunPoints() {
        DigitalRunPoints entity = new DigitalRunPoints();

        entity.setDescription("description");
        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // expected
            manager.getTransaction().rollback();
        }
    }

    /**
     * Persistence test for <code>TrackType</code>. Field description not set.
     */
    public void testTrackType() {
        TrackType entity = new TrackType();

        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // expected
            manager.getTransaction().rollback();
        }
    }

    /**
     * Persistence test for <code>PointsCalculator</code>. Required className not set.
     */
    public void testPointsCalculator() {
        PointsCalculator entity = new PointsCalculator();
        entity.setDescription("description");

        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // expected
            manager.getTransaction().rollback();
        }
    }

    /**
     * Persistence test for <code>ProjectType</code>. Required id not set.
     */
    public void testProjectType() {
        ProjectType entity = new ProjectType();
        entity.setCreationUser("creationUser");
        entity.setDescription("description");
        entity.setModificationUser("modificationUser");

        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // expected
            manager.getTransaction().rollback();
        }
    }

    /**
     * Persistence test for <code>TrackContestResultCalculator</code>. Required className not set.
     */
    public void testTrackContestResultCalculator() {
        TrackContestResultCalculator entity = new TrackContestResultCalculator();
        entity.setDescription("description");

        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // expected
            manager.getTransaction().rollback();
        }
    }

    /**
     * Creates the TrackStatus for testing purpose.
     *
     * @return the entity created
     */
    private TrackStatus createTrackStatus() {
        TrackStatus entity = new TrackStatus();
        entity.setDescription("description");
        return entity;
    }

    /**
     * Creates the TrackType for testing purpose.
     *
     * @return the entity created
     */
    private TrackType createTrackType() {
        TrackType entity = new TrackType();
        entity.setDescription("description");
        return entity;
    }

    /**
     * Creates the Track for testing purpose.
     *
     * @param pointsCalculator
     *            the associated points calculator
     * @param trackStatus
     *            the associated track status
     * @param trackType
     *            the associated track type
     * @return the entity created
     */
    private Track createTrack(PointsCalculator pointsCalculator, TrackStatus trackStatus, TrackType trackType) {
        Track entity = new Track();
        entity.setPointsCalculator(pointsCalculator);
        entity.setTrackStatus(trackStatus);
        entity.setTrackType(trackType);
        entity.setDescription("description");
        entity.setStartDate(new Date());
        entity.setEndDate(new Date());
        entity.setCreationDate(new Date());
        entity.setModificationDate(new Date());
        return entity;
    }

}
