/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * TextValidationOutputFormatter.java
 */
package com.topcoder.apps.screening.applications.specification.impl.formatters;

import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputFormatter;
import com.topcoder.util.xmi.parser.data.UMLDiagram;
import com.topcoder.util.xmi.parser.data.UMLElement;

/**
 * <p>
 * This class is a concrete formatter for the validation output. It converts the data to the text format.
 * </p>
 *
 * <p>
 * Format of the output data:
 *
 * <pre>
 * OutputType: [report|error]
 * // only if diagram reference is not empty
 * Diagram: diagramType diagramName(diagramId)
 * // only if element reference is not empty
 * Element: elementType elementName(elementId)
 * Details: details string
 * </pre>
 * </p>
 *
 * <p>
 * Thread-Safety: This class is stateless and therefore thread-safe.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public class TextValidationOutputFormatter implements ValidationOutputFormatter {

    /**'OutputType' keyword. */
    private static final String OUTPUT_TYPE = "OutputType: ";

    /**'Diagram' keyword. */
    private static final String DIAGRAM = "Diagram: ";

    /**'Element' keyword. */
    private static final String ELEMENT = "Element: ";

    /**'Details' keyword. */
    private static final String DETAILS = "Details: ";

    /**New line symbol.*/
    private static final String NEW_LINE = "\n";

    /**Open bracket.*/
    private static final String OPEN_BRACKET = "(";

    /**Close bracket.*/
    private static final String CLOSE_BRACKET = ")";

    /**
     * <p>
     * Default class constructor.
     * </p>
     */
    public TextValidationOutputFormatter() {
        // does nothing
    }

    /**
     * <p>
     * The formatter method which converts the given validation output to the textual representation.
     * Each ValidationOutput instance is converted to a simple text item described in the class documentation.
     * All items are concatenated together and the resulting text is returned.
     * </p>
     *
     * @param output the data to format
     * @return the formatted output
     * @throws IllegalArgumentException if the array or any item in the array is null
     */
    public String[] format(ValidationOutput[] output) {
        if (output == null) {
            throw new IllegalArgumentException("ValidationOutput array must not be null.");
        }
        for (int i = 0, n = output.length; i < n; i++) {
            if (output[i] == null) {
                throw new IllegalArgumentException("ValidationOutput array must not contain null.");
            }
        }
        String []result = new String[output.length];
        for (int i = 0, n = output.length; i < n; i++) {
            result[i] = createOutput(output[i]);
        }
        return result;
    }

    /**
     * <p>
     * This method formats received output:
     * OutputType: [report|error]
     * // only if diagram reference is not empty
     * Diagram: diagramType diagramName(diagramId)
     * // only if element reference is not empty
     * Element: elementType elementName(elementId)
     * Details: details string.
     * </p>
     *
     * @param output ValidationOutput that will be formatted
     * @return string formatted output
     */
    private String createOutput(ValidationOutput output) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(OUTPUT_TYPE).append(output.getType().getType()).append(NEW_LINE);

        //if diagram not null, than add info about it
        UMLDiagram diagram = output.getDiagram();
        if (diagram != null) {
            //append type
            buffer.append(DIAGRAM).append(diagram.getDiagramType()).append(" ");
            //append name
            buffer.append(diagram.getDiagramName());
            //append id
            buffer.append(OPEN_BRACKET).append(diagram.getDiagramId()).append(CLOSE_BRACKET).append(NEW_LINE);
        }

        //if element not null, than add info about it
        UMLElement element = output.getElement();
        if (element != null) {
            //append type
            buffer.append(ELEMENT).append(element.getElementType()).append(" ");
            //append name
            buffer.append(element.getElementName());
            //append id
            buffer.append(OPEN_BRACKET).append(element.getElementId()).append(CLOSE_BRACKET).append(NEW_LINE);
        }

        //append details
        buffer.append(DETAILS).append(output.getDetails());

        return buffer.toString();
    }
}