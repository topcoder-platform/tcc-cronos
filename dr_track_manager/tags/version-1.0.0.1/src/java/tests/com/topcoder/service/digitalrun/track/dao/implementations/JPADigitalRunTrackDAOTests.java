/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.dao.implementations;

import com.topcoder.search.builder.SearchBundle;

import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;
import com.topcoder.service.digitalrun.track.DigitalRunTrackFilterFactory;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerPersistenceException;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;

import com.topcoder.util.log.LogManager;

import junit.extensions.TestSetup;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.easymock.EasyMock;

import org.hibernate.ejb.Ejb3Configuration;

import java.util.Date;
import java.util.HashMap;

import javax.ejb.SessionContext;

import javax.persistence.EntityManagerFactory;


/**
 * <p>
 * Unit test cases for the class <code>JPADigitalRunTrackDAO.</code>
 * </p>
 * @author waits
 * @version 1.0
 */
public class JPADigitalRunTrackDAOTests extends BaseTestCase {
    /** JPADigitalRunTrackDAO instance to test against. */
    private static JPADigitalRunTrackDAO dao = null;

    /**
     * <p>
     * Return All the EJB test suite.
     * </p>
     *
     * @return all the EJB test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(JPADigitalRunTrackDAOTests.class);

        /**
         * <p>
         * Setup the unit test.
         * </p>
         */
        TestSetup wrapper = new TestSetup(suite) {
                /**
                 * Create instance.
                 */
                @SuppressWarnings("unchecked")
                protected void setUp() {
                    dao = new JPADigitalRunTrackDAO();

                    // get the EntityManager
                    Ejb3Configuration cfg = new Ejb3Configuration();
                    EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
                    manager = emf.createEntityManager();
                    dao.setActiveStatusId(1);
                    dao.setLogger(LogManager.getLog());
                    dao.setUnitName("unitName");
                    context = EasyMock.createMock(SessionContext.class);

                    SearchBundle searchBundle = new SearchBundle("name", new HashMap(), "context");
                    dao.setSearchBundle(searchBundle);
                }

                /**
                 * <p>
                 * Tear down the test.
                 * </p>
                 */
                @Override
                protected void tearDown() throws Exception {
                    //to remove all
                }
            };

        return wrapper;
    }

    /**
     * <p>
     * Test the setSearchBundle method.
     * </p>
     */
    public void testSetBundle_null() {
        try {
            dao.setSearchBundle(null);
            fail("The searchBundle is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the setActiveStatusId method.
     * </p>
     */
    public void testSsetActiveStatusId_negative() {
        try {
            dao.setActiveStatusId(-1);
            fail("The active status id is negative.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#createTrack(com.topcoder.service.digitalrun.entity.Track) } method. It is
     * an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrack() throws Exception {
        manager.getTransaction().begin();
        
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(2);
        EasyMock.replay(context);

        ProjectType projectType = this.createProjectType(10L);
        manager.persist(projectType);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        manager.persist(pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        manager.persist(trackStatus);

        TrackType trackType = createTrackType();
        manager.persist(trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);

        //persist
        track = dao.createTrack(track);

        //verify
        assertTrue("It is invalid persisted.", track.getId() > 0);

        //retrieve
        track = dao.getTrack(track.getId());
        //verify
        assertEquals("Invalid persisted.", "description", track.getDescription());

        manager.remove(track);
        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(projectType);
        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#createTrack(com.topcoder.service.digitalrun.entity.Track) } method.  the
     * parameter is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrack_nulltrack() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.replay(context);

        //persist
        try {
            dao.createTrack(null);
            fail("The Track is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#createTrack(com.topcoder.service.digitalrun.entity.Track) } method.  fail
     * to get entity manager, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrack_exceptionOccurs() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        //persist
        try {
            Track track = new Track();
            dao.createTrack(track);
            fail("Fail to get entity manager");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#createTrack(com.topcoder.service.digitalrun.entity.Track) } method.  the
     * parameter is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrack_withPositiveIdtrack() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.replay(context);

        //persist
        try {
            Track track = new Track();
            track.setId(2);
            dao.createTrack(track);
            fail("The Track is invalid.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#updateTrack(com.topcoder.service.digitalrun.entity.Track) } method. It is
     * an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateTrack() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);
        EasyMock.replay(context);

        ProjectType projectType = this.createProjectType(10L);
        manager.persist(projectType);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        manager.persist(pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        manager.persist(trackStatus);

        TrackType trackType = createTrackType();
        manager.persist(trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);

        //persist
        track = dao.createTrack(track);

        //verify
        assertTrue("It is invalid persisted.", track.getId() > 0);

        //retrieve
        track = dao.getTrack(track.getId());
        //verify
        assertEquals("Invalid persisted.", "description", track.getDescription());

        //update
        track.setDescription("new Description");

        dao.updateTrack(track);

        //retrieve
        track = dao.getTrack(track.getId());
        //verify
        assertEquals("Invalid persisted.", "new Description", track.getDescription());
        //remove
        manager.remove(track);
        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(projectType);
        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#updateTrack(com.topcoder.service.digitalrun.entity.Track) } method.  the
     * parameter is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateTrack_nulltrack() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.replay(context);

        //persist
        try {
            dao.updateTrack(null);
            fail("The Track is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#updateTrack(com.topcoder.service.digitalrun.entity.Track) } method.  the
     * parameter  does not exist in database.EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateTrack_notExistTrack() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        ProjectType projectType = this.createProjectType(10L);
        manager.persist(projectType);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        manager.persist(pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        manager.persist(trackStatus);

        TrackType trackType = createTrackType();
        manager.persist(trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);
        track.setId(100);

        try {
            //persist
            dao.updateTrack(track);
            fail("The Track is not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(projectType);
        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#removeTrack(long) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrack() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(3);
        EasyMock.replay(context);

        ProjectType projectType = this.createProjectType(10L);
        manager.persist(projectType);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        manager.persist(pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        manager.persist(trackStatus);

        TrackType trackType = createTrackType();
        manager.persist(trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);

        //persist
        track = dao.createTrack(track);

        //verify
        assertTrue("It is invalid persisted.", track.getId() > 0);

        //retrieve
        track = dao.getTrack(track.getId());
        //verify
        assertEquals("Invalid persisted.", "description", track.getDescription());

        manager.remove(track);
        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(projectType);

        try {
            //verify
            dao.getTrack(track.getId());
            fail("Removed.");
        } catch (EntityNotFoundException e) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#removeTrack(long) } method. The id is negative, IAE expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrack_negativeId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.replay(context);

        try {
            //verify
            dao.removeTrack(-1L);
            fail("Removed.");
        } catch (IllegalArgumentException e) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#removeTrack(long) } method. The id does not exist in db,
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrack_notExist() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(2);
        EasyMock.replay(context);

        ProjectType projectType = this.createProjectType(10L);
        manager.persist(projectType);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        manager.persist(pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        manager.persist(trackStatus);

        TrackType trackType = createTrackType();
        manager.persist(trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);

        //persist
        track = dao.createTrack(track);

        //remove
        manager.remove(track);
        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(projectType);

        try {
            //remove again.
            dao.removeTrack(track.getId());
            fail("Removed.");
        } catch (EntityNotFoundException e) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#removeTrack(long) } method. Exception occurs during retrieve entity
     * manager. DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrack_exceptionOccurs() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(null);
        EasyMock.replay(context);

        try {
            //remove again.
            dao.removeTrack(1L);
            fail("Removed.");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#getTrack(long) } method. The id is negative, IAE expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrack_negativeId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.replay(context);

        try {
            //verify
            dao.getTrack(-1L);
            fail("Removed.");
        } catch (IllegalArgumentException e) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#getTrack(long) } method. The id is not exist, EntityNotFoundException
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrack_notExistId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager);
        EasyMock.replay(context);

        try {
            //verify
            dao.getTrack(2L);
            fail("Removed.");
        } catch (EntityNotFoundException e) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#getTrack(id) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrack() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(2);
        EasyMock.replay(context);

        ProjectType projectType = this.createProjectType(10L);
        manager.persist(projectType);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        manager.persist(pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        manager.persist(trackStatus);

        TrackType trackType = createTrackType();
        manager.persist(trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);

        //persist
        track = dao.createTrack(track);

        //verify
        assertTrue("It is invalid persisted.", track.getId() > 0);

        //retrieve
        track = dao.getTrack(track.getId());
        //verify
        assertEquals("Invalid persisted.", "description", track.getDescription());

        manager.remove(track);
        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(projectType);
        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#getActiveTracks() } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetActiveTracks() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(2);
        EasyMock.replay(context);

        ProjectType projectType = this.createProjectType(10L);
        manager.persist(projectType);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        manager.persist(pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        manager.persist(trackStatus);
        dao.setActiveStatusId(trackStatus.getId());

        TrackType trackType = createTrackType();
        manager.persist(trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);

        track.setStartDate(new Date(new Date().getTime() - 100000));
        track.setEndDate(new Date(new Date().getTime() + 100000));
        //persist
        track = dao.createTrack(track);

        //verify
        assertTrue("It is invalid persisted.", track.getId() > 0);

        //retrieve
        assertEquals("The size of active track is invalid.", 1, dao.getActiveTracks().size());

        manager.remove(track);
        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(projectType);
        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#removeTrackProjectType(Track, ProjectType) } method. It is an accuracy
     * test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackProjectType() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(2);
        EasyMock.replay(context);

        ProjectType projectType = this.createProjectType(10L);
        manager.persist(projectType);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        manager.persist(pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        manager.persist(trackStatus);
        dao.setActiveStatusId(trackStatus.getId());

        TrackType trackType = createTrackType();
        manager.persist(trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);

        track.setStartDate(new Date(new Date().getTime() - 100000));
        track.setEndDate(new Date(new Date().getTime() + 100000));
        //persist
        track = dao.createTrack(track);

        //verify
        assertTrue("It is invalid persisted.", track.getId() > 0);

        //remove project type from track
        dao.removeTrackProjectType(track, projectType);
        track.getProjectTypes().remove(projectType);
        manager.merge(track);
        manager.getTransaction().commit();
        manager.getTransaction().begin();
        manager.remove(track);
        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(projectType);
        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#removeTrackProjectType(Track, ProjectType) } method. The ProjectType does
     * not  exist, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackProjectType_notExistProjectType()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(2);
        EasyMock.replay(context);

        ProjectType projectType = this.createProjectType(10L);
        manager.persist(projectType);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        manager.persist(pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        manager.persist(trackStatus);
        dao.setActiveStatusId(trackStatus.getId());

        TrackType trackType = createTrackType();
        manager.persist(trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);

        track.setStartDate(new Date(new Date().getTime() - 100000));
        track.setEndDate(new Date(new Date().getTime() + 100000));
        //persist
        track = dao.createTrack(track);

        //verify
        assertTrue("It is invalid persisted.", track.getId() > 0);

        //remove project type from track
        try {
            dao.removeTrackProjectType(track, createProjectType(100L));
            fail("No project type exist.");
        } catch (EntityNotFoundException e) {
            //good
        }

        manager.remove(track);
        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(projectType);
        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#removeTrackProjectType(Track, ProjectType) } method. The ProjectType does
     * not  exist, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackProjectType_notExistTrack()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        ProjectType projectType = this.createProjectType(10L);
        manager.persist(projectType);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        manager.persist(pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        manager.persist(trackStatus);
        dao.setActiveStatusId(trackStatus.getId());

        TrackType trackType = createTrackType();
        manager.persist(trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);

        try {
            //remove project type from track
            dao.removeTrackProjectType(track, projectType);
            fail("No project type exist.");
        } catch (EntityNotFoundException e) {
            //good
        }

        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(projectType);
        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#addTrackProjectType(Track, ProjectType) } method. It is an accuracy test
     * case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testaddTrackProjectType() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(2);
        EasyMock.replay(context);

        ProjectType projectType = this.createProjectType(10L);
        manager.persist(projectType);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        manager.persist(pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        manager.persist(trackStatus);
        dao.setActiveStatusId(trackStatus.getId());

        TrackType trackType = createTrackType();
        manager.persist(trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);

        track.setStartDate(new Date(new Date().getTime() - 100000));
        track.setEndDate(new Date(new Date().getTime() + 100000));
        //persist
        track = dao.createTrack(track);

        projectType = this.createProjectType(101L);
        manager.persist(projectType);
        manager.getTransaction().commit();

        manager.getTransaction().begin();
        //add project type from track
        dao.addTrackProjectType(track, manager.find(ProjectType.class, 101L));

        manager.remove(track);
        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(manager.find(ProjectType.class, 101L));
        manager.remove(manager.find(ProjectType.class, 10L));
        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#addTrackProjectType(Track, ProjectType) } method. the projectType does not
     * exist, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testaddTrackProjectType_notExistPRojectType()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(2);
        EasyMock.replay(context);

        ProjectType projectType = this.createProjectType(10L);
        manager.persist(projectType);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        manager.persist(pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        manager.persist(trackStatus);
        dao.setActiveStatusId(trackStatus.getId());

        TrackType trackType = createTrackType();
        manager.persist(trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);

        track.setStartDate(new Date(new Date().getTime() - 100000));
        track.setEndDate(new Date(new Date().getTime() + 100000));
        //persist
        track = dao.createTrack(track);

        try {
            //add project type from track
            dao.addTrackProjectType(track, this.createProjectType(101L));
            fail("The projectType does not exist.");
        } catch (EntityNotFoundException e) {
            //good
        }

        manager.remove(track);
        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(projectType);
        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#addTrackProjectType(Track, ProjectType) } method. the track does not
     * exist, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testaddTrackProjectType_notExistTrack()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        ProjectType projectType = this.createProjectType(10L);
        manager.persist(projectType);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        manager.persist(pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        manager.persist(trackStatus);
        dao.setActiveStatusId(trackStatus.getId());

        TrackType trackType = createTrackType();
        manager.persist(trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType, projectType);

        try {
            //add project type from track
            dao.addTrackProjectType(track, projectType);
            fail("The projectType does not exist.");
        } catch (EntityNotFoundException e) {
            //good
        }

        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(projectType);
        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * Test searchTracks method with null filter. iae expected.
     *
     * @throws Exception into JUnit
     */
    public void testSearchTracks_nullFilter() throws Exception {
        try {
            dao.searchTracks(null);
            fail("the filter is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test searchTracks method with filter.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testSearchTracks() throws Exception {
        try {
            dao.searchTracks(DigitalRunTrackFilterFactory.createTrackIdEqualsFilter(1));
            fail("fail to search.");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //good
        }
    }
}
