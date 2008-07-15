/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.stresstests;

import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackStatusDAO;

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
 * Stress test cases for the class <code>JPADigitalRunTrackStatusDAO.</code>
 * </p>
 * @author telly
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
     * Test the {@link JPADigitalRunTrackStatusDAO#updateTrackStatus(TrackStatus) } method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCRUDTrackStatus() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            manager.getTransaction().begin();
            dao.setSessionContext(context);
            EasyMock.reset(context);
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
            dao.removeTrackStatus(updated.getId());
            manager.getTransaction().commit();

            //verify
            assertEquals("The size of result is invalid.", 0, dao.getAllTrackStatuses().size());
            EasyMock.verify(context);
        }

        printResult("CRUD on TrackStatus ", NUMBER);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunTrackStatusDAO#getAllTrackStatuss() } method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetAllTrackStatuss() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            manager.getTransaction().begin();
            dao.setSessionContext(context);
            EasyMock.reset(context);
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

        printResult("getAllTrackStatuss", NUMBER);
    }
}
