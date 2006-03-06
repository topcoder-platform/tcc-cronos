/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.apps.screening.applications.specification.failuretests;

import junit.framework.TestCase;

import com.topcoder.apps.screening.applications.specification.ConfigurationException;
import com.topcoder.apps.screening.applications.specification.impl.DefaultSubmissionValidatorFactory;


/**
 * <p>
 * Failure tests for <code>ValidationOutput</code>.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class DefaultSubmissionValidatorFactoryFailureTests extends TestCase {
    /**
     * Config the setup environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();
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
     * Failure test for <code>DefaultSubmissionValidatorFactory()</code>.
     */
    public void testDefaultSubmissionValidatorFactory() {
        try {
            new DefaultSubmissionValidatorFactory();

            // expected
        } catch (Exception e) {
            fail("Exception should not be thrown.");
        }
    }

    /**
     * Failure test for <code>DefaultSubmissionValidatorFactory(String)</code>.
     */
    public void testDefaultSubmissionValidatorFactoryString() {
        try {
            new DefaultSubmissionValidatorFactory(TestHelper.SUBMISSION_VALIDATOR_FACTORY_NAMESPACE);

            // expected
        } catch (Exception e) {
            fail("Exception should not be thrown.");
        }
    }

    /**
     * Failure test for <code>DefaultSubmissionValidatorFactory(String)</code>, with null namespace, IAE expected.
     */
    public void testDefaultSubmissionValidatorFactoryString_null_namespace() {
        try {
            new DefaultSubmissionValidatorFactory(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>DefaultSubmissionValidatorFactory(String)</code>, with empty namespace, IAE expected.
     */
    public void testDefaultSubmissionValidatorFactoryString_empty_namespace() {
        try {
            new DefaultSubmissionValidatorFactory("     ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>createValidators()</code>.
     */
    public void testCreateValidators() {
        DefaultSubmissionValidatorFactory factory = new DefaultSubmissionValidatorFactory();

        try {
            factory.createValidators();

            // expected
        } catch (ConfigurationException e) {
            fail("ConfigurationException should not be thrown.");
        }
    }

    /**
     * Failure test for <code>createValidators()</code>, with illegal namespace, CE expected.
     */
    public void testCreateValidators_illegal_namespace() {
        DefaultSubmissionValidatorFactory factory = new DefaultSubmissionValidatorFactory(
                "com.topcoder.apps.screening.application.specification.impl.IllegalSubmissionValidatorFactory");

        try {
            factory.createValidators();
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>createValidators()</code>, with validators property missing, CE expected.
     */
    public void testCreateValidators_missing_validators() {
        DefaultSubmissionValidatorFactory factory = new DefaultSubmissionValidatorFactory(
                "com.topcoder.apps.screening.application.specification.impl.DefaultSubmissionValidatorFactory_missing_validators");

        try {
            factory.createValidators();
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>createValidators()</code>, with null validators property, CE expected.
     */
    public void testCreateValidators_null_validators() {
        DefaultSubmissionValidatorFactory factory = new DefaultSubmissionValidatorFactory(
                "com.topcoder.apps.screening.application.specification.impl.DefaultSubmissionValidatorFactory_null_validators");

        try {
            factory.createValidators();
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>createValidators()</code>, with one of the validator_class properties missing, CE
     * expected.
     */
    public void testCreateValidators_missing_validator_class() {
        DefaultSubmissionValidatorFactory factory = new DefaultSubmissionValidatorFactory(
                "com.topcoder.apps.screening.application.specification.impl.DefaultSubmissionValidatorFactory_misssing_validator_class");

        try {
            factory.createValidators();
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>createValidators()</code>, with null validator_class properties, CE expected.
     */
    public void testCreateValidators_null_validator_class() {
        DefaultSubmissionValidatorFactory factory = new DefaultSubmissionValidatorFactory(
                "com.topcoder.apps.screening.application.specification.impl.DefaultSubmissionValidatorFactory_null_validator_class");

        try {
            factory.createValidators();
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>createFormatter()</code>.
     */
    public void testCreateFormatter() {
        DefaultSubmissionValidatorFactory factory = new DefaultSubmissionValidatorFactory();

        try {
            factory.createFormatter();

            // expected
        } catch (ConfigurationException e) {
            fail("ConfigurationException should not be thrown.");
        }
    }

    /**
     * Failure test for <code>createFormatter()</code>, with illegal namespace, CE expected.
     */
    public void testCreateFormatter_illegal_namespace() {
        DefaultSubmissionValidatorFactory factory = new DefaultSubmissionValidatorFactory(
                "com.topcoder.apps.screening.application.specification.impl.IllegalSubmissionValidatorFactory");

        try {
            factory.createFormatter();
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>createFormatter()</code>, with formatter_class property missing, CE expected.
     */
    public void testCreateFormatter_missing_formatter_class() {
        DefaultSubmissionValidatorFactory factory = new DefaultSubmissionValidatorFactory(
                "com.topcoder.apps.screening.application.specification.impl.DefaultSubmissionValidatorFactory_missing_formatter_class");

        try {
            factory.createFormatter();
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>createFormatter()</code>, with null formatter_class property, CE expected.
     */
    public void testCreateFormatter_null_formatter_class() {
        DefaultSubmissionValidatorFactory factory = new DefaultSubmissionValidatorFactory(
                "com.topcoder.apps.screening.application.specification.impl.DefaultSubmissionValidatorFactory_null_formatter_class");

        try {
            factory.createFormatter();
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // expected
        }
    }
}
