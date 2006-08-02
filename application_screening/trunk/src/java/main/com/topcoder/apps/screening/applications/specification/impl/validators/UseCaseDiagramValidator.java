/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * UseCaseDiagramValidator.java
 */
package com.topcoder.apps.screening.applications.specification.impl.validators;

import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.SubmissionValidator;
import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>
 * This abstract class defines validation template for use case diagrams. Validation of submission is
 * implemented as validation of each use case diagram and aggregation of the validation output.
 * </p>
 *
 * <p>
 * All extensions of this class should only provide a primitive for validation of a single use case diagram,
 * this primitive will be called for each use case diagram found in the submission and the resulting data will
 * be aggregated automatically.
 * Additionally, this validator checks a mandatory condition that the submission has at least one use case
 * diagram (if not, an ERROR is reported).
 * </p>
 *
 * <p>
 * Thread-Safety: This class is stateless and therefore thread-safe. All concrete implementations of this
 * class should provide thread-safe implementation of the validateUseCaseDiagram method.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public abstract class UseCaseDiagramValidator extends SubmissionValidator {

    /**
     * <p>
     * Default class constructor (protected).
     * </p>
     */
    protected UseCaseDiagramValidator() {
        // does nothing
    }

    /**
     * <p>
     * This methods defines a validation template.
     * <p>
     *
     * <p>
     * It will do the following:
     * <ul>
     * <li>check if the submission has at least one use case diagram, if not create an error output</li>
     * <li>for each use case diagram in the submission call validateUseCaseDiagram</li>
     * <li>outputs from all calls are merget to a single array</li>
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

        UMLUseCaseDiagram []useCaseDiagrams = submission.getUseCaseDiagrams();
        if (useCaseDiagrams.length == 0) {
            ValidationOutput error = new ValidationOutput(ValidationOutputType.ERROR,
                    "Submission does not contain any use cases");
            return new ValidationOutput[]{error};
        }

        List outputs = new ArrayList();
        for (int i = 0, n = useCaseDiagrams.length; i < n; i++) {
            outputs.addAll(Arrays.asList(validateUseCaseDiagram(useCaseDiagrams[i], submission)));
        }

        return (ValidationOutput[]) outputs.toArray(new ValidationOutput[outputs.size()]);
    }

    /**
     * <p>
     * This is a declaration of an abstract protected method to validate a single use case diagram.
     * Different validation rules can be defined by concrete implementations. The diagram is validated
     * in the scope of the given submission; the validator shouldn't try to validate other diagrams but
     * only the selected one.
     * </p>
     *
     * @param useCaseDiagram diagram to validate
     * @param submission submission containing this diagram
     * @return array of validation outputs
     */
    protected abstract ValidationOutput[] validateUseCaseDiagram(UMLUseCaseDiagram useCaseDiagram,
                                                                 Submission submission);
}