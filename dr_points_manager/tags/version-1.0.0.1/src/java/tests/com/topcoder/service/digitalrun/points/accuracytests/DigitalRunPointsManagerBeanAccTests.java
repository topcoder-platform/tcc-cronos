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
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.points.ConfigurationProvider;
import com.topcoder.service.digitalrun.points.manager.bean.DigitalRunPointsManagerBean;

import junit.framework.TestCase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Date;
import java.util.List;


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

        DigitalRunPointsStatus status = new DigitalRunPointsStatus();
        status.setCreationDate(new Date());
        status.setDescription("description");
        status.setModificationDate(new Date());
        entity.setDigitalRunPointsStatus(status);
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
        setData(entity);

        DigitalRunPointsStatus status = new DigitalRunPointsStatus();
        setData(status);
        entity.setDigitalRunPointsStatus(status);

        DigitalRunPointsOperation op = new DigitalRunPointsOperation();
        setData(op);
        entity.setDigitalRunPointsOperation(op);

        DigitalRunPointsReferenceType type = new DigitalRunPointsReferenceType();
        setData(type);
        entity.setDigitalRunPointsReferenceType(type);

        DigitalRunPointsType type2 = new DigitalRunPointsType();
        setData(type2);
        entity.setDigitalRunPointsType(type2);

        Track track = new Track();
        setData(track);
        entity.setTrack(track);
        entity.setUserId(123);
        entity.setReferenceId(234);

        dao.createDigitalRunPoints(entity);
        // update it
        entity.setDescription("new description");
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
        setData(entity);

        DigitalRunPointsStatus status = new DigitalRunPointsStatus();
        setData(status);
        entity.setDigitalRunPointsStatus(status);

        DigitalRunPointsOperation op = new DigitalRunPointsOperation();
        setData(op);
        entity.setDigitalRunPointsOperation(op);

        DigitalRunPointsReferenceType type = new DigitalRunPointsReferenceType();
        setData(type);
        entity.setDigitalRunPointsReferenceType(type);

        DigitalRunPointsType type2 = new DigitalRunPointsType();
        setData(type2);
        entity.setDigitalRunPointsType(type2);

        Track track = new Track();
        setData(track);
        entity.setTrack(track);
        entity.setUserId(123);
        entity.setReferenceId(234);

        dao.createDigitalRunPoints(entity);
        dao.removeDigitalRunPoints(entity.getId());
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
        setData(entity);

        DigitalRunPointsStatus status = new DigitalRunPointsStatus();
        setData(status);
        entity.setDigitalRunPointsStatus(status);

        DigitalRunPointsOperation op = new DigitalRunPointsOperation();
        setData(op);
        entity.setDigitalRunPointsOperation(op);

        DigitalRunPointsReferenceType type = new DigitalRunPointsReferenceType();
        setData(type);
        entity.setDigitalRunPointsReferenceType(type);

        DigitalRunPointsType type2 = new DigitalRunPointsType();
        setData(type2);
        entity.setDigitalRunPointsType(type2);

        Track track = new Track();
        setData(track);
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
    }

    /**
     * the accuracy test for the method createDigitalRunPointsType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testcreateDigitalRunPointsType() throws Exception {
        DigitalRunPointsType entity = new DigitalRunPointsType();
        setData(entity);
        dao.createDigitalRunPointsType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsOperation id is " + entity.getId());

        DigitalRunPointsType result = dao.getDigitalRunPointsType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsType(entity.getId());
    }

    /**
     * the accuracy test for the method updateDigitalRunPointsType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testupdateDigitalRunPointsType() throws Exception {
        DigitalRunPointsType entity = new DigitalRunPointsType();
        setData(entity);
        dao.createDigitalRunPointsType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsType id is " + entity.getId());

        DigitalRunPointsType result = dao.getDigitalRunPointsType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateDigitalRunPointsType(entity);
        result = dao.getDigitalRunPointsType(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsType(entity.getId());
    }

    /**
     * the accuracy test for the method removeDigitalRunPointsType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testremoveDigitalRunPointsType() throws Exception {
        DigitalRunPointsType entity = new DigitalRunPointsType();
        setData(entity);
        dao.createDigitalRunPointsType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsType id is " + entity.getId());

        DigitalRunPointsType result = dao.getDigitalRunPointsType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        // it is removed.
        dao.removeDigitalRunPointsType(entity.getId());
    }

    /**
     * the accuracy test for the method getDigitalRunPointsType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetDigitalRunPointsType() throws Exception {
        DigitalRunPointsType entity = new DigitalRunPointsType();
        setData(entity);
        dao.createDigitalRunPointsType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsType id is " + entity.getId());

        // get the result
        DigitalRunPointsType result = dao.getDigitalRunPointsType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateDigitalRunPointsType(entity);
        result = dao.getDigitalRunPointsType(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsType(entity.getId());
    }

    /**
     * the accuracy test for the method getAllDigitalRunPointsTypes.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetAllDigitalRunPointsTypes() throws Exception {
        DigitalRunPointsType entity = new DigitalRunPointsType();
        setData(entity);
        dao.createDigitalRunPointsType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsType id is " + entity.getId());

        // get the result
        // List<DigitalRunPointsType> list = dao.getAllDigitalRunPointsTypes();
    }

    /**
     * the accuracy test for the method createDigitalRunPointsStatus.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testcreateDigitalRunPointsStatus() throws Exception {
        DigitalRunPointsStatus entity = new DigitalRunPointsStatus();
        setData(entity);
        dao.createDigitalRunPointsStatus(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsOperation id is " + entity.getId());

        DigitalRunPointsStatus result = dao.getDigitalRunPointsStatus(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsStatus(entity.getId());
    }

    /**
     * the accuracy test for the method updateDigitalRunPointsStatus.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testupdateDigitalRunPointsStatus() throws Exception {
        DigitalRunPointsStatus entity = new DigitalRunPointsStatus();
        setData(entity);
        dao.createDigitalRunPointsStatus(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsStatus id is " + entity.getId());

        DigitalRunPointsStatus result = dao.getDigitalRunPointsStatus(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateDigitalRunPointsStatus(entity);
        result = dao.getDigitalRunPointsStatus(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsStatus(entity.getId());
    }

    /**
     * the accuracy test for the method removeDigitalRunPointsStatus.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testremoveDigitalRunPointsStatus() throws Exception {
        DigitalRunPointsStatus entity = new DigitalRunPointsStatus();
        setData(entity);
        dao.createDigitalRunPointsStatus(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsStatus id is " + entity.getId());

        DigitalRunPointsStatus result = dao.getDigitalRunPointsStatus(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        // it is removed.
        dao.removeDigitalRunPointsStatus(entity.getId());
    }

    /**
     * the accuracy test for the method getDigitalRunPointsStatus.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetDigitalRunPointsStatus() throws Exception {
        DigitalRunPointsStatus entity = new DigitalRunPointsStatus();
        setData(entity);
        dao.createDigitalRunPointsStatus(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsStatus id is " + entity.getId());

        // get the result
        DigitalRunPointsStatus result = dao.getDigitalRunPointsStatus(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateDigitalRunPointsStatus(entity);
        result = dao.getDigitalRunPointsStatus(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsStatus(entity.getId());
    }

    /**
     * the accuracy test for the method getAllDigitalRunPointsStatuses.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetAllDigitalRunPointsStatuses() throws Exception {
        DigitalRunPointsStatus entity = new DigitalRunPointsStatus();
        setData(entity);
        dao.createDigitalRunPointsStatus(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsStatus id is " + entity.getId());

        // get the result
        List<DigitalRunPointsStatus> list = dao.getAllDigitalRunPointsStatuses();
    }

    /**
     * the accuracy test for the method createDigitalRunPointsOperation.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testcreateDigitalRunPointsOperation() throws Exception {
        DigitalRunPointsOperation entity = new DigitalRunPointsOperation();
        setData(entity);
        dao.createDigitalRunPointsOperation(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsOperation id is " + entity.getId());

        DigitalRunPointsOperation result = dao.getDigitalRunPointsOperation(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsOperation(entity.getId());
    }

    /**
     * the accuracy test for the method updateDigitalRunPointsOperation.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testupdateDigitalRunPointsOperation() throws Exception {
        DigitalRunPointsOperation entity = new DigitalRunPointsOperation();
        setData(entity);
        dao.createDigitalRunPointsOperation(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsOperation id is " + entity.getId());

        DigitalRunPointsOperation result = dao.getDigitalRunPointsOperation(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateDigitalRunPointsOperation(entity);
        result = dao.getDigitalRunPointsOperation(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsOperation(entity.getId());
    }

    /**
     * the accuracy test for the method removeDigitalRunPointsOperation.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testremoveDigitalRunPointsOperation() throws Exception {
        DigitalRunPointsOperation entity = new DigitalRunPointsOperation();
        setData(entity);
        dao.createDigitalRunPointsOperation(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsOperation id is " + entity.getId());

        DigitalRunPointsOperation result = dao.getDigitalRunPointsOperation(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        // it is removed.
        dao.removeDigitalRunPointsOperation(entity.getId());
    }

    /**
     * the accuracy test for the method getDigitalRunPointsOperation.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetDigitalRunPointsOperation() throws Exception {
        DigitalRunPointsOperation entity = new DigitalRunPointsOperation();
        setData(entity);
        dao.createDigitalRunPointsOperation(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsOperation id is " + entity.getId());

        // get the result
        DigitalRunPointsOperation result = dao.getDigitalRunPointsOperation(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateDigitalRunPointsOperation(entity);
        result = dao.getDigitalRunPointsOperation(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsOperation(entity.getId());
    }

    /**
     * the accuracy test for the method getAllDigitalRunPointsOperations.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetAllDigitalRunPointsOperations()
        throws Exception {
        DigitalRunPointsOperation entity = new DigitalRunPointsOperation();
        setData(entity);
        dao.createDigitalRunPointsOperation(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsOperation id is " + entity.getId());

        // get the result
        List<DigitalRunPointsOperation> list = dao.getAllDigitalRunPointsOperations();
    }

    /**
     * the accuracy test for the method createDigitalRunPointsReferenceType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_createDigitalRunPointsReferenceType()
        throws Exception {
        DigitalRunPointsReferenceType entity = new DigitalRunPointsReferenceType();
        setData(entity);
        dao.createDigitalRunPointsReferenceType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The new created DigitalRunPointsReferenceType id is " + entity.getId());

        DigitalRunPointsReferenceType result = dao.getDigitalRunPointsReferenceType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsReferenceType(entity.getId());
    }

    /**
     * the accuracy test for the method updateDigitalRunPointsReferenceType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_updateDigitalRunPointsReferenceType()
        throws Exception {
        DigitalRunPointsReferenceType entity = new DigitalRunPointsReferenceType();
        setData(entity);
        dao.createDigitalRunPointsReferenceType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The new created DigitalRunPointsReferenceType id is " + entity.getId());

        entity.setDescription("new description");
        dao.updateDigitalRunPointsReferenceType(entity);

        DigitalRunPointsReferenceType result = dao.getDigitalRunPointsReferenceType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsReferenceType(entity.getId());
    }

    /**
     * the accuracy test for the method removeDigitalRunPointsReferenceType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_removeDigitalRunPointsReferenceType()
        throws Exception {
        DigitalRunPointsReferenceType entity = new DigitalRunPointsReferenceType();
        setData(entity);
        dao.createDigitalRunPointsReferenceType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The new created DigitalRunPointsReferenceType id is " + entity.getId());

        entity.setDescription("new description");
        dao.updateDigitalRunPointsReferenceType(entity);

        DigitalRunPointsReferenceType result = dao.getDigitalRunPointsReferenceType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        // it should be removed.
        dao.removeDigitalRunPointsReferenceType(entity.getId());
    }

    /**
     * the accuracy test for the method getDigitalRunPointsReferenceType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_getDigitalRunPointsReferenceType()
        throws Exception {
        DigitalRunPointsReferenceType entity = new DigitalRunPointsReferenceType();
        setData(entity);
        dao.createDigitalRunPointsReferenceType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The new created DigitalRunPointsReferenceType id is " + entity.getId());

        entity.setDescription("new description");
        dao.updateDigitalRunPointsReferenceType(entity);

        // get by id.
        DigitalRunPointsReferenceType result = dao.getDigitalRunPointsReferenceType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        // it should be removed.
        dao.removeDigitalRunPointsReferenceType(entity.getId());
    }

    /**
     * the accuracy test for the method getAllDigitalRunPointsReferenceTypes.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_getAllDigitalRunPointsReferenceTypes()
        throws Exception {
        DigitalRunPointsReferenceType entity = new DigitalRunPointsReferenceType();
        setData(entity);
        dao.createDigitalRunPointsReferenceType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The new created DigitalRunPointsReferenceType id is " + entity.getId());

        entity.setDescription("new description");
        dao.updateDigitalRunPointsReferenceType(entity);

        List<DigitalRunPointsReferenceType> list = dao.getAllDigitalRunPointsReferenceTypes();
    }
}
