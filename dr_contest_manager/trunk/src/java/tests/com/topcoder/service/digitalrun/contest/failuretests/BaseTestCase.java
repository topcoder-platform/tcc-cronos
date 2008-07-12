/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.failuretests;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.hibernate.ejb.Ejb3Configuration;

import com.topcoder.service.digitalrun.contest.DigitalRunContestManagerRemote;
import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackContest;
import com.topcoder.service.digitalrun.entity.TrackContestResultCalculator;
import com.topcoder.service.digitalrun.entity.TrackContestType;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * The base test case for failure tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseTestCase extends TestCase {

    /**
     * <p>
     * The ear name.
     * </p>
     */
    public static final String EAR_NAME = "dr_contest_manager";

    /**
     * <p>
     * Represents an error message.
     * </p>
     */
    public static final String ERROR_MESSAGE = "error";

    /**
     * <p>
     * Represents an error cause.
     * </p>
     */
    public static final Throwable CAUSE = new Exception("FailureTest");

    /**
     * <p>
     * Represents the exception data.
     * </p>
     */
    public static final ExceptionData EXCEPTION_DATA = new ExceptionData();

    /**
     * <p>
     * Represents the application code set in exception data.
     * </p>
     */
    public static final String APPLICATION_CODE = "Failure";

    /**
     * <p>
     * The mock instance of <code>SessionContext</code>.
     * </p>
     */
    public static final SessionContext CONTEXT = EasyMock.createNiceMock(SessionContext.class);

    /**
     * <p>
     * The mock instance of <code>EntityManager</code>.
     * </p>
     */
    public static final EntityManager EM = EasyMock.createNiceMock(EntityManager.class);

    /**
     * <p>
     * The persistence unit used in the tests.
     * </p>
     */
    public static final String PERSISTENCE_UNIT = "HibernateDRPersistence";

    /**
     * <p>
     * The persistence unit used in the tests.
     * </p>
     */
    private static EntityManager entityManager;

    static {
        EXCEPTION_DATA.setApplicationCode(APPLICATION_CODE);
    }

    /**
     * <p>
     * Get <code>EntityManager</code>.
     * </p>
     *
     * @return <code>EntityManager</code>.
     */
    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            Ejb3Configuration cfg = new Ejb3Configuration();
            EntityManagerFactory emf = cfg.configure("/META-INF/hibernate.cfg.xml").buildEntityManagerFactory();
            entityManager = emf.createEntityManager();
        }
        return entityManager;
    }

    /**
     * <p>
     * Get <code>DigitalRunContestManagerBean</code> EJB remote interface.
     * </p>
     *
     * @return <code>DigitalRunContestManagerBean</code> EJB remote interface.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected DigitalRunContestManagerRemote getDigitalRunContestManagerRemoteEJB() throws Exception {

        Properties env = new Properties();
        Context context = new InitialContext(env);
        return (DigitalRunContestManagerRemote) context.lookup(EAR_NAME + "/DigitalRunContestManagerBean/remote");
    }

    /**
     * <p>
     * Get the value from protected field.
     * </p>
     *
     * @param targetClazz
     *            The target class.
     * @param target
     *            The target object.
     * @param fieldName
     *            The name of field.
     * @return the value of the field.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static Object getField(Class<?> targetClazz, Object target, String fieldName) throws Exception {
        Field field = targetClazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }

    /**
     * <p>
     * Set the value into protected field.
     * </p>
     *
     * @param targetClazz
     *            The target class.
     * @param target
     *            The target object.
     * @param fieldName
     *            The name of field.
     * @param fieldValue
     *            The field value to be set.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void setField(Class<?> targetClazz, Object target, String fieldName, Object fieldValue)
            throws Exception {
        Field field = targetClazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, fieldValue);
    }

    /**
     * Helper method to persist the entity with transaction.
     *
     * @param entity
     *            the entity to persist
     */
    protected void persist(Object entity) {
        getEntityManager().getTransaction().begin();
        getEntityManager().persist(entity);
        getEntityManager().getTransaction().commit();
    }

    /**
     * Helper method to remove the entity with transaction.
     *
     * @param entity
     *            the entity to persist
     */
    protected void delete(Object entity) {
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(entity);
        getEntityManager().getTransaction().commit();
    }

    /**
     * Creates the TrackContestType for testing purpose.
     *
     * @return the entity created
     */
    protected TrackContestType createTrackContestType() {
        TrackContestType entity = new TrackContestType();
        entity.setDescription("description");
        return entity;
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
     * Creates the TrackContestResultCalculator for testing purpose.
     *
     * @return the entity created
     */
    protected TrackContestResultCalculator createTrackContestResultCalculator() {
        TrackContestResultCalculator entity = new TrackContestResultCalculator();
        entity.setClassName("className");
        entity.setDescription("description");
        return entity;
    }

    /**
     * Creates the TrackContest for testing purpose.
     *
     * @param track
     *            the associated track
     * @param trackContestResultCalculator
     *            the associated track contest result calculator
     * @param trackContestType
     *            the associated track contest type
     * @return the entity created
     */
    protected TrackContest createTrackContest(Track track, TrackContestResultCalculator trackContestResultCalculator,
            TrackContestType trackContestType) {
        TrackContest entity = new TrackContest();
        entity.setDescription("description");
        entity.setTrack(track);
        entity.setTrackContestResultCalculator(trackContestResultCalculator);
        entity.setTrackContestType(trackContestType);
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
    protected Track createTrack(PointsCalculator pointsCalculator, TrackStatus trackStatus, TrackType trackType) {
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
