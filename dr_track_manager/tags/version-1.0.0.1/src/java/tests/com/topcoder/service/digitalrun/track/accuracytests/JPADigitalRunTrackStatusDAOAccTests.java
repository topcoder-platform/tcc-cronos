/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.accuracytests;

import java.util.Date;
import java.util.List;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

import com.topcoder.service.digitalrun.entity.BaseEntity;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackStatusDAO;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import junit.framework.TestCase;


/**
 * The accuracy test for the class {@link JPADigitalRunTrackStatusDAO}.
 *
 * @author KLW
 * @version 1.0
 */
public class JPADigitalRunTrackStatusDAOAccTests extends TestCase {
    /**
     * The JPADigitalRunTrackStatusDAO instance for test.
     */
    private JPADigitalRunTrackStatusDAO dao;
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
        dao = new JPADigitalRunTrackStatusDAO();
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
     * set some data.
     */
    private void setData(BaseEntity entity) {
        entity.setCreationDate(new Date());
        entity.setDescription("description");
        entity.setModificationDate(new Date());
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

}
