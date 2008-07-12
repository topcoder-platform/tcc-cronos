/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.dao.implementations;

import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerPersistenceException;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;

import com.topcoder.util.log.LogManager;

import junit.extensions.TestSetup;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.easymock.EasyMock;

import org.hibernate.ejb.Ejb3Configuration;

import java.util.Date;

import javax.ejb.SessionContext;

import javax.persistence.EntityManagerFactory;


/**
 * <p>
 * Unit test cases for the class <code>JPADigitalRunTrackStatusDAO.</code>
 * </p>
 * @author waits
 * @version 1.0
 */
public class JPADigitalRunTrackStatusDAOTests extends BaseTestCase {
    /** JPADigitalRunTrackStatusDAO instance to test against. */
    private static JPADigitalRunTrackStatusDAO dao = null;

    /**
     * <p>
     * Return All the EJB test suite.
     * </p>
     *
     * @return all the EJB test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(JPADigitalRunTrackStatusDAOTests.class);

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
                    dao = new JPADigitalRunTrackStatusDAO();

                    // get the EntityManager
                    Ejb3Configuration cfg = new Ejb3Configuration();
                    EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
                    manager = emf.createEntityManager();
                    dao.setLogger(LogManager.getLog());
                    dao.setUnitName("unitName");
                    context = EasyMock.createMock(SessionContext.class);
                    dao.setUnitName("unitName");
                    dao.setLogger(LogManager.getLog());
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
     * Test the {@link JPADigitalTrackTypeDAO#createTrackStatus(TrackStatus) } method.  the param is null, iae
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrackStatus_nullParam() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.replay(context);

        //persist
        try {
            dao.createTrackStatus(null);
            fail("The TrackStatus is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#createTrackStatus(TrackStatus) } method.  the param is null, iae
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrackStatus_positiveIdParam()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.replay(context);

        //persist
        try {
            TrackStatus type = createTrackStatus();
            type.setId(1L);
            dao.createTrackStatus(type);
            fail("The TrackStatus has positive id.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#createTrackStatus(TrackStatus) } method. It is an accuracy test
     * case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrackStatus() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        TrackStatus trackStatus = createTrackStatus();
        EasyMock.replay(context);
        //persist
        trackStatus = dao.createTrackStatus(trackStatus);
        manager.getTransaction().commit();

        assertTrue("invalid persist.", trackStatus.getId() > 0);

        //retrieve
        TrackStatus persisted = dao.getTrackStatus(trackStatus.getId());
        assertEquals("The description is invalid.", "description", persisted.getDescription());
        manager.getTransaction().begin();
        //remove
        dao.remove(TrackStatus.class, trackStatus.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllTrackStatuses().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#updateTrackStatus(TrackStatus) } method.  the param is null, iae
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateTrackStatus_nullParam() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.replay(context);

        //persist
        try {
            dao.updateTrackStatus(null);
            fail("The TrackStatus is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#updateTrackStatus(TrackStatus) } method. It is an accuracy test
     * case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateTrackStatus() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(6);

        TrackStatus trackStatus = createTrackStatus();
        EasyMock.replay(context);
        //persist
        trackStatus = dao.createTrackStatus(trackStatus);

        assertTrue("invalid persist.", trackStatus.getId() > 0);

        //retrieve
        TrackStatus persisted = dao.getTrackStatus(trackStatus.getId());
        assertEquals("The description is invalid.", "description", persisted.getDescription());
        //update
        persisted.setDescription("new description");
        persisted.setCreationDate(new Date());
        persisted.setModificationDate(new Date());
        dao.updateTrackStatus(persisted);
        manager.getTransaction().commit();

        //verify
        TrackStatus updated = dao.getTrackStatus(trackStatus.getId());
        assertEquals("The description is invalid.", "new description", updated.getDescription());

        manager.getTransaction().begin();
        //remove
        dao.remove(TrackStatus.class, trackStatus.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllTrackStatuses().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#removeTrackStatus(long) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackStatus() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(3);

        TrackStatus trackStatus = createTrackStatus();
        EasyMock.replay(context);
        //persist
        trackStatus = dao.createTrackStatus(trackStatus);

        //remove
        dao.removeTrackStatus(trackStatus.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllTrackStatuses().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#removeTrackStatus(long) } method.  The id is negative. iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackStatus_negativeId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);

        EasyMock.replay(context);

        try {
            //remove
            dao.removeTrackStatus(-1L);
            fail("The TrackStatus id is negative.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#removeTrackStatus(long) } method.  The id is negative.
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackStatus_notExistId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.removeTrackStatus(100L);
            fail("The TrackStatus id does not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#removeTrackStatus(long) } method.  Fail to get entityManager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackStatus_exception() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.removeTrackStatus(100L);
            fail("The TrackStatus id does not exist.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#getTrackStatus(long) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackStatus() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        TrackStatus trackStatus = createTrackStatus();
        EasyMock.replay(context);
        //persist
        trackStatus = dao.createTrackStatus(trackStatus);

        //retrieve
        TrackStatus persisted = dao.getTrackStatus(trackStatus.getId());
        assertEquals("The description is invalid.", "description", persisted.getDescription());

        //remove
        dao.removeTrackStatus(trackStatus.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllTrackStatuses().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#getAllTrackStatuss() } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetAllTrackStatuss() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        TrackStatus trackStatus = createTrackStatus();
        EasyMock.replay(context);
        //persist
        trackStatus = dao.createTrackStatus(trackStatus);

        //retrieve
        assertEquals("The size of result is invalid.", 1, dao.getAllTrackStatuses().size());

        //remove
        dao.removeTrackStatus(trackStatus.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllTrackStatuses().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#getAllTrackStatuss(long) } method.  Fail to get entityManager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testgetAllTrackStatuss_exception() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.getAllTrackStatuses();
            fail("Fail to get entity manager.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#getTrackStatus(long) } method.  The id is negative. iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackStatus_negativeId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);

        EasyMock.replay(context);

        try {
            //remove
            dao.getTrackStatus(-1L);
            fail("The TrackStatus id is negative.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#getTrackStatus(long) } method.  The id is negative.
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackStatus_notExistId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.getTrackStatus(100L);
            fail("The TrackStatus id does not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#getTrackStatus(long) } method.  Fail to get entityManager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackStatus_exception() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.getTrackStatus(100L);
            fail("The TrackStatus id does not exist.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }
}
