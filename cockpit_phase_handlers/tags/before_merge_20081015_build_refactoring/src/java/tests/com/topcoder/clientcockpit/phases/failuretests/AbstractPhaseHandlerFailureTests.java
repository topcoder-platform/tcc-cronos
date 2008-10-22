/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.failuretests;

import com.topcoder.clientcockpit.phases.AbstractPhaseHandler;
import com.topcoder.clientcockpit.phases.PhaseHandlerConfigurationException;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.service.studio.contest.ContestManager;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for AbstractPhaseHandler.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class AbstractPhaseHandlerFailureTests extends TestCase {

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        System.setProperty("java.naming.factory.initial",
            "com.topcoder.clientcockpit.phases.failuretests.MockInitialContextFactory");
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        System.clearProperty("java.naming.factory.initial");
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AbstractPhaseHandlerFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler(String) for failure.
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1_NullNamespace() throws Exception {
        try {
            new MockAbstractPhaseHandler((String) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler(String) for failure.
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1_EmptyNamespace() throws Exception {
        try {
            new MockAbstractPhaseHandler(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler(ContestManager) for failure.
     * It tests the case that when bean is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_NullBean() throws Exception {
        try {
            new MockAbstractPhaseHandler((ContestManager) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor3_NullNamespace() throws Exception {
        try {
            new MockAbstractPhaseHandler(null, new MockContestManager());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor3_EmptyNamespace() throws Exception {
        try {
            new MockAbstractPhaseHandler(" ", new MockContestManager());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when bean is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor3_NullBean() throws Exception {
        try {
            new MockAbstractPhaseHandler(AbstractPhaseHandler.class.getPackage().getName(), null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "bean_name" property is missing, <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_Missing_bean_name() throws Exception {
        TestHelper.removeProperty("com.topcoder.clientcockpit.phases", "bean_name");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "bean_name", "beanName");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "bean_name" property has empty value, <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_bean_name() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "bean_name", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "bean_name", "beanName");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "bean_name" property causes JNDI throw error while looking up,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_Invalid_bean_name() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "bean_name", "ExceptionBean");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlingException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "bean_name", "beanName");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "use_cache" property has empty value, <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_use_cache() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "use_cache", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "use_cache", "true");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "use_cache" property has invalid value, <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_InvalidValue_use_cache() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "use_cache", "turn");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "use_cache", "true");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "send_start_phase_email" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_send_start_phase_email() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "send_start_phase_email", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "send_start_phase_email", "true");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "start_email_template_source" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_start_email_template_source() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "start_email_template_source", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "start_email_template_source",
                "startTemplateSource");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "start_email_template_name" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_Missing_start_email_template_name() throws Exception {
        TestHelper.removeProperty("com.topcoder.clientcockpit.phases", "start_email_template_name");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "start_email_template_name",
                "startTemplateName");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "start_email_template_name" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_start_email_template_name() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "start_email_template_name", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "start_email_template_name",
                "startTemplateName");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "start_email_subject" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_Missing_start_email_subject() throws Exception {
        TestHelper.removeProperty("com.topcoder.clientcockpit.phases", "start_email_subject");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "start_email_subject", "startEmailSubject");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "start_email_subject" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_start_email_subject() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "start_email_subject", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "start_email_subject", "startEmailSubject");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "start_email_from_address" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_Missing_start_email_from_address() throws Exception {
        TestHelper.removeProperty("com.topcoder.clientcockpit.phases", "start_email_from_address");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "start_email_from_address", "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "start_email_from_address" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_start_email_from_address() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "start_email_from_address", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "start_email_from_address", "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "send_end_phase_email" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_send_end_phase_email() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "send_end_phase_email", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "send_end_phase_email", "true");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "end_email_template_source" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_end_email_template_source() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "end_email_template_source", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "end_email_template_source",
                "endTemplateSource");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "end_email_template_name" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_Missing_end_email_template_name() throws Exception {
        TestHelper.removeProperty("com.topcoder.clientcockpit.phases", "end_email_template_name");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "end_email_template_name", "endTemplateName");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "end_email_template_name" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_end_email_template_name() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "end_email_template_name", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "end_email_template_name", "endTemplateName");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "end_email_subject" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_Missing_end_email_subject() throws Exception {
        TestHelper.removeProperty("com.topcoder.clientcockpit.phases", "end_email_subject");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "end_email_subject", "endEmailSubject");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "end_email_subject" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_end_email_subject() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "end_email_subject", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "end_email_subject", "endEmailSubject");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "end_email_from_address" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_Missing_end_email_from_address() throws Exception {
        TestHelper.removeProperty("com.topcoder.clientcockpit.phases", "end_email_from_address");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "end_email_from_address", "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "end_email_from_address" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_end_email_from_address() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "end_email_from_address", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "end_email_from_address", "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "send_one_hour_phase_email" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_send_one_hour_phase_email() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "send_one_hour_phase_email", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "send_one_hour_phase_email", "true");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "one_hour_email_template_source" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_one_hour_email_template_source() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "one_hour_email_template_source", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "one_hour_email_template_source",
                "oneHourTemplateSource");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "one_hour_email_template_name" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_Missing_one_hour_email_template_name() throws Exception {
        TestHelper.removeProperty("com.topcoder.clientcockpit.phases", "one_hour_email_template_name");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "one_hour_email_template_name",
                "oneHourTemplateName");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "one_hour_email_template_name" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_one_hour_email_template_name() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "one_hour_email_template_name", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "one_hour_email_template_name",
                "oneHourTemplateName");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "one_hour_email_subject" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_Missing_one_hour_email_subject() throws Exception {
        TestHelper.removeProperty("com.topcoder.clientcockpit.phases", "one_hour_email_subject");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "one_hour_email_subject", "oneHourEmailSubject");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "one_hour_email_subject" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_one_hour_email_subject() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "one_hour_email_subject", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "one_hour_email_subject", "oneHourEmailSubject");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "one_hour_email_from_address" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_Missing_one_hour_email_from_address() throws Exception {
        TestHelper.removeProperty("com.topcoder.clientcockpit.phases", "one_hour_email_from_address");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "one_hour_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "one_hour_email_from_address" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_one_hour_email_from_address() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "one_hour_email_from_address", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "one_hour_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "send_eight_hours_phase_email" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_send_eight_hours_phase_email() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "send_eight_hours_phase_email", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "send_eight_hours_phase_email", "true");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "eight_hours_email_template_source" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_eight_hours_email_template_source() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "eight_hours_email_template_source", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "eight_hours_email_template_source",
                "eightHoursTemplateSource");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "eight_hours_email_template_name" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_Missing_eight_hours_email_template_name() throws Exception {
        TestHelper.removeProperty("com.topcoder.clientcockpit.phases", "eight_hours_email_template_name");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "eight_hours_email_template_name",
                "eightHoursTemplateName");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "eight_hours_email_template_name" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_eight_hours_email_template_name() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "eight_hours_email_template_name", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "eight_hours_email_template_name",
                "eightHoursTemplateName");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "eight_hours_email_subject" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_Missing_eight_hours_email_subject() throws Exception {
        TestHelper.removeProperty("com.topcoder.clientcockpit.phases", "eight_hours_email_subject");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "eight_hours_email_subject",
                "eightHoursEmailSubject");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "eight_hours_email_subject" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_eight_hours_email_subject() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "eight_hours_email_subject", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "eight_hours_email_subject",
                "eightHoursEmailSubject");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "eight_hours_email_from_address" property is missing,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_Missing_eight_hours_email_from_address() throws Exception {
        TestHelper.removeProperty("com.topcoder.clientcockpit.phases", "eight_hours_email_from_address");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "eight_hours_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "eight_hours_email_from_address" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_eight_hours_email_from_address() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "eight_hours_email_from_address", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "eight_hours_email_from_address",
                "address@yahoo.com");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "message_generator_key" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_message_generator_key() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "message_generator_key", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "message_generator_key", "messageGeneratorKey");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "specification_factory_namespace" property has empty value,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_EmptyValue_specification_factory_namespace() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "specification_factory_namespace", " ");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "specification_factory_namespace",
                "com.topcoder.clientcockpit.objectfactory");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "specification_factory_namespace" property points to a object factory namespace which
     * defines a wrong type not instance of <code>EmailMessageGenerator</code>,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_invalid_specification_factory_namespace_1() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "specification_factory_namespace",
            "objectfactory_wrong_type");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "specification_factory_namespace",
                "com.topcoder.clientcockpit.objectfactory");
        }
    }

    /**
     * <p>
     * Tests ctor AbstractPhaseHandler#AbstractPhaseHandler() for failure.
     * </p>
     *
     * <p>
     * The "specification_factory_namespace" property points to a object factory namespace which has
     * invalid configuration,
     * <code>PhaseHandlerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor4_invalid_specification_factory_namespace_2() throws Exception {
        TestHelper.setProperty("com.topcoder.clientcockpit.phases", "specification_factory_namespace",
            "objectfactory_invalid_configuration");
        try {
            new MockAbstractPhaseHandler();
            fail("PhaseHandlerConfigurationException is expected");
        } catch (PhaseHandlerConfigurationException e) {
            //pass
        } finally {
            TestHelper.setProperty("com.topcoder.clientcockpit.phases", "specification_factory_namespace",
                "com.topcoder.clientcockpit.objectfactory");
        }
    }

}