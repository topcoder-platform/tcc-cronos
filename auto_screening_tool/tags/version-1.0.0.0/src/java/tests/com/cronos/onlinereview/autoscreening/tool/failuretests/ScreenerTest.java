/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.failuretests;

import junit.framework.TestCase;

import com.cronos.onlinereview.autoscreening.tool.ConfigurationException;
import com.cronos.onlinereview.autoscreening.tool.Screener;

/**
 * Failure tests for <code>Screener</code>.
 * @author assistant
 * @version 1.0
 */
public class ScreenerTest extends TestCase {

    /**
     * Represents the id of the screener.
     */
    private static final long ID = 1;

    /**
     * Represents the namespace of the screener.
     */
    private static final String NS = "com.cronos.onlinereview.autoscreening.tool.Screener";

    /**
     * Represents the Screener to test.
     */
    private Screener screener;

    /**
     * Set up the environment.
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig();
        screener = new Screener(ID, NS);
    }

    /**
     * Clean up the environment.
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the id is
     * negative. Expected : {@link IllegalArgumentException}.
     * @throws ConfigurationException
     *             to JUnit
     */
    public void testScreener_NegativeId() throws ConfigurationException {
        try {
            new Screener(-1, NS);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the id is
     * zero. Expected : {@link IllegalArgumentException}.
     * @throws ConfigurationException
     *             to JUnit
     */
    public void testScreener_ZeroId() throws ConfigurationException {
        try {
            new Screener(0, NS);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the
     * namespace is null. Expected : {@link IllegalArgumentException}.
     * @throws ConfigurationException
     *             to JUnit
     */
    public void testScreener_NullNS() throws ConfigurationException {
        try {
            new Screener(ID, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the
     * namespace is empty. Expected : {@link IllegalArgumentException}.
     * @throws ConfigurationException
     *             to JUnit
     */
    public void testScreener_EmptyNS() throws ConfigurationException {
        try {
            new Screener(ID, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the
     * namespace for object factory is missing. Expected :
     * {@link IllegalArgumentException}.
     * @throws Exception
     *             to JUnit
     */
    public void testScreener_NoObjectFactoryNamespace() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/no_of_ns.xml");
        try {
            new Screener(ID,
                "com.cronos.onlinereview.autoscreening.tool.failure.No.ObjectFactory.Namespace");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the
     * screeningTaskDAO is missing. Expected : {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoScreeningTaskDAO() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/task_logger_upload_chooser.xml");
        try {
            new Screener(ID,
                "com.cronos.onlinereview.autoscreening.tool.failure.No.ScreeningTaskDAO");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the
     * screeningTaskDAO class is missing. Expected :
     * {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoScreeningTaskDAOClass() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/task_logger_upload_chooser.xml");
        try {
            new Screener(ID,
                "com.cronos.onlinereview.autoscreening.tool.failure.No.ScreeningTaskDAO.class");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the
     * screeningResponseLogger is missing. Expected :
     * {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoResponseLogger() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/task_logger_upload_chooser.xml");
        try {
            new Screener(ID,
                "com.cronos.onlinereview.autoscreening.tool.failure.No.ScreeningResponseLogger");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the
     * screeningResponseLogger class is missing. Expected :
     * {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoScreeningResponseLoggerClass() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/task_logger_upload_chooser.xml");
        try {
            new Screener(ID,
                "com.cronos.onlinereview.autoscreening.tool.failure.No.ScreeningResponseLogger.class");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the
     * TaskChooser is missing. Expected : {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoTaskChooser() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/task_logger_upload_chooser.xml");
        try {
            new Screener(ID, "com.cronos.onlinereview.autoscreening.tool.failure.No.TaskChooser");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the
     * TaskChooser class is missing. Expected : {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoTaskChooserClass() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/task_logger_upload_chooser.xml");
        try {
            new Screener(ID,
                "com.cronos.onlinereview.autoscreening.tool.failure.No.TaskChooser.class");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the
     * FileUpload is missing. Expected : {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoFileUpload() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/task_logger_upload_chooser.xml");
        try {
            new Screener(ID, "com.cronos.onlinereview.autoscreening.tool.failure.No.FileUpload");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the
     * FileUpload class is missing. Expected : {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoFileUploadClass() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/task_logger_upload_chooser.xml");
        try {
            new Screener(ID,
                "com.cronos.onlinereview.autoscreening.tool.failure.No.FileUpload.class");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the rule
     * id is missing. Expected : {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoRuleId() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/project_rules.xml");
        try {
            new Screener(ID, "com.cronos.onlinereview.autoscreening.tool.failure.No.RuleId");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the rule
     * screening logic is missing. Expected : {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoRuleScreeningLogic() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/project_rules.xml");
        try {
            new Screener(ID,
                "com.cronos.onlinereview.autoscreening.tool.failure.No.RuleScreeningLogic");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the rule
     * screening logic is missing. Expected : {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoRuleScreeningLogicName() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/project_rules.xml");
        try {
            new Screener(ID,
                "com.cronos.onlinereview.autoscreening.tool.failure.No.RuleScreeningLogicName");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the rule
     * screening logic class is missing. Expected :
     * {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoRuleScreeningLogicClass() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/project_rules.xml");
        try {
            new Screener(ID,
                "com.cronos.onlinereview.autoscreening.tool.failure.No.RuleScreeningLogicClass");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the rule
     * screening logic succeedSeverity is missing. Expected :
     * {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoRuleScreeningLogicSucceedSeverity() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/project_rules.xml");
        try {
            new Screener(ID,
                "com.cronos.onlinereview.autoscreening.tool.failure.No.RuleScreeningLogic.succeedSeverity");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the rule
     * screening logic failSeverity is missing. Expected :
     * {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoRuleScreeningLogicFailSeverity() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/project_rules.xml");
        try {
            new Screener(ID,
                "com.cronos.onlinereview.autoscreening.tool.failure.No.RuleScreeningLogic.failSeverity");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

    /**
     * Test method for Screener(long, java.lang.String). In this case, the rule
     * screening logic errorSeverity is missing. Expected :
     * {@link IllegalArgumentException}.
     * @throws Exception
     */
    public void testScreener_NoRuleScreeningLogicErrorSeverity() throws Exception {
        TestHelper.unloadNS(NS);
        TestHelper.loadFile("failure/project_rules.xml");
        try {
            new Screener(ID,
                "com.cronos.onlinereview.autoscreening.tool.failure.No.RuleScreeningLogic.errorSeverity");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // should land here
        }
    }

}
