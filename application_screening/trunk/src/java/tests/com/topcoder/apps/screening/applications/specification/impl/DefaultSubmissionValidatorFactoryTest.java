/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultSubmissionValidatorFactoryTest.java
 */
package com.topcoder.apps.screening.applications.specification.impl;

import com.topcoder.apps.screening.applications.specification.ConfigurationException;
import com.topcoder.apps.screening.applications.specification.Helper;
import com.topcoder.apps.screening.applications.specification.SubmissionValidator;
import com.topcoder.apps.screening.applications.specification.ValidationOutputFormatter;
import com.topcoder.apps.screening.applications.specification.impl.formatters.XMLValidationOutputFormatter;
import com.topcoder.apps.screening.applications.specification.impl.formatters.XMLValidationOutputFormatterWithNS;
import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>DefaultSubmissionValidatorFactory</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultSubmissionValidatorFactoryTest extends TestCase {

    /**Prefix for namespace with invalid configuration.*/
    private static final String INVALID_NS = "com.topcoder.apps.screening.applications.specification.impl.invalid";

    /**Prefix for namespace with valid configurations.*/
    private static final String VALID_NS = "com.topcoder.apps.screening.applications.specification.impl.valid";

    /**DefaultSubmissionValidatorFactory instance that will be tested.*/
    private DefaultSubmissionValidatorFactory factory;

    /**
     * <p>
     * Set up environment.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void setUp() throws Exception {
        Helper.clearNamespace();
        Helper.initNamespace();

        factory = new DefaultSubmissionValidatorFactory();
    }

    /**
     * <p>
     * Tear down environment.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void tearDown() throws Exception {
        Helper.clearNamespace();
    }

    /**
     * <p>
     * Tests constructor with no args.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCtor1() throws Exception {
        assertNotNull("Must not be null.", factory);
    }

    /**
     * <p>
     * Tests second constructor if namespace is null.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCtor2IfNamespaceNull() throws Exception {
        try {
            new DefaultSubmissionValidatorFactory(null);
            fail("Namespace cannot be null.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests second constructor if namespace is empty.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCtor2IfNamespaceEmpty() throws Exception {
        try {
            new DefaultSubmissionValidatorFactory("  ");
            fail("Namespace cannot be empty.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests constructor with namespace argument.
     * Note, that in constructor it is not checked whether namespace exist or not,
     * so we can pass to it any string.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCtor2() throws Exception {
        factory = new DefaultSubmissionValidatorFactory("namespace");
        assertNotNull("Must not be null.", factory);
    }

    /**
     * <p>
     * Tests method createValidators() if namespace, set in constructor, is unknown.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCreateValidatorsIfNamespaceUnknown() throws Exception {
        factory = new DefaultSubmissionValidatorFactory("unknown.namespace");
        try {
            factory.createValidators();
            fail("Namespace must not be unknown.");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method createValidators() if there is no validators' names in configuration.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCreateValidatorsIfNoValidatorNames() throws Exception {
        factory = new DefaultSubmissionValidatorFactory(INVALID_NS + 1);
        try {
            factory.createValidators();
            fail("Validator names must be specified in config file.");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method createValidators() if there some validators don't have class name.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCreateValidatorsIfNoClassName() throws Exception {
        factory = new DefaultSubmissionValidatorFactory(INVALID_NS + 2);
        try {
            factory.createValidators();
            fail("Each validator must have its class name.");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method createValidators() if there some validator's class name is invalid.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCreateValidatorsIfClassNameInvalid() throws Exception {
        factory = new DefaultSubmissionValidatorFactory(INVALID_NS + 3);
        try {
            factory.createValidators();
            fail("Each validator must have valid class name.");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method createValidators() if there for some validator namespace is specified, but
     * there is no constructors that accepts string.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCreateValidatorsIfNamespceNotNullAndClassNaveNoValidCtor() throws Exception {
        factory = new DefaultSubmissionValidatorFactory(INVALID_NS + 4);
        try {
            factory.createValidators();
            fail("If namespace specified, than there must be constructor that accepts String.");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method createValidators().
     * In this test 5 validators will be loaded. All of them were created without using namespaces.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCreateValidatorsWithoutNamespaces() throws Exception {
        factory = new DefaultSubmissionValidatorFactory(VALID_NS + 1);
        SubmissionValidator []validators = factory.createValidators();
        //there must be 5 validators
        assertEquals("There must be 5 validators.", 5, validators.length);
    }

    /**
     * <p>
     * Tests method createValidators().
     * In this test 5 validators will be loaded.
     * One of the validator has specified namespace, and is loaded normally.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCreateValidatorsWithNamespace() throws Exception {
        factory = new DefaultSubmissionValidatorFactory(VALID_NS + 2);
        SubmissionValidator []validators = factory.createValidators();
        //there must be 5 validators
        assertEquals("There must be 5 validators.", 5, validators.length);
    }

    /**
     * <p>
     * Tests method createFormatter() if there is no class name.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCreateFormatterIfNoClassName() throws Exception {
        factory = new DefaultSubmissionValidatorFactory(INVALID_NS + 5);
        try {
            factory.createFormatter();
            fail("Formatter class name must be specified.");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method createFormatter() if class name is invalid.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCreateFormatterIfClassNameInvalid() throws Exception {
        factory = new DefaultSubmissionValidatorFactory(INVALID_NS + 6);
        try {
            factory.createFormatter();
            fail("Formatter must have valid class name.");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method createValidators() if formatter's namespace is specified, but
     * there is no constructors that accepts string.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCreateFormatterIfNamespcaeNotNullAndClassNaveNoValidCtor() throws Exception {
        factory = new DefaultSubmissionValidatorFactory(INVALID_NS + 7);
        try {
            factory.createFormatter();
            fail("If namespace specified, than there must be constructor that accepts String.");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * <p>
     * Tests method createFormatter().
     * It will be created without using namespaces.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCreateFormatterWithoutNamespaces() throws Exception {
        factory = new DefaultSubmissionValidatorFactory(VALID_NS + 1);
        ValidationOutputFormatter formatter = factory.createFormatter();
        assertNotNull("Formatter must not be null.", formatter);
        assertTrue("Formatter must be instance of XMLValidationOutputFormatter.",
                formatter instanceof XMLValidationOutputFormatter);
    }

    /**
     * <p>
     * Tests method createFormatter().
     * Formatter will be created using namespace.
     * </p>
     *
     * @throws Exception excpetion
     */
    public void testCreateFormatterWithNamespace() throws Exception {
        factory = new DefaultSubmissionValidatorFactory(VALID_NS + 3);
        ValidationOutputFormatter formatter = factory.createFormatter();
        assertNotNull("Formatter must not be null.", formatter);
        assertTrue("Formatter must be instance of XMLValidationOutputFormatter.",
                formatter instanceof XMLValidationOutputFormatterWithNS);
    }
}