/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity.accuracytests;

import com.topcoder.service.digitalrun.entity.DigitalRunPoints;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsOperation;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsReferenceType;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsStatus;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsType;
import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackContest;
import com.topcoder.service.digitalrun.entity.TrackContestResultCalculator;
import com.topcoder.service.digitalrun.entity.TrackContestType;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;

import junit.extensions.TestSetup;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.ejb.Ejb3Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;


/**
 * <p>
 * Accuracy test cases for the persistence of this component.
 * </p>
 * @author waits
 * @version 1.0
 */
public class PersistenceAccuracyTests extends TestCase {
    /** Represents the entity manager used for CRUD operations on entity. */
    private static EntityManager manager;

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(PersistenceAccuracyTests.class);

        TestSetup wrapper = new TestSetup(suite) {
                /**
                 * setup the environment.
                 */
                protected void setUp() throws Exception {
                    Ejb3Configuration cfg = new Ejb3Configuration();
                    EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
                    manager = emf.createEntityManager();
                }

                /**
                 * clear the environment.
                 */
                protected void tearDown() throws Exception {
                    manager.close();
                }
            };

        return wrapper;
    }

    /**
     * <p>
     * Test the persistence of Track.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testTrack() throws Exception {
        EntityTransaction tx = manager.getTransaction();

        try {
            tx.begin();

            PointsCalculator pointsCalculator = createPointsCalculator();
            TrackStatus trackStatus = createTrackStatus();
            TrackType trackType = createTrackType();
            ProjectType projecType = createProjectType();
            TrackContestResultCalculator calculator = createTrackContestResultCalculator();
            TrackContestType trackContestType = createTrackContestType();

            manager.persist(trackType);
            manager.persist(trackStatus);
            manager.persist(pointsCalculator);
            manager.persist(projecType);
            manager.persist(trackContestType);
            manager.persist(calculator);

            Track entity = createTrack(pointsCalculator, trackStatus, trackType, projecType);

            // save
            manager.persist(entity);

            // make sure the data is persisted to database
            tx.commit();

            // make sure the entity is not cached
            manager.clear();

            tx.begin();

            // read
            Track peristed = manager.find(Track.class, entity.getId());

            assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(),
                entity.getDescription());
            assertEquals("Failed to persist the entity #PointsCalculator mismatch", peristed.getPointsCalculator(),
                entity.getPointsCalculator());
            assertEquals("Failed to persist the entity #TrackStatus mismatch", peristed.getTrackStatus(),
                entity.getTrackStatus());
            assertEquals("Failed to persist the entity #TrackType mismatch", peristed.getTrackType(),
                entity.getTrackType());

            
            TrackContest contest = createTrackContest(peristed,calculator,trackContestType);
            
            manager.persist(contest);
            
            tx.commit();
            
            manager.clear();
            
            tx.begin();
            // update
            peristed.setDescription("newdisc");

            Collection<TrackContest> trackContests = new ArrayList<TrackContest>();
            trackContests.add(contest);
            peristed.setTrackContests(trackContests);
            
            manager.merge(peristed);

            tx.commit();

            tx.begin();

            Track updated = manager.find(Track.class, entity.getId());
            assertEquals("Failed to update the entity #Description mismatch", peristed.getDescription(),
                updated.getDescription());
            assertEquals("The size of trackContest is invalid.", 1, updated.getTrackContests().size());

            manager.remove(manager.find(TrackContest.class,contest.getId()));
            // delete
            manager.remove(updated);
            tx.commit();
            peristed = manager.find(Track.class, updated.getId());
            assertNull("Failed to delete the entity", peristed);

            tx.begin();
            manager.remove(manager.find(PointsCalculator.class, pointsCalculator.getId()));
            manager.remove(manager.find(TrackStatus.class, trackStatus.getId()));
            manager.remove(manager.find(TrackType.class, trackType.getId()));
            manager.remove(manager.find(ProjectType.class, projecType.getId()));
            manager.remove(manager.find(TrackContestResultCalculator.class, calculator.getId()));
            manager.remove(manager.find(TrackContestType.class, trackContestType.getId()));
            
            tx.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();

            if (tx.isActive()) {
                tx.rollback();
            }

            throw e;
        }
    }

    /**
     * Persistence test for <code>DigitalRunPoints</code>.
     */
    public void testDigitalRunPoints() {
        EntityTransaction tx = manager.getTransaction();

        try {
            tx.begin();

            PointsCalculator pointsCalculator = createPointsCalculator();
            TrackStatus trackStatus = createTrackStatus();
            TrackType trackType = createTrackType();
            ProjectType projectType = createProjectType();
            Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);

            DigitalRunPointsOperation digitalRunPointsOperation = createDigitalRunPointsOperation();
            DigitalRunPointsReferenceType digitalRunPointsReferenceType = createDigitalRunPointsReferenceType();
            DigitalRunPointsStatus digitalRunPointsStatus = createDigitalRunPointsStatus();
            DigitalRunPointsType digitalRunPointsType = createDigitalRunPointsType();

            manager.persist(pointsCalculator);
            manager.persist(trackStatus);
            manager.persist(trackType);
            manager.persist(projectType);
            manager.persist(track);
            manager.persist(digitalRunPointsOperation);
            manager.persist(digitalRunPointsReferenceType);
            manager.persist(digitalRunPointsStatus);
            manager.persist(digitalRunPointsType);

            DigitalRunPoints entity = createDigitalRunPoints(digitalRunPointsOperation, digitalRunPointsReferenceType,
                    digitalRunPointsStatus, digitalRunPointsType, track);

            // save
            manager.persist(entity);

            tx.commit();

            manager.clear();

            tx.begin();

            // read
            DigitalRunPoints peristed = manager.find(DigitalRunPoints.class, entity.getId());

            assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(),
                entity.getDescription());
            assertEquals("Failed to persist the entity #Amount mismatch", peristed.getAmount(), entity.getAmount());
            assertEquals("Failed to persist the entity #ReferenceId mismatch", peristed.getReferenceId(),
                entity.getReferenceId());
            assertEquals("Failed to persist the entity #UserId mismatch", peristed.getUserId(), entity.getUserId());
            assertEquals("Failed to persist the entity #ApplicationDate mismatch", peristed.getApplicationDate().getTime(),
                entity.getApplicationDate().getTime());
            assertEquals("Failed to persist the entity #AwardDate mismatch", peristed.getAwardDate().getTime(),
                entity.getAwardDate().getTime());
            assertEquals("Failed to persist the entity #DigitalRunPointsType mismatch",
                peristed.getDigitalRunPointsType(), entity.getDigitalRunPointsType());

            assertEquals("Failed to persist the entity #Track mismatch", peristed.getTrack(), entity.getTrack());
            assertEquals("Failed to persist the entity #DigitalRunPointsOperation mismatch",
                peristed.getDigitalRunPointsOperation(), entity.getDigitalRunPointsOperation());
            assertEquals("Failed to persist the entity #DigitalRunPointsReferenceType mismatch",
                peristed.getDigitalRunPointsReferenceType(), entity.getDigitalRunPointsReferenceType());
            assertEquals("Failed to persist the entity #DigitalRunPointsStatus mismatch",
                peristed.getDigitalRunPointsStatus(), entity.getDigitalRunPointsStatus());
            assertEquals("Failed to persist the entity #DigitalRunPointsType mismatch",
                peristed.getDigitalRunPointsType(), entity.getDigitalRunPointsType());

            // update
            peristed.setDescription("newdisc");

            manager.merge(peristed);
            tx.commit();
            manager.clear();
            tx.begin();

            DigitalRunPoints updated = manager.find(DigitalRunPoints.class, entity.getId());
            assertEquals("Failed to update the entity #Description mismatch", peristed.getDescription(),
                updated.getDescription());

            // delete
            manager.remove(updated);
            peristed = manager.find(DigitalRunPoints.class, updated.getId());
            assertNull("Failed to delete the entity", peristed);

            manager.remove(manager.find(Track.class, track.getId()));
            manager.remove(manager.find(DigitalRunPointsOperation.class, digitalRunPointsOperation.getId()));
            manager.remove(manager.find(DigitalRunPointsReferenceType.class, digitalRunPointsReferenceType.getId()));
            manager.remove(manager.find(DigitalRunPointsStatus.class, digitalRunPointsStatus.getId()));
            manager.remove(manager.find(DigitalRunPointsType.class, digitalRunPointsType.getId()));
            manager.remove(manager.find(PointsCalculator.class, pointsCalculator.getId()));
            manager.remove(manager.find(TrackStatus.class, trackStatus.getId()));
            manager.remove(manager.find(TrackType.class, trackType.getId()));
            manager.remove(manager.find(ProjectType.class,projectType.getId()));
            tx.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();

            if (tx.isActive()) {
                tx.rollback();
            }

            throw e;
        }
    }

    /**
     * Creates the DigitalRunPointsStatus for testing purpose.
     *
     * @return the entity created
     */
    private DigitalRunPointsStatus createDigitalRunPointsStatus() {
        DigitalRunPointsStatus entity = new DigitalRunPointsStatus();
        entity.setDescription("description");

        return entity;
    }

    /**
     * Creates the DigitalRunPointsOperation for testing purpose.
     *
     * @return the entity created
     */
    private DigitalRunPointsOperation createDigitalRunPointsOperation() {
        DigitalRunPointsOperation entity = new DigitalRunPointsOperation();
        entity.setDescription("description");

        return entity;
    }

    /**
     * Creates the DigitalRunPointsReferenceType for testing purpose.
     *
     * @return the entity created
     */
    private DigitalRunPointsReferenceType createDigitalRunPointsReferenceType() {
        DigitalRunPointsReferenceType entity = new DigitalRunPointsReferenceType();
        entity.setDescription("description");

        return entity;
    }

    /**
     * Creates the DigitalRunPointsType for testing purpose.
     *
     * @return the entity created
     */
    private DigitalRunPointsType createDigitalRunPointsType() {
        DigitalRunPointsType entity = new DigitalRunPointsType();
        entity.setDescription("description");

        return entity;
    }

    /**
     * Creates the TrackContestType for testing purpose.
     *
     * @return the entity created
     */
    private TrackContestType createTrackContestType() {
        TrackContestType entity = new TrackContestType();
        entity.setDescription("description");

        return entity;
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
     * Creates the PointsCalculator for testing purpose.
     *
     * @return the entity created
     */
    private PointsCalculator createPointsCalculator() {
        PointsCalculator entity = new PointsCalculator();
        entity.setClassName("className");
        entity.setDescription("description");

        return entity;
    }

    /**
     * Creates the TrackContestResultCalculator for testing purpose.
     *
     * @return the entity created
     */
    private TrackContestResultCalculator createTrackContestResultCalculator() {
        TrackContestResultCalculator entity = new TrackContestResultCalculator();
        entity.setClassName("className");
        entity.setDescription("description");

        return entity;
    }

    /**
     * Creates the ProjectType for testing purpose.
     *
     * @return the entity created
     */
    private ProjectType createProjectType() {
        ProjectType entity = new ProjectType();
        entity.setId(1232);
        entity.setCreationUser("creationUser");
        entity.setDescription("description");
        entity.setModificationUser("modificationUser");
        entity.setName("name");

        return entity;
    }

    /**
     * Creates the TrackContest for testing purpose.
     *
     * @param track the associated track
     * @param trackContestResultCalculator the associated track contest result calculator
     * @param trackContestType the associated track contest type
     *
     * @return the entity created
     */
    private TrackContest createTrackContest(Track track, TrackContestResultCalculator trackContestResultCalculator,
        TrackContestType trackContestType) {
        TrackContest entity = new TrackContest();
        // entity.set
        entity.setDescription("description");
        entity.setTrack(track);
        entity.setTrackContestResultCalculator(trackContestResultCalculator);
        entity.setTrackContestType(trackContestType);

        return entity;
    }

    /**
     * Creates the Track for testing purpose.
     *
     * @param pointsCalculator the associated points calculator
     * @param trackStatus the associated track status
     * @param trackType the associated track type
     *
     * @return the entity created
     */
    private Track createTrack(PointsCalculator pointsCalculator, TrackStatus trackStatus, TrackType trackType,
    		ProjectType projectType) {
        Track entity = new Track();
        entity.setPointsCalculator(pointsCalculator);
        entity.setTrackStatus(trackStatus);
        entity.setTrackType(trackType);
        entity.setDescription("description");
        entity.setStartDate(new Date());
        entity.setEndDate(new Date());
        entity.setCreationDate(new Date());
        entity.setModificationDate(new Date());
        Collection<ProjectType> projectTypes = new ArrayList<ProjectType>();
        projectTypes.add(projectType);
        entity.setProjectTypes(projectTypes);

        return entity;
    }

    /**
     * Creates the DigitalRunPoints for testing purpose.
     *
     * @param digitalRunPointsOperation the associated DR points operation
     * @param digitalRunPointsReferenceType the associated DR points reference type
     * @param digitalRunPointsStatus the associated DR points status
     * @param digitalRunPointsType the associated DR points type
     * @param track the associated track
     *
     * @return the entity created
     */
    private DigitalRunPoints createDigitalRunPoints(DigitalRunPointsOperation digitalRunPointsOperation,
        DigitalRunPointsReferenceType digitalRunPointsReferenceType, DigitalRunPointsStatus digitalRunPointsStatus,
        DigitalRunPointsType digitalRunPointsType, Track track) {
        DigitalRunPoints entity = new DigitalRunPoints();
        entity.setAmount(100.0);
        entity.setApplicationDate(new Date());
        entity.setAwardDate(new Date());
        entity.setDescription("description");
        entity.setDigitalRunPointsOperation(digitalRunPointsOperation);
        entity.setDigitalRunPointsReferenceType(digitalRunPointsReferenceType);
        entity.setDigitalRunPointsStatus(digitalRunPointsStatus);
        entity.setDigitalRunPointsType(digitalRunPointsType);
        entity.setPotential(true);
        entity.setReferenceId(10);
        entity.setTrack(track);
        entity.setUserId(101);

        return entity;
    }
}
