/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.dao.implementations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.ejb.Ejb3Configuration;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.service.digitalrun.entity.DigitalRunPoints;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsOperation;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsReferenceType;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsStatus;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsType;
import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;
import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerPersistenceException;
import com.topcoder.service.digitalrun.points.EntityNotFoundException;
import com.topcoder.service.digitalrun.points.TestHelper;
import com.topcoder.util.datavalidator.ObjectValidator;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit test cases for class JPADigitalRunPointsDAO. All the method are tested.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JPADigitalRunPointsDAOTest extends TestCase {

    /**
     * A EntityManager instance for testing.
     */
    private static MockEntityManager em;

    /**
     * A JPADigitalRunPointsDAO instance for testing.
     */
    private JPADigitalRunPointsDAO impl;

    /**
     * Get this test suite.
     *
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(JPADigitalRunPointsDAOTest.class);
    }

    /**
     * Set up the test environment.
     */
    @Override
    public void setUp() {
        impl = new JPADigitalRunPointsDAO();
        impl.setLogger(LogManager.getLog("temp"));

        if (em == null) {
            EntityManager manager = null;
            try {
                Ejb3Configuration cfg = new Ejb3Configuration();
                EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
                manager = emf.createEntityManager();
            } catch (Exception e) {
                // ignore
            }
            em = new MockEntityManager(manager);
        }
        em.SetExceptionFlag(false);
        em.clear();
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        MockSessionContext sc = new MockSessionContext();
        sc.setEm(em);
        impl.setSessionContext(sc);
        impl.setUnitName("unitName");
    }

    /**
     * Clear the database.
     */
    public void tearDown() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
            Query res = em.createNativeQuery("delete from dr_points_status_lu");
            res.executeUpdate();
            res = em.createNativeQuery("delete from dr_points_reference_type_lu");
            res.executeUpdate();
            res = em.createNativeQuery("delete from dr_points_operation_lu");
            res.executeUpdate();
            res = em.createNativeQuery("delete from dr_points_type_lu");
            res.executeUpdate();
            res = em.createNativeQuery("delete from dr_points");
            res.executeUpdate();
        }
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>JPADigitalRunPointsDAO()</code>, expects the
     * instance is created properly.
     * </p>
     */
    public void testCtor_Accuracy() {
        assertNotNull("Failed to create the JPADigitalRunPointsDAO instance.", impl);
    }

    /**
     * <p>
     * Accuracy test for the <code>createDigitalRunPoints(DigitalRunPoints points)</code> method,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPoints_Accuracy() throws Exception {
        DigitalRunPoints points = new DigitalRunPoints();
        points.setAmount(1.1);
        points.setDescription("description");
        points.setApplicationDate(new Date());
        points.setAwardDate(new Date());
        points.setCreationDate(new Date());
        points.setModificationDate(new Date());

        DigitalRunPointsStatus digitalRunPointsStatus = new DigitalRunPointsStatus();
        digitalRunPointsStatus.setDescription("description");
        points.setDigitalRunPointsStatus(digitalRunPointsStatus);
    }

    /**
     * <p>
     * Failure test for the <code>createDigitalRunPoints(DigitalRunPoints points)</code> method
     * with the points is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPoints_WithNull() throws Exception {
        try {
            impl.createDigitalRunPoints(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createDigitalRunPoints(DigitalRunPoints points)</code> method
     * with the points is exist.<br>
     * DigitalRunPointsManagerPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPoints_WithExistedPoints() throws Exception {
        em.SetExceptionFlag(true);
        DigitalRunPoints points = new DigitalRunPoints();
        try {
            impl.createDigitalRunPoints(points);
            fail("DigitalRunPointsManagerPersistenceException should be thrown.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createDigitalRunPoints(DigitalRunPoints points)</code> method
     * with the points's id is positive.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPoints_WithIdPositive() throws Exception {
        DigitalRunPoints points = new DigitalRunPoints();
        points.setId(2);
        try {
            impl.createDigitalRunPoints(points);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>updateDigitalRunPoints(DigitalRunPoints points)</code> method,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPoints_Accuracy() throws Exception {
        DigitalRunPoints points = new DigitalRunPoints();
        points.setAmount(1.1);
        points.setDescription("description");
        points.setApplicationDate(new Date());
        points.setAwardDate(new Date());
        points.setCreationDate(new Date());
        points.setModificationDate(new Date());

        DigitalRunPointsStatus digitalRunPointsStatus = new DigitalRunPointsStatus();
        digitalRunPointsStatus.setDescription("description");
        points.setDigitalRunPointsStatus(digitalRunPointsStatus);
    }

    /**
     * <p>
     * Failure test for the <code>updateDigitalRunPoints(DigitalRunPoints points)</code> method
     * with the points is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPoints_WithNull() throws Exception {
        try {
            impl.updateDigitalRunPoints(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateDigitalRunPoints(DigitalRunPoints points)</code> method
     * with the points is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPoints_WithNotExistPoints() throws Exception {
        JPADigitalRunPointsDAOTest.em.SetExceptionFlag(true);
        try {
            impl.updateDigitalRunPoints(new DigitalRunPoints());
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>removeDigitalRunPoints(long pointsId)</code> method, expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPoints_Accuracy() throws Exception {
        DigitalRunPoints points = new DigitalRunPoints();
        points.setAmount(1.1);
        points.setDescription("description");
        points.setApplicationDate(new Date());
        points.setAwardDate(new Date());
        points.setCreationDate(new Date());
        points.setModificationDate(new Date());

        DigitalRunPointsStatus digitalRunPointsStatus = new DigitalRunPointsStatus();
        digitalRunPointsStatus.setDescription("description");
        points.setDigitalRunPointsStatus(digitalRunPointsStatus);
    }

    /**
     * <p>
     * Failure test for the <code>removeDigitalRunPoints(long pointsId)</code> method with the
     * pointsId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPoints_WithNegativeId() throws Exception {
        try {
            impl.removeDigitalRunPoints(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>removeDigitalRunPoints(long pointsId)</code> method with the
     * pointsId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPoints_WithNotExistId() throws Exception {
        try {
            impl.removeDigitalRunPoints(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getDigitalRunPoints(long pointsId)</code> method, expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPoints_Accuracy() throws Exception {
        DigitalRunPoints points = new DigitalRunPoints();
        points.setAmount(1.1);
        points.setDescription("description");
        points.setApplicationDate(new Date());
        points.setAwardDate(new Date());
        points.setCreationDate(new Date());
        points.setModificationDate(new Date());

        DigitalRunPointsStatus digitalRunPointsStatus = new DigitalRunPointsStatus();
        digitalRunPointsStatus.setDescription("description");
        points.setDigitalRunPointsStatus(digitalRunPointsStatus);
    }

    /**
     * <p>
     * Failure test for the <code>getDigitalRunPoints(long pointsId)</code> method with the
     * pointsId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPoints_WithNegativeId() throws Exception {
        try {
            impl.getDigitalRunPoints(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getDigitalRunPoints(long pointsId)</code> method with the
     * pointsId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPoints_WithNotExistId() throws Exception {
        try {
            impl.getDigitalRunPoints(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>searchDigitalRunPoints(Filter filter)</code> method, expects no
     * error occurs.
     * </p>
     *
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
        impl.searchDigitalRunPoints(filter);
    }

    /**
     * <p>
     * Failure test for the <code>searchDigitalRunPoints(Filter filter)</code> method with the
     * filter is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSearchDigitalRunPoints_WithNull() throws Exception {
        try {
            impl.searchDigitalRunPoints(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>searchDigitalRunPoints(Filter filter)</code> method with the
     * SearchBuilderException occurs.<br>
     * DigitalRunPointsManagerPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testSearchDigitalRunPoints_WithSearchBuilderExceptionOccurs() throws Exception {
        Map<String, ObjectValidator> fields = new HashMap<String, ObjectValidator>();
        fields.put("key_1", new AlwaysTrueValidator());
        Map<String, String> alias = new HashMap<String, String>();
        alias.put("key_1", "value_1");
        SearchBundle searchBundle = new SearchBundle("name", fields, alias, "context",
                new MockSearchStrategy());

        impl.setSearchBundle(searchBundle);

        EqualToFilter filter = new EqualToFilter("Invalid", new Long(1));
        try {
            impl.searchDigitalRunPoints(filter);
            fail("DigitalRunPointsManagerPersistenceException should be thrown.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * Creates the TrackStatus for testing purpose.
     *
     * @return the entity created
     */
    protected TrackStatus createTrackStatus() {
        TrackStatus entity = new TrackStatus();
        entity.setDescription("description");
        return entity;
    }

    /**
     * Creates the TrackType for testing purpose.
     *
     * @return the entity created
     */
    protected TrackType createTrackType() {
        TrackType entity = new TrackType();
        entity.setDescription("description");
        return entity;
    }

    /**
     * Creates the PointsCalculator for testing purpose.
     *
     * @return the entity created
     */
    protected PointsCalculator createPointsCalculator() {
        PointsCalculator entity = new PointsCalculator();
        entity.setClassName("className");
        entity.setDescription("description");
        return entity;
    }

    /**
     * Creates the Track for testing purpose.
     *
     * @param pointsCalculator
     *            the associated points calculator
     * @param trackStatus
     *            the associated track status
     * @param trackType
     *            the associated track type
     * @return the entity created
     */
    protected Track createTrack(PointsCalculator pointsCalculator, TrackStatus trackStatus,
            TrackType trackType) {
        Track entity = new Track();
        entity.setPointsCalculator(pointsCalculator);
        entity.setTrackStatus(trackStatus);
        entity.setTrackType(trackType);
        entity.setDescription("description");
        entity.setStartDate(new Date());
        entity.setEndDate(new Date());
        entity.setCreationDate(new Date());
        entity.setModificationDate(new Date());
        return entity;
    }

}
