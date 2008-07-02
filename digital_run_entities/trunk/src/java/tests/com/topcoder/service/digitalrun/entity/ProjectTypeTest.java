/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link ProjectType} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectTypeTest extends TestCase {

    /**
     * Represents the <code>ProjectType</code> instance to test.
     */
    private ProjectType projectType = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void setUp() throws Exception {
        projectType = new ProjectType();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void tearDown() throws Exception {
        projectType = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ProjectTypeTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ProjectType#ProjectType()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_ProjectType() {
        // check for null
        assertNotNull("ProjectType creation failed", projectType);
    }

    /**
     * <p>
     * Accuracy test for {@link ProjectType#setName(String)} and {@link ProjectType#getName()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     *
     */
    public void test_accuracy_setName() {
        // set the value to test
        projectType.setName("test");
        assertEquals("getName and setName failure occured", "test", projectType.getName());
    }

    /**
     * <p>
     * Failure test for {@link ProjectType#setName(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String name : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setName() throws Exception {
        try {
            projectType.setName(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'name' cannot be null.");
        }
    }

    /**
     * <p>
     * Failure test for {@link ProjectType#setName(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String name : Empty string
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setName1() throws Exception {
        try {
            projectType.setName("");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'name' cannot be empty.");
        }
    }

    /**
     * <p>
     * Failure test for {@link ProjectType#setName(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String name : Empty string with only spaces
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setName2() throws Exception {
        try {
            projectType.setName("   ");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'name' cannot be empty.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link ProjectType#setCreationUser(String)} and {@link ProjectType#getCreationUser()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     *
     */
    public void test_accuracy_setCreationUser() {
        // set the value to test
        projectType.setCreationUser("test");
        assertEquals("getCreationUser and setCreationUser failure occured", "test", projectType.getCreationUser());
    }

    /**
     * <p>
     * Failure test for {@link ProjectType#setCreationUser(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String creationUser : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setCreationUser() throws Exception {
        try {
            projectType.setCreationUser(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'creationUser' cannot be null.");
        }
    }

    /**
     * <p>
     * Failure test for {@link ProjectType#setCreationUser(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String creationUser : Empty string
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setCreationUser1() throws Exception {
        try {
            projectType.setCreationUser("");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'creationUser' cannot be empty.");
        }
    }

    /**
     * <p>
     * Failure test for {@link ProjectType#setCreationUser(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String creationUser : Empty string with only spaces
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setCreationUser2() throws Exception {
        try {
            projectType.setCreationUser("  ");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'creationUser' cannot be empty.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link ProjectType#setModificationUser(String)} and
     * {@link ProjectType#getModificationUser()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     *
     */
    public void test_accuracy_setModificationUser() {
        // set the value to test
        projectType.setModificationUser("test");
        assertEquals("getModificationUser and setModificationUser failure occured", "test", projectType
                .getModificationUser());
    }

    /**
     * <p>
     * Failure test for {@link ProjectType#setModificationUser(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String modificationUser : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setModificationUser() throws Exception {
        try {
            projectType.setModificationUser(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'modificationUser' cannot be null.");
        }
    }

    /**
     * <p>
     * Failure test for {@link ProjectType#setModificationUser(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String modificationUser : Empty string
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setModificationUser1() throws Exception {
        try {
            projectType.setModificationUser("");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'modificationUser' cannot be empty.");
        }
    }

    /**
     * <p>
     * Failure test for {@link ProjectType#setModificationUser(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String modificationUser : Empty string with only spaces
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setModificationUser2() throws Exception {
        try {
            projectType.setModificationUser("  ");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'modificationUser' cannot be empty.");
        }
    }
}
