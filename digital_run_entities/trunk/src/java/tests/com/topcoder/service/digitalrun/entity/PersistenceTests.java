/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.ejb.Ejb3Configuration;

/**
 * <p>
 * Tests the persistence functionality of all entities.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PersistenceTests extends TestCase {

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
        suite.addTestSuite(PersistenceTests.class);

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
     * Persistence test for <code>DigitalRunPointsStatus</code>.
     */
    public void testDigitalRunPointsStatus() {
        DigitalRunPointsStatus entity = createDigitalRunPointsStatus();

        // save
        persist(entity);

        // refresh to get the time stamps to the entity
        manager.refresh(entity);

        // read
        DigitalRunPointsStatus peristed = manager.find(DigitalRunPointsStatus.class, entity.getId());

        assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(), entity
                .getDescription());

        // update
        peristed.setDescription("newdisc");

        update(peristed);
        DigitalRunPointsStatus updated = manager.find(DigitalRunPointsStatus.class, entity.getId());
        assertEquals("Failed to update the entity #Description mismatch", peristed.getDescription(), updated
                .getDescription());

        // delete
        delete(updated);
        peristed = manager.find(DigitalRunPointsStatus.class, updated.getId());
        assertNull("Failed to delete the entity", peristed);
    }

    /**
     * Persistence test for <code>DigitalRunPointsOperation</code>.
     */
    public void testDigitalRunPointsOperation() {
        DigitalRunPointsOperation entity = createDigitalRunPointsOperation();

        // save
        persist(entity);

        // refresh to get the time stamps to the entity
        manager.refresh(entity);

        // read
        DigitalRunPointsOperation peristed = manager.find(DigitalRunPointsOperation.class, entity.getId());

        assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(), entity
                .getDescription());

        // update
        peristed.setDescription("newdisc");

        update(peristed);
        DigitalRunPointsOperation updated = manager.find(DigitalRunPointsOperation.class, entity.getId());
        assertEquals("Failed to update the entity #Description mismatch", peristed.getDescription(), updated
                .getDescription());

        // delete
        delete(updated);
        peristed = manager.find(DigitalRunPointsOperation.class, updated.getId());
        assertNull("Failed to delete the entity", peristed);
    }

    /**
     * Persistence test for <code>DigitalRunPointsReferenceType</code>.
     */
    public void testDigitalRunPointsReferenceType() {
        DigitalRunPointsReferenceType entity = createDigitalRunPointsReferenceType();

        // save
        persist(entity);

        // refresh to get the time stamps to the entity
        manager.refresh(entity);

        // read
        DigitalRunPointsReferenceType peristed = manager.find(DigitalRunPointsReferenceType.class, entity.getId());

        assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(), entity
                .getDescription());

        // update
        peristed.setDescription("newdisc");

        update(peristed);
        DigitalRunPointsReferenceType updated = manager.find(DigitalRunPointsReferenceType.class, entity.getId());
        assertEquals("Failed to update the entity #Description mismatch", peristed.getDescription(), updated
                .getDescription());

        // delete
        delete(updated);
        peristed = manager.find(DigitalRunPointsReferenceType.class, updated.getId());
        assertNull("Failed to delete the entity", peristed);
    }

    /**
     * Persistence test for <code>DigitalRunPointsType</code>.
     */
    public void testDigitalRunPointsType() {
        DigitalRunPointsType entity = createDigitalRunPointsType();

        // save
        persist(entity);

        // refresh to get the time stamps to the entity
        manager.refresh(entity);

        // read
        DigitalRunPointsType peristed = manager.find(DigitalRunPointsType.class, entity.getId());

        assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(), entity
                .getDescription());

        // update
        peristed.setDescription("newdisc");

        update(peristed);
        DigitalRunPointsType updated = manager.find(DigitalRunPointsType.class, entity.getId());
        assertEquals("Failed to update the entity #Description mismatch", peristed.getDescription(), updated
                .getDescription());

        // delete
        delete(updated);
        peristed = manager.find(DigitalRunPointsType.class, updated.getId());
        assertNull("Failed to delete the entity", peristed);
    }

    /**
     * Persistence test for <code>TrackContestType</code>.
     */
    public void testTrackContestType() {
        TrackContestType entity = createTrackContestType();

        // save
        persist(entity);

        // refresh to get the time stamps to the entity
        manager.refresh(entity);

        // read
        TrackContestType peristed = manager.find(TrackContestType.class, entity.getId());

        assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(), entity
                .getDescription());

        // update
        peristed.setDescription("newdisc");

        update(peristed);
        TrackContestType updated = manager.find(TrackContestType.class, entity.getId());
        assertEquals("Failed to update the entity #Description mismatch", peristed.getDescription(), updated
                .getDescription());

        // delete
        delete(updated);
        peristed = manager.find(TrackContestType.class, updated.getId());
        assertNull("Failed to delete the entity", peristed);
    }

    /**
     * Persistence test for <code>TrackStatus</code>.
     */
    public void testTrackStatus() {
        TrackStatus entity = createTrackStatus();

        // save
        persist(entity);

        // refresh to get the time stamps to the entity
        manager.refresh(entity);

        // read
        TrackStatus peristed = manager.find(TrackStatus.class, entity.getId());

        assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(), entity
                .getDescription());

        // update
        peristed.setDescription("newdisc");

        update(peristed);
        TrackStatus updated = manager.find(TrackStatus.class, entity.getId());
        assertEquals("Failed to update the entity #Description mismatch", peristed.getDescription(), updated
                .getDescription());

        // delete
        delete(updated);
        peristed = manager.find(TrackStatus.class, updated.getId());
        assertNull("Failed to delete the entity", peristed);
    }

    /**
     * Persistence test for <code>Track</code>.
     */
    public void testTrack() {
        PointsCalculator pointsCalculator = createPointsCalculator();
        TrackStatus trackStatus = createTrackStatus();
        TrackType trackType = createTrackType();

        persist(pointsCalculator);
        persist(trackStatus);
        persist(trackType);

        Track entity = createTrack(pointsCalculator, trackStatus, trackType);

        // save
        persist(entity);

        // refresh to get the time stamps to the entity
        manager.refresh(entity);

        // read
        Track peristed = manager.find(Track.class, entity.getId());

        assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(), entity
                .getDescription());
        assertEquals("Failed to persist the entity #PointsCalculator mismatch", peristed.getPointsCalculator(),
                entity.getPointsCalculator());
        assertEquals("Failed to persist the entity #TrackStatus mismatch", peristed.getTrackStatus(), entity
                .getTrackStatus());
        assertEquals("Failed to persist the entity #TrackType mismatch", peristed.getTrackType(), entity
                .getTrackType());

        // update
        peristed.setDescription("newdisc");

        update(peristed);
        Track updated = manager.find(Track.class, entity.getId());
        assertEquals("Failed to update the entity #Description mismatch", peristed.getDescription(), updated
                .getDescription());

        // delete
        delete(updated);
        peristed = manager.find(Track.class, updated.getId());
        assertNull("Failed to delete the entity", peristed);

        delete(pointsCalculator);
        delete(trackStatus);
        delete(trackType);
    }

    /**
     * Persistence test for <code>TrackContest</code>.
     */
    public void testTrackContest() {
        PointsCalculator pointsCalculator = createPointsCalculator();
        TrackStatus trackStatus = createTrackStatus();
        TrackType trackType = createTrackType();
        Track track = createTrack(pointsCalculator, trackStatus, trackType);
        TrackContestResultCalculator trackContestResultCalculator = createTrackContestResultCalculator();
        TrackContestType trackContestType = createTrackContestType();

        persist(pointsCalculator);
        persist(trackStatus);
        persist(trackType);
        persist(track);
        persist(trackContestResultCalculator);
        persist(trackContestType);

        TrackContest entity = createTrackContest(track, trackContestResultCalculator, trackContestType);

        // save
        persist(entity);

        // refresh to get the time stamps to the entity
        manager.refresh(entity);

        // read
        TrackContest peristed = manager.find(TrackContest.class, entity.getId());

        assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(), entity
                .getDescription());
        assertEquals("Failed to persist the entity #Track mismatch", peristed.getTrack(), entity.getTrack());
        assertEquals("Failed to persist the entity #TrackContestResultCalculator mismatch", peristed
                .getTrackContestResultCalculator(), entity.getTrackContestResultCalculator());
        assertEquals("Failed to persist the entity #TrackContestType mismatch", peristed.getTrackContestType(),
                entity.getTrackContestType());

        // test case for the track#trackContests
        List<TrackContest> trackContests = new ArrayList<TrackContest>();
        trackContests.add(peristed);
        track.setTrackContests(trackContests);

        // update
        peristed.setDescription("newdisc");

        update(peristed);
        TrackContest updated = manager.find(TrackContest.class, entity.getId());
        assertEquals("Failed to update the entity #Description mismatch", peristed.getDescription(), updated
                .getDescription());

        // now the track also should have the updated entity
        assertEquals("Failed to update the track#trackContests", ((List<TrackContest>) track.getTrackContests())
                .get(0).getDescription(), updated.getDescription());

        // delete
        delete(updated);
        peristed = manager.find(TrackContest.class, updated.getId());
        assertNull("Failed to delete the entity", peristed);

        delete(track);
        delete(trackContestResultCalculator);
        delete(trackContestType);
        delete(pointsCalculator);
        delete(trackStatus);
        delete(trackType);
    }

    /**
     * Persistence test for <code>DigitalRunPoints</code>.
     */
    public void testDigitalRunPoints() {
        PointsCalculator pointsCalculator = createPointsCalculator();
        TrackStatus trackStatus = createTrackStatus();
        TrackType trackType = createTrackType();
        Track track = createTrack(pointsCalculator, trackStatus, trackType);
        DigitalRunPointsOperation digitalRunPointsOperation = createDigitalRunPointsOperation();
        DigitalRunPointsReferenceType digitalRunPointsReferenceType = createDigitalRunPointsReferenceType();
        DigitalRunPointsStatus digitalRunPointsStatus = createDigitalRunPointsStatus();
        DigitalRunPointsType digitalRunPointsType = createDigitalRunPointsType();
        persist(pointsCalculator);
        persist(trackStatus);
        persist(trackType);
        persist(track);
        persist(digitalRunPointsOperation);
        persist(digitalRunPointsReferenceType);
        persist(digitalRunPointsStatus);
        persist(digitalRunPointsType);

        DigitalRunPoints entity = createDigitalRunPoints(digitalRunPointsOperation, digitalRunPointsReferenceType,
                digitalRunPointsStatus, digitalRunPointsType, track);

        // save
        persist(entity);

        // refresh to get the time stamps to the entity
        manager.refresh(entity);

        // read
        DigitalRunPoints peristed = manager.find(DigitalRunPoints.class, entity.getId());

        assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(), entity
                .getDescription());
        assertEquals("Failed to persist the entity #Amount mismatch", peristed.getAmount(), entity.getAmount());
        assertEquals("Failed to persist the entity #ReferenceId mismatch", peristed.getReferenceId(), entity
                .getReferenceId());
        assertEquals("Failed to persist the entity #UserId mismatch", peristed.getUserId(), entity.getUserId());
        assertEquals("Failed to persist the entity #ApplicationDate mismatch", peristed.getApplicationDate(),
                entity.getApplicationDate());
        assertEquals("Failed to persist the entity #AwardDate mismatch", peristed.getAwardDate(), entity
                .getAwardDate());
        assertEquals("Failed to persist the entity #DigitalRunPointsType mismatch", peristed
                .getDigitalRunPointsType(), entity.getDigitalRunPointsType());

        assertEquals("Failed to persist the entity #Track mismatch", peristed.getTrack(), entity.getTrack());
        assertEquals("Failed to persist the entity #DigitalRunPointsOperation mismatch", peristed
                .getDigitalRunPointsOperation(), entity.getDigitalRunPointsOperation());
        assertEquals("Failed to persist the entity #DigitalRunPointsReferenceType mismatch", peristed
                .getDigitalRunPointsReferenceType(), entity.getDigitalRunPointsReferenceType());
        assertEquals("Failed to persist the entity #DigitalRunPointsStatus mismatch", peristed
                .getDigitalRunPointsStatus(), entity.getDigitalRunPointsStatus());
        assertEquals("Failed to persist the entity #DigitalRunPointsType mismatch", peristed
                .getDigitalRunPointsType(), entity.getDigitalRunPointsType());

        // test case for the track#digitalRunPoints
        List<DigitalRunPoints> digitalRunPoints = new ArrayList<DigitalRunPoints>();
        digitalRunPoints.add(peristed);
        track.setDigitalRunPoints(digitalRunPoints);

        // update
        peristed.setDescription("newdisc");

        update(peristed);
        DigitalRunPoints updated = manager.find(DigitalRunPoints.class, entity.getId());
        assertEquals("Failed to update the entity #Description mismatch", peristed.getDescription(), updated
                .getDescription());

        // now the track also should have the updated entity
        assertEquals("Failed to update the track#digitalRunPoints", ((List<DigitalRunPoints>) track
                .getDigitalRunPoints()).get(0).getDescription(), updated.getDescription());

        // delete
        delete(updated);
        peristed = manager.find(DigitalRunPoints.class, updated.getId());
        assertNull("Failed to delete the entity", peristed);

        delete(track);
        delete(digitalRunPointsOperation);
        delete(digitalRunPointsReferenceType);
        delete(digitalRunPointsStatus);
        delete(digitalRunPointsType);
        delete(pointsCalculator);
        delete(trackStatus);
        delete(trackType);
    }

    /**
     * Persistence test for <code>TrackType</code>.
     */
    public void testTrackType() {
        TrackType entity = createTrackType();

        // save
        persist(entity);

        // refresh to get the time stamps to the entity
        manager.refresh(entity);

        // read
        TrackType peristed = manager.find(TrackType.class, entity.getId());

        assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(), entity
                .getDescription());

        // update
        peristed.setDescription("newdisc");

        update(peristed);
        TrackType updated = manager.find(TrackType.class, entity.getId());
        assertEquals("Failed to update the entity #Description mismatch", peristed.getDescription(), updated
                .getDescription());

        // delete
        delete(updated);
        peristed = manager.find(TrackType.class, updated.getId());
        assertNull("Failed to delete the entity", peristed);
    }

    /**
     * Persistence test for <code>PointsCalculator</code>.
     */
    public void testPointsCalculator() {
        PointsCalculator entity = createPointsCalculator();

        // save
        persist(entity);

        // refresh to get the time stamps to the entity
        manager.refresh(entity);

        // read
        PointsCalculator peristed = manager.find(PointsCalculator.class, entity.getId());

        assertEquals("Failed to persist the entity #ClassName mismatch", peristed.getClassName(), entity
                .getClassName());
        assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(), entity
                .getDescription());

        // update
        peristed.setClassName("newClass");

        update(peristed);
        PointsCalculator updated = manager.find(PointsCalculator.class, entity.getId());
        assertEquals("Failed to update the entity #ClassName mismatch", peristed.getClassName(), updated
                .getClassName());

        // delete
        delete(updated);
        peristed = manager.find(PointsCalculator.class, updated.getId());
        assertNull("Failed to delete the entity", peristed);
    }

    /**
     * Persistence test for <code>ProjectType</code>.
     */
    public void testProjectType() {
        ProjectType entity = createProjectType();

        // save
        persist(entity);

        // refresh to get the time stamps to the entity
        manager.refresh(entity);

        // read
        ProjectType peristed = manager.find(ProjectType.class, entity.getId());

        assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(), entity
                .getDescription());
        assertEquals("Failed to persist the entity #CreationUser mismatch", peristed.getCreationUser(), entity
                .getCreationUser());
        assertEquals("Failed to persist the entity #ModificationUser mismatch", peristed.getModificationUser(),
                entity.getModificationUser());
        assertEquals("Failed to persist the entity #Name mismatch", peristed.getName(), entity.getName());

        // update
        peristed.setDescription("newdisc");

        update(peristed);
        ProjectType updated = manager.find(ProjectType.class, entity.getId());
        assertEquals("Failed to update the entity #Description mismatch", peristed.getDescription(), updated
                .getDescription());

        // delete
        delete(updated);
        peristed = manager.find(ProjectType.class, updated.getId());
        assertNull("Failed to delete the entity", peristed);
    }

    /**
     * Persistence test for <code>TrackContestResultCalculator</code>.
     */
    public void testTrackContestResultCalculator() {
        TrackContestResultCalculator entity = createTrackContestResultCalculator();

        // save
        persist(entity);

        // refresh to get the time stamps to the entity
        manager.refresh(entity);

        // read
        TrackContestResultCalculator peristed = manager.find(TrackContestResultCalculator.class, entity.getId());

        assertEquals("Failed to persist the entity #ClassName mismatch", peristed.getClassName(), entity
                .getClassName());
        assertEquals("Failed to persist the entity #Description mismatch", peristed.getDescription(), entity
                .getDescription());

        // update
        peristed.setClassName("newClass");

        update(peristed);
        TrackContestResultCalculator updated = manager.find(TrackContestResultCalculator.class, entity.getId());
        assertEquals("Failed to update the entity #ClassName mismatch", peristed.getClassName(), updated
                .getClassName());

        // delete
        delete(updated);
        peristed = manager.find(TrackContestResultCalculator.class, updated.getId());
        assertNull("Failed to delete the entity", peristed);
    }

    /**
     * Helper method to persist the entity with transaction.
     *
     * @param entity
     *            the entity to persist
     */
    private void persist(Object entity) {
        manager.getTransaction().begin();
        manager.persist(entity);
        manager.getTransaction().commit();
    }

    /**
     * Helper method to update the entity with transaction.
     *
     * @param entity
     *            the entity to persist
     */
    private void update(Object entity) {
        manager.getTransaction().begin();
        manager.merge(entity);
        manager.getTransaction().commit();
    }

    /**
     * Helper method to remove the entity with transaction.
     *
     * @param entity
     *            the entity to persist
     */
    private void delete(Object entity) {
        manager.getTransaction().begin();
        manager.remove(entity);
        manager.getTransaction().commit();
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
        entity.setId(10);
        entity.setCreationUser("creationUser");
        entity.setDescription("description");
        entity.setModificationUser("modificationUser");
        entity.setName("name");
        return entity;
    }

    /**
     * Creates the TrackContest for testing purpose.
     *
     * @param track
     *            the associated track
     * @param trackContestResultCalculator
     *            the associated track contest result calculator
     * @param trackContestType
     *            the associated track contest type
     * @return the entity created
     */
    private TrackContest createTrackContest(Track track,
            TrackContestResultCalculator trackContestResultCalculator, TrackContestType trackContestType) {
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

    /**
     * Creates the DigitalRunPoints for testing purpose.
     *
     * @param digitalRunPointsOperation
     *            the associated DR points operation
     * @param digitalRunPointsReferenceType
     *            the associated DR points reference type
     * @param digitalRunPointsStatus
     *            the associated DR points status
     * @param digitalRunPointsType
     *            the associated DR points type
     * @param track
     *            the associated track
     * @return the entity created
     */
    private DigitalRunPoints createDigitalRunPoints(DigitalRunPointsOperation digitalRunPointsOperation,
            DigitalRunPointsReferenceType digitalRunPointsReferenceType,
            DigitalRunPointsStatus digitalRunPointsStatus, DigitalRunPointsType digitalRunPointsType, Track track) {
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
