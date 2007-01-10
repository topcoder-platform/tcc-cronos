/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.accuracytests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.project.Client;
import com.topcoder.timetracker.project.Project;


/**
 * <p>
 * This class contains the accuracy unit tests for Client.java.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class ClientTest extends TestCase {
    /**
     * <p>
     * Represents the Client instance for testing.
     * </p>
     */
    private Client client = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    protected void setUp() {
        client = new Client(1);
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(ClientTest.class);
    }

    /**
     * <p>
     * Tests constructor.
     * </p>
     */
    public void testConstructorAccuracy1() {
        client = new Client();
        assertNotNull("client was not properly created", client);
        assertEquals("client was not properly created", 0, client.getProjects().size());
        assertEquals("client was not properly created", -1, client.getId());
    }

    /**
     * <p>
     * Tests constructor.
     * </p>
     */
    public void testConstructorAccuracy2() {
        assertNotNull("client was not properly created", client);
        assertEquals("client was not properly created", 0, client.getProjects().size());
        assertEquals("client was not properly created", 1, client.getId());
    }

    /**
     * <p>
     * Tests constructor.
     * </p>
     */
    public void testConstructorAccuracy3() {
        List projects = new ArrayList();
        Project project = new Project();
        projects.add(project);
        client = new Client(1, projects);
        assertNotNull("client was not properly created", client);
        assertEquals("client was not properly created", 1, client.getId());
        assertEquals("client was not properly created", 1, client.getProjects().size());
    }

    /**
     * <p>
     * Tests constructor.
     * </p>
     */
    public void testConstructorAccuracy4() {
        List projects = new ArrayList();
        Project project = new Project();
        projects.add(project);
        projects.add(project);
        client = new Client(1, projects);
        assertNotNull("client was not properly created", client);
        assertEquals("client was not properly created", 1, client.getId());
        assertEquals("client was not properly created", 1, client.getProjects().size());
    }

    /**
     * <p>
     * Tests accuracy of getId() method.
     * </p>
     */
    public void testGetIdAccuracy() {
        assertEquals(1, client.getId());
    }

    /**
     * <p>
     * Tests accuracy of setId() method.
     * </p>
     */
    public void testSetIdAccuracy() {
        client.setId(2);
        assertEquals(2, client.getId());
    }

    /**
     * <p>
     * Tests accuracy of getCreationDate() method.
     * </p>
     */
    public void testGetCreationDateAccuracy() {
        assertNull(client.getCreationDate());
    }

    /**
     * <p>
     * Tests accuracy of setCreationDate() method.
     * </p>
     */
    public void testSetCreationDateAccuracy() {
        Date date = new Date();
        client.setCreationDate(date);
        assertEquals(date, client.getCreationDate());
    }

    /**
     * <p>
     * Tests accuracy of getCreationUser() method.
     * </p>
     */
    public void testGetCreationUserAccuracy() {
        assertNull(client.getCreationUser());
    }

    /**
     * <p>
     * Tests accuracy of setCreationUser() method.
     * </p>
     */
    public void testSetCreationUserAccuracy() {
        String user = "create";
        client.setCreationUser(user);
        assertEquals(user, client.getCreationUser());
    }

    /**
     * <p>
     * Tests accuracy of getModificationDate() method.
     * </p>
     */
    public void testGetModificationDateAccuracy() {
        assertNull(client.getModificationDate());
    }

    /**
     * <p>
     * Tests accuracy of setModificationDate() method.
     * </p>
     */
    public void testSetModificationDateAccuracy() {
        Date date = new Date();
        client.setModificationDate(date);
        assertEquals(date, client.getModificationDate());
    }

    /**
     * <p>
     * Tests accuracy of getModificationUser() method.
     * </p>
     */
    public void testGetModificationUserAccuracy() {
        assertNull(client.getModificationUser());
    }

    /**
     * <p>
     * Tests accuracy of setModificationUser() method.
     * </p>
     */
    public void testSetModificationUserAccuracy() {
        String user = "create";
        client.setModificationUser(user);
        assertEquals(user, client.getModificationUser());
    }

    /**
     * <p>
     * Tests accuracy of getName() method.
     * </p>
     */
    public void testGetNameAccuracy() {
        assertNull(client.getName());
    }

    /**
     * <p>
     * Tests accuracy of setName() method.
     * </p>
     */
    public void testSetNameAccuracy() {
        String user = "create";
        client.setName(user);
        assertEquals(user, client.getName());
    }

    /**
     * <p>
     * Tests accuracy of getProjects() method.
     * </p>
     */
    public void testGetProjectsAccuracy() {
        List projects = new ArrayList();
        Project project = new Project();
        projects.add(project);
        client = new Client(1, projects);
        assertEquals(1, client.getProjects().size());
        assertEquals(project, client.getProjects().get(0));
    }

    /**
     * <p>
     * Tests accuracy of setProjects() method.
     * </p>
     */
    public void testSetProjectsAccuracy() {
        List projects = new ArrayList();
        Project project = new Project();
        projects.add(project);
        client.setProjects(projects);
        assertEquals(1, client.getProjects().size());
        assertEquals(project, client.getProjects().get(0));
    }

    /**
     * <p>
     * Tests accuracy of addProject() method.
     * </p>
     */
    public void testAddProjectAccuracy() {
        Project project = new Project();
        assertTrue("project was not properly added", client.addProject(project));
        assertFalse("project should not be added", client.addProject(project));
    }

    /**
     * <p>
     * Tests accuracy of removeProject() method.
     * </p>
     */
    public void testRemoveProjectAccuracy() {
        Project project = new Project();
        assertFalse("There should be no project to remove", client.removeProject(project.getId()));
        client.addProject(project);
        assertTrue("prject was not properly removed", client.removeProject(project.getId()));
    }
}
