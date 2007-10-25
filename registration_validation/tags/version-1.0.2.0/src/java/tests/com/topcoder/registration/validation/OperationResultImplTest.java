/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.topcoder.registration.team.service.OperationResult;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for OperationResultImpl.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class OperationResultImplTest extends TestCase {
    /**
     * <p>
     * Repesents a flag as to whether the operation was successful.
     * </p>
     */
    private boolean successful;

    /**
     * <p>
     * Represents the error messages if the operation was not successful.
     * </p>
     *
     */
    private String[] errors;

    /**
     * <p>
     * The OperationResultImpl instance for testing purpose.
     * </p>
     */
    private OperationResultImpl operationResult;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        successful = false;
        errors = new String[] {"error 1", "error 2", "error 3"};
        operationResult = new OperationResultImpl(successful, errors);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies OperationResultImpl inheritance OperationResult.
     * </p>
     */
    public void testInheritance1() {
        operationResult = new OperationResultImpl();
        assertTrue("SurveyContentException does not subclass Exception.",
                operationResult instanceof OperationResult);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies OperationResultImpl inheritance OperationResult.
     * </p>
     */
    public void testInheritance2() {
        operationResult = new OperationResultImpl(
                successful, errors);
        assertTrue("SurveyContentException does not subclass Exception.",
                operationResult instanceof OperationResult);
    }

    /**
     * <p>
     * Tests ctor OperationResultImpl#OperationResultImpl() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created OperationResultImpl instance should not be
     * null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1() throws Exception {

        operationResult = new OperationResultImpl();
        assertNotNull("Failed to create a new OperationResultImpl instance.",
                operationResult);

        assertTrue("Failed to create a new OperationResultImpl instance.",
                operationResult.isSuccessful());
        assertEquals("Failed to create a new OperationResultImpl instance.", 0,
                operationResult.getErrors().length);
    }

    /**
     * <p>
     * Tests ctor OperationResultImpl#OperationResultImpl(boolean, String[]) for
     * accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created OperationResultImpl instance should not be
     * null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2() throws Exception {

        operationResult = new OperationResultImpl(successful, errors);
        assertNotNull("Failed to create a new OperationResultImpl instance.",
                operationResult);

        assertEquals("Failed to create a new OperationResultImpl instance.",
                successful, operationResult.isSuccessful());
        assertEquals("Failed to create a new OperationResultImpl instance.",
                errors.length, operationResult.getErrors().length);
    }

    /**
     * <p>
     * Tests ctor OperationResultImpl#OperationResultImpl(boolean, String[]) for
     * accuracy.
     * </p>
     *
     * <p>
     * It tests the case when errors is null and successful is true.
     * </p>
     *
     * <p>
     * It verifies the newly created OperationResultImpl instance should not be
     * null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_NullErrors_True() throws Exception {

        operationResult = new OperationResultImpl(true, null);
        assertNotNull("Failed to create a new OperationResultImpl instance.",
                operationResult);

        assertNotNull("Failed to create a new OperationResultImpl instance.",
                operationResult);
        assertTrue("Failed to create a new OperationResultImpl instance.",
                operationResult.isSuccessful());
        assertEquals("Failed to create a new OperationResultImpl instance.", 0,
                operationResult.getErrors().length);
    }

    /**
     * <p>
     * Tests ctor OperationResultImpl#OperationResultImpl(boolean, String[]) for
     * accuracy.
     * </p>
     *
     * <p>
     * It tests the case when errors is empty and successful is true.
     * </p>
     *
     * <p>
     * It verifies the newly created OperationResultImpl instance should not be
     * null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_EmptyErrors_True() throws Exception {
        operationResult = new OperationResultImpl(true, new String[0]);
        assertNotNull("Failed to create a new OperationResultImpl instance.",
                operationResult);
        assertTrue("Failed to create a new OperationResultImpl instance.",
                operationResult.isSuccessful());
        assertEquals("Failed to create a new OperationResultImpl instance.", 0,
                operationResult.getErrors().length);
    }

    /**
     * <p>
     * Tests ctor OperationResultImpl#OperationResultImpl(boolean, String[]) for
     * accuracy.
     * </p>
     *
     * <p>
     * It tests the case when errors is null and successful is false.
     * </p>
     *
     * <p>
     * It verifies the newly created OperationResultImpl instance should not be
     * null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_NullErrors_False() throws Exception {
        operationResult = new OperationResultImpl(false, null);
        assertNotNull("Failed to create a new OperationResultImpl instance.",
                operationResult);
        assertFalse("Failed to create a new OperationResultImpl instance.",
                operationResult.isSuccessful());
        assertEquals("Failed to create a new OperationResultImpl instance.", 0,
                operationResult.getErrors().length);
    }

    /**
     * <p>
     * Tests ctor OperationResultImpl#OperationResultImpl(boolean, String[]) for
     * accuracy.
     * </p>
     *
     * <p>
     * It tests the case when errors is empty and successful is false.
     * </p>
     *
     * <p>
     * It verifies the newly created OperationResultImpl instance should not be
     * null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_EmptyErrors_False() throws Exception {
        operationResult = new OperationResultImpl(false, new String[0]);
        assertNotNull("Failed to create a new OperationResultImpl instance.",
                operationResult);
        assertFalse("Failed to create a new OperationResultImpl instance.",
                operationResult.isSuccessful());
        assertEquals("Failed to create a new OperationResultImpl instance.", 0,
                operationResult.getErrors().length);
    }

    /**
     * <p>
     * Tests ctor OperationResultImpl#OperationResultImpl(boolean, String[]) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when errors contains null elements in the array and
     * successful is true.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_NullElements_True() throws Exception {
        try {
            new OperationResultImpl(true, new String[] {null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor OperationResultImpl#OperationResultImpl(boolean, String[]) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when errors contains empty elements in the array and
     * successful is true.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_EmptyElements_True() throws Exception {
        try {
            new OperationResultImpl(true, new String[] {"" });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor OperationResultImpl#OperationResultImpl(boolean, String[]) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when errors contains full of spaces elements in the
     * array and successful is true.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_TrimmeEmptyElements_True() throws Exception {
        try {
            new OperationResultImpl(true, new String[] {"  " });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor OperationResultImpl#OperationResultImpl(boolean, String[]) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when errors is a non-empty array but successful is
     * true.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_NonEmptyErrors_True() throws Exception {
        try {
            new OperationResultImpl(true, new String[] {"non-empty" });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor OperationResultImpl#OperationResultImpl(boolean, String[]) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when errors contains null elements in the array and
     * successful is false.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_NullElements_False() throws Exception {
        try {
            new OperationResultImpl(false,
                    new String[] {"contains null", null });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor OperationResultImpl#OperationResultImpl(boolean, String[]) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when errors contains empty elements in the array and
     * successful is false.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_EmptyElements_False() throws Exception {
        try {
            new OperationResultImpl(false,
                    new String[] {"contains empty", "" });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor OperationResultImpl#OperationResultImpl(boolean, String[]) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when errors contains full of spaces elements in the
     * array and successful is false.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2_TrimmeEmptyElements_False() throws Exception {
        try {
            new OperationResultImpl(false, new String[] {"contains empty",
                "  " });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OperationResultImpl#setErrors(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when errors is empty and successful is true.
     * </p>
     *
     * <p>
     * Should have set the error messages correctly.
     * </p>
     *
     */
    public void testSetErrors_EmptyErrors_True() {
        operationResult = new OperationResultImpl();
        operationResult.setErrors(new String[0]);
        assertEquals("Failed to set the error messages correctly.", 0,
                operationResult.getErrors().length);
    }

    /**
     * <p>
     * Tests OperationResultImpl#setErrors(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when errors is null and successful is true.
     * </p>
     *
     * <p>
     * Should have set the error messages correctly.
     * </p>
     *
     */
    public void testSetErrors_NullErrors_True() {
        operationResult = new OperationResultImpl();
        operationResult.setErrors(null);
        assertEquals("Failed to set the error messages correctly.", 0,
                operationResult.getErrors().length);
    }

    /**
     * <p>
     * Tests OperationResultImpl#setErrors(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when errors is null and successful is false.
     * </p>
     *
     * <p>
     * Should have set the error messages correctly.
     * </p>
     *
     */
    public void testSetErrors_NullErrors_False() {
        operationResult.setSuccessful(false);
        operationResult.setErrors(null);
        assertEquals("Failed to set the error messages correctly.", 0,
                operationResult.getErrors().length);
    }

    /**
     * <p>
     * Tests OperationResultImpl#setErrors(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when errors is empty and successful is false.
     * </p>
     *
     * <p>
     * Should have set the error messages correctly.
     * </p>
     *
     */
    public void testSetErrors_EmptyErrors_False() {
        operationResult.setSuccessful(false);
        operationResult.setErrors(new String[0]);
        assertEquals("Failed to set the error messages correctly.", 0,
                operationResult.getErrors().length);
    }

    /**
     * <p>
     * Tests OperationResultImpl#setErrors(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when errors contains null elements in the array and
     * successful is false.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testSetErrors_NullElements_False() {
        try {
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {null });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests OperationResultImpl#setErrors(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when errors contains empty elements in the array and
     * successful is false.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testSetErrors_EmptyElements_False() {
        try {
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {null });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests OperationResultImpl#setErrors(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when errors contains null elements in the array and
     * successful is true.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetErrors_NullElements_True() throws Exception {
        try {
            operationResult.setSuccessful(true);
            operationResult.setErrors(new String[] {null });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OperationResultImpl#setErrors(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when errors contains empty elements in the array and
     * successful is true.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetErrors_EmptyElements_True() throws Exception {
        try {
            operationResult.setSuccessful(true);
            operationResult.setErrors(new String[] {"" });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OperationResultImpl#setErrors(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when errors contains full of spaces elements in the
     * array and successful is true.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetErrors_TrimmedEmptyElements_True() throws Exception {
        try {
            operationResult.setSuccessful(true);
            operationResult.setErrors(new String[] {"  " });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OperationResultImpl#setErrors(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when errors is a non-empty array but successful is
     * true.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetErrors_NonEmptyErrors_True() throws Exception {
        try {
            operationResult.setSuccessful(true);
            operationResult.setErrors(new String[] {"non-empty" });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OperationResultImpl#setErrors(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when errors contains full of spaces elements in the
     * array and successful is false.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testSetErrors_TrimmedEmptyElements_False() {
        try {
            operationResult.setSuccessful(false);
            operationResult.setErrors(new String[] {"contains full of spaces",
                "  " });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests OperationResultImpl#getErrors() for accuracy.
     * </p>
     *
     * <p>
     * It verifies OperationResultImpl#getErrors() is correct.
     * </p>
     */
    public void testGetErrors() {
        operationResult = new OperationResultImpl(successful, errors);
        assertEquals("Failed to get the error messages priority.",
            errors.length, operationResult.getErrors().length);
    }

    /**
     * <p>
     * Tests OperationResultImpl#isSuccessful() for accuracy.
     * </p>
     *
     * <p>
     * It verifies OperationResultImpl#isSuccessful() is correct.
     * </p>
     */
    public void testIsSuccessful() {
        assertEquals("Failed to get the result flag for the operation.",
                successful, operationResult.isSuccessful());
    }

    /**
     * <p>
     * Tests OperationResultImpl#setSuccessful(boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when successful is true, but errors is not empty
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testSetSuccessful_NonEmptyErrors_True() {
        try {
            operationResult.setErrors(new String[] {"NonEmptyErrors" });
            operationResult.setSuccessful(true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests OperationResultImpl#setSuccessful(boolean) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when successful is false, and errors is not empty
     * </p>
     *
     * <p>
     * Should have set the error messages correctly.
     * </p>
     *
     */
    public void testSetSuccessful_NonEmptyErrors_False() {
        operationResult = new OperationResultImpl(false,
                new String[] {"NonEmptyErrors" });
        operationResult.setSuccessful(false);
        assertFalse("Failed to set the error messages priority.",
                operationResult.isSuccessful());
    }

    /**
     * <p>
     * Tests OperationResultImpl#setSuccessful(boolean) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when successful is true, and errors is empty
     * </p>
     *
     * <p>
     * Should have set the error messages correctly.
     * </p>
     *
     */
    public void testSetSuccessful_EmptyErrors_True() {
        operationResult.setErrors(new String[0]);
        operationResult.setSuccessful(true);

        assertTrue("Failed to set the error messages priority.",
                operationResult.isSuccessful());
    }

    /**
     * <p>
     * Tests OperationResultImpl#setSuccessful(boolean) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when successful is false, and errors is empty
     * </p>
     *
     * <p>
     * Should have set the error messages correctly.
     * </p>
     *
     */
    public void testSetSuccessful_EmptyErrors_False() {
        operationResult.setErrors(new String[0]);
        operationResult.setSuccessful(false);

        assertFalse("Failed to set the error messages priority.",
                operationResult.isSuccessful());
    }
}
