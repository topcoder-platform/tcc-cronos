package com.topcoder.apps.screening.applications.specification.accuracytests;

import java.io.File;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputFormatter;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.apps.screening.applications.specification.impl.formatters.XMLValidationOutputFormatter;
import com.topcoder.util.xmi.parser.data.UMLDiagram;
import com.topcoder.util.xmi.parser.data.UMLElement;

public class XMLValidationOutputFormatterTest extends TestCase {

    /**
     * Setting for validating via XML Schema.
     */
    private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

    /**
     * Setting for validating via XML Schema.
     */
    private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

    /**
     * Setting for validating via XML Schema.
     */
    private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    private UMLDiagram diagram;

    private UMLElement element;

    private String details;

    private ValidationOutput validationOutput1;

    private ValidationOutput validationOutput2;

    private ValidationOutput validationOutput3;

    private ValidationOutputFormatter xmlValidationOutputFormatter;

    /**
     * Creates a test suite for the tests in this test case.
     * 
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        return new TestSuite(XMLValidationOutputFormatterTest.class);
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
        xmlValidationOutputFormatter = new XMLValidationOutputFormatter();
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.formatters.TextValidationOutputFormatter.format(ValidationOutput[])'
     */
    public final void testFormatXMLDefinitionExists() throws Exception {
        assertTrue(
                "xml definition does not exists",
                xmlValidationOutputFormatter
                        .format(new ValidationOutput[] { validationOutput1 })[0]
                        .indexOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?>") != -1);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.formatters.TextValidationOutputFormatter.format(ValidationOutput[])'
     */
    public final void testFormatFirstOutputSchemaValidation() throws Exception {
        assertTrue(
                "xml was not generated according to Schema definition",
                isXmlValid(xmlValidationOutputFormatter
                        .format(new ValidationOutput[] { validationOutput1 })[0]));
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.formatters.TextValidationOutputFormatter.format(ValidationOutput[])'
     */
    public final void testFormatSecondOutputSchemaValidation() throws Exception {
        assertTrue(
                "xml was not generated according to Schema definition",
                isXmlValid(xmlValidationOutputFormatter
                        .format(new ValidationOutput[] { validationOutput2 })[0]));
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.formatters.TextValidationOutputFormatter.format(ValidationOutput[])'
     */
    public final void testFormatThirdOutputSchemaValidation() throws Exception {
        assertTrue(
                "xml was not generated according to Schema definition",
                isXmlValid(xmlValidationOutputFormatter
                        .format(new ValidationOutput[] { validationOutput3 })[0]));
    }

    public final void testFormatAllOutputSchemaValidation() throws Exception {
        assertTrue("xml was not generated according to Schema definition",
                isXmlValid(xmlValidationOutputFormatter
                        .format(new ValidationOutput[] { validationOutput1,
                                validationOutput2, validationOutput3 })[0]));
    }

    public final void testFormatGroupingDiagramsErrors() throws Exception {
        // initializing outputs; diagrams the same
        validationOutput1 = new ValidationOutput(ValidationOutputType.ERROR,
                diagram, element, details + 1);
        validationOutput2 = new ValidationOutput(ValidationOutputType.ERROR,
                diagram, element, details + 2);
        validationOutput3 = new ValidationOutput(ValidationOutputType.ERROR,
                diagram, element, details + 3);
        String formatted = xmlValidationOutputFormatter
                .format(new ValidationOutput[] { validationOutput1,
                        validationOutput2, validationOutput3 })[0];
        String expected = "<diagram id=\""
                + diagram.getDiagramId()
                + "\" name=\""
                + diagram.getDiagramName()
                + "\" type=\""
                + diagram.getDiagramType()
                + "\"><element id=\""
                + element.getElementId()
                + "\" name=\""
                + element.getElementName()
                + "\" type=\""
                + element.getElementType()
                + "\"><error>details1</error><error>details2</error><error>details3</error></element></diagram>";
        assertTrue("xml was not generated not correctly", formatted.replaceAll(
                "\n", "").indexOf(expected) != -1);
    }

    public final void testFormatGroupingDiagramsElementsErrors()
            throws Exception {
        validationOutput1 = new ValidationOutput(ValidationOutputType.ERROR,
                diagram, element, details + 1);
        validationOutput2 = new ValidationOutput(ValidationOutputType.ERROR,
                diagram, element, details + 2);
        UMLElement anotherElement = new UMLElement();
        anotherElement.setElementType("elementType" + 1);
        anotherElement.setElementName("elementName" + 1);
        anotherElement.setElementId("elementId" + 1);
        validationOutput3 = new ValidationOutput(ValidationOutputType.ERROR,
                diagram, anotherElement, details + 3);
        String formatted = xmlValidationOutputFormatter
                .format(new ValidationOutput[] { validationOutput1,
                        validationOutput2, validationOutput3 })[0];
        String expected = "<diagram id=\""
                + diagram.getDiagramId()
                + "\" name=\""
                + diagram.getDiagramName()
                + "\" type=\""
                + diagram.getDiagramType()
                + "\"><element id=\""
                + element.getElementId()
                + "\" name=\""
                + element.getElementName()
                + "\" type=\""
                + element.getElementType()
                + "\"><error>details1</error><error>details2</error></element><element id=\""
                + anotherElement.getElementId() + "\" name=\""
                + anotherElement.getElementName() + "\" type=\""
                + anotherElement.getElementType()
                + "\"><error>details3</error></element></diagram>";
        assertTrue("xml was not generated not correctly", formatted.replaceAll(
                "\n", "").indexOf(expected) != -1);
    }

    public final void testFormatGroupingDiagramsReports() throws Exception {
        // initializing outputs; diagrams the same
        validationOutput1 = new ValidationOutput(ValidationOutputType.REPORT,
                diagram, element, details + 1);
        validationOutput2 = new ValidationOutput(ValidationOutputType.REPORT,
                diagram, element, details + 2);
        validationOutput3 = new ValidationOutput(ValidationOutputType.REPORT,
                diagram, element, details + 3);
        String formatted = xmlValidationOutputFormatter
                .format(new ValidationOutput[] { validationOutput1,
                        validationOutput2, validationOutput3 })[0];
        String expected = "<diagram id=\""
                + diagram.getDiagramId()
                + "\" name=\""
                + diagram.getDiagramName()
                + "\" type=\""
                + diagram.getDiagramType()
                + "\"><element id=\""
                + element.getElementId()
                + "\" name=\""
                + element.getElementName()
                + "\" type=\""
                + element.getElementType()
                + "\"><report>details1</report><report>details2</report><report>details3</report></element></diagram>";
        assertTrue("xml was not generated not correctly", formatted.replaceAll(
                "\n", "").indexOf(expected) != -1);
    }

    public final void testFormatGroupingDiagramsElementsReports()
            throws Exception {
        validationOutput1 = new ValidationOutput(ValidationOutputType.REPORT,
                diagram, element, details + 1);
        validationOutput2 = new ValidationOutput(ValidationOutputType.REPORT,
                diagram, element, details + 2);
        UMLElement anotherElement = new UMLElement();
        anotherElement.setElementType("elementType" + 1);
        anotherElement.setElementName("elementName" + 1);
        anotherElement.setElementId("elementId" + 1);
        validationOutput3 = new ValidationOutput(ValidationOutputType.REPORT,
                diagram, anotherElement, details + 3);
        String formatted = xmlValidationOutputFormatter
                .format(new ValidationOutput[] { validationOutput1,
                        validationOutput2, validationOutput3 })[0];
        String expected = "<diagram id=\""
                + diagram.getDiagramId()
                + "\" name=\""
                + diagram.getDiagramName()
                + "\" type=\""
                + diagram.getDiagramType()
                + "\"><element id=\""
                + element.getElementId()
                + "\" name=\""
                + element.getElementName()
                + "\" type=\""
                + element.getElementType()
                + "\"><report>details1</report><report>details2</report></element><element id=\""
                + anotherElement.getElementId() + "\" name=\""
                + anotherElement.getElementName() + "\" type=\""
                + anotherElement.getElementType()
                + "\"><report>details3</report></element></diagram>";
        assertTrue("xml was not generated not correctly", formatted.replaceAll(
                "\n", "").indexOf(expected) != -1);
    }

    /**
     * Checking that xml is valid via Schema.
     * 
     * @param xml
     *            xml to validate
     * @param xsdFileName
     *            file, under which Schema is located
     * @return that xml is valid
     * @throws Exception
     *             wrap all exceptions
     */
    private boolean isXmlValid(String xml) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        SAXParser parser=factory.newSAXParser();
        parser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
        parser.setProperty(JAXP_SCHEMA_SOURCE, new File(
                "test_files/accuracytests/XMLFormatter.xsd"));
        ErrorHandler handler = new ErrorHandler();
        parser.parse(new InputSource(new StringReader(xml)),handler);
        return handler.isValid();
    }
}

class ErrorHandler extends DefaultHandler {

    /**
     * defining that xml is valid.
     */
    private boolean valid = true;

    /**
     * if errors occurs.
     * 
     * @param e
     *            exception
     * @throws SAXException
     *             if xml is not valid
     */
    public void error(SAXParseException e) {
        valid = false;
        System.err.println(e.getMessage());
    }

    /**
     * is xml valid.
     * 
     * @return is valid
     */
    public boolean isValid() {
        return valid;
    }
}