/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.dao.implementations;

import com.topcoder.service.digitalrun.entity.PointsCalculator;
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
 * Unit test cases for the class <code>JPADigitalRunPointsCalculatorDAO.</code>
 * </p>
 * @author waits
 * @version 1.0
 */
public class JPADigitalRunPointsCalculatorDAOTests extends BaseTestCase {
    /** JPADigitalRunPointsCalculatorDAO instance to test against. */
    private static JPADigitalRunPointsCalculatorDAO dao = null;

    /**
     * <p>
     * Return All the EJB test suite.
     * </p>
     *
     * @return all the EJB test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(JPADigitalRunPointsCalculatorDAOTests.class);

        /**
         * <p>
         * Setup the unit test.
         * </p>
         */
        TestSetup wrapper = new TestSetup(suite) {
                /**
                 * Create instance.
                 */
                protected void setUp() {
                    dao = new JPADigitalRunPointsCalculatorDAO();

                    // get the EntityManager
                    Ejb3Configuration cfg = new Ejb3Configuration();
                    EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
                    manager = emf.createEntityManager();
                    context = EasyMock.createMock(SessionContext.class);
                    dao.setSessionContext(context);
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
     * Test the {@link JPADigitalRunPointsCalculatorDAO#createPointsCalculator(PointsCalculator) } method.  the param
     * is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreatePointsCalculator_nullParam()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.replay(context);

        //persist
        try {
            dao.createPointsCalculator(null);
            fail("The PointsCalculator is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunPointsCalculatorDAO#createPointsCalculator(PointsCalculator) } method.  the param
     * is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreatePointsCalculator_positiveIdParam()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.replay(context);

        //persist
        try {
            dao.createPointsCalculator(createPointsCalculator(1L));
            fail("The PointsCalculator has positive id.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunPointsCalculatorDAO#createPointsCalculator(PointsCalculator) } method. It is an
     * accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreatePointsCalculator() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        EasyMock.replay(context);
        //persist
        pointsCalculator = dao.createPointsCalculator(pointsCalculator);
        manager.getTransaction().commit();

        assertTrue("invalid persist.", pointsCalculator.getId() > 0);

        //retrieve
        PointsCalculator persisted = dao.getPointsCalculator(pointsCalculator.getId());
        assertEquals("The name is invalid.", "className", persisted.getClassName());
        assertEquals("The description is invalid.", "description", persisted.getDescription());
        manager.getTransaction().begin();
        //remove
        dao.remove(PointsCalculator.class, pointsCalculator.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllPointsCalculators().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunPointsCalculatorDAO#updatePointsCalculator(PointsCalculator) } method.  the param
     * is null, iae expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdatePointsCalculator_nullParam()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.replay(context);

        //persist
        try {
            dao.updatePointsCalculator(null);
            fail("The PointsCalculator is null.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunPointsCalculatorDAO#updatePointsCalculator(PointsCalculator) } method. It is an
     * accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdatePointsCalculator() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(6);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        EasyMock.replay(context);
        //persist
        pointsCalculator = dao.createPointsCalculator(pointsCalculator);

        assertTrue("invalid persist.", pointsCalculator.getId() > 0);

        //retrieve
        PointsCalculator persisted = dao.getPointsCalculator(pointsCalculator.getId());
        assertEquals("The name is invalid.", "className", persisted.getClassName());
        assertEquals("The description is invalid.", "description", persisted.getDescription());
        //update
        persisted.setDescription("new description");
        persisted.setCreationDate(new Date());
        persisted.setModificationDate(new Date());
        dao.updatePointsCalculator(persisted);
        manager.getTransaction().commit();

        //verify
        PointsCalculator updated = dao.getPointsCalculator(pointsCalculator.getId());
        assertEquals("The description is invalid.", "new description", updated.getDescription());

        manager.getTransaction().begin();
        //remove
        dao.remove(PointsCalculator.class, pointsCalculator.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllPointsCalculators().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunPointsCalculatorDAO#removePointsCalculator(long) } method. It is an accuracy test
     * case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemovePointsCalculator() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(3);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        EasyMock.replay(context);
        //persist
        pointsCalculator = dao.createPointsCalculator(pointsCalculator);

        //remove
        dao.removePointsCalculator(pointsCalculator.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllPointsCalculators().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunPointsCalculatorDAO#removePointsCalculator(long) } method.  The id is negative. iae
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemovePointsCalculator_negativeId()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);

        EasyMock.replay(context);

        try {
            //remove
            dao.removePointsCalculator(-1L);
            fail("The PointsCalculator id is negative.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunPointsCalculatorDAO#removePointsCalculator(long) } method.  The id is negative.
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemovePointsCalculator_notExistId()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.removePointsCalculator(100L);
            fail("The PointsCalculator id does not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunPointsCalculatorDAO#removePointsCalculator(long) } method.  Fail to get
     * entityManager, DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testRemovePointsCalculator_exception()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.removePointsCalculator(100L);
            fail("The PointsCalculator id does not exist.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunPointsCalculatorDAO#getPointsCalculator(long) } method. It is an accuracy test
     * case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetPointsCalculator() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        EasyMock.replay(context);
        //persist
        pointsCalculator = dao.createPointsCalculator(pointsCalculator);

        //retrieve
        PointsCalculator persisted = dao.getPointsCalculator(pointsCalculator.getId());
        assertEquals("The name is invalid.", "className", persisted.getClassName());
        assertEquals("The description is invalid.", "description", persisted.getDescription());

        //remove
        dao.removePointsCalculator(pointsCalculator.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllPointsCalculators().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunPointsCalculatorDAO#getAllPointsCalculators() } method. It is an accuracy test
     * case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetAllPointsCalculators() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);

        PointsCalculator pointsCalculator = createPointsCalculator(null);
        EasyMock.replay(context);
        //persist
        pointsCalculator = dao.createPointsCalculator(pointsCalculator);

        //retrieve
        assertEquals("The size of result is invalid.", 1, dao.getAllPointsCalculators().size());

        //remove
        dao.removePointsCalculator(pointsCalculator.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllPointsCalculators().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunPointsCalculatorDAO#getAllPointsCalculators(long) } method.  Fail to get
     * entityManager, DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testgetAllPointsCalculators_exception()
        throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.getAllPointsCalculators();
            fail("Fail to get entity manager.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunPointsCalculatorDAO#getPointsCalculator(long) } method.  The id is negative. iae
     * expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetPointsCalculator_negativeId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.replay(context);

        try {
            //remove
            dao.getPointsCalculator(-1L);
            fail("The PointsCalculator id is negative.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunPointsCalculatorDAO#getPointsCalculator(long) } method.  The id is negative.
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetPointsCalculator_notExistId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.getPointsCalculator(100L);
            fail("The PointsCalculator id does not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunPointsCalculatorDAO#getPointsCalculator(long) } method.  Fail to get entityManager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetPointsCalculator_exception() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        try {
            //remove
            dao.getPointsCalculator(100L);
            fail("The PointsCalculator id does not exist.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }
}
