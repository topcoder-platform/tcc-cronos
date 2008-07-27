/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.manager.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.topcoder.service.digitalrun.points.ConfigurationProvider;
import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerPersistenceException;
import com.topcoder.service.digitalrun.points.EntityNotFoundException;
import com.topcoder.service.digitalrun.points.TestHelper;
import com.topcoder.service.digitalrun.points.dao.implementations.AlwaysTrueValidator;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsDAO;
import com.topcoder.service.digitalrun.points.dao.implementations.MockEntityManager;
import com.topcoder.service.digitalrun.points.dao.implementations.MockSearchStrategy;
import com.topcoder.service.digitalrun.points.dao.implementations.MockSessionContext;
import com.topcoder.util.datavalidator.ObjectValidator;

/**
 * <p>
 * Unit test cases for class DigitalRunPointsManagerBean. All the method are tested.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunPointsManagerBeanTest extends TestCase {

    /**
     * A EntityManager instance for testing.
     */
    private static MockEntityManager em;

    /**
     * A DigitalRunPointsManagerBean instance for testing.
     */
    private DigitalRunPointsManagerBean impl;

    /**
     * Get this test suite.
     *
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(DigitalRunPointsManagerBeanTest.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void setUp() throws Exception {
        impl = new DigitalRunPointsManagerBean();
        ConfigurationProvider.retrieveConfigurationFromFile();
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "logName", "logger_temp");
        MockSessionContext sc = new MockSessionContext();
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
        sc.setEm(em);
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "sessionContext", sc);
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "unitName", "unit_name");

        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "pointsDAOKey", "PointsDAOImpl");
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "pointsTypeDAOKey",
                "PointsTypeDAOImpl");
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "pointsOperationDAOKey",
                "PointsOperationDAOImpl");
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "pointsReferenceTypeDAOKey",
                "PointsReferenceTypeDAOImpl");
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "pointsStatusDAOKey",
                "PointsStatusDAOImpl");

        Method method = DigitalRunPointsManagerBean.class.getDeclaredMethod("initialize");
        method.setAccessible(true);
        method.invoke(impl);
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
     * Accuracy test for the constructor <code>DigitalRunPointsManagerBean()</code>, expects the
     * instance is created properly.
     * </p>
     */
    public void testCtor_Accuracy() {
        assertNotNull("Failed to create the DigitalRunPointsManagerBean instance.", impl);
    }

    /**
     * <p>
     * Failure test for the <code>initialize()</code> method with the pointsDAOKey is empty.<br>
     * DigitalRunPointsManagerBeanConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInitialize_WithPointsDAOKeyEmpty() throws Exception {
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "pointsDAOKey", "  ");

        Method method = DigitalRunPointsManagerBean.class.getDeclaredMethod("initialize");
        method.setAccessible(true);
        try {
            method.invoke(impl);
            fail("DigitalRunPointsManagerBeanConfigurationException should be thrown.");
        } catch (InvocationTargetException e) {
            // The InvocationTargetException should be thrown
            // Because the I call the method by reflect
        }
    }

    /**
     * <p>
     * Failure test for the <code>initialize()</code> method with the sessionContext is null.<br>
     * DigitalRunPointsManagerBeanConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInitialize_WithSessionContextNull() throws Exception {
        TestHelper.setPrivateField(DigitalRunPointsManagerBean.class, impl, "sessionContext", null);

        Method method = DigitalRunPointsManagerBean.class.getDeclaredMethod("initialize");
        method.setAccessible(true);
        try {
            method.invoke(impl);
            fail("DigitalRunPointsManagerBeanConfigurationException should be thrown.");
        } catch (InvocationTargetException e) {
            // The InvocationTargetException should be thrown
            // Because the I call the method by reflect
        }
    }

    /**
     * <p>
     * Failure test for the <code>initialize()</code> method with the invalid config.<br>
     * DigitalRunPointsManagerBeanConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInitialize_WithInvalidConfig1() throws Exception {
        ConfigurationProvider.retrieveConfigurationFromFile(ConfigurationProvider.DEFAULT_FILE,
                "InvalidConfig1");

        Method method = DigitalRunPointsManagerBean.class.getDeclaredMethod("initialize");
        method.setAccessible(true);
        try {
            method.invoke(impl);
            fail("DigitalRunPointsManagerBeanConfigurationException should be thrown.");
        } catch (InvocationTargetException e) {
            // The InvocationTargetException should be thrown
            // Because the I call the method by reflect
        }
    }

    /**
     * <p>
     * Failure test for the <code>initialize()</code> method with the invalid config.<br>
     * DigitalRunPointsManagerBeanConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInitialize_WithInvalidConfig2() throws Exception {
        ConfigurationProvider.retrieveConfigurationFromFile(ConfigurationProvider.DEFAULT_FILE,
                "InvalidConfig2");

        Method method = DigitalRunPointsManagerBean.class.getDeclaredMethod("initialize");
        method.setAccessible(true);
        try {
            method.invoke(impl);
            fail("DigitalRunPointsManagerBeanConfigurationException should be thrown.");
        } catch (InvocationTargetException e) {
            // The InvocationTargetException should be thrown
            // Because the I call the method by reflect
        }
    }

    /**
     * <p>
     * Failure test for the <code>initialize()</code> method with the invalid config.<br>
     * DigitalRunPointsManagerBeanConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testInitialize_WithInvalidConfig3() throws Exception {
        ConfigurationProvider.retrieveConfigurationFromFile(ConfigurationProvider.DEFAULT_FILE,
                "InvalidConfig3");

        Method method = DigitalRunPointsManagerBean.class.getDeclaredMethod("initialize");
        method.setAccessible(true);
        try {
            method.invoke(impl);
            fail("DigitalRunPointsManagerBeanConfigurationException should be thrown.");
        } catch (InvocationTargetException e) {
            // The InvocationTargetException should be thrown
            // Because the I call the method by reflect
        }
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
        TestHelper.persist(em, digitalRunPointsStatus);
        points.setDigitalRunPointsStatus(digitalRunPointsStatus);

        DigitalRunPointsType digitalRunPointsType = new DigitalRunPointsType();
        digitalRunPointsType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsType);
        points.setDigitalRunPointsType(digitalRunPointsType);

        DigitalRunPointsReferenceType digitalRunPointsReferenceType = new DigitalRunPointsReferenceType();
        digitalRunPointsReferenceType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsReferenceType);
        points.setDigitalRunPointsReferenceType(digitalRunPointsReferenceType);

        DigitalRunPointsOperation digitalRunPointsOperation = new DigitalRunPointsOperation();
        digitalRunPointsOperation.setDescription("description");
        TestHelper.persist(em, digitalRunPointsOperation);
        points.setDigitalRunPointsOperation(digitalRunPointsOperation);

        PointsCalculator pointsCalculator = createPointsCalculator();
        TestHelper.persist(em, pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        TestHelper.persist(em, trackStatus);

        TrackType trackType = createTrackType();
        TestHelper.persist(em, trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType);
        TestHelper.persist(em, track);

        System.out.println(track.getId());

        points.setTrack(track);
        points.setUserId(123);
        points.setReferenceId(234);

        impl.createDigitalRunPoints(points);

        System.out.println("The entity id is: " + points.getId());

        impl.removeDigitalRunPoints(points.getId());

        TestHelper.delete(em, digitalRunPointsStatus);
        TestHelper.delete(em, digitalRunPointsType);
        TestHelper.delete(em, digitalRunPointsReferenceType);
        TestHelper.delete(em, digitalRunPointsOperation);
        TestHelper.delete(em, track);
        TestHelper.delete(em, pointsCalculator);
        TestHelper.delete(em, trackStatus);
        TestHelper.delete(em, trackType);
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
        TestHelper.persist(em, digitalRunPointsStatus);
        points.setDigitalRunPointsStatus(digitalRunPointsStatus);

        DigitalRunPointsType digitalRunPointsType = new DigitalRunPointsType();
        digitalRunPointsType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsType);
        points.setDigitalRunPointsType(digitalRunPointsType);

        DigitalRunPointsReferenceType digitalRunPointsReferenceType = new DigitalRunPointsReferenceType();
        digitalRunPointsReferenceType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsReferenceType);
        points.setDigitalRunPointsReferenceType(digitalRunPointsReferenceType);

        DigitalRunPointsOperation digitalRunPointsOperation = new DigitalRunPointsOperation();
        digitalRunPointsOperation.setDescription("description");
        TestHelper.persist(em, digitalRunPointsOperation);
        points.setDigitalRunPointsOperation(digitalRunPointsOperation);

        PointsCalculator pointsCalculator = createPointsCalculator();
        TestHelper.persist(em, pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        TestHelper.persist(em, trackStatus);

        TrackType trackType = createTrackType();
        TestHelper.persist(em, trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType);
        TestHelper.persist(em, track);

        System.out.println(track.getId());

        points.setTrack(track);
        points.setUserId(123);
        points.setReferenceId(234);

        impl.createDigitalRunPoints(points);

        points.setAmount(100.0);
        points.setDescription("new description");
        impl.updateDigitalRunPoints(points);

        DigitalRunPoints result = impl.getDigitalRunPoints(points.getId());
        assertEquals("The amount should be same as ", 100.0, result.getAmount());
        assertEquals("The description should be same as ", "new description", result.getDescription());

        impl.removeDigitalRunPoints(result.getId());

        TestHelper.delete(em, digitalRunPointsStatus);
        TestHelper.delete(em, digitalRunPointsType);
        TestHelper.delete(em, digitalRunPointsReferenceType);
        TestHelper.delete(em, digitalRunPointsOperation);
        TestHelper.delete(em, track);
        TestHelper.delete(em, pointsCalculator);
        TestHelper.delete(em, trackStatus);
        TestHelper.delete(em, trackType);
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
        this.em.SetExceptionFlag(true);
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
        TestHelper.persist(em, digitalRunPointsStatus);
        points.setDigitalRunPointsStatus(digitalRunPointsStatus);

        DigitalRunPointsType digitalRunPointsType = new DigitalRunPointsType();
        digitalRunPointsType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsType);
        points.setDigitalRunPointsType(digitalRunPointsType);

        DigitalRunPointsReferenceType digitalRunPointsReferenceType = new DigitalRunPointsReferenceType();
        digitalRunPointsReferenceType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsReferenceType);
        points.setDigitalRunPointsReferenceType(digitalRunPointsReferenceType);

        DigitalRunPointsOperation digitalRunPointsOperation = new DigitalRunPointsOperation();
        digitalRunPointsOperation.setDescription("description");
        TestHelper.persist(em, digitalRunPointsOperation);
        points.setDigitalRunPointsOperation(digitalRunPointsOperation);

        PointsCalculator pointsCalculator = createPointsCalculator();
        TestHelper.persist(em, pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        TestHelper.persist(em, trackStatus);

        TrackType trackType = createTrackType();
        TestHelper.persist(em, trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType);
        TestHelper.persist(em, track);

        System.out.println(track.getId());

        points.setTrack(track);
        points.setUserId(123);
        points.setReferenceId(234);

        impl.createDigitalRunPoints(points);

        assertNotNull("The result should not be null.", impl.getDigitalRunPoints(points.getId()));

        impl.removeDigitalRunPoints(points.getId());

        try {
            impl.getDigitalRunPoints(points.getId());
            fail("The result should be not exist");
        } catch (EntityNotFoundException e) {
            // expected
        }

        TestHelper.delete(em, digitalRunPointsStatus);
        TestHelper.delete(em, digitalRunPointsType);
        TestHelper.delete(em, digitalRunPointsReferenceType);
        TestHelper.delete(em, digitalRunPointsOperation);
        TestHelper.delete(em, track);
        TestHelper.delete(em, pointsCalculator);
        TestHelper.delete(em, trackStatus);
        TestHelper.delete(em, trackType);
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
        TestHelper.persist(em, digitalRunPointsStatus);
        points.setDigitalRunPointsStatus(digitalRunPointsStatus);

        DigitalRunPointsType digitalRunPointsType = new DigitalRunPointsType();
        digitalRunPointsType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsType);
        points.setDigitalRunPointsType(digitalRunPointsType);

        DigitalRunPointsReferenceType digitalRunPointsReferenceType = new DigitalRunPointsReferenceType();
        digitalRunPointsReferenceType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsReferenceType);
        points.setDigitalRunPointsReferenceType(digitalRunPointsReferenceType);

        DigitalRunPointsOperation digitalRunPointsOperation = new DigitalRunPointsOperation();
        digitalRunPointsOperation.setDescription("description");
        TestHelper.persist(em, digitalRunPointsOperation);
        points.setDigitalRunPointsOperation(digitalRunPointsOperation);

        PointsCalculator pointsCalculator = createPointsCalculator();
        TestHelper.persist(em, pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        TestHelper.persist(em, trackStatus);

        TrackType trackType = createTrackType();
        TestHelper.persist(em, trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType);
        TestHelper.persist(em, track);

        System.out.println(track.getId());

        points.setTrack(track);
        points.setUserId(123);
        points.setReferenceId(234);

        impl.createDigitalRunPoints(points);

        DigitalRunPoints result = impl.getDigitalRunPoints(points.getId());

        assertEquals("The amount should be same as ", 1.1, result.getAmount());
        assertEquals("The description should be same as ", "description", result.getDescription());

        impl.removeDigitalRunPoints(points.getId());

        TestHelper.delete(em, digitalRunPointsStatus);
        TestHelper.delete(em, digitalRunPointsType);
        TestHelper.delete(em, digitalRunPointsReferenceType);
        TestHelper.delete(em, digitalRunPointsOperation);
        TestHelper.delete(em, track);
        TestHelper.delete(em, pointsCalculator);
        TestHelper.delete(em, trackStatus);
        TestHelper.delete(em, trackType);
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

        JPADigitalRunPointsDAO pointsDAO = (JPADigitalRunPointsDAO) TestHelper.getPrivateField(
                DigitalRunPointsManagerBean.class, impl, "pointsDAO");
        pointsDAO.setSearchBundle(searchBundle);

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

        JPADigitalRunPointsDAO pointsDAO = (JPADigitalRunPointsDAO) TestHelper.getPrivateField(
                DigitalRunPointsManagerBean.class, impl, "pointsDAO");
        pointsDAO.setSearchBundle(searchBundle);

        EqualToFilter filter = new EqualToFilter("Invalid", new Long(1));
        try {
            impl.searchDigitalRunPoints(filter);
            fail("DigitalRunPointsManagerPersistenceException should be thrown.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)</code>
     * method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsOperation_Accuracy() throws Exception {
        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();

        pointsOperation.setDescription("description");

        assertEquals("The pointsOperation id should be zero.", 0, pointsOperation.getId());
        impl.createDigitalRunPointsOperation(pointsOperation);
        System.out.println("The entity id is: " + pointsOperation.getId());
        impl.removeDigitalRunPointsOperation(pointsOperation.getId());
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)</code>
     * method with the pointsOperation is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsOperation_WithNull() throws Exception {
        try {
            impl.createDigitalRunPointsOperation(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)</code>
     * method with the pointsOperation is exist.<br>
     * DigitalRunPointsManagerPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsOperation_WithExistedOperation() throws Exception {
        em.SetExceptionFlag(true);
        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();
        try {
            impl.createDigitalRunPointsOperation(pointsOperation);
            fail("DigitalRunPointsManagerPersistenceException should be thrown.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)</code>
     * method with the pointsOperation's id is positive.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsOperation_WithIdPositive() throws Exception {
        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();
        pointsOperation.setId(2);
        try {
            impl.createDigitalRunPointsOperation(pointsOperation);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>updateDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)</code>
     * method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsOperation_Accuracy() throws Exception {
        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();
        pointsOperation.setCreationDate(new Date());
        pointsOperation.setModificationDate(new Date());
        pointsOperation.setDescription("description");
        impl.createDigitalRunPointsOperation(pointsOperation);

        pointsOperation.setDescription("new description");
        impl.updateDigitalRunPointsOperation(pointsOperation);

        DigitalRunPointsOperation result = impl.getDigitalRunPointsOperation(pointsOperation.getId());
        assertEquals("The description should be same as ", "new description", result.getDescription());

        impl.removeDigitalRunPointsOperation(result.getId());
    }

    /**
     * <p>
     * Failure test for the
     * <code>updateDigitalRunPointsOperation(DigitalRunpointsOperation pointsOperation)</code>
     * method with the pointsOperation is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsOperation_WithNull() throws Exception {
        try {
            impl.updateDigitalRunPointsOperation(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>updateDigitalRunPointsOperation(DigitalRunpointsOperation pointsOperation)</code>
     * method with the pointsOperation is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsOperation_WithNotExistpointsOperation() throws Exception {
        this.em.SetExceptionFlag(true);
        try {
            impl.updateDigitalRunPointsOperation(new DigitalRunPointsOperation());
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>removeDigitalRunPointsOperation(long pointsOperationId)</code>
     * method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsOperation_Accuracy() throws Exception {
        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();
        pointsOperation.setDescription("description");

        impl.createDigitalRunPointsOperation(pointsOperation);

        assertNotNull("The result should not be null.", impl.getDigitalRunPointsOperation(pointsOperation
                .getId()));

        impl.removeDigitalRunPointsOperation(pointsOperation.getId());

        try {
            impl.getDigitalRunPointsOperation(pointsOperation.getId());
            fail("The result should be not exist");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>removeDigitalRunPointsOperation(long pointsOperationId)</code>
     * method with the pointsOperationId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsOperation_WithNegativeId() throws Exception {
        try {
            impl.removeDigitalRunPointsOperation(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>removeDigitalRunPointsOperation(long pointsOperationId)</code>
     * method with the pointsOperationId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsOperation_WithNotExistId() throws Exception {
        try {
            impl.removeDigitalRunPointsOperation(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getDigitalRunPointsOperation(long pointsOperationId)</code>
     * method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsOperation_Accuracy() throws Exception {
        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();
        pointsOperation.setDescription("description");

        impl.createDigitalRunPointsOperation(pointsOperation);

        DigitalRunPointsOperation result = impl.getDigitalRunPointsOperation(pointsOperation.getId());

        assertEquals("The description should be same as ", "description", result.getDescription());

        impl.removeDigitalRunPointsOperation(pointsOperation.getId());
    }

    /**
     * <p>
     * Failure test for the <code>getDigitalRunPointsOperation(long pointsOperationId)</code>
     * method with the pointsOperationId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsOperation_WithNegativeId() throws Exception {
        try {
            impl.getDigitalRunPointsOperation(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getDigitalRunPointsOperation(long pointsOperationId)</code>
     * method with the pointsOperationId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsOperation_WithNotExistId() throws Exception {
        try {
            impl.getDigitalRunPointsOperation(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getAllDigitalRunPointsOperations()</code> method, expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetAllDigitalRunPointsOperations_Accuracy() throws Exception {
        List<DigitalRunPointsOperation> result = impl.getAllDigitalRunPointsOperations();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 0, result.size());

        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();
        pointsOperation.setDescription("description");
        impl.createDigitalRunPointsOperation(pointsOperation);

        result = impl.getAllDigitalRunPointsOperations();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 1, result.size());

    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)</code>
     * method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsReferenceType_Accuracy() throws Exception {
        DigitalRunPointsReferenceType pointsReferenceType = new DigitalRunPointsReferenceType();

        pointsReferenceType.setDescription("description");

        assertEquals("The pointsReferenceType id should be zero.", 0, pointsReferenceType.getId());
        impl.createDigitalRunPointsReferenceType(pointsReferenceType);
        System.out.println("The entity id is: " + pointsReferenceType.getId());
        impl.removeDigitalRunPointsReferenceType(pointsReferenceType.getId());
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)</code>
     * method with the pointsReferenceType is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsReferenceType_WithNull() throws Exception {
        try {
            impl.createDigitalRunPointsReferenceType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)</code>
     * method with the pointsReferenceType is exist.<br>
     * DigitalRunPointsManagerPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsReferenceType_WithExistedType() throws Exception {
        em.SetExceptionFlag(true);
        DigitalRunPointsReferenceType pointsReferenceType = new DigitalRunPointsReferenceType();
        try {
            impl.createDigitalRunPointsReferenceType(pointsReferenceType);
            fail("DigitalRunPointsManagerPersistenceException should be thrown.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)</code>
     * method with the pointsReferenceType's id is positive.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsReferenceType_WithIdPositive() throws Exception {
        DigitalRunPointsReferenceType pointsReferenceType = new DigitalRunPointsReferenceType();
        pointsReferenceType.setId(2);
        try {
            impl.createDigitalRunPointsReferenceType(pointsReferenceType);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>updateDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)</code>
     * method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsReferenceType_Accuracy() throws Exception {
        DigitalRunPointsReferenceType pointsReferenceType = new DigitalRunPointsReferenceType();
        pointsReferenceType.setCreationDate(new Date());
        pointsReferenceType.setModificationDate(new Date());
        pointsReferenceType.setDescription("description");
        impl.createDigitalRunPointsReferenceType(pointsReferenceType);

        pointsReferenceType.setDescription("new description");
        impl.updateDigitalRunPointsReferenceType(pointsReferenceType);

        DigitalRunPointsReferenceType result = impl.getDigitalRunPointsReferenceType(pointsReferenceType
                .getId());
        assertEquals("The description should be same as ", "new description", result.getDescription());

        impl.removeDigitalRunPointsReferenceType(result.getId());
    }

    /**
     * <p>
     * Failure test for the
     * <code>updateDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)</code>
     * method with the pointsReferenceType is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsReferenceType_WithNull() throws Exception {
        try {
            impl.updateDigitalRunPointsReferenceType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>updateDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)</code>
     * method with the pointsReferenceType is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsReferenceType_WithNotExistpointsOperation() throws Exception {
        this.em.SetExceptionFlag(true);
        try {
            impl.updateDigitalRunPointsReferenceType(new DigitalRunPointsReferenceType());
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>removeDigitalRunPointsReferenceType(long pointsReferenceTypeId)</code> method,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsReferenceType_Accuracy() throws Exception {
        DigitalRunPointsReferenceType pointsReferenceType = new DigitalRunPointsReferenceType();
        pointsReferenceType.setDescription("description");

        impl.createDigitalRunPointsReferenceType(pointsReferenceType);

        assertNotNull("The result should not be null.", impl
                .getDigitalRunPointsReferenceType(pointsReferenceType.getId()));

        impl.removeDigitalRunPointsReferenceType(pointsReferenceType.getId());

        try {
            impl.getDigitalRunPointsReferenceType(pointsReferenceType.getId());
            fail("The result should be not exist");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>removeDigitalRunPointsReferenceType(long pointsReferenceTypeId)</code> method with
     * the pointsReferenceTypeId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsReferenceType_WithNegativeId() throws Exception {
        try {
            impl.removeDigitalRunPointsReferenceType(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>removeDigitalRunPointsReferenceType(long pointsReferenceTypeId)</code> method with
     * the pointsReferenceTypeId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsReferenceType_WithNotExistId() throws Exception {
        try {
            impl.removeDigitalRunPointsReferenceType(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>getDigitalRunPointsReferenceType(long pointsReferenceTypeId)</code> method, expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsReferenceType_Accuracy() throws Exception {
        DigitalRunPointsReferenceType pointsReferenceType = new DigitalRunPointsReferenceType();
        pointsReferenceType.setDescription("description");

        impl.createDigitalRunPointsReferenceType(pointsReferenceType);

        DigitalRunPointsReferenceType result = impl.getDigitalRunPointsReferenceType(pointsReferenceType
                .getId());

        assertEquals("The description should be same as ", "description", result.getDescription());

        impl.removeDigitalRunPointsReferenceType(pointsReferenceType.getId());
    }

    /**
     * <p>
     * Failure test for the
     * <code>getDigitalRunPointsReferenceType(long pointsReferenceTypeId)</code> method with the
     * pointsReferenceTypeId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsReferenceType_WithNegativeId() throws Exception {
        try {
            impl.getDigitalRunPointsReferenceType(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>getDigitalRunPointsReferenceType(long pointsReferenceTypeId)</code> method with the
     * pointsReferenceTypeId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsReferenceType_WithNotExistId() throws Exception {
        try {
            impl.getDigitalRunPointsReferenceType(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getAllDigitalRunPointsReferenceTypes()</code> method, expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetAllDigitalRunPointsReferenceTypes_Accuracy() throws Exception {
        List<DigitalRunPointsReferenceType> result = impl.getAllDigitalRunPointsReferenceTypes();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 0, result.size());

        DigitalRunPointsReferenceType pointsReferenceType = new DigitalRunPointsReferenceType();
        pointsReferenceType.setDescription("description");
        impl.createDigitalRunPointsReferenceType(pointsReferenceType);

        result = impl.getAllDigitalRunPointsReferenceTypes();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 1, result.size());
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsStatus_Accuracy() throws Exception {
        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();

        pointsStatus.setDescription("description");

        assertEquals("The pointsStatus id should be zero.", 0, pointsStatus.getId());
        impl.createDigitalRunPointsStatus(pointsStatus);
        System.out.println("The entity id is: " + pointsStatus.getId());
        impl.removeDigitalRunPointsStatus(pointsStatus.getId());
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method with
     * the pointsStatus is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsStatus_WithNull() throws Exception {
        try {
            impl.createDigitalRunPointsStatus(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method with
     * the pointsStatus is exist.<br>
     * DigitalRunPointsManagerPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsStatus_WithExistedStatus() throws Exception {
        em.SetExceptionFlag(true);
        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();
        try {
            impl.createDigitalRunPointsStatus(pointsStatus);
            fail("DigitalRunPointsManagerPersistenceException should be thrown.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method with
     * the pointsStatus's id is positive.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsStatus_WithIdPositive() throws Exception {
        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();
        pointsStatus.setId(2);
        try {
            impl.createDigitalRunPointsStatus(pointsStatus);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>updateDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsStatus_Accuracy() throws Exception {
        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();
        pointsStatus.setCreationDate(new Date());
        pointsStatus.setModificationDate(new Date());
        pointsStatus.setDescription("description");
        impl.createDigitalRunPointsStatus(pointsStatus);

        pointsStatus.setDescription("new description");
        impl.updateDigitalRunPointsStatus(pointsStatus);

        DigitalRunPointsStatus result = impl.getDigitalRunPointsStatus(pointsStatus.getId());
        assertEquals("The description should be same as ", "new description", result.getDescription());

        impl.removeDigitalRunPointsStatus(result.getId());
    }

    /**
     * <p>
     * Failure test for the
     * <code>updateDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method with
     * the pointsStatus is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsStatus_WithNull() throws Exception {
        try {
            impl.updateDigitalRunPointsStatus(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>updateDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method with
     * the pointsStatus is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsStatus_WithNotExistpointsOperation() throws Exception {
        this.em.SetExceptionFlag(true);
        try {
            impl.updateDigitalRunPointsStatus(new DigitalRunPointsStatus());
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>removeDigitalRunPointsStatus(long pointsStatusId)</code>
     * method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsStatus_Accuracy() throws Exception {
        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();
        pointsStatus.setDescription("description");

        impl.createDigitalRunPointsStatus(pointsStatus);

        assertNotNull("The result should not be null.", impl.getDigitalRunPointsStatus(pointsStatus.getId()));

        impl.removeDigitalRunPointsStatus(pointsStatus.getId());

        try {
            impl.getDigitalRunPointsStatus(pointsStatus.getId());
            fail("The result should be not exist");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>removeDigitalRunPointsStatus(long pointsStatusId)</code> method
     * with the pointsStatusId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsStatus_WithNegativeId() throws Exception {
        try {
            impl.removeDigitalRunPointsStatus(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>removeDigitalRunPointsStatus(long pointsStatusId)</code> method
     * with the pointsStatusId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsStatus_WithNotExistId() throws Exception {
        try {
            impl.removeDigitalRunPointsStatus(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getDigitalRunPointsStatus(long pointsStatusId)</code> method,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsStatus_Accuracy() throws Exception {
        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();
        pointsStatus.setDescription("description");

        impl.createDigitalRunPointsStatus(pointsStatus);

        DigitalRunPointsStatus result = impl.getDigitalRunPointsStatus(pointsStatus.getId());

        assertEquals("The description should be same as ", "description", result.getDescription());

        impl.removeDigitalRunPointsStatus(pointsStatus.getId());
    }

    /**
     * <p>
     * Failure test for the <code>getDigitalRunPointsStatus(long pointsStatusId)</code> method
     * with the pointsStatusId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsStatus_WithNegativeId() throws Exception {
        try {
            impl.getDigitalRunPointsStatus(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getDigitalRunPointsStatus(long pointsStatusId)</code> method
     * with the pointsStatusId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsStatus_WithNotExistId() throws Exception {
        try {
            impl.getDigitalRunPointsStatus(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getAllDigitalRunPointsStatuses()</code> method, expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetAllDigitalRunPointsStatuses_Accuracy() throws Exception {
        List<DigitalRunPointsStatus> result = impl.getAllDigitalRunPointsStatuses();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 0, result.size());

        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();
        pointsStatus.setDescription("description");
        impl.createDigitalRunPointsStatus(pointsStatus);

        result = impl.getAllDigitalRunPointsStatuses();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 1, result.size());
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createDigitalRunPointsType(DigitalRunPointsType pointsType)</code> method, expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsType_Accuracy() throws Exception {
        DigitalRunPointsType pointsType = new DigitalRunPointsType();

        pointsType.setDescription("description");

        assertEquals("The pointsType id should be zero.", 0, pointsType.getId());
        impl.createDigitalRunPointsType(pointsType);
        System.out.println("The entity id is: " + pointsType.getId());
        impl.removeDigitalRunPointsType(pointsType.getId());
    }

    /**
     * <p>
     * Failure test for the <code>createDigitalRunPointsType(DigitalRunPointsType pointsType)</code>
     * method with the pointsType is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsType_WithNull() throws Exception {
        try {
            impl.createDigitalRunPointsType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createDigitalRunPointsType(DigitalRunPointsType pointsType)</code>
     * method with the pointsType is exist.<br>
     * DigitalRunPointsManagerPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsType_WithExistedType() throws Exception {
        em.SetExceptionFlag(true);
        DigitalRunPointsType pointsType = new DigitalRunPointsType();
        try {
            impl.createDigitalRunPointsType(pointsType);
            fail("DigitalRunPointsManagerPersistenceException should be thrown.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createDigitalRunPointsType(DigitalRunPointsType pointsType)</code>
     * method with the pointsType's id is positive.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsType_WithIdPositive() throws Exception {
        DigitalRunPointsType pointsType = new DigitalRunPointsType();
        pointsType.setId(2);
        try {
            impl.createDigitalRunPointsType(pointsType);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>updateDigitalRunPointsType(DigitalRunPointsType pointsType)</code> method, expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsType_Accuracy() throws Exception {
        DigitalRunPointsType pointsType = new DigitalRunPointsType();
        pointsType.setCreationDate(new Date());
        pointsType.setModificationDate(new Date());
        pointsType.setDescription("description");
        impl.createDigitalRunPointsType(pointsType);

        pointsType.setDescription("new description");
        impl.updateDigitalRunPointsType(pointsType);

        DigitalRunPointsType result = impl.getDigitalRunPointsType(pointsType.getId());
        assertEquals("The description should be same as ", "new description", result.getDescription());

        impl.removeDigitalRunPointsType(result.getId());
    }

    /**
     * <p>
     * Failure test for the <code>updateDigitalRunPointsType(DigitalRunPointsType pointsType)</code>
     * method with the pointsType is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsType_WithNull() throws Exception {
        try {
            impl.updateDigitalRunPointsType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateDigitalRunPointsType(DigitalRunPointsType pointsType)</code>
     * method with the pointsType is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsType_WithNotExistpointsOperation() throws Exception {
        this.em.SetExceptionFlag(true);
        try {
            impl.updateDigitalRunPointsType(new DigitalRunPointsType());
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>removeDigitalRunPointsType(long pointsTypeId)</code> method,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsType_Accuracy() throws Exception {
        DigitalRunPointsType pointsType = new DigitalRunPointsType();
        pointsType.setDescription("description");

        impl.createDigitalRunPointsType(pointsType);

        assertNotNull("The result should not be null.", impl.getDigitalRunPointsType(pointsType.getId()));

        impl.removeDigitalRunPointsType(pointsType.getId());

        try {
            impl.getDigitalRunPointsType(pointsType.getId());
            fail("The result should be not exist");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>removeDigitalRunPointsType(long pointsTypeId)</code> method with
     * the pointsTypeId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsType_WithNegativeId() throws Exception {
        try {
            impl.removeDigitalRunPointsType(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>removeDigitalRunPointsType(long pointsTypeId)</code> method with
     * the pointsTypeId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsType_WithNotExistId() throws Exception {
        try {
            impl.removeDigitalRunPointsType(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getDigitalRunPointsType(long pointsTypeId)</code> method,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsType_Accuracy() throws Exception {
        DigitalRunPointsType pointsType = new DigitalRunPointsType();
        pointsType.setDescription("description");

        impl.createDigitalRunPointsType(pointsType);

        DigitalRunPointsType result = impl.getDigitalRunPointsType(pointsType.getId());

        assertEquals("The description should be same as ", "description", result.getDescription());

        impl.removeDigitalRunPointsType(pointsType.getId());
    }

    /**
     * <p>
     * Failure test for the <code>getDigitalRunPointsType(long pointsTypeId)</code> method with
     * the pointsTypeId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsType_WithNegativeId() throws Exception {
        try {
            impl.getDigitalRunPointsType(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getDigitalRunPointsType(long pointsTypeId)</code> method with
     * the pointsTypeId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsType_WithNotExistId() throws Exception {
        try {
            impl.getDigitalRunPointsType(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getAllDigitalRunPointsTypes()</code> method, expects no error
     * occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetAllDigitalRunPointsTypes_Accuracy() throws Exception {
        List<DigitalRunPointsType> result = impl.getAllDigitalRunPointsTypes();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 0, result.size());

        DigitalRunPointsType pointsType = new DigitalRunPointsType();
        pointsType.setDescription("description");
        impl.createDigitalRunPointsType(pointsType);

        result = impl.getAllDigitalRunPointsTypes();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 1, result.size());

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
