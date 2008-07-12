/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.stresstests;

import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunPointsCalculatorDAO;

import com.topcoder.util.log.LogManager;

import junit.extensions.TestSetup;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.easymock.EasyMock;

import org.hibernate.ejb.Ejb3Configuration;

import javax.ejb.SessionContext;

import javax.persistence.EntityManagerFactory;


/**
 * <p>
 * Stress test cases for the class <code>JPADigitalRunPointsCalculatorDAO.</code>
 * </p>
 * @author telly
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
                    dao.setUnitName("unitName");
                    dao.setLogger(LogManager.getLog());
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
     * Test the {@link JPADigitalRunPointsCalculatorDAO#createPointsCalculator(PointsCalculator) } method. It is an
     * accuracy test case.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCRUDPointsCalculator() throws Exception {
        start();
    	
        for(int i = 0 ; i < NUMBER; i++) {
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
	        dao.removePointsCalculator(persisted.getId());
	        manager.getTransaction().commit();
	
	        //verify
	        assertEquals("The size of result is invalid.", 0, dao.getAllPointsCalculators().size());
	        EasyMock.verify(context);
        }
        
        printResult("CRUD on PointsCalculator ", NUMBER);
    }
}
