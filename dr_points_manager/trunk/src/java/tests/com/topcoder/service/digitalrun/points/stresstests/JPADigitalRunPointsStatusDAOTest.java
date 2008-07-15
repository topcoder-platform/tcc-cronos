/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.stresstests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.Ejb3Configuration;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.digitalrun.entity.DigitalRunPointsStatus;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsStatusDAO;

/**
 * <p>
 * Stress test cases for class JPADigitalRunPointsStatusDAO.
 * </p>
 * @author moon.river
 * @version 1.0
 */
public class JPADigitalRunPointsStatusDAOTest extends TestCase {
    /**
     * A JPADigitalRunPointsStatusDAO instance for testing.
     */
    private JPADigitalRunPointsStatusDAO impl;

    /**
     * A EntityManager instance for testing.
     */
    private MockEntityManager em;

    /**
     * Get this test suite.
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(JPADigitalRunPointsStatusDAOTest.class);
    }

    /**
     * Set up the test environment.
     */
    public void setUp() {
        impl = new JPADigitalRunPointsStatusDAO();

        Ejb3Configuration cfg = new Ejb3Configuration();
        EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
        EntityManager manager = emf.createEntityManager();

        em = new MockEntityManager(manager);

        em.SetExceptionFlag(false);

        MockSessionContext sc = new MockSessionContext();
        sc.setEm(em);
        impl.setSessionContext(sc);
        impl.setUnitName("unitName");
    }

    /**
     * <p>
     * Stress test for the
     * <code>createDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsStatus() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();
            impl.createDigitalRunPointsStatus(pointsStatus);
        }
        System.out.println("Creating status for 1000 times used " + (System.currentTimeMillis() - start) + "ms");
    }


    /**
     * <p>
     * Stress test for the
     * <code>updateDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsStatus() throws Exception {
        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            impl.updateDigitalRunPointsStatus(pointsStatus);
        }
        System.out.println("Updating status for 1000 times used " + (System.currentTimeMillis() - start) + "ms");
    }


}
