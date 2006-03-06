/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultActivityDiagramValidatorTest.java
 */
package com.topcoder.apps.screening.applications.specification.impl.validators;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.ValidationOutputType;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLElementTypes;
import com.topcoder.util.xmi.parser.data.UMLState;
import com.topcoder.util.xmi.parser.data.UMLStateType;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;
import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>DefaultActivityDiagramValidator</code> class.
 * </p>
 *
 * <p>
 * Also in this class method from class<code>UseCaseDiagramValidator</code> is tested:
 *  - ActivityDiagramValidator.validateSubmission(Submission submission).
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultActivityDiagramValidatorTest extends TestCase {

    /**DefaultActivityDiagramValidator instance that will be tested.*/
    private DefaultActivityDiagramValidator activityDiagramValidator;

    /**UMLActivityDiagram that is used for testing.*/
    private UMLActivityDiagram activityDiagram;

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
        activityDiagram.setDiagramName("Activity diagram.");

        submission = new Submission(new UMLUseCaseDiagram[0], new UMLActivityDiagram[0]);

        activityDiagramValidator = new DefaultActivityDiagramValidator();
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram is null.
     * </p>
     */
    public void testValidateActivityDiagramIfDiagramNull() {
        try {
            activityDiagramValidator.validateActivityDiagram(null, submission);
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
            activityDiagramValidator.validateActivityDiagram(activityDiagram, null);
            fail("Submission cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram does not have
     * initial states.
     * </p>
     */
    public void testValidateActivityDiagramIfNoInitialStates() {
        UMLState finalState = new UMLState("1", UMLStateType.FINAL_STATE, "finalState");
        finalState.setElementType(UMLElementTypes.UML_FINAL_STATE);
        finalState.setElementName("finalState");
        activityDiagram.addElement("1", finalState);

        ValidationOutput []outputs = activityDiagramValidator.validateActivityDiagram(
                activityDiagram, submission);
        //there must be 1 output
        assertEquals("There must be 1 error output.", 1, outputs.length);
        checkAndPrintOutputs(outputs);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram does not have
     * final states.
     * </p>
     */
    public void testValidateActivityDiagramIfNoFinalStates() {
        UMLState initState = new UMLState("1", UMLStateType.INITIAL_STATE, "initState");
        initState.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        initState.setElementName("initState");
        activityDiagram.addElement("1", initState);

        ValidationOutput []outputs = activityDiagramValidator.validateActivityDiagram(
                activityDiagram, submission);
        //there must be 1 output
        assertEquals("There must be 1 error output.", 1, outputs.length);
        checkAndPrintOutputs(outputs);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram does not have
     * initala nd final states.
     * </p>
     */
    public void testValidateActivityDiagramIfNoInitialFinalStates() {
        ValidationOutput []outputs = activityDiagramValidator.validateActivityDiagram(
                activityDiagram, submission);
        //there must be 2 output
        assertEquals("There must be 2 error output.", 2, outputs.length);
        checkAndPrintOutputs(outputs);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram initial and final
     * states have empty names.
     * </p>
     */
    public void testValidateActivityDiagramIfNamesEmpty() {
        UMLState initState = new UMLState("1", UMLStateType.INITIAL_STATE, "  ");
        initState.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        initState.setElementName("  ");
        activityDiagram.addElement("1", initState);
        UMLState finalState = new UMLState("1", UMLStateType.FINAL_STATE, "  ");
        finalState.setElementType(UMLElementTypes.UML_FINAL_STATE);
        finalState.setElementName("  ");
        activityDiagram.addElement("2", finalState);

        ValidationOutput []outputs = activityDiagramValidator.validateActivityDiagram(
                activityDiagram, submission);
        //there must be 2 outputs
        assertEquals("There must be 2 error outputs", 2, outputs.length);
        checkAndPrintOutputs(outputs);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission).
     * </p>
     */
    public void testValidateActivityDiagram() {
        UMLState initState = new UMLState("1", UMLStateType.INITIAL_STATE, "initialState");
        initState.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        initState.setElementName("initialState");
        activityDiagram.addElement("1", initState);
        UMLState finalState = new UMLState("1", UMLStateType.FINAL_STATE, "finalState");
        finalState.setElementType(UMLElementTypes.UML_FINAL_STATE);
        finalState.setElementName("finalState");
        activityDiagram.addElement("2", finalState);

        ValidationOutput []outputs = activityDiagramValidator.validateActivityDiagram(
                activityDiagram, submission);
        //there must be no outputs
        assertEquals("There must be no error outputs", 0, outputs.length);
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if submission is null.
     * </p>
     */
    public void testValidateSubmissionIfNull() {
        try {
            activityDiagramValidator.validateSubmission(null);
            fail("Submission cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if submission is valid and
     * there is no outputs.
     * </p>
     */
    public void testValidateSubmission() {
        UMLState initState = new UMLState("1", UMLStateType.INITIAL_STATE, "initialState");
        initState.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        initState.setElementName("initialState");
        activityDiagram.addElement("1", initState);
        UMLState finalState = new UMLState("1", UMLStateType.FINAL_STATE, "finalState");
        finalState.setElementType(UMLElementTypes.UML_FINAL_STATE);
        finalState.setElementName("final state");
        activityDiagram.addElement("2", finalState);

        submission = new Submission(new UMLUseCaseDiagram[0], new UMLActivityDiagram[]{activityDiagram});
        ValidationOutput []outputs = activityDiagramValidator.validateSubmission(submission);
        //there must be no outputs
        assertEquals("There must be no error outputs", 0, outputs.length);
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if in submission one activity diagram
     * is valid, and one is invalid.
     * </p>
     */
    public void testValidateSubmissionIfOneValidOneInvalid() {
        UMLState initState = new UMLState("1", UMLStateType.INITIAL_STATE, "initialState");
        initState.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        initState.setElementName("initialState");
        activityDiagram.addElement("1", initState);
        UMLState finalState = new UMLState("1", UMLStateType.FINAL_STATE, "finalState");
        finalState.setElementType(UMLElementTypes.UML_FINAL_STATE);
        finalState.setElementName("finalState");
        activityDiagram.addElement("2", finalState);

        //create invalid activity diagram
        UMLActivityDiagram invalidActDiagram = new UMLActivityDiagram();
        invalidActDiagram.setDiagramName("Invalid diagram");

        submission = new Submission(new UMLUseCaseDiagram[0],
                new UMLActivityDiagram[]{activityDiagram, invalidActDiagram});
        ValidationOutput []outputs = activityDiagramValidator.validateSubmission(submission);
        //there must be 2 outputs
        assertEquals("There must be 2 error outputs", 2, outputs.length);
        checkAndPrintOutputs(outputs);
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if in submission both activity diagrams
     * are invalid.
     * </p>
     */
    public void testValidateSubmissionIfBothInvalid() {
        UMLState initState = new UMLState("1", UMLStateType.INITIAL_STATE, "initialState");
        initState.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        initState.setElementName("initialState");
        activityDiagram.addElement("1", initState);

        //create invalid activity diagram
        UMLActivityDiagram invalidActDiagram = new UMLActivityDiagram();
        invalidActDiagram.setDiagramName("Invalid diagram");

        submission = new Submission(new UMLUseCaseDiagram[0],
                new UMLActivityDiagram[]{activityDiagram, invalidActDiagram});
        ValidationOutput []outputs = activityDiagramValidator.validateSubmission(submission);
        //there must be 2 outputs
        assertEquals("There must be 3 error outputs", 3, outputs.length);
        checkAndPrintOutputs(outputs);
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