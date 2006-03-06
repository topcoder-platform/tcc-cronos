/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * SubmissionValidatorTest.java
 */
package com.topcoder.apps.screening.applications.specification;

import junit.framework.TestCase;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;

/**
 * <p>
 * Unit tests for <code>SubmissionValidator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SubmissionValidatorTest extends TestCase {

    /**SubmissionValidator instance that will be tested.*/
    private SubmissionValidator validator;

    /**
     * <p>
     * Set up environment.
     * </p>
     */
    public void setUp() {
        validator = new MockSubmissionValidator();
    }

    /**
     * <p>
     * Tests method valid(submission) if submission is null.
     * </p>
     */
    public void testValidIfNotSubmission() {
        assertFalse("False must be returned.", validator.valid(null));
        assertFalse("False must be returned.", validator.valid((Submission) null));
    }

    /**
     * <p>
     * Tests method valid(submission).
     * </p>
     */
    public void testValid() {
        Submission submission = new Submission(new UMLUseCaseDiagram[0], new UMLActivityDiagram[0]);
        assertTrue("True must be returned.", validator.valid(submission));
    }
}