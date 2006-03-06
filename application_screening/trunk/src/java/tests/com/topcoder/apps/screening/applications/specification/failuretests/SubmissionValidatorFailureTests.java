/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.apps.screening.applications.specification.failuretests;

import junit.framework.TestCase;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.SubmissionValidator;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;


/**
 * <p>
 * Failure tests for <code>SubmissionValidatorion</code>.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class SubmissionValidatorFailureTests extends TestCase {
    /**
     * Failure test for <code>SubmissionValidator()</code>.
     */
    public void testSubmissionValidator() {
        try {
            new SimpleSubmissionValidator();

            // expected
        } catch (Exception e) {
            fail("Exception should not be thrown.");
        }
    }

    /**
     * Subclass of SubmissionValidator, used for failure tests.
     */
    static class SimpleSubmissionValidator extends SubmissionValidator {
        public SimpleSubmissionValidator() {
            super();
        }

        public ValidationOutput[] validateSubmission(Submission submission) {
            return null;
        }
    }
}
