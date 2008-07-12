/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.accuracytests;

import java.util.Date;
import java.util.List;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

import com.topcoder.service.digitalrun.entity.BaseEntity;
import com.topcoder.service.digitalrun.entity.TrackType;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackTypeDAO;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import junit.framework.TestCase;


/**
 * The accuracy test for the class {@link JPADigitalRunTrackTypeDAO}.
 *
 * @author KLW
 * @version 1.0
 */
public class JPADigitalRunTrackTypeDAOAccTests extends TestCase {
    /**
     * The JPADigitalRunTrackTypeDAO instance for test.
     */
    private JPADigitalRunTrackTypeDAO dao;
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
        dao = new JPADigitalRunTrackTypeDAO();
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
}
