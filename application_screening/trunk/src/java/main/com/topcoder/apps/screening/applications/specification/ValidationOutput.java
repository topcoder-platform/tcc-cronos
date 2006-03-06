/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ValidationOutput.java
 */
package com.topcoder.apps.screening.applications.specification;

import com.topcoder.util.xmi.parser.data.UMLDiagram;
import com.topcoder.util.xmi.parser.data.UMLElement;

/**
 * <p>
 * This class represents a single item of the validator output. Note that a single validator can generate
 * many different ValidationOutput items for a single submission. Each validation output has a type
 * (ERROR or REPORT), a related UML diagram, a related UML element and a string of details.
 * Note that the UML element and even the UML diagram may be unspecified (for example,
 * an error is related to the whole submission but not to a particular diagram/element), if so null
 * values will be returned by appropriate class getters.
 * A string of details may be a simple text string or it may have complex structure (as for the
 * activity diagram paths report). The details string and the validation output should be passed
 * to some formatter class to generate a comfortable human readable representation.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is immutable and therefore thread-safe.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public class ValidationOutput {

    /**
     * Represents the type of the validation output (only two types: REPORT and ERROR are defined in
     * the current design). This value is assigned in the constructor and not changed afterwards. It can
     * never be null. The value can be retrieved using getType() method.
     */
    private final ValidationOutputType type;

    /**
     * Represents the UML diagram related with this validation output.
     * This value is assigned in the constructor and not changed afterwards. The value is allowed
     * to be null (null value means that the output is not related to any particular diagram).
     * The value can be retrieved using getDiagram() method.
     */
    private final UMLDiagram diagram;

    /**
     * Represents the UML element (for example, a use case or a state machine state)
     * related with this validation output.
     * This value is assigned in the constructor and not changed afterwards. The value is allowed
     * to be null (null value means that the output is not related to any particular element).
     * The value can be retrieved using getElement() method.
     */
    private final UMLElement element;

    /**
     * Represents the details string for this validation output.
     * This value is assigned in the constructor and not changed afterwards.
     * The value can never be null or an empty string.
     * It can be retrieved using getDetails() method.
     */
    private final String details;

    /**
     * <p>
     * Constructor. Creates the validation output instance with the given parameters. The output is not related
     * to any particular UML diagram or element.
     * </p>
     *
     * @param type    the output type to use
     * @param details the output details
     * @throws IllegalArgumentException if any parameter is null or the details is an empty string
     */
    public ValidationOutput(ValidationOutputType type, String details) {
        if (type == null) {
            throw new IllegalArgumentException("ValidationOutputType must not be null.");
        }
        if (details == null) {
            throw new IllegalArgumentException("Details string must not be null.");
        }
        if (details.trim().length() == 0) {
            throw new IllegalArgumentException("Details string must not be empty.");
        }

        this.type = type;
        this.details = details;
        this.diagram = null;
        this.element = null;
    }

    /**
     * <p>
     * Constructor. Creates the validation output instance with the given parameters. The output is not related
     * to any particular UML element.
     * </p>
     *
     * @param type    the output type to use
     * @param diagram the diagram this output is related to
     * @param details the output details
     * @throws IllegalArgumentException if any parameter is null or the details is an empty string
     */
    public ValidationOutput(ValidationOutputType type, UMLDiagram diagram, String details) {
        if (type == null) {
            throw new IllegalArgumentException("ValidationOutputType must not be null.");
        }
        if (diagram == null) {
            throw new IllegalArgumentException("Diagram must not be null.");
        }
        if (details == null) {
            throw new IllegalArgumentException("Details string must not be null.");
        }
        if (details.trim().length() == 0) {
            throw new IllegalArgumentException("Details string must not be empty.");
        }

        this.type = type;
        this.details = details;
        this.diagram = diagram;
        this.element = null;
    }

    /**
     * <p>
     * Constructor. Creates the validation output instance with the given parameters.
     * </p>
     *
     * @param type    the output type to use
     * @param diagram the diagram this output is related to
     * @param element the element this output is related to
     * @param details the output details
     * @throws IllegalArgumentException if any parameter is null or the details is an empty string
     */
    public ValidationOutput(ValidationOutputType type, UMLDiagram diagram, UMLElement element, String details) {
        if (type == null) {
            throw new IllegalArgumentException("ValidationOutputType must not be null.");
        }
        if (diagram == null) {
            throw new IllegalArgumentException("Diagram must not be null.");
        }
        if (element == null) {
            throw new IllegalArgumentException("Element must not be null.");
        }
        if (details == null) {
            throw new IllegalArgumentException("Details string must not be null.");
        }
        if (details.trim().length() == 0) {
            throw new IllegalArgumentException("Details string must not be empty.");
        }

        this.type = type;
        this.details = details;
        this.diagram = diagram;
        this.element = element;
    }

    /**
     * <p>
     * Returns the output type.
     * </p>
     *
     * @return the output type
     */
    public ValidationOutputType getType() {
        return type;
    }

    /**
     * <p>
     * Returns the diagram for this output (might be null if the output is not related to any UML diagram).
     * </p>
     *
     * @return the diagram this output is related to
     */
    public UMLDiagram getDiagram() {
        return diagram;
    }

    /**
     * <p>
     * Returns the element for this output (might be null if the output is not related to any UML element).
     * </p>
     *
     * @return the element this output is related to
     */
    public UMLElement getElement() {
        return element;
    }

    /**
     * <p>
     * Returns the output details.
     * </p>
     *
     * @return the output details
     */
    public String getDetails() {
        return details;
    }
}