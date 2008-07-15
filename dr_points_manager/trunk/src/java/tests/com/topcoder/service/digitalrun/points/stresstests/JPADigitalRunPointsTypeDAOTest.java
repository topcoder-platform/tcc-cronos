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

import com.topcoder.service.digitalrun.entity.DigitalRunPointsType;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsTypeDAO;

/**
 * <p>
 * Stress test cases for class JPADigitalRunPointsTypeDAO. All the method are tested.
 * </p>
 * @author moon.river
 * @version 1.0
 */
public class JPADigitalRunPointsTypeDAOTest extends TestCase {
    /**
     * A JPADigitalRunPointsTypeDAO instance for testing.
     */
    private JPADigitalRunPointsTypeDAO impl;

    /**
     * A EntityManager instance for testing.
     */
    private MockEntityManager em;

    /**
     * Get this test suite.
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(JPADigitalRunPointsTypeDAOTest.class);
    }

    /**
     * Set up the test environment.
     */
    public void setUp() {
        impl = new JPADigitalRunPointsTypeDAO();

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
     * <code>createDigitalRunPointsType(DigitalRunPointsType pointsType)</code> method.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsType() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            DigitalRunPointsType pointsType = new DigitalRunPointsType();
            impl.createDigitalRunPointsType(pointsType);
        }
        System.out.println("Creating type for 1000 times used " + (System.currentTimeMillis() - start) + "ms");
    }


    /**
     * <p>
     * Stress test for the
     * <code>updateDigitalRunPointsType(DigitalRunPointsType pointsType)</code> method.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsType() throws Exception {
        DigitalRunPointsType pointsType = new DigitalRunPointsType();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            impl.updateDigitalRunPointsType(pointsType);
        }
        System.out.println("Updating type for 1000 times used " + (System.currentTimeMillis() - start) + "ms");
    }

}
