/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.accuracytests;

import com.topcoder.service.digitalrun.entity.BaseEntity;
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
import com.topcoder.service.digitalrun.points.TestHelper;
import com.topcoder.service.digitalrun.points.manager.bean.DigitalRunPointsManagerBean;

import junit.framework.TestCase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Date;
import java.util.List;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;


/**
 * The accuracy test for the class {@link DigitalRunPointsManagerBean}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunPointsManagerBeanAccTests extends TestCase {
    /**
     * The DigitalRunPointsManagerBean for test.
     */
    private DigitalRunPointsManagerBean dao;

    /**
     * sets up the test environment.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    protected void setUp() throws Exception {
        dao = new DigitalRunPointsManagerBean();
        ConfigurationProvider.retrieveConfigurationFromFile();
        setPrivateField(dao, "logName", "logger_temp");
        setPrivateField(dao, "sessionContext", new MockSessionContext());
        setPrivateField(dao, "unitName", "unit_name");

        setPrivateField(dao, "pointsDAOKey", "PointsDAOImpl");
        setPrivateField(dao, "pointsTypeDAOKey", "PointsTypeDAOImpl");
        setPrivateField(dao, "pointsOperationDAOKey", "PointsOperationDAOImpl");
        setPrivateField(dao, "pointsReferenceTypeDAOKey", "PointsReferenceTypeDAOImpl");
        setPrivateField(dao, "pointsStatusDAOKey", "PointsStatusDAOImpl");

        Method info = dao.getClass().getDeclaredMethod("initialize");
        info.setAccessible(true);
        info.invoke(dao);
    }

    /**
     * tear down the test environment.
     */
    protected void tearDown() {
        dao = null;
    }

    /**
     * the accuracy test for the constructor.
     */
    public void testCtor() {
        assertNotNull("The instance should not be null.", dao);
    }

    /**
     * the accuracy test for the method initialize.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_initialize() throws Exception {
        Method info = dao.getClass().getDeclaredMethod("initialize");
        info.setAccessible(true);
        info.invoke(dao);
    }

    /**
     * Sets the value of a private field in the given class.
     *
     * @param instance
     *            the instance which the private field belongs to
     * @param name
     *            the name of the private field to be set
     * @param value
     *            the value to set
     */
    private static void setPrivateField(Object instance, String name, Object value) {
        Field field = null;

        try {
            // get the reflection of the field
            field = instance.getClass().getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
    }

    /**
     * the accuracy test for the method createDigitalRunPoints.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testcreateDigitalRunPoints() throws Exception {
        DigitalRunPoints entity = new DigitalRunPoints();
        entity.setAmount(100.00);
        entity.setApplicationDate(new Date());
        entity.setAwardDate(new Date());
        entity.setCreationDate(new Date());
        entity.setDescription("description");

        SessionContext sessionContext = (SessionContext)TestHelper.getPrivateField(DigitalRunPointsManagerBean.class, dao, "sessionContext");
        EntityManager em = (EntityManager)sessionContext.lookup("em");

        DigitalRunPointsStatus digitalRunPointsStatus = new DigitalRunPointsStatus();
        digitalRunPointsStatus.setDescription("description");
        TestHelper.persist(em, digitalRunPointsStatus);
        entity.setDigitalRunPointsStatus(digitalRunPointsStatus);

        DigitalRunPointsType digitalRunPointsType = new DigitalRunPointsType();
        digitalRunPointsType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsType);
        entity.setDigitalRunPointsType(digitalRunPointsType);

        DigitalRunPointsReferenceType digitalRunPointsReferenceType = new DigitalRunPointsReferenceType();
        digitalRunPointsReferenceType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsReferenceType);
        entity.setDigitalRunPointsReferenceType(digitalRunPointsReferenceType);

        DigitalRunPointsOperation digitalRunPointsOperation = new DigitalRunPointsOperation();
        digitalRunPointsOperation.setDescription("description");
        TestHelper.persist(em, digitalRunPointsOperation);
        entity.setDigitalRunPointsOperation(digitalRunPointsOperation);

        PointsCalculator pointsCalculator = createPointsCalculator();
        TestHelper.persist(em, pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        TestHelper.persist(em, trackStatus);

        TrackType trackType = createTrackType();
        TestHelper.persist(em, trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType);
        TestHelper.persist(em, track);

        entity.setTrack(track);

        dao.createDigitalRunPoints(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);

        DigitalRunPoints result = dao.getDigitalRunPoints(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getAmount(), result.getAmount());
        assertEquals("The entity is incorrect.", entity.getApplicationDate(), result.getApplicationDate());
        assertEquals("The entity is incorrect.", entity.getAwardDate(), result.getAwardDate());
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        System.out.println("The DigitalRunPoints id is " + entity.getId());
        dao.removeDigitalRunPoints(entity.getId());

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
     * the accuracy test for the method updateDigitalRunPoints.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testupdateDigitalRunPoints() throws Exception {
        DigitalRunPoints entity = new DigitalRunPoints();
        entity.setAmount(100.00);
        entity.setApplicationDate(new Date());
        entity.setAwardDate(new Date());
        entity.setCreationDate(new Date());
        entity.setDescription("description");

        SessionContext sessionContext = (SessionContext)TestHelper.getPrivateField(DigitalRunPointsManagerBean.class, dao, "sessionContext");
        EntityManager em = (EntityManager)sessionContext.lookup("em");

        DigitalRunPointsStatus digitalRunPointsStatus = new DigitalRunPointsStatus();
        digitalRunPointsStatus.setDescription("description");
        TestHelper.persist(em, digitalRunPointsStatus);
        entity.setDigitalRunPointsStatus(digitalRunPointsStatus);

        DigitalRunPointsType digitalRunPointsType = new DigitalRunPointsType();
        digitalRunPointsType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsType);
        entity.setDigitalRunPointsType(digitalRunPointsType);

        DigitalRunPointsReferenceType digitalRunPointsReferenceType = new DigitalRunPointsReferenceType();
        digitalRunPointsReferenceType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsReferenceType);
        entity.setDigitalRunPointsReferenceType(digitalRunPointsReferenceType);

        DigitalRunPointsOperation digitalRunPointsOperation = new DigitalRunPointsOperation();
        digitalRunPointsOperation.setDescription("description");
        TestHelper.persist(em, digitalRunPointsOperation);
        entity.setDigitalRunPointsOperation(digitalRunPointsOperation);

        PointsCalculator pointsCalculator = createPointsCalculator();
        TestHelper.persist(em, pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        TestHelper.persist(em, trackStatus);

        TrackType trackType = createTrackType();
        TestHelper.persist(em, trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType);
        TestHelper.persist(em, track);

        entity.setTrack(track);
        entity.setTrack(track);
        entity.setUserId(123);
        entity.setReferenceId(234);

        dao.createDigitalRunPoints(entity);
        // update it
        entity.setDescription("new description");
        entity.setModificationDate(new Date());
        dao.updateDigitalRunPoints(entity);

        DigitalRunPoints result = dao.getDigitalRunPoints(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getAmount(), result.getAmount());
        assertEquals("The entity is incorrect.", entity.getApplicationDate(), result.getApplicationDate());
        assertEquals("The entity is incorrect.", entity.getAwardDate(), result.getAwardDate());
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        System.out.println("The DigitalRunPoints id is " + entity.getId());
        dao.removeDigitalRunPoints(entity.getId());

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
     * set some data.
     */
    private void setData(BaseEntity entity) {
        entity.setCreationDate(new Date());
        entity.setDescription("description");
        entity.setModificationDate(new Date());
    }

    /**
     * the accuracy test for the method removeDigitalRunPoints.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testremoveDigitalRunPoints() throws Exception {
        DigitalRunPoints entity = new DigitalRunPoints();
        entity.setAmount(100.00);
        entity.setApplicationDate(new Date());
        entity.setAwardDate(new Date());
        entity.setCreationDate(new Date());
        entity.setDescription("description");

        SessionContext sessionContext = (SessionContext)TestHelper.getPrivateField(DigitalRunPointsManagerBean.class, dao, "sessionContext");
        EntityManager em = (EntityManager)sessionContext.lookup("em");

        DigitalRunPointsStatus digitalRunPointsStatus = new DigitalRunPointsStatus();
        digitalRunPointsStatus.setDescription("description");
        TestHelper.persist(em, digitalRunPointsStatus);
        entity.setDigitalRunPointsStatus(digitalRunPointsStatus);

        DigitalRunPointsType digitalRunPointsType = new DigitalRunPointsType();
        digitalRunPointsType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsType);
        entity.setDigitalRunPointsType(digitalRunPointsType);

        DigitalRunPointsReferenceType digitalRunPointsReferenceType = new DigitalRunPointsReferenceType();
        digitalRunPointsReferenceType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsReferenceType);
        entity.setDigitalRunPointsReferenceType(digitalRunPointsReferenceType);

        DigitalRunPointsOperation digitalRunPointsOperation = new DigitalRunPointsOperation();
        digitalRunPointsOperation.setDescription("description");
        TestHelper.persist(em, digitalRunPointsOperation);
        entity.setDigitalRunPointsOperation(digitalRunPointsOperation);

        PointsCalculator pointsCalculator = createPointsCalculator();
        TestHelper.persist(em, pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        TestHelper.persist(em, trackStatus);

        TrackType trackType = createTrackType();
        TestHelper.persist(em, trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType);
        TestHelper.persist(em, track);

        entity.setTrack(track);
        entity.setTrack(track);
        entity.setUserId(123);
        entity.setReferenceId(234);

        dao.createDigitalRunPoints(entity);
        dao.removeDigitalRunPoints(entity.getId());

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
     * the accuracy test for the method getDigitalRunPoints.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetDigitalRunPoints() throws Exception {
        DigitalRunPoints entity = new DigitalRunPoints();
        entity.setAmount(100.00);
        entity.setApplicationDate(new Date());
        entity.setAwardDate(new Date());
        entity.setCreationDate(new Date());
        entity.setDescription("description");

        SessionContext sessionContext = (SessionContext)TestHelper.getPrivateField(DigitalRunPointsManagerBean.class, dao, "sessionContext");
        EntityManager em = (EntityManager)sessionContext.lookup("em");

        DigitalRunPointsStatus digitalRunPointsStatus = new DigitalRunPointsStatus();
        digitalRunPointsStatus.setDescription("description");
        TestHelper.persist(em, digitalRunPointsStatus);
        entity.setDigitalRunPointsStatus(digitalRunPointsStatus);

        DigitalRunPointsType digitalRunPointsType = new DigitalRunPointsType();
        digitalRunPointsType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsType);
        entity.setDigitalRunPointsType(digitalRunPointsType);

        DigitalRunPointsReferenceType digitalRunPointsReferenceType = new DigitalRunPointsReferenceType();
        digitalRunPointsReferenceType.setDescription("description");
        TestHelper.persist(em, digitalRunPointsReferenceType);
        entity.setDigitalRunPointsReferenceType(digitalRunPointsReferenceType);

        DigitalRunPointsOperation digitalRunPointsOperation = new DigitalRunPointsOperation();
        digitalRunPointsOperation.setDescription("description");
        TestHelper.persist(em, digitalRunPointsOperation);
        entity.setDigitalRunPointsOperation(digitalRunPointsOperation);

        PointsCalculator pointsCalculator = createPointsCalculator();
        TestHelper.persist(em, pointsCalculator);

        TrackStatus trackStatus = createTrackStatus();
        TestHelper.persist(em, trackStatus);

        TrackType trackType = createTrackType();
        TestHelper.persist(em, trackType);

        Track track = createTrack(pointsCalculator, trackStatus, trackType);
        TestHelper.persist(em, track);

        entity.setTrack(track);
        entity.setTrack(track);
        entity.setUserId(123);
        entity.setReferenceId(234);

        dao.createDigitalRunPoints(entity);

        DigitalRunPoints result = dao.getDigitalRunPoints(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getAmount(), result.getAmount());
        assertEquals("The entity is incorrect.", entity.getApplicationDate(), result.getApplicationDate());
        assertEquals("The entity is incorrect.", entity.getAwardDate(), result.getAwardDate());
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        System.out.println("The DigitalRunPoints id is " + entity.getId());
        dao.removeDigitalRunPoints(entity.getId());

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
