/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * Demo.java
 */
package com.topcoder.apps.screening.applications.specification;

import com.topcoder.apps.screening.applications.specification.impl.DefaultSubmissionValidatorFactory;
import com.topcoder.apps.screening.applications.specification.impl.formatters.TextValidationOutputFormatter;
import com.topcoder.apps.screening.applications.specification.impl.validators.ActivityDiagramPathReportGenerator;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultActivityDiagramValidator;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultUseCaseDiagramNamingValidator;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultUseCaseDiagramValidator;
import com.topcoder.util.xmi.parser.data.UMLActivityDiagram;
import com.topcoder.util.xmi.parser.data.UMLActor;
import com.topcoder.util.xmi.parser.data.UMLElementTypes;
import com.topcoder.util.xmi.parser.data.UMLState;
import com.topcoder.util.xmi.parser.data.UMLStateType;
import com.topcoder.util.xmi.parser.data.UMLTransition;
import com.topcoder.util.xmi.parser.data.UMLUseCase;
import com.topcoder.util.xmi.parser.data.UMLUseCaseDiagram;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;

/**
 * <p>
 * This is a demo of component usage.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {

    /**UMLActivityDiagrams array.*/
    private UMLActivityDiagram []activityDiagrams;

    /**UMLUseCaseDiagrams array.*/
    private UMLUseCaseDiagram []useCaseDiagrams;

    /**
     * <p>
     * Set up environment.
     * </p>
     *
     * @throws Exception exception
     */
    public void setUp() throws Exception {
        Helper.clearNamespace();
        Helper.initNamespace();

        //create use case diagram
        UMLUseCaseDiagram useCaseDiagram = new UMLUseCaseDiagram();
        useCaseDiagram.setDiagramName("Use Case Diagram");
        useCaseDiagram.setDiagramId("1");

        UMLUseCase useCase = new UMLUseCase();
        useCase.setElementName("Use case");
        useCase.setElementId("2");
        useCaseDiagram.addElement(useCase.getElementId(), useCase);

        UMLActor actor = new UMLActor();
        actor.setElementName("Actor");
        actor.setElementId("3");
        useCaseDiagram.addElement(actor.getElementId(), actor);

        useCaseDiagrams = new UMLUseCaseDiagram[1];
        useCaseDiagrams[0] = useCaseDiagram;

        //create activity diagram
        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("Activity Diagram");
        activityDiagram.setDiagramId("4");

        UMLState init = new UMLState("5", UMLStateType.INITIAL_STATE, "initial state");
        init.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState finalSt = new UMLState("6", UMLStateType.FINAL_STATE, "final state");
        finalSt.setElementType(UMLElementTypes.UML_FINAL_STATE);
        UMLState state = new UMLState("7", UMLStateType.ACTION_STATE, "action state");
        state.setElementType(UMLElementTypes.UML_ACTION_STATE);
        activityDiagram.addElement(init.getElementId(), init);
        activityDiagram.addElement(finalSt.getElementId(), finalSt);
        activityDiagram.addElement(state.getElementId(), state);

        UMLTransition tr1 = new UMLTransition("8", "5", "7", "", "", "");
        UMLTransition tr2 = new UMLTransition("", "7", "6", "", "", "");
        activityDiagram.addElement(tr1.getElementId(), tr1);
        activityDiagram.addElement(tr2.getElementId(), tr2);

        activityDiagrams = new UMLActivityDiagram[1];
        activityDiagrams[0] = activityDiagram;
    }

    /**
     * <p>
     * Tear down environment.
     * </p>
     *
     * @throws Exception exception
     */
    public void tearDown() throws Exception {
        Helper.clearNamespace();
    }

    /**
     * <p>
     * In this demo we will show how submission, that contains use case and activity diagrams
     * can be validated using different validators.
     * Also we will show how to get all unique paths throughout the activity diagram.
     * </p>
     */
    public void testDemoValidatorsUsage() {
        //create submission
        Submission submission = new Submission(useCaseDiagrams, activityDiagrams);

        //create new validator (DefaultUseCaseDiagramNamingValidator)
        SubmissionValidator submissionValidator = new DefaultUseCaseDiagramNamingValidator();
        //validate submission and get validation outputs
        ValidationOutput []outputs = submissionValidator.validateSubmission(submission);
        //show all details
        for (int i = 0, n = outputs.length; i < n; i++) {
            System.out.println(outputs[i].getDetails());
        }

        //create another validator
        submissionValidator = new DefaultActivityDiagramValidator();
        //this time we only want to know whetehr submission is valid or not
        System.out.println("Is submission valid: " + submissionValidator.valid(submission));

        //get all unique paths
        ActivityDiagramPathReportGenerator pathReportGenerator = new ActivityDiagramPathReportGenerator();
        ValidationOutput []paths = pathReportGenerator.validateSubmission(submission);
        System.out.println("First path: " + paths[0].getDetails());
    }

    /**
     * <p>
     * In this demo we will show how to use ValidationManager, and how to validate
     * file/xmistring/fileinputsream and get formatted outputs.
     * </p>
     *
     * @throws Exception exception
     */
    public void testDemoValidatorManagerUsage() throws Exception {
        //create ValidationManager which will use full class name as namespace
        ValidationManager validationManager = new ValidationManager();
        //or directly specify validators and formatter
        SubmissionValidator []validators = new SubmissionValidator[2];
        validators[0] = new DefaultUseCaseDiagramValidator();
        validators[1] = new DefaultActivityDiagramValidator();
        validationManager = new ValidationManager(validators, new TextValidationOutputFormatter());
        //or you can specify validator factory
        validationManager = new ValidationManager(new DefaultSubmissionValidatorFactory());

        //validate ready submission
        Submission submission = new Submission(useCaseDiagrams, activityDiagrams);
        String []outputs = validationManager.validate(submission);
        System.out.println("\n" + outputs[0]);

        //validate xmi file
        String fileName = "test_files/unittests/xmi/demo.xmi";
        outputs = validationManager.validate(new File(fileName));
        //get formatted outputs
        System.out.println("\n" + outputs[0]);

        //format file input stream
        FileInputStream fis = new FileInputStream("test_files/unittests/xmi/file_stream.xmi");
        outputs = validationManager.validate(fis);
        System.out.println("\n" + outputs[0]);

        //also you can get unformatted validation outputs
        ValidationOutput []validationOutputs = validationManager.validateRaw(
                Helper.getFileAsString("test_files/unittests/xmi/xmi_string.xmi"));

        //format them using text formatter
        TextValidationOutputFormatter textFormatter = new TextValidationOutputFormatter();
        String []formatted = textFormatter.format(validationOutputs);
        for (int i = 0, n = formatted.length; i < n; i++) {
            System.out.println("\n" + formatted[i]);
        }
    }
}
