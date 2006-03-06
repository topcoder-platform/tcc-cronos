/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * DefaultActivityDiagramNamingValidatorTest.java
 */
package com.topcoder.apps.screening.applications.specification.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.ValidationOutput;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultActivityDiagramNamingValidator;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLUseCase;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;

/**
 * <p>
 * Unit tests for <code>DefaultActivityDiagramNamingValidator</code> class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultActivityDiagramNamingValidatorTest extends TestCase {

    /** DefaultActivityDiagramNamingValidator instance that will be tested. */
    private DefaultActivityDiagramNamingValidator activityDiagramNamingValidator;

    /** UMLActivityDiagram that is used for testing. */
    private UMLActivityDiagram activityDiagram;

    /** UMLUseCaseDiagrams array. */
    private UMLUseCaseDiagram[] useCaseDiagrams;

    /**
     * Creates a test suite for the tests in this test case.
     * 
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        return new TestSuite(DefaultActivityDiagramNamingValidatorTest.class);
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

        useCaseDiagrams = new UMLUseCaseDiagram[] { diagram1, diagram2 };

        activityDiagramNamingValidator = new DefaultActivityDiagramNamingValidator();
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram does
     * not have 'Activity' keyword in the name.
     * </p>
     */
    public void testValidateActivityDiagramNoKeyWordInName() {
        activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("Diagram name");

        ValidationOutput[] outputs = activityDiagramNamingValidator
                .validateSubmission(new Submission(useCaseDiagrams,
                        new UMLActivityDiagram[] { activityDiagram }));
        // there must be one error
        assertEquals("There must be 1 error output.", 1, outputs.length);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram does
     * not have corresponding UseCaseDiagram.
     * </p>
     */
    public void testValidateActivityDiagramNoMatching() {
        ValidationOutput[] outputs = activityDiagramNamingValidator
                .validateSubmission(new Submission(useCaseDiagrams,
                        new UMLActivityDiagram[] { activityDiagram }));
        // there must be one error
        assertEquals("There must be 1 error output.", 1, outputs.length);
    }

    /**
     * <p>
     * Tests method validateActivityDiagram(diagram, submission) if diagram has
     * have corresponding UseCaseDiagram.
     * </p>
     */
    public void testValidateActivityDiagramCorrect() {
        UMLUseCase match = new UMLUseCase();
        match.setElementName("First Use Case");
        useCaseDiagrams[1].addElement("100", match);
        ValidationOutput[] outputs = activityDiagramNamingValidator
                .validateSubmission(new Submission(useCaseDiagrams,
                        new UMLActivityDiagram[] { activityDiagram }));
        // there must be no errors
        assertEquals("There must be no error outputs.", 0, outputs.length);
    }
}