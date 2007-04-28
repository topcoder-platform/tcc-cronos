/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.math.BigDecimal;

import java.util.Date;


/**
 * Unit tests for ProjectWorker implementation.
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
public class ProjectWorkerTest extends TestCase {
    /**
     * The ProjectWorker instance to test against.
     */
    private ProjectWorker worker = null;

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ProjectWorkerTest.class);

        return suite;
    }

    /**
     * Prepares a ProjectWorker instance for testing.
     */
    public void setUp() {
        worker = new ProjectWorker();
    }

    /**
     * Test of constructor with no argument. Verifies if the id is -1 and the project is null.
     */
    public void testConstructor_NoArg() {
        worker = new ProjectWorker();

        assertEquals("Fails to set id", -1, worker.getWorkerId());
        assertNull("Fails to set project", worker.getProject());
    }

    /**
     * Test of constructor with two arguments. Verifies it by getting the id and project being set.
     */
    public void testConstructor_TwoArgs() {
        Project project = new Project();
        int workerId = 10;

        worker = new ProjectWorker(project, workerId);

        assertEquals("Fails to set id", workerId, worker.getWorkerId());
        assertEquals("Fails to set project", project, worker.getProject());
    }

    /**
     * Test of setProject method with null project. Expects NullPointerException.
     */
    public void testSetProject_NullProject() {
        try {
            worker.setProject(null);
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

        worker.setProject(project);
        assertEquals("Returns an incorrect project", project, worker.getProject());
    }

    /**
     * Test of getWorkerId and setWorkerId methods. Verifies it by getting the worker id back after setting it.
     */
    public void testGetSetWorkerId() {
        int workerId = 10;

        worker.setWorkerId(workerId);
        assertEquals("Returns an incorrect worker id", workerId, worker.getWorkerId());
    }

    /**
     * Test of setStartDate method with null date. Expects NullPointerException.
     */
    public void testSetStartDate_NullDate() {
        try {
            worker.setStartDate(null);
            fail("Sets null start date");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * Test of getStartDate and setStartDate methods with non-null date. Verifies it by getting the date back after
     * setting it.
     */
    public void testGetSetStartDate_NonNullDate() {
        Date date = new Date();

        worker.setStartDate(date);
        assertEquals("Returns an incorrect start date", date, worker.getStartDate());
    }

    /**
     * Test of setEndDate method with null date. Expects NullPointerException.
     */
    public void testSetEndDate_NullDate() {
        try {
            worker.setEndDate(null);
            fail("Sets null end date");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * Test of getEndDate and setEndDate methods with non-null date. Verifies it by getting the date back after setting
     * it.
     */
    public void testGetSetEndDate_NonNullDate() {
        Date date = new Date();

        worker.setEndDate(date);
        assertEquals("Returns an incorrect end date", date, worker.getEndDate());
    }

    /**
     * Test of setPayRate method with null pay rate. Expects NullPointerException.
     */
    public void testSetPayRate_NullPayRate() {
        try {
            worker.setPayRate(null);
            fail("Sets null pay rate");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * Test of setPayRate method with invalid pay rate. Tests against values less than 0. Expects
     * IllegalArgumentException.
     */
    public void testSetPayRate_InvalidPayRate() {
        try {
            worker.setPayRate(new BigDecimal(-1));
            fail("Sets pay rate less than 0");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getPayRate and setPayRate methods with valid pay rate. Tests against 0 and positive values. Verifies it
     * by getting the pay rate back after setting it.
     */
    public void testGetSetPayRate_ValidPayRate() {
        // test against 0
        BigDecimal payRate = new BigDecimal(0);

        worker.setPayRate(payRate);
        assertEquals("Returns an incorrect pay rate", payRate, worker.getPayRate());

        // test against positive values
        payRate = new BigDecimal(10);

        worker.setPayRate(payRate);
        assertEquals("Returns an incorrect pay rate", payRate, worker.getPayRate());
    }

    /**
     * Test of setCreationDate method with null date. Expects NullPointerException.
     */
    public void testSetCreationDate_NullDate() {
        try {
            worker.setCreationDate(null);
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

        worker.setCreationDate(date);
        assertEquals("Returns an incorrect creation date", date, worker.getCreationDate());
    }

    /**
     * Test of setCreationUser method with null user. Expects NullPointerException.
     */
    public void testSetCreationUser_NullUser() {
        try {
            worker.setCreationUser(null);
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
            worker.setCreationUser("");
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

        worker.setCreationUser(user);
        assertEquals("Returns an incorrect creation user", user, worker.getCreationUser());
    }

    /**
     * Test of setModificationDate method with null date. Expects NullPointerException.
     */
    public void testSetModificationDate_NullDate() {
        try {
            worker.setModificationDate(null);
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

        worker.setModificationDate(date);
        assertEquals("Returns an incorrect modification date", date, worker.getModificationDate());
    }

    /**
     * Test of setModificationUser method with null user. Expects NullPointerException.
     */
    public void testSetModificationUser_NullUser() {
        try {
            worker.setModificationUser(null);
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
            worker.setModificationUser("");
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

        worker.setModificationUser(user);
        assertEquals("Returns an incorrect modification user", user, worker.getModificationUser());
    }
}
