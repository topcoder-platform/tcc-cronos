/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.stresstests;

import com.topcoder.service.actions.ValidationErrorRecord;
import com.topcoder.service.actions.ValidationErrors;

import junit.framework.TestCase;

/**
 * Stress tests for <code>SpecificationReviewAction</code>.
 * @author moon.river
 * @version 1.0
 */
public class SpecificationReviewActionTest extends TestCase {

    /**
     * Instance to test.
     */
    private SpecificationReviewActionTester instance;

    /**
     * Sets up the environment.
     * @throws java.lang.Exception to JUnit
     */
    public void setUp() throws Exception {
        instance = new SpecificationReviewActionTester();
    }

    /**
     * Test method for {@link com.topcoder.direct.actions.SpecificationReviewAction
     * #addValidationError(java.lang.String, java.lang.String[])}.
     */
    public void testAddValidationError() {
        // use a large message array
        String[] messages = new String[1000];
        for (int i = 0; i < 1000; i++) {
            messages[i] = "abc";
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            instance.addValidationError("test", messages);
        }
        long end = System.currentTimeMillis();

        // check the result
        ValidationErrors errors = (ValidationErrors) instance.getModel().getData(instance.getValidationErrorsKey());
        assertNotNull("The errors are not added.", errors);
        ValidationErrorRecord[] records = errors.getErrors();
        assertEquals("There should be 1000 record.", 1000, records.length);
        // the records should be all the same
        for (int i = 0; i < 1000; i++) {
            ValidationErrorRecord record = records[i];
            assertEquals("Wrong property name.", "test", record.getPropertyName());
            assertEquals("Wrong messages", messages, record.getMessages());
        }

        System.out.println("AddValidationErrors: " + (end - start) + "ms");
    }

}
