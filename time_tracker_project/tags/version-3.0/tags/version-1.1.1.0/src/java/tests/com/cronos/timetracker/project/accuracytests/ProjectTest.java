/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.accuracytests;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.timetracker.project.Project;


/**
 * <p>
 * This class contains the accuracy unit tests for Project.java.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class ProjectTest extends TestCase {
    /**
     * <p>
     * Represents the Project instance for testing.
     * </p>
     */
    private Project project = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    protected void setUp() {
        project = new Project(1);
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(ProjectTest.class);
    }

    /**
     * <p>
     * Tests constructor.
     * </p>
     */
    public void testConstructorAccuracy1() {
        project = new Project();
        assertNotNull("project was not properly created", project);
        assertEquals(0, project.getExpenseEntries().size());
        assertEquals(0, project.getTimeEntries().size());
        assertEquals(0, project.getWorkersIds().size());
        assertEquals(-1, project.getId());
        assertEquals(-1, project.getManagerId());
    }

    /**
     * <p>
     * Tests constructor.
     * </p>
     */
    public void testConstructorAccuracy2() {
        assertNotNull("project was not properly created", project);
        assertEquals(0, project.getExpenseEntries().size());
        assertEquals(0, project.getTimeEntries().size());
        assertEquals(0, project.getWorkersIds().size());
        assertEquals(1, project.getId());
        assertEquals(-1, project.getManagerId());
    }

    /**
     * <p>
     * Tests constructor.
     * </p>
     */
    public void testConstructorAccuracy3() {
        TreeSet workers = new TreeSet();
        workers.add(new Integer(1));

        project = new Project(1, 1, workers);
        assertNotNull("project was not properly created", project);
        assertEquals(1, project.getId());
        assertEquals(1, project.getManagerId());
        assertEquals(1, project.getWorkersIds().size());
        assertEquals(0, project.getExpenseEntries().size());
        assertEquals(0, project.getTimeEntries().size());
    }

    /**
     * <p>
     * Tests accuracy of getId() method.
     * </p>
     */
    public void testGetIdAccuracy() {
        assertEquals(1, project.getId());
    }

    /**
     * <p>
     * Tests accuracy of setId() method.
     * </p>
     */
    public void testSetIdAccuracy() {
        project.setId(2);
        assertEquals(2, project.getId());
    }

    /**
     * <p>
     * Tests accuracy of getCreationDate() method.
     * </p>
     */
    public void testGetCreationDateAccuracy() {
        assertNull(project.getCreationDate());
    }

    /**
     * <p>
     * Tests accuracy of setCreationDate() method.
     * </p>
     */
    public void testSetCreationDateAccuracy() {
        Date date = new Date();
        project.setCreationDate(date);
        assertEquals(date, project.getCreationDate());
    }

    /**
     * <p>
     * Tests accuracy of getCreationUser() method.
     * </p>
     */
    public void testGetCreationUserAccuracy() {
        assertNull(project.getCreationUser());
    }

    /**
     * <p>
     * Tests accuracy of setCreationUser() method.
     * </p>
     */
    public void testSetCreationUserAccuracy() {
        String user = "create";
        project.setCreationUser(user);
        assertEquals(user, project.getCreationUser());
    }

    /**
     * <p>
     * Tests accuracy of getModificationDate() method.
     * </p>
     */
    public void testGetModificationDateAccuracy() {
        assertNull(project.getModificationDate());
    }

    /**
     * <p>
     * Tests accuracy of setModificationDate() method.
     * </p>
     */
    public void testSetModificationDateAccuracy() {
        Date date = new Date();
        project.setModificationDate(date);
        assertEquals(date, project.getModificationDate());
    }

    /**
     * <p>
     * Tests accuracy of getModificationUser() method.
     * </p>
     */
    public void testGetModificationUserAccuracy() {
        assertNull(project.getModificationUser());
    }

    /**
     * <p>
     * Tests accuracy of setModificationUser() method.
     * </p>
     */
    public void testSetModificationUserAccuracy() {
        String user = "create";
        project.setModificationUser(user);
        assertEquals(user, project.getModificationUser());
    }

    /**
     * <p>
     * Tests accuracy of getName() method.
     * </p>
     */
    public void testGetNameAccuracy() {
        assertNull(project.getName());
    }

    /**
     * <p>
     * Tests accuracy of setName() method.
     * </p>
     */
    public void testSetNameAccuracy() {
        String user = "create";
        project.setName(user);
        assertEquals(user, project.getName());
    }

    /**
     * <p>
     * Tests accuracy of getDescription() method.
     * </p>
     */
    public void testGetDescriptionAccuracy() {
        assertNull(project.getDescription());
    }

    /**
     * <p>
     * Tests accuracy of setDescription() method.
     * </p>
     */
    public void testSetDescriptionAccuracy() {
        String des = "des";
        project.setDescription(des);
        assertEquals(des, project.getDescription());
    }

    /**
     * <p>
     * Tests accuracy of getStartDate() method.
     * </p>
     */
    public void testGetStartDateAccuracy() {
        assertNull(project.getStartDate());
    }

    /**
     * <p>
     * Tests accuracy of setStartDate() method.
     * </p>
     */
    public void testSetStartDateAccuracy() {
        Date date = new Date();
        project.setStartDate(date);
        assertEquals(date, project.getStartDate());
    }

    /**
     * <p>
     * Tests accuracy of getEndDate() method.
     * </p>
     */
    public void testGetEndDateAccuracy() {
        assertNull(project.getEndDate());
    }

    /**
     * <p>
     * Tests accuracy of setEndDate() method.
     * </p>
     */
    public void testSetEndDateAccuracy() {
        Date date = new Date();
        project.setEndDate(date);
        assertEquals(date, project.getEndDate());
    }

    /**
     * <p>
     * Tests accuracy of getManager() method.
     * </p>
     */
    public void testGetManagerAccuracy() {
        assertEquals(-1, project.getManagerId());
    }

    /**
     * <p>
     * Tests accuracy of setManager() method.
     * </p>
     */
    public void testSetManagerAccuracy() {
        project.setManagerId(1);
        assertEquals(1, project.getManagerId());
    }

    /**
     * <p>
     * Tests accuracy of getWorkers() method.
     * </p>
     */
    public void testGetWorkersAccuracy() {
        assertEquals(0, project.getWorkersIds().size());
    }

    /**
     * <p>
     * Tests accuracy of setWorkers() method.
     * </p>
     */
    public void testSetWorkersAccuracy() {
        Set workers = new TreeSet();
        workers.add(new Integer(1));
        project.setWorkersIds(workers);
        assertEquals(1, project.getWorkersIds().size());
    }

    /**
     * <p>
     * Tests accuracy of addWorker() method.
     * </p>
     */
    public void testAddWorkerAccuracy() {
        assertTrue("worker was not properly added", project.addWorkerId(1));
        assertFalse("worker should not be added", project.addWorkerId(1));
    }

    /**
     * <p>
     * Tests accuracy of removeWorker() method.
     * </p>
     */
    public void testRemoveWorkerAccuracy() {
        assertFalse("worker should not be removed", project.removeWorkerId(1));
        project.addWorkerId(1);
        assertTrue("worker was not properly removed", project.removeWorkerId(1));
    }

    /**
     * <p>
     * Tests accuracy of getExpenseEntries() method.
     * </p>
     */
    public void testGetExpenseEntriesAccuracy() {
        assertEquals(0, project.getExpenseEntries().size());
    }

    /**
     * <p>
     * Tests accuracy of setExpenseEntries() method.
     * </p>
     */
    public void testSetExpenseEntriesAccuracy() {
        Set expense = new TreeSet();
        expense.add(new Integer(1));
        project.setExpenseEntries(expense);
        assertEquals(expense, project.getExpenseEntries());
    }

    /**
     * <p>
     * Tests accuracy of addExpense() method.
     * </p>
     */
    public void testAddExpenseAccuracy() {
        assertTrue("Expense was not properly added", project.addExpenseEntry(1));
        assertFalse("Expense should not be added", project.addExpenseEntry(1));
    }

    /**
     * <p>
     * Tests accuracy of removeExpense() method.
     * </p>
     */
    public void testRemoveExpenseAccuracy() {
        assertFalse("Expense should not be removed", project.removeExpenseEntry(1));
        project.addExpenseEntry(1);
        assertTrue("Expense was not properly moved", project.removeExpenseEntry(1));
    }

    /**
     * <p>
     * Tests accuracy of getTimeEntry() method.
     * </p>
     */
    public void testGetTimeEntryAccuracy() {
        assertEquals(0, project.getTimeEntries().size());
    }

    /**
     * <p>
     * Tests accuracy of setTimeEntry() method.
     * </p>
     */
    public void testSetTimeEntryAccuracy() {
        Set entry = new TreeSet();
        entry.add(new Integer(1));
        project.setTimeEntries(entry);
        assertEquals(entry, project.getTimeEntries());
    }

    /**
     * <p>
     * Tests accuracy of addTimeEntry() method.
     * </p>
     */
    public void testAddTimeEntryAccuracy() {
        assertTrue("TimeEntry was not properly added", project.addTimeEntry(1));
        assertFalse("TimeEntry should not be added", project.addTimeEntry(1));
    }

    /**
     * <p>
     * Tests accuracy of removeTimeEntry() method.
     * </p>
     */
    public void testRemoveTimeEntryAccuracy() {
        assertFalse("TimeEntry should not be removed", project.removeTimeEntry(1));
        project.addTimeEntry(1);
        assertTrue("TimeEntry was not properly removed", project.removeTimeEntry(1));
    }
}
