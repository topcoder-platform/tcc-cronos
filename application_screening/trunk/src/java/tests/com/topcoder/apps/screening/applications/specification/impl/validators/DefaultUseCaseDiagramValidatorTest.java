/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultUseCaseDiagramValidatorTest.java
 */
package com.topcoder.apps.screening.applications.specification.impl.validators;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLActor;
import com.topcoder.util.xmi.parser.data.UMLElement;
import com.topcoder.util.xmi.parser.data.UMLUseCase;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Unit tests for <code>DefaultUseCaseDiagramValidatorTest</code> class.
 * </p>
 *
 * <p>
 * Also in this class tested methods from classes <code>SubmissionValidator</code>
 * and <code>UseCaseDiagramValidator</code>:
 *  - SubmissionValidator.valid(Object obj)
 *  - UseCaseDiagramValidator.validateSubmission(Submission submission).
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultUseCaseDiagramValidatorTest extends TestCase {

    /**DefaultUseCaseDiagramValidator instance that will be tested.*/
    private DefaultUseCaseDiagramValidator useCaseDiagramValidator;

    /**Submission that is used for testing.*/
    private Submission submission;

    /**UMLUseCaseDiagram that is used for testing.*/
    private UMLUseCaseDiagram useCaseDiagram1;

    /**UMLUseCaseDiagram that is used for testing.*/
    private UMLUseCaseDiagram useCaseDiagram2;

    /**
     * <p>
     * Set up environment.
     * </p>
     *
     * @throws Exception exception
     */
    public void setUp() throws Exception {
        useCaseDiagramValidator = new DefaultUseCaseDiagramValidator();

        useCaseDiagram1 = new UMLUseCaseDiagram();
        useCaseDiagram1.setDiagramName("First use case diagram");
        useCaseDiagram1.setDiagramId("1");
        useCaseDiagram2 = new UMLUseCaseDiagram();
        useCaseDiagram2.setDiagramId("2");
        useCaseDiagram2.setDiagramName("Second use case diagram");

        submission = new Submission(new UMLUseCaseDiagram[0], new UMLActivityDiagram[0]);
    }

    /**
     * <p>
     * Tests method validateUseCaseDiagram(useCaseDiagram, submission) if diagram is null.
     * </p>
     */
    public void testValidateUseCaseDiagramIfDiagram() {
        try {
            useCaseDiagramValidator.validateUseCaseDiagram(null, submission);
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
            useCaseDiagramValidator.validateUseCaseDiagram(useCaseDiagram1, null);
            fail("UseCaseDiagram cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validateUseCaseDiagram(useCaseDiagram, submission) if diagram does not contain
     * use cases.
     * </p>
     */
    public void testValidateUseCaseDiagramIfNoUseCases() {
        //add actor to diagram
        UMLElement actor = new UMLActor();
        actor.setElementName("actor");
        actor.setElementId("1");
        useCaseDiagram1.addElement(actor.getElementId(), actor);

        //than validate use case diagram - there must be one error that there is no use cases
        ValidationOutput []outputs = useCaseDiagramValidator.validateUseCaseDiagram(useCaseDiagram1, submission);
        //first output must be error
        assertEquals("ValidationOutput must be ERROR.", ValidationOutputType.ERROR, outputs[0].getType());
        assertEquals("Diagrams must be equal.", useCaseDiagram1, outputs[0].getDiagram());
        assertNull("Element must be null.", outputs[0].getElement());
        assertTrue("Details string must not be empty.", outputs[0].getDetails().trim().length() != 0);

        assertEquals("There must be 1 error output.", 1, outputs.length);
        checkAndPrintOutputs(outputs);
    }

    /**
     * <p>
     * Tests method validateUseCaseDiagram(useCaseDiagram, submission) if diagram contains
     * use cases, but all of them are abstract.
     * </p>
     */
    public void testValidateUseCaseDiagramIfAllUseCasesAbstract() {
        //add actor to diagram
        UMLElement actor = new UMLActor();
        actor.setElementName("actor");
        actor.setElementId("1");
        useCaseDiagram1.addElement(actor.getElementId(), actor);

        //add two use cases that are abstract
        UMLUseCase useCase1 = new UMLUseCase();
        useCase1.setElementName("use case 1");
        useCase1.setElementId("100");
        useCase1.setIsAbstract(true);
        useCaseDiagram1.addElement(useCase1.getElementId(), useCase1);
        UMLUseCase useCase2 = new UMLUseCase();
        useCase2.setElementName("use case 2");
        useCase2.setIsAbstract(true);
        useCase1.setElementId("200");
        useCaseDiagram1.addElement(useCase2.getElementId(), useCase2);

        //than validate use case diagram
        ValidationOutput []outputs = useCaseDiagramValidator.validateUseCaseDiagram(useCaseDiagram1, submission);
        //there must be 3 error outputs: 2 for abstract use cases, and 1 - for diagram that does
        //not have concrete use cases
        assertEquals("There must be 3 error outputs.", 3, outputs.length);
        checkAndPrintOutputs(outputs);

        //for each use case there will be error output, which states that use case is abstract
        ValidationOutput []useCase1Output = getOutputsForElement(outputs, useCase1);
        assertEquals("There must be 1 output.", 1, useCase1Output.length);
        assertTrue("Details string must contain this substring.",
                useCase1Output[0].getDetails().indexOf("abstract use case") != -1);

        ValidationOutput []useCase2Output = getOutputsForElement(outputs, useCase2);
        assertEquals("There must be 1 output.", 1, useCase2Output.length);
        assertTrue("Details string must contain this substring.",
                useCase2Output[0].getDetails().indexOf("abstract use case") != -1);
    }

    /**
     * <p>
     * Tests method validateUseCaseDiagram(useCaseDiagram, submission) if diagram contains
     * use cases, but all of them contains empty names.
     * </p>
     */
    public void testValidateUseCaseDiagramIfAllUseCasesHasEmptyName() {
        //add actor to diagram
        UMLElement actor = new UMLActor();
        actor.setElementName("actor");
        actor.setElementId("1");
        useCaseDiagram1.addElement(actor.getElementId(), actor);

        //add two use cases with empty names
        UMLUseCase useCase1 = new UMLUseCase();
        useCase1.setElementName("");
        useCase1.setElementId("100");
        useCase1.setIsAbstract(false);
        useCaseDiagram1.addElement(useCase1.getElementId(), useCase1);
        UMLUseCase useCase2 = new UMLUseCase();
        useCase2.setElementName("  ");
        useCase2.setIsAbstract(false);
        useCase1.setElementId("200");
        useCaseDiagram1.addElement(useCase2.getElementId(), useCase2);

        //than validate use case diagram
        ValidationOutput []outputs = useCaseDiagramValidator.validateUseCaseDiagram(useCaseDiagram1, submission);
        //there must be 2 error outputs: both for use cases with empty names
        assertEquals("There must be 2 error outputs.", 2, outputs.length);
        checkAndPrintOutputs(outputs);

        //for each use case there will be error output, which states that use case is abstract
        ValidationOutput []useCase1Output = getOutputsForElement(outputs, useCase1);
        assertEquals("There must be 1 output.", 1, useCase1Output.length);
        assertTrue("Details string must contain this substring.",
                useCase1Output[0].getDetails().indexOf("use case has empty name") != -1);

        ValidationOutput []useCase2Output = getOutputsForElement(outputs, useCase2);
        assertEquals("There must be 1 output.", 1, useCase2Output.length);
        assertTrue("Details string must contain this substring.",
                useCase2Output[0].getDetails().indexOf("use case has empty name") != -1);
    }

    /**
     * <p>
     * Tests method validateUseCaseDiagram(useCaseDiagram, submission) if diagram does
     * not contain any actor.
     * </p>
     */
    public void testValidateUseCaseDiagramIfNoActors() {
        //add two valid use cases
        UMLUseCase useCase1 = new UMLUseCase();
        useCase1.setElementName("use case 1");
        useCase1.setElementId("100");
        useCase1.setIsAbstract(false);
        useCaseDiagram1.addElement(useCase1.getElementId(), useCase1);
        UMLUseCase useCase2 = new UMLUseCase();
        useCase2.setElementName("use case 2");
        useCase2.setIsAbstract(false);
        useCase1.setElementId("200");
        useCaseDiagram1.addElement(useCase2.getElementId(), useCase2);

        //than validate use case diagram
        ValidationOutput []outputs = useCaseDiagramValidator.validateUseCaseDiagram(useCaseDiagram1, submission);
        //there must be 1 error outputs: there is no actors
        assertEquals("There must be 1 error output.", 1, outputs.length);
        checkAndPrintOutputs(outputs);
    }

    /**
     * <p>
     * Tests method validateUseCaseDiagram(useCaseDiagram, submission) if diagram contains
     * actors with empty names.
     * </p>
     */
    public void testValidateUseCaseDiagramIfContainActorWithEmptyName() {
        //add actors to diagram
        UMLElement actor1 = new UMLActor();
        actor1.setElementName("actor");
        actor1.setElementId("1");
        useCaseDiagram1.addElement(actor1.getElementId(), actor1);
        //add actor with no name to diagram
        UMLElement actor2 = new UMLActor();
        actor2.setElementId("1");
        useCaseDiagram1.addElement(actor2.getElementId(), actor2);

        //add valid use case
        UMLUseCase useCase1 = new UMLUseCase();
        useCase1.setElementName("use case 1");
        useCase1.setElementId("100");
        useCase1.setIsAbstract(false);
        useCaseDiagram1.addElement(useCase1.getElementId(), useCase1);

        //than validate use case diagram
        ValidationOutput []outputs = useCaseDiagramValidator.validateUseCaseDiagram(useCaseDiagram1, submission);
        //there must be 1 error output: for actor that has empty name
        assertEquals("There must be 1 error output.", 1, outputs.length);
        checkAndPrintOutputs(outputs);
        assertEquals("Actors must be equal.", actor2, outputs[0].getElement());
    }

    /**
     * <p>
     * Tests method validateUseCaseDiagram(useCaseDiagram, submission) if diagram contains
     * all necessary elements.
     * </p>
     */
    public void testValidateUseCaseDiagramIfValid() {
        //add valid actors to diagram
        UMLElement actor1 = new UMLActor();
        actor1.setElementName("actor");
        actor1.setElementId("1");
        useCaseDiagram1.addElement(actor1.getElementId(), actor1);
        //add actor with no name to diagram
        UMLElement actor2 = new UMLActor();
        actor2.setElementName("actor2");
        actor2.setElementId("1");
        useCaseDiagram1.addElement(actor2.getElementId(), actor2);

        //add two valid use cases
        UMLUseCase useCase1 = new UMLUseCase();
        useCase1.setElementName("use case 1");
        useCase1.setElementId("100");
        useCase1.setIsAbstract(false);
        useCaseDiagram1.addElement(useCase1.getElementId(), useCase1);
        UMLUseCase useCase2 = new UMLUseCase();
        useCase2.setElementName("use case 2");
        useCase2.setIsAbstract(false);
        useCase1.setElementId("200");
        useCaseDiagram1.addElement(useCase2.getElementId(), useCase2);

        ValidationOutput []outputs = useCaseDiagramValidator.validateUseCaseDiagram(useCaseDiagram1, submission);
        assertEquals("Outputs array must be empty.", 0, outputs.length);
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if submission is null.
     * <p>
     */
    public void testValidateSubmissionIfNull() {
        try {
            useCaseDiagramValidator.validateSubmission(null);
            fail("Submission cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if submission does not contain use
     * case diagrams.
     * <p>
     */
    public void testValidateSubmissionIfNoDiagrams() {
        submission = new Submission(new UMLUseCaseDiagram[0], new UMLActivityDiagram[0]);
        ValidationOutput []outputs = useCaseDiagramValidator.validateSubmission(submission);
        //there must be only one error output, which states that there is no use case diagrams
        assertEquals("There must be 1 error output.", 1, outputs.length);
        checkAndPrintOutputs(outputs);
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if submission contains use case diagrams,
     * that both are not correct.
     * (first diagram does not contain use case diagrams, second  does not contain actors)
     * <p>
     */
    public void testValidateSubmissionIfDiagramsNotCorrect() {
        //add valid actors to first diagram
        UMLElement actor1 = new UMLActor();
        actor1.setElementName("actor");
        actor1.setElementId("1");
        useCaseDiagram1.addElement(actor1.getElementId(), actor1);
        //add actor with no name to diagram
        UMLElement actor2 = new UMLActor();
        actor2.setElementName("actor2");
        actor2.setElementId("1");
        useCaseDiagram1.addElement(actor2.getElementId(), actor2);

        //add two valid use cases for second diagram
        UMLUseCase useCase1 = new UMLUseCase();
        useCase1.setElementName("use case 1");
        useCase1.setElementId("100");
        useCase1.setIsAbstract(false);
        useCaseDiagram2.addElement(useCase1.getElementId(), useCase1);
        UMLUseCase useCase2 = new UMLUseCase();
        useCase2.setElementName("use case 2");
        useCase2.setIsAbstract(false);
        useCase1.setElementId("200");
        useCaseDiagram2.addElement(useCase2.getElementId(), useCase2);

        //create submission with diagrams
        submission = new Submission(new UMLUseCaseDiagram[]{useCaseDiagram1, useCaseDiagram2},
                new UMLActivityDiagram[0]);
        //validate
        ValidationOutput []outputs = useCaseDiagramValidator.validateSubmission(submission);
        assertEquals("There must be 2 error outputs.", 2, outputs.length);
        checkAndPrintOutputs(outputs);

        //Note: if we validate separately diagrams, and put their errors together,
        //they must be the same as validateSubmission(submission) returned
        ValidationOutput []d1output = useCaseDiagramValidator.
                validateUseCaseDiagram(useCaseDiagram1, submission);
        ValidationOutput []d2output = useCaseDiagramValidator.
                validateUseCaseDiagram(useCaseDiagram2, submission);
        assertEquals("Size must be equal.", outputs.length, d1output.length + d2output.length);
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if submission contains valid use case diagrams.
     * <p>
     */
    public void testValidateSubmissionIfCorrect() {
        //add valid actors to diagrams
        UMLElement actor1 = new UMLActor();
        actor1.setElementName("actor");
        actor1.setElementId("1");
        useCaseDiagram1.addElement(actor1.getElementId(), actor1);
        //add actor with no name to diagram
        UMLElement actor2 = new UMLActor();
        actor2.setElementName("actor2");
        actor2.setElementId("1");
        useCaseDiagram2.addElement(actor2.getElementId(), actor2);

        //add valid use cases to diagrams
        UMLUseCase useCase1 = new UMLUseCase();
        useCase1.setElementName("use case 1");
        useCase1.setElementId("100");
        useCase1.setIsAbstract(false);
        useCaseDiagram1.addElement(useCase1.getElementId(), useCase1);
        UMLUseCase useCase2 = new UMLUseCase();
        useCase2.setElementName("use case 2");
        useCase2.setIsAbstract(false);
        useCase1.setElementId("200");
        useCaseDiagram2.addElement(useCase2.getElementId(), useCase2);

        //create submission with diagrams
        submission = new Submission(new UMLUseCaseDiagram[]{useCaseDiagram1, useCaseDiagram2},
                new UMLActivityDiagram[0]);
        //validate
        ValidationOutput []outputs = useCaseDiagramValidator.validateSubmission(submission);
        assertEquals("There must be no outputs.", 0, outputs.length);
    }

    /**
     * <p>
     * Tests method valid(obj) if object is not instance of Submission.
     * </p>
     */
    public void testValidIfNotSubmission() {
        assertFalse("Must be not valid.", useCaseDiagramValidator.valid(null));
        assertFalse("Must be not valid.", useCaseDiagramValidator.valid(new Object()));
    }

    /**
     * <p>
     * Tests method valid(obj) if submission is not valid.
     * (first diagram does not contain use case diagrams, second  does not contain actors)
     * </p>
     */
    public void testValidIfNotValid() {
        //add valid actors to first diagram
        UMLElement actor1 = new UMLActor();
        actor1.setElementName("actor");
        actor1.setElementId("1");
        useCaseDiagram1.addElement(actor1.getElementId(), actor1);
        //add actor with no name to diagram
        UMLElement actor2 = new UMLActor();
        actor2.setElementName("actor2");
        actor2.setElementId("1");
        useCaseDiagram1.addElement(actor2.getElementId(), actor2);

        //add two valid use cases for second diagram
        UMLUseCase useCase1 = new UMLUseCase();
        useCase1.setElementName("use case 1");
        useCase1.setElementId("100");
        useCase1.setIsAbstract(false);
        useCaseDiagram2.addElement(useCase1.getElementId(), useCase1);
        UMLUseCase useCase2 = new UMLUseCase();
        useCase2.setElementName("use case 2");
        useCase2.setIsAbstract(false);
        useCase1.setElementId("200");
        useCaseDiagram2.addElement(useCase2.getElementId(), useCase2);

        //create submission with diagrams
        submission = new Submission(new UMLUseCaseDiagram[]{useCaseDiagram1, useCaseDiagram2},
                new UMLActivityDiagram[0]);

        //validate
        assertFalse("Submission is not valid.", useCaseDiagramValidator.valid(submission));
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

    /**
     * <p>
     * This method returns ValidationOutputs which element field matches specified useCase.
     * </p>
     *
     * @param outputs ValidationOutputs array
     * @param useCase that is used for matching
     * @return validation outputs, which matches useCase
     */
    private ValidationOutput[] getOutputsForElement(ValidationOutput[] outputs, UMLUseCase useCase) {
        List result = new ArrayList();
        for (int i = 0, n = outputs.length; i < n; i++) {
            if (outputs[i].getElement() == useCase) {
                result.add(outputs[i]);
            }
        }
        return (ValidationOutput[]) result.toArray(new ValidationOutput[result.size()]);
    }
}