/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultUseCaseDiagramValidator.java
 */
package com.topcoder.apps.screening.applications.specification.impl.validators;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLElement;
import com.topcoder.util.xmi.parser.data.UMLUseCase;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * This class provides default validation of a use case diagram.
 * </p>
 *
 * <p>
 * The following conditions will be checked:
 * <ul>
 * <li>the diagram should have at least one use case</li>
 * <li>the diagram should have at least one actor</li>
 * <li>all use cases should be concrete (no abstract use cases are allowed)</li>
 * <li>all use cases should have non-empty names</li>
 * <li>all actors should have non-empty names</li>
 * <li>the diagram should have at least one concret use case</li>
 * </ul>
 * </p>
 *
 * <p>
 * Thread-Safety: This class is stateless and therefore thread-safe.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public class DefaultUseCaseDiagramValidator extends UseCaseDiagramValidator {

    /**
     * <p>
     * Default class constructor.
     * </p>
     */
    public DefaultUseCaseDiagramValidator() {
        // does nothing
    }

    /**
     * <p>
     * Concrete validation method implementing the checks documented in the class docs.
     * The validator should check that at least one use case and at least one actor exist,
     * all use cases are concrete (no abstract ones) and all use cases/actors have non-empty names.
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

        List retList = new ArrayList();

        //validate use cases
        boolean hasConcreteUseCase = false;
        List useCases = useCaseDiagram.getUseCases();
        if (useCases.isEmpty()) {
            retList.add(new ValidationOutput(ValidationOutputType.ERROR, useCaseDiagram,
                    "the use case diagram is missing an use case"));
        }
        for (Iterator iter = useCases.iterator(); iter.hasNext();) {
            UMLUseCase useCase = (UMLUseCase) iter.next();
            if (!useCase.isAbstract()) {
                hasConcreteUseCase = true;
            } else {
                retList.add(new ValidationOutput(ValidationOutputType.ERROR, useCaseDiagram, useCase,
                        "the use case diagram has an abstract use case"));
            }

            if (useCase.getElementName() == null || useCase.getElementName().trim().length() == 0) {
                retList.add(new ValidationOutput(ValidationOutputType.ERROR, useCaseDiagram, useCase,
                        "the use case has empty name"));
            }
        }
        if (!useCases.isEmpty() && !hasConcreteUseCase) {
            retList.add(new ValidationOutput(ValidationOutputType.ERROR, useCaseDiagram,
                    "the use case diagram is missing a concrete use case"));
        }

        //validate actors
        List actors = useCaseDiagram.getActors();
        if (actors.isEmpty()) {
            retList.add(new ValidationOutput(ValidationOutputType.ERROR, useCaseDiagram,
                    "the use case diagram is missing an actor"));
        }
        for (Iterator iter = actors.iterator(); iter.hasNext();) {
            UMLElement actor = (UMLElement) iter.next();
            if (actor.getElementName() == null || actor.getElementName().trim().length() == 0) {
                retList.add(new ValidationOutput(ValidationOutputType.ERROR, useCaseDiagram, actor,
                        "the actor has empty name"));
            }
        }

        return (ValidationOutput[]) retList.toArray(new ValidationOutput[retList.size()]);
    }
}
