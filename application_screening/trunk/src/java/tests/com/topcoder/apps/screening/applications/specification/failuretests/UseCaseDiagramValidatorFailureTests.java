/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.apps.screening.applications.specification.failuretests;

import junit.framework.TestCase;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.impl.validators.UseCaseDiagramValidator;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;


/**
 * <p>
 * Failure tests for <code>UseCaseDiagramValidator</code>.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class UseCaseDiagramValidatorFailureTests extends TestCase {
    /**
     * Failure tests for <code>validateSubmission(Submission)</code>, with null submission, IAE expected.
     */
    public void testValidateSubmission_null_submission() {
        UseCaseDiagramValidator validator = new SimpleUseCaseDiagramValidator();

        try {
            validator.validateSubmission(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}


/**
 * <p>
 * Mock implementation for UseCaseDiagramValidator.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
class SimpleUseCaseDiagramValidator extends UseCaseDiagramValidator {
    /**
     * Inheritent super validateUseCaseDiagram.
     *
     * @param useCaseDiagram The diagram to validate
     * @param submission The submission containing this diagram
     *
     * @return the array of validation outputs
     */
    public ValidationOutput[] validateUseCaseDiagram(UMLUseCaseDiagram useCaseDiagram, Submission submission) {
        return null;
    }
}
