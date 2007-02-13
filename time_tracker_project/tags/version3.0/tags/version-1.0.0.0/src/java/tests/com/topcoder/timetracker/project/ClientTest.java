/*
 * ClientTest.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Unit tests for Client implementation.
 * 
 * <p>
 * Here we assume that the constructors delegate the call to the proper setter methods, so that invalid arguments are
 * not tested in the constructors.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientTest extends TestCase {
    /**
     * The Client instance to test against.
     */
    private Client client = null;

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ClientTest.class);

        return suite;
    }

    /**
     * Prepares a Client instance for testing.
     */
    public void setUp() {
        client = new Client();
    }

    /**
     * Test of constructor with no argument. Verifies if the id is -1 and the project is empty.
     */
    public void testConstructor_NoArg() {
        client = new Client();

        assertEquals("Fails to set id", -1, client.getId());
        assertTrue("Fails to set projects", client.getProjects().isEmpty());
    }

    /**
     * Test of constructor with one argument. Verifies it by getting the id being set. Also verifies if the project is
     * empty.
     */
    public void testConstructor_OneArg() {
        int id = 10;

        client = new Client(id);

        assertEquals("Fails to set id", id, client.getId());
        assertTrue("Fails to set projects", client.getProjects().isEmpty());
    }

    /**
     * Test of constructor with two arguments. Verifies it by getting the id and projects being set.
     */
    public void testConstructor_TwoArgs() {
        int id = 10;
        List projects = new ArrayList();

        for (int i = 0; i < 3; i++) {
            projects.add(new Project(i));
        }

        client = new Client(id, projects);

        List results = client.getProjects();

        assertEquals("Fails to set id", id, client.getId());
        for (Iterator i = projects.iterator(); i.hasNext();) {
            assertTrue("Fails to set projects", results.remove(i.next()));
        }
        assertTrue("Fails to set projects", results.isEmpty());
    }

    /**
     * Test of getId and setId methods. Verifies it by getting the id back after setting it.
     */
    public void testGetSetId() {
        int id = 10;

        client.setId(id);
        assertEquals("Returns an incorrect id", id, client.getId());
    }

    /**
     * Test of setName method with null name. Expects NullPointerException.
     */
    public void testSetName_NullName() {
        try {
            client.setName(null);
            fail("Sets null name");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of setName method with empty name. Expects IllegalArgumentException.
     */
    public void testSetName_EmptyName() {
        try {
            client.setName("");
            fail("Sets empty name");
        } catch (IllegalArgumentException e) {}
    }

    /**
     * Test of setName method with non-empty name. Verifies it by getting the name back after setting it.
     */
    public void testGetSetName_NonEmptyName() {
        String name = "name";

        client.setName(name);
        assertEquals("Returns an incorrect name", name, client.getName());
    }

    /**
     * Test of setCreationDate method with null date. Expects NullPointerException.
     */
    public void testSetCreationDate_NullDate() {
        try {
            client.setCreationDate(null);
            fail("Sets null creation date");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of getCreationDate and setCreationDate methods with non-null date. Verifies it by getting the date back
     * after setting it.
     */
    public void testGetSetCreationDate_NonNullDate() {
        Date date = new Date();

        client.setCreationDate(date);
        assertEquals("Returns an incorrect creation date", date, client.getCreationDate());
    }

    /**
     * Test of setCreationUser method with null user. Expects NullPointerException.
     */
    public void testSetCreationUser_NullUser() {
        try {
            client.setCreationUser(null);
            fail("Sets null creation user");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of setCreationUser method with empty user. Expects IllegalArgumentException.
     */
    public void testSetCreationUser_EmptyUser() {
        try {
            client.setCreationUser("");
            fail("Sets empty creation user");
        } catch (IllegalArgumentException e) {}
    }

    /**
     * Test of getCreationUser and setCreationUser methods with non-empty user. Verifies it by getting the user back
     * after setting it.
     */
    public void testGetSetCreationUser_NonEmptyUser() {
        String user = "user";

        client.setCreationUser(user);
        assertEquals("Returns an incorrect creation user", user, client.getCreationUser());
    }

    /**
     * Test of setModificationDate method with null date. Expects NullPointerException.
     */
    public void testSetModificationDate_NullDate() {
        try {
            client.setModificationDate(null);
            fail("Sets null modification date");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of getModificationDate and setModificationDate methods with non-null date. Verifies it by getting the date
     * back after setting it.
     */
    public void testGetSetModificationDate_NonNullDate() {
        Date date = new Date();

        client.setModificationDate(date);
        assertEquals("Returns an incorrect modification date", date, client.getModificationDate());
    }

    /**
     * Test of setModificationUser method with null user. Expects NullPointerException.
     */
    public void testSetModificationUser_NullUser() {
        try {
            client.setModificationUser(null);
            fail("Sets null modification user");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of setModificationUser method with empty user. Expects IllegalArgumentException.
     */
    public void testSetModificationUser_EmptyUser() {
        try {
            client.setModificationUser("");
            fail("Sets empty modification user");
        } catch (IllegalArgumentException e) {}
    }

    /**
     * Test of getModificationUser and setModificationUser methods with non-empty user. Verifies it by getting the user
     * back after setting it.
     */
    public void testGetSetModificationUser_NonEmptyUser() {
        String user = "user";

        client.setModificationUser(user);
        assertEquals("Returns an incorrect modification user", user, client.getModificationUser());
    }

    /**
     * Test of setProjects method with null projects. Expects NullPointerException.
     */
    public void testSetProjects_NullProjects() {
        try {
            client.setProjects(null);
            fail("Sets null projects");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of setProjects method with invalid projects containing null or non-Project element. Expects
     * IllegalArgumentException.
     */
    public void testSetProjects_InvalidProjects() {
        List projects = new ArrayList();

        // try null element
        projects.add(null);
        try {
            client.setProjects(projects);
            fail("Sets projects containing null element");
        } catch (IllegalArgumentException e) {}

        // try non-Project element
        projects.clear();
        projects.add("String");
        try {
            client.setProjects(projects);
            fail("Sets projects containing non-Project element");
        } catch (IllegalArgumentException e) {}
    }

    /**
     * Test of getProjects and setProjects methods with valid projects containing Project elements. Verifies it by
     * getting the projects back after setting it.
     */
    public void testSetProjects_ValidProjects() {
        List projects = new ArrayList();

        for (int i = 0; i < 3; i++) {
            projects.add(new Project(i));
        }

        client.setProjects(projects);

        List results = client.getProjects();

        for (Iterator i = projects.iterator(); i.hasNext();) {
            assertTrue("Returns an incorrect list of projects", results.remove(i.next()));
        }
        assertTrue("Returns an incorrect list of projects", results.isEmpty());
    }

    /**
     * Test of addProject method with null project. Expects NullPointerException.
     */
    public void testAddProject_NullProject() {
        try {
            client.addProject(null);
            fail("Adds null project");
        } catch (NullPointerException e) {}
    }

    /**
     * Test of addProject method with an existing project. Verifies if false is returned.
     */
    public void testAddProject_ExistProject() {
        Project project = new Project();

        client.addProject(project);

        assertFalse("Adds an existing project", client.addProject(project));
    }

    /**
     * Test of addProject method with a non-existing project. Verifies if true is returned. Also verifies if the
     * project was added to the client.
     */
    public void testAddProject_NonExistProject() {
        Project project = new Project();

        assertTrue("Fails to add non-existing project", client.addProject(project));
        assertTrue("Fails to add non-existing project", client.getProjects().contains(project));
    }

    /**
     * Test of removeProject method with a non-existing project. Verifies if false is returned.
     */
    public void testRemoveProject_NonExistProject() {
        int projectId = 10;

        assertFalse("Removes non-existing project", client.removeProject(projectId));
    }

    /**
     * Test of removeProject method with an existing project. Verifies if true is returned. Also verifies if the
     * project was removed from the client.
     */
    public void testRemoveProject_ExistProject() {
        int projectId = 10;
        Project project = new Project(projectId);

        client.addProject(project);

        assertTrue("Fails to remove an existing project", client.removeProject(projectId));
        assertFalse("Fails to remove an existing project", client.getProjects().contains(project));
    }
}
