/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.accuracytests;


import java.util.Date;
import java.util.List;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunPointsCalculatorDAO;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import junit.framework.TestCase;


/**
 * The accuracy test for the class {@link JPADigitalRunPointsCalculatorDAO}.
 *
 * @author KLW
 * @version 1.0
 */
public class JPADigitalRunPointsCalculatorDAOAccTests extends TestCase {
    /**
     * The JPADigitalRunPointsCalculatorDAO instance for test.
     */
    private JPADigitalRunPointsCalculatorDAO dao;
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
        dao = new JPADigitalRunPointsCalculatorDAO();
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
    private void setData(PointsCalculator entity) {
        entity.setCreationDate(new Date());
        entity.setDescription("description");
        entity.setModificationDate(new Date());
        entity.setClassName("className");
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
}
