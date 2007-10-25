/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.validators.simple;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.registration.validation.TestHelper;
import com.topcoder.registration.validation.RegistrationValidationHelper;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.util.datavalidator.AbstractObjectValidator;
import com.topcoder.util.datavalidator.BundleInfo;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for class ProjectInPhaseValidator.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectInPhaseValidatorTest extends TestCase {

    /**
     * <p>
     * Represents a valid formed namespace for this class.
     * </p>
     */
    private static final String NAMESPACE = "simple.Good";

    /**
     * <p>
     * Represents the resource bundle information used by this validator.
     * </p>
     */
    private BundleInfo bundleInfo;

    /**
     * <p>
     * Represents the minimum reliability the user must have for the given
     * rating type.
     * </p>
     *
     */
    private long phaseId = 3;

    /**
     * <p>
     * The ProjectInPhaseValidator instance for testing purpose.
     * </p>
     */
    private ProjectInPhaseValidator validator;




    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("DataValidationRegistrationValidator.xml");
        TestHelper.loadXMLConfig("Document_Generator.xml");
        TestHelper.loadXMLConfig("ProjectInPhaseValidator.xml");

        bundleInfo = RegistrationValidationHelper.createBundleInfo(NAMESPACE);

        validator = new ProjectInPhaseValidator(NAMESPACE);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");

    }

    /**
     * Clears the testing environment.
     *
     * @throws Exception
     *             when error occurs
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies ProjectInPhaseValidator subclasses AbstractObjectValidator.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
                "ProjectInPhaseValidator does not subclass AbstractObjectValidator.",
                new ProjectInPhaseValidator(NAMESPACE) instanceof AbstractObjectValidator);
    }

    /**
     * <p>
     * Tests ctor ProjectInPhaseValidator(BundleInfo, long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created ProjectInPhaseValidator instance should not
     * be null.
     * </p>
     *
     */
    public void testCtor1() {


        validator = new ProjectInPhaseValidator(bundleInfo, phaseId);
        assertNotNull(
                "Failed to create a new ProjectInPhaseValidator instance.",
                validator);
        BundleInfo bundleInfoField = validator.getBundleInfo();
        assertEquals("Failed to initialize the bundleInfo field.", null,
                TestHelper.compareBundleInfos(bundleInfoField, bundleInfo));


    }

    /**
     * <p>
     * Tests ctor ProjectInPhaseValidator(BundleInfo, long) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the bundleInfo is invalid.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_InvalidBundleInfo() {

        try {
            bundleInfo = new BundleInfo();
            validator = new ProjectInPhaseValidator(bundleInfo, phaseId);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectInPhaseValidator(BundleInfo, long) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the bundleInfo is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_NullBundleInfo() {


        try {
            validator = new ProjectInPhaseValidator(null, phaseId);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectInPhaseValidator(BundleInfo, long) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the phaseId is negative.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_NegativeMinimumReliability() {


        try {
            validator = new ProjectInPhaseValidator(bundleInfo, -1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectInPhaseValidator(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created ProjectInPhaseValidator instance should not
     * be null.
     * </p>
     *
     */
    public void testCtor2() {
        validator = new ProjectInPhaseValidator(NAMESPACE);
        BundleInfo bundleInfoField = validator.getBundleInfo();
        assertEquals("Failed to initialize the bundleInfo field.", null,
                TestHelper.compareBundleInfos(bundleInfoField, bundleInfo));
    }

    /**
     * <p>
     * Tests ctor ProjectInPhaseValidator(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created ProjectInPhaseValidator instance should not
     * be null.
     * </p>
     *
     */
    public void testCtor2_NoBundleName() {
        validator = new ProjectInPhaseValidator("simple.NoBundleName");
        assertNotNull("Failed to create ProjectInPhaseValidator instance.",
                validator);
    }

    /**
     * <p>
     * Tests ctor ProjectInPhaseValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor2_NullNamespace() {

        try {
            new ProjectInPhaseValidator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor ProjectInPhaseValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace is empty.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor2_EmptyNamespace() {

        try {
            new ProjectInPhaseValidator("");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectInPhaseValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace is full of space.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor2_TrimmedEmptyNamespace() {

        try {
            new ProjectInPhaseValidator("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectInPhaseValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace is unknown.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_UnknownNamespace() {

        try {
            new ProjectInPhaseValidator("unknownNamespace");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectInPhaseValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when bundleInfo is not configured properly.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_InvalidBundleInfo() {

        try {
            new ProjectInPhaseValidator("simple.InvalidBundleInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectInPhaseValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when phaseId property is missing in the namespace.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_MissingValidatorInfo() {

        try {
            new ProjectInPhaseValidator("simple.MissingValidatorInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectInPhaseValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the phaseId property value is invalid.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_InvalidValidatorInfo() {

        try {
            new ProjectInPhaseValidator("simple.InvalidValidatorInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor ProjectInPhaseValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the phaseId is negative.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_NegativeValidatorInfo() {

        try {
            new ProjectInPhaseValidator("simple.NegativeValidatorInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ProjectInPhaseValidator#getMessage(Object) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the obj is valid.
     * </p>
     *
     * <p>
     * Should have returned null if obj is valid.
     * </p>
     *
     * <p>
     * It verifies the method return null if obj is valid. Otherwise, an error
     * message is returned.
     * </p>
     *
     */
    public void testGetMessage_True() {


        Object obj = TestHelper.createValidationInfoForTest();

        // get an open phase, sets the phase.id and adds it to the project
        FullProjectData project = ((ValidationInfo) obj).getProject();
        PhaseStatus openPhaseStatus = PhaseStatus.OPEN;
        long phaseLength = 7;
        Phase phase = new Phase(project, phaseLength);
        phase.setPhaseStatus(openPhaseStatus);
        phase.setId(3);
        project.clearPhases();
        project.addPhase(phase);

        String message = validator.getMessage(obj);
        assertNull("Failed to get the error messages priority.", message);


    }

    /**
     * <p>
     * Tests ProjectInPhaseValidator#getMessage(Object) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the obj is invalid.
     * </p>
     *
     * <p>
     * Should have returned an error message if obj is invalid.
     * </p>
     *
     */
    public void testGetMessage_False() {


        Object obj = TestHelper.createValidationInfoForTest();

        // get an open phase, sets the phase.id and adds it to the project
        FullProjectData project = ((ValidationInfo) obj).getProject();
        PhaseStatus openPhaseStatus = PhaseStatus.OPEN;
        long phaseLength = 7;
        Phase phase = new Phase(project, phaseLength);
        phase.setPhaseStatus(openPhaseStatus);
        phase.setId(1);
        project.clearPhases();
        project.addPhase(phase);

        String message = validator.getMessage(obj);
        assertNotNull("Failed to get the error messages correctly.", message);
        assertTrue("Failed to get the error messages correctly.", (message
                .trim().length() > 0));


    }

    /**
     * <p>
     * Tests ProjectInPhaseValidator#getMessage(Object) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the obj is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testGetMessage_Null() {

        try {
            validator.getMessage(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ProjectInPhaseValidator#getMessage(Object) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the obj is not ValidationInfo.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testGetMessage_NotValidationInfo() {

        try {
            validator.getMessage(new Object());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ProjectInPhaseValidator#getMessage(Object) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the template is bad formed when generating
     * message.
     * </p>
     *
     * <p>
     * Should have thrown ValidationProcessingException.
     * </p>
     *
     */
    public void testGetMessage_BadTemplate() {


        bundleInfo.setDefaultMessage("badTemplate.txt");
        validator = new ProjectInPhaseValidator(bundleInfo, phaseId);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");

        Object obj = TestHelper.createValidationInfoForTest();

        // get an open phase, sets the phase.id and adds it to the project
        FullProjectData project = ((ValidationInfo) obj).getProject();
        PhaseStatus openPhaseStatus = PhaseStatus.OPEN;
        long phaseLength = 7;
        Phase phase = new Phase(project, phaseLength);
        phase.setPhaseStatus(openPhaseStatus);
        phase.setId(1);
        project.clearPhases();
        project.addPhase(phase);

        try {
            validator.getMessage(obj);
            fail("ValidationProcessingException expected.");
        } catch (ValidationProcessingException e) {
            // good
        }
    }

}
