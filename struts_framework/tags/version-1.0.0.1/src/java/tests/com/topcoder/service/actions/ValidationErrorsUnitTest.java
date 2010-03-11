/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>ValidationErrors</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ValidationErrorsUnitTest extends TestCase {

    /**
     * The instance used in testing.
     */
    private ValidationErrors instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        instance = new ValidationErrors();
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to junit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    @Test
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for getter and setter for errors. Verifies the setter sets the value properly and that
     * the getter gets the value properly.
     */
    @Test
    public void test_errorsGetterAndSetter_Accuracy1() {
        assertNull("incorrect default value", instance.getErrors());

        ValidationErrorRecord errRec1 = new ValidationErrorRecord();
        String[] messages1 = new String[] {"message1", "message2"};
        errRec1.setMessages(messages1);
        errRec1.setPropertyName("prop1");

        ValidationErrorRecord errRec2 = new ValidationErrorRecord();
        String[] messages2 = new String[] {"message3", "message4"};
        errRec2.setMessages(messages2);
        errRec2.setPropertyName("prop2");

        ValidationErrorRecord[] errors = new ValidationErrorRecord[] {errRec1, errRec2};

        // set the value with the setter
        instance.setErrors(errors);

        // validate the results using the getter
        assertSame("incorrect value after setting", errors, instance.getErrors());

        assertEquals("wrong number of errors", errors.length, instance.getErrors().length);

        assertEquals("wrong property name for first error record", errors[0].getPropertyName(),
            instance.getErrors()[0].getPropertyName());
        assertEquals("wrong first message for first error record", errors[0].getMessages()[0],
            instance.getErrors()[0].getMessages()[0]);
        assertEquals("wrong second message for first error record", errors[0].getMessages()[1],
            instance.getErrors()[0].getMessages()[1]);

        assertEquals("wrong property name for second error record", errors[1].getPropertyName(),
            instance.getErrors()[1].getPropertyName());
        assertEquals("wrong first message for second error record", errors[1].getMessages()[0],
            instance.getErrors()[1].getMessages()[0]);
        assertEquals("wrong second message for second error record", errors[1].getMessages()[1],
            instance.getErrors()[1].getMessages()[1]);

    }

    /**
     * Accuracy test for getter and setter for errors. Verifies that null can be assigned to errors.
     */
    @Test
    public void test_errorsGetterAndSetter_Accuracy2() {
        assertNull("incorrect default value", instance.getErrors());

        // set the value with the setter
        instance.setErrors(null);

        // validate result
        assertNull("errors should be null", instance.getErrors());
    }

}
