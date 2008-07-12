/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.manager.bean;

import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;
import com.topcoder.service.digitalrun.track.DigitalRunTrackFilterFactory;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerPersistenceException;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;
import com.topcoder.service.digitalrun.track.dao.implementations.BaseTestCase;

import junit.extensions.TestSetup;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.easymock.EasyMock;

import org.hibernate.ejb.Ejb3Configuration;

import java.lang.reflect.Method;

import java.util.Date;

import javax.ejb.SessionContext;

import javax.persistence.EntityManagerFactory;


/**
 * <p>
 * Unit test cases for the class <code>DigitalRunTrackManagerBean.</code>
 * </p>
 * @author waits
 * @version 1.0
 */
public class DigitalRunTrackManagerBeanTests extends BaseTestCase {
    /** DigitalRunTrackManagerBean to test against. */
    private static DigitalRunTrackManagerBean bean = null;

    /**
     * <p>
     * Return All the EJB test suite.
     * </p>
     *
     * @return all the EJB test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(DigitalRunTrackManagerBeanTests.class);

        /**
         * <p>
         * Setup the unit test.
         * </p>
         */
        TestSetup wrapper = new TestSetup(suite) {
                /**
                 * Create instance.
                 *
                 * @throws Exception into JUnit
                 */
                @SuppressWarnings("unchecked")
                protected void setUp() throws Exception {
                    context = EasyMock.createMock(SessionContext.class);
                    bean = new DigitalRunTrackManagerBean();

                    // get the EntityManager
                    Ejb3Configuration cfg = new Ejb3Configuration();
                    EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
                    manager = emf.createEntityManager();

                    setPrivateField(bean, "unitName", "unitName");
                    setPrivateField(bean, "trackDAOKey", "trackDAOKey");
                    setPrivateField(bean, "pointsCalculatorDAOKey", "pointsCalculatorDAOKey");
                    setPrivateField(bean, "projectTypeDAOKey", "projectTypeDAOKey");
                    setPrivateField(bean, "trackStatusDAOKey", "trackStatusDAOKey");
                    setPrivateField(bean, "trackTypeDAOKey", "trackTypeDAOKey");
                    setPrivateField(bean, "sessionContext", context);
                    setPrivateField(bean, "activeStatusId", 1L);

                    Method method = DigitalRunTrackManagerBean.class.getDeclaredMethod("initialize");
                    method.setAccessible(true);
                    method.invoke(bean);
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
     * <code>Test the {@link DigitalRunTrackManagerBean#DigitalRunTrackManagerBean()} method.</code>
     *
     * @throws Exception into Junit
     */
    public void testCtor() throws Exception {
        assertNotNull("Fail to instantiate the instance.", bean);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#createTrack(com.topcoder.service.digitalrun.entity.Track) } method.
     * It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrack() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
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
        track = bean.createTrack(track);

        //verify
        assertTrue("It is invalid persisted.", track.getId() > 0);

        //retrieve
        track = bean.getTrack(track.getId());
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
        context.setRollbackOnly();
        EasyMock.replay(context);

        //persist
        try {
            bean.createTrack(null);
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
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        //persist
        try {
            Track track = new Track();
            bean.createTrack(track);
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
        context.setRollbackOnly();
        EasyMock.replay(context);

        //persist
        try {
            Track track = new Track();
            track.setId(2);
            bean.createTrack(track);
            fail("The Track is invalid.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#updateTrack(com.topcoder.service.digitalrun.entity.Track) } method.
     * It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateTrack() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
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
        track = bean.createTrack(track);

        //verify
        assertTrue("It is invalid persisted.", track.getId() > 0);

        //retrieve
        track = bean.getTrack(track.getId());
        //verify
        assertEquals("Invalid persisted.", "description", track.getDescription());

        //update
        track.setDescription("new Description");

        bean.updateTrack(track);

        //retrieve
        track = bean.getTrack(track.getId());
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
     * Test the {@link DigitalRunTrackManagerBean#updateTrack(com.topcoder.service.digitalrun.entity.Track) } method.
     * the parameter is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateTrack_nulltrack() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.replay(context);

        //persist
        try {
            bean.updateTrack(null);
            fail("The Track is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackbean#updateTrack(com.topcoder.service.digitalrun.entity.Track) } method.  the
     * parameter  does not exist in database.EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateTrack_notExistTrack() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
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
            bean.updateTrack(track);
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
     * Test the {@link JPADigitalRunTrackbean#removeTrack(long) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrack() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
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
        track = bean.createTrack(track);

        //verify
        assertTrue("It is invalid persisted.", track.getId() > 0);

        //retrieve
        track = bean.getTrack(track.getId());
        //verify
        assertEquals("Invalid persisted.", "description", track.getDescription());

        manager.remove(track);
        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(projectType);

        try {
            //verify
            bean.getTrack(track.getId());
            fail("Removed.");
        } catch (EntityNotFoundException e) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackbean#removeTrack(long) } method. The id is negative, IAE expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrack_negativeId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.replay(context);

        try {
            //verify
            bean.removeTrack(-1L);
            fail("Removed.");
        } catch (IllegalArgumentException e) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackbean#removeTrack(long) } method. The id does not exist in db,
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrack_notExist() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
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
        track = bean.createTrack(track);

        //remove
        manager.remove(track);
        manager.remove(trackType);
        manager.remove(trackStatus);
        manager.remove(pointsCalculator);
        manager.remove(projectType);

        try {
            //remove again.
            bean.removeTrack(track.getId());
            fail("Removed.");
        } catch (EntityNotFoundException e) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackbean#removeTrack(long) } method. Exception occurs during retrieve entity
     * manager. DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrack_exceptionOccurs() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(null);
        EasyMock.replay(context);

        try {
            //remove again.
            bean.removeTrack(1L);
            fail("Removed.");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackbean#getTrack(long) } method. The id is negative, IAE expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrack_negativeId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.replay(context);

        try {
            //verify
            bean.getTrack(-1L);
            fail("Removed.");
        } catch (IllegalArgumentException e) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackbean#getTrack(long) } method. The id is not exist, EntityNotFoundException
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrack_notExistId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(manager);
        EasyMock.replay(context);

        try {
            //verify
            bean.getTrack(2L);
            fail("Removed.");
        } catch (EntityNotFoundException e) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackbean#getTrack(id) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrack() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
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
        track = bean.createTrack(track);

        //verify
        assertTrue("It is invalid persisted.", track.getId() > 0);

        //retrieve
        track = bean.getTrack(track.getId());
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
     * Test the {@link JPADigitalRunTrackbean#getActiveTracks() } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetActiveTracks() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
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

        track.setStartDate(new Date(new Date().getTime() - 100000));
        track.setEndDate(new Date(new Date().getTime() + 100000));
        //persist
        track = bean.createTrack(track);

        //verify
        assertTrue("It is invalid persisted.", track.getId() > 0);

        //retrieve
        assertEquals("The size of active track is invalid.", 0, bean.getActiveTracks().size());

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
     * Test the {@link JPADigitalRunTrackbean#removeTrackProjectType(Track, ProjectType) } method. It is an accuracy
     * test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackProjectType() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
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

        track.setStartDate(new Date(new Date().getTime() - 100000));
        track.setEndDate(new Date(new Date().getTime() + 100000));
        //persist
        track = bean.createTrack(track);

        //verify
        assertTrue("It is invalid persisted.", track.getId() > 0);

        //remove project type from track
        bean.removeTrackProjectType(track, projectType);
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
     * Test the {@link JPADigitalRunTrackbean#removeTrackProjectType(Track, ProjectType) } method. The ProjectType does
     * not  exist, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackProjectType_notExistProjectType()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
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
        track = bean.createTrack(track);

        //verify
        assertTrue("It is invalid persisted.", track.getId() > 0);

        //remove project type from track
        try {
            bean.removeTrackProjectType(track, createProjectType(100L));
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
     * Test the {@link JPADigitalRunTrackbean#removeTrackProjectType(Track, ProjectType) } method. The ProjectType does
     * not  exist, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackProjectType_notExistTrack()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
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

        try {
            //remove project type from track
            bean.removeTrackProjectType(track, projectType);
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
     * Test the {@link JPADigitalRunTrackbean#addTrackProjectType(Track, ProjectType) } method. It is an accuracy test
     * case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testaddTrackProjectType() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
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
        track = bean.createTrack(track);

        projectType = this.createProjectType(101L);
        manager.persist(projectType);
        manager.getTransaction().commit();

        manager.getTransaction().begin();
        //add project type from track
        bean.addTrackProjectType(track, manager.find(ProjectType.class, 101L));

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
     * Test the {@link JPADigitalRunTrackbean#addTrackProjectType(Track, ProjectType) } method. the projectType does
     * not exist, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testaddTrackProjectType_notExistPRojectType()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
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
        track = bean.createTrack(track);

        try {
            //add project type from track
            bean.addTrackProjectType(track, this.createProjectType(101L));
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
     * Test the {@link JPADigitalRunTrackbean#addTrackProjectType(Track, ProjectType) } method. the track does not
     * exist, EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testaddTrackProjectType_notExistTrack()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
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

        try {
            //add project type from track
            bean.addTrackProjectType(track, projectType);
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
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.replay(context);

        try {
            bean.searchTracks(null);
            fail("the filter is null.");
        } catch (IllegalArgumentException e) {
            //good
        }

        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test searchTracks method with filter.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testSearchTracks() throws Exception {
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.replay(context);

        try {
            bean.searchTracks(DigitalRunTrackFilterFactory.createTrackIdEqualsFilter(1));
            fail("fail to search.");
        } catch (DigitalRunTrackManagerPersistenceException e) {
            //good
        }

        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#createPointsCalculator(PointsCalculator) } method.  the param
     * is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreatePointsCalculator_nullParam()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.replay(context);

        //persist
        try {
            bean.createPointsCalculator(null);
            fail("The PointsCalculator is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#createPointsCalculator(PointsCalculator) } method.  the param
     * is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreatePointsCalculator_positiveIdParam()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();

        EasyMock.replay(context);

        //persist
        try {
            bean.createPointsCalculator(createPointsCalculator(1L));
            fail("The PointsCalculator has positive id.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#createPointsCalculator(PointsCalculator) } method. It is an
     * accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreatePointsCalculator() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(3);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        EasyMock.replay(context);
        //persist
        pointsCalculator = bean.createPointsCalculator(pointsCalculator);
        manager.getTransaction().commit();

        assertTrue("invalid persist.", pointsCalculator.getId() > 0);

        //retrieve
        PointsCalculator persisted = bean.getPointsCalculator(pointsCalculator.getId());
        assertEquals("The name is invalid.", "className", persisted.getClassName());
        assertEquals("The description is invalid.", "description", persisted.getDescription());
        manager.getTransaction().begin();
        //remove
        manager.remove(manager.find(PointsCalculator.class, pointsCalculator.getId()));
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllPointsCalculators().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#updatePointsCalculator(PointsCalculator) } method.  the param
     * is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdatePointsCalculator_nullParam()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.replay(context);

        //persist
        try {
            bean.updatePointsCalculator(null);
            fail("The PointsCalculator is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#updatePointsCalculator(PointsCalculator) } method. It is an
     * accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdatePointsCalculator() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(5);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        EasyMock.replay(context);
        //persist
        pointsCalculator = bean.createPointsCalculator(pointsCalculator);

        assertTrue("invalid persist.", pointsCalculator.getId() > 0);

        //retrieve
        PointsCalculator persisted = bean.getPointsCalculator(pointsCalculator.getId());
        assertEquals("The name is invalid.", "className", persisted.getClassName());
        assertEquals("The description is invalid.", "description", persisted.getDescription());
        //update
        persisted.setDescription("new description");
        persisted.setCreationDate(new Date());
        persisted.setModificationDate(new Date());
        bean.updatePointsCalculator(persisted);
        manager.getTransaction().commit();

        //verify
        PointsCalculator updated = bean.getPointsCalculator(pointsCalculator.getId());
        assertEquals("The description is invalid.", "new description", updated.getDescription());

        manager.getTransaction().begin();
        //remove
        manager.remove(manager.find(PointsCalculator.class, pointsCalculator.getId()));
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllPointsCalculators().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#removePointsCalculator(long) } method. It is an accuracy test
     * case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemovePointsCalculator() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(3);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        EasyMock.replay(context);
        //persist
        pointsCalculator = bean.createPointsCalculator(pointsCalculator);

        //remove
        bean.removePointsCalculator(pointsCalculator.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllPointsCalculators().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#removePointsCalculator(long) } method.  The id is negative.
     * iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemovePointsCalculator_negativeId()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.replay(context);

        try {
            //remove
            bean.removePointsCalculator(-1L);
            fail("The PointsCalculator id is negative.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#removePointsCalculator(long) } method.  The id is negative.
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemovePointsCalculator_notExistId()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.removePointsCalculator(100L);
            fail("The PointsCalculator id does not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#removePointsCalculator(long) } method.  Fail to get
     * entityManager, DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemovePointsCalculator_exception()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.removePointsCalculator(100L);
            fail("The PointsCalculator id does not exist.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getPointsCalculator(long) } method. It is an accuracy test
     * case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetPointsCalculator() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        EasyMock.replay(context);
        //persist
        pointsCalculator = bean.createPointsCalculator(pointsCalculator);

        //retrieve
        PointsCalculator persisted = bean.getPointsCalculator(pointsCalculator.getId());
        assertEquals("The name is invalid.", "className", persisted.getClassName());
        assertEquals("The description is invalid.", "description", persisted.getDescription());

        //remove
        bean.removePointsCalculator(pointsCalculator.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllPointsCalculators().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getAllPointsCalculators() } method. It is an accuracy test
     * case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetAllPointsCalculators() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        EasyMock.replay(context);
        //persist
        pointsCalculator = bean.createPointsCalculator(pointsCalculator);

        //retrieve
        assertEquals("The size of result is invalid.", 1, bean.getAllPointsCalculators().size());

        //remove
        bean.removePointsCalculator(pointsCalculator.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllPointsCalculators().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getAllPointsCalculators(long) } method.  Fail to get
     * entityManager, DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testgetAllPointsCalculators_exception()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.getAllPointsCalculators();
            fail("Fail to get entity manager.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getPointsCalculator(long) } method.  The id is negative. iae
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetPointsCalculator_negativeId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();

        EasyMock.replay(context);

        try {
            //remove
            bean.getPointsCalculator(-1L);
            fail("The PointsCalculator id is negative.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getPointsCalculator(long) } method.  The id is negative.
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetPointsCalculator_notExistId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.getPointsCalculator(100L);
            fail("The PointsCalculator id does not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getPointsCalculator(long) } method.  Fail to get
     * entityManager, DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetPointsCalculator_exception() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.getPointsCalculator(100L);
            fail("The PointsCalculator id does not exist.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getProjectType(long)} method.It is an accuracy test case.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetProjectType() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(2);
        EasyMock.replay(context);

        //persist
        ProjectType projectType = createProjectType(10L);
        manager.persist(projectType);

        //retrieve
        projectType = bean.getProjectType(10L);
        manager.getTransaction().commit();

        //verify
        assertEquals("The name is invalid.", "description", projectType.getDescription());
        manager.getTransaction().begin();
        //remove
        manager.remove(manager.find(ProjectType.class, projectType.getId()));
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllProjectTypes().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getProjectType(long)} method.the id does not exist.
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetProjectType_notExistEntity() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        //retrieve
        try {
            bean.getProjectType(100);
            fail("The ProjectType id does not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.getTransaction().commit();

        //verify
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getProjectType(long)} method.Fail to retrieve entity manager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetProjectType_error() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        //retrieve
        try {
            bean.getProjectType(100);
            fail("Fail to retrieve the entity manager.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();

        //verify
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getProjectType(long)} method. The id is negative.
     * IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetProjectType_negativeId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.replay(context);

        //retrieve
        try {
            bean.getProjectType(-1L);
            fail("The id is negative.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getAllProjectTypes()} method.Fail to retrieve entity manager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetAllProjectTypes_error() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        //retrieve
        try {
            bean.getAllProjectTypes();
            fail("Fail to retrieve the entity manager.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();

        //verify
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getAllProjectTypes()} method.It is an accuracy test case.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetAllProjectTypes() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(3);
        EasyMock.replay(context);

        //persist
        ProjectType projectType = createProjectType(10L);
        manager.persist(projectType);

        //verify
        assertEquals("The size of result is invalid.", 1, bean.getAllProjectTypes().size());

        //retrieve
        projectType = bean.getAllProjectTypes().get(0);

        //verify
        assertEquals("The name is invalid.", "description", projectType.getDescription());

        //remove
        manager.remove(manager.find(ProjectType.class, projectType.getId()));
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllProjectTypes().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#createTrackStatus(TrackStatus) } method.  the param is null, iae
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrackStatus_nullParam() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();

        EasyMock.replay(context);

        //persist
        try {
            bean.createTrackStatus(null);
            fail("The TrackStatus is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusbean#createTrackStatus(TrackStatus) } method.  the param is null, iae
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrackStatus_positiveIdParam()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();

        EasyMock.replay(context);

        //persist
        try {
            TrackStatus type = createTrackStatus();
            type.setId(1L);
            bean.createTrackStatus(type);
            fail("The TrackStatus has positive id.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusbean#createTrackStatus(TrackStatus) } method. It is an accuracy test
     * case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrackStatus() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(3);

        TrackStatus trackStatus = createTrackStatus();
        EasyMock.replay(context);
        //persist
        trackStatus = bean.createTrackStatus(trackStatus);
        manager.getTransaction().commit();

        assertTrue("invalid persist.", trackStatus.getId() > 0);

        //retrieve
        TrackStatus persisted = bean.getTrackStatus(trackStatus.getId());
        assertEquals("The description is invalid.", "description", persisted.getDescription());
        manager.getTransaction().begin();
        //remove
        manager.remove(manager.find(TrackStatus.class, trackStatus.getId()));
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllTrackStatuses().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusbean#updateTrackStatus(TrackStatus) } method.  the param is null, iae
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateTrackStatus_nullParam() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();

        EasyMock.replay(context);

        //persist
        try {
            bean.updateTrackStatus(null);
            fail("The TrackStatus is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusbean#updateTrackStatus(TrackStatus) } method. It is an accuracy test
     * case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateTrackStatus() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(5);

        TrackStatus trackStatus = createTrackStatus();
        EasyMock.replay(context);
        //persist
        trackStatus = bean.createTrackStatus(trackStatus);

        assertTrue("invalid persist.", trackStatus.getId() > 0);

        //retrieve
        TrackStatus persisted = bean.getTrackStatus(trackStatus.getId());
        assertEquals("The description is invalid.", "description", persisted.getDescription());
        //update
        persisted.setDescription("new description");
        persisted.setCreationDate(new Date());
        persisted.setModificationDate(new Date());
        bean.updateTrackStatus(persisted);
        manager.getTransaction().commit();

        //verify
        TrackStatus updated = bean.getTrackStatus(trackStatus.getId());
        assertEquals("The description is invalid.", "new description", updated.getDescription());

        manager.getTransaction().begin();
        //remove
        manager.remove(manager.find(TrackStatus.class, trackStatus.getId()));
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllTrackStatuses().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusbean#removeTrackStatus(long) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackStatus() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(3);

        TrackStatus trackStatus = createTrackStatus();
        EasyMock.replay(context);
        //persist
        trackStatus = bean.createTrackStatus(trackStatus);

        //remove
        bean.removeTrackStatus(trackStatus.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllTrackStatuses().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusbean#removeTrackStatus(long) } method.  The id is negative. iae
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackStatus_negativeId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.replay(context);

        try {
            //remove
            bean.removeTrackStatus(-1L);
            fail("The TrackStatus id is negative.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusbean#removeTrackStatus(long) } method.  The id is negative.
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackStatus_notExistId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.removeTrackStatus(100L);
            fail("The TrackStatus id does not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusbean#removeTrackStatus(long) } method.  Fail to get entityManager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackStatus_exception() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.removeTrackStatus(100L);
            fail("The TrackStatus id does not exist.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusbean#getTrackStatus(long) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackStatus() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        TrackStatus trackStatus = createTrackStatus();
        EasyMock.replay(context);
        //persist
        trackStatus = bean.createTrackStatus(trackStatus);

        //retrieve
        TrackStatus persisted = bean.getTrackStatus(trackStatus.getId());
        assertEquals("The description is invalid.", "description", persisted.getDescription());

        //remove
        bean.removeTrackStatus(trackStatus.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllTrackStatuses().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusbean#getAllTrackStatuss() } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetAllTrackStatuss() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        TrackStatus trackStatus = createTrackStatus();
        EasyMock.replay(context);
        //persist
        trackStatus = bean.createTrackStatus(trackStatus);

        //retrieve
        assertEquals("The size of result is invalid.", 1, bean.getAllTrackStatuses().size());

        //remove
        bean.removeTrackStatus(trackStatus.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllTrackStatuses().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusbean#getAllTrackStatuss(long) } method.  Fail to get entityManager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testgetAllTrackStatuss_exception() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.getAllTrackStatuses();
            fail("Fail to get entity manager.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusbean#getTrackStatus(long) } method.  The id is negative. iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackStatus_negativeId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();

        EasyMock.replay(context);

        try {
            //remove
            bean.getTrackStatus(-1L);
            fail("The TrackStatus id is negative.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusbean#getTrackStatus(long) } method.  The id is negative.
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackStatus_notExistId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.getTrackStatus(100L);
            fail("The TrackStatus id does not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusbean#getTrackStatus(long) } method.  Fail to get entityManager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackStatus_exception() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.getTrackStatus(100L);
            fail("The TrackStatus id does not exist.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#createTrackType(TrackType) } method.  the param is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrackType_nullParam() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();

        EasyMock.replay(context);

        //persist
        try {
            bean.createTrackType(null);
            fail("The TrackType is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#createTrackType(TrackType) } method.  the param is null, iae
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrackType_positiveIdParam() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();

        EasyMock.replay(context);

        //persist
        try {
            TrackType type = createTrackType();
            type.setId(1L);
            bean.createTrackType(type);
            fail("The TrackType has positive id.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#createTrackType(TrackType) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrackType() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(3);

        TrackType trackType = createTrackType();
        EasyMock.replay(context);
        //persist
        trackType = bean.createTrackType(trackType);
        manager.getTransaction().commit();

        assertTrue("invalid persist.", trackType.getId() > 0);

        //retrieve
        TrackType persisted = bean.getTrackType(trackType.getId());
        assertEquals("The description is invalid.", "description", persisted.getDescription());
        manager.getTransaction().begin();
        //remove
        manager.remove(manager.find(TrackType.class, trackType.getId()));
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllTrackTypes().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#updateTrackType(TrackType) } method.  the param is null, iae
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateTrackType_nullParam() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.replay(context);

        //persist
        try {
            bean.updateTrackType(null);
            fail("The TrackType is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#updateTrackType(TrackType) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateTrackType() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(5);

        TrackType trackType = createTrackType();
        EasyMock.replay(context);
        //persist
        trackType = bean.createTrackType(trackType);

        assertTrue("invalid persist.", trackType.getId() > 0);

        //retrieve
        TrackType persisted = bean.getTrackType(trackType.getId());
        assertEquals("The description is invalid.", "description", persisted.getDescription());
        //update
        persisted.setDescription("new description");
        persisted.setCreationDate(new Date());
        persisted.setModificationDate(new Date());
        bean.updateTrackType(persisted);
        manager.getTransaction().commit();

        //verify
        TrackType updated = bean.getTrackType(trackType.getId());
        assertEquals("The description is invalid.", "new description", updated.getDescription());

        manager.getTransaction().begin();
        //remove
        manager.remove(manager.find(TrackType.class, trackType.getId()));
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllTrackTypes().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#removeTrackType(long) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackType() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(3);

        TrackType trackType = createTrackType();
        EasyMock.replay(context);
        //persist
        trackType = bean.createTrackType(trackType);

        //remove
        bean.removeTrackType(trackType.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllTrackTypes().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#removeTrackType(long) } method.  The id is negative. iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackType_negativeId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();

        EasyMock.replay(context);

        try {
            //remove
            bean.removeTrackType(-1L);
            fail("The TrackType id is negative.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#removeTrackType(long) } method.  The id is negative.
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackType_notExistId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.removeTrackType(100L);
            fail("The TrackType id does not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#removeTrackType(long) } method.  Fail to get entityManager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackType_exception() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.removeTrackType(100L);
            fail("The TrackType id does not exist.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getTrackType(long) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackType() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        TrackType trackType = createTrackType();
        EasyMock.replay(context);
        //persist
        trackType = bean.createTrackType(trackType);

        //retrieve
        TrackType persisted = bean.getTrackType(trackType.getId());
        assertEquals("The description is invalid.", "description", persisted.getDescription());

        //remove
        bean.removeTrackType(trackType.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllTrackTypes().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getAllTrackTypes() } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetAllTrackTypes() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        TrackType trackType = createTrackType();
        EasyMock.replay(context);
        //persist
        trackType = bean.createTrackType(trackType);

        //retrieve
        assertEquals("The size of result is invalid.", 1, bean.getAllTrackTypes().size());

        //remove
        bean.removeTrackType(trackType.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, bean.getAllTrackTypes().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getAllTrackTypes(long) } method.  Fail to get entityManager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testgetAllTrackTypes_exception() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.getAllTrackTypes();
            fail("Fail to get entity manager.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getTrackType(long) } method.  The id is negative. iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackType_negativeId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();

        EasyMock.replay(context);

        try {
            //remove
            bean.getTrackType(-1L);
            fail("The TrackType id is negative.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getTrackType(long) } method.  The id is negative.
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackType_notExistId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.getTrackType(100L);
            fail("The TrackType id does not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link DigitalRunTrackManagerBean#getTrackType(long) } method.  Fail to get entityManager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackType_exception() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        context.setRollbackOnly();
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            bean.getTrackType(100L);
            fail("The TrackType id does not exist.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }
}
