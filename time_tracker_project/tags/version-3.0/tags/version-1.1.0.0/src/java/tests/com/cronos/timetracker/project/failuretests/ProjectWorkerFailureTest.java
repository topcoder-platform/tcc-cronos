/*
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.cronos.timetracker.project.failuretests;

import java.math.BigDecimal;

import junit.framework.TestCase;

import com.cronos.timetracker.project.ProjectWorker;

/**
 * Failure tests for ProjectUtility implementation.
 * 
 * @author dmks
 * @version 1.0
 */
public class ProjectWorkerFailureTest extends TestCase {
    /**
     * The ProjectWorker instance to test against.
     */
    private ProjectWorker worker = null;

    /**
     * Prepares a ProjectWorker instance for testing.
     */
    public void setUp() {
        worker = new ProjectWorker();
    }

    /**
     * Test constructor with null project. Expects NullPointerException.
     *  
     */
    public void testConstructor() {
        try {
            new ProjectWorker(null, -1);
            fail("Constructs with null project");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Test of setProject method with null project. Expects
     * NullPointerException.
     */
    public void testSetProject_NullProject() {
        try {
            worker.setProject(null);
            fail("Sets null project");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setStartDate method with null date. Expects NullPointerException.
     */
    public void testSetStartDate_NullDate() {
        try {
            worker.setStartDate(null);
            fail("Sets null start date");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setEndDate method with null date. Expects NullPointerException.
     */
    public void testSetEndDate_NullDate() {
        try {
            worker.setEndDate(null);
            fail("Sets null end date");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setPayRate method with null pay rate. Expects
     * NullPointerException.
     */
    public void testSetPayRate_NullPayRate() {
        try {
            worker.setPayRate(null);
            fail("Sets null pay rate");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setPayRate method with invalid pay rate. Tests against values
     * less than 0. Expects IllegalArgumentException.
     */
    public void testSetPayRate_InvalidPayRate() {
        try {
            worker.setPayRate(new BigDecimal(-1));
            fail("Sets pay rate less than 0");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of setCreationDate method with null date. Expects
     * NullPointerException.
     */
    public void testSetCreationDate_NullDate() {
        try {
            worker.setCreationDate(null);
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
            worker.setCreationUser(null);
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
            worker.setCreationUser("");
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
            worker.setModificationDate(null);
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
            worker.setModificationUser(null);
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
            worker.setModificationUser("");
            fail("Sets empty modification user");
        } catch (IllegalArgumentException e) {
        }
    }
}
