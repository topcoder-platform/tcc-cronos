/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.accuracytests;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

import com.topcoder.service.digitalrun.entity.BaseEntity;
import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;
import com.topcoder.service.digitalrun.track.ConfigurationProvider;
import com.topcoder.service.digitalrun.track.DigitalRunTrackDAO;
import com.topcoder.service.digitalrun.track.manager.bean.DigitalRunTrackManagerBean;

import junit.framework.TestCase;


/**
 * The accuracy test for the class {@link DigitalRunTrackManagerBean}.
 *
 * @author KLW
 * @version 1.0
 */
public class DigitalRunTrackManagerBeanAccTests extends TestCase {
    /**
     * The DigitalRunTrackManagerBean instance for test.
     */
    private DigitalRunTrackManagerBean dao;
    /**
     * The EntityManager for test.
     */
    private EntityManager manager;

    /**
     * sets up the test environment.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    protected void setUp() throws Exception {
        dao = new DigitalRunTrackManagerBean();
        SessionContext context = new MockSessionContext();
        manager = (EntityManager) context.lookup("entitManager");
        

        setPrivateField(dao, "unitName", "unitName");
        setPrivateField(dao, "trackDAOKey", "trackDAOKey");
        setPrivateField(dao, "pointsCalculatorDAOKey", "pointsCalculatorDAOKey");
        setPrivateField(dao, "projectTypeDAOKey", "projectTypeDAOKey");
        setPrivateField(dao, "trackStatusDAOKey", "trackStatusDAOKey");
        setPrivateField(dao, "trackTypeDAOKey", "trackTypeDAOKey");
        setPrivateField(dao, "sessionContext", context);
        setPrivateField(dao, "activeStatusId", 20L);

        Method info = dao.getClass().getDeclaredMethod("initialize");
        info.setAccessible(true);
        info.invoke(dao);
        
        manager.getTransaction().begin();
    }

    /**
     * tear down the test environment.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    protected void tearDown() throws Exception {
        manager.getTransaction().commit();
        dao = null;
    }

    /**
     * the accuracy test for the constructor.
     */
    public void testCtor() {
        assertNotNull("The instance should not be null.", dao);
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
     * the accuracy test for the method createTrack.
     * 
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void test_createTrack() throws Exception {
        Track entity = new Track();
        setData(entity);
        entity.setStartDate(new Date());
        entity.setEndDate(new Date());

        ProjectType projectType = new ProjectType();
        projectType.setId(20);
        setData(projectType);
        projectType.setCreationUser("test");
        projectType.setModificationUser("test");
        projectType.setName("name");
        manager.persist(projectType);

        List<ProjectType> projectTypes = new ArrayList<ProjectType>();
        projectTypes.add(projectType);
        entity.setProjectTypes(projectTypes);

        PointsCalculator pointsCalculator = new PointsCalculator();
        setData(pointsCalculator);
        pointsCalculator.setClassName("className");
        manager.persist(pointsCalculator);
        entity.setPointsCalculator(pointsCalculator);

        TrackStatus trackStatus = new TrackStatus();
        setData(trackStatus);
        manager.persist(trackStatus);
        entity.setTrackStatus(trackStatus);

        TrackType trackType = new TrackType();
        setData(trackType);
        manager.persist(trackType);
        entity.setTrackType(trackType);

        dao.createTrack(entity);
        try {
            assertTrue("The result is incorrect.", entity.getId() > 0);

            Track result = dao.getTrack(entity.getId());
            assertEquals("The result is incorrect.", entity.getId(), result
                    .getId());
            assertEquals("The result is incorrect.", entity.getCreationDate(),
                    result.getCreationDate());
            assertEquals("The result is incorrect.", entity.getDescription(),
                    result.getDescription());
            assertEquals("The result is incorrect.", entity
                    .getModificationDate(), result.getModificationDate());

            List<ProjectType> resultProjectTypes = (List<ProjectType>) result
                    .getProjectTypes();
            assertEquals("The result is incorrect.", projectType.getId(),
                    resultProjectTypes.get(0).getId());
            assertEquals("The result is incorrect.", 1, resultProjectTypes
                    .size());
            assertEquals("The result is incorrect.", pointsCalculator.getId(),
                    entity.getPointsCalculator().getId());
            assertEquals("The result is incorrect.", trackStatus.getId(),
                    result.getTrackStatus().getId());
            assertEquals("The result is incorrect.", trackType.getId(), result
                    .getTrackType().getId());
        } finally {
            manager.remove(entity);
            manager.remove(projectType);
            manager.remove(pointsCalculator);
            manager.remove(trackStatus);
            manager.remove(trackType);
        }
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
     * the accuracy test for the method updateTrack.
     * 
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void test_updateTrack() throws Exception {
        Track entity = new Track();
        setData(entity);
        entity.setStartDate(new Date());
        entity.setEndDate(new Date());

        ProjectType projectType = new ProjectType();
        projectType.setId(20);
        setData(projectType);
        projectType.setCreationUser("test");
        projectType.setModificationUser("test");
        projectType.setName("name");
        manager.persist(projectType);

        List<ProjectType> projectTypes = new ArrayList<ProjectType>();
        projectTypes.add(projectType);
        entity.setProjectTypes(projectTypes);

        PointsCalculator pointsCalculator = new PointsCalculator();
        setData(pointsCalculator);
        pointsCalculator.setClassName("className");
        manager.persist(pointsCalculator);
        entity.setPointsCalculator(pointsCalculator);

        TrackStatus trackStatus = new TrackStatus();
        setData(trackStatus);
        manager.persist(trackStatus);
        entity.setTrackStatus(trackStatus);

        TrackType trackType = new TrackType();
        setData(trackType);
        manager.persist(trackType);
        entity.setTrackType(trackType);

        dao.createTrack(entity);
        assertTrue("The result is incorrect.", entity.getId() > 0);
        // update it.
        entity.setDescription("new description");
        entity.setStartDate(new Date());
        dao.updateTrack(entity);

        Track result = dao.getTrack(entity.getId());
        try {
            assertEquals("The result is incorrect.", entity.getId(), result
                    .getId());
            assertEquals("The result is incorrect.", entity.getCreationDate(),
                    result.getCreationDate());
            assertEquals("The result is incorrect.", entity.getDescription(),
                    result.getDescription());
            assertEquals("The result is incorrect.", entity
                    .getModificationDate(), result.getModificationDate());

            List<ProjectType> resultProjectTypes = (List<ProjectType>) result
                    .getProjectTypes();
            assertEquals("The result is incorrect.", projectType.getId(),
                    resultProjectTypes.get(0).getId());
            assertEquals("The result is incorrect.", 1, resultProjectTypes
                    .size());
            assertEquals("The result is incorrect.", pointsCalculator.getId(),
                    entity.getPointsCalculator().getId());
            assertEquals("The result is incorrect.", trackStatus.getId(),
                    result.getTrackStatus().getId());
            assertEquals("The result is incorrect.", trackType.getId(), result
                    .getTrackType().getId());
        } finally {
            manager.remove(entity);
            manager.remove(projectType);
            manager.remove(pointsCalculator);
            manager.remove(trackStatus);
            manager.remove(trackType);
        }
    }

    /**
     * the accuracy test for the method removeTrack.
     * 
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void test_removeTrack() throws Exception {
        Track entity = new Track();
        setData(entity);
        entity.setStartDate(new Date());
        entity.setEndDate(new Date());

        ProjectType projectType = new ProjectType();
        projectType.setId(20);
        setData(projectType);
        projectType.setCreationUser("test");
        projectType.setModificationUser("test");
        projectType.setName("name");
        manager.persist(projectType);

        List<ProjectType> projectTypes = new ArrayList<ProjectType>();
        projectTypes.add(projectType);
        entity.setProjectTypes(projectTypes);

        PointsCalculator pointsCalculator = new PointsCalculator();
        setData(pointsCalculator);
        pointsCalculator.setClassName("className");
        manager.persist(pointsCalculator);
        entity.setPointsCalculator(pointsCalculator);

        TrackStatus trackStatus = new TrackStatus();
        setData(trackStatus);
        manager.persist(trackStatus);
        entity.setTrackStatus(trackStatus);

        TrackType trackType = new TrackType();
        setData(trackType);
        manager.persist(trackType);
        entity.setTrackType(trackType);

        dao.createTrack(entity);
        try {
            assertTrue("The result is incorrect.", entity.getId() > 0);
        } finally {
            // remove it.

            dao.removeTrack(entity.getId());
            manager.remove(projectType);
            manager.remove(pointsCalculator);
            manager.remove(trackStatus);
            manager.remove(trackType);
        }
    }

    /**
     * the accuracy test for the method getTrack.
     * 
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void test_getTrack() throws Exception {
        Track entity = new Track();
        setData(entity);
        entity.setStartDate(new Date());
        entity.setEndDate(new Date());

        ProjectType projectType = new ProjectType();
        projectType.setId(20);
        setData(projectType);
        projectType.setCreationUser("test");
        projectType.setModificationUser("test");
        projectType.setName("name");
        manager.persist(projectType);

        List<ProjectType> projectTypes = new ArrayList<ProjectType>();
        projectTypes.add(projectType);
        entity.setProjectTypes(projectTypes);

        PointsCalculator pointsCalculator = new PointsCalculator();
        setData(pointsCalculator);
        pointsCalculator.setClassName("className");
        manager.persist(pointsCalculator);
        entity.setPointsCalculator(pointsCalculator);

        TrackStatus trackStatus = new TrackStatus();
        setData(trackStatus);
        manager.persist(trackStatus);
        entity.setTrackStatus(trackStatus);

        TrackType trackType = new TrackType();
        setData(trackType);
        manager.persist(trackType);
        entity.setTrackType(trackType);

        dao.createTrack(entity);
        assertTrue("The result is incorrect.", entity.getId() > 0);
        try {
            // get the result
            Track result = dao.getTrack(entity.getId());
            assertEquals("The result is incorrect.", entity.getId(), result
                    .getId());
            assertEquals("The result is incorrect.", entity.getCreationDate(),
                    result.getCreationDate());
            assertEquals("The result is incorrect.", entity.getDescription(),
                    result.getDescription());
            assertEquals("The result is incorrect.", entity
                    .getModificationDate(), result.getModificationDate());

            List<ProjectType> resultProjectTypes = (List<ProjectType>) result
                    .getProjectTypes();
            assertEquals("The result is incorrect.", projectType.getId(),
                    resultProjectTypes.get(0).getId());
            assertEquals("The result is incorrect.", 1, resultProjectTypes
                    .size());
            assertEquals("The result is incorrect.", pointsCalculator.getId(),
                    entity.getPointsCalculator().getId());
            assertEquals("The result is incorrect.", trackStatus.getId(),
                    result.getTrackStatus().getId());
            assertEquals("The result is incorrect.", trackType.getId(), result
                    .getTrackType().getId());
        } finally {
            manager.remove(entity);
            manager.remove(projectType);
            manager.remove(pointsCalculator);
            manager.remove(trackStatus);
            manager.remove(trackType);
        }
    }
    /**
     * <p>
     * Gets the field value of an object.
     * </p>
     *
     * @param obj
     *            the object where to get the field value.
     * @param fieldName
     *            the name of the field.
     * @return the field value
     * @throws Exception
     *             any exception occurs.
     */
    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
    /**
     * the accuracy test for the method getActiveTracks.
     * 
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void test_getActiveTracks() throws Exception {
        Track entity = new Track();
        setData(entity);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 12, 12);
        entity.setEndDate(calendar.getTime());
        calendar.set(1998, 12, 12);
        entity.setStartDate(calendar.getTime());

        ProjectType projectType = new ProjectType();
        projectType.setId(20);
        setData(projectType);
        projectType.setCreationUser("test");
        projectType.setModificationUser("test");
        projectType.setName("name");
        manager.persist(projectType);

        List<ProjectType> projectTypes = new ArrayList<ProjectType>();
        projectTypes.add(projectType);
        entity.setProjectTypes(projectTypes);

        PointsCalculator pointsCalculator = new PointsCalculator();
        setData(pointsCalculator);
        pointsCalculator.setClassName("className");
        manager.persist(pointsCalculator);
        entity.setPointsCalculator(pointsCalculator);

        TrackStatus trackStatus = new TrackStatus();
        setData(trackStatus);
        manager.persist(trackStatus);
        entity.setTrackStatus(trackStatus);

        TrackType trackType = new TrackType();
        setData(trackType);
        manager.persist(trackType);
        entity.setTrackType(trackType);
        try {
            dao.createTrack(entity);
            assertTrue("The result is incorrect.", entity.getId() > 0);
            DigitalRunTrackDAO trackDAO = (DigitalRunTrackDAO) getFieldValue(dao,"trackDAO");
            setPrivateField(trackDAO, "activeStatusId", trackStatus.getId());

            List<Track> list = dao.getActiveTracks();
            assertEquals(1, list.size());

            Track result = list.get(0);
            assertEquals("The result is incorrect.", entity.getId(), result
                    .getId());
            assertEquals("The result is incorrect.", entity.getCreationDate(),
                    result.getCreationDate());
            assertEquals("The result is incorrect.", entity.getDescription(),
                    result.getDescription());
            assertEquals("The result is incorrect.", entity
                    .getModificationDate(), result.getModificationDate());

            List<ProjectType> resultProjectTypes = (List<ProjectType>) result
                    .getProjectTypes();
            assertEquals("The result is incorrect.", projectType.getId(),
                    resultProjectTypes.get(0).getId());
            assertEquals("The result is incorrect.", 1, resultProjectTypes
                    .size());
            assertEquals("The result is incorrect.", pointsCalculator.getId(),
                    entity.getPointsCalculator().getId());
            assertEquals("The result is incorrect.", trackStatus.getId(),
                    result.getTrackStatus().getId());
            assertEquals("The result is incorrect.", trackType.getId(), result
                    .getTrackType().getId());
        } finally {
            manager.remove(entity);
            manager.remove(projectType);

            manager.remove(pointsCalculator);
            manager.remove(trackStatus);
            manager.remove(trackType);
        }
    }

    /**
     * the accuracy test for the method addTrackProjectType.
     * 
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void test_addTrackProjectType() throws Exception {
        Track entity = new Track();
        setData(entity);
        entity.setStartDate(new Date());
        entity.setEndDate(new Date());

        ProjectType projectType = new ProjectType();
        projectType.setId(20);
        setData(projectType);
        projectType.setCreationUser("test");
        projectType.setModificationUser("test");
        projectType.setName("name");
        manager.persist(projectType);

        List<ProjectType> projectTypes = new ArrayList<ProjectType>();
        projectTypes.add(projectType);
        entity.setProjectTypes(projectTypes);

        PointsCalculator pointsCalculator = new PointsCalculator();
        setData(pointsCalculator);
        pointsCalculator.setClassName("className");
        manager.persist(pointsCalculator);
        entity.setPointsCalculator(pointsCalculator);

        TrackStatus trackStatus = new TrackStatus();
        setData(trackStatus);
        manager.persist(trackStatus);
        entity.setTrackStatus(trackStatus);

        TrackType trackType = new TrackType();
        setData(trackType);
        manager.persist(trackType);
        entity.setTrackType(trackType);

        dao.createTrack(entity);
        assertTrue("The result is incorrect.", entity.getId() > 0);

        // add a project type
        ProjectType type = new ProjectType();
        type.setId(21);
        setData(type);
        type.setCreationUser("test");
        type.setModificationUser("test");
        type.setName("name");
        manager.persist(type);
        manager.getTransaction().commit();
        manager.getTransaction().begin();
        dao.addTrackProjectType(entity, type);
        manager.getTransaction().commit();
        manager.getTransaction().begin();

        Track result = dao.getTrack(entity.getId());
        assertEquals("The result is incorrect.", entity.getId(), result.getId());
        assertEquals("The result is incorrect.", entity.getCreationDate(),
                result.getCreationDate());
        assertEquals("The result is incorrect.", entity.getDescription(),
                result.getDescription());
        assertEquals("The result is incorrect.", entity.getModificationDate(),
                result.getModificationDate());

        try {
            assertEquals("The result is incorrect.", pointsCalculator.getId(),
                    entity.getPointsCalculator().getId());
            assertEquals("The result is incorrect.", trackStatus.getId(),
                    result.getTrackStatus().getId());
            assertEquals("The result is incorrect.", trackType.getId(), result
                    .getTrackType().getId());
        } finally {
            manager.remove(entity);
            manager.remove(projectType);
            manager.remove(type);
            manager.remove(pointsCalculator);
            manager.remove(trackStatus);
            manager.remove(trackType);
        }
    }
    /**
     * the accuracy test for the method removeTrackProjectType.
     * 
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void test_removeTrackProjectType() throws Exception {
        Track entity = new Track();
        setData(entity);
        entity.setStartDate(new Date());
        entity.setEndDate(new Date());

        ProjectType projectType = new ProjectType();
        projectType.setId(20);
        setData(projectType);
        projectType.setCreationUser("test");
        projectType.setModificationUser("test");
        projectType.setName("name");
        manager.persist(projectType);

        List<ProjectType> projectTypes = new ArrayList<ProjectType>();
        projectTypes.add(projectType);
        entity.setProjectTypes(projectTypes);

        PointsCalculator pointsCalculator = new PointsCalculator();
        setData(pointsCalculator);
        pointsCalculator.setClassName("className");
        manager.persist(pointsCalculator);
        entity.setPointsCalculator(pointsCalculator);

        TrackStatus trackStatus = new TrackStatus();
        setData(trackStatus);
        manager.persist(trackStatus);
        entity.setTrackStatus(trackStatus);

        TrackType trackType = new TrackType();
        setData(trackType);
        manager.persist(trackType);
        entity.setTrackType(trackType);

        dao.createTrack(entity);
        assertTrue("The result is incorrect.", entity.getId() > 0);
        dao.removeTrackProjectType(entity, projectType);
        manager.getTransaction().commit();
        manager.getTransaction().begin();
        try {
            

            Track result = dao.getTrack(entity.getId());
            assertEquals("The result is incorrect.", entity.getId(), result
                    .getId());
            assertEquals("The result is incorrect.", entity.getCreationDate(),
                    result.getCreationDate());
            assertEquals("The result is incorrect.", entity.getDescription(),
                    result.getDescription());
            assertEquals("The result is incorrect.", entity
                    .getModificationDate(), result.getModificationDate());

            assertEquals("The result is incorrect.", pointsCalculator.getId(),
                    entity.getPointsCalculator().getId());
            assertEquals("The result is incorrect.", trackStatus.getId(),
                    result.getTrackStatus().getId());
            assertEquals("The result is incorrect.", trackType.getId(), result
                    .getTrackType().getId());
        } finally {
            manager.remove(entity);
            manager.remove(projectType);
            manager.remove(pointsCalculator);
            manager.remove(trackStatus);
            manager.remove(trackType);
        }
    }
    /**
     * the accuracy test for the method createTrackType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testcreateTrackType() throws Exception {
        TrackType entity = new TrackType();
        setData(entity);
        dao.createTrackType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The TrackOperation id is " + entity.getId());

        TrackType result = dao.getTrackType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeTrackType(entity.getId());
    }
    /**
     * the accuracy test for the method updateTrackType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testupdateTrackType() throws Exception {
        TrackType entity = new TrackType();
        setData(entity);
        dao.createTrackType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The TrackType id is " + entity.getId());

        TrackType result = dao.getTrackType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateTrackType(entity);
        result = dao.getTrackType(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeTrackType(entity.getId());
    }

    /**
     * the accuracy test for the method removeTrackType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testremoveTrackType() throws Exception {
        TrackType entity = new TrackType();
        setData(entity);
        dao.createTrackType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The TrackType id is " + entity.getId());

        TrackType result = dao.getTrackType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        // it is removed.
        dao.removeTrackType(entity.getId());
    }

    /**
     * the accuracy test for the method getTrackType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetTrackType() throws Exception {
        TrackType entity = new TrackType();
        setData(entity);
        dao.createTrackType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The TrackType id is " + entity.getId());

        // get the result
        TrackType result = dao.getTrackType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateTrackType(entity);
        result = dao.getTrackType(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeTrackType(entity.getId());
    }

    /**
     * the accuracy test for the method getAllTrackTypes.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetAllTrackTypes()
        throws Exception {
        TrackType entity = new TrackType();
        setData(entity);
        dao.createTrackType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The TrackType id is " + entity.getId());

        // get the result
        List<TrackType> list = dao.getAllTrackTypes();
        assertEquals("The size should be 1", 1, list.size());

        TrackType result = list.get(0);
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateTrackType(entity);
        result = dao.getTrackType(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeTrackType(entity.getId());
    }
    /**
     * the accuracy test for the method createTrackStatus.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testcreateTrackStatus() throws Exception {
        TrackStatus entity = new TrackStatus();
        setData(entity);
        dao.createTrackStatus(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The TrackOperation id is " + entity.getId());

        TrackStatus result = dao.getTrackStatus(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeTrackStatus(entity.getId());
    }
    /**
     * the accuracy test for the method updateTrackStatus.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testupdateTrackStatus() throws Exception {
        TrackStatus entity = new TrackStatus();
        setData(entity);
        dao.createTrackStatus(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The TrackStatus id is " + entity.getId());

        TrackStatus result = dao.getTrackStatus(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateTrackStatus(entity);
        result = dao.getTrackStatus(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeTrackStatus(entity.getId());
    }

    /**
     * the accuracy test for the method removeTrackStatus.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testremoveTrackStatus() throws Exception {
        TrackStatus entity = new TrackStatus();
        setData(entity);
        dao.createTrackStatus(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The TrackStatus id is " + entity.getId());

        TrackStatus result = dao.getTrackStatus(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        // it is removed.
        dao.removeTrackStatus(entity.getId());
    }

    /**
     * the accuracy test for the method getTrackStatus.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetTrackStatus() throws Exception {
        TrackStatus entity = new TrackStatus();
        setData(entity);
        dao.createTrackStatus(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The TrackStatus id is " + entity.getId());

        // get the result
        TrackStatus result = dao.getTrackStatus(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateTrackStatus(entity);
        result = dao.getTrackStatus(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeTrackStatus(entity.getId());
    }

    /**
     * the accuracy test for the method getAllTrackStatuses.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetAllTrackStatuses()
        throws Exception {
        TrackStatus entity = new TrackStatus();
        setData(entity);
        dao.createTrackStatus(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The TrackStatus id is " + entity.getId());

        // get the result
        List<TrackStatus> list = dao.getAllTrackStatuses();
        assertEquals("The size should be 1", 1, list.size());

        TrackStatus result = list.get(0);
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateTrackStatus(entity);
        result = dao.getTrackStatus(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeTrackStatus(entity.getId());
    }
    /**
     * the accuracy test for the method createPointsCalculator.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testcreatePointsCalculator() throws Exception {
        PointsCalculator entity = new PointsCalculator();
        setData(entity);
        entity.setClassName("className");
        dao.createPointsCalculator(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsOperation id is " + entity.getId());

        PointsCalculator result = dao.getPointsCalculator(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removePointsCalculator(entity.getId());
    }
    /**
     * the accuracy test for the method updatePointsCalculator.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testupdatePointsCalculator() throws Exception {
        PointsCalculator entity = new PointsCalculator();
        setData(entity);
        entity.setClassName("className");
        dao.createPointsCalculator(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The PointsCalculator id is " + entity.getId());

        PointsCalculator result = dao.getPointsCalculator(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updatePointsCalculator(entity);
        result = dao.getPointsCalculator(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removePointsCalculator(entity.getId());
    }

    /**
     * the accuracy test for the method removePointsCalculator.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testremovePointsCalculator() throws Exception {
        PointsCalculator entity = new PointsCalculator();
        setData(entity);
        entity.setClassName("className");
        dao.createPointsCalculator(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The PointsCalculator id is " + entity.getId());

        PointsCalculator result = dao.getPointsCalculator(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        // it is removed.
        dao.removePointsCalculator(entity.getId());
    }

    /**
     * the accuracy test for the method getPointsCalculator.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetPointsCalculator() throws Exception {
        PointsCalculator entity = new PointsCalculator();
        setData(entity);
        entity.setClassName("className");
        dao.createPointsCalculator(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The PointsCalculator id is " + entity.getId());

        // get the result
        PointsCalculator result = dao.getPointsCalculator(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updatePointsCalculator(entity);
        result = dao.getPointsCalculator(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removePointsCalculator(entity.getId());
    }

    /**
     * the accuracy test for the method getAllPointsCalculators.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetAllPointsCalculators()
        throws Exception {
        PointsCalculator entity = new PointsCalculator();
        setData(entity);
        entity.setClassName("className");
        dao.createPointsCalculator(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The PointsCalculator id is " + entity.getId());

        // get the result
        List<PointsCalculator> list = dao.getAllPointsCalculators();
        assertEquals("The size should be 1", 1, list.size());

        PointsCalculator result = list.get(0);
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updatePointsCalculator(entity);
        result = dao.getPointsCalculator(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removePointsCalculator(entity.getId());
    }
    /**
     * The accuracy test for the method getProjectType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_getProjectType() throws Exception {
        ProjectType type1 = new ProjectType();
        type1.setId(10);
        type1.setCreationUser("creationUser");
        type1.setDescription("description");
        type1.setModificationUser("modificationUser");
        type1.setName("name");
        manager.persist(type1);
        ProjectType result = dao.getProjectType(type1.getId());
        assertNotNull("The result should not be null.", result);
        assertEquals("The result is incorrect.", type1.getId(), result.getId());
        assertEquals("The result is incorrect.", type1.getCreationDate(), result.getCreationDate());
        assertEquals("The result is incorrect.", type1.getCreationUser(), result.getCreationUser());
        assertEquals("The result is incorrect.", type1.getDescription(), result.getDescription());
        assertEquals("The result is incorrect.", type1.getModificationDate(), result.getModificationDate());
        manager.remove(type1);
    }

    /**
     * The accuracy test for the method getAllProjectTypes.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_getAllProjectTypes() throws Exception {
        ProjectType type1 = new ProjectType();
        type1.setId(10);
        type1.setCreationUser("creationUser");
        type1.setDescription("description");
        type1.setModificationUser("modificationUser");
        type1.setName("name");
        manager.persist(type1);
        List<ProjectType> list = dao.getAllProjectTypes();
        assertEquals("The size of list is incorrect.", 1, list.size());

        ProjectType result = list.get(0);
        assertNotNull("The result should not be null.", result);
        assertEquals("The result is incorrect.", type1.getId(), result.getId());
        assertEquals("The result is incorrect.", type1.getCreationDate(), result.getCreationDate());
        assertEquals("The result is incorrect.", type1.getCreationUser(), result.getCreationUser());
        assertEquals("The result is incorrect.", type1.getDescription(), result.getDescription());
        assertEquals("The result is incorrect.", type1.getModificationDate(), result.getModificationDate());
        manager.remove(type1);
    }

}
