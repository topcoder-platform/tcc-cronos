/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.accuracytests;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.timetracker.project.Project;
import com.cronos.timetracker.project.ProjectManager;


/**
 * <p>
 * This class contains the accuracy unit tests for ProjectManager.java.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class ProjectManagerTest extends TestCase {
    /**
     * <p>
     * Represents the ProjectManager instance for testing.
     * </p>
     */
    private ProjectManager manager = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    protected void setUp() {
        manager = new ProjectManager();
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(ProjectManagerTest.class);
    }

    /**
     * <p>
     * Tests constructor.
     * </p>
     */
    public void testConstructorAccuracy1() {
        assertNotNull(manager);
    }

    /**
     * <p>
     * Tests constructor.
     * </p>
     */
    public void testConstructorAccuracy2() {
        Project project = new Project();
        manager = new ProjectManager(project, 1);
        assertNotNull(manager);
    }

    /**
     * <p>
     * Tests accuracy of getProject() method.
     * </p>
     */
    public void testGetProjectAccuracy() {
        assertNull(manager.getProject());
    }

    /**
     * <p>
     * Tests accuracy of setProject() method.
     * </p>
     */
    public void testSetProjectAccuracy() {
        Project project = new Project();
        manager.setProject(project);
        assertEquals(project, manager.getProject());
    }

    /**
     * <p>
     * Tests accuracy of getManger() method.
     * </p>
     */
    public void testGetMangerAccuracy() {
        assertEquals(-1, manager.getManagerId());
    }

    /**
     * <p>
     * Tests accuracy of setManger() method.
     * </p>
     */
    public void testSetMangerAccuracy() {
        manager.setManagerId(2);
        assertEquals(2, manager.getManagerId());
    }

    /**
     * <p>
     * Tests accuracy of getCreationDate() method.
     * </p>
     */
    public void testGetCreationDateAccuracy() {
        assertNull(manager.getCreationDate());
    }

    /**
     * <p>
     * Tests accuracy of setCreationDate() method.
     * </p>
     */
    public void testSetCreationDateAccuracy() {
        Date date = new Date();
        manager.setCreationDate(date);
        assertEquals(date, manager.getCreationDate());
    }

    /**
     * <p>
     * Tests accuracy of getCreationUser() method.
     * </p>
     */
    public void testGetCreationUserAccuracy() {
        assertNull(manager.getCreationUser());
    }

    /**
     * <p>
     * Tests accuracy of setCreationUser() method.
     * </p>
     */
    public void testSetCreationUserAccuracy() {
        String user = "create";
        manager.setCreationUser(user);
        assertEquals(user, manager.getCreationUser());
    }

    /**
     * <p>
     * Tests accuracy of getModificationDate() method.
     * </p>
     */
    public void testGetModificationDateAccuracy() {
        assertNull(manager.getModificationDate());
    }

    /**
     * <p>
     * Tests accuracy of setModificationDate() method.
     * </p>
     */
    public void testSetModificationDateAccuracy() {
        Date date = new Date();
        manager.setModificationDate(date);
        assertEquals(date, manager.getModificationDate());
    }

    /**
     * <p>
     * Tests accuracy of getModificationUser() method.
     * </p>
     */
    public void testGetModificationUserAccuracy() {
        assertNull(manager.getModificationUser());
    }

    /**
     * <p>
     * Tests accuracy of setModificationUser() method.
     * </p>
     */
    public void testSetModificationUserAccuracy() {
        String user = "create";
        manager.setModificationUser(user);
        assertEquals(user, manager.getModificationUser());
    }
}
