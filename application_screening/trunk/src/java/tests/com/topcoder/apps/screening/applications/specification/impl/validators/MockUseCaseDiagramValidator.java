/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * MockUseCaseDiagramValidator.java
 */
package com.topcoder.apps.screening.applications.specification.impl.validators;

import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;

/**
 * <p>
 * Mock implementation of <code>UseCaseDiagramValidator</code> class.
 * Used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockUseCaseDiagramValidator extends UseCaseDiagramValidator {

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public MockUseCaseDiagramValidator() {
    }

    /**
     * <p>
     * Dummy implementation of validateUseCaseDiagram method.
     * </p>
     *
     * @param useCaseDiagram use case diagram
     * @param submission submission
     * @return empty array
     */
    protected ValidationOutput[] validateUseCaseDiagram(UMLUseCaseDiagram useCaseDiagram, Submission submission) {
        return new ValidationOutput[0];
    }
}
