/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.stresstests;

import com.topcoder.search.builder.SearchBundle;

import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackDAO;

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
     * Test the {@link JPADigitalRunTrackDAO#updateTrack(com.topcoder.service.digitalrun.entity.Track) } method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCRUDTrack() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            manager.getTransaction().begin();
            context = EasyMock.createMock(SessionContext.class);
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

        printResult("CRUD on Track ", NUMBER);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#getActiveTracks() } method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetActiveTracks() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            manager.getTransaction().begin();
            context = EasyMock.createMock(SessionContext.class);
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

        printResult("getActiveTracks ", NUMBER);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackDAO#removeTrackProjectType(Track, ProjectType) } method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackProjectType() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            manager.getTransaction().begin();
            context = EasyMock.createMock(SessionContext.class);
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

        printResult("removeTrackProjectType ", NUMBER);
    }
}
