package com.topcoder.timetracker.entry.time.failuretests;

import com.topcoder.timetracker.entry.time.TaskType;

import junit.framework.TestCase;
/**
 * <p>
 * Failure test cases for TaskType.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class TaskTypeFailureTests extends TestCase {
    /**
     * <p>
     * The TaskType instance for testing.
     * </p>
     */
    private TaskType instance;
    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new TaskType();
    }
    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        instance = null;
    }
    /**
     * <p>
     * Tests TaskType#setCompanyId(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when companyId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetCompanyId() {
        try {
            instance.setCompanyId(-999);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskType#setDescription(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetDescription1() {
        try {
            instance.setDescription(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskType#setDescription(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testSetDescription2() {
        try {
            instance.setDescription(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
}
