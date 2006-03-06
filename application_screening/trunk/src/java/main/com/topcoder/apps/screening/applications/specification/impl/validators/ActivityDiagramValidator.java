/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ActivityDiagramValidator.java
 */
package com.topcoder.apps.screening.applications.specification.impl.validators;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.apps.screening.applications.specification.SubmissionValidator;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * This abstract class defines validation template for activity diagrams. Validation of submission is
 * implemented as validation of each activity diagram and aggregation of the validation output.
 * </p>
 *
 * <p>
 * All extensions of this class should only provide a primitive for validation of a single activity diagram,
 * this primitive will be called for each activity diagram found in the submission and the resulting data will
 * be aggregated automatically.
 * </p>
 *
 * <p>
 * Additionally, this validator checks a mandatory condition that the submission has at least one activity
 * diagram (if not, an ERROR is reported).
 * </p>
 *
 * <p>
 * Thread-Safety: This class is stateless and therefore thread-safe. All concrete implementations of this
 * class should provide thread-safe implementation of the validateActivityDiagram method.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public abstract class ActivityDiagramValidator extends SubmissionValidator {

    /**
     * Default class constructor (protected). Does nothing and throws no exceptions.
     */
    protected ActivityDiagramValidator() {
        // your code here
    }

    /**
     * <p>
     * This methods defines a validation template.
     * </p>
     *
     * <p>
     * It will do the following:
     * <ul>
     * <li>check if the submission has at least one activity diagram
     *    (if not create an error output and return it)</li>
     * <li>for each activity diagram in the submission call validateActivityDiagram</li>
     * <li>outputs from all calls is merged to a single array</li>
     * </ul>
     * </p>
     *
     * @param submission submission to validate
     * @return array of validation outputs
     * @throws IllegalArgumentException if the parameter is null
     */
    public ValidationOutput[] validateSubmission(Submission submission) {
        if (submission == null) {
            throw new IllegalArgumentException("Submission must not be null.");
        }

        UMLActivityDiagram []activityDiagrams = submission.getActivityDiagrams();
        if (activityDiagrams.length == 0) {
            ValidationOutput output = new ValidationOutput(ValidationOutputType.ERROR,
                    "submission is missing an activity diagram");
            return new ValidationOutput[]{output};
        }

        List result = new ArrayList();
        for (int i = 0, n = activityDiagrams.length; i < n; i++) {
            result.addAll(Arrays.asList(validateActivityDiagram(activityDiagrams[i], submission)));
        }
        return (ValidationOutput[]) result.toArray(new ValidationOutput[result.size()]);
    }

    /**
     * <p>
     * This is a declaration of an abstract protected method to validate a single activity diagram. Different
     * validation rules can be defined by concrete implementations. The diagram is validated in the scope of
     * the given submission; the validator shouldn't try to validate other diagrams but only the selected one.
     * </p>
     *
     * @param activityDiagram diagram to validate
     * @param submission submission containing this diagram
     * @return array of validation outputs
     */
    protected abstract ValidationOutput[] validateActivityDiagram(UMLActivityDiagram activityDiagram,
                                                                  Submission submission);
}