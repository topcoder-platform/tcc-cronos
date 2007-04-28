/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;


/**
 * Unit tests for ProjectManager implementation.
 *
 * <p>
 * Here we assume that the constructors delegate the call to the proper setter methods, so that invalid arguments are
 * not tested in the constructors.
 * </p>
 *
 * @author colau
 * @version 1.1
 *
 * @since 1.0
 */
public class ProjectManagerTest extends TestCase {
    /**
     * The ProjectManager instance to test against.
     */
    private ProjectManager manager = null;

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ProjectManagerTest.class);

        return suite;
    }

    /**
     * Prepares a ProjectManager instance for testing.
     */
    public void setUp() {
        manager = new ProjectManager();
    }

    /**
     * Test of constructor with no argument. Verifies if the id is -1 and the project is null.
     */
    public void testConstructor_NoArg() {
        manager = new ProjectManager();

        assertEquals("Fails to set id", -1, manager.getManagerId());
        assertNull("Fails to set project", manager.getProject());
    }

    /**
     * Test of constructor with two arguments. Verifies it by getting the id and project being set.
     */
    public void testConstructor_TwoArgs() {
        Project project = new Project();
        int managerId = 10;

        manager = new ProjectManager(project, managerId);

        assertEquals("Fails to set id", managerId, manager.getManagerId());
        assertEquals("Fails to set project", project, manager.getProject());
    }

    /**
     * Test of setProject method with null project. Expects NullPointerException.
     */
    public void testSetProject_NullProject() {
        try {
            manager.setProject(null);
            fail("Sets null project");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * Test of getProject and setProject methods with non-null project. Verifies it by getting the project back after
     * setting it.
     */
    public void testGetSetProject_NonNullProject() {
        Project project = new Project();

        manager.setProject(project);
        assertEquals("Returns an incorrect project", project, manager.getProject());
    }

    /**
     * Test of getManagerId and setManagerId methods. Verifies it by getting the manager id back after setting it.
     */
    public void testGetSetManagerId() {
        int managerId = 10;

        manager.setManagerId(managerId);
        assertEquals("Returns an incorrect manager id", managerId, manager.getManagerId());
    }

    /**
     * Test of setCreationDate method with null date. Expects NullPointerException.
     */
    public void testSetCreationDate_NullDate() {
        try {
            manager.setCreationDate(null);
            fail("Sets null creation date");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * Test of getCreationDate and setCreationDate methods with non-null date. Verifies it by getting the date back
     * after setting it.
     */
    public void testGetSetCreationDate_NonNullDate() {
        Date date = new Date();

        manager.setCreationDate(date);
        assertEquals("Returns an incorrect creation date", date, manager.getCreationDate());
    }

    /**
     * Test of setCreationUser method with null user. Expects NullPointerException.
     */
    public void testSetCreationUser_NullUser() {
        try {
            manager.setCreationUser(null);
            fail("Sets null creation user");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * Test of setCreationUser method with empty user. Expects IllegalArgumentException.
     */
    public void testSetCreationUser_EmptyUser() {
        try {
            manager.setCreationUser("");
            fail("Sets empty creation user");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getCreationUser and setCreationUser methods with non-empty user. Verifies it by getting the user back
     * after setting it.
     */
    public void testGetSetCreationUser_NonEmptyUser() {
        String user = "user";

        manager.setCreationUser(user);
        assertEquals("Returns an incorrect creation user", user, manager.getCreationUser());
    }

    /**
     * Test of setModificationDate method with null date. Expects NullPointerException.
     */
    public void testSetModificationDate_NullDate() {
        try {
            manager.setModificationDate(null);
            fail("Sets null modification date");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * Test of getModificationDate and setModificationDate methods with non-null date. Verifies it by getting the date
     * back after setting it.
     */
    public void testGetSetModificationDate_NonNullDate() {
        Date date = new Date();

        manager.setModificationDate(date);
        assertEquals("Returns an incorrect modification date", date, manager.getModificationDate());
    }

    /**
     * Test of setModificationUser method with null user. Expects NullPointerException.
     */
    public void testSetModificationUser_NullUser() {
        try {
            manager.setModificationUser(null);
            fail("Sets null modification user");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * Test of setModificationUser method with empty user. Expects IllegalArgumentException.
     */
    public void testSetModificationUser_EmptyUser() {
        try {
            manager.setModificationUser("");
            fail("Sets empty modification user");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getModificationUser and setModificationUser methods with non-empty user. Verifies it by getting the user
     * back after setting it.
     */
    public void testGetSetModificationUser_NonEmptyUser() {
        String user = "user";

        manager.setModificationUser(user);
        assertEquals("Returns an incorrect modification user", user, manager.getModificationUser());
    }
}
