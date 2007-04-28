/*
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project.failuretests;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.timetracker.project.Project;

/**
 * Failure tests for Project implementation.
 * 
 * @author dmks
 * @version 1.0
 */
public class ProjectFailureTest extends TestCase {
    /**
     * The Project instance to test against.
     */
    private Project project = null;

    /**
     * Prepares a Project instance for testing.
     */
    public void setUp() {
        project = new Project();
    }

    /**
     * Test of constructor with null workers ids. Expects NullPointerException.
     *  
     */
    public void testConstructor1() {
        try {
            new Project(-1, -1, null);
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Test of constructor with non-Integer workers ids. Expects
     * IllegalArgumentException.
     *  
     */
    public void testConstructor2() {
        Set ids = new HashSet();
        ids.add(new Object());
        try {
            new Project(-1, -1, ids);
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Test of setDescription method with null description. Expects
     * NullPointerException.
     */
    public void testSetDescription_NullDescription() {
        try {
            project.setDescription(null);
            fail("Sets null description");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setDescription method with empty description. Expects
     * IllegalArgumentException.
     */
    public void testSetDescription_EmptyDescription() {
        try {
            project.setDescription("");
            fail("Sets empty description");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of setName method with null name. Expects NullPointerException.
     */
    public void testSetName_NullName() {
        try {
            project.setName(null);
            fail("Sets null name");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setName method with empty name. Expects IllegalArgumentException.
     */
    public void testSetName_EmptyName() {
        try {
            project.setName(" ");
            fail("Sets empty name");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of setStartDate method with null date. Expects NullPointerException.
     */
    public void testSetStartDate_NullDate() {
        try {
            project.setStartDate(null);
            fail("Sets null start date");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of getStartDate and setStartDate methods with non-null date.
     * Verifies it by getting the date back after setting it.
     */
    public void testGetSetStartDate_NonNullDate() {
        Date date = new Date();

        project.setStartDate(date);
        assertEquals("Returns an incorrect start date", date, project.getStartDate());
    }

    /**
     * Test of setEndDate method with null date. Expects NullPointerException.
     */
    public void testSetEndDate_NullDate() {
        try {
            project.setEndDate(null);
            fail("Sets null end date");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setWorkersIds method with null ids. Expects NullPointerException.
     */
    public void testSetWorkersIds_NullIds() {
        try {
            project.setWorkersIds(null);
            fail("Sets null workers ids");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setWorkersIds method with invalid ids containing null and
     * non-Integer elements. Expects IllegalArgumentException.
     */
    public void testSetWorkersIds_InvalidIds() {
        Set workersIds = new HashSet();

        // try null element
        workersIds.add(null);
        try {
            project.setWorkersIds(workersIds);
            fail("Sets workers ids containing null element");
        } catch (IllegalArgumentException e) {
        }

        // try non-Integer element
        workersIds.clear();
        workersIds.add("String");
        try {
            project.setWorkersIds(workersIds);
            fail("Sets workers ids containing non-Integer element");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of setCreationDate method with null date. Expects
     * NullPointerException.
     */
    public void testSetCreationDate_NullDate() {
        try {
            project.setCreationDate(null);
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
            project.setCreationUser(null);
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
            project.setCreationUser("");
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
            project.setModificationDate(null);
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
            project.setModificationUser(null);
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
            project.setModificationUser("");
            fail("Sets empty modification user");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of setTimeEntries method with null entries. Expects
     * NullPointerException.
     */
    public void testSetTimeEntries_NullEntries() {
        try {
            project.setTimeEntries(null);
            fail("Sets null time entries");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setTimeEntries method with invalid entries containing null and
     * non-Integer elements. Expects IllegalArgumentException.
     */
    public void testSetTimeEntries_InvalidEntries() {
        Set timeEntries = new HashSet();

        // try null element
        timeEntries.add(null);
        try {
            project.setTimeEntries(timeEntries);
            fail("Sets time entries containing null element");
        } catch (IllegalArgumentException e) {
        }

        // try non-Integer element
        timeEntries.clear();
        timeEntries.add("String");
        try {
            project.setTimeEntries(timeEntries);
            fail("Sets time entries containing non-Integer element");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of setExpenseEntries method with null entries. Expects
     * NullPointerException.
     */
    public void testSetExpenseEntries_NullEntries() {
        try {
            project.setExpenseEntries(null);
            fail("Sets null expense entries");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of setExpenseEntries method with invalid entries containing null and
     * non-Integer elements. Expects IllegalArgumentException.
     */
    public void testSetExpenseEntries_InvalidEntries() {
        Set expenseEntries = new HashSet();

        // try null element
        expenseEntries.add(null);
        try {
            project.setExpenseEntries(expenseEntries);
            fail("Sets expense entries containing null element");
        } catch (IllegalArgumentException e) {
        }

        // try non-Integer element
        expenseEntries.clear();
        expenseEntries.add("String");
        try {
            project.setExpenseEntries(expenseEntries);
            fail("Sets expense entries containing non-Integer element");
        } catch (IllegalArgumentException e) {
        }
    }

}
