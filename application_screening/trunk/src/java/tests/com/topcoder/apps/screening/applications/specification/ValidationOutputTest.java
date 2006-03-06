/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ValidationOutputTest.java
 */
package com.topcoder.apps.screening.applications.specification;

import com.topcoder.util.xmi.parser.data.UMLDiagram;
import com.topcoder.util.xmi.parser.data.UMLElement;
import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>ValidationOutput</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ValidationOutputTest extends TestCase {

    /**ValidationOutput instance that will be tested.*/
    private ValidationOutput validationOutput;

    /**ValidationOutputType used for testing.*/
    private ValidationOutputType type;

    /**UMLDiagram used for testing.*/
    private UMLDiagram diagram;

    /**UMLElement used for testing.*/
    private UMLElement element;

    /**Details string.*/
    private String details;

    /**
     * <p>
     * Set up environment.
     * </p>
     *
     * @throws Exception exception
     */
    public void setUp() throws Exception {
        type = ValidationOutputType.REPORT;

        diagram = new UMLDiagram();
        diagram.setDiagramName("my_diagram");

        element = new UMLElement();
        element.setElementName("my_element");

        details = "details string";

        validationOutput = new ValidationOutput(type, diagram, element, details);
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, details) if type is null.
     * </p>
     */
    public void testCtor1IfTypeNull() {
        try {
            new ValidationOutput(null, details);
            fail("ValidationOutputType cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, details) if details is null.
     * </p>
     */
    public void testCtor1IfDetailsNull() {
        try {
            new ValidationOutput(type, null);
            fail("Details string cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, details) if details is empty.
     * </p>
     */
    public void testCtor1IfDetailsEmpty() {
        try {
            new ValidationOutput(type, "   ");
            fail("Details string cannot be empty.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, details) on accuracy.
     * </p>
     */
    public void testCtor1Accuracy() {
        validationOutput = new ValidationOutput(type, details);
        assertEquals("Must be equal.", type, validationOutput.getType());
        assertEquals("Must be equal.", details, validationOutput.getDetails());
        assertNull("Diagram must be null.", validationOutput.getDiagram());
        assertNull("Element must be null.", validationOutput.getElement());
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, diagram, details) if type is null.
     * </p>
     */
    public void testCtor2IfTypeNull() {
        try {
            new ValidationOutput(null, diagram, details);
            fail("ValidationOutputType cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, diagram, details) if diagram is null.
     * </p>
     */
    public void testCtor2IfDiagramNull() {
        try {
            new ValidationOutput(type, null, details);
            fail("Diagram cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, diagram, details) if details is null.
     * </p>
     */
    public void testCtor2IfDetailsNull() {
        try {
            new ValidationOutput(type, diagram, null);
            fail("Details string cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, diagram, details) if details is empty.
     * </p>
     */
    public void testCtor2IfDetailsEmpty() {
        try {
            new ValidationOutput(type, diagram, "   ");
            fail("Details string cannot be empty.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, diagram, details) on accuracy.
     * </p>
     */
    public void testCtor2Accuracy() {
        validationOutput = new ValidationOutput(type, diagram, details);
        assertEquals("Must be equal.", type, validationOutput.getType());
        assertEquals("Must be equal.", details, validationOutput.getDetails());
        assertEquals("Must be equal.", diagram, validationOutput.getDiagram());
        assertNull("Element must be null.", validationOutput.getElement());
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, diagram, element, details) if type is null.
     * </p>
     */
    public void testCtor3IfTypeNull() {
        try {
            new ValidationOutput(null, diagram, element, details);
            fail("ValidationOutputType cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, diagram, element, details) if diagram is null.
     * </p>
     */
    public void testCtor3IfDiagramNull() {
        try {
            new ValidationOutput(type, null, element, details);
            fail("Diagram cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, diagram, element, details) if element is null.
     * </p>
     */
    public void testCtor3IfElementNull() {
        try {
            new ValidationOutput(type, diagram, null, details);
            fail("Element cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, diagram, element, details) if details is null.
     * </p>
     */
    public void testCtor3IfDetailsNull() {
        try {
            new ValidationOutput(type, diagram, element, null);
            fail("Details string cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, diagram, element, details) if details is empty.
     * </p>
     */
    public void testCtor3IfDetailsEmpty() {
        try {
            new ValidationOutput(type, diagram, element, "   ");
            fail("Details string cannot be empty.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationOutput(type, diagram, element, details) on accuracy.
     * </p>
     */
    public void testCtor3Accuracy() {
        assertEquals("Must be equal.", type, validationOutput.getType());
        assertEquals("Must be equal.", details, validationOutput.getDetails());
        assertEquals("Must be equal.", diagram, validationOutput.getDiagram());
        assertEquals("Must be equal.", element, validationOutput.getElement());
    }
}