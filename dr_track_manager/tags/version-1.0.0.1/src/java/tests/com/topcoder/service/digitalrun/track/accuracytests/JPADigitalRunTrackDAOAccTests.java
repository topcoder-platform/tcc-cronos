/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.accuracytests;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.service.digitalrun.entity.BaseEntity;
import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackDAO;

import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.SessionContext;

import javax.persistence.EntityManager;

/**
 * The accuracy test for the class {@link JPADigitalRunTrackDAO}.
 * 
 * @author KLW
 * @version 1.0
 */
public class JPADigitalRunTrackDAOAccTests extends TestCase {
    /**
     * The JPADigitalRunTrackDAO instance for test.
     */
    private JPADigitalRunTrackDAO dao;

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
        dao = new JPADigitalRunTrackDAO();

        Log logger = LogManager.getLog();
        dao.setLogger(logger);

        SessionContext context = new MockSessionContext();
        manager = (EntityManager) context.lookup("entitManager");
        dao.setSessionContext(new MockSessionContext());
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
     * the accuracy test for the method setActiveStatusId.
     * 
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void test_setActiveStatusId() {
        dao.setActiveStatusId(23L);

        // no exception
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
            dao.setActiveStatusId(trackStatus.getId());

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
     * the accuracy test for the method setSearchBundle.
     * 
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void test_setSearchBundle() {
        SearchBundle searchBundle = new SearchBundle("name", new HashMap(), "context");
        dao.setSearchBundle(searchBundle);
    }
}
