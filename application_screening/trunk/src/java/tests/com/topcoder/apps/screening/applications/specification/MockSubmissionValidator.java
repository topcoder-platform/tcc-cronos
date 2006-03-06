/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * SubmissionValidatorMock.java
 */
package com.topcoder.apps.screening.applications.specification;

/**
 * <p>
 * Mock implementation of <code>SubmissionValidatorMock</code> class.
 * Used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockSubmissionValidator extends SubmissionValidator {

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public MockSubmissionValidator() {
    }

    /**
     * <p>
     * Dummy implementation of validateSubmission method.
     * </p>
     *
     * @param submission submission
     * @return empty array
     */
    public ValidationOutput[] validateSubmission(Submission submission) {
        return new ValidationOutput[0];
    }
}
