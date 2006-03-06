/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultUseCaseDiagramNamingValidatorTest.java
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
 * Unit tests for <code>DefaultUseCaseDiagramNamingValidator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultUseCaseDiagramNamingValidatorTest extends TestCase {

    /**DefaultUseCaseDiagramNamingValidator instance that will be tested.*/
    private DefaultUseCaseDiagramNamingValidator useCaseDiagramNamingValidator;

    /**Submission that is used for testing.*/
    private Submission submission;

    /**UMLUseCaseDiagram that is used for testing.*/
    private UMLUseCaseDiagram useCaseDiagram;

    /**UMLActivityDiagram that is used for testing.*/
    private UMLActivityDiagram activityDiagram1;

    /**UMLActivityDiagram that is used for testing.*/
    private UMLActivityDiagram activityDiagram2;

    /**UMLActivityDiagram that is used for testing.*/
    private UMLActivityDiagram activityDiagram3;

    /**
     * <p>
     * Set up environment.
     * </p>
     *
     * @throws Exception exception
     */
    public void setUp() throws Exception {
        useCaseDiagramNamingValidator = new DefaultUseCaseDiagramNamingValidator();

        //initialize use case diagram:
        //it will have three use cases
        useCaseDiagram = new UMLUseCaseDiagram();
        useCaseDiagram.setDiagramName("Use Case Diagram");
        UMLUseCase useCase1 = new UMLUseCase();
        useCase1.setElementName("Create validators");
        UMLUseCase useCase2 = new UMLUseCase();
        useCase2.setElementName("Create formatters");
        UMLUseCase useCase3 = new UMLUseCase();
        useCase3.setElementName("Validate submission");
        useCaseDiagram.addElement("1", useCase1);
        useCaseDiagram.addElement("2", useCase2);
        useCaseDiagram.addElement("3", useCase3);

        activityDiagram1 = new UMLActivityDiagram();
        activityDiagram1.setDiagramName("Create validators Activity");
        activityDiagram2 = new UMLActivityDiagram();
        activityDiagram2.setDiagramName("Create formatters activity diagram");
        activityDiagram3 = new UMLActivityDiagram();
        activityDiagram3.setDiagramName("Validate submission activity");

        submission = new Submission(new UMLUseCaseDiagram[]{useCaseDiagram},
                new UMLActivityDiagram[]{activityDiagram1, activityDiagram2, activityDiagram3});
    }

    /**
     * <p>
     * Tests method validateUseCaseDiagram(useCaseDiagram, submission) if diagram is null.
     * </p>
     */
    public void testValidateUseCaseDiagramIfDiagramNull() {
        try {
            useCaseDiagramNamingValidator.validateUseCaseDiagram(null, submission);
            fail("UseCaseDiagram cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validateUseCaseDiagram(useCaseDiagram, submission) if submission is null.
     * </p>
     */
    public void testValidateUseCaseDiagramIfSubmissionNull() {
        try {
            useCaseDiagramNamingValidator.validateUseCaseDiagram(useCaseDiagram, null);
            fail("UseCaseDiagram cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validateUseCaseDiagram(useCaseDiagram, submission) if two use cases
     * do not have corresponding activity diagram.
     * </p>
     */
    public void testValidateUseCaseDiagramIfTwoUseCasesDontHaveActivity() {
        submission = new Submission(new UMLUseCaseDiagram[]{useCaseDiagram},
                new UMLActivityDiagram[]{activityDiagram1});
        ValidationOutput []outputs = useCaseDiagramNamingValidator.validateUseCaseDiagram(useCaseDiagram, submission);
        //there must be 2 outputs
        assertEquals("There must be 2 error outputs.", 2, outputs.length);
        checkAndPrintOutputs(outputs);
    }

    /**
     * <p>
     * Tests method validateUseCaseDiagram(useCaseDiagram, submission) if one use case
     * has single corresponding activity diagram, that has optional part in its name:
     * "&lt;use case name&gt; activity diagram - optional part".
     * </p>
     */
    public void testValidateUseCaseDiagramIfSingleActivityHasOptionalPart() {
        activityDiagram1.setDiagramName("Crate validators Activity - optional part");
        submission = new Submission(new UMLUseCaseDiagram[]{useCaseDiagram},
                new UMLActivityDiagram[]{activityDiagram1, activityDiagram2, activityDiagram3});
        ValidationOutput []outputs = useCaseDiagramNamingValidator.validateUseCaseDiagram(useCaseDiagram, submission);
        //there must be 1 output
        assertEquals("There must be 1 error output.", 1, outputs.length);
        checkAndPrintOutputs(outputs);
    }

    /**
     * <p>
     * Tests method validateUseCaseDiagram(useCaseDiagram, submission) if one activity diagram has
     * some 'illegal text':
     * "&lt;use case name&gt; activity diagram illegal text".
     * This will mean that use case will not have corresponding activity diagram.
     *
     * </p>
     */
    public void testValidateUseCaseDiagramIfActivityHasMoreText() {
        activityDiagram1.setDiagramName("Crate validators Activity illegal text");
        submission = new Submission(new UMLUseCaseDiagram[]{useCaseDiagram},
                new UMLActivityDiagram[]{activityDiagram1, activityDiagram2, activityDiagram3});
        ValidationOutput []outputs = useCaseDiagramNamingValidator.validateUseCaseDiagram(useCaseDiagram, submission);
        //there must be 1 output
        assertEquals("There must be 1 error output.", 1, outputs.length);
        checkAndPrintOutputs(outputs);
    }

    /**
     * <p>
     * Tests method validateUseCaseDiagram(useCaseDiagram, submission) if one use case
     * has null name. This will means that it will not have corresponding
     * activity diagram.
     * </p>
     */
    public void testValidateUseCaseDiagramIfUseCaseHasInvalidName() {
        useCaseDiagram.addElement("100", new UMLUseCase());
        submission = new Submission(new UMLUseCaseDiagram[]{useCaseDiagram},
                new UMLActivityDiagram[]{activityDiagram1, activityDiagram2, activityDiagram3});
        ValidationOutput []outputs = useCaseDiagramNamingValidator.validateUseCaseDiagram(useCaseDiagram, submission);
        //there must be 1 output
        assertEquals("There must be 1 error output.", 1, outputs.length);
        checkAndPrintOutputs(outputs);
    }

    /**
     * <p>
     * Tests method validateUseCaseDiagram(useCaseDiagram, submission) if all use cases
     * have corresponding activity diagram.
     * </p>
     */
    public void testValidateUseCaseDiagram() {
        ValidationOutput []outputs = useCaseDiagramNamingValidator.validateUseCaseDiagram(useCaseDiagram, submission);
        //there must be no outputs
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