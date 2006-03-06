package com.topcoder.apps.screening.applications.specification.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLDiagram;
import com.topcoder.util.xmi.parser.data.UMLElement;

public class ValidationOutputTest extends TestCase {

    private ValidationOutputType type = ValidationOutputType.REPORT;

    private UMLDiagram diagram = new UMLDiagram();

    private UMLElement element = new UMLElement();

    private String details = "details";

    private ValidationOutput validationOutput;

    /**
     * Creates a test suite for the tests in this test case.
     * 
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        return new TestSuite(ValidationOutputTest.class);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutput.getType()'
     */
    public final void testGetTypeFirstConstructor() {
        validationOutput = new ValidationOutput(type, details);
        assertNotNull("must be not null", validationOutput.getType());
        assertEquals("types must be equal", type, validationOutput.getType());
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutput.getType()'
     */
    public final void testGetTypeSecondConstructor() {
        validationOutput = new ValidationOutput(type, diagram, details);
        assertNotNull("must be not null", validationOutput.getType());
        assertEquals("types must be equal", type, validationOutput.getType());
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutput.getType()'
     */
    public final void testGetTypeThirdConstructor() {
        validationOutput = new ValidationOutput(type, diagram, element, details);
        assertNotNull("must be not null", validationOutput.getType());
        assertEquals("types must be equal", type, validationOutput.getType());
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutput.getDiagram()'
     */
    public final void testGetDiagramFirstConstructor() {
        validationOutput = new ValidationOutput(type, details);
        assertNull("must be null", validationOutput.getDiagram());
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutput.getDiagram()'
     */
    public final void testGetDiagramSecondConstructor() {
        validationOutput = new ValidationOutput(type, diagram, details);
        assertNotNull("must be not null", validationOutput.getDiagram());
        assertEquals("diagrams must be equal", diagram, validationOutput.getDiagram());
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutput.getDiagram()'
     */
    public final void testGetDiagramThirdConstructor() {
        validationOutput = new ValidationOutput(type, diagram, element, details);
        assertNotNull("must be not null", validationOutput.getDiagram());
        assertEquals("diagrams must be equal", diagram, validationOutput.getDiagram());
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutput.getElement()'
     */
    public final void testGetElementFirstConstructor() {
        validationOutput = new ValidationOutput(type, details);
        assertNull("must be null", validationOutput.getElement());
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutput.getElement()'
     */
    public final void testGetElementSecondConstructor() {
        validationOutput = new ValidationOutput(type, diagram, details);
        assertNull("must be null", validationOutput.getElement());
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutput.getElement()'
     */
    public final void testGetElementThirdConstructor() {
        validationOutput = new ValidationOutput(type, diagram, element, details);
        assertNotNull("must be not null", validationOutput.getElement());
        assertEquals("elements must be equal", element, validationOutput.getElement());
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutput.getType()'
     */
    public final void testGetDetailsFirstConstructor() {
        validationOutput = new ValidationOutput(type, details);
        assertNotNull("must be not null", validationOutput.getDetails());
        assertEquals("details must be equal", details, validationOutput.getDetails());
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutput.getDetails()'
     */
    public final void testGetDetailsSecondConstructor() {
        validationOutput = new ValidationOutput(type, diagram, details);
        assertNotNull("must be not null", validationOutput.getDetails());
        assertEquals("details must be equal", details, validationOutput.getDetails());
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.ValidationOutput.getDetails()'
     */
    public final void testGetDetailsThirdConstructor() {
        validationOutput = new ValidationOutput(type, diagram, element, details);
        assertNotNull("must be not null", validationOutput.getDetails());
        assertEquals("details must be equal", details, validationOutput.getDetails());
    }
}
