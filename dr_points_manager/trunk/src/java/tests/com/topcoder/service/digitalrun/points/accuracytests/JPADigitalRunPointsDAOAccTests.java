/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.accuracytests;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;

import com.topcoder.service.digitalrun.entity.BaseEntity;
import com.topcoder.service.digitalrun.entity.DigitalRunPoints;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsOperation;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsReferenceType;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsStatus;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsType;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsDAO;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.io.File;

import java.util.Date;


/**
 * The accuracy test for the class {@link JPADigitalRunPointsDAO}.
 *
 * @author KLW
 * @version 1.0
 */
public class JPADigitalRunPointsDAOAccTests extends TestCase {
    /**
     * The JPADigitalRunPointsDAO instance for test.
     */
    private JPADigitalRunPointsDAO dao;

    /**
     * sets up the test environment.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    protected void setUp() throws Exception {
        dao = new JPADigitalRunPointsDAO();
        dao.setSessionContext(new MockSessionContext());
    }

    /**
     * tear down the test environment.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    protected void tearDown() throws Exception {
        dao = null;
    }

    /**
     * the accuracy test for the constructor.
     */
    public void testCtor() {
        assertNotNull("The instance should not be null.", dao);
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
     * the accuracy test for the method setSearchBundle.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testsetSearchBundle() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(new File("test_files/accuracy/config.xml").getCanonicalPath());
        cm.add(new File("test_files/accuracy/dbSearchStrategyConfig.xml").getCanonicalPath());

        // set the search bundle.
        SearchBundleManager manager = new SearchBundleManager("com.topcoder.search.builder");
        SearchBundle s = manager.getSearchBundle("FirstSearchBundle");
        dao.setSearchBundle(s);
        // no exception
        cm.removeNamespace("com.topcoder.search.builder");
        cm.removeNamespace("com.topcoder.search.builder.validator.factory");
        cm.removeNamespace("com.topcoder.search.builder.strategy.factory");
        cm.removeNamespace("DBSearchStrategy");
    }
}
