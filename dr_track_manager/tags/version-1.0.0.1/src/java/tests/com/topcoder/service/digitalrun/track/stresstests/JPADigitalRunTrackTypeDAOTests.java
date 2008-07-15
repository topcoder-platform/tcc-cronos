/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.stresstests;

import com.topcoder.service.digitalrun.entity.TrackType;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackTypeDAO;

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
 * Stress test cases for the class <code>JPADigitalRunTrackTypeDAO.</code>
 * </p>
 * @author telly
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
                    context = EasyMock.createMock(SessionContext.class);
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
     * Test the {@link JPADigitalRunTrackTypeDAO#updateTrackType(TrackType) } method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCRUDTrackType() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            manager.getTransaction().begin();
            dao.setSessionContext(context);
            EasyMock.reset(context);
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
            dao.removeTrackType(updated.getId());
            manager.getTransaction().commit();

            //verify
            assertEquals("The size of result is invalid.", 0, dao.getAllTrackTypes().size());
            EasyMock.verify(context);
        }

        printResult("CRUD on TrackType ", NUMBER);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackTypeDAO#getAllTrackTypes() } method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetAllTrackTypes() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            manager.getTransaction().begin();
            dao.setSessionContext(context);
            EasyMock.reset(context);
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

        printResult("CRUD on getAllTrackTypes ", NUMBER);
    }
}
