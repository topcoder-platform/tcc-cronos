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

import com.topcoder.service.digitalrun.entity.DigitalRunPointsOperation;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsOperationDAO;

/**
 * <p>
 * Unit test cases for class JPADigitalRunPointsOperationDAO. All the method are tested.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JPADigitalRunPointsOperationDAOTest extends TestCase {

    /**
     * A JPADigitalRunPointsOperationDAO instance for testing.
     */
    private JPADigitalRunPointsOperationDAO impl;

    /**
     * A EntityManager instance for testing.
     */
    private MockEntityManager em;

    /**
     * Get this test suite.
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(JPADigitalRunPointsOperationDAOTest.class);
    }

    /**
     * Set up the test environment.
     */
    public void setUp() {
        impl = new JPADigitalRunPointsOperationDAO();

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
     * <code>createDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)</code>
     * method.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateDigitalRunPointsOperation() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();
            impl.createDigitalRunPointsOperation(pointsOperation);
        }
        System.out.println("Creating operations for 1000 times used " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * <p>
     * Stress test for the
     * <code>updateDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)</code>
     * method.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsOperation() throws Exception {
        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            impl.updateDigitalRunPointsOperation(pointsOperation);
        }
        System.out.println("Updating operation for 1000 times used " + (System.currentTimeMillis() - start) + "ms");
    }

}
