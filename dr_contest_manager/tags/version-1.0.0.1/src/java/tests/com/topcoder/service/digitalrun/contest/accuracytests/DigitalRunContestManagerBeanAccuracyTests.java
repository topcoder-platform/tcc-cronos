/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.accuracytests;

import com.topcoder.service.digitalrun.contest.DigitalRunContestManagerRemote;
import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackContest;
import com.topcoder.service.digitalrun.entity.TrackContestResultCalculator;
import com.topcoder.service.digitalrun.entity.TrackContestType;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;

import junit.framework.TestCase;

import org.hibernate.ejb.Ejb3Configuration;

import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 * <p>Accuracy test cases for the EJB.</p>
 * @author waits
 * @version 1.0
 */
public class DigitalRunContestManagerBeanAccuracyTests extends TestCase {
    /**
     * <p>
     * The ear name.
     * </p>
     */
    public static final String EAR_NAME = "dr_contest_manager";

    /**
     * <p>
     * The persistence unit used in the tests.
     * </p>
     */
    private static EntityManager entityManager;

    /** The remote interface. */
    private static DigitalRunContestManagerRemote remote;

    /**
     * Get remote interface.
     */
    protected void setUp() throws Exception {
        remote = this.getDigitalRunContestManagerRemote();
    }

    /**
     * <p>
     * Create/update/retrieve/remove <code>TrackContest</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTrackContest() throws Exception {
        PointsCalculator pointsCalculator = createPointsCalculator();
        TrackStatus trackStatus = createTrackStatus();
        TrackType trackType = createTrackType();
        Track track = createTrack(pointsCalculator, trackStatus, trackType);

        persist(pointsCalculator);
        persist(trackStatus);
        persist(trackType);
        persist(track);

        TrackContestResultCalculator trackContestResultCalculator = createTrackContestResultCalculator();
        TrackContestType trackContestType = createTrackContestType();

        persist(trackContestResultCalculator);
        persist(trackContestType);

        TrackContest entity = createTrackContest(track, trackContestResultCalculator, trackContestType);

        // Create TrackContest
        TrackContest created = remote.createTrackContest(entity);

        assertTrue("id should be set", entity.getId() != created.getId());

        // Fetch TrackContest
        TrackContest found = remote.getTrackContest(created.getId());

        found.setDescription("new description");

        // Update TrackContest
        remote.updateTrackContest(found);

        // Fetch TrackContest again
        found = remote.getTrackContest(created.getId());
        assertEquals("Description should be updated", "new description", found.getDescription());

        // Remove TrackContest
        remote.removeTrackContest(created.getId());

        // Fetch TrackContest again
        assertNull("TrackContest should be removed", remote.getTrackContest(created.getId()));
        
        delete(trackContestResultCalculator);
        delete(trackContestType);
        delete(track);
        delete(pointsCalculator);
        delete(trackStatus);
        delete(trackType);
    }

    /**
     * <p>
     * Create/update/retrieve/remove <code>TrackContestType</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTrackContestType() throws Exception {
        TrackContestType entity = createTrackContestType();

        // Create TrackContestType
        TrackContestType created = remote.createTrackContestType(entity);

        assertTrue("id should be set", entity.getId() != created.getId());

        // Fetch TrackContestType
        TrackContestType found = remote.getTrackContestType(created.getId());

        found.setDescription("new description");

        // Update TrackContestType
        remote.updateTrackContestType(found);

        // Fetch TrackContestType again
        found = remote.getTrackContestType(created.getId());
        assertEquals("Description should be updated", "new description", found.getDescription());

        // Fetch all TrackContestTypes
        assertEquals("There should be 1 TrackContestType", 1, remote.getAllTrackContestTypes().size());

        // Remove TrackContestType
        remote.removeTrackContestType(created.getId());

        // Fetch TrackContestType again
        assertNull("TrackContestType should be removed", remote.getTrackContestType(created.getId()));

        // Fetch all TrackContestTypes again
        assertEquals("There should be 0 TrackContestType", 0, remote.getAllTrackContestTypes().size());
    }

    /**
     * <p>
     * Create/update/retrieve/remove <code>TrackContestResultCalculator</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTrackContestResultCalculator() throws Exception {
        TrackContestResultCalculator entity = createTrackContestResultCalculator();

        // Create TrackContestResultCalculator
        TrackContestResultCalculator created = remote.createTrackContestResultCalculator(entity);

        assertTrue("id should be set", entity.getId() != created.getId());

        // Fetch TrackContestResultCalculator
        TrackContestResultCalculator found = remote.getTrackContestResultCalculator(created.getId());

        found.setDescription("new description");

        // Update TrackContestResultCalculator
        remote.updateTrackContestResultCalculator(found);

        // Fetch TrackContestResultCalculator again
        found = remote.getTrackContestResultCalculator(created.getId());
        assertEquals("Description should be updated", "new description", found.getDescription());

        // Fetch all TrackContestResultCalculators
        assertEquals("There should be 1 TrackContestResultCalculator", 1,
            remote.getAllTrackContestResultCalculators().size());

        // Remove TrackContestResultCalculator
        remote.removeTrackContestResultCalculator(created.getId());

        // Fetch TrackContestResultCalculator again
        assertNull("TrackContestResultCalculator should be removed",
            remote.getTrackContestResultCalculator(created.getId()));

        // Fetch all TrackContestResultCalculators again
        assertEquals("There should be 0 TrackContestResultCalculator", 0,
            remote.getAllTrackContestResultCalculators().size());
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
    private DigitalRunContestManagerRemote getDigitalRunContestManagerRemote()
        throws Exception {
        Properties env = new Properties();
        env.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        env.setProperty("java.naming.provider.url", "jnp://127.0.0.1:1099");
        env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");

        Context context = new InitialContext(env);

        return (DigitalRunContestManagerRemote) context.lookup(EAR_NAME + "/DigitalRunContestManagerBean/remote");
    }

    /**
     * Helper method to persist the entity with transaction.
     *
     * @param entity the entity to persist
     */
    protected void persist(Object entity) {
        getEntityManager().getTransaction().begin();
        getEntityManager().persist(entity);
        getEntityManager().getTransaction().commit();
    }

    /**
     * Helper method to remove the entity with transaction.
     *
     * @param entity the entity to persist
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
     * @param track the associated track
     * @param trackContestResultCalculator the associated track contest result calculator
     * @param trackContestType the associated track contest type
     *
     * @return the entity created
     */
    protected TrackContest createTrackContest(Track track, TrackContestResultCalculator trackContestResultCalculator,
        TrackContestType trackContestType) {
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
     * @param pointsCalculator the associated points calculator
     * @param trackStatus the associated track status
     * @param trackType the associated track type
     *
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
     * Get <code>EntityManager</code>.
     * </p>
     *
     * @return <code>EntityManager</code>.
     */
    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            Ejb3Configuration cfg = new Ejb3Configuration();
            EntityManagerFactory emf = cfg.configure("/META-INF/hibernate.cfg.xml").buildEntityManagerFactory();
            entityManager = emf.createEntityManager();
        }

        return entityManager;
    }
}
