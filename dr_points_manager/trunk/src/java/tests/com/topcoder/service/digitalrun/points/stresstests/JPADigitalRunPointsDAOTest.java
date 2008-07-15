/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.stresstests;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.Ejb3Configuration;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.service.digitalrun.entity.DigitalRunPoints;
import com.topcoder.service.digitalrun.points.dao.implementations.AlwaysTrueValidator;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsDAO;
import com.topcoder.util.datavalidator.ObjectValidator;

/**
 * <p>
 * Stress test cases for class JPADigitalRunPointsDAO.
 * </p>
 * @author moon.river
 * @version 1.0
 */
public class JPADigitalRunPointsDAOTest extends TestCase {

    /**
     * A JPADigitalRunPointsDAO instance for testing.
     */
    private JPADigitalRunPointsDAO impl;

    /**
     * A EntityManager instance for testing.
     */
    private MockEntityManager em;

    /**
     * Get this test suite.
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(JPADigitalRunPointsDAOTest.class);
    }

    /**
     * Set up the test environment.
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        impl = new JPADigitalRunPointsDAO();

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
     * Stress test for the <code>createDigitalRunPoints(DigitalRunPoints points)</code> method.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPoints() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            DigitalRunPoints points = new DigitalRunPoints();
            impl.createDigitalRunPoints(points);
        }
        System.out.println("Creating points for 1000 times used " + (System.currentTimeMillis() - start) + "ms");
    }


    /**
     * <p>
     * Stress test for the <code>updateDigitalRunPoints(DigitalRunPoints points)</code> method.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPoints() throws Exception {
        DigitalRunPoints points = new DigitalRunPoints();
        impl.createDigitalRunPoints(points);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            impl.updateDigitalRunPoints(points);
        }
        System.out.println("Updating points for 1000 times used " + (System.currentTimeMillis() - start) + "ms");
    }


    /**
     * <p>
     * Stress test for the <code>searchDigitalRunPoints(Filter filter)</code> method.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    public void testSearchDigitalRunPoints_Accuracy() throws Exception {
        Map<String, ObjectValidator> fields = new HashMap<String, ObjectValidator>();
        fields.put("key_1", new AlwaysTrueValidator());
        Map<String, String> alias = new HashMap<String, String>();
        alias.put("key_1", "value_1");
        SearchBundle searchBundle = new SearchBundle("name", fields, alias, "context",
                new MockSearchStrategy());
        impl.setSearchBundle(searchBundle);

        EqualToFilter filter = new EqualToFilter("key_1", new Long(1));

        DigitalRunPoints points = new DigitalRunPoints();
        impl.createDigitalRunPoints(points);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            impl.searchDigitalRunPoints(filter);
        }
        System.out.println("Searching points for 1000 times used " + (System.currentTimeMillis() - start) + "ms");
    }

}
