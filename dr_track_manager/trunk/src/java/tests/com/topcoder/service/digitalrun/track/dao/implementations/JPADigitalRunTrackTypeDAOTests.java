/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.dao.implementations;

import com.topcoder.service.digitalrun.entity.TrackType;
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
 * Unit test cases for the class <code>JPADigitalRunTrackTypeDAO.</code>
 * </p>
 * @author waits
 * @version 1.0
 */
public class JPADigitalRunTrackTypeDAOTests extends BaseTestCase {
    /** JPADigitalRunTrackTypeDAO instance to test against. */
    private static JPADigitalRunTrackTypeDAO dao = null;

    /**
     * <p>
     * Return All the EJB test suite.
     * </p>
     *
     * @return all the EJB test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(JPADigitalRunTrackTypeDAOTests.class);

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
                    dao = new JPADigitalRunTrackTypeDAO();

                    // get the EntityManager
                    Ejb3Configuration cfg = new Ejb3Configuration();
                    EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
                    manager = emf.createEntityManager();
                    dao.setLogger(LogManager.getLog());
                    dao.setUnitName("unitName");
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
     * Test the {@link JPADigitalTrackTypeDAO#createTrackType(TrackType) } method.  the param is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrackType_nullParam() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.replay(context);

        //persist
        try {
            dao.createTrackType(null);
            fail("The TrackType is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#createTrackType(TrackType) } method.  the param is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrackType_positiveIdParam() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.replay(context);

        //persist
        try {
            TrackType type = createTrackType();
            type.setId(1L);
            dao.createTrackType(type);
            fail("The TrackType has positive id.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#createTrackType(TrackType) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateTrackType() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        TrackType trackType = createTrackType();
        EasyMock.replay(context);
        //persist
        trackType = dao.createTrackType(trackType);
        manager.getTransaction().commit();

        assertTrue("invalid persist.", trackType.getId() > 0);

        //retrieve
        TrackType persisted = dao.getTrackType(trackType.getId());
        assertEquals("The description is invalid.", "description", persisted.getDescription());
        manager.getTransaction().begin();
        //remove
        dao.remove(TrackType.class, trackType.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllTrackTypes().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#updateTrackType(TrackType) } method.  the param is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateTrackType_nullParam() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.replay(context);

        //persist
        try {
            dao.updateTrackType(null);
            fail("The TrackType is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#updateTrackType(TrackType) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateTrackType() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(6);

        TrackType trackType = createTrackType();
        EasyMock.replay(context);
        //persist
        trackType = dao.createTrackType(trackType);

        assertTrue("invalid persist.", trackType.getId() > 0);

        //retrieve
        TrackType persisted = dao.getTrackType(trackType.getId());
        assertEquals("The description is invalid.", "description", persisted.getDescription());
        //update
        persisted.setDescription("new description");
        persisted.setCreationDate(new Date());
        persisted.setModificationDate(new Date());
        dao.updateTrackType(persisted);
        manager.getTransaction().commit();

        //verify
        TrackType updated = dao.getTrackType(trackType.getId());
        assertEquals("The description is invalid.", "new description", updated.getDescription());

        manager.getTransaction().begin();
        //remove
        dao.remove(TrackType.class, trackType.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllTrackTypes().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#removeTrackType(long) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackType() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(3);

        TrackType trackType = createTrackType();
        EasyMock.replay(context);
        //persist
        trackType = dao.createTrackType(trackType);

        //remove
        dao.removeTrackType(trackType.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllTrackTypes().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#removeTrackType(long) } method.  The id is negative. iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackType_negativeId() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.replay(context);

        try {
            //remove
            dao.removeTrackType(-1L);
            fail("The TrackType id is negative.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#removeTrackType(long) } method.  The id is negative.
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackType_notExistId() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.removeTrackType(100L);
            fail("The TrackType id does not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#removeTrackType(long) } method.  Fail to get entityManager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemoveTrackType_exception() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.removeTrackType(100L);
            fail("The TrackType id does not exist.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#getTrackType(long) } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackType() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        TrackType trackType = createTrackType();
        EasyMock.replay(context);
        //persist
        trackType = dao.createTrackType(trackType);

        //retrieve
        TrackType persisted = dao.getTrackType(trackType.getId());
        assertEquals("The description is invalid.", "description", persisted.getDescription());

        //remove
        dao.removeTrackType(trackType.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllTrackTypes().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#getAllTrackTypes() } method. It is an accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetAllTrackTypes() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        TrackType trackType = createTrackType();
        EasyMock.replay(context);
        //persist
        trackType = dao.createTrackType(trackType);

        //retrieve
        assertEquals("The size of result is invalid.", 1, dao.getAllTrackTypes().size());

        //remove
        dao.removeTrackType(trackType.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllTrackTypes().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#getAllTrackTypes(long) } method.  Fail to get entityManager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testgetAllTrackTypes_exception() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.getAllTrackTypes();
            fail("Fail to get entity manager.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#getTrackType(long) } method.  The id is negative. iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackType_negativeId() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.replay(context);

        try {
            //remove
            dao.getTrackType(-1L);
            fail("The TrackType id is negative.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#getTrackType(long) } method.  The id is negative.
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackType_notExistId() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.getTrackType(100L);
            fail("The TrackType id does not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#getTrackType(long) } method.  Fail to get entityManager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTrackType_exception() throws Exception {
        manager.getTransaction().begin();
        context = EasyMock.createMock(SessionContext.class);
        dao.setSessionContext(context);

        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.getTrackType(100L);
            fail("The TrackType id does not exist.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }
}
