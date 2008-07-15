/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.accuracytests;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.service.digitalrun.entity.BaseEntity;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsStatus;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsStatusDAO;


/**
 * The accuracy test for the class {@link JPADigitalRunPointsStatusDAO}.
 *
 * @author KLW
 * @version 1.0
 */
public class JPADigitalRunPointsStatusDAOAccTests extends TestCase {
    /**
     * The JPADigitalRunPointsStatusDAO instance for test.
     */
    private JPADigitalRunPointsStatusDAO dao;

    /**
     * sets up the test environment.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    protected void setUp() throws Exception {
        dao = new JPADigitalRunPointsStatusDAO();
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
     * set some data.
     */
    private void setData(BaseEntity entity) {
        entity.setCreationDate(new Date());
        entity.setDescription("description");
        entity.setModificationDate(new Date());
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
    public void testgetAllDigitalRunPointsStatuses()
        throws Exception {
        DigitalRunPointsStatus entity = new DigitalRunPointsStatus();
        setData(entity);
        dao.createDigitalRunPointsStatus(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsStatus id is " + entity.getId());

        // get the result
        List<DigitalRunPointsStatus> list = dao.getAllDigitalRunPointsStatuses();
        assertEquals("The size should be 1", 1, list.size());

        DigitalRunPointsStatus result = list.get(0);
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateDigitalRunPointsStatus(entity);
        result = dao.getDigitalRunPointsStatus(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsStatus(entity.getId());
    }

}
