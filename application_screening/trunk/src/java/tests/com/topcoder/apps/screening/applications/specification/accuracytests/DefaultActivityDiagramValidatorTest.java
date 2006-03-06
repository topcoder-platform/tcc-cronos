/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultActivityDiagramValidatorTest.java
 */
package com.topcoder.apps.screening.applications.specification.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultActivityDiagramValidator;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLElementTypes;
import com.topcoder.util.xmi.parser.data.UMLState;
import com.topcoder.util.xmi.parser.data.UMLStateType;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;

/**
 * <p>
 * Unit tests for <code>DefaultActivityDiagramValidator</code> class.
 * </p>
 * 
 * <p>
 * Also in this class method from class<code>UseCaseDiagramValidator</code>
 * is tested: - ActivityDiagramValidator.validateSubmission(Submission
 * submission).
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultActivityDiagramValidatorTest extends TestCase {

    /** DefaultActivityDiagramValidator instance that will be tested. */
    private DefaultActivityDiagramValidator activityDiagramValidator;

    /** UMLActivityDiagram that is used for testing. */
    private UMLActivityDiagram activityDiagram;

    /**
     * Creates a test suite for the tests in this test case.
     * 
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        return new TestSuite(DefaultActivityDiagramValidatorTest.class);
    }

    /**
     * <p>
     * Set up environment.
     * </p>
     * 
     * @throws Exception
     *             exception
     */
    public void setUp() throws Exception {
        activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("Activity diagram.");
        activityDiagramValidator = new DefaultActivityDiagramValidator();
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission).
     * </p>
     */
    public void testValidateActivityDiagram() {
        UMLState initState = new UMLState("1", UMLStateType.INITIAL_STATE,
                "initialState");
        initState.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        initState.setElementName("initialState");
        activityDiagram.addElement("1", initState);
        UMLState finalState = new UMLState("1", UMLStateType.FINAL_STATE,
                "finalState");
        finalState.setElementType(UMLElementTypes.UML_FINAL_STATE);
        finalState.setElementName("finalState");
        activityDiagram.addElement("2", finalState);
        ValidationOutput[] outputs = activityDiagramValidator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram }));
        // there must be no outputs
        assertEquals("There must be no error outputs", 0, outputs.length);
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if submission is valid and
     * there is no outputs.
     * </p>
     */
    public void testValidateSubmissionCorrect() {
        UMLState initState = new UMLState("1", UMLStateType.INITIAL_STATE,
                "initialState");
        initState.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        initState.setElementName("initialState");
        activityDiagram.addElement("1", initState);
        UMLState finalState = new UMLState("1", UMLStateType.FINAL_STATE,
                "finalState");
        finalState.setElementType(UMLElementTypes.UML_FINAL_STATE);
        finalState.setElementName("final state");
        activityDiagram.addElement("2", finalState);
        ValidationOutput[] outputs = activityDiagramValidator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram }));
        // there must be no outputs
        assertEquals("There must be no error outputs", 0, outputs.length);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram does
     * not have initial states.
     * </p>
     */
    public void testValidateActivityDiagramInitialNotExists() {
        UMLState finalState = new UMLState("1", UMLStateType.FINAL_STATE,
                "finalState");
        finalState.setElementType(UMLElementTypes.UML_FINAL_STATE);
        finalState.setElementName("finalState");
        activityDiagram.addElement("1", finalState);
        ValidationOutput[] outputs = activityDiagramValidator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram }));
        // there must be 1 output
        assertEquals("There must be 1 error output.", 1, outputs.length);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram does
     * not have final states.
     * </p>
     */
    public void testValidateActivityDiagramFinalNotExists() {
        UMLState initState = new UMLState("1", UMLStateType.INITIAL_STATE,
                "initState");
        initState.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        initState.setElementName("initState");
        activityDiagram.addElement("1", initState);
        ValidationOutput[] outputs = activityDiagramValidator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram }));
        // there must be 1 output
        assertEquals("There must be 1 error output.", 1, outputs.length);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram does
     * not have initala nd final states.
     * </p>
     */
    public void testValidateActivityDiagramIfInitialFinalNotExist() {
        ValidationOutput[] outputs = activityDiagramValidator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram }));
        // there must be 2 output
        assertEquals("There must be 2 error output.", 2, outputs.length);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram
     * initial and final states have empty names.
     * </p>
     */
    public void testValidateActivityDiagramNamesEmpty() {
        UMLState initState = new UMLState("1", UMLStateType.INITIAL_STATE, "  ");
        initState.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        initState.setElementName("  ");
        activityDiagram.addElement("1", initState);
        UMLState finalState = new UMLState("1", UMLStateType.FINAL_STATE, "  ");
        finalState.setElementType(UMLElementTypes.UML_FINAL_STATE);
        finalState.setElementName("  ");
        activityDiagram.addElement("2", finalState);
        ValidationOutput[] outputs = activityDiagramValidator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram }));
        // there must be 2 outputs
        assertEquals("There must be 2 error outputs", 2, outputs.length);
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if in submission one activity
     * diagram is valid, and one is invalid.
     * </p>
     */
    public void testValidateSubmissionIfOneValidOneInvalid() {
        UMLState initState = new UMLState("1", UMLStateType.INITIAL_STATE,
                "initialState");
        initState.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        initState.setElementName("initialState");
        activityDiagram.addElement("1", initState);
        UMLState finalState = new UMLState("1", UMLStateType.FINAL_STATE,
                "finalState");
        finalState.setElementType(UMLElementTypes.UML_FINAL_STATE);
        finalState.setElementName("finalState");
        activityDiagram.addElement("2", finalState);

        // create invalid activity diagram
        UMLActivityDiagram invalidActDiagram = new UMLActivityDiagram();
        invalidActDiagram.setDiagramName("Invalid diagram");
        ValidationOutput[] outputs = activityDiagramValidator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram,
                                invalidActDiagram }));
        // there must be 2 outputs
        assertEquals("There must be 2 error outputs", 2, outputs.length);
    }

    /**
     * <p>
     * Tests method validateSubmission(submission) if in submission both
     * activity diagrams are invalid.
     * </p>
     */
    public void testValidateSubmissionIfBothInvalid() {
        UMLState initState = new UMLState("1", UMLStateType.INITIAL_STATE,
                "initialState");
        initState.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        initState.setElementName("initialState");
        activityDiagram.addElement("1", initState);

        // create invalid activity diagram
        UMLActivityDiagram invalidActDiagram = new UMLActivityDiagram();
        invalidActDiagram.setDiagramName("Invalid diagram");
        ValidationOutput[] outputs = activityDiagramValidator
                .validateSubmission(new Submission(new UMLUseCaseDiagram[0],
                        new UMLActivityDiagram[] { activityDiagram,
                                invalidActDiagram }));
        // there must be 2 outputs
        assertEquals("There must be 3 error outputs", 3, outputs.length);
    }
}