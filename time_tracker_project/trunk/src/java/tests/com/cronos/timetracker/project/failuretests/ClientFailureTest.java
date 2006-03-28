/*
 * ClientFailureTest.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.cronos.timetracker.project.failuretests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.cronos.timetracker.project.Client;
import com.cronos.timetracker.project.Project;


/**
 * Failure tests for Client implementation.
 * 
 * <p>
 * Here we assume that the constructors delegate the call to the proper setter methods, so that invalid arguments are
 * not tested in the constructors.
 * </p>
 *
 * @author dmks
 * @version 1.0
 */
public class ClientFailureTest extends TestCase {

    /**
     * The Client instance to test against.
     */
    private Client client = null;

    /**
     * Prepares a Client instance for testing.
     */
    public void setUp() {
        client = new Client();
    }
    
    /**
     * Test constructor with illegal projects list. Expects IllegalArgumentException.
     *
     */
    public void testConstructor1() {
        try {
            new Client(1, Arrays.asList(new Project[]{null, null}));
        } catch (IllegalArgumentException e) {            
        }
    }
    
    /**
     * Test constructor with illegal projects list. Expects IllegalArgumentException.
     *
     */
    public void testConstructor2() {
        try {
            new Client(1, Arrays.asList(new Object[]{new Project(), new Object()}));
        } catch (IllegalArgumentException e) {            
        }
    }
    
    /**
     * Test of setName method with null name. Expects NullPointerException.
     */
    public void testSetName_NullName() {
        try {
            client.setName(null);
            fail("Sets null name");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setName method with empty name. Expects IllegalArgumentException.
     */
    public void testSetName_EmptyName() {
        try {
            client.setName("");
            fail("Sets empty name");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of setCreationDate method with null date. Expects NullPointerException.
     */
    public void testSetCreationDate_NullDate() {
        try {
            client.setCreationDate(null);
            fail("Sets null creation date");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setCreationUser method with null user. Expects NullPointerException.
     */
    public void testSetCreationUser_NullUser() {
        try {
            client.setCreationUser(null);
            fail("Sets null creation user");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setCreationUser method with empty user. Expects IllegalArgumentException.
     */
    public void testSetCreationUser_EmptyUser() {
        try {
            client.setCreationUser("");
            fail("Sets empty creation user");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of setModificationDate method with null date. Expects NullPointerException.
     */
    public void testSetModificationDate_NullDate() {
        try {
            client.setModificationDate(null);
            fail("Sets null modification date");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setModificationUser method with null user. Expects NullPointerException.
     */
    public void testSetModificationUser_NullUser() {
        try {
            client.setModificationUser(null);
            fail("Sets null modification user");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setModificationUser method with empty user. Expects IllegalArgumentException.
     */
    public void testSetModificationUser_EmptyUser() {
        try {
            client.setModificationUser("");
            fail("Sets empty modification user");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of setProjects method with null projects. Expects NullPointerException.
     */
    public void testSetProjects_NullProjects() {
        try {
            client.setProjects(null);
            fail("Sets null projects");
        } catch (NullPointerException e) {
        }
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
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of addProject method with null project. Expects NullPointerException.
     */
    public void testAddProject_NullProject() {
        try {
            client.addProject(null);
            fail("Adds null project");
        } catch (NullPointerException e) {
        }
    }
}
