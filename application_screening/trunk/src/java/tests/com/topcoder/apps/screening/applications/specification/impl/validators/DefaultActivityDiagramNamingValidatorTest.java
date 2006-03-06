/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultActivityDiagramNamingValidatorTest.java
 */
package com.topcoder.apps.screening.applications.specification.impl.validators;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLUseCase;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;
import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>DefaultActivityDiagramNamingValidator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultActivityDiagramNamingValidatorTest extends TestCase {

    /**DefaultActivityDiagramNamingValidator instance that will be tested.*/
    private DefaultActivityDiagramNamingValidator activityDiagramNamingValidator;

    /**UMLActivityDiagram that is used for testing.*/
    private UMLActivityDiagram activityDiagram;

    /**UMLUseCaseDiagrams array.*/
    private UMLUseCaseDiagram []useCaseDiagrams;

    /**Submission that is used for testing.*/
    private Submission submission;

    /**
     * <p>
     * Set up environment.
     * </p>
     *
     * @throws Exception exception
     */
    public void setUp() throws Exception {
        activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("First Use Case activity diagram");

        UMLUseCaseDiagram diagram1 = new UMLUseCaseDiagram();
        diagram1.setDiagramName("Use case diagram1");
        UMLUseCase useCase1 = new UMLUseCase();
        useCase1.setElementName("use case 1");
        diagram1.addElement("1", useCase1);
        UMLUseCase useCase2 = new UMLUseCase();
        useCase2.setElementName("use case 2");
        diagram1.addElement("2", useCase2);

        UMLUseCaseDiagram diagram2 = new UMLUseCaseDiagram();
        diagram2.setDiagramName("Use case diagram2");
        UMLUseCase useCase3 = new UMLUseCase();
        useCase3.setElementName("use case 3");
        diagram2.addElement("3", useCase3);

        useCaseDiagrams = new UMLUseCaseDiagram[]{diagram1, diagram2};

        submission = new Submission(useCaseDiagrams, new UMLActivityDiagram[]{activityDiagram});

        activityDiagramNamingValidator = new DefaultActivityDiagramNamingValidator();
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram is null.
     * </p>
     */
    public void testValidateActivityDiagramIfDiagramNull() {
        try {
            activityDiagramNamingValidator.validateActivityDiagram(null, submission);
            fail("ActivityDiagram cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if submission is null.
     * </p>
     */
    public void testValidateActivityDiagramIfSubmissionNull() {
        try {
            activityDiagramNamingValidator.validateActivityDiagram(activityDiagram, null);
            fail("Submission cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram does not
     * have 'Activity' keyword in the name.
     * </p>
     */
    public void testValidateActivityDiagramIfNoKeyWordInName() {
        activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("Diagram name");

        submission = new Submission(useCaseDiagrams, new UMLActivityDiagram[]{activityDiagram});
        ValidationOutput []outputs = activityDiagramNamingValidator.
                validateActivityDiagram(activityDiagram, submission);
        //there must be one error
        assertEquals("There must be 1 error output.", 1, outputs.length);
        checkAndPrintOutputs(outputs);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram does not
     * have corresponding UseCaseDiagram.
     * </p>
     */
    public void testValidateActivityDiagramIfNoMatching() {
        submission = new Submission(useCaseDiagrams, new UMLActivityDiagram[]{activityDiagram});
        ValidationOutput []outputs = activityDiagramNamingValidator.
                validateActivityDiagram(activityDiagram, submission);
        //there must be one error
        assertEquals("There must be 1 error output.", 1, outputs.length);
        checkAndPrintOutputs(outputs);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram has
     * have corresponding UseCaseDiagram.
     * </p>
     */
    public void testValidateActivityDiagram() {
        UMLUseCase match = new UMLUseCase();
        match.setElementName("First Use Case");
        useCaseDiagrams[1].addElement("100", match);
        submission = new Submission(useCaseDiagrams, new UMLActivityDiagram[]{activityDiagram});
        ValidationOutput []outputs = activityDiagramNamingValidator.
                validateActivityDiagram(activityDiagram, submission);
        //there must be no errors
        assertEquals("There must be no error outputs.", 0, outputs.length);
    }

    /**
     * <p>
     * Check each output whether ERROR or not, and print details.
     * </p>
     *
     * @param outputs ValidationOutputs array
     */
    private void checkAndPrintOutputs(ValidationOutput []outputs) {
        for (int i = 0, n = outputs.length; i < n; i++) {
            assertEquals("Output must be error.", ValidationOutputType.ERROR, outputs[i].getType());
            if (outputs[i].getDiagram() != null) {
                System.out.print("Diagram name: " + outputs[i].getDiagram().getDiagramName() + "; ");
            }
            System.out.print("Error ouput details: " + outputs[i].getDetails());
            if (outputs[i].getElement() != null) {
                if (outputs[i].getElement().getElementName() != null
                        && outputs[i].getElement().getElementName().trim().length() != 0) {
                    System.out.println(" - " + outputs[i].getElement().getElementName() + "; ");
                } else {
                    System.out.println();
                }
            } else {
                System.out.println();
            }
        }
        System.out.println();
    }
}