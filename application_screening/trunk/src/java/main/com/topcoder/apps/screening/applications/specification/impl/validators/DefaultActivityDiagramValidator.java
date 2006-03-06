/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultActivityDiagramValidator.java
 */
package com.topcoder.apps.screening.applications.specification.impl.validators;

import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLState;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * <p>
 * This class provides default validation of an activity diagram.
 * </p>
 *
 * <p>
 * The following conditions will be checked:
 * <ul>
 * <li>the diagram should have at least one initial state</li>
 * <li>the diagram should have at least one final state</li>
 * <li>all initial states should have non-empty names</li>
 * <li>all final states should have non-empty names</li>
 * </ul>
 *
 * <p>
 * Thread-Safety: This class is stateless and therefore thread-safe.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public class DefaultActivityDiagramValidator extends ActivityDiagramValidator {

    /**
     * <p>
     * Default class constructor.
     * </p>
     */
    public DefaultActivityDiagramValidator() {
        //does nothing
    }

    /**
     * <p>
     * Concrete validation method implementing the checks documented in the class docs.
     * The validator should check that at least one initial state and at least one final state exist
     * and all initial/final states have non-empty names.
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
            throw new IllegalArgumentException("ActivityDiagram must not be null.");
        }
        if (submission == null) {
            throw new IllegalArgumentException("Submission must not be null.");
        }
        List retList = new ArrayList();

        //checks whether at least one initial state exists or not
        List initialStates = activityDiagram.getInitialStates();
        if (initialStates.isEmpty()) {
            retList.add(new ValidationOutput(ValidationOutputType.ERROR, activityDiagram,
                    "the activity diagram is missing an initial state"));
        }
        //checks initial states' names
        for (Iterator iter = initialStates.iterator(); iter.hasNext();) {
            UMLState state = (UMLState) iter.next();
            if (state.getName() != null && state.getName().trim().length() == 0) {
                retList.add(new ValidationOutput(ValidationOutputType.ERROR,
                        activityDiagram, state, "the initial state has empty name"));
            }
        }

        //checks whether at least one final state exists or not
        List finalStates = activityDiagram.getFinalStates();
        if (finalStates.isEmpty()) {
            retList.add(new ValidationOutput(ValidationOutputType.ERROR, activityDiagram,
                    "the activity diagram is missing a final state"));
        }
        //checks final states' names
        for (Iterator iter = finalStates.iterator(); iter.hasNext();) {
            UMLState state = (UMLState) iter.next();
            if (state.getName() != null && state.getName().trim().length() == 0) {
                retList.add(new ValidationOutput(ValidationOutputType.ERROR,
                        activityDiagram, state, "the final state has empty name"));
            }
        }

        return (ValidationOutput[]) retList.toArray(new ValidationOutput[retList.size()]);
    }
}