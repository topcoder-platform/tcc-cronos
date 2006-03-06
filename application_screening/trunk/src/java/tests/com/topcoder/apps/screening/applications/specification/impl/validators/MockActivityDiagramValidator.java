/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * MockActivityDiagramValidator.java
 */
package com.topcoder.apps.screening.applications.specification.impl.validators;

import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;

/**
 * <p>
 * Mock implementation of <code>ActivityDiagramValidator</code> class.
 * Used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockActivityDiagramValidator extends ActivityDiagramValidator {

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public MockActivityDiagramValidator() {
        super();
    }

    /**
     * <p>
     * Dummy implementation of validateActivityDiagram method.
     * </p>
     *
     * @param activityDiagram activity diagram
     * @param submission submission
     * @return empty array
     */
    protected ValidationOutput[] validateActivityDiagram(UMLActivityDiagram activityDiagram, Submission submission) {
        return new ValidationOutput[0];
    }
}
