/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ActivityDiagramValidatorTest.java
 */
package com.topcoder.apps.screening.applications.specification.impl.validators;

import junit.framework.TestCase;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;

/**
 * <p>
 * Unit tests for <code>ActivityDiagramValidator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ActivityDiagramValidatorTest extends TestCase {

    /**ActivityDiagramValidator instance that will be tested.*/
    private ActivityDiagramValidator validator;

    /**
     * <p>
     * Set up environment.
     * </p>
     */
    public void setUp() {
        validator = new MockActivityDiagramValidator();
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if submission is null.
     * </p>
     */
    public void testValidateSubmissionIfNull() {
        try {
            validator.validateSubmission(null);
            fail("Submission cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if submission does not contain activity diagrams.
     * </p>
     */
    public void testValidateSubmissionIfNoActivityDiagrams() {
        ValidationOutput[] outputs = validator.validateSubmission(new Submission(
                new UMLUseCaseDiagram[0], new UMLActivityDiagram[0]));
        //there must be one error output
        assertEquals("There must be 1 output.", 1, outputs.length);
        assertEquals("Output must have ERROR type.", ValidationOutputType.ERROR, outputs[0].getType());
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if submission contains activity diagram
     * (no outputs will be returned).
     * </p>
     */
    public void testValidateSubmission() {
        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        ValidationOutput[] outputs = validator.validateSubmission(new Submission(
                new UMLUseCaseDiagram[0], new UMLActivityDiagram[]{activityDiagram}));
        //there must be no error outputs
        assertEquals("There must be no outputs.", 0, outputs.length);
    }
}