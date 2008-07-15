/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.accuracytests;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.service.digitalrun.entity.DigitalRunPoints;
import com.topcoder.service.digitalrun.points.TestHelper;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsDAO;
import com.topcoder.util.config.ConfigManager;


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
        AccuracyTestHelper.clearDatabase();
    }

    /**
     * tear down the test environment.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    protected void tearDown() throws Exception {
        dao = null;
        AccuracyTestHelper.clearDatabase();
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
        AccuracyTestHelper.setPoints(entity);
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
        AccuracyTestHelper.setPoints(entity);

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
     * the accuracy test for the method removeDigitalRunPoints.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testremoveDigitalRunPoints() throws Exception {
        DigitalRunPoints entity = new DigitalRunPoints();
        AccuracyTestHelper.setPoints(entity);

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
        AccuracyTestHelper.setPoints(entity);

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

}
