/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.actions;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.TCSubject;
import com.topcoder.service.actions.AggregateDataModel;
import com.topcoder.service.actions.ValidationErrors;

/**
 * <p>
 * Unit tests for <code>SpecificationReviewAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SpecificationReviewActionUnitTest extends TestCase {

    /**
     * The instance used in testing.
     */
    private MockSpecificationReviewAction instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        instance = new MockSpecificationReviewAction();
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * Tests the class inheritance to make sure it is correct.
     */
    @Test
    public void testInheritance() {
        assertTrue("inheritance is wrong", instance instanceof SessionAwareAction);
    }

    /**
     * Accuracy test for DEFAULT_TC_SUBJECT_SESSION_KEY. Verifies that the value for the constant is correct.
     */
    @Test
    public void test_DEFAULT_TC_SUBJECT_SESSION_KEY_Accuracy() {
        assertEquals("value for constant is wrong", "tcSubject",
            SpecificationReviewAction.DEFAULT_TC_SUBJECT_SESSION_KEY);
    }

    /**
     * Accuracy test for DEFAULT_RESULT_DATA_KEY. Verifies that the value for the constant is correct.
     */
    @Test
    public void test_DEFAULT_RESULT_DATA_KEY_Accuracy() {
        assertEquals("value for constant is wrong", "result", SpecificationReviewAction.DEFAULT_RESULT_DATA_KEY);
    }

    /**
     * Accuracy test for DEFAULT_EXCEPTION_KEY. Verifies that the value for the constant is correct.
     */
    @Test
    public void test_DEFAULT_EXCEPTION_KEY_Accuracy() {
        assertEquals("value for constant is wrong", "result", SpecificationReviewAction.DEFAULT_EXCEPTION_KEY);
    }

    /**
     * Accuracy test for DEFAULT_VALIDATION_ERRORS_KEY. Verifies that the value for the constant is correct.
     */
    @Test
    public void test_DEFAULT_VALIDATION_ERRORS_KEY_Accuracy() {
        assertEquals("value for constant is wrong", "validationErrors",
            SpecificationReviewAction.DEFAULT_VALIDATION_ERRORS_KEY);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    @Test
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for getTCSubject. Verifies the returned value is correct.
     */
    @Test
    public void test_getTCSubject_Accuracy() {
        Map<String, Object> session = new HashMap<String, Object>();
        TCSubject tcSubject = new TCSubject(42);
        session.put("tcSubject", tcSubject);
        instance.setSession(session);

        assertSame("getter is wrong", tcSubject, instance.getTCSubject());
    }

    /**
     * Accuracy test for setResultData. Verifies the data is set in model. In this test, the model
     * is null in the action and must be created in the method.
     */
    @Test
    public void test_setResultData_Accuracy1() {
        Object obj = new Object();
        instance.setResultData(obj);
        assertSame("data was not set correctly in model", obj, instance.getModel().getData(
            SpecificationReviewAction.DEFAULT_RESULT_DATA_KEY));
    }

    /**
     * Accuracy test for setResultData. Verifies the data is set in model. In this test, the model
     * is already present in the action.
     */
    @Test
    public void test_setResultData_Accuracy2() {
        instance.setModel(new AggregateDataModel());
        Object obj = new Object();
        instance.setResultData(obj);
        assertSame("data was not set correctly in model", obj, instance.getModel().getData(
            SpecificationReviewAction.DEFAULT_RESULT_DATA_KEY));
    }

    /**
     * Failure test for setResultData. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setResultData_Null() {
        try {
            instance.setResultData(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Accuracy test for setException. Verifies the data is set in model. In this test, the model
     * is null in the action and must be created in the method.
     */
    @Test
    public void test_setException_Accuracy1() {
        Exception obj = new Exception("test");
        instance.setException(obj);
        assertSame("data was not set correctly in model", obj, instance.getModel().getData(
            SpecificationReviewAction.DEFAULT_EXCEPTION_KEY));
    }

    /**
     * Accuracy test for setException. Verifies the data is set in model. In this test, the model
     * is already present in the action.
     */
    @Test
    public void test_setException_Accuracy2() {
        instance.setModel(new AggregateDataModel());
        Exception obj = new Exception("test");
        instance.setException(obj);
        assertSame("data was not set correctly in model", obj, instance.getModel().getData(
            SpecificationReviewAction.DEFAULT_EXCEPTION_KEY));
    }

    /**
     * Failure test for setException. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setException_Null() {
        try {
            instance.setException(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Accuracy test for addValidationError. Verifies that validation error is stored in model correctly.
     */
    @Test
    public void test_addValidationError_Accuracy1() {
        // add the validation error for the property
        instance.addValidationError("property1", new String[] {"validation message1", "validation message2"});

        // make sure error was stored correctly in model
        AggregateDataModel model = instance.getModel();
        assertNotNull("aggregate data model should not be null", model);
        assertNotNull("validation errors should not be null", model
            .getData(SpecificationReviewAction.DEFAULT_VALIDATION_ERRORS_KEY));
        ValidationErrors validationErrors =
            (ValidationErrors) model.getData(SpecificationReviewAction.DEFAULT_VALIDATION_ERRORS_KEY);
        assertNotNull("errors array in validation errors object should not be null", validationErrors.getErrors());
        assertEquals("validation errors object contains wrong number of error elements", 1, validationErrors
            .getErrors().length);
        TestHelper.assertValidationErrorRecord(validationErrors.getErrors()[0], "property1", new String[] {
            "validation message1", "validation message2"});
    }

    /**
     * Accuracy test for addValidationError. Verifies that validation error is stored in model correctly when
     * there are already existing validation errors present in model
     */
    @Test
    public void test_addValidationError_Accuracy2() {
        // add an existing validation error to model
        test_addValidationError_Accuracy1();

        // add the new validation error for another property
        instance.addValidationError("property2", new String[] {"validation message3", "validation message4"});

        // make sure new error was stored correctly in model, and also make sure old error is still present
        AggregateDataModel model = instance.getModel();
        assertNotNull("aggregate data model should not be null", model);
        assertNotNull("validation errors should not be null", model
            .getData(SpecificationReviewAction.DEFAULT_VALIDATION_ERRORS_KEY));
        ValidationErrors validationErrors =
            (ValidationErrors) model.getData(SpecificationReviewAction.DEFAULT_VALIDATION_ERRORS_KEY);
        assertNotNull("errors array in validation errors object should not be null", validationErrors.getErrors());
        assertEquals("validation errors object contains wrong number of error elements", 2, validationErrors
            .getErrors().length);
        TestHelper.assertValidationErrorRecord(validationErrors.getErrors()[0], "property1", new String[] {
            "validation message1", "validation message2"});
        TestHelper.assertValidationErrorRecord(validationErrors.getErrors()[1], "property2", new String[] {
            "validation message3", "validation message4"});
    }

    /**
     * Failure test for addValidationError. The propertyName is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_addValidationError_NullPropertyName() {
        try {
            instance.addValidationError(null, new String[] {"a"});
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for addValidationError. The propertyName is empty, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_addValidationError_EmptyPropertyName() {
        try {
            instance.addValidationError("   ", new String[] {"a"});
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for addValidationError. The messages parameter is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_addValidationError_NullMessages() {
        try {
            instance.addValidationError("a", null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for addValidationError. The messages parameter is empty array, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_addValidationError_EmptyMessages() {
        try {
            instance.addValidationError("a", new String[0]);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for addValidationError. The messages parameter contains null element,
     * so IllegalArgumentException is expected.
     */
    @Test
    public void test_addValidationError_NullElementInMessages() {
        try {
            instance.addValidationError("a", new String[] {"top", null, "coder"});
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for addValidationError. The messages parameter contains empty element,
     * so IllegalArgumentException is expected.
     */
    @Test
    public void test_addValidationError_EmptyElementInMessages() {
        try {
            instance.addValidationError("a", new String[] {"top", "  ", "coder"});
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Accuracy test for getContestId. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestId_Accuracy() {
        int test = 1;
        instance.setContestId(test);
        assertEquals("getter is wrong", test, instance.getContestId());
    }

    /**
     * Accuracy test for setContestId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContestId_Accuracy() {
        long test = 1;
        instance.setContestId(test);
        TestHelper.assertFieldEquals(SpecificationReviewAction.class, test, instance, "contestId");
    }

    /**
     * Accuracy test for isStudio. Verifies the returned value is correct.
     */
    @Test
    public void test_getStudio_Accuracy() {
        boolean test = true;
        instance.setStudio(test);
        assertEquals("getter is wrong", test, instance.isStudio());
    }

    /**
     * Accuracy test for setStudio. Verifies the assigned value is correct.
     */
    @Test
    public void test_setStudio_Accuracy() {
        boolean test = true;
        instance.setStudio(test);
        TestHelper.assertFieldEquals(SpecificationReviewAction.class, test, instance, "studio");
    }

    /**
     * Accuracy test for getTCSubjectSessionKey. Verifies the returned value is correct.
     */
    @Test
    public void test_getTCSubjectSessionKey_Accuracy() {
        String test = "test";
        instance.setTCSubjectSessionKey(test);
        assertEquals("getter is wrong", test, instance.getTCSubjectSessionKey());
    }

    /**
     * Accuracy test for setTCSubjectSessionKey. Verifies the assigned value is correct.
     */
    @Test
    public void test_setTCSubjectSessionKey_Accuracy() {
        String test = "test";
        instance.setTCSubjectSessionKey(test);
        TestHelper.assertFieldEquals(SpecificationReviewAction.class, test, instance, "tcSubjectSessionKey");
    }

    /**
     * Failure test for setTCSubjectSessionKey. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setTCSubjectSessionKey_Null() {
        try {
            instance.setTCSubjectSessionKey(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setTCSubjectSessionKey. The value is empty, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setTCSubjectSessionKey_Empty() {
        try {
            instance.setTCSubjectSessionKey("  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Accuracy test for getResultDataKey. Verifies the returned value is correct.
     */
    @Test
    public void test_getResultDataKey_Accuracy() {
        String test = "test";
        instance.setResultDataKey(test);
        assertEquals("getter is wrong", test, instance.getResultDataKey());
    }

    /**
     * Accuracy test for setResultDataKey. Verifies the assigned value is correct.
     */
    @Test
    public void test_setResultDataKey_Accuracy() {
        String test = "test";
        instance.setResultDataKey(test);
        TestHelper.assertFieldEquals(SpecificationReviewAction.class, test, instance, "resultDataKey");
    }

    /**
     * Failure test for setResultDataKey. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setResultDataKey_Null() {
        try {
            instance.setResultDataKey(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setResultDataKey. The value is empty, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setResultDataKey_Empty() {
        try {
            instance.setResultDataKey("  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Accuracy test for getExceptionKey. Verifies the returned value is correct.
     */
    @Test
    public void test_getExceptionKey_Accuracy() {
        String test = "test";
        instance.setExceptionKey(test);
        assertEquals("getter is wrong", test, instance.getExceptionKey());
    }

    /**
     * Accuracy test for setExceptionKey. Verifies the assigned value is correct.
     */
    @Test
    public void test_setExceptionKey_Accuracy() {
        String test = "test";
        instance.setExceptionKey(test);
        TestHelper.assertFieldEquals(SpecificationReviewAction.class, test, instance, "exceptionKey");
    }

    /**
     * Failure test for setExceptionKey. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setExceptionKey_Null() {
        try {
            instance.setExceptionKey(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setExceptionKey. The value is empty, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setExceptionKey_Empty() {
        try {
            instance.setExceptionKey("  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Accuracy test for getValidationErrorsKey. Verifies the returned value is correct.
     */
    @Test
    public void test_getValidationErrorsKey_Accuracy() {
        String test = "test";
        instance.setValidationErrorsKey(test);
        assertEquals("getter is wrong", test, instance.getValidationErrorsKey());
    }

    /**
     * Accuracy test for setValidationErrorsKey. Verifies the assigned value is correct.
     */
    @Test
    public void test_setValidationErrorsKey_Accuracy() {
        String test = "test";
        instance.setValidationErrorsKey(test);
        TestHelper.assertFieldEquals(SpecificationReviewAction.class, test, instance, "validationErrorsKey");
    }

    /**
     * Failure test for setValidationErrorsKey. The value is null, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setValidationErrorsKey_Null() {
        try {
            instance.setValidationErrorsKey(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setValidationErrorsKey. The value is empty, so IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setValidationErrorsKey_Empty() {
        try {
            instance.setValidationErrorsKey("  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
