/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultUseCaseDiagramNamingValidator.java
 */
package com.topcoder.apps.screening.applications.specification.impl.validators;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLElement;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * This class provides default naming validation of a use case diagram.
 * </p>
 *
 * <p>
 * The following conditions will be checked:
 * <ul>
 * <li>for each use case there should be at least one correspondent activity diagram
 *     (if the use case name is X then the activity diagram name can either
 *     be X Activity (- Y) or X Activity Diagram ( - Y))</li>
 * <li>the optional part (- Y) can only be present if there are at least two activity
 *     diagrams corresponding to the use case.</li>
 * </ul>
 * The comparison is case insensitive.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is stateless and therefore thread-safe.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public class DefaultUseCaseDiagramNamingValidator extends UseCaseDiagramValidator {

    /**'activity diagram' keyword.*/
    private static final String ACTIVITY_DIAGRAM_SUFFIX = "activity diagram";

    /**'activity' keyword.*/
    private static final String ACTIVITY_SUFFIX = "activity";

    /**
     * <p>
     * Default class constructor.
     * </p>
     */
    public DefaultUseCaseDiagramNamingValidator() {
        //does nothing
    }

    /**
     * <p>
     * Concrete validation method implementing the checks documented in the class docs.
     * Validator checks for each use case there should be at least one correspondent activity diagram.
     * If there is only one corresponding diagram, its name should exactly match the use case name
     * (no optional part is allowed).
     * </p>
     *
     * @param useCaseDiagram the diagram to validate
     * @param submission     the submission containing this diagram
     * @return the array of validation outputs
     * @throws IllegalArgumentException if any parameter is null
     */
    protected ValidationOutput[] validateUseCaseDiagram(UMLUseCaseDiagram useCaseDiagram,
                                                        Submission submission) {
        if (useCaseDiagram == null) {
            throw new IllegalArgumentException("UseCaseDiagram must not be null.");
        }
        if (submission == null) {
            throw new IllegalArgumentException("Submission must not be null.");
        }

        //list with validation outputs
        List retList = new ArrayList();
        List useCases = useCaseDiagram.getUseCases();
        UMLActivityDiagram []activityDiagrams = submission.getActivityDiagrams();

        for (Iterator iter = useCases.iterator(); iter.hasNext();) {
            UMLElement useCase = (UMLElement) iter.next();
            int cntDiagrams = 0;
            boolean hasExactMatch = false;

            String useCaseName = useCase.getElementName();
            //if UseCase's name is null or empty, add error about it and continue
            if (useCaseName == null || useCaseName.trim().length() == 0) {
                retList.add(new ValidationOutput(ValidationOutputType.ERROR, useCaseDiagram, useCase,
                        "the use case has empty name"));
                continue;
            }

            useCaseName = useCase.getElementName().trim().toLowerCase();
            for (int i = 0, n = activityDiagrams.length; i < n; i++) {
                String adName = activityDiagrams[i].getDiagramName();
                //if activity diagram has null or empty name, than skip it
                if (adName == null || adName.trim().length() == 0) {
                    continue;
                }
                adName = adName.trim().toLowerCase();

                if (!adName.startsWith(useCaseName)) {
                    continue;
                }

                String rem = adName.substring(useCaseName.length()).trim();

                if (rem.startsWith(ACTIVITY_DIAGRAM_SUFFIX)) {
                    rem = rem.substring(ACTIVITY_DIAGRAM_SUFFIX.length());
                } else if (rem.startsWith(ACTIVITY_SUFFIX)) {
                    rem = rem.substring(ACTIVITY_SUFFIX.length());
                } else {
                    continue;
                }

                rem = rem.trim();
                //if some text still exists and not (-Y), than continue
                if (rem.length() != 0 && !rem.startsWith("-")) {
                    continue;
                }

                cntDiagrams++;
                if (rem.length() == 0) {
                    //if no optional part, than this is exact match
                    hasExactMatch = true;
                }
            }

            if (cntDiagrams == 0) {
                retList.add(new ValidationOutput(ValidationOutputType.ERROR, useCaseDiagram, useCase,
                        "the use case doesn't have any corresponding activity diagrams"));
            }

            if (!hasExactMatch && cntDiagrams == 1) {
                retList.add(new ValidationOutput(ValidationOutputType.ERROR, useCaseDiagram, useCase,
                        "the use case with a single corresponding activity diagram should not have "
                                + "the optional part in the activity diagram name"));
            }
        }

        return (ValidationOutput[]) retList.toArray(new ValidationOutput[retList.size()]);
    }
}
