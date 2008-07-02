/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link BaseEntity} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseEntityTest extends TestCase {

    /**
     * Represents the <code>BaseEntity</code> instance to test.
     */
    private BaseEntity baseEntity = null;

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
        baseEntity = new MockBaseEntity();
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
        baseEntity = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(BaseEntityTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link BaseEntity#BaseEntity()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_BaseEntity() {
        // check for null
        assertNotNull("BaseEntity creation failed", baseEntity);
    }

    /**
     * <p>
     * Accuracy test for {@link BaseEntity#setId(long)} and {@link BaseEntity#getId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is 1.
     * </p>
     *
     */
    public void test_accuracy_setId() {
        // set the value to test
        baseEntity.setId(1);
        assertEquals("getId and setId failure occured", 1, baseEntity.getId());
    }

    /**
     * <p>
     * Failure test for {@link BaseEntity#setId(long)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long id : negative value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setId() throws Exception {
        try {
            baseEntity.setId(-1);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'id' should be positive number");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link BaseEntity#setCreationDate(Date)} and {@link BaseEntity#getCreationDate()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setCreationDate() {
        // set the value to test
        Date date = new Date();
        baseEntity.setCreationDate(date);
        assertEquals("getCreationDate and setCreationDate failure occured", date, baseEntity.getCreationDate());
    }

    /**
     * <p>
     * Failure test for {@link BaseEntity#setCreationDate(Date)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Date creationDate : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setCreationDate() throws Exception {
        try {
            baseEntity.setCreationDate(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'creationDate' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link BaseEntity#setModificationDate(Date)} and {@link BaseEntity#getModificationDate()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setModificationDate() {
        // set the value to test
        Date date = new Date();
        baseEntity.setModificationDate(date);
        assertEquals("getModificationDate and setModificationDate failure occured", date, baseEntity
                .getModificationDate());
    }

    /**
     * <p>
     * Failure test for {@link BaseEntity#setModificationDate(Date)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Date modificationDate : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setModificationDate() throws Exception {
        try {
            baseEntity.setModificationDate(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'modificationDate' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link BaseEntity#setDescription(String)} and {@link BaseEntity#getDescription()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     *
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        baseEntity.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", baseEntity.getDescription());
    }

    /**
     * <p>
     * Failure test for {@link BaseEntity#setDescription(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String description : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setDescription() throws Exception {
        try {
            baseEntity.setDescription(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'description' cannot be null.");
        }
    }

    /**
     * <p>
     * Failure test for {@link BaseEntity#setDescription(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String description : Empty string
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setDescription1() throws Exception {
        try {
            baseEntity.setDescription("");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'description' cannot be empty.");
        }
    }

    /**
     * <p>
     * Failure test for {@link BaseEntity#setDescription(String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String description : Empty string with only spaces
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setDescription2() throws Exception {
        try {
            baseEntity.setDescription("  ");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'description' cannot be empty.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link BaseEntity#equals(Object)} method.
     * </p>
     * <p>
     * Validates the possible cases for equal.
     * </p>
     *
     */
    public void test_accuracy_equals() {
        assertFalse("equals failed", baseEntity.equals("str"));

        assertFalse("equals failed", baseEntity.equals(new MockBaseEntity()));

        baseEntity.setId(1);

        assertFalse("equals failed", baseEntity.equals(new MockBaseEntity()));

        MockBaseEntity entity = new MockBaseEntity();
        entity.setId(1);
        assertTrue("equals failed", baseEntity.equals(entity));
    }

    /**
     * <p>
     * Accuracy test for {@link BaseEntity#hashCode()} method.
     * </p>
     * <p>
     * Hash code should directly depends on id.
     * </p>
     *
     */
    public void test_accuracy_hashCode() {
        MockBaseEntity entity = new MockBaseEntity();
        assertFalse("hashCode failed", baseEntity.hashCode() == entity.hashCode());
        baseEntity.setId(10);
        assertEquals("hashCode failed", baseEntity.hashCode(), new Long(10).hashCode());
        entity.setId(10);
        assertTrue("hashCode failed", baseEntity.hashCode() == entity.hashCode());
    }

    /**
     * <p>
     * Mock base entity for testing purpose.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    class MockBaseEntity extends BaseEntity {

        /**
         * Generated serial version id.
         */
        private static final long serialVersionUID = 6238157791107396338L;

        /**
         * Default no-arg constructor.
         */
        public MockBaseEntity() {
            // empty
        }
    }
}
