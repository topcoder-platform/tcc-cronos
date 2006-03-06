package com.topcoder.apps.screening.applications.specification.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputFormatter;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.apps.screening.applications.specification.impl.formatters.TextValidationOutputFormatter;
import com.topcoder.util.xmi.parser.data.UMLDiagram;
import com.topcoder.util.xmi.parser.data.UMLElement;

public class TextValidationOutputFormatterTest extends TestCase {

    private UMLDiagram diagram;

    private UMLElement element;

    private String details;

    private ValidationOutput validationOutput1;

    private ValidationOutput validationOutput2;

    private ValidationOutput validationOutput3;

    private ValidationOutputFormatter textValidationOutputFormatter;

    /**
     * Creates a test suite for the tests in this test case.
     * 
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        return new TestSuite(TextValidationOutputFormatterTest.class);
    }

    protected void setUp() {
        diagram = new UMLDiagram();
        diagram.setDiagramType("diagramType");
        diagram.setDiagramName("diagramName");
        diagram.setDiagramId("diagramId");
        element = new UMLElement();
        element.setElementType("elementType");
        element.setElementName("elementName");
        element.setElementId("elementId");
        details = "details";
        // initializing validation outputs
        validationOutput1 = new ValidationOutput(ValidationOutputType.ERROR,
                details);
        validationOutput2 = new ValidationOutput(ValidationOutputType.REPORT,
                diagram, details);
        validationOutput3 = new ValidationOutput(ValidationOutputType.ERROR,
                diagram, element, details);
        textValidationOutputFormatter = new TextValidationOutputFormatter();
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.formatters.TextValidationOutputFormatter.format(ValidationOutput[])'
     */
    public final void testFormatFirstOutput() throws Exception {
        assertEquals(
                "formatted text must be equal",
                getFormattedString(validationOutput1),
                textValidationOutputFormatter
                        .format(new ValidationOutput[] { validationOutput1 })[0]);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.formatters.TextValidationOutputFormatter.format(ValidationOutput[])'
     */
    public final void testFormatSecondOutput() throws Exception  {
        assertEquals(
                "formatted text must be equal",
                getFormattedString(validationOutput2),
                textValidationOutputFormatter
                        .format(new ValidationOutput[] { validationOutput2 })[0]);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.formatters.TextValidationOutputFormatter.format(ValidationOutput[])'
     */
    public final void testFormatThirdOutput() throws Exception  {
        assertEquals(
                "formatted text must be equal",
                getFormattedString(validationOutput3),
                textValidationOutputFormatter
                        .format(new ValidationOutput[] { validationOutput3 })[0]);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.formatters.TextValidationOutputFormatter.format(ValidationOutput[])'
     */
    public final void testFormatAllOutput() throws Exception {
        String[] formatted = textValidationOutputFormatter
                .format(new ValidationOutput[] { validationOutput1,
                        validationOutput2, validationOutput3 });
        assertEquals("formatted text must be equal",
                getFormattedString(validationOutput1), formatted[0]);
        assertEquals("formatted text must be equal",
                getFormattedString(validationOutput2), formatted[1]);
        assertEquals("formatted text must be equal",
                getFormattedString(validationOutput3), formatted[2]);
    }

    private String getFormattedString(ValidationOutput output) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("OutputType: ");
        buffer.append(output.getType().getType());
        buffer.append("\n");
        if (output.getDiagram() != null) {
            buffer.append("Diagram: ");
            buffer.append(output.getDiagram().getDiagramType() + " ");
            buffer.append(output.getDiagram().getDiagramName());
            buffer.append("(" + output.getDiagram().getDiagramId() + ")");
            buffer.append("\n");
        }
        if (output.getElement() != null) {
            buffer.append("Element: ");
            buffer.append(output.getElement().getElementType() + " ");
            buffer.append(output.getElement().getElementName());
            buffer.append("(" + output.getElement().getElementId() + ")");
            buffer.append("\n");
        }
        buffer.append("Details: ");
        buffer.append(output.getDetails());
        return buffer.toString();
    }
}