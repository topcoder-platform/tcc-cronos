/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.failuretests;

import com.cronos.onlinereview.phases.AbstractPhaseHandler;
import com.cronos.onlinereview.phases.ConfigurationException;
import com.topcoder.project.phases.Phase;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>A failure test for {@link AbstractPhaseHandler} class. Tests the proper handling of invalid input data by the
 * methods. Passes the invalid arguments to the methods and expects the appropriate exception to be thrown.</p>
 *
 * @author isv
 * @version 1.0
 */
public class AbstractPhaseHandlerFailureTest extends AbstractTestCase {

    /**
     * <p>The instances of {@link AbstractPhaseHandler} which are tested. These instances are initialized in {@link
     * #setUp()} method and released in {@link #tearDown()} method. Each instance is initialized using a separate
     * constructor provided by the tested class.<p>
     */
    private AbstractPhaseHandlerSubclass[] testedInstances = null;

    /**
     * <p>Gets the test suite for {@link AbstractPhaseHandler} class.</p>
     *
     * @return a <code>TestSuite</code> providing the tests for {@link AbstractPhaseHandler} class.
     */
    public static Test suite() {
        return new TestSuite(AbstractPhaseHandlerFailureTest.class);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.testedInstances = new AbstractPhaseHandlerSubclass[1];
        this.testedInstances[0] = new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
    }

    /**
     * <p>Tears down the fixture. This method is called after a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        this.testedInstances = null;
        super.tearDown();
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler(String)} constructor for proper
     * handling the invalid input arguments.</p>
     *
     * <p>Passes {@link null} as <code>namespace</code> and expects the <code>IllegalArgumentException</code> to be
     * thrown.</p>
     */
    public void testConstructor_String_namespace_null() {
        try {
            new AbstractPhaseHandlerSubclass(null);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler(String)} constructor for proper
     * handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>namespace</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testConstructor_String_namespace_ZERO_LENGTH_STRING() {
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler(String)} constructor for proper
     * handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>namespace</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testConstructor_String_namespace_WHITESPACE_ONLY_STRING() {
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#sendEmail(Phase)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link null} as <code>phase</code> and expects the <code>IllegalArgumentException</code> to be
     * thrown.</p>
     */
    public void testSendEmail_Phase_phase_null() {
        for (int i = 0; i < this.testedInstances.length; i++) {
            try {
                this.testedInstances[i].sendEmail(null);
                Assert.fail("IllegalArgumentException should have been thrown");
            } catch (IllegalArgumentException e) {
                // expected behavior
            } catch (Exception e) {
                Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
            }
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Removes the <code>ConnectionFactoryNS</code> configuration property from the configuration namespace and
     * expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_1() {
        String[] values = ConfigHelper.removeProperty("com.cronos.onlinereview.phases.failuretests",
                                                      "ConnectionFactoryNS");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests", "ConnectionFactoryNS", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Sets the <code>ConnectionFactoryNS</code> configuration property from the configuration namespace to empty
     * value and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_2() {
        String[] values = ConfigHelper.setProperty("com.cronos.onlinereview.phases.failuretests", "ConnectionFactoryNS",
                                                   "");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests", "ConnectionFactoryNS", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Sets the <code>ConnectionFactoryNS</code> configuration property from the configuration namespace to invalid
     * value and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_3() {
        String[] values = ConfigHelper.setProperty("com.cronos.onlinereview.phases.failuretests", "ConnectionFactoryNS",
                                                   "3cwefqf141gg1g41");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests", "ConnectionFactoryNS", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Sets the <code>ManagerHelperNamespace</code> configuration property from the configuration namespace to
     * invalid value and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_4() {
        String[] values = ConfigHelper.setProperty("com.cronos.onlinereview.phases.failuretests",
                                                   "ManagerHelperNamespace", "3cwefqf141gg1g41");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests", "ManagerHelperNamespace",
                                         values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Removes the <code>StartPhaseEmail.EmailTemplateSource</code> configuration property from the configuration
     * namespace and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_5() {
        String[] values = ConfigHelper.removeProperty("com.cronos.onlinereview.phases.failuretests",
                                                      "StartPhaseEmail.EmailTemplateSource");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "StartPhaseEmail.EmailTemplateSource", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Sets the <code>StartPhaseEmail.EmailTemplateSource</code> configuration property from the configuration
     * namespace to empty value and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_6() {
        String[] values = ConfigHelper.setProperty("com.cronos.onlinereview.phases.failuretests",
                                                   "StartPhaseEmail.EmailTemplateSource", "");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "StartPhaseEmail.EmailTemplateSource", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Removes the <code>StartPhaseEmail.EmailTemplateName</code> configuration property from the configuration
     * namespace and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_7() {
        String[] values = ConfigHelper.removeProperty("com.cronos.onlinereview.phases.failuretests",
                                                      "StartPhaseEmail.EmailTemplateName");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "StartPhaseEmail.EmailTemplateName", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Sets the <code>StartPhaseEmail.EmailTemplateName</code> configuration property from the configuration
     * namespace to empty value and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_8() {
        String[] values = ConfigHelper.setProperty("com.cronos.onlinereview.phases.failuretests",
                                                   "StartPhaseEmail.EmailTemplateName", "");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "StartPhaseEmail.EmailTemplateName", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Removes the <code>StartPhaseEmail.EmailSubject</code> configuration property from the configuration namespace
     * and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_9() {
        String[] values = ConfigHelper.removeProperty("com.cronos.onlinereview.phases.failuretests",
                                                      "StartPhaseEmail.EmailSubject");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "StartPhaseEmail.EmailSubject", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Sets the <code>StartPhaseEmail.EmailSubject</code> configuration property from the configuration namespace to
     * empty value and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_10() {
        String[] values = ConfigHelper.setProperty("com.cronos.onlinereview.phases.failuretests",
                                                   "StartPhaseEmail.EmailSubject", "");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "StartPhaseEmail.EmailSubject", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Removes the <code>StartPhaseEmail.EmailFromAddress</code> configuration property from the configuration
     * namespace and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_11() {
        String[] values = ConfigHelper.removeProperty("com.cronos.onlinereview.phases.failuretests",
                                                      "StartPhaseEmail.EmailFromAddress");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "StartPhaseEmail.EmailFromAddress", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Sets the <code>StartPhaseEmail.EmailFromAddress</code> configuration property from the configuration namespace
     * to empty value and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_12() {
        String[] values = ConfigHelper.setProperty("com.cronos.onlinereview.phases.failuretests",
                                                   "StartPhaseEmail.EmailFromAddress", "");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "StartPhaseEmail.EmailFromAddress", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Removes the <code>EndPhaseEmail.EmailTemplateSource</code> configuration property from the configuration
     * namespace and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_13() {
        String[] values = ConfigHelper.removeProperty("com.cronos.onlinereview.phases.failuretests",
                                                      "EndPhaseEmail.EmailTemplateSource");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "EndPhaseEmail.EmailTemplateSource", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Sets the <code>EndPhaseEmail.EmailTemplateSource</code> configuration property from the configuration
     * namespace to empty value and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_14() {
        String[] values = ConfigHelper.setProperty("com.cronos.onlinereview.phases.failuretests",
                                                   "EndPhaseEmail.EmailTemplateSource", "");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "EndPhaseEmail.EmailTemplateSource", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Removes the <code>EndPhaseEmail.EmailTemplateName</code> configuration property from the configuration
     * namespace and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_15() {
        String[] values = ConfigHelper.removeProperty("com.cronos.onlinereview.phases.failuretests",
                                                      "EndPhaseEmail.EmailTemplateName");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "EndPhaseEmail.EmailTemplateName", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Sets the <code>EndPhaseEmail.EmailTemplateName</code> configuration property from the configuration namespace
     * to empty value and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_16() {
        String[] values = ConfigHelper.setProperty("com.cronos.onlinereview.phases.failuretests",
                                                   "EndPhaseEmail.EmailTemplateName", "");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "EndPhaseEmail.EmailTemplateName", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Removes the <code>EndPhaseEmail.EmailSubject</code> configuration property from the configuration namespace
     * and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_17() {
        String[] values = ConfigHelper.removeProperty("com.cronos.onlinereview.phases.failuretests",
                                                      "EndPhaseEmail.EmailSubject");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "EndPhaseEmail.EmailSubject", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Sets the <code>EndPhaseEmail.EmailSubject</code> configuration property from the configuration namespace to
     * empty value and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_18() {
        String[] values = ConfigHelper.setProperty("com.cronos.onlinereview.phases.failuretests",
                                                   "EndPhaseEmail.EmailSubject", "");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "EndPhaseEmail.EmailSubject", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Removes the <code>EndPhaseEmail.EmailFromAddress</code> configuration property from the configuration
     * namespace and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_19() {
        String[] values = ConfigHelper.removeProperty("com.cronos.onlinereview.phases.failuretests",
                                                      "EndPhaseEmail.EmailFromAddress");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "EndPhaseEmail.EmailFromAddress", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link AbstractPhaseHandler#AbstractPhaseHandler,String} for proper behavior if the
     * configuration is invalid.</p>
     *
     * <p>Sets the <code>EndPhaseEmail.EmailFromAddress</code> configuration property from the configuration namespace
     * to empty value and expects the <code>ConfigurationException</code> to be thrown.</p>
     */
    public void testAbstractPhaseHandler_String_20() {
        String[] values = ConfigHelper.setProperty("com.cronos.onlinereview.phases.failuretests",
                                                   "EndPhaseEmail.EmailFromAddress", "");
        try {
            new AbstractPhaseHandlerSubclass(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.cronos.onlinereview.phases.failuretests",
                                         "EndPhaseEmail.EmailFromAddress", values);
        }
    }
}
