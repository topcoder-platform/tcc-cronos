/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ValidationManagerTest.java
 */
package com.topcoder.apps.screening.applications.specification;

import com.topcoder.apps.screening.applications.specification.impl.DefaultSubmissionValidatorFactory;
import com.topcoder.apps.screening.applications.specification.impl.formatters.TextValidationOutputFormatter;
import com.topcoder.apps.screening.applications.specification.impl.formatters.XMLValidationOutputFormatter;
import com.topcoder.apps.screening.applications.specification.impl.validators.ActivityDiagramPathReportGenerator;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultActivityDiagramNamingValidator;
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
import java.util.Arrays;

/**
 * <p>
 * Unit tests for <code>ValidationManager</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ValidationManagerTest extends TestCase {

    /**Prefix for namespace with invalid configuration.*/
    private static final String INVALID_NS = "com.topcoder.apps.screening.applications.specification.invalid";

    /**Prefix for namespace with valid configurations.*/
    private static final String VALID_NS = "com.topcoder.apps.screening.applications.specification.valid";

    /**ValidationManager instance that will be tested.*/
    private ValidationManager validationManager;

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

        validationManager = new ValidationManager();
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
     * Tests constructor ValidationManager(namespace) if namespace is invalid (null or empty).
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtor1IfNamespaceInvalid() throws Exception {
        try {
            new ValidationManager((String) null);
            fail("Namespace cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
        try {
            new ValidationManager("   ");
            fail("Namespace cannot be empty.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationManager(namespace) if namespace is unknown.
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtor1IfNamespaceUnknown() throws Exception {
        try {
            new ValidationManager("unknown.namespace");
            fail("Namespace must be valid.");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationManager(namespace) if factory class name is not specified.
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtor1IfFactoryClassNameNull() throws Exception {
        try {
            new ValidationManager(INVALID_NS + 1);
            fail("Factory class name property is required.");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationManager(namespace) if factory class name is invalid.
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtor1IfFactoryClassNameInvalid() throws Exception {
        try {
            new ValidationManager(INVALID_NS + 2);
            fail("Factory class name must be valid.");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationManager(namespace) without using factory's namespace.
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtor1WithoutFactoryNamespace() throws Exception {
        validationManager = new ValidationManager(VALID_NS + 1);
        assertNotNull("Must not be null.", validationManager);

        //there must be 5 validators
        assertEquals("There must be 5 validators.", 5, validationManager.getValidators().length);
        //formatter must be instance of XMLValidationOutputFormatter
        assertTrue("Formatter must be instance of XMLValidationOutputFormatter.",
                validationManager.getFormatter() instanceof XMLValidationOutputFormatter);
    }

    /**
     * <p>
     * Tests constructor ValidationManager(namespace) with using factory's namespace.
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtor1WithFactoryNamespace() throws Exception {
        validationManager = new ValidationManager(VALID_NS + 2);
        assertNotNull("Must not be null.", validationManager);

        //there must be 5 validators
        assertEquals("There must be 5 validators.", 5, validationManager.getValidators().length);
        //formatter must be instance of XMLValidationOutputFormatter
        assertTrue("Formatter must be instance of XMLValidationOutputFormatter.",
                validationManager.getFormatter() instanceof XMLValidationOutputFormatter);
    }

    /**
     * <p>
     * Tests constructor ValidationManager(SubmissionValidator[] validators,
     * ValidationOutputFormatter formatter) if validators array is null or empty.
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtor2IfValidatorsArrayNullOrEmpty() throws Exception {
        try {
            new ValidationManager(null, new XMLValidationOutputFormatter());
            fail("SubmissionValidators array cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
        try {
            new ValidationManager(new SubmissionValidator[0], new XMLValidationOutputFormatter());
            fail("SubmissionValidators array cannot be empty.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationManager(SubmissionValidator[] validators,
     * ValidationOutputFormatter formatter) if validators array contains null.
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtor2IfValidatorsArrayContainNull() throws Exception {
        try {
            SubmissionValidator []validators = new SubmissionValidator[2];
            validators[0] = new DefaultUseCaseDiagramValidator();
            validators[1] = null;
            new ValidationManager(validators, new XMLValidationOutputFormatter());
            fail("SubmissionValidators array cannot contain null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationManager(SubmissionValidator[] validators,
     * ValidationOutputFormatter formatter) if formatter is null.
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtor2IfFormatterNull() throws Exception {
        try {
            SubmissionValidator []validators = new SubmissionValidator[1];
            validators[0] = new DefaultUseCaseDiagramValidator();
            new ValidationManager(validators, null);
            fail("Formatter cannot contain be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationManager(SubmissionValidator[] validators,
     * ValidationOutputFormatter formatter).
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtor2() throws Exception {
        SubmissionValidator []validators = new SubmissionValidator[1];
        validators[0] = new DefaultUseCaseDiagramValidator();
        validationManager = new ValidationManager(validators, new TextValidationOutputFormatter());

        assertTrue("Arrays must be equal.", Arrays.equals(validators, validationManager.getValidators()));
        assertTrue("Must be instance of TextValidationOutputFormatter.",
                validationManager.getFormatter() instanceof TextValidationOutputFormatter);
    }

    /**
     * <p>
     * Tests constructor ValidationManager(SubmissionValidatorFactory factory) if factory is null.
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtor3IfFactoryNull() throws Exception {
        try {
            new ValidationManager((DefaultSubmissionValidatorFactory) null);
            fail("Factory cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor ValidationManager(SubmissionValidatorFactory factory).
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtor3() throws Exception {
        validationManager = new ValidationManager(new DefaultSubmissionValidatorFactory());
        //there must be 5 validators
        assertEquals("There must be 5 validators.", 5, validationManager.getValidators().length);
        //formatter must be instance of XMLValidationOutputFormatter
        assertTrue("Formatter must be instance of XMLValidationOutputFormatter.",
                validationManager.getFormatter() instanceof XMLValidationOutputFormatter);
    }

    /**
     * <p>
     * Tests method validate(Submission submission) if submission is null.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateSubmissionIfNull() throws Exception {
        try {
            validationManager.validate((Submission) null);
            fail("Submission cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validate(String xmiString) if xmiString is null.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateXmiStringIfNull() throws Exception {
        try {
            validationManager.validate((String) null);
            fail("XmiString cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validate(String xmiString) if xmiString is empty.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateXmiStringIfEmpty() throws Exception {
        try {
            validationManager.validate("   ");
            fail("XmiString cannot be empty.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validate(File file) if file is null.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateFileIfNull() throws Exception {
        try {
            validationManager.validate((File) null);
            fail("File cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validate(FileInputStream stream) if stream is null.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateFileInputStreamIfNull() throws Exception {
        try {
            validationManager.validate((FileInputStream) null);
            fail("FileInputStream cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validate(Submission submission).
     * </p>
     *
     * <p>
     * In thos test we crate manager with two validators:
     * DefaultUseCaseDiagramValidator and DefaultActivityDiagramValidator.
     * And submission will not have any diagrams.
     * </p>
     *
     * <p>
     * Result must be:
     *
     * &lt;Validation&gt;
     * &lt;Errors&gt;
     * &lt;Error&gt;submission is missing an use case diagram&lt;/Error&gt;
     * &lt;Error&gt;submission is missing an activity diagram&lt;/Error&gt;
     * &lt;/Errors&gt;
     * &lt;Reports&gt;
     * &lt;/Reports&gt;
     * &lt;/Validation&gt;
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateSubmission1() throws Exception {
        Submission submission = new Submission(new UMLUseCaseDiagram[0], new UMLActivityDiagram[0]);
        SubmissionValidator []validators = new SubmissionValidator[2];
        validators[0] = new DefaultUseCaseDiagramValidator();
        validators[1] = new DefaultActivityDiagramValidator();
        validationManager = new ValidationManager(validators, new XMLValidationOutputFormatter());
        String result = validationManager.validate(submission)[0].replaceAll("\n", "");

        assertTrue("Must contain this substring.",
                result.indexOf("<error>submission is missing an use case diagram</error>") != -1);
        assertTrue("Must contain this substring.",
                result.indexOf("<error>submission is missing an activity diagram</error>") != -1);
        assertTrue("Must contain this substring.", result.indexOf("<reports></reports>") != -1);
    }

    /**
     * <p>
     * Tests method validate(Submission submission).
     * </p>
     *
     * <p>
     * In thos test we crate manager with two validators:
     * DefaultUseCaseDiagramValidator and DefaultActivityDiagramValidator.
     * Submission will be valid, so there will be no ERRORs/REPORTs.
     * </p>
     *
     * <p>
     * Result must be:
     *
     * &lt;Validation&gt;
     * &lt;Errors&gt;
     * &lt;/Errors&gt;
     * &lt;Reports&gt;
     * &lt;/Reports&gt;
     * &lt;/Validation&gt;
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateSubmission2() throws Exception {
        UMLUseCaseDiagram useCaseDiagram = new UMLUseCaseDiagram();
        useCaseDiagram.setDiagramName("Use Case Diagram.");
        useCaseDiagram.setDiagramId("1");
        //create use case
        UMLUseCase useCase = new UMLUseCase();
        useCase.setElementName("Use Case");
        useCase.setElementId("11");
        useCase.setIsAbstract(false);
        useCaseDiagram.addElement(useCase.getElementId(), useCase);
        //create actor
        UMLActor actor = new UMLActor();
        actor.setElementName("Actor");
        actor.setElementId("111");
        useCaseDiagram.addElement(actor.getElementId(), actor);

        //create activity diagram
        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("Activity Diagram");
        activityDiagram.setDiagramId("2");
        //add initial and final states
        UMLState initial = new UMLState("22", UMLStateType.INITIAL_STATE, "initial");
        initial.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        activityDiagram.addElement(initial.getElementId(), initial);
        UMLState finall = new UMLState("222", UMLStateType.FINAL_STATE, "final1");
        finall.setElementType(UMLElementTypes.UML_FINAL_STATE);
        activityDiagram.addElement(finall.getElementId(), finall);

        Submission submission = new Submission(new UMLUseCaseDiagram[]{useCaseDiagram},
                new UMLActivityDiagram[]{activityDiagram});
        SubmissionValidator []validators = new SubmissionValidator[2];
        validators[0] = new DefaultUseCaseDiagramValidator();
        validators[1] = new DefaultActivityDiagramValidator();
        validationManager = new ValidationManager(validators, new XMLValidationOutputFormatter());
        String result = validationManager.validate(submission)[0].replaceAll("\n", "");

        assertTrue("Must contain this substring.",
                result.indexOf("<validation><errors></errors><reports></reports></validation>") != -1);
    }

    /**
     * <p>
     * Tests method validate(Submission submission).
     * </p>
     *
     * <p>
     * In thos test we crate manager with one validator - ActivityDiagramPathReportGenerator - that
     * will generate REPORTs with all unique paths.
     * Activity diagram will have two initial and two final states:
     * I1->AS3, I2->AS3, AS3->AS4, AS4->F5, AS4->F6
     * In the result we will have four paths:
     * path: I1->AS3->AS4->F5
     * path: I2->AS3->AS4->F5
     * path: I1->AS3->AS4->F6
     * path: I2->AS3->AS4->F6
     * </p>
     *
     * <p>
     * Result must be:
     *
     * &lt;Validation&gt;
     * &lt;Errors&gt;
     * &lt;/Errors&gt;
     * &lt;Reports&gt;
     * &lt;Diagram id=&quot;666&quot; name=&quot;Activity Diagram&quot; type=&quot;ActivityDiagram&quot;&gt;
     * &lt;Report&gt;
     * &lt;Path number='1'&gt;
     * &lt;Node&gt;inital2 - guardCondition2&lt;/Node&gt;
     * &lt;Node&gt;state1 - guardCondition3&lt;/Node&gt;
     * &lt;Node&gt;state2 - guardCondition4&lt;/Node&gt;
     * &lt;/Path&gt;
     * &lt;/Report&gt;
     * &lt;Report&gt;
     * &lt;Path number='2'&gt;
     * &lt;Node&gt;inital2 - guardCondition2&lt;/Node&gt;
     * &lt;Node&gt;state1 - guardCondition3&lt;/Node&gt;
     * &lt;Node&gt;state2 - guardCondition5&lt;/Node&gt;
     * &lt;/Path&gt;
     * &lt;/Report&gt;
     * &lt;Report&gt;
     * &lt;Path number='3'&gt;
     * &lt;Node&gt;initial1 - guardCondition1&lt;/Node&gt;
     * &lt;Node&gt;state1 - guardCondition3&lt;/Node&gt;
     * &lt;Node&gt;state2 - guardCondition4&lt;/Node&gt;
     * &lt;/Path&gt;
     * &lt;/Report&gt;
     * &lt;Report&gt;
     * &lt;Path number='4'&gt;
     * &lt;Node&gt;initial1 - guardCondition1&lt;/Node&gt;
     * &lt;Node&gt;state1 - guardCondition3&lt;/Node&gt;
     * &lt;Node&gt;state2 - guardCondition5&lt;/Node&gt;
     * &lt;/Path&gt;
     * &lt;/Report&gt;
     * &lt;/Diagram&gt;
     * &lt;/Reports&gt;
     * &lt;/Validation&gt;
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateSubmission3() throws Exception {
        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("Activity Diagram");
        activityDiagram.setDiagramId("666");

        UMLState as2 = new UMLState("3", UMLStateType.ACTION_STATE, "state1");
        as2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as3 = new UMLState("4", UMLStateType.ACTION_STATE, "state2");
        as3.setElementType(UMLElementTypes.UML_ACTION_STATE);

        UMLState i1 = new UMLState("1", UMLStateType.INITIAL_STATE, "initial1");
        i1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState i2 = new UMLState("2", UMLStateType.INITIAL_STATE, "inital2");
        i2.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState f5 = new UMLState("5", UMLStateType.FINAL_STATE, "final1");
        f5.setElementType(UMLElementTypes.UML_FINAL_STATE);
        UMLState f6 = new UMLState("6", UMLStateType.FINAL_STATE, "final2");
        f6.setElementType(UMLElementTypes.UML_FINAL_STATE);

        UMLTransition tr1 = new UMLTransition("7", "1", "3", "tr1", "guardCondition1", "guardEffect1");
        UMLTransition tr2 = new UMLTransition("8", "2", "3", "tr2", "guardCondition2", "guardEffect2");
        UMLTransition tr3 = new UMLTransition("9", "3", "4", "tr3", "guardCondition3", "guardEffect3");
        UMLTransition tr4 = new UMLTransition("10", "4", "5", "tr4", "guardCondition4", "guardEffect4");
        UMLTransition tr5 = new UMLTransition("11", "4", "6", "tr5", "guardCondition5", "guardEffect5");

        activityDiagram.addElement(as2.getElementId(), as2);
        activityDiagram.addElement(as3.getElementId(), as3);
        activityDiagram.addElement(i1.getElementId(), i1);
        activityDiagram.addElement(i2.getElementId(), i2);
        activityDiagram.addElement(f5.getElementId(), f5);
        activityDiagram.addElement(f6.getElementId(), f6);
        activityDiagram.addElement(tr1.getElementId(), tr1);
        activityDiagram.addElement(tr2.getElementId(), tr2);
        activityDiagram.addElement(tr3.getElementId(), tr3);
        activityDiagram.addElement(tr4.getElementId(), tr4);
        activityDiagram.addElement(tr5.getElementId(), tr5);

        Submission submission = new Submission(new UMLUseCaseDiagram[0],
                new UMLActivityDiagram[]{activityDiagram});
        SubmissionValidator []validators = new SubmissionValidator[1];
        validators[0] = new ActivityDiagramPathReportGenerator();
        validationManager = new ValidationManager(validators, new XMLValidationOutputFormatter());
        String result = validationManager.validate(submission)[0].replaceAll("\n", "");

        //perform some checking
        assertTrue("Must contain this substring.", result.indexOf("<report>"
                + "<Path number='1'>"
                + "<Node>inital2 - guardCondition2</Node>"
                + "<Node>state1 - guardCondition3</Node>"
                + "<Node>state2 - guardCondition4</Node>"
                + "</Path>"
                + "</report>") != -1);

        assertTrue("Must contain this substring.", result.indexOf("<report>"
                + "<Path number='4'>"
                + "<Node>initial1 - guardCondition1</Node>"
                + "<Node>state1 - guardCondition3</Node>"
                + "<Node>state2 - guardCondition5</Node>"
                + "</Path>"
                + "</report>") != -1);
    }

    /**
     * <p>
     * Tests method validate(Submission submission).
     * </p>
     *
     * <p>
     * In thos test we crate manager with two validators: ActivityDiagramPathReportGenerator - that
     * will generate REPORTs with all unique paths, and DefaultUseCaseDiagramValidator - that will return
     * errors about use case diagram absence.
     * </p>
     *
     * <p>
     * Activity diagram will have two initial and two final states:
     * I1->AS3, I2->AS3, AS3->AS4, AS4->F5, AS4->F6
     * In the result we will have four paths:
     * path: I1->AS3->AS4->F5
     * path: I2->AS3->AS4->F5
     * path: I1->AS3->AS4->F6
     * path: I2->AS3->AS4->F6
     * </p>
     *
     * <p>
     * Result must be:
     *
     * &lt;Validation&gt;
     * &lt;Errors&gt;
     * &lt;Error&gt;submission is missing an use case diagram&lt;/Error&gt;
     * &lt;/Errors&gt;
     * &lt;Reports&gt;
     * &lt;Diagram id=&quot;666&quot; name=&quot;Activity Diagram&quot; type=&quot;ActivityDiagram&quot;&gt;
     * &lt;Report&gt;
     * &lt;Path number='1'&gt;
     * &lt;Node&gt;inital2 - guardCondition2&lt;/Node&gt;
     * &lt;Node&gt;state1 - guardCondition3&lt;/Node&gt;
     * &lt;Node&gt;state2 - guardCondition4&lt;/Node&gt;
     * &lt;/Path&gt;
     * &lt;/Report&gt;
     * &lt;Report&gt;
     * &lt;Path number='2'&gt;
     * &lt;Node&gt;inital2 - guardCondition2&lt;/Node&gt;
     * &lt;Node&gt;state1 - guardCondition3&lt;/Node&gt;
     * &lt;Node&gt;state2 - guardCondition5&lt;/Node&gt;
     * &lt;/Path&gt;
     * &lt;/Report&gt;
     * &lt;Report&gt;
     * &lt;Path number='3'&gt;
     * &lt;Node&gt;initial1 - guardCondition1&lt;/Node&gt;
     * &lt;Node&gt;state1 - guardCondition3&lt;/Node&gt;
     * &lt;Node&gt;state2 - guardCondition4&lt;/Node&gt;
     * &lt;/Path&gt;
     * &lt;/Report&gt;
     * &lt;Report&gt;
     * &lt;Path number='4'&gt;
     * &lt;Node&gt;initial1 - guardCondition1&lt;/Node&gt;
     * &lt;Node&gt;state1 - guardCondition3&lt;/Node&gt;
     * &lt;Node&gt;state2 - guardCondition5&lt;/Node&gt;
     * &lt;/Path&gt;
     * &lt;/Report&gt;
     * &lt;/Diagram&gt;
     * &lt;/Reports&gt;
     * &lt;/Validation&gt;
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateSubmission4() throws Exception {
        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("Activity Diagram");
        activityDiagram.setDiagramId("666");

        UMLState as2 = new UMLState("3", UMLStateType.ACTION_STATE, "state1");
        as2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as3 = new UMLState("4", UMLStateType.ACTION_STATE, "state2");
        as3.setElementType(UMLElementTypes.UML_ACTION_STATE);

        UMLState i1 = new UMLState("1", UMLStateType.INITIAL_STATE, "initial1");
        i1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState i2 = new UMLState("2", UMLStateType.INITIAL_STATE, "inital2");
        i2.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState f5 = new UMLState("5", UMLStateType.FINAL_STATE, "final1");
        f5.setElementType(UMLElementTypes.UML_FINAL_STATE);
        UMLState f6 = new UMLState("6", UMLStateType.FINAL_STATE, "final2");
        f6.setElementType(UMLElementTypes.UML_FINAL_STATE);

        UMLTransition tr1 = new UMLTransition("7", "1", "3", "tr1", "guardCondition1", "guardEffect1");
        UMLTransition tr2 = new UMLTransition("8", "2", "3", "tr2", "guardCondition2", "guardEffect2");
        UMLTransition tr3 = new UMLTransition("9", "3", "4", "tr3", "guardCondition3", "guardEffect3");
        UMLTransition tr4 = new UMLTransition("10", "4", "5", "tr4", "guardCondition4", "guardEffect4");
        UMLTransition tr5 = new UMLTransition("11", "4", "6", "tr5", "guardCondition5", "guardEffect5");

        activityDiagram.addElement(as2.getElementId(), as2);
        activityDiagram.addElement(as3.getElementId(), as3);
        activityDiagram.addElement(i1.getElementId(), i1);
        activityDiagram.addElement(i2.getElementId(), i2);
        activityDiagram.addElement(f5.getElementId(), f5);
        activityDiagram.addElement(f6.getElementId(), f6);
        activityDiagram.addElement(tr1.getElementId(), tr1);
        activityDiagram.addElement(tr2.getElementId(), tr2);
        activityDiagram.addElement(tr3.getElementId(), tr3);
        activityDiagram.addElement(tr4.getElementId(), tr4);
        activityDiagram.addElement(tr5.getElementId(), tr5);

        Submission submission = new Submission(new UMLUseCaseDiagram[0],
                new UMLActivityDiagram[]{activityDiagram});
        SubmissionValidator []validators = new SubmissionValidator[2];
        validators[0] = new ActivityDiagramPathReportGenerator();
        validators[1] = new DefaultUseCaseDiagramValidator();
        validationManager = new ValidationManager(validators, new XMLValidationOutputFormatter());
        String result = validationManager.validate(submission)[0].replaceAll("\n", "");

        //perform some checking
        assertTrue("Must contain this substring.", result.indexOf("<report>"
                + "<Path number='1'>"
                + "<Node>inital2 - guardCondition2</Node>"
                + "<Node>state1 - guardCondition3</Node>"
                + "<Node>state2 - guardCondition4</Node>"
                + "</Path>"
                + "</report>") != -1);

        assertTrue("Must contain this substring.", result.indexOf("<report>"
                + "<Path number='4'>"
                + "<Node>initial1 - guardCondition1</Node>"
                + "<Node>state1 - guardCondition3</Node>"
                + "<Node>state2 - guardCondition5</Node>"
                + "</Path>"
                + "</report>") != -1);

        assertTrue("Must contain this substring.", result.indexOf("<errors>"
                + "<error>submission is missing an use case diagram</error>"
                + "</errors>") != -1);
    }

    /**
     * <p>
     * Tests method validateRaw(Submission submission) if submission is null.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateRawSubmissionIfNull() throws Exception {
        try {
            validationManager.validateRaw((Submission) null);
            fail("Submission cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validateRaw(String xmiString) if xmiString is null.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateRawXmiStringIfNull() throws Exception {
        try {
            validationManager.validateRaw((String) null);
            fail("XmiString cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validateRaw(String xmiString) if xmiString is empty.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateRawXmiStringIfEmpty() throws Exception {
        try {
            validationManager.validateRaw("   ");
            fail("XmiString cannot be empty.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validateRaw(File file) if file is null.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateRawFileIfNull() throws Exception {
        try {
            validationManager.validateRaw((File) null);
            fail("File cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validateRaw(FileInputStream stream) if stream is null.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateRawFileInputStreamIfNull() throws Exception {
        try {
            validationManager.validateRaw((FileInputStream) null);
            fail("FileInputStream cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method validateRaw(Submission submission).
     * </p>
     *
     * <p>
     * In thos test we crate manager with two validators:
     * DefaultUseCaseDiagramValidator and DefaultActivityDiagramValidator.
     * And submission will not have any diagrams.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateRawSubmission1() throws Exception {
        Submission submission = new Submission(new UMLUseCaseDiagram[0], new UMLActivityDiagram[0]);
        SubmissionValidator []validators = new SubmissionValidator[2];
        validators[0] = new DefaultUseCaseDiagramValidator();
        validators[1] = new DefaultActivityDiagramValidator();
        validationManager = new ValidationManager(validators, new XMLValidationOutputFormatter());
        ValidationOutput []outputs = validationManager.validateRaw(submission);

        //there must be 2 ERROR outputs
        assertEquals("There must be 2 ValidationOutputs.", 2, outputs.length);
        for (int i = 0, n = outputs.length; i < n; i++) {
            assertEquals("Output must have type ERROR.",
                    ValidationOutputType.ERROR, outputs[i].getType());
        }
    }

    /**
     * <p>
     * Tests method validateRaw(Submission submission).
     * </p>
     *
     * <p>
     * In thos test we crate manager with two validators:
     * DefaultUseCaseDiagramValidator and DefaultActivityDiagramValidator.
     * Submission will be valid, so there will be no ERRORs/REPORTs.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateRawSubmission2() throws Exception {
        UMLUseCaseDiagram useCaseDiagram = new UMLUseCaseDiagram();
        useCaseDiagram.setDiagramName("Use Case Diagram.");
        useCaseDiagram.setDiagramId("1");
        //create use case
        UMLUseCase useCase = new UMLUseCase();
        useCase.setElementName("Use Case");
        useCase.setElementId("11");
        useCase.setIsAbstract(false);
        useCaseDiagram.addElement(useCase.getElementId(), useCase);
        //create actor
        UMLActor actor = new UMLActor();
        actor.setElementName("Actor");
        actor.setElementId("111");
        useCaseDiagram.addElement(actor.getElementId(), actor);

        //create activity diagram
        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("Activity Diagram");
        activityDiagram.setDiagramId("2");
        //add initial and final states
        UMLState initial = new UMLState("22", UMLStateType.INITIAL_STATE, "initial");
        initial.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        activityDiagram.addElement(initial.getElementId(), initial);
        UMLState finall = new UMLState("222", UMLStateType.FINAL_STATE, "final1");
        finall.setElementType(UMLElementTypes.UML_FINAL_STATE);
        activityDiagram.addElement(finall.getElementId(), finall);

        UMLUseCaseDiagram []useCaseDiagrams = new UMLUseCaseDiagram[1];
        useCaseDiagrams[0] = useCaseDiagram;
        UMLActivityDiagram []activityDiagrams = new UMLActivityDiagram[1];
        activityDiagrams[0] = activityDiagram;
        Submission submission = new Submission(useCaseDiagrams, activityDiagrams);
        SubmissionValidator []validators = new SubmissionValidator[2];
        validators[0] = new DefaultUseCaseDiagramValidator();
        validators[1] = new DefaultActivityDiagramValidator();
        validationManager = new ValidationManager(validators, new XMLValidationOutputFormatter());
        ValidationOutput []outputs = validationManager.validateRaw(submission);

        //there must be no outputs
        assertEquals("There must be no ValidationOutputs.", 0, outputs.length);
    }

    /**
     * <p>
     * Tests method validateRaw(Submission submission).
     * </p>
     *
     * <p>
     * In thos test we crate manager with one validator - ActivityDiagramPathReportGenerator - that
     * will generate REPORTs with all unique paths.
     * In the result we will have four paths.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateRawSubmission3() throws Exception {
        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("Activity Diagram");
        activityDiagram.setDiagramId("666");

        UMLState as2 = new UMLState("3", UMLStateType.ACTION_STATE, "state1");
        as2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as3 = new UMLState("4", UMLStateType.ACTION_STATE, "state2");
        as3.setElementType(UMLElementTypes.UML_ACTION_STATE);

        UMLState i1 = new UMLState("1", UMLStateType.INITIAL_STATE, "initial1");
        i1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState i2 = new UMLState("2", UMLStateType.INITIAL_STATE, "inital2");
        i2.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState f5 = new UMLState("5", UMLStateType.FINAL_STATE, "final1");
        f5.setElementType(UMLElementTypes.UML_FINAL_STATE);
        UMLState f6 = new UMLState("6", UMLStateType.FINAL_STATE, "final2");
        f6.setElementType(UMLElementTypes.UML_FINAL_STATE);

        UMLTransition tr1 = new UMLTransition("7", "1", "3", "tr1", "guardCondition1", "guardEffect1");
        UMLTransition tr2 = new UMLTransition("8", "2", "3", "tr2", "guardCondition2", "guardEffect2");
        UMLTransition tr3 = new UMLTransition("9", "3", "4", "tr3", "guardCondition3", "guardEffect3");
        UMLTransition tr4 = new UMLTransition("10", "4", "5", "tr4", "guardCondition4", "guardEffect4");
        UMLTransition tr5 = new UMLTransition("11", "4", "6", "tr5", "guardCondition5", "guardEffect5");

        activityDiagram.addElement(as2.getElementId(), as2);
        activityDiagram.addElement(as3.getElementId(), as3);
        activityDiagram.addElement(i1.getElementId(), i1);
        activityDiagram.addElement(i2.getElementId(), i2);
        activityDiagram.addElement(f5.getElementId(), f5);
        activityDiagram.addElement(f6.getElementId(), f6);
        activityDiagram.addElement(tr1.getElementId(), tr1);
        activityDiagram.addElement(tr2.getElementId(), tr2);
        activityDiagram.addElement(tr3.getElementId(), tr3);
        activityDiagram.addElement(tr4.getElementId(), tr4);
        activityDiagram.addElement(tr5.getElementId(), tr5);

        Submission submission = new Submission(new UMLUseCaseDiagram[0],
                new UMLActivityDiagram[]{activityDiagram});
        SubmissionValidator []validators = new SubmissionValidator[1];
        validators[0] = new ActivityDiagramPathReportGenerator();
        validationManager = new ValidationManager(validators, new XMLValidationOutputFormatter());
        ValidationOutput []outputs = validationManager.validateRaw(submission);

        //there must be 4 REPORT outputs
        assertEquals("There must be 4 ValidationOutputs.", 4, outputs.length);
        for (int i = 0, n = outputs.length; i < n; i++) {
            assertEquals("Output must have type ERROR.", ValidationOutputType.REPORT, outputs[i].getType());
        }
    }

    /**
     * <p>
     * Tests method validateRaw(Submission submission).
     * </p>
     *
     * <p>
     * In thos test we crate manager with two validators: ActivityDiagramPathReportGenerator - that
     * will generate REPORTs with all unique paths, and DefaultUseCaseDiagramValidator - that will return
     * errors about use case diagram absence.
     * </p>
     *
     * <p>
     * In the result we will have four REPORTs and one ERROR.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateRawSubmission4() throws Exception {
        UMLActivityDiagram activityDiagram = new UMLActivityDiagram();
        activityDiagram.setDiagramName("Activity Diagram");
        activityDiagram.setDiagramId("666");

        UMLState as2 = new UMLState("3", UMLStateType.ACTION_STATE, "state1");
        as2.setElementType(UMLElementTypes.UML_ACTION_STATE);
        UMLState as3 = new UMLState("4", UMLStateType.ACTION_STATE, "state2");
        as3.setElementType(UMLElementTypes.UML_ACTION_STATE);

        UMLState i1 = new UMLState("1", UMLStateType.INITIAL_STATE, "initial1");
        i1.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState i2 = new UMLState("2", UMLStateType.INITIAL_STATE, "inital2");
        i2.setElementType(UMLElementTypes.UML_PSEUDO_STATE);
        UMLState f5 = new UMLState("5", UMLStateType.FINAL_STATE, "final1");
        f5.setElementType(UMLElementTypes.UML_FINAL_STATE);
        UMLState f6 = new UMLState("6", UMLStateType.FINAL_STATE, "final2");
        f6.setElementType(UMLElementTypes.UML_FINAL_STATE);

        UMLTransition tr1 = new UMLTransition("7", "1", "3", "tr1", "guardCondition1", "guardEffect1");
        UMLTransition tr2 = new UMLTransition("8", "2", "3", "tr2", "guardCondition2", "guardEffect2");
        UMLTransition tr3 = new UMLTransition("9", "3", "4", "tr3", "guardCondition3", "guardEffect3");
        UMLTransition tr4 = new UMLTransition("10", "4", "5", "tr4", "guardCondition4", "guardEffect4");
        UMLTransition tr5 = new UMLTransition("11", "4", "6", "tr5", "guardCondition5", "guardEffect5");

        activityDiagram.addElement(as2.getElementId(), as2);
        activityDiagram.addElement(as3.getElementId(), as3);
        activityDiagram.addElement(i1.getElementId(), i1);
        activityDiagram.addElement(i2.getElementId(), i2);
        activityDiagram.addElement(f5.getElementId(), f5);
        activityDiagram.addElement(f6.getElementId(), f6);
        activityDiagram.addElement(tr1.getElementId(), tr1);
        activityDiagram.addElement(tr2.getElementId(), tr2);
        activityDiagram.addElement(tr3.getElementId(), tr3);
        activityDiagram.addElement(tr4.getElementId(), tr4);
        activityDiagram.addElement(tr5.getElementId(), tr5);

        Submission submission = new Submission(new UMLUseCaseDiagram[0],
                new UMLActivityDiagram[]{activityDiagram});
        SubmissionValidator []validators = new SubmissionValidator[2];
        validators[0] = new ActivityDiagramPathReportGenerator();
        validators[1] = new DefaultUseCaseDiagramValidator();
        validationManager = new ValidationManager(validators, new XMLValidationOutputFormatter());
        ValidationOutput []outputs = validationManager.validateRaw(submission);

        int reports = 0;
        int errors = 0;
        //there must be 5 outputs
        assertEquals("There must be 5 ValidationOutputs.", 5, outputs.length);
        for (int i = 0, n = outputs.length; i < n; i++) {
            if (outputs[i].getType() == ValidationOutputType.REPORT) {
                reports++;
            } else if (outputs[i].getType() == ValidationOutputType.ERROR) {
                errors++;
            }
        }

        assertEquals("There must be 4 REPORTs.", 4, reports);
        assertEquals("There must be 1 ERROR.", 1, errors);
    }

    /**
     * <p>
     * Tests method validate(xmiString).
     * </p>
     *
     * <p>
     * Xmi string contains:
     * - one use case diagram, which has 2 actors (with names), and 3 use cases (with names and
     * noå abstract);
     * - one activity diagram, which has 2 initial states, 3 action states, and one final state.
     * </p>
     *
     * <p>
     * We will use three validators: DefaultUseCaseDiagramValidator, DefaultActivityDiagramValidator,
     * ActivityDiagramPathReportGenerator, and XMLValidationOutputFormatter formatter.
     * There will be no ERROR messages, and two REPORT's - about paths in activity diagram.
     * </p>
     *
     * <p>
     * Activity diagram:
     * I1->AS1, I2->AS2, AS1->AS3, AS2->AS3, AS3->F1.
     *
     * Paths:
     * I1->AS1->AS3->F1;
     * I2->AS2->AS3->F1.
     * </p>
     *
     * <p>
     * Result must look like:
     *
     * &lt;Validation&gt;
     * &lt;Errors&gt;
     * &lt;/Errors&gt;
     * &lt;Reports&gt;
     * &lt;Diagram id=&quot;I808680m1098bca64b3mm7ea5&quot;
     *      name=&quot;Activity diagram_1&quot; type=&quot;ActivityDiagram&quot;&gt;
     * &lt;Report&gt;
     * &lt;Path number='1'&gt;
     * &lt;Node&gt;Second Initial State - guard condition 2&lt;/Node&gt;
     * &lt;Node&gt;Action State 2 - guard condition 4&lt;/Node&gt;
     * &lt;Node&gt;Action State 3 - guard condition 5&lt;/Node&gt;
     * &lt;/Path&gt;
     * &lt;/Report&gt;
     * &lt;Report&gt;
     * &lt;Path number='2'&gt;
     * &lt;Node&gt;First Initial State - guard condition 1&lt;/Node&gt;
     * &lt;Node&gt;Action State 1 - guard condition 3&lt;/Node&gt;
     * &lt;Node&gt;Action State 3 - guard condition 5&lt;/Node&gt;
     * &lt;/Path&gt;
     * &lt;/Report&gt;
     * &lt;/Diagram&gt;
     * &lt;/Reports&gt;
     * &lt;/Validation&gt;
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateXmiString() throws Exception {
        SubmissionValidator []validators = new SubmissionValidator[3];
        validators[0] = new ActivityDiagramPathReportGenerator();
        validators[1] = new DefaultUseCaseDiagramValidator();
        validators[2] = new DefaultActivityDiagramValidator();
        validationManager = new ValidationManager(validators, new XMLValidationOutputFormatter());
        String fileName = "test_files/unittests/xmi/xmi_string.xmi";
        String xmiString = Helper.getFileAsString(fileName);
        String result = validationManager.validate(xmiString)[0].replaceAll("\n", "");

        //perform some checkings
        assertTrue("Must contain this substring.", result.indexOf("<errors></errors>") != -1);

        assertTrue("Must contain this substring.", result.indexOf("<report>"
                + "<Path number='1'>"
                + "<Node>Second Initial State - guard condition 2</Node>"
                + "<Node>Action State 2 - guard condition 4</Node>"
                + "<Node>Action State 3 - guard condition 5</Node>"
                + "</Path>"
                + "</report>") != -1);

        assertTrue("Must contain this substring.", result.indexOf("<report>"
                + "<Path number='2'>"
                + "<Node>First Initial State - guard condition 1</Node>"
                + "<Node>Action State 1 - guard condition 3</Node>"
                + "<Node>Action State 3 - guard condition 5</Node>"
                + "</Path>"
                + "</report>") != -1);
    }

    /**
     * <p>
     * Tests method validateRaw(xmiString).
     * </p>
     *
     * <p>
     * Xmi string contains:
     * - one use case diagram, which has 2 actors (with names), and 3 use cases (with names and
     * noå abstract);
     * - one activity diagram, which has 2 initial states, 3 action states, and one final state.
     * </p>
     *
     * <p>
     * We will use five validators: DefaultUseCaseDiagramValidator, DefaultActivityDiagramValidator,
     * ActivityDiagramPathReportGenerator, DefaultActivityDiagramNamingValidatorand,
     * DefaultUseCaseDiagramNamingValidator, and XMLValidationOutputFormatter formatter.
     * There will be 4 ERRORs messages - use cases have no corresponding activity diagrams and
     * activity diagram has no corresponding use case, and 2 REPORT's - about paths in activity diagram.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateRawXmiString() throws Exception {
        String fileName = "test_files/unittests/xmi/xmi_string.xmi";
        String xmiString = Helper.getFileAsString(fileName);
        ValidationOutput []outputs = validationManager.validateRaw(xmiString);
        assertEquals("There must be 6 outputs.", 6, outputs.length);

        int error = 0;
        int report = 0;
        for (int i = 0, n = outputs.length; i < n; i++) {
            if (outputs[i].getType() == ValidationOutputType.ERROR) {
                error++;
            } else if (outputs[i].getType() == ValidationOutputType.REPORT) {
                report++;
            }
        }

        assertEquals("There must be 4 ERROR outputs.", 4, error);
        assertEquals("There must be 2 REPORT outputs.", 2, report);
    }

    /**
     * <p>
     * Tests method validate(stream).
     * </p>
     *
     * <p>
     * Stream contains:
     * - one use case diagram, which has 1 actors (with name), and 2 use cases (with names and
     * noå abstract);
     * - two activity diagrams, that corresponds to use cases
     * </p>
     *
     * <p>
     * We will use two validators: DefaultUseCaseDiagramValidator and
     * DefaultActivityDiagramNamingValidatorand - there must be no errors and reports.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateFIS() throws Exception {
        SubmissionValidator []validators = new SubmissionValidator[2];
        validators[0] = new DefaultActivityDiagramNamingValidator();
        validators[1] = new DefaultUseCaseDiagramValidator();
        validationManager = new ValidationManager(validators, new TextValidationOutputFormatter());
        String fileName = "test_files/unittests/xmi/file_stream.xmi";
        FileInputStream fis = new FileInputStream(fileName);
        String result[] = validationManager.validate(fis);

        //there must be no strings in result array, because validation is successful
        assertEquals("There must be no outputs.", 0, result.length);
    }

    /**
     * <p>
     * Tests method validateRaw(stream).
     * </p>
     *
     * <p>
     * Stream contains:
     * - one use case diagram, which has 2 actors (with names), and 3 use cases (with names and
     * noå abstract);
     * - one activity diagram, which has 2 initial states, 3 action states, and one final state.
     * </p>
     *
     * <p>
     * We will use five validators: DefaultUseCaseDiagramValidator, DefaultActivityDiagramValidator,
     * ActivityDiagramPathReportGenerator, DefaultActivityDiagramNamingValidatorand,
     * DefaultUseCaseDiagramNamingValidator, and TextValidationOutputFormatter formatter.
     * There will be no ERRORs messages, and 2 REPORT's - about paths in activity diagrams.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateRawFIS() throws Exception {
        SubmissionValidator []validators = new SubmissionValidator[5];
        validators[0] = new DefaultActivityDiagramNamingValidator();
        validators[1] = new DefaultUseCaseDiagramValidator();
        validators[2] = new DefaultActivityDiagramValidator();
        validators[3] = new ActivityDiagramPathReportGenerator();
        validators[4] = new DefaultUseCaseDiagramNamingValidator();
        validationManager = new ValidationManager(validators, new TextValidationOutputFormatter());

        String fileName = "test_files/unittests/xmi/file_stream.xmi";
        FileInputStream fis = new FileInputStream(fileName);
        ValidationOutput []outputs = validationManager.validateRaw(fis);
        //there must be REPORT outputs
        assertEquals("There must be 2 outputs.", 2, outputs.length);
        for (int i = 0, n = outputs.length; i < n; i++) {
            assertEquals("Must be REPORT output.", ValidationOutputType.REPORT, outputs[i].getType());
        }
    }

    /**
     * <p>
     * Tests method validate(file).
     * </p>
     *
     * <p>
     * File contains:
     * - one activity diagrams, that has 2 initial and 2 final states, and 2 loops.
     * </p>
     *
     * <p>
     * We will use one validator ActivityDiagramPathReportGenerator so that to get all unique paths.
     * </p>
     *
     * <p>
     * In result we will have 12 paths.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateFile() throws Exception {
        SubmissionValidator []validators = new SubmissionValidator[1];
        validators[0] = new ActivityDiagramPathReportGenerator();
        validationManager = new ValidationManager(validators, new TextValidationOutputFormatter());
        String fileName = "test_files/unittests/xmi/loops_file.xmi";
        String result[] = validationManager.validate(new File(fileName));
        //there must be 12 paths
        assertEquals("There must be 12 paths.", 12, result.length);
        for (int i = 0, n = result.length; i < n; i++) {
            System.out.println(result[i] + "\n");
        }
    }

    /**
     * <p>
     * Tests method validateRaw(file).
     * </p>
     *
     * <p>
     * File contains:
     * - one activity diagrams, that has 2 initial and 2 final states, and 2 loops.
     * </p>
     *
     * <p>
     * We will use one validator ActivityDiagramPathReportGenerator so that to get all unique paths,
     * DefaultActivityDiagramValidator and DefaultActivityDiagramNamingValidator validators.
     * </p>
     *
     * <p>
     * In result there will be 12 REPORTs (paths) and 1 ERROR about absence of corresponding use case.
     * </p>
     *
     * @throws Exception exception
     */
    public void testValidateRawFile() throws Exception {
        SubmissionValidator []validators = new SubmissionValidator[3];
        validators[0] = new DefaultActivityDiagramValidator();
        validators[1] = new ActivityDiagramPathReportGenerator();
        validators[2] = new DefaultActivityDiagramNamingValidator();
        validationManager = new ValidationManager(validators, new TextValidationOutputFormatter());

        String fileName = "test_files/unittests/xmi/loops_file.xmi";
        ValidationOutput []outputs = validationManager.validateRaw(new File(fileName));
        assertEquals("There must be 12 outputs.", 13, outputs.length);

        int error = 0;
        int report = 0;
        for (int i = 0, n = outputs.length; i < n; i++) {
            if (outputs[i].getType() == ValidationOutputType.ERROR) {
                error++;
            } else if (outputs[i].getType() == ValidationOutputType.REPORT) {
                report++;
            }
        }

        assertEquals("There must be 1 ERROR output.", 1, error);
        assertEquals("There must be 12 REPORT outputs.", 12, report);
    }
}