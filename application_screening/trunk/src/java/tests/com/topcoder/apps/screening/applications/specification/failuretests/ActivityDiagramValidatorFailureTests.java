/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.apps.screening.applications.specification.failuretests;

import junit.framework.TestCase;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.impl.validators.ActivityDiagramValidator;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;


/**
 * <p>
 * Failure tests for <code>ActivityDiagramValidator</code>.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class ActivityDiagramValidatorFailureTests extends TestCase {
    /**
     * Failure tests for <code>validateSubmission(Submission)</code>, with null submission, IAE expected.
     */
    public void testValidateSubmission() {
        try {
            (new SimpleActivityDiagramValidator()).validateSubmission(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}


/**
 * Subclass of ActivityDiagramValidator used for failure tests.
 */
class SimpleActivityDiagramValidator extends ActivityDiagramValidator {
    /**
     * Inheritent super validateActivityDiagram.
     *
     * @param activityDiagram The diagram to validate
     * @param submission The submission containing this diagram
     *
     * @return the array of validation outputs
     */
    protected ValidationOutput[] validateActivityDiagram(UMLActivityDiagram activityDiagram, Submission submission) {
        return null;
    }
}
