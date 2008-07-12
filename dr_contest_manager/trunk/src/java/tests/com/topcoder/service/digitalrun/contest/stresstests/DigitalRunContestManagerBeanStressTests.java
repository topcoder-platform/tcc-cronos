/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.stresstests;

import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;

import org.hibernate.ejb.Ejb3Configuration;

import com.topcoder.service.digitalrun.contest.DigitalRunContestManagerRemote;
import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackContest;
import com.topcoder.service.digitalrun.entity.TrackContestResultCalculator;
import com.topcoder.service.digitalrun.entity.TrackContestType;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;

/**
 * <p>
 * Stress tests for <code>DigitalRunContestManagerBean</code>.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class DigitalRunContestManagerBeanStressTests extends TestCase {

    /**
     * <p>
     * The ear name.
     * </p>
     */
    private static final String EAR_NAME = "dr_contest_manager";

    /**
     * <p>
     * The persistence unit used in the tests.
     * </p>
     */
    private static EntityManager entityManager;

    /**
     * <p>
     * Loop times.
     * </p>
     */
    private static final int COUNT = 100;

    /**
     * <p>
     * <code>Track</code> used in the tests.
     * </p>
     */
    private Track track;

    /**
     * <p>
     * <code>PointsCalculator</code> used in the tests.
     * </p>
     */
    private PointsCalculator pointsCalculator;

    /**
     * <p>
     * <code>TrackStatus</code> used in the tests.
     * </p>
     */
    private TrackStatus trackStatus;

    /**
     * <p>
     * <code>TrackType</code> used in the tests.
     * </p>
     */
    private TrackType trackType;

    /**
     * <p>
     * Get <code>EntityManager</code>.
     * </p>
     *
     * @return <code>EntityManager</code>.
     */
    private static EntityManager getEntityManager() {
        if (entityManager == null) {
            Ejb3Configuration cfg = new Ejb3Configuration();
            EntityManagerFactory emf = cfg.configure("/META-INF/hibernate.cfg.xml").buildEntityManagerFactory();
            entityManager = emf.createEntityManager();
        }
        return entityManager;
    }

    /**
     * <p>
     * Get <code>DigitalRunContestManagerBean</code> EJB remote interface.
     * </p>
     *
     * @return <code>DigitalRunContestManagerBean</code> EJB remote interface.
     *
     * @throws Exception to JUnit.
     */
    protected DigitalRunContestManagerRemote getDigitalRunContestManagerRemoteEJB() throws Exception {

        Properties env = new Properties();
        Context context = new InitialContext(env);
        return (DigitalRunContestManagerRemote)
            context.lookup(EAR_NAME + "/DigitalRunContestManagerBean/remote");
    }

    /**
     * Helper method to persist the entity with transaction.
     *
     * @param entity
     *            the entity to persist
     */
    protected void persist(Object entity) {
        getEntityManager().getTransaction().begin();
        getEntityManager().persist(entity);
        getEntityManager().getTransaction().commit();
    }

    /**
     * Helper method to remove the entity with transaction.
     *
     * @param entity
     *            the entity to persist
     */
    protected void delete(Object entity) {
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(entity);
        getEntityManager().getTransaction().commit();
    }

    /**
     * Creates the TrackContestType for testing purpose.
     *
     * @return the entity created
     */
    protected TrackContestType createTrackContestType() {
        TrackContestType entity = new TrackContestType();
        entity.setDescription("description");
        return entity;
    }

    /**
     * Creates the TrackStatus for testing purpose.
     *
     * @return the entity created
     */
    protected TrackStatus createTrackStatus() {
        TrackStatus entity = new TrackStatus();
        entity.setDescription("description");
        return entity;
    }

    /**
     * Creates the TrackType for testing purpose.
     *
     * @return the entity created
     */
    protected TrackType createTrackType() {
        TrackType entity = new TrackType();
        entity.setDescription("description");
        return entity;
    }

    /**
     * Creates the PointsCalculator for testing purpose.
     *
     * @return the entity created
     */
    protected PointsCalculator createPointsCalculator() {
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
    protected TrackContestResultCalculator createTrackContestResultCalculator() {
        TrackContestResultCalculator entity = new TrackContestResultCalculator();
        entity.setClassName("className");
        entity.setDescription("description");
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
    protected TrackContest createTrackContest(Track track,
            TrackContestResultCalculator trackContestResultCalculator, TrackContestType trackContestType) {
        TrackContest entity = new TrackContest();
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
    protected Track createTrack(PointsCalculator pointsCalculator, TrackStatus trackStatus, TrackType trackType) {
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
     * <p>
     * Create/update/remove <code>TrackContest</code> <code>COUNT</code> times.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTrackContest() throws Exception {

        // Create a Track
        pointsCalculator = createPointsCalculator();
        trackStatus = createTrackStatus();
        trackType = createTrackType();
        track = createTrack(pointsCalculator, trackStatus, trackType);

        persist(pointsCalculator);
        persist(trackStatus);
        persist(trackType);
        persist(track);

        // lookup the bean from JNDI
        DigitalRunContestManagerRemote manager = (DigitalRunContestManagerRemote)
            new InitialContext().lookup(EAR_NAME + "/DigitalRunContestManagerBean/remote");

        // create a new contest type
        TrackContestType entity = createTrackContestType();
        TrackContestType contestType = manager.createTrackContestType(entity);
        contestType = manager.getTrackContestType(contestType.getId());

        // create a new calculator
        TrackContestResultCalculator rc = createTrackContestResultCalculator();
        TrackContestResultCalculator calculator = manager.createTrackContestResultCalculator(rc);
        calculator = manager.getTrackContestResultCalculator(calculator.getId());

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {

            // create a new contest
            TrackContest contest = this.createTrackContest(track, calculator, contestType);
            contest = manager.createTrackContest(contest);

            contest = manager.getTrackContest(contest.getId());

            manager.updateTrackContest(contest);

            manager.removeTrackContest(contest.getId());
        }

        System.out.println("Create/update/remove TrackContest 100 times takes time: "
                + (System.currentTimeMillis() - startTime));

        // delete contest type
        manager.removeTrackContestType(contestType.getId());

        // delete calculator
        manager.removeTrackContestResultCalculator(calculator.getId());

        // delete Track
        delete(track);
        delete(pointsCalculator);
        delete(trackStatus);
        delete(trackType);
    }

    /**
     * <p>
     * Create/update/remove <code>TrackContestType</code> <code>COUNT</code> times.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTrackContestType() throws Exception {
        // lookup the bean from JNDI
        DigitalRunContestManagerRemote manager = (DigitalRunContestManagerRemote)
            new InitialContext().lookup(EAR_NAME + "/DigitalRunContestManagerBean/remote");

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            // create a new contest type
            TrackContestType entity = this.createTrackContestType();
            TrackContestType contestType = manager.createTrackContestType(entity);

            contestType = manager.getTrackContestType(contestType.getId());

            manager.updateTrackContestType(contestType);

            manager.getAllTrackContestTypes();

            manager.removeTrackContestType(contestType.getId());
        }

        System.out.println("Create/update/remove TrackContestType 100 times takes time: "
            + (System.currentTimeMillis() - startTime));
    }

    /**
     * <p>
     * Create/update/remove <code>TrackContestResultCalculator</code> <code>COUNT</code> times.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTrackContestResultCalculator() throws Exception {
        // lookup the bean from JNDI
        DigitalRunContestManagerRemote manager = (DigitalRunContestManagerRemote)
            new InitialContext().lookup(EAR_NAME + "/DigitalRunContestManagerBean/remote");

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {

            // create a new calculator
            TrackContestResultCalculator rc = this.createTrackContestResultCalculator();

            TrackContestResultCalculator calculator = manager.createTrackContestResultCalculator(rc);

            calculator = manager.getTrackContestResultCalculator(calculator.getId());

            manager.updateTrackContestResultCalculator(calculator);

            manager.getAllTrackContestResultCalculators();

            manager.removeTrackContestResultCalculator(calculator.getId());
        }

        System.out.println("Create/update/remove TrackContestResultCalculator 100 times takes time: "
            + (System.currentTimeMillis() - startTime));
    }
}
