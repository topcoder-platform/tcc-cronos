/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Unit tests for Project implementation.
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
public class ProjectTest extends TestCase {
    /**
     * The Project instance to test against.
     */
    private Project project = null;

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ProjectTest.class);

        return suite;
    }

    /**
     * Prepares a Project instance for testing.
     */
    public void setUp() {
        project = new Project();
    }

    /**
     * Test of constructor with no argument. Verifies if the id is -1, the id of the manager is -1, and the workers
     * ids, time entries and expense entries are empty.
     */
    public void testConstructor_NoArg() {
        project = new Project();

        assertEquals("Fails to set id", -1, project.getId());
        assertEquals("Fails to set manager id", -1, project.getManagerId());
        assertTrue("Fails to set workers ids", project.getWorkersIds().isEmpty());
        assertTrue("Fails to set time entries", project.getTimeEntries().isEmpty());
        assertTrue("Fails to set expense entries", project.getExpenseEntries().isEmpty());
    }

    /**
     * Test of constructor with one argument. Verifies it by getting the id being set. Also verifies if the id of the
     * manager is -1, and the workers ids, time entries and expense entries are empty.
     */
    public void testConstructor_OneArg() {
        int id = 10;

        project = new Project(id);

        assertEquals("Fails to set id", id, project.getId());
        assertEquals("Fails to set manager id", -1, project.getManagerId());
        assertTrue("Fails to set workers ids", project.getWorkersIds().isEmpty());
        assertTrue("Fails to set time entries", project.getTimeEntries().isEmpty());
        assertTrue("Fails to set expense entries", project.getExpenseEntries().isEmpty());
    }

    /**
     * Test of constructor with three arguments. Verifies it by getting the id, the manager id and the workers ids
     * being set. Also verifies if the time entries and expense entries are empty.
     */
    public void testConstructor_ThreeArgs() {
        int id = 10;
        int managerId = 20;
        Set workersIds = new HashSet();

        for (int i = 0; i < 3; i++) {
            workersIds.add(new Integer(i));
        }

        project = new Project(id, managerId, workersIds);

        assertEquals("Fails to set id", id, project.getId());
        assertEquals("Fails to set manager id", managerId, project.getManagerId());
        assertEquals("Fails to set workers ids", workersIds, project.getWorkersIds());
        assertTrue("Fails to set time entries", project.getTimeEntries().isEmpty());
        assertTrue("Fails to set expense entries", project.getExpenseEntries().isEmpty());
    }

    /**
     * Test of getId and setId methods. Verifies it by getting the id back after setting it.
     */
    public void testGetSetId() {
        int id = 10;

        project.setId(id);
        assertEquals("Returns an incorrect id", id, project.getId());
    }

    /**
     * Test of setDescription method with null description. Expects NullPointerException.
     */
    public void testSetDescription_NullDescription() {
        try {
            project.setDescription(null);
            fail("Sets null description");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * Test of setDescription method with empty description. Expects IllegalArgumentException.
     */
    public void testSetDescription_EmptyDescription() {
        try {
            project.setDescription("");
            fail("Sets empty description");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of setDescription method with non-empty description. Verifies it by getting the description back after
     * setting it.
     */
    public void testGetSetDescription_NonEmptyDescription() {
        String description = "description";

        project.setDescription(description);
        assertEquals("Returns an incorrect description", description, project.getDescription());
    }

    /**
     * Test of setName method with null name. Expects NullPointerException.
     */
    public void testSetName_NullName() {
        try {
            project.setName(null);
            fail("Sets null name");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * Test of setName method with empty name. Expects IllegalArgumentException.
     */
    public void testSetName_EmptyName() {
        try {
            project.setName("");
            fail("Sets empty name");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of setName method with non-empty name. Verifies it by getting the name back after setting it.
     */
    public void testGetSetName_NonEmptyName() {
        String name = "name";

        project.setName(name);
        assertEquals("Returns an incorrect name", name, project.getName());
    }

    /**
     * Test of setStartDate method with null date. Expects NullPointerException.
     */
    public void testSetStartDate_NullDate() {
        try {
            project.setStartDate(null);
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
            // good
        }
    }

    /**
     * Test of getEndDate and setEndDate methods with non-null date. Verifies it by getting the date back after setting
     * it.
     */
    public void testGetSetEndDate_NonNullDate() {
        Date date = new Date();

        project.setEndDate(date);
        assertEquals("Returns an incorrect end date", date, project.getEndDate());
    }

    /**
     * Test of getManagerId and setManagerId methods. Verifies it by getting the manager id back after setting it.
     */
    public void testGetSetManagerId() {
        int managerId = 10;

        project.setManagerId(managerId);
        assertEquals("Returns an incorrect manager id", managerId, project.getManagerId());
    }

    /**
     * Test of setWorkersIds method with null ids. Expects NullPointerException.
     */
    public void testSetWorkersIds_NullIds() {
        try {
            project.setWorkersIds(null);
            fail("Sets null workers ids");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * Test of setWorkersIds method with invalid ids containing null and non-Integer elements. Expects
     * IllegalArgumentException.
     */
    public void testSetWorkersIds_InvalidIds() {
        Set workersIds = new HashSet();

        // try null element
        workersIds.add(null);
        try {
            project.setWorkersIds(workersIds);
            fail("Sets workers ids containing null element");
        } catch (IllegalArgumentException e) {
            // good
        }

        // try non-Integer element
        workersIds.clear();
        workersIds.add("String");
        try {
            project.setWorkersIds(workersIds);
            fail("Sets workers ids containing non-Integer element");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getWorkersIds and setWorkersIds methods with valid ids containing Integer elements. Verifies it by
     * getting the ids back after setting it.
     */
    public void testGetSetWorkersIds_ValidIds() {
        Set workersIds = new HashSet();

        for (int i = 0; i < 3; i++) {
            workersIds.add(new Integer(i));
        }

        project.setWorkersIds(workersIds);
        assertEquals("Returns an incorrect set of workers ids", workersIds, project.getWorkersIds());
    }

    /**
     * Test of setCreationDate method with null date. Expects NullPointerException.
     */
    public void testSetCreationDate_NullDate() {
        try {
            project.setCreationDate(null);
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

        project.setCreationDate(date);
        assertEquals("Returns an incorrect creation date", date, project.getCreationDate());
    }

    /**
     * Test of setCreationUser method with null user. Expects NullPointerException.
     */
    public void testSetCreationUser_NullUser() {
        try {
            project.setCreationUser(null);
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
            project.setCreationUser("");
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

        project.setCreationUser(user);
        assertEquals("Returns an incorrect creation user", user, project.getCreationUser());
    }

    /**
     * Test of setModificationDate method with null date. Expects NullPointerException.
     */
    public void testSetModificationDate_NullDate() {
        try {
            project.setModificationDate(null);
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

        project.setModificationDate(date);
        assertEquals("Returns an incorrect modification date", date, project.getModificationDate());
    }

    /**
     * Test of setModificationUser method with null user. Expects NullPointerException.
     */
    public void testSetModificationUser_NullUser() {
        try {
            project.setModificationUser(null);
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
            project.setModificationUser("");
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

        project.setModificationUser(user);
        assertEquals("Returns an incorrect modification user", user, project.getModificationUser());
    }

    /**
     * Test of setTimeEntries method with null entries. Expects NullPointerException.
     */
    public void testSetTimeEntries_NullEntries() {
        try {
            project.setTimeEntries(null);
            fail("Sets null time entries");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * Test of setTimeEntries method with invalid entries containing null and non-Integer elements. Expects
     * IllegalArgumentException.
     */
    public void testSetTimeEntries_InvalidEntries() {
        Set timeEntries = new HashSet();

        // try null element
        timeEntries.add(null);
        try {
            project.setTimeEntries(timeEntries);
            fail("Sets time entries containing null element");
        } catch (IllegalArgumentException e) {
            // good
        }

        // try non-Integer element
        timeEntries.clear();
        timeEntries.add("String");
        try {
            project.setTimeEntries(timeEntries);
            fail("Sets time entries containing non-Integer element");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getTimeEntries and setTimeEntries methods with valid entries containing Integer elements. Verifies it by
     * getting the entries back after setting it.
     */
    public void testGetSetTimeEntries_ValidEntries() {
        Set timeEntries = new HashSet();

        for (int i = 0; i < 3; i++) {
            timeEntries.add(new Integer(i));
        }

        project.setTimeEntries(timeEntries);
        assertEquals("Returns an incorrect set of time entries", timeEntries, project.getTimeEntries());
    }

    /**
     * Test of setExpenseEntries method with null entries. Expects NullPointerException.
     */
    public void testSetExpenseEntries_NullEntries() {
        try {
            project.setExpenseEntries(null);
            fail("Sets null expense entries");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * Test of setExpenseEntries method with invalid entries containing null and non-Integer elements. Expects
     * IllegalArgumentException.
     */
    public void testSetExpenseEntries_InvalidEntries() {
        Set expenseEntries = new HashSet();

        // try null element
        expenseEntries.add(null);
        try {
            project.setExpenseEntries(expenseEntries);
            fail("Sets expense entries containing null element");
        } catch (IllegalArgumentException e) {
            // good
        }

        // try non-Integer element
        expenseEntries.clear();
        expenseEntries.add("String");
        try {
            project.setExpenseEntries(expenseEntries);
            fail("Sets expense entries containing non-Integer element");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test of getExpenseEntries and setExpenseEntries methods with valid entries containing Integer elements. Verifies
     * it by getting the entries back after setting it.
     */
    public void testGetSetExpenseEntries_ValidEntries() {
        Set expenseEntries = new HashSet();

        for (int i = 0; i < 3; i++) {
            expenseEntries.add(new Integer(i));
        }

        project.setExpenseEntries(expenseEntries);
        assertEquals("Returns an incorrect set of expense entries", expenseEntries, project.getExpenseEntries());
    }

    /**
     * Test of addWorkerId method with an existing id. Verifies if false is returned.
     */
    public void testAddWorkerId_ExistId() {
        int workerId = 10;

        project.addWorkerId(workerId);

        assertFalse("Adds an existing worker id", project.addWorkerId(workerId));
    }

    /**
     * Test of addWorkerId method with a non-existing id. Verifies if true is returned. Also verifies if the worker id
     * was added to the project.
     */
    public void testAddWorkerId_NonExistId() {
        int workerId = 10;

        assertTrue("Fails to add non-existing worker id", project.addWorkerId(workerId));
        assertTrue("Fails to add non-existing worker id", project.getWorkersIds().contains(new Integer(workerId)));
    }

    /**
     * Test of removeWorkerId method with a non-existing id. Verifies if false is returned.
     */
    public void testRemoveWorkerId_NonExistId() {
        int workerId = 10;

        assertFalse("Removes a non-existing worker id", project.removeWorkerId(workerId));
    }

    /**
     * Test of removeWorkerId method with an existing id. Verifies if true is returned. Also verifies if the worker id
     * was removed from the project.
     */
    public void testRemoveWorkerId_ExistId() {
        int workerId = 10;

        project.addWorkerId(workerId);

        assertTrue("Fails to remove existing worker id", project.removeWorkerId(workerId));
        assertFalse("Fails to remove existing worker id", project.getWorkersIds().contains(new Integer(workerId)));
    }

    /**
     * Test of addTimeEntry method with an existing entry. Verifies if false is returned.
     */
    public void testAddTimeEntry_ExistEntry() {
        int timeEntry = 10;

        project.addTimeEntry(timeEntry);

        assertFalse("Adds an existing time entry", project.addTimeEntry(timeEntry));
    }

    /**
     * Test of addTimeEntry method with a non-existing entry. Verifies if true is returned. Also verifies if the time
     * entry was added to the project.
     */
    public void testAddTimeEntry_NonExistEntry() {
        int timeEntry = 10;

        assertTrue("Fails to add non-existing time entry", project.addTimeEntry(timeEntry));
        assertTrue("Fails to add non-existing time entry", project.getTimeEntries().contains(new Integer(timeEntry)));
    }

    /**
     * Test of removeTimeEntry method with a non-existing entry. Verifies if false is returned.
     */
    public void testRemoveTimeEntry_NonExistEntry() {
        int timeEntry = 10;

        assertFalse("Removes a non-existing time entry", project.removeTimeEntry(timeEntry));
    }

    /**
     * Test of removeTimeEntry method with an existing entry. Verifies if true is returned. Also verifies if the time
     * entry was removed from the project.
     */
    public void testRemoveTimeEntry_ExistEntry() {
        int timeEntry = 10;

        project.addTimeEntry(timeEntry);

        assertTrue("Fails to remove existing time entry", project.removeTimeEntry(timeEntry));
        assertFalse("Fails to remove existing time entry", project.getTimeEntries().contains(new Integer(timeEntry)));
    }

    /**
     * Test of addExpenseEntry method with an existing entry. Verifies if false is returned.
     */
    public void testAddExpenseEntry_ExistEntry() {
        int expenseEntry = 10;

        project.addExpenseEntry(expenseEntry);

        assertFalse("Adds an existing expense entry", project.addExpenseEntry(expenseEntry));
    }

    /**
     * Test of addExpenseEntry method with a non-existing entry. Verifies if true is returned. Also verifies if the
     * expense entry was added to the project.
     */
    public void testAddExpenseEntry_NonExistEntry() {
        int expenseEntry = 10;

        assertTrue("Fails to add non-existing expense entry", project.addExpenseEntry(expenseEntry));
        assertTrue("Fails to add non-existing expense entry",
            project.getExpenseEntries().contains(new Integer(expenseEntry)));
    }

    /**
     * Test of removeExpenseEntry method with a non-existing entry. Verifies if false is returned.
     */
    public void testRemoveExpenseEntry_NonExistEntry() {
        int expenseEntry = 10;

        assertFalse("Removes a non-existing expense entry", project.removeExpenseEntry(expenseEntry));
    }

    /**
     * Test of removeExpenseEntry method with an existing entry. Verifies if true is returned. Also verifies if the
     * expense entry was removed from the project.
     */
    public void testRemoveExpenseEntry_ExistEntry() {
        int expenseEntry = 10;

        project.addExpenseEntry(expenseEntry);

        assertTrue("Fails to remove existing expense entry", project.removeExpenseEntry(expenseEntry));
        assertFalse("Fails to remove existing expense entry",
            project.getExpenseEntries().contains(new Integer(expenseEntry)));
    }
}
