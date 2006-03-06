package com.topcoder.apps.screening.applications.specification.accuracytests;

import java.io.File;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.apps.screening.applications.specification.SubmissionValidator;
import com.topcoder.apps.screening.applications.specification.impl.DefaultSubmissionValidatorFactory;
import com.topcoder.apps.screening.applications.specification.impl.formatters.XMLValidationOutputFormatter;
import com.topcoder.apps.screening.applications.specification.impl.validators.ActivityDiagramPathReportGenerator;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultActivityDiagramNamingValidator;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultActivityDiagramValidator;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultUseCaseDiagramNamingValidator;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultUseCaseDiagramValidator;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

public class DefaultSubmissionValidatorFactoryTest extends TestCase {

    private DefaultSubmissionValidatorFactory defaultSubmissionValidatorFactory;

    /**
     * Creates a test suite for the tests in this test case.
     * 
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        return new TestSuite(DefaultSubmissionValidatorFactoryTest.class);
    }

    /**
     * Sets up for each test case.
     * 
     * @throws Exception
     *             wrap all exceptions
     */
    protected void setUp() throws Exception {
        // adding workable configuration
        ConfigManager.getInstance().add(
                new File("test_files/accuracytests/Configurations.xml")
                        .getAbsolutePath());
        defaultSubmissionValidatorFactory = new DefaultSubmissionValidatorFactory();
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.DefaultSubmissionValidatorFactory.createValidators()'
     */
    public final void testCreateValidatorsNumbers() throws Exception {
        assertEquals("number of validators must be 5", 5,
                defaultSubmissionValidatorFactory.createValidators().length);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.DefaultSubmissionValidatorFactory.createValidators()'
     */
    public final void testCreateValidatorsInstanceActivityDiagramPathReportGeneratorExist() throws Exception {
        boolean exist = false;
        SubmissionValidator[] validators = defaultSubmissionValidatorFactory
                .createValidators();
        for (int i = 0; i < validators.length; i++) {
            if (validators[i] instanceof ActivityDiagramPathReportGenerator) {
                exist = true;
                break;
            }
        }
        assertTrue("ActivityDiagramPathReportGenerator instance must exist",
                exist);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.DefaultSubmissionValidatorFactory.createValidators()'
     */
    public final void testCreateValidatorsInstanceDefaultActivityDiagramNamingValidatorExist() throws Exception {
        boolean exist = false;
        SubmissionValidator[] validators = defaultSubmissionValidatorFactory
                .createValidators();
        for (int i = 0; i < validators.length; i++) {
            if (validators[i] instanceof DefaultActivityDiagramNamingValidator) {
                exist = true;
                break;
            }
        }
        assertTrue("DefaultActivityDiagramNamingValidator instance must exist",
                exist);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.DefaultSubmissionValidatorFactory.createValidators()'
     */
    public final void testCreateValidatorsInstanceDefaultActivityDiagramValidatorExist() throws Exception {
        boolean exist = false;
        SubmissionValidator[] validators = defaultSubmissionValidatorFactory
                .createValidators();
        for (int i = 0; i < validators.length; i++) {
            if (validators[i] instanceof DefaultActivityDiagramValidator) {
                exist = true;
                break;
            }
        }
        assertTrue("DefaultActivityDiagramValidator instance must exist", exist);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.DefaultSubmissionValidatorFactory.createValidators()'
     */
    public final void testCreateValidatorsInstanceDefaultUseCaseDiagramNamingValidatorExist() throws Exception {
        boolean exist = false;
        SubmissionValidator[] validators = defaultSubmissionValidatorFactory
                .createValidators();
        for (int i = 0; i < validators.length; i++) {
            if (validators[i] instanceof DefaultUseCaseDiagramNamingValidator) {
                exist = true;
                break;
            }
        }
        assertTrue("DefaultUseCaseDiagramNamingValidator instance must exist",
                exist);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.DefaultSubmissionValidatorFactory.createValidators()'
     */
    public final void testCreateValidatorsInstanceDefaultUseCaseDiagramValidatorExist() throws Exception {
        boolean exist = false;
        SubmissionValidator[] validators = defaultSubmissionValidatorFactory
                .createValidators();
        for (int i = 0; i < validators.length; i++) {
            if (validators[i] instanceof DefaultUseCaseDiagramValidator) {
                exist = true;
                break;
            }
        }
        assertTrue("DefaultUseCaseDiagramValidator instance must exist", exist);
    }

    /*
     * Test method for
     * 'com.topcoder.apps.screening.application.specification.impl.DefaultSubmissionValidatorFactory.createFormatter()'
     */
    public final void testCreateFormatter() throws Exception {
        assertTrue(
                "XMLValidationOutputFormatter instance must exist",
                defaultSubmissionValidatorFactory.createFormatter() instanceof XMLValidationOutputFormatter);
    }

    /**
     * Tear down for each test case.
     * 
     * @throws Exception
     *             wrap all exceptions
     */
    protected void tearDown() throws Exception {
        ConfigManager mgr = ConfigManager.getInstance();
        Iterator itr = mgr.getAllNamespaces();
        while (itr.hasNext()) {
            String ns = (String) itr.next();

            try {
                mgr.removeNamespace(ns);
            } catch (UnknownNamespaceException e) {
                e.printStackTrace();
            }
        }
    }
}