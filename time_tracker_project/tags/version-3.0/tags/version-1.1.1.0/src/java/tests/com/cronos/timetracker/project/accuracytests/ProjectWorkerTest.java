/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.accuracytests;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.timetracker.project.Project;
import com.cronos.timetracker.project.ProjectWorker;


/**
 * <p>
 * This class contains the accuracy unit tests for ProjectWorker.java.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class ProjectWorkerTest extends TestCase {
    /**
     * <p>
     * Represents the ProjectWorker instance for testing.
     * </p>
     */
    private ProjectWorker worker = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    protected void setUp() {
        worker = new ProjectWorker();
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(ProjectWorkerTest.class);
    }

    /**
     * <p>
     * Tests constructor.
     * </p>
     */
    public void testConstructorAccuracy1() {
        assertNotNull(worker);
    }

    /**
     * <p>
     * Tests constructor.
     * </p>
     */
    public void testConstructorAccuracy2() {
        Project project = new Project();
        worker = new ProjectWorker(project, 1);
        assertNotNull(worker);
    }

    /**
     * <p>
     * Tests accuracy of getProject() method.
     * </p>
     */
    public void testGetProjectAccuracy() {
        assertNull(worker.getProject());
    }

    /**
     * <p>
     * Tests accuracy of setProject() method.
     * </p>
     */
    public void testSetProjectAccuracy() {
        Project project = new Project();
        worker.setProject(project);
        assertEquals(project, worker.getProject());
    }

    /**
     * <p>
     * Tests accuracy of getWorker() method.
     * </p>
     */
    public void testGetWorkerAccuracy() {
        assertEquals(-1, worker.getWorkerId());
    }

    /**
     * <p>
     * Tests accuracy of setWorker() method.
     * </p>
     */
    public void testSetWorkerAccuracy() {
        worker.setWorkerId(2);
        assertEquals(2, worker.getWorkerId());
    }

    /**
     * <p>
     * Tests accuracy of getStartDate() method.
     * </p>
     */
    public void testGetStartDateAccuracy() {
        assertNull(worker.getStartDate());
    }

    /**
     * <p>
     * Tests accuracy of setStartDate() method.
     * </p>
     */
    public void testSetStartDateAccuracy() {
        Date date = new Date();
        worker.setStartDate(date);
        assertEquals(date, worker.getStartDate());
    }

    /**
     * <p>
     * Tests accuracy of getEndDate() method.
     * </p>
     */
    public void testGetEndDateAccuracy() {
        assertNull(worker.getEndDate());
    }

    /**
     * <p>
     * Tests accuracy of setEndDate() method.
     * </p>
     */
    public void testSetEndDateAccuracy() {
        Date date = new Date();
        worker.setEndDate(date);
        assertEquals(date, worker.getEndDate());
    }

    /**
     * <p>
     * Tests accuracy of getPayRate() method.
     * </p>
     */
    public void testGetPayRateAccuracy() {
        assertNull(worker.getPayRate());
    }

    /**
     * <p>
     * Tests accuracy of setPayRate() method.
     * </p>
     */
    public void testSetPayRateAccuracy() {
        BigDecimal rate = new BigDecimal("10");
        worker.setPayRate(rate);
        assertEquals(rate, worker.getPayRate());
    }

    /**
     * <p>
     * Tests accuracy of getCreationDate() method.
     * </p>
     */
    public void testGetCreationDateAccuracy() {
        assertNull(worker.getCreationDate());
    }

    /**
     * <p>
     * Tests accuracy of setCreationDate() method.
     * </p>
     */
    public void testSetCreationDateAccuracy() {
        Date date = new Date();
        worker.setCreationDate(date);
        assertEquals(date, worker.getCreationDate());
    }

    /**
     * <p>
     * Tests accuracy of getCreationUser() method.
     * </p>
     */
    public void testGetCreationUserAccuracy() {
        assertNull(worker.getCreationUser());
    }

    /**
     * <p>
     * Tests accuracy of setCreationUser() method.
     * </p>
     */
    public void testSetCreationUserAccuracy() {
        String user = "create";
        worker.setCreationUser(user);
        assertEquals(user, worker.getCreationUser());
    }

    /**
     * <p>
     * Tests accuracy of getModificationDate() method.
     * </p>
     */
    public void testGetModificationDateAccuracy() {
        assertNull(worker.getModificationDate());
    }

    /**
     * <p>
     * Tests accuracy of setModificationDate() method.
     * </p>
     */
    public void testSetModificationDateAccuracy() {
        Date date = new Date();
        worker.setModificationDate(date);
        assertEquals(date, worker.getModificationDate());
    }

    /**
     * <p>
     * Tests accuracy of getModificationUser() method.
     * </p>
     */
    public void testGetModificationUserAccuracy() {
        assertNull(worker.getModificationUser());
    }

    /**
     * <p>
     * Tests accuracy of setModificationUser() method.
     * </p>
     */
    public void testSetModificationUserAccuracy() {
        String user = "create";
        worker.setModificationUser(user);
        assertEquals(user, worker.getModificationUser());
    }
}
