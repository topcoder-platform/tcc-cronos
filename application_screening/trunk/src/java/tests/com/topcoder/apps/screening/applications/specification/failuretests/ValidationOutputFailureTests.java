/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.apps.screening.applications.specification.failuretests;

import junit.framework.TestCase;

import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLDiagram;
import com.topcoder.util.xmi.parser.data.UMLElement;


/**
 * <p>
 * Failure tests for <code>ValidationOutput</code>.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class ValidationOutputFailureTests extends TestCase {
    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, String)</code>.
     */
    public void testValidationOutputValidationOutputTypeString() {
        try {
            new ValidationOutput(ValidationOutputType.REPORT, "details");

            // expected
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        }
    }

    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, String)</code>, with null type, IAE expected.
     */
    public void testValidationOutputValidationOutputTypeString_null_type() {
        try {
            new ValidationOutput(null, "details");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, String)</code>, with null details, IAE expected.
     */
    public void testValidationOutputValidationOutputTypeString_null_details() {
        try {
            new ValidationOutput(ValidationOutputType.REPORT, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, String)</code>, with empty details, IAE expected.
     */
    public void testValidationOutputValidationOutputTypeString_empty_details() {
        try {
            new ValidationOutput(ValidationOutputType.REPORT, "     ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, UMLDiagram, String)</code>.
     */
    public void testValidationOutputValidationOutputTypeUMLDiagramString() {
        try {
            new ValidationOutput(ValidationOutputType.REPORT, new UMLDiagram(), "details");

            // expected
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        }
    }

    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, UMLDiagram, String)</code>, with null type, IAE
     * expected.
     */
    public void testValidationOutputValidationOutputTypeUMLDiagramString_null_type() {
        try {
            new ValidationOutput(null, new UMLDiagram(), "details");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, UMLDiagram, String)</code>, with null diagram, IAE
     * expected.
     */
    public void testValidationOutputValidationOutputTypeUMLDiagramString_null_diagram() {
        try {
            new ValidationOutput(ValidationOutputType.REPORT, null, "details");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, UMLDiagram, String)</code>, with null details, IAE
     * expected.
     */
    public void testValidationOutputValidationOutputTypeUMLDiagramString_null_details() {
        try {
            new ValidationOutput(ValidationOutputType.REPORT, new UMLDiagram(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, UMLDiagram, String)</code>, with empty details,
     * IAE expected.
     */
    public void testValidationOutputValidationOutputTypeUMLDiagramString_empty_details() {
        try {
            new ValidationOutput(ValidationOutputType.REPORT, new UMLDiagram(), "       ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, UMLDiagram, UMLElement, String)</code>.
     */
    public void testValidationOutputValidationOutputTypeUMLDiagramUMLElementString() {
        try {
            new ValidationOutput(ValidationOutputType.REPORT, new UMLDiagram(), new UMLElement(), "details");

            // expected
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        }
    }

    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, UMLDiagram, UMLElement, String)</code>, with null
     * type, IAE expected.
     */
    public void testValidationOutputValidationOutputTypeUMLDiagramUMLElementString_null_type() {
        try {
            new ValidationOutput(null, new UMLDiagram(), new UMLElement(), "details");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, UMLDiagram, UMLElement, String)</code>, with null
     * diagram, IAE expected.
     */
    public void testValidationOutputValidationOutputTypeUMLDiagramUMLElementString_null_diagram() {
        try {
            new ValidationOutput(ValidationOutputType.REPORT, null, new UMLElement(), "details");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, UMLDiagram, UMLElement, String)</code>, with null
     * element, IAE expected.
     */
    public void testValidationOutputValidationOutputTypeUMLDiagramUMLElementString_null_element() {
        try {
            new ValidationOutput(ValidationOutputType.REPORT, new UMLDiagram(), null, "details");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, UMLDiagram, UMLElement, String)</code>, with null
     * details, IAE expected.
     */
    public void testValidationOutputValidationOutputTypeUMLDiagramUMLElementString_null_details() {
        try {
            new ValidationOutput(ValidationOutputType.REPORT, new UMLDiagram(), new UMLElement(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationOutput(ValidationOutputType, UMLDiagram, UMLElement, String)</code>, with empty
     * details, IAE expected.
     */
    public void testValidationOutputValidationOutputTypeUMLDiagramUMLElementString_empty_details() {
        try {
            new ValidationOutput(ValidationOutputType.REPORT, new UMLDiagram(), new UMLElement(), "     ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
