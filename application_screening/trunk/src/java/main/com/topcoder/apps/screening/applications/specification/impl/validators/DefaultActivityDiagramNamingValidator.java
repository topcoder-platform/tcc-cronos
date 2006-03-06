/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultActivityDiagramNamingValidator.java
 */
package com.topcoder.apps.screening.applications.specification.impl.validators;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLElement;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * This class provides default naming validation of an activity diagram.
 * It should check that the activity diagram has at least one corresponding use case.
 * </p>
 *
 * <p>
 * The activity diagram name should start from a use case name following the &quot;Activity&quot; keyword
 * and possibly an optional part.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is stateless and therefore thread-safe.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public class DefaultActivityDiagramNamingValidator extends ActivityDiagramValidator {

    /**'activity' keyword.*/
    private static final String ACTIVITY_KEYWORD = "activity";

    /**
     * <p>
     * Default class constructor.
     * </p>
     */
    public DefaultActivityDiagramNamingValidator() {
        // does nothing
    }

    /**
     * <p>
     * Concrete validation method implementing the checks documented in the class docs.
     * The validator should check that at least one use case matches the activity diagram.
     * Note, that comparison is case insensitive.
     * </p>
     *
     * @param activityDiagram the diagram to validate
     * @param submission     the submission containing this diagram
     * @return the array of validation outputs
     * @throws IllegalArgumentException if any parameter is null
     */
    protected ValidationOutput[] validateActivityDiagram(UMLActivityDiagram activityDiagram,
                                                         Submission submission) {
        if (activityDiagram == null) {
            throw new IllegalArgumentException("UseCaseDiagram must not be null.");
        }
        if (submission == null) {
            throw new IllegalArgumentException("Submission must not be null.");
        }
        if (activityDiagram.getDiagramName() == null || activityDiagram.getDiagramName().trim().length() == 0) {
            ValidationOutput output = new ValidationOutput(ValidationOutputType.ERROR, activityDiagram,
                    "the activity diagram name is missing");
            return new ValidationOutput[]{output};
        }

        /**
         * 'First Use Case Activity diagram' - sample name
         * Use case name should be 'First Use Case'
         */
        String adName = activityDiagram.getDiagramName().trim().toLowerCase();
        //check whether keyword presents in name
        int idx = adName.indexOf(ACTIVITY_KEYWORD);

        if (idx == -1) {
            ValidationOutput output = new ValidationOutput(ValidationOutputType.ERROR, activityDiagram,
                    "the activity diagram name is missing the 'Activity' keyword");
            return new ValidationOutput[]{output};
        }

        //get name of use case
        String matchWith = adName.substring(0, idx).trim();
        UMLUseCaseDiagram []useCaseDiagrams = submission.getUseCaseDiagrams();
        for (int i = 0, n = useCaseDiagrams.length; i < n; i++) {
            List useCases = useCaseDiagrams[i].getUseCases();
            for (Iterator iter = useCases.iterator(); iter.hasNext();) {
                UMLElement useCase = (UMLElement) iter.next();
                //if UseCase's name is null or empty, than we will skip this UseCase
                if (useCase.getElementName() != null
                        && useCase.getElementName().trim().toLowerCase().equals(matchWith)) {
                    return new ValidationOutput[0];
                }
            }
        }

        ValidationOutput output = new ValidationOutput(ValidationOutputType.ERROR, activityDiagram,
                "the activity diagram doesn't have any corresponding use cases");

        return new ValidationOutput[]{output};
    }
}