/*
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.cronos.timetracker.project.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.project.ProjectManager;

/**
 * Failure tests for ProjectManager implementation.
 * 
 * @author dmks
 * @version 1.0
 */
public class ProjectManagerFailureTest extends TestCase {
    /**
     * The ProjectManager instance to test against.
     */
    private ProjectManager manager = null;

    /**
     * Prepares a ProjectManager instance for testing.
     */
    public void setUp() {
        manager = new ProjectManager();
    }

    /**
     * Test constructor with null project. Expects NullPointerException.
     *  
     */
    public void testConstructor() {
        try {
            new ProjectManager(null, -1);
            fail("Construct will null project");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Test of setProject method with null project. Expects
     * NullPointerException.
     */
    public void testSetProject_NullProject() {
        try {
            manager.setProject(null);
            fail("Sets null project");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setCreationDate method with null date. Expects
     * NullPointerException.
     */
    public void testSetCreationDate_NullDate() {
        try {
            manager.setCreationDate(null);
            fail("Sets null creation date");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setCreationUser method with null user. Expects
     * NullPointerException.
     */
    public void testSetCreationUser_NullUser() {
        try {
            manager.setCreationUser(null);
            fail("Sets null creation user");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setCreationUser method with empty user. Expects
     * IllegalArgumentException.
     */
    public void testSetCreationUser_EmptyUser() {
        try {
            manager.setCreationUser("\t");
            fail("Sets empty creation user");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of setModificationDate method with null date. Expects
     * NullPointerException.
     */
    public void testSetModificationDate_NullDate() {
        try {
            manager.setModificationDate(null);
            fail("Sets null modification date");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setModificationUser method with null user. Expects
     * NullPointerException.
     */
    public void testSetModificationUser_NullUser() {
        try {
            manager.setModificationUser(null);
            fail("Sets null modification user");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setModificationUser method with empty user. Expects
     * IllegalArgumentException.
     */
    public void testSetModificationUser_EmptyUser() {
        try {
            manager.setModificationUser("");
            fail("Sets empty modification user");
        } catch (IllegalArgumentException e) {
        }
    }
}
