/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * UseCaseDiagramValidatorTest.java
 */
package com.topcoder.apps.screening.applications.specification.impl.validators;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;
import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>UseCaseDiagramValidator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UseCaseDiagramValidatorTest extends TestCase {

    /**UseCaseDiagramValidator instance that will be tested.*/
    private UseCaseDiagramValidator validator;

    /**
     * <p>
     * Set up environment.
     * </p>
     */
    public void setUp() {
        validator = new MockUseCaseDiagramValidator();
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
     * Tests method validateSubmission(submission) if submission does not contain use case diagrams.
     * </p>
     */
    public void testValidateSubmissionIfNoUseCaseDiagrams() {
        ValidationOutput[] outputs = validator.validateSubmission(new Submission(
                new UMLUseCaseDiagram[0], new UMLActivityDiagram[0]));
        //there must be one error output
        assertEquals("There must be 1 output.", 1, outputs.length);
        assertEquals("Output must have ERROR type.", ValidationOutputType.ERROR, outputs[0].getType());
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if submission contains use case diagram
     * (no outputs will be returned).
     * </p>
     */
    public void testValidateSubmission() {
        UMLUseCaseDiagram useCaseDiagram = new UMLUseCaseDiagram();
        ValidationOutput[] outputs = validator.validateSubmission(new Submission(
                new UMLUseCaseDiagram[]{useCaseDiagram}, new UMLActivityDiagram[0]));
        //there must be no error outputs
        assertEquals("There must be no outputs.", 0, outputs.length);
    }
}