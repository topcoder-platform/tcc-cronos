/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.apps.screening.applications.specification.failuretests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;

import com.topcoder.apps.screening.applications.specification.ConfigurationException;
import com.topcoder.apps.screening.applications.specification.Submission;
import com.topcoder.apps.screening.applications.specification.SubmissionValidator;
import com.topcoder.apps.screening.applications.specification.SubmissionValidatorFactory;
import com.topcoder.apps.screening.applications.specification.ValidationManager;
import com.topcoder.apps.screening.applications.specification.ValidationOutputFormatter;
import com.topcoder.apps.screening.applications.specification.impl.DefaultSubmissionValidatorFactory;
import com.topcoder.apps.screening.applications.specification.impl.formatters.TextValidationOutputFormatter;


/**
 * <p>
 * Failure tests for <code>ValidationManager</code>.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class ValidationManagerFailureTests extends TestCase {
    /** ValidationManager instance used for failure tests. */
    private ValidationManager validation;

    /**
     * Config the setup environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();
        validation = new ValidationManager(TestHelper.VALIDATION_MANAGER_NAMESPACE);
    }

    /**
     * Clear config environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.cleanConfig();
    }

    /**
     * Failure test for <code>ValidationManager()</code>.
     */
    public void testValidationManager() {
        try {
            new ValidationManager();

            // expected
        } catch (ConfigurationException e) {
            fail("ConfigurationException should not be thrown.");
        }
    }

    /**
     * Failure test for <code>ValidationManager(String)</code>.
     */
    public void testValidationManagerString() {
        try {
            new ValidationManager(TestHelper.VALIDATION_MANAGER_NAMESPACE);

            // expected
        } catch (ConfigurationException e) {
            fail("ConfigurationException should not be thrown.");
        }
    }

    /**
     * Failure test for <code>ValidationManager(String)</code>, with null namespace, IAE expected.
     *
     * @throws Exception to JUnit
     */
    public void testValidationManagerString_null_namespace()
        throws Exception {
        try {
            new ValidationManager((String) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationManager(String)</code>, with empty namespace, IAE expected.
     *
     * @throws Exception to JUnit
     */
    public void testValidationManagerString_empty_namespace()
        throws Exception {
        try {
            new ValidationManager("      ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationManager(String)</code>, with unknown namespace, CE expected.
     */
    public void testValidationManagerString_unknown_namespace() {
        try {
            new ValidationManager("com.topcoder.apps.screening.application.specification.ValidationManager_unknown");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationManager(String)</code>, with no factory_class property contained, CE expected.
     */
    public void testValidationManagerString_null_factory_class() {
        try {
            new ValidationManager(
                "com.topcoder.apps.screening.application.specification.ValidationManager_no_factory_class");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationManager(String)</code>, with factory_class missing property value, CE expected.
     */
    public void testValidationManagerString_empty_factory_class() {
        try {
            new ValidationManager(
                "com.topcoder.apps.screening.application.specification.ValidationManager_empty_factory_class");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationManager(String)</code>, with invalid factory_class, CE expected.
     */
    public void testValidationManagerString_invalid_factory_class() {
        try {
            new ValidationManager(
                "com.topcoder.apps.screening.application.specification.ValidationManager_invalid_factory_class");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationManager(String)</code>, with invalid factory_namespace, CE expected.
     */
    public void testValidationManagerString_invalid_factory_namespace() {
        try {
            new ValidationManager(
                "com.topcoder.apps.screening.application.specification.ValidationManager_invalid_factory_namespace");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationManager(String)</code>, with factory_namespace missing property value, CE
     * expected.
     */
    public void testValidationManagerString_empty_factory_namespace() {
        try {
            new ValidationManager(
                "com.topcoder.apps.screening.application.specification.ValidationManager_empty_factory_namespace");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationManager(SubmissionValidator[], ValidationOutputFormatter)</code>, with null
     * validators, IAE expected.
     */
    public void testValidationManagerSubmissionValidatorArrayValidationOutputFormatter_null_validators() {
        try {
            new ValidationManager(null, new TextValidationOutputFormatter());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationManager(SubmissionValidator[], ValidationOutputFormatter)</code>, with empty
     * validators array, IAE expected.
     */
    public void testValidationManagerSubmissionValidatorArrayValidationOutputFormatter_empty_validators_array() {
        try {
            new ValidationManager(new SubmissionValidator[0], new TextValidationOutputFormatter());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationManager(SubmissionValidator[], ValidationOutputFormatter)</code>, with null
     * formatter, IAE expected.
     */
    public void testValidationManagerSubmissionValidatorArrayValidationOutputFormatter_null_formatter() {
        try {
            new ValidationManager(new SubmissionValidator[1], null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationManager(SubmissionValidatorFactory)</code>.
     */
    public void testValidationManagerSubmissionValidatorFactory() {
        try {
            new ValidationManager(new SubmissionValidatorFactory() {

                /**
                 * <p>
                 * .
                 * </p>
                 * @return
                 * @see com.topcoder.apps.screening.applications.specification.SubmissionValidatorFactory#createFormatter()
                 */
                public ValidationOutputFormatter createFormatter() {
                    return null;
                }

                /**
                 * <p>
                 * .
                 * </p>
                 * @return
                 * @see com.topcoder.apps.screening.applications.specification.SubmissionValidatorFactory#createValidators()
                 */
                public SubmissionValidator[] createValidators() {
                    return new SubmissionValidator[0];
                }
                
            });

            // expected
        } catch (ConfigurationException e) {
            fail("ConfigurationException should not be thrown.");
        }
    }

    /**
     * Failure test for <code>ValidationManager(SubmissionValidatorFactory)</code>, with null factory, IAE expected.
     *
     * @throws Exception to JUnit
     */
    public void testValidationManagerSubmissionValidatorFactory_null_factory()
        throws Exception {
        try {
            new ValidationManager((SubmissionValidatorFactory) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>ValidationManager(SubmissionValidatorFactory)</code>, with illegal factory, CE expected.
     */
    public void testValidationManagerSubmissionValidatorFactory_illegal_factory() {
        try {
            new ValidationManager(new DefaultSubmissionValidatorFactory(
                    "com.topcoder.apps.screening.applications.specification.impl.IllegalSubmissionValidatorFactory"));
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }
    
    /**
     * Failure test for <code>validate(Submission)</code>, with null submission, IAE expected.
     *
     * @throws Exception to JUnit
     */
    public void testValidateSubmission_null_submission()
        throws Exception {
        try {
            validation.validate((Submission) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>validate(String)</code>, with null xmiString, IAE expected.
     *
     * @throws Exception to JUnit
     */
    public void testValidateString_null_xmiString() throws Exception {
        try {
            validation.validate((String) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>validate(String)</code>, with legal xmi string, should be ok.
     *
     * @throws Exception to JUnit
     */
    public void testValidateString_legal_xmiString() throws Exception {
        String xmi = getXmiFromFile("failure/legal.xmi");
        
        try {
            validation.validate(xmi);
            // expected
        } catch (Exception e) {
            fail("should be ok.");
        }
    }
    
    /**
     * Failure test for <code>validate(File)</code>, with null file, IAE expected.
     *
     * @throws Exception to JUnit
     */
    public void testValidateString_null_file() throws Exception {
        try {
            validation.validate((File) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    /**
     * Failure test for <code>validate(File)</code>, with legal file, should be ok.
     *
     * @throws Exception to JUnit
     */
    public void testValidateString_legal_xmifile() throws Exception {
        File xmi = new File(TestHelper.class.getClassLoader().getResource("failure/legal.xmi").getFile());
        
        try {
            validation.validate(xmi);
            // expected
        } catch (Exception e) {
            fail("should be ok.");
        }
    }

    /**
     * Failure test for <code>validate(FileInputStream)</code>, with null stream, IAE expected.
     *
     * @throws Exception to JUnit
     */
    public void testValidateString_null_stream() throws Exception {
        try {
            validation.validate((FileInputStream) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>validate(FileInputStream)</code>, with legal stream, should be ok.
     *
     * @throws Exception to JUnit
     */
    public void testValidateString_legal_stream() throws Exception {
        FileInputStream xmi = new FileInputStream(TestHelper.class.getClassLoader().getResource("failure/legal.xmi").getFile());
        
        try {
            validation.validate(xmi);
            // expected
        } catch (Exception e) {
            fail("should be ok.");
        }
    }

    /**
     * Failure test for <code>validateRaw(Submission)</code>, with null submission, IAE expected.
     *
     * @throws Exception to JUnit
     */
    public void testValidateRawSubmission_null_submission()
        throws Exception {
        try {
            validation.validateRaw((Submission) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>validateRaw(String)</code>, with null xmiString, IAE expected.
     *
     * @throws Exception to JUnit
     */
    public void testValidateRawString_null_xmiString()
        throws Exception {
        try {
            validation.validateRaw((String) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>validateRaw(String)</code>, with legal xmiString, should be ok.
     *
     * @throws Exception to JUnit
     */
    public void testValidateRawString_legal_xmiString()
        throws Exception {
        String xmi = getXmiFromFile("failure/legal.xmi");
        
        try {
            validation.validateRaw(xmi);
            // expected
        } catch (Exception e) {
            fail("should be ok.");
        }
    }

    /**
     * Failure test for <code>validateRaw(File)</code>, with null file, IAE expected.
     *
     * @throws Exception to JUnit
     */
    public void testValidateRawString_null_file() throws Exception {
        try {
            validation.validateRaw((File) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>validateRaw(File)</code>, with legal file, should be ok.
     *
     * @throws Exception to JUnit
     */
    public void testValidateRawString_legal_file() throws Exception {
        File xmi = new File(TestHelper.class.getClassLoader().getResource("failure/legal.xmi").getFile());
        
        try {
            validation.validateRaw(xmi);
            // expected
        } catch (Exception e) {
            fail("should be ok.");
        }
    }

    /**
     * Failure test for <code>validateRaw(FileInputStream)</code>, with null stream, IAE expected.
     *
     * @throws Exception to JUnit
     */
    public void testValidateRawString_null_stream() throws Exception {
        try {
            validation.validateRaw((FileInputStream) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>validateRaw(FileInputStream)</code>, with legal stream, should be ok.
     *
     * @throws Exception to JUnit
     */
    public void testValidateRawString_legal_stream() throws Exception {
        FileInputStream xmi = new FileInputStream(TestHelper.class.getClassLoader().getResource("failure/legal.xmi").getFile());
        
        try {
            validation.validateRaw(xmi);
            // expected
        } catch (Exception e) {
            fail("should be ok.");
        }
    }

    /**
     * <p>
     * Read xmi file and return its content.
     * </p>
     * 
     * @param filename the name of file to read
     * @return read contents
     * @throws Exception if fails to read file
     */
    private static String getXmiFromFile(String filename) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        TestHelper.class.getClassLoader().getResourceAsStream(filename),
                        "UTF-8"));
        
        try {
            StringBuffer buf = new StringBuffer();
            
            String next = null;
            while ((next = reader.readLine()) != null) {
                buf.append(next);
            }
            
            return buf.toString();
        } finally {
            reader.close();
        }
    }
}
