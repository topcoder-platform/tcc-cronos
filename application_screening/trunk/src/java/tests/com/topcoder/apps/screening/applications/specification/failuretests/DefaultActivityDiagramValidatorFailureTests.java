/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.apps.screening.applications.specification.failuretests;

import junit.framework.TestCase;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultActivityDiagramValidator;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;


/**
 * <p>
 * Failure tests for <code>DefaultActivityDiagramValidator</code>.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class DefaultActivityDiagramValidatorFailureTests extends TestCase {
    /**
     * Failure tests for <code>validateActivityDiagram(UMLActivityDiagram, Submission)</code>, with null
     * activityDiagram, IAE expected.
     */
    public void testValidateActivityDiagram_null_activityDiagram() {
        SimpleDefaultActivityDiagramValidator validator = new SimpleDefaultActivityDiagramValidator();

        try {
            validator.validateActivityDiagram(null, new Submission(new UMLUseCaseDiagram[0], new UMLActivityDiagram[0]));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for <code>validateActivityDiagram(UMLActivityDiagram, Submission)</code>, with null submission,
     * IAE expected.
     */
    public void testValidateActivityDiagram_null_submission() {
        SimpleDefaultActivityDiagramValidator validator = new SimpleDefaultActivityDiagramValidator();

        try {
            validator.validateActivityDiagram(new UMLActivityDiagram(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}


/**
 * Subclass of DefaultActivityDiagramValidator used for failure tests.
 */
class SimpleDefaultActivityDiagramValidator extends DefaultActivityDiagramValidator {
    /**
     * Inheritent super validateActivityDiagram.
     *
     * @param activityDiagram The diagram to validate
     * @param submission The submission containing this diagram
     *
     * @return the array of validation outputs
     */
    protected ValidationOutput[] validateActivityDiagram(UMLActivityDiagram activityDiagram, Submission submission) {
        return super.validateActivityDiagram(activityDiagram, submission);
    }
}
